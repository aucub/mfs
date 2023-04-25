package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.dao.LinkLogDao;
import cn.edu.zut.mfs.dao.RoleRelationDao;
import cn.edu.zut.mfs.dao.UserDao;
import cn.edu.zut.mfs.dao.UserLoginLogDao;
import cn.edu.zut.mfs.domain.*;
import cn.edu.zut.mfs.dto.FindPageDto;
import cn.edu.zut.mfs.dto.FindUserPageDto;
import cn.edu.zut.mfs.service.RedisService;
import cn.edu.zut.mfs.service.RequestProcessor;
import cn.edu.zut.mfs.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleRelationDao roleRelationDao;

    private RedisService redisService;
    private LinkLogDao linkLogDao;

    private UserLoginLogDao userLoginLogDao;

    @Autowired
    public void setUserLoginLogDao(UserLoginLogDao userLoginLogDao) {
        this.userLoginLogDao = userLoginLogDao;
    }

    @Autowired
    public void setLinkLogDao(LinkLogDao linkLogDao) {
        this.linkLogDao = linkLogDao;
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleRelationDao(RoleRelationDao roleRelationDao) {
        this.roleRelationDao = roleRelationDao;
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
    @Cacheable("onlineList")
    public List<User> onlineList() {
        List<User> users = new ArrayList<>();
        redisService.keys("rsocket").forEach(key -> {
            users.add(userDao.selectById(key));
        });
        return users;
    }

    @Override
    public List<User> connectList() {
        List<User> users = new ArrayList<>();
        RequestProcessor.nonBlockingHashMap.keySet().forEach(key -> {
            users.add(userDao.selectById(key));
        });
        return users;
    }

    @Override
    public Long onlineUsers() {
        return redisService.size("rsocket");
    }

    @Override
    public Boolean update(User user) {
        return userDao.updateById(user) == 1;
    }


    @Override
    public Boolean delete(String id) {
        return userDao.deleteById(id) == 1;
    }

    @Override
    @Transactional
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
    public List<String> getRoleListAsString(String userId) {
        List<String> roles = new ArrayList<>();
        roleRelationDao.getRoleList(userId).forEach(item -> roles.add(item.getName()));
        return roles;
    }


    @Override
    public User getUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return userDao.selectByMap(params).get(0);
    }

    @Override
    public Page<UserLoginLog> getUserLoginLogList(FindPageDto findPageDto) {
        Page<UserLoginLog> page = Page.of(findPageDto.getPageNum(), findPageDto.getPageSize());
        return userLoginLogDao.list(page, findPageDto.getKeyword());
    }

    @Override
    public Page<LinkLog> getLinkLogList(FindPageDto findPageDto) {
        Page<LinkLog> page = Page.of(findPageDto.getPageNum(), findPageDto.getPageSize());
        return linkLogDao.list(page, findPageDto.getKeyword());
    }
}
