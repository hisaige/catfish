package com.catfish.gateway.filter;

import com.catfish.gateway.util.MonoUtils;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * 自定义拦截器
 * 针对配置的url添加的拦截器,可以根据需要作用于单个下层服务中，避免全局生效导致每个url都要去比对一遍
 * 这样配置才生效,如果调换两个服务声明的配置，后面的配置将不生效
 *  filters:
 *     - StripPrefix=0
 *      # 测试拦截器1
 *     - name: TestGet2
 *         args:
 *           urlPatterns:
 *             - /test/**
 *             - /test2/**
 *
 * @author chenyj
 * 2021/7/8 - 17:46.
 **/
@Component
@Slf4j
public class TestGet2GatewayFilterFactory extends AbstractGatewayFilterFactory<GatewayFilterCommonConfig> {

    public TestGet2GatewayFilterFactory() {
        super(GatewayFilterCommonConfig.class);
    }

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public GatewayFilter apply(GatewayFilterCommonConfig config) {

        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                List<String> urlPatterns = config.getUrlPatterns();
                if(CollectionUtils.isEmpty(urlPatterns)) {
                    return chain.filter(exchange);
                }
                String path = exchange.getRequest().getURI().getPath();
                for (String urlPattern : urlPatterns) {
                    if(pathMatcher.match(urlPattern, path)) {
                        return MonoUtils.writeResponse(exchange, ReturnCodeEnum.PERMISSION_DENY);
                    }
                }
                return chain.filter(exchange);
            }

            @Override
            public String toString() {
                return filterToStringCreator(TestGet2GatewayFilterFactory.this)
                        .append("urlPatterns", config.getUrlPatterns()).toString();
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("urlPatterns");
    }
}
