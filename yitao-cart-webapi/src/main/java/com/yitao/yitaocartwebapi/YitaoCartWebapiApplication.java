package com.yitao.yitaocartwebapi;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class YitaoCartWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoCartWebapiApplication.class, args);
    }

}
