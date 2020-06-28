package com.bitwave.enums;

import lombok.Getter;

@Getter
public enum ResultCode {

    /**
     * 通用结果
     */
    SUCCESS(100000,"操作成功"),
    FAILED(100002,"操作失败");


    private int  code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
