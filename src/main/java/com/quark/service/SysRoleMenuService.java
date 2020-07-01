package com.quark.service;

import com.quark.entity.SysMenu;

import java.util.List;

public interface SysRoleMenuService {

    List<SysMenu> findSysMenuByUsername(String username);

}
