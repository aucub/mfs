package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<User> {
    /**
     * 获取用户
     */
    Page<User> list(Page<User> page, @Param("roleId") String roleId, @Param("keyword") String keyword);
}
