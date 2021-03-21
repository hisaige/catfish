package com.catfish.ums.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户资源
 * @author chenyj
 * 2020/5/24 - 23:40.
 **/
@Data
public class MenuPermissionDTO {

    //资源或菜单的id列表
    private List<String> resourceIds;

    //资源状态，true->启用资源，false->禁用资源，就算用户角色中有这个资源也不会生效
    private Boolean status = true;
}
