package com.quark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quark.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;
import java.util.Set;

public interface SysUserRoleMapper extends BaseMapper<SysUser> {

    List<Role> findUseRoleByUsername(@Param("username") String username);

    Set<Integer> findUserRoleIdByUserId(@Param("userId")Integer userId);
}
