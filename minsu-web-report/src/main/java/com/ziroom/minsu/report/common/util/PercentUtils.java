package com.ziroom.minsu.report.common.util;

import com.asura.framework.base.util.BigDecimalUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>百分比</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/15.
 * @version 1.0
 * @since 1.0
 */
public final class PercentUtils {


    /**
     * 获取当前的百分比
     * @author afi
     * @created 2017年02月16日 20:39:12
     * @param
     * @return
     */
    public static Double getPercent(Number fenzi,Number fenmu){
        Double rate = BigDecimalUtil.div(ValueUtil.getdoubleValue(fenzi),ValueUtil.getdoubleValue(fenmu),4);
        return BigDecimalUtil.mul(rate,100);
    }


}
