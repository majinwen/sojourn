
package com.ziroom.minsu.services.message.test.proxy;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.message.SysComplainEntity;
import com.ziroom.minsu.services.message.proxy.SysComplainServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;

/**
 * <p>投诉建议 测试</p>
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
public class SysComplainServiceProxyTest extends BaseTest{


	@Resource(name = "message.sysComplainServiceProxy")
	private SysComplainServiceProxy sysComplainServiceProxy;
	@Test
	public void testSave() {
		SysComplainEntity sysComplainEntity = new SysComplainEntity();

		sysComplainEntity.setComplainMphone("18701428475");
		sysComplainEntity.setComplainUid("uidfdsfdsfdsf");
		sysComplainEntity.setComplainUsername("杨东");
		sysComplainEntity.setContent("hahahhfdsfdsfdsfah");
		sysComplainEntity.setCreateDate(new Date());

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject( this.sysComplainServiceProxy.save(JsonEntityTransform.Object2Json(sysComplainEntity)));

		System.out.println(dto);
	}

	@Test
	public void  testSelectByPrimaryKey(){
		String result = sysComplainServiceProxy.selectByPrimaryKey("99");
		System.out.println("********************:" + result);
	}
}
