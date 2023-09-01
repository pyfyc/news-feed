package com.pyfyc.newsfeed.controller;

import com.pyfyc.newsfeed.dto.CreateFeedDto;
import com.pyfyc.newsfeed.dto.FeedDto;
import com.pyfyc.newsfeed.dto.UpdateFeedDto;
import com.pyfyc.newsfeed.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping()
    @Operation(
            summary = "Add news",
            description = "Please fill in parameters to create news",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "News has been added (Created)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FeedDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "News was not added " +
                                    "due to failed validation (Bad Request)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "News was not added " +
                                    "due to bad category (Not Found)"
                    )
            }
    )
    public FeedDto createFeed(@RequestBody @Valid CreateFeedDto createFeedDto) {
        return feedService.create(createFeedDto);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete news",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "News has been deleted (No Content)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "News was not deleted " +
                                    "due to it was not found (Not Found)"
                    )
            }
    )
    public void deleteFeed(@PathVariable("id") Long id) {
        feedService.delete(id);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update news parameters",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News has been updated (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FeedDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "News was not updated " +
                                    "due to failed validation (Bad Request)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "News was not updated " +
                                    "due to it was not found (Not Found)"
                    )
            }
    )
    public FeedDto updateFeed(@PathVariable("id") Long id,
                              @RequestBody @Valid UpdateFeedDto updateFeedDto) {
        return feedService.update(id, updateFeedDto);
    }

    @GetMapping()
    @Operation(
            summary = "Get all news (with pagination)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All news (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FeedDto.class)
                            )
                    )
            }
    )
    public Collection<FeedDto> getFeed(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return feedService.getFeed(page, size);
    }
}
