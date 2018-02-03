package com.ziroom.minsu.services.common.thread.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>发送消息的线程池</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/12.
 * @version 1.0
 * @since 1.0
 */
public class SendThreadPool {

    /**
     * 线程池
     */
    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());


    /**
     * 执行线程 (异步执行)
     * @param command
     */
    public static void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
