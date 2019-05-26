package util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 *
 * @author NEWCIH
 */
public class CacheStorage<T> {

    private Map<String, T> data = new ConcurrentHashMap<>();
    private Map<String, Map.Entry<Long, Long>> keyExpire = new ConcurrentHashMap<>();

    public void set(String key, T value) {
        set(key, value, null);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param expireMillisSecond
     */
    public void set(String key, T value, Long expireMillisSecond) {
        System.out.println("存入key=" + key + ", value=" + value + "，有效期=" + expireMillisSecond);

        if (expireMillisSecond != null) {
            data.put(key, value);
            keyExpire.put(key, Maps.immutableEntry(System.currentTimeMillis(), expireMillisSecond));
        } else {
            data.put(key, value);
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public T get(String key) {
        System.out.println("读取key=" + key);
        clearKeyExpire(key);
        return data.get(key);
    }

    /**
     * 清除已过期的key-value数据
     *
     * @param key
     */
    private void clearKeyExpire(String key) {
        if (keyExpire.get(key) == null) {
            return;
        }
        Map.Entry<Long, Long> expireEntry = keyExpire.get(key);
        if (expireEntry.getKey() + expireEntry.getValue() < System.currentTimeMillis()) {
            keyExpire.remove(key);
            data.remove(key);
            System.out.println("清除过期key=" + key);
        }
    }
}
