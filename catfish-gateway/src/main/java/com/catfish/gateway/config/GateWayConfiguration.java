package com.catfish.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.catfish.common.security.util.JwtTokenManager;
import com.catfish.gateway.config.properties.GatewayConfigProperties;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author chenyj
 * 2020/12/15 - 23:38.
 **/
@Configuration
public class GateWayConfiguration {

//    @Autowired
    private GatewayConfigProperties GatewayConfigProperties;
    private JwtTokenManager jwtTokenUtils;

    public GateWayConfiguration(GatewayConfigProperties gatewayConfigProperties, JwtTokenManager jwtTokenUtils) {
        this.GatewayConfigProperties = gatewayConfigProperties;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter()
    {
        return new SentinelGatewayFilter();
    }

//    @Bean
//    public JwtTokenFilter jwtTokenFilter() {
//        return new JwtTokenFilter(jwtTokenUtils);
//    }
}
