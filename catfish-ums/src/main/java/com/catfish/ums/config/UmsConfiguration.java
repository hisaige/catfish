package com.catfish.ums.config;

import com.catfish.common.security.config.properties.SecurityProperties;
import com.catfish.common.security.filter.JwtTokenFilter;
import com.catfish.common.security.util.JwtTokenManager;
import com.hisaige.web.core.configuration.mvc.EmptyStringToNullResolveProcessor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/24$ - 2:23$
 */
@Configuration
public class UmsConfiguration {

    private SecurityProperties securityProperties;
    private JwtTokenManager jwtTokenUtils;

    public UmsConfiguration(ObjectProvider<JwtTokenManager> jwtTokenUtils, ObjectProvider<SecurityProperties> securityProperties) {
        this.jwtTokenUtils = jwtTokenUtils.getIfAvailable();
        this.securityProperties = securityProperties.getIfAvailable();
    }

    /**
     * 密码加密工具
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(jwtTokenUtils, securityProperties);
    }

    /**
     * SpringMVC参数处理器 将空字符串转化为null
     * @return EmptyStringToNullResolveRegister
     */
    @Bean
    public EmptyStringToNullResolveProcessor emptyStringToNullResolveProcessor() {
        return new EmptyStringToNullResolveProcessor(true);
    }
}
