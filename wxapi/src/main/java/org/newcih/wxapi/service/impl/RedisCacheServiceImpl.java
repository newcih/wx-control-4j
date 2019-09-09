package org.newcih.wxapi.service.impl;

import org.newcih.wxapi.service.CacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author newcih
 */
@Service("redisCache")
public class RedisCacheServiceImpl implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Long KEY_CACHE_MINUTES = 1L;

    public RedisCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        System.out.println("设置值类型" + value.getClass());
        redisTemplate.opsForValue().set(key, value, KEY_CACHE_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public Object get(String key) {

        if (redisTemplate.opsForValue().get(key) != null) {
            System.out.println("读取值类型" + redisTemplate.opsForValue().get(key).getClass());
        }

        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
