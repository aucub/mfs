package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.service.EncryptService;
import cn.edu.zut.mfs.service.RedisService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.google.crypto.tink.Aead;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class EncryptServiceImpl implements EncryptService {
    Aead kekAead = null;
    KeysetHandle handle;
    RedisService redisService;

    public EncryptServiceImpl() {
        try {
            AeadConfig.register();
            handle = KeysetHandle.read(JsonKeysetReader.withInputStream(new FileInputStream("./src/main/resources/keyset.json")), kekAead);
            kekAead = handle.getPrimitive(Aead.class);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLoginPublicKey() {
        ECIES ecies = new ECIES();
        String publicKey = ecies.getPublicKeyBase64();
        String privateKey = ecies.getPrivateKeyBase64();
        redisService.set(publicKey, privateKey);
        return publicKey;
    }

    @Override
    public String transformer(String username, String publicKey, String password) {
        String privateKey = redisService.get(publicKey);
        ECIES ecies = new ECIES(privateKey, null);
        password = StrUtil.utf8Str(ecies.decrypt(password, KeyType.PrivateKey));
        try {
            password = new String(kekAead.encrypt(password.getBytes(), username.getBytes()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return password;
    }
}
