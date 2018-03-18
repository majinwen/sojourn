package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 *
 *
 * @Author: wangxm113
 * @Date: 2017年11月04日 17时52分20秒
 */
public class SurrenderCostEntity extends BaseEntity {
    /**
     * 物业交割ID
     */
    private String surrendercostId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 解约协议id
     */
    private String surrenderId;

    /**
     * 本结算单对应的退租类型(主要用于与解约协议主表比较是否一致,不一致重新生成解约协议)
     */
    private String fsurtype;

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
     * 乙方开户人姓名
     */
    private String faccountname;

    /**
     * 乙方银行名称
     */
    private String fbankname;

    /**
     * 乙方银行账号
     */
    private String fbankaccount;

    /**
     * 解约ZO
     */
    private String fhandlezo;

    /**
     * 付款状态 0:未付款 1:已付款 （财务审核状态）
     */
    private Integer fpaymentstatus;

    /**
     * 收款状态 0:未收款 1:已收款 （财务审核状态）
     */
    private Integer fgatheringstatus;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;

    /**
     * 全国化预留字段
     */
    private String fcity;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建ID
     */
    private String createrid;

    /**
     * 更新时间
     */
    private Date fupdatetime;

    /**
     * 修改ID
     */
    private String updaterid;

    /**
     * 支票号
     */
    private String fchequeno;

    /**
     * 财务审核状态 [0 待审核 ,1 审核未通过 , 2 审核通过]
     */
    private Integer ffinanceauditstatus;

    /**
     * 财务审核人ID
     */
    private String financeauditorid;

    /**
     * 财务审核人Name
     */
    private String financeauditorname;

    /**
     * 确认付款人ID
     */
    private String confirmpayerid;

    /**
     * 确认付款人name
     */
    private String confirmpayername;

    /**
     * 财务审核时间
     */
    private Date ffinanceauditdate;

    /**
     * 确认付款时间
     */
    private Date fconpaymentdate;

    /**
     * 实际付款时间
     */
    private Date factualpaymentdate;

    /**
     * 备注信息
     */
    private String fremark;

    private String fpaymentmode;

    /**
     * 公司开户银行名称
     */
    private String fcombankname;

    /**
     * 公司开户银行账号
     */
    private String fcombankaccount;

    /**
     * 已缴租金到期日-标志费用结算单是否需要重新计算呢
     */
    private String frentenddate;

    /**
     * 关联城市表
     */
    private String cityid;

    public String getSurrendercostId() {
        return surrendercostId;
    }

