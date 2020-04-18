package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-13
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sch
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/myMvcTest").setViewName("Myself/first");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/reLogin").setViewName("index");
        registry.addViewController("/toLogin").setViewName("security/login");
    }
    /**
     *  注入自定义国际化组件
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolverConfig();
    }
    /**
     * 拦截器
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptorConfig())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/webjars/**")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/index")
//                .excludePathPatterns("index.html")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/css/**")
//                .excludePathPatterns("/images/**")
//                .excludePathPatterns("/js/**");
//    }
}
