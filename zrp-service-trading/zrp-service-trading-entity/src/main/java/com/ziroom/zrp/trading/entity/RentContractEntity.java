package com.ziroom.zrp.trading.entity;

import com.asura.framework.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同实体类
 *
 * @param
 * @author jixd
 * @created 2017年09月07日 19:50:47
 * @return
 */
public class RentContractEntity extends BaseEntity {

	private static final long serialVersionUID = -2801203931962218718L;

	/**
     * 系统合同ID
     */
    private String contractId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 1-年租 2-月租 3-日租 @see com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum
     */
    private String conType;

    /**
     * 出房合同号
     */
    private String conRentCode;

    /**
     * 前合同号
     */
    private String preConRentCode;

    /**
     * 承租合同签署日期：签约时间
     */
    private Date conSignDate;

    /**
     * 合同生效日期：起租日期？
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conStartDate;

    /**
     * 合同截止日期：到期日期？
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conEndDate;

    /**
     * 解约日期：退租日期
     */
    private Date conReleaseDate;

    /**
     * 押金
     */
    private Double conDeposit;

    /**
     * 应缴服务费
     *
     */
    private Double conCommission;

    /**
     * 实际应收服务费
     * @deprecated
     */
    private Double conMustCommission;

    /**
     * 合同状态 @See com.ziroom.zrp.service.trading.valenum.ContractStatusEnum
     */
    private String conStatusCode;

    /**
     * 原合同状态（供更新合同信息时使用）
     */
    private transient String preConStatusCode;

    /**
     * 审核状态：0:待审核；1：审核驳回；2：审核通过 @see com.ziroom.zrp.service.trading.valenum.ContractAuditStatusEnum
     */
    private String conAuditState;

    /**
     * 审核人
     */
    private String conAuditer;

    /**
     * 签约周期：0 15天及以内；1 1个月；2 2个月；3 3个月；4 4个月；5 5个月；6 6个月；12 一年
     */
    private Integer conRentYear;

    /**
     * 付款周期：1 月付 3 季付 6 半年付 12 年付 9 一次性付清（短租）
     */
    private String conCycleCode;

    /**
     * 备注
     */
    private String conRemark;

    /**
     * 操作原因(审核未通过原因)
     */
    private String conNoRenewalReason;

    /**
     * 项目名称
     */
    private String proName;
    /**
     * 房间id
     */
    private String roomId;


    /**
     * 计划出房价
     */
    private Double roomSalesPrice;


    /**
     * 门牌号 房间号
     */
    private String houseRoomNo;

    /**
     * 客户类型：1 普通个人客户 2 企悦会员工 3 企业客户
     */
    private Integer customerType;

    private String customerId;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 客户手机 签约手机号
     */
    private String customerMobile;

    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建人
     */
    private String createrid;

    /**
     * 修改时间
     */
    private Date fupdatetime;

    /**
     * 修改人
     */
    private String updaterid;

    /**
     * 是否删除
     */
    private Integer fisdel;

    /**
     * 是否有效
     */
    private Integer fvalid;


    /**
     * 实际出房价：合同签约的价格
     */
    private Double factualprice;

    /**
     * 服务费折扣
     */
    private Double fdiscountserprice;

    /**
     * 优惠后服务费
     */
    private Double fserviceprice;

    /**
     * 城市id
     */
    private String cityid;

    /**
     * 客户库uid
     */
    private String customerUid;

    /**
     * 活动：该合同参与的活动 FK 与活动表关联
     */
    private Integer activityId;

    /**
     * 活动优惠金额
     */
    private String activitymoney;

    /**
     * 办理ZO：管家
     */
    private String fhandlezo;

    /**
     * 办理ZO系统号
     */
    private String fhandlezocode;

    /**
     * 办理时间：录入合同时间
     */
    private Date fhandletime;


    /**
     * 解约协议id：FK
     */
    private String freleasecontractid;

    /**
     * 合同文件
     */
    private String fcontractfile;

    /**
     * 身份证件照片
     */
    private String fcertcardpic;

