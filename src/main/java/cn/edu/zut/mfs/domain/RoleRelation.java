package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("role_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRelation {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("role_id")
    private String roleId;
}
