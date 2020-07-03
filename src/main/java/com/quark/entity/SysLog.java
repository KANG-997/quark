package com.quark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysLog)实体类
 *
 * @author kangkai
 * @since 2020-06-30 11:01:29
 */

@Data
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 993843248614078860L;
    /**
    * 主键ID
    */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 用户名
    */
    private String username;
    /**
    * 操作类型
    */
    private String action;
    /**
    * 耗时
    */
    private Long timeCost;
    /**
    * 调用方法
    */
    private String method;
    /**
    * 参数
    */
    private String params;
    /**
    * ip地址
    */
    private String ip;
    /**
    * 操作时间
    */
    private Date createTime;
    /**
    * 地址
    */
    private String location;



}