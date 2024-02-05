package com.unknown.system.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Created Guoqz
 * @Date 2023-10-28 19:27
 * @Description TODO
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    @Order(1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //注意，微服务架构下groupName一定要去掉，不然访问资源会出现404，导致Knife4j文档请求异常
                .select()
                //这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.unknown.system.controller"))
                // 扫描指定注解
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 扫描所有接口
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .termsOfServiceUrl("")
                //contact是一个重载方法，其中一个为contact(String contact),但是不建议使用，建议使用contact(Contact contact)
                .contact(contact())
                .version("1.0")
                .build();
    }

    private Contact contact() {
        return new Contact("name", "url", "email");
    }

}
