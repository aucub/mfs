package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RoleDao;
import cn.edu.zut.mfs.dao.RoleRelationDao;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Role> list() {
        return roleDao.selectList(null);
    }
}
