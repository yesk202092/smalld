package com.smalld.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smalld.common.exception.CommonException;
import com.smalld.common.pojo.AdminUserToken;
import com.smalld.common.pojo.UserToken;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
/**
 * @author yesk
 */
public class JwtUtils {

    private final static String SECRET = "secret";


    /**
     * 签发对象：这个用户的id
     * 签发时间：现在
     * 有效时间：30分钟
     * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
     * 加密密钥：这个人的id加上一串字符串
     * 通过source字段判断是否隔离
     */
    public static String createToken(UserToken userToken) {
        if (StringUtils.isBlank(userToken.getSource())) {
            //不传或者为空则为默认值
            userToken.setSource("WEB");
        }
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 120);
        Date expiresDate = nowTime.getTime();
        //返回一个token
        return JWT.create().withAudience(userToken.getUserNo())   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .withClaim("userNo", userToken.getUserNo())
                .withClaim("userId", userToken.getUserId())
                .withClaim("userName", userToken.getName())
                .withClaim("realName", userToken.getRealName())
                .withClaim("source", userToken.getSource())
                .sign(Algorithm.HMAC256(userToken.getSource() + SECRET));   //加密
    }

    /**
     * 签发对象：这个用户的id
     * 签发时间：现在
     * 有效时间：30分钟
     * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
     * 加密密钥：这个人的id加上一串字符串
     * 通过source字段判断是否隔离
     */
    public static String createAdminToken(AdminUserToken userToken) {
        if (StringUtils.isBlank(userToken.getTenant())) {
            //不传或者为空则为默认值
            userToken.setTenant("ADMIN");;
        }
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 120);
        Date expiresDate = nowTime.getTime();
        //返回一个token
        return JWT.create().withAudience(userToken.getUserNo())   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .withClaim("userNo", userToken.getUserNo())
                .withClaim("userId", userToken.getUserId())
                .withClaim("userName", userToken.getUserName())
                .withClaim("realName", userToken.getRealName())
                .withClaim("tenant", userToken.getTenant())
                .withClaim("email", userToken.getEmail())
                .sign(Algorithm.HMAC256(userToken.getTenant() + SECRET));   //加密
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     *
     * @param token
     * @throws CommonException
     */
    public static void verifyToken(String token, String source) throws CommonException {
        if (StringUtils.isBlank(source)) {
            //不传或者为空则为默认值
            source = "WEB";
        }
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(source + SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            throw new CommonException("非法token");
        }
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     *
     * @param token
     * @param tenant 是否存在租户
     * @throws CommonException
     */
    public static void verifyAdminToken(String token, String tenant) throws CommonException {
        if (StringUtils.isBlank(tenant)) {
            //不传或者为空则为默认值
            tenant = "ADMIN";
        }
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(tenant + SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            throw new CommonException("非法token");
        }
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) throws CommonException {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new CommonException("token解析异常");
        }
        return audience;
    }


    /**
     * 通过载荷名字获取载荷的值
     */
    public static UserToken getUserToken(String token) {
        Map<String, Claim> map = JWT.decode(token).getClaims();

        UserToken userToken = new UserToken();
        userToken.setUserNo(map.get("userNo").asString());
        return userToken;
    }

    /**
     * 通过载荷名字获取载荷的值
     */
    public static AdminUserToken getAdminUserToken(String token) {
        Map<String, Claim> map = JWT.decode(token).getClaims();

        AdminUserToken userToken = new AdminUserToken();
        if (ObjectUtils.isNotEmpty(map.get("userNo"))) {
            userToken.setUserNo(map.get("userNo").asString());
        }
        if (ObjectUtils.isNotEmpty(map.get("userId"))) {
            userToken.setUserId(map.get("userId").asLong());
        }
        if (ObjectUtils.isNotEmpty(map.get("userName"))) {
            userToken.setUserName(map.get("userName").asString());
        }
        if (ObjectUtils.isNotEmpty(map.get("realName"))) {
            userToken.setRealName(map.get("realName").asString());
        }
        if (ObjectUtils.isNotEmpty(map.get("tenant"))) {
            userToken.setTenant(map.get("tenant").asString());
        }
        if (ObjectUtils.isNotEmpty(map.get("email"))) {
            userToken.setEmail(map.get("email").asString());
        }
        return userToken;
    }


}
