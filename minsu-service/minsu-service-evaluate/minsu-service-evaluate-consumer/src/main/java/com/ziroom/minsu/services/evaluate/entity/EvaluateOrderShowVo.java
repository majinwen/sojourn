package com.ziroom.minsu.services.evaluate.entity;

import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;

/**
 * <p>带显示状态的记录</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EvaluateOrderShowVo extends EvaluateOrderEntity{

    private static final long serialVersionUID = -4666284332992660417L;
    /**
     * 显示状态 0=不显示 1=显示
     */
    private Integer showStatus;

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }
}
