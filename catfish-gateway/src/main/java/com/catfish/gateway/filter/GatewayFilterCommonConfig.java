package com.catfish.gateway.filter;

import java.util.List;
import java.util.Objects;

/**
 * 拦截器通用配置,如果添加了新的配置项一定要重写equals和hashCode
 * @author chenyj
 * 2021/7/8 - 17:37.
 **/
public class GatewayFilterCommonConfig {

    /**
     * 执行顺序
     */
    private Integer order;

    /**
     * 匹配url
     */
    private List<String> urlPatterns;



    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GatewayFilterCommonConfig that = (GatewayFilterCommonConfig) o;
        return Objects.equals(order, that.order) && Objects.equals(urlPatterns, that.urlPatterns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, urlPatterns);
    }
}
