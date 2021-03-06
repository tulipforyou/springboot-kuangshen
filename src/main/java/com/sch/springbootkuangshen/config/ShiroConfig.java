package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-27
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sch
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建RealM对象
     * @return 用户授权认证类
     */
    @Bean(name = "userRealM")
    public UserRealM realM(){
        return new UserRealM();
    }
    /**
     * 创建SecurityManager对象
     * @return SecurityManager对象
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealM") UserRealM userRealM){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联RealM
        defaultWebSecurityManager.setRealm(userRealM);

        return defaultWebSecurityManager;
    }
    /**
     * 创建Subject对象
     * @return Subject对象
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //关联defaultWebSecurityManager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        shiroFilterFactoryBean.setLoginUrl("/shiro/toLogin");
        //未授权用户提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/shiro/unauthorized");

        //设置过滤器
        Map<String,String> filterMap=new LinkedHashMap<>();
        filterMap.put("/shiro/add","perms[user:add]"); //拥有对某个资源的权限才可以访问
        filterMap.put("/shiro/update","authc");
        /*
         anon  无需认证即可访问
         authc   必须认证才可以访问
         user  必须用记住我功能的用户才可以访问
         perms  拥有对某个资源的权限才可以访问
         role  拥有某个角色才可以访问
         */
//        filterMap.put("/shiro/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    //整合thymeleaf
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
