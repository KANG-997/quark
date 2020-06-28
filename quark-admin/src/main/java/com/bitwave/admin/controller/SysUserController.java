package com.bitwave.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bitwave.admin.dtos.SysUserLoginDto;
import com.bitwave.admin.service.SysUserService;
import com.bitwave.entity.SysUser;
import com.bitwave.enums.ResultCode;
import com.bitwave.response.ResultResponse;
import com.bitwave.utils.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private JWTUtil jwtUtil;


    @PostMapping("/login")
    private ResultResponse sysUserLogin(SysUserLoginDto sysUserLoginDto){
        SysUser user = sysUserService.getUserByUserName(sysUserLoginDto.getUsername());
        if (user == null) {
            return ResultResponse.failed(ResultCode.FAILED);
        }
        if (!user.getPassword().equals(sysUserLoginDto.getPassword())) {
            return ResultResponse.failed(ResultCode.FAILED);
        }
        String token = jwtUtil.generateToken(user);
        if (token == null && StringUtils.isBlank(token)) {
            return ResultResponse.failed(ResultCode.FAILED);
        }
        return ResultResponse.success(ResultCode.SUCCESS);
    }


}
