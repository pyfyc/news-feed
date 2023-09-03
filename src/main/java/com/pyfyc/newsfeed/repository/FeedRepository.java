package com.pyfyc.newsfeed.repository;

import com.pyfyc.newsfeed.entity.Category;
import com.pyfyc.newsfeed.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findAllByOrderByNameAsc(Pageable page);
    List<Feed> findByNameContainingIgnoreCaseOrderByNameAsc(String name, Pageable page);
    List<Feed> findByDescriptionContainingIgnoreCaseOrderByNameAsc(String desc, Pageable page);
    List<Feed> findByCategoryOrderByNameAsc(Category category, Pageable page);
}
