package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.vo.UserLoginVo;

public interface EncryptService {
    String getPublicKey();

    Boolean transformer(UserLoginVo userLoginVo);
}
