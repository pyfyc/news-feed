package com.pyfyc.newsfeed.mapper;

import com.pyfyc.newsfeed.dto.CategoryDto;
import com.pyfyc.newsfeed.dto.CreateCategoryDto;
import com.pyfyc.newsfeed.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = FeedMapper.class)
public interface CategoryMapper {

    @Mapping(source = "name", target = "name")
    void create(@MappingTarget Category target, CreateCategoryDto source);

    CategoryDto toDto(Category category);

    Category map(CategoryDto categoryDto);
}
