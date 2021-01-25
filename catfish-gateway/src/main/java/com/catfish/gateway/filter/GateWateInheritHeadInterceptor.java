package com.catfish.gateway.filter;

import com.catfish.common.core.entity.CatfishConstants;
import com.catfish.common.security.util.JwtTokenManager;
import com.hisaige.web.core.util.RequestUtils;
import com.hisaige.web.core.util.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * 网关模块 微服务调用时 tokenA替换为tokenB
 * @author chenyj
 * @version 1.0
 * @date 2021/1/1$ - 17:18$
 * @deprecated 暂时不用，仅用客户端传过来的token即可
 */
//@Component
public class GateWateInheritHeadInterceptor implements RequestInterceptor {

    private JwtTokenManager jwtTokenManager;

    public GateWateInheritHeadInterceptor(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {

        // 自定义传递的header信息
        String userId = RequestUtils.getHeadOrAttribute(CatfishConstants.USER_ID);
        String userName = RequestUtils.getHeadOrAttribute(CatfishConstants.USER_NAME);

        if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(userId)) {
            //生成并设置token
            String token = jwtTokenManager.generateToken(userName, userId);
            requestTemplate.header(CatfishConstants.USER_TOKEN, token);

            //设置userId
            requestTemplate.header(CatfishConstants.USER_ID, userId);
            //设置userName
            requestTemplate.header(CatfishConstants.USER_NAME, userName);
        }
    }
}
