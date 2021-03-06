package com.yitao.yitaoauthwebapi;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = "com.yitao")
@EnableDubbo
public class YitaoAuthWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoAuthWebapiApplication.class, args);
    }

}
