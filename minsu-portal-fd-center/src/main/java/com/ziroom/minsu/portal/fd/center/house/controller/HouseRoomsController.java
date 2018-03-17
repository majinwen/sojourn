/**
 * @FileName: HouseRoomsController.java
 * @Package com.ziroom.minsu.portal.fd.center.house.controller
 * 
 * @author jixd
 * @created 2016年8月12日 下午1:08:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.house.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RandomUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.google.gson.JsonObject;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.portal.fd.center.common.utils.ImageHelper;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.portal.fd.center.house.service.HouseUpdateLogService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseIssuePcService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseUpdateHistoryLogService;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HousePicBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseRoomsWithBedsPcDto;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.dto.RoomHasBeds;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.pc.dto.HouseIssueDescDto;
import com.ziroom.minsu.services.house.pc.dto.HousePicDelDto;
import com.ziroom.minsu.services.house.pc.dto.HousePicTypeDto;
import com.ziroom.minsu.services.house.pc.dto.HouseRoomBaseMsg;
import com.ziroom.minsu.services.house.pc.dto.RoomDefaultPicDto;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.domain.FileInfoResponse.InternalFile;
import com.ziroom.tech.storage.client.service.StorageService;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * <p>发布房源 -- 分租发布</p>
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
@Controller
public class HouseRoomsController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(HouseRoomsController.class);
	
	@Resource(name="house.houseIssuePcService")
	private HouseIssuePcService houseIssuePcService;
	
	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;
	
	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
	
	@Value("#{'${pic_size_375_281}'.trim()}")
	private String pic_size_375_281;
	
	@Value("#{'${pic_size}'.trim()}")
	private String pic_size;
	
	@Value("#{'${pic_default_house_url}'.trim()}")
	private String pic_default_house_url;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Resource(name="storageService")
	private StorageService storageService;
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "fd.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;
	
	@Resource(name = "house.houseUpdateHistoryLogService")
	private HouseUpdateHistoryLogService houseUpdateHistoryLogService;
	/**
	 * 
	 * 分租房间信息
	 *
	 * @author jixd
	 * @created 2016年8月12日 下午1:07:19
	 *
	 * @param fid
	 * @return
	 */
	@RequestMapping("/houseIssue/rooms/{fid}")
	public String houseRooms(Model model,@PathVariable String fid,String roomFid){

		String roomListJson = houseIssuePcService.findHouseRoomWithBedsList(fid);
		LogUtil.info(LOGGER, "houseRooms方法,fid={},roomFid={},roomListJson={}",fid,roomFid, roomListJson);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(roomListJson);
		try {
			if(dto.getCode() == DataTransferObject.ERROR){
				return "/error/500";
			}
			HouseRoomsWithBedsPcDto houseRooms = dto.parseData("roomList", new TypeReference<HouseRoomsWithBedsPcDto>() {});

			//当前房间
			List<RoomHasBeds> currentRoom = new ArrayList<>();

			//如果有房间正在审核或者已上架则不能修改户型
			Integer isModify = 0;

			List<RoomHasBeds> roomList = houseRooms.getRoomList();
			int houseRoomIssueCount = 0;
			if (!Check.NuNCollection(roomList)) {
				houseRoomIssueCount = roomList.size();

				if (!Check.NuNStr(roomFid)) {
					for (RoomHasBeds room : roomList) {
						HouseRoomMsgEntity roomMsg = room.getRoomMsg();

						//如果有房间正在审核或者已上架则不能修改户型
						if (isModify != 1 && !isModify(roomMsg.getRoomStatus())) {
							isModify = 1;
						}

						// 是否当前房间
						if (roomFid.equals(roomMsg.getFid())) {

							/**要审核字段替换开始**/
							Map<String, HouseFieldAuditLogVo> houseFieldAuditMap = houseUpdateLogService.houseFieldAuditLogVoConvertMap(fid, roomFid, houseRooms.getHouseBaseMsgEntity().getRentWay(), 0);
							if (houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())) {
								roomMsg.setRoomName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
							}
							if (houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Area.getFieldPath())) {
								roomMsg.setRoomArea(Double.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Area.getFieldPath()).getNewValue()));
							}
							if (houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Check_In_Limit.getFieldPath())) {
								roomMsg.setCheckInLimit(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Check_In_Limit.getFieldPath()).getNewValue()));
							}
							if (houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Price.getFieldPath())) {
								roomMsg.setRoomPrice(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Price.getFieldPath()).getNewValue()));
							}
							if (houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Cleaning_Fees.getFieldPath())) {
								roomMsg.setRoomCleaningFees(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Cleaning_Fees.getFieldPath()).getNewValue()));
							}
							/**要审核字段替换结束**/

							currentRoom.add(room);
						}
					}

					if (Check.NuNCollection(currentRoom)) {
						roomFid = "";
					}
					// 只显示当前房间的信息，其他舍弃 2017-09-20
					houseRooms.setRoomList(currentRoom);
				}
			}

			//2 床类型
			String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());

			//3 床规格
			String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
			List<EnumVo> bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			List<EnumVo> bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);

			//获取清洁费百分比
			String cleaningFeesJson=cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
			String cleaningFees=SOAResParseUtil.getStrFromDataByKey(cleaningFeesJson, "textValue");

			//入住人数限制
			String limitJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
			List<EnumVo> limitList=SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);

			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
			
			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);

			model.addAttribute("roomFid",roomFid);
			model.addAttribute("isModify", isModify);
			model.addAttribute("cleaningFees", Double.parseDouble(cleaningFees));
			model.addAttribute("bedTypeList", bedTypeList);
			model.addAttribute("bedSizeList", bedSizeList);
			model.addAttribute("limitList", limitList);
			model.addAttribute("houseFid", fid);
			model.addAttribute("houseRooms", houseRooms);
			model.addAttribute("houseRoomIssueCount", houseRoomIssueCount);
			model.addAttribute("clickStep", HouseIssueStepEnum.FIVE.getCode());
			if(houseRooms.getHouseBaseMsgEntity().getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
				model.addAttribute("nowStep", houseRooms.getHouseBaseMsgEntity().getOperateSeq());
			}else{
				model.addAttribute("nowStep", HouseIssueStepEnum.FOUR.getCode());
			}
			model.addAttribute("priceLow", priceLow);
			model.addAttribute("priceHigh", priceHigh);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "houseRooms error:{}", e);
		}
		return "/houseIssue/houseRoomsFen";
	}
	
	/**
	 * 
	 * 新增或者更新房间信息
	 *
	 * @author jixd
	 * @created 2016年8月12日 下午3:42:53
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/saveOrUpdateRoom")
	@ResponseBody
	public DataTransferObject saveOrUpdateRooms(String param){
		LogUtil.info(LOGGER, "方法【saveOrUpdateRooms】，保存合租房间信息参数param:{}", param);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(param)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto;
		}
		JSONObject houseObj = JSONObject.parseObject(param);
		JSONArray roomsArray = houseObj.getJSONArray("rooms");
		String houseFid = houseObj.getString("houseFid");
		Integer toiletNum = houseObj.getInteger("toiletNum");
		Integer roomNum=houseObj.getInteger("roomNum");
		if(Check.NuNObj(toiletNum) || toiletNum.intValue() == 0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("卫生间数量至少为1");
			return dto;
		}
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源Fid为空");
			return dto;
		}
		if(Check.NuNObj(roomsArray)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间信息为空");
			return dto;
		}
		//判断房间数量是否大于户型
		if(!Check.NuNObj(roomsArray)&&roomNum<roomsArray.size()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间数量不能大于户型");
			return dto;
		}
		String resultJson = houseIssuePcService.saveOrUpHouseFRooms(param);
		dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		dto.putValue("houseBaseFid", houseFid);
	
		return dto;
	}
	
	/**
	 * 
	 * 删除房间
	 *
	 * @author jixd
	 * @created 2016年8月12日 下午3:45:57
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/delRoom/{roomFid}")
	@ResponseBody
	public DataTransferObject delRoom(@PathVariable String roomFid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(roomFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间Fid为空");
			return dto;
		}
		String delRoomJson = houseIssuePcService.delFRoomByFid(roomFid);
		DataTransferObject delRoomDto = JsonEntityTransform.json2DataTransferObject(delRoomJson);
		LogUtil.info(LOGGER, "【删除房间结果】result={}", delRoomJson);
		return delRoomDto;
	}
	
	/**
	 * 
	 * 删除床铺信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午12:02:34
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/delBed/{bedFid}")
	@ResponseBody
	public DataTransferObject delBed(@PathVariable String bedFid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(bedFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("床铺Fid为空");
			return dto;
		}
		String delResult = houseIssuePcService.delBedByFid(bedFid);
		LogUtil.info(LOGGER, "【删除床铺结果】result={}", delResult);
		return JsonEntityTransform.json2DataTransferObject(delResult);
	}
	
	/**
	 * 
	 * 发布房源 房源描述信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午8:20:05
	 *
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/houseIssue/desc/{housefid}")
	public String houseDescMsg(@PathVariable String housefid,String roomFid, Model model) throws SOAParseException{
		//roomFid直接放到页面中，不做任何处理
		model.addAttribute("roomFid",roomFid);
		//查询房源基本信息
		String houseJson=houseIssueService.searchHouseBaseAndExtByFid(housefid);
		HouseBaseExtDto houseBaseExt=SOAResParseUtil.getValueFromDataByKey(houseJson, "obj",HouseBaseExtDto.class);
		//查询房源描述信息
		String resultJson = houseIssuePcService.findHouseBaseAndDesc(housefid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			HouseIssueDescDto parseData = dto.parseData("desc", new TypeReference<HouseIssueDescDto>() {});
			model.addAttribute("clickStep", HouseIssueStepEnum.FOUR.getCode());
			if(houseBaseExt.getOperateSeq()>=HouseIssueStepEnum.SEVEN.getCode()){
			    model.addAttribute("nowStep", houseBaseExt.getOperateSeq());
		    }else{
		    	model.addAttribute("nowStep", HouseIssueStepEnum.THREE.getCode());
			}
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseUpdateLogService.houseFieldAuditLogVoConvertMap(housefid, roomFid, houseBaseExt.getRentWay(), 0);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath())){
				parseData.setHouseName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath())){
				parseData.setHouseDesc(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath())){
				parseData.setHouseAround(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath()).getNewValue());
			}
			/**要审核字段替换结束**/
			model.addAttribute("desc", parseData);
		}
		return "houseIssue/houseDescMsg";
	}
	
	/**
	 * 
	 * 保存房源描述信息
	 *
	 * @author jixd
	 * @created 2016年8月15日 下午9:07:03
	 *
	 * @param houseFid
	 * @return
	 */
	@RequestMapping("/houseIssue/saveOrUpDesc/{housefid}")
	@ResponseBody
	public DataTransferObject saveHouseDesc(@PathVariable String housefid,HouseIssueDescDto houseDesc){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(housefid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FId为空");
				return dto;
			}
			if(Check.NuNObj(houseDesc)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}
			if(Check.NuNStr(houseDesc.getHouseName())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源名称为空");
				return dto;
			}
			if(Check.NuNStr(houseDesc.getHouseDesc())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源描述为空");
				return dto;
			}
			if(houseDesc.getHouseDesc().length()<100){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源描述不能少于100个字");
				return dto;
			}
			if(houseDesc.getHouseDesc().length()>1000){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源描述不能超过1000个字");
				return dto;
			}			
			
			if(Check.NuNStr(houseDesc.getHouseAround())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("周边情况为空");
				return dto;
			}
			if(houseDesc.getHouseAround().length()<100){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("周边情况不能少于100个字");
				return dto;
			}
			if(houseDesc.getHouseAround().length()>1000){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("周边情况不能超过1000个字");
				return dto;
			}
			houseDesc.setHouseFid(housefid);
			
			//保存修改记录 --- 查询历史数据
			String houseJson=houseIssueService.searchHouseBaseAndExtByFid(housefid);
			HouseBaseExtDto houseBaseExt=SOAResParseUtil.getValueFromDataByKey(houseJson, "obj",HouseBaseExtDto.class);
			Integer rentWay = null;
			if(!Check.NuNObj(houseBaseExt)){
				 rentWay = houseBaseExt.getRentWay();
			}
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(housefid,houseDesc.getRoomFid(), rentWay);
			String resultJson = houseIssuePcService.saveOrUpdateHouseDesc(JsonEntityTransform.Object2Json(houseDesc));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			dto.putValue("houseBaseFid", housefid);
			
			if(dto.getCode() == DataTransferObject.SUCCESS){
				//保存修改记录 --- 保存
				if(!Check.NuNObj(houseUpdateHistoryLogDto)){
					houseUpdateHistoryLogDto.setHouseFid(housefid);
					houseUpdateLogService.saveHistoryLog(houseDesc,houseUpdateHistoryLogDto);
				}
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【保存房源周边描述异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
			return dto;
		}
		return dto;
	}
	
	/**
	 * 
	 * 房源图片上传
	 *
	 * @author jixd
	 * @created 2016年8月16日 下午3:08:55
	 *
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/houseIssue/pic/{houseFid}")
	public String housePic(@PathVariable String houseFid, String roomFid,Model model) throws SOAParseException{
		//roomFid直接传递到页面
		model.addAttribute("roomFid", roomFid);
		if(Check.NuNStr(houseFid)){
			//跳转404页面
		}
		//查询房源基本信息
		String houseJson=houseIssueService.searchHouseBaseAndExtByFid(houseFid);
		HouseBaseExtDto houseBaseExt=SOAResParseUtil.getValueFromDataByKey(houseJson, "obj",HouseBaseExtDto.class);
		model.addAttribute("houseFid", houseFid);
//		model.addAttribute("clickStep", HouseIssueStepEnum.SIX.getCode());
//		if(houseBaseExt.getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
//			model.addAttribute("nowStep", houseBaseExt.getOperateSeq());
//		}else{
//			model.addAttribute("nowStep", HouseIssueStepEnum.FIVE.getCode());
//		}
		model.addAttribute("clickStep", HouseIssueStepEnum.SEVEN.getCode());
		model.addAttribute("nowStep", HouseIssueStepEnum.SEVEN.getCode());
		model.addAttribute("rentWay", houseBaseExt.getRentWay());
		int isShow = 0;
		int status = 0;
		Integer houseStatus = houseBaseExt.getHouseStatus();
		if(houseBaseExt.getRentWay() == RentWayEnum.HOUSE.getCode()){
			if(isIssueStatus(houseStatus)){
				isShow = 1;
			}
			if(houseStatus == HouseStatusEnum.SJ.getCode()){
				status = 1;
			}
		}
		//合租房源 需要判断下面的房间是否有需要发布的房间 如果有需要发布房间 则显示发布按钮
		if(houseBaseExt.getRentWay() == RentWayEnum.ROOM.getCode()){
			if(Check.NuNStr(roomFid)){
				isShow = 1;
			} else {
				String roomJson=houseIssueService.searchHouseRoomMsgByFid(roomFid);
				HouseRoomMsgEntity roomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				if(!Check.NuNObj(roomMsgEntity)){
					if(roomMsgEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
						status = 1;
					}
					if(isIssueStatus(roomMsgEntity.getRoomStatus())){
						isShow = 1;
					}
				}
			}
//			String resultJson = houseIssueService.searchRoomListByHouseBaseFid(houseFid);
//			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
//			if(resultDto.getCode() == DataTransferObject.SUCCESS){
//				List<HouseRoomMsgEntity> roomList = resultDto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>() {});
//				for(HouseRoomMsgEntity room : roomList){
//					if(room.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
//						status = 1;
//					}
//					if(isIssueStatus(room.getRoomStatus())){
//						isShow = 1;
//					}
//				}
//			}
		}
		
		model.addAttribute("picSmallSize", pic_size_375_281);
		model.addAttribute("picBigSize", pic_size);
		model.addAttribute("picBaseAddrMona", picBaseAddrMona);
		model.addAttribute("status", status);
		model.addAttribute("isShow", isShow);
		return "houseIssue/housePic";
	}
	
	
	/**
	 * 
	 * 显示房源照片 根据不同的类型，不同房间
	 *
	 * @author jixd
	 * @created 2016年8月17日 下午4:53:57
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/housePic/{houseFid}")
	@ResponseBody
	public DataTransferObject houseTypePics(@PathVariable String houseFid, String roomFid){
		DataTransferObject dto = new DataTransferObject();
		try{
			HousePicTypeDto requestParam = new HousePicTypeDto();
			requestParam.setHouseFid(houseFid);
			//查询房源图片，根据类型，查询全部类型
			String housePicListJson = houseIssuePcService.findHousePicByTypeAndFid(JsonEntityTransform.Object2Json(requestParam));
			DataTransferObject housePicListDto = JsonEntityTransform.json2DataTransferObject(housePicListJson);
			List<HousePicMsgEntity> picMsgList = null;
			if(housePicListDto.getCode() == DataTransferObject.SUCCESS){
				picMsgList = housePicListDto.parseData("picList", new TypeReference<List<HousePicMsgEntity>>() {});
			}

			//查询房源信息
			String houseBaseJsonString=houseIssueService.searchHouseBaseMsgByFid(houseFid);
			HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJsonString, "obj", HouseBaseMsgEntity.class);

			if(Check.NuNObj(houseBaseMsgEntity)){
				LogUtil.error(LOGGER, "houseTypePics(),该房源数据异常，houseFid = {}", houseFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return dto;
			}

			JSONArray jsonArray = new JSONArray();

			// 当前房间的照片
			if(!Check.NuNStr(roomFid)){
				String roomJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
				DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
				HouseRoomMsgEntity roomMsg = new HouseRoomMsgEntity();
				if(roomDto.getCode() == DataTransferObject.SUCCESS){
					roomMsg = roomDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
				}
				if(Check.NuNObj(roomMsg)){
					LogUtil.error(LOGGER, "houseTypePics(), 房间不存在，houseFid = {},roomFid={}", houseFid,roomFid);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房间不存在");
					return dto;
				}
				JSONObject obj = new JSONObject();
				obj.put("picType",0);
				obj.put("roomfid", roomMsg.getFid());
				obj.put("url", "");
				if(!Check.NuNCollection(picMsgList)){
					for(HousePicMsgEntity picMsg : picMsgList){
						//如果是卧室类型，并且是该房间fid，则设置图片
						if(picMsg.getPicType() == HousePicTypeEnum.WS.getCode() && roomMsg.getFid().equals(picMsg.getRoomFid())){
							obj.put("url", PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size_375_281));
							break;
						}
					}
				}
				jsonArray.add(obj);
			}else{
				//查询房源下面所有房间
				String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseFid);
				DataTransferObject roomListDto = JsonEntityTransform.json2DataTransferObject(roomListJson);
				List<HouseRoomMsgEntity> roomMsgList = null;
				if(roomListDto.getCode() == DataTransferObject.SUCCESS){
					roomMsgList = roomListDto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>() {});
				}
				if(!Check.NuNCollection(roomMsgList)){
					for(HouseRoomMsgEntity roomMsg : roomMsgList){
						JSONObject obj = new JSONObject();
						obj.put("picType",0);
						obj.put("roomfid", roomMsg.getFid());
						obj.put("url", "");
						if(!Check.NuNCollection(picMsgList)){
							for(HousePicMsgEntity picMsg : picMsgList){
								//如果是卧室类型，并且是该房间fid，则设置图片
								if(picMsg.getPicType() == HousePicTypeEnum.WS.getCode() && roomMsg.getFid().equals(picMsg.getRoomFid())){
									obj.put("url", PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size_375_281));
									break;
								}
							}
						}
						jsonArray.add(obj);
					}
				}
			}

			//除了房间其他类型的图片都要设置一张
			for (HousePicTypeEnum picEnum : HousePicTypeEnum.values()) {  
				if(picEnum.getCode() == HousePicTypeEnum.WS.getCode()){
					continue;
				}
				if(picEnum.getCode()==HousePicTypeEnum.KT.getCode()&&houseBaseMsgEntity.getHallNum()==0){
					continue;
				}
				if(picEnum.getCode()==HousePicTypeEnum.WSJ.getCode()&&houseBaseMsgEntity.getToiletNum()==0){
					continue;
				}
				if(picEnum.getCode()==HousePicTypeEnum.CF.getCode()&&houseBaseMsgEntity.getKitchenNum()==0){
					continue;
				}
				JSONObject obj = new JSONObject();
				obj.put("picType", picEnum.getCode());
				obj.put("roomfid", "");
				obj.put("url", "");
				if(!Check.NuNCollection(picMsgList)){
					for(HousePicMsgEntity picMsg : picMsgList){
						if(picMsg.getPicType() == picEnum.getCode()){
							obj.put("url", PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size_375_281));
							break;
						}
					}
				}
				
				jsonArray.add(obj);
			}
			dto.putValue("picList", jsonArray);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【展示房源图片列表异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 获取房间图片集合
	 *
	 * @author jixd
	 * @created 2016年8月17日 下午7:53:22
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/typepic")
	@ResponseBody
	public DataTransferObject getRoomPics(String houseFid,String roomFid,Integer picType){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto;
		}
		try{
			List<String> defaultPicList = new ArrayList<>();
			DataTransferObject defaultPicDto = JsonEntityTransform.json2DataTransferObject(houseIssuePcService.getHouseDefaultPicList(houseFid));
			if(defaultPicDto.getCode() == DataTransferObject.SUCCESS){
				defaultPicList = defaultPicDto.parseData("list", new TypeReference<List<String>>() {});
			}
		
			HousePicTypeDto requestParam = new HousePicTypeDto();
			requestParam.setHouseFid(houseFid);
			requestParam.setRoomFid(roomFid);
			List<Integer> picTypes = new ArrayList<>();
			picTypes.add(picType);
			requestParam.setPicTypes(picTypes);
			
			String housePicListJson = houseIssuePcService.findHousePicByTypeAndFid(JsonEntityTransform.Object2Json(requestParam));
			DataTransferObject housePicListDto = JsonEntityTransform.json2DataTransferObject(housePicListJson);
			
			JSONArray jsonArr = new JSONArray();
			if(housePicListDto.getCode() == DataTransferObject.SUCCESS){
				List<HousePicMsgEntity> picList = housePicListDto.parseData("picList", new TypeReference<List<HousePicMsgEntity>>() {});
				if(!Check.NuNCollection(picList)){
					for(HousePicMsgEntity picMsg : picList){
						JSONObject obj = new JSONObject();
						obj.put("picfid", picMsg.getFid());
						obj.put("isdefault", 0);
						obj.put("picbaseurl", picMsg.getPicBaseUrl());
						obj.put("url", PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size_375_281));
						obj.put("suffix", picMsg.getPicSuffix());
						if(!Check.NuNCollection(defaultPicList)){
							for(String str : defaultPicList){
								if(picMsg.getFid().equals(str)){
									obj.put("isdefault", 1);
									break;
								}
							}
						}
						jsonArr.add(obj);
					}
				}
			}
			dto.putValue("picList", jsonArr);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取房间图片异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	
	
	/**
	 * 
	 * 上传房源图片
	 *
	 * @author jixd
	 * @created 2016年8月16日 下午3:34:23
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/uploadPic")
	@ResponseBody
	public String houseUploadPic(@RequestParam(value="file") MultipartFile multipartFile){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(multipartFile)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("上传照片为空");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER,"图片信息:contentType{}",multipartFile.getContentType());
		try {
			LogUtil.info(LOGGER,"图片信息:originalFilename:{},size:{}",multipartFile.getOriginalFilename(),multipartFile.getSize());
			FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, multipartFile.getOriginalFilename(), multipartFile.getBytes(), "民宿房源照片",0l, "民宿房源图片上传");
			if(!Check.NuNObj(fileResponse.getFile())){
				dto.putValue("file", fileResponse.getFile());
			}else{
				LogUtil.error(LOGGER,"上传图片失败返回结果result={}",JsonEntityTransform.Object2Json(fileResponse));
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("上传照片失败");
				return dto.toJsonString();
			}
		} catch (IOException e) {
			LogUtil.error(LOGGER, "【上传照片失败】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("上传照片失败");
			return dto.toJsonString();
		}
		LogUtil.info(LOGGER,"pc上传房源图片返回结果={}",dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 兼容IE处理 上传图片操作
	 *
	 * @author jixd
	 * @created 2016年8月25日 下午3:23:55
	 *
	 * @return
	 */
	@RequestMapping("/housePic/upload")
	@ResponseBody
	public String houseUploadifyPic(HttpServletRequest request,HttpServletResponse response){
		DataTransferObject dto = new DataTransferObject();
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			InternalFile file = null;
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile multipartFile = entity.getValue();
				FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, multipartFile.getOriginalFilename(), multipartFile.getBytes(), "民宿房源照片",0l, "民宿房源图片上传");
				if(!Check.NuNObj(fileResponse.getFile())){
					file = fileResponse.getFile();
				}
			}
			dto.putValue("file", file);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【IE上传文件出错】e={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("上传文件出错");
		}
		LogUtil.info(LOGGER,"pcIE上传房源图片返回结果={}",dto.toJsonString());
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * 删除房源图片
	 *
	 * @author jixd
	 * @created 2016年8月16日 下午3:35:19
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/delHousePic")
	@ResponseBody
	public DataTransferObject delHousePic(HousePicDelDto picParam,String picserveruuid){
		DataTransferObject dto = new DataTransferObject();
		if(!Check.NuNStr(picserveruuid)){
//			try {
//				boolean delete = storageService.delete(picserveruuid);
//				if(delete){
//					LogUtil.info(LOGGER, "【删除房源照片成功】");
//				}
//			} catch (Exception e) {
//				LogUtil.error(LOGGER, "删除图片失败，picserveruuid={},e={}",picserveruuid, e);
//			}
			
			return dto;
		}
		HousePicBaseParamsDto housePicBaseParamsDto = new HousePicBaseParamsDto();
		housePicBaseParamsDto.setHouseBaseFid(picParam.getHouseBaseFid());
		housePicBaseParamsDto.setHouseRoomFid(picParam.getHouseRoomFid());
		housePicBaseParamsDto.setHousePicFid(picParam.getHousePicFid());
		housePicBaseParamsDto.setPicType(picParam.getPicType());
		housePicBaseParamsDto.setPicSource(3);
		if(!Check.NuNObj(picParam.getPicType()) && !Check.NuNStr(picParam.getHousePicFid())){
			String resultJson = houseIssueService.deleteHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicBaseParamsDto));
			LogUtil.info(LOGGER, "【删除图片信息】result={}", resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		}
		return dto;
	}
	
	
	/**
	 * 
	 * 保存房源图片
	 *
	 * @author jixd
	 * @created 2016年8月16日 下午3:37:04
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/saveHousePic")
	@ResponseBody
	public DataTransferObject saveHousePic(String param){
		DataTransferObject dto = new DataTransferObject();
		try{
			JSONObject object = JSONObject.parseObject(param);
			JSONArray imgList = object.getJSONArray("imgList");
			String houseFid = object.getString("housefid");
			Integer picType = object.getInteger("picType");
			String roomFid = object.getString("forUrlJumpRoomFid");
			
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto;
			}
			if(Check.NuNObj(picType)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型为空");
				return dto;
			}
			/** 获取房源状态 **/
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			String houseBaseJson=houseIssueService.searchHouseBaseMsgByFid(houseFid);
			HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseMsgEntity.class);
			if(!Check.NuNObj(houseBaseMsgEntity)){
				if(RentWayEnum.HOUSE.getCode()==houseBaseMsgEntity.getRentWay()){
					houseStatus=houseBaseMsgEntity.getHouseStatus();
				} else if(RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay()&&!Check.NuNStr(roomFid)) {
					String roomJson=houseIssueService.searchHouseRoomMsgByFid(roomFid);
					HouseRoomMsgEntity houseRoomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
					houseStatus=houseRoomMsgEntity.getRoomStatus();
				}
			}
			/** 获取房源状态 **/
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto=houseUpdateLogService.findWaitUpdateHouseInfo(houseFid, roomFid, null);
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
			}
			String houseBaseExtJson=houseIssueService.searchHouseBaseAndExtByFid(houseFid);
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
			JSONArray nimgList = new JSONArray();
			for(int i = 0;i<imgList.size();i++){
				JSONObject obj = imgList.getJSONObject(i);
				String baseUrl = obj.getString("picbaseurl");
				String picFid = obj.getString("picfid");
				String picUUID = obj.getString("picserveruuid");
				String picsuffix = obj.getString("picsuffix");
				Integer degree = obj.getInteger("degrees");
				if ("undefined".equals(baseUrl)){
					continue;
				}
				//审核不通过要新增状态
				if(HouseStatusEnum.ZPSHWTG.getCode()==houseStatus){
					obj.put("auditStatus", 3);
				} else if(HousePicTypeEnum.WS.getCode()!=picType&&RentWayEnum.ROOM.getCode()==houseBaseMsgEntity.getRentWay()&&Check.NuNStr(roomFid)) {
					obj.put("auditStatus", 3);
				} else {
					obj.put("auditStatus", 0);
				}
				//如果有旋转图片的话 先旋转图片
				if(degree!=null && degree > 0){
					String fullPic = PicUtil.getFullPic(picBaseAddrMona, baseUrl, picsuffix, pic_size_375_281);
					FileInfoResponse fileInfoResponse = getRotateUploadImg(fullPic,picsuffix,degree);
					if(!Check.NuNObj(fileInfoResponse)){
						if(!Check.NuNObj(fileInfoResponse.getFile())){
							//如果还没有上传过照片的话直接删除物理图片
							if(Check.NuNStr(picFid) && !Check.NuNStr(picUUID)){
//							storageService.delete(picUUID);
							}
							InternalFile file = fileInfoResponse.getFile();
							obj.put("picserveruuid", file.getUuid());
							obj.put("picbaseurl", file.getUrlBase());
							obj.put("picsuffix", file.getUrlExt());
						}
					}

				}
				nimgList.add(obj);
			}
			object.remove("imgList");
			object.put("list", nimgList);
			String resultJson = houseIssuePcService.saveHousePicByType(object.toJSONString());
			LogUtil.info(LOGGER, "【保存图片返回结果】result={}", resultJson);
			
			DataTransferObject resultDto=JsonEntityTransform.json2DataTransferObject(resultJson);
			//如果成功保存默认图片修改历史
			if(resultDto.getCode()==DataTransferObject.SUCCESS&&!Check.NuNObj(resultDto.getData().get("defaultPicFid"))){
				if(!Check.NuNObj(houseUpdateHistoryLogDto)){
					if(houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
						HouseBaseExtEntity newBaseExtEntity=new HouseBaseExtEntity();
						newBaseExtEntity.setDefaultPicFid(resultDto.getData().get("defaultPicFid").toString());
						houseUpdateHistoryLogDto.setHouseBaseExt(newBaseExtEntity);
					} else if (houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
						HouseRoomMsgEntity newRoomMsgEntity=new HouseRoomMsgEntity();
						newRoomMsgEntity.setDefaultPicFid(resultDto.getData().get("defaultPicFid").toString());
						houseUpdateHistoryLogDto.setHouseRoomMsg(newRoomMsgEntity);
					}
					houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				}
			}
			return resultDto;
		}catch(Exception e){
			LogUtil.error(LOGGER, "【保存图片异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("保存图片失败");
			return dto;
		}
	}
	
	
	/**
	 * 
	 * 获取旋转后的图片
	 *
	 * @author jixd
	 * @created 2016年8月18日 上午10:39:27
	 *
	 * @return
	 */
	private FileInfoResponse getRotateUploadImg(String picUrl,String suffix,int degree){
		try{
			URL url = new URL(picUrl);    
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
			conn.setRequestMethod("GET");    
			conn.setConnectTimeout(5 * 1000);    
			InputStream in = conn.getInputStream();//通过输入流获取图片数据 
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			ImageHelper.rotateImg(in, out, degree, null);
			String newPicName = RandomUtil.genRandomNum(3)+suffix;
			return storageService.upload(storageKey, storageLimit, newPicName, out.toByteArray(), "民宿房源照片",0l, "民宿房源图片上传");
		}catch(Exception e){
			LogUtil.error(LOGGER, "【旋转图片上传异常】e={}", e);
			return null;
		}
	}
	
	
	
	/**
	 * 
	 * 获取房源公共区域图片
	 *
	 * @author jixd
	 * @created 2016年8月17日 上午11:11:25
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/getOtherPic/{houseFid}")
	@ResponseBody
	public DataTransferObject getHousePublicPic(@PathVariable String houseFid){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto;
			}
			
			//查询房源默认图片
			List<String> defaultPicList = new ArrayList<>();
			DataTransferObject defaultPicDto = JsonEntityTransform.json2DataTransferObject(houseIssuePcService.getHouseDefaultPicList(houseFid));
			if(defaultPicDto.getCode() == DataTransferObject.SUCCESS){
				defaultPicList = defaultPicDto.parseData("list", new TypeReference<List<String>>() {});
			}
			
			
			HousePicTypeDto requestParam = new HousePicTypeDto();
			requestParam.setHouseFid(houseFid);
			
			List<Integer> picTypes = new ArrayList<>();
			picTypes.add(HousePicTypeEnum.SW.getCode());
			picTypes.add(HousePicTypeEnum.KT.getCode());
			requestParam.setPicTypes(picTypes);
			//查询房源图片，根据类型，查询全部类型
			String housePicListJson = houseIssuePcService.findHousePicByTypeAndFid(JsonEntityTransform.Object2Json(requestParam));
			DataTransferObject housePicListDto = JsonEntityTransform.json2DataTransferObject(housePicListJson);
			List<HousePicMsgEntity> picMsgList = null;
			if(housePicListDto.getCode() == DataTransferObject.SUCCESS){
				picMsgList = housePicListDto.parseData("picList", new TypeReference<List<HousePicMsgEntity>>() {});
			}
			
			JSONArray jsonArr = new JSONArray();
			for(HousePicMsgEntity picMsg : picMsgList){
				JSONObject obj = new JSONObject();
				obj.put("picfid", picMsg.getFid());
				obj.put("isdefault", 0);
				obj.put("url", PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size_375_281));
				if(!Check.NuNCollection(defaultPicList)){
					for(String str : defaultPicList){
						if(picMsg.getFid().equals(str)){
							obj.put("isdefault", 1);
							break;
						}
					}
				}
				jsonArr.add(obj);
			}
			dto.putValue("list", jsonArr);
			
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取其他照片失败】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	
	
	/**
	 * 
	 * 设置默认图片,合租房间中选择公共区域图片设置该房间的默认图片
	 *
	 * @author jixd
	 * @created 2016年8月24日 下午7:49:05
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/setDefaultPic")
	@ResponseBody
	public DataTransferObject setRoomDefaultPic(RoomDefaultPicDto defaultPicParam){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(defaultPicParam)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}
			if(Check.NuNStr(defaultPicParam.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto;
			}
			if(Check.NuNStr(defaultPicParam.getRoomFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间FID为空");
				return dto;
			}
			if(Check.NuNStr(defaultPicParam.getPicFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片FID为空");
				return dto;
			}
			//添加默认图片修改记录
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto=new HouseUpdateHistoryLogDto();
			houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.PC.getCode());
			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
			houseUpdateHistoryLogDto.setCreaterFid(UserUtils.getCurrentUid());
			houseUpdateHistoryLogDto.setHouseFid(defaultPicParam.getHouseFid());
			String houseBaseExtJson=houseIssueService.searchHouseBaseAndExtByFid(defaultPicParam.getHouseFid());
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
			houseUpdateHistoryLogDto.setRentWay(houseBaseExtDto.getRentWay());
			if(houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				houseUpdateHistoryLogDto.setOldHouseBaseExt(houseBaseExtDto.getHouseBaseExt());
			} else if (houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
				houseUpdateHistoryLogDto.setRoomFid(defaultPicParam.getRoomFid());
				String roomJson=houseIssueService.searchHouseRoomMsgByFid(defaultPicParam.getRoomFid());
				HouseRoomMsgEntity roomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				houseUpdateHistoryLogDto.setOldHouseRoomMsg(roomMsgEntity);
			}
			
			String resultJson = houseIssuePcService.setRoomDefaultPic(JsonEntityTransform.Object2Json(defaultPicParam));
			
			DataTransferObject resultDto=JsonEntityTransform.json2DataTransferObject(resultJson);
			//如果成功保存默认图片修改历史
			if(resultDto.getCode()==DataTransferObject.SUCCESS){
				if(houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
					HouseBaseExtEntity newBaseExtEntity=new HouseBaseExtEntity();
					newBaseExtEntity.setDefaultPicFid(defaultPicParam.getPicFid());
					houseUpdateHistoryLogDto.setHouseBaseExt(newBaseExtEntity);
				} else if (houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
					HouseRoomMsgEntity newRoomMsgEntity=new HouseRoomMsgEntity();
					newRoomMsgEntity.setDefaultPicFid(defaultPicParam.getPicFid());
					houseUpdateHistoryLogDto.setHouseRoomMsg(newRoomMsgEntity);
				}
				houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			}
			return resultDto;
		}catch(Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		
		return dto;
	}
	
	/**
	 * 
	 * 获取分租房间集合
	 *
	 * @author jixd
	 * @created 2016年8月26日 下午8:59:12
	 *
	 * @return
	 */
	@RequestMapping("/house/getFRooms")
	@ResponseBody
	public DataTransferObject getFRooms(String houseFid,String roomFid,String type){
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(roomFid)&&Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源fid为空");
				return dto;
			}
			JSONArray array = new JSONArray();
			if(!Check.NuNStr(roomFid)){
				String resultJson = houseIssueService.searchRoomByRoomFid(roomFid);
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(resultDto.getCode() == DataTransferObject.ERROR){
					return resultDto;
				}
				HouseRoomMsgEntity  room = resultDto.parseData("houseRoomMsg", new TypeReference<HouseRoomMsgEntity>() {});

				if (!Check.NuNObj(room)) {
					Integer status = room.getRoomStatus();
					if (("fabu".equals(type) && isIssueStatus(status)) || "preview".equals(type)) {
						JSONObject obj = new JSONObject();
						obj.put("roomFid", room.getFid());
						obj.put("roomName", room.getRoomName());
						obj.put("statusName", HouseStatusEnum.getHouseStatusByCode(status).getShowStatusName());
						array.add(obj);
					}
				}
				dto.putValue("roomList", array);
				return dto;
			}
			if(!Check.NuNStr(houseFid)){
				String resultJson = houseIssueService.searchRoomListByHouseBaseFid(houseFid);
				DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(resultDto.getCode() == DataTransferObject.ERROR){
					return resultDto;
				}

				List<HouseRoomMsgEntity> roomList = resultDto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>() {});

				if(!Check.NuNCollection(roomList)){
					
					for (HouseRoomMsgEntity room : roomList) {
						Integer status = room.getRoomStatus();
						if(type!=null && "fabu".equals(type)){
							if(!isIssueStatus(status)){
							  continue;
							}
						}
						JSONObject obj = new JSONObject();
						obj.put("roomFid", room.getFid());
						obj.put("roomName", room.getRoomName());
						obj.put("statusName", HouseStatusEnum.getHouseStatusByCode(status).getShowStatusName());
						array.add(obj);
					}
					
				}
				dto.putValue("roomList", array);
				return dto;
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取合租房间集合异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		
		return dto;
	}
	
	/**
	 * 
	 * 发布合租房源
	 *
	 * @author jixd
	 * @created 2016年8月27日 下午4:15:09
	 *
	 * @return
	 */
	@RequestMapping("/rooms/release")
	@ResponseBody
	public DataTransferObject releaseRoomsFinally(String param){
		DataTransferObject dto = new DataTransferObject();
		LogUtil.info(LOGGER, "【PC合租房间发布参数】param={}",param);
		try{
			if(Check.NuNStr(param)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}
			JSONObject requestObj = JSONObject.parseObject(param);
			String houseFid = requestObj.getString("houseFid");
			String rentWay = requestObj.getString("rentWay");
			JSONArray roomArray = requestObj.getJSONArray("roomList");
			if(Check.NuNStr(houseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto;
			}
			if(Check.NuNObj(roomArray)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("没有选中的房间");
				return dto;
			}
			
			List<String> roomFids = new ArrayList<String>();
			for (int i = 0; i < roomArray.size(); i++) {
				//发布的房源是否有默认照片
				 HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
				 houseBaseParamsDto.setHouseBaseFid(houseFid);
				 houseBaseParamsDto.setRentWay(Integer.valueOf(rentWay));
				 houseBaseParamsDto.setRoomFid(roomArray.getString(i));
				 Map<String, HouseFieldAuditLogVo> houseFieldAuditMap = houseUpdateLogService.houseFieldAuditLogVoConvertMap(houseFid, roomArray.getString(i), Integer.valueOf(rentWay), 0);
		         if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath())){
	            	houseBaseParamsDto.setAuditDefaultPic(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath()).getNewValue());
	             }
		         if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())){
		        	houseBaseParamsDto.setAuditHallNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath()).getNewValue()));
		         }
				 String isSetDefaultPicJson = houseIssueService.isSetDefaultPic(JsonEntityTransform.Object2Json(houseBaseParamsDto));
				 DataTransferObject isSetDefaultPicDto = JsonEntityTransform.json2DataTransferObject(isSetDefaultPicJson);
				 if (isSetDefaultPicDto.getCode() == DataTransferObject.ERROR) {
					 LogUtil.error(LOGGER, "issueHouse(),获取房源或房间是否设置了封面照片失败,msg:{}",isSetDefaultPicDto.getMsg());
					 dto.setErrCode(DataTransferObject.ERROR);
					 dto.setMsg("发布房源异常");
					 return dto;
				 }
				 Boolean hasDefault = SOAResParseUtil.getBooleanFromDataByKey(isSetDefaultPicJson,"hasDefault");
				 if(!hasDefault){
					 dto.setErrCode(DataTransferObject.ERROR);
					 dto.setMsg("请给房源或者每个房间设置封面照片");
					 return dto;
				 }
				roomFids.add(roomArray.getString(i));
			}
			Map<Integer, String> errorMsgMap = new TreeMap<Integer, String>();
			boolean isUploadable = validatePicNumByType(houseFid, Integer.valueOf(rentWay), roomFids, errorMsgMap);
			if (!isUploadable) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(assembleErrorMsg(errorMsgMap));
				return dto;
			}
			
			String uid = UserUtils.getCurrentUid();
			String houseBaseJson=houseIssueService.searchHouseBaseMsgByFid(houseFid);
			HouseBaseMsgEntity houseBase=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseMsgEntity.class);
			Integer oldStatus = houseBase.getHouseStatus();
			String resultJson = houseIssuePcService.issueRooms(param);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//发布房源成功以后判断是否为房东，如果
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				if(oldStatus == HouseStatusEnum.DFB.getCode()){
					houseBusinessInput(houseFid,uid);
				}
			}
			
			LogUtil.info(LOGGER, "【PC合租房间发布返回结果】result={}",resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【合租发布房源异常】e={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("发布房源异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 房源发布页 
	 *
	 * @author jixd
	 * @created 2016年8月18日 下午5:55:33
	 *
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/houseIssue/release")
	@ResponseBody
	public DataTransferObject releaseHouseFinally(String houseFid, Integer rentWay) throws SOAParseException{
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源FID为空");
			return dto;
		}
		//发布的房源是否有默认照片
		 HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
		 houseBaseParamsDto.setHouseBaseFid(houseFid);
		 houseBaseParamsDto.setRentWay(rentWay);
		 Map<String, HouseFieldAuditLogVo> houseFieldAuditMap = houseUpdateLogService.houseFieldAuditLogVoConvertMap(houseFid, null, rentWay, 0);
         if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath())){
        	houseBaseParamsDto.setAuditDefaultPic(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath()).getNewValue());
         }
         if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())){
        	houseBaseParamsDto.setAuditHallNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath()).getNewValue()));
         }
		 String isSetDefaultPicJson = houseIssueService.isSetDefaultPic(JsonEntityTransform.Object2Json(houseBaseParamsDto));
		 DataTransferObject isSetDefaultPicDto = JsonEntityTransform.json2DataTransferObject(isSetDefaultPicJson);
		 if (isSetDefaultPicDto.getCode() == DataTransferObject.ERROR) {
			 LogUtil.error(LOGGER, "issueHouse(),获取房源或房间是否设置了封面照片失败,msg:{}",isSetDefaultPicDto.getMsg());
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("发布房源异常");
			 return dto;
		 }
		 Boolean hasDefault = SOAResParseUtil.getBooleanFromDataByKey(isSetDefaultPicJson,"hasDefault");
		 if(!hasDefault){
			 dto.setErrCode(DataTransferObject.ERROR);
			 dto.setMsg("请给房源或者每个房间设置封面照片");
			 return dto;
		 }

		String uid = UserUtils.getCurrentUid();
		JsonObject param=new JsonObject();
		param.addProperty("houseFid", houseFid);
		param.addProperty("lanUid", uid);
		Map<Integer, String> errorMsgMap = new TreeMap<Integer, String>();
		boolean isUploadable = this.validatePicNumByType(houseFid, rentWay, null, errorMsgMap);
		if (!isUploadable) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(assembleErrorMsg(errorMsgMap));
			return dto;
		}
		String resultJson=houseIssuePcService.issueHouse(param.toString());
		dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		//发布房源成功录入商机和判断是否更新成房东
		if(dto.getCode()==DataTransferObject.SUCCESS){
			Integer operateSeq=SOAResParseUtil.getIntFromDataByKey(resultJson, "operateSeq");
			if(HouseIssueStepEnum.SEVEN.getCode() == operateSeq.intValue()){
				//发布房源录入商机
				houseBusinessInput(houseFid,uid);
			}
		}
		return dto;
	}
	
	/**
	 * by类型校验是否上传照片且照片数量达到最少限制数量
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param houseBaseFid
	 * @param rentWay
	 * @param roomFids
	 * @param errorMsgMap 
	 * @throws SOAParseException 
	 * @return
	 */
	private boolean validatePicNumByType(String houseBaseFid, Integer rentWay, List<String> roomFids, Map<Integer, String> errorMsgMap) throws SOAParseException {
		String picJson = houseIssueService.searchHousePicMsgListByHouseFid(houseBaseFid);
		String houseBaseJsonString=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJsonString, "obj", HouseBaseMsgEntity.class);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(picJson);
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			//不算的照片数量
			int subNum=0;
			List<HousePicMsgEntity> picList = SOAResParseUtil.getListValueFromDataByKey(picJson, "list", HousePicMsgEntity.class);
			Map<String, List<HousePicMsgEntity>> wsMap = new HashMap<String, List<HousePicMsgEntity>>();
			List<HousePicMsgEntity> ktList = new ArrayList<HousePicMsgEntity>();
			List<HousePicMsgEntity> cfList = new ArrayList<HousePicMsgEntity>();
			List<HousePicMsgEntity> wsjList = new ArrayList<HousePicMsgEntity>();
			List<HousePicMsgEntity> swList = new ArrayList<HousePicMsgEntity>();
			for (HousePicMsgEntity housePicMsgEntity : picList) {
				Integer picType = housePicMsgEntity.getPicType();
				if (!Check.NuNObj(picType)) {
					if (picType.intValue() == HousePicTypeEnum.WS.getCode()) {
						this.filterRoomPic(wsMap, houseBaseFid, rentWay, roomFids, housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.KT.getCode()) {
						ktList.add(housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.CF.getCode()) {
						cfList.add(housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.WSJ.getCode()) {
						wsjList.add(housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.SW.getCode()) {
						swList.add(housePicMsgEntity);
					}
				}
			}
			
			boolean flag = true;
			int totalPicNum = 0;// 整套出租:所有图片 独立房间: 房间图片 + 公区图片
			int ktPicNum = ktList.size();
			//如果客厅为0 不进行判断
			if(houseBaseMsgEntity.getHallNum()!=0){
				totalPicNum += ktPicNum;
				if (ktPicNum < HousePicTypeEnum.KT.getMin()) {
					flag = false;
					errorMsgMap.put(HousePicTypeEnum.KT.getCode(), "客厅至少" + HousePicTypeEnum.KT.getMin() + "张照片");
				}
			} else {
				subNum+=HousePicTypeEnum.KT.getMin();
			}
			int cfPicNum = cfList.size();
			totalPicNum += cfPicNum;
            if (houseBaseMsgEntity.getKitchenNum() != 0) {
                totalPicNum += cfPicNum;
                if (cfPicNum < HousePicTypeEnum.CF.getMin()) {
                    flag = false;
                    errorMsgMap.put(HousePicTypeEnum.CF.getCode(), "厨房至少" + HousePicTypeEnum.CF.getMin() + "张照片");
                }
            } else {
                subNum += HousePicTypeEnum.CF.getMin();
            }
			int wsjPicNum = wsjList.size();
			//卫生间为0 不进行判断
			if(houseBaseMsgEntity.getToiletNum()!=0){
				totalPicNum += wsjPicNum;
				if (wsjPicNum < HousePicTypeEnum.WSJ.getMin()) {
					flag = false;
					errorMsgMap.put(HousePicTypeEnum.WSJ.getCode(), "卫生间至少" + HousePicTypeEnum.WSJ.getMin() + "张照片");
				}
			} else {
				subNum+=HousePicTypeEnum.WSJ.getMin();
			}
			int swPicNum = swList.size();
			totalPicNum += swPicNum;
			if (swPicNum < HousePicTypeEnum.SW.getMin()) {
				flag = false;
				errorMsgMap.put(HousePicTypeEnum.SW.getCode(), "室外至少" + HousePicTypeEnum.SW.getMin() + "张照片");
			}
			if (Check.NuNMap(wsMap)) {
				flag = false;
				errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
			}
			if (rentWay == RentWayEnum.HOUSE.getCode()) {
				for (Entry<String, List<HousePicMsgEntity>> entry : wsMap.entrySet()) {
					int wsPicNum = entry.getValue().size();
					totalPicNum += wsPicNum;
					if (wsPicNum < HousePicTypeEnum.WS.getMin()) {
						flag = false;
						errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
					}
				}
				if (totalPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)) {
					flag = false;
					errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)+ "张");
				}
			} else if (rentWay == RentWayEnum.ROOM.getCode()) {
				for (Entry<String, List<HousePicMsgEntity>> entry : wsMap.entrySet()) {
					int wsPicNum = entry.getValue().size();
					if (wsPicNum < HousePicTypeEnum.WS.getMin()) {
						flag = false;
						errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
					}
					if (totalPicNum + wsPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)) {
						flag = false;
						errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum) + "张");
					}
				}
			}
			return flag;
		}
		return false;
	}

	/**
	 * 筛选指定房间照片
	 *
	 * @author liujun
	 * @created 2017年3月2日
	 *
	 * @param wsMap
	 * @param houseBaseFid 
	 * @param rentWay
	 * @param roomFids 
	 * @param housePicMsgEntity
	 * @throws SOAParseException 
	 */
	private void filterRoomPic(Map<String, List<HousePicMsgEntity>> wsMap, String houseBaseFid, Integer rentWay, List<String> roomFids,
			HousePicMsgEntity housePicMsgEntity) throws SOAParseException {
		if (!Check.NuNStrStrict(housePicMsgEntity.getRoomFid())) {
			if (rentWay == RentWayEnum.HOUSE.getCode() || (rentWay == RentWayEnum.ROOM.getCode() && Check.NuNCollection(roomFids))) {
				if (Check.NuNMap(wsMap)) {
					String resultJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
					if (dto.getCode() == DataTransferObject.SUCCESS) {
						List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomMsgEntity.class);
						for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
							List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
							wsMap.put(houseRoomMsgEntity.getFid(), wsList);
						}
					}
				}
				if (wsMap.containsKey(housePicMsgEntity.getRoomFid())) {
					List<HousePicMsgEntity> wsList = wsMap.get(housePicMsgEntity.getRoomFid());
					wsList.add(housePicMsgEntity);
				}
			} else if (rentWay == RentWayEnum.ROOM.getCode() && !Check.NuNCollection(roomFids)) {
				if (Check.NuNMap(wsMap)) {
					for (String roomFid : roomFids) {
						List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
						wsMap.put(roomFid, wsList);
					}
				}
				if (wsMap.containsKey(housePicMsgEntity.getRoomFid())) {
					List<HousePicMsgEntity> wsList = wsMap.get(housePicMsgEntity.getRoomFid());
					wsList.add(housePicMsgEntity);
				}							
			}
		}
	}

	/**
	 * 拼接错误信息字符串
	 *
	 * @author liujun
	 * @created 2017年3月10日
	 * @param errorMsgMap 
	 *
	 * @return
	 */
	private String assembleErrorMsg(Map<Integer, String> errorMsgMap) {
		int temp = 0;
		StringBuilder sb = new StringBuilder();
		for (String errorMsg : errorMsgMap.values()) {
			if (temp == 0) {
				sb.append(errorMsg);
			} else {
				sb.append("、").append(errorMsg);
			}
			temp++;
		}
		return sb.toString();
	}

	/**
	 * 
	 * 房源发布成功页
	 * 如果为合租的话，拼接roomFid - 连接，显示房间列表
	 *
	 * @author jixd
	 * @created 2016年8月18日 下午9:33:03
	 *
	 * @return
	 */
	@RequestMapping("/houseIssue/success/{houseFid}")
	public String releaseHouseSuccess(@PathVariable String houseFid, String roomFid,String rfids,Model model){
		List<HouseRoomBaseMsg> list = new ArrayList<>();
		
		//房源fid获取房源或者房间集合
		String hrList = houseIssuePcService.getHouseRoomBaseMsgList(houseFid);
		DataTransferObject jsonList = JsonEntityTransform.json2DataTransferObject(hrList);
		if(jsonList.getCode() == DataTransferObject.SUCCESS){
			list = jsonList.parseData("list",new TypeReference<List<HouseRoomBaseMsg>>(){});
			if(!Check.NuNStr(rfids)){
				List<HouseRoomBaseMsg> newList = new ArrayList<>();
				//如果为分租的话 ，先移除
				String[] roomfids = rfids.split("-");
				for(HouseRoomBaseMsg msg : list){
					for(String fid : roomfids){
						if(msg.getRoomFid().equals(fid)){
							newList.add(msg);
						}
					}
				}
				list = newList;
			}
			
			for(HouseRoomBaseMsg msg : list){
				String fullPic = pic_default_house_url;
				HousePicMsgEntity picMsgEntity = msg.getPicMsgEntity();
				if(!Check.NuNObj(picMsgEntity)){
					fullPic = PicUtil.getFullPic(picBaseAddrMona, picMsgEntity.getPicBaseUrl(), picMsgEntity.getPicSuffix(), pic_size_375_281);
				}
				msg.setUrl(fullPic);
				msg.setRentWayName(RentWayEnum.getRentWayByCode(msg.getRentWay()).getName());
			}
			
			model.addAttribute("list", list);
		}
		
		model.addAttribute("houseFid", houseFid);
		model.addAttribute("roomFid", roomFid);
		return "houseIssue/houseReleaseSuccess";
	}
	
	
	/**
	 * 
	 * 是否发布状态
	 *
	 * @author jixd
	 * @created 2016年8月27日 上午11:28:41
	 *
	 * @param code
	 */
	private boolean isIssueStatus(int status){
		return status == HouseStatusEnum.DFB.getCode() || status == HouseStatusEnum.XXSHWTG.getCode() 
				|| status == HouseStatusEnum.ZPSHWTG.getCode() || status == HouseStatusEnum.XJ.getCode() 
				|| status == HouseStatusEnum.QZXJ.getCode();
	}
	
	
	/**
	 * 
	 * 是否可以修改
	 *
	 * @author jixd
	 * @created 2016年8月31日 上午11:53:00
	 *
	 * @param status
	 * @return
	 */
	private boolean isModify(int status) {
		return status == HouseStatusEnum.DFB.getCode() || status == HouseStatusEnum.XXSHWTG.getCode()
				|| status ==HouseStatusEnum.ZPSHWTG.getCode() || status == HouseStatusEnum.XJ.getCode() || status == HouseStatusEnum.QZXJ.getCode();
	}
	
	
	/**
	 * 发布房源添加商机
	 *
	 * @author bushujie
	 * @created 2016年7月11日 下午10:35:04
	 *
	 * @param landlordUid
	 * @param houseBaseFid
	 * @throws SOAParseException
	 */
	private void houseBusinessInput(String houseBaseFid,String landlordUid) throws SOAParseException{
		String custJson=customerInfoService.getCustomerInfoByUid(landlordUid);
		CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(custJson, "customerBase", CustomerBaseMsgEntity.class);
		if(!Check.NuNObj(customer)&&!Check.NuNStr(customer.getCustomerMobile())){
			// 屏蔽商机录入 modified by liujun 2017-02-24
			/*HouseBusinessMsgExtDto dto=new HouseBusinessMsgExtDto();
			dto.setLandlordMobile(customer.getCustomerMobile());
			String businessJson=houseBusinessService.findHouseBusExtByCondition(JsonEntityTransform.Object2Json(dto));
			List<HouseBusinessMsgExtEntity> listExtEntities=SOAResParseUtil.getListValueFromDataByKey(businessJson, "listExtEntities", HouseBusinessMsgExtEntity.class);
			String houseBaseJson=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
			HouseBaseMsgEntity houseBase=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseMsgEntity.class);
			String housePhyJson=houseIssueService.searchHousePhyMsgByHouseBaseFid(houseBaseFid);
			HousePhyMsgEntity housePhy=SOAResParseUtil.getValueFromDataByKey(housePhyJson, "obj", HousePhyMsgEntity.class);
			if(!Check.NuNCollection(listExtEntities)){
				HouseBusinessInputDto inputDto=new HouseBusinessInputDto();
				//商机基本信息
				inputDto.getBusinessMsg().setNationCode(housePhy.getNationCode());
				inputDto.getBusinessMsg().setProvinceCode(housePhy.getProvinceCode());
				inputDto.getBusinessMsg().setCityCode(housePhy.getCityCode());
				inputDto.getBusinessMsg().setAreaCode(housePhy.getAreaCode());
				inputDto.getBusinessMsg().setCommunityName(housePhy.getCommunityName());
				inputDto.getBusinessMsg().setHouseAddr(houseBase.getHouseAddr());
				inputDto.getBusinessMsg().setHouseBaseFid(houseBaseFid);
				inputDto.getBusinessMsg().setRentWay(houseBase.getRentWay());
				inputDto.getBusinessMsg().setCreateFid(landlordUid);
				//商机来源信息
				inputDto.getBusinessSource().setBusniessSource(1);
				inputDto.getBusinessSource().setIsJobArea(1);
				inputDto.getBusinessSource().setCreateFid(landlordUid);
				//商机扩展信息
				inputDto.getBusinessExt().setLandlordName(listExtEntities.get(0).getLandlordName());
				inputDto.getBusinessExt().setLandlordMobile(listExtEntities.get(0).getLandlordMobile());
				inputDto.getBusinessExt().setLandlordNickName(listExtEntities.get(0).getLandlordNickName());
				inputDto.getBusinessExt().setLandlordQq(listExtEntities.get(0).getLandlordQq());
				inputDto.getBusinessExt().setLandlordType(listExtEntities.get(0).getLandlordType());
				inputDto.getBusinessExt().setLandlordEmail(listExtEntities.get(0).getLandlordEmail());
				inputDto.getBusinessExt().setLandlordWechat(listExtEntities.get(0).getLandlordWechat());
				inputDto.getBusinessExt().setDtGuardCode(listExtEntities.get(0).getDtGuardCode());
				inputDto.getBusinessExt().setDtGuardName(listExtEntities.get(0).getDtGuardName());
				inputDto.getBusinessExt().setManagerCode(listExtEntities.get(0).getManagerCode());
				inputDto.getBusinessExt().setManagerName(listExtEntities.get(0).getManagerName());
				inputDto.getBusinessExt().setIsMeet(listExtEntities.get(0).getIsMeet());
				inputDto.getBusinessExt().setBusinessPlan(3);
				houseBusinessService.insertHouseBusiness(JsonEntityTransform.Object2Json(inputDto));
			}*/
			//判断是否更新房东标示
			if(customer.getIsLandlord()!=1){
			    CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
				customerBaseMsgEntity.setUid(landlordUid);
				customerBaseMsgEntity.setIsLandlord(1);
				//设置客户为房东
				customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
			}
		}
	}
	
	
}
