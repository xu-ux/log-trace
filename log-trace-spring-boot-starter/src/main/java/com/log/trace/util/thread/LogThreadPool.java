package com.log.trace.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @descriptions: 线程池
 * @author: xucl
 * @date: 2021/11/24 9:10
 * @version: 1.0
 */
public class LogThreadPool {

    public static ThreadPoolExecutor pool;

    static {
        int coreNumbers = Runtime.getRuntime().availableProcessors() * 2 + 1;
        pool = new ThreadPoolExecutor(
                coreNumbers,
                coreNumbers,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),
                Executors.defaultThreadFactory(),
                // 拒绝策略
                // new ThreadPoolExecutor.AbortPolicy() // 抛出异常
                new ThreadPoolExecutor.CallerRunsPolicy() // 交给调用线程执行
        );
    }
}
