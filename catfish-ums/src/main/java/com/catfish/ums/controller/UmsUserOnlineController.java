package com.catfish.ums.controller;

import com.catfish.common.security.access.UmsUserDetails;
import com.catfish.ums.entity.dto.OnlineUserDTO;
import com.catfish.ums.entity.vo.LoginUserInfo;
import com.catfish.ums.service.UmsUserService;
import com.hisaige.dbcore.entity.dto.PageReq;
import com.hisaige.dbcore.entity.dto.PageRes;
import com.hisaige.redis.service.RedisService;
import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import com.hisaige.web.core.entity.domain.R;
import com.hisaige.web.core.entity.enums.ReturnCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/10$ - 10:02$
 */
@RestController
@RequestMapping("/user/online")
@Api(tags = "UmsUserOnlineController", description = "在线用户管理")
public class UmsUserOnlineController {

    @Autowired
    private RedisService<UmsUserDetails> redisService;

    @Autowired
    private UmsUserService umsUserService;

    @GetMapping("/list")
    @ApiOperation("在线用户列表")
    public AjaxMessageRes<PageRes<LoginUserInfo>> getOnlineUsers(PageReq pageReq, OnlineUserDTO onlineUserDTO) {//OnlineUserDTO onlineUser,

        List<LoginUserInfo> retList = umsUserService.getOnlineUsers(onlineUserDTO);

        Integer pageNum = pageReq.getPageNum();
        Integer pageSize = pageReq.getPageSize();

        return R.ok(PageRes.restPage(retList, pageNum, pageSize));
    }

    @PostMapping("/logout")
    @ApiOperation("在线用户列表")
    public AjaxMessageRes logout(String tokenPrefix, String username) {//OnlineUserDTO onlineUser,

        if(umsUserService.logout(tokenPrefix, username)) {
            return R.ok(null);
        }
        return R.fail(ReturnCodeEnum.LOGOUT_FAILED);
    }

    @PostMapping("/logout/{token}")
    @ApiOperation("在线用户列表")
    public AjaxMessageRes<Boolean> logout(@PathVariable("token") String token) {//OnlineUserDTO onlineUser,

        return R.ok(umsUserService.logout(token));
    }
}
