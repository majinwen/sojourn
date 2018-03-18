package com.zra.common.dto.pricerange;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 价格区间dto
 * @author tianxf9
 *
 */
public class PriceRangeDto {
	
	/**
	 * 价格范围id
	 */
	@ApiModelProperty(value = "价格范围id")
	private Integer id;
	
	/**
	 * 价格范围描述
	 */
	@ApiModelProperty(value="价格范围描述")
	private String rangeDescribe;
	
	/**
	 *价格最小值
	 */
	@ApiModelProperty(value = "价格最小值")
	private BigDecimal minPrice;
	
	/**
	 * 价格最大值
	 */
	@ApiModelProperty(value = "价格最大值")
	private BigDecimal maxPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRangeDescribe() {
		return rangeDescribe;
	}

	public void setRangeDescribe(String rangeDescribe) {
		this.rangeDescribe = rangeDescribe;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
}
