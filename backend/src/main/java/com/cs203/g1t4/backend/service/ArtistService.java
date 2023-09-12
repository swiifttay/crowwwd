package com.cs203.g1t4.backend.service;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.data.response.artist.ArtistResponse;
import com.cs203.g1t4.backend.data.response.artist.SingleArtistResponse;
import com.cs203.g1t4.backend.data.response.common.SuccessResponse;
import com.cs203.g1t4.backend.models.Artist;
import com.cs203.g1t4.backend.models.exceptions.InvalidArtistIdException;
import com.cs203.g1t4.backend.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public Response addArtist(ArtistRequest request) {

        Artist artist = Artist.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(request.getArtistImage())
                .build();

        artistRepository.save(artist);

        return SuccessResponse.builder()
                .response("Artist has been created successfully")
                .build();
    }

    public Response deleteArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        artistRepository.deleteById(artistId);

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response updateArtistById(String artistId, ArtistRequest request) {

        Artist oldArtist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        Artist newArtist = Artist.builder()
                .id(oldArtist.getId())
                .name(request.getName())
                .description(request.getDescription())
                .website(request.getWebsite())
                .artistImage(request.getArtistImage())
                .build();

        artistRepository.save(newArtist);

        return SingleArtistResponse.builder()
                .artist(newArtist)
                .build();
    }

    public Response findArtistById(String artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new InvalidArtistIdException(artistId));

        return SingleArtistResponse.builder()
                .artist(artist)
                .build();
    }

    public Response getAllArtist() {

        List<Artist> artistList = artistRepository.findAll();

        return ArtistResponse.builder()
                .artists(artistList)
                .build();
    }
}
