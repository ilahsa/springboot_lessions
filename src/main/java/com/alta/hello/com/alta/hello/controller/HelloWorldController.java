package com.alta.hello.com.alta.hello.controller;


import com.alta.hello.com.alta.hello.config.AltaConfig;
import com.alta.hello.com.alta.hello.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class HelloWorldController {
    @Autowired
    private  AltaConfig config;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

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
        Map< String, Object> studentMap = jdbcTemplate.queryForMap("select * from event_client_report limit 1", new HashMap<>());
        log.info(studentMap.get("event_name")+"");
        return  user;
    }
}