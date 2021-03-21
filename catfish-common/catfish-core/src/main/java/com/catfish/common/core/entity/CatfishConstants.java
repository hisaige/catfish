package com.catfish.common.core.entity;

/**
 * 常量
 * @author chenyj
 * @version 1.0
 * @date 2021/1/1$ - 16:07$
 */
public interface CatfishConstants {

    String USER_ID = "userId";
    String USERNAME = "username";
    String USER_IP = "userIp";
    String USER_ORG = "orgId";
    String USER_TOKEN = "token";

    //缓存到redis中的用户前缀
    String TOKEN_HEAD = "ums_tokens:";

    String CACHED_REQUEST_BODY_OBJECT_KEY = "cached_request_body_object_key";

    /**
     * OSS
     */
    String ALIYUN_ENDPIONT = "https://catfish01.oss-cn-shenzhen.aliyuncs.com/";

    //特殊符号
    String SLASH = "/";

}
