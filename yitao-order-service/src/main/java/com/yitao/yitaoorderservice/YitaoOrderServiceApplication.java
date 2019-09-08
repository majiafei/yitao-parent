package com.yitao.yitaoorderservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.yitao.mapper")
@EnableDubbo
public class YitaoOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoOrderServiceApplication.class, args);
    }

}
