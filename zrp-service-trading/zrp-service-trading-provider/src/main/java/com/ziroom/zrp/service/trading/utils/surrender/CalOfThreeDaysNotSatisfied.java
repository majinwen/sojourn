package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;

import java.math.BigDecimal;

/**
 * <p></p>
 * <p> 三天不满意
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangxm113
 * @version 1.0
 * @Date Created in 2017年11月04日
 * @since 1.0
 */
public class CalOfThreeDaysNotSatisfied implements CalculateInterface {
    /**
     * 三天不满意：
     * 应缴房租 = 0
     * 应缴服务费 = 0
     * 应缴押金 = 0
     * 应缴违约金 = 0
     *
     * @param null
     * @return
     * @Author: wangxm113
     * @CreateDate: 2017年11月04日
     */
    @Override
    public CalReturnPojo calculate(CalNeedPojo paramEntity) throws Exception {
        CalReturnPojo result = new CalReturnPojo();
        result.setNeedPayFZ(BigDecimal.ZERO);
        result.setNeedPayFWF(BigDecimal.ZERO);
        result.setNeedPayWYJ(BigDecimal.ZERO);
        return result;
    }
}
