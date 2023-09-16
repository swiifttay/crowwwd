package com.cs203.g1t4.backend.data.response.artist;

import com.cs203.g1t4.backend.data.response.Response;
import com.cs203.g1t4.backend.models.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleArtistResponse implements Response {

    private Artist artist;
}
