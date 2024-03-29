package com.catfish.ums;

import com.hisaige.dbcore.annotation.EnableCoreDatabase;
import com.hisaige.i18n.annotation.EnableI18n;
import com.hisaige.redis.annotation.EnableRedisCache;
import com.hisaige.swagger.annotation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author chenyj
 * 2020/12/16 - 22:03.
 **/
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCoreDatabase(basePackages = "com.catfish.ums.mapper")
@EnableI18n
@EnableSwagger
@EnableRedisCache
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
