package com.catfish.ums.service.impl;

import cn.hutool.core.lang.Assert;
import com.catfish.common.core.entity.CatfishConstants;
import com.catfish.common.security.access.UmsUserDetails;
import com.catfish.common.security.entity.model.UmsPermission;
import com.catfish.common.security.entity.model.UmsUser;
import com.catfish.common.security.util.JwtTokenManager;
import com.catfish.common.security.util.SystemUserUtils;
import com.catfish.ums.entity.domain.UmsResource;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.ums.entity.domain.UmsUserPermission;
import com.catfish.ums.entity.domain.UmsUserResource;
import com.catfish.ums.entity.domain.UmsUserRole;
import com.catfish.ums.entity.dto.OnlineUserDTO;
import com.catfish.ums.entity.vo.LoginUserInfo;
import com.catfish.ums.entity.vo.UserInfo;
import com.catfish.ums.mapper.UmsUserMapper;
import com.catfish.ums.mapstruct.UserDetail2LoginUserMapStruct;
import com.catfish.ums.service.UmsOrganizationService;
import com.catfish.ums.service.UmsPermissionService;
import com.catfish.ums.service.UmsResourceService;
import com.catfish.ums.service.UmsRoleService;
import com.catfish.ums.service.UmsUserPermissionService;
import com.catfish.ums.service.UmsUserResourceService;
import com.catfish.ums.service.UmsUserRoleService;
import com.catfish.ums.service.UmsUserService;
import com.hisaige.dbcore.entity.dto.PageReq;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import com.hisaige.i18n.locale.LocaleMessage;
import com.hisaige.redis.service.RedisService;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.exception.InvalidException;
import com.hisaige.web.core.util.CollectionUtils;
import com.hisaige.web.core.util.IpUtils;
import com.hisaige.web.core.util.RequestUtils;
import com.hisaige.web.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
@Service
@Slf4j
public class UmsUserServiceImpl extends BaseServiceImpl<UmsUserMapper, UmsUser> implements UmsUserService {

    @Autowired
    private UmsRoleService umsRoleService;

    @Autowired
    private UmsResourceService umsResourceService;

    @Autowired
    private UmsUserRoleService umsUserRoleService;

    @Autowired
    private UmsUserResourceService umsUserResourceService;

    @Autowired
    private UmsPermissionService umsPermissionService;

    @Autowired
    private UmsUserPermissionService umsUserPermissionService;

    @Autowired
    private UmsOrganizationService umsOrganizationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocaleMessage localeMessage;

    @Autowired
    private JwtTokenManager jwtTokenUtils;

    @Autowired
    private RedisService<UmsUserDetails> redisService;

    @Override
    public UserInfo getUserInfo() throws Exception {
        UserInfo userInfo = new UserInfo();
        String userId = SystemUserUtils.getUserId();
        UmsUser umsUser = mapper.selectByPrimaryKey(userId);
        if(null == umsUser) {
            throw new BadCredentialsException("get user info failed...");
        }

        userInfo.setName(umsUser.getUsername());
        userInfo.setAvatar(umsUser.getAvatar());
        userInfo.setIntroduction(umsUser.getIntroduction());

        List<UmsRole> roles = getRoles(userId);
        List<String> rolesName = roles.stream().map(UmsRole::getRoleCode).collect(Collectors.toList());

        userInfo.setRoles(rolesName);
        return userInfo;
    }

    @Override
    public int addRoles(String userId, List<String> roleIds) throws Exception {

        if (null == userId || !mapper.existsWithPrimaryKey(userId)) { //用户必须存在
            log.warn("user not exist, userId:{}", userId);
            throw new InvalidException(ReturnCodeEnum.USER_NOT_EXIST);
        }
        List<UmsRole> roles = umsRoleService.getByIds(roleIds);
        if (CollectionUtils.isEmpty(roles)) {
            log.warn("role not exist, roleIds:{}", roleIds);
            throw new InvalidException(ReturnCodeEnum.ROLE_NOT_EXIST);
        }
        Set<String> retRoleIds = roles.stream().map(UmsRole::getId).collect(Collectors.toSet());
        if (retRoleIds.size() != roleIds.size()) {
            //有部分角色不存在
            roleIds.removeAll(retRoleIds); //取差集
            if (!CollectionUtils.isEmpty(roleIds)) {
                //存在差集说明有部分userId不存在
                log.warn("role is not exist, roleIds:{}", roleIds);
            }
        }

        ArrayList<UmsUserRole> umsUserRoles = new ArrayList<>();
        for (String roleId : retRoleIds) {
            UmsUserRole umsUserRole = new UmsUserRole();
            umsUserRole.setCreateTime(new Date());
            umsUserRole.setUserId(userId);
            umsUserRole.setRoleId(roleId);
            umsUserRoles.add(umsUserRole);
        }
        return umsUserRoleService.saveAll(umsUserRoles);
    }

