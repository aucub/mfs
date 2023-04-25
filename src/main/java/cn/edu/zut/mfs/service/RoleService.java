package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Role;

import java.util.List;

public interface RoleService {

    /**
     * 获取所有角色列表
     */
    List<Role> list();
}
