package cn.edu.zut.mfs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserVo {
    private String username;
    private String publicKey;
    private String password;
}
