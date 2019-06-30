package com.yitao.common.filter;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.net.SocketImpl;

/**
 * @ProjectName: house
 * @Package: com.yitao.common.filter
 * @ClassName: GlobalCorsConfig
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/5 14:46
 */

@SpringBootConfiguration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 配置允许跨域的域名
        config.addAllowedOrigin("http://api.yitao.com");
        config.addAllowedOrigin("http://manage.yitao.com");
        config.addAllowedOrigin("http://image.yitao.com");
        config.addAllowedOrigin("http://www.yitao.com");
        // 允许发送cookie
        config.setAllowCredentials(true);

        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.PATCH);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.TRACE);

        config.setMaxAge(3600L);

        // 允许头信息
        config.addAllowedHeader("*");

        // 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
