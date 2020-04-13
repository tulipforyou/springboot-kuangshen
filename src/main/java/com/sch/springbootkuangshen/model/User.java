package com.sch.springbootkuangshen.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-12
 * @PackName com.sch.springbootkuangshen.model
 * @Project springboot-kuangshen
 */

/**
 * @author sch
 */
@Component
@Validated
@ConfigurationProperties(prefix = "user")
public class User {
    private int id;
    @Email
    @NotNull(message = "用户名不能为空")
    private String userName;
    @Max(value = 120)
    @NotNull(message = "年龄不能为空")
    private int age;
    @NotNull(message = "性别不能为空")
    @Length(message = "性别填写有误")
    private String sex;

    public User() {
    }

    public User(int id, String name, int age, String sex) {
        this.id = id;
        this.userName = name;
        this.age = age;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
