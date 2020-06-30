package com.quark.authentication;

import com.quark.entity.SysRole;
import com.quark.entity.SysUser;
import com.quark.service.SysUserRoleService;
import com.quark.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleSet = sysUserRoleService.findUserRoleByUser(username).stream().map(SysRole::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String credentials = (String) token.getCredentials();
        String username = JwtUtil.getUsername(credentials);
        if (StringUtils.isEmpty(username)){
            throw new AuthenticationException("token已失效");
        }
        SysUser user = sysUserService.findUserByUsername(username);
        if (user == null) {
            throw  new AuthenticationException("token校验不通过");
        }
        if (!JwtUtil.verify(credentials,username,user.getPassword())) {
            throw new AuthenticationException("token验证失败");
        }

        return new SimpleAuthenticationInfo(credentials,credentials,"myRealm");
    }
}
