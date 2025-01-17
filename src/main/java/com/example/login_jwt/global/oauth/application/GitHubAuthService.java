package com.example.login_jwt.global.oauth.application;

import com.example.login_jwt.auth.api.dto.response.UserInfo;
import com.example.login_jwt.auth.application.AuthService;
import com.example.login_jwt.global.oauth.exception.OAuthException;
import com.example.login_jwt.member.domain.SocialType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class GitHubAuthService implements AuthService {

    @Value(value = "${oauth.github.client-id}")
    private String clientId;

    @Value(value = "${oauth.github.client-secret}")
    private String clientSecret;

    @Value(value = "${oauth.github.access-token-url}")
    private String getGithubAccessTokenUrl;

    @Value(value = "${oauth.github.user-info-url}")
    private String getGithubUserInfoUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getProvider() {
        return String.valueOf(SocialType.GITHUB).toLowerCase();
    }

    @Transactional
    @Override
    public UserInfo getUserInfo(String code) {
        String accessToken = extractGithubAccessToken(code);
        return requestGithubUserInfo(accessToken);
    }

    private String extractGithubAccessToken(String code) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = createGithubAccessTokenEntity(code);

        try {
            ResponseEntity<String> responseTokenEntity = restTemplate.exchange(
                    getGithubAccessTokenUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            try {
                String[] tokenParts = Objects.requireNonNull(responseTokenEntity.getBody()).split("&");
                String[] accessTokenParts = tokenParts[0].split("=");
                return accessTokenParts[1];
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                throw new OAuthException("액세스 토큰 추출에 실패했습니다.");
            }

        } catch (RestClientException e) {
            throw new OAuthException();
        }

    }

    private HttpEntity<MultiValueMap<String, String>> createGithubAccessTokenEntity(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();

        MultiValueMap<String, String> reqParams = new LinkedMultiValueMap<>();
        reqParams.add("client_id", clientId);
        reqParams.add("client_secret", clientSecret);
        reqParams.add("code", code);

        return new HttpEntity<>(reqParams, httpHeaders);
    }

    private UserInfo requestGithubUserInfo(String accessToken) {
        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntityWithAccessToken(accessToken);

        try {
            ResponseEntity<UserInfo> responseUserInfoEntity = restTemplate.exchange(
                    getGithubUserInfoUrl,
                    HttpMethod.GET,
                    requestEntity,
                    UserInfo.class);

            return responseUserInfoEntity.getBody();
        } catch (RestClientException e) {
            throw new OAuthException();
        }
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntityWithAccessToken(String accessToken) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", String.format("token %s", accessToken));
        return new HttpEntity<>(requestHeaders);
    }

}
