package com.alta.hello.tools.uniqueid;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.alta.hello.tools.core.Asserts.notGreaterThan;
import static com.alta.hello.tools.core.Asserts.notSmallerThan;

/**
 * 雪花ID生成器.
 *
 * <p>
 *     缺点:强依赖系统时间.
 * </p>
 * <p>
 *     协议格式： 0 - 41位时间戳 - 5位数据中心标识 - 5位机器标识 - 12位序列号
 * </p>
 * @author GD
 * @version 0.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class SnowId {

    private Lock lock = new ReentrantLock();
    /**
     * 起始时间戳, UTC 20170101000000
     */
    private final static long START_STMP = 1483228800000L;
    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;
    /**
     * 数据中心占用的位数
     */
    private final static long DATACENTER_BIT = 5;

    /**
     * 数据中心最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    /**
     * 机器号最大值
     */
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    /**
     * 序列最大值
     */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 序列号最左位
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    /**
     * 机器号最左位
     */
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    /**
     * 数据中心最左位
     */
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心
     */
    private long dataCenterId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号初始值
     */
    private long sequence = 0L;
    /**
     * 上一个ID时间戳
     */
    private long lastStmp = -1L;

    /**
     * 默认构造实例.
     *
     * @see #SnowId(long, long)
     */
    public SnowId() {
        this.dataCenterId = 1L;
        this.machineId = 1L;
    }

    /**
     * 根据机器标识构造实例.
     *
     * @param machineId 机器标识ID
     * @throws IllegalArgumentException 当机器标识值错误时抛出异常
     * @see #SnowId(long, long)
     */
    public SnowId(long machineId) {
        notGreaterThan(machineId, MAX_MACHINE_NUM);
        notSmallerThan(machineId, 0L);
        this.dataCenterId = 1L;
        this.machineId = machineId;
    }

    /**
     * 根据数据中心和机器码构造实例.
     *
     * <p>
     *     分布式部署服务时,各服务数据节点标识和机器标识作为联合键必须唯一.
     * </p>
     *
     * @param dataCenterId 数据节点标识ID
     * @param machineId 机器标识ID
     * @throws IllegalArgumentException 当数据中心或机器标识值错误时抛出异常
     */
    public SnowId(long dataCenterId, long machineId) {
        notGreaterThan(dataCenterId, MAX_DATACENTER_NUM);
        notSmallerThan(dataCenterId, 0L);
        notGreaterThan(machineId, MAX_MACHINE_NUM);
        notSmallerThan(machineId, 0L);
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 申请ID.
     *
     * <pre>
     *     此处sleep处理有风险,如果运行期间更改系统时间或者系统时间回调超过2秒钟，会产生重复ID.
     *     依使用情况修改为抛出runtime异常.
     *     同样会造成序列增长到最大处理有问题.
     *     一般遇不到，暂这样处理
     * </pre>
     *
     * @return long型唯一ID
     */
    public long nextId() {
        lock.lock();
        try {
            long currStmp = getNewstmp();
            int count = 0;
            while (currStmp < lastStmp) {
                if (count++ >= 2000) {
                    break;
                }
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                }
                currStmp = getNewstmp();
            }
            if (currStmp == lastStmp) {
                /** 相同毫秒内，序列号自增 */
                sequence = (sequence + 1) & MAX_SEQUENCE;
                /** 同一毫秒的序列数已经达到最大 */
                if (sequence == 0L) {
                    currStmp = getNextMill();
                }
            } else {
                /** 不同毫秒内，序列号置为0 */
                sequence = 0L;
            }
            lastStmp = currStmp;
            // 时间戳部分 | 数据中心部分 | 机器标识部分 | 序列号部分
            return (currStmp - START_STMP) << TIMESTMP_LEFT
                    | dataCenterId << DATACENTER_LEFT
                    | machineId << MACHINE_LEFT
                    | sequence;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 申请ID.
     *
     * @return 字符串类型唯一ID
     */
    public String nextIdStr() {
        return String.valueOf(nextId());
    }

    /**
     * 获取下一毫秒.
     *
     * @return long型下一毫秒
     */
    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    /**
     * UTC当前时间戳.
     *
     * @return long型时间戳
     */
    private long getNewstmp() {
        return Instant.now(Clock.systemUTC()).toEpochMilli();
    }

}
