package com.ziroom.minsu.report.house.vo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.report.common.annotation.FieldMeta;

/**
 * <p>HouseOrderInfoVo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */

public class HouseCommonVo extends BaseEntity {
  
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1L;

	@FieldMeta(name="房源/房间编号",order=1)
	private String houseSn;
	
	@FieldMeta(name="房源/房间名称",order=2)
	private String houseName;
	
	@FieldMeta(name="房源地址",order=3)
	private String houseAddr;
	
	@FieldMeta(name="城市编号",order=4)
	private String cityCode;
	
	@FieldMeta(name="维护管家工号",order=5)
	private String empGuardCode;
	
	@FieldMeta(name="维护管家姓名",order=6)
	private String empGuardName;
	
	@FieldMeta(name="地推管家工号",order=7)
	private String empPushCode;
	
	@FieldMeta(name="地推管家姓名",order=8)
	private String empPushName;
	
	@FieldMeta(name="房东姓名",order=9)
	private String lanRealName;
	
    @FieldMeta(name="房东电话",order=10)
	private String lanMobile;
	
	@FieldMeta(name="日租金价格(分)",order=11)
	private Integer price;

	@FieldMeta(skip = true)
	private Integer houseStatus;
	
	@FieldMeta(name="房源/房间状态",order=12)
	private String houseStatusName;
	
	
	@FieldMeta(skip = true)
	private Integer houseChannel;
	
	@FieldMeta(name="房源渠道",order=13)
	private String houseChannelName;
	

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getEmpGuardCode() {
		return empGuardCode;
	}

	public void setEmpGuardCode(String empGuardCode) {
		this.empGuardCode = empGuardCode;
	}

	public String getEmpGuardName() {
		return empGuardName;
	}

	public void setEmpGuardName(String empGuardName) {
		this.empGuardName = empGuardName;
	}

	public String getEmpPushCode() {
		return empPushCode;
	}

	public void setEmpPushCode(String empPushCode) {
		this.empPushCode = empPushCode;
	}

	public String getEmpPushName() {
		return empPushName;
	}

	public void setEmpPushName(String empPushName) {
		this.empPushName = empPushName;
	}

	public String getLanRealName() {
		return lanRealName;
	}

	public void setLanRealName(String lanRealName) {
		this.lanRealName = lanRealName;
	}

	

	public String getLanMobile() {
		return lanMobile;
	}

	public void setLanMobile(String lanMobile) {
		this.lanMobile = lanMobile;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	

	public String getHouseStatusName() {
		return houseStatusName;
	}

	public void setHouseStatusName(String houseStatusName) {
		this.houseStatusName = houseStatusName;
	}

	public Integer getHouseChannel() {
		return houseChannel;
	}

	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}

	public String getHouseChannelName() {
		return houseChannelName;
	}

	public void setHouseChannelName(String houseChannelName) {
		this.houseChannelName = houseChannelName;
	}

	
}
