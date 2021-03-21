package com.catfish.common.security.handler;

import com.alibaba.fastjson.JSON;
import com.hisaige.i18n.locale.LocaleMessage;
import com.hisaige.web.core.entity.domain.AjaxExceptionRes;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private LocaleMessage localeMessage;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSONString(new AjaxExceptionRes(ReturnCodeEnum.PERMISSION_DENY)));
        response.getWriter().flush();
    }
}
