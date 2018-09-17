package com.alta.hello.async;

import com.alta.hello.HelloApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by baiba on 2018-09-14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Task1Test {

    @Autowired
    private Task1 task;
    @Test
    public void doTaskOne() throws Exception {
        Future<String> t1 = task.doTaskOne();
        Future<String> t2 = task.doTaskTwo();
        long start = System.currentTimeMillis();
        while (true){
            if(t1.isDone() && t2.isDone()){
                System.out.println("task finished");

                break;
            }
            Thread.sleep(1000);
        }
        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }
    @Test
    public void doTaskOne1() throws Exception {
        task.doTaskOne();
        task.doTaskTwo();
        Thread.currentThread().join();
    }
}