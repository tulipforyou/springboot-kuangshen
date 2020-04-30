package com.sch.springbootkuangshen.model;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-16
 * @PackName com.sch.springbootkuangshen.dao
 * @Project springboot-kuangshen
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体类")
public class User implements Serializable {
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户姓名")
    private String name;
    @ApiModelProperty("用户年龄")
    private Integer age;
    @ApiModelProperty("用户性别")
    private Character sex;
}
