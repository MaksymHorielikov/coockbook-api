package com.example.cookbookrestapi.dto.response;

import lombok.Data;

@Data
public class ResponseExceptionDto {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
