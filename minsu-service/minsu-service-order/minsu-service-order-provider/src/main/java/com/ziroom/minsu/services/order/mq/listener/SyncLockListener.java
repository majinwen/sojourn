package com.ziroom.minsu.services.order.mq.listener;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.asura.framework.utils.LogUtil;
import com.rabbitmq.client.QueueingConsumer;
import com.ziroom.minsu.services.common.entity.CalendarDataVo;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.mq.CalendarLockMq;
import com.ziroom.minsu.services.order.dto.LockHouseRequest;
import com.ziroom.minsu.services.order.service.HouseLockServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>同步日历锁 mq</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年08月23日 16:16
 * @since 1.0
 */
public class SyncLockListener implements IRabbitMqMessageLisenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncLockListener.class);

    @Resource(name = "order.houseLockServiceImpl")
    HouseLockServiceImpl houseLockServiceImpl;

    @Resource(name = "house.rabbitSendClient")
    private RabbitMqSendClient rabbitMqSendClient;

    @Resource(name="house.queueName")
    private QueueName queueName;

    private static final String logPreStr = "SyncLockListener MQ-";

    @Override
    public void processMessage(QueueingConsumer.Delivery delivery) throws Exception {
        String msg = null;
        try {
            msg = new String(delivery.getBody(),"UTF-8");
            CalendarLockMq calendarLockMq = JsonEntityTransform.json2Object(msg, CalendarLockMq.class);
            LogUtil.info(LOGGER, logPreStr + "收到消息 lockHouseRequest={}", calendarLockMq);
            LockHouseRequest lockHouseRequest = calendarLockMq.getData();

            if(Check.NuNObj(lockHouseRequest)
                    || Check.NuNStr(lockHouseRequest.getHouseFid())
                    || Check.NuNObj(lockHouseRequest.getRentWay())){
                throw new BusinessException(logPreStr + "数据不合法");
            }

            List<CalendarDataVo> calendarDataVos = lockHouseRequest.getCalendarDataVos();

            if(!Check.NuNCollection(calendarDataVos)){

                List<Date> allDate = new ArrayList<>();
                Date today = new Date();
                String todayStr = DateUtil.dateFormat(today, "yyyy-MM-dd");
                try {
                    today= DateUtil.parseDate(todayStr +" 00:00:00","yyyy-MM-dd");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //处理房源锁状态
                for (CalendarDataVo calendarDataVo : calendarDataVos){
                    Date startDate = calendarDataVo.getStartDate();
                    Date endDate = calendarDataVo.getEndDate();
                    List<Date> dates = DateSplitUtil.dateSplit(startDate, endDate);
                    for (Date date : dates){
                        if (date.before(today)){
                            continue;
                        }
                        allDate.add(date);
                    }
                }

                if(!Check.NuNCollection(allDate)){
                    lockHouseRequest.setLockDayList(allDate);
                    houseLockServiceImpl.syncAirLock(lockHouseRequest);

                    String pushContent = JsonEntityTransform.Object2Json(new HouseMq(lockHouseRequest.getHouseFid(),
                            lockHouseRequest.getRoomFid(), lockHouseRequest.getRentWay(), null, null));
                    // 推送队列消息
                    rabbitMqSendClient.sendQueue(queueName, pushContent);
                    LogUtil.info(LOGGER, logPreStr + "同步房源日历成功,推送{}搜索刷新索引,推送内容:{}", queueName.getName(), pushContent);
                }
            }

        }catch (Exception e){
            LogUtil.error(LOGGER, logPreStr + "处理异常 msg={}, e={}", msg, e);
        }

    }
}
