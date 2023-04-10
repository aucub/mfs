package cn.edu.zut.mfs.utils;

import cn.edu.zut.mfs.dto.UserLoginDto;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.DeterministicAead;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.config.TinkConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class EncryptUtils {
    private static DeterministicAead daead;

    public static Boolean encryptUser(UserLoginDto userLoginDto) {
        init();
        userLoginDto.setPassword(encrypt(userLoginDto.getPassword(), userLoginDto.getUsername()));
        return true;
    }

    public final static void init() {
        try {
            TinkConfig.register();
            InputStream inputStream = new ClassPathResource("Keyset.json").getInputStream();
            KeysetHandle keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withInputStream(inputStream));
            daead = keysetHandle.getPrimitive(DeterministicAead.class);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String plaintext, String contextInfo) {
        try {
            return Base64.getEncoder().encodeToString(daead.encryptDeterministically(plaintext.getBytes(), contextInfo.getBytes()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String plaintext, String contextInfo) {
        try {
            return Arrays.toString(daead.decryptDeterministically(Base64.getDecoder().decode(plaintext), contextInfo.getBytes()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
