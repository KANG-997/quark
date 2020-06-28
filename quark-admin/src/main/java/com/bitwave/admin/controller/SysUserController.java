package com.bitwave.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bitwave.admin.anno.LogInfo;
import com.bitwave.admin.dtos.SysUserLoginDto;
import com.bitwave.admin.service.SysUserService;
import com.bitwave.admin.utils.JWTUtil;
import com.bitwave.entity.SysUser;
import com.bitwave.enums.LogCode;
import com.bitwave.enums.ResultCode;
import com.bitwave.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JWTUtil jwtUtil;


    @LogInfo(value = "用户登录",operatorType = LogCode.SELECT)
    @PostMapping("/login")
    public ResultResponse sysUserLogin(@RequestBody SysUserLoginDto sysUserLoginDto){
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
        Map<String,String> data = new HashMap<>();
        data.put("token",token);
        return ResultResponse.success(ResultCode.SUCCESS,data);
    }


}
