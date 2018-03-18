package com.zra.system.logic;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.util.DateUtils;
import com.zra.common.utils.QuartzUtil;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.system.logic.QuartzSettingJobForOldTask;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: wangxm113
 * CreateDate: 2017/4/20.
 */
@Component
public class NewTaskAspect {
    private static final String DATE_FORMAT_SECONDS = "yyyy-MM-dd HH:mm:ss";
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger("NewTaskAspect");
    private static final ThreadLocal<Long> startTime = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };
    @Autowired
    private QuartzSettingJobForOldTask quartzSettingJobForOldTask;

    public Object before(JoinPoint pjd) throws Throwable {
        String className = pjd.getTarget().getClass().getSimpleName();
        LOGGER.info(">>>>>>>>>>>{} before begin>>>>>>>>>>>", className);
        Object o = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(pjd.getTarget().getClass().getName() + "." + pjd.getSignature().getName());
//        System.out.println(pjd.getArgs());
//        System.out.println(pjd.getKind());
//        System.out.println(pjd.getSignature().getDeclaringTypeName());
//        System.out.println(pjd.getSourceLocation().getFileName());
//        System.out.println(pjd.getTarget().getClass().getSimpleName());

        long start = System.currentTimeMillis();
        setValue(start);
        boolean canRunTask = quartzSettingJobForOldTask.canRunTask(className, true);//注意旧的定时任务，任务名字和类名一致
        if (!canRunTask) {
            LOGGER.info("{}:任务无法运行", className);
            TaskConcurrentControl.addLock("0");//
            return o;
        }
        TaskConcurrentControl.addLock("1");
        String startDate = simpleDateFormat.format(new Date());
        LOGGER.info("{}:任务start=============================={}", className, startDate);
        // 记录详细日志
//        QuartzUtil.getThreadFlg(className, 1, QuartzUtil.strMin);
//        o = pjd.proceed();
        LOGGER.info(">>>>>>>>>>>{} before end>>>>>>>>>>>", className);
        return o;
    }

    public void after(JoinPoint pjd) {
        String className = pjd.getTarget().getClass().getSimpleName();
        LOGGER.info(">>>>>>>>>>>{} after begin>>>>>>>>>>>", className);
        quartzSettingJobForOldTask.updateTaskRunFlagStop(className);
        long end = System.currentTimeMillis();
        long start = getValue();
        TaskConcurrentControl.addLock("0");
        removeValue();
        String taskEndTime = DateUtils.DateToStr(new Date(), DATE_FORMAT_SECONDS);
        LOGGER.info("{}:任务end=============================={}", className, taskEndTime);
        LOGGER.info("{}:任务耗时=============================={}", className, (end - start) + " ms");
        LOGGER.info(">>>>>>>>>>>{} after end>>>>>>>>>>>", className);
    }

    public void afterThrow(JoinPoint pjd, Throwable throwable) {
        String className = pjd.getTarget().getClass().getSimpleName();
        LOGGER.info(">>>>>>>>>>>{} afterThrow begin>>>>>>>>>>>", className);
        LOGGER.error("[定时任务afterThrow]出错！定时任务名称:" + className, throwable);
//        quartzSettingJobForOldTask.updateTaskRunFlagStop(className);
//        long end = System.currentTimeMillis();
//        long start = getValue();
//        TaskConcurrentControl.addLock("1");
//        removeValue();
//        String taskEndTime = DateUtils.DateToStr(new Date(), DATE_FORMAT_SECONDS);
//        LOGGER.info("{}:任务end=============================={}", className, taskEndTime);
//        LOGGER.info("{}:任务耗时=============================={}", className, (end - start) + " ms");
        LOGGER.info(">>>>>>>>>>>{} afterThrow end>>>>>>>>>>>", className);
    }

    private void setValue(long time) {
        startTime.set(time);
    }

    private Long getValue() {
        return startTime.get();
    }

    private void removeValue() {
        startTime.remove();
    }
}
