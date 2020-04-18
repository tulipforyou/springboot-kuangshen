package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-16
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * @author sch
 * springSecurity的配置类
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 授权管理
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()  //首页都可以访问
                .antMatchers("/level1/**").hasRole("vip1")  //level1下所有请求必须是有vip1权限
                .antMatchers("/level2/**").hasRole("vip2")  //level2下所有请求必须是有vip2权限
                .antMatchers("/level3/**").hasRole("vip3"); //level3下所有请求必须是有vip3权限
        //没有权限的人跳转到springSecurity的登录页,只管上面已有的路由，其他的不管。。。。
        //自定义登录页
        http.formLogin().loginPage("/toLogin").usernameParameter("userName").passwordParameter("password").loginProcessingUrl("/login");
        //开启注销功能，自动请求/logout请求，结合前端即可
        http.logout().logoutRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/logout", "GET"), new AntPathRequestMatcher("/logout", "POST"))).logoutSuccessUrl("/index");
        //开启记住我功能,默认保存两周，在cookie里
        //在自定义登录页上加入记住我
        http.rememberMe().rememberMeParameter("rememberMe");
    }
    /**
     * 用户认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //从内存加载一个用户并设置角色，不连接数据库所以从内存加载,正常应该从数据库读取用户信息
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("sch").password(new BCryptPasswordEncoder().encode("123")).roles("vip1","vip3")
                .and()
                .withUser("lq").password(new BCryptPasswordEncoder().encode("123")).roles("vip2")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123")).roles("vip2","vip1","vip3");
    }
}
