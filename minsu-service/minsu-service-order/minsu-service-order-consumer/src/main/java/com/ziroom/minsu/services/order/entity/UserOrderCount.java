package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>当前订单的统计</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/20.
 * @version 1.0
 * @since 1.0
 */
public class UserOrderCount  extends BaseEntity {


    private static final long serialVersionUID = 3096849013001446703L;


    /** 预约中的订单数量 */
    private Long applyNum = 0L;

    /** 待支付的订单数量 */
    private Long waitPayNum = 0L;

    /** 待入住的订单数量 */
    private Long waitCheckInNum = 0L;

    /** 待评价的订单数量 */
    private Long waitEvalNum = 0L;

    public Long getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Long applyNum) {
        this.applyNum = applyNum;
    }

    public Long getWaitPayNum() {
        return waitPayNum;
    }

    public void setWaitPayNum(Long waitPayNum) {
        this.waitPayNum = waitPayNum;
    }

    public Long getWaitCheckInNum() {
        return waitCheckInNum;
    }

    public void setWaitCheckInNum(Long waitCheckInNum) {
        this.waitCheckInNum = waitCheckInNum;
    }

    public Long getWaitEvalNum() {
        return waitEvalNum;
    }

    public void setWaitEvalNum(Long waitEvalNum) {
        this.waitEvalNum = waitEvalNum;
    }
}
