package com.quark.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quark.entity.SysUser;
import com.quark.mapper.SysUserMapper;
import com.quark.service.SysUserService;
import com.quark.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;


    @Override
    public SysUser findUserByUsername(String username) {
        return sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public void addUser(SysUser sysUser) {
        sysUser.setPassword(MD5Util.encrypt(sysUser.getPassword()));
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(0);
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void updateLastLoginTime(Integer userId) {
        SysUser user = new SysUser();
        user.setLastLoginTime(new Date());
        this.sysUserMapper.update(user,new QueryWrapper<SysUser>().eq("id",userId));
    }
}
