package cn.edu.zut.mfs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class EncrypttTests {
    /*final ECIES ecies = new ECIES();
    String text = "我是一段特别长的测试";

    Aead kekAead = null;

    @Test
    public void Test() {
        System.out.println(ecies.getPrivateKeyBase64());
        System.out.println(ecies.getPublicKeyBase64());
        String encryptStr = ecies.encryptBase64(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(ecies.decrypt(encryptStr, KeyType.PrivateKey));
        System.out.println(encryptStr);
        System.out.println(decryptStr);
    }

    @Test
    public void test1() {
        try {
            TinkConfig.register();
            KeysetHandle privateKeysetHandle = KeysetHandle.generateNew(KeyTemplates.get("ECIES_P256_COMPRESSED_HKDF_HMAC_SHA256_AES128_GCM"));
            CleartextKeysetHandle.write(privateKeysetHandle, JsonKeysetWriter.withFile(new File("./src/main/resources/privateKeyset.json")));
            KeysetHandle publicKeysetHandle = privateKeysetHandle.getPublicKeysetHandle();
            CleartextKeysetHandle.write(publicKeysetHandle, JsonKeysetWriter.withFile(new File("./src/main/resources/publicKeyset.json")));
            HybridEncrypt hybridEncrypt = publicKeysetHandle.getPrimitive(HybridEncrypt.class);
            byte[] ciphertext = hybridEncrypt.encrypt("test".getBytes(), "one".getBytes());
            privateKeysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File("./src/main/resources/privateKeyset.json")));
            HybridDecrypt hybridDecrypt = privateKeysetHandle.getPrimitive(HybridDecrypt.class);
            byte[] plaintext = hybridDecrypt.decrypt(ciphertext, "one".getBytes());
            System.out.println(new String(plaintext));
        } catch (GeneralSecurityException | IOException ex) {
            System.out.println("test0");
        }
    }*/
}
