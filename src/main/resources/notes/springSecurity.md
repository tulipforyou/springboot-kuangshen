# **springSecurity**

## 基本架构
### 授权管理
```java
/**
 * @author sch
 * springSecurity的配置类
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()  //首页都可以访问
                .antMatchers("/level1/**").hasRole("vip1")  //level1下所有请求必须是有vip1权限
                .antMatchers("/level2/**").hasRole("vip2")  //level2下所有请求必须是有vip2权限
                .antMatchers("/level3/**").hasRole("vip3"); //level3下所有请求必须是有vip3权限
        //没有权限的人跳转到springSecurity的登录页,只管上面已有的路由，其他的不管。。。。
        http.formLogin();
    }
}
```
### 用户注销
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/").permitAll()  //首页都可以访问
            .antMatchers("/level1/**").hasRole("vip1")  //level1下所有请求必须是有vip1权限
            .antMatchers("/level2/**").hasRole("vip2")  //level2下所有请求必须是有vip2权限
            .antMatchers("/level3/**").hasRole("vip3"); //level3下所有请求必须是有vip3权限
    //没有权限的人跳转到springSecurity的登录页,只管上面已有的路由，其他的不管。。。。
    http.formLogin();
    //开启注销功能，自动请求/logout请求，结合前端即可
    http.logout().deleteCookies().logoutSuccessUrl("/index");
}
只多了一句http.logout();注销成功返回至首页
```
前端：
```html
<div class="row" style="text-align: center">
     <div class="col-md-12">
         <a th:href="@{/logout}">退出</a>
     </div>
</div>
```
### 用户认证
```java
/**
 * 用户认证
 */
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //从内存加载一个用户并设置角色，不连接数据库所以从内存加载,正常应该从数据库读取用户信息
    auth.inMemoryAuthentication().withUser("sch").password("123").roles("vip1","vip3")
            .and()
            .withUser("lq").password("123").roles("vip2");
}
```
#### 报错解决
```java
There was an unexpected error (type=Internal Server Error, status=500).
There is no PasswordEncoder mapped for the id "null"

原因： 密码没有被加密
解决： 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //从内存加载一个用户并设置角色，不连接数据库所以从内存加载,正常应该从数据库读取用户信息
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("sch").password(new BCryptPasswordEncoder().encode("123")).roles("vip1","vip3")
                .and()
                .withUser("lq").password(new BCryptPasswordEncoder().encode("123")).roles("vip2");
    }
注意不同，密码被加密
```
### thymeleaf和springSecurity整合
#### 根据登录用户角色不同只显示对应的内容，不再显示没有权限的内容导致点击报错
1.导入整合包：
```properties
<dependency>
     <groupId>org.thymeleaf.extras</groupId>
     <artifactId>thymeleaf-extras-springsecurity5</artifactId>
     <version>3.0.4.RELEASE</version>
</dependency>
```
2.html页面导入命名空间
```html
<html lang="en" xmlns:th="http://www.thymeleaf.org"
                xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
```
3.使用
```html
<!--    用户没有登录-----没有被认证 !isAuthenticated() -->
    <div class="row" style="text-align: center" sec:authorize="!isAuthenticated()">
        <div class="col-md-12">
            <a th:href="@{/login}">登录</a>
        </div>
    </div>
<!--    用户已被认证-->
    <div class="row" style="text-align: center" sec:authorize="isAuthenticated()">
        <div class="col-md-6">
            <a th:href="@{/logout}">退出</a>
        </div>
        <div class="col-md-6">
            用户：<span sec:authentication="name"></span>
            角色：<span sec:authentication="principal.authorities"></span>
        </div>
    </div>
```
4.只显示当前登录用户有权限的内容
```html
<div class="row">
        <div class="col-sm" sec:authorize="hasRole('vip1')">
            <a th:href="@{/level1/list/1}" class="btn btn-success">1</a>
            <a th:href="@{/level1/list/2}" class="btn btn-success">2</a>
        </div>
        <div class="col-sm" sec:authorize="hasRole('vip2')">
            <a th:href="@{/level2/list/1}" class="btn btn-success">3</a>
            <a th:href="@{/level2/list/2}" class="btn btn-success">4</a>
        </div>
        <div class="col-sm" sec:authorize="hasRole('vip3')">
            <a th:href="@{/level3/list/1}" class="btn btn-success">5</a>
            <a th:href="@{/level3/list/2}" class="btn btn-success">6</a>
        </div>
</div>
```
sec:authorize="hasRole('vip1')"----->有vip1角色时显示，否则隐藏
5.记住我功能
```java
   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()  //首页都可以访问
                .antMatchers("/level1/**").hasRole("vip1")  //level1下所有请求必须是有vip1权限
                .antMatchers("/level2/**").hasRole("vip2")  //level2下所有请求必须是有vip2权限
                .antMatchers("/level3/**").hasRole("vip3"); //level3下所有请求必须是有vip3权限
        //没有权限的人跳转到springSecurity的登录页,只管上面已有的路由，其他的不管。。。。
        http.formLogin();
        //开启注销功能，自动请求/logout请求，结合前端即可
        http.logout().deleteCookies().logoutSuccessUrl("/index");
        //开启记住我功能,默认保存两周，在cookie里
        http.rememberMe();
    }
```
保存两周，在cookie里，可手动更改
## 自定义登录页
1.添加视图映射到自定义登录页：
```java
 @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/myMvcTest").setViewName("Myself/first");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/reLogin").setViewName("index");
        registry.addViewController("/toLogin").setViewName("security/login");
    }
```
2.登录按钮
```html
<div class="row" style="text-align: center" sec:authorize="!isAuthenticated()">
        <div class="col-md-12">
            <a th:href="@{/toLogin}">登录</a>
        </div>
</div>
```
3.自定义登录页：
```html
<body class="text-center">
<div class="container-fluid">
    <div class="row">
        <form class="form-signin" th:action="@{/login}" method="post">
            <img class="mb-4" th:src="@{/images/bootstrap-solid.svg}" alt="" width="72" height="72">
            <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
            <p style="color: red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
            <label class="sr-only" th:text="#{login.username}"></label>
            <input type="text" name="userName" class="form-control" th:placeholder="#{login.username}" required=""
                   autofocus="">
            <label class="sr-only" th:text="#{login.password}"></label>
            <input type="password" name="password" class="form-control" th:placeholder="#{login.password}" required="">
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" value="remember-me" name="rememberMe"> [[#{login.remember}]]
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
            <p class="mt-5 mb-3 text-muted">© 2017-2020</p>
            <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
            <a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
        </form>
    </div>
</div>
</body>
```
登录页表单提交到springSecurity的login请求中
4.配置自定义页面使其生效：
```java
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
```
### 问题解决
自定义页面生效后退出功能异常：

请看https://blog.csdn.net/weixin_44516305/article/details/88684107
