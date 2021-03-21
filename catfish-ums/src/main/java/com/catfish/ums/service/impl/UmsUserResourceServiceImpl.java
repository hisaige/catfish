package com.catfish.ums.service.impl;

import com.catfish.ums.entity.domain.UmsUserResource;
import com.catfish.ums.mapper.UmsUserResourceMapper;
import com.catfish.ums.service.UmsUserResourceService;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户资源服务
 * @author chenyj
 * 2020/5/24 - 10:01.
 **/
@Service
public class UmsUserResourceServiceImpl extends BaseServiceImpl<UmsUserResourceMapper, UmsUserResource> implements UmsUserResourceService {
}
