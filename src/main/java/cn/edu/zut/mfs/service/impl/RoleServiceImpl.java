package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RoleDao;
import cn.edu.zut.mfs.dao.RoleRelationDao;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    RoleDao roleDao;
    RoleRelationDao roleRelationDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setRoleRelationDao(RoleRelationDao roleRelationDao) {
        this.roleRelationDao = roleRelationDao;
    }

    @Override
    public Boolean save(Role role) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", role.getName());
        if (roleDao.selectByMap(params).isEmpty()) {
            return roleDao.insert(role) == 1;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean delete(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("role_id", id);
        roleRelationDao.deleteByMap(params);
        return roleDao.deleteById(id) == 1;
    }

    @Override
    public Boolean update(Role role) {
        return roleDao.updateById(role) == 1;
    }

    @Override
    public List<Role> list() {
        return roleDao.selectList(null);
    }
}
