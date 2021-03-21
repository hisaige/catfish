package com.catfish.ums.controller;

import com.catfish.ums.entity.dto.MenuPermissionDTO;
import com.catfish.ums.entity.domain.UmsMenu;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.ums.service.UmsRoleService;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.controller.BaseController;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色操作
 * @author chenyj
 * 2020/5/23 - 18:53.
 **/
@Controller
@RequestMapping("/role")
@Api(tags = "UmsRoleController", description = "角色管理")
public class UmsRoleController extends BaseController<UmsRoleService, UmsRole> {

    @Autowired
    private UmsRoleService umsRoleService;

    @PostMapping("/menu/add/{roleId}")
    @ResponseBody
    @ApiOperation("为角色添加菜单")
    public AjaxMessageRes<Integer> addUserResource(@PathVariable("roleId") String userId, @RequestBody MenuPermissionDTO menuResourceDTO) throws Exception {

        return new AjaxMessageRes<>(umsRoleService.addMenus(userId, menuResourceDTO.getResourceIds(), menuResourceDTO.getStatus()));
    }

    @PostMapping("/permission/add/{userId}")
    @ResponseBody
    @ApiOperation("为用户设置权限")
    public AjaxMessageRes<Integer> addRolePermission(@PathVariable("userId") String userId, @RequestBody MenuPermissionDTO menuPermissionDTO) throws Exception {

        return new AjaxMessageRes<>(umsRoleService.addPermissions(userId, menuPermissionDTO.getResourceIds(), menuPermissionDTO.getStatus()));
    }

    @GetMapping("/permission/get/{roleId}")
    @ResponseBody
    @ApiOperation("获取角色拥有的权限")
    public AjaxMessageRes<List<UmsPermission>> getRolePermissions(@PathVariable("roleId") String roleId) throws Exception {

        return new AjaxMessageRes<>(umsRoleService.getPermissions(roleId));
    }

    @GetMapping("/menu/get/{roleId}")
    @ResponseBody
    @ApiOperation("获取角色拥有的菜单")
    public AjaxMessageRes<List<UmsMenu>> getRoleMenus(@PathVariable("roleId") String roleId) throws Exception {

        return new AjaxMessageRes<>(umsRoleService.getRoleMenus(roleId));
    }


}
