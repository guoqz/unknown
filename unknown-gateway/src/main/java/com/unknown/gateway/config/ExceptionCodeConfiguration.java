package com.unknown.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "gateway")
@PropertySource(value = "classpath:config/exception-code.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
public class ExceptionCodeConfiguration {

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
