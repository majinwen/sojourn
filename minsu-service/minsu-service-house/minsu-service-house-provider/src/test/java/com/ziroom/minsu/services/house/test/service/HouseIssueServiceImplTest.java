/**
 * @FileName: HouseManageServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author bushujie
 * @created 2016年4月3日 下午1:10:09
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseDefaultPicInfoVo;
import com.ziroom.minsu.services.house.entity.HouseMonthRevenueVo;
import com.ziroom.minsu.services.house.entity.HouseMsgVo;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.service.HouseDataHandlImpl;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.service.TenantHouseServiceImpl;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>房东端房源管理实现测试类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liujun
 * @version 1.0
 * @since 1.0
 */
public class HouseIssueServiceImplTest extends BaseTest {

	@Resource(name = "house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;

	@Resource(name = "house.tenantHouseServiceImpl")
	private TenantHouseServiceImpl tenantHouseServiceImpl;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Resource(name = "house.houseDataHandlImpl")
	private HouseDataHandlImpl houseDataHandl;

	@Test
	public void getHousePicListByPicTypeTest() {
		List<HousePicMsgEntity> picList = houseIssueServiceImpl.getHousePicList("8a9084df5847373d0158485f8b7d032d", null, 1);
		System.err.println(picList.size());
	}

	@Test
	public void calculateRevenueTest() {
		LandlordRevenueDto landlordRevenueDto = new LandlordRevenueDto();
		landlordRevenueDto.setHouseBaseFid("8a9e9aae5419cc22015419cc250a0002");
		landlordRevenueDto.setStatisticsDateYear(2016);
		landlordRevenueDto.setStatisticsDateMonth(4);
		HouseMonthRevenueVo vo = houseManageServiceImpl.calculateRevenue(landlordRevenueDto);
		System.err.println(JsonEntityTransform.Object2Json(vo));
	}

//	@Test
//	public void getHousePicList(){
//		//List<MinsuEleEntity> list=tenantHouseServiceImpl.getHousePicList("8a9084df54ca4c710154ca6e7fff00cf",0);
//		System.err.println(JsonEntityTransform.Object2Json(list));
//	}

	@Test
	public void upHouseBaseDetailVoByFid() {
		HouseBaseDetailVo houseBaseDetailVo = new HouseBaseDetailVo();

		houseBaseDetailVo.setIsIssue(0);
		houseBaseDetailVo.setFid("8a9e98c056a0c7b40156a0c7b4f20000");
		houseBaseDetailVo.setRentWay(0);
		houseBaseDetailVo.setHouseAroundDesc("周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况周边情况");

		houseIssueServiceImpl.upHouseBaseDetailVoByFid(houseBaseDetailVo);

	}


	@Test
	public void mergeHouseBaseAndPhyAndExt() {
		HouseMsgVo houseMsgVo = new HouseMsgVo();
		houseMsgVo.setFid("8a9084df56d5ae4e0156d5d44a080070");
		houseMsgVo.setHouseName("66666666666666666");
		houseMsgVo.setLandlordUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		houseMsgVo.setHouseSn("110100850704Z");
		HousePhyMsgEntity housePhyMsg = new HousePhyMsgEntity();
		housePhyMsg.setNationCode("100000");
		housePhyMsg.setProvinceCode("110000");
		housePhyMsg.setCityCode("110100");
		housePhyMsg.setAreaCode("110101");
		housePhyMsg.setBuildingCode("20160505144625");
		housePhyMsg.setCreateDate(new Date());
		housePhyMsg.setFid("8a9084df56d5ae4e0156d5d44a03006f");
		housePhyMsg.setCommunityName("99999");
		housePhyMsg.setLastModifyDate(new Date());
		housePhyMsg.setCreateUid("d185f535-2b4c-4dc3-8d9a-2eafab152ef4");
		houseMsgVo.setHousePhyMsg(housePhyMsg);
		HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
		houseBaseExt.setCheckInLimit(4);
		houseBaseExt.setHouseBaseFid("8a9084df56d5ae4e0156d5d44a080070");
		houseBaseExt.setFid("8a9084df56d5ae4e0156d5d44a0c0071");
		houseMsgVo.setHouseBaseExt(houseBaseExt);
		houseIssueServiceImpl.mergeHouseBaseAndPhyAndExt(houseMsgVo);

	}
	
	
/*	@Test
	public void updateHouseConfTest(){
		List<HouseConfMsgEntity> list = new ArrayList<HouseConfMsgEntity>();
		int i=0;
		while (i<=60) {
			HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
			i++;
			houseConfMsg.setHouseBaseFid("8a90a2d4549ac7990154a3ee6f0f0245");
			if(i<30){
				houseConfMsg.setDicCode(ProductRulesEnum.ProductRulesEnum002.getValue());
			}else{
				houseConfMsg.setDicCode(ProductRulesEnum.ProductRulesEnum0015.getValue());
			}
			
			houseConfMsg.setDicVal(""+i);
			list.add(houseConfMsg);
		}
		
		try {
			houseIssueServiceImpl.updateHouseConf(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/

	@Test
	public void updateHouseConfTest() {
		//threadPoolExecutor.execute(new Aaa(houseIssueServiceImpl));
		for (int i = 0; i < 2; i++) {
			String fid;
			if (i == 0) {
				//fid = "8a9e9c945663382c015663382c380001";//没问题的顺序
				fid = "8a9e9c945663382c015663382c380000";
			} else {
				fid = "fdjspfjdsfjdspiofjdsiopfjdsiofj1";
				//fid = "8a9e9c945663382c015663382c380000";//没问题的顺序
			}
			threadPoolExecutor.execute(new Aaa(houseIssueServiceImpl, fid));
		}
		/*for (int i=0 ; i<1; i++) {
			String fid;
			if(i==0){
				fid = "8a9e9c945663382c015663382c380001";
				//fid = "8a9e9c945663382c015663382c380000";
			}else{
				//fid = "8a9e9c945663382c015663382c380001";
				fid = "8a9e9c945663382c015663382c380000";
			}
			threadPoolExecutor.execute(new Aaa(houseIssueServiceImpl,null));
		}*/
		try {
			Thread.sleep(999999);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*{picType:0,roomfid:'8a9e9aad568cd0fb01568cd0fb570000',housefid:'8a9e9aad568ca29d01568ca29dbb0001',imgList:[{isdefault:0,picbaseurl:'group1/M00/00/D0/ChAiMFe1X8CASDEbAAArpQcVXio661',picfid:'8a9e9a9e541a404c01541a498df70002',picserveruuid:'',degrees:0},{isdefault:0,picbaseurl:'group1/M00/00/D1/ChAiMFe1fayAL7NiAANUx_PHN1Q357',picfid:'8a9e9893569cc09101569cc091020000',picserveruuid:'',degrees:0},{isdefault:1,picbaseurl:'group1/M00/00/D0/ChAiMFe1cwOAQAxbAAAU0MQ1_Zs305',picfid:'8a9e9893569cc95401569cc954410000',picserveruuid:'',degrees:0}]}
	m_photo.js:754 Object {code: 0, msg: "", data: Object}*/

	@Test
	public void testsaveHousePicByType() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("picType", 0);
		jsonObj.put("roomfid", "8a9e9aad568cd0fb01568cd0fb570000");
		jsonObj.put("housefid", "8a9e9aad568ca29d01568ca29dbb0001");
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("isdefault", 1);
		obj.put("picbaseurl", "group1/M00/00/D0/ChAiMFe1X8CASDEbAAArpQcVXio661");
		obj.put("degrees", 0);
		obj.put("picserveruuid", "");
		obj.put("picfid", "8a9e9a9e541a404c01541a498df70002");
		array.add(obj);
		jsonObj.put("imgList", array);


	}


	private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	class Aaa implements Runnable {

		private HouseIssueServiceImpl bbbbb;

		private String fid;

		public Aaa(HouseIssueServiceImpl houseIssueServiceImpl, String fid) {
			this.bbbbb = houseIssueServiceImpl;
			this.fid = fid;
		}

		@Override
		public void run() {
			List<HouseConfMsgEntity> list = new ArrayList<HouseConfMsgEntity>();
			HouseConfMsgEntity hce = new HouseConfMsgEntity();
			hce.setHouseBaseFid(fid);
			System.out.println("=============================================:" + hce.getHouseBaseFid());
			hce.setDicCode(ProductRulesEnum.ProductRulesEnum002.getValue());
			hce.setDicVal("1");
			list.add(hce);
			
		/*	
			HouseConfMsgEntity hce1 = new HouseConfMsgEntity();
			hce1.setHouseBaseFid("8a9e9c945663382c015663382c380000");
			System.out.println("=============================================:"+hce.getHouseBaseFid());
			hce1.setDicCode(ProductRulesEnum.ProductRulesEnum002.getValue());
			hce1.setDicVal("1");
			list.add(hce1);*/
			bbbbb.updateHouseConf(list);
			/*for (int i=0; i<100; i++) {
				List<HouseConfMsgEntity> list = new ArrayList<HouseConfMsgEntity>();
				HouseConfMsgEntity hce = new HouseConfMsgEntity();
				hce.setHouseBaseFid("8a90a2d4549ac7990154a3ee6f0f0245");
				hce.setDicCode(ProductRulesEnum.ProductRulesEnum002.getValue());
				hce.setDicVal("1");
				list.add(hce);
				bbbbb.updateHouseConf(list);
			}*/

		}

	}

	@Test
	public void findHouseDepositConfByHouseFidTest() {
		DataTransferObject dto = new DataTransferObject();
		HouseConfMsgEntity conf = houseIssueServiceImpl.findHouseDepositConfByHouseFid("8a9e989e5ca9699b015ca9699b200001", dto);
		System.out.println(JsonEntityTransform.Object2Json(conf));
	}

	@Test
	public void findDefaultPicListInfoTest() {
		HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
		houseBaseParamsDto.setHouseBaseFid("8a90a2d455b47fe00155ba279e9401e1");
		houseBaseParamsDto.setRentWay(1);
		List<HouseDefaultPicInfoVo> list = houseIssueServiceImpl.findDefaultPicListInfo(houseBaseParamsDto);
		System.out.print(list);
	}

	@Test
	public void findHouseDepositConfByHouseFidTest1() {
		DataTransferObject dto = new DataTransferObject();
		HouseConfMsgEntity findHouseDepositConfByHouseFid = houseIssueServiceImpl.findHouseDepositConfByHouseFid("8a9e989e5caae095015cab05359a003b", "8a9e989e5caae095015cab077c52004a", 1, dto);
		System.out.print(findHouseDepositConfByHouseFid);
	}

	@Test
	public void testhouseDataHandl() {
		try {
			houseDataHandl.handHouseData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findHallTest() {
		HouseHallVo hallVo = houseIssueServiceImpl.findHall("8a9084df601561ed01602001103d2564");
		if (hallVo != null) {
			System.err.println(hallVo.toString());
		}
	}
}
