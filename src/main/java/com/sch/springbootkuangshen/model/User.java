package com.sch.springbootkuangshen.model;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-16
 * @PackName com.sch.springbootkuangshen.dao
 * @Project springboot-kuangshen
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private Character sex;
}
