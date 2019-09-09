package org.newcih.wxapi.service.impl;

import org.newcih.wxapi.service.CacheService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author newcih
 */
@Service("localCache")
public class LocalCacheServiceImpl implements CacheService {

    private ConcurrentHashMap<String, Object> localMap = new ConcurrentHashMap<>(50);

    @Override
    public void set(String key, Object value) {
        localMap.put(key, value);
    }

    @Override
    public Object get(String key) {
        return localMap.get(key);
    }

    @Override
    public String getString(String key) {
        Object value = localMap.get(key);
        if (value instanceof String) {
            return ((String) value);
        }
        throw new ClassCastException("缓存值非String类型");
    }
}
