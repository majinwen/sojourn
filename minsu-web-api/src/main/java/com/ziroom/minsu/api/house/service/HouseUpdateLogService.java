/**
 * @FileName: HouseUpdateLogService.java
 * @Package com.ziroom.minsu.api.house.service
 * 
 * @author yd
 * @created 2017年7月11日 上午10:07:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.house.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.DecimalCalculate;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.house.api.inner.HouseUpdateHistoryLogService;
import com.ziroom.minsu.services.house.dto.AssembleRoomMsgDto;
import com.ziroom.minsu.services.house.dto.CancellationPolicyDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseDescAndConfDto;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.dto.WaitUpdateHouseInfoDto;
import com.ziroom.minsu.services.house.entity.RoomMsgVo;
import com.ziroom.minsu.services.house.issue.dto.HouseCheckInMsgDto;
import com.ziroom.minsu.services.house.issue.dto.HouseDescAndBaseInfoDto;
import com.ziroom.minsu.services.house.issue.dto.HousePriceDto;
import com.ziroom.minsu.services.house.issue.dto.HouseTypeLocationDto;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

/**
 * <p>房源 修改记录 保存日志</p>
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
@Service("api.houseUpdateLogService")
public class HouseUpdateLogService extends AbstractController{

	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseUpdateLogService.class);

	
	@Resource(name = "house.houseUpdateHistoryLogService")
	private HouseUpdateHistoryLogService houseUpdateHistoryLogService;
	

	/**
	 * 
	 * 查询 待修改的房源历史信息
	 *
	 * @author yd
	 * @created 2017年7月7日 下午2:42:37
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public HouseUpdateHistoryLogDto findWaitUpdateHouseInfo(String houseFid,String roomFid,Integer rentWay){

		if(Check.NuNStr(houseFid)){
			LogUtil.info(LOGGER, "【房源修改记录保存-原房源数据查询】参数错误houseFid={},rentWay={}", houseFid,rentWay);
			return null;
		}

		WaitUpdateHouseInfoDto waitUpdateHouseInfo = new WaitUpdateHouseInfoDto();
		waitUpdateHouseInfo.setHouseFid(houseFid);
		waitUpdateHouseInfo.setRentWay(rentWay);
		waitUpdateHouseInfo.setRoomFid(roomFid);
		DataTransferObject   dto = JsonEntityTransform.json2DataTransferObject(this.houseUpdateHistoryLogService.findWaitUpdateHouseInfo(JsonEntityTransform.Object2Json(waitUpdateHouseInfo)));


		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "【房源修改记录保存-原房源数据查询失败】msg={},houseFid={},roomFid={},rentWay={}", dto.getMsg(),houseFid,roomFid,rentWay);
			return null;
		}
		HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = dto.parseData("houseUpdateHistoryLogDto", new TypeReference<HouseUpdateHistoryLogDto>() {
		});
		if(!Check.NuNObj(houseUpdateHistoryLogDto)){
			houseUpdateHistoryLogDto.setHouseFid(houseFid);
			houseUpdateHistoryLogDto.setRentWay(rentWay);
			houseUpdateHistoryLogDto.setRoomFid(roomFid);
		}
		return houseUpdateHistoryLogDto;
	}
	
	/**
	 * 
	 * 保存房源 位置信息 修改记录
	 *
	 * @author yd
	 * @created 2017年7月6日 下午3:41:42
	 *
	 * @param houseTypeLocationDto
	 */
	public void saveHistoryLog(HouseTypeLocationDto houseTypeLocationDto,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {
			if(!Check.NuNObjs(houseTypeLocationDto,houseUpdateHistoryLogDto)&&!Check.NuNStr(houseTypeLocationDto.getHouseBaseFid())){
				//详细地址组合
				StringBuilder houseAddr=new StringBuilder();
				String[] regionNames=houseTypeLocationDto.getRegionName().split(",");
				String[] regionCode = houseTypeLocationDto.getRegionCode().split(",");
				if(regionCode[0].equals("100000")){
					if(regionNames.length>3){
						houseAddr.append(regionNames[2]).append(regionNames[3]);
					} else {
						houseAddr.append(regionNames[1]).append(regionNames[2]);
					}
				} else {
					houseAddr.append(regionNames[1]);
				}
				houseAddr.append(houseTypeLocationDto.getHouseStreet());
				if(!Check.NuNStr(houseTypeLocationDto.getCommunityName())){
					houseAddr.append(houseTypeLocationDto.getCommunityName());
				}
				if(!Check.NuNStr(houseTypeLocationDto.getHouseNumber())){
					houseAddr.append(" ").append(houseTypeLocationDto.getHouseNumber());
				}
				//房源基础信息
				HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
				houseBaseMsgEntity.setRentWay(houseTypeLocationDto.getRentWay());
				houseBaseMsgEntity.setHouseType(houseTypeLocationDto.getHouseType());
				houseBaseMsgEntity.setHouseAddr(houseAddr.toString());
				//房源物理信息
				HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
				housePhyMsgEntity.setNationCode(houseTypeLocationDto.getRegionCode().split(",")[0]);
				housePhyMsgEntity.setProvinceCode(houseTypeLocationDto.getRegionCode().split(",")[1]);
				housePhyMsgEntity.setCityCode(houseTypeLocationDto.getRegionCode().split(",")[2]);
				housePhyMsgEntity.setAreaCode(houseTypeLocationDto.getRegionCode().split(",")[3]);
				housePhyMsgEntity.setCommunityName(houseTypeLocationDto.getCommunityName());
				// 如果是谷歌地图，保存时也修改百度坐标
				if(!Check.NuNObjs(houseTypeLocationDto.getGoogleLatitude(),houseTypeLocationDto.getGoogleLongitude()) && Check.NuNObjs(houseTypeLocationDto.getLongitude(), houseTypeLocationDto.getLatitude())){
					Gps baiduGps = CoordinateTransforUtils.wgs84_To_bd09(houseTypeLocationDto.getGoogleLatitude(), houseTypeLocationDto.getGoogleLongitude());
					housePhyMsgEntity.setGoogleLatitude(houseTypeLocationDto.getGoogleLatitude());
					housePhyMsgEntity.setGoogleLongitude(houseTypeLocationDto.getGoogleLongitude());
					housePhyMsgEntity.setLatitude(baiduGps.getWgLat());
					housePhyMsgEntity.setLongitude(baiduGps.getWgLon());
				}
				// 如果是百度地图，保存时也修改谷歌坐标
				if(Check.NuNObjs(houseTypeLocationDto.getGoogleLatitude(),houseTypeLocationDto.getGoogleLongitude()) && !Check.NuNObjs(houseTypeLocationDto.getLongitude(), houseTypeLocationDto.getLatitude())){
					Gps googleGps = CoordinateTransforUtils.bd09_To_Gps84(houseTypeLocationDto.getLatitude(), houseTypeLocationDto.getLongitude());
					housePhyMsgEntity.setLatitude(houseTypeLocationDto.getLatitude());
					housePhyMsgEntity.setLongitude(houseTypeLocationDto.getLongitude());
					housePhyMsgEntity.setGoogleLatitude(googleGps.getWgLat());
					housePhyMsgEntity.setGoogleLongitude(googleGps.getWgLon());
				}
				//房源扩展信息
				HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
				houseBaseExtEntity.setHouseStreet(houseTypeLocationDto.getHouseStreet());
				houseBaseExtEntity.setDetailAddress(houseTypeLocationDto.getHouseNumber());
				//更新房源基础信息
				houseBaseMsgEntity.setFid(houseTypeLocationDto.getHouseBaseFid());
				//更新房源扩展信息
				houseBaseExtEntity.setHouseBaseFid(houseTypeLocationDto.getHouseBaseFid());

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBaseMsgEntity);
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
				houseUpdateHistoryLogDto.setHousePhyMsg(housePhyMsgEntity);
				houseUpdateHistoryLogDto.setSourceType(houseTypeLocationDto.getHouseSource());
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(houseTypeLocationDto.getLandlordUid());
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【类型和位置保存-保存修改记录】返回结果dtoJson={},houseBaseFid={}", dtoJson,houseTypeLocationDto.getHouseBaseFid());
			}
		} catch (Exception e) {
			LogUtil.info(LOGGER, "【类型和位置保存-保存修改记录异常】e={}",e);
		}
	}
	

	/**
	 * 
	 * 保存退订政策数据 保存修改记录
	 *
	 * @author yd
	 * @created 2017年7月6日 下午7:57:13
	 *
	 * @param requestDto
	 */
	public void saveHistoryLog(CancellationPolicyDto requestDto,HttpServletRequest request,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){
		try {

			if(!Check.NuNObj(requestDto)&&!Check.NuNStr(requestDto.getHouseBaseFid())
					&&!Check.NuNObjs(requestDto.getRentWay(),houseUpdateHistoryLogDto)){

				HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
				houseBase.setFid(requestDto.getHouseBaseFid());
				houseBase.setRentWay(requestDto.getRentWay());
				HouseBaseExtEntity houseBaseExt = null;
				HouseRoomMsgEntity houseRoomMsg = null;
				HouseRoomExtEntity  houseRoomExt =  null;

				int rentWay  = requestDto.getRentWay();
				RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);

				if(rentWayEnum == null||rentWayEnum.getCode()==RentWayEnum.BED.getCode()) return ;

				//整租保存 修改记录
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					houseBaseExt = new HouseBaseExtEntity();
					houseBaseExt.setCheckOutRulesCode(requestDto.getCancellationPolicy());
					houseBaseExt.setOrderType(requestDto.getOrderType());
					HouseDescEntity houseDesc = new HouseDescEntity();
					houseDesc.setHouseRules(requestDto.getHouseRules());
					houseUpdateHistoryLogDto.setHouseDesc(houseDesc);
				}

				//分租保存 修改记录
				if(rentWay == RentWayEnum.ROOM.getCode()){
					houseRoomExt = new HouseRoomExtEntity();
					houseRoomExt.setOrderType(requestDto.getOrderType());
					houseRoomExt.setCheckOutRulesCode(requestDto.getCancellationPolicy());
					houseRoomExt.setRoomRules(requestDto.getHouseRules());
					houseRoomMsg = new HouseRoomMsgEntity();
					houseRoomMsg.setFid(requestDto.getRoomFid());
				}

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBase);
				houseUpdateHistoryLogDto.setHouseRoomMsg(houseRoomMsg);
				houseUpdateHistoryLogDto.setHouseRoomExt(houseRoomExt);
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExt);
				houseUpdateHistoryLogDto.setSourceType(getUserSourceType(request));
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(getUserId(request));
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【保存退订政策数据 -保存修改记录】返回结果dtoJson={},roomFid={},houseBaseFid={}", dtoJson,requestDto.getHouseBaseFid(),requestDto.getRoomFid());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【房源修改记录保存-保存退订政策数据 】异常，e= {}", e);
		}
	}
	
	
	/**
	 * 
	 * 保存入住信息 修改记录
	 *
	 * @author yd
	 * @created 2017年7月6日 下午8:44:13
	 *
	 * @param requestDto
	 * @param request
	 */
	public  void saveHistoryLog(HouseCheckInMsgDto requestDto,HttpServletRequest request,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){

		try {

			if(!Check.NuNObj(requestDto)&&!Check.NuNStr(requestDto.getHouseBaseFid())
					&&!Check.NuNObjs(requestDto.getRentWay(),houseUpdateHistoryLogDto,request)){

				HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
				houseBase.setFid(requestDto.getHouseBaseFid());
				houseBase.setRentWay(requestDto.getRentWay());
				HouseBaseExtEntity houseBaseExtEntity = null;
				HouseRoomMsgEntity houseRoomMsg = null;
				HouseRoomExtEntity  houseRoomExtEntity =  null;

				int rentWay  = requestDto.getRentWay();
				RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);

				if(rentWayEnum == null||rentWayEnum.getCode()==RentWayEnum.BED.getCode()) return ;

				if (rentWay == RentWayEnum.HOUSE.getCode()){
					houseBaseExtEntity = new HouseBaseExtEntity();
					houseBaseExtEntity.setHouseBaseFid(requestDto.getHouseBaseFid());
					houseBaseExtEntity.setMinDay(requestDto.getMinDay());
					houseBaseExtEntity.setCheckInTime(requestDto.getCheckInTime());
					houseBaseExtEntity.setCheckOutTime(requestDto.getCheckOutTime());

				}

				if (rentWay == RentWayEnum.ROOM.getCode()){
					houseRoomMsg = new HouseRoomMsgEntity();
					houseRoomMsg.setFid(requestDto.getRoomFid());
					houseRoomExtEntity = new HouseRoomExtEntity();
					houseRoomExtEntity.setRoomFid(requestDto.getRoomFid());
					houseRoomExtEntity.setMinDay(requestDto.getMinDay());
					houseRoomExtEntity.setCheckInTime(requestDto.getCheckInTime());
					houseRoomExtEntity.setCheckOutTime(requestDto.getCheckOutTime());
				}

				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBase);
				houseUpdateHistoryLogDto.setHouseRoomMsg(houseRoomMsg);
				houseUpdateHistoryLogDto.setHouseRoomExt(houseRoomExtEntity);
				houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
				houseUpdateHistoryLogDto.setSourceType(getUserSourceType(request));
				houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
				houseUpdateHistoryLogDto.setCreaterFid(getUserId(request));
				String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
				LogUtil.info(LOGGER, "【保存退订政策数据 -保存入住信息】返回结果dtoJson={},houseBaseFid={},roomFid={}", dtoJson,requestDto.getHouseBaseFid(),requestDto.getRoomFid());
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【房源修改记录保存-保存入住信息 】异常，e= {}", e);
		}

	}
	
	

	/**
	 * 
	 * 修改房源保存修改记录：房源管理时描述及基础信息保存（整租）
	 *
	 * @author yd
	 * @created 2017年7月10日 下午2:16:36
	 *
	 * @param houseDescAndBaseInfoDto
	 * @param request
	 * @param houseUpdateHistoryLogDto
	 */
	public  void saveHistoryLog(HouseDescAndBaseInfoDto houseDescAndBaseInfoDto ,HttpServletRequest request,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){

		if(!Check.NuNObjs(houseDescAndBaseInfoDto,houseUpdateHistoryLogDto,request)&&!Check.NuNObj(houseDescAndBaseInfoDto.getRentWay())
				&&!Check.NuNStr(houseDescAndBaseInfoDto.getHouseBaseFid())){

			HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
			houseBase.setFid(houseDescAndBaseInfoDto.getHouseBaseFid());
			houseBase.setRentWay(houseDescAndBaseInfoDto.getRentWay());
			houseBase.setHouseArea(houseDescAndBaseInfoDto.getHouseArea());
			houseBase.setHouseName(houseDescAndBaseInfoDto.getHouseName());

			//处理户型 卧室,客厅,卫生间,厨房,阳台
			if(!Check.NuNStr(houseDescAndBaseInfoDto.getHouseModel())){
				List<String> modelList=Arrays.asList(houseDescAndBaseInfoDto.getHouseModel().split(","));
				houseBase.setRoomNum(Integer.valueOf(modelList.get(0)));
				houseBase.setHallNum(Integer.valueOf(modelList.get(1)));
				houseBase.setToiletNum(Integer.valueOf(modelList.get(2)));
				houseBase.setKitchenNum(Integer.valueOf(modelList.get(3)));
				houseBase.setBalconyNum(Integer.valueOf(modelList.get(4)));
			}

			HouseBaseExtEntity houseBaseExtEntity = null;
			HouseDescEntity desc  = null;
			int rentWay  = houseDescAndBaseInfoDto.getRentWay();
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);

			if(rentWayEnum == null||rentWayEnum.getCode()==RentWayEnum.BED.getCode()) return ;
			if (rentWay == RentWayEnum.HOUSE.getCode()){
				houseBaseExtEntity = new HouseBaseExtEntity();
				houseBaseExtEntity.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
				houseBaseExtEntity.setCheckInLimit(houseDescAndBaseInfoDto.getCheckInLimit());
				desc = new HouseDescEntity();
				desc.setHouseDesc(houseDescAndBaseInfoDto.getHouseDesc());
				desc.setHouseAroundDesc(houseDescAndBaseInfoDto.getHouseAroundDesc());
			}

			houseUpdateHistoryLogDto.setHouseBaseMsg(houseBase);
			houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
			houseUpdateHistoryLogDto.setHouseDesc(desc);
			houseUpdateHistoryLogDto.setSourceType(getUserSourceType(request));
			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
			houseUpdateHistoryLogDto.setCreaterFid(getUserId(request));
			String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			LogUtil.info(LOGGER, "【房源管理时描述及基础信息保存（整租） -保存修改记录】返回结果dtoJson={},roomFid={},houseBaseFid={}", dtoJson,houseDescAndBaseInfoDto.getHouseBaseFid(),houseDescAndBaseInfoDto.getRoomFid());

		}

	}


	/**
	 * 
	 * 修改房源保存修改记录：房源管理时描述及基础信息保存（分租）
	 *
	 * @author yd
	 * @created 2017年7月10日 下午2:16:36
	 *
	 * @param houseDescAndBaseInfoDto
	 * @param request
	 * @param houseUpdateHistoryLogDto
	 */
	public  void saveHistoryLog(HouseDescAndConfDto houseDescAndConfDto ,HttpServletRequest request,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto,String roomFid){

		if(!Check.NuNObjs(houseDescAndConfDto,houseUpdateHistoryLogDto,request)&&!Check.NuNObj(houseDescAndConfDto.getHouseBaseExtDto())
				&&!Check.NuNStr(houseDescAndConfDto.getHouseBaseExtDto().getFid())&&!Check.NuNObj(houseDescAndConfDto.getHouseBaseExtDto().getRentWay())){


			int rentWay  = houseDescAndConfDto.getHouseBaseExtDto().getRentWay();
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);
			if(rentWayEnum == null||rentWayEnum.getCode()==RentWayEnum.BED.getCode()) return ;


			HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
			houseBase.setFid(houseDescAndConfDto.getHouseBaseExtDto().getFid());
			houseBase.setRentWay(houseDescAndConfDto.getHouseBaseExtDto().getRentWay());
			houseBase.setHouseArea(houseDescAndConfDto.getHouseBaseExtDto().getHouseArea());
			houseBase.setBalconyNum(houseDescAndConfDto.getHouseBaseExtDto().getBalconyNum());
			houseBase.setHallNum(houseDescAndConfDto.getHouseBaseExtDto().getHallNum());
			houseBase.setKitchenNum(houseDescAndConfDto.getHouseBaseExtDto().getKitchenNum());
			houseBase.setRoomNum(houseDescAndConfDto.getHouseBaseExtDto().getRoomNum());
			houseBase.setToiletNum(houseDescAndConfDto.getHouseBaseExtDto().getToiletNum());


			HouseBaseExtEntity houseBaseExtEntity = null;
			HouseDescEntity desc  = null;


			houseBaseExtEntity = new HouseBaseExtEntity();
			houseBaseExtEntity.setHouseBaseFid(houseDescAndConfDto.getHouseBaseExtDto().getFid());
			if(!Check.NuNObj(houseDescAndConfDto.getHouseBaseExtDto())){
				houseBaseExtEntity.setIsTogetherLandlord(houseDescAndConfDto.getHouseBaseExtDto().getHouseBaseExt().getIsTogetherLandlord());
			}
			
			if(!Check.NuNObj(houseDescAndConfDto.getHouseBaseDetailVo())){
				desc = new HouseDescEntity();
				desc.setHouseDesc(houseDescAndConfDto.getHouseBaseDetailVo().getHouseDesc());
				desc.setHouseAroundDesc(houseDescAndConfDto.getHouseBaseDetailVo().getHouseAroundDesc());
			}
			

			houseUpdateHistoryLogDto.setHouseBaseMsg(houseBase);
			houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
			houseUpdateHistoryLogDto.setHouseDesc(desc);
			houseUpdateHistoryLogDto.setSourceType(getUserSourceType(request));
			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
			houseUpdateHistoryLogDto.setCreaterFid(getUserId(request));
			String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			LogUtil.info(LOGGER, "【房源管理时描述及基础信息保存（分租） -保存修改记录】返回结果dtoJson={},roomFid={},houseBaseFid={}", dtoJson,houseDescAndConfDto.getHouseBaseExtDto().getFid(),roomFid);

		}

	}
	
	
	
	/**
	 * 
	 * 分租第二步2-3合租单间信息保存  ： 保存修改日志
	 *
	 * @author yd
	 * @created 2017年7月10日 下午2:16:36
	 *
	 * @param houseDescAndBaseInfoDto
	 * @param request
	 * @param houseUpdateHistoryLogDto
	 */
	public  void saveHistoryLog(AssembleRoomMsgDto assembleRoomMsgDto ,HttpServletRequest request,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto,HouseBaseParamsDto requestDto ){

		if(!Check.NuNObjs(requestDto,houseUpdateHistoryLogDto,request,assembleRoomMsgDto)
				&&!Check.NuNStr(requestDto.getHouseBaseFid())&&!Check.NuNObj(requestDto.getRentWay())){

			int rentWay  = requestDto.getRentWay();
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);
			if(rentWayEnum == null||rentWayEnum.getCode()==RentWayEnum.BED.getCode()) return ;


			HouseBaseMsgEntity houseBase = assembleRoomMsgDto.getHouseBaseExtDto();
			HouseBaseExtEntity houseBaseExtEntity = null;

			if(!Check.NuNObj(assembleRoomMsgDto.getHouseBaseExtDto())){
				houseBaseExtEntity = assembleRoomMsgDto.getHouseBaseExtDto().getHouseBaseExt();
			}

			HouseRoomMsgEntity  houseRoomMsg = null;
			RoomMsgVo roomMsgVo = assembleRoomMsgDto.getRoomMsgVo();
			if(!Check.NuNObj(roomMsgVo)){
				if(!Check.NuNObj(assembleRoomMsgDto.getRoomMsgVo().getRoomPrice())){
					assembleRoomMsgDto.getRoomMsgVo().setRoomPrice(assembleRoomMsgDto.getRoomMsgVo().getRoomPrice().intValue()*100);  //转化为分
				}
				if(!Check.NuNObj(assembleRoomMsgDto.getRoomMsgVo().getRoomCleaningFees())){
					assembleRoomMsgDto.getRoomMsgVo().setRoomCleaningFees(assembleRoomMsgDto.getRoomMsgVo().getRoomCleaningFees().intValue()*100);  //转化为分
				}
				houseRoomMsg = roomMsgVo;
			}

			houseUpdateHistoryLogDto.setHouseBaseMsg(houseBase);
			houseUpdateHistoryLogDto.setHouseBaseExt(houseBaseExtEntity);
			houseUpdateHistoryLogDto.setHouseRoomMsg(houseRoomMsg);
			houseUpdateHistoryLogDto.setSourceType(getUserSourceType(request));
			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
			houseUpdateHistoryLogDto.setCreaterFid(getUserId(request));
			String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			LogUtil.info(LOGGER, "【分租第二步2-3合租单间信息保存 -保存修改记录】返回结果dtoJson={},roomFid={},houseBaseFid={}", dtoJson,requestDto.getRoomFid(),requestDto.getHouseBaseFid());

		}

	}
	
	/**
	 * 
	 * 修改价格保存日志
	 *
	 * @author bushujie
	 * @created 2017年9月25日 下午6:05:40
	 *
	 * @param housePriceDto
	 * @param request
	 * @param houseUpdateHistoryLogDto
	 */
	public  void saveHistoryLog(HousePriceDto housePriceDto ,HttpServletRequest request,HouseUpdateHistoryLogDto houseUpdateHistoryLogDto){

		if(!Check.NuNObjs(houseUpdateHistoryLogDto,request,housePriceDto)
				&&!Check.NuNStr(housePriceDto.getHouseBaseFid())&&!Check.NuNObj(housePriceDto.getRentWay())){

			int rentWay  = housePriceDto.getRentWay();
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);
			if(rentWayEnum == null||rentWayEnum.getCode()==RentWayEnum.BED.getCode()) return ;
			
			if(RentWayEnum.HOUSE.getCode()==rentWay){
				HouseBaseMsgEntity houseBase=new HouseBaseMsgEntity();
				houseBase.setLeasePrice(DecimalCalculate.mul(housePriceDto.getLeasePrice().toString(), "100").intValue());
				houseBase.setHouseCleaningFees(DecimalCalculate.mul(housePriceDto.getCleaningFees().toString(), "100").intValue());
				houseUpdateHistoryLogDto.setHouseBaseMsg(houseBase);
				houseUpdateHistoryLogDto.setHouseFid(housePriceDto.getHouseBaseFid());
				houseUpdateHistoryLogDto.setRentWay(housePriceDto.getRentWay());
				houseUpdateHistoryLogDto.setRoomFid(null);
			} else if(RentWayEnum.ROOM.getCode()==rentWay){
				HouseRoomMsgEntity houseRoomMsg=new HouseRoomMsgEntity();
				houseRoomMsg.setRoomPrice(DecimalCalculate.mul(housePriceDto.getLeasePrice().toString(), "100").intValue());
				houseRoomMsg.setRoomCleaningFees(DecimalCalculate.mul(housePriceDto.getCleaningFees().toString(), "100").intValue());
				houseUpdateHistoryLogDto.setHouseRoomMsg(houseRoomMsg);
				houseUpdateHistoryLogDto.setHouseFid(housePriceDto.getHouseBaseFid());
				houseUpdateHistoryLogDto.setRentWay(housePriceDto.getRentWay());
				houseUpdateHistoryLogDto.setRoomFid(housePriceDto.getRoomFid());
			}
			houseUpdateHistoryLogDto.setSourceType(getUserSourceType(request));
			houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.LANLORD.getCode());
			houseUpdateHistoryLogDto.setCreaterFid(getUserId(request));
			String  dtoJson = this.houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			LogUtil.info(LOGGER, "【修改价格保存日志】返回结果dtoJson={},roomFid={},houseBaseFid={}", dtoJson,housePriceDto.getRoomFid(),housePriceDto.getHouseBaseFid());
		}

	}
	
	/**
	 * 
	 * 获取来源
	 *
	 * @author yd
	 * @created 2017年7月6日 下午8:21:50
	 *
	 * @param request
	 * @return
	 */
	private int getUserSourceType(HttpServletRequest request){

		int houseSource = HouseSourceEnum.MSIT.getCode();
		Header header=getHeader(request);
		//判断来源
		if(header.getOsType().equals("1")){
			houseSource =HouseSourceEnum.ANDROID.getCode();
		} else if(header.getOsType().equals("2")) {
			houseSource = HouseSourceEnum.IOS.getCode();
		}

		return houseSource;
	}
}
