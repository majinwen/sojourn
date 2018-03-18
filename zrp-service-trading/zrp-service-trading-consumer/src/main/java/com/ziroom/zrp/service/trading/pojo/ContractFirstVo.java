package com.ziroom.zrp.service.trading.pojo;

import java.util.List;

/**
 * <p>第一步保存活动时返回的vo</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月24日 14:51
 * @since 1.0
 */
public class ContractFirstVo {


    //父合同id
    private String surParentRentId;

    //子合同id
    private List<String> contractIdList;


    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }

    public List<String> getContractIdList() {
        return contractIdList;
    }

    public void setContractIdList(List<String> contractIdList) {
        this.contractIdList = contractIdList;
    }
}
