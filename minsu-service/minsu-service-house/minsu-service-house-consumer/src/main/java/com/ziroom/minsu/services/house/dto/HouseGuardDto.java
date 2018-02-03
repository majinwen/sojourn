package com.ziroom.minsu.services.house.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * 
 * <p>
 * 房源维护管家参数dto
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseGuardDto extends PageRequest {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 4478762835153593998L;
	
	/**
	 * 房源管家逻辑id
	 */
	private String houseGuardFid;

	/**
	 * 房源名称
	 */
	private String houseName;

	/**
	 * 房源编号
	 */
	private String houseSn;
	
	/**
	 * 房源状态
	 */
	private Integer houseStatus;
	
	/**
	 * 管家姓名
	 */
	private String zoName;
	
	/**
	 * 管家系统号
	 */
	private String zoCode;
	
	/**
	 * 管家手机号
	 */
	private String zoMobile;
	
	/**
	 * 房东姓名
	 */
	private String landlordName;
	
	/**
	 * 房东昵称
	 */
	private String landlordNickname;
	
	/**
	 * 房东手机号
	 */
	private String landlordMobile;

	/**
	 * 国家code
	 */
	private String nationCode;

	/**
	 * 省code
	 */
	private String provinceCode;

	/**
	 * 城市code
	 */
	private String cityCode;

	/**
	 * 区域code
	 */
	private String areaCode;

	/**
	 * 房东uid集合
	 */
	private List<String> listLandlordUid;

	/**
	 * ZO系统号集合
	 */
	private List<String> listZoCode;
	
	/**
	 * 房源fid集合
	 */
	private List<String> listHouseFid;
	
	/**
	 * 房源出租类型
	 */
	// 房源出租类型
	private Integer rentWay;

	/**
	 * 房间编号
	 */
	private String roomSn;
	
    /**
     * 权限类型
     */
    private int roleType=0;
    
    /**
     * 员工code
     */
    private String empCode;
    
	/**
     * 服务区域
     */
    private List<CurrentuserCityEntity> userCityList=new ArrayList<CurrentuserCityEntity>();
    
	/**
	 * 渠道
	 */
	private Integer houseChannel;

	public Integer getHouseChannel() {
		return houseChannel;
	}

	public void setHouseChannel(Integer houseChannel) {
		this.houseChannel = houseChannel;
	}

	public Integer getRentWay() {
		return rentWay;
	}

	public void setRentWay(Integer rentWay) {
		this.rentWay = rentWay;
	}

	public String getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<String> getListLandlordUid() {
		return listLandlordUid;
	}

	public void setListLandlordUid(List<String> listLandlordUid) {
		this.listLandlordUid = listLandlordUid;
	}

	public List<String> getListZoCode() {
		return listZoCode;
	}

	public void setListZoCode(List<String> listZoCode) {
		this.listZoCode = listZoCode;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordNickname() {
		return landlordNickname;
	}

	public void setLandlordNickname(String landlordNickname) {
		this.landlordNickname = landlordNickname;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public String getZoCode() {
		return zoCode;
	}

	public void setZoCode(String zoCode) {
		this.zoCode = zoCode;
	}

	public String getZoMobile() {
		return zoMobile;
	}

	public void setZoMobile(String zoMobile) {
		this.zoMobile = zoMobile;
	}

	public String getHouseGuardFid() {
		return houseGuardFid;
	}

	public void setHouseGuardFid(String houseGuardFid) {
		this.houseGuardFid = houseGuardFid;
	}

	public Integer getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(Integer houseStatus) {
		this.houseStatus = houseStatus;
	}

	public List<String> getListHouseFid() {
		return listHouseFid;
	}

	public void setListHouseFid(List<String> listHouseFid) {
		this.listHouseFid = listHouseFid;
	}
	
	/**
	 * @return the roleType
	 */
	public int getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the userCityList
	 */
	public List<CurrentuserCityEntity> getUserCityList() {
		return userCityList;
	}

	/**
	 * @param userCityList the userCityList to set
	 */
	public void setUserCityList(List<CurrentuserCityEntity> userCityList) {
		this.userCityList = userCityList;
	}
}
