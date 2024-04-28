package com.example.springdtostock.exception;

public class NoSuchBankCardException extends RuntimeException{
    public NoSuchBankCardException(String message) {
        super(message);
    }
}
