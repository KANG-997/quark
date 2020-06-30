package com.quark.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author kangk
 * @since 2020-06-30 09:58:14
 */

@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = -83410208799861297L;
    /**
    * 主键ID
    */
    private Integer id;
    /**
    * 登录名
    */
    private String username;
    /**
    * 登录密码
    */
    private String password;
    /**
    * 名称
    */
    private String name;
    /**
    * 性别：0男 1女 2保密
    */
    private Integer gender;
    /**
    * 电子邮箱
    */
    private String email;
    /**
    * 电话号码
    */
    private String phone;
    /**
    * 地址
    */
    private String address;
    /**
    * 部门名称
    */
    private Integer deptId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改时间
    */
    private Date modifyTime;
    /**
    * 上次登录时间
    */
    private Date lastLoginTime;
    /**
    * 状态：0正常 1冻结 2逻辑删除
    */
    private Integer status;
    /**
    * 备注
    */
    private String description;


}