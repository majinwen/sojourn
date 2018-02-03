/**
 * @FileName: TroyPhotogBookServiceProxyTest.java
 * @Package com.ziroom.minsu.services.house.test.proxy
 * 
 * @author yd
 * @created 2016年11月8日 上午9:20:36
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.test.proxy;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.services.house.photog.dto.PhotoOrderDto;
import com.ziroom.minsu.services.house.proxy.HouseIssueServiceProxy;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import com.asura.framework.base.entity.DataTransferObject;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import com.ziroom.minsu.services.house.dto.HouseRequestDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogOrderUpdateDto;
import com.ziroom.minsu.services.house.proxy.TroyPhotogBookServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.photographer.BookOrderSourceEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.photographer.BusinessTypeEnum;
import com.ziroom.minsu.valenum.photographer.CreaterTypeEnum;
import com.ziroom.minsu.valenum.photographer.UpdateTypeEnum;

/**
 * <p>预约摄影接口测试</p>
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
public class TroyPhotogBookServiceProxyTest extends BaseTest{
	@Resource(name = "photographer.troyPhotogBookServiceProxy")
	private TroyPhotogBookServiceProxy troyPhotogBookServiceProxy;

	@Resource(name ="house.houseIssueServiceProxy")
	private HouseIssueServiceProxy houseIssueServiceProxy;

	/**
	 * Test method for {@link com.ziroom.minsu.services.house.proxy.TroyPhotogBookServiceProxy#findNeedPhotographerHouse(java.lang.String)}.
	 */
	@Test
	public void testFindNeedPhotographerHouse() {
		
		HouseRequestDto houseRequest = new HouseRequestDto();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.troyPhotogBookServiceProxy.findNeedPhotographerHouse(JsonEntityTransform.Object2Json(houseRequest)));
		
		System.out.println(dto);
	}

	
	@Test
	public void testBookHousePhotographer() throws ParseException{
		
		
		BookHousePhotogDto bookHousePhotogDto = new BookHousePhotogDto();
		
		bookHousePhotogDto.setBookEndTime(DateUtil.parseDate("2016-11-20 23:00:00", "yyyy-MM-dd HH:mm:ss"));
		bookHousePhotogDto.setBookerMobile("18701482472");
		bookHousePhotogDto.setBookerUid("00300CB2213DDACBE05010AC69062479");
		bookHousePhotogDto.setBookerName("杨东2222");
		bookHousePhotogDto.setBookOrderRemark("杨东下的订单");
		bookHousePhotogDto.setBookStartTime(DateUtil.parseDate("2016-11-08 23:00:00", "yyyy-MM-dd HH:mm:ss"));
		bookHousePhotogDto.setCustomerMobile("18701482473");
		bookHousePhotogDto.setCustomerName("55555");
		bookHousePhotogDto.setCustomerUid("00300CB2213DDACBE05010AC69062479");
		bookHousePhotogDto.setHouseFid("8a9e9a9454801ac50154801edf380015");
		bookHousePhotogDto.setHouseName("王府井豪华大房");
		bookHousePhotogDto.setHouseSn("110100835738Z");
		bookHousePhotogDto.setShotAddr("北京市东城区将台路5号院 1号楼4单元1304号");
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.troyPhotogBookServiceProxy.bookHousePhotographer(JsonEntityTransform.Object2Json(bookHousePhotogDto)));
		System.out.println(dto);
	}
	
	@Test
	public void updatePhotographerBookOrderBySnTest() throws ParseException {
		
		PhotogOrderUpdateDto photogOrderUpdateDto = new PhotogOrderUpdateDto();
		
		PhotographerBookOrderEntity  photographerBookOrder = new PhotographerBookOrderEntity();
		
		photographerBookOrder.setPhotographerMobile("18701482472");
		photographerBookOrder.setPhotographerName("杨东测试2");
		photographerBookOrder.setPhotographerUid("8a9e9899582e187701582e1877860000");
		photographerBookOrder.setBookOrderSn("PG161108222OUVK2203427");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date reslDate = dateFormat.parse("2017-3-15 9:00:00");
		//存入实际上门时间
		photographerBookOrder.setDoorHomeTime(reslDate);

		photogOrderUpdateDto.setPhotographerBookOrder(photographerBookOrder);
		
		photogOrderUpdateDto.setCreaterFid("7a8c4184-8e2e-37b4-08e8-f4c20225e350");
		photogOrderUpdateDto.setCreaterType(CreaterTypeEnum.ZIROOM_PHOTODEPARTMENT.getCode());
		photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG.getCode());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.troyPhotogBookServiceProxy.updatePhotographerBookOrderBySn(JsonEntityTransform.Object2Json(photogOrderUpdateDto)));
		
		System.out.println(dto);
	}
	@Test
	public void testFindBookOrderDetail(){
//		PG161111CBXYOQI4161344
		PhotoOrderDto photoDto = new PhotoOrderDto();
		photoDto.setHouseFid("8a9084df5847373d0158485f8b7d032d");
		String resultJson = troyPhotogBookServiceProxy.findPhotogOrderDetail(JsonEntityTransform.Object2Json(photoDto));
		System.err.println(resultJson);
	}

	@Test
	public void testFindHouseOrRoomDetail() throws SOAParseException {
		DataTransferObject dto = new DataTransferObject();
		String houseBase = houseIssueServiceProxy.searchHouseBaseMsgByFid("8a9e9a9454801ac501548026fb610029");
		HouseBaseMsgEntity houseMsg = SOAResParseUtil.getValueFromDataByKey(houseBase, "obj", HouseBaseMsgEntity.class);
		if(!Check.NuNObj(houseMsg) && houseMsg.getRentWay()== RentWayEnum.ROOM.getCode()){
			//是分租
			dto.putValue("ok", YesOrNoEnum.YES.getCode());
		}else if(!Check.NuNObj(houseMsg) && houseMsg.getRentWay() == RentWayEnum.HOUSE.getCode()){
			//不是分租
			dto.putValue("ok", YesOrNoEnum.NO.getCode());
		}
		System.err.println(dto.toJsonString());
	}

	@Test
	public void testReceiveOrCancelPhoto() throws ParseException {
		PhotographerBookOrderEntity book = new PhotographerBookOrderEntity();
		book.setBookOrderSn("PG161111CBXYOQI4161344");
		PhotogOrderUpdateDto photogOrderUpdateDto = new PhotogOrderUpdateDto();
		photogOrderUpdateDto.setCreaterFid("8a9e9aaf5456d812015456eba86618c3");
		photogOrderUpdateDto.setCreaterType(CreaterTypeEnum.ZIROOM_PHOTODEPARTMENT.getCode());
		photogOrderUpdateDto.setPhotographerBookOrder(book);
		//收图
		/*Date reslDate = DateUtil.parseDate("2017-3-2 15:55:00", "yyyy-MM-dd HH:mm:ss");
		book.setReceivePictureTime(reslDate);
		photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_RECE_PHOTO.getCode());*/
		photogOrderUpdateDto.setUpdateType(14);
		photogOrderUpdateDto.setRemark("我去了，房东不方便，所以没有拍摄");
		String resultJson = troyPhotogBookServiceProxy.updatePhotographerBookOrderBySn(JsonEntityTransform.Object2Json(photogOrderUpdateDto));
		System.err.println(resultJson);
	}

	@Test
	public void testAssignPhotographer() throws ParseException {
		PhotographerBookOrderEntity  photographerBookOrder = new PhotographerBookOrderEntity();
		Date reslDate = DateUtil.parseDate("2017-03-05 15:45:00", "yyyy-MM-dd HH:mm:ss");
		//存入实际上门时间
		photographerBookOrder.setDoorHomeTime(reslDate);
		//存入摄影师信息
		photographerBookOrder.setPhotographerMobile("13308727855");
		photographerBookOrder.setPhotographerName("张倩");
		photographerBookOrder.setPhotographerUid("8a909977584d5c6a01584dbac101025f");
		photographerBookOrder.setBookOrderSn("PG161111CBXYOQI4161344");
		//调用接口参数准备
		PhotogOrderUpdateDto photogOrderUpdateDto = new PhotogOrderUpdateDto();
		photogOrderUpdateDto.setCreaterFid("8a9e9aaf5456d812015456eba86618c3");
		photogOrderUpdateDto.setCreaterType(CreaterTypeEnum.ZIROOM_PHOTODEPARTMENT.getCode());
		photogOrderUpdateDto.setPhotographerBookOrder(photographerBookOrder);
		photogOrderUpdateDto.setUpdateType(UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG_MODIFY.getCode());
		String resultJson = troyPhotogBookServiceProxy.updatePhotographerBookOrderBySn(JsonEntityTransform.Object2Json(photogOrderUpdateDto));
		System.err.println(resultJson);
	}

	@Test
	public void findLogsTest(){
		PhotoOrderDto photoDto = new PhotoOrderDto();
		photoDto.setBookOrderSn("PG161111CBXYOQI4161344");
		String logs = troyPhotogBookServiceProxy.findLogs(JsonEntityTransform.Object2Json(photoDto));
		System.err.println(logs);
	}

	@Test
	public void testFindBookOrderByHouseFid(){
		PhotoOrderDto photoOrderDto = new PhotoOrderDto();
		photoOrderDto.setHouseFid("8a90a2d45ac6377b015ac6619db3004f");
		String photogOrderDetail = troyPhotogBookServiceProxy.findPhotogOrderDetail(JsonEntityTransform.Object2Json(photoOrderDto));
		System.err.println(photogOrderDetail);
	}
	
	@Test
	public void testfindlastestBookOrderByHouseFid(){
		PhotographerBookOrderEntity photographerBookOrderEntity = new PhotographerBookOrderEntity();
		photographerBookOrderEntity.setHouseFid("8a9084df57080e7001570818a9010024");
		String s = photographerBookOrderEntity.getHouseFid();
		String latestPhotoOrder = troyPhotogBookServiceProxy.findBookOrderByHouseFid(s);
		System.out.println(latestPhotoOrder);
	}
	
}
