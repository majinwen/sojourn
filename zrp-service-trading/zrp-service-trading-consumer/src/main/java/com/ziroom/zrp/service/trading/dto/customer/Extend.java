package com.ziroom.zrp.service.trading.dto.customer;

import java.io.Serializable;
/**
 * <p>查询友家个人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月21日
 * @since 1.0
 */
public class Extend implements Serializable{
	 
	private static final long serialVersionUID = -35123056991467796L;
	/**
	  * 社会资质证明类型:1胸卡,2工作证,3学生证,4劳动合同,5工作证明,6就业协议,7营业执照副本,8介绍信,9收入证明,10京籍人士担保协议'
	  */
	 private int social_proof;
	 /**
	  * 证明人姓名
	  */
	 private String certifier_name;
	 /**
	  * 证明人电话
	  */
	 private String  certifier_phone;
	 /**
	  * 公司/学校名称
	  */
	 private String work_name;
	 /**
	  * 公司id
	  */
	 private String work_id;
	 /**
	  * 公司/学校地址
	  */
	 private String work_address;
	 /**
	  * 公司地址坐标经度
	  */
	 private String work_longitude;
	 /**
	  * 公司地址坐标纬度
	  */
	 private String work_latitude;
	 /**
	  * 紧急联系人姓名
	  */
	 private String urgency_name;
	 /**
	  * 紧急联系人电话
	  */
	 private String urgency_phone;
	 /**
	  * 与紧急联系人关系
	  */
	 private String urgency_relation;
	 /**
	  * 行业
	  */
	 private String industry;
	 /**
	  * 企业系统行业id
	  */
	 private int industry_id;
	 /**
	  * 方向
	  */
	 private String direction;
	 /**
	  * 企业系统方向id
	  */
	 private int direction_id;
	 /**
	  * 附件地址  社会资质照片
	  */
	 private String accessory_url;
	 
	public int getSocial_proof() {
		return social_proof;
	}
	public void setSocial_proof(int social_proof) {
		this.social_proof = social_proof;
	}
	public String getCertifier_name() {
		return certifier_name;
	}
	public void setCertifier_name(String certifier_name) {
		this.certifier_name = certifier_name;
	}
	public String getCertifier_phone() {
		return certifier_phone;
	}
	public void setCertifier_phone(String certifier_phone) {
		this.certifier_phone = certifier_phone;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getWork_id() {
		return work_id;
	}
	public void setWork_id(String work_id) {
		this.work_id = work_id;
	}
	public String getWork_address() {
		return work_address;
	}
	public void setWork_address(String work_address) {
		this.work_address = work_address;
	}
	public String getWork_longitude() {
		return work_longitude;
	}
	public void setWork_longitude(String work_longitude) {
		this.work_longitude = work_longitude;
	}
	public String getWork_latitude() {
		return work_latitude;
	}
	public void setWork_latitude(String work_latitude) {
		this.work_latitude = work_latitude;
	}
	public String getUrgency_name() {
		return urgency_name;
	}
	public void setUrgency_name(String urgency_name) {
		this.urgency_name = urgency_name;
	}
	public String getUrgency_phone() {
		return urgency_phone;
	}
	public void setUrgency_phone(String urgency_phone) {
		this.urgency_phone = urgency_phone;
	}
	public String getUrgency_relation() {
		return urgency_relation;
	}
	public void setUrgency_relation(String urgency_relation) {
		this.urgency_relation = urgency_relation;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public int getIndustry_id() {
		return industry_id;
	}
	public void setIndustry_id(int industry_id) {
		this.industry_id = industry_id;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getDirection_id() {
		return direction_id;
	}
	public void setDirection_id(int direction_id) {
		this.direction_id = direction_id;
	}
	public String getAccessory_url() {
		return accessory_url;
	}
	public void setAccessory_url(String accessory_url) {
		this.accessory_url = accessory_url;
	}
	 
}
