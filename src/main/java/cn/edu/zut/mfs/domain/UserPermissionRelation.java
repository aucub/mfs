package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("user_permission_relation")
@Data
@AllArgsConstructor
public class UserPermissionRelation {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("permission_id")
    private String permissionId;
    private int type;
}
