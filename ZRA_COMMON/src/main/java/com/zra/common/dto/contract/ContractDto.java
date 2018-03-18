package com.zra.common.dto.contract;

import java.util.Date;

/**
 * 合同dto
 */
public class ContractDto {

    private String id;//zo id

    private String renewState;//续约状态

    private Integer contractType;//合同类型（长租 短租）

    private Integer days;//距到期日的天数

    private Date endDate;//到期日
    
    private String roomId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRenewState() {
        return renewState;
    }

    public void setRenewState(String renewState) {
        this.renewState = renewState;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    
}
