package com.quark.authentication;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 2382225420867827606L;

    private String token;

    public JwtToken(String token) {
        this.token = token;
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
