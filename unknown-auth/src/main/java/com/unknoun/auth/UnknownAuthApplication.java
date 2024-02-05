package com.unknoun.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Created Guoqz
 * @Date 2023-11-05 17:25
 * @Description TODO
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class UnknownAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnknownAuthApplication.class, args);
    }

}
