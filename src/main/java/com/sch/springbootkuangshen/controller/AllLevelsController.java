package com.sch.springbootkuangshen.controller;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-16
 * @PackName com.sch.springbootkuangshen.controller
 * @Project springboot-kuangshen
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sch
 */
@Controller
public class AllLevelsController {
    @RequestMapping(path = "/index_security")
    public String levelRouter(){
        return "security/index_security";
    }
    @RequestMapping(path = "/level1/list/{id}")
    public String levelRouter1(@PathVariable(value = "id")String id){
        return "security/level1/list"+id;
    }
    @RequestMapping(path = "/level2/list/{id}")
    public String levelRouter2(@PathVariable(value = "id")String id){
        return "security/level2/list"+id;
    }
    @RequestMapping(path = "/level3/list/{id}")
    public String levelRouter3(@PathVariable(value = "id")String id){
        return "security/level3/list"+id;
    }
}
