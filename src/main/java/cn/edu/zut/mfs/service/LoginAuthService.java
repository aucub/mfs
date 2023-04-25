package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.domain.LinkLog;
import cn.edu.zut.mfs.domain.UserLoginLog;
import cn.edu.zut.mfs.dto.UserLoginDto;

public interface LoginAuthService {
    Boolean login(UserLoginDto userLoginDto);

    /**
     * 修改密码
     */
    Boolean updatePassword(UserLoginDto userLoginDto);

    /**
     * 添加登录日志
     */
    Boolean addLoginLog(UserLoginLog userLoginLog);

    Boolean addLinkLog(LinkLog linkLog);
}
