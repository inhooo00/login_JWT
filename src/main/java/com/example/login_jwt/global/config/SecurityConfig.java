package com.example.login_jwt.global.config;

import com.example.login_jwt.global.error.exception.CustomAuthenticationFailureHandler;
import com.example.login_jwt.global.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        permitAllForOAuthEndpoints(http);
        configureAdminAuthority(http);
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handle -> handle.authenticationEntryPoint(new CustomAuthenticationFailureHandler()))
                .build();
    }

    private void permitAllForOAuthEndpoints(HttpSecurity http) throws Exception {
        String[] permittedUrls = {
                "/login/oauth2/code/google",
                "/api/google/token",
                "/api/github/token",
                "/api/apple/token",
                "/api/token/access",
        };

        for (String url : permittedUrls) {
            http.authorizeHttpRequests(authorize -> authorize.requestMatchers(antMatcher(url)).permitAll());
        }
    }

    private void configureAdminAuthority(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/admin/**").hasRole("ADMIN"));
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers("/docs/**", "/api-docs/**", "/swagger-ui/**", "/favicon.ico");
    }
}