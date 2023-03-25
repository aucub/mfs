package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.RoleRelationDao;
import cn.edu.zut.mfs.dao.UserDao;
import cn.edu.zut.mfs.dao.UserPermissionRelationDao;
import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RoleRelation;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.service.UserService;
import cn.edu.zut.mfs.dto.FindPageDto;
import cn.edu.zut.mfs.dto.UserLoginDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public Object list(FindPageDto findPageDto) {
        Page<User> page= Page.of(findPageDto.getPageNum(), findPageDto.getPageSize());
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(User::getUsername, findPageDto.getKeyword()).or().like(User::getNickname, findPageDto.getKeyword());
        userDao.selectPage(page,queryWrapper);
        return page;
    }

    @Override
    public Boolean update(User user) {
        return userDao.updateById(user)==1;
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
        //permissions.addAll(roleRelationDao.getPermissionList(userId));
        permissions.addAll(userPermissionRelationDao.getPermissionList(userId));
        return permissions;
    }

    @Override
    public Boolean updatePassword(UserLoginDto userLoginDto) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return userDao.selectByMap(params).get(0);
    }
}
