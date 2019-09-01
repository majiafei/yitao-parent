package com.yitao.yitaouserservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.yitao")
@EnableDubbo
public class YitaoUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoUserServiceApplication.class, args);
    }

}
