package com.catfish.ums.entity.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "ums_resource")
public class UmsResource implements Serializable {

    @Id
    private Long id;

    @ApiModelProperty(value = "资源id")
    private Long resourceId;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @ApiModelProperty(value = "启用状态：0->禁用；1->启用")
    private Integer status;

    private Integer sort;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }



    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resourceId=").append(resourceId);
        sb.append(", resourceName=").append(resourceName);
        sb.append(", resourceType=").append(resourceType);
        sb.append(", status=").append(status);
        sb.append(", sort=").append(sort);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}