package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RedisService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.HybridEncrypt;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.config.TinkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class EncryptServiceImpl implements EncryptService {
    KeysetHandle publicKeysetHandle;
    RedisService redisService;
    HybridEncrypt hybridEncrypt;

    @Autowired
    public EncryptServiceImpl() {
        try {
            TinkConfig.register();
            publicKeysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withInputStream(new ClassPathResource("publicKeyset.json").getInputStream()));
            hybridEncrypt = publicKeysetHandle.getPrimitive(HybridEncrypt.class);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public String getPublicKey() {
        ECIES ecies = new ECIES();
        String publicKey = ecies.getPublicKeyBase64();
        String privateKey = ecies.getPrivateKeyBase64();
        redisService.set(publicKey, privateKey);
        return publicKey;
    }

    @Override
    public String transformer(String username, String publicKey, String password) {
        String privateKey = redisService.get(publicKey);
        redisService.remove(publicKey);
        ECIES ecies = new ECIES(privateKey, null);
        password = StrUtil.utf8Str(ecies.decrypt(password, KeyType.PrivateKey));
        try {
            password = new String(hybridEncrypt.encrypt(password.getBytes(), username.getBytes()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return password;
    }
}
