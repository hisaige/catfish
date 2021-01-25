package com.catfish.ums.entity.dto;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/10$ - 10:04$
 */
public class OnlineUserDTO {

    private String username;
    private String ipAddr;

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

}
