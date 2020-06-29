package com.bitwave.authentication;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JWTToken implements AuthenticationToken {

    private static final long serialVersionUID = -1446479255906388350L;

    private String token;

    private String expire;


    public JWTToken(String token) {
        this.token = token;
    }

    public JWTToken(String token, String expire) {
        this.token = token;
        this.expire = expire;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
