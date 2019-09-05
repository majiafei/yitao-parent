package com.yitao.common.interceptor.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName: FilterProperties
 * @Auther: admin
 * @Date: 2019/9/5 11:37
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "yt.filter")
public class FilterProperties {
    private List<String> allowPaths;
}
