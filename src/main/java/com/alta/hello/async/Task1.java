package com.alta.hello.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * Created by baiba on 2018-09-14.
 */
@Component
public class Task1 {
    public static Random random =new Random();
    @Async("reportExecutor")
    public Future<String> doTaskOne() throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务一完成");
    }

    @Async("reportExecutor")
    public Future<String> doTaskTwo() throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(20000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("任务二完成");
    }
}
