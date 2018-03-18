package com.ziroom.zrp.service.trading.pojo;

import java.math.BigDecimal;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangxm113
 * @version 1.0
 * @Date Created in 2017年07月27日 10:47
 * @since 1.0
 */
public class CalReturnPojo {
    //应缴房租
    private BigDecimal needPayFZ = BigDecimal.ZERO;
    //应缴服务费
    private BigDecimal needPayFWF = BigDecimal.ZERO;
    //应缴违约金
    private BigDecimal needPayWYJ = BigDecimal.ZERO;

    public BigDecimal getNeedPayFZ() {
        return needPayFZ;
    }

    public void setNeedPayFZ(BigDecimal needPayFZ) {
        this.needPayFZ = needPayFZ;
    }

    public BigDecimal getNeedPayFWF() {
        return needPayFWF;
    }

    public void setNeedPayFWF(BigDecimal needPayFWF) {
        this.needPayFWF = needPayFWF;
    }

    public BigDecimal getNeedPayWYJ() {
        return needPayWYJ;
    }

    public void setNeedPayWYJ(BigDecimal needPayWYJ) {
        this.needPayWYJ = needPayWYJ;
    }

    @Override
    public String toString() {
        return "CalReturnPojo{" +
                "needPayFZ=" + needPayFZ +
                ", needPayFWF=" + needPayFWF +
                ", needPayWYJ=" + needPayWYJ +
                '}';
    }
}
