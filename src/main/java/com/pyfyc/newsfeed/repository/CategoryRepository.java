package com.pyfyc.newsfeed.repository;

import com.pyfyc.newsfeed.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
