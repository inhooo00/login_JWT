package com.example.login_jwt.oauth;

import com.example.login_jwt.oauth.dto.OAuthDto;

public interface OauthService {

    OAuthDto.LoginResponse getAccessToken(String code);
    OAuthDto.UserResponse getUserInfo(String accessToken);
}
