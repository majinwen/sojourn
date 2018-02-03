package com.ziroom.minsu.services.customer.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.customer.CustomerBehaviorOperationLogEntity;
import com.ziroom.minsu.services.customer.dao.CustomerBehaviorDao;
import com.ziroom.minsu.services.customer.dto.CustomerBehaviorRequest;

import com.ziroom.minsu.services.customer.entity.CustomerBehaviorVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>用户行为（成长体系）</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月11日
 * @version 1.0
 * @since 1.0
 */
@Service("customer.customerBehaviorServiceImpl")
public class CustomerBehaviorServiceImpl {

    @Resource(name="customer.customerBehaviorDao")
    private CustomerBehaviorDao customerBehaviorDao;

    /**
     * 
     * 保存用户行为
     * 
     * @author zhangyl2
     * @created 2017年10月11日 11:47
     * @param 
     * @return 
     */
    public int saveCustomerBehavior(CustomerBehaviorEntity customerBehaviorEntity){
        return customerBehaviorDao.insertCustomerBehavior(customerBehaviorEntity);
    }

    /**
     *
     * 定时任务补偿
     * 查询过去一段时间某类型行为记录
     *
     * @author zhangyl2
     * @created 2017年10月12日 18:08
     * @param
     * @return
     */
    public List<String> queryCustomerBehaviorProveFidsForJob(CustomerBehaviorRequest customerBehaviorRequest){
        return customerBehaviorDao.queryCustomerBehaviorProveFidsForJob(customerBehaviorRequest);
    }
    
    /**
	 * 根据uid查询所有行为日记录
	 *
	 * @author loushuai
	 * @created 2017年10月13日 上午11:12:16
	 *
	 * @param
	 * @return
	 */
	public PagingResult<CustomerBehaviorVo> getCustomerBehaviorList(CustomerBehaviorRequest customerBehaviorRequest) {
		return customerBehaviorDao.getCustomerBehaviorList(customerBehaviorRequest);
	}

    /**
     *
     * 查询单个行为
     *
     * @author zhangyl2
     * @created 2017年10月12日 18:08
     * @param
     * @return
     */
    public CustomerBehaviorVo getOneCustomerBehavior(CustomerBehaviorEntity customerBehaviorEntity){
        return customerBehaviorDao.getOneCustomerBehavior(customerBehaviorEntity);
    }

    /**
     * 修改用户行为
     *
     * @author zhangyl2
     * @created 2017年10月13日 上午11:12:16
     *
     * @param
     * @return
     */
    public int updateCustomerBehaviorAttr(CustomerBehaviorEntity customerBehaviorEntity) {
        return customerBehaviorDao.updateCustomerBehaviorAttr(customerBehaviorEntity);
    }

    /**
     *
     * 保存操作日志
     *
     * @author zhangyl2
     * @created 2017年10月13日 15:51
     * @param
     * @return
     */
    public int insertCustomerBehaviorLog(CustomerBehaviorOperationLogEntity operationLogEntity){
        return customerBehaviorDao.insertCustomerBehaviorLog(operationLogEntity);
    }

}
