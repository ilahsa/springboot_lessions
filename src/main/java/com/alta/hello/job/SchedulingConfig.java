package com.alta.hello.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by baiba on 2018-09-14.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Scheduled(cron = "0/5 * * * * ?") // 每5秒执行一次
    public void sheduleTest(){
        System.out.println(">>>>>>>>> SchedulingConfig.scheduler() "+System.currentTimeMillis());
    }
}
