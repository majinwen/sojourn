package com.ziroom.zrp.service.trading.proxy.calculation;

import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RoomRentBillDto;
import com.ziroom.zrp.service.trading.pojo.ContractPojo;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
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
 * <p>月付</p>
 * <p> 房租上涨discount （配置） 目前是1.05倍
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月16日 15:11
 * @version 1.0
 * @since 1.0
 */
public class PayMonthlyImpl implements PaymentMethod {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayMonthlyImpl.class);
	@Override
	public PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) throws Exception{
		ContractPojo contractPojo = paymentBaseDataPojo.getContractPojo();
		contractPojo.setRoomSalesPrice(contractPojo.getDiscount().multiply(new BigDecimal(contractPojo.getRoomSalesPrice())).doubleValue());
		LeaseCyclePojo pojo = new LeaseCyclePojo(contractPojo.getConType(), contractPojo.getProjectId(), contractPojo.getRentType());
		paymentBaseDataPojo.setContractPojo(contractPojo);

		PaymentTermsDto paymentTermsDto = LeaseCycleFactory.createLeaseCycle(pojo).calculate(paymentBaseDataPojo);
		try {
			//计算期数 生成应收账单 租期
			Integer totalPeriod = paymentTermsDto.getConRentYear();// 押一付一方式 期数=月数
			Date conStartDate = contractPojo.getConStartDate();
			List<RoomRentBillDto> list = new ArrayList<>();
			String startDate;
			String endDate;
			Date endCycleDate;
			RoomRentBillDto roomRentBillDto;

			for (int i = 1; i <= totalPeriod; i++) {
				roomRentBillDto = new RoomRentBillDto();

				// 计算日期
				endCycleDate = DateTool.getNextXDate(conStartDate, 1, 1);// 开始日期 +1个月
				endCycleDate = DateTool.getNextXDate(endCycleDate, 0, -1); // 下一个账单周期的开始日期
				startDate = DateUtilFormate.formatDateToString(conStartDate, DateUtilFormate.DATEFORMAT_4);// 周期的开始日期
				endDate = DateUtilFormate.formatDateToString(endCycleDate, DateUtilFormate.DATEFORMAT_4);// 周期的结束日期
				conStartDate = DateTool.getNextXDate(endCycleDate, 0, 1); // 下一个账单周期的开始日期
				roomRentBillDto.setStartDate(startDate);
				roomRentBillDto.setEndDate(endDate);
				roomRentBillDto.setPeriod(i);
				roomRentBillDto.setRentPrice(paymentTermsDto.getRentPrice());
				roomRentBillDto.setRentCount(1);
				roomRentBillDto.setPeriodTotalRentPrice(paymentTermsDto.getRentPrice().multiply(new BigDecimal(1)));// 每一期的总房租金额
				if (i == 1) { //只有第一期会交服务费和押金
					roomRentBillDto.setServicePrice(paymentTermsDto.getServicePrice());
					roomRentBillDto.setDepositPrice(paymentTermsDto.getDepositPrice());
				}else{
					paymentTermsDto.setServicePrice(BigDecimal.ZERO);
					paymentTermsDto.setDepositPrice(BigDecimal.ZERO);
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
