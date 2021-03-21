package com.catfish.common.file;

import com.hisaige.dbcore.annotation.EnableCoreDatabase;
import com.hisaige.swagger.annotation.EnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 12:34$
 */
@SpringBootApplication
@EnableSwagger
@EnableCoreDatabase(basePackages = {"com.catfish.common.file.mapper"})
public class CatfishFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatfishFileApplication.class, args);
    }
}
