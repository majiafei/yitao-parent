package com.yitao.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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

    public static PublicKey getPublicKey(String publicKeyFilePath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytes = readFile(publicKeyFilePath);
        return getPublicKey(bytes);
    }

    public static PrivateKey getPrivateKey(String privateKeyFilePath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] bytes = readFile(privateKeyFilePath);
        return getPrivateKey(bytes);
    }

    public static PublicKey getPublicKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        return rsa.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    public static byte[] readFile(String filePath) throws IOException {
        return Files.readAllBytes(new File(filePath).toPath());
    }
}
