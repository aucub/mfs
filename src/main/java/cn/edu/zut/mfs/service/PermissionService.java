package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 获取权限详情
     */
    Permission getItem(String id);

    /**
     * 获取权限列表
     */
    List<Permission> list();
}
