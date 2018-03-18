package com.ziroom.zrp.service.trading.dto.surrender;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月30日 15时42分
 * @Version 1.0
 * @Since 1.0
 */
public class SurRoomListDto {
    private Integer pageNum;
    private Integer rows;
    private String surParentId;
    private String contractId;
    private String surrenderId;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSurParentId() {
        return surParentId;
    }

    public void setSurParentId(String surParentId) {
        this.surParentId = surParentId;
    }
}
