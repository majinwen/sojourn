package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>订单相关的权重信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/18.
 * @version 1.0
 * @since 1.0
 */
public class WeightOrderNumVo extends BaseEntity{

    private static final long serialVersionUID = 33223123109946703L;

    /** 订单数量  */
    private Integer orderCount;

    /** 待确认数量  */
    private Integer waitCount;

    /** 时效数量  */
    private Integer timeRefuse;

    /** 拒绝数量  */
    private Integer landRefuse;
    
    /** 取消数量  */
    private Integer cancleOrderCount;
    
    /** 支付数量  */
    private Integer paidOrderCount;
    
    public Integer getPaidOrderCount() {
		return paidOrderCount;
	}

	public void setPaidOrderCount(Integer paidOrderCount) {
		this.paidOrderCount = paidOrderCount;
	}

	public Integer getCancleOrderCount() {
		return cancleOrderCount;
	}

	public void setCancleOrderCount(Integer cancleOrderCount) {
		this.cancleOrderCount = cancleOrderCount;
	}

	public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(Integer waitCount) {
        this.waitCount = waitCount;
    }

    public Integer getTimeRefuse() {
        return timeRefuse;
    }

    public void setTimeRefuse(Integer timeRefuse) {
        this.timeRefuse = timeRefuse;
    }

    public Integer getLandRefuse() {
        return landRefuse;
    }

    public void setLandRefuse(Integer landRefuse) {
        this.landRefuse = landRefuse;
    }
}
