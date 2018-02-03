package com.ziroom.minsu.services.mq.listener;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.rabbitmq.entity.RabbitMessage;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.utils.LogUtil;
import com.rabbitmq.client.QueueingConsumer;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.search.service.FreshIndexServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>刷新房源的监听</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/12.
 * @version 1.0
 * @since 1.0
 */
@Component("search.freshHouseInfoListener")
public class FreshHouseInfoListener implements IRabbitMqMessageLisenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshHouseInfoListener.class);

    @Resource(name = "search.freshIndexServiceImpl")
    FreshIndexServiceImpl freshIndexService;

    /**
     * 刷新房源的信息
     * @param delivery
     */
    @Override
    public void processMessage(QueueingConsumer.Delivery delivery) {

        String jsonpar = null;
        try {
            String json = new String(delivery.getBody(),"UTF-8");
            RabbitMessage rabbitMessage = JsonEntityTransform.json2Object(json, RabbitMessage.class);
            LogUtil.info(LOGGER, "mq start.................................");
            LogUtil.info(LOGGER, "MQ刷新的房源信息为{}", rabbitMessage.getData());
            DataTransferObject dto = new DataTransferObject();
            jsonpar = rabbitMessage.getData();
            HouseMq houseMq = JsonEntityTransform.json2Entity(jsonpar, HouseMq.class);
            if(Check.NuNObj(houseMq) || Check.NuNStr(houseMq.getHouseBaseFid())){
                throw new BusinessException("houseInfo error");
            }
            LogUtil.info(LOGGER, "MQ 收到消息 开始等待1秒钟");
            Thread.sleep(1000);
            LogUtil.info(LOGGER, "MQ 收到消息 等待结束，开始干活儿");
            freshIndexService.freshIndexByHouseFid(houseMq.getHouseBaseFid(),dto);
            LogUtil.info(LOGGER, "MQ刷新的房源信息为:{}的结果:{}", rabbitMessage.getData(),JsonEntityTransform.Object2Json(dto));
        }catch (Exception e){

            LogUtil.error(LOGGER, "MQ 处理异常 par:{}",jsonpar);
            LogUtil.error(LOGGER,"handle consult message:{}",e);
        }

    }


}
