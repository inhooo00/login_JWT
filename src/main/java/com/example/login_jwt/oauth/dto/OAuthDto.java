package com.example.login_jwt.oauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class OAuthDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserResponse {
        private String socialId;
        private String socialType;
        private String email;
        private String nickName;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequest {

        private String socialId;

        @NotBlank(message = "소셜 타입은 필수입니다. ")
        private String socialType;

        @NotBlank(message = "이메일은 필수입니다. ")
        @Email(message = "정확한 이메일 형식으로 입력해주세요. ")
        private String email;

        @NotBlank(message = "닉네임은 입력해주세요. ")
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하로 입력해주세요. ")
        private String nickName;

        // 추가할수도 있음
    }
}
