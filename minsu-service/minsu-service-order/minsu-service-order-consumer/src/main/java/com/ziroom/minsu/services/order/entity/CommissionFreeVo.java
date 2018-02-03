package com.ziroom.minsu.services.order.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>免佣金的逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 16/10/9.
 * @version 1.0
 * @since 1.0
 */
public class CommissionFreeVo extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 4564232557707L;


    /**
     * 默认不免用尽
     */
    private Integer freeFlag = YesOrNoEnum.NO.getCode();


    /**
     * 当前的免用尽的code
     */
    private String freeCode;

    /**
     * 当前的免佣金的名称
     */
    private String freeName;

    public Integer getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    public String getFreeCode() {
        return freeCode;
    }

    public void setFreeCode(String freeCode) {
        this.freeCode = freeCode;
    }

    public String getFreeName() {
        return freeName;
    }

    public void setFreeName(String freeName) {
        this.freeName = freeName;
    }
}
