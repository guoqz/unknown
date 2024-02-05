package com.unknown.common.exception;

import com.unknown.common.config.ExceptionConfig;
import com.unknown.common.exception.http.CustomException;
import com.unknown.common.exception.http.HttpException;
import com.unknown.common.exception.http.ServerErrorException;
import com.unknown.common.respone.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 */

@RestControllerAdvice
public class GlobalException {

    @Autowired
    private ExceptionConfig exceptionConfig;

    private final String lineSeparatorStr = System.getProperty("line.separator");

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public R<String> handleCustomException(HttpServletRequest request, CustomException exception) {
        StringBuilder exStr = new StringBuilder();
        exStr.append("ERROR MESSAGE: ").append(lineSeparatorStr);
        exStr.append("访问地址:").append(request.getRequestURL()).append(lineSeparatorStr);
        exStr.append("请求方法:").append(request.getMethod()).append(lineSeparatorStr);
        exStr.append("自定义错误信息:").append(exceptionConfig.getDesc(exception.getCode())).append(",code:").append(exception.getCode()).append(lineSeparatorStr);
        exStr.append("错误堆栈信息如下:").append(exception.fillInStackTrace().getMessage()).append(lineSeparatorStr);
        for (StackTraceElement stackTraceElement : exception.fillInStackTrace().getStackTrace()) {
            exStr.append("at ").append(stackTraceElement).append(lineSeparatorStr);
        }
        return new R<>(exception.getCode(), exception.getMessage(), request.getMethod() + ":" + request.getRequestURI());
    }

    @ExceptionHandler(HttpException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handleHttpException(HttpServletRequest request, HttpException exception) {
        StringBuilder exStr = new StringBuilder();
        exStr.append("ERROR MESSAGE: ").append(lineSeparatorStr);
        exStr.append("访问地址:").append(request.getRequestURL()).append(lineSeparatorStr);
        exStr.append("请求方法:").append(request.getMethod()).append(lineSeparatorStr);
        exStr.append("自定义错误信息:").append(exceptionConfig.getDesc(exception.getCode())).append(",code:").append(exception.getCode()).append(lineSeparatorStr);
        exStr.append("错误堆栈信息如下:").append(exception.fillInStackTrace().getMessage()).append(lineSeparatorStr);
        for (StackTraceElement stackTraceElement : exception.fillInStackTrace().getStackTrace()) {
            exStr.append("at ").append(stackTraceElement).append(lineSeparatorStr);
        }
        return new R<>(exception.getCode(), exceptionConfig.getDesc(exception.getCode()), request.getMethod() + ":" + request.getRequestURI());
    }

    @ExceptionHandler({ServerErrorException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handleServerErrorException(HttpServletRequest request, ServerErrorException exception) {
        StringBuilder exStr = new StringBuilder();
        exStr.append("ERROR MESSAGE: ").append(lineSeparatorStr);
        exStr.append("访问地址:").append(request.getRequestURL()).append(lineSeparatorStr);
        exStr.append("请求方法:").append(request.getMethod()).append(lineSeparatorStr);
        exStr.append("自定义错误信息:").append(exceptionConfig.getDesc(exception.getCode())).append(",code:").append(exception.getCode()).append(lineSeparatorStr);
        exStr.append("错误堆栈信息如下:").append(exception.fillInStackTrace().getMessage()).append(lineSeparatorStr);
        for (StackTraceElement stackTraceElement : exception.fillInStackTrace().getStackTrace()) {
            exStr.append("at ").append(stackTraceElement).append(lineSeparatorStr);
        }

        return new R<>(exception.getCode(), exceptionConfig.getDesc(exception.getCode()), request.getMethod() + ":" + request.getRequestURI());
    }

}