package com.bitwave.authentication;

import com.bitwave.enums.ResultCode;
import com.bitwave.response.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private static final String  TOKEN_PREFIX = "Authentication";




    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request,response)) {
            try {
                executeLogin(request,response);
            } catch (Exception e) {
                ResultResponse.failed(ResultCode.FAILED);
            }
        }
        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(TOKEN_PREFIX);
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String token = httpServletRequest.getHeader("Authorization");
            JWTToken jwtToken = new JWTToken(token);
            getSubject(request,response).login(jwtToken);
            return true;
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
