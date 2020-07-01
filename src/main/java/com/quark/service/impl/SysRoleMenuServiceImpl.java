package com.quark.service.impl;

import com.quark.entity.SysMenu;
import com.quark.mapper.SysRoleMenuMapper;
import com.quark.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findSysMenuByUsername(String username) {
        return sysRoleMenuMapper.findUserPermission(username);
    }
}
