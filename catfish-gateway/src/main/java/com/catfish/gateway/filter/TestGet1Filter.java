package com.catfish.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author chenyj
 * 2021/7/8 - 17:46.
 **/
@Component
@Slf4j
public class TestGet1Filter extends AbstractGatewayFilterFactory<GatewayFilterCommonConfig> {

    public TestGet1Filter() {
        super(GatewayFilterCommonConfig.class);
    }

    @Override
    public String name() {
        return "testGet1Filter";
    }

    @Override
    public GatewayFilter apply(GatewayFilterCommonConfig config) {
        TestGet1Filter.TestGet1GatewayFilter testGet1GatewayFilter = new TestGet1GatewayFilter();
        Integer order = config.getOrder();
        if (order == null) {
            return testGet1GatewayFilter;
        }
        return new OrderedGatewayFilter(testGet1GatewayFilter, order);
    }

    public static class TestGet1GatewayFilter implements GatewayFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {



            return chain.filter(exchange);
        }
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("order");
    }
}
