package com.quark.common;


import com.quark.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID = 2591481969566415959L;

    private Integer code;

    private String msg;

    private T data;

    public ResultResponse(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ResultResponse(ResultCode resultCode,T data){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public static<T> ResultResponse success(ResultCode resultCode,T data){
        return new ResultResponse(resultCode,data);
    }

    public static ResultResponse success(ResultCode resultCode){
        return new ResultResponse(resultCode);
    }

    public static ResultResponse failed(ResultCode resultCode){
        return new ResultResponse(resultCode);
    }

}
