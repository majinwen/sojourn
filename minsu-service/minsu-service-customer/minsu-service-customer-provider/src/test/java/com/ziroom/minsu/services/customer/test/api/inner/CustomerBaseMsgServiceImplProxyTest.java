/**
 * @FileName: CustomerBaseMsgServiceImplProxyTest.java
 * @Package com.ziroom.minsu.services.customer.test.api.inner
 * 
 * @author jixd
 * @created 2016年4月25日 上午11:25:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.test.api.inner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.services.customer.dto.CustomerExtDto;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.CustomerInfoDto;
import com.ziroom.minsu.services.customer.entity.CustomerDetailVo;
import com.ziroom.minsu.services.customer.proxy.CustomerInfoPcServiceProxy;
import com.ziroom.minsu.services.customer.proxy.CustomerMsgManagerServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
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
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class CustomerBaseMsgServiceImplProxyTest  extends BaseTest{

	@Resource(name="customer.customerMsgManagerServiceProxy")
	private CustomerMsgManagerServiceProxy proxy;
	
	@Resource(name="customer.customerInfoPcServiceProxy")
	private CustomerInfoPcServiceProxy InfoPcServiceProxy;
	








    @Test
    public void queryCustomerRoleMsgAll(){

        CustomerExtDto customerExtDto = new CustomerExtDto();
        String resutlt = proxy.queryCustomerRoleMsg(JsonEntityTransform.Object2Json(customerExtDto));
        System.out.println(resutlt);
    }

    @Test
    public void queryCustomerRoleMsg(){
        CustomerExtDto customerExtDto = new CustomerExtDto();
        customerExtDto.setRoleCode("seed");
        String resutlt = proxy.queryCustomerRoleMsg(JsonEntityTransform.Object2Json(customerExtDto));
        System.out.println(resutlt);
    }


    @Test
	public void testGetCustomerDetail(){
		String resutlt = proxy.getCustomerDetail("8a9e9a9f544b35ff01544b35ff950000");
		DataTransferObject obj = JsonEntityTransform.json2DataTransferObject(resutlt);
		CustomerDetailVo parseData = obj.parseData("customerDetail", new TypeReference<CustomerDetailVo>() {});
		System.out.println(resutlt);
	}

	@Test
	public void testQueryList(){
		CustomerBaseMsgDto dto = new CustomerBaseMsgDto();
		dto.setPage(1);
		dto.setLimit(5);
		dto.setAuditStatus(0);
		dto.setIsLandlord(0);
		//String paramJson = JsonEntityTransform.Object2Json(dto);
		String paramJson = "{\"page\":1,\"limit\":10,\"uid\":null,\"realName\":\"\",\"nickName\":\"\",\"customerMobile\":\"\",\"isLandlord\":0,\"auditStatus\":0}";
		String liststr = proxy.queryCustomerBaseMsg(JsonEntityTransform.Object2Json(dto));
		System.out.println(liststr);
		DataTransferObject dtoobj = JsonEntityTransform.json2DataTransferObject(liststr);
		List<CustomerBaseMsgEntity> datalist = dtoobj.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {});
		System.out.println(dtoobj.getData().get("total"));

	}
	@Test
	public void saveCustomerInfoTest() throws ParseException{

		/*CustomerInfoDto customerInfoDto = new CustomerInfoDto();

		CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
		customerBaseMsgEntity.setRealName("王大锤");
		customerBaseMsgEntity.setNickName("锤子");
		customerBaseMsgEntity.setCustomerMobile("15937253545");
		customerBaseMsgEntity.setAuditStatus(0);
		customerBaseMsgEntity.setAreaCode("110");
		customerBaseMsgEntity.setCityCode("1000");
		customerBaseMsgEntity.setClearingCode("1");
		customerBaseMsgEntity.setCustomerSex(1);
		customerBaseMsgEntity.setCustomerEmail("lsls@qq.com");
		customerBaseMsgEntity.setIdType(0);
		customerBaseMsgEntity.setIsContactAuth(0);//是否认证
		customerBaseMsgEntity.setIsLandlord(0);//是否房东
		customerBaseMsgEntity.setIsUploadIcon(1);
		customerBaseMsgEntity.setLastModifyDate(new Date());
		customerBaseMsgEntity.setCreateDate(new Date());
		customerBaseMsgEntity.setNationCode("1122");
		customerBaseMsgEntity.setProvinceCode("4455");
		customerBaseMsgEntity.setRentPayment(1);
		customerBaseMsgEntity.setResideAddr("朝阳区");
		customerBaseMsgEntity.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");

		CustomerBankCardMsgEntity customerBankCardMsgEntity=new CustomerBankCardMsgEntity();
		customerBankCardMsgEntity.setBankcardHolder("王二小");
		customerBankCardMsgEntity.setBankcardNo("6221880469651234");
		customerBankCardMsgEntity.setUid(customerBaseMsgEntity.getUid());
		customerBankCardMsgEntity.setFid(UUIDGenerator.hexUUID());
		customerBankCardMsgEntity.setBankName("北京银行");
		customerBankCardMsgEntity.setBranchName("劲松分行");

		CustomerPicMsgEntity customerPicMsgEntity=new CustomerPicMsgEntity();
		customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
		customerPicMsgEntity.setUid(customerBaseMsgEntity.getUid());
		customerPicMsgEntity.setPicBaseUrl("dddd");
		customerPicMsgEntity.setPicName("dddd");
		customerPicMsgEntity.setPicServerUuid(UUIDGenerator.hexUUID());
		customerPicMsgEntity.setPicSuffix(".jpg");
		customerPicMsgEntity.setPicType(3);

		CustomerPicMsgEntity customerPicMsg=new CustomerPicMsgEntity();
		customerPicMsg.setFid(UUIDGenerator.hexUUID());
		customerPicMsg.setUid(customerBaseMsgEntity.getUid());
		customerPicMsg.setPicBaseUrl("eeeee");
		customerPicMsg.setPicName("eeeeeee");
		customerPicMsg.setPicServerUuid(UUIDGenerator.hexUUID());
		customerPicMsg.setPicSuffix(".jpg");
		customerPicMsg.setPicType(2);

		List<CustomerPicMsgEntity> listMsgEntities = new ArrayList<CustomerPicMsgEntity>();
		listMsgEntities.add(customerPicMsgEntity);
		listMsgEntities.add(customerPicMsg);

		customerInfoDto.setCustomerBaseMsg(customerBaseMsgEntity);
		customerInfoDto.setCustomerBankCardMsg(customerBankCardMsgEntity);
		customerInfoDto.setListCustomerPicMsg(listMsgEntities);*/

		CustomerInfoDto customerInfoDto = new CustomerInfoDto();
		CustomerBaseMsgEntity customerBaseMsg=new CustomerBaseMsgEntity();
		customerBaseMsg.setUid("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
		customerBaseMsg.setRealName("fdsfdsf");
		customerBaseMsg.setCustomerMobile("18701482472");
		customerBaseMsg.setCustomerSex(1); //gender  不知道 代表什么 TODO
		customerBaseMsg.setCustomerBirthday(DateUtil.parseDate("1990-07-22", "yyyy-MM-dd"));
		customerBaseMsg.setCustomerEmail("610039854@qq.com");

		customerBaseMsg.setIdType(1); //具体含义  TODO
		customerBaseMsg.setIdNo("4564684f748789789789");
		customerBaseMsg.setIsLandlord(0);
		customerBaseMsg.setAuditStatus(AuditStatusEnum.SUBMITAUDIT.getCode());
		customerBaseMsg.setResideAddr("4564564564");
		customerBaseMsg.setCustomerEdu(1);
		customerBaseMsg.setCustomerJob("456df4sf564ds5f6");
		customerBaseMsg.setIsContactAuth(0);
		customerBaseMsg.setIsIdentityAuth(0);
		customerBaseMsg.setIsUploadIcon(0);
		customerBaseMsg.setCreateDate(new Date());
		customerBaseMsg.setLastModifyDate(new Date());
		customerBaseMsg.setIsDel(IsDelEnum.NOT_DEL.getCode());
		customerBaseMsg.setNickName("153f1ds53f12d3");
		customerInfoDto.setCustomerBaseMsg(customerBaseMsg);
		List<CustomerPicMsgEntity> listCustomerPicMsg = new ArrayList<CustomerPicMsgEntity>();
		//保存用户照片 可能还有其他照片信息  身份证
		CustomerPicMsgEntity customerPicMsg = new CustomerPicMsgEntity();
		customerPicMsg.setFid(UUIDGenerator.hexUUID());
		customerPicMsg.setUid(customerBaseMsg.getUid()); 
		customerPicMsg.setPicType(PicTypeEnum.USER_PHOTO.getCode());
		customerPicMsg.setPicBaseUrl("5f6ds65f6ds5f6ds5f");
		customerPicMsg.setCreateDate(new Date());
		customerPicMsg.setLastModifyDate(new Date());
		customerPicMsg.setIsDel(IsDelEnum.NOT_DEL.getCode());
		listCustomerPicMsg.add(customerPicMsg);

		//TODO  可能还有其他照片信息  身份证
		customerInfoDto.setListCustomerPicMsg(listCustomerPicMsg);

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.proxy.saveCustomerInfo(JsonEntityTransform.Object2Json(customerInfoDto)));

		System.out.println(dto);
	}

	@Test
	public void getCutomerVoTest(){

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.proxy.getCutomerVo("f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6"));
		System.out.println(dto);




	}
	@Test
	public void initCustomerAuthDataTest(){
		String initCustomerAuthData = InfoPcServiceProxy.initCustomerAuthData("52a4aea1-5527-7421-1b25-83fbca1c1856");
		System.out.println(initCustomerAuthData);
	}
}
