package com.quark.service;

import com.quark.entity.SysUser;

public interface SysUserService {

    SysUser findUserByUsername(String username);

    void addUser(SysUser sysUser);

    void updateLastLoginTime(Integer userId);
}
