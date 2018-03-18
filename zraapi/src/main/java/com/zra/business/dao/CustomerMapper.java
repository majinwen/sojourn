package com.zra.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.business.entity.CustomerEntity;

/**
 * @author wangws21 2016年8月4日
 * 商机客户dto
 */
@Repository
public interface CustomerMapper {
	
	/**
	 * 插入商机用户 wangws21 2016-8-4
	 * @param customerDto 商机用户dto
	 * @return num
	 */
	int insert(CustomerEntity customer);
	
	/**
	 * 根据客户电话号码查询客户信息
	 * @author tianxf9
	 * @param phoneStr
	 * @return
	 */
	List<CustomerEntity> queryCustByPhone(String phoneStr);
	
	/**
	 * wangws21 2016-8-5
	 * 根据bid获取客户实体
	 * @param customerBid 客户bid
	 * @return 客户实体
	 */
	CustomerEntity getCustomerByBid(@Param("customerBid") String customerBid);
	
	/**
	 * 更新商机用户 wangws21 2016-8-6
	 * @param customerDto 商机用户dto
	 * @return num
	 */
	int update(CustomerEntity customer);

	CustomerEntity getCuatomerByBuId(@Param("buId") Integer buId);

	/**
	 * wangws21 2017-1-16  取出30天内同项目同手机号的商机数量.
	 * @param projectId 项目id
	 * @param phone 手机号
	 * @param startDate 当前时间前30天
	 * @return int
	 */
	int getCustNum4KYL(@Param("projectId") String projectId, @Param("phone") String phone, @Param("startDate") String startDate);

}


