package cn.edu.zut.mfs.utils;

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
import java.util.Base64;

@Slf4j
public class EncryptUtils {
    static KeysetHandle keysetHandle;
    static DeterministicAead daead;

    public final static void init() {
        try {
            TinkConfig.register();
            InputStream inputStream = new ClassPathResource("Keyset.json").getInputStream();
            keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withInputStream(inputStream));
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

}
