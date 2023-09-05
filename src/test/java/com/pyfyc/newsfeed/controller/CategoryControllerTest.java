package com.pyfyc.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyfyc.newsfeed.dto.CreateCategoryDto;
import com.pyfyc.newsfeed.entity.Category;
import com.pyfyc.newsfeed.exception.CategoryNotFoundException;
import com.pyfyc.newsfeed.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @MockBean
    CategoryRepository categoryRepository;

    /**
     * Return 200 (OK) when create new category.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenCreateCategory() throws Exception {
        CreateCategoryDto createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setName("cat1");

        Category category = new Category();
        category.setId(1L);
        category.setName("cat1");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("http://localhost:" + port + "/categories")
                        .content(objectMapper.writeValueAsBytes(createCategoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(createCategoryDto.getName()));
    }

    /**
     * Return 400 (Bad Request) when create new category with invalid name.
     * @throws Exception
     */
    @Test
    void ReturnBadRequestWhenCreateCategoryWithInvalidName() throws Exception {
        CreateCategoryDto categoryDto = new CreateCategoryDto();
        categoryDto.setName("ca"); // Invalid name: size must be between 3 and 100

        mockMvc.perform(post("http://localhost:" + port + "/categories")
                        .content(objectMapper.writeValueAsBytes(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    /**
     * Return 200 (OK) when delete category.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenDeleteCategory() throws Exception {
        Long id = 1L;

        Category category = new Category();
        category.setId(1L);
        category.setName("cat1");

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(category));

        mockMvc.perform(delete("http://localhost:" + port + "/categories/{id}", id))
                .andExpect(status().isOk());

        verify(categoryRepository, times(1)).delete(category);
    }

    /**
     * Return 400 (Bad Request) when delete not existing category.
     * @throws Exception
     */
    @Test
    void ReturnBadRequestWhenDeleteNotExistingCategory() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("http://localhost:" + port + "/categories/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNotFoundException));
    }
}