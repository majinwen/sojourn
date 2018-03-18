package com.zra.business.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.business.entity.CustomerEntity;
import com.zra.business.service.CustomerService;

/**
 * 
 * @author tianxf9
 *
 */
@Component
public class CustomerLogic {
	
	@Autowired
	private CustomerService service;
	
	/**
	 * 保存客户
	 * @author tianxf9
	 * @param entity
	 * @return
	 */
    public int saveCustomerEntity(CustomerEntity entity) {
    	return service.saveCustomer(entity);
    }
    
    /**根据客户电话号码查询客户信息
     * @author tianxf9
     * @param phoneStr
     * @return
     */
    public List<CustomerEntity> getCustByPhone(String phoneStr) {
    	return service.getCustByPhone(phoneStr);
    }
    
    /** 
     * 根据bid获取客户实体
     * @param customerBid bid
     * @return CustomerEntity
     */
    public CustomerEntity getCuatomerByBid(String customerBid) {
        return service.getCuatomerByBid(customerBid);
    }
}
