package com.cuiyq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cuiyq.mapper")
@EnableScheduling
public class PartnerMatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnerMatchApplication.class, args);
    }

}
