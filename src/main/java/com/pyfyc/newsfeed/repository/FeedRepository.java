package com.pyfyc.newsfeed.repository;

import com.pyfyc.newsfeed.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findAllByOrderByNameAsc(Pageable pageable);
}
