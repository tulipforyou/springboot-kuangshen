package com.sch.springbootkuangshen.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @RequestMapping(path = "/toLogin")
    public String toLogin(){
        return "shiro/login/login";
    }
    @RequestMapping(path = "/unauthorized")
    public String unauthorized(){
        return "shiro/login/unauthorized";
    }
    @RequestMapping(path = "/login")
    public String login(String username, String password, Model model){
        //用户认证

        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据,作为令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //登录令牌   自动验证   可加异常捕获
        try{
            subject.login(usernamePasswordToken);
            return "dashboard";
        }catch(UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "shiro/login/login";
        }catch(IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "shiro/login/login";
        }
    }
}
