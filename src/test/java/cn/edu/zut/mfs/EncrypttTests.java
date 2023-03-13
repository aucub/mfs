package cn.edu.zut.mfs;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.google.crypto.tink.*;
import com.google.crypto.tink.config.TinkConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Slf4j
@SpringBootTest
public class EncrypttTests {
    ECIES ecies = new ECIES();
    String text = "root";

    Aead kekAead = null;

     @Test
     public void Test() {
         ecies = new ECIES(null, "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE6XyIgVxcpBGqPEGsZeNvd9zZMw9U5NDH41xWFoIzntF/MoyS8aptARpZ2ilvqATGAd2MUGsQZRO32+pHzBwTUg==");
         String encryptStr = ecies.encryptBase64(text, KeyType.PublicKey);
         //String decryptStr = StrUtil.utf8Str(ecies.decrypt(encryptStr, KeyType.PrivateKey));
         System.out.println(encryptStr);
         //System.out.println(decryptStr);
     }


    @Test
    public void test1() {
        try {
            TinkConfig.register();
            KeysetHandle keysetHandle = KeysetHandle.generateNew(KeyTemplates.get("AES256_SIV"));
            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(new File("./src/main/resources/Keyset.json")));
            keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File("./src/main/resources/Keyset.json")));
            DeterministicAead daead =keysetHandle.getPrimitive(DeterministicAead.class);
            byte[] ciphertext = daead.encryptDeterministically("root".getBytes(), "root".getBytes());
            byte[] plaintext = daead.decryptDeterministically(ciphertext, "root".getBytes());
            System.out.println(new String(plaintext));
        } catch (GeneralSecurityException | IOException ex) {
            System.out.println("test0");
        }
    }
}
