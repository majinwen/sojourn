/**
 * @FileName: SmsTemplateDao.java
 * @Package com.ziroom.minsu.services.basedata.dao
 * 
 * @author yd
 * @created 2016年4月1日 下午4:32:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.dao.SmsTemplateDao;
import com.ziroom.minsu.services.basedata.dto.SmsTemplateRequest;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>测试短信模板相关接口</p>
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
public class SmsTemplateDaoTest  extends BaseTest {


	@Resource(name="basedata.smsTemplateDao")
	private SmsTemplateDao smsTemplateDao;
	@Test
	public void findEntityByConditionTest(){
		SmsTemplateRequest smsTemplateRequest = new SmsTemplateRequest();
		PagingResult<SmsTemplateEntity> pagingResult =   smsTemplateDao.findEntityByCondition(smsTemplateRequest);
		System.out.println(pagingResult);
	}

	@Test
	public void  saveEntityTest(){

		SmsTemplateEntity smsTemplateEntity =  new SmsTemplateEntity();
		smsTemplateEntity.setCreateFid("123487"+(new Date().getTime()));
		smsTemplateEntity.setCreateTime(new Date());
		smsTemplateEntity.setSmsCode("123456");
		smsTemplateEntity.setSmsEnabled(0);
		smsTemplateEntity.setCreateFid("456465");
		smsTemplateEntity.setSmsComment("你好，感谢您！fdsfsdfds");
		smsTemplateEntity.setSmsName("yangdofdsfsdfdsng");

		int index = this.smsTemplateDao.saveEntity(smsTemplateEntity);

		if(index>0){
			System.out.println("保存成功");
		}else{
			System.out.println("保存失败");
		}
	}

	@Test
	public void findEntityByIdTest(){

		SmsTemplateEntity smsTemplateEntity = this.smsTemplateDao.findEntityById(1);
		if(smsTemplateEntity != null){
			System.out.println(smsTemplateEntity.toString());
		}


	}

	
	@Test
	public void findEntityByFidTest(){
		SmsTemplateEntity smsTemplateEntity = this.smsTemplateDao.findEntityByFid("48979871459578747009");
		if(smsTemplateEntity != null){
			System.out.println(smsTemplateEntity.toString());
		}
	}
	@Test
	public void findEntityBySmsCodeTest(){
		SmsTemplateEntity smsTemplateEntity = this.smsTemplateDao.findEntityBySmsCode("123456789789798789");
		if(smsTemplateEntity != null){
			System.out.println(smsTemplateEntity.toString());
		}
	}
	@Test
	public void updateEntityByIdTest(){

		SmsTemplateEntity smsTemplateEntity =  new SmsTemplateEntity();
		smsTemplateEntity.setId(1);
		smsTemplateEntity.setCreateFid("789778978"+(new Date().getTime()));
		smsTemplateEntity.setSmsCode("4564789798");
		smsTemplateEntity.setSmsEnabled(1);
		smsTemplateEntity.setCreateFid("456465");
		smsTemplateEntity.setSmsComment("你好，感谢您！");
		smsTemplateEntity.setSmsName("yangdong");
		smsTemplateEntity.setFid("fdsafdf"+(new Date().getTime()));
		int index = this.smsTemplateDao.updateEntityById(smsTemplateEntity);

		System.out.println(index);
	}
	
	@Test
	public void updateEntityByFidTest(){
		SmsTemplateEntity smsTemplateEntity =  new SmsTemplateEntity();
		smsTemplateEntity.setCreateFid("789778978"+(new Date().getTime()));
		smsTemplateEntity.setSmsCode("4564789798");
		smsTemplateEntity.setSmsEnabled(1);
		smsTemplateEntity.setSmsComment("你好，感谢您！dasdasdfsdafasf");
		smsTemplateEntity.setSmsName("yangdong");
		smsTemplateEntity.setFid("48979871459578747009");
		int index = this.smsTemplateDao.updateEntityByFid(smsTemplateEntity);

		System.out.println(index);
	}
	
	@Test
	public void deleteEntityByIdTest(){
		int index = this.smsTemplateDao.deleteEntityById(1);
		System.out.println(index);
	}
	
	public void deleteEntityByFidTest(){
		int index = this.smsTemplateDao.deleteEntityByFid("8a9e9c8d53d5cddb0153d5e41e39001f");
		System.out.println(index);
	}
}
