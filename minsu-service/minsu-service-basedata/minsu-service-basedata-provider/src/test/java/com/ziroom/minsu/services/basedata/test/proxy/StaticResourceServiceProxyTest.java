package com.ziroom.minsu.services.basedata.test.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.entity.base.StaticResourceEntity;
import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.basedata.dto.StaticResourceRequest;
import com.ziroom.minsu.services.basedata.proxy.StaticResourceServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.valenum.top.StaticResourceTypeEnum;

/**
 * 
 * <p>静态资源proxy</p>
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
public class StaticResourceServiceProxyTest extends BaseTest {

	@Resource(name="basedata.staticResourceServiceProxy")
	private StaticResourceServiceProxy staticResourceServiceProxy;

	@Test
	public void findStaticResourceListByPageTest() {
		StaticResourceRequest request = new StaticResourceRequest();
		request.setResCode("2017-TOP50");
		request.setResTitle("2017年度");
		request.setResType(StaticResourceTypeEnum.TEXT.getCode());
		request.setCreateDateStart("2017-01-01");
		request.setCreateDateEnd("2017-04-01");
		List<String> createFidList = new ArrayList<String>();
		createFidList.add("00300CB2213DDACBE05010AC69062479");
		request.setCreateFidList(createFidList );
		String resultJson = staticResourceServiceProxy.findStaticResourceListByPage(JsonEntityTransform.Object2Json(request));
		System.err.println(resultJson);
	}

	@Test
	public void testgetTipsMsgHasSubTitleByCode(){
		String tipsMsgHasSubTitleByCode = staticResourceServiceProxy.getTipsMsgHasSubTitleByCode("TIP_WHY_AUTH");
		System.err.println(tipsMsgHasSubTitleByCode);

	}


	@Test
	public void testsaveStaticEntity(){
		StaticResourceEntity staticResourceEntity = new StaticResourceEntity();
		staticResourceEntity.setResCode("TIP_HOW_PUBLISH_MULTI_ROOM_1");
		staticResourceEntity.setParentCode("TIP_HOW_PUBLISH_MULTI_ROOM");
		staticResourceEntity.setResType(1);
		staticResourceEntity.setResTitle(null);
		staticResourceEntity.setResContent("出租方式为独立房间时，您准备出租的房间数量，即为您此次需要上架的房间数量。我们可以一次发布多个房间，避免重复填写公共信息，提升发布房源效率。");
		staticResourceEntity.setCreateFid("001");
		String s = staticResourceServiceProxy.saveStaticEntity(JsonEntityTransform.Object2Json(staticResourceEntity));

	}
	
	@Test
	public void findStaticResByResCodeTest(){
		String resJson=staticResourceServiceProxy.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_EXPLAIN");
		System.err.println(resJson);
	}
}
