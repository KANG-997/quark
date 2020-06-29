package com.bitwave.service;

import com.bitwave.entity.SysUser;

public interface UserService {

    SysUser selectUserByUsername(String username);


}
