package com.ziroom.zrp.service.trading.proxy.calculation;

import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.dto.RoomRentBillDto;
import com.ziroom.zrp.service.trading.pojo.ContractPojo;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
import com.zra.common.utils.DateUtilFormate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>一次结清</p>
 * <p>
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
public class  PayAllImpl implements PaymentMethod {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayAllImpl.class);

	@Override
	public PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) throws Exception{
		ContractPojo contractPojo = paymentBaseDataPojo.getContractPojo();
		LeaseCyclePojo pojo = new LeaseCyclePojo(contractPojo.getConType(), contractPojo.getProjectId(), contractPojo.getRentType());
		PaymentTermsDto paymentTermsDto = LeaseCycleFactory.createLeaseCycle(pojo).calculate(paymentBaseDataPojo);

		//计算期数 生成应收账单 租期
		Integer conRentYear = paymentTermsDto.getConRentYear();// 一次结清 期数=1
		Integer totalPeriod = 1;
		List<RoomRentBillDto> list = new ArrayList<>();
		Date conStartDate = contractPojo.getConStartDate();
		Date conEndDate = contractPojo.getConEndDate();
		String startDate;
		String endDate;
		RoomRentBillDto roomRentBillDto;


		roomRentBillDto = new RoomRentBillDto();

		// 计算日期
		startDate = DateUtilFormate.formatDateToString(conStartDate, DateUtilFormate.DATEFORMAT_4);// 周期的开始日期
		endDate = DateUtilFormate.formatDateToString(conEndDate, DateUtilFormate.DATEFORMAT_4);// 周期的结束日期
		roomRentBillDto.setStartDate(startDate);
		roomRentBillDto.setEndDate(endDate);
		roomRentBillDto.setPeriod(totalPeriod);
		roomRentBillDto.setRentPrice(paymentTermsDto.getRentPrice());
		roomRentBillDto.setRentCount(conRentYear);
		roomRentBillDto.setPeriodTotalRentPrice(paymentTermsDto.getRentPrice().multiply(new BigDecimal(conRentYear)));// 每一期的总房租金额
		roomRentBillDto.setServicePrice(paymentTermsDto.getServicePrice());
		roomRentBillDto.setDepositPrice(paymentTermsDto.getDepositPrice());

		roomRentBillDto.setPeriodTotalMoney(roomRentBillDto.getPeriodTotalRentPrice().add(paymentTermsDto.getRenewDepositPriceDiff()).add(paymentTermsDto.getServicePrice()));

		list.add(roomRentBillDto);

		paymentTermsDto.setRoomRentBillDtos(list);

		return paymentTermsDto;
	}
}
