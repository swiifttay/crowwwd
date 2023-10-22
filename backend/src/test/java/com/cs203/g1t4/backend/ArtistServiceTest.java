package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedArtistException;
import com.cs203.g1t4.backend.models.exceptions.InvalidCredentialsException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.service.ArtistService;
import com.cs203.g1t4.backend.service.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
    @Mock
    ArtistRepository artistRepository;

    @Mock
    S3Service s3Service;

    @InjectMocks
    private ArtistService artistService;

    Artist existingArtist;

    MultipartFile image;

    @BeforeEach
    void setUp() {
        // arrange for all tests
        existingArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .website("www.taylorswift.com")
                .artistImage("taylorswiftimage")
                .artistImageURL("www.taylorswiftimage.com")
                .description("Most popular artist in the world")
                .build();

        image = mock(MultipartFile.class);

        artistService = new ArtistService(artistRepository, s3Service);
        ReflectionTestUtils.setField(artistService, "bucketName", "your_bucket_name");
    }

    @Test
    void addArtist_NewArtist_ReturnSuccessResponse() {
        // arrange
        ArtistRequest artistRequest = ArtistRequest.builder()
                .name("Sabrina Carpenter")
                .website("www.sabrinacarpenter.com")
                .description("Upcoming popular artist in the world")
                .build();

        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);

        // mock artistRepository "findByName" method
        when(artistRepository.findByName(any(String.class))).thenReturn(Optional.empty());

        // mock MultipartFile "getOriginalFilename" method
        when(image.getOriginalFilename()).thenReturn("sabrinacarpenterimage");

        // mock artistRepository "save" method
        when(artistRepository.save(artistCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Artist savedArtist = artistCaptor.getValue();
            savedArtist.setId("1235");
            return savedArtist;
        });

        // act
        Response response = artistService.addArtist(artistRequest, image);

        // assert
        assertTrue(response instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response;
        assertEquals("Artist has been created successfully", successResponse.getResponse());
        verify(artistRepository).save(artistCaptor.getValue());
        verify(s3Service).putObject(
                eq("your_bucket_name"),
                eq("artist-images/%s/sabrinacarpenterimage".formatted("1235")),
                same(image)
        );
    }

    @Test
    void addArtist_DuplicateArtist_ReturnDuplicatedArtistException() {
        // arrange
        ArtistRequest artistRequest = ArtistRequest.builder()
                .name("Taylor Swift")
                .website("www.taylorswift2.com")
                .description("Most popular artist in the world too")
                .build();

        // mock artistRepository "findByName" method
        when(artistRepository.findByName(any(String.class))).thenReturn(Optional.of(existingArtist));

        // act
        DuplicatedArtistException e = assertThrows(DuplicatedArtistException.class, () -> {
            artistService.addArtist(artistRequest, null);
        });

        // assert
        assertEquals("Artist: Taylor Swift already exists", e.getMessage());
        verify(artistRepository).findByName(eq("Taylor Swift"));
    }

//    @Test
//    void addArtist_DuplicateArtist_ReturnSuccessResponse() {
//        // arrange
//
//        // act
//
//        // assert
//    }
//
//    @Test
//    void deleteArtistById_ExistingArtist_ReturnSingleArtistResponse() {
//        // arrange
//        String artistId = "1234";
//
//        // mock artistRepository "findById" method
//        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(existingArtist));
//
//        // mock artistRepository "deleteById" method
//        doNothing().when(artistRepository).deleteById(any(String.class));
//
//        // act
//        Response response = artistService.deleteArtistById(artistId);
//
//        // assert
//        assertTrue(response instanceof SingleArtistResponse);
//        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
//        assertEquals(existingArtist, singleArtistResponse.getArtist());
//
//        verify(artistRepository).findById(artistId);
//        verify(artistRepository).deleteById(artistId);
//    }
//
//    @Test
//    void deleteArtistById_invalidArtistId_ReturnInvalidArtistIdException() {
//        // arrange
//        String invalidArtistId = "1236";
//
//        // mock artistRepository "findById" method
//        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());
//
//        // act
//        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
//            artistService.deleteArtistById(invalidArtistId);
//        });
//
//        // assert
//        verify(artistRepository).findById(invalidArtistId);
//    }
//
////    @Test
////    void updateArtistById_ValidUpdates_ReturnSingleArtistResponse() {
////        // arrange
////        String artistId = "1234";
////
////        existingArtist = Artist.builder()
////                .id("1234")
////                .name("Taylor Swift Update")
////                .website("www.taylorswiftUpdate.com")
////                .artistImage("taylorswiftimage")
////                .artistImageURL("www.taylorswiftimage.com")
////                .description("Most popular artist in the world - Update")
////                .build();
////
////        ArtistRequest updateArtistRequest = ArtistRequest.builder()
////                .name("Taylor Swift Update")
////                .website("www.taylorswiftUpdate.com")
////                .artistImage("taylorswiftimageUpdate")
////                .description("Most popular artist in the world - Update")
////                .build();
////
////        // mock artistRepository "save" method
////        when(artistRepository.save(any(Artist.class))).thenReturn(existingArtist);
////
////        // act
////        Response response = artistService.updateArtistById(artistId, updateArtistRequest);
////
////        // assert
////        assertTrue(response instanceof SingleArtistResponse);
////        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
////        assertEquals(existingArtist, singleArtistResponse.getArtist());
////    }
}
