package com.quark.exception;

import com.quark.common.ResultResponse;
import com.quark.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandle {

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public ResultResponse error(NullPointerException e){
        log.error("空指针异常:{}",e.getMessage());
        return ResultResponse.failed(ResultCode.FAILED);
    }
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResultResponse error(AuthenticationException e){
        log.error("认证异常:{}",e.getMessage());
        return ResultResponse.failed(ResultCode.FAILED);
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultResponse error(UnauthorizedException e){
        log.error("无权限操作，请联系管理员:{}",e.getMessage());
        e.printStackTrace();
        return ResultResponse.failed(ResultCode.LOGIN_ERROR_AUTHORIZATION);
    }

    @ResponseBody
    @ExceptionHandler(UnauthenticatedException.class)
    public ResultResponse error(UnauthenticatedException e) {
        log.error("无权限操作，请联系管理员:{}", e.getMessage());
        e.printStackTrace();
        return ResultResponse.failed(ResultCode.LOGIN_ERROR_AUTHENTICATION);
    }
}
