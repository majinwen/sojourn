package com.ziroom.minsu.services.basedata.test.api.inner;

import com.ziroom.minsu.services.basedata.proxy.LoginServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>登陆测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/25.
 * @version 1.0
 * @since 1.0
 */
public class LoginServiceTest extends BaseTest {


    @Resource(name="basedata.loginServiceProxy")
    private LoginServiceProxy loginServiceProxy;

    @Test
    public void findResourceVoProxyTest(){
        String resultJsonStr=loginServiceProxy.currentuserReslist("8a9e9aaf537e3f7501537e3f75af0000");
        System.out.println(resultJsonStr);
    }
    
    @Test
    public void getCurrentuserInfoTest(){
    	String userFid = "busj";
    	String resultJson = loginServiceProxy.getCurrentuserInfo(userFid);
    	System.err.println(resultJson);
    }

}
