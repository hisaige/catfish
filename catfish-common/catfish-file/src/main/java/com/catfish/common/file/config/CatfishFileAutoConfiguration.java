package com.catfish.common.file.config;

import com.catfish.common.file.config.properties.CatfishFileProperties;
import com.catfish.common.file.service.FileService;
import com.catfish.common.file.service.impl.AliyunFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 15:26$
 */
@Configuration
@EnableConfigurationProperties({CatfishFileProperties.class})
public class CatfishFileAutoConfiguration {

    private static final Map<String, String> serviceMap = new ConcurrentHashMap<>();

    @Autowired
    private CatfishFileProperties catfishFileProperties;

    static {
        serviceMap.put("aliyun", "com.catfish.common.file.service.impl.AliyunFileServiceImpl");
    }

    @Bean
    public FileService catfishFileService() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //根据配置文件选择文件服务
        String fileServiceName = serviceMap.get(catfishFileProperties.getOssType());
        if(null != fileServiceName) {
            return (FileService) Class.forName(fileServiceName).newInstance();
        }
        return new AliyunFileServiceImpl();
    }

}
