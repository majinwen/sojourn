/**
 * @FileName: PhotographerBaseMsgExtDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午3:05:27
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgExtEntity;
import com.ziroom.minsu.services.house.dao.PhotographerBaseMsgExtDao;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.photographer.JobTypeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerIdTypeEnum;

/**
 * <p>摄影师扩展信息 测试</p>
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
public class PhotographerBaseMsgExtDaoTest extends BaseTest{

	
	@Resource(name = "photographer.photographerBaseMsgExtDao")
	private PhotographerBaseMsgExtDao photographerBaseMsgExtDao;
	
	
	@Test
	public void savePhotographerBaseMsgExtTest() {
		
		PhotographerBaseMsgExtEntity photographerBaseMsgExt = new PhotographerBaseMsgExtEntity();
		
		photographerBaseMsgExt.setFid(UUIDGenerator.hexUUID());
		photographerBaseMsgExt.setIdType(PhotographerIdTypeEnum.ID.getCode());
		photographerBaseMsgExt.setIdNo("420365897812554112");
		photographerBaseMsgExt.setJobType(JobTypeEnum.FULL_TIME.getCode());
		photographerBaseMsgExt.setPhotographerUid("8a9e9899582e187701582e1877860000");
		photographerBaseMsgExt.setPhotographyIntroduce("哈啊哈哈哈哈哈哈哈哈哈哈哈哈111111111111111111");
		photographerBaseMsgExt.setResideAddr("北京 北京人 周口店人");
		
		this.photographerBaseMsgExtDao.savePhotographerBaseMsgExt(photographerBaseMsgExt);
	}
	
	@Test
	public void updatePhotographerBaseMsgExtByUidTest(){
		
		PhotographerBaseMsgExtEntity photographerBaseMsgExt = new PhotographerBaseMsgExtEntity();
		photographerBaseMsgExt.setPhotographerUid("8a9e9899582e187701582e1877860000");
		photographerBaseMsgExt.setResideAddr("fdsjfhdsofhdsoifh fdfsdfsdfsdfsdf");
		this.photographerBaseMsgExtDao.updatePhotographerBaseMsgExtByUid(photographerBaseMsgExt);
		
	}

}
