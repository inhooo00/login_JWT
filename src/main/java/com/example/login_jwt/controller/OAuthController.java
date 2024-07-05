package com.example.login_jwt.controller;

import com.example.login_jwt.oauth.GoogleOauthService;
import com.example.login_jwt.oauth.KakaoOAuthService;
import com.example.login_jwt.oauth.dto.OAuthDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "로그인/회원가입", description = "로그인/회원가입을 담당하는 api그룹")
@RequestMapping("/api/v1/oauth2")
public class OAuthController {

    private final KakaoOAuthService kakaoOAuthService;
    private final GoogleOauthService googleOAuthService;

    @GetMapping("callback/kakao")
    @Operation(
            summary = "카카오 로그인 콜백",
            description = "카카오 로그인 콜백 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카카오 로그인 콜백 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<OAuthDto.LoginResponse> kakaoCallback(@RequestParam String code) {
        return ResponseEntity.ok(kakaoOAuthService.getAccessToken(code));
    }

    @PostMapping("kakao/user")
    @Operation(
            summary = "카카오 유저 정보",
            description = "카카오 유저 정보를 조회하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카카오 유저 정보 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<OAuthDto.UserResponse> kakaoUser(@RequestParam String accessToken) {
        return ResponseEntity.ok(kakaoOAuthService.getUserInfo(accessToken));
    }

    @GetMapping("callback/google")
    @Operation(
            summary = "구글 로그인 콜백",
            description = "구글 로그인 콜백 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "구글 로그인 콜백 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<OAuthDto.LoginResponse> googleCallback(@RequestParam String code) {
        return ResponseEntity.ok(googleOAuthService.getAccessToken(code));
    }

    @PostMapping("google/user")
    @Operation(
            summary = "구글 유저 정보",
            description = "구글 유저 정보를 조회하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "구글 유저 정보 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "URL문제 or 관리자 문의"),
                    @ApiResponse(responseCode = "500", description = "토큰 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<OAuthDto.UserResponse> googleUser(@RequestParam String accessToken) {
        return ResponseEntity.ok(googleOAuthService.getUserInfo(accessToken));
    }
}
