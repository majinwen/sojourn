package com.ziroom.zrp.service.trading.dto;

import com.asura.framework.base.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>房租账单信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月10日 15:12
 * @since 1.0
 */
public class RentFinReceiBillDto extends BaseEntity{

    private String contractId;

    private Integer payNum;

    private Date planGatherDate;

    private String roomId;

    private BigDecimal oughtAmount;


    public RentFinReceiBillDto() {

    }


    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public Date getPlanGatherDate() {
        return planGatherDate;
    }

    public void setPlanGatherDate(Date planGatherDate) {
        this.planGatherDate = planGatherDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public BigDecimal getOughtAmount() {
        return oughtAmount;
    }

    public void setOughtAmount(BigDecimal oughtAmount) {
        this.oughtAmount = oughtAmount;
    }
}
