package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-27
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        return shiroFilterFactoryBean;
    }
}
