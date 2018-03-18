package com.ziroom.zrp.service.trading.proxy.calculation;

import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RoomRentBillDto;
import com.ziroom.zrp.service.trading.pojo.ContractPojo;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
import com.ziroom.zrp.service.trading.valenum.ActivityCategoryEnum;
import com.ziroom.zrp.service.trading.valenum.PaymentCycleEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import com.zra.common.utils.DateTool;
import com.zra.common.utils.DateUtilFormate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>半年付</p>
 * <p> 半年付 服务费折扣 discount （目前是0.85）
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月16日 15:11
 * @since 1.0
 */
public class PayHalfYearlyImpl implements PaymentMethod {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayHalfYearlyImpl.class);

	private static final int HALFYEAR_VALUE = 6;
	private static final String ACTIVITY_SUFFIX = "减免服务费";


	@Override
	public PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) throws Exception{
		BigDecimal servicePrice;
		ContractPojo contractPojo = paymentBaseDataPojo.getContractPojo();

		//计算金额
		LeaseCyclePojo pojo = new LeaseCyclePojo(contractPojo.getConType(), contractPojo.getProjectId(), contractPojo.getRentType());
		PaymentTermsDto paymentTermsDto = LeaseCycleFactory.createLeaseCycle(pojo).calculate(paymentBaseDataPojo);

		servicePrice = paymentTermsDto.getServicePrice().multiply(contractPojo.getDiscount()).setScale(0, BigDecimal.ROUND_HALF_UP);//服务费折扣
		Double activityPrice = paymentTermsDto.getServicePrice().subtract(servicePrice).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		if (activityPrice != 0) {
			//减免的服务费 添加到活动列表
			RentContractActivityEntity rentContractActivityEntity = new RentContractActivityEntity();
			rentContractActivityEntity.setActivityName(PaymentCycleEnum.BNF.getShowName() + ACTIVITY_SUFFIX);
			rentContractActivityEntity.setDiscountAccount(activityPrice);
			rentContractActivityEntity.setCategory(ActivityCategoryEnum.PAY_METHOD.getCode());
			rentContractActivityEntity.setContractId(paymentBaseDataPojo.getContractPojo().getContractId());
			rentContractActivityEntity.setExpenseItemCode(CostCodeEnum.KHFWF.getZraCode());
			paymentTermsDto.getActList().add(rentContractActivityEntity);
		}

		//总金额重新计算 总= 总-原服务费+现服务费
		paymentTermsDto.setTotalMoney(paymentTermsDto.getTotalMoney().subtract(paymentTermsDto.getServicePrice()).add(servicePrice));
		paymentTermsDto.setServicePrice(servicePrice);

		try {
			//计算期数 生成应收账单 租期
			Integer conRentYear = paymentTermsDto.getConRentYear();// 押一付六方式 期数=月数/6
			Integer totalPeriod = conRentYear / HALFYEAR_VALUE;
			Date conStartDate = contractPojo.getConStartDate();
			List<RoomRentBillDto> list = new ArrayList<>();
			String startDate;
			String endDate;
			Date endCycleDate;
			RoomRentBillDto roomRentBillDto;

			for (int i = 1; i <= totalPeriod; i++) {
				roomRentBillDto = new RoomRentBillDto();

				// 计算日期
				endCycleDate = DateTool.getNextXDate(conStartDate, 1, HALFYEAR_VALUE);// 开始日期 +6个月
				endCycleDate = DateTool.getNextXDate(endCycleDate, 0, -1); // 下一个账单周期的开始日期
				startDate = DateUtilFormate.formatDateToString(conStartDate, DateUtilFormate.DATEFORMAT_4);// 周期的开始日期
				endDate = DateUtilFormate.formatDateToString(endCycleDate, DateUtilFormate.DATEFORMAT_4);// 周期的结束日期
				conStartDate = DateTool.getNextXDate(endCycleDate, 0, 1); // 下一个账单周期的开始日期
				roomRentBillDto.setStartDate(startDate);
				roomRentBillDto.setEndDate(endDate);
				roomRentBillDto.setPeriod(i);
				roomRentBillDto.setRentPrice(paymentTermsDto.getRentPrice());
				roomRentBillDto.setRentCount(HALFYEAR_VALUE);
				roomRentBillDto.setPeriodTotalRentPrice(paymentTermsDto.getRentPrice().multiply(new BigDecimal(HALFYEAR_VALUE)));// 每一期的总房租金额
				if (i == 1) { //只有第一期会交服务费和押金
					roomRentBillDto.setServicePrice(paymentTermsDto.getServicePrice());
					roomRentBillDto.setDepositPrice(paymentTermsDto.getDepositPrice());
				} else {
					paymentTermsDto.setDepositPrice(BigDecimal.ZERO);
					paymentTermsDto.setServicePrice(BigDecimal.ZERO);
					paymentTermsDto.setRenewDepositPriceDiff(BigDecimal.ZERO);
				}
				roomRentBillDto.setPeriodTotalMoney(roomRentBillDto.getPeriodTotalRentPrice().add(paymentTermsDto.getRenewDepositPriceDiff()).add(paymentTermsDto.getServicePrice()));

				list.add(roomRentBillDto);
			}

			paymentTermsDto.setRoomRentBillDtos(list);

		} catch (ParseException e) {
			LOGGER.error("【PayMonthlyImpl.calculate()】日期解析失败,e={},param={}", e, contractPojo.getConStartDate());
		}
		return paymentTermsDto;
	}
}
