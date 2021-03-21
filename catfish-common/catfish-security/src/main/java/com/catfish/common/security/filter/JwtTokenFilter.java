package com.catfish.common.security.filter;

import com.catfish.common.security.config.properties.SecurityProperties;
import com.catfish.common.security.util.JwtTokenManager;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.util.CollectionUtils;
import com.hisaige.web.core.util.RequestUtils;
import com.hisaige.web.core.util.ResponseUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/30$ - 0:28$
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenManager jwtTokenUtils;

    private SecurityProperties securityProperties;

    public JwtTokenFilter(JwtTokenManager jwtTokenUtils, SecurityProperties securityProperties) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if(!CollectionUtils.isEmpty(securityProperties.getIgnoreUrls())){
            String requestUri = httpServletRequest.getRequestURI();
            PathMatcher pathMatcher = new AntPathMatcher();
            //白名单请求直接放行
            for (String path : securityProperties.getIgnoreUrls()) {
                if (pathMatcher.match(path, requestUri)) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }

//        UmsUser umsUser = jwtTokenUtils.getUserFromToken(RequestUtils.getHeadOrAttribute("token"));
//        if(null == umsUser){
//            //用户信息在redis中不存在说明用户
//            ResponseUtils.writeObj2Json(R.fail(new BadCredentialsException("token状态已失效")));
//        }
        if(jwtTokenUtils.isTokenExpired(RequestUtils.getHeadOrAttribute("token"))){
            //用户信息在redis中不存在说明用户
            ResponseUtils.writeObj2Json(R.fail(new BadCredentialsException("登录状态已失效")));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
