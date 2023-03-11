package cn.edu.zut.mfs;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncrypttTests {
    final ECIES ecies = new ECIES();
    String text = "我是一段特别长的测试";

    @Test
    public void Test() {
        System.out.println(ecies.getPrivateKeyBase64());
        System.out.println(ecies.getPublicKeyBase64());
        String encryptStr = ecies.encryptBase64(text.toString(), KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(ecies.decrypt(encryptStr, KeyType.PrivateKey));
        System.out.println(encryptStr);
        System.out.println(decryptStr);
    }

}
