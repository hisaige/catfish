package com.catfish.common.security.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hisaige.dbcore.support.UUIDGenId;
import com.hisaige.web.core.valid.EditGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户权限表实体类
 */
@Table(name = "ums_permission")
@JsonInclude(JsonInclude.Include.NON_NULL) //为空的字段不返回
@Getter
@Setter
@ToString
public class UmsPermission implements Serializable {

    @Id
    @NotNull(message = "msg.id.notnull", groups = {EditGroup.class})
    @KeySql(genId = UUIDGenId.class)
    private String id;

    @ApiModelProperty(value = "父级ID")
    private String parentId;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "权限类型 0-目录，1-菜单，2-按钮")
    private Integer type;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "启用状态 true-启用")
    private Boolean status;

    @ApiModelProperty(value = "权限图标")
    private String icon;

    @ApiModelProperty(value = "是否是系统生成的，true表示是，不能删除")
    private Boolean isSystem;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}