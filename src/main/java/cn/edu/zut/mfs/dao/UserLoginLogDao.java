package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.UserLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLoginLogDao extends BaseMapper<UserLoginLog> {
    Page<UserLoginLog> list(Page<UserLoginLog> page, @Param("keyword") String keyword);
}
