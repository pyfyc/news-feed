package com.pyfyc.newsfeed.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryNotFoundException extends RuntimeException {

    private final long id;

    public CategoryNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Category not found, id = " + id;
    }
}
