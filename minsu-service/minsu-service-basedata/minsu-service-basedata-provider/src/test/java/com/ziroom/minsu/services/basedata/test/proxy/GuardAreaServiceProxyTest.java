/**
 * @FileName: GuardAreaServiceProxyTest.java
 * @Package com.ziroom.minsu.services.basedata.test.proxy
 * 
 * @author yd
 * @created 2016年7月5日 下午7:45:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.proxy;

import static org.junit.Assert.fail;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.proxy.GuardAreaServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;

/**
 * <p>代理层 测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class GuardAreaServiceProxyTest extends BaseTest{


	@Resource(name = "basedata.guardAreaServiceProxy")
	private GuardAreaServiceProxy guardAreaServiceProxy;


	@Test
	public void findGuardAreaByCodeTest() {

		GuardAreaRequest guardAreaRequest =  new GuardAreaRequest();
		guardAreaRequest.setAreaCode("110108");
		guardAreaRequest.setCityCode("110108");
		guardAreaRequest.setNationCode("100000");
		guardAreaRequest.setProvinceCode("110008");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.guardAreaServiceProxy.findGuardAreaByCode(JsonEntityTransform.Object2Json(guardAreaRequest)));

		if(dto.getCode() == DataTransferObject.SUCCESS){
			GuardAreaEntity guardAreaEntity = dto.parseData("guardArea", new TypeReference<GuardAreaEntity>() {
			});
		}

	}

	@Test
	public void findGuardAreaByFidTest(){
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.guardAreaServiceProxy.findGuardAreaByFid("8a9e9cd955c5947c0155c5c3c1aa0005"));

		if(dto.getCode() == DataTransferObject.SUCCESS){
			GuardAreaEntity guardAreaEntity = dto.parseData("guardArea", new TypeReference<GuardAreaEntity>() {
			});
			System.out.println(guardAreaEntity);
		}
	}

}
