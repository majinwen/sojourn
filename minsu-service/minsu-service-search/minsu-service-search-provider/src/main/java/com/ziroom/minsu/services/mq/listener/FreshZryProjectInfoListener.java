package com.ziroom.minsu.services.mq.listener;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.rabbitmq.entity.RabbitMessage;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.utils.LogUtil;
import com.rabbitmq.client.QueueingConsumer;
import com.ziroom.minsu.services.search.service.FreshZryIndexServiceImpl;

/**
 * 
 * <p>刷新自如驿的监听</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangyl
 * @since 1.0
 * @version 1.0
 */
public class FreshZryProjectInfoListener implements IRabbitMqMessageLisenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshZryProjectInfoListener.class);

    @Resource(name = "search.freshZryIndexServiceImpl")
    FreshZryIndexServiceImpl freshZryIndexServiceImpl;
    
    /**
     * 刷新自如驿的信息
     * @param delivery
     */
    @SuppressWarnings("unchecked")
	@Override
    public void processMessage(QueueingConsumer.Delivery delivery) {

        String msg = null;
        try {
            RabbitMessage rabbitMessage = JsonEntityTransform.json2Object(new String(delivery.getBody(),"UTF-8"), RabbitMessage.class);
            msg = rabbitMessage.getData();
            LogUtil.info(LOGGER, "FreshZryProjectInfoListener MQ 收到消息 msg={}", msg);
            
            if (Check.NuNStr(msg)) {
            	throw new BusinessException("FreshZryProjectInfoListener error");
            }
            
            Map<String, String> projectBidMap = (Map<String, String>) JsonEntityTransform.json2Map(msg);
            String projectBid = projectBidMap.get("projectBid");
            
            LogUtil.info(LOGGER, "FreshZryProjectInfoListener MQ 收到消息 projectBid={}", projectBid);
            
            String processResult = freshZryIndexServiceImpl.syncDealSearchIndex(projectBid);
            
            LogUtil.info(LOGGER, "FreshZryProjectInfoListener MQ刷新的自如驿信息为:projectBid{}的结果:{}", projectBid, processResult);
        }catch (Exception e){
            LogUtil.error(LOGGER, "FreshZryProjectInfoListener MQ 处理异常 msg={}, e={}", msg, e);
        }

    }


}
