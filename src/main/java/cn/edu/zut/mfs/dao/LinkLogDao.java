package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.LinkLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LinkLogDao extends BaseMapper<LinkLog> {
    Page<LinkLog> list(Page<LinkLog> page, @Param("keyword") String keyword);
}
