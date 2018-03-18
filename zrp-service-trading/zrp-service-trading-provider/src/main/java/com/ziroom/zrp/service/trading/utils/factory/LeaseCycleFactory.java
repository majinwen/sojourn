package com.ziroom.zrp.service.trading.utils.factory;

import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.service.trading.dto.PaymentTermsDto;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.pojo.PaymentBaseDataPojo;
import com.ziroom.zrp.service.trading.proxy.calculation.LeaseCycle;
import com.ziroom.zrp.service.trading.proxy.calculation.DayLeaseCycleImpl;
import com.ziroom.zrp.service.trading.proxy.calculation.MonthLeaseCycleImpl;
import com.ziroom.zrp.service.trading.proxy.calculation.YearLeaseCycleImpl;
import com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * <p>租赁周期工厂类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年09月12日 16:31
 * @since 1.0
 */
public class LeaseCycleFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(LeaseCycleFactory.class);

	public static LeaseCycle createLeaseCycle(LeaseCyclePojo leaseCyclePojo) throws Exception {
		LeaseCycle leaseCycle;
		if (isDayLease(leaseCyclePojo)) {
			// 2017/9/12 创建日租实现
			leaseCycle = new DayLeaseCycleImpl(leaseCyclePojo);

		} else if (isMonthLease(leaseCyclePojo)){
			// 2017/9/12 创建月租实现
			leaseCycle = new MonthLeaseCycleImpl(leaseCyclePojo);

		} else if (isYearLease(leaseCyclePojo)) {
			// 2017/9/12 创建年租实现
			leaseCycle = new YearLeaseCycleImpl(leaseCyclePojo);

		}else{
			LogUtil.error(LOGGER, "【租赁周期不存在】：{}", leaseCyclePojo.getConType());
			leaseCycle =new LeaseCycle() {
				@Override
				public PaymentTermsDto calculate(PaymentBaseDataPojo paymentBaseDataPojo) {
					return null;
				}

				@Override
				public String calculateActualRoomPrice(Integer conRentYear, Double roomSalesPrice) {
					return null;
				}
			};
		}
		return leaseCycle;
	}

	private static boolean isYearLease(LeaseCyclePojo leaseCyclePojo) {
		return Objects.equals(leaseCyclePojo.getConType(), LeaseCycleEnum.YEAR.getCode());
	}

	private static boolean isMonthLease(LeaseCyclePojo leaseCyclePojo) {
		return Objects.equals(leaseCyclePojo.getConType(), LeaseCycleEnum.MONTH.getCode());
	}

	private static boolean isDayLease(LeaseCyclePojo leaseCyclePojo) {
		return Objects.equals(leaseCyclePojo.getConType(), LeaseCycleEnum.DAY.getCode());
	}

}
