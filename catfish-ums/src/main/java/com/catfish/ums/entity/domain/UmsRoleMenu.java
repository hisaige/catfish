package com.catfish.ums.entity.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "ums_role_menu")
public class UmsRoleMenu implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "菜单ID")
    private String menuId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", menuId=").append(menuId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}