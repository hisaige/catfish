package com.catfish.ums.service;

import com.catfish.ums.entity.domain.UmsMenu;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.service.BaseService;

import java.util.List;

/**
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
public interface UmsRoleService extends BaseService<UmsRole> {

    int addMenus(String roleId, List<String> menuIds, Boolean status) throws Exception;

    int addPermissions(String userId, List<String> resourceIds, Boolean status) throws Exception;

    List<UmsPermission> getPermissions(String roleId) throws Exception;

    List<UmsMenu> getRoleMenus(String roleId) throws Exception;

}
