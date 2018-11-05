package com.alta.hello.service.blockingqueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baiba on 2018-11-05.
 */
public class MyBlockingQueue<T> {

    /**
     * 容器用於存放元素
     */
    private final List<T> list = new ArrayList<>();

    //隊列中最大容量
    private final int maxSize ;

    //隊列中當前元素的數量
    private int size = 0;

    private final Object lockObj = new Object();

    public MyBlockingQueue(int maxSize){
        this.maxSize = maxSize;
    }

    public void put(T obj) throws InterruptedException {
        synchronized (lockObj){
            while (size == maxSize){
                lockObj.wait();
            }
            list.add(obj);
            size++;
            lockObj.notifyAll();
        }
    }

    public T get() throws InterruptedException {
        T t;
        synchronized (lockObj){
            while(size == 0){
                lockObj.wait();
            }
            t = list.remove(0);
            size--;
            lockObj.notifyAll();
        }
        return t;
    }
}
