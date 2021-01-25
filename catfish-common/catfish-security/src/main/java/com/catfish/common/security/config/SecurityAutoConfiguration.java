package com.catfish.common.security.config;

import com.catfish.common.security.config.properties.SecurityProperties;
import com.catfish.common.security.filter.InheritHeadInterceptor;
import com.catfish.common.security.util.JwtTokenManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/24$ - 2:26$
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration implements BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * token工具
     * @return JwtTokenUtil
     */
    @Bean
    public JwtTokenManager jwtTokenManager(){

        SecurityProperties securityProperties = beanFactory.getBean(SecurityProperties.class);
        return new JwtTokenManager(securityProperties);
    }

    /**
     * 可以自定义一个名为inheritHeadInterceptor的bean代替
     * @return InheritHeadInterceptor
     */
    @Bean
    @ConditionalOnMissingBean(name = "inheritHeadInterceptor")
    public InheritHeadInterceptor inheritHeadInterceptor(){
        return new InheritHeadInterceptor(jwtTokenManager());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
