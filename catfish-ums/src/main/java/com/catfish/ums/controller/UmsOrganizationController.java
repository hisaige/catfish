package com.catfish.ums.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.hisaige.dbcore.controller.BaseController;
import com.catfish.ums.entity.UmsOrganization;
import com.catfish.ums.service.impl.UmsOrganizationServiceImpl;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenyj
 * @since 2021-01-25
 */
@RestController
@RequestMapping("/ums/ums-organization")
public class UmsOrganizationController extends BaseController<UmsOrganizationServiceImpl, UmsOrganization> {

}

