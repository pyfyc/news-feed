package com.pyfyc.newsfeed.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeedDto {

    private String name;
    private String description;
    private LocalDate publishDate;
}
