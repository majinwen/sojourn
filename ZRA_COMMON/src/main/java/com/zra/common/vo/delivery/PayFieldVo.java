package com.zra.common.vo.delivery;

import com.zra.common.vo.base.BaseFieldVo;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月17日 11:27
 * @since 1.0
 */
public class PayFieldVo extends BaseFieldVo{
    /**
     * 0 未支付 1=已支付
     */
    private int isPay;

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }
}
