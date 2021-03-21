package com.catfish.ums.entity.domain;

import com.hisaige.web.core.valid.AddGroup;
import com.hisaige.web.core.valid.EditGroup;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Table(name = "ums_user_permission")
public class UmsUserPermission implements Serializable {

    @Id
    @ApiModelProperty(value = "id", example = "123")
    private String id;

    @NotNull(message = "msg.userId.NotNull", groups = {EditGroup.class, AddGroup.class})
    private String userId;

    @ApiModelProperty(value = "权限id")
    @NotNull(message = "msg.resourceId.NotNull", groups = {EditGroup.class, AddGroup.class})
    private String permissionId;

    @NotNull(message = "msg.status.NotNull", groups = {EditGroup.class, AddGroup.class})
    private Boolean status;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}