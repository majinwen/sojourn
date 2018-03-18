package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;

import java.math.BigDecimal;

/**
 * <p></p>
 * <p> 正退
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
public class CalOfNormal implements CalculateInterface {

    /**
     * 正退：<br/>
     * 应缴房租 = 已缴房租，暂不考虑房租未缴纳导致欠款，产生逾期违约金的情况。如果发生，由管家自己修改。<br/>
     * 应缴服务费 = 已缴服务费 <br/>
     * 应缴押金 = 0 <br/>
     * 应缴违约金 = 0 <br/>
     *
     * @param null
     * @return
     * @Author: wangxm113
     * @CreateDate: 2017年11月04日
     */
    @Override
    public CalReturnPojo calculate(CalNeedPojo paramEntity) {
        CalReturnPojo result = new CalReturnPojo();
        result.setNeedPayFZ(paramEntity.getHavePayFZ());
        result.setNeedPayFWF(paramEntity.getHavePayFWF());
        result.setNeedPayWYJ(BigDecimal.ZERO);
        return result;
    }
}
