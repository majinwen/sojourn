package com.ziroom.minsu.services.order.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/6.
 * @version 1.0
 * @since 1.0
 */
public class LandOrderListRequest extends PageRequest {

    private static final long serialVersionUID = 56457889798545403L;

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @see com.ziroom.minsu.valenum.order.LandlordOrderTypeEnum
     * 房东订单状态类型 ：1=待处理  2=进行中  3=已处理
     */
    private Integer lanOrderType;


    public Integer getLanOrderType() {
        return lanOrderType;
    }

    public void setLanOrderType(Integer lanOrderType) {
        this.lanOrderType = lanOrderType;
    }

}
