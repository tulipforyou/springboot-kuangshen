package com.sch.springbootkuangshen;

import com.sch.service.HelloService;
import com.sch.springbootkuangshen.Mapper.UserMapper;
import com.sch.springbootkuangshen.dao.DepartmentDao;
import com.sch.springbootkuangshen.dao.UserByJDBCDao;
import com.sch.springbootkuangshen.model.User;
import com.sch.springbootkuangshen.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;

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

    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Test
    //简单邮件
    public void mailTest(){
        for (int i = 0; i < 10; i++) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("springboot邮件测试");
            simpleMailMessage.setFrom("2524356811@qq.com");
            simpleMailMessage.setTo("18856362003@163.com");
            simpleMailMessage.setText("邮件由springboot官方发送--------"+i);

            javaMailSender.send(simpleMailMessage);
        }

    }
    @Test
    //复杂邮件
    public void mailTest1(){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            //帮助类进行信息组装,开启多文件支持,设置编码
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
            mimeMessageHelper.setSubject("复杂邮件测试");
            //支持html
            mimeMessageHelper.setText("<p style='color:red'>复杂邮件测试通过springboot</P>",true);
            mimeMessageHelper.setFrom("2524356811@qq.com");
            mimeMessageHelper.setTo("18856362003@163.com");
            //附件
            mimeMessageHelper.addAttachment("shiro授权管理.png"
                    ,new File("src/main/resources/static/images/shiro授权管理.png"));
        } catch (MessagingException e) {
            System.out.println("复杂邮件发送错误--->"+e.getMessage());
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

//    redisTest
    @Autowired
    RedisUtils redisUtils;
    @Test
    public void RedisTest(){
        User user = new User(1, "sch", 26, 'n');
        redisUtils.set("user",user,20);
        System.out.println(redisUtils.get("user"));
    }
}
