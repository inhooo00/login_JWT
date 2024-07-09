package com.example.login_jwt.member.exception;


import com.example.login_jwt.global.error.exception.InvalidGroupException;

public class ExistsNickNameException extends InvalidGroupException {
    public ExistsNickNameException(String message) {
        super(message);
    }

    public ExistsNickNameException() {
        this("이미 존재하는 닉네임입니다.");
    }
}
