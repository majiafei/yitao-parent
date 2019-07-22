package com.yitao.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;

/**
 * @ClassName: RsaUtils
 * @Auther: admin
 * @Date: 2019/7/22 16:46
 * @Description:
 */
public class RsaUtils {

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFilename, publicKeyBytes);
        // 私钥
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    private static void writeFile(String publicKeyFilename, byte[] publicKeyBytes) throws IOException {
        File file = new File(publicKeyFilename);
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(file.toPath(), publicKeyBytes);
    }
}
