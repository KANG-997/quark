package com.bitwave.enums;

import lombok.Getter;

@Getter
public enum LogCode {

    UNDEFINE(-1,"未定义操作类型"),
    SELECT(0,"查询"),
    INSERT(1,"插入"),
    UPDATE(2,"更新"),
    DELETE(3,"删除");

    LogCode(Integer code,String operator){
        this.code = code;
        this.operator = operator;
    }

    private Integer code;
    private String operator;
}
