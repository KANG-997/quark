package com.quark.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(10000,"success"),
    FAILED(10001,"failed");


    private Integer code;
    private String msg;

    ResultCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
