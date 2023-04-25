package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.dto.UserRegisterDto;

public interface RegisterService {

    /**
     * 用户注册
     */
    Boolean register(UserRegisterDto userRegisterDto);
}
