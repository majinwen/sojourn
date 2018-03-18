package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.waterwatt.IntellectWatermeterReadDto;
import com.ziroom.zrp.service.trading.proxy.IntellectWaterMeterServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2018年02月26日 
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class IntellectWaterMeterServiceProxyTest extends BaseTest {

    @Resource(name = "trading.intellectWaterMeterServiceProxy")
    private IntellectWaterMeterServiceProxy intellectWaterMeterServiceProxy;
    @Test
    public void getIntellectWatermeterReadByPageTest() {
        IntellectWatermeterReadDto intellectWatermeterReadDto = new IntellectWatermeterReadDto();
        intellectWatermeterReadDto.setPage(1);
        intellectWatermeterReadDto.setRows(20);

        List<String> projectIds = new ArrayList<>();
        projectIds.add("8a9099cb576ba5c101576ea29c8a0027");

        intellectWatermeterReadDto.setProjectIds(projectIds);
        String resultJson = intellectWaterMeterServiceProxy.getIntellectWatermeterReadByPage(JsonEntityTransform.Object2Json(intellectWatermeterReadDto));
        System.out.println(resultJson);
    }

}