package com.example.login_jwt.member.api.dto.response;

import lombok.Builder;

@Builder
public record MemberCommentResDto(
        String nickName,
        String occupation
) {
}
