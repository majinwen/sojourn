package com.ziroom.minsu.services.search.vo;

import com.asura.framework.base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>评论的数据库信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class EvaluateDbInfoVo extends BaseEntity {

    /** 序列化id */
    private static final long serialVersionUID = 136255215641L;

    /** 整洁卫生星级平均值 */
    private Double houseCleanAva = 5.00;

    /** 描述相符星级平均值 */
    private Double desMatchAva = 5.00;

    /** 安全程度星级平均值 */
    private Double safeDegreeAva = 5.00;

    /** 交通位置星级平均值 */
    private Double trafPosAva = 5.00;

    /** 性价比星级平均值 */
    private Double costPerforAva = 5.00;

    /** 评价总数 */
    private Integer  evaTotal = 0;

    public Integer getEvaTotal() {
        return evaTotal;
    }

    public void setEvaTotal(Integer evaTotal) {
        this.evaTotal = evaTotal;
    }

    public Double getHouseCleanAva() {
        return houseCleanAva;
    }

    public void setHouseCleanAva(Double houseCleanAva) {
        this.houseCleanAva = houseCleanAva;
    }

    public Double getDesMatchAva() {
        return desMatchAva;
    }

    public void setDesMatchAva(Double desMatchAva) {
        this.desMatchAva = desMatchAva;
    }

    public Double getSafeDegreeAva() {
        return safeDegreeAva;
    }

    public void setSafeDegreeAva(Double safeDegreeAva) {
        this.safeDegreeAva = safeDegreeAva;
    }

    public Double getTrafPosAva() {
        return trafPosAva;
    }

    public void setTrafPosAva(Double trafPosAva) {
        this.trafPosAva = trafPosAva;
    }

    public Double getCostPerforAva() {
        return costPerforAva;
    }

    public void setCostPerforAva(Double costPerforAva) {
        this.costPerforAva = costPerforAva;
    }
}
