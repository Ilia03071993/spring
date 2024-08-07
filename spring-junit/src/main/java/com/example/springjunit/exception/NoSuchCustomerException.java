package com.example.springjunit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCustomerException extends RuntimeException{

    public NoSuchCustomerException(String message) {
        super(message);
    }
}
