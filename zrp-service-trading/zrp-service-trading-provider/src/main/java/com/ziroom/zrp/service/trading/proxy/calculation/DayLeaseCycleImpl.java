package com.ziroom.zrp.service.trading.proxy.calculation;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.dto.ZrpActRequest;
import com.ziroom.minsu.services.cms.entity.ZrpActFeeVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.valenum.cms.ActCustomerTypeEnum;
import com.ziroom.minsu.valenum.cms.SignTypeEnum;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.valenum.ActivityCategoryEnum;
import com.ziroom.zrp.service.trading.valenum.ContractSignTypeEnum;
import com.ziroom.zrp.service.trading.valenum.CustomerTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import com.zra.common.constant.BillMsgConstant;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>日租 金额计算</p>
 * <p>实际房租 = 房租 x 房租调幅</p>
 * <p>总房租=实际房租价格 x 12/365 x 天数</p>
 * <p>服务费=总房租 x 服务费调幅 x 标准服务费率</p>
 * <p>押金= 实际房租</p>
 * <p>说明：小于30天 没有押金</p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月12日 20:27
 * @since 1.0
 */
public class DayLeaseCycleImpl implements LeaseCycle {
	private static final Logger LOGGER = LoggerFactory.getLogger(DayLeaseCycleImpl.class);
	private RentalRate rentalRate;

	public DayLeaseCycleImpl(LeaseCyclePojo leaseCyclePojo) throws Exception{
		rentalRate = new RentalRate(leaseCyclePojo);
	}

