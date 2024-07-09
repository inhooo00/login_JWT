package com.example.login_jwt.auth.application;

import com.example.login_jwt.auth.api.dto.response.UserInfo;

public interface AuthService {
    UserInfo getUserInfo(String authCode);

    String getProvider();
}
