package com.sch.springbootkuangshen.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-13
 * @PackName com.sch.springbootkuangshen.controller
 * @Project springboot-kuangshen
 */
/**
 * @author sch
 */
@RestController
public class HelloController {
    @RequestMapping(path = "/hello")
    public String hello(){
        return "hello world";
    }

}
