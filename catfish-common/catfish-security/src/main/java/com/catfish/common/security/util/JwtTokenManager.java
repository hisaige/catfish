package com.catfish.common.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.catfish.common.security.config.properties.SecurityProperties;
import com.catfish.common.security.entity.model.UmsUser;
import com.hisaige.web.core.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * Created by macro on 2018/4/26.
 */
public class JwtTokenManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenManager.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_USERID= "userId";
    private static final String CLAIM_KEY_ORGID = "orgId";
    private static final String CLAIM_KEY_CREATED = "created";

    private SecurityProperties securityProperties;

//    private ThreadLocal<Claims> claimsContextHolder = new ThreadLocal<>();

    public JwtTokenManager(SecurityProperties securityProperties){
        this.securityProperties = securityProperties;
    }

    /**
     * 生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, securityProperties.getSecret())
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(securityProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.error("token parse failed... unexpected token:" + token, e);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + securityProperties.getExpiration() * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取登录用户id
     */
    public String getUserIdFromToken(String token) {
        String userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = MapUtil.getStr(claims, CLAIM_KEY_USERID);
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    public UmsUser getUserFromToken(String token) {
        UmsUser umsUser = new UmsUser();
        try {
            Claims claims = getClaimsFromToken(token);
            umsUser.setId(MapUtil.getStr(claims, CLAIM_KEY_USERID));
            umsUser.setUsername(MapUtil.getStr(claims, CLAIM_KEY_USERNAME));
            umsUser.setOrgId(MapUtil.getLong(claims, CLAIM_KEY_ORGID));
        } catch (Exception ignored) {
        }
        return umsUser;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param validateUser 待验证的用户名
     */
    public boolean validateToken(String token, String validateUser) {
        String username;
        Date expiredDate;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
            expiredDate = claims.getExpiration();
        } catch (Exception e) {
            return false;
        }
        return username.equals(validateUser) && !expiredDate.before(new Date());
    }

    /**
     * 判断token是否已经失效
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return null !=expiredDate && expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据用户信息生成token
     * @param userName 用户名
     * @param userId 用户id
     * @param orgId 用户所在的组织名
     * @return token
     */
    public String generateToken(String userName, String userId, Long orgId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userName);
        claims.put(CLAIM_KEY_USERID, userId);
        claims.put(CLAIM_KEY_ORGID, orgId);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     * @param oldToken 带tokenHead的token
     */
    public String refreshHeadToken(String oldToken) {
        if(StringUtils.isEmpty(oldToken)){
            return null;
        }
        String token = oldToken.substring(securityProperties.getTokenHead().length());
        if(StringUtils.isEmpty(token)){
            return null;
        }
        //token校验不通过
        Claims claims = getClaimsFromToken(token);
        if(claims==null){
            return null;
        }
        //如果token已经过期，不支持刷新
        if(isTokenExpired(token)){
            return null;
        }
        //如果token在一定内刚刷新过，返回原token
        if(tokenRefreshJustBefore(token, securityProperties.getRefreshInterval())){
            return token;
        }else{
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if(refreshDate.after(created)&&refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }
}
