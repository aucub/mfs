package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.dto.UserRegisterDto;

public interface RegisterService {
    Boolean register(UserRegisterDto userRegisterDto);
}
