package com.ziroom.minsu.services.customer.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerBehaviorEntity;
import com.ziroom.minsu.entity.customer.CustomerBehaviorOperationLogEntity;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.customer.dao.CustomerBehaviorDao;
import com.ziroom.minsu.services.customer.dto.CustomerBehaviorRequest;
import com.ziroom.minsu.services.customer.proxy.CustomerBehaviorServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorRoleEnum;
import com.ziroom.minsu.valenum.customer.LandlordBehaviorEnum;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class CustomerBehaviorServiceProxyTest extends BaseTest {

	@Resource(name = "customer.customerBehaviorServiceProxy")
	private CustomerBehaviorServiceProxy customerBehaviorServiceProxy;

	@Test
	public void testSaveCustomerBehavior() {
        LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.ORDER_SINGLE_EVALUATE;
        CustomerBehaviorEntity customerBehaviorEntity = new CustomerBehaviorEntity();
        customerBehaviorEntity.setProveFid("123456789134567987");
        customerBehaviorEntity.setAttribute(behaviorEnum.getAttribute());
        customerBehaviorEntity.setRole(CustomerBehaviorRoleEnum.LANDLORD.getCode());
        customerBehaviorEntity.setUid("111111111111111000000");
        customerBehaviorEntity.setType(behaviorEnum.getType());
        customerBehaviorEntity.setScore(behaviorEnum.getScore());
        customerBehaviorEntity.setCreateFid("111111111111111000000");
        customerBehaviorEntity.setCreateType(behaviorEnum.getCreateType());

        customerBehaviorEntity.setRemark("test");

        System.out.println(customerBehaviorServiceProxy.saveCustomerBehavior(JsonEntityTransform.Object2Json(customerBehaviorEntity)));
	}

    @Test
    public void testQueryCustomerBehaviorProveFidsForJob() {
        //查询过去一段时间某类型行为记录
        LandlordBehaviorEnum behaviorEnum = LandlordBehaviorEnum.REFUSE_APPLY;
        CustomerBehaviorRequest customerBehaviorRequest = new CustomerBehaviorRequest();
        customerBehaviorRequest.setType(behaviorEnum.getType());
        Date now = new Date();
        customerBehaviorRequest.setStartTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 00:00:00"));
        customerBehaviorRequest.setEndTime(DateUtil.dateFormat(DateSplitUtil.getYesterday(now), "yyyy-MM-dd 23:59:59"));

        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(customerBehaviorServiceProxy.queryCustomerBehaviorProveFidsForJob(customerBehaviorRequest.toJsonStr()));
        System.out.println(resultDto.parseData("proveFidList", new TypeReference<List<String>>() {
        }));
    }

    @Resource(name = "customer.customerBehaviorDao")
    private CustomerBehaviorDao customerBehaviorDao;

    @Test
    public void testgetCustomerBehaviorList() {
        //查询是否存在
    	CustomerBehaviorRequest customerBehaviorRequest = new CustomerBehaviorRequest();
    	customerBehaviorRequest.setUid("8a9e9a9f544b35ff01544b35ff950000");
        customerBehaviorRequest.setLimit(10);
        System.out.println(customerBehaviorDao.getCustomerBehaviorList(customerBehaviorRequest));
        System.out.println(customerBehaviorServiceProxy.getCustomerBehaviorList(JsonEntityTransform.Object2Json(customerBehaviorRequest)));
    }

    @Test
    public void testSaveCustomerBehaviorLog(){
        CustomerBehaviorOperationLogEntity log = new CustomerBehaviorOperationLogEntity();
        log.setBehaviorFid("");
    }

}
