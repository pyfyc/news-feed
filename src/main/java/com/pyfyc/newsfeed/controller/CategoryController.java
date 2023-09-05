package com.pyfyc.newsfeed.controller;

import com.pyfyc.newsfeed.dto.CategoryDto;
import com.pyfyc.newsfeed.dto.CreateCategoryDto;
import com.pyfyc.newsfeed.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @Operation(
            summary = "Add category",
            description = "Please fill in parameters to add new category",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category has been added (Created)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CategoryDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Category was not added " +
                                    "due to failed validation (Bad Request)"
                    ),
                    @ApiResponse(
                            responseCode = "406",
                            description = "Category was not added " +
                                    "due to it already exists (Not Acceptable)"
                    )
            }
    )
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryDto createCategoryDto) {
        CategoryDto categoryDto = categoryService.create(createCategoryDto);
        return categoryDto;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Category has been deleted (No Content)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category was not deleted " +
                                    "due to it was not found (Not Found)"
                    )
            }
    )
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
