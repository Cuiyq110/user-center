package com.cuiyq;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.cuiyq.mapper")
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
    }

}
