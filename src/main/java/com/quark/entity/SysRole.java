package com.quark.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author kangkai
 * @since 2020-06-30 15:47:07
 */

@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 813723110628900630L;
    /**
    * 角色ID
    */
    private Integer roleId;
    /**
    * 角色名
    */
    private String roleName;
    /**
    * 备注
    */
    private String remark;
    /**
    * 状态：0正常 1冻结 2逻辑删除
    */
    private Integer status;
    
    private Date createTime;
    
    private Date modifyTime;



}