/**
 * @FileName: CustomerAuthVo.java
 * @Package com.ziroom.minsu.services.customer.entity
 * 
 * @author bushujie
 * @created 2016年7月26日 下午7:10:13
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.entity;

import java.util.Map;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;

/**
 * <p>客户认证信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class CustomerAuthVo extends CustomerBaseMsgEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3266752950934009776L;
	/**
	 * 证件正面照
	 */
	private CustomerPicMsgEntity voucherFrontPic=new CustomerPicMsgEntity();
	/**
	 * 证件反面照
	 */
	private CustomerPicMsgEntity voucherBackPic=new CustomerPicMsgEntity();
	/**
	 * 手持证件照
	 */
	private CustomerPicMsgEntity voucherHandPic=new CustomerPicMsgEntity();
	
	/**
	 * 头像照片
	 */
	private CustomerPicMsgEntity voucherHeadPic=new CustomerPicMsgEntity();
	
	/**
	 * 证件类型名称
	 */
    private String idTypeName;
    
    /**
	 * 证件正面照url
	 */
    private String voucherFrontPicUrl;
    
    /**
	 * 证件反面照url
	 */
    private String voucherBackPicUrl;
    
    /**
	 * 手持证件照url
	 */
    private String voucherHandPicUrl;
    
    /**
     * 证件类型列表
     */
    private Map<Integer, String> idTypeMap = CustomerIdTypeEnum.getCustomerIdTypeMap();
	 
	public Map<Integer, String> getIdTypeMap() {
		return idTypeMap;
	}
	public void setIdTypeMap(Map<Integer, String> idTypeMap) {
		this.idTypeMap = idTypeMap;
	}
	public String getVoucherFrontPicUrl() {
		return voucherFrontPicUrl;
	}
	public void setVoucherFrontPicUrl(String voucherFrontPicUrl) {
		this.voucherFrontPicUrl = voucherFrontPicUrl;
	}
	public String getVoucherBackPicUrl() {
		return voucherBackPicUrl;
	}
	public void setVoucherBackPicUrl(String voucherBackPicUrl) {
		this.voucherBackPicUrl = voucherBackPicUrl;
	}
	public String getVoucherHandPicUrl() {
		return voucherHandPicUrl;
	}
	public void setVoucherHandPicUrl(String voucherHandPicUrl) {
		this.voucherHandPicUrl = voucherHandPicUrl;
	}
	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}
	
	
	/**
	 * @return the voucherHeadPic
	 */
	public CustomerPicMsgEntity getVoucherHeadPic() {
		return voucherHeadPic;
	}
	/**
	 * @param voucherHeadPic the voucherHeadPic to set
	 */
	public void setVoucherHeadPic(CustomerPicMsgEntity voucherHeadPic) {
		this.voucherHeadPic = voucherHeadPic;
	}
	/**
	 * @return the voucherFrontPic
	 */
	public CustomerPicMsgEntity getVoucherFrontPic() {
		return voucherFrontPic;
	}
	/**
	 * @param voucherFrontPic the voucherFrontPic to set
	 */
	public void setVoucherFrontPic(CustomerPicMsgEntity voucherFrontPic) {
		this.voucherFrontPic = voucherFrontPic;
	}
	/**
	 * @return the voucherBackPic
	 */
	public CustomerPicMsgEntity getVoucherBackPic() {
		return voucherBackPic;
	}
	/**
	 * @param voucherBackPic the voucherBackPic to set
	 */
	public void setVoucherBackPic(CustomerPicMsgEntity voucherBackPic) {
		this.voucherBackPic = voucherBackPic;
	}
	/**
	 * @return the voucherHandPic
	 */
	public CustomerPicMsgEntity getVoucherHandPic() {
		return voucherHandPic;
	}
	/**
	 * @param voucherHandPic the voucherHandPic to set
	 */
	public void setVoucherHandPic(CustomerPicMsgEntity voucherHandPic) {
		this.voucherHandPic = voucherHandPic;
	}

}
