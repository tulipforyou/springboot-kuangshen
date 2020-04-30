# Swagger
## 使用
* 导包
```xml
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```
* 配置
```java
@Configuration
@EnableSwagger2   //开启Swagger2
public class SwaggerConfig {
}
```
* 使用
```html
访问    http://localhost:8089/springBootWeb/swagger-ui.html
```
## 复杂使用和详细配置
* 配置扫描的包或类(默认是全部扫描)
```java
@Configuration
@EnableSwagger2   //开启Swagger2
public class SwaggerConfig {
    //配置Swagger的Docket的Bean实例
    @Bean
    public Docket docket(Environment environment){
        //根据环境不同决定是否使用Swagger
        Profiles profiles=Profiles.of("dev");
        //判断环境是否为指定环境     "dev"
        boolean flag=environment.acceptsProfiles(profiles);


        return new Docket(DocumentationType.SWAGGER_2)
                //根据环境不同决定是否启用Swagger
                .enable(flag)
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
```
* Swagger的model配置
只要controller的方法返回的是实体类(model)即可被扫描
```java
@RequestMapping(path = "/user")
public User user(){
    return new User();
}
```
实体类配置
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体类")    //*******
public class User {
    @ApiModelProperty("用户id")       //*******
    private Integer id;
    @ApiModelProperty("用户姓名")      //*******
    private String name;
    @ApiModelProperty("用户年龄")        //*******
    private Integer age;
    @ApiModelProperty("用户性别")       //*******
    private Character sex;
}
```
控制类配置
```java
@ApiOperation("Swagger Controller测试")
@RequestMapping(path = "/user")
public User user(@ApiParam("姓名") String name){
    return new User();
}
```