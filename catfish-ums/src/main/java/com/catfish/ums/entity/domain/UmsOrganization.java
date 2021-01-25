package com.catfish.ums.entity.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class UmsOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id，这里用数字当作id主要是为了方便ancestors字段
     */
    @Id
    private Long id;

    private String orgName;

    private String orgCode;

    /**
     * 父组织id
     */
    private Long orgPid;

    /**
     * 父组织id集合
     */
    private String ancestors;

    private Date createTime;

    private Date updateTime;

    /**
     * 子组织
     */
    @Transient
    private List<UmsOrganization> children = new ArrayList<>();

}
