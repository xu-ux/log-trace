package com.log.trace.helper;

import com.log.trace.pojo.Constants;
import com.log.trace.pojo.LogInfo;
import com.log.trace.util.QueueUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @descriptions: redis服务助手
 * @author: xucl
 * @date: 2021/11/24 9:15
 * @version: 1.0
 */
public class RedisHelper {

    /**
     * 获取spring容器中自身对象
     */
    private static RedisHelper SELF_REDIS;

    /**
     * redis模板
     */
    private RedisTemplate<String,Object> traceRedisTemplate;


    public RedisHelper(RedisTemplate<String, Object> traceRedisTemplate) {
        this.traceRedisTemplate = traceRedisTemplate;
    }

    /**
     * 存储springbean
     * @param redisHelper
     */
    public static void setSelfBean(RedisHelper redisHelper){
        Assert.notNull(redisHelper,"Redis Helper Is Null !!!");
        SELF_REDIS = redisHelper;
    }


    public static RedisHelper getSelfBean(){
        return SELF_REDIS;
    }


    /**
     * 放入redis缓存
     */
    public void action(){
        List<LogInfo> list = QueueUtils.pop();
        if (list.size() > BigDecimal.ZERO.intValue()){
            leftPushAll(Constants.LOG_TRACE_QUEUE_KEY,list);
        }
    }


    /**
     * 队列添加
     * @param key
     * @param value
     */
    private void leftPush(String key,Object value){
        traceRedisTemplate.opsForList().leftPush(key, value);
    }


    /**
     * 批量插入队列
     * @param key
     * @param value
     */
    private void leftPushAll(String key, Collection<LogInfo> value){
       value.forEach(s -> leftPush(key,s));
    }


}
