package com.ziroom.minsu.services.house.test.proxy;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgExtEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.services.house.photog.dto.PhotogDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogPicDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogRequestDto;
import com.ziroom.minsu.services.house.proxy.PhotogMgtServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerSexEnum;
import com.ziroom.minsu.valenum.customer.PicTypeEnum;
import com.ziroom.minsu.valenum.photographer.JobTypeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerGradeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerIdTypeEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerSourceEnum;
import com.ziroom.minsu.valenum.photographer.PhotographerStatuEnum;

/**
 * <p>摄影师管理代理测试</p>
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
public class PhotogMgtServiceProxyTest extends BaseTest {
	
	@Resource(name = "photog.photogMgtServiceProxy")
	private PhotogMgtServiceProxy photogMgtServiceProxy;
	
	@Test
	public void findPhotographerListByPageTest() {
		PhotogRequestDto requestDto = new PhotogRequestDto();
		requestDto.setCityCode("110100");
		String resultJson = photogMgtServiceProxy.findPhotographerListByPage(JsonEntityTransform.Object2Json(requestDto));
		System.err.println(resultJson);
	}
	
	@Test
	public void findPhotographerMsgByUidTest() {
		String photographerUid = "8a9e999e5841fbc4015841fbc7840001";
		String resultJson = photogMgtServiceProxy.findPhotographerMsgByUid(photographerUid);
		System.err.println(resultJson);
	}
	
	@Test
	public void insertPhotographerMsgTest() throws ParseException {
		PhotographerBaseMsgEntity base = new PhotographerBaseMsgEntity();
		base.setCityCode("110000");
		base.setCityName("北京市");
		base.setCreateDate(new Date());
		base.setEmail("778924232@qq.com");
		base.setMobile("18410074987");
		base.setNickName("御龙豕韦");
		base.setPhotographerGrade(PhotographerGradeEnum.GRADE_A.getCode());
		base.setPhotographerSource(PhotographerSourceEnum.SOURCE_TROY_INPUT.getCode());
		base.setPhotographerStartTime(DateUtil.parseDate("2000-01-01", "yyyy-MM-dd"));
		base.setPhotographerStatu(PhotographerStatuEnum.NORMAL.getCode());
		base.setPhotographerUid(UUIDGenerator.hexUUID());
		
		PhotographerBaseMsgExtEntity ext = new PhotographerBaseMsgExtEntity();
		ext.setCreateDate(new Date());
		
		PhotogDto dto = new PhotogDto();
		dto.setBase(base);
		dto.setExt(ext);
		String resultJson = photogMgtServiceProxy.insertPhotographerMsg(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}
	
	@Test
	public void updatePhotographerMsgTest() {
		PhotographerBaseMsgEntity base = new PhotographerBaseMsgEntity();
		base.setCityCode("110000");
		base.setCityName("北京市");
		base.setCreateDate(new Date());
		base.setEmail("778924232@qq.com");
		base.setMobile("18410074987");
		base.setNickName("御龙豕韦");
		base.setPhotographerGrade(PhotographerGradeEnum.GRADE_A.getCode());
		base.setPhotographerSource(PhotographerSourceEnum.SOURCE_TROY_INPUT.getCode());
		base.setPhotographerStatu(PhotographerStatuEnum.NORMAL.getCode());
		base.setPhotographerUid("8a9e999e5841fbc4015841fbc7840001");
		base.setRealName("刘军");
		base.setSex(CustomerSexEnum.BOY.getCode());
		
		PhotographerBaseMsgExtEntity ext = new PhotographerBaseMsgExtEntity();
		ext.setIdType(PhotographerIdTypeEnum.ID.getCode());
		ext.setIdNo("429001198801019113");
		ext.setJobType(JobTypeEnum.FULL_TIME.getCode());
		ext.setResideAddr("天通苑北二区");
		ext.setPhotographyIntroduce("我是摄影师");
		ext.setCreateDate(new Date());
		
		PhotogDto dto = new PhotogDto();
		dto.setBase(base);
		dto.setExt(ext);
		String resultJson = photogMgtServiceProxy.updatePhotographerMsg(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}
	
	@Test
	public void findPhotogPicByUidAndTypeTest() {
		PhotogPicDto dto = new PhotogPicDto();
		dto.setPhotographerUid("8a9e999e5841fbc4015841fbc7840001");
		dto.setPicType(PicTypeEnum.USER_PHOTO.getCode());
		String resultJson = photogMgtServiceProxy.findPhotogPicByUidAndType(JsonEntityTransform.Object2Json(dto));
		System.err.println(resultJson);
	}
	
	@Test
	public void insertPhotographerPicMsgTest() {
		PhotographerBaseMsgPicEntity picEntity = new PhotographerBaseMsgPicEntity();
		picEntity.setPhotographerUid("8a9e999e5841fbc4015841fbc7840001");
		picEntity.setPicType(PicTypeEnum.HOLD_PHOTO.getCode());
		picEntity.setPicServerUuid("96992a9b-1e25-401a-8729-4ae95ad57030");
		String resultJson = photogMgtServiceProxy.insertPhotographerPicMsg(JsonEntityTransform.Object2Json(picEntity));
		System.err.println(resultJson);
	}
	
	@Test
	public void updatePhotographerPicMsgTest() {
		PhotographerBaseMsgPicEntity picEntity = new PhotographerBaseMsgPicEntity();
		picEntity.setFid("8a9e99a85847efea015847efeafb0000");
		picEntity.setIsDel(YesOrNoEnum.YES.getCode());
		String resultJson = photogMgtServiceProxy.updatePhotographerPicMsg(JsonEntityTransform.Object2Json(picEntity));
		System.err.println(resultJson);
	}
}
