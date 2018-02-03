/**
 * @FileName: HouseTopSaveDto.java
 * @Package com.ziroom.minsu.services.house.dto
 * 
 * @author bushujie
 * @created 2017年3月20日 下午6:12:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.dto;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseTopEntity;

/**
 * <p>TODO</p>
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
public class HouseTopSaveDto extends HouseTopEntity{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7882990379717422761L;
	/**
	 * 选择标签集合
	 */
	private List<String> tagFids=new ArrayList<String>(); 
	/**
	 * 是否分享到其他房间
	 */
	private Integer isShare;
	/**
	 * 条目集合
	 */
	private List<String> columnS=new ArrayList<>();
	
	/**
	 * 员工code
	 */
	private String empCode;
	
	/**
	 * 员工名称
	 */
	private String empName;
	
	/**
	 * top房源条目fid集合
	 */
	private List<String> houseTopColumnFidS=new ArrayList<>();
	
	/**
	 * top房源条目排序号集合
	 */
	private List<Integer> columnSortS=new ArrayList<>(); 
	
	/**
	 * 新建条目集合
	 */
	private List<Integer> columnSortNew=new ArrayList<>();
	
	/**
	 * 条目内容
	 */
	private List<String> columnContent=new ArrayList<>();
	
	/**
	 * 条目内容(更新)
	 */
	private List<String> columnContentU=new ArrayList<>();
	
	/**
	 * 亮点标题fid
	 */
	private String sternTitleFid;
	
	/**
	 * 亮点标题
	 */
	private String sternTitle;
	
	/**
	 * 亮点内容fid
	 */
	private String sternContentFid;
	
	/**
	 * 亮点内容
	 */
	private String sternContent;
	
	/**
	 * 封面图片参数fid
	 */
	private String coverPicParamFid;
	
	/**
	 * 封面图片参数
	 */
	private String coverPicParam;
	
	/**
	 * 更新标示 0：新建，1：更新
	 */
	private List<Integer> markUp=new ArrayList<>();
	
	/**
	 * @return the columnContentU
	 */
	public List<String> getColumnContentU() {
		return columnContentU;
	}
	/**
	 * @param columnContentU the columnContentU to set
	 */
	public void setColumnContentU(List<String> columnContentU) {
		this.columnContentU = columnContentU;
	}
	
	/**
	 * @return the markUp
	 */
	public List<Integer> getMarkUp() {
		return markUp;
	}
	/**
	 * @param markUp the markUp to set
	 */
	public void setMarkUp(List<Integer> markUp) {
		this.markUp = markUp;
	}
	/**
	 * @return the sternTitleFid
	 */
	public String getSternTitleFid() {
		return sternTitleFid;
	}
	/**
	 * @param sternTitleFid the sternTitleFid to set
	 */
	public void setSternTitleFid(String sternTitleFid) {
		this.sternTitleFid = sternTitleFid;
	}
	/**
	 * @return the sternContentFid
	 */
	public String getSternContentFid() {
		return sternContentFid;
	}
	/**
	 * @param sternContentFid the sternContentFid to set
	 */
	public void setSternContentFid(String sternContentFid) {
		this.sternContentFid = sternContentFid;
	}
	/**
	 * @return the coverPicParamFid
	 */
	public String getCoverPicParamFid() {
		return coverPicParamFid;
	}
	/**
	 * @param coverPicParamFid the coverPicParamFid to set
	 */
	public void setCoverPicParamFid(String coverPicParamFid) {
		this.coverPicParamFid = coverPicParamFid;
	}
	
	/**
	 * @return the coverPicParam
	 */
	public String getCoverPicParam() {
		return coverPicParam;
	}
	/**
	 * @param coverPicParam the coverPicParam to set
	 */
	public void setCoverPicParam(String coverPicParam) {
		this.coverPicParam = coverPicParam;
	}
	/**
	 * @return the sternContent
	 */
	public String getSternContent() {
		return sternContent;
	}
	/**
	 * @param sternContent the sternContent to set
	 */
	public void setSternContent(String sternContent) {
		this.sternContent = sternContent;
	}
	/**
	 * @return the sternTitle
	 */
	public String getSternTitle() {
		return sternTitle;
	}
	/**
	 * @param sternTitle the sternTitle to set
	 */
	public void setSternTitle(String sternTitle) {
		this.sternTitle = sternTitle;
	}
	/**
	 * @return the columnContent
	 */
	public List<String> getColumnContent() {
		return columnContent;
	}
	/**
	 * @param columnContent the columnContent to set
	 */
	public void setColumnContent(List<String> columnContent) {
		this.columnContent = columnContent;
	}
	/**
	 * @return the columnSortNew
	 */
	public List<Integer> getColumnSortNew() {
		return columnSortNew;
	}
	/**
	 * @param columnSortNew the columnSortNew to set
	 */
	public void setColumnSortNew(List<Integer> columnSortNew) {
		this.columnSortNew = columnSortNew;
	}
	/**
	 * @return the houseTopColumnFidS
	 */
	public List<String> getHouseTopColumnFidS() {
		return houseTopColumnFidS;
	}
	/**
	 * @param houseTopColumnFidS the houseTopColumnFidS to set
	 */
	public void setHouseTopColumnFidS(List<String> houseTopColumnFidS) {
		this.houseTopColumnFidS = houseTopColumnFidS;
	}
	/**
	 * @return the columnSortS
	 */
	public List<Integer> getColumnSortS() {
		return columnSortS;
	}
	/**
	 * @param columnSortS the columnSortS to set
	 */
	public void setColumnSortS(List<Integer> columnSortS) {
		this.columnSortS = columnSortS;
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
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the tagFids
	 */
	public List<String> getTagFids() {
		return tagFids;
	}
	/**
	 * @param tagFids the tagFids to set
	 */
	public void setTagFids(List<String> tagFids) {
		this.tagFids = tagFids;
	}
	/**
	 * @return the isShare
	 */
	public Integer getIsShare() {
		return isShare;
	}
	/**
	 * @param isShare the isShare to set
	 */
	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}
	/**
	 * @return the columnS
	 */
	public List<String> getColumnS() {
		return columnS;
	}
	/**
	 * @param columnS the columnS to set
	 */
	public void setColumnS(List<String> columnS) {
		this.columnS = columnS;
	}
}
