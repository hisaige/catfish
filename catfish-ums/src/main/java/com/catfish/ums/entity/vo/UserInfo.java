package com.catfish.ums.entity.vo;

import java.util.List;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/29$ - 23:37$
 */
public class UserInfo {
    private String name;
    private String avatar;
    private String introduction;
    private List<String> roles;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
