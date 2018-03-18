package com.ziroom.zrp.service.trading.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.RentVoucherItemEntity;
/**
 * <p>收款单单身DAO</p>
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
@Repository("trading.rentVoucherItemDao")
public class RentVoucherItemDao {
	private String SQLID = "trading.rentVoucherItemDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 * <p>保存收款单单身</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int saveRentVoucherItem(RentVoucherItemEntity rentVoucherItemEntity){
		return mybatisDaoContext.save(SQLID + "saveRentVoucherItemEntity",rentVoucherItemEntity);
	}
	/**
	 * <p>更新收款单单身</p>
	 * @author xiangb
	 * @created 2017年10月16日
	 * @param
	 * @return
	 */
	public int updateRentVoucherItemEntity(RentVoucherItemEntity rentVoucherItemEntity){
		return mybatisDaoContext.save(SQLID + "updateRentVoucherItemEntity",rentVoucherItemEntity);
	}

}
