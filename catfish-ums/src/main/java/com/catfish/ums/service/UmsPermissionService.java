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
}
