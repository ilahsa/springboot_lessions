package com.alta.hello.tools.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis操作通用类
 * Created by swann on 2018/04/09
 **/
public class RedisUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private StringRedisTemplate redisTemplate;
    private RedisTemplate pubRedisTemplate;

    public RedisUtils(StringRedisTemplate _redisTemplate, RedisTemplate _pubRedisTemplate) {
        redisTemplate = _redisTemplate;
        pubRedisTemplate = _pubRedisTemplate;
    }

    /**
    *
    * @Param:
    * @return:
    * @Description:  redis发布主题
    */
    public void pubRedisData(String channel, Object msg) {
        logger.info("pub-redis -> channel: {}, msg: {}", channel, msg);
        pubRedisTemplate.convertAndSend(channel, msg);
    }


    /**
    *
    * @Param:
    * @return:  符合条件的key
    * @Description:  根据提供的pattern遍历redis中符合条件的key,目前每次遍历数为5000
    */
    public List<String> scanKeys(String keyPattern) {
        List<String> keyList = redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                List<String> binaryKeys = new ArrayList<>();

                try(Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(keyPattern).count(5000).build())) {
                    while (cursor.hasNext()) {
                        byte[] key = cursor.next();
                        binaryKeys.add(new String(key, StandardCharsets.UTF_8));
                    }
                } catch (IOException e) {
                    logger.debug(e.getMessage());
                }
                return binaryKeys;
            }
        });
        return keyList;
    }

    public boolean cabTSVCheck(String rediskey, String reqTS) {
        /*if(com.synative.cs.tools.util.StringUtils.isEmpty(reqTS)) {
            logger.info("TS check failed");
            return false;
        }
        String timeStamp =  redisTemplate.opsForValue().get(rediskey) +"";
        if(StringUtils.isEmpty(timeStamp) || "null".equals(timeStamp)) {
            return true;
        }
        int ret = new BigDecimal(reqTS).compareTo(new BigDecimal(timeStamp));
        if (ret == 1) {
            return true;
        } else {
            logger.info("TS check failed");
            return false;
        }*/
        return true;
    }

    public boolean reqTSCheck(String rediskey, String reqTS) {
        /*if(com.synative.cs.tools.util.StringUtils.isEmpty(reqTS)) {
            logger.info("TS check failed");
            return false;
        }
        String timeStamp =  redisTemplate.opsForHash().get(rediskey, RedisBase.TIMESTAMP) +"";
        if(StringUtils.isEmpty(timeStamp) || "null".equals(timeStamp)) {
            return true;
        }
        int ret = new BigDecimal(reqTS).compareTo(new BigDecimal(timeStamp));
        if (ret == 1) {
            return true;
        } else {
            logger.info("TS check failed");
            return false;
        }*/
        return true;
    }

    /**
     *
     * @Param:
     * @return:
     * @Description: 将时间戳转换为yyyy-MM-dd HH:mm:ss格式字符串
     */
    public String tsConvert(String timestamp) {
        long microSec;
        if (timestamp.indexOf(".") > 0 && timestamp.length() > 13) {
            //微秒级时间戳 -> 毫秒
            microSec = (long) (Double.valueOf(timestamp).doubleValue() * 1000);
        } else {
            //毫秒级时间戳
            microSec = Long.valueOf(timestamp).longValue();
        }
        return yyyyMMddHHmmss.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(microSec), ZoneId.systemDefault()));
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            logger.error("hset exception", e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("hset exception", e);
            return false;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sRemove(String key, String... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            logger.error("setRemove exception", e);
            return 0;
        }
    }

    /**
     * 如果不存在设置
     *
     * @param key
     * @return
     */
    public boolean setIfAbsent(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, key);
    }

    /**
     * 如果不存在设置
     *
     * @param key
     * @return
     */
    public boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 抢占并设置过期时间
     *
     * @param key
     * @param expire
     * @return
     */
    public boolean setIfAbsent(String key, int expire) {
        boolean flag = redisTemplate.opsForValue().setIfAbsent(key, key);
        if (flag) {
            this.expire(key, expire);
        }
        return flag;
    }

    /**
     * 先获取key对应的value值,然后将旧的value更新为新的value
     *
     * @param key
     * @param value
     * @return oldValue
     */
    public String getSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public void del(Collection<String> keys) {
        if (keys != null && keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("expire exception", e);
            return false;
        }
    }

    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            logger.error("expire exception", e);
            return false;
        }
    }

    /**
     * 删除指定缓存失效时间
     *
     * @param key  键
     * @return
     */
    public boolean persist(String key) {
        try {
            return redisTemplate.persist(key);
        } catch (Exception e) {
            logger.error("persist exception", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("haskey exception", e);
            return false;
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("set exception", e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("set exception", e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value + "", time, TimeUnit.SECONDS);
            } else {
                set(key, value + "");
            }
            return true;
        } catch (Exception e) {
            logger.error("set exception", e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public String hget(String key, String item) {
        Object obj = redisTemplate.opsForHash().get(key, item);
        if (null == obj) {
            return null;
        }
        return String.valueOf(obj);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<String, String> hmget(String key) {
        return redisTemplate.<String, String>opsForHash().entries(key);
    }

    /**
     * HASH GET
     *
     * @param key
     * @param values
     * @return
     */
    public List<Object> hmget(String key, Collection<Object> values) {
        return redisTemplate.opsForHash().multiGet(key, values);
    }


    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, CommonUtils.checkMapValue(map));
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("hmset exception", e);
            return false;
        }
    }

    /**
     *
     * @param key  键
     * @param map  对应多个键值
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, CommonUtils.checkMapValue(map));
            return true;
        } catch (Exception e) {
            logger.error("hmset exception", e);
            return false;
        }
    }

    public boolean hashMapSet(String key, Map<String, String> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("hashMapSet exception", e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<String> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("sGet exception", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasValue(String key, String value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            logger.error("sHasKey exception", e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, String... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            logger.error("sSet exception", e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, String... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            logger.error("sSetAndTime exception", e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            logger.error("sGetSetSize exception", e);
            return 0;
        }
    }

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<String> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("lGet exception", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("lGetListSize exception", e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public String lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error("lGetIndex exception", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, String value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error("lSet exception", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, String value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("lSet exception", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<String> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            logger.error("lSet exception", e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<String> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("lSet exception", e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, String value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error("lUpdateIndex exception", e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, String value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            logger.error("lRemove exception", e);
            return 0;
        }
    }
    
    /** 
    * 有序集合添加
    *
    * @Param:  
    * @return: 
    */
    public boolean zAdd(String key, String value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            logger.error("lRemove exception", e);
            return false;
        }
    }
    
    /** 
    * 有序集合删除,通过score
    *
    * @Param:  
    * @return: 
    */
    public long zRemRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error("zRemRangeByScore exception", e);
            return 0;
        }
    }
    
    /** 
    * 有序集合统计数量,通过score
    *
    * @Param:  
    * @return: 
    */
    public long zCount(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            logger.error("zCount exception", e);
            return 0;
        }
    }

}
