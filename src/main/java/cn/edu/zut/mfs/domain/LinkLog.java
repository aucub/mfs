package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@TableName("link_log")
@Data
@AllArgsConstructor
public class LinkLog {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("create_time")
    private Date createTime;
    private String route;
    private String ip;
}
