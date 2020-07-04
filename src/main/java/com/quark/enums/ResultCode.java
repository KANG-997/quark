package com.quark.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(10000,"success"),
    FAILED(10001,"failed"),

    /**
     * 用户相关 20000开始
     */
    LOGIN_USER_NOT_EXISTS(20000,"用户信息不存在，请核对用户信息！"),
    LOGIN_USER_WRONG_PASSWORD(20001,"用户名或密码错误"),
    LOGIN_USER_STATUS_ABNORMAL(20002,"用户状态异常,请联系管理员！"),
    LOGIN_ERROR_AUTHORIZATION(20003,"用户无操作权限,请联系管理员！"),
    LOGIN_ERROR_AUTHENTICATION(20004,"用户未认证，请登录后再操作！"),
    LOGIN_SUCCESS(20001,"login_success");


    private Integer code;
    private String msg;

    ResultCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
