package com.example.login_jwt.member.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberGitHubUrlUpdateReqDto(
        @NotBlank
        String gitHubUrl
) {
}
