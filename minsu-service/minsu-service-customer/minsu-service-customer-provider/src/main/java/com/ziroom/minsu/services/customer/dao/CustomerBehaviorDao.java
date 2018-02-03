package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.customer.CustomerBehaviorOperationLogEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBehaviorRequest;

import com.ziroom.minsu.services.customer.entity.CustomerBehaviorVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @version 1.0
 * @Date Created in 2017年10月11日
 * @since 1.0
 */
@Repository("customer.customerBehaviorDao")
public class CustomerBehaviorDao {

    private String SQLID = "customer.customerBehaviorDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CustomerBehaviorDao.class);

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 保存用户行为
     * 
     * @author zhangyl2
     * @created 2017年10月13日 15:57
     * @param 
     * @return 
     */
    public int insertCustomerBehavior(CustomerBehaviorEntity customerBehaviorEntity) {
        int index = -1;
        if (customerBehaviorEntity != null) {
            if (customerBehaviorEntity.getFid() == null) customerBehaviorEntity.setFid(UUIDGenerator.hexUUID());

            LogUtil.info(logger, "当前待更新实体对象customerBehaviorEntity={}", customerBehaviorEntity);
            index = this.mybatisDaoContext.save(SQLID + "insertCustomerBehavior", customerBehaviorEntity);
        }
        return index;
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
        return mybatisDaoContext.findAll(SQLID + "queryCustomerBehaviorProveFidsForJob", String.class, customerBehaviorRequest);
    }
    
	/**
	 * 根据uid查询所有行为日记录
	 * 
	 * @author loushuai
	 * @created 2017年10月13日 上午11:12:53
	 *
	 * @param
	 * @return
	 */
	public PagingResult<CustomerBehaviorVo> getCustomerBehaviorList(CustomerBehaviorRequest customerBehaviorRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(customerBehaviorRequest.getPage());
		pageBounds.setLimit(customerBehaviorRequest.getLimit());
		PagingResult<CustomerBehaviorVo> result = mybatisDaoContext.findForPage(SQLID+"getCustomerBehaviorList", CustomerBehaviorVo.class, customerBehaviorRequest, pageBounds);
		return result;
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
        return mybatisDaoContext.findOne(SQLID + "getOneCustomerBehavior", CustomerBehaviorVo.class, customerBehaviorEntity);
    }

    /**
     *
     * 修改用户行为
     *
     * @author zhangyl2
     * @created 2017年10月12日 18:08
     * @param
     * @return
     */
    public int updateCustomerBehaviorAttr(CustomerBehaviorEntity customerBehaviorEntity){
        return mybatisDaoContext.update(SQLID + "updateCustomerBehaviorAttr", customerBehaviorEntity);
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
    public int insertCustomerBehaviorLog(CustomerBehaviorOperationLogEntity operationLogEntity) {
        int index = -1;
        if (operationLogEntity != null) {
            if (operationLogEntity.getFid() == null) operationLogEntity.setFid(UUIDGenerator.hexUUID());

            LogUtil.info(logger, "当前待更新实体对象operationLogEntity={}", operationLogEntity);
            index = this.mybatisDaoContext.save(SQLID + "insertCustomerBehaviorLog", operationLogEntity);
        }
        return index;
    }

}
