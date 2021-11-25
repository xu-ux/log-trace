package com.log.trace.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.log.trace.helper.RedisHelper;
import com.log.trace.util.QueueUtils;
import com.log.trace.pojo.LogInfo;
import com.log.trace.util.thread.LogThreadPool;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @descriptions: 自定义appender, 数据传输给redis
 * @author: xucl
 * @date: 2021/11/24 9:07
 * @version: 1.0
 */
public class RedisAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    /**
     * 线程池
     */
    private static ThreadPoolExecutor executor = LogThreadPool.pool;

    @Override
    protected void append(ILoggingEvent eventObject) {
        QueueUtils.offer(LogInfo.getLogInfo(eventObject));
        RedisHelper redisHelper = RedisHelper.getSelfBean();
        if (Objects.nonNull(redisHelper)){
            executor.execute(redisHelper::action);
        }
    }
}
