package com.ziroom.zrp.trading.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
/**
 * <p>收款单实体</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月16日
 * @since 1.0
 */
public class RentPaymentVoucherEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1608745101852311590L;

	/**
     * 主键
     */
    private Integer voucherId;

    /**
     * 付款计划ID
     */
    private Integer paymentId;

    /**
     * [预留]费用单ID:
     */
    private Integer costId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 活动ID
     */
    private Integer activityId;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 收款单编号
     */
    private String paymentCode;

    /**
     * 收款类型(1合同首次;2合同非首次;3费用账单;9其它收款)
     */
    private String firstPay;

    /**
     * 收款渠 在线支付、门店、外勤等
     */
    private String collectionChannel;

    /**
     * 合同号
     */
    private String rentContractCode;

    /**
     * 项目
     */
    private String projectName;

    /**
     * 房间
     */
    private String roomName;

    /**
     * 客户
     */
    private String customerName;

    /**
     * 同笔第几次
     */
    private Integer paymentNum;

    /**
     * 应收金额
     */
    private Double payableRental;

    /**
     * 收款日期 [应收日期]
     */
    private Date paymentTime;

    /**
     * 收款日期  实际收款日期
     */
    private Date realityReceipt;

    /**
     * 制单时间
     */
    private Date createDate;

    /**
     * 确认日期 打款日期
     */
    private Date collectionDate;

    /**
     * 收款方式 在线支付、POS机、支票、银行汇款、微信支付、转款等
     */
    private String collectionWay;

    /**
     * POS机类型 工行POS、中行POS、民生POS、混合POS等
     */
    private String collectionWayDetail;

    /**
     * 在线方式子类:
1 支付宝
2 银联
3 微信
     */
    private Integer onlinePaymentWay;

    /**
     * tpossetting
     */
    private String posId;

    /**
     * 终端号 [不确定]
     */
    private String posCode;

    /**
     * 参考号
     */
    private String refnum;

    /**
     * 付款人
     */
    private String paymentorName;

    /**
     * 付款账号
     */
    private String collectionCardNumber;

    /**
     * 收款流水号
     */
    private String serialNo;

    /**
     * 是否需要发票 是否需要发票 0否1是
     */
    private Integer needInvoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 实收合计 实收金额
     */
    private Double factPayment;

    /**
     * 收款人
     */
    private String collectionPersonName;

    /**
     * 单据状态 付款单状态：0待收款；1收款异常；2已收款；4已作废；6未提交
     */
    private String state;

    /**
     * 财务确认人
     */
    private String financeConfirm;

    /**
     * 防伪码
     */
    private String verycode;

    /**
     * 作废原因/异常描述
     */
    private String exceptionCollectionReason;

    /**
     * 收据单号
     */
    private String documentCode;

    /**
     * 打印次数
     */
    private Integer printNum;

    /**
     * 异常原因 类型   财务审核驳回 0  财务自动打回 1 
     */
    private Integer exceptionType;

    /**
     * 异常退回操作:
0 作废
1 修改
     */
    private Integer exceptionOperate;

    /**
     * 城市
     */
    private String city;

    /**
     * 备注
     */
    private String remark;

    /**
     * POS收款小票图片
     */
    private String attach;

    /**
     * [支票支付时]支票号:
     */
    private String checkNumber;

    /**
     * [支票支付时]出票单位:
     */
    private String checkCompany;

    /**
     * [银行汇款时]汇入行:
     */
    private String remittanceBank;

    /**
     * [银行汇款时]汇入账户:
     */
    private String remittanceAccount;

    /**
     * 服务费
     */
    private Double servicePrice;

    /**
     * 收款单房租
     */
    private Double rentMoney;

    /**
     * 押金
     */
    private Double yajin;

    /**
     * 佣金 代替服务费
     */
    private Double yongjin;

    /**
     * 有线电视费用
     */
    private Double cableTelevisionPrice;

    /**
     * 保洁费
     */
    private Double cleanPrice;

    /**
     * 水费
     */
    private Double waterPrice;

    /**
     * 电费
     */
    private Double telPrice;

    /**
     * 供暖费
     */
    private Double hotPrice;

    /**
     * 其他费用
     */
    private Double electricityPrice;

    /**
     * 多收款项
     */
    private Double otherPrice;

    private Integer fisdel;

    private Date fcreatetime;

    private String createrid;

    private Date fupdatetime;

    private String updaterid;

    /**
     * 收款人系统号
     */
    private String collectionpersoncode;

    /**
     * 确认人系统号
     */
    private String financeconfirmcode;

    /**
     * 结算方式Id
     */
    private String finsettleid;

    /**
     * 解约协议费用结算单Id
     */
    private String surrendercostId;

    /**
     * 关联城市表
     */
    private String cityid;

    /**
     * 生成方式(0.自动生成；1.手工录入)
     */
    private Integer genWay;

    /**
     * 外部交易号
     */
    private String outTradeNo;

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode == null ? null : paymentCode.trim();
    }

    public String getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(String firstPay) {
        this.firstPay = firstPay == null ? null : firstPay.trim();
    }

    public String getCollectionChannel() {
        return collectionChannel;
    }

    public void setCollectionChannel(String collectionChannel) {
        this.collectionChannel = collectionChannel == null ? null : collectionChannel.trim();
    }

    public String getRentContractCode() {
        return rentContractCode;
    }

    public void setRentContractCode(String rentContractCode) {
        this.rentContractCode = rentContractCode == null ? null : rentContractCode.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName == null ? null : roomName.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(Integer paymentNum) {
        this.paymentNum = paymentNum;
    }

    public Double getPayableRental() {
        return payableRental;
    }

    public void setPayableRental(Double payableRental) {
        this.payableRental = payableRental;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getRealityReceipt() {
        return realityReceipt;
    }

    public void setRealityReceipt(Date realityReceipt) {
        this.realityReceipt = realityReceipt;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getCollectionWay() {
        return collectionWay;
    }

    public void setCollectionWay(String collectionWay) {
        this.collectionWay = collectionWay == null ? null : collectionWay.trim();
    }

    public String getCollectionWayDetail() {
        return collectionWayDetail;
    }

    public void setCollectionWayDetail(String collectionWayDetail) {
        this.collectionWayDetail = collectionWayDetail == null ? null : collectionWayDetail.trim();
    }

    public Integer getOnlinePaymentWay() {
        return onlinePaymentWay;
    }

    public void setOnlinePaymentWay(Integer onlinePaymentWay) {
        this.onlinePaymentWay = onlinePaymentWay;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId == null ? null : posId.trim();
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode == null ? null : posCode.trim();
    }

    public String getRefnum() {
        return refnum;
    }

    public void setRefnum(String refnum) {
        this.refnum = refnum == null ? null : refnum.trim();
    }

    public String getPaymentorName() {
        return paymentorName;
    }

    public void setPaymentorName(String paymentorName) {
        this.paymentorName = paymentorName == null ? null : paymentorName.trim();
    }

    public String getCollectionCardNumber() {
        return collectionCardNumber;
    }

    public void setCollectionCardNumber(String collectionCardNumber) {
        this.collectionCardNumber = collectionCardNumber == null ? null : collectionCardNumber.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public Integer getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(Integer needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public Double getFactPayment() {
        return factPayment;
    }

    public void setFactPayment(Double factPayment) {
        this.factPayment = factPayment;
    }

    public String getCollectionPersonName() {
        return collectionPersonName;
    }

    public void setCollectionPersonName(String collectionPersonName) {
        this.collectionPersonName = collectionPersonName == null ? null : collectionPersonName.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getFinanceConfirm() {
        return financeConfirm;
    }

    public void setFinanceConfirm(String financeConfirm) {
        this.financeConfirm = financeConfirm == null ? null : financeConfirm.trim();
    }

    public String getVerycode() {
        return verycode;
    }

    public void setVerycode(String verycode) {
        this.verycode = verycode == null ? null : verycode.trim();
    }

    public String getExceptionCollectionReason() {
        return exceptionCollectionReason;
    }

    public void setExceptionCollectionReason(String exceptionCollectionReason) {
        this.exceptionCollectionReason = exceptionCollectionReason == null ? null : exceptionCollectionReason.trim();
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode == null ? null : documentCode.trim();
    }

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }

    public Integer getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(Integer exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Integer getExceptionOperate() {
        return exceptionOperate;
    }

    public void setExceptionOperate(Integer exceptionOperate) {
        this.exceptionOperate = exceptionOperate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber == null ? null : checkNumber.trim();
    }

    public String getCheckCompany() {
        return checkCompany;
    }

    public void setCheckCompany(String checkCompany) {
        this.checkCompany = checkCompany == null ? null : checkCompany.trim();
    }

    public String getRemittanceBank() {
        return remittanceBank;
    }

    public void setRemittanceBank(String remittanceBank) {
        this.remittanceBank = remittanceBank == null ? null : remittanceBank.trim();
    }

    public String getRemittanceAccount() {
        return remittanceAccount;
    }

    public void setRemittanceAccount(String remittanceAccount) {
        this.remittanceAccount = remittanceAccount == null ? null : remittanceAccount.trim();
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Double getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(Double rentMoney) {
        this.rentMoney = rentMoney;
    }

    public Double getYajin() {
        return yajin;
    }

    public void setYajin(Double yajin) {
        this.yajin = yajin;
    }

    public Double getYongjin() {
        return yongjin;
    }

    public void setYongjin(Double yongjin) {
        this.yongjin = yongjin;
    }

    public Double getCableTelevisionPrice() {
        return cableTelevisionPrice;
    }

    public void setCableTelevisionPrice(Double cableTelevisionPrice) {
        this.cableTelevisionPrice = cableTelevisionPrice;
    }

    public Double getCleanPrice() {
        return cleanPrice;
    }

    public void setCleanPrice(Double cleanPrice) {
        this.cleanPrice = cleanPrice;
    }

    public Double getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(Double waterPrice) {
        this.waterPrice = waterPrice;
    }

    public Double getTelPrice() {
        return telPrice;
    }

    public void setTelPrice(Double telPrice) {
        this.telPrice = telPrice;
    }

    public Double getHotPrice() {
        return hotPrice;
    }

    public void setHotPrice(Double hotPrice) {
        this.hotPrice = hotPrice;
    }

    public Double getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(Double electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public Double getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(Double otherPrice) {
        this.otherPrice = otherPrice;
    }

    public Integer getFisdel() {
        return fisdel;
    }

    public void setFisdel(Integer fisdel) {
        this.fisdel = fisdel;
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

    public String getCollectionpersoncode() {
        return collectionpersoncode;
    }

    public void setCollectionpersoncode(String collectionpersoncode) {
        this.collectionpersoncode = collectionpersoncode == null ? null : collectionpersoncode.trim();
    }

    public String getFinanceconfirmcode() {
        return financeconfirmcode;
    }

    public void setFinanceconfirmcode(String financeconfirmcode) {
        this.financeconfirmcode = financeconfirmcode == null ? null : financeconfirmcode.trim();
    }

    public String getFinsettleid() {
        return finsettleid;
    }

    public void setFinsettleid(String finsettleid) {
        this.finsettleid = finsettleid == null ? null : finsettleid.trim();
    }

    public String getSurrendercostId() {
        return surrendercostId;
    }

    public void setSurrendercostId(String surrendercostId) {
        this.surrendercostId = surrendercostId == null ? null : surrendercostId.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public Integer getGenWay() {
        return genWay;
    }

    public void setGenWay(Integer genWay) {
        this.genWay = genWay;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }
}