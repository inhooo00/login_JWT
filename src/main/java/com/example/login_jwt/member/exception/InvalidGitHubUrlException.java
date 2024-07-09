package com.example.login_jwt.member.exception;


import com.example.login_jwt.global.error.exception.InvalidGroupException;
public class InvalidGitHubUrlException extends InvalidGroupException {
    public InvalidGitHubUrlException(final String message) {
        super(message);
    }

    public InvalidGitHubUrlException() {
        this("잘못된 깃 허브 주소입니다.");
    }
}
