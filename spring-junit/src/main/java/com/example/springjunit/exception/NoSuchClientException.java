package com.example.springjunit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchClientException extends RuntimeException{
    public NoSuchClientException(String message) {
        super(message);
    }
}
