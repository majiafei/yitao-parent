package com.yitao.yitaouserwebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.yitao", exclude = DataSourceAutoConfiguration.class)
public class YitaoUserWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YitaoUserWebapiApplication.class, args);
    }

}
