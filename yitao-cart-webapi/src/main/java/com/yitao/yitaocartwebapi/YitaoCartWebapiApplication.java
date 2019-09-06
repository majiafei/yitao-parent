package com.yitao.yitaocartwebapi;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.yitao.common.interceptor.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.yitao")
@EnableDubbo
@EnableConfigurationProperties(JwtProperties.class)
public class YitaoCartWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoCartWebapiApplication.class, args);
    }

}
