package com.cs203.g1t4.backend.data.request.event;

import com.cs203.g1t4.backend.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatingDetailsRequest {

    @NotNull(message = "Categories is required")
    private List<Category> categories;
}
