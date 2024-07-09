package com.example.login_jwt.member.exception;


import com.example.login_jwt.global.error.exception.InvalidGroupException;

public class ExistsLikeMemberException extends InvalidGroupException {
    public ExistsLikeMemberException(String message) {
        super(message);
    }

    public ExistsLikeMemberException() {
        this("이미 관심 목록에 추가 되었습니다.");
    }
}
