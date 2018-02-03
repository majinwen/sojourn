/**
 * @FileName: PhotographerBaseMsgDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午2:17:46
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.services.house.dao.PhotographerBaseMsgDao;
import com.ziroom.minsu.services.house.photog.dto.PhotogDetailDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogRequestDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerGradeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerSourceEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerStatuEnum;

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
public class PhotographerBaseMsgDaoTest extends BaseTest{

	@Resource(name = "photographer.photographerBaseMsgDao")
	private PhotographerBaseMsgDao photographerBaseMsgDao;

	@Test
	public void savePhotographerBaseMsgTest() throws ParseException {

		PhotographerBaseMsgEntity photographerBaseMsg = new PhotographerBaseMsgEntity();

		photographerBaseMsg.setCityCode("100001");
		photographerBaseMsg.setCityName("北京");
		photographerBaseMsg.setEmail("610039854@qq.com");
		photographerBaseMsg.setMobile("18701482472");
		photographerBaseMsg.setNickName("测试1");
		photographerBaseMsg.setPhotographerGrade(PhotographerGradeEnum.GRADE_A.getCode());
		photographerBaseMsg.setPhotographerSource(PhotographerSourceEnum.SOURCE_TROY_INPUT.getCode());
		photographerBaseMsg.setPhotographerStartTime(DateUtil.parseDate("2016-11-04", "yyyy-MM-dd"));
		photographerBaseMsg.setPhotographerStatu(PhotographerStatuEnum.NORMAL.getCode());
		photographerBaseMsg.setPhotographerUid(UUIDGenerator.hexUUID());
		photographerBaseMsg.setRealName("杨东测试1");
		photographerBaseMsg.setSex(CustomerSexEnum.BOY.getCode());
		int i = this.photographerBaseMsgDao.savePhotographerBaseMsg(photographerBaseMsg);

		System.out.println(i);
	}

	@Test
	public void updatePhotographerBaseMsgByUidTest(){

		PhotographerBaseMsgEntity photographerBaseMsg = new PhotographerBaseMsgEntity();
		photographerBaseMsg.setPhotographerUid("8a9e9899582e187701582e1877860000");
		photographerBaseMsg.setNickName("测试2");

		int i = this.photographerBaseMsgDao.updatePhotographerBaseMsgByUid(photographerBaseMsg);
		System.out.println(i);
	}
	
	@Test
	public void findPhotographerListByPageTest(){
		PhotogRequestDto photogDto = new PhotogRequestDto();
		PagingResult<PhotographerBaseMsgEntity> pagingResult = this.photographerBaseMsgDao.findPhotographerListByPage(photogDto);
		System.err.println(JsonEntityTransform.Object2Json(pagingResult));
	}
	
	@Test
	public void findPhotogDetailByUidTest(){
		String photographerUid = "8a9e999e5841fbc4015841fbc7840001";
		PhotogDetailDto photogExtDto = this.photographerBaseMsgDao.findPhotogDetailByUid(photographerUid);
		System.err.println(JsonEntityTransform.Object2Json(photogExtDto));
	}

}
