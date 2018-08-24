package com.alta.hello.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AltaConfig {

    @Value("${alta.title}")
    private String title;

    @Getter
    @Value("${alta.description}")
    private String description;

    public  String getTitle(){
        return title;
    }
}
