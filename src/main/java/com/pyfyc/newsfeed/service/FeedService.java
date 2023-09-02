package com.pyfyc.newsfeed.service;

import com.pyfyc.newsfeed.dto.CreateFeedDto;
import com.pyfyc.newsfeed.dto.FeedDto;
import com.pyfyc.newsfeed.dto.UpdateFeedDto;
import com.pyfyc.newsfeed.entity.Feed;
import com.pyfyc.newsfeed.exception.FeedNotFoundException;
import com.pyfyc.newsfeed.mapper.FeedMapper;
import com.pyfyc.newsfeed.repository.FeedRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    public Collection<FeedDto> getNews(Integer page, Integer size) {
        Pageable pageable = getPage(page, size);
        return feedRepository.findAllByOrderByNameAsc(pageable).stream()
                .map(feed -> feedMapper.toDto(feed))
                .toList();
    }

    public Collection<FeedDto> getNewsByName(String name, Integer page, Integer size) {
        Pageable pageable = getPage(page, size);
        return feedRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name, pageable).stream()
                .map(feed -> feedMapper.toDto(feed))
                .toList();
    }

    public Collection<FeedDto> getNewsByDesc(String desc, Integer page, Integer size) {
        Pageable pageable = getPage(page, size);
        return feedRepository.findByDescriptionContainingIgnoreCaseOrderByNameAsc(desc, pageable).stream()
                .map(feed -> feedMapper.toDto(feed))
                .toList();
    }

    /**
     * Return page object if page and size parameters are not null.
     * If at least one of the parameters is null - return all pages.
     * @param page page number (starting from 0)
     * @param size number of items on the page
     * @return page object
     */
    private Pageable getPage(Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        return pageable;
    }
}
