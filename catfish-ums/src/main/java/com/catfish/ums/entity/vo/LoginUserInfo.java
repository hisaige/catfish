package com.catfish.ums.entity.vo;

import java.util.Date;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/10$ - 17:58$
 */
public class LoginUserInfo {

    private String username;
    private String ipAddr;
    private String ipLocation;
    private String clientOs;
    private String browser;

    private Date loginTime;

    private Date lastAccessTime;

    private String tokenPrefix;

    public LoginUserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
