package com.example.login_jwt.oauth;

import com.example.login_jwt.oauth.dto.OAuthDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleOauthService implements OauthService {

    @Value("${oauth.google.client-id}")
    private String clientId;
    @Value("${oauth.google.client-secret}")
    private String clientSecret;
    @Value("${oauth.google.redirect-url}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    @Override
    public OAuthDto.LoginResponse getAccessToken(String code) {
        String requestUrl = "https://oauth2.googleapis.com/token";

        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntityToGetAccessToken(code);
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        return parseAccessToken(response);
    }

    @Override
    public OAuthDto.UserResponse getUserInfo(String accessToken) {
        String requestUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;

        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntityToGetUserInfo();
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        return parseUserInfo(response);
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntityToGetAccessToken(String code) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("code", code);
        requestParams.add("scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
        requestParams.add("client_id", clientId);
        requestParams.add("client_secret", clientSecret);
        requestParams.add("redirect_uri", redirectUri);
        requestParams.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(requestParams, headers);
    }

    private OAuthDto.LoginResponse parseAccessToken(ResponseEntity<String> response) {
        JsonElement element = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();

        String accessToken = element.getAsJsonObject()
                .get("access_token")
                .getAsString();

        return OAuthDto.LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntityToGetUserInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(headers);
    }

    private OAuthDto.UserResponse parseUserInfo(ResponseEntity<String> response) {
        JsonElement element = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();

        String socialId = element.getAsJsonObject()
                .get("id")
                .getAsString();

        String email = element.getAsJsonObject()
                .get("email")
                .getAsString();

        String nickName = element.getAsJsonObject()
                .get("name")
                .getAsString();

        return OAuthDto.UserResponse.builder()
                .socialId(socialId)
                .socialType("GOOGLE")
                .email(email)
                .nickName(nickName)
                .build();
    }
}
