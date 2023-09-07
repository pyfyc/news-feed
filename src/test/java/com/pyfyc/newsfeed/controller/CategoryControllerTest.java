package com.pyfyc.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.pyfyc.newsfeed.constant.DtoConstantTest.CREATE_CATEGORY_DTO;
import static com.pyfyc.newsfeed.constant.DtoConstantTest.CREATE_CATEGORY_DTO_INVALID_NAME;
import static com.pyfyc.newsfeed.constant.EntityConstantTest.CATEGORY;
import static com.pyfyc.newsfeed.constant.EntityConstantTest.ID1;
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
        when(categoryRepository.save(any(Category.class))).thenReturn(CATEGORY);

        mockMvc.perform(post("http://localhost:" + port + "/categories")
                        .content(objectMapper.writeValueAsBytes(CREATE_CATEGORY_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(CREATE_CATEGORY_DTO.getName()));
    }

    /**
     * Return 400 (Bad Request) when create new category with invalid name.
     * @throws Exception
     */
    @Test
    void ReturnBadRequestWhenCreateCategoryWithInvalidName() throws Exception {
        mockMvc.perform(post("http://localhost:" + port + "/categories")
                        .content(objectMapper.writeValueAsBytes(CREATE_CATEGORY_DTO_INVALID_NAME))
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
        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(CATEGORY));

        mockMvc.perform(delete("http://localhost:" + port + "/categories/{id}", ID1))
                .andExpect(status().isOk());

        verify(categoryRepository, times(1)).delete(CATEGORY);
    }

    /**
     * Return 400 (Bad Request) when delete not existing category.
     * @throws Exception
     */
    @Test
    void ReturnBadRequestWhenDeleteNotExistingCategory() throws Exception {
        mockMvc.perform(delete("http://localhost:" + port + "/categories/{id}", ID1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNotFoundException));
    }
}