package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-29
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sch
 * Swagger配置类
 */
@Configuration
@EnableSwagger2   //开启Swagger2
public class SwaggerConfig {
    //配置Swagger的Docket的Bean实例
    @Bean
    public Docket docket(Environment environment){
        //根据环境不同决定是否使用Swagger
        //Profiles profiles=Profiles.of("dev");
        //判断环境是否为指定环境     "dev"
        //boolean flag=environment.acceptsProfiles(profiles);


        return new Docket(DocumentationType.SWAGGER_2)
                //根据环境不同决定是否启用Swagger
                //.enable(flag)
                //配置分组
                .groupName("孙朝辉01")
                .select()
                //配置扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.sch.springbootkuangshen.controller"))
                .build();
    }


    //多分组配置,用于协同开发,自己配自己的环境即可
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                //配置分组
                .groupName("孙朝辉02")
                .select()
                //配置扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.sch.springbootkuangshen.dao"))
                .build();
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                //配置分组
                .groupName("孙朝辉03")
                .select()
                //配置扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.sch.springbootkuangshen.Mapper"))
                .build();
    }
}
