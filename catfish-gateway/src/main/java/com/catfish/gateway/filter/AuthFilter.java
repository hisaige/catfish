package com.catfish.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.catfish.common.core.entity.CatfishConstants;
import com.catfish.common.security.access.UmsUserDetails;
import com.catfish.common.security.util.JwtTokenManager;
import com.catfish.common.security.config.properties.SecurityProperties;
import com.catfish.common.security.entity.model.UmsUser;
import com.hisaige.redis.service.RedisService;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * 网关鉴权
 * @author chenyj
 * @version 1.0
 * @date 2020/12/25$ - 0:14$
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private RedisService<UmsUserDetails> redisService;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();

        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : securityProperties.getIgnoreUrls()) {
            if (pathMatcher.match(path, url)) {
                return chain.filter(exchange);
            }
        }
        String token = getToken(request);

        if(StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, ReturnCodeEnum.TOKEN_EMPTY);
        }

        UmsUserDetails umsUserDetails = redisService.get(CatfishConstants.TOKEN_HEAD + token);
        UmsUser umsUser;
        if(null == umsUserDetails || null == (umsUser=umsUserDetails.getRetUser())) {
            //redis中不存在则鉴权失败
            return unauthorizedResponse(exchange, ReturnCodeEnum.AUTHENTICATION_FAILED);
        } else {
            //刷新缓存
            umsUserDetails.setLastAccessTime(new Date());

            //更新缓存
            redisService.set(CatfishConstants.TOKEN_HEAD + token, umsUserDetails, 60 * 10 * 3);


            //将用户id放到request
//            request.getHeaders().put(CatfishConstants.USER_ID, Collections.singletonList(umsUser.getId()));
            //将用户名放到request
//            request.getHeaders().put(CatfishConstants.USERNAME, Collections.singletonList(umsUser.getUsername()));
            String ipAddr = "";
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            if(null != remoteAddress) {
                ipAddr = remoteAddress.getHostName();
            }
            //设置用户信息到请求头
            ServerHttpRequest retRequest = exchange.getRequest()
                    .mutate()
                    .header(CatfishConstants.USER_ID, umsUser.getId())
                    .header(CatfishConstants.USERNAME, umsUser.getUsername())
                    .header(CatfishConstants.USER_IP, ipAddr)
                    .header(CatfishConstants.USER_ORG, String.valueOf(umsUser.getOrgId()))
                    //生成tokenB，非网关的微服务只需要解析tokenB，能解析出来则鉴权通过
                    .header(CatfishConstants.USER_TOKEN, jwtTokenManager.generateToken(umsUser.getUsername(), umsUser.getId(), umsUser.getOrgId()))
                    .build();
            exchange = exchange.mutate().request(retRequest).build();
        }
        return chain.filter(exchange);
    }

    private String getToken(ServerHttpRequest request) {

        String authToken = request.getHeaders().getFirst(securityProperties.getTokenHeader());
        if (authToken != null && authToken.startsWith(securityProperties.getTokenHead())) {
            //取前缀的后面部分，+1 是为了去掉一个多余的空格
            authToken = authToken.substring(securityProperties.getTokenHead().length() + 1);
        }
        return authToken;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ReturnCodeEnum returnCodeEnum)
    {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(returnCodeEnum)));
        }));
    }

}
