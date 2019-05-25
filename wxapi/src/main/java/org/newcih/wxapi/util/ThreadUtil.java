package org.newcih.wxapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author NEWCIH
 */
public class ThreadUtil {

    private static final Logger log = LoggerFactory.getLogger(ThreadUtil.class);
    private static final Integer CORE_SIZE = 8;
    private static final Integer MAX_POOL_SIZE = 16;
    private static final Integer KEEP_ALIVE_SECOND = 30;
    private static final Integer BLOCK_QUEUE_SIZE = 1000;

    public static Executor executor = new ThreadPoolExecutor(CORE_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_SECOND, TimeUnit.SECONDS, new LinkedBlockingQueue<>(BLOCK_QUEUE_SIZE),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("POOL-" + r.toString());
                return thread;
            },
            (r, e) -> log.warn("当前线程池阻塞队列大小{}，该线程{}进入拒绝处理方法，需要抛弃", e.getActiveCount(), r)
    );


}
