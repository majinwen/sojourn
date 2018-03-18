package com.ziroom.zrp.service.trading.entity;

/*
 * <P></P>
 * <P>
 * <PRE>
 * <BR> 修改记录
 * <BR>------------------------------------------
 * <BR> 修改日期       修改人        修改内容
 * </PRE>
 * 
 * @Author lusp
 * @Date Create in 2017年09月 21日 20:12
 * @Version 1.0
 * @Since 1.0
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SyncContractVo implements Serializable{

    private static final long serialVersionUID = -2764674839208523928L;

    /**
     * CRM中合同ID [合同ID（contractid）（财务需将数据类型改成String）]
     */
    private String crmContractId;

    /**
     * 	房屋ID [项目ID（project_id）（或项目表中的fId）]
     */
    private String houseId;

    /**
     * 	房屋标号 [收房合同号（项目表tproject中的f_contract_number）]
     */
    private String houseCode;

    /**
     * 房源编号 [房间号]
     */
    private String houseSourceCode;

    /**
     * 房间号ID [房间ID/床位ID（fid）]
     */
    private String roomId;

    /**
     * 	房间code [房间编号/床位编号（froomnumber）]
     */
    private String roomCode;

    /**
     * 	承租合同号 [合同号（con_rent_code）]
     */
    private String rentContractCode;

    /**
     * 	续约合同对应的原合同号
     */
    private String originalContract;

    /**
     * 	承租合同签署日期 [签订日期（con_sign_date）]
     */
    private Date lesseeSignDate;

    /**
     * 签约状态：QYZ("签约中", "qyz"),
     * DGJJG("待管家交割","dgjjg"),
     * DZF("待支付", "dzf") √,
     * YZF("已支付", "yzf") √,
     * YCQ("已超期", "ycq"),
     * dtjsh (待管家审核)；
     * DSH("待审核", "dsh"),
     * YSH("已审核", "ysh") √,
     * YBH("已驳回", "ybh"),
     * YGB("已关闭", "ygb") √;
     * ytz(已退租) √
     * ydq(已到期) √
     *
     * [√ 的是我们的状态需要区分开并且需要传给财务的。（con_status_code、con_audit_state两个字段联合判断）]
     */
    private String statusCode;

    /**
     * 	承租开始日 [起租日期（con_start_date）]
     */
    private Date lesseeStartDate;

    /**
     * 	承租截止日 [到期日期（con_end_date）]
     */
    private Date lesseeEndDate;

    /**
     * 	月租金 [应收月租金（factualprice）]
     */
    private BigDecimal monthRent;

    /**
     * 	押金 [押金（con_deposit）]
     */
    private BigDecimal deposit;

    /**
     * 	收款周期编码：1,3,6,12,99；月付，季付，半年付，年付、京东白条和自如分期
     * 	[付款方式（con_cycle_code）（其中一次性付清对应财务的编码：99）]
     */
    private String collectCycleCode;

    /**
     * 	物业地址 [项目地址]
     */
    private String ratingAddress;

    /**
     * 	出房管家系统号
     */
    private String agentCode;

    /**
     * 	出房管家姓名
     */
    private String agentName;

    /**
     * 	出房管家手机号
     */
    private String agentPhone;

    /**
     * 	创建人ID [办理ZO系统号（fhandleZOcode）]
     */
    private String creatorId;

    /**
     * 	创建人姓名 [办理ZO姓名（fhandleZO）]
     */
    private String creatorName;

    /**
     * 	创建日期 [创建时间（fcreatetime）]
     */
    private Date creatorDate;

    /**
     * 	是否删除 [是否删除（财务的后面会在接口上标明枚举，注意查看接口文档）]
     */
    private Integer isDel;

    /**
     * 	服务费 [实收服务费（con_must_commission）]
     */
    private BigDecimal commission;

    /**
     * 	收房合同号 [收房合同号（项目表tproject中的f_contract_number）]
     */
    private String hireContractCode;

    /**
     * 	经纪人业绩佣金（原折扣前佣金） [应缴服务费（con_commission）]
     */
    private String frontCommission;

    /**
     * 	租期类型 [签约周期（con_rent_year）]
     */
    private String tenancyType;

    /**
     * 	是否为短租 0：不是 1：是 [我们系统里的划分规则]
     */
    private Integer isShortRent;

    /**
     * 	是否为大客户 0：否 1：是 [客户类型（customer_type：1/3对应财务的0；2对应财务的1）]
     */
    private Integer isImportantCustomer;

    /**
     * 	客户类型（1 普通个人客户 2 企悦会员工 3 企业客户）
     */
    private Integer customerType;

    /**
     * 	客户id
     */
    private String customerId;

    /**
     * 	是否是app签约 1：是 0：否 [	签约合同来源（fsource）]
     */
    private Integer contractStrategy;

    /**
     * 	公司code [公司编码（项目表tproject中的的fCompanyId）]
     */
    private String companyCode;

    /**
     * 	自如转签出房新合同号 [合同号（con_rent_code）]
     */
    private String ziroomRentContractCode;

    /**
     * 	新的合同生效日期 [起租日期（con_start_date）]
     */
    private Date effectDate;

    /**
     * 	房屋类型（1 是合租  3是整租，4是整租3.0,5是NC, 6 自如寓房间，7 自如寓床位） [出租方式（rent_type）（对应财务的6或7）]
     */
    private Integer houseType;

    /**
     * 	数据来源 [ZRA（定值）]
     */
    private String source;

    /**
     * 	客户姓名 [客户姓名（customer_name）]
     */
    private String customerName;

    /**
     * 	客户UID [客户uuid（customer_uid）]
     */
    private String customerUid;

    /**
     * 	客户手机号 [客户手机（customer_mobile）]
     */
    private String customerPhone;

    /**
     * 	客户证件号[证件号码（个人：rent_checkin_person表中的cert_num；企业：rent_eps_customer表中的contacter_num）]
     */
    private String certNum;

    /**
     * 	证件类型 [证件类型（个人：rent_checkin_person中的cert_type；企业：待定……）] 非必须
     */
    private String certType;

    /**
     * 	转款类型 [新签：0；续约：2]
     */
    private Integer transferType;

    private Date lastModifyTime;

    /**
     * 	是否是续约（1：是）
     */
    private Integer isResign;

    /**
     * 	父合同号 -- 企业签约时使用
     */
    private String parentContractCode;

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getCrmContractId() {
        return crmContractId;
    }

    public void setCrmContractId(String crmContractId) {
        this.crmContractId = crmContractId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseSourceCode() {
        return houseSourceCode;
    }

    public void setHouseSourceCode(String houseSourceCode) {
        this.houseSourceCode = houseSourceCode;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRentContractCode() {
        return rentContractCode;
    }

    public void setRentContractCode(String rentContractCode) {
        this.rentContractCode = rentContractCode;
    }

    public String getOriginalContract() {
        return originalContract;
    }

    public void setOriginalContract(String originalContract) {
        this.originalContract = originalContract;
    }

    public Date getLesseeSignDate() {
        return lesseeSignDate;
    }

    public void setLesseeSignDate(Date lesseeSignDate) {
        this.lesseeSignDate = lesseeSignDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getLesseeStartDate() {
        return lesseeStartDate;
    }

    public void setLesseeStartDate(Date lesseeStartDate) {
        this.lesseeStartDate = lesseeStartDate;
    }

    public Date getLesseeEndDate() {
        return lesseeEndDate;
    }

    public void setLesseeEndDate(Date lesseeEndDate) {
        this.lesseeEndDate = lesseeEndDate;
    }

    public BigDecimal getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(BigDecimal monthRent) {
        this.monthRent = monthRent;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getCollectCycleCode() {
        return collectCycleCode;
    }

    public void setCollectCycleCode(String collectCycleCode) {
        this.collectCycleCode = collectCycleCode;
    }

    public String getRatingAddress() {
        return ratingAddress;
    }

    public void setRatingAddress(String ratingAddress) {
        this.ratingAddress = ratingAddress;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatorDate() {
        return creatorDate;
    }

    public void setCreatorDate(Date creatorDate) {
        this.creatorDate = creatorDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getHireContractCode() {
        return hireContractCode;
    }

    public void setHireContractCode(String hireContractCode) {
        this.hireContractCode = hireContractCode;
    }

    public String getFrontCommission() {
        return frontCommission;
    }

    public void setFrontCommission(String frontCommission) {
        this.frontCommission = frontCommission;
    }

    public String getTenancyType() {
        return tenancyType;
    }

    public void setTenancyType(String tenancyType) {
        this.tenancyType = tenancyType;
    }

    public Integer getIsShortRent() {
        return isShortRent;
    }

    public void setIsShortRent(Integer isShortRent) {
        this.isShortRent = isShortRent;
    }

    public Integer getIsImportantCustomer() {
        return isImportantCustomer;
    }

    public void setIsImportantCustomer(Integer isImportantCustomer) {
        this.isImportantCustomer = isImportantCustomer;
    }

    public Integer getContractStrategy() {
        return contractStrategy;
    }

    public void setContractStrategy(Integer contractStrategy) {
        this.contractStrategy = contractStrategy;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getZiroomRentContractCode() {
        return ziroomRentContractCode;
    }

    public void setZiroomRentContractCode(String ziroomRentContractCode) {
        this.ziroomRentContractCode = ziroomRentContractCode;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
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

    public Integer getIsResign() {
        return isResign;
    }

    public void setIsResign(Integer isResign) {
        this.isResign = isResign;
    }

    public String getParentContractCode() {
        return parentContractCode;
    }

    public void setParentContractCode(String parentContractCode) {
        this.parentContractCode = parentContractCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }
}
