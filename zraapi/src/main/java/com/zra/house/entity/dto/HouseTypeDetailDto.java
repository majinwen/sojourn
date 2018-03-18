package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/5 15:40
 * @since 1.0
 */
public class HouseTypeDetailDto {

    /**
     * 户型名称
     */
    @ApiModelProperty(value = "户型名称")
    private String  houseTypeName;

    /**
     * 房型介绍
     */
    @ApiModelProperty(value = "房型介绍")
    private String roomIntroduction;

    /**
     * 全景看房链接
     */
    @ApiModelProperty(value = "全景看房链接")
    private String panoramicUrl;

    /**
     * 分享链接
     */
    @ApiModelProperty(value = "分享链接")
    private String shareUrl;
    /**
     * 房型图片列表
     */
    @ApiModelProperty(value = "房型图片列表")
    private List<HouseTypeImgDto> houseTypeImgDtoList;

    /**
     * 房型配置
     */
    @ApiModelProperty(value = "房型配置")
    private List<HouseConfigDto> houseConfigDtoList;
    //added by wangxm113 for M站
    @ApiModelProperty(value = "最低价格")
    private String minPrice;
    @ApiModelProperty(value = "最高价格")
    private String maxPrice;
    @ApiModelProperty(value = "联系管家")
    private String phone;
    
    //add by tianxf9 0608App优化
    @ApiModelProperty(value ="简介地址")
    private String proAddrDesc;
    @ApiModelProperty(value = "项目经度")
    private Double lng;
    @ApiModelProperty(value = "项目纬度")
    private Double lat;
    @ApiModelProperty(value ="核心标签")
    private String coreLab;
    @ApiModelProperty(value="基本标签")
    private List<String> basicLab;
    @ApiModelProperty(value="活动标签")
    private List<HouseTypeLabDto> activityLab;
    //end by tianxf9
    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPanoramicUrl() {
        return panoramicUrl;
    }

    public void setPanoramicUrl(String panoramicUrl) {
        this.panoramicUrl = panoramicUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public String getRoomIntroduction() {
        return roomIntroduction;
    }

    public void setRoomIntroduction(String roomIntroduction) {
        this.roomIntroduction = roomIntroduction;
    }

    public List<HouseTypeImgDto> getHouseTypeImgDtoList() {
        return houseTypeImgDtoList;
    }

    public void setHouseTypeImgDtoList(List<HouseTypeImgDto> houseTypeImgDtoList) {
        this.houseTypeImgDtoList = houseTypeImgDtoList;
    }

    public List<HouseConfigDto> getHouseConfigDtoList() {
        return houseConfigDtoList;
    }

    public void setHouseConfigDtoList(List<HouseConfigDto> houseConfigDtoList) {
        this.houseConfigDtoList = houseConfigDtoList;
    }

	public String getProAddrDesc() {
		return proAddrDesc;
	}

	public void setProAddrDesc(String proAddrDesc) {
		this.proAddrDesc = proAddrDesc;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getCoreLab() {
		return coreLab;
	}

	public void setCoreLab(String coreLab) {
		this.coreLab = coreLab;
	}
	
	
	public List<String> getBasicLab() {
		return basicLab;
	}

	public void setBasicLab(List<String> basicLab) {
		this.basicLab = basicLab;
	}

	public List<HouseTypeLabDto> getActivityLab() {
		return activityLab;
	}

	public void setActivityLab(List<HouseTypeLabDto> activityLab) {
		this.activityLab = activityLab;
	}
    
    
}
