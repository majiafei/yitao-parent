package com.yitao.auth.properties;

import com.yitao.common.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ClassName: JwtProperties
 * @Auther: admin
 * @Date: 2019/7/26 10:33
 * @Description:
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "yt.jwt")
public class JwtProperties {

    private String pubKeyPath;

    private String cookieName;

    private String secret;

    private String priKeyPath;

    private Integer expire;

    private Integer cookieMaxAge;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @PostConstruct
    public void init() {
        try {
            File pubFile = new File(pubKeyPath);
            File privateFile = new File(priKeyPath);
            if (!pubFile.exists() || !privateFile.exists()) {
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成公钥私钥失败");
        }
    }

}
