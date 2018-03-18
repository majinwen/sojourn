package com.ziroom.zrp.service.trading.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.entity.CheckSignCusInfoVo;
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
@Repository("trading.rentEpsCustomerDao")
public class RentEpsCustomerDao{
	
	private String SQLID = "trading.rentEpsCustomerDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 * <p>保存企业入住人信息</p>
	 * @author xiangb
	 * @created 2017年9月14日
	 * @param rentEpsCustomerEntity
	 * @return int
	 */
	public int saveRentEpsCustomer(RentEpsCustomerEntity rentEpsCustomerEntity){
		rentEpsCustomerEntity.setIsDeleted(0);
		return mybatisDaoContext.save(SQLID + "saveRentEpsCustomer",rentEpsCustomerEntity);
	}

	/**
	 * 修改企业客户信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public int updateRentEpsCustomer(RentEpsCustomerEntity rentEpsCustomerEntity) {
		return mybatisDaoContext.update(SQLID + "updateRentEpsCustomer", rentEpsCustomerEntity);
	}

	/**
	 * 根据uid查询客户信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	public RentEpsCustomerEntity findOneRentEpsCustomerByUid(String customerUid) {
		return mybatisDaoContext.findOne(SQLID + "findOneRentEpsCustomerByUid", RentEpsCustomerEntity.class, customerUid);
	}

	/**
	 * <p>根据企业组织机构码和租房人查询是否已保存</p>
	 * @author xiangb
	 * @created 2017年9月14日
	 * @param
	 * @return
	 */
	public RentEpsCustomerEntity findRentEpsCustomerByCustomer(RentEpsCustomerEntity rentEpsCustomerEntity){
		return mybatisDaoContext.findOne(SQLID + "findRentEpsCustomer",RentEpsCustomerEntity.class,rentEpsCustomerEntity);
	}

	/**
	 * @description: 根据主键id查询企业用户信息
	 * @author: lusp
	 * @date: 2017/9/25 16:14
	 * @params: id
	 * @return: RentEpsCustomerEntity
	 */
	public RentEpsCustomerEntity findRentEpsCustomerById(String id){
		return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey",RentEpsCustomerEntity.class,id);
	}


}
