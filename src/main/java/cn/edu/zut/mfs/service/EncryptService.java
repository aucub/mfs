package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.dto.UserLoginDto;

public interface EncryptService {
    String getPublicKey();

    Boolean transformer(UserLoginDto userLoginDto);
    Boolean encrypt(UserLoginDto userLoginDto);
}
