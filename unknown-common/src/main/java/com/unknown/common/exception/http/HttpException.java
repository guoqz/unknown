package com.unknown.common.exception.http;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer statusCode = 500;
}
