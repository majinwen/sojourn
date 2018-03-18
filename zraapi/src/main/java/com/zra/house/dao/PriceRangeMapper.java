package com.zra.house.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zra.common.dto.pricerange.PriceRangeDto;

/**
 * 价格范围Dto
 * @author tianxf9
 *
 */
@Repository
public interface PriceRangeMapper {
	
	public List<PriceRangeDto> getAllPriceRangeEntitys();

}
