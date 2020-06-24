package com.bitwave.utils;

import com.bitwave.entity.SysUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@ConfigurationProperties(prefix = "jwt")
public class JWTUtil {

    private static final String KEY_PREFIX = "admin.token:";

    @Value("${secret}")
    private String secret;
    @Value("${expiration}")
    private Long expiration;
    @Value("${tokenPrefix}")
    private String tokenPrefix;

    private String generateToken(Map<String, Object>claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    private Claims getClaimsFormToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JWT验证失败:{};异常:{}",token,e.getMessage());
        }
        return claims;
    }

    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private boolean isTokenExpired(String token){
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    private Date getExpiredDateFromToken(String token){
        Claims claims = getClaimsFormToken(token);
        return claims.getExpiration();
    }


    public String getUserNameFormToken(String token){
        String username;
        try {
            Claims claims = getClaimsFormToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
           username = null;
           log.error("获取用户名失败:{};异常:{}",username,e.getMessage());
        }
        return username;
    }

    public boolean validateToken(String token, SysUser user){
        String username = getUserNameFormToken(token);
        return username.equals(user.getName());
    }






}
