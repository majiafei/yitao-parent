package com.yitao.yitaoauthservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.yitao", exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.yitao.mapper")
@EnableDubbo
public class YitaoAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoAuthServiceApplication.class, args);
    }

}
