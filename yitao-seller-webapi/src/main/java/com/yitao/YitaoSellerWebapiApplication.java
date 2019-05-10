package com.yitao;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class YitaoSellerWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoSellerWebapiApplication.class, args);
    }

}
