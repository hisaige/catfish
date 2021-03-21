package com.catfish.ums.controller;

import com.catfish.ums.entity.domain.UmsMenu;
import com.catfish.ums.service.UmsMenuService;
import com.hisaige.dbcore.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 菜单操作
 * @author chenyj
 * 2020/5/23 - 18:53.
 **/
@Controller
@RequestMapping("/menu")
@Api(tags = "UmsMenuController", description = "菜单管理")
public class UmsMenuController extends BaseController<UmsMenuService, UmsMenu> {


//    @Override
//    @PostMapping("/create")
//    @ResponseBody
//    @ApiOperation("创建用户")
//    public AjaxMessageRes<Integer> create(@RequestBody @Validated({AddGroup.class}) UmsUser umsUser) {
//        return new AjaxMessageRes<>(service.save(umsUser));
//    }

}
