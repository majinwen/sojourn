package com.ziroom.minsu.services.house.entity;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.house.*;

import com.ziroom.minsu.valenum.house.RentWayEnum;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>订单需要的房源信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/2.
 * @version 1.0
 * @since 1.0
 */
public class OrderNeedHouseVo extends HouseBaseMsgEntity{

	/**
     * 序列化id
     */
    private static final long serialVersionUID = 258945869781065776L;
    
	// 国家code
	private String nationCode;

	// 省code
	private String provinceCode;

	// 城市code
	private String cityCode;

	// 区code
	private String areaCode;
	
    // 房间名称
    private String roomName;
    
    // 房间编号
    private String roomSn;

    // 床铺编号
    private String bedSn;

	// 房源默认图片
	private HousePicMsgEntity houseDefaultPic;

	//单价
    private Integer price;

	// 房源保洁费(分)
	private Integer roomCleaningFees;
    
    //房间fid
    private String roomFid;
    
    //床的fid
    private String bedFid;
    
    /** 房源的配置信息 */
    private List<HouseConfMsgEntity> HouseConfList = new ArrayList<>();

    /** 房源的价格 */
    private List<SpecialPriceVo> housePriceList;
    
    /**
     * 房源或者房间周末价格规则列表
     */
    List<HousePriceWeekConfEntity> houseWeekPriceList;
    
    //房源基础信息扩展表
    private HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
	/**
	 * 房间基础信息扩展
	 */
    private HouseRoomExtEntity houseRoomExtEntity;
    
    /**
     * 合租房源状态 10:待发布,11:已发布,20:信息审核通过,21:信息审核未通过,30:照片审核未通过,40:上架,41:下架,50:强制下架
     */
    private Integer roomStatus;
    
    /**
     * 房间入住人数限制
     */
    private Integer roomCheckInLimit;
    
    /**
     *  有效值字典
     */
    private Map<String,Object> enumDicMap = new HashMap<>();
    
	public Map<String, Object> getEnumDicMap() {
		return enumDicMap;
	}
	public void setEnumDicMap(Map<String, Object> enumDicMap) {
		this.enumDicMap = enumDicMap;
	}
	public String transName() {
		if (Check.NuNObj(getRentWay())){
			return "";
		}
		if (RentWayEnum.HOUSE.getCode() == getRentWay()){
			return getHouseName();
		}else {
			return getRoomName();
		}
	}
	/**
	 * 获取当前订单的房源编号
	 * @author jixd
	 * @created 2016年11月18日 16:38:03
	 * @param
	 * @return
	 */
	public String getHouserOrRoomSn(){
		if (RentWayEnum.HOUSE.getCode() == getRentWay()){
			return getHouseSn();
		}else{
			return getRoomSn();
		}
	}

	/**
	 * @return the roomCheckInLimit
	 */
	public Integer getRoomCheckInLimit() {
		return roomCheckInLimit;
	}

	/**
	 * @param roomCheckInLimit the roomCheckInLimit to set
	 */
	public void setRoomCheckInLimit(Integer roomCheckInLimit) {
		this.roomCheckInLimit = roomCheckInLimit;
	}

	/**
	 * @return the roomStatus
	 */
	public Integer getRoomStatus() {
		return roomStatus;
	}

	/**
	 * @param roomStatus the roomStatus to set
	 */
	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	public HouseBaseExtEntity getHouseBaseExtEntity() {
		return houseBaseExtEntity;
	}

	public void setHouseBaseExtEntity(HouseBaseExtEntity houseBaseExtEntity) {
        if(Check.NuNObj(houseBaseExtEntity)){
            return;
        }
		this.houseBaseExtEntity = houseBaseExtEntity;
	}

	public String getRoomFid() {
   		return roomFid;
   	}

   	public void setRoomFid(String roomFid) {
   		this.roomFid = roomFid;
   	}

   	public String getBedFid() {
   		return bedFid;
   	}

   	public void setBedFid(String bedFid) {
   		this.bedFid = bedFid;
   	}

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public String getRoomName() {
    	return roomName;
    }
    
    public void setRoomName(String roomName) {
    	this.roomName = roomName;
    }

    public String getBedSn() {
        return bedSn;
    }

    public void setBedSn(String bedSn) {
        this.bedSn = bedSn;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<HouseConfMsgEntity> getHouseConfList() {
        return HouseConfList;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getRoomCleaningFees() {
		return roomCleaningFees;
	}

	public void setRoomCleaningFees(Integer roomCleaningFees) {
		this.roomCleaningFees = roomCleaningFees;
	}

	public void setHouseConfList(List<HouseConfMsgEntity> houseConfList) {
        HouseConfList = houseConfList;
    }

   	public HousePicMsgEntity getHouseDefaultPic() {
   		return houseDefaultPic;
   	}

   	public void setHouseDefaultPic(HousePicMsgEntity houseDefaultPic) {
   		this.houseDefaultPic = houseDefaultPic;
   	}
   	
	public List<SpecialPriceVo> getHousePriceList() {
		return housePriceList;
	}

	public void setHousePriceList(List<SpecialPriceVo> housePriceList) {
		this.housePriceList = housePriceList;
	}

	public List<HousePriceWeekConfEntity> getHouseWeekPriceList() {
		return houseWeekPriceList;
	}

	public void setHouseWeekPriceList(
			List<HousePriceWeekConfEntity> houseWeekPriceList) {
		this.houseWeekPriceList = houseWeekPriceList;
	}

	public String getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(String roomSn) {
		this.roomSn = roomSn;
	}

	public HouseRoomExtEntity getHouseRoomExtEntity() {
		return houseRoomExtEntity;
	}

	public void setHouseRoomExtEntity(HouseRoomExtEntity houseRoomExtEntity) {
		this.houseRoomExtEntity = houseRoomExtEntity;
	}
}
