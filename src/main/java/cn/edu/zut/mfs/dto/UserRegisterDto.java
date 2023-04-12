package cn.edu.zut.mfs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserRegisterDto {
    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 用户名
     */
    private String username;
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    @TableField("create_time")
    private Date createTime;
    @TableField("last_update_time")
    private Date lastUpdateTime;
    private String note;
    @TableLogic()
    @TableField(select = false)
    private Boolean deleted;
}
