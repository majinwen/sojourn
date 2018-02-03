package com.ziroom.minsu.services.order.test.utils;

import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import org.junit.Test;

/**
 * <p>订单编号的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class OrderSnUtilTest extends BaseTest {


    /**
     * 创建多个线程
     * @throws Exception
     */
    @Test
    public void TestThreadSn() throws  Exception{
        int threadCount = 10;
        for(int i= 0;i<threadCount;i++){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    OrderSnUtil.doTest();
                }
            };
            thread.start();
        }
        Thread.currentThread().sleep(5000L);
    }
}
