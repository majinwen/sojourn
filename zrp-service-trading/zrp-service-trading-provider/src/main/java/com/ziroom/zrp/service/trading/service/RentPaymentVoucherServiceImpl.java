package com.ziroom.zrp.service.trading.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.RentDetailDao;
import com.ziroom.zrp.service.trading.dao.RentPaymentVoucherDao;
import com.ziroom.zrp.trading.entity.RentPaymentVoucherEntity;
/**
 * <p>收款单Service</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月16日
 * @since 1.0
 */
@Service("trading.rentPaymentVoucherServiceImpl")
public class RentPaymentVoucherServiceImpl {
	
	@Resource(name = "trading.rentPaymentVoucherDao")
	private RentPaymentVoucherDao rentPaymentVoucherDao;
	/**
	 * <p>保存收款单</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int saveRentPaymentVoucher(RentPaymentVoucherEntity rentPaymentVoucherEntity){
		return rentPaymentVoucherDao.saveRentPaymentVoucher(rentPaymentVoucherEntity);
	}

	public int updateRentPaymentVoucher(RentPaymentVoucherEntity rentPaymentVoucherEntity){
		return rentPaymentVoucherDao.updateRentPaymentVoucher(rentPaymentVoucherEntity);
	}

}
