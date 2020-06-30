package com.quark.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (SysRoleMenu)实体类
 *
 * @author makejava
 * @since 2020-06-30 15:52:54
 */

@Data
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 869594878676389434L;
    /**
    * 角色Id
    */
    private Integer roleId;
    /**
    * 菜单id
    */
    private Integer menuId;
}