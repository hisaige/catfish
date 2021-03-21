package com.catfish.ums.service.impl;

import com.catfish.ums.entity.domain.UmsMenu;
import com.catfish.ums.mapper.UmsMenuMapper;
import com.catfish.ums.service.UmsMenuService;
import com.hisaige.dbcore.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author chenyj
 * 2020/5/23 - 18:54.
 **/
@Service
public class UmsMenuServiceImpl extends BaseServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {
}
