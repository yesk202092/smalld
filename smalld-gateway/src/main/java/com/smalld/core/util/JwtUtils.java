package com.smalld.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smalld.common.exception.CommonException;
import com.smalld.common.pojo.UserToken;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    /**
     * 签发对象：这个用户的id
     * 签发时间：现在
     * 有效时间：30分钟
     * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
     * 加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(UserToken userToken) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30);
        Date expiresDate = nowTime.getTime();

        //返回一个token
        return JWT.create().withAudience(userToken.getUserNo())   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .withClaim("userNo", userToken.getUserNo())
                .withClaim("userName", userToken.getName())
                .withClaim("realName", userToken.getRealName())
                .withClaim("source", userToken.getSource())
                .sign(Algorithm.HMAC256(userToken.getUserNo() + "HelloLehr"));   //加密
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     *
     * @param token
     * @throws CommonException
     */
    public static void verifyToken(String token, String secret) throws CommonException {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret + "HelloLehr")).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            throw new CommonException();
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
            throw new CommonException();
        }
        return audience;
    }


    /**
     * 通过载荷名字获取载荷的值
     */
    public static UserToken getClaimByName(String token) {
        Map<String, Claim> map = JWT.decode(token).getClaims();

        UserToken userToken = new UserToken();
        userToken.setUserNo(map.get("userNo").asString());
        return userToken;
    }

}
