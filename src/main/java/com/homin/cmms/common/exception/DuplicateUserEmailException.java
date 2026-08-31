package com.homin.cmms.common.exception;

public class DuplicateUserEmailException extends RuntimeException {

    public DuplicateUserEmailException(String email) {
        super("이미 사용 중인 이메일입니다: " + email);
    }
}
