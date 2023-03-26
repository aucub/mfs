package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("role_permission_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionRelation {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String roleId;
    private String permissionId;
}
