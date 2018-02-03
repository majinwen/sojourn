
package com.ziroom.minsu.services.customer.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;

/**
 * <p>用户基本信息扩展dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.customerBaseMsgExtDao")
public class CustomerBaseMsgExtDao {

	
	private String SQLID="customer.customerBaseMsgExtDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * get entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:48:38
	 *
	 * @param uid
	 * @return
	 */
	public CustomerBaseMsgExtEntity selectByUid(String uid){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("uid", uid);
		return this.mybatisDaoContext.findOne(SQLID+"selectByUid", CustomerBaseMsgExtEntity.class, paramsMap);
	}
	
	/**
	 * 
	 * 保存用户扩展信息
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:51:41
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	public int insertSelective(CustomerBaseMsgExtEntity customerBaseMsgExt){
		
		if(!Check.NuNObj(customerBaseMsgExt)&&Check.NuNObj(customerBaseMsgExt.getFid())){
			customerBaseMsgExt.setFid(UUIDGenerator.hexUUID());
		}
		return this.mybatisDaoContext.save(SQLID+"insertSelective", customerBaseMsgExt);
	}
	
	/**
	 * 
	 * update entity by uid
	 *
	 * @author yd
	 * @created 2016年5月26日 下午9:54:55
	 *
	 * @param customerBaseMsgExt
	 * @return
	 */
	public int updateByUidSelective(CustomerBaseMsgExtEntity customerBaseMsgExt){
		if(Check.NuNObj(customerBaseMsgExt)||Check.NuNStr(customerBaseMsgExt.getUid())) return -1;
		return this.mybatisDaoContext.update(SQLID+"updateByUidSelective", customerBaseMsgExt);
	}
	
}
