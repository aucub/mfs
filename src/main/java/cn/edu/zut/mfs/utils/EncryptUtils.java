package cn.edu.zut.mfs.utils;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.DeterministicAead;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.config.TinkConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Slf4j
public class EncryptUtils {
    private static DeterministicAead daead;

    static void init() {
        try {
            TinkConfig.register();
            InputStream inputStream = new ClassPathResource("Keyset.json").getInputStream();
            KeysetHandle keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withInputStream(inputStream));
            daead = keysetHandle.getPrimitive(DeterministicAead.class);
        } catch (GeneralSecurityException | IOException e) {
            log.error("加密失败", e);
        }
    }


    /**
     * 加密
     */
    @SneakyThrows
    public static String encrypt(String plaintext, String contextInfo) {
        init();
        return Base64.getEncoder().encodeToString(daead.encryptDeterministically(plaintext.getBytes(), contextInfo.getBytes()));
    }

}
