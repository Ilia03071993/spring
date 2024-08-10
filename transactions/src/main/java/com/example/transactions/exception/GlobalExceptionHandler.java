package com.example.transactions.exception;

import com.example.transactions.entity.Log;
import com.example.transactions.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {
    private final AuditService auditService;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(status)
                .body(errors);
    }

    @ExceptionHandler({NoSuchOrderException.class})
    public ResponseEntity<?> handleNoSuchOrderException(Exception ex, HandlerMethod handlerMethod) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("operation", handlerMethod.getMethod().getName());
        errors.put("message", ex.getMessage());
        String dateTimeStr = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        errors.put("datetime", dateTimeStr);

        auditService.addLog(new Log(errors.get("operation"), errors.get("message")));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errors);
    }
}