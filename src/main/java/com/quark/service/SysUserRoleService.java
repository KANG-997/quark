package com.quark.service;

import com.quark.entity.SysRole;
import com.quark.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService {

    List<SysRole> findUserRoleByUser(String username);


}
