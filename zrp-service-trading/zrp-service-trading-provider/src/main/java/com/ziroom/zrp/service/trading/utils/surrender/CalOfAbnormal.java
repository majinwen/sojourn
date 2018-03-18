package com.ziroom.zrp.service.trading.utils.surrender;

import com.ziroom.zrp.service.trading.pojo.CalNeedPojo;
import com.ziroom.zrp.service.trading.pojo.CalReturnPojo;
import com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum;

import java.math.BigDecimal;

/**
 * <p></p>
 * <p> 非退
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日
 */
public class CalOfAbnormal implements CalculateInterface {

    /**
     * 非退：<br/>
     * 应缴房租 = 已缴房租，暂不考虑房租未缴纳导致欠款，产生逾期违约金的情况。如果发生，由管家自己修改。<br/>
     * 应缴服务费 = 已缴服务费 <br/>
     * 应缴押金 = 0 <br/>
     * 应缴违约金 = 100%房租金 <br/>
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
        if (LeaseCycleEnum.DAY.getCode().equals(paramEntity.getContract().getConType())) {
            result.setNeedPayWYJ(new BigDecimal(paramEntity.getContract().getFactualprice()).multiply(new BigDecimal(30)));
        } else {
            result.setNeedPayWYJ(new BigDecimal(paramEntity.getContract().getFactualprice()));
        }
        return result;
    }
}
