package com.catfish.common.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyj
 * 2020/5/29 - 0:13.
 **/
@ConfigurationProperties(prefix = "catfish.security.settings")
@Data
public class SecurityProperties {

    //jwt密钥信息
    private String secret = "hisaige123";

    //token前缀，如htoken，则token信息为 `htoken xxxxx` 中间含有一个空格
    private String tokenHeader = "ums";

    //token头部，如auth，请求时请求头必须包含属性auth放token信息
    private String tokenHead;

    //过期时间，单位秒
    private Long expiration = 60 * 60 * 24L;

    //刷新间隔，单位秒
    private Integer refreshInterval = 60 * 30;

    private List<String> ignoreUrls = new ArrayList<>();

}
