package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RedisService;
import cn.edu.zut.mfs.vo.UserVo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.HybridEncrypt;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.config.TinkConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

@Slf4j
@Service
public class EncryptServiceImpl implements EncryptService {
    static KeysetHandle publicKeysetHandle;
    RedisService redisService;
    static HybridEncrypt hybridEncrypt;

    private static void init() {
        try {
            TinkConfig.register();
            InputStream inputStream = new ClassPathResource("publicKeyset.json").getInputStream();
            publicKeysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withInputStream(inputStream));
            hybridEncrypt = publicKeysetHandle.getPrimitive(HybridEncrypt.class);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

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
        String privateKey = redisService.get(userVo.getPublicKey());
        //redisService.remove(publicKey);
        ECIES ecies = new ECIES(privateKey, null);
        userVo.setPassword(StrUtil.utf8Str(ecies.decrypt(userVo.getPassword(), KeyType.PrivateKey)));
        try {
            userVo.setPassword(new String(hybridEncrypt.encrypt(userVo.getPassword().getBytes(), userVo.getUsername().getBytes())));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
