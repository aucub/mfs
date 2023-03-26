package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private int status;
}
