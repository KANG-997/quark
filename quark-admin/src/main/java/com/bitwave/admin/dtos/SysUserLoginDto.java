package com.bitwave.admin.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserLoginDto implements Serializable {
    private static final long serialVersionUID = 8513600414791916979L;
    private String username;
    private String password;
    private String verifyCode;
}
