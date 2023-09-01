package com.pyfyc.newsfeed.repository;

import com.pyfyc.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

}
