package com.ledok.spring.productservice.advice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductIncorrectData {
    private String message;
    private String error;
    private LocalDateTime timestamp;
    private String path;

    public ProductIncorrectData(String message, String error) {
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }
}