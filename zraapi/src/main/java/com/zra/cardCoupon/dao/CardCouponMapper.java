package com.zra.cardCoupon.dao;

import com.zra.cardCoupon.entity.CardCouponUsageEntity;
import com.zra.common.dto.pay.CardCouponDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by cuigh6 on 2017/5/23.
 */
@Repository
public interface CardCouponMapper {
	int addCardCouponUsage(CardCouponUsageEntity cardCouponUsageEntity);

	int updateCardCouponUsage(CardCouponUsageEntity c);
	
	/**
	 * 查询已锁定的卡券
	 * @author tianxf9
	 * @param userId
	 * @param type
	 * @return
	 */
	List<CardCouponDto> getLockedCardCouponEntitys(@Param("userId")String userId,@Param("type")Integer type);
	
	/**
	 * 更新租金卡券使用状态
	 * @param code
	 * @param usageState
	 * @return
	 */
	int updateCardCouponState(@Param("code")String code,@Param("usageState")Integer usageState,@Param("type")Integer type);
}
