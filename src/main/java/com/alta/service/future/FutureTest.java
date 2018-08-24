package com.alta.service.future;

/**
 * Created by baiba on 2018-08-23.
 * 试验 Java 的 Future 用法
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTest {

    /*
     *  无返回值的
    */
    public static class  Task1 implements  Runnable{
        @Override
        public  void run(){
            System.out.println("hehe");
        }
    }
    /*
    * 有返回值的 实现
    * */
    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tid = String.valueOf(Thread.currentThread().getId());
            System.out.printf("Thread#%s : in call\n", tid);
            return tid;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newCachedThreadPool();
        //无返回值
        for(int i=0;i<10;i++){
            es.submit(new Task1());
        }
        for(int i=0; i<10;i++)
            results.add(es.submit(new Task()));

        for(Future<String> res : results)
            System.out.println(res.get());
    }

}