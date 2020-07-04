package com.quark.controller;

import com.quark.annon.LogInfo;
import com.quark.authentication.JwtUtil;
import com.quark.common.ResultResponse;
import com.quark.entity.SysLoginLog;
import com.quark.entity.SysUser;
import com.quark.enums.ResultCode;
import com.quark.service.SysLoginLogService;
import com.quark.service.SysUserService;
import com.quark.utils.MD5Util;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SysUserLoginController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysLoginLogService sysLoginLogService;


    @PostMapping("/login")
    public ResultResponse sysUserLogin(@RequestParam("username")String username,@RequestParam("password")String password){
        SysUser user = sysUserService.findUserByUsername(username);
        if (user == null){
            return ResultResponse.failed(ResultCode.LOGIN_USER_NOT_EXISTS);
        }
        if (!user.getPassword().equals(MD5Util.encrypt(password))) {

            return ResultResponse.failed(ResultCode.LOGIN_USER_WRONG_PASSWORD);
        }
        if (user.getStatus() != 0){
            return ResultResponse.failed(ResultCode.LOGIN_USER_STATUS_ABNORMAL);
        }
        String token = JwtUtil.sign(username, MD5Util.encrypt(password));
        Map<String, Object> result = new LinkedHashMap<>();
        user.setPassword("");
        result.put("userInfo",user);
        result.put("token",token);
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLogService.saveLoginLog(sysLoginLog);
        this.sysUserService.updateLastLoginTime(user.getId());
        return ResultResponse.success(ResultCode.SUCCESS,result);
    }

}
