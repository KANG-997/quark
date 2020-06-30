package com.quark.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysUserRole)实体类
 *
 * @author makejava
 * @since 2020-06-30 15:56:45
 */
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -60943304180490657L;
    /**
    * 主键ID
    */
    private Integer id;
    /**
    * 用户ID
    */
    private Integer userId;
    /**
    * 角色ID
    */
    private Integer roleId;
    /**
    * 创建日期
    */
    private Date createDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}