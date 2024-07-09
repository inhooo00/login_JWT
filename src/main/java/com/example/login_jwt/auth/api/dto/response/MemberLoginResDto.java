package com.example.login_jwt.auth.api.dto.response;

import com.example.login_jwt.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberLoginResDto(
        Member findMember
) {
    public static MemberLoginResDto from(Member member) {
        return MemberLoginResDto.builder()
                .findMember(member)
                .build();
    }
}
