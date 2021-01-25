package com.catfish.ums.service;

import com.catfish.ums.entity.domain.UmsMenu;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.service.BaseService;
import com.hisaige.web.core.exception.InvalidException;

import java.util.List;

/**
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
public interface UmsRoleService extends BaseService<UmsRole> {

    int addMenus(String roleId, List<String> menuIds, Boolean status) throws InvalidException;

    int addPermissions(String userId, List<String> resourceIds, Boolean status) throws InvalidException;

    List<UmsPermission> getPermissions(String roleId);

    List<UmsPermission> getPermissionsWithCategory(String roleId) throws InvalidException;

    List<UmsMenu> getRoleMenus(String roleId);

}
