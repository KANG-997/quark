package com.quark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quark.entity.SysRoleMenu;

import java.awt.*;
import java.util.List;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<Menu> findUserPermission(String username);

}
