package com.catfish.common.security.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hisaige.dbcore.support.UUIDGenId;
import com.hisaige.web.core.valid.AddGroup;
import com.hisaige.web.core.valid.EditGroup;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Table(name = "ums_user")
public class UmsUser implements Serializable {

    @Id
    @ApiModelProperty(value = "id", example = "123")
    @NotNull(message = "msg.id.notnull", groups = {EditGroup.class})
    @KeySql(genId = UUIDGenId.class)
    private String id;

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "msg.username.notnull", groups = {AddGroup.class})
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "msg.password.notnull", groups = {AddGroup.class})
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "证件号码")
    private String idcard;

    @ApiModelProperty(value = "备注信息")
    private String introduction;

    @ApiModelProperty(value = "到期时间", hidden = true)
    private Date validDate;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", orgId=").append(orgId);
        sb.append(", avatar=").append(avatar);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", nickName=").append(nickName);
        sb.append(", realName=").append(realName);
        sb.append(", idcard=").append(idcard);
        sb.append(", introduction=").append(introduction);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}