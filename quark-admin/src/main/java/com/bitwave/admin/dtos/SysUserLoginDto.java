package com.bitwave.admin.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserLoginDto implements Serializable {

    private String username;
    private String password;
    private String verifyCode;
}
