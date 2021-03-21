package com.catfish.ums.entity.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hisaige.dbcore.entity.po.DateTimePO;
import com.hisaige.web.core.valid.AddGroup;
import com.hisaige.web.core.valid.EditGroup;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenyj
 * @since 2021-01-25
 */
@Data
@Table(name = "ums_organization")
public class UmsOrganization extends DateTimePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id，这里用数字当作id主要是为了方便ancestors字段
     */
    @Id
    @NotNull(groups = {EditGroup.class}, message = "节点ID不能为空")
    private Long id;

    @NotEmpty(groups = {AddGroup.class, EditGroup.class}, message = "组织名称不能为空")
    private String orgName;

    @NotEmpty(groups = {AddGroup.class, EditGroup.class}, message = "组织编码不能为空")
    private String orgCode;

    /**
     * 父组织id
     */
    @NotNull(groups = {AddGroup.class, EditGroup.class}, message = "父节点不能为空")
    private Long orgPid;

    /**
     * 父组织id集合
     */
    private String ancestors;

    /**
     * 子组织
     */
    @Transient
    private List<UmsOrganization> children = new ArrayList<>();

}
