package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RoleRelationDao;
import cn.edu.zut.mfs.dao.UserDao;
import cn.edu.zut.mfs.dao.UserPermissionRelationDao;
import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RoleRelation;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.dto.FindUserPageDto;
import cn.edu.zut.mfs.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleRelationDao roleRelationDao;
    private UserPermissionRelationDao userPermissionRelationDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleRelationDao(RoleRelationDao roleRelationDao) {
        this.roleRelationDao = roleRelationDao;
    }

    @Autowired
    public void setUserPermissionRelationDao(UserPermissionRelationDao userPermissionRelationDao) {
        this.userPermissionRelationDao = userPermissionRelationDao;
    }

    @Override
    public User getUserById(String id) {
        return userDao.selectById(id);
    }

    @Override
    public Page<User> list(FindUserPageDto findUserPageDto) {
        Page<User> page = Page.of(findUserPageDto.getPageNum(), findUserPageDto.getPageSize());
        return userDao.list(page, findUserPageDto.getRoleId(), findUserPageDto.getKeyword());
    }

    @Override
    public Boolean update(User user) {
        return userDao.updateById(user) == 1;
    }


    @Override
    public Boolean delete(String id) {
        User user = new User();
        user.setId(id);
        return userDao.deleteById(user) == 1;
    }

    @Override
    public Boolean updateRole(String userId, List<String> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        roleRelationDao.deleteByMap(params);
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (String roleId : roleIds) {
                RoleRelation roleRelation = new RoleRelation();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                roleRelationDao.insert(roleRelation);
                count--;
            }
        }
        return count == 0;
    }

    @Override
    public List<Role> getRoleList(String userId) {
        return roleRelationDao.getRoleList(userId);
    }

    @Override
    public List<Permission> getPermissionList(String userId) {
        List<Permission> permissions = new ArrayList<>();
        //permissions.addAll(roleRelationDao.getPermissionList(userId));
        permissions.addAll(userPermissionRelationDao.getPermissionList(userId));
        return permissions;
    }

    @Override
    public List<String> getPermissions(String userId) {
        List<String> permissions = new ArrayList<>();
        roleRelationDao.getPermissionList(userId).forEach(item -> permissions.add(item.getValue()));
        userPermissionRelationDao.getPermissionList(userId).forEach(item -> permissions.add(item.getValue()));
        return permissions;
    }

    @Override
    public User getUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return userDao.selectByMap(params).get(0);
    }
}
