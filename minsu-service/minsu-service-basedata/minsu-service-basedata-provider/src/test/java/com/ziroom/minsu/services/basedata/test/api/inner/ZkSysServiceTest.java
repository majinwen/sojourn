package com.ziroom.minsu.services.basedata.test.api.inner;

import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/29.
 * @version 1.0
 * @since 1.0
 */
public class ZkSysServiceTest extends BaseTest {

    @Resource(name="basedata.zkSysServiceProxy")
    private ZkSysService zkSysService;


    @Test
    public void getZkSysValueTest(){

       String aa=  zkSysService.getZkSysValue("house", "todayDiscountStartTime");
        String mapType = ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
        System.out.println(mapType);
    }


    @Test
    public void getZkSysValue(){

        String aa=  ZkUtil.getZkSysValue("job", "mobileList");

        System.out.println(aa);
    }


}
