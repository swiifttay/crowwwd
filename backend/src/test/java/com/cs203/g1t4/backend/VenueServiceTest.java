package com.cs203.g1t4.backend;

import com.cs203.g1t4.backend.data.request.venue.VenueRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.data.response.venue.SingleVenueResponse;
import com.cs203.g1t4.backend.models.Venue;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.models.exceptions.InvalidVenueException;
import com.cs203.g1t4.backend.repository.VenueRepository;
import com.cs203.g1t4.backend.service.serviceImpl.VenueServiceImpl;
import com.cs203.g1t4.backend.service.services.S3Service;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {
    @Mock
    VenueRepository venueRepository;

    @Mock
    S3Service s3Service;

    @InjectMocks
    private VenueServiceImpl venueService;

    private Venue venue;
    MultipartFile image;
    @BeforeEach
    void setUp() {
        // arrange for all tests
        venue = Venue.builder()
                .id("1234")
                .address("Singapore National Stadium Road")
                .locationName("Singapore National Stadium")
                .postalCode("123456")
                .description("Location for concert")
                .venueImageName("SingaporeNationalStadium")
                .build();

        image = mock(MultipartFile.class);

        venueService = new VenueServiceImpl(venueRepository, s3Service);
        ReflectionTestUtils.setField(venueService, "bucketName", "your_bucket_name");
    }

    @Test
    void getVenue_ValidVenueId_ReturnSingleVenueResponse() {
        // arrange
        String venueImageUrl = "https://your_bucket_name.s3.ap-southeast-1.amazonaws.com/venue-images/1234/SingaporeNationalStadium";

        // mock venueRepository "findById" method
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));

        // act
        Response response = venueService.getVenue(venue.getId());

        // assert
        assertTrue(response instanceof SingleVenueResponse);
        SingleVenueResponse singleVenueResponse = (SingleVenueResponse) response;
        assertEquals(venue, singleVenueResponse.getVenue());
        assertEquals(venueImageUrl, singleVenueResponse.getImageURL());

    }

    @Test
    void getVenue_InvalidVenueId_ReturnInvalidVenueException() {
        // mock venueRepository "findById" method
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.empty());

        // act
        InvalidVenueException e = assertThrows(InvalidVenueException.class, () -> {
            venueService.getVenue("1235");
        });

        // assert
        verify(venueRepository).findById(eq("1235"));
    }

    @Test
    void createVenue_ValidRequest_ReturnSuccessResponse() {
        // arrange
        VenueRequest venueRequest = VenueRequest.builder()
                .address("Esplanade Road")
                .locationName("Esplanade")
                .postalCode("S123457")
                .description("Durian Architecture")
                .build();

        Venue newVenue = Venue.builder()
                .id("1234")
                .address("Esplanade Road")
                .locationName("Esplanade")
                .postalCode("S123457")
                .description("Durian Architecture")
                .build();

        ArgumentCaptor<Venue> venueArgumentCaptor = ArgumentCaptor.forClass(Venue.class);

        // mock the getOriginalFilename method
        when(image.getOriginalFilename()).thenReturn("imageName");

        // mock artistRepository "save" method
        when(venueRepository.save(venueArgumentCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Venue savedArtist = venueArgumentCaptor.getValue();
            savedArtist.setId("1234");
            return savedArtist;
        });

        // act
        Response response = venueService.createVenue(venueRequest, image);

        // assert
        assertTrue(response instanceof SuccessResponse);
        SuccessResponse successResponse = (SuccessResponse) response;
        assertEquals("Venue has been created successfully", successResponse.getResponse());
        verify(venueRepository).save(venueArgumentCaptor.getValue());
        verify(s3Service).putObject(
                eq("your_bucket_name"),
                eq("venue-images/%s/%s".formatted("1234", "imageName")),
                same(image)
        );
    }


    @Test
    void updateVenue_ValidRequestsWithImage_ReturnSingleVenueResponse () {
        // arrange
        Venue updatedVenue = Venue.builder()
                .id("1234")
                .address("Singapore National Stadium Road - U")
                .locationName("Singapore National Stadium - U")
                .postalCode("123457")
                .description("Location for concert - U")
                .venueImageName("SingaporeNationalStadium")
                .build();

        VenueRequest venueRequest = VenueRequest.builder()
                .address("Singapore National Stadium Road - U")
                .locationName("Singapore National Stadium - U")
                .postalCode("123457")
                .description("Location for concert - U")
                .build();


        ArgumentCaptor<Venue> venueArgumentCaptor = ArgumentCaptor.forClass(Venue.class);

        // mock venueRepository "findById" method
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));

        // mock artistRepository "save" method
        when(venueRepository.save(venueArgumentCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Venue savedArtist = venueArgumentCaptor.getValue();
            return savedArtist;
        });

        // act
        Response response = venueService.updateVenue(venue.getId(), venueRequest, image);

        // assert
        assertTrue(response instanceof SingleVenueResponse);
        SingleVenueResponse singleVenueResponse = (SingleVenueResponse) response;
        assertEquals(updatedVenue, singleVenueResponse.getVenue());
        verify(venueRepository).save(venueArgumentCaptor.getValue());
        verify(image, times(0)).getOriginalFilename();
        verify(s3Service).putObject(
                eq("your_bucket_name"),
                eq("venue-images/%s/%s".formatted("1234", venue.getVenueImageName())),
                same(image)
        );

    }

    @Test
    void updateVenue_ValidRequestsWithoutImage_ReturnSingleVenueResponse () {
        // arrange
        Venue updatedVenue = Venue.builder()
                .id("1234")
                .address("Singapore National Stadium Road - U")
                .locationName("Singapore National Stadium - U")
                .postalCode("123457")
                .description("Location for concert - U")
                .venueImageName("SingaporeNationalStadium")
                .build();

        VenueRequest venueRequest = VenueRequest.builder()
                .address("Singapore National Stadium Road - U")
                .locationName("Singapore National Stadium - U")
                .postalCode("123457")
                .description("Location for concert - U")
                .build();


        ArgumentCaptor<Venue> venueArgumentCaptor = ArgumentCaptor.forClass(Venue.class);

        // mock venueRepository "findById" method
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));

        // mock artistRepository "save" method
        when(venueRepository.save(venueArgumentCaptor.capture())).thenAnswer(invocation -> {
            // modify the artist object to have an id
            Venue savedArtist = venueArgumentCaptor.getValue();
            return savedArtist;
        });

        // act
        Response response = venueService.updateVenue(venue.getId(), venueRequest, null);

        // assert
        assertTrue(response instanceof SingleVenueResponse);
        SingleVenueResponse singleVenueResponse = (SingleVenueResponse) response;
        assertEquals(updatedVenue, singleVenueResponse.getVenue());
        verify(venueRepository).save(venueArgumentCaptor.getValue());
    }

    @Test
    void removeVenue_ValidVenueId_ReturnSingleVenueResponse() {

        // mock venueRepository "findById" method
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.of(venue));


        // act
        Response response = venueService.removeVenue(venue.getId());

        // assert
        assertTrue(response instanceof SingleVenueResponse);
        SingleVenueResponse singleVenueResponse = (SingleVenueResponse) response;
        assertEquals(venue, singleVenueResponse.getVenue());
        verify(venueRepository).deleteById(venue.getId());

    }

    @Test
    void removeVenue_InvalidVenueId_ReturnInvalidVenueException() {

        // mock venueRepository "findById" method
        when(venueRepository.findById(any(String.class))).thenReturn(Optional.empty());


        // act
        InvalidVenueException e = assertThrows(InvalidVenueException.class, () -> {
            venueService.removeVenue("1235");
        });

        // assert
        verify(venueRepository).findById("1235");
    }
}
