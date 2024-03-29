package com.catfish.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class CacheRequestFilter extends AbstractGatewayFilterFactory<GatewayFilterCommonConfig> {
    public CacheRequestFilter() {
        super(GatewayFilterCommonConfig.class);
    }

    @Override
    public String name() {
        return "CacheRequestFilter";
    }

    @Override
    public GatewayFilter apply(GatewayFilterCommonConfig orderConfig) {
        CacheRequestGatewayFilter cacheRequestGatewayFilter = new CacheRequestGatewayFilter();
        Integer order = orderConfig.getOrder();
        if (order == null) {
            return cacheRequestGatewayFilter;
        }
        return new OrderedGatewayFilter(cacheRequestGatewayFilter, order);
    }

    public static class CacheRequestGatewayFilter implements GatewayFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

            HttpMethod method = exchange.getRequest().getMethod();
            if (method == null || method.matches("GET") || method.matches("DELETE")) {
                // 不过滤 GET 和 DELETE 请求
                return chain.filter(exchange);
            }
            return DataBufferUtils.join(exchange.getRequest().getBody()).map(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                DataBufferUtils.release(dataBuffer);
                return bytes;
            }).defaultIfEmpty(new byte[0]).flatMap(bytes -> {
                DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
                ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        if (bytes.length > 0) {
                            return Flux.just(dataBufferFactory.wrap(bytes));
                        }
                        return Flux.empty();
                    }
                };
                return chain.filter(exchange.mutate().request(decorator).build());
            });
        }
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("order");
    }

}
