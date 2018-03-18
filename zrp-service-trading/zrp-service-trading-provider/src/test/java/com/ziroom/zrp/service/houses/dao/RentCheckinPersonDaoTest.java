package com.ziroom.zrp.service.houses.dao;


import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.google.gson.JsonObject;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dao.RentCheckinPersonDao;
import com.ziroom.zrp.service.trading.dto.PersonAndSharerDto;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonSearchDto;
import com.ziroom.zrp.service.trading.entity.CheckSignCusInfoVo;
import com.ziroom.zrp.service.trading.entity.RentCheckinPersonVo;
import com.ziroom.zrp.service.trading.pojo.PersonAndSharerPojo;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.esp.utils.HttpClientHelper;

import org.junit.Test;

import javax.annotation.Resource;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class RentCheckinPersonDaoTest extends BaseTest{



	@Resource(name = "trading.rentCheckinPersonDao")
	private RentCheckinPersonDao rentCheckinPersonDao;


	/*@Test
	public void testFindCheckSignCusInfoVoByUid(){
		RentContractEntity rentContractEntity = new RentContractEntity();
		rentContractEntity.setContractid("2c908d174e67e5ae014e6cdbf7a3015d");
		CheckSignCusInfoVo checkSignCusInfoVo = rentCheckinPersonDao.findCheckSignCusInfoVoByUid(rentContractEntity);
	}

	@Test
	public void testsignCheckSignature(){
		ItrusUtil itrusUtil = new ItrusUtil();
		ItrusUtilPortType itrusUtilPortType = itrusUtil.getItrusUtilHttpPort();
		String signStr = itrusUtilPortType.signature("{\"param\":\"hello\"}");
		System.err.println(signStr);
	}

	@Test
	public void testSignCheckVerify(){
		ItrusUtil itrusUtil = new ItrusUtil();
		ItrusUtilPortType itrusUtilPortType = itrusUtil.getItrusUtilHttpPort();
		String signStr = itrusUtilPortType.verify("{\"Message\":\"测试\",\"signMessage\":\"密文\"}");
		System.err.println(signStr);
	}*/

	// 创建个人用户
	@Test
	public void createUser() {
		JSONObject reqData = new JSONObject();
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("userType", 0);
//		user.put("fullname", "将府快速搭建");
		user.put("userId", "5656562232232");
		user.put("idCardNum", "59888985656666355");
//		user.put("idCardType", "身份证");
		user.put("mobile", "13255556325");
		reqData.put("autoCert", true);
		reqData.put("user", user);
		reqData.put("timestamp",System.currentTimeMillis());
		reqData.put("apiId","itrus");
//		reqData.put("autoCert", true);

		String jfkd = "省几个几个";
		String aaa = reqData.toJSONString();
		String jsonRespStr = "{isOK:false}";
		try {
			jsonRespStr = HttpClientHelper.jsonCallWithHmacSha1("http://esp.ziroom.com/esp/api/createUser?lang=zh_CN", reqData.toJSONString(),"a12d765e3319adc129c9b822d5c1eb84");
			System.err.println(jsonRespStr);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testfindByContractId(){
		RentCheckinPersonEntity  person = rentCheckinPersonDao.findCheckinPersonByContractId("123");
		System.err.println(JSONObject.toJSON(person));
	}
	@Test
	public void testsaveCheckinPerson(){
		RentCheckinPersonEntity rentCheckinPersonEntity = new RentCheckinPersonEntity();
		rentCheckinPersonEntity.setSex(1);
		rentCheckinPersonEntity.setUid("54564654654");
		rentCheckinPersonEntity.setName("姓名");
		rentCheckinPersonEntity.setCertType(1);
		rentCheckinPersonEntity.setPhoneNum("15213261525");
		rentCheckinPersonEntity.setCertPic1(null);
		rentCheckinPersonEntity.setCertPic2(null);
		rentCheckinPersonEntity.setCertNum(null);
		rentCheckinPersonEntity.setContractId("8a9e98b75f0436ae015f0436aefa0000");
		int isSuccess = rentCheckinPersonDao.saveCheckinPerson(rentCheckinPersonEntity);
		System.err.println("=========="+isSuccess);
	}
	
	@Test
	public void  testCheckinPersonList(){
		RentCheckinPersonSearchDto dto=new RentCheckinPersonSearchDto();
		dto.setPage(1);
		dto.setRows(10);
		//dto.getProjectIdList().add("21");
		//dto.getProjectIdList().add("17");
		//dto.setProjectId("16");
		dto.setIntoState(1);
		//dto.setProjectId("21");
		PagingResult<RentCheckinPersonVo> resut=rentCheckinPersonDao.searchByCriteria(dto);
		System.err.println(JsonEntityTransform.Object2Json(resut.getRows()));
	}


    @Test
    public void testSelectPersonAndSharer(){
        RentCheckinPersonSearchDto dto = new RentCheckinPersonSearchDto();
        dto.setPage(1);
        dto.setRows(10);
        dto.setContractId("8a9099cb5a183e26015a1bc7b2f10095");
        PagingResult<PersonAndSharerPojo> resut=rentCheckinPersonDao.selectHistoryPersonAndSharer(dto);
        System.err.println(JsonEntityTransform.Object2Json(resut.getRows()));
    }
}
