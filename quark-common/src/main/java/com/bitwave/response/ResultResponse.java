package com.bitwave.response;

import com.bitwave.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID = -3625066231651514252L;

    private int code;

    private String msg;

    private T data;

    private ResultResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private  ResultResponse(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultResponse success(ResultCode resultCode){
        return new ResultResponse(resultCode.getCode(),resultCode.getMsg());
    }

    public static ResultResponse failed(ResultCode resultCode){
        return new ResultResponse(resultCode.getCode(),resultCode.getMsg());
    }

    public static<T> ResultResponse success(ResultCode resultCode,T data){
        return new ResultResponse(resultCode.getCode(),resultCode.getMsg(),data);
    }

}
