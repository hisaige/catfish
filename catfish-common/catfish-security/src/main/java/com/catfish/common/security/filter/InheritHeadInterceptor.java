package com.catfish.common.security.filter;

import com.catfish.common.core.entity.CatfishConstants;
import com.catfish.common.security.entity.model.UmsUser;
import com.catfish.common.security.util.JwtTokenManager;
import com.hisaige.web.core.util.RequestUtils;
import com.hisaige.web.core.util.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 微服务调用时用来传递请求头
 * @author chenyj
 * @version 1.0
 * @date 2021/1/1$ - 16:02$
 */
public class InheritHeadInterceptor implements RequestInterceptor {

    private JwtTokenManager jwtTokenManager;

    public InheritHeadInterceptor(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = RequestUtils.getRequest();
        if (null != httpServletRequest) {
            // 传递header信息
            String token = RequestUtils.getHeadOrAttribute(CatfishConstants.USER_TOKEN);
            if(!StringUtils.isEmpty(token)) {
                requestTemplate.header(CatfishConstants.USER_TOKEN, token);
                UmsUser userFromToken = jwtTokenManager.getUserFromToken(token);
                //设置userId
                requestTemplate.header(CatfishConstants.USER_ID, userFromToken.getId());
                //设置userName
                requestTemplate.header(CatfishConstants.USERNAME, userFromToken.getUsername());
                //设置组织ID
                requestTemplate.header(CatfishConstants.USER_ORG, String.valueOf(userFromToken.getOrgId()));
            }
        }
    }
}
