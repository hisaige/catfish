package com.catfish.common.security.util;

import com.catfish.common.core.entity.CatfishConstants;
import com.hisaige.web.core.util.RequestUtils;
import com.hisaige.web.core.util.StringUtils;

/**
 * @author chenyj
 * @version 1.0
 * @date 2020/12/30$ - 0:00$
 */
public class SystemUserUtils {

    /**
     * 获取当前用户id
     * @return String
     */
    public static String getUserId(){
        return RequestUtils.getHeadOrAttribute(CatfishConstants.USER_ID);
    }

    /**
     * 获取当前用户id
     * @return String
     */
    public static String getUserIp(){
        String ipAddr = RequestUtils.getHeadOrAttribute(CatfishConstants.USER_IP);
        if(StringUtils.isEmpty(ipAddr)){
            ipAddr = RequestUtils.getReqIp();
        }
        return ipAddr;
    }

    /**
     * 获取当前用户id
     * @return String
     */
    public static String getUserOrg(){
        return RequestUtils.getHeadOrAttribute(CatfishConstants.USER_ORG);
    }

    /**
     * 获取用户名，注意配合实际项目使用
     * @return String
     */
    public static String getReqUserName(){
        return RequestUtils.getHeadOrAttribute(CatfishConstants.USERNAME);
    }
}
