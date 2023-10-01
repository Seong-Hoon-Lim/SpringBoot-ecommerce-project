package com.kittopmall.config.exception;

public class PasswordNotChangedException extends RuntimeException {
    public PasswordNotChangedException() {
        super("제공된 비밀번호가 현재 비밀번호와 같습니다. 다른 비밀번호를 입력해주세요.");
    }
}
