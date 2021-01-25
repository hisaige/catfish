package com.catfish.ums.service.impl;

import com.catfish.ums.entity.domain.UmsOrganization;
import com.catfish.ums.mapper.UmsOrganizationMapper;
import com.catfish.ums.service.UmsOrganizationService;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import com.hisaige.web.core.util.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenyj
 * @since 2021-01-25
 */
@Service
public class UmsOrganizationServiceImpl extends BaseServiceImpl<UmsOrganizationMapper, UmsOrganization> implements UmsOrganizationService {

    @Override
    public UmsOrganization getRootTree(Boolean recursion) {

        UmsOrganization rootNode = getRootNode();
        if(null == recursion || !recursion) {
            return rootNode;
        }
        if(null != rootNode) {
            List<UmsOrganization> umsOrganizations = mapper.selectChildrenById(rootNode.getId());
            if(!CollectionUtils.isEmpty(umsOrganizations)) {
                //构造树，将父组织也放进来一起构造
                umsOrganizations.add(0, rootNode);
                buildTree(umsOrganizations);
            }
        }
        return rootNode;
    }

    @Override
    public UmsOrganization getRootNode() {
        Example example = new Example(UmsOrganization.class);
        example.createCriteria().andEqualTo("ancestors", 0);
        List<UmsOrganization> umsOrganizations = mapper.selectByExampleAndRowBounds(example, new RowBounds(0, 1));
        return CollectionUtils.isEmpty(umsOrganizations)?null:umsOrganizations.get(0);
    }

    @Override
    public UmsOrganization buildTree(List<UmsOrganization> organizations) {
        if (CollectionUtils.isEmpty(organizations)) {
            return null;
        }

        UmsOrganization root = organizations.get(0);

        Map<Long, UmsOrganization> idOrgMap = new HashMap<>();

        for (UmsOrganization organization : organizations) {
            if(root.getAncestors().length() > organization.getAncestors().length()) {
                //身为父节点，ancestors长度肯定更短
                root = organization;
            }
            idOrgMap.put(organization.getId(), organization);
        }

        for (UmsOrganization organization : organizations) {
            if(idOrgMap.containsKey(organization.getOrgPid())) {
                //构造父子关系
                idOrgMap.get(organization.getOrgPid()).getChildren().add(organization);
            }
        }
        return root;
    }
}