    /**
     * 社会资质照片
     */
    private String fsocialcertpic;


    /**
     * 签约类型(SignContractTypeEnum),0:新签.1:续约.2:换租
     */
    private String fsigntype;

    /**
     * 审核人系统号
     */
    private String fconauditercode;

    /**
     * 短租租期
     */
    private String frentmonth;

    /**
     * 审核日期
     */
    private Date fconauditdate;

    /**
     * 计算年(月)出租率时是否考虑  0:否 1:是
     */
    private Integer fiscalculate;

    /**
     * 解约之后保存合同之前的状态
     */
    private String foriginalstate;


    /**
     * 解约协议id
     */
    private String fsurrenderid;

    /**
     * 合同类别 1 出房合同;2 收房合同
     */
    private String fcontractcategory;

    /**
     * 签合同来源:空或1为自如寓管理后台;2为M站
     */
    private String fsource;

    /**
     * 续约状态
     */
    private Integer isRenew;

    /**
     * 开放预定时间
     */
    private Date canPreDate;

    /**
     * 租客可优先预约时间
     */
    private Date canRenewDate;

    /**
     * 解约时候申请日期，始终保持与首次申请的日期相同
     */
    private Date fapplicationdate;

    /**
     * 预计退租日期，始终保持与首次退租的日期相同
     */
    private Date fexpecteddate;

    /**
     * 委托书照片地址
     */
    private String proxyPicurl;

    /**
     * 合同文本版本
     */
    private String conTplVersion;
    /**
     * 首次支付时间
     */
    private Date firstPayTime;

    /**
     * 提交合同时间
     */
    private Date submitContractTime;

    /**
     * 首期账单金额
     */
    private BigDecimal firstPeriodMoney;

    /**
     * 父合同id
     */
    private String surParentRentId;

    /**
     * 父合同号
     */
    private String surParentRentCode;

    /**
     * 前合同的父合同号
     */
    private String preSurParentRentId;

    /**
     *是否同步给财务:0-未同步;1-成功 2-失败
     */
    private Integer isSyncToFin;

    /**
     * 关闭类型  1-用户手动关闭 2-定时任务关闭 3-管家后台关闭
     */
    private Integer closeType;

    /**
     *是否可以修改
     */
    private Integer isPossibleModify;
    /**
     * 数据版本
     */
    private Integer dataVersion;

    /**
     * 子合同是否已生成pdf
     */
    private Integer isTransferPdf;

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getConRentCode() {
        return conRentCode;
    }

    public void setConRentCode(String conRentCode) {
        this.conRentCode = conRentCode;
    }

    public String getPreConRentCode() {
        return preConRentCode;
    }

    public void setPreConRentCode(String preConRentCode) {
        this.preConRentCode = preConRentCode;
    }

    public Date getConSignDate() {
        return conSignDate;
    }

