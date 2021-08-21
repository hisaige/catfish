package com.catfish.ums.mapstruct;

import com.catfish.common.security.access.UmsUserDetails;
import com.catfish.ums.entity.vo.LoginUserInfo;
import com.hisaige.web.core.mapstruct.BaseMapStruct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 在@Mapper注解中可以添加 uses = EntityObjFactory.class管理bean
 * @author chenyj
 * @version 1.0
 * @date 2021/1/10$ - 23:27$
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDetail2LoginUserMapStruct extends BaseMapStruct<LoginUserInfo, UmsUserDetails> {
    UserDetail2LoginUserMapStruct INSTANCE = Mappers.getMapper(UserDetail2LoginUserMapStruct.class);
}
