package com.bitwave.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Author: kangk
 * @Date: 2020/6/29 9:23
 * @Description: JWT工具类
 */

@Slf4j
public class JWTUtil {

    /**
     * token默认失效时间
     */
    private static final Long TOKEN_EXPIRE = 36 * 1000L;


    /**
     * 生成token
     * @param username
     * @param secret
     * @return token
     */
    public static String sign(String username,String secret){
        try {
            username = StringUtils.lowerCase(username);
            Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证用户token
     * @param token
     * @param username
     * @param secret
     * @return boolean
     */
    public static boolean verify(String token,String username,String secret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",username )
                    .build();
            verifier.verify(token);
            log.info("token verified!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("token is invalid{}",e.getMessage());
            return false;
        }
    }

    /**
     * 获取用户名
     * @param token
     * @return username
     */
    public static String getUsername(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error("error：{}",e.getMessage());
            return null;
        }
    }

}
