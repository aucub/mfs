package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.PermissionDao;
import cn.edu.zut.mfs.dao.RoleDao;
import cn.edu.zut.mfs.dao.RolePermissionRelationDao;
import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RolePermissionRelation;
import cn.edu.zut.mfs.service.RoleService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    RoleDao roleDao;
    PermissionDao permissionDao;
    RolePermissionRelationDao rolePermissionRelationDao;
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Autowired
    public void setRolePermissionRelationDao(RolePermissionRelationDao rolePermissionRelationDao) {
        this.rolePermissionRelationDao = rolePermissionRelationDao;
    }

    @Override
    public Boolean create(Role role) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", role.getName());
        if(roleDao.selectByMap(params).isEmpty()){
            return roleDao.insert(role) == 1;
        }
        return false;
    }

    @Override
    public Boolean update(String id, Role role) {
        role.setId(id);
        return roleDao.updateById(role) == 1;
    }

    @Override
    public List<Role> list() {
        return roleDao.selectList(null);
    }

    @Override
    public List<Permission> listPermission(String roleId) {
        return roleDao.listPermission(roleId);
    }

    @Override
    public Boolean allocPermission(String roleId, List<String> permissionIds) {
        int count = permissionIds == null ? 0 : permissionIds.size();
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", roleId);
        rolePermissionRelationDao.deleteByMap(params);
        if (!CollectionUtils.isEmpty(permissionIds)){
            for (String permissionId : permissionIds) {
                RolePermissionRelation rolePermissionRelation = new RolePermissionRelation();
                rolePermissionRelation.setRoleId(roleId);
                rolePermissionRelation.setPermissionId(permissionId);
                rolePermissionRelationDao.insert(rolePermissionRelation);
                count--;
            }
        }
        return count == 0;
    }
}