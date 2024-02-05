package com.unknown.common.exception.http;

import com.unknown.common.enums.http.CustomExceptionType;

public class CustomException extends RuntimeException {
    private int code;
    private String message;

    private CustomException() {
    }

    public CustomException(CustomExceptionType exceptionTypeEnum) {
        this.code = exceptionTypeEnum.getCode();
        this.message = exceptionTypeEnum.getDescription();
    }

    public CustomException(CustomExceptionType exceptionTypeEnum, String message) {
        this.code = exceptionTypeEnum.getCode();
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
