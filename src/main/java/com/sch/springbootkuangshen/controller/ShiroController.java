package com.sch.springbootkuangshen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-27
 * @PackName com.sch.springbootkuangshen.controller
 * @Project springboot-kuangshen
 */

/**
 * @author sch
 */
@Controller
@RequestMapping(path = "/shiro")
public class ShiroController {
    @RequestMapping(path = "/add")
    public String add(){
        return "shiro/add";
    }
    @RequestMapping(path = "/update")
    public String update(){
        return "shiro/update";
    }
}
