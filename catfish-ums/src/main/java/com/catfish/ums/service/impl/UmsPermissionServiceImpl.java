package com.catfish.ums.service.impl;

import com.catfish.ums.entity.domain.UmsRolePermission;
import com.catfish.ums.mapper.UmsPermissionMapper;
import com.catfish.ums.mapper.UmsRolePermissionMapper;
import com.catfish.ums.service.UmsPermissionService;
import com.catfish.ums.service.UmsRolePermissionService;
import com.catfish.common.security.entity.model.UmsPermission;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import com.hisaige.web.core.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
@Service
@Slf4j
public class UmsPermissionServiceImpl extends BaseServiceImpl<UmsPermissionMapper, UmsPermission> implements UmsPermissionService {

    @Autowired
    private UmsRolePermissionMapper umsRolePermissionMapper;

    @Autowired
    private UmsRolePermissionService umsRolePermissionService;

    @Override
    public List<UmsPermission> getByIds(Iterable<? extends Serializable> permissionIds) throws Exception {
        return super.getByIds(permissionIds);
    }


    @Override
    public void checkPermissionIds(List<String> ids, List<UmsPermission> retPermissions) throws InvalidException {
        if (CollectionUtils.isEmpty(retPermissions)) {
            log.warn("permission not exist, permissionIds:{}", ids);
            throw new InvalidException(ReturnCodeEnum.ROLE_NOT_EXIST);
        }
        Set<String> retPermissionIds = retPermissions.stream().map(UmsPermission::getId).collect(Collectors.toSet());
        if (retPermissionIds.size() != ids.size()) {
            //部分权限不存在
            ids.removeAll(retPermissionIds); //取差集
            if (!CollectionUtils.isEmpty(ids)) {
                //存在差集说明有部分permissionIds不存在
                log.warn("permission is not exist, permissionIds:{}", ids);
            }
        }
    }

    @Override
    public List<UmsPermission> getByRoleIds(Iterable<String> roleIds) throws Exception {
        if(null == roleIds || !roleIds.iterator().hasNext()){
            return new ArrayList<>();
        }

        Example example = new Example(UmsRolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleId", roleIds);

        List<UmsRolePermission> umsRolePermissions = umsRolePermissionMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(umsRolePermissions)){
            return new ArrayList<>();
        }
        return getByIds(umsRolePermissions.stream().map(UmsRolePermission::getPermissionId).collect(Collectors.toList()));
    }
}
