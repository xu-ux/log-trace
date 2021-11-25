package com.log.trace.util.thread;

/**
 * @descriptions: 保存每个线程的 traceId
 * @author: xucl
 * @date: 2021/11/24 9:10
 * @version: 1.0
 */
public class TraceIdThreadLocal {

    /**
     * 线程trace对象
     */
    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    /**
     * 添加
     * @param val
     */
    public static void add(String val){
        TRACE_ID.set(val);
    }

    /**
     * 获取
     * @return
     */
    public static String get(){
        return TRACE_ID.get();
    }

    /**
     * 删除
     */
    public static void remove(){
        TRACE_ID.remove();
    }

}
