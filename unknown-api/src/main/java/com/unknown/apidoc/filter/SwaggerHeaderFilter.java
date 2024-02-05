package com.unknown.apidoc.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Created Guoqz
 * @Date 2023-10-28 19:10
 * @Description TODO
 */
@Component
public class SwaggerHeaderFilter<C> extends AbstractGatewayFilterFactory<C> {

    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    private static final String API_URI = "v2/api-docs";

    @Override
    public GatewayFilter apply(C config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            if (!StringUtils.endsWithIgnoreCase(path, API_URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(API_URI));
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }

}
