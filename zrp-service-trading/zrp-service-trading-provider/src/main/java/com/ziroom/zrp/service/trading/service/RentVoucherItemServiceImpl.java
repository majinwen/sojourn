package com.ziroom.zrp.service.trading.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.RentVoucherItemDao;
import com.ziroom.zrp.trading.entity.RentVoucherItemEntity;
/**
 * <p>收款单单身Service</p>
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
@Service("trading.rentVoucherItemServiceImpl")
public class RentVoucherItemServiceImpl {

	@Resource(name = "trading.rentVoucherItemDao")
    private RentVoucherItemDao rentVoucherItemDao;
	/**
	 * <p>保存收款单单身</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int saveRentVoucherItem(RentVoucherItemEntity rentVoucherItemEntity){
		return rentVoucherItemDao.saveRentVoucherItem(rentVoucherItemEntity);
	}
	/**
	 * <p>更新收款单单身</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int updateRentVoucherItemEntity(RentVoucherItemEntity rentVoucherItemEntity){
		return rentVoucherItemDao.updateRentVoucherItemEntity(rentVoucherItemEntity);
	}
	
}
