package com.pyfyc.newsfeed.exception;

public class CategoryNotFoundException extends RuntimeException {

    private final long id;

    public CategoryNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Категория с id = " + id + " не найдена!";
    }
}
