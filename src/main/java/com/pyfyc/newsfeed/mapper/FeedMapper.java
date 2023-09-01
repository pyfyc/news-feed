package com.pyfyc.newsfeed.mapper;

import com.pyfyc.newsfeed.dto.CategoryDto;
import com.pyfyc.newsfeed.dto.CreateFeedDto;
import com.pyfyc.newsfeed.dto.FeedDto;
import com.pyfyc.newsfeed.dto.UpdateFeedDto;
import com.pyfyc.newsfeed.entity.Category;
import com.pyfyc.newsfeed.entity.Feed;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = CategoryMapper.class)
public interface FeedMapper {

    //@Mapping(source = "name", target = "name")
    //@Mapping(source = "description", target = "description")
    @Mapping(target = "publishDate", expression = "java(java.time.LocalDate.now())")
    //@Mapping(source = "category", target = "category")
    void create(@MappingTarget Feed target, CreateFeedDto source);

    @Mapping(source = "name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "description", target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(@MappingTarget Feed target, UpdateFeedDto source);

    FeedDto toDto(Feed feed);

    Category map(CategoryDto value);
}
