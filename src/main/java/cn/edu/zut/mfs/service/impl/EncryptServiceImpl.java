package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RedisService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import cn.edu.zut.mfs.vo.UserVo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EncryptServiceImpl implements EncryptService {
    RedisService redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }


    public String getPublicKey() {
        ECIES ecies = new ECIES();
        String publicKey = ecies.getPublicKeyBase64();
        String privateKey = ecies.getPrivateKeyBase64();
        redisService.set(publicKey, privateKey);
        return publicKey;
    }

    public Boolean transformer(UserVo userVo) {
        EncryptUtils.init();
        String privateKey = redisService.get(userVo.getPublicKey());
        redisService.remove(userVo.getPublicKey());
        ECIES ecies = new ECIES(privateKey, null);
        userVo.setPassword(StrUtil.utf8Str(ecies.decrypt(userVo.getPassword(), KeyType.PrivateKey)));
        userVo.setPassword(EncryptUtils.encrypt(userVo.getPassword(), userVo.getPassword()));
        return true;
    }
}
