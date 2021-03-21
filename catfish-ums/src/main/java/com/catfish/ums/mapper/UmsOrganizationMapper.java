package com.catfish.ums.mapper;

import com.catfish.ums.entity.domain.UmsOrganization;
import com.hisaige.dbcore.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenyj
 * @since 2021-01-25
 */
public interface UmsOrganizationMapper extends BaseMapper<UmsOrganization> {

    /**
     * 根据id查询其子组织
     * @param id 组织id
     * @return List<UmsOrganization>
     */
    @Select("select * from ums_organization where find_in_set(#{id}, ancestors)")
    List<UmsOrganization> selectChildrenById(Long id);

    /**
     * 根据id查询其子组织
     * @param id 组织id
     * @return List<UmsOrganization>
     */
    @Select("select id from ums_organization where find_in_set(#{id}, ancestors)")
    List<Long> selectChildrenIdById(Long id);
}
