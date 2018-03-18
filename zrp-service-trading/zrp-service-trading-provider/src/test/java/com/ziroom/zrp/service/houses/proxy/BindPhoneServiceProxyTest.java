package com.ziroom.zrp.service.houses.proxy;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.proxy.BindPhoneServiceProxy;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月01日 19:35
 * @since 1.0
 */
public class BindPhoneServiceProxyTest extends BaseTest {

    @Resource(name="trading.bindPhoneServiceProxy")
    private BindPhoneServiceProxy bindPhoneServiceProxy;

    @Test
    public void testFindByEmployeeId() {
//        String employeeId = "9000088085420255004";
        String employeeId = "90000880854";
        String result = bindPhoneServiceProxy.findBindPhoneByEmployeeId(employeeId);
        System.out.println("result:" + result);
    }
}
