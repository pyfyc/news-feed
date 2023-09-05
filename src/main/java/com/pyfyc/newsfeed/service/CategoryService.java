package com.pyfyc.newsfeed.service;

import com.pyfyc.newsfeed.dto.CategoryDto;
import com.pyfyc.newsfeed.dto.CreateCategoryDto;
import com.pyfyc.newsfeed.entity.Category;
import com.pyfyc.newsfeed.exception.CategoryNotFoundException;
import com.pyfyc.newsfeed.mapper.CategoryMapper;
import com.pyfyc.newsfeed.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto create(CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        categoryMapper.create(category, createCategoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.delete(category);
    }
}
