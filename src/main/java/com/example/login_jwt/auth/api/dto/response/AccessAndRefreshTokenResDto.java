package com.example.login_jwt.auth.api.dto.response;

import lombok.Builder;

@Builder
public record AccessAndRefreshTokenResDto(
        String accessToken,
        String refreshToken
) {
}
