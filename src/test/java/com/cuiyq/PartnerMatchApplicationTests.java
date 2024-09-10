package com.cuiyq;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.cuiyq.mapper")
class PartnerMatchApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("测试");
    }

}
