package com.catfish.ums.service;

import com.catfish.ums.entity.domain.UmsOrganization;
import com.hisaige.dbcore.service.BaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenyj
 * @since 2021-01-25
 */
public interface UmsOrganizationService extends BaseService<UmsOrganization> {

    /**
     * 获取组织树
     * @param recursion true表示递归
     * @return 组织树
     */
    UmsOrganization getRootTree(Boolean recursion);

    /**
     * 获取根组织节点
     * @return UmsOrganization
     */
    UmsOrganization getRootNode();

    /**
     * 根据列表构造组织树
     * @param organizations 组织树列表
     * @return UmsOrganization 返回组织树根节点
     */
    UmsOrganization buildTree(List<UmsOrganization> organizations);
}
