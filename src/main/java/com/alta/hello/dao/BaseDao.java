package com.alta.hello.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by baiba on 2018-08-23.
 */
@Component
public class BaseDao<T> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Class<T> getClazz() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected List<T> all(String sql) {
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(getClazz()));
    }

    protected List<T> all(String sql, Object... objects) {
        if(jdbcTemplate == null){
            System.out.println(3333333);
        }
        List<T> list = jdbcTemplate.query(sql, objects, BeanPropertyRowMapper.newInstance(getClazz()));
        return list;
    }

    protected int count(String sql) {
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    protected int count(String sql, Object... objects) {
        return jdbcTemplate.queryForObject(sql, objects, Integer.class);
    }
}