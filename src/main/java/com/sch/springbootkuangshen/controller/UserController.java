package com.sch.springbootkuangshen.controller;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-15
 * @PackName com.sch.springbootkuangshen.controller
 * @Project springboot-kuangshen
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;

/**
 * @author sch
 */
@Controller
public class UserController {
    @RequestMapping(path = "/loginM")
    public String login(@RequestParam("userName")String userName,
                        @RequestParam("password")String password,
                        Model model, HttpSession session){
        if(!StringUtils.isEmpty(userName)&& "1".equals(password)){
            session.setAttribute("isLogin","hasLogin");
            return"redirect:/dashboard";
        }else{
            model.addAttribute("msg","登录失败，请重新登录");
            return "index";
        }
    }
}
