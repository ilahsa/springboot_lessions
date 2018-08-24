package com.alta.hello.controller;


import com.alta.hello.config.AltaConfig;
import com.alta.hello.dao.impl.UserDao;
import com.alta.hello.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class HelloWorldController {
    @Autowired
    private  AltaConfig config;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World1234";
    }

    @RequestMapping("/getuser")
    public User getUser(){
        User user = new User();
        user.setName(config.getTitle());
        user.setPassword("123456");
        log.info("getuser-----------");
        Map< String, Object> studentMap = jdbcTemplate.queryForMap("select * from user limit 1", new HashMap<>());
        log.info(studentMap.get("name")+"");
        List<User> users =userDao.all();
        log.info(users.stream().findFirst().get().getName());
        return  user;
    }
}