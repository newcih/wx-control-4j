package org.newcih.wxapi.service.impl;

import org.newcih.wxapi.service.LockService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author newcih
 */
@Service
public class RedisLockServiceImpl implements LockService {

    private final RedisTemplate<String, Integer> redisTemplate;

    public RedisLockServiceImpl(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String resource) {
        return redisTemplate.opsForValue().setIfAbsent(resource, LocalDateTime.now().getMinute(), GET_LOCK_MINUTES, TimeUnit.MINUTES);
    }
}