    @Override
    public int addResources(String userId, List<Long> resourceIds) throws Exception {
        if (null == userId || !mapper.existsWithPrimaryKey(userId)) { //用户必须存在
            log.warn("user not exist, userId:{}", userId);
            throw new InvalidException(ReturnCodeEnum.USER_NOT_EXIST);
        }
        List<UmsResource> resources = umsResourceService.getByIds(resourceIds);
        if (CollectionUtils.isEmpty(resources)) {
            log.warn("resources not exist, resourceIds:{}", resourceIds);
            throw new InvalidException(ReturnCodeEnum.ROLE_NOT_EXIST);
        }
        Set<Long> retResourceIds = resources.stream().map(UmsResource::getId).collect(Collectors.toSet());
        if (retResourceIds.size() != resourceIds.size()) {
            //有部分角色不存在
            resourceIds.removeAll(retResourceIds); //取差集
            if (!CollectionUtils.isEmpty(resourceIds)) {
                //存在差集说明有部分resourceIds不存在
                log.warn("resource is not exist, resourceIds:{}", resourceIds);
            }
        }
        ArrayList<UmsUserResource> umsUserResources = new ArrayList<>();
        for (String resourceId : resourceIds) {
            UmsUserResource umsUserResource = new UmsUserResource();
            umsUserResource.setCreateTime(new Date());
            umsUserResource.setUserId(userId);
            umsUserResource.setResourceId(resourceId);
            umsUserResources.add(umsUserResource);
        }
        return umsUserResourceService.saveAll(umsUserResources);
    }

    @Override
    public int addPermissions(String userId, List<String> permissionIds, Boolean status) throws Exception {
        if (null == userId || !mapper.existsWithPrimaryKey(userId)) { //用户必须存在
            log.warn("user not exist, userId:{}", userId);
            throw new InvalidException(ReturnCodeEnum.USER_NOT_EXIST);
        }
        List<UmsPermission> retPermissions = umsPermissionService.getByIds(permissionIds);
        umsPermissionService.checkPermissionIds(permissionIds, retPermissions);
        ArrayList<UmsUserPermission> umsUserPermissions = new ArrayList<>();
        for (String permissionId : permissionIds) {
            UmsUserPermission umsUserPermission = new UmsUserPermission();
            umsUserPermission.setCreateTime(new Date());
            umsUserPermission.setUserId(userId);
            umsUserPermission.setStatus(status); //设置禁用状态
            umsUserPermission.setPermissionId(permissionId);
            umsUserPermissions.add(umsUserPermission);
        }
        return umsUserPermissionService.saveAll(umsUserPermissions);
    }

    @Override
    public List<UmsResource> getUserResources(String userId) throws Exception {

        if (null == userId) {
            return new ArrayList<>();
        }
        UmsUserResource umsUserResource = new UmsUserResource();
        umsUserResource.setUserId(userId);
        List<UmsUserResource> umsUserResources = umsUserResourceService.get(umsUserResource);
        if (CollectionUtils.isEmpty(umsUserResources)) {
            return new ArrayList<>();
        }
        Set<String> resourceIds = umsUserResources.stream().map(UmsUserResource::getResourceId).collect(Collectors.toSet());
        return umsResourceService.getByIds(resourceIds);
    }

