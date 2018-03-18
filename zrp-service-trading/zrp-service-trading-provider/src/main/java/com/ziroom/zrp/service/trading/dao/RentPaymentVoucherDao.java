package com.ziroom.zrp.service.trading.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.RentPaymentVoucherEntity;


/**
 * <p>收款单实体DAO</p>
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
@Repository("trading.rentPaymentVoucherDao")
public class RentPaymentVoucherDao {

	private String SQLID = "trading.rentPaymentVoucherDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 * <p>保存收款单</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int saveRentPaymentVoucher(RentPaymentVoucherEntity rentPaymentVoucherEntity){
		return mybatisDaoContext.save(SQLID + "saveRentPaymentVoucher",rentPaymentVoucherEntity);
	}
	/**
	 * <p>更新收款单</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int updateRentPaymentVoucher(RentPaymentVoucherEntity rentPaymentVoucherEntity){
		return mybatisDaoContext.save(SQLID + "updateRentPaymentVoucher",rentPaymentVoucherEntity);
	}
	
}
