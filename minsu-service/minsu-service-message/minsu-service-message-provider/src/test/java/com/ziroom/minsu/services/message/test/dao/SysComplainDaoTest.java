
package com.ziroom.minsu.services.message.test.dao;

import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.message.dto.LandlordComplainRequest;
import com.ziroom.minsu.services.message.entity.SysComplainVo;
import org.junit.Assert;
import org.junit.Test;

import com.ziroom.minsu.entity.message.SysComplainEntity;
import com.ziroom.minsu.services.message.dao.SysComplainDao;
import com.ziroom.minsu.services.message.test.base.BaseTest;

/**
 * <p>投诉 建议 dao层 去掉</p>
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
public class SysComplainDaoTest extends BaseTest{

	
	@Resource(name = "message.sysComplainDao")
	private SysComplainDao sysComplainDao;
	@Test
	public void testSave() {
		
		SysComplainEntity sysComplainEntity = new SysComplainEntity();
		
		sysComplainEntity.setComplainMphone("18701428475");
		sysComplainEntity.setComplainUid("uid");
		sysComplainEntity.setComplainUsername("杨东");
		sysComplainEntity.setContent("hahahhhah");
		sysComplainEntity.setCreateDate(new Date());
		
		int index = this.sysComplainDao.save(sysComplainEntity);
		
		System.out.println(index);
	}

	@Test
	public void testQueryByCondition(){
		LandlordComplainRequest request = new LandlordComplainRequest();
		request.setComplainUsername("王大锤");
		request.setComplainMphone("15937253545");
		PagingResult<SysComplainVo> result = this.sysComplainDao.queryByCondition(request);
		System.out.println("*********************" + result.getTotal());
	}

	@Test
	public void testSelectOne(){
//		SysComplainEntity sysComplainVo = this.sysComplainDao.selectByPrimaryKey(new Integer(7));
//		System.out.println(sysComplainVo.getContent());
		SysComplainEntity sysComplainVo2 = this.sysComplainDao.selectByPrimaryKey(null);
		Assert.assertNull(sysComplainVo2);
	}
}
