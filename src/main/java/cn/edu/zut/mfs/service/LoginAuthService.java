package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.dto.UserLoginDto;

public interface LoginAuthService {
    Boolean login(UserLoginDto userLoginDto);

    /**
     * 修改密码
     */
    Boolean updatePassword(UserLoginDto userLoginDto);

}
