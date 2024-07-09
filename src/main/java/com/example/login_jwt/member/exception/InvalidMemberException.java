package com.example.login_jwt.member.exception;

import com.example.login_jwt.global.error.exception.InvalidGroupException;
public class InvalidMemberException extends InvalidGroupException {
    public InvalidMemberException(final String message) {
        super(message);
    }

    public InvalidMemberException() {
        this("잘못된 회원의 정보입니다.");
    }
}
