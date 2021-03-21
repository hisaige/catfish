package com.catfish.ums.controller;

import com.catfish.ums.entity.domain.UmsOrganization;
import com.catfish.ums.entity.dto.MenuPermissionDTO;
import com.catfish.ums.entity.dto.UmsUserLoginDTO;
import com.catfish.ums.entity.domain.UmsResource;
import com.catfish.ums.entity.domain.UmsRole;
import com.catfish.ums.entity.vo.UserInfo;
import com.catfish.ums.service.UmsOrganizationService;
import com.catfish.ums.service.UmsUserService;
import com.catfish.common.security.config.properties.SecurityProperties;
import com.catfish.common.security.entity.model.UmsPermission;
import com.catfish.common.security.entity.model.UmsUser;
import com.hisaige.dbcore.controller.BaseController;
import com.hisaige.dbcore.entity.dto.PageReq;
import com.hisaige.web.core.entity.domain.AjaxExceptionRes;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import com.hisaige.web.core.util.StringUtils;
import com.hisaige.web.core.valid.AddGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户操作
 * @author chenyj
 * 2020/5/23 - 18:53.
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "UmsUserController", description = "用户管理")
public class UmsUserController extends BaseController<UmsUserService, UmsUser> {

    private static final Logger logger = LoggerFactory.getLogger(UmsUserController.class);

    @Autowired
    private UmsUserService umsUserService;

    @Autowired
    private UmsOrganizationService umsOrganizationService;

    @Autowired
    private SecurityProperties securityProperties;

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMessageRes<Object> login(@RequestBody @Valid UmsUserLoginDTO umsUserLoginDTO, BindingResult result) throws Exception {
        String token = service.login(umsUserLoginDTO.getUsername(), umsUserLoginDTO.getPassword());
        if (token == null) {
            return new AjaxExceptionRes(ReturnCodeEnum.LOGIN_FAILED);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", securityProperties.getTokenHead());

        return R.ok(tokenMap);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessageRes<UserInfo> info() throws Exception {
        return R.ok(service.getUserInfo());
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public AjaxMessageRes<Object> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(securityProperties.getTokenHeader());
        String refreshToken = service.refreshToken(token);
        if (refreshToken == null) {
            return new AjaxExceptionRes(ReturnCodeEnum.TOKEN_EXPIRED);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", securityProperties.getTokenHead());
        return new AjaxMessageRes<>(tokenMap);
    }


    @PostMapping("/role/add/{userId}")
    @ApiOperation("为用户设置角色")
    public AjaxMessageRes<Integer> addUserRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) throws Exception {

        return new AjaxMessageRes<>(umsUserService.addRoles(userId, roleIds));
    }

    @PostMapping("/resource/add/{userId}")
    @ApiOperation("为用户设置资源")
    public AjaxMessageRes<Integer> addUserResource(@PathVariable("userId") String userId, @RequestBody List<String> resourceIds) throws Exception {

        return new AjaxMessageRes<>(umsUserService.addResources(userId, resourceIds));
    }

    @PostMapping("/permission/add/{userId}")
    @ApiOperation("为用户设置权限")
    public AjaxMessageRes<Integer> addUserPermission(@PathVariable("userId") String userId, @RequestBody MenuPermissionDTO menuPermissionDTO) throws Exception {

        return new AjaxMessageRes<>(umsUserService.addPermissions(userId, menuPermissionDTO.getResourceIds(), menuPermissionDTO.getStatus()));
    }

    @GetMapping("/menu/get/{userId}")
    @ApiOperation("获取用户拥有的菜单")
    public AjaxMessageRes<List<UmsResource>> getUserMenus(@PathVariable("userId") String userId) throws Exception {
        return new AjaxMessageRes<>(umsUserService.getUserResources(userId));
    }

    //获取用户拥有的资源
    @GetMapping("/resource/get/{userId}")
    @ApiOperation("获取用户拥有的资源")
    public AjaxMessageRes<List<UmsResource>> getUserResources(@PathVariable("userId") String userId) throws Exception {

        return new AjaxMessageRes<>(umsUserService.getUserResources(userId));
    }

    @GetMapping("/role/get/{userId}")
    @ApiOperation("获取用户拥有的角色")
    public AjaxMessageRes<List<UmsRole>> getUserRoles(@PathVariable("userId") String userId) throws Exception {

        return new AjaxMessageRes<>(umsUserService.getRoles(userId));
    }

    //获取用户拥有的权限,这里需要注意用户的直属权限以及用户的角色所拥有的权限，如果冲突则以用户的直属权限为主
    @GetMapping("/permission/get/{userId}")
    @ApiOperation("获取用户拥有的权限")
    public AjaxMessageRes<List<UmsPermission>> getUserPermissions(@PathVariable("userId") String userId) throws Exception {

        return new AjaxMessageRes<>(umsUserService.getUserPermissions(userId));
    }

    @GetMapping("/search")
    @ResponseBody
    @ApiOperation("根据实体条件检索")
    @Override
    public AjaxMessageRes<Object> search(PageReq pageDTO, UmsUser record) throws Exception {
        if("".equals(record.getUsername())){
            record.setUsername(null);
        }
        if("".equals(record.getPhone())){
            record.setPhone(null);
        }
        if("".equals(record.getEmail())){
            record.setEmail(null);
        }
        if(StringUtils.isEmpty(record.getOrgId())) {
            UmsOrganization userRootNode = umsOrganizationService.getUserRootNode();
            record.setOrgId(null == userRootNode ? null : userRootNode.getId());
        }
        return super.search(pageDTO, record);
    }

    @PostMapping("/create")
    @ResponseBody
    @ApiOperation("根据实体创建")
    @Override
    public AjaxMessageRes<Object> create(@RequestBody @Validated({AddGroup.class}) UmsUser umsUser) throws Exception {

        return new AjaxMessageRes<>(service.save(umsUser));
    }

    @PostMapping("/register")
    @ResponseBody
    @ApiOperation("用户注册，同create")
    public AjaxMessageRes<Object> regist(@RequestBody @Validated({AddGroup.class}) UmsUser umsUser) throws Exception {

        // todo 密码考虑使用非对称加密，将公钥给前端保存，注册用户或用户登录时使用公钥加密密码后台使用私钥解密出密码
        return new AjaxMessageRes<>(service.save(umsUser));
    }

//    @PostMapping("/creates")
//    @ResponseBody
//    @ApiOperation("根据实体批量创建--不支持")
//    @Override
//    public AjaxMessageRes<Integer> create(@RequestBody @Valid List<UmsUser> records) {
//        return new AjaxExceptionRes(ReturnCodeEnum.SERVER_EXCEPTION);
//    public AjaxMessageRes<Object> create(@RequestBody @Valid List<UmsUser> records) {
//        return new AjaxExceptionRes(new RequestRejectedException("method reject"));
//    }

//    @Override
//    @PostMapping("/update")
//    @ResponseBody
//    @ApiOperation("根据实体更新")
//    public AjaxMessageRes<Object> updateByPrimaryKeySelective(@RequestBody @Validated({EditGroup.class}) UmsUser umsUser) {
//        if(!StringUtils.isEmpty(umsUser)){
//            //用户名不允许修改
//            umsUser.setUserName(null);
//        }
//        return new AjaxMessageRes<>(service.updateByPrimaryKeySelective(umsUser));
//    }
}
