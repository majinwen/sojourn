package com.zra.common.utils;

/**
 * 定时任务并发控制
 */
public class TaskConcurrentControl {

    //    public static final ThreadLocal<String> holder = new ThreadLocal<String>();
    public static final ThreadLocal<String> holder = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "1";
        }
    };

    /**
     * 有锁为1，没锁为0
     */
    public static void addLock(String value) {
        holder.set(value);
    }

    /**
     * 获得锁
     */
    public static String getLock() {
        return holder.get();
    }
}
