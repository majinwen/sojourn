package com.ziroom.minsu.services.message.test.proxy;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.SysMsgManagerEntity;
import com.ziroom.minsu.services.message.dto.SysMsgManagerRequest;
import com.ziroom.minsu.services.message.proxy.SysMsgManagerProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;

/**
 * @author jixd on 2016年4月18日
 * @version 1.0
 * @since 1.0
 */
public class SysMsgManagerProxyTest extends BaseTest{
	
	@Resource(name = "message.sysMsgManagerProxy")
	private SysMsgManagerProxy  sysMsgManagerProxy;
	@Test
	public void testSaveMsg(){
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setCreateTime(new Date());
		entity.setCreateUid(UUIDGenerator.hexUUID());
		entity.setFid(UUIDGenerator.hexUUID());
		entity.setIsDel(0);
		entity.setMsgContent("您的手机已欠费");
		entity.setMsgTitle("test");
		entity.setMsgTargetType(1);
		entity.setMsgTargetUid(UUIDGenerator.hexUUID());
		entity.setIsRelease(0);
		entity.setLastModifyDate(new Date());
		entity.setModifyUid(UUIDGenerator.hexUUID());
		
		String json = JsonEntityTransform.Object2Json(entity);
		sysMsgManagerProxy.saveSysMsgManager(json);
	}
	
	@Test
	public void testDeleteSysMsg(){
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setFid("8a9e9a965428ff45015428ff45640001");
		String json = JsonEntityTransform.Object2Json(entity);
		sysMsgManagerProxy.releaseMsg(json);
		
	}
	
	@Test
	public void testQuerySysMsg(){
		SysMsgManagerRequest request = new SysMsgManagerRequest();
		request.setFid("8a9e9a965428fffa015428fffa6d0001");
		request.setLimit(2);
		request.setPage(1);
		JsonEntityTransform.Object2Json(request);
		String json = sysMsgManagerProxy.queryMsgPage(JsonEntityTransform.Object2Json(request));
		System.out.println(json);
		/*DataTransferObject obj = JsonEntityTransform.json2DataTransferObject(sysMsgProxy.querySysMsg(requestjson));
		List<SysMsgEntity> parseData = obj.parseData("sysMsgList", new TypeReference<List<SysMsgEntity>>(){});*/
	
	}
	
	@Test
	public void testdeleteMsg(){
		SysMsgManagerEntity entity = new SysMsgManagerEntity();
		entity.setFid("8a9e9a965427cf83015427cf83030001");
		sysMsgManagerProxy.deleteMsg(JsonEntityTransform.Object2Json(entity));
	}
}
