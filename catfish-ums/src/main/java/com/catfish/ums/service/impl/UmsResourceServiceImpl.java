package com.catfish.ums.service.impl;

import com.catfish.ums.entity.domain.UmsResource;
import com.catfish.ums.mapper.UmsResourceMapper;
import com.catfish.ums.service.UmsResourceService;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户资源服务
 * @author chenyj
 * 2020/5/24 - 10:01.
 **/
@Service
public class UmsResourceServiceImpl extends BaseServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
}
