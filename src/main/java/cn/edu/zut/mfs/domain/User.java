package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * ID
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户状态
     */
    private Boolean status;
    @TableField("create_time")
    private Date createTime;
    @TableField("last_update_time")
    private Date lastUpdateTime;
    @TableField("login_time")
    private Date loginTime;
    private String note;
}
