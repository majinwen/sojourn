package com.zra.cardCoupon.logic;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.cardCoupon.entity.CardCouponUsageEntity;
import com.zra.cardCoupon.entity.ExtCouponEntity;
import com.zra.cardCoupon.service.CardCouponService;
import com.zra.common.dto.pay.*;
import com.zra.common.enums.CardCouponComeSourceEnum;
import com.zra.common.enums.CardCouponTypeEnum;
import com.zra.common.enums.CardCouponUsageState;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zra.common.enums.ErrorEnum.COUPON_CODE_INVALID;
import static com.zra.common.enums.ErrorEnum.COUPON_CODE_NO_PASS;

/**
 * Created by cuigh6 on 2017/5/22.
 * 卡券系统交互
 * <p>
 * 1.查询用户的优惠券<br>
 * 2.检查优惠券<br>
 * 3.使用优惠券<br>
 * 4.恢复优惠券<br>
 * <p>
 * 5.查询用户的租金卡<br>
 * 6.检查租金卡<br>
 * 7.使用租金卡<br>
 * 8.恢复租金卡<br>
 * 9.兑换优惠券<br>
 * </p>
 */
@Component
public class CardCouponLogic {

	@Autowired
	private CardCouponService cardCouponService;

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CardCouponLogic.class);
	


	/**
	 * 查询优惠券通过用户标识
	 *
	 * @param uid 用户标识
	 * @return
	 */
	public List<CardCouponDto> couponQuery(String uid,Integer couponStatus) {
		List<CardCouponDto> resultList = new ArrayList<>();
		List<ExtCouponEntity> list = CardCouponUtils.INSTANCE.couponGet(uid, couponStatus);
		for (ExtCouponEntity entity : list) {
			resultList.add(entityToDto(entity));
		}
		
		//modify by tianxf9 可以使用的优惠券 = 根据uid从卡券系统获取的优惠券 - card_coupon_usage表中保存态，未删除的优惠券
		List<CardCouponDto> lockedEntitys = this.cardCouponService.getLockedCardCouponEntitys(uid, CardCouponTypeEnum.COUPON.getIndex());
		
		if(CollectionUtils.isEmpty(lockedEntitys)) {
			return resultList;
		}
	    resultList.removeAll(lockedEntitys);
		//end by tianxf9
		return resultList;
	}
	
	/**
	 * 查询优惠券通过用户标识
	 *
	 * @param uid 用户标识
	 * @return
	 */
	public List<CardCouponDto> couponQueryForMs(CardCouponParamsDto params) {
		List<CardCouponDto> resultList = new ArrayList<>();
		List<ExtCouponEntity> list = CardCouponUtils.INSTANCE.couponGet(params.getUid(),
				SysConstant.CARD_COUPON_STATUS);
		for (ExtCouponEntity entity : list) {
			resultList.add(entityToDto(entity));
		}
		List<CardCouponDto> lockedEntitys = this.cardCouponService.getLockedCardCouponEntitys(params.getUid(),
				CardCouponTypeEnum.COUPON.getIndex());
		List<String> codes = params.getCode();
		if (CollectionUtils.isEmpty(codes)) {
			resultList.removeAll(lockedEntitys);
			return resultList;
		}
		List<CardCouponDto> removeEntitys = new ArrayList<>();
		for (CardCouponDto dto : lockedEntitys) {
			boolean isRemove = true;
			for (String code : codes) {
				if (code.equals(dto.getCode())) {
					isRemove = false;
					break;
				}
			}
			if (isRemove) {
				removeEntitys.add(dto);
			}
		}
		resultList.removeAll(removeEntitys);
		for (CardCouponDto dto : resultList) {
			for (String code : codes) {
				if(dto.getCode().equals(code)) {
					dto.setIsSelected(2);
				}
			}
		}
		return resultList;
	}

	/**
	 * 检查优惠券
	 *
	 * @param couponCode 优惠券编码
	 * @param uid        用户标识
	 * @return
	 */
	public ExtCouponEntity couponCheck(String couponCode, String uid) {
		ExtCouponEntity entity = CardCouponUtils.INSTANCE.couponCheck(couponCode, uid);
		if (entity == null) {
			throw new ResultException(ErrorEnum.COUPON_CODE_NO_PASS.getMessage(), COUPON_CODE_NO_PASS.getCode());
		}
		return entity;
	}
	
	/**
	 * 校验优惠券（for zrams）
	 * @author tianxf9
	 * @return
	 */
	public Map<String,Object> couponCheckForMs(CardCouponParamsDto params) {
		
		Map<String,Object> resultMap = new HashMap<>();
		ExtCouponEntity entity = null;
		
		if(CollectionUtils.isEmpty(params.getCode())||StringUtils.isBlank(params.getUid())) {
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "优惠券校验参数不能为空");
			resultMap.put(SysConstant.DATA, null);
			return resultMap;
		}
		
		try {
			entity = this.couponCheck(params.getCode().get(0), params.getUid());
			resultMap.put(SysConstant.STATE, SysConstant.SUCCESS);
			resultMap.put(SysConstant.MESSAGE, "优惠券校验通过！");
		} catch (Exception e) {
			LOGGER.error("优惠券校验不通过！", e);
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "优惠券校验不通过！请重新选择！");
		}
		CardCouponDto dto = null;
		if(entity!=null) {
			dto = entityToDto(entity);
		}
		resultMap.put(SysConstant.DATA, dto);
		return resultMap;
		
	}

	/**
	 * 使用优惠券
	 *
	 * @param code     优惠券编码
	 * @param uid      用户标识
	 * @param billFid  账单标识
	 * @param billType 类型
	 * @return
	 */
	public boolean couponUse(String code, String uid, String billFid, Integer billType,Integer comeSource) {
		String mobile = null;
		try {
			mobile = CardCouponUtils.INSTANCE.getPhoneByUid(uid);
		} catch (Exception e) {
			LOGGER.error("获取手机号报错", e);
		}
		boolean isOk = CardCouponUtils.INSTANCE.couponUse(code, uid, mobile);
		int affect = 0;
		if (isOk) {
			if(CardCouponComeSourceEnum.APP.getIndex()==comeSource.intValue()) {
				//记录优惠券消费表
				CardCouponUsageEntity cardCouponUsageEntity = new CardCouponUsageEntity(uid, billFid, code, billType, CardCouponUsageState.SUCCESS.getIndex(),comeSource);
				affect = cardCouponService.addCardCouponUsage(cardCouponUsageEntity);
			}else {
				this.cardCouponService.updateCardCouponState(CardCouponUsageState.SUCCESS.getIndex(), code, CardCouponTypeEnum.COUPON.getIndex());
			}
			LOGGER.info("记录优惠券消费表影响行数:{}", affect);
			return true;
		}
		throw new ResultException(ErrorEnum.COUPON_USE_FAIL);
	}
	
	/**
	 * 使用优惠券（zrams）
	 * @author tianxf9
	 * @param params
	 * @return
	 */
	public Map<String,Object> couponUseForMs(CardCouponParamsDto params) {
		
		Map<String,Object> resultMap = new HashMap<>();
		if(!validParamsIsNotNull(params)) {
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "参数不能为空！");
		}
		
		try {
			this.couponUse(params.getCode().get(0), params.getUid(), params.getVoucherId(), params.getBillType(), params.getComeSource());
			resultMap.put(SysConstant.STATE, SysConstant.SUCCESS);
			resultMap.put(SysConstant.MESSAGE, "使用优惠券成功!");
		} catch (Exception e) {
			LOGGER.error("使用优惠券失败！", e);
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "使用优惠券失败！");
		}
		return resultMap;
	}
	
	/**
	 * 参数校验
	 * @author tianxf9
	 * @return
	 */
	public boolean validParamsIsNotNull(CardCouponParamsDto params) {

		if (params == null) {
			return false;
		}
		if (CollectionUtils.isEmpty(params.getCode())) {
			return false;
		}
		if (StringUtils.isBlank(params.getUid())) {
			return false;
		}
		if (StringUtils.isBlank(params.getVoucherId())) {
			return false;
		}
		if (params.getComeSource() == null) {
			return false;
		}

		return true;
	}

	/**
	 * 优惠券恢复
	 *
	 * @param couponCode 优惠券编码
	 * @return
	 */
	public boolean couponRecovery(String couponCode) {
		CardCouponUsageEntity c = new CardCouponUsageEntity();
		boolean isOk = CardCouponUtils.INSTANCE.couponRecovery(couponCode);
		if (isOk) {
			// 更改优惠券消费表状态 恢复成功
			c.setUsageState(SysConstant.CARD_COUPON_USAGE_STATE_YTH);
			c.setIsDel(1);
		} else {
			// 更改优惠券消费表状态 恢复失败
			c.setUsageState(SysConstant.CARD_COUPON_USAGE_STATE_HTSB);
			c.setIsDel(1);
		}
		c.setCouponCode(couponCode);
		int affect = this.cardCouponService.updateCardCouponUsage(c);
		LOGGER.info("记录优惠券恢复状态影响行数:{}", affect);
		if (!isOk) {
			boolean recoveryCardCouponMsg = Boolean.valueOf(PropUtils.getString(ZraApiConst.CARD_COUPON_FAIL_MSG));
			if (recoveryCardCouponMsg) {
				String cusPhone = String.valueOf(PropUtils.getString(ZraApiConst.RECEIVE_CARD_COUPON_PHONE));
				if (StrUtils.isNotNullOrBlank(cusPhone) && StringUtil.isPhoneNum(cusPhone)) {
					String smsContent = "优惠券【" + couponCode + "】恢复失败！请查看原因：并帮客户回复优惠券！";
					LOGGER.info("[优惠券恢复失败推送]手机号：" + cusPhone + "\t短信内容：" + smsContent);
					SmsUtils.INSTANCE.sendSMS(smsContent, cusPhone);
				}
			}else {
				LOGGER.info("[优惠券恢复失败推送]手机号无效！");
			}
		}
		return isOk;
	}

	/**
	 * 租金卡查询
	 *
	 * @param uid 用户标识
	 * @return
	 */
	public List<CardCouponDto> cardQuery(String uid,Integer cardStatus) {
		List<CardCouponDto> resultList = new ArrayList<>();
		List<ExtCouponEntity> list = CardCouponUtils.INSTANCE.cardGet(uid, cardStatus);
		for (ExtCouponEntity entity : list) {
			resultList.add(entityToDto(entity));
		}
		
		//modify by tianxf9 
		List<CardCouponDto> lockedEntitys = this.cardCouponService.getLockedCardCouponEntitys(uid, CardCouponTypeEnum.CARD.getIndex());
		if(CollectionUtils.isEmpty(lockedEntitys)) {
			return resultList;
		}
	    resultList.removeAll(lockedEntitys);
	    //end by tianxf9
		return resultList;
	}
	
	/**
	 * 查询租金卡for ms
	 * @param
	 * @param
	 * @return
	 */
	public List<CardCouponDto> cardQueryForMs(CardCouponParamsDto params) {
		List<CardCouponDto> resultList = new ArrayList<>();
		List<ExtCouponEntity> list = CardCouponUtils.INSTANCE.cardGet(params.getUid(),
				SysConstant.CARD_COUPON_STATUS);
		for (ExtCouponEntity entity : list) {
			resultList.add(entityToDto(entity));
		}
		List<CardCouponDto> lockedEntitys = this.cardCouponService.getLockedCardCouponEntitys(params.getUid(),
				CardCouponTypeEnum.CARD.getIndex());
		List<String> codes = params.getCode();
		if (CollectionUtils.isEmpty(codes)) {
			resultList.removeAll(lockedEntitys);
			return resultList;
		}
		List<CardCouponDto> removeEntitys = new ArrayList<>();
		for (CardCouponDto dto : lockedEntitys) {
			boolean isRemove = true;
			for (String code : codes) {
				if (code.equals(dto.getCode())) {
					isRemove = false;
					break;
				}
			}
			if (isRemove) {
				removeEntitys.add(dto);
			}
		}
		resultList.removeAll(removeEntitys);
		for (CardCouponDto dto : resultList) {
			for (String code : codes) {
				if(code.equals(dto.getCode())) {
					dto.setIsSelected(2);
				}
			}
		}
		return resultList;

	}

	/**
	  * @description: 查询员工租金卡及卡券新方法
	  * @author: lusp
	  * @date: 2017/12/23 下午 17:00
	  * @params:
	  * @return:
	  */
	public CardCouponQueryResponse cardQueryForMsNew(CardCouponQueryRequest params) {
		List<CardCouponDto> resultList = new ArrayList<>();
		CardCouponQueryResponse cardCouponQueryResponse = CardCouponUtils.INSTANCE.cardGetNew(params);
		return cardCouponQueryResponse;
	}

	/**
	 * @description: 消费员工租金卡及卡券新方法
	 * @author: lusp
	 * @date: 2017/12/24 下午 23:01
	 * @params:
	 * @return:
	 */
	public CardCouponUseResponse cardUseForMsNew(CardCouponUseRequest params) {
		List<CardCouponDto> resultList = new ArrayList<>();
		return CardCouponUtils.INSTANCE.cardUseNew(params);
	}

	/**
	 * 租金卡检查
	 *
	 * @param couponCodes 租金卡编码数组
	 * @param uid         用户标识
	 * @return
	 */
	public List<ExtCouponEntity> cardCheck(List<String> couponCodes, String uid) {
		List<ExtCouponEntity> resultList = new ArrayList<>();
		StringBuilder failure = new StringBuilder();
		for (String code : couponCodes) {
			ExtCouponEntity entity = CardCouponUtils.INSTANCE.cardCheck(code, uid);
			if (entity == null) {
				failure.append(code).append(",");
				continue;
			}
			resultList.add(entity);
		}
		if (failure.length() > 1) {
			throw new ResultException(failure.toString() + ":" + ErrorEnum.RENT_CARD_CODE_NO_PASS.getMessage(), ErrorEnum.RENT_CARD_CODE_NO_PASS.getCode());
		}
		return resultList;
	}
	
	/**
	 * 校验租金卡（for zrams）
	 * @author tianxf9
	 * @return
	 */
	public Map<String,Object> cardCheckForMs(CardCouponParamsDto params) {
		Map<String,Object> resultMap = new HashMap<>();
		List<ExtCouponEntity> resultList = new ArrayList<>();
		
		if(CollectionUtils.isEmpty(params.getCode())||StringUtils.isBlank(params.getUid())) {
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "租金卡校验参数不能为空！");
			resultMap.put(SysConstant.DATA, null);
		}
		
		try {
			resultList = this.cardCheck(params.getCode(), params.getUid());
			resultMap.put(SysConstant.STATE, SysConstant.SUCCESS);
			resultMap.put(SysConstant.MESSAGE, "租金卡校验通过！");
		} catch (Exception e) {
			LOGGER.error("租金卡校验不通过！", e);
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "租金卡校验不通过！请重新选择！");
		}
		List<CardCouponDto> cardCouponDtos = new ArrayList<>();
		for(ExtCouponEntity exEntity:resultList) {
			cardCouponDtos.add(entityToDto(exEntity));
		}
		resultMap.put(SysConstant.DATA, cardCouponDtos);
		return resultMap;
	}

	/**
	 * 租金卡使用
	 * 循环调用租金卡消费接口，如果租金卡有租金卡消费失败，则回退租金卡（临时解决方案）
	 *
	 * @param codes    租金卡编码
	 * @param uid      用户标识
	 * @param billFid  账单标识
	 * @param billType 卡券类型
	 * @return
	 */
	public boolean cardUse(List<String> codes, String uid, String billFid, Integer billType,Integer comeSource) {
		List<String> failure = new ArrayList<>();
		boolean success;
		for (String code : codes) {
			success = CardCouponUtils.INSTANCE.cardUse(code, uid);
			if (!success) {
				failure.add(code);//添加到失败列表
				continue;
			}
			//记录优惠券消费表
			CardCouponUsageEntity cardCouponUsageEntity = new CardCouponUsageEntity(uid, billFid, code,
					billType, CardCouponUsageState.SUCCESS.getIndex(),comeSource);
			int affect = cardCouponService.addCardCouponUsage(cardCouponUsageEntity);
			LOGGER.info("记录租金卡消费表影响行数:{}", affect);
		}
		if (failure.size() <=1) {
			return true;
		}
		for (String failCode : failure) {//恢复卡券
			this.cardRecovery(failCode);
		}
		if (!failure.isEmpty()) {
			throw new ResultException(ErrorEnum.CARD_USE_FAIL); //租金卡消费失败
		}
		return true;
	}

	
	/**
	 * 租金卡使用
	 * 循环调用租金卡消费接口，如果租金卡有租金卡消费失败，则回退租金卡（临时解决方案）
	 *
	 * @param codes    租金卡编码
	 * @param uid      用户标识
	 * @return
	 */
	public boolean cardUseForMs(List<String> codes, String uid) {
		
		List<String> failure = new ArrayList<>();
		boolean success;
		for (String code : codes) {
			success = CardCouponUtils.INSTANCE.cardUse(code, uid);
			//记录优惠券消费表
			if(success) {
				this.cardCouponService.updateCardCouponState(CardCouponUsageState.SUCCESS.getIndex(),code,CardCouponTypeEnum.CARD.getIndex());
			}else {
				failure.add(code);//添加到失败列表
				this.cardCouponService.updateCardCouponState(CardCouponUsageState.CONSUM_FAIL.getIndex(),code,CardCouponTypeEnum.CARD.getIndex());
			}
		}
		if (failure.size() <=1) {
			return true;
		}
		for (String failCode : failure) {//恢复卡券
			this.cardRecovery(failCode);
		}
		if (!failure.isEmpty()) {
			throw new ResultException(ErrorEnum.CARD_USE_FAIL); //租金卡消费失败
		}
		return true;
	}
	/**
	 * 使用租金卡
	 * @author tianxf9
	 * @param params
	 * @return
	 */
	public Map<String,Object> cardUseForMs(CardCouponParamsDto params) {
		
		Map<String,Object> resultMap = new HashMap<>();
		if(!validParamsIsNotNull(params)) {
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "参数不能为空！");
		}
		try {
			this.cardUseForMs(params.getCode(), params.getUid());
		} catch (Exception e) {
			LOGGER.error("租金卡使用失败！", e);
			resultMap.put(SysConstant.STATE, SysConstant.FAIL);
			resultMap.put(SysConstant.MESSAGE, "使用租金卡失败！");
		}
		resultMap.put(SysConstant.STATE, SysConstant.SUCCESS);
		resultMap.put(SysConstant.MESSAGE, "使用租金卡成功！");
		return resultMap;
	}

	/**
	 * 更新消费卡券记录
	 *
	 * @param entity 卡券对象
	 * @return
	 */
	public int updateCardCouponUsage(CardCouponUsageEntity entity) {
		return this.cardCouponService.updateCardCouponUsage(entity);
	}

	/**
	 * 租金卡恢复
	 *
	 * @param rentCardCode 租金卡编码
	 * @return
	 */
	public boolean cardRecovery(String rentCardCode) {
		CardCouponUsageEntity c = new CardCouponUsageEntity();
		boolean isOk = CardCouponUtils.INSTANCE.cardRecovery(rentCardCode);
		if (isOk) {
			//更改租金卡消费表状态 恢复成功
			c.setUsageState(SysConstant.CARD_COUPON_USAGE_STATE_YTH);
			c.setIsDel(1);
		} else {
			//更改租金卡消费表状态 恢复失败
			c.setUsageState(SysConstant.CARD_COUPON_USAGE_STATE_HTSB);
			c.setIsDel(1);
		}
		c.setRentCardCode(rentCardCode);
		int affect = this.cardCouponService.updateCardCouponUsage(c);
		LOGGER.info("记录租金卡恢复状态影响行数:{}", affect);
		if (!isOk) {
			boolean recoveryCardCouponMsg = Boolean.valueOf(PropUtils.getString(ZraApiConst.CARD_COUPON_FAIL_MSG));
			if (recoveryCardCouponMsg) {
				String cusPhone = String.valueOf(PropUtils.getString(ZraApiConst.RECEIVE_CARD_COUPON_PHONE));
				if (StrUtils.isNotNullOrBlank(cusPhone) && StringUtil.isPhoneNum(cusPhone)) {
					String smsContent = "租金卡【" + rentCardCode + "】恢复失败！请查看原因：并帮客户回复租金卡！";
					LOGGER.info("[租金卡恢复失败推送]手机号：" + cusPhone + "\t短信内容：" + smsContent);
					SmsUtils.INSTANCE.sendSMS(smsContent, cusPhone);
				}
			}else {
				LOGGER.info("[优惠券恢复失败推送]手机号无效！");
			}
		}
		return isOk;
	}

	/**
	 * 优惠券兑换
	 *
	 * @param couponCode 优惠券编码
	 * @param uid        用户标识
	 * @return
	 */
	public CardCouponDto couponBind(String couponCode, String uid) {
		if (couponCode == null || "".equals(couponCode) || uid == null || "".equals(uid)) {
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ExtCouponEntity entity = CardCouponUtils.INSTANCE.couponBind(couponCode, uid);

		if (entity != null) {
			return entityToDto(entity);
		}
		throw new ResultException(COUPON_CODE_INVALID);
	}

	/**
	 * 数据转换
	 *
	 * @param entity 业务实体
	 */
	private CardCouponDto entityToDto(ExtCouponEntity entity) {
		CardCouponDto dto = new CardCouponDto();
		dto.setMoney(CommonUtil.doubleToString(CommonUtil.fenTOYuan(entity.getMoney())));
		dto.setCode(entity.getCode());
		dto.setDesc(entity.getDesc());
		dto.setName(entity.getName());
		dto.setRule(entity.getRule());
		String startDate = null;
		String endDate = null;
		try {
			startDate = dateFormatterConvert(entity.getStart_time(), DateUtil.TIME_FORMAT, DateUtil.DATE_FORMAT);
			endDate = dateFormatterConvert(entity.getEnd_time(), DateUtil.TIME_FORMAT, DateUtil.DATE_FORMAT);
		} catch (Exception e) {
			LOGGER.error("日期解析失败", e);
		}
		dto.setStartTime(startDate);
		dto.setEndTime(endDate);
		return dto;
	}

	private String dateFormatterConvert(String dateStr, String sourcePattern, String targetPattern) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(sourcePattern);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(targetPattern);
		return dateFormat1.format(dateFormat.parse(dateStr));
	}

}