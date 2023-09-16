package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping("/addArtist")
    public ResponseEntity<Response> addArtist(@Valid @RequestBody ArtistRequest request) {

        Response response = artistService.addArtist(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteArtist/{artistId}")
    public ResponseEntity<Response> deleteArtist(@PathVariable String artistId) {

        Response response = artistService.deleteArtistById(artistId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateArtist/{artistId}")
    public ResponseEntity<Response> updateArtist(@PathVariable String artistId, @Valid @RequestBody ArtistRequest request) {

        Response response = artistService.updateArtistById(artistId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("getArtist/{artistId}")
    public ResponseEntity<Response> getArtistById(@PathVariable String artistId) {

        Response response = artistService.findArtistById(artistId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllArtists")
    public ResponseEntity<Response> getAllArtist() {
        Response response = artistService.getAllArtist();

        return ResponseEntity.ok(response);
    }


    @PostMapping("{artistId}/artist-image")
    public ResponseEntity<Response> uploadArtistImage(
            @PathVariable("artistId") String artistId,
            @RequestBody MultipartFile multipartFile) {

        // Upload the artist image
        Response response = artistService.uploadArtistImage(artistId, multipartFile);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{artistId}/artist-image")
    public ResponseEntity<Response> getArtistImage(@PathVariable("artistId") String artistId) {

        // Upload the artist image
        Response response = artistService.getArtistImage(artistId);

        return ResponseEntity.ok(response);
    }

}
