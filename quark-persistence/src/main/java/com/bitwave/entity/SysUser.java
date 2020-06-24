package com.bitwave.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2020-06-23 16:37:11
 */

@Getter
@Setter
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = -48537488874498249L;
    
    private Integer id;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 显示名称
    */
    private String name;
    /**
    * 创建日期
    */

    private Date createdate;
    /**
    * 更新日期
    */
    private Date updatedate;
    /**
    * 状态码（0:有效 1:冻结  2:永久禁用）
    */
    private Short state;


}