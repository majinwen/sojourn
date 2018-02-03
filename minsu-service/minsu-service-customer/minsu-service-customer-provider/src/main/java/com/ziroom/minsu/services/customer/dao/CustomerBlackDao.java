
package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBlackDto;
import com.ziroom.minsu.services.customer.entity.CustomerBlackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>用户黑名单</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.customerBlackDao")
public class CustomerBlackDao {

	
	private String SQLID="customer.customerBlackDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 查询黑名单根据uid
	 *
	 * @author jixd
	 * @created 2016年10月27日 下午9:48:38
	 *
	 * @param uid
	 * @return
	 */
	public CustomerBlackEntity selectByUid(String uid){
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("uid", uid);
		return this.mybatisDaoContext.findOne(SQLID+"selectByUid", CustomerBlackEntity.class, paramsMap);
	}


	/**
	 * 获取当前的黑名单信息
	 * @author afi
	 * @param fid
	 * @return
	 */
	public CustomerBlackVo getCustomerBlackVo(String fid){
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("fid", fid);
		return this.mybatisDaoContext.findOne(SQLID+"getCustomerBlackVo", CustomerBlackVo.class, paramsMap);

	}

	/**
	 * 
	 * 增加黑名单
	 *
	 * @author jixd
	 * @created 2016年10月27日 下午9:51:41
	 *
	 * @param customerBlackEntity
	 * @return
	 */
	public int insertSelective(CustomerBlackEntity customerBlackEntity){
		
		if(!Check.NuNObj(customerBlackEntity)&&Check.NuNObj(customerBlackEntity.getFid())){
			customerBlackEntity.setFid(UUIDGenerator.hexUUID());
		}
		return this.mybatisDaoContext.save(SQLID+"insertSelective", customerBlackEntity);
	}

	/**
	 * 更新黑名单，根据fid
	 * @author jixd
	 * @created 2016年10月27日 下午9:51:41
	 * @param customerBlackEntity
	 * @return
	 */
	public int updateByFid(CustomerBlackEntity customerBlackEntity){
		return this.mybatisDaoContext.update(SQLID+"updateByFid",customerBlackEntity);
	}


	/**
	 *
	 * 查询黑名单分页
	 *
	 * @author jixd
	 * @created 2016年10月27日 下午9:56:50
	 *
	 * @return list
	 */
	public PagingResult<CustomerBlackVo> queryCustomerBlackList(CustomerBlackDto customerBlackDto){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(customerBlackDto.getLimit());
		pageBounds.setPage(customerBlackDto.getPage());
		return mybatisDaoContext.findForPage(SQLID + "queryCustomerBlackList", CustomerBlackVo.class, customerBlackDto, pageBounds);
	}

	/**
	 * 根据imei查询黑名单
	 * @author wangwt
	 * @created 2017年08月02日 15:24:24
	 * @param
	 * @return
	 */
    public CustomerBlackEntity selectByImei(String imei) {
		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("imei", imei);
		return this.mybatisDaoContext.findOne(SQLID+"selectByImei", CustomerBlackEntity.class, paramsMap);
    }
}
