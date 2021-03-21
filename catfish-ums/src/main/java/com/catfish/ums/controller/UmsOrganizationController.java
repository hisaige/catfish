package com.catfish.ums.controller;


import com.hisaige.web.core.entity.domain.AjaxMessageRes;
import com.hisaige.web.core.entity.domain.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.hisaige.dbcore.controller.BaseController;
import com.catfish.ums.entity.domain.UmsOrganization;
import com.catfish.ums.service.impl.UmsOrganizationServiceImpl;


/**
 * <p>
 *  组织树
 * </p>
 * @author chenyj
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/organization")
public class UmsOrganizationController extends BaseController<UmsOrganizationServiceImpl, UmsOrganization> {

    @GetMapping("/getRootTree")
    @ApiOperation("获取组织树根节点")
    public AjaxMessageRes<UmsOrganization> getRootTree(Boolean recursion) {
        return R.ok(service.getRootTree(recursion));
    }

    @GetMapping("/getUserRootTree")
    @ApiOperation("获取当前用户所在组织树根节点")
    public AjaxMessageRes<UmsOrganization> getUserRootTree(Boolean recursion) throws Exception {
        return R.ok(service.getUserRootTree(recursion));
    }

}

