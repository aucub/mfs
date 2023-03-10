package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterDao extends BaseMapper<User> {
}
