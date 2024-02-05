package com.unknown.document.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Created Guoqz
 * @Date 2023-09-16 10:41
 * @Description TODO
 */

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "unknown.document.minio")
//@PropertySource("classpath:config/minio.properties")
public class MinioConfig {

    // endPoint是一个URL，域名，IPv4或者IPv6地址
    @Value("${unknown.document.minio.endpoint}")
    private String endpoint;

    // TCP/IP端口号
    @Value("${unknown.document.minio.port}")
    private int port;

    // accessKey类似于用户ID，用于唯一标识你的账户
    @Value("${unknown.document.minio.accessKey}")
    private String accessKey;

    // secretKey是你账户的密码
    @Value("${unknown.document.minio.secretKey}")
    private String secretKey;

    // 如果是true，则用的是https而不是http,默认值是false
    @Value("${unknown.document.minio.secure}")
    private Boolean secure;

    // 默认存储桶
    @Value("${unknown.document.minio.defaultBucketName}")
    private String defaultBucketName;


    @Bean
    public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
        //System.out.println("endpoint = " + endpoint);
        //System.out.println("port = " + port);
        //System.out.println("accessKey = " + accessKey);
        //System.out.println("secretKey = " + secretKey);
        //System.out.println("secure = " + secure);
        //System.out.println("defaultBucketName = " + defaultBucketName);
        return new MinioClient(endpoint, port, accessKey, secretKey, secure);
    }
}
