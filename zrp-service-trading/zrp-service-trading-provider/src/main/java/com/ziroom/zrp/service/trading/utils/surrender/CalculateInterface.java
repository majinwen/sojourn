package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;

/**
 * <p>工厂模式：解约费用计算接口</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日
 */
public interface CalculateInterface {
    CalReturnPojo calculate(CalNeedPojo paramEntity) throws Exception;
}
