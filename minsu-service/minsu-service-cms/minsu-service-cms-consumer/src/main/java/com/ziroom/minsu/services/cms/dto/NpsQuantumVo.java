package com.ziroom.minsu.services.cms.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/7/11 15:16
 * @version 1.0
 * @since 1.0
 */
public class NpsQuantumVo extends BaseEntity {

    private static final long serialVersionUID = -5544177057793705331L;


    /**
     * NPS 值
     */
    private Double npsValue;

    /**
     * 推荐者百分比
     */
    private Double commendPercent;

    /**
     * 批评者百分比
     */
    private Double criticismPercent;

    public Double getNpsValue() {
        return npsValue;
    }

    public void setNpsValue(Double npsValue) {
        this.npsValue = npsValue;
    }

    public Double getCommendPercent() {
        return commendPercent;
    }

    public void setCommendPercent(Double commendPercent) {
        this.commendPercent = commendPercent;
    }

    public Double getCriticismPercent() {
        return criticismPercent;
    }

    public void setCriticismPercent(Double criticismPercent) {
        this.criticismPercent = criticismPercent;
    }
}
