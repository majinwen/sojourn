package com.zra.house.entity.dto;

import java.util.List;

import com.zra.common.dto.pricerange.PriceRangeDto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 项目筛选条件Dto
 * @author tianxf9
 *
 */
public class SearchProjectCondition {
	
    /**
     * 城市列表
     */
    @ApiModelProperty(value = "城市列表")
    private List<CityDto> cityList;
    
    /**
     * 价格范围列表
     */
    @ApiModelProperty(value = "价格范围列表")
    private List<PriceRangeDto>  priceRangeList;
    
    @ApiModelProperty(value = "价格最大值（字符类型）")
    private String unlimitPriceStr;
    
    @ApiModelProperty(value = "价格最大值（Double类型）")
    private Double unlimitPrice;
    
	public List<CityDto> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityDto> cityList) {
		this.cityList = cityList;
	}

	public List<PriceRangeDto> getPriceRangeList() {
		return priceRangeList;
	}

	public void setPriceRangeList(List<PriceRangeDto> priceRangeList) {
		this.priceRangeList = priceRangeList;
	}

	public String getUnlimitPriceStr() {
		return unlimitPriceStr;
	}

	public void setUnlimitPriceStr(String unlimitPriceStr) {
		this.unlimitPriceStr = unlimitPriceStr;
	}

	public Double getUnlimitPrice() {
		return unlimitPrice;
	}

	public void setUnlimitPrice(Double unlimitPrice) {
		this.unlimitPrice = unlimitPrice;
	}
}
