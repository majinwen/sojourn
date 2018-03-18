package com.ziroom.zrp.service.trading.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.zrp.service.trading.dao.RentEpsCustomerDao;
import com.ziroom.zrp.trading.entity.RentEpsCustomerEntity;
/**
 * <p>企业签约人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
@Service("trading.rentEpsCustomerServiceImpl")
public class RentEpsCustomerServiceImpl {
	
	@Resource(name="trading.rentEpsCustomerDao")
	private RentEpsCustomerDao rentEpsCustomerDao;
	
	/**
	 * <p>保存企业入住人信息</p>
	 * @author xiangb
	 * @created 2017年9月14日
	 * @param RentEpsCustomerEntity
	 * @return int
	 */
	public int saveRentEpsCustomer(RentEpsCustomerEntity rentEpsCustomerEntity){
		return rentEpsCustomerDao.saveRentEpsCustomer(rentEpsCustomerEntity);
	}
	
	/**
	 * <p>根据企业组织机构码和租房人查询是否已保存</p>
	 * @author xiangb
	 * @created 2017年9月14日
	 * @param
	 * @return
	 */
	public RentEpsCustomerEntity findRentEpsCustomerByCustomer(RentEpsCustomerEntity rentEpsCustomerEntity){
		return rentEpsCustomerDao.findRentEpsCustomerByCustomer(rentEpsCustomerEntity);
	}
	/**
	 * @description: 根据主键id查询企业用户信息
	 * @author: lusp
	 * @date: 2017/9/25 16:15
	 * @params: id
	 * @return: RentEpsCustomerEntity
	 */
	public RentEpsCustomerEntity findRentEpsCustomerById(String id){
		return rentEpsCustomerDao.findRentEpsCustomerById(id);
	}
	/**
	 * 根据uid查询客户信息
	 * @author xiangbin
	 * @created 2017年10月30日
	 */
	public RentEpsCustomerEntity findRentEpsCustomerByCustomerUid(String customerUid){
		return rentEpsCustomerDao.findOneRentEpsCustomerByUid(customerUid);
	}
	
}
