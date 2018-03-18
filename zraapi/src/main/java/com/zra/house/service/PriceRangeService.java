package com.zra.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.dto.pricerange.PriceRangeDto;
import com.zra.house.dao.PriceRangeMapper;

@Service
public class PriceRangeService {
	
	@Autowired
	private PriceRangeMapper priceRangeMapper;
	
	/**
	 * 获取所有价格范围
	 * @author tianxf9
	 * @return
	 */
	public List<PriceRangeDto> getAllEntitys() {
		return priceRangeMapper.getAllPriceRangeEntitys();
	}

}
