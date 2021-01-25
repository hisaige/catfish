package com.catfish.ums.controller;

import com.catfish.ums.entity.domain.UmsPermissionCategory;
import com.catfish.ums.service.UmsPermissionCategoryService;
import com.hisaige.dbcore.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 权限这种类操作
 * @author chenyj
 * 2020/5/23 - 18:53.
 **/
@Controller
@RequestMapping("/permission/category")
@Api(tags = "UmsPermissionCategoryController", description = "权限种类管理")
public class UmsPermissionCategoryController extends BaseController<UmsPermissionCategoryService, UmsPermissionCategory> {


//    @Override
//    @PostMapping("/create")
//    @ResponseBody
//    @ApiOperation("创建用户")
//    public AjaxMessageRes<Integer> create(@RequestBody @Validated({AddGroup.class}) UmsUser umsUser) {
//        return new AjaxMessageRes<>(service.save(umsUser));
//    }

}
