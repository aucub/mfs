package cn.edu.zut.mfs.dao;

import cn.edu.zut.mfs.domain.Permission;
import cn.edu.zut.mfs.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {
    /**
     * 获取角色权限
     */
    List<Permission> listPermission(@Param("roleId") String roleId);
}
