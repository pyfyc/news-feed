package com.pyfyc.newsfeed.service;

import com.pyfyc.newsfeed.dto.CreateFeedDto;
import com.pyfyc.newsfeed.dto.FeedDto;
import com.pyfyc.newsfeed.dto.UpdateFeedDto;
import com.pyfyc.newsfeed.entity.Feed;
import com.pyfyc.newsfeed.exception.FeedNotFoundException;
import com.pyfyc.newsfeed.mapper.FeedMapper;
import com.pyfyc.newsfeed.repository.FeedRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedMapper feedMapper;

    public FeedDto create(CreateFeedDto createFeedDto) {
        Feed feed = new Feed();
        feedMapper.create(feed, createFeedDto);
        return feedMapper.toDto(feedRepository.save(feed));
    }

    public void delete(Long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new FeedNotFoundException(id));
        feedRepository.delete(feed);
    }

    public FeedDto update(Long id, UpdateFeedDto updateFeedDto) {
        Feed feed = feedRepository.findById(id).orElseThrow(() -> new FeedNotFoundException(id));
        feedMapper.patch(feed, updateFeedDto);
        return feedMapper.toDto(feedRepository.save(feed));
    }
}
