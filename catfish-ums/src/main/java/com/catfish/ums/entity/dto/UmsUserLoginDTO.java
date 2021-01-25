package com.catfish.ums.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by macro on 2018/4/26.
 */
public class UmsUserLoginDTO {
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "msg.username.empty")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "msg.password.empty")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
