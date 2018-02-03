package com.ziroom.minsu.services.order.test.cat;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

/**
 * <p>cat测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/2.
 * @version 1.0
 * @since 1.0
 */
public class CatTest extends BaseTest {

    @Test
    public void TestTest() throws Exception{

        for(;;){
            test();
        }

//        Thread.sleep(30000L);
    }

    private void test(){
        /**
         * Metric埋点1：
         * Cat.logMetricForCount(String name)name:指标名
         * 记录一个指标出现的次数：hello就是指标名
         * 场景：统计URL:'/hello'这个request的请求次数
         * 每次请求cat都会对这个指标名name进行+1操作,类似计数器
         */
        Cat.logMetricForCount("orderNum");

        /**
         * Transaction埋点1:
         * Transaction主要用来记录一段程序的访问访问行为,行为包含了
         * 这段程序的开始和结束过程的调用关系,访问时间等等一系列的对程序的描述信息
         * 场景：记录index()方法执行开始和结束期间的行为。
         * Transaction tran  = Cat.newTransaction(String type, String name);
         * type：类型，name:名称，这两个组合开发人员自定义
         */
        Transaction tran = Cat.newTransaction("order", "loooooooo");

        int random = (int) (Math.random() * 10);
        try {
//            Thread.sleep(random);
            /**
             * Event埋点2：
             * logEvent(String type, String name, String status, String nameValuePairs)
             * type:时间的类型,name,时间的名称,status,时间的状态，nameValuePairs：
             * 记录一个事件,
             */
            Cat.logEvent("afiEvent", "afiEvent", "1", "222");
            /**
             * Metric埋点2：
             * Cat.logMetricForSum(String name,double value);value:指标的值
             * 记录一个指标出现的总和
             * 场景：需要知道单位时间类的交易金额,原理和logMetricForCount一样
             */
            Cat.logMetricForSum("orderSum", random);

            if (random == 5) {
                throw new RuntimeException("random is " + random);
            }
            /**
             * 设置消息的状态,必须设置，0:标识成功,其他标识发生了异常
             */
            tran.setStatus(Transaction.SUCCESS);
        } catch (Exception e) {
            /**
             * cat使用log4j记录异常信息
             */
            Cat.logError(e);
            /**
             * 发生异常了，要设置消息的状态为e
             */
            tran.setStatus(e);
        } finally {
            /**
             * complete()方法必须要写、
             * 因为前面记录的所有消息都是在这里异步发送出去的
             * cat源码中使用的是netty,NIO来发送cat-client
             * 收集到的消息包
             */
            tran.complete();
        }
        /**
         * Metric埋点3：
         * Cat.logMetricForDuration(...)
         * 记录一个指标出现的平均值,
         * 场景：适合统计平均值的场景
         */
        Cat.logMetricForDuration("orderAvg", random);

        System.out.println("22222222222222222");
    }
}
