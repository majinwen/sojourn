package com.zra.common.vo.bill;

import com.zra.common.vo.base.BaseFieldVo;
import com.zra.common.vo.contract.ProjectInfoVo;
import com.zra.common.vo.pay.ActivityInfoVo;
import com.zra.common.vo.pay.HadPayInfoVo;
import com.zra.common.vo.pay.PayInfoVo;

import java.util.Date;

/**
 * <p>账单详情Vo (支付页面数据载体)</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月10日 19:43
 * @since 1.0
 */
public class BillDetailVo {
	private String contractId;
	private String contractCode;
	private ShouldPayDateVo shouldPayDate; // 支付时间对象
	private ProjectInfoVo projectInfo;// 项目头信息
	private PayCostItemsVo payInfo;// 支付费用项
	private ActivityInfoVo activityInfo; // 活动信息
	private HadPayInfoVo havePayInfo;// 已经支付信息
	private BaseFieldVo leftPayInfo;// 剩余支付信息
	private String billNumList; //应收账单列表
	private String billType;// 账单类型
	private NeedPayInfo thisTimeNeedPay;// 需要支付的金额
	private String systemId;//收银台系统标识 100005
	private Integer signType;// 签约类型
	private Date payEndTime;// 支付结束时间

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public ShouldPayDateVo getShouldPayDate() {
		return shouldPayDate;
	}

	public void setShouldPayDate(ShouldPayDateVo shouldPayDate) {
		this.shouldPayDate = shouldPayDate;
	}

	public ProjectInfoVo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfoVo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public PayCostItemsVo getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PayCostItemsVo payInfo) {
		this.payInfo = payInfo;
	}

	public HadPayInfoVo getHavePayInfo() {
		return havePayInfo;
	}

	public void setHavePayInfo(HadPayInfoVo havePayInfo) {
		this.havePayInfo = havePayInfo;
	}

	public ActivityInfoVo getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(ActivityInfoVo activityInfo) {
		this.activityInfo = activityInfo;
	}

	public BaseFieldVo getLeftPayInfo() {
		return leftPayInfo;
	}

	public void setLeftPayInfo(BaseFieldVo leftPayInfo) {
		this.leftPayInfo = leftPayInfo;
	}

	public String getBillNumList() {
		return billNumList;
	}

	public void setBillNumList(String billNumList) {
		this.billNumList = billNumList;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public NeedPayInfo getThisTimeNeedPay() {
		return thisTimeNeedPay;
	}

	public void setThisTimeNeedPay(NeedPayInfo thisTimeNeedPay) {
		this.thisTimeNeedPay = thisTimeNeedPay;
	}
}
