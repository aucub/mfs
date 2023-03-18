package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("user")
@Data
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
     * 用户密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户状态
     */
    @TableField("status")
    private Boolean status;
    private Date createTime;
    private Date lastUpdateTime;
    private Date loginTime;
    private String note;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
