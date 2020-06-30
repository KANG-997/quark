package com.quark.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysLoginLog)实体类
 *
 * @author kangkai
 * @since 2020-06-30 11:01:06
 */
@Data
public class SysLoginLog implements Serializable {
    private static final long serialVersionUID = -42153962821944528L;
    /**
    * 用户名
    */
    private String username;
    /**
    * 登录时间
    */
    private Date loginTme;
    /**
    * 地点
    */
    private String location;
    /**
    * IP
    */
    private String ip;

}