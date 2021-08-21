package com.catfish.gateway.util;

import com.alibaba.fastjson.JSON;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author chenyj
 * 2021/7/8 - 19:05.
 **/
public class MonoUtils {

    public static Mono<Void> writeResponse(ServerWebExchange exchange, ReturnCodeEnum returnCodeEnum) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(returnCodeEnum)));
        }));
    }
}
