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

