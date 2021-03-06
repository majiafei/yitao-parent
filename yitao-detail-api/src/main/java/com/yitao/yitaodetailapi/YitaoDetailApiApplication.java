package com.yitao.yitaodetailapi;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableDubbo
@EnableAsync
public class YitaoDetailApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoDetailApiApplication.class, args);
    }

}
