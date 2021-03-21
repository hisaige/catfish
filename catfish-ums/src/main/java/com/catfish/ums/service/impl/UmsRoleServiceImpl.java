package com.catfish.ums.service.impl;

import com.catfish.ums.entity.domain.UmsMenu;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.ums.entity.domain.UmsRoleMenu;
import com.catfish.ums.entity.domain.UmsRolePermission;
import com.catfish.ums.mapper.UmsRoleMapper;
import com.catfish.ums.service.UmsMenuService;
import com.catfish.ums.service.UmsPermissionService;
import com.catfish.ums.service.UmsRoleMenuService;
import com.catfish.ums.service.UmsRolePermissionService;
import com.catfish.ums.service.UmsRoleService;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import com.hisaige.web.core.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色服务
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
@Service
@Slf4j
public class UmsRoleServiceImpl extends BaseServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsRoleMenuService umsRoleMenuService;

    @Autowired
    private UmsMenuService umsMenuService;

    @Autowired
    private UmsPermissionService umsPermissionService;

    @Autowired
    private UmsRolePermissionService umsRolePermissionService;

    @Override
    public int addMenus(String roleId, List<String> menuIds, Boolean status) throws Exception {
        if (null == roleId || !mapper.existsWithPrimaryKey(roleId)) { //用户必须存在
            log.warn("role not exist, roleId:{}", roleId);
            throw new InvalidException(ReturnCodeEnum.ROLE_NOT_EXIST);
        }
        List<UmsMenu> umsMenus = umsMenuService.getByIds(menuIds);
        if (CollectionUtils.isEmpty(umsMenus)) {
            log.warn("menus not exist, roleIds:{}", menuIds);
            throw new InvalidException(ReturnCodeEnum.MENU_NOT_EXIST);
        }
        Set<String> retMenuIds = umsMenus.stream().map(UmsMenu::getId).collect(Collectors.toSet());
        if (retMenuIds.size() != menuIds.size()) {
            //有部分角色不存在
            menuIds.removeAll(retMenuIds); //取差集
            if (!CollectionUtils.isEmpty(menuIds)) {
                //存在差集说明有部分resourceIds不存在
                log.warn("menus is not exist, resourceIds:{}", menuIds);
            }
        }
        ArrayList<UmsRoleMenu> umsRoleMenus = new ArrayList<>();
        for (String menuId : retMenuIds) {
            UmsRoleMenu umsRoleMenu = new UmsRoleMenu();
            umsRoleMenu.setRoleId(roleId);
            umsRoleMenu.setMenuId(menuId);
            umsRoleMenus.add(umsRoleMenu);
        }
        return umsRoleMenuService.saveAll(umsRoleMenus);
    }

    @Override
    public int addPermissions(String roleId, List<String> permissionIds, Boolean status) throws Exception {
        if (null == roleId || !mapper.existsWithPrimaryKey(roleId)) { //用户必须存在
            log.warn("role not exist, roleId:{}", roleId);
            throw new InvalidException(ReturnCodeEnum.USER_NOT_EXIST);
        }
        List<UmsPermission> retPermissions = umsPermissionService.getByIds(permissionIds);
        umsPermissionService.checkPermissionIds(permissionIds, retPermissions);
        ArrayList<UmsRolePermission> umsRolePermissions = new ArrayList<>();

        for (String permissionId : permissionIds) {
            UmsRolePermission umsRolePermission = new UmsRolePermission();
            umsRolePermission.setCreateTime(new Date());
            umsRolePermission.setRoleId(roleId);
            umsRolePermission.setPermissionId(permissionId);
            umsRolePermissions.add(umsRolePermission);
        }
        return umsRolePermissionService.saveAll(umsRolePermissions);
    }

    @Override
    public List<UmsPermission> getPermissions(String roleId) throws Exception {

        Assert.notNull(roleId, "msg.roleId.null");
        UmsRolePermission umsRolePermission = new UmsRolePermission();
        umsRolePermission.setRoleId(roleId);
        List<UmsRolePermission> umsRolePermissions = umsRolePermissionService.get(umsRolePermission);

        List<String> permissionIds = umsRolePermissions.stream().map(UmsRolePermission::getPermissionId).collect(Collectors.toList());

        return umsPermissionService.getByIds(permissionIds);
    }

    @Override
    public List<UmsMenu> getRoleMenus(String roleId) throws Exception {
        if(null == roleId){
            return new ArrayList<>();
        }
        UmsRoleMenu umsRoleMenu = new UmsRoleMenu();
        umsRoleMenu.setRoleId(roleId);
        List<UmsRoleMenu> umsRoleMenus = umsRoleMenuService.get(umsRoleMenu);

        if(CollectionUtils.isEmpty(umsRoleMenus)){
            return new ArrayList<>();
        }

        Set<String> umsRoleMenuIds = umsRoleMenus.stream().map(UmsRoleMenu::getRoleId).collect(Collectors.toSet());
        return umsMenuService.getByIds(umsRoleMenuIds);
    }

}
