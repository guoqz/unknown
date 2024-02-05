package com.unknown.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author:jerry
 * @create: 2020-07-07 17:06
 * @description:
 **/
@PropertySource(value = "classpath:config/exception-code.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "unknown")
@Component
public class ExceptionConfig {

    private Map<Integer, String> point;

    public Map<Integer, String> getPoint() {
        return point;
    }

    public void setPoint(Map<Integer, String> point) {
        this.point = point;
    }

    public String getDesc(Integer code) {
        return point.get(code);
    }

}
