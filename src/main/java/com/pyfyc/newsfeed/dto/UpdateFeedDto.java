package com.pyfyc.newsfeed.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFeedDto {

    // all fields are nullable, but if not null then need to validate

    @Size(min = 3, max = 100)
    private String name;

    @Size(min = 3, max = 100)
    private String description;

    private CategoryDto category;
}
