package com.ziroom.minsu.services.order.entity;

import com.ziroom.minsu.entity.order.FinancePenaltyEntity;

/**
 * 罚款单列表展示
 * @author jixd
 * @created 2017年05月15日 10:58:40
 * @param
 * @return
 */
public class PenaltyVo extends FinancePenaltyEntity{

    /**
     * 房东姓名
     */
    private String landlordName;
    /**
     * 房东电话
     */
    private String landlordTel;

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getLandlordTel() {
        return landlordTel;
    }

    public void setLandlordTel(String landlordTel) {
        this.landlordTel = landlordTel;
    }
}
