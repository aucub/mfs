package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RedisService;
import cn.edu.zut.mfs.utils.EncryptUtils;
import cn.edu.zut.mfs.dto.UserLoginDto;
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

    public Boolean transformer(UserLoginDto userLoginDto) {
        EncryptUtils.init();
        String privateKey = redisService.get(userLoginDto.getPublicKey());
        //redisService.remove(userVo.getPublicKey());
        ECIES ecies = new ECIES(privateKey, null);
        userLoginDto.setPassword(StrUtil.utf8Str(ecies.decrypt(userLoginDto.getPassword(), KeyType.PrivateKey)));
        userLoginDto.setPassword(EncryptUtils.encrypt(userLoginDto.getPassword(), userLoginDto.getUsername()));
        return true;
    }

    @Override
    public Boolean encrypt(UserLoginDto userLoginDto) {
        EncryptUtils.init();
        userLoginDto.setPassword(EncryptUtils.encrypt(userLoginDto.getPassword(), userLoginDto.getUsername()));
        return true;
    }
}
