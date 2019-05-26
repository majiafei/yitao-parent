package com.yitao.yitaouploadservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Package: com.yitao.yitaouploadservice.config
 * @ClassName: UploadProerties
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 21:10
 */

@Data
@Component
@ConfigurationProperties(prefix = "yitao.upload")
public class UploadProerties {

    // fastdfs的配置文件
    private String configPath;
    // 图片服务器的地址
    private String baseUrl;
}
