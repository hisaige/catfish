package com.catfish.ums.service;

import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.service.BaseService;
import com.hisaige.web.core.exception.InvalidException;

import java.util.List;

/**
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
public interface UmsPermissionService extends BaseService<UmsPermission> {
    void checkPermissionIds(List<String> ids, List<UmsPermission> retPermissions) throws InvalidException;
    List<UmsPermission> getByRoleIds(Iterable<String> roleIds) throws Exception;

    List<UmsPermission> getRoot();

    /**
     * 根据父节点ID获取
     * @param parentId 父节点id
     * @return List<UmsPermission>
     */
    List<UmsPermission> getByParentId(String parentId);

    /**
     * 根据条件过滤权限
     * @param name 权限名称
     * @param status 状态 禁用/启用
     * @return List<UmsPermission>
     */
    List<UmsPermission> filter(String name, Boolean status);
}
