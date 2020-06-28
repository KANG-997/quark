package com.bitwave.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysLogs)实体类
 *
 * @author makejava
 * @since 2020-06-28 13:48:54
 */
@Getter
@Setter
@TableName("sys_logs")
public class SysLogs implements Serializable {
    private static final long serialVersionUID = -71508670459266081L;
    /**
    * 主键ID
    */
    private Integer id;
    /**
    * 请求路径
    */
    private String requesturi;
    /**
    * 请求方法
    */
    private String requestmethod;
    /**
    * 请求内容类型
    */
    private String requestcontenttype;
    /**
    * 操作类型
    */
    private String operatortype;
    /**
    * 请求日期
    */
    private Date requestdate;
    /**
    * 用户IP
    */
    private String remotehost;
    /**
    * 用户
    */
    private String requestuser;


}