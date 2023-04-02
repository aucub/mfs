package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("permission")
@Data
@AllArgsConstructor
public class Permission {
    private String id;
    private String pid;
    private String name;
    private String value;
    @TableLogic()
    @TableField(select = false)
    private int deleted;
}
