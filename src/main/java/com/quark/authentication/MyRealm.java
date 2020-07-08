package com.quark.authentication;

import com.quark.entity.SysMenu;
import com.quark.entity.SysRole;
import com.quark.entity.SysUser;
import com.quark.service.SysLogService;
import com.quark.service.SysRoleMenuService;
import com.quark.service.SysUserRoleService;
import com.quark.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        SysUser user = sysUserService.findUserByUsername(username);
        List<SysMenu> userPerms = sysRoleMenuService.findSysMenuByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(userPerms.stream().map(SysMenu::getPerms).collect(Collectors.toSet()));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String username = JwtUtil.getUsername(token);
        if (username == null){
            throw new AuthenticationException("TOKEN INVALID");
        }
        SysUser user = sysUserService.findUserByUsername(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在");
        }
        if (JwtUtil.verify(token,username,user.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        return new SimpleAuthenticationInfo(token,token,"REALM");
    }
}
