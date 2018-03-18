package com.zra.cardCoupon.service;

import com.zra.cardCoupon.dao.CardCouponMapper;
import com.zra.cardCoupon.entity.CardCouponUsageEntity;
import com.zra.common.dto.pay.CardCouponDto;
import com.zra.common.utils.KeyGenUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cuigh6 on 2017/5/23.
 */
@Service
public class CardCouponService {
	@Autowired
	private CardCouponMapper cardCouponMapper;

	public int addCardCouponUsage(CardCouponUsageEntity cardCouponUsageEntity) {
		cardCouponUsageEntity.setUsageId(KeyGenUtils.genKey());
		return cardCouponMapper.addCardCouponUsage(cardCouponUsageEntity);
	}

	public int updateCardCouponUsage(CardCouponUsageEntity c) {
		return cardCouponMapper.updateCardCouponUsage(c);
	}
	
	/**
	 * 获取已锁定的卡券
	 * @author tianxf9
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<CardCouponDto> getLockedCardCouponEntitys(String userId,Integer type) {
		return this.cardCouponMapper.getLockedCardCouponEntitys(userId, type);
	}
	
	/**
	 * 
	 * @param state
	 * @param code
	 * @return
	 */
	public int updateCardCouponState(Integer usageState,String code,Integer type) {
		return this.cardCouponMapper.updateCardCouponState(code, usageState, type);
	}
}
