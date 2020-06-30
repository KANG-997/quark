package com.quark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quark.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUser> {

    List<Role> findUseRoleByUsername(@Param("username") String username);
}
