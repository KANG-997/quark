package com.quark.exception;

import com.quark.common.ResultResponse;
import com.quark.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandle {

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public ResultResponse error(NullPointerException e){
        e.printStackTrace();
        log.error("空指针异常:{}",e.getMessage());
        return ResultResponse.failed(ResultCode.FAILED);
    }
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResultResponse error(AuthenticationException e){
        e.printStackTrace();
        log.error("认证异常:{}",e.getMessage());
        return ResultResponse.failed(ResultCode.FAILED);
    }
}
