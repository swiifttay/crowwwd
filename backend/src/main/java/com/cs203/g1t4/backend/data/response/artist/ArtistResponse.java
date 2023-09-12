package com.cs203.g1t4.backend.data.response.artist;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponse implements Response {

    private List<Artist> artists;
}
