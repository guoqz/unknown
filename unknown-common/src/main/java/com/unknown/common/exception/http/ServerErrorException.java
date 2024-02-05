package com.unknown.common.exception.http;

public class ServerErrorException extends HttpException {

    public ServerErrorException(Integer code) {
        this.code = code;
        this.statusCode = 500;
    }
}
