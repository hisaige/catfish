package com.catfish.common.security.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Table(name = "ums_permission")
@JsonInclude(JsonInclude.Include.NON_NULL) //为空的字段不返回
public class UmsPermission implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(value = "父级ID")
    private String parentId;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UmsPermission{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}