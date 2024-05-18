package com.example.springdtostock.exception;

import com.example.springdtostock.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> list = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(status.value(), "Validation error", list));
    }

    @ExceptionHandler({
            NoSuchCustomerException.class,
            NoSuchProductException.class,
            NoSuchCategoryException.class,
            NoSuchBankCardException.class,
            NoSuchUserException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null));
    }

//    @ExceptionHandler(NoSuchProductException.class)
//    public ResponseEntity<ErrorResponse> handleNoSuchProductException(NoSuchProductException ex) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null));
//    }
//
//    @ExceptionHandler(NoSuchCategoryException.class)
//    public ResponseEntity<ErrorResponse> handleNoSuchCategoryException(NoSuchCategoryException ex) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null));
//    }
}