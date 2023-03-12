package cn.edu.zut.mfs;

import org.springframework.boot.test.context.SpringBootTest;

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
            AeadConfig.register();
            KeysetHandle handle = KeysetHandle.generateNew(KeyTemplates.get("AES128_GCM"));
            kekAead = handle.getPrimitive(Aead.class);
            handle.write(JsonKeysetWriter.withOutputStream(new FileOutputStream("./src/main/resources/keyset.json")), kekAead);
            handle = KeysetHandle.read(JsonKeysetReader.withInputStream(new FileInputStream("./src/main/resources/keyset.json")), kekAead);
            kekAead = handle.getPrimitive(Aead.class);
            byte[] ciphertext = kekAead.encrypt("test".getBytes(), "one".getBytes());
            byte[] plaintext = kekAead.decrypt(ciphertext, "one".getBytes());
            System.out.println(new String(plaintext));
        } catch (GeneralSecurityException | IOException ex) {
            System.out.println("test0");
        }
    }
*/
}
