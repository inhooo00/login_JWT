package com.example.login_jwt.auth.api;

import com.example.login_jwt.auth.api.dto.request.RefreshTokenReqDto;
import com.example.login_jwt.auth.api.dto.request.TokenReqDto;
import com.example.login_jwt.auth.api.dto.response.MemberLoginResDto;
import com.example.login_jwt.auth.api.dto.response.UserInfo;
import com.example.login_jwt.auth.application.AuthMemberService;
import com.example.login_jwt.auth.application.AuthService;
import com.example.login_jwt.auth.application.AuthServiceFactory;
import com.example.login_jwt.auth.application.TokenService;
import com.example.login_jwt.global.jwt.api.dto.TokenDto;
import com.example.login_jwt.global.template.RspTemplate;
import com.example.login_jwt.member.domain.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;
    private final TokenService tokenService;

//    @Operation(summary = "로그인 후 토큰 발급", description = "액세스, 리프레쉬 토큰을 발급합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
//    })
    @PostMapping("/{provider}/token")
    public RspTemplate<TokenDto> generateAccessAndRefreshToken(
//            @Parameter(name = "provider", description = "소셜 타입(google, github, apple)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto) {

        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(tokenReqDto.authCode());

        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo, SocialType.valueOf(provider.toUpperCase()));
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "토큰 발급", getToken);
    }

//    @Operation(summary = "액세스 토큰 재발급", description = "리프레쉬 토큰으로 액세스 토큰을 발급합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
//    })
    @PostMapping("/token/access")
    public RspTemplate<TokenDto> generateAccessToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto) {
        TokenDto getToken = tokenService.generateAccessToken(refreshTokenReqDto);

        return new RspTemplate<>(HttpStatus.OK, "액세스 토큰 발급", getToken);
    }

}
