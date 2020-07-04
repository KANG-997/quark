package com.quark.service.impl;

import com.quark.entity.SysRole;
import com.quark.mapper.SysUserRoleMapper;
import com.quark.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysRole> findUserRoleByUser(String username) {
        return null;
    }

    @Override
    public Set<Integer> findUserRoleIdByUserId(String userId) {
        return null;
    }
}
