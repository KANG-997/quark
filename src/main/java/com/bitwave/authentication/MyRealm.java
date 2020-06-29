package com.bitwave.authentication;


import com.bitwave.entity.SysUser;
import com.bitwave.manager.UserManager;
import com.bitwave.mapper.UserMapper;
import com.bitwave.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getPrincipal();
        String username = JWTUtil.getUsername(token);
        SysUser user = userService.selectUserByUsername(username);
        if (StringUtils.isBlank(username)) {
            throw  new AuthenticationException("token 校验不通过");
        }
        if (user == null){
            throw new AuthenticationException("用户名或密码错误");
        }
        if (!JWTUtil.verify(token,username,user.getPassword())) {
            throw  new AuthenticationException("token 校验不通过");
        }
        return new SimpleAuthenticationInfo(token,token,"MyRealm");
    }
}
