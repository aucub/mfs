package cn.edu.zut.mfs.service.impl;


import cn.edu.zut.mfs.dao.PermissionDao;
import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private PermissionDao permissionDao;

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Override
    public Permission getItem(String id) {
        return permissionDao.selectById(id);
    }

    @Override
    public List<Permission> list() {
        return permissionDao.selectList(null);
    }
}
