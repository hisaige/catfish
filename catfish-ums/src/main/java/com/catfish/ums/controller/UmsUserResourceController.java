package com.catfish.ums.controller;

import com.catfish.ums.entity.domain.UmsUserResource;
import com.catfish.ums.service.UmsUserResourceService;
import com.hisaige.dbcore.controller.BaseController;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author chenyj
 * 2020/5/24 - 10:24.
 **/
@Controller
@RequestMapping("/userResource")
@Api(tags = "UmsUserResourceController", description = "用户资源管理")
public class UmsUserResourceController extends BaseController<UmsUserResourceService, UmsUserResource> {

    @PostMapping("/update/{id}")
    @ResponseBody
    @ApiOperation("更新用户信息")
    public AjaxMessageRes<Integer> update(@PathVariable String id, @Valid @RequestBody UmsUserResource umsUserResource) throws Exception {
        umsUserResource.setId(id);
        return new AjaxMessageRes<>(service.updateByPrimaryKeySelective(umsUserResource));
    }
}
