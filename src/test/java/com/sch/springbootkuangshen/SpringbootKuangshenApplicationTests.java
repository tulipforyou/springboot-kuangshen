package com.sch.springbootkuangshen;

import com.sch.service.HelloService;
import com.sch.springbootkuangshen.Mapper.UserMapper;
import com.sch.springbootkuangshen.dao.DepartmentDao;
import com.sch.springbootkuangshen.dao.UserByJDBCDao;
import com.sch.springbootkuangshen.model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class SpringbootKuangshenApplicationTests {
    @Autowired
    UserByJDBCDao userByJDBCDao;
    @Test
    void contextLoads() {
        User byId = userByJDBCDao.findById(1);
        System.out.println(byId);
    }
    @Autowired
    UserMapper userMapper;
    @Test
    public void test(){
        User byId = userMapper.findById(2);
        System.out.println(byId);
    }
    Logger logger= LoggerFactory.getLogger(getClass());
    @Test
    public void logTest(){
        logger.info("sch");
    }
}
