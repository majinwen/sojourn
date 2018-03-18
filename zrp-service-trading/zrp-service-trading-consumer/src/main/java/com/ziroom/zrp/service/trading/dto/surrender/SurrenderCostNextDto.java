package com.ziroom.zrp.service.trading.dto.surrender;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.trading.entity.SurrenderCostSumBodyVo;
import com.ziroom.zrp.trading.entity.SurrenderCostItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>解约协议-费用结算点击下一步时封装的dto</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月16日 14时24分
 * @Version 1.0
 * @Since 1.0
 */
public class SurrenderCostNextDto extends BaseEntity {
    /**
     * 物业交割ID
     */
    private String surrendercostId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 解约协议ID
     */
    private String surrenderId;

    /**
     * 父解约协议id(防止编号生成出问题)
     */
    private String surParentId;

    /**
     * 本结算单对应的退租类型(主要用于与解约协议主表比较是否一致,不一致重新生成解约协议)
     */
    private String fsurtype;

    /**
     * 已缴租金到期日-标志费用结算单是否需要重新计算呢
     */
    private String frentenddate;

    /**
     * 解约责任方 [0 公司，1 租客]
     */
    private String fresponsibility;

    /**
     * 费用结算单remark
     */
    private String fcostremark;

    /**
     * 支付方  [0 公司，1 租客]
     */
    private String fpayer;

    /**
     * 结算金额
     */
    private Double fsettlementamount;

    /**
     * 是否为修改（0：新增；1：修改）
     */
    private Integer isEdit;

    /**
     * 乙方开户人姓名
     */
    private String faccountname;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 乙方银行名称
     */
    private String fbankname;

    /**
     * 乙方银行账号
     */
    private String fbankaccount;

    /**
     * 付款对象类型 1客户
     */
    private String cusType;

    /**
     * 付款对象名称
     */
    private String cusName;

    /**
     * 付款手机号
     */
    private String cusMobile;

    /**
     * 付款方式
     */
    private String refundType;

    /**
     * 是否临时保存（0：不是；1：是）
     * 批量解约时，保存单个合同解约信息是不需要生成应收账单/付款单
     */
    private int isTemp;

    /**
     * 费用项信息surrenderCostItem，当批解保存单个合同或者个人解约点击下一步时需要
     */
    private int needReCal;//是否需要重新计算（0：不需要；1：需要）
    private String surrCotItemJson;
    private List<SurrenderCostSumBodyVo> costItemEntityList;

    /**
     * zo
     */
    private String zoId;
    private String zoName;

    private String cityId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getNeedReCal() {
        return needReCal;
    }

    public void setNeedReCal(int needReCal) {
        this.needReCal = needReCal;
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId;
    }

    public Integer getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public String getZoId() {
        return zoId;
    }

    public void setZoId(String zoId) {
        this.zoId = zoId;
    }

    public String getZoName() {
        return zoName;
    }

    public void setZoName(String zoName) {
        this.zoName = zoName;
    }

    public String getSurrendercostId() {
        return surrendercostId;
    }

    public void setSurrendercostId(String surrendercostId) {
        this.surrendercostId = surrendercostId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSurParentId() {
        return surParentId;
    }

    public void setSurParentId(String surParentId) {
        this.surParentId = surParentId;
    }

    public String getFsurtype() {
        return fsurtype;
    }

    public void setFsurtype(String fsurtype) {
        this.fsurtype = fsurtype;
    }

    public String getFrentenddate() {
        return frentenddate;
    }

    public void setFrentenddate(String frentenddate) {
        this.frentenddate = frentenddate;
    }

    public String getFresponsibility() {
        return fresponsibility;
    }

    public void setFresponsibility(String fresponsibility) {
        this.fresponsibility = fresponsibility;
    }

    public String getFcostremark() {
        return fcostremark;
    }

    public void setFcostremark(String fcostremark) {
        this.fcostremark = fcostremark;
    }

    public String getFpayer() {
        return fpayer;
    }

    public void setFpayer(String fpayer) {
        this.fpayer = fpayer;
    }

    public Double getFsettlementamount() {
        return fsettlementamount;
    }

    public void setFsettlementamount(Double fsettlementamount) {
        this.fsettlementamount = fsettlementamount;
    }

    public String getFaccountname() {
        return faccountname;
    }

    public void setFaccountname(String faccountname) {
        this.faccountname = faccountname;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getFbankname() {
        return fbankname;
    }

    public void setFbankname(String fbankname) {
        this.fbankname = fbankname;
    }

    public String getFbankaccount() {
        return fbankaccount;
    }

    public void setFbankaccount(String fbankaccount) {
        this.fbankaccount = fbankaccount;
    }

    public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusMobile() {
        return cusMobile;
    }

    public void setCusMobile(String cusMobile) {
        this.cusMobile = cusMobile;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public int getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(int isTemp) {
        this.isTemp = isTemp;
    }

    public String getSurrCotItemJson() {
        return surrCotItemJson;
    }

    public void setSurrCotItemJson(String surrCotItemJson) {
        this.surrCotItemJson = surrCotItemJson;
    }

    public List<SurrenderCostSumBodyVo> getCostItemEntityList() {
        return costItemEntityList;
    }

    public void setCostItemEntityList(List<SurrenderCostSumBodyVo> costItemEntityList) {
        this.costItemEntityList = costItemEntityList;
    }
}
