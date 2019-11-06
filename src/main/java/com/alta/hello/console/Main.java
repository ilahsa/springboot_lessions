package com.alta.hello.console;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by baiba on 2018-09-13.
 */
public class Main {
    //访问次数
    static AtomicInteger count = new AtomicInteger();
    //模拟访问请求
    private synchronized static void req() throws InterruptedException {
        count.incrementAndGet();
        Thread.sleep(1000);
    }
    public static void main(String[] args) throws InterruptedException {
        int threadSize =100;
        //开始时间
        long start = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for(int i=0;i<threadSize;i++){
            Thread thread = new Thread(()->{
                try{
                    for(int j=0;j<10;j++){
                        req();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        //阻塞等待所以线程完成
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("耗时多少 "+(end-start) + " 访问总数 "+count.get());
    }
}
