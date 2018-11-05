package com.alta.hello.service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by baiba on 2018-10-24.
 */
@Service
public class AccessLimitService {

    //每秒只发出5个令牌
    RateLimiter rateLimiter = RateLimiter.create(5.0);

    /**
     * 尝试获取令牌
     * @return
     */
    public boolean tryAcquire(){
        List<String> rr = Lists.newArrayList();
        return rateLimiter.tryAcquire();
    }
}