    public void setConSignDate(Date conSignDate) {
        this.conSignDate = conSignDate;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public Date getConEndDate() {
        return conEndDate;
    }

    public void setConEndDate(Date conEndDate) {
        this.conEndDate = conEndDate;
    }

    public Date getConReleaseDate() {
        return conReleaseDate;
    }

    public void setConReleaseDate(Date conReleaseDate) {
        this.conReleaseDate = conReleaseDate;
    }

    public Double getConDeposit() {
        return conDeposit;
    }

    public void setConDeposit(Double conDeposit) {
        this.conDeposit = conDeposit;
    }

    public Double getConCommission() {
        return conCommission;
    }

    public void setConCommission(Double conCommission) {
        this.conCommission = conCommission;
    }

    /**
     *
     * @deprecated
     *
     * */
    public Double getConMustCommission() {
        return conMustCommission;
    }

    /**
     *
     * @deprecated
     *
     * */
    public void setConMustCommission(Double conMustCommission) {
        this.conMustCommission = conMustCommission;
    }

    public String getConStatusCode() {
        return conStatusCode;
    }

    public void setConStatusCode(String conStatusCode) {
        this.conStatusCode = conStatusCode;
    }

    public String getPreConStatusCode() {
        return preConStatusCode;
    }

    public void setPreConStatusCode(String preConStatusCode) {
        this.preConStatusCode = preConStatusCode;
    }

    public String getConAuditState() {
        return conAuditState;
    }

    public void setConAuditState(String conAuditState) {
        this.conAuditState = conAuditState;
    }

    public String getConAuditer() {
        return conAuditer;
    }

    public void setConAuditer(String conAuditer) {
        this.conAuditer = conAuditer;
    }

    public Integer getConRentYear() {
        return conRentYear;
    }

    public void setConRentYear(Integer conRentYear) {
        this.conRentYear = conRentYear;
    }

    public String getConCycleCode() {
        return conCycleCode;
    }

    public void setConCycleCode(String conCycleCode) {
        this.conCycleCode = conCycleCode;
    }

    public String getConRemark() {
        return conRemark;
    }

    public void setConRemark(String conRemark) {
        this.conRemark = conRemark;
    }

    public String getConNoRenewalReason() {
        return conNoRenewalReason;
    }

    public void setConNoRenewalReason(String conNoRenewalReason) {
        this.conNoRenewalReason = conNoRenewalReason;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Double getRoomSalesPrice() {
        return roomSalesPrice;
    }

    public void setRoomSalesPrice(Double roomSalesPrice) {
        this.roomSalesPrice = roomSalesPrice;
    }

    public String getHouseRoomNo() {
        return houseRoomNo;
    }

    public void setHouseRoomNo(String houseRoomNo) {
        this.houseRoomNo = houseRoomNo;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
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
        this.createrid = createrid;
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
        this.updaterid = updaterid;
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

    public Double getFactualprice() {
        return factualprice;
    }

    public void setFactualprice(Double factualprice) {
        this.factualprice = factualprice;
    }

    public Double getFserviceprice() {
        return fserviceprice;
    }

    public void setFserviceprice(Double fserviceprice) {
        this.fserviceprice = fserviceprice;
    }

    public Double getFdiscountserprice() {
        return fdiscountserprice;
    }

    public void setFdiscountserprice(Double fdiscountserprice) {
        this.fdiscountserprice = fdiscountserprice;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivitymoney() {
        return activitymoney;
    }

    public void setActivitymoney(String activitymoney) {
        this.activitymoney = activitymoney;
    }

    public String getFhandlezo() {
        return fhandlezo;
    }

    public void setFhandlezo(String fhandlezo) {
        this.fhandlezo = fhandlezo;
    }

    public String getFhandlezocode() {
        return fhandlezocode;
    }

    public void setFhandlezocode(String fhandlezocode) {
        this.fhandlezocode = fhandlezocode;
    }

    public Date getFhandletime() {
        return fhandletime;
    }

    public void setFhandletime(Date fhandletime) {
        this.fhandletime = fhandletime;
    }

    public String getFreleasecontractid() {
        return freleasecontractid;
    }

    public void setFreleasecontractid(String freleasecontractid) {
        this.freleasecontractid = freleasecontractid;
    }

    public String getFcontractfile() {
        return fcontractfile;
    }

    public void setFcontractfile(String fcontractfile) {
        this.fcontractfile = fcontractfile;
    }

    public String getFcertcardpic() {
        return fcertcardpic;
    }

    public void setFcertcardpic(String fcertcardpic) {
        this.fcertcardpic = fcertcardpic;
    }

    public String getFsocialcertpic() {
        return fsocialcertpic;
    }

    public void setFsocialcertpic(String fsocialcertpic) {
        this.fsocialcertpic = fsocialcertpic;
    }

    public String getFsigntype() {
        return fsigntype;
    }

    public void setFsigntype(String fsigntype) {
        this.fsigntype = fsigntype;
    }

    public String getFconauditercode() {
        return fconauditercode;
    }

    public void setFconauditercode(String fconauditercode) {
        this.fconauditercode = fconauditercode;
    }

    public String getFrentmonth() {
        return frentmonth;
    }

    public void setFrentmonth(String frentmonth) {
        this.frentmonth = frentmonth;
    }

    public Date getFconauditdate() {
        return fconauditdate;
    }

    public void setFconauditdate(Date fconauditdate) {
        this.fconauditdate = fconauditdate;
    }

    public Integer getFiscalculate() {
        return fiscalculate;
    }

    public void setFiscalculate(Integer fiscalculate) {
        this.fiscalculate = fiscalculate;
    }

    public String getForiginalstate() {
        return foriginalstate;
    }

    public void setForiginalstate(String foriginalstate) {
        this.foriginalstate = foriginalstate;
    }

    public String getFsurrenderid() {
        return fsurrenderid;
    }

    public void setFsurrenderid(String fsurrenderid) {
        this.fsurrenderid = fsurrenderid;
    }

    public String getFcontractcategory() {
        return fcontractcategory;
    }

    public void setFcontractcategory(String fcontractcategory) {
        this.fcontractcategory = fcontractcategory;
    }

    public String getFsource() {
        return fsource;
    }

    public void setFsource(String fsource) {
        this.fsource = fsource;
    }

    public Integer getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(Integer isRenew) {
        this.isRenew = isRenew;
    }

    public Date getCanPreDate() {
        return canPreDate;
    }

    public void setCanPreDate(Date canPreDate) {
        this.canPreDate = canPreDate;
    }

    public Date getCanRenewDate() {
        return canRenewDate;
    }

    public void setCanRenewDate(Date canRenewDate) {
        this.canRenewDate = canRenewDate;
    }

    public Date getFapplicationdate() {
        return fapplicationdate;
    }

    public void setFapplicationdate(Date fapplicationdate) {
        this.fapplicationdate = fapplicationdate;
    }

    public Date getFexpecteddate() {
        return fexpecteddate;
    }

    public void setFexpecteddate(Date fexpecteddate) {
        this.fexpecteddate = fexpecteddate;
    }

    public String getProxyPicurl() {
        return proxyPicurl;
    }

    public void setProxyPicurl(String proxyPicurl) {
        this.proxyPicurl = proxyPicurl;
    }

    public String getConTplVersion() {
        return conTplVersion;
    }

    public void setConTplVersion(String conTplVersion) {
        this.conTplVersion = conTplVersion;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getFirstPayTime() {
        return firstPayTime;
    }

    public void setFirstPayTime(Date firstPayTime) {
        this.firstPayTime = firstPayTime;
    }

    public Date getSubmitContractTime() {
        return submitContractTime;
    }

    public void setSubmitContractTime(Date submitContractTime) {
        this.submitContractTime = submitContractTime;
    }

    public BigDecimal getFirstPeriodMoney() {
        return firstPeriodMoney;
    }

    public void setFirstPeriodMoney(BigDecimal firstPeriodMoney) {
        this.firstPeriodMoney = firstPeriodMoney;
    }

    public String getSurParentRentId() {
        return surParentRentId;
    }

    public void setSurParentRentId(String surParentRentId) {
        this.surParentRentId = surParentRentId;
    }

    public String getSurParentRentCode() {
        return surParentRentCode;
    }

    public void setSurParentRentCode(String surParentRentCode) {
        this.surParentRentCode = surParentRentCode;
    }

    public Integer getIsSyncToFin() {
        return isSyncToFin;
    }

    public void setIsSyncToFin(Integer isSyncToFin) {
        this.isSyncToFin = isSyncToFin;
    }

    public Integer getIsPossibleModify() {
        return isPossibleModify;
    }

    public void setIsPossibleModify(Integer isPossibleModify) {
        this.isPossibleModify = isPossibleModify;
    }

    public String getPreSurParentRentId() {
        return preSurParentRentId;
    }

    public void setPreSurParentRentId(String preSurParentRentId) {
        this.preSurParentRentId = preSurParentRentId;
    }

    public Integer getIsTransferPdf() {
        return isTransferPdf;
    }

    public void setIsTransferPdf(Integer isTransferPdf) {
        this.isTransferPdf = isTransferPdf;
    }
}