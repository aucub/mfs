package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {
    /**
     * 添加角色
     */
    Boolean create(Role role);
    /**
     * 删除角色
     */
    Boolean delete(String id);

    /**
     * 修改角色信息
     */
    Boolean update(String id, Role role);

    /**
     * 获取所有角色列表
     */
    List<Role> list();

    /**
     * 获取角色相关权限
     */
    List<Permission> listPermission(String roleId);

    /**
     * 给角色分配权限
     */
    @Transactional
    Boolean allocPermission(String roleId, List<String> permissionIds);
}