	@Override
	public PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) throws Exception{
		BigDecimal totalMoney;// 总金额
		BigDecimal totalRentPrice; // 总房租
		BigDecimal dayRentPrice; // 日房租
		BigDecimal servicePrice; // 服务费
		BigDecimal depositPrice = BigDecimal.ZERO; // 押金
		BigDecimal actualRentPrice; // 实际房租价格
		Double roomSalesPrice = paymentBaseDataPojo.getContractPojo().getRoomSalesPrice();
		String signType = paymentBaseDataPojo.getContractPojo().getSignType();
		Integer customerType = paymentBaseDataPojo.getContractPojo().getCustomerType();
		PaymentTermsDto paymentTermsDto = new PaymentTermsDto();
		Double preConDeposit = 0D;
		//计算租赁时常
		Integer dayDiff = DateSplitUtil.countDateSplitNeq(paymentBaseDataPojo.getContractPojo().getConStartDate(),
				paymentBaseDataPojo.getContractPojo().getConEndDate()) + 1;
		actualRentPrice = new BigDecimal(roomSalesPrice * rentalRate.rentPriceStrategyEntity.getShortPriceRate()).setScale(0, RoundingMode.HALF_UP);
		dayRentPrice = actualRentPrice.multiply(new BigDecimal(12)).divide(new BigDecimal(365), 0, BigDecimal.ROUND_HALF_UP);
		totalRentPrice = dayRentPrice.multiply(new BigDecimal(dayDiff));
		servicePrice = totalRentPrice.multiply(new BigDecimal(rentalRate.rentPriceStrategyEntity.getShortSPriceRate())).multiply(rentalRate.getStandardServiceRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal originServicePrice = servicePrice;// 原始金额
		// 计算活动 减免服务费
		ZrpActRequest zrpActRequest = new ZrpActRequest();
		zrpActRequest.setCommonFee(servicePrice);
		zrpActRequest.setRentDay(DateSplitUtil.countDateSplitNeq(paymentBaseDataPojo.getContractPojo().getConStartDate(),
				paymentBaseDataPojo.getContractPojo().getConEndDate()) + 1);
		zrpActRequest.setUid(paymentBaseDataPojo.getContractPojo().getUid());
		zrpActRequest.setProjectId(paymentBaseDataPojo.getContractPojo().getProjectId());
		zrpActRequest.setRoomId(paymentBaseDataPojo.getContractPojo().getRoomId());
		zrpActRequest.setLayoutId(paymentBaseDataPojo.getRoomPojo().getHouseId());
		zrpActRequest.setCustomerType(customerType == CustomerTypeEnum.PERSON.getCode() ?
				ActCustomerTypeEnum.PERSON.getCode() : customerType == CustomerTypeEnum.ENTERPRICE.getCode() ? ActCustomerTypeEnum.ENTERPRISE.getCode() : ActCustomerTypeEnum.UNLIMITED.getCode());
		zrpActRequest.setSignType(signType.equals(ContractSignTypeEnum.NEW.getValue()) ?
				SignTypeEnum.NEW_SIGN.getCode() : signType.equals(ContractSignTypeEnum.RENEW.getValue()) ? SignTypeEnum.RENEW.getCode() : SignTypeEnum.UNLIMITED.getCode());
		String paramJson = JsonEntityTransform.Object2Json(zrpActRequest);
		DataTransferObject dto = rentalRate.getActivityConfigList(paramJson);
		List<ZrpActFeeVo> actList = dto.parseData("actList", new TypeReference<List<ZrpActFeeVo>>() {
		});

		List<RentContractActivityEntity> list = new ArrayList<>();
		RentContractActivityEntity activityEntity;
		double activityDisCount = 0d;
		for (ZrpActFeeVo zrpActFeeVo : actList) {
			activityEntity = new RentContractActivityEntity();
			activityEntity.setExpenseItemCode(CostCodeEnum.KHFWF.getZraCode());
			activityEntity.setCategory(ActivityCategoryEnum.ACTIVITY.getCode());
			activityEntity.setDiscountAccount(zrpActFeeVo.getDiscountAmount());
			activityEntity.setActivityName(zrpActFeeVo.getActName());
			activityEntity.setActivityNumber(zrpActFeeVo.getActSn());
			activityEntity.setContractId(paymentBaseDataPojo.getContractPojo().getContractId());
			list.add(activityEntity);
			activityDisCount += zrpActFeeVo.getDiscountAmount();
		}
		servicePrice = servicePrice.subtract(BigDecimal.valueOf(activityDisCount)).setScale(0, BigDecimal.ROUND_HALF_UP);

		if (dayDiff < 30) {//签约日期小于30天 押金为一天的房租
			// 2017/9/15 是否有其他逻辑
			depositPrice = dayRentPrice;
		} else if (dayDiff >= 30) {//签约日期大于等于30天
			depositPrice = actualRentPrice;
		}

		//判断是否为续约 对服务费处理
		if (signType.equals(ContractSignTypeEnum.RENEW.getValue())) {
			servicePrice = rentalRate.getDiscountServiceFee(paymentBaseDataPojo, list, servicePrice);
			preConDeposit = paymentBaseDataPojo.getContractPojo().getDepositPrice();// 前押金价格
			// 如果原合同押金< 现合同押金 需要补交差值   如果大于不变
			if (preConDeposit.compareTo(depositPrice.doubleValue()) == 1) {
				depositPrice = BigDecimal.valueOf(preConDeposit);
			}
		}
		BigDecimal renewDepositPriceDiff = depositPrice.subtract(BigDecimal.valueOf(preConDeposit)).setScale(0,BigDecimal.ROUND_HALF_UP);
		totalMoney = totalRentPrice.add(servicePrice).add(renewDepositPriceDiff);

		paymentTermsDto.setConRentYear(dayDiff);
		paymentTermsDto.setRentPrice(dayRentPrice);
		paymentTermsDto.setServicePrice(servicePrice);//减免后金额
		paymentTermsDto.setDepositPrice(depositPrice);
		paymentTermsDto.setRenewDepositPriceDiff(renewDepositPriceDiff);
		paymentTermsDto.setTotalMoney(totalMoney);
		paymentTermsDto.setOriginServicePrice(originServicePrice);// 原始金额

		paymentTermsDto.setActList(list);

		return paymentTermsDto;
	}

	@Override
	public String calculateActualRoomPrice(Integer conRentYear, Double roomSalesPrice) {
		BigDecimal dayRentPrice; // 日房租
		BigDecimal actualRentPrice; // 实际房租价格
		actualRentPrice = new BigDecimal(roomSalesPrice * rentalRate.rentPriceStrategyEntity.getShortPriceRate()).setScale(0, RoundingMode.HALF_UP);
		dayRentPrice = actualRentPrice.multiply(new BigDecimal(12)).divide(new BigDecimal(365), 0, BigDecimal.ROUND_HALF_UP);
		return String.format("%s%s/日", BillMsgConstant.RMB, dayRentPrice);
	}

}
