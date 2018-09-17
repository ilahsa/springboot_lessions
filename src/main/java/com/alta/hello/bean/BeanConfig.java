package com.alta.hello.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baiba on 2018-09-14.
 */
@Configuration
public class BeanConfig {
    @Bean(name = "testDemo")
    public Map testDemo(){
        Map map = new HashMap<>();
        map.put("id",100);
        map.put("name","name_001");
        return map;
    }
}
