package cn.edu.zut.mfs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("user")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserLoginDto {
    @TableId
    private String username;
    private String password;
    @TableField(exist = false)
    private Boolean isLastingCookie;
}
