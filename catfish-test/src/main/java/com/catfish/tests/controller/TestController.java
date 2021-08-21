package com.catfish.tests.controller;

import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyj
 * 2021/7/8 - 10:58.
 **/
@RestController
@RefreshScope
public class TestController {

    @Value("${test.config.no1:testGet}")
    private String testGet1;

    @GetMapping("/test/testGet1")
    public AjaxMessageRes<String> testGet1() {

        return new AjaxMessageRes<>(testGet1);
    }

    @GetMapping("/test1/testGet1")
    public AjaxMessageRes<String> testGet2() {

        return new AjaxMessageRes<>(testGet1);
    }
}
