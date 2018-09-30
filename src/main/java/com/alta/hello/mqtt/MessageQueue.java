package com.alta.hello.mqtt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * Created by baiba on 2018-09-29.
 */
public class MessageQueue {
    private BlockingQueue<MsgInfo> m_queue = new LinkedBlockingQueue<MsgInfo>(100);
    private static MessageQueue instantce = new MessageQueue();
    /**
     *   * 构造函数,必
     */
    private void MessageQueue() {
    }

    public static MessageQueue getInstantce(){
        return instantce;
    }
    /**
     *   * 得到当前队列项目数目 
     */
    public int getcount() {
        return m_queue.size();
    }
    /**
     *   * 清空队列  *  
     */
    public void clear() {
        m_queue.clear();
    }

    /** 
     * * 得到当前队列头项目  *  
     * * @return 当前队列头项目 
     */
    public MsgInfo get() {
        return m_queue.poll();
    }

    /**
     *   * 得到当前队列头项目 
     */
    public MsgInfo get(long timeoutmillisecond) throws InterruptedException {
        return m_queue.poll(timeoutmillisecond, TimeUnit.MILLISECONDS);
    }

    /**
     *   * 放入队列尾元素  *   * @return 成功为true,失败为false,失败一般是队列满
     */
    public boolean put(MsgInfo item) throws InterruptedException {
        return m_queue.offer(item);
    }
    /**
     *   * 放入队列尾元素  *   * @param item  *      
     */
    public boolean put(MsgInfo item, long timeoutmillisecond) throws InterruptedException {
        return m_queue.offer(item, timeoutmillisecond, TimeUnit.MILLISECONDS);
    }
}
