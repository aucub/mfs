package cn.edu.zut.mfs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -1;
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
    private String creator;
    @TableField("create_time")
    private Date createTime;
    private String updater;
    @TableField("last_update_time")
    private Date lastUpdateTime;
    @TableField("login_time")
    private Date loginTime;
    private Boolean type;
    private String note;
    @TableLogic()
    @TableField(select = false)
    private Boolean deleted;
}
