package com.ziroom.minsu.services.customer.dao;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;
import com.ziroom.minsu.services.customer.dto.CollectionConditionDto;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.customer.entity.CustomerCollectionVo;

/**
 * 
 * <p>客户房源收藏dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.customerCollectionDao")
public class CustomerCollectionDao {
	
    private String SQLID="customer.customerCollectionDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 新增客户房源收藏信息
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param customerCollection
     */
	public void insertCustomerCollection(CustomerCollectionEntity customerCollection) {
		if(Check.NuNObj(customerCollection)
				||Check.NuNStrStrict(customerCollection.getUid())
				||Check.NuNStrStrict(customerCollection.getLandlordUid())
				||Check.NuNStrStrict(customerCollection.getHouseFid())
				||Check.NuNObj(customerCollection.getRentWay())){
			throw new BusinessException("收藏参数错误");
		}
		
		String fid = MD5Util.MD5Encode(customerCollection.getUid()+customerCollection.getLandlordUid()+customerCollection.getHouseFid()+customerCollection.getRentWay(), "UTF-8");
		customerCollection.setFid(fid);
		mybatisDaoContext.save(SQLID + "insertCustomerCollection", customerCollection);
	}
    
    /**
     * 
     * 根据逻辑fid查询客户房源收藏信息
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param fid
     * @return
     */
	public CustomerCollectionEntity findCustomerCollectionEntityByFid(String fid) {
		return mybatisDaoContext.findOneSlave(SQLID + "findCustomerCollectionEntityByFid", 
				CustomerCollectionEntity.class, fid);
	}
	
	/**
	 * 条件查询客户房源收藏信息
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param conditionDto
	 * @return
	 */
	public CustomerCollectionEntity findCustomerCollectionEntityByCondition(CollectionConditionDto conditionDto) {
		return mybatisDaoContext.findOneSlave(SQLID + "findCustomerCollectionEntityByCondition", 
				CustomerCollectionEntity.class, conditionDto);
	}

    /**
     * 
     * 根据逻辑客户uid查询客户房源收藏信息集合
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param customerCollDto
     * @return
     */
	public PagingResult<CustomerCollectionVo> findCustomerCollectionVoListByUid(CustomerCollectionDto customerCollDto) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(customerCollDto.getPage());
		pageBounds.setLimit(customerCollDto.getLimit());
		return mybatisDaoContext.findForPage(SQLID + "findCustomerCollectionVoListByUid", 
				CustomerCollectionVo.class, customerCollDto, pageBounds);
	}
    
    /**
     * 
     * 根据逻辑id更新客户房源收藏信息
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param customerCollection
     * @return
     */
	public int updateCustomerCollectionByFid(CustomerCollectionEntity customerCollection) {
		return mybatisDaoContext.update(SQLID + "updateCustomerCollectionByFid", customerCollection);
	}
	
	/**
	 * 
	 * 根据逻辑id更新客户房源收藏信息
	 *
	 * @author liujun
	 * @created 2016年7月28日
	 *
	 * @param customerCollection
	 * @return
	 */
	public long countByUid(String uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		return mybatisDaoContext.countBySlave(SQLID + "countByUid", map);
	}
}
