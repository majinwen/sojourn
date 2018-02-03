package com.ziroom.minsu.services.order.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.util.List;

/**
 * <p>评价或者初见的评价状态</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/10.
 * @version 1.0
 * @since 1.0
 */
public class EvalSynRequest extends BaseEntity {

    private static final long serialVersionUID = 30968412301446703L;


    /**
     * 订单集合
     */
    private List<String> listOrderSn;


    /**
     * 初见状态
     */
    private Integer cjStatus;


    /**
     * 评价状态
     */
    private Integer evlStatus;


    public List<String> getListOrderSn() {
        return listOrderSn;
    }

    public void setListOrderSn(List<String> listOrderSn) {
        this.listOrderSn = listOrderSn;
    }

    public Integer getCjStatus() {
        return cjStatus;
    }

    public void setCjStatus(Integer cjStatus) {
        this.cjStatus = cjStatus;
    }

    public Integer getEvlStatus() {
        return evlStatus;
    }

    public void setEvlStatus(Integer evlStatus) {
        this.evlStatus = evlStatus;
    }
}
