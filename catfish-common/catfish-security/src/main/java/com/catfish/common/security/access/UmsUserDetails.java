package com.catfish.common.security.access;

import com.catfish.common.security.entity.model.UmsPermission;
import com.catfish.common.security.entity.model.UmsUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyj
 * 2020/6/6 - 14:12.
 **/
public class UmsUserDetails implements UserDetails {

    /**
     * 用户信息
     */
    private UmsUser retUser;

    /**
     * 制控权限
     */
    private List<UmsPermission> permissions;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 登录ip所属位置
     */
    private String ipLocation;

    /**
     * 客户端系统
     */
    private String clientOs;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 最后访问时间
     */
    private Date lastAccessTime;

    /**
     * token前缀，用来剔用户等操作
     */
    private String tokenPrefix;

    public UmsUserDetails() {
    }

    public UmsUserDetails(UmsUser retUser, List<UmsPermission> permissions) {
        this.retUser = retUser;
        this.permissions = permissions;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户拥有的权限
        return permissions.stream()
                .map(permission ->new SimpleGrantedAuthority(permission.getId()+":"+permission.getName()))
                .collect(Collectors.toList());
    }

    public UmsUser getRetUser() {
        return retUser;
    }

    public void setRetUser(UmsUser retUser) {
        this.retUser = retUser;
    }

    public List<UmsPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UmsPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return retUser.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return retUser.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        Integer status = retUser.getStatus();
        return null != status && status.equals(1);
    }

    @JsonIgnore
    public Long getOrgId() {

        return retUser == null ? null : retUser.getOrgId();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getIpLocation() {
        return ipLocation;
    }

    public void setIpLocation(String ipLocation) {
        this.ipLocation = ipLocation;
    }

    public String getClientOs() {
        return clientOs;
    }

    public void setClientOs(String clientOs) {
        this.clientOs = clientOs;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

}
