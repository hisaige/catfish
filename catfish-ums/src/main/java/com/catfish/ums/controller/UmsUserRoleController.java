package com.catfish.ums.controller;

import com.catfish.ums.entity.domain.UmsUserRole;
import com.catfish.ums.service.UmsUserRoleService;
import com.hisaige.dbcore.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户操作
 * @author chenyj
 * 2020/5/23 - 18:53.
 **/
@Controller
@RequestMapping("/user/role")
@Api(tags = "UmsUserRoleController", description = "用户管理")
@Slf4j
public class UmsUserRoleController extends BaseController<UmsUserRoleService, UmsUserRole> {

}
