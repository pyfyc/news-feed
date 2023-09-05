package com.pyfyc.newsfeed.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FeedNotFoundException extends RuntimeException{

    private final long id;

    public FeedNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "News item not found, id = " + id;
    }
}
