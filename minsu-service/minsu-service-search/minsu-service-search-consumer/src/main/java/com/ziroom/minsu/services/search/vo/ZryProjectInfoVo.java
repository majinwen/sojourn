package com.ziroom.minsu.services.search.vo;

import java.util.Set;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>自如驿数据同步模型</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月28日 10:18
 * @since 1.0
 */
public class ZryProjectInfoVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6776587036749479238L;

	/**
	 * 项目id
	 */
	private String projectBid;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
     * 地址
     */
    private String projectAddress;
	
	/**
	 * 房型数量
	 */
	private Integer houseModelCount;
	
	/**
	 * 城市code
	 */
	private String cityCode;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 区域code
	 */
	private String areaCode;
	
	/**
	 * 区域名称
	 */
	private String areaName;
	
	/**
	 * 将来日期的订单锁定库存数"170801,2"
	 */
	private Set<String> dayOrderLockedStocks;
	
	/**
     * 将来日期的其他锁定库存数"170801,2"
     */
    private Set<String> daySelfLockedStocks;
	
	/**
	 * 评价数量
	 */
	private Integer evaluateCount;
	
	/**
	 * 评价分
	 */
	private Double evaluateScore;
	
    /**
     * 业主昵称
     */
    private String nickName;
    
    /**
     * 上架时间
     */
    private Long passDate;

	/**
	 * 到期时间
	 */
	private Long tillDate;
    
    /**
     * 图片
     */
    private String picUrl;
	
	/**
	 * 最低价格
	 */
	private Integer minPrice;
	
	/**
	 * 最高价格
	 */
	private Integer maxPrice;
	
	/**
	 * 状态 默认-0，新上-1，未上-2
	 */
	private Integer status;
	
	/**
	 * 状态名称
	 */
	private String statusName;
	
	/**
	 * 库存总量
	 */
	private Integer totalStock;
	
	/**
	 * 次序
	 */
	private Integer order;
	
	/**
	 * 更新时间
	 */
	private Long refreshDate;
	
	
    /** 热门区域 */
    private Set<String> hotReginBusiness;
    
    
	/**
	 * @return the hotReginBusiness
	 */
	public Set<String> getHotReginBusiness() {
		return hotReginBusiness;
	}

	/**
	 * @param hotReginBusiness the hotReginBusiness to set
	 */
	public void setHotReginBusiness(Set<String> hotReginBusiness) {
		this.hotReginBusiness = hotReginBusiness;
	}

	/**
	 * @return the projectBid
	 */
	public String getProjectBid() {
		return projectBid;
	}

	/**
	 * @param projectBid the projectBid to set
	 */
	public void setProjectBid(String projectBid) {
		this.projectBid = projectBid;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**
	 * @return the projectAddress
	 */
	public String getProjectAddress() {
		return projectAddress;
	}

	/**
	 * @param projectAddress the projectAddress to set
	 */
	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	/**
	 * @return the houseModelCount
	 */
	public Integer getHouseModelCount() {
		return houseModelCount;
	}

	/**
	 * @param houseModelCount the houseModelCount to set
	 */
	public void setHouseModelCount(Integer houseModelCount) {
		this.houseModelCount = houseModelCount;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the dayOrderLockedStocks
	 */
	public Set<String> getDayOrderLockedStocks() {
		return dayOrderLockedStocks;
	}

	/**
	 * @param dayOrderLockedStocks the dayOrderLockedStocks to set
	 */
	public void setDayOrderLockedStocks(Set<String> dayOrderLockedStocks) {
		this.dayOrderLockedStocks = dayOrderLockedStocks;
	}

	/**
	 * @return the daySelfLockedStocks
	 */
	public Set<String> getDaySelfLockedStocks() {
		return daySelfLockedStocks;
	}

	/**
	 * @param daySelfLockedStocks the daySelfLockedStocks to set
	 */
	public void setDaySelfLockedStocks(Set<String> daySelfLockedStocks) {
		this.daySelfLockedStocks = daySelfLockedStocks;
	}

	/**
	 * @return the evaluateCount
	 */
	public Integer getEvaluateCount() {
		return evaluateCount;
	}

	/**
	 * @param evaluateCount the evaluateCount to set
	 */
	public void setEvaluateCount(Integer evaluateCount) {
		this.evaluateCount = evaluateCount;
	}

	/**
	 * @return the evaluateScore
	 */
	public Double getEvaluateScore() {
		return evaluateScore;
	}

	/**
	 * @param evaluateScore the evaluateScore to set
	 */
	public void setEvaluateScore(Double evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the passDate
	 */
	public Long getPassDate() {
		return passDate;
	}

	/**
	 * @param passDate the passDate to set
	 */
	public void setPassDate(Long passDate) {
		this.passDate = passDate;
	}

	public Long getTillDate() {
		return tillDate;
	}

	public void setTillDate(Long tillDate) {
		this.tillDate = tillDate;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @return the minPrice
	 */
	public Integer getMinPrice() {
		return minPrice;
	}

	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * @return the maxPrice
	 */
	public Integer getMaxPrice() {
		return maxPrice;
	}

	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the totalStock
	 */
	public Integer getTotalStock() {
		return totalStock;
	}

	/**
	 * @param totalStock the totalStock to set
	 */
	public void setTotalStock(Integer totalStock) {
		this.totalStock = totalStock;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * @return the refreshDate
	 */
	public Long getRefreshDate() {
		return refreshDate;
	}

	/**
	 * @param refreshDate the refreshDate to set
	 */
	public void setRefreshDate(Long refreshDate) {
		this.refreshDate = refreshDate;
	}

	
}
