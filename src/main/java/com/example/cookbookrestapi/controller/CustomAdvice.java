package com.example.cookbookrestapi.controller;

import com.example.cookbookrestapi.dto.response.ResponseExceptionDto;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseExceptionDto> handleException(RuntimeException e) {
        ResponseExceptionDto response = new ResponseExceptionDto();
        response.setTimestamp(LocalDateTime.now().toString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.name());
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
