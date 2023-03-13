package cn.edu.zut.mfs.service;

import cn.edu.zut.mfs.vo.UserVo;

public interface EncryptService {
    String getPublicKey();

    Boolean transformer(UserVo userVo);
}
