package com.log.trace.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @descriptions: 队列工具
 * @author: xucl
 * @date: 2021/11/24 10:41
 * @version: 1.0
 */
public class QueueUtils {

    /**
     * 一次性从blockingQueue获取100条数据
     */
    private static final int MAX_ELEMENT = 100;


    /**
     *
     *阻塞队列容量 2000
     */
    private static final BlockingDeque<Object> queueList = new LinkedBlockingDeque<Object>(2000);


    /**
     * 添加操作
     * @param obj
     */
    public static <T>void add(T obj){
        try{
            queueList.add(obj);
        }catch (IllegalStateException e){
            // 如果队列溢出 清空队列
            queueList.clear();
        }
    }

    /**
     * 添加操作
     * @param obj
     */
    public static <T>void offer(T obj){
        try{
            // 队列满了 可以阻塞3秒等待空间
            queueList.offer(obj,3, TimeUnit.SECONDS);
        } catch (IllegalStateException | InterruptedException e){
            // 如果队列异常 清空队列
            queueList.clear();
        }
    }

    /**
     * 从队列里面获取数据
     * @return
     */
    public static <T> List<T> pop() {
        LinkedList<Object> list = new LinkedList<>();
        // 一次性获取100条数据
        queueList.drainTo(list,MAX_ELEMENT);
        return (LinkedList<T>)list;
    }
}
