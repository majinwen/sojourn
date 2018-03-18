package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.PaymentBillDao;
import com.ziroom.zrp.service.trading.dao.PaymentBillDetailDao;
import com.ziroom.zrp.trading.entity.PaymentBillDetailEntity;
import com.ziroom.zrp.trading.entity.PaymentBillEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>付款单类服务层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年10月17日 19:55
 * @since 1.0
 */
@Service("trading.paymentBillServiceImpl")
public class PaymentBillServiceImpl {

	@Resource(name = "trading.paymentBillDao")
	private PaymentBillDao paymentBillDao;

	@Resource(name = "trading.paymentBillDetailDao")
	private PaymentBillDetailDao paymentBillDetailDao;


	/**
	 * 保存付款单 主表和子表
	 * @param paymentBillEntityList 主表列表
	 * @param paymentBillDetailEntityList 子表列表
	 * @return int
	 * @author cuigh6
	 * @Date 2017年10月19日
	 */
	public int savePaymentBill(List<PaymentBillEntity> paymentBillEntityList, List<PaymentBillDetailEntity> paymentBillDetailEntityList) {
		int affect = this.paymentBillDao.savePaymentBill(paymentBillEntityList);
		int affect1 = this.paymentBillDetailDao.savePaymentBillDetail(paymentBillDetailEntityList);
		return affect + affect1;
	}

	/**
	 * 更新付款单同步状态
	 * @param paymentBillEntityList 主表列表
	 * @return int
	 * @author cuigh6
	 * @Date 2017年10月20日
	 */
	public int updatePaymentSyncStatus(List<PaymentBillEntity> paymentBillEntityList) {
		int affect = 0;
		for (PaymentBillEntity paymentBillEntity : paymentBillEntityList) {
			paymentBillEntity.setSynFinance(0);
			affect += this.paymentBillDao.updatePaymentSyncStatus(paymentBillEntity);
		}
		return affect;
	}
}