    @Override
    public List<UmsPermission> getUserPermissions(String userId) throws Exception {

        if (null == userId) {
            return new ArrayList<>();
        }
        UmsUserPermission umsUserPermission = new UmsUserPermission();
        umsUserPermission.setUserId(userId);

        List<UmsUserPermission> umsUserPermissions = umsUserPermissionService.get(umsUserPermission);

        //获取用户的所有直属权限，将禁用的和启用的分开放
        Set<String> notUmsUserPermissionSet = new HashSet<>();
        Set<String> umsUserPermissionSet = new HashSet<>();
        //过滤禁用的
        for (UmsUserPermission action : umsUserPermissions) {
            if (!BooleanUtils.isTrue(action.getStatus())) {
                //被禁用的
                notUmsUserPermissionSet.add(action.getPermissionId());
            } else {
                //启用的
                umsUserPermissionSet.add(action.getPermissionId());
            }
        }
        List<UmsPermission> umsPermissions = umsPermissionService.getByIds(umsUserPermissionSet);

        //获取用户的所有角色
        List<UmsRole> roles = getRoles(userId);

        if (CollectionUtils.isEmpty(roles)) {
            //角色为空则返回用户直接关联的权限
            return umsPermissions;
        }
        //获取用户所有角色拥有的权限的集合
        List<UmsPermission> fromRolePermissions = umsPermissionService.getByRoleIds(roles.stream().map(UmsRole::getId).collect(Collectors.toSet()));

        //角色权限集合与用户启用的直属权限取并集，与用户未启用的直属权限取交集
        if (fromRolePermissions.isEmpty()) {
            return umsPermissions;
        } else {
            List<UmsPermission> filterPermission;
            if (!notUmsUserPermissionSet.isEmpty()) {
                filterPermission = fromRolePermissions.stream().filter(action -> !notUmsUserPermissionSet.contains(action.getId())).collect(Collectors.toList());
            } else {
                filterPermission = fromRolePermissions;
            }
            for (UmsPermission umsPermission : filterPermission) {
                if (!umsUserPermissionSet.contains(umsPermission.getId())) {
                    umsPermissions.add(umsPermission);
                }
            }
        }
        return umsPermissions;
    }

    @Override
    public List<UmsRole> getRoles(String userId) throws Exception {

        if (null == userId) {
            return new ArrayList<>();
        }
        UmsUserRole umsUserRole = new UmsUserRole();
        umsUserRole.setUserId(userId);
        List<UmsUserRole> umsUserRoles = umsUserRoleService.get(umsUserRole);
        Set<String> umsUserRoleIds = umsUserRoles.stream().map(UmsUserRole::getRoleId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(umsUserRoleIds)) {
            return new ArrayList<>();
        }
        return umsRoleService.getByIds(umsUserRoleIds);
    }

    @Override
    public UmsUserDetails loadUserByUsername(String userName) throws Exception {
        //获取用户信息
        UmsUser umsUser = new UmsUser();
        umsUser.setUsername(userName);
        List<UmsUser> byProvider = get(umsUser);
        if (CollectionUtils.isEmpty(byProvider)) {
            throw new UsernameNotFoundException("user not exist,user:" + userName);
        }
        UmsUser retUser = byProvider.get(0);
        List<UmsPermission> permissions = getUserPermissions(retUser.getId());
        return new UmsUserDetails(retUser, permissions);
    }

