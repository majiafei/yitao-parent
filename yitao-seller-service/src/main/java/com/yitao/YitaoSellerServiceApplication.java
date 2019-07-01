package com.yitao;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.yitao.mapper")
@EnableAsync
public class YitaoSellerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoSellerServiceApplication.class, args);
    }

}
