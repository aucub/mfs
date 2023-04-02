package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("role")
@Data
@AllArgsConstructor
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String name;
    private String description;
    @TableLogic()
    @TableField(select = false)
    private int deleted;
}
