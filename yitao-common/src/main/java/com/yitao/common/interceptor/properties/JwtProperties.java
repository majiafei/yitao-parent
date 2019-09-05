package com.yitao.common.interceptor.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName: JwtProperties
 * @Auther: admin
 * @Date: 2019/9/5 15:37
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "yt.jwt")
public class JwtProperties {
    private String cookieName;

    private String pubKeyPath;

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }
}