    public void setSurrendercostId(String surrendercostId) {
        this.surrendercostId = surrendercostId == null ? null : surrendercostId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getSurrenderId() {
        return surrenderId;
    }

    public void setSurrenderId(String surrenderId) {
        this.surrenderId = surrenderId == null ? null : surrenderId.trim();
    }

    public String getFsurtype() {
        return fsurtype;
    }

    public void setFsurtype(String fsurtype) {
        this.fsurtype = fsurtype == null ? null : fsurtype.trim();
    }

    public String getFresponsibility() {
        return fresponsibility;
    }

    public void setFresponsibility(String fresponsibility) {
        this.fresponsibility = fresponsibility == null ? null : fresponsibility.trim();
    }

    public String getFcostremark() {
        return fcostremark;
    }

    public void setFcostremark(String fcostremark) {
        this.fcostremark = fcostremark == null ? null : fcostremark.trim();
    }

    public String getFpayer() {
        return fpayer;
    }

    public void setFpayer(String fpayer) {
        this.fpayer = fpayer == null ? null : fpayer.trim();
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
        this.faccountname = faccountname == null ? null : faccountname.trim();
    }

    public String getFbankname() {
        return fbankname;
    }

    public void setFbankname(String fbankname) {
        this.fbankname = fbankname == null ? null : fbankname.trim();
    }

    public String getFbankaccount() {
        return fbankaccount;
    }

    public void setFbankaccount(String fbankaccount) {
        this.fbankaccount = fbankaccount == null ? null : fbankaccount.trim();
    }

    public String getFhandlezo() {
        return fhandlezo;
    }

    public void setFhandlezo(String fhandlezo) {
        this.fhandlezo = fhandlezo == null ? null : fhandlezo.trim();
    }

    public Integer getFpaymentstatus() {
        return fpaymentstatus;
    }

    public void setFpaymentstatus(Integer fpaymentstatus) {
        this.fpaymentstatus = fpaymentstatus;
    }

    public Integer getFgatheringstatus() {
        return fgatheringstatus;
    }

    public void setFgatheringstatus(Integer fgatheringstatus) {
        this.fgatheringstatus = fgatheringstatus;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
    }

    public Integer getFvalid() {
        return fvalid;
    }

    public void setFvalid(Integer fvalid) {
        this.fvalid = fvalid;
    }

    public String getFcity() {
        return fcity;
    }

    public void setFcity(String fcity) {
        this.fcity = fcity == null ? null : fcity.trim();
    }

    public Date getFcreatetime() {
        return fcreatetime;
    }

    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    public String getCreaterid() {
        return createrid;
    }

    public void setCreaterid(String createrid) {
        this.createrid = createrid == null ? null : createrid.trim();
    }

    public Date getFupdatetime() {
        return fupdatetime;
    }

    public void setFupdatetime(Date fupdatetime) {
        this.fupdatetime = fupdatetime;
    }

    public String getUpdaterid() {
        return updaterid;
    }

    public void setUpdaterid(String updaterid) {
        this.updaterid = updaterid == null ? null : updaterid.trim();
    }

    public String getFchequeno() {
        return fchequeno;
    }

    public void setFchequeno(String fchequeno) {
        this.fchequeno = fchequeno == null ? null : fchequeno.trim();
    }

    public Integer getFfinanceauditstatus() {
        return ffinanceauditstatus;
    }

    public void setFfinanceauditstatus(Integer ffinanceauditstatus) {
        this.ffinanceauditstatus = ffinanceauditstatus;
    }

    public String getFinanceauditorid() {
        return financeauditorid;
    }

    public void setFinanceauditorid(String financeauditorid) {
        this.financeauditorid = financeauditorid == null ? null : financeauditorid.trim();
    }

    public String getFinanceauditorname() {
        return financeauditorname;
    }

    public void setFinanceauditorname(String financeauditorname) {
        this.financeauditorname = financeauditorname == null ? null : financeauditorname.trim();
    }

    public String getConfirmpayerid() {
        return confirmpayerid;
    }

    public void setConfirmpayerid(String confirmpayerid) {
        this.confirmpayerid = confirmpayerid == null ? null : confirmpayerid.trim();
    }

    public String getConfirmpayername() {
        return confirmpayername;
    }

    public void setConfirmpayername(String confirmpayername) {
        this.confirmpayername = confirmpayername == null ? null : confirmpayername.trim();
    }

    public Date getFfinanceauditdate() {
        return ffinanceauditdate;
    }

    public void setFfinanceauditdate(Date ffinanceauditdate) {
        this.ffinanceauditdate = ffinanceauditdate;
    }

    public Date getFconpaymentdate() {
        return fconpaymentdate;
    }

    public void setFconpaymentdate(Date fconpaymentdate) {
        this.fconpaymentdate = fconpaymentdate;
    }

    public Date getFactualpaymentdate() {
        return factualpaymentdate;
    }

    public void setFactualpaymentdate(Date factualpaymentdate) {
        this.factualpaymentdate = factualpaymentdate;
    }

    public String getFremark() {
        return fremark;
    }

    public void setFremark(String fremark) {
        this.fremark = fremark == null ? null : fremark.trim();
    }

    public String getFpaymentmode() {
        return fpaymentmode;
    }

    public void setFpaymentmode(String fpaymentmode) {
        this.fpaymentmode = fpaymentmode == null ? null : fpaymentmode.trim();
    }

    public String getFcombankname() {
        return fcombankname;
    }

    public void setFcombankname(String fcombankname) {
        this.fcombankname = fcombankname == null ? null : fcombankname.trim();
    }

    public String getFcombankaccount() {
        return fcombankaccount;
    }

    public void setFcombankaccount(String fcombankaccount) {
        this.fcombankaccount = fcombankaccount == null ? null : fcombankaccount.trim();
    }

    public String getFrentenddate() {
        return frentenddate;
    }

    public void setFrentenddate(String frentenddate) {
        this.frentenddate = frentenddate == null ? null : frentenddate.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }
}