package com.bitwave.shiro;

import com.bitwave.admin.service.SysUserService;
import com.bitwave.entity.SysUser;
import com.bitwave.enums.ResultCode;
import com.bitwave.response.ResultResponse;
import com.bitwave.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private JWTUtil jwtUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String username = jwtUtil.getUserNameFormToken(principal.toString());
        SysUser user  = sysUserService.getUserByUserName(username);
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getPrincipal();
        String username = jwtUtil.getUserNameFormToken(token);
        SysUser user = sysUserService.getUserByUserName(username);
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException("token 无效");
        }
        if (user == null){
            throw new AuthenticationException("用户不存在或冻结，请联系管理员");
        }
        if (!jwtUtil.verify(token,username)) {
            throw new AuthenticationException("token 验证失败");
        }
        return new SimpleAuthenticationInfo(token,token,"REALM");
    }
}
