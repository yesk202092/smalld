
package com.smalld.core.filter;

import com.smalld.common.pojo.UserToken;
import com.smalld.common.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author yesk
 * @description 转发认证全局过滤器，为请求添加 TOKEN
 * @date 2022/11/18 1:51
 */
@Component
public class ForwardAuthFilter implements GlobalFilter {

    private static final String HEADER_ORIGIN = "Origin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();

        /** token **/
        List<String> tokenValueList = httpHeaders.get("token");
        String tokenValue = null;
        if (Objects.nonNull(tokenValueList) && !tokenValueList.isEmpty()) {
            tokenValue = tokenValueList.get(0);
        }

        /** Origin **/
        String originValue = null;
        List<String> originValueList = httpHeaders.get(HEADER_ORIGIN);
        if (Objects.nonNull(originValueList) && !originValueList.isEmpty()) {
            originValue = originValueList.get(0);
        }

        ServerHttpRequest newRequest = exchange
                .getRequest()
                .mutate()
                // 为请求追加 Origin 参数
                .header(HEADER_ORIGIN, originValue)
                // 为请求追加 Token 参数
                .header("token", tokenValue)
                .build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }

}
