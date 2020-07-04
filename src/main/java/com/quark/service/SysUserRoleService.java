package com.quark.service;

import com.quark.entity.SysRole;
import com.quark.entity.SysUserRole;

import java.util.List;
import java.util.Set;

public interface SysUserRoleService {

    List<SysRole> findUserRoleByUser(String username);

    Set<Integer> findUserRoleIdByUserId(String userId);


}
