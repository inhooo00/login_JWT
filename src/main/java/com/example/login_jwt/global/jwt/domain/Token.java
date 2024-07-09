package com.example.login_jwt.global.jwt.domain;

import com.example.login_jwt.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId")
    private Long tokenId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String refreshToken;

    @Builder
    private Token(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    public void refreshTokenUpdate(String refreshToken) {
        if (!this.refreshToken.equals(refreshToken)) {
            this.refreshToken = refreshToken;
        }
    }
}
