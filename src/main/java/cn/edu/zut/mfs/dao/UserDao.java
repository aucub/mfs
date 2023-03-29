package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<User>{
    /**
     * 更新用户信息
     */
    Integer update(User user);
    /**
     * 根据用户id获取用户
     */
    User getItem(String id);
    /**
     * 根据角色获取用户
     */
    Page<User> listByRoleId(Page<User> page, @Param("roleId") String roleId);
}
