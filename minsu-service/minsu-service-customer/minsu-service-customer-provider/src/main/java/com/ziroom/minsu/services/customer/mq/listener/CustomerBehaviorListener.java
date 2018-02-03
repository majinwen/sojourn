package com.ziroom.minsu.services.customer.mq.listener;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.rabbitmq.entity.RabbitMessage;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.utils.LogUtil;
import com.rabbitmq.client.QueueingConsumer;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.services.customer.service.CustomerBehaviorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * <p>客户行为mq</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月10日
 * @version 1.0
 * @since 1.0
 */
public class CustomerBehaviorListener implements IRabbitMqMessageLisenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBehaviorListener.class);

    private static final String LOG_PRE_STR = "[客户行为记录]CustomerBehaviorListener-";

    @Resource(name = "customer.customerBehaviorServiceImpl")
    CustomerBehaviorServiceImpl customerBehaviorServiceImpl;

    /**
     * 
     * 客户行为
     * 
     * @author zhangyl2
     * @created 2017年10月11日 16:43
     * @param 
     * @return 
     */
	@Override
    public void processMessage(QueueingConsumer.Delivery delivery) {

        String msg = null;
        try {
            RabbitMessage rabbitMessage = JsonEntityTransform.json2Object(new String(delivery.getBody(),"UTF-8"), RabbitMessage.class);
            msg = rabbitMessage.getData();
            LogUtil.info(LOGGER, LOG_PRE_STR + "MQ 收到消息 msg={}", msg);
            
            if (Check.NuNStr(msg)) {
            	throw new BusinessException(LOG_PRE_STR + "error");
            }

            CustomerBehaviorEntity customerBehaviorEntity = JsonEntityTransform.json2Object(msg, CustomerBehaviorEntity.class);

            if (Check.NuNObj(customerBehaviorEntity.getAttribute())
                    || Check.NuNObj(customerBehaviorEntity.getRole())
                    || Check.NuNObj(customerBehaviorEntity.getUid())
                    || Check.NuNObj(customerBehaviorEntity.getType())
                    || Check.NuNObj(customerBehaviorEntity.getScore())
                    || Check.NuNObj(customerBehaviorEntity.getCreateFid())
                    || Check.NuNObj(customerBehaviorEntity.getCreateType())
                    || Check.NuNStr(customerBehaviorEntity.getRemark())) {

                LogUtil.error(LOGGER, LOG_PRE_STR + "MQ 参数非法 customerBehaviorEntity={}", customerBehaviorEntity);
                return;
            }

            customerBehaviorServiceImpl.saveCustomerBehavior(customerBehaviorEntity);
            
        }catch (Exception e){
            LogUtil.error(LOGGER, LOG_PRE_STR + "MQ 处理异常 msg={}, e={}", msg, e);
        }

    }


}
