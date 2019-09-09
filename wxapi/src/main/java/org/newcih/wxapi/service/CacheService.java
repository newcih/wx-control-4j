package org.newcih.wxapi.service;

/**
 * 缓存服务
 *
 * @author newcih
 */
public interface CacheService {

    void set(String key, Object value);

    Object get(String key);

    String getString(String key);
}
