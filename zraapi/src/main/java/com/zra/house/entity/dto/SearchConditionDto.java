package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

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
 * @date 2016/8/1 15:27
 * @since 1.0
 */
public class SearchConditionDto {

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市列表")
    private List<CityDto> cityList;


    /**
     * 价格
     */
    @ApiModelProperty(value = "价格区间")
    private PriceDto price;

    /**
     * 入住时间
     */
    @ApiModelProperty(value = "入住时间")
    private List<Map> checkInTime;

    /**
     * 条件排序
     */
    @ApiModelProperty(value = "条件排序")
    private List<Map> sortList;

    public List<Map> getSortList() {
        return sortList;
    }

    public void setSortList(List<Map> sortList) {
        this.sortList = sortList;
    }

    public List<CityDto> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityDto> cityList) {
        this.cityList = cityList;
    }

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(PriceDto price) {
        this.price = price;
    }

    public List<Map> getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(List<Map> checkInTime) {
        this.checkInTime = checkInTime;
    }
}
