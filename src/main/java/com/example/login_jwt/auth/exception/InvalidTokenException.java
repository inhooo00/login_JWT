package com.example.login_jwt.auth.exception;


import com.example.login_jwt.global.error.exception.AuthGroupException;

public class InvalidTokenException extends AuthGroupException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
        this("토큰이 유효하지 않습니다.");
    }
}
