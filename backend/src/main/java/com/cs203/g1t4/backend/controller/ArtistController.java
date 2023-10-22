package com.cs203.g1t4.backend.controller;

import com.cs203.g1t4.backend.data.request.artist.ArtistRequest;
import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping(value = "/addArtist", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> addArtist(@Valid @ModelAttribute ArtistRequest request, @RequestPart MultipartFile image) {

        Response response = artistService.addArtist(request, image);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteArtist/{artistId}")
    public ResponseEntity<Response> deleteArtist(@PathVariable String artistId) {

        Response response = artistService.deleteArtistById(artistId);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value="/updateArtist/{artistId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Response> updateArtist(@PathVariable String artistId, @Valid @ModelAttribute ArtistRequest request, @RequestPart(required = false) MultipartFile image) {

        Response response = artistService.updateArtistById(artistId, request, image);

        return ResponseEntity.ok(response);
    }

    @GetMapping("getArtist/{artistId}")
    public ResponseEntity<Response> getArtistById(@PathVariable String artistId) {

        Response response = artistService.getArtistById(artistId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllArtists")
    public ResponseEntity<Response> getAllArtist() {
        Response response = artistService.getAllArtist();

        return ResponseEntity.ok(response);
    }

}
