/**
 * @FileName: InviteCreateOrderCmsProxyTest.java
 * @Package com.ziroom.minsu.services.cms.test.proxy
 * 
 * @author loushuai
 * @created 2017年12月2日 下午3:39:06
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.cms.dto.InviteCmsRequest;
import com.ziroom.minsu.services.cms.proxy.InviteCreateOrderCmsProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.cms.InviteSourceEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
public class InviteCreateOrderCmsProxyTest extends BaseTest{

	@Resource(name="cms.inviteCreateOrderCmsProxy")
	private InviteCreateOrderCmsProxy inviteCreateOrderCmsProxy;
	
	@Test
    public void getInviterDetailTest(){
		InviteCmsRequest inviterDetailRequest =  new InviteCmsRequest();
		inviterDetailRequest.setUid("loushuai");
    	inviterDetailRequest.setInviteSource(InviteSourceEnum.NEW_INVITE.getCode());
		String inviterDetail = inviteCreateOrderCmsProxy.getInviterDetail(JsonEntityTransform.Object2Json(inviterDetailRequest));
		System.out.println(inviterDetail);
    }
	
	@Test
    public void getInviteCodeTest(){
		InviteCmsRequest inviterDetailRequest =  new InviteCmsRequest();
		inviterDetailRequest.setUid("lfdfdsfdfdf");
		//inviterDetailRequest.setPhone("18201693996");
		inviterDetailRequest.setPhone(null);
		String inviterDetail = inviteCreateOrderCmsProxy.getOrInitInviteCode(JsonEntityTransform.Object2Json(inviterDetailRequest));
		System.out.println(inviterDetail);
    }
	
	@Test
    public void getCouponListTest(){
		InviteCmsRequest inviterDetailRequest =  new InviteCmsRequest();
		inviterDetailRequest.setUid("loushuai");
    	inviterDetailRequest.setGroupSn("BEIYAOQINGREN");
		String inviterDetail = inviteCreateOrderCmsProxy.getCouponList(JsonEntityTransform.Object2Json(inviterDetailRequest));
		System.out.println(inviterDetail);
    }
    
    @Test
	public void pointsExchangeTest(){
		InviteCmsRequest inviterDetailRequest =  new InviteCmsRequest();
		inviterDetailRequest.setUid("loushuai");
		inviterDetailRequest.setGroupSn("BEIYAOQINGREN");
		inviterDetailRequest.setActSn("YQR01");
		String inviterDetail = inviteCreateOrderCmsProxy.pointsExchange(JsonEntityTransform.Object2Json(inviterDetailRequest));
		System.out.println(inviterDetail);
	}

	@Test
	public void getPointTiersByUidSourceTest(){
		Map map = new HashMap();
		map.put("uid", "yanb");
		map.put("pointsSource",1);
		String result = inviteCreateOrderCmsProxy.getPointTiersByUidSource(JsonEntityTransform.Object2Json(map));
		System.err.println(result);
	}

	@Test
	public void addPointsByListTest(){
////		InviteOrderRequest a = new InviteOrderRequest("loushuai4","AABBCC",1);
////		InviteOrderRequest b = new InviteOrderRequest("loushuai2","AABBCC",1);
////		InviteOrderRequest c = new InviteOrderRequest("loushuai3","AABBCC",1);
//		List<InviteOrderRequest> orderList = new ArrayList<>();
//		orderList.add(a);
//		orderList.add(b);
//		orderList.add(c);
//		String s=inviteCreateOrderCmsProxy.addPointsByList(JsonEntityTransform.Object2Json(orderList));
	}
}
