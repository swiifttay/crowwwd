package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedUsernameException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import com.cs203.g1t4.backend.service.ArtistService;
import com.cs203.g1t4.backend.service.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {
    @Mock
    ArtistRepository artistRepository;

    @Mock
    S3Service s3Service;

    ArtistService artistService;

    Artist existingArtist;

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

        // mock AuthenticationServiceImpl
        artistService = new ArtistService(artistRepository, s3Service);
    }

    @Test
    void addArtist_NewArtist_ReturnSuccessResponse() {
        // arrange
        ArtistRequest artistRequest = ArtistRequest.builder()
                .name("Sabrina Carpenter")
                .website("www.sabrinacarpenter.com")
                .artistImage("sabrinacarpenterimage")
                .description("Upcoming popular artist in the world")
                .build();

        Artist savingArtist = Artist.builder()
                .name("Sabrina Carpenter")
                .website("www.sabrinacarpenter.com")
                .artistImage("sabrinacarpenterimage")
                .description("Upcoming popular artist in the world")
                .build();

        Artist newArtist = Artist.builder()
                .id("1235")
                .name("Sabrina Carpenter")
                .website("www.sabrinacarpenter.com")
                .artistImage("sabrinacarpenterimage")
                .description("Upcoming popular artist in the world")
                .build();

        // mock artistRepository "save" method
        when(artistRepository.save(any(Artist.class))).thenReturn(newArtist);

        // act
        Response response = artistService.addArtist(artistRequest);

        //assert
        assertTrue(response instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response;
        assertEquals("Artist has been created successfully", successResponse.getResponse());
        verify(artistRepository).save(savingArtist);
    }

    @Test
    void addArtist_DuplicateArtist_ReturnSuccessResponse() {
        // arrange

        // act

        // assert
    }

    @Test
    void deleteArtistById_ExistingArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(existingArtist));

        // mock artistRepository "deleteById" method
        doNothing().when(artistRepository).deleteById(any(String.class));

        // act
        Response response = artistService.deleteArtistById(artistId);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
        assertEquals(existingArtist, singleArtistResponse.getArtist());

        verify(artistRepository).findById(artistId);
        verify(artistRepository).deleteById(artistId);
    }

    @Test
    void deleteArtistById_invalidArtistId_ReturnInvalidArtistIdException() {
        // arrange
        String invalidArtistId = "1236";

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            artistService.deleteArtistById(invalidArtistId);
        });

        // assert
        verify(artistRepository).findById(invalidArtistId);
    }

//    @Test
//    void updateArtistById_ValidUpdates_ReturnSingleArtistResponse() {
//        // arrange
//        String artistId = "1234";
//
//        existingArtist = Artist.builder()
//                .id("1234")
//                .name("Taylor Swift Update")
//                .website("www.taylorswiftUpdate.com")
//                .artistImage("taylorswiftimage")
//                .artistImageURL("www.taylorswiftimage.com")
//                .description("Most popular artist in the world - Update")
//                .build();
//
//        ArtistRequest updateArtistRequest = ArtistRequest.builder()
//                .name("Taylor Swift Update")
//                .website("www.taylorswiftUpdate.com")
//                .artistImage("taylorswiftimageUpdate")
//                .description("Most popular artist in the world - Update")
//                .build();
//
//        // mock artistRepository "save" method
//        when(artistRepository.save(any(Artist.class))).thenReturn(existingArtist);
//
//        // act
//        Response response = artistService.updateArtistById(artistId, updateArtistRequest);
//
//        // assert
//        assertTrue(response instanceof SingleArtistResponse);
//        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
//        assertEquals(existingArtist, singleArtistResponse.getArtist());
//    }
}