    @Override
    public String login(String userName, String password) throws Exception {

        Assert.notBlank(userName, "msg.userName.blank");
        UmsUserDetails userDetails = loadUserByUsername(userName);

        boolean matches = passwordEncoder.matches(password, userDetails.getPassword());
        if (!matches) {
            throw new BadCredentialsException(localeMessage.getMsgBundle().getString(String.valueOf(ReturnCodeEnum.LOGIN_FAILED.getCode())));
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtils.generateToken(userDetails.getRetUser().getUsername(), userDetails.getRetUser().getId(), userDetails.getOrgId());

        String reqIp = SystemUserUtils.getUserIp();
        userDetails.setIpAddr(reqIp);

        Date now = new Date();
        userDetails.setLoginTime(now);
        userDetails.setLastAccessTime(now);

        HttpServletRequest request = RequestUtils.getRequest();

        String agents = request.getHeader("USER-AGENT");
        userDetails.setBrowser(agents);
        userDetails.setIpAddr(IpUtils.getIp(request));

        //截取部分前缀，当作用户信息缓存
//        int length = token.length();//token长度是200，取前缀168个
        userDetails.setTokenPrefix(token.substring(0, 168));

        redisService.set(CatfishConstants.TOKEN_HEAD + token, userDetails, 60 * 10 * 3);
        //todo 后续添加是否限制用户重复登录功能
        return token;
    }

    @Override
    public String refreshToken(String token) {
        return null;
    }

    @Override
    public List<LoginUserInfo> getOnlineUsers(OnlineUserDTO onlineUserDTO) {
        Collection<String> tokenKeys = redisService.keys(CatfishConstants.TOKEN_HEAD + "*");

        List<UmsUserDetails> retList = new ArrayList<>();

        boolean isFilterUserName = false;
        boolean isFilterIpAddr= false;

        if(!CollectionUtils.isEmpty(tokenKeys)) {
            for (String tokenKey : tokenKeys) {
                UmsUserDetails umsUserDetails = redisService.get(tokenKey);
                UmsUser retUser = umsUserDetails.getRetUser();
                if(!StringUtils.isEmpty(onlineUserDTO.getIpAddr())){
                    //过滤IP
                    if(onlineUserDTO.getIpAddr().equals(umsUserDetails.getIpAddr())) {
                        retList.add(umsUserDetails);
                        continue;
                    }
                    isFilterUserName = true;
                }
                if(!StringUtils.isEmpty(onlineUserDTO.getUsername())){
                    //过滤IP
                    if(onlineUserDTO.getUsername().equals(umsUserDetails.getUsername())) {
                        retList.add(umsUserDetails);
                        continue;
                    }
                    isFilterIpAddr = true;
                }
                if(!isFilterUserName && !isFilterIpAddr) {
                    retList.add(umsUserDetails);
                }
            }
        }
        return UserDetail2LoginUserMapStruct.INSTANCE.toDto(retList);
    }

    @Override
    public Boolean logout(String tokenPrefix, String userName) {
        if(StringUtils.isEmpty(tokenPrefix) || StringUtils.isEmpty(userName)){
            return false;
        }

        //根据前缀匹配token
        Collection<String> tokenKeys = redisService.keys(CatfishConstants.TOKEN_HEAD  + tokenPrefix+ "*");
        //匹配到的token不为空则进行遍历比对用户名
        if(!CollectionUtils.isEmpty(tokenKeys)) {
            for (String tokenKey : tokenKeys) {
                UmsUserDetails umsUserDetails = redisService.get(tokenKey);
                if(userName.equals(umsUserDetails.getUsername())){
                    //如果token前缀和用户名都匹配，则进行剔除操作
                    return redisService.del(tokenKey);
                }
            }
        }
        return false;
    }

    @Override
    public Boolean logout(String token) {
        return redisService.del(CatfishConstants.TOKEN_HEAD + token);
    }

    @Override
    public int save(UmsUser record) throws Exception {
        record.setCreateTime(new Date());
        record.setPassword(passwordEncoder.encode(record.getPassword()));
        return super.save(record);
    }

    @Override
    public int updateByPrimaryKey(UmsUser record) throws Exception {
        if(null != record && "admin".equals(record.getUsername())){
            //todo 抛出异常
            return 0;
        }
        record.setPassword(passwordEncoder.encode(record.getPassword()));
        return super.updateByPrimaryKey(record);
    }

    @Override
    public boolean del(UmsUser record) throws Exception {
        if(null != record && "admin".equals(record.getUsername())){
            //todo 抛出异常
            return false;
        }
        return super.del(record);
    }

    @Override
    public boolean delById(Serializable id) throws Exception {
        UmsUser umsUser = get(id);
        if(null != umsUser && "admin".equals(umsUser.getUsername())){
            //todo 抛出异常
            return false;
        }
        return super.delById(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UmsUser record) throws Exception {
        if(null != record) {
            if(!StringUtils.isEmpty(record.getPassword())) {
                record.setPassword(passwordEncoder.encode(record.getPassword()));
            }
            return super.updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    @Override
    public List<UmsUser> getByProvider(PageReq pageDTO, UmsUser record) throws Exception {


        Example example = new Example(getEntityClass());

        if(!org.springframework.util.StringUtils.isEmpty(pageDTO.getOrderProperty())){
            //排序
            Example.OrderBy orderBy = example.orderBy(pageDTO.getOrderProperty());
            if("asc".equalsIgnoreCase(pageDTO.getDirection())){
                orderBy.asc();
            } else {
                orderBy.desc();
            }
        }

        Long orgId = record.getOrgId();

        List<Long> orgIds = umsOrganizationService.getChildIdsByOrgId(orgId);
        orgIds.add(orgId); //查询结果包含当前组织

        //赋值为空避免条件查询时带上
        record.setOrgId(null);

        Example.Criteria criteria = example.createCriteria();
        //使用不为空的对象属性查询，使用=号
        criteria.andEqualTo(record);
        //所选择组织下的全部用户
        criteria.andIn("orgId", orgIds);

        List<UmsUser> umsUsers = mapper.selectByExample(example);

        //根据sort排序
        umsUsers.sort((user1, user2) -> {
            if(null != user1.getSort() && null != user2.getSort()) {
                return user1.getSort().compareTo(user2.getSort());
            } else {
                return 1;
            }
        });

        return umsUsers;
    }
}
