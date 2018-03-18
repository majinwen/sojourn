package com.ziroom.zrp.service.trading.dto;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2018年01月19日 10:50
 * @since 1.0
 */
public class RentRoomDto extends BaseEntity {

    private String surParentRentId;


    /**
     * 业务类型:
     * @see com.ziroom.zrp.service.trading.valenum.BussTypeEnum
     **/
    private Integer bussType;

    public RentRoomDto() {

    }

    public RentRoomDto(String surParentRentId, Integer bussType) {
        this.surParentRentId = surParentRentId;
        this.bussType = bussType;
    }

    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }

    public Integer getBussType() {
        return bussType;
    }

    public void setBussType(Integer bussType) {
        this.bussType = bussType;
    }
}
