package com.bitwave.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bitwave.entity.SysUser;
import com.bitwave.mapper.UserMapper;
import com.bitwave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUser selectUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username));
    }
}
