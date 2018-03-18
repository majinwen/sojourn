package com.zra.common.filter;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;

import com.zra.common.utils.IpAddressUtil;
import com.zra.common.utils.RedisUtil;
import com.zra.common.utils.TaskConcurrentControl;


/**
 * 定时任务拦截
 */
public class TaskAspect {
	
	 private static final Logger LOGGER = LoggerFactoryProxy.getLogger("FILTERLOG");

    public Object aroundMethod(ProceedingJoinPoint pjd) throws Throwable{
        {
            String key = pjd.getTarget().getClass().getName() + "." + pjd.getSignature().getName();
            String ip = IpAddressUtil.getLocalIPAddress();
            String getIp = "";
            //同步操作防止读取和写入产生并发
            synchronized (this) {
                getIp = RedisUtil.getDataFromCache(key);
                if (StringUtils.isBlank(getIp)) {
                    RedisUtil.setDataToCache(key, ip, 300);
                }
            }
            String regetKey = "";
            if (StringUtils.isBlank(getIp)) {
                regetKey = RedisUtil.getDataFromCache(key);
            } else {
                regetKey = getIp;
            }

            //如果为本机Ip,执行定时任务
            if (!StringUtils.isBlank(regetKey) && regetKey.equals(ip)) {
                TaskConcurrentControl.addLock("1");
                Thread.currentThread().setName(key + "_" + System.currentTimeMillis());
            } else {
                TaskConcurrentControl.addLock("0");
            }

            Object o = null;
            try {
                o = pjd.proceed();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            } finally {
                //如果为本机，任务执行完释放任务锁
                if ((ip.equals(regetKey))) {
                    RedisUtil.deleteData(key);
                }
                TaskConcurrentControl.addLock("0");
            }

            return o;
        }
    }
}
