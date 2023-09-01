package com.pyfyc.newsfeed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateFeedDto {

    // all fields are mandatory, validation - not null

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    @Size(min = 3, max = 100)
    private String description;

    private LocalDate publishDate;

    private CategoryDto category;
}
