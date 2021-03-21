package com.catfish.ums.service;

import com.catfish.common.security.access.UmsUserDetails;
import com.catfish.ums.entity.domain.UmsResource;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.common.security.entity.model.UmsPermission;
import com.catfish.common.security.entity.model.UmsUser;
import com.catfish.ums.entity.dto.OnlineUserDTO;
import com.catfish.ums.entity.vo.LoginUserInfo;
import com.catfish.ums.entity.vo.UserInfo;
import com.hisaige.dbcore.service.BaseService;
import com.hisaige.web.core.exception.InvalidException;

import java.util.List;

/**
 * 用户在线离线状态管理
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
public interface UmsUserService extends BaseService<UmsUser> {

    /**
     * 获取当前用户信息
     * @return
     */
    UserInfo getUserInfo() throws Exception;

    /**
     * 添加角色
     * @param userId 用户id
     * @param roleIds 角色id列表
     * @return 激活条数
     * @throws InvalidException 验证异常
     */
    int addRoles(String userId, List<String> roleIds) throws Exception;

    /**
     * @param userId 用户id
     * @param resourceIds 角色id列表
     * @return 激活条数
     * @throws InvalidException 验证异常
     */
    int addResources(String userId, List<String> resourceIds) throws Exception;

    int addPermissions(String userId, List<String> permissionIds, Boolean status) throws Exception;

    List<UmsResource> getUserResources(String userId) throws Exception;

    List<UmsPermission> getUserPermissions(String userId) throws Exception;

    List<UmsRole> getRoles(String userId) throws Exception;

    UmsUserDetails loadUserByUsername(String userName) throws Exception;

    String login(String userName, String password) throws Exception;

    String refreshToken(String token);

    List<LoginUserInfo> getOnlineUsers(OnlineUserDTO onlineUserDTO);

    /**
     * 踢掉某在线用户
     * @param tokenPrefix token前缀 用来模糊搜索token进行登出
     * @param userName 配合tokenPrefix 检索出用户名后对比是否匹配，作为兜底避免踢错用户
     * @return Boolean
     */
    Boolean logout(String tokenPrefix, String userName);

    /**
     * 用户登出
     * @param token token
     * @return Boolean
     */
    Boolean logout(String token);
}
