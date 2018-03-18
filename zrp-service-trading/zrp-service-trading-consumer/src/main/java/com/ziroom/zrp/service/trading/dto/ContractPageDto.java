package com.ziroom.zrp.service.trading.dto;

import com.zra.common.dto.base.BasePageParamDto;

/**
 * <p>分页查询</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月28日 16:57
 * @since 1.0
 */
public class ContractPageDto extends BasePageParamDto{
    /**
     * 父合同id
     */
    private String surParentRentId;

    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }
}
