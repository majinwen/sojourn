/**
 * @FileName: StatusConfDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author yd
 * @created 2016年9月27日 下午8:32:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.base.StatusConfEntity;
import com.ziroom.minsu.services.message.dao.StatusConfDao;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
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
public class StatusConfDaoTest extends BaseTest{
	
	

	@Resource(name = "message.statusConfDao")
	private StatusConfDao statusConfDao;
	
	public final static String IM_OPEN_FLAG = "IM_OPEN_FLAG";
	
	
	@Test
	public void saveTest() {
		
		StatusConfEntity statusConfEntity =  new StatusConfEntity();
		statusConfEntity.setFid(UUIDGenerator.hexUUID());
		statusConfEntity.setCreateUid(UUIDGenerator.hexUUID());
		statusConfEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		statusConfEntity.setStaKey(IM_OPEN_FLAG);
		statusConfEntity.setStaVal("0");
		
		int i = statusConfDao.save(statusConfEntity);
		
		System.out.println(i);
	}
	
	
	@Test
	public void queryStatusConfByKeyTest(){
		
		StatusConfEntity statusConfEntity =  this.statusConfDao.queryStatusConfByKey(IM_OPEN_FLAG);
		
		System.out.println(statusConfEntity);
	}
	
	@Test
	public void updateByConditionTest(){
		
		StatusConfEntity statusConfEntity =  new StatusConfEntity();
		statusConfEntity.setStaKey(IM_OPEN_FLAG);
		statusConfEntity.setStaVal("1");

		int i  = this.statusConfDao.updateByCondition(statusConfEntity);
		
		System.out.println(i);
	}

}
