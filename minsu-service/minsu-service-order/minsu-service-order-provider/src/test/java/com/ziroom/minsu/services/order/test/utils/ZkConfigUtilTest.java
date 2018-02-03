package com.ziroom.minsu.services.order.test.utils;

import com.asura.framework.conf.subscribe.ConfigSubscriber;
import com.asura.framework.quartz.job.EnumSysConfig;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import com.ziroom.minsu.services.order.utils.OrderSnUtil;
import org.junit.Test;

/**
 * <p>zk的常量设置</p>
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
public class ZkConfigUtilTest extends BaseTest {

    public static void main(String[] args) {
        int i = 0XFF;

        System.out.println(i);
    }

    /**
     * 创建多个线程
     * @throws Exception
     */
    @Test
    public void TestValue() throws  Exception{


        String value = ConfigSubscriber.getInstance().getConfigValue("asura", "quartzKP");
        if (value == null) {
            value = EnumSysConfig.asura_quartzKP.getDefaultValue();
        }
        System.out.println(value);
    }
}
