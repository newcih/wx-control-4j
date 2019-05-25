package util;

import com.google.common.collect.Maps;
import lombok.Data;

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

    public void set(Class clazz, String key, T value) {
        set(clazz, key, value, null);
    }

    /**
     * 写入缓存
     *
     * @param clazz
     * @param key
     * @param value
     * @param expireMillisSecond
     */
    public void set(Class clazz, String key, T value, Long expireMillisSecond) {
        set(clazz.getName(), key, value, expireMillisSecond);
    }

    /**
     * 写入缓存
     *
     * @param groupId
     * @param key
     * @param value
     * @param expireMillisSecond
     */
    public void set(String groupId, String key, T value, Long expireMillisSecond) {
        System.out.println("存入key=" + groupId + "@" + key + ", value=" + value + "，有效期=" + expireMillisSecond);

        final String finalKey = groupId + "@" + key;
        if (expireMillisSecond != null) {
            data.put(finalKey, value);
            keyExpire.put(finalKey, Maps.immutableEntry(System.currentTimeMillis(), expireMillisSecond));
        } else {
            data.put(finalKey, value);
        }
    }

    public T get(Class clazz, String key) {
        return get(clazz.getName(), key);
    }

    /**
     * 获取缓存
     *
     * @param groupId
     * @param key
     * @return
     */
    public T get(String groupId, String key) {
        System.out.println("读取key=" + groupId + "@" + key);
        final String finalKey = groupId + "@" + key;
        clearKeyExpire(finalKey);
        return data.get(finalKey);
    }

    /**
     * 清除已过期的key-value数据
     *
     * @param finalKey
     */
    private void clearKeyExpire(String finalKey) {
        if (keyExpire.get(finalKey) == null) {
            return;
        }
        Map.Entry<Long, Long> expireEntry = keyExpire.get(finalKey);
        if (expireEntry.getKey() + expireEntry.getValue() < System.currentTimeMillis()) {
            keyExpire.remove(finalKey);
            data.remove(finalKey);
            System.out.println("清除过期key=" + finalKey);
        }
    }
}
