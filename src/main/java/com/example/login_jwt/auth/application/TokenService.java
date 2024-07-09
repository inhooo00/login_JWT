package com.example.login_jwt.auth.application;

import com.example.login_jwt.auth.api.dto.request.RefreshTokenReqDto;
import com.example.login_jwt.auth.api.dto.response.MemberLoginResDto;
import com.example.login_jwt.auth.exception.InvalidTokenException;
import com.example.login_jwt.global.jwt.TokenProvider;
import com.example.login_jwt.global.jwt.api.dto.TokenDto;
import com.example.login_jwt.global.jwt.domain.Token;
import com.example.login_jwt.global.jwt.domain.repository.TokenRepository;
import com.example.login_jwt.member.domain.Member;
import com.example.login_jwt.member.domain.repository.MemberRepository;
import com.example.login_jwt.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TokenService {

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    public TokenService(TokenProvider tokenProvider, TokenRepository tokenRepository, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public TokenDto getToken(MemberLoginResDto memberLoginResDto) {
        TokenDto tokenDto = tokenProvider.generateToken(memberLoginResDto.findMember().getEmail());

        tokenSaveAndUpdate(memberLoginResDto, tokenDto);

        return tokenDto;
    }

    private void tokenSaveAndUpdate(MemberLoginResDto memberLoginResDto, TokenDto tokenDto) {
        if (!tokenRepository.existsByMember(memberLoginResDto.findMember())) {
            tokenRepository.save(Token.builder()
                    .member(memberLoginResDto.findMember())
                    .refreshToken(tokenDto.refreshToken())
                    .build());
        }

        refreshTokenUpdate(memberLoginResDto, tokenDto);
    }

    private void refreshTokenUpdate(MemberLoginResDto memberLoginResDto, TokenDto tokenDto) {
        Token token = tokenRepository.findByMember(memberLoginResDto.findMember()).orElseThrow();
        token.refreshTokenUpdate(tokenDto.refreshToken());
    }

    @Transactional
    public TokenDto generateAccessToken(RefreshTokenReqDto refreshTokenReqDto) {
        if (!tokenRepository.existsByRefreshToken(refreshTokenReqDto.refreshToken()) || !tokenProvider.validateToken(refreshTokenReqDto.refreshToken())) {
            throw new RuntimeException();
        }

        Token token = tokenRepository.findByRefreshToken(refreshTokenReqDto.refreshToken()).orElseThrow();
        Member member = memberRepository.findById(token.getMember().getMemberId()).orElseThrow(MemberNotFoundException::new);

        return tokenProvider.generateAccessTokenByRefreshToken(member.getEmail(), token.getRefreshToken());
    }

}
