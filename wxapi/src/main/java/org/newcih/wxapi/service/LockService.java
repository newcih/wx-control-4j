package org.newcih.wxapi.service;

/**
 * @author newcih
 */
public interface LockService {

    /**
     * 获取锁的时间
     */
    Long GET_LOCK_MINUTES = 1L;

    /**
     * 分布式环境下获取锁
     *
     * @return 获取状态
     */
    boolean lock(String resources);

}
