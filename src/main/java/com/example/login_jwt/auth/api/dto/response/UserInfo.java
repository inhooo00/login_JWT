package com.example.login_jwt.auth.api.dto.response;

public record UserInfo(
        String email,
        String name,
        String picture
) {
}
