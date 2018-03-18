package com.zra.house.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.pricerange.PriceRangeDto;
import com.zra.house.service.PriceRangeService;

/**
 * 价格范围logic
 * @author tianxf9
 *
 */
@Component
public class PriceRangeLogic {
	
	@Autowired
	private PriceRangeService priceRangeService;
	
	/**
	 * 获取价格范围列表
	 * @author tianxf9
	 * @return
	 */
	public List<PriceRangeDto> getAllEntitys() {
		return this.priceRangeService.getAllEntitys();
	}

}
