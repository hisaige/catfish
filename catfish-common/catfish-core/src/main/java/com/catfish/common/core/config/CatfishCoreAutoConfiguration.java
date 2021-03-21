package com.catfish.common.core.config;

import com.catfish.common.core.advice.CatfishControllerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/29$ - 21:57$
 */
@Configuration
public class CatfishCoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CatfishControllerAdvice catfishControllerAdvice(){
        return new CatfishControllerAdvice();
    }
}
