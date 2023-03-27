package cn.edu.zut.mfs.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("user")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserLoginDto {
    private String username;
    @TableField(exist = false)
    private String publicKey;
    private String password;
}
