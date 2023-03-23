package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import cn.edu.zut.mfs.domain.RoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleRelationDao extends BaseMapper<RoleRelation>{
    /**
     * 获取用户所有角色
     */
    List<Role> getRoleList(@Param("userId") String userId);
    /**
     * 获取用户角色权限
     */
    List<Permission> getPermissionList(@Param("userId") String userId);

}
