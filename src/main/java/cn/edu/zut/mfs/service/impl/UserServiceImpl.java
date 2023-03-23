package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RoleRelationDao;
import cn.edu.zut.mfs.dao.UserDao;
import cn.edu.zut.mfs.dao.UserPermissionRelationDao;
import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RoleRelation;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.vo.UserLoginVo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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
    public User getItem(String id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public Boolean update(String id, User user) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        User user=new User();
        user.setId(id);
        return userDao.deleteById(user) == 1;
    }

    @Override
    public Boolean updateRole(String userId, List<String> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        roleRelationDao.deleteByMap(params);
        if (!CollectionUtils.isEmpty(roleIds)){
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
        List<Permission> permissions=new ArrayList<>();
        permissions.addAll(roleRelationDao.getPermissionList(userId));
        permissions.addAll(userPermissionRelationDao.getPermissionList(userId));
        return permissions;
    }

    @Override
    public Boolean updatePassword(UserLoginVo userLoginVo) {
        return null;
    }

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }
}