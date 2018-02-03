/**
 * @FileName: CustomerUpdateHistoryLogDaoTest.java
 * @Package com.ziroom.minsu.services.customer.test.dao
 * 
 * @author loushuai
 * @created 2017年8月4日 下午1:14:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.entity.customer.CustomerUpdateHistoryLogEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.common.utils.ClassReflectUtils;
import com.ziroom.minsu.services.customer.dao.CustomerUpdateHistoryLogDao;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class CustomerUpdateHistoryLogDaoTest extends BaseTest {
	
	    @Resource(name="customer.customerUpdateHistoryLogDao")
	    private CustomerUpdateHistoryLogDao customerUpdateHistoryLogDao;

	    @Test
	    public void testcustomerUpdateHistoryLogDao(){
	    	CustomerUpdateHistoryLogEntity customerUpdateHistoryLog = new CustomerUpdateHistoryLogEntity();
	    	customerUpdateHistoryLog.setUid("8a9e9a9f544b35ff01544b35ff950000");
	    	customerUpdateHistoryLog.setCreaterFid("0000233355");
			customerUpdateHistoryLog.setCreaterType(CreaterTypeEnum.OTHER.getCode());
			customerUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
			customerUpdateHistoryLog.setFieldDesc(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldDesc());
			customerUpdateHistoryLog.setFieldPath(ClassReflectUtils.getFieldNamePath(HouseBaseMsgEntity.class,HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldName()));
			String fieldPathKey = MD5Util.MD5Encode("1111111111"+"11111111111111"+1+customerUpdateHistoryLog.getFieldPath(), "UTF-8");
			customerUpdateHistoryLog.setFieldPathKey(fieldPathKey);
			customerUpdateHistoryLog.setIsText(IsTextEnum.IS_NOT_TEST.getCode());
			customerUpdateHistoryLog.setNewValue("杨东的测试房源55555");
			customerUpdateHistoryLog.setOldValue("大连民族的测试房源666666");
			customerUpdateHistoryLog.setSourceType(HouseSourceEnum.ANDROID.getCode());
	    	customerUpdateHistoryLogDao.insertSelective(customerUpdateHistoryLog);
	       // System.out.println(JsonEntityTransform.Object2Json(rst));
	    }
	    
	    @Test
	    public void testselectByUid(){
	    	CustomerUpdateHistoryLogEntity selectByUid = customerUpdateHistoryLogDao.selectByUid("8a9e9a9f544b35ff01544b35ff950000");
	    	System.out.println(selectByUid);
	    }

}
