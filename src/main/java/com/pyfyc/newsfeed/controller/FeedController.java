package com.pyfyc.newsfeed.controller;

import com.pyfyc.newsfeed.dto.CategoryDto;
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
            description = "Return all news sorted by name asc.",
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
    public Collection<FeedDto> getNews(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return feedService.getNews(page, size);
    }

    @GetMapping("/by-name")
    @Operation(
            summary = "Get news by name (with pagination)",
            description = "Return all news with name containing text (name parameter) " +
                    "ignoring case sorted by name asc.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News by name (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FeedDto.class)
                            )
                    )
            }
    )
    public Collection<FeedDto> getNewsByName(
            @RequestParam(required = true) String name,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return feedService.getNewsByName(name, page, size);
    }

    @GetMapping("/by-desc")
    @Operation(
            summary = "Get news by description (with pagination)",
            description = "Return all news with description containing text (desc parameter) " +
                    "ignoring case sorted by name asc.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News by description (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FeedDto.class)
                            )
                    )
            }
    )
    public Collection<FeedDto> getNewsByDesc(
            @RequestParam(required = true) String desc,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return feedService.getNewsByDesc(desc, page, size);
    }

    @GetMapping("/by-cat")
    @Operation(
            summary = "Get news by category (with pagination)",
            description = "Return all news of the category (category parameter) " +
                    "sorted by name asc.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News by category (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FeedDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Category was not found (Internal Server Error)"
                    )
            }
    )
    public Collection<FeedDto> getNewsByCat(
            @RequestBody CategoryDto category,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return feedService.getNewsByCat(category, page, size);
    }
}
