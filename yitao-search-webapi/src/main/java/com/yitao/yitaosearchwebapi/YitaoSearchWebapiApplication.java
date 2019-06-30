package com.yitao.yitaosearchwebapi;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yitao.common", "com.yitao.yitaosearchwebapi"})
@EnableDubbo
public class YitaoSearchWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoSearchWebapiApplication.class, args);
    }

}
