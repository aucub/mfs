package cn.edu.zut.mfs;

import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import com.google.crypto.tink.*;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.daead.DeterministicAeadConfig;
import com.google.crypto.tink.daead.DeterministicAeadKeyTemplates;
import com.google.crypto.tink.hybrid.HybridKeyTemplates;
import com.google.crypto.tink.proto.EcPointFormat;
import com.google.crypto.tink.proto.EllipticCurveType;
import com.google.crypto.tink.proto.HashType;
import com.google.crypto.tink.proto.OutputPrefixType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Slf4j
@SpringBootTest
public class EncrypttTests {
    ECIES ecies = new ECIES();
    String text = "root";

     @Test
     public void Test() {
         ecies = new ECIES(null, "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE6XyIgVxcpBGqPEGsZeNvd9zZMw9U5NDH41xWFoIzntF/MoyS8aptARpZ2ilvqATGAd2MUGsQZRO32+pHzBwTUg==");
         String encryptStr = ecies.encryptBase64(text, KeyType.PublicKey);
         //String decryptStr = StrUtil.utf8Str(ecies.decrypt(encryptStr, KeyType.PrivateKey));
         System.out.println(encryptStr);
         //System.out.println(decryptStr);
     }


    @Test
    public void test1() throws GeneralSecurityException, IOException {
            TinkConfig.register();
            KeysetHandle  keysetHandle= KeysetHandle.generateNew(DeterministicAeadKeyTemplates.AES256_SIV);
            DeterministicAead deterministicAead=keysetHandle.getPrimitive(DeterministicAead.class);
            CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withOutputStream(new FileOutputStream("./src/main/resources/Keyset.json")));
            keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withInputStream(new FileInputStream("./src/main/resources/PublicKeyset.json")));
            DeterministicAead deterministicAead1 = keysetHandle.getPrimitive(DeterministicAead.class);
            byte[] ciphertext = deterministicAead1.encryptDeterministically("root".getBytes(), "root".getBytes());
            System.out.println(Base64.getEncoder().encodeToString(ciphertext));
    }
}
