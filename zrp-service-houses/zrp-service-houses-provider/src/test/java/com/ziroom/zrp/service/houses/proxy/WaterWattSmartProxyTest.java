package com.ziroom.zrp.service.houses.proxy;

import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.WaterWattPagingDto;
import com.ziroom.zrp.service.houses.valenum.MeterTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * <p>水电单元测试</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年02月07日 14:56
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
public class WaterWattSmartProxyTest extends BaseTest {

    @Resource(name="houses.waterWattSmartProxy")
    private WaterWattSmartProxy waterWattSmartProxy;

    /**
     * 查询智能水电房间列表
     * 项目权限
     */
    @Test
    public void pagingWaterWattTest(){

        WaterWattPagingDto waterWattPagingDto = new WaterWattPagingDto();
        waterWattPagingDto.setDeviceType(MeterTypeEnum.ELECTRICITY.getCode());

        String jsonStr = waterWattSmartProxy.pagingWaterWatt(waterWattPagingDto.toJsonStr());

        System.out.println(jsonStr);

        Assert.notNull(jsonStr, "pagingWaterWatt fail!");
    }

}
