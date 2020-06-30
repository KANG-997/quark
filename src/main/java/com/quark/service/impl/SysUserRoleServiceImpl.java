package com.quark.service.impl;

import com.quark.entity.SysRole;
import com.quark.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {


    @Override
    public List<SysRole> findUserRoleByUser(String username) {
        return null;
    }
}
