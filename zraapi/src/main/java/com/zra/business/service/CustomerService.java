package com.zra.business.service;

import java.util.List;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.business.dao.CustomerMapper;
import com.zra.business.entity.CustomerEntity;

/**
 * @author wangws21 2016年8月1日
 * 商机客户服务
 */
@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(CustomerService.class);
    
    @Autowired
    private CustomerMapper custmapper;
    
    /**
     * 保存客户
     * @author tianxf9
     * @param custEntity
     * @return
     */
    public int saveCustomer(CustomerEntity custEntity) {
    	return this.custmapper.insert(custEntity);
    }
    
    /**
     * 根据电话号码查询出最新客户记录
     * @author tianxf9
     * @param phoneStr
     * @return
     */
    public List<CustomerEntity> getCustByPhone(String phoneStr) {
    	return this.custmapper.queryCustByPhone(phoneStr);
    }

    /** 
     * 根据bid获取客户实体
     * @param customerBid bid
     * @return CustomerEntity
     */
    public CustomerEntity getCuatomerByBid(String customerBid) {
        return this.custmapper.getCustomerByBid(customerBid);
    }

    public CustomerEntity getCuatomerByBuId(Integer buId) {
        return custmapper.getCuatomerByBuId(buId);
    }
}
