package com.ziroom.minsu.services.mq;

import com.asura.framework.rabbitmq.connection.RabbitConnectionFactory;
import com.asura.framework.rabbitmq.entity.QueueName;
import com.asura.framework.rabbitmq.receive.IRabbitMqMessageLisenter;
import com.asura.framework.rabbitmq.receive.IRabbitMqReceiver;
import com.asura.framework.rabbitmq.receive.queue.RabbitMqQueueReceiver;
import com.ziroom.minsu.services.mq.listener.FreshHouseInfoListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>mq测试</p>
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
@ContextConfiguration(locations = {"classpath:/META-INF/spring/applicationContext-search.xml",
        "classpath:/META-INF/spring/search-rabbitmq.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MqTest {

    @Resource(name = "search.freshReceiver")
    private IRabbitMqReceiver rabbitMqReceiver;


    @Test
    public void t_processMessage_01() throws Exception {

        rabbitMqReceiver.receiveMessage();

        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++");
        Thread.sleep(60000);
    }



    @Test
    public void testSingleThradReceiver() throws Exception {
        RabbitMqQueueReceiver rabbitMqReceiver = new RabbitMqQueueReceiver();
        rabbitMqReceiver.setQueueName(new QueueName("DFW", "test", "001"));
        RabbitConnectionFactory rabbitConnectionFactory = new RabbitConnectionFactory();
        rabbitConnectionFactory.init();
        rabbitMqReceiver.setRabbitConnectionFactory(rabbitConnectionFactory);
        IRabbitMqMessageLisenter lisenter = new FreshHouseInfoListener();
        List<IRabbitMqMessageLisenter> lisenterList = new ArrayList<>();
        lisenterList.add(lisenter);
        rabbitMqReceiver.setRabbitMqMessageLiteners(lisenterList);
        rabbitMqReceiver.receiveMessage();
        Thread.sleep(5000000);
    }


}
