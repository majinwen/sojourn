package com.ziroom.minsu.services.search.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.search.vo.TonightDiscountInfoVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>房源的返回结果</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/26.
 * @version 1.0
 * @since 1.0
 */
/**
 * @author your name
 *
 */
public class HouseInfoEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -71456858025171L;

    /** id */
    private String fid;
    
    /** 房源fid */
    private String houseFid;

    /** 名称 */
    private String houseName;

    /** 地址 */
    private String houseAddr;

    /** 房屋类型 整租 合租 */
    private Integer rentWay;

    /** 房屋类型 整租 合租 */
    private String rentWayName;

    /** 极速 普通 */
    
    /** 极速 普通 */
    private Integer orderType;
    
    private String orderTypeName;

    /** 图片 */
    private String picUrl;

    /** 房东照片 */
    private String landlordUrl;


    /** 房东id */
    private String landlordUid;

    /** 房东昵称 */
    private String nickName;

    /** 更新时间 */
    private Long updateTime;

    /** 权重 */
    private Long weights;

    /** 权重构成明细 */
    private String weightsComposition;

    /** Solr得分() */
    private Double score;

    /**  */
    private Integer evaluateCount;

    /** 价格 */
    private Integer price;
    
    /** 原始价格 */
    private Integer originalPrice;

    /** 入住人数 */
    private Integer personCount;

    /** 房间数 */
    private Integer roomCount;

    /** 是否收藏 */
    private Integer isCollect = YesOrNoEnum.NO.getCode();

    /** 是否智能锁 */
    private Integer isLock;

    /** 床铺信息 */
    List<Map> bedList = new ArrayList<>();

    /** 卫生间数量 */
    private Integer toiletCount;

    /** 是否独立卫生间 */
    private Integer isToilet;

    /** 阳台数量 */
    private Integer balconyCount;

    /** 是否阳台 */
    private Integer isBalcony;

    /** 当前房源在指定时间是否可租 */
    private Integer isAvailable = YesOrNoEnum.YES.getCode();

    /** 城市编码 */
    private String cityCode;
    
    /** 城市名称 */
    private String cityName;


    List<ChildEntity> rooms = new ArrayList<ChildEntity>();

    List<ChildEntity> brotherRooms =  new ArrayList<ChildEntity>();

    List<ChildEntity> brotherBeds =  new ArrayList<ChildEntity>();

    /**
     * 获取当前的经纬度和目标房源的距离信息
     */
    private String dist;

    /** 坐标 */
    private String loc;

    /** 评价得分 */
    private Double evaluateScore;
    
    /** 真实评价得分 */
    private Double realEvaluateScore;

    /** 标签信息 */
    private LabelEntity labelInfo;
    
    private List<LabelEntity> labelInfoList;
    
    private List<LabelTipsEntity> labelTipsList;
    
    /**
     * 房源类型
     */
    private Integer houseType;
    private String houseTypeName;
    
    /**
     * 是否top50已经上线的房源
     */
    private Integer isTop50Online= YesOrNoEnum.NO.getCode();
    
    /** 个性化标签 */
    private List<String> indivLabelTipsList;
    
    /** 锁定日期  yyMMdd*/
    private List<String> occupyDays;
    
    /** top50标题 */
    private String top50Title;
    
    /** 房源品质登记 A B C  */
  	private String houseQualityGrade;
  	
  	/**
  	 * 是否设置今日特惠
  	 */
  	private Integer isToNightDiscount= YesOrNoEnum.NO.getCode();
  	
  	/**
  	 * 今日特惠信息
  	 */
  	private TonightDiscountInfoVo tonightDiscountInfoVo;
  	
    /** 60天内接受的单数 */
    private Long acceptOrder60DaysCount=0L;
    
    /** 60天总单数 */
    private Long order60DaysCount=0L;
    
    /** 60天内房东（申请类型房源）接受率 */
    private String acceptOrderRateIn60Days;
    
    /** 房东是否是是在30天审核通过 */
    private Integer isAuditPassIn30Days = YesOrNoEnum.NO.getCode();
    
    /** 房东是否是是在30天审核通过展示字段*/
    private String isAuditPassIn30DaysStr ;
    
    /** 热门区域 */
    private List<String> hotRegin;

    private Integer isNew = YesOrNoEnum.NO.getCode();

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public List<String> getHotRegin() {
		return hotRegin;
	}

	public void setHotRegin(List<String> hotRegin) {
		this.hotRegin = hotRegin;
	}

	public Long getAcceptOrder60DaysCount() {
		return acceptOrder60DaysCount;
	}

	public Long getOrder60DaysCount() {
		return order60DaysCount;
	}

	public void setAcceptOrder60DaysCount(Long acceptOrder60DaysCount) {
		this.acceptOrder60DaysCount = acceptOrder60DaysCount;
	}

	public void setOrder60DaysCount(Long order60DaysCount) {
		this.order60DaysCount = order60DaysCount;
	}

    public TonightDiscountInfoVo getTonightDiscountInfoVo() {
        return tonightDiscountInfoVo;
    }

    public void setTonightDiscountInfoVo(TonightDiscountInfoVo tonightDiscountInfoVo) {
        this.tonightDiscountInfoVo = tonightDiscountInfoVo;
    }

    public Integer getIsToNightDiscount() {
		return isToNightDiscount;
	}

	public void setIsToNightDiscount(Integer isToNightDiscount) {
		this.isToNightDiscount = isToNightDiscount;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getHouseQualityGrade() {
		return houseQualityGrade;
	}

	public void setHouseQualityGrade(String houseQualityGrade) {
		this.houseQualityGrade = houseQualityGrade;
	}

	public String getTop50Title() {
		return top50Title;
	}

	public void setTop50Title(String top50Title) {
		this.top50Title = top50Title;
	}

	public List<String> getOccupyDays() {
		return occupyDays;
	}

	public void setOccupyDays(List<String> occupyDays) {
		this.occupyDays = occupyDays;
	}

	public String getHouseFid() {
		return houseFid;
	}

	public void setHouseFid(String houseFid) {
		this.houseFid = houseFid;
	}

	public List<String> getIndivLabelTipsList() {
		return indivLabelTipsList;
	}

	public void setIndivLabelTipsList(List<String> indivLabelTipsList) {
		this.indivLabelTipsList = indivLabelTipsList;
	}

	public Integer getIsTop50Online() {
		return isTop50Online;
	}

	public void setIsTop50Online(Integer isTop50Online) {
		this.isTop50Online = isTop50Online;
	}

	public List<LabelTipsEntity> getLabelTipsList() {
		return labelTipsList;
	}

	public void setLabelTipsList(List<LabelTipsEntity> labelTipsList) {
		this.labelTipsList = labelTipsList;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getRealEvaluateScore() {
		return realEvaluateScore;
	}

	public void setRealEvaluateScore(Double realEvaluateScore) {
		this.realEvaluateScore = realEvaluateScore;
	}

	public List<LabelEntity> getLabelInfoList() {
		return labelInfoList;
	}

	public void setLabelInfoList(List<LabelEntity> labelInfoList) {
		this.labelInfoList = labelInfoList;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

    
    public Integer getHouseType() {
		return houseType;
	}

	public void setHouseType(Integer houseType) {
		this.houseType = houseType;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getLandlordUrl() {
        return landlordUrl;
    }

    public void setLandlordUrl(String landlordUrl) {
        this.landlordUrl = landlordUrl;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getWeights() {
        return weights;
    }

    public void setWeights(Long weights) {
        this.weights = weights;
    }

    public String getWeightsComposition() {
        return weightsComposition;
    }

    public void setWeightsComposition(String weightsComposition) {
        this.weightsComposition = weightsComposition;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public String getRentWayName() {
        return rentWayName;
    }

    public void setRentWayName(String rentWayName) {
        this.rentWayName = rentWayName;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public List<ChildEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<ChildEntity> rooms) {
        this.rooms = rooms;
    }

    public List<ChildEntity> getBrotherRooms() {
        return brotherRooms;
    }

    public void setBrotherRooms(List<ChildEntity> brotherRooms) {
        this.brotherRooms = brotherRooms;
    }

    public List<ChildEntity> getBrotherBeds() {
        return brotherBeds;
    }

    public void setBrotherBeds(List<ChildEntity> brotherBeds) {
        this.brotherBeds = brotherBeds;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Double getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(Double evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public List<Map> getBedList() {
        return bedList;
    }

    public void setBedList(List<Map> bedList) {
        this.bedList = bedList;
    }

    public Integer getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(Integer toiletCount) {
        this.toiletCount = toiletCount;
    }

    public Integer getIsToilet() {
        return isToilet;
    }

    public void setIsToilet(Integer isToilet) {
        this.isToilet = isToilet;
    }

    public Integer getBalconyCount() {
        return balconyCount;
    }

    public void setBalconyCount(Integer balconyCount) {
        this.balconyCount = balconyCount;
    }

    public Integer getIsBalcony() {
        return isBalcony;
    }

    public void setIsBalcony(Integer isBalcony) {
        this.isBalcony = isBalcony;
    }

    public String getLandlordUid() {
        return landlordUid;
    }

    public void setLandlordUid(String landlordUid) {
        this.landlordUid = landlordUid;
    }


    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }

    public LabelEntity getLabelInfo() {
        return labelInfo;
    }

    public void setLabelInfo(LabelEntity labelInfo) {
        this.labelInfo = labelInfo;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

	public String getAcceptOrderRateIn60Days() {
		return acceptOrderRateIn60Days;
	}

	public void setAcceptOrderRateIn60Days(String acceptOrderRateIn60Days) {
		this.acceptOrderRateIn60Days = acceptOrderRateIn60Days;
	}

	public Integer getIsAuditPassIn30Days() {
		return isAuditPassIn30Days;
	}

	public void setIsAuditPassIn30Days(Integer isAuditPassIn30Days) {
		this.isAuditPassIn30Days = isAuditPassIn30Days;
	}

	public String getIsAuditPassIn30DaysStr() {
		return isAuditPassIn30DaysStr;
	}

	public void setIsAuditPassIn30DaysStr(String isAuditPassIn30DaysStr) {
		this.isAuditPassIn30DaysStr = isAuditPassIn30DaysStr;
	}

}
