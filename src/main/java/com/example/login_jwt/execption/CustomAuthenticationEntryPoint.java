package com.example.login_jwt.execption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skhuthon.skhuthon_0th_team9.global.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authenticationException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.UNAUTHORIZED_USER_EXCEPTION);
            String json = mapper.writeValueAsString(errorResponse);

            OutputStream outputStream = response.getOutputStream();
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("AuthenticationEntryPoint error", e);
        }
    }
}
