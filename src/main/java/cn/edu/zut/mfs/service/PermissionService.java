package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 获取权限详情
     */
    Permission getItem(String id);

    /**
     * 查询权限
     */
    List<Permission> list();
}
