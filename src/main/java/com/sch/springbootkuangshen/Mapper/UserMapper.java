package com.sch.springbootkuangshen.Mapper;

import com.sch.springbootkuangshen.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-16
 * @PackName com.sch.springbootkuangshen.dao
 * @Project springboot-kuangshen
 */

/**
 * @author sch
 */
@Mapper  //表明是mybatis的mapper类，会被spring扫描到
@Repository
public interface UserMapper {
    /**
     * 查询一个
     * @param id id
     * @return user
     */
    User findById(Integer id);
}
