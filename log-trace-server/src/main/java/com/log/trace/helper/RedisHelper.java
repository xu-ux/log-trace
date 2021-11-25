package com.log.trace.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @descriptions: redis服务助手
 * @author: xucl
 * @date: 2021/11/24 9:15
 * @version: 1.0
 */
@Component
public class RedisHelper {

    /**
     * redis模板
     */
    @Autowired
    private RedisTemplate<String,Object> traceRedisTemplate;

    /**
     * 获取队列中的一个元素
     * @param key
     * @return
     */
    public <T> T rightPop(String key){
        return (T)traceRedisTemplate.opsForList().rightPop(key);
    }


    /**
     * 获取指定区间的元素
     * @param key
     * @param count
     * @return
     */
    public <T>List<T> rightPopRange(String key,long count){
        ArrayList<T> list = new ArrayList<>();

        for (long i = 0; i < count; i++) {
            Object item = rightPop(key);
            if (Objects.nonNull(item)){
                list.add((T)item);
            }
        }
        return list;
    }


}
