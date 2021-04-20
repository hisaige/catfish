package com.catfish.ums.controller;

import com.catfish.common.security.entity.model.UmsUser;
import com.catfish.ums.service.UmsPermissionService;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.controller.BaseController;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import com.hisaige.web.core.valid.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * 权限操作
 *
 * @author chenyj
 * 2020/5/23 - 18:53.
 **/
@Controller
@RequestMapping("/permission")
@Api(tags = "UmsPermissionController", description = "菜单管理")
public class UmsPermissionController extends BaseController<UmsPermissionService, UmsPermission> {


//    @Override
//    @PostMapping("/create")
//    @ResponseBody
//    @ApiOperation("创建用户")
//    public AjaxMessageRes<Integer> create(@RequestBody @Validated({AddGroup.class}) UmsUser umsUser) {
//        return new AjaxMessageRes<>(service.save(umsUser));
//    }

    @GetMapping("/getRoot")
    @ResponseBody
    @ApiOperation("获取根目录权限列表")
    public AjaxMessageRes<List<UmsPermission>> getRoot() {

        return new AjaxMessageRes<>(service.getRoot());
    }

    @GetMapping("/children/{parentId}")
    @ResponseBody
    @ApiOperation("根据父节点id获取权限列表")
    public AjaxMessageRes<List<UmsPermission>> getByParentId(@PathVariable String parentId) {

        return new AjaxMessageRes<>(service.getByParentId(parentId));
    }

    @GetMapping("/filter")
    @ResponseBody
    @ApiOperation("过滤权限树")
    public AjaxMessageRes<List<UmsPermission>> filter(String name, Boolean status) {

        return new AjaxMessageRes<>(service.filter(name, status));
    }

}
