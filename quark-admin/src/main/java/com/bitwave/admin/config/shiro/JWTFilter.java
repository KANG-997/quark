package com.bitwave.admin.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest)request;
        String authorization = req.getHeader("Authorization");
        return authorization != null;
    }

    protected boolean executeLogin(ServletRequest request,ServletResponse response) throws  Exception{
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        JwtToken token = new JwtToken(authorization);
        getSubject(request,response).login(token);
        return true;
    }
}
