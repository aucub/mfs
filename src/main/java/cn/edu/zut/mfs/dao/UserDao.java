package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
}
