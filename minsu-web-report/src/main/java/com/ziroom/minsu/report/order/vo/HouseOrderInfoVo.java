package com.ziroom.minsu.report.order.vo;

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

public class HouseOrderInfoVo extends BaseEntity {
   /**
	 * 
	 */
	@FieldMeta(skip = true)
	private static final long serialVersionUID = 1449723418011236882L;
	
	@FieldMeta(name="房源/房间fid",order=1)
	private String houseFid;
	
	@FieldMeta(name="房源/房间编号",order=1)
	private String houseSn;

	@FieldMeta(name="房源/房间名称",order=2)
	private String houseName;
	
	@FieldMeta(name="房源地址",order=1)
	private String houseAddr;
	
	@FieldMeta(name="城市编号",order=1)
	private String cityCode;
	
	@FieldMeta(name="维护管家工号",order=1)
	private String empGuardCode;
	
	@FieldMeta(name="维护管家姓名",order=1)
	private String empGuardName;
	
	@FieldMeta(name="地推管家工号",order=1)
	private String empPushCode;
	
	@FieldMeta(name="地推管家姓名",order=1)
	private String empPushName;
	
	@FieldMeta(name="房东姓名",order=1)
	private String lanRealName;
	
    @FieldMeta(name="房东电话",order=1)
	private String lanCusMobile;
	
	@FieldMeta(name="订单量",order=3)
	private Integer orderNum;

	@FieldMeta(name="订单间夜",order=4)
	private Integer orderNight;
	
	@FieldMeta(name="房屋租金",order=5)
	private Integer rentalMoney;
	
	@FieldMeta(name="房屋服务费",order=5)
	private Integer orderServiceMoney;
	
	@FieldMeta(name="居住间夜",order=5)
	private Integer statyNight ;
	
	@FieldMeta(name="居住间夜服务费",order=5)
	private Integer stayServiceMoney;
	
	@FieldMeta(name="房源评价量",order=5)
	private Integer evaNum ;
	
	@FieldMeta(name="未来30天入住间夜",order=5)
	private Integer willStatyNight;
	
	@FieldMeta(name="未来30天入住间夜服务费",order=5)
	private Integer willServiceMoney ;

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
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

	public String getLanCusMobile() {
		return lanCusMobile;
	}

	public void setLanCusMobile(String lanCusMobile) {
		this.lanCusMobile = lanCusMobile;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getOrderNight() {
		return orderNight;
	}

	public void setOrderNight(Integer orderNight) {
		this.orderNight = orderNight;
	}

	public Integer getRentalMoney() {
		return rentalMoney;
	}

	public void setRentalMoney(Integer rentalMoney) {
		this.rentalMoney = rentalMoney;
	}

	public Integer getOrderServiceMoney() {
		return orderServiceMoney;
	}

	public void setOrderServiceMoney(Integer orderServiceMoney) {
		this.orderServiceMoney = orderServiceMoney;
	}

	public Integer getStatyNight() {
		return statyNight;
	}

	public void setStatyNight(Integer statyNight) {
		this.statyNight = statyNight;
	}

	public Integer getStayServiceMoney() {
		return stayServiceMoney;
	}

	public void setStayServiceMoney(Integer stayServiceMoney) {
		this.stayServiceMoney = stayServiceMoney;
	}

	public Integer getEvaNum() {
		return evaNum;
	}

	public void setEvaNum(Integer evaNum) {
		this.evaNum = evaNum;
	}

	public Integer getWillStatyNight() {
		return willStatyNight;
	}

	public void setWillStatyNight(Integer willStatyNight) {
		this.willStatyNight = willStatyNight;
	}

	public Integer getWillServiceMoney() {
		return willServiceMoney;
	}

	public void setWillServiceMoney(Integer willServiceMoney) {
		this.willServiceMoney = willServiceMoney;
	}
	
	/**
	 * @return the houseSn
	 */
	public String getHouseSn() {
		return houseSn;
	}

	/**
	 * @param houseSn the houseSn to set
	 */
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
}
