package com.alta.hello.commander;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by baiba on 2018-08-23.
 */
@Component
@Order(1)
public class Runner1 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("The OrderRunner1 start to initialize ..." + 1);
    }
}
