package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class UserLoginLog {
    private String id;
    private String userId;
    private Date createTime;
    private String ip;
}
