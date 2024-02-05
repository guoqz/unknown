
package com.unknown.common.enums.http;

public enum CustomExceptionType {
    SYSTEM_ERROR(500, "系统出现异常，请您稍后再试或联系管理员！"),
    NOTFOUND_ERROR(501, "请求路径不存在！"),
    PARAMS_ERROR(502, "参数异常！"),
    AUTH_ERROR(503, "鉴权异常！"),
    FORBIDDEN_ERROR(504, "禁止访问！"),
    HTTP_ERROR(505, "HTTP请求异常！"),
    LICENSE_ERROR(506, "系统授权异常！"),
    INTERFACE_FORBIDDEN_ERROR(507, "接口无权访问!");

    private final int code;
    private final String description;

    CustomExceptionType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCode() {
        return this.code;
    }
}
