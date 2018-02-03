/**
 * @FileName: PhotographerBaseMsgPicDaoTest.java
 * @Package com.ziroom.minsu.services.house.test.dao
 * 
 * @author yd
 * @created 2016年11月4日 下午7:08:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.services.house.dao.PhotographerBaseMsgPicDao;
import com.ziroom.minsu.services.house.photog.dto.PhotogPicDto;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

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
public class PhotographerBaseMsgPicDaoTest extends BaseTest{

	
	@Resource(name = "photographer.photographerBaseMsgPicDao")
	private  PhotographerBaseMsgPicDao photographerBaseMsgPicDao;
	
	@Test
	public void savePhotographerBaseMsgPicTest() {
		
		PhotographerBaseMsgPicEntity photographerBaseMsgPic = new PhotographerBaseMsgPicEntity();
		
		photographerBaseMsgPic.setFid(UUIDGenerator.hexUUID());
		photographerBaseMsgPic.setIsDel(IsDelEnum.NOT_DEL.getCode());
		photographerBaseMsgPic.setPhotographerUid("8a9e9899582e187701582e1877860000");
		photographerBaseMsgPic.setPicBaseUrl("group1/M00/00/19/ChAiMFcfA46ARENQAAAqGzNtjhs514");
		photographerBaseMsgPic.setPicName("tou.jpg");
		photographerBaseMsgPic.setPicServerUuid("b68d956b-fca7-465d-af53-ebb76ac6fe46");
		photographerBaseMsgPic.setPicSuffix(".jpg");
		photographerBaseMsgPic.setPicType(PicTypeEnum.USER_PHOTO.getCode());
		
		int i = this.photographerBaseMsgPicDao.savePhotographerBaseMsgPic(photographerBaseMsgPic);
		
		System.out.println(i);
	}
	
	@Test
	public void findPhotogPicByUidAndTypeTest() {
		PhotogPicDto picDto = new PhotogPicDto();
		picDto.setPhotographerUid("8a9e999e5841fbc4015841fbc7840001");
		picDto.setPicType(PicTypeEnum.USER_PHOTO.getCode());
		PhotographerBaseMsgPicEntity picEntity = this.photographerBaseMsgPicDao.findPhotogPicByUidAndType(picDto );
		System.err.println(JsonEntityTransform.Object2Json(picEntity));
	}
	
	@Test
	public void updatePhotographerBaseMsgPicByFidTest() {
		PhotographerBaseMsgPicEntity photographerBaseMsgPic = new PhotographerBaseMsgPicEntity();
		photographerBaseMsgPic.setFid("8a9e99a85847efea015847efeafb0000");
		photographerBaseMsgPic.setIsDel(YesOrNoEnum.YES.getCode());
		int upNum = this.photographerBaseMsgPicDao.updatePhotographerBaseMsgPicByFid(photographerBaseMsgPic);
		System.err.println(JsonEntityTransform.Object2Json(upNum));
	}

}
