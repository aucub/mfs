package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.PermissionDao;
import cn.edu.zut.mfs.dao.RoleDao;
import cn.edu.zut.mfs.dao.RolePermissionRelationDao;
import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    RoleDao roleDao;
    PermissionDao permissionDao;
    RolePermissionRelationDao permissionRelationDao;
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Autowired
    public void setPermissionRelationDao(RolePermissionRelationDao permissionRelationDao) {
        this.permissionRelationDao = permissionRelationDao;
    }

    @Override
    public Boolean create(Role role) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", role.getName());
        if(roleDao.selectByMap(params).isEmpty()){
            if(roleDao.insert(role)==1){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean update(String id, Role role) {
        role.setId(id);
        if(roleDao.updateById(role)==1){
            return true;
        }
        return false;
    }

    @Override
    public List<Role> list() {
        return roleDao.selectList(null);
    }

    @Override
    public List<Permission> listPermission(String roleId) {
        return null;
    }

    @Override
    public Boolean allocPermission(String roleId, List<String> permissionIds) {
        return null;
    }
}
