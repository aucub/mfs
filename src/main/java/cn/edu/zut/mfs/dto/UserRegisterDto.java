package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    /**
     * ID
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    private String publicKey;
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户状态
     */
    private Boolean status;
    private Date createTime;
    private Date lastUpdateTime;
    private String note;
}
