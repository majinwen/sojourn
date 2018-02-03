package com.ziroom.minsu.services.evaluate.test.mq;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.services.evaluate.test.base.BaseTest;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorRoleEnum;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * <p>mq测试</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2017年10月11日
 * @since 1.0
 */
public class MqTest extends BaseTest {

    @Resource(name = "behavior.rabbitSendClient")
    private RabbitMqSendClient rabbitMqSendClient;

    @Resource(name = "behavior.queueName")
    private QueueName queueName;

    @Test
    public void testBehavior() throws Exception {
        LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.ORDER_SINGLE_EVALUATE;
        CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
        customerBehaviorEntity.setProveFid("11111111111111");
        customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
        customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
        customerBehaviorEntity.setUid("222222222222");
        customerBehaviorEntity.setType(behaviorEnum.getType());
        customerBehaviorEntity.setScore(behaviorEnum.getScore());
        customerBehaviorEntity.setCreateFid("222222222222");
        customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

        customerBehaviorEntity.setRemark("ceshi");

        // 推送队列消息
        for (int i = 0; i < 10; i++) {
            rabbitMqSendClient.sendQueue(queueName, JsonEntityTransform.Object2Json(customerBehaviorEntity));
        }
    }

}
