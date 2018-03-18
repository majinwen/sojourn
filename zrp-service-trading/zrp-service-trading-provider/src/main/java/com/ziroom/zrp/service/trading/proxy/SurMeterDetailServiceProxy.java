package com.ziroom.zrp.service.trading.proxy;

import com.ziroom.zrp.service.trading.service.SurMeterDetailServiceImpl;
import com.ziroom.zrp.trading.entity.SurMeterDetailEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月15日 13时19分
 * @Version 1.0
 * @Since 1.0
 */
@Component("trading.surMeterDetailServiceProxy")
public class SurMeterDetailServiceProxy {
    @Resource(name = "trading.surMeterDetailServiceImpl")
    private SurMeterDetailServiceImpl surMeterDetailServiceImpl;

    /**
     * 退租水电交割表的水电交割费用
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时18分12秒
     */
    public SurMeterDetailEntity getSDPriceBySurrenderId(String surrenderId) {
        return surMeterDetailServiceImpl.getSDPriceBySurrenderId(surrenderId);
    }
}
