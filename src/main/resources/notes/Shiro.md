# Shiro
## 三大对象
* Subject   用户
* SecurityManager   用户管理
* RealM   连接数据
## 基本使用(没有和springboot整合)
* 导入依赖
* 配置文件
* 导入quickstart程序
## 集成springboot
* 导入依赖
```xml
<dependency>
       <groupId>org.apache.shiro</groupId>
       <artifactId>shiro-spring</artifactId>
       <version>1.5.2</version>
</dependency>
```
* 编写shiro的配置类(三大对象的配置)
```java
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

public class UserRealM extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
```
![](/home/sch/project/untitled/springboot-kuangshen/src/main/resources/static/images/shiro授权管理.png)
* 实例
```java
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
}

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

public class UserRealM extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username="root";
        String password="123456";
        //获取用户的令牌信息进行认证
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //用户名验证
        if (!token.getUsername().equals(username)) {
            return null;  //自动抛出UnknownAccountException
        }
        //密码无需验证,由shiro自动验证
        return new SimpleAuthenticationInfo("",password,"");
    }
}
```
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>shiro登录</title>
</head>
<body>
<p th:text="${msg}" th:if="${msg != null}" style="color: #FD482C"></p>
<form th:action="@{/shiro/login}" method="post">
    username:<label>
    <input type="text" name="username"/>
</label>
    password:<label>
    <input type="password" name="password"/>
</label>
    <input type="submit" value="login">
</form>
</body>
</html>
```

## 和thymeleaf整合
* 导包
```xml
<!-- https://mvnrepository.com/artifact/com.github.theborakompanioni/thymeleaf-extras-shiro -->
<dependency>
    <groupId>com.github.theborakompanioni</groupId>
    <artifactId>thymeleaf-extras-shiro</artifactId>
    <version>2.0.0</version>
</dependency>

```
* 配置
```java
@Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
```
* 使用
1. html导入shiro的命名空间
```html
xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">  
```
2.使用
```html
<div class="col-md-4" style="text-align: center" shiro:hasPermission="user:add">
			<h1 style="text-align: center; color: #00FFFF">
				我是<a th:href="@{/shiro/add}">add</a>
			</h1>
		</div>
		<div class="col-md-4" style="text-align: center">
			<a class="btn btn-danger" th:href="@{/dashboard}">进入系统</a>
		</div>
		<div class="col-md-4" style="text-align: center" shiro:hasPermission="user:update">
			<h1 style="text-align: center; color: #00FFFF">
				我是<a th:href="@{/shiro/update}">update</a>
			</h1>
		</div>
</div>
```
