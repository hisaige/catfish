package com.catfish.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyj
 * 2020/12/15 - 23:27.
 **/
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "catfish.gateway.settings")
public class GatewayConfigProperties {

    private List<String> whiteUrls = new ArrayList<>();

    public List<String> getWhiteUrls() {
        return whiteUrls;
    }

    public void setWhiteUrls(List<String> whiteUrls) {
        this.whiteUrls = whiteUrls;
    }
}
