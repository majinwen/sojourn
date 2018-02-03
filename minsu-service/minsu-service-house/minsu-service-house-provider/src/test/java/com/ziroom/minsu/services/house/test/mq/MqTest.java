package com.ziroom.minsu.services.house.test.mq;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.send.RabbitMqSendClient;
import com.ziroom.minsu.services.common.mq.HouseMq;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>mq测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/20.
 * @version 1.0
 * @since 1.0
 */
public class MqTest extends BaseTest{

    @Resource(name="house.queueName")
    private QueueName queueName ;


    @Resource(name = "house.rabbitSendClient")
    private RabbitMqSendClient rabbitSendClient;

    @Test
    public void testGetConnection() throws Exception {
        Assert.assertNotNull(rabbitSendClient);
    }


    @Test
    public void testSendQueue2() throws Exception {
        while (true) {
            rabbitSendClient.sendQueue(queueName, "HELLO WORLD + dingfangwen");
        }
    }


    @Test
    public void testtest() throws Exception {

        for (int i=0;i<10;i++){
            String aa = JsonEntityTransform.Object2Json(new HouseMq("8a9e9a9a54963a0a015498df62560043",
                    null, RentWayEnum.HOUSE.getCode(), 1, 2));
            rabbitSendClient.sendQueue(queueName, aa);
        }

    }
}
