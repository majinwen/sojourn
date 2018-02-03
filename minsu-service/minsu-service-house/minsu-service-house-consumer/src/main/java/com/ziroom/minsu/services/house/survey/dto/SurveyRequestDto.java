package com.ziroom.minsu.services.house.survey.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.services.common.dto.PageRequest;

/**
 * <p>房源实勘请求dto</p>
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
public class SurveyRequestDto extends PageRequest{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8872790187659653606L;
	
	//实勘编号
	private String surveySn;
	
    //实勘结果 101：符合品质，102;不符品质
	private Integer surveyResult;
	
    //是否审阅 0:否 1:是
    private Integer isAudit;

	//房源编号
	private String houseSn;
	
	// 房东uid
	private String landlordUid;

	// 房东姓名
	private String landlordName;

	// 房东手机
	private String landlordMobile;

	//地推管家工号
	private String empPushCode;
	
	//地推管家姓名
	private String empPushName;
	
	//地推管家手机
	private String empPushMobile;

	//维护管家工号
	private String empGuardCode;
	
	//维护管家姓名
	private String empGuardName;
	
	//维护管家手机
	private String empGuardMobile;

	// 国家code
	private String nationCode;

	// 省code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 区code
	private String areaCode;
	
	private List<String> landlordUidList;
	
	private List<String> guardCodeList;
	
	private List<String> pushCodeList;
	

	/**
	 * 角色类型
	 */
	private Integer roleType;
	
	/**
	 * fid集合
	 */
	private List<String> listFid = new ArrayList<>();

	/**
	 * @return the listFid
	 */
	public List<String> getListFid() {
		return listFid;
	}

	/**
	 * @param listFid the listFid to set
	 */
	public void setListFid(List<String> listFid) {
		this.listFid = listFid;
	}

	/**
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	
	public String getSurveySn() {
		return surveySn;
	}
	
	public void setSurveySn(String surveySn) {
		this.surveySn = surveySn;
	}

	public Integer getSurveyResult() {
		return surveyResult;
	}

	public void setSurveyResult(Integer surveyResult) {
		this.surveyResult = surveyResult;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getLandlordUid() {
		return landlordUid;
	}

	public void setLandlordUid(String landlordUid) {
		this.landlordUid = landlordUid;
	}

	public String getLandlordName() {
		return landlordName;
	}

	public void setLandlordName(String landlordName) {
		this.landlordName = landlordName;
	}

	public String getLandlordMobile() {
		return landlordMobile;
	}

	public void setLandlordMobile(String landlordMobile) {
		this.landlordMobile = landlordMobile;
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

	public String getEmpPushMobile() {
		return empPushMobile;
	}

	public void setEmpPushMobile(String empPushMobile) {
		this.empPushMobile = empPushMobile;
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

	public String getEmpGuardMobile() {
		return empGuardMobile;
	}

	public void setEmpGuardMobile(String empGuardMobile) {
		this.empGuardMobile = empGuardMobile;
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

	public List<String> getLandlordUidList() {
		return landlordUidList;
	}

	public void setLandlordUidList(List<String> landlordUidList) {
		this.landlordUidList = landlordUidList;
	}

	public List<String> getGuardCodeList() {
		return guardCodeList;
	}

	public void setGuardCodeList(List<String> guardCodeList) {
		this.guardCodeList = guardCodeList;
	}

	public List<String> getPushCodeList() {
		return pushCodeList;
	}

	public void setPushCodeList(List<String> pushCodeList) {
		this.pushCodeList = pushCodeList;
	}
	
}
