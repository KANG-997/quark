package com.bitwave.utils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.bitwave.entity.SysUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
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

    private String tokenFilter(String token){
        if (token.startsWith("Bearer")) {
            token = token.replace("Bearer","").trim();
            return token;
        }
        return token.trim();
    }

    private boolean tokenRefreshJustBefore(String token,int time){
        Claims claims = getClaimsFormToken(token);
        Date create = claims.get("createTime",Date.class);
        Date refresh = new Date();
        if (refresh.after(create)&&refresh.before(DateUtil.offsetSecond(create,time))) {
            return true;
        }
        return false;
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

    public String generateToken(SysUser user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        claims.put("createTime",new Date());
        return generateToken(claims);
    }

    public String refreshHeadToken(String oldToken){
        if (StringUtils.isEmpty(oldToken)) {
            return null;
        }
        String token = oldToken.substring(tokenPrefix.length());
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = getClaimsFormToken(token);
        if (claims == null) {
            return null;
        }
        if (isTokenExpired(token)) {
            return null;
        }
        if (tokenRefreshJustBefore(token,30 * 60)){
            return token;
        }else{
            claims.put("createTime",new Date());
            return generateToken(claims);
        }
    }

    public boolean verify(String token,String username){
        token = tokenFilter(token);
        try {
            Key key = new SecretKeySpec(token.getBytes(), SignatureAlgorithm.HS512.getJcaName());
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            String tokenUsername = claimsJws.getBody().getSubject();
            if (!tokenUsername.equals(username)) {
                log.info("用户名不匹配");
                return false;
            }
        } catch (Exception e) {
            log.error("验证失败");
            return false;
        }
        return true;
    }
}
