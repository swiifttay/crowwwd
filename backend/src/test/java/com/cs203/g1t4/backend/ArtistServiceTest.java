package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.ArtistResponse;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.DuplicatedArtistException;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
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

import java.util.Arrays;
import java.util.List;
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

    Artist customArtist;

    Artist spotifyArtist;

    MultipartFile image;

    @BeforeEach
    void setUp() {
        // arrange for all tests
        customArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .website("www.taylorswift.com")
                .artistImage("taylorswiftimage")
                .description("Most popular artist in the world")
                .build();

        spotifyArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .website("www.taylorswift.com")
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
        when(artistRepository.findByName(any(String.class))).thenReturn(Optional.of(customArtist));

        // act
        DuplicatedArtistException e = assertThrows(DuplicatedArtistException.class, () -> {
            artistService.addArtist(artistRequest, null);
        });

        // assert
        assertEquals("Artist: Taylor Swift already exists", e.getMessage());
        verify(artistRepository).findByName(eq("Taylor Swift"));
    }

    @Test
    void deleteArtistById_ExistingArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // mock artistRepository "deleteById" method
        doNothing().when(artistRepository).deleteById(any(String.class));

        // act
        Response response = artistService.deleteArtistById(artistId);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
        assertEquals(customArtist, singleArtistResponse.getArtist());

        verify(artistRepository).findById(artistId);
        verify(artistRepository).deleteById(artistId);
    }

    @Test
    void deleteArtistById_InvalidArtistId_ReturnInvalidArtistIdException() {
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

    @Test
    void updateArtistById_ValidUpdatesWithoutImage_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);

        ArtistRequest updateArtistRequest = ArtistRequest.builder()
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .description("Most popular artist in the world - Update")
                .build();

        Artist updatedArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .artistImage("taylorswiftimage")
                .description("Most popular artist in the world - Update")
                .build();

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // mock artistRepository "save" method
        when(artistRepository.save(artistCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Artist savedArtist = artistCaptor.getValue();
            return savedArtist;
        });

        // act
        Response response = artistService.updateArtistById(artistId, updateArtistRequest, null);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
        assertEquals(updatedArtist, singleArtistResponse.getArtist());
    }

    @Test
    void updateArtistById_ValidUpdatesWithImageAndPreviousCustomArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);

        ArtistRequest updateArtistRequest = ArtistRequest.builder()
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .description("Most popular artist in the world - Update")
                .build();

        Artist updatedArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .artistImage("taylorswiftimage")
                .description("Most popular artist in the world - Update")
                .build();

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // mock artistRepository "save" method
        when(artistRepository.save(artistCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Artist savedArtist = artistCaptor.getValue();
            return savedArtist;
        });

        // act
        Response response = artistService.updateArtistById(artistId, updateArtistRequest, image);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
        assertEquals(updatedArtist, singleArtistResponse.getArtist());
        verify(artistRepository).save(artistCaptor.getValue());
        verify(image, times(0)).getOriginalFilename();
        verify(s3Service).putObject(
                eq("your_bucket_name"),
                eq("artist-images/%s/%s".formatted("1234", customArtist.getArtistImage())),
                same(image)
        );
    }

    @Test
    void updateArtistById_ValidUpdatesWithoutImageAndPreviousSpotifyArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);

        ArtistRequest updateArtistRequest = ArtistRequest.builder()
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .description("Most popular artist in the world - Update")
                .build();

        Artist updatedArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .artistImageURL("www.taylorswiftimage.com")
                .description("Most popular artist in the world - Update")
                .build();

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(spotifyArtist));

        // mock artistRepository "save" method
        when(artistRepository.save(artistCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Artist savedArtist = artistCaptor.getValue();
            return savedArtist;
        });

        // act
        Response response = artistService.updateArtistById(artistId, updateArtistRequest, null);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
        assertEquals(updatedArtist, singleArtistResponse.getArtist());
        verify(artistRepository).save(artistCaptor.getValue());
    }

    @Test
    void updateArtistById_ValidUpdatesWithImageAndPreviousSpotifyArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);

        ArtistRequest updateArtistRequest = ArtistRequest.builder()
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .description("Most popular artist in the world - Update")
                .build();

        Artist updatedArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift Update")
                .website("www.taylorswiftUpdate.com")
                .artistImage("taylorswiftimageupdate")
                .description("Most popular artist in the world - Update")
                .build();

        // mock MultipartFile "getOriginalFilename" method
        when(image.getOriginalFilename()).thenReturn("taylorswiftimageupdate");

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(spotifyArtist));

        // mock artistRepository "save" method
        when(artistRepository.save(artistCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Artist savedArtist = artistCaptor.getValue();
            return savedArtist;
        });

        // act
        Response response = artistService.updateArtistById(artistId, updateArtistRequest, image);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse singleArtistResponse = (SingleArtistResponse) response;
        assertEquals(updatedArtist, singleArtistResponse.getArtist());
        verify(artistRepository).save(artistCaptor.getValue());
        verify(s3Service).putObject(
                eq("your_bucket_name"),
                eq("artist-images/%s/%s".formatted("1234", "taylorswiftimageupdate")),
                same(image)
        );
    }

    @Test
    void updateArtistById_InvalidUpdateArtistName_ReturnDuplicatedArtistException() {
        // arrange
        String artistId = "1234";

        ArtistRequest updateArtistRequest = ArtistRequest.builder()
                .name("Sabrina Carpenter")
                .website("www.sabrinacarpenter.com")
                .description("Upcoming popular artist in the world")
                .build();

        Artist duplicatedArtist = Artist.builder()
                .id("1234")
                .name("Sabrina Carpenter")
                .website("www.sabrinacarpenter.com")
                .artistImage("sabrinacarpenterimage")
                .description("Upcoming popular artist in the world")
                .build();

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // mock artistRepository "findByName" method
        when(artistRepository.findByName(any(String.class))).thenReturn(Optional.of(duplicatedArtist));

        // act
        DuplicatedArtistException e = assertThrows(DuplicatedArtistException.class, () -> {
            artistService.updateArtistById(artistId, updateArtistRequest, null);
        });

        // assert
        assertEquals("Artist: Sabrina Carpenter already exists", e.getMessage());
        verify(artistRepository).findByName(eq("Sabrina Carpenter"));
    }

    @Test
    void getArtistById_ValidArtistIdForCustomArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        Artist editedArtist = Artist.builder()
                .id("1234")
                .name("Taylor Swift")
                .website("www.taylorswift.com")
                .artistImage("taylorswiftimage")
                .artistImageURL("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted("your_bucket_name", "1234", "taylorswiftimage"))
                .description("Most popular artist in the world")
                .build();

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(customArtist));

        // act
        Response response = artistService.getArtistById(artistId);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse successResponse = (SingleArtistResponse) response;
        assertEquals(editedArtist, successResponse.getArtist());
    }

    @Test
    void getArtistById_ValidArtistIdForSpotifyArtist_ReturnSingleArtistResponse() {
        // arrange
        String artistId = "1234";

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.of(spotifyArtist));

        // act
        Response response = artistService.getArtistById(artistId);

        // assert
        assertTrue(response instanceof SingleArtistResponse);
        SingleArtistResponse successResponse = (SingleArtistResponse) response;
        assertEquals(spotifyArtist, successResponse.getArtist());
    }

    @Test
    void getArtistById_InvalidArtistId_ReturnSingleArtistResponse() {
        // arrange
        String invalidArtistId = "1234";

        // mock artistRepository "findById" method
        when(artistRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidArtistIdException e = assertThrows(InvalidArtistIdException.class, () -> {
            artistService.deleteArtistById(invalidArtistId);
        });

        // assert
        verify(artistRepository).findById(invalidArtistId);
    }

    @Test
    void getAllArtist_NoParameter_ReturnSingleArtistResponse() {
        // arrange
        List<Artist> allArtists = Arrays.asList(customArtist, spotifyArtist);

        // mock artistRepository "findAll" method
        when(artistRepository.findAll()).thenReturn(allArtists);

        // Call the method to be tested
        Response response = artistService.getAllArtist();

        // Verify the behavior of the service method
        assertTrue(response instanceof ArtistResponse);
        ArtistResponse listOfArtistResponse = (ArtistResponse) response;
        List<Artist> listOfArtistInResponse = listOfArtistResponse.getArtists();
        assertEquals(allArtists, listOfArtistInResponse);
        assertEquals(2, listOfArtistInResponse.size());
        assertEquals("https://%s.s3.ap-southeast-1.amazonaws.com/artist-images/%s/%s".formatted("your_bucket_name", "1234", "taylorswiftimage"),listOfArtistInResponse.get(0).getArtistImageURL());
        assertEquals("www.taylorswiftimage.com",listOfArtistInResponse.get(1).getArtistImageURL());

    }

}
