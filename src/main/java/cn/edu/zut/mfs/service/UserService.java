package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.User;
import cn.edu.zut.mfs.dto.FindUserPageDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    /**
     * 根据用户id获取用户
     */
    User getUserById(String id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    Page<User> list(FindUserPageDto findUserPageDto);


    /**
     * 修改指定用户信息
     */
    Boolean update(User user);

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
     * 获取用户对应角色
     */
    List<String> getRoleListAsString(String userId);


    /**
     * 根据用户名获取用户
     */
    User getUserByUsername(String username);


    List<User> onlineList();

    Long onlineUsers();
}
