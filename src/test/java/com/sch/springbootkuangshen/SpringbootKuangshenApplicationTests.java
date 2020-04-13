package com.sch.springbootkuangshen;

import com.sch.springbootkuangshen.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootKuangshenApplicationTests {

    @Autowired
    private User user;
    @Test
    void contextLoads() {
        System.out.println(user);
    }

}
