package com.pyfyc.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyfyc.newsfeed.entity.Category;
import com.pyfyc.newsfeed.entity.Feed;
import com.pyfyc.newsfeed.exception.FeedNotFoundException;
import com.pyfyc.newsfeed.repository.CategoryRepository;
import com.pyfyc.newsfeed.repository.FeedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pyfyc.newsfeed.constant.DtoConstantTest.*;
import static com.pyfyc.newsfeed.constant.EntityConstantTest.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @MockBean
    FeedRepository feedRepository;

    @MockBean
    CategoryRepository categoryRepository;

    /**
     * Return 200 (OK) when create new feed.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenCreateFeed() throws Exception {
        when(feedRepository.save(any(Feed.class))).thenReturn(FEED);

        mockMvc.perform(post("http://localhost:" + port + "/feeds")
                        .content(objectMapper.writeValueAsBytes(CREATE_FEED_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(CREATE_FEED_DTO.getName()))
                .andExpect(jsonPath("$.description").value(CREATE_FEED_DTO.getDescription()));
    }

    /**
     * Return 400 (Bad Request) when create feed with invalid name.
     * @throws Exception
     */
    @Test
    void ReturnBadRequestWhenCreateFeedWithInvalidName() throws Exception {
        mockMvc.perform(post("http://localhost:" + port + "/feeds")
                        .content(objectMapper.writeValueAsBytes(CREATE_FEED_DTO_INVALID_NAME))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    /**
     * Return 200 (OK) when delete feed.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenDeleteFeed() throws Exception {
        when(feedRepository.findById(any(Long.class))).thenReturn(Optional.of(FEED));

        mockMvc.perform(delete("http://localhost:" + port + "/feeds/{id}", ID1))
                .andExpect(status().isOk());

        verify(feedRepository, times(1)).delete(FEED);
    }

    /**
     * Return 400 (Bad Request) when delete not existing feed.
     * @throws Exception
     */
    @Test
    void ReturnBadRequestWhenDeleteNotExistingFeed() throws Exception {
        mockMvc.perform(delete("http://localhost:" + port + "/feeds/{id}", ID1))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof FeedNotFoundException));
    }

    /**
     * Return 200 (OK) when update feed.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenUpdateFeed() throws Exception {
        when(feedRepository.findById(any(Long.class))).thenReturn(Optional.of(FEED_UPD));
        when(feedRepository.save(any(Feed.class))).thenReturn(FEED_UPD);

        mockMvc.perform(patch("http://localhost:" + port + "/feeds/{id}", ID1)
                        .content(objectMapper.writeValueAsBytes(UPDATE_FEED_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(UPDATE_FEED_DTO.getName()))
                .andExpect(jsonPath("$.description").value(UPDATE_FEED_DTO.getDescription()));
    }

    /**
     * Return 200 (OK) when get all news.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenGetNews() throws Exception {
        final List<Feed> feedList = new ArrayList<>();
        feedList.add(FEED);

        when(feedRepository.findAllByOrderByNameAsc(any(Pageable.class))).thenReturn(feedList);

        mockMvc.perform(get("http://localhost:" + port + "/feeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(FEED_DTO.getName()))
                .andExpect(jsonPath("$[0].description").value(FEED_DTO.getDescription()));
    }

    /**
     * Return 200 (OK) when get news by name.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenGetNewsByName() throws Exception {
        final List<Feed> feedList = new ArrayList<>();
        feedList.add(FEED);

        String name = "news";

        when(feedRepository.findByNameContainingIgnoreCaseOrderByNameAsc(any(String.class), any(Pageable.class))).thenReturn(feedList);

        mockMvc.perform(get("http://localhost:" + port + "/feeds/by-name")
                        .queryParam("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(FEED_DTO.getName()))
                .andExpect(jsonPath("$[0].description").value(FEED_DTO.getDescription()));
    }

    /**
     * Return 200 (OK) when get news by description.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenGetNewsByDesc() throws Exception {
        final List<Feed> feedList = new ArrayList<>();
        feedList.add(FEED);

        String description = "update";

        when(feedRepository.findByDescriptionContainingIgnoreCaseOrderByNameAsc(any(String.class), any(Pageable.class))).thenReturn(feedList);

        mockMvc.perform(get("http://localhost:" + port + "/feeds/by-desc")
                        .queryParam("desc", description))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(FEED_DTO.getName()))
                .andExpect(jsonPath("$[0].description").value(FEED_DTO.getDescription()));
    }

    /**
     * Return 200 (OK) when get news by category.
     * @throws Exception
     */
    @Test
    void ReturnOKWhenGetNewsByCat() throws Exception {
        final List<Feed> feedList = new ArrayList<>();
        feedList.add(FEED);

        when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(CATEGORY));
        when(feedRepository.findByCategoryOrderByNameAsc(any(Category.class), any(Pageable.class))).thenReturn(feedList);

        mockMvc.perform(get("http://localhost:" + port + "/feeds/by-cat")
                        .content(objectMapper.writeValueAsBytes(CATEGORY_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(FEED.getName()))
                .andExpect(jsonPath("$[0].description").value(FEED.getDescription()));
    }
}