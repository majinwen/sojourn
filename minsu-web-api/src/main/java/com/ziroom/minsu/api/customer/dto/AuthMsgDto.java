package com.ziroom.minsu.api.customer.dto;

import com.ziroom.minsu.api.common.dto.BaseParamDto;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;

/**
 * <p>
 * 补充用户认证信息
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年6月16日
 * @since 1.0
 * @version 1.0
 */
public class AuthMsgDto extends BaseParamDto {

	// 真实姓名
	private String realName;

	// 客户手机号
	private String mobile;

	// 验证码
	private String authCode;

	// 证件类型
	private Integer idType;

	// 证件号码
	private String idNo;
	
	/**
	 * 证件正面照
	 */
	private CustomerPicMsgEntity voucherFrontPic;
	/**
	 * 证件反面照
	 */
	private CustomerPicMsgEntity voucherBackPic;
	/**
	 * 手持证件照
	 */
	private CustomerPicMsgEntity voucherHandPic;
	
	/**
	 * 头像照片
	 */
	private CustomerPicMsgEntity voucherHeadPic;
	
	public CustomerPicMsgEntity getVoucherFrontPic() {
		return voucherFrontPic;
	}

	public void setVoucherFrontPic(CustomerPicMsgEntity voucherFrontPic) {
		this.voucherFrontPic = voucherFrontPic;
	}

	public CustomerPicMsgEntity getVoucherBackPic() {
		return voucherBackPic;
	}

	public void setVoucherBackPic(CustomerPicMsgEntity voucherBackPic) {
		this.voucherBackPic = voucherBackPic;
	}

	public CustomerPicMsgEntity getVoucherHandPic() {
		return voucherHandPic;
	}

	public void setVoucherHandPic(CustomerPicMsgEntity voucherHandPic) {
		this.voucherHandPic = voucherHandPic;
	}

	public CustomerPicMsgEntity getVoucherHeadPic() {
		return voucherHeadPic;
	}

	public void setVoucherHeadPic(CustomerPicMsgEntity voucherHeadPic) {
		this.voucherHeadPic = voucherHeadPic;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

}
