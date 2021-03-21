package com.catfish.ums.controller;

import com.catfish.ums.service.UmsPermissionService;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 权限操作
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

}
