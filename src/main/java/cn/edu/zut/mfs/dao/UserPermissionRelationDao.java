package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.UserPermissionRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserPermissionRelationDao extends BaseMapper<UserPermissionRelation> {
    /**
     * 获取用户权限
     */
    List<Permission> getPermissionList(@Param("userId") String userId);
}
