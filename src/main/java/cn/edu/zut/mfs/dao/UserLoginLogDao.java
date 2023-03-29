package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.UserLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginLogDao extends BaseMapper<UserLoginLog> {
}
