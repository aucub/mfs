package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.dto.UserLoginDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao extends BaseMapper<UserLoginDto> {
}
