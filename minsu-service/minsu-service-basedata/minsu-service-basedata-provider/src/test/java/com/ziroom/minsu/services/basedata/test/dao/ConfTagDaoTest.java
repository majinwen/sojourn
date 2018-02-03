/**
 * @FileName: ConfTagDaoTest.java
 * @Package com.ziroom.minsu.services.basedata.test.dao
 * 
 * @author zl
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.ConfTagEntity;
import com.ziroom.minsu.services.basedata.dao.ConfTagDao;
import com.ziroom.minsu.services.basedata.dto.ConfTagRequest;
import com.ziroom.minsu.services.basedata.dto.ConfTagVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class ConfTagDaoTest extends BaseTest {

	 @Resource(name = "basedata.confTagDao")
	 private ConfTagDao confTagDao;
	 
	 @Test
	 public void findByConfTagRequest(){
		 ConfTagRequest request = new ConfTagRequest();
//		 request.setTagName("海");
//		 request.setIsValid(YesOrNoEnum.YES.getCode());
		 PagingResult<ConfTagVo> list = confTagDao.findByConfTagRequest(request);
		 System.out.println(JsonEntityTransform.Object2Json(list));		 
	 }

	 @Test
	 public void testFindTagsListByConf(){
		 ConfTagRequest request = new ConfTagRequest();
		 request.setTagType(1);
		 List<ConfTagVo> list = confTagDao.findByConfTagRequestList(request);
		 System.err.println(JsonEntityTransform.Object2Json(list));

	 }

	 
	 @Test
	 public void addConfTag(){
		 ConfTagEntity entity = new ConfTagEntity();
		 entity.setCreateDate(new Date());
		 entity.setCreateFid("010");
		 entity.setFid(UUIDGenerator.hexUUID());
		 entity.setIsDel(YesOrNoEnum.NO.getCode());
		 entity.setIsValid(YesOrNoEnum.NO.getCode());
		 entity.setLastModifyDate(new Date());
		 entity.setTagName("透明房");
		 entity.setTagType(1);
		 
		 System.out.println(confTagDao.addConfTag(entity));
	 }
	 
	 @Test
	 public void modifyTagName(){
		 ConfTagEntity entity = new ConfTagEntity();
		 entity.setFid("8a9e9896598734350159873435dc0000");
		 entity.setLastModifyDate(new Date());
		 entity.setTagName("透明房型");
		 System.out.println(confTagDao.modifyTagName(entity));
	 }
	 
	 @Test
	 public void modifyTagStatus(){
		 ConfTagEntity entity = new ConfTagEntity();
		 entity.setFid("8a9e9896598734350159873435dc0000");
		 entity.setLastModifyDate(new Date());
		 entity.setIsValid(YesOrNoEnum.YES.getCode());
		 System.out.println(confTagDao.modifyTagStatus(entity));
	 }
	 
	 

}
