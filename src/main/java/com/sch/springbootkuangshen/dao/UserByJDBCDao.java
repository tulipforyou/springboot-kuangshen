package com.sch.springbootkuangshen.dao;

import com.sch.springbootkuangshen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
@Repository
public class UserByJDBCDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public User findById(Integer id){
        return null;
    }
}
