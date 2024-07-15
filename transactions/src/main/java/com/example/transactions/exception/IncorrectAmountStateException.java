package com.example.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IncorrectAmountStateException extends RuntimeException{
    public IncorrectAmountStateException(String message) {
        super(message);
    }
}
