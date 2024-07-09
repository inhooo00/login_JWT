package com.example.login_jwt.member.exception;

import com.example.login_jwt.global.error.exception.InvalidGroupException;
public class InvalidNickNameAddressException extends InvalidGroupException {
    public InvalidNickNameAddressException(String message) {
        super(message);
    }

    public InvalidNickNameAddressException() {
        this("닉네임 형식이 올바르지 않습니다.");
    }
}
