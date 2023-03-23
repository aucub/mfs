package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.vo.UserLoginVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    /**
     * 根据用户id获取用户
     */
    User getItem(String id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<User> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    Boolean update(String id, User user);

    /**
     * 删除指定用户
     */
    Boolean delete(String id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    Boolean updateRole(String userId, List<String> roleIds);

    /**
     * 获取用户对应角色
     */
    List<Role> getRoleList(String userId);

    /**
     * 获取指定用户的权限
     */
    List<Permission> getPermissionList(String userId);

    /**
     * 修改密码
     */
    Boolean updatePassword(UserLoginVo userLoginVo);

    /**
     * 获取用户信息
     */
    User loadUserByUsername(String username);

}
