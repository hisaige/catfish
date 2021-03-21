package com.catfish.common.file.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/17$ - 15:32$
 */
@ConfigurationProperties(prefix = "catfish.file.settings")
public class CatfishFileProperties {

    private String ossType = "aliyun";

    public String getOssType() {
        return ossType;
    }

    public void setOssType(String ossType) {
        this.ossType = ossType;
    }
}
