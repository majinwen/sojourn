package com.ziroom.minsu.services.customer.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerCollectionEntity;
import com.ziroom.minsu.services.customer.dao.CustomerCollectionDao;
import com.ziroom.minsu.services.customer.dto.CollectionConditionDto;
import com.ziroom.minsu.services.customer.dto.CustomerCollectionDto;
import com.ziroom.minsu.services.customer.entity.CustomerCollectionVo;

/**
 * 
 * <p>客户房源收藏实现类</p>
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
@Service("customer.customerCollectionServiceImpl")
public class CustomerCollectionServiceImpl {
	
	@Resource(name="customer.customerCollectionDao")
	private CustomerCollectionDao customerCollectionDao;
	
	
	/**
     * 
     * 新增客户房源收藏信息
     *
     * @author liujun
     * @created 2016年7月28日
     *
     * @param customerCollection
	 * @return 
     */
	public String insertCustomerCollection(CustomerCollectionEntity customerCollection) {
		String fid = UUIDGenerator.hexUUID();
		customerCollection.setFid(fid);
		customerCollection.setCreateDate(new Date());
		customerCollectionDao.insertCustomerCollection(customerCollection);
		return fid;
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
		return customerCollectionDao.findCustomerCollectionEntityByFid(fid);
	}
	
	/**
	 * 
	 * 条件查询客户房源收藏信息
	 *
	 * @author liujun
	 * @created 2016年8月2日
	 *
	 * @param conditionDto
	 * @return
	 */
	public CustomerCollectionEntity findCustomerCollectionEntityByCondition(CollectionConditionDto conditionDto) {
		return customerCollectionDao.findCustomerCollectionEntityByCondition(conditionDto);
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
		return customerCollectionDao.findCustomerCollectionVoListByUid(customerCollDto);
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
		return customerCollectionDao.updateCustomerCollectionByFid(customerCollection);
	}

	/**
	 * 根据用户uid查询收藏房源条数
	 *
	 * @author liujun
	 * @created 2016年8月5日
	 *
	 * @param uid
	 * @return
	 */
	public long countByUid(String uid) {
		return customerCollectionDao.countByUid(uid);
	}
}
