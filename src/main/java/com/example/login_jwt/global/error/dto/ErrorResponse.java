package com.example.login_jwt.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}