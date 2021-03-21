package com.catfish.ums.service.impl;

import com.catfish.common.security.util.SystemUserUtils;
import com.catfish.ums.entity.domain.UmsOrganization;
import com.catfish.ums.mapper.UmsOrganizationMapper;
import com.catfish.ums.service.UmsOrganizationService;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import com.hisaige.web.core.util.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.SQLIntegrityConstraintViolationException;
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

    private static final Logger logger = LoggerFactory.getLogger(UmsOrganizationServiceImpl.class);

    @Override
    public UmsOrganization getUserRootTree(Boolean recursion) throws Exception {
        UmsOrganization userRootNode = getUserRootNode();
        return getTree(userRootNode, recursion);
    }

    @Override
    public UmsOrganization getUserRootNode() throws Exception {
        String userOrg = SystemUserUtils.getUserOrg();
        if(null == userOrg) {
            return getRootNode();
        }
        //根据ID获取
        return get(userOrg);
    }

    @Override
    public UmsOrganization getRootTree(Boolean recursion) {

        UmsOrganization rootNode = getRootNode();
        return getTree(rootNode, recursion);
    }

    @Override
    public UmsOrganization getTree(UmsOrganization rootNode, Boolean recursion) {
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

    @Override
    public List<Long> getChildIdsByOrgId(Long orgId) {
        return mapper.selectChildrenIdById(orgId);
    }

    @Override
    public int saveSelective(UmsOrganization record) throws Exception {

        Long pid = record.getOrgPid();
        if(null == pid) {
            throw new InvalidException(ReturnCodeEnum.ILLEGALARGUMENT_EXCEPTION);
        }
        UmsOrganization umsOrganization = get(record.getOrgPid());

        //设置祖宗节点路径id集合
        record.setAncestors(umsOrganization.getAncestors() + "," + record.getOrgPid());
        int active = 0;
        try{
            active = super.saveSelective(record);
        } catch (SQLIntegrityConstraintViolationException e) {

            logger.error("saveSelective failed...", e);

            //外键约束导致失败
            if(e.getMessage().contains(record.getOrgCode())) {
                //如果数值和组织编码相关
                Example example = new Example(getEntityClass());
                example.createCriteria().andEqualTo("orgCode", record.getOrgCode());
                UmsOrganization existOrgCodeNode = mapper.selectOneByExample(example);
                if(null != existOrgCodeNode) {
                    throw new InvalidException(ReturnCodeEnum.ILLEGALARGUMENT_EXCEPTION, "组织编码已经存在与组织" + existOrgCodeNode.getOrgName());
                }
            }
        }
        return active;
    }

    @Override
    public int updateByPrimaryKeySelective(UmsOrganization record) throws Exception {

        //不修改这个属性，将其设置为null
        record.setOrgPid(null);
        record.setCreateTime(null);

        return super.updateByPrimaryKeySelective(record);
    }
}
