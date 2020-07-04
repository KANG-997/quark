package com.quark.authentication;

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

@Slf4j
public class JwtUtil {

    private static final long EXPIRE_TIME = 60 * 60 * 1000L;

    public static String sign(String username,String secret){
        try {
            username = StringUtils.lowerCase(username);
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("生成token失败：{}",e.getMessage());
            return null;
        }
    }

    public static boolean verify(String token,String username,String secret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("token:{},验证token失败:{}",token,e.getMessage());
            return false;
        }
    }

    public static String getUsername(String token){
        try {
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            log.error("token:{},获取用户名失败：{}",token,e.getMessage());
            return null;
        }
    }

    public static String subToken(String token){
        if (token != null && token.startsWith("Bearer")) {
            token = token.replace("Bearer","").trim();
            return token;
        }
        return "";
    }
}
