package com.ziroom.zrp.service.houses.proxy;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.proxy.WaterClearingServiceProxy;
import com.ziroom.zrp.trading.entity.IntellectWatermeterReadEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年10月13日 11:47
 * @since 1.0
 */

public class WaterClearingServiceProxyTest extends BaseTest{

    @Resource(name = "trading.waterClearingServiceProxy")
    private WaterClearingServiceProxy waterClearingServiceProxy;


    @Test
    public void manualClearingAllContractInRoomTest(){
        IntellectWatermeterReadEntity readEntity = new IntellectWatermeterReadEntity();
        readEntity.setFid("8a9e9ad260e9d7fe0160e9d7fedc0000");
        readEntity.setProjectId("8a9099cb576ba5c101576ea29c8a0027");
        readEntity.setRoomId("8a90a3ab57e7054a0157e7f830cd0ec0");
        readEntity.setMeterReading(99d);
        readEntity.setReadTime(new Date());
        readEntity.setHandleId("60001029");
        readEntity.setHandleName("张扬乐");
        readEntity.setHandleTime(new Date());
        String result = waterClearingServiceProxy.manualClearingAllContractInRoom(readEntity.toJsonStr());
        System.out.println(result);
    }

}
