package com.catfish.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.hisaige.web.core.entity.domain.AjaxExceptionRes;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义返回结果：未登录或登录过期
 */
public class IAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        AjaxExceptionRes ajaxExceptionRes = new AjaxExceptionRes(ReturnCodeEnum.NOTLOGIN_EXCEPTION);
        ajaxExceptionRes.setMsg(authException.getMessage());
        response.getWriter().println(JSONUtil.parse(ajaxExceptionRes));
        response.getWriter().flush();
    }
}
