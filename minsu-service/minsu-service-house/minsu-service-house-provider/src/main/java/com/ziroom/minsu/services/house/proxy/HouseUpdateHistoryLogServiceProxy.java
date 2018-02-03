/**
 * @FileName: HouseUpdateHistoryLogServiceProxy.java
 * @Package com.ziroom.minsu.services.house.proxy
 * 
 * @author yd
 * @created 2017年7月4日 下午4:51:58
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseDescEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryLogEntity;
import com.ziroom.minsu.services.house.api.inner.HouseUpdateHistoryLogService;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.dto.WaitUpdateHouseInfoDto;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.service.HouseIssueServiceImpl;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;
import com.ziroom.minsu.services.house.service.HouseUpdateHistoryLogServiceImpl;
import com.ziroom.minsu.services.house.service.TroyHouseMgtServiceImpl;
import com.ziroom.minsu.services.house.utils.HouseUtils;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>房源更新记录 代理</p>
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
@Component("house.houseUpdateHistoryLogServiceProxy")
public class HouseUpdateHistoryLogServiceProxy implements HouseUpdateHistoryLogService{



	private  static Logger logger = LoggerFactory.getLogger(HouseUpdateHistoryLogServiceProxy.class);


	@Resource(name="house.houseManageServiceImpl")
	private HouseManageServiceImpl houseManageServiceImpl;


	@Resource(name = "house.houseUpdateHistoryLogServiceImpl")
	private HouseUpdateHistoryLogServiceImpl  houseUpdateHistoryLogServiceImpl;

	@Resource(name = "house.houseIssueServiceImpl")
	private HouseIssueServiceImpl houseIssueServiceImpl;

	@Resource(name="house.troyHouseMgtServiceImpl")
	private TroyHouseMgtServiceImpl troyHouseMgtServiceImpl;

	/**
	 * 
	 * 保存 用户更新记录
	 * 说明: 保存的修改记录 一定不要提前入库  当前接口用的是 当前用户输入 最新记录 和 数据库对应记录做对比  
	 *  1. 一定先调用 此方法后  在把对应修改入库 
	 *  2. 不可 异步调用  
	 *  3. 可以失败后  不影响正常业务
	 *
	 * @author yd
	 * @created 2017年7月4日 下午4:54:31
	 *
	 * @param paramJson
	 * @return
	 */
	@Override
	public String saveHistoryLog(String paramJson) {

		DataTransferObject dto = new DataTransferObject();

		HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = JsonEntityTransform.json2Object(paramJson, HouseUpdateHistoryLogDto.class);
		
		String roomFid = houseUpdateHistoryLogDto.getRoomFid();
		String houseFid = houseUpdateHistoryLogDto.getHouseFid();
		Integer rentWay = houseUpdateHistoryLogDto.getRentWay();
		
		if(!Check.NuNObj(houseUpdateHistoryLogDto.getOldHouseBaseMsg())) rentWay =  houseUpdateHistoryLogDto.getOldHouseBaseMsg().getRentWay();
		if(Check.NuNObjs(houseUpdateHistoryLogDto,rentWay)||Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("保存房源更新记录-房源参数错误");
			LogUtil.error(logger, "【保存房源更新记录-房源参数错误】houseFid={},rentWay={},roomFid={}", houseFid,rentWay,roomFid);
			return dto.toJsonString();
		}
	
		RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);
		
		
		if(Check.NuNObj(rentWayEnum)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源出租方式错误");
			return dto.toJsonString();
		}
		if(Check.NuNObjs(houseUpdateHistoryLogDto.getCreateType(),houseUpdateHistoryLogDto.getSourceType())
				||Check.NuNStr(houseUpdateHistoryLogDto.getCreaterFid())){
			LogUtil.error(logger, "【保存房源更新记录-参数错误】createrFid={},createType()={},sourceType={}",houseUpdateHistoryLogDto.getCreaterFid(),
					houseUpdateHistoryLogDto.getCreateType(),houseUpdateHistoryLogDto.getSourceType());
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			return dto.toJsonString();
		}

		//待保存的 修改记录
		List<HouseUpdateHistoryLogEntity> list = new ArrayList<HouseUpdateHistoryLogEntity>();
		//填充房源修改记录
		HouseUtils.contrastHouseBaseMsgObj(houseUpdateHistoryLogDto.getHouseBaseMsg(), houseUpdateHistoryLogDto.getOldHouseBaseMsg(), list);

		//填充房间 修改记录
		HouseUtils.contrastHouseRoomMsgObj(houseUpdateHistoryLogDto.getHouseRoomMsg(), houseUpdateHistoryLogDto.getOldHouseRoomMsg(), list,rentWay);

		//填充房间描述 修改记录
		HouseUtils.contrastHouseDescObj(houseUpdateHistoryLogDto.getHouseDesc(), houseUpdateHistoryLogDto.getOldHouseDesc(), list);

		//填充房源扩展信息 修改记录
		HouseUtils.contrastHouseBaseExtObj(houseUpdateHistoryLogDto.getHouseBaseExt(), houseUpdateHistoryLogDto.getOldHouseBaseExt(), list);

		//填充房源 物理信息 修改记录
		HouseUtils.contrastHousePhyMsgObj(houseUpdateHistoryLogDto.getHousePhyMsg(), houseUpdateHistoryLogDto.getOldHousePhyMsg(), list);

		//填充 房间扩展信息
		HouseUtils.contrastHouseRoomExtObj(houseUpdateHistoryLogDto.getHouseRoomExt(), houseUpdateHistoryLogDto.getOldHouseRoomExt(), list);

		//填充 房源配置信息
		//fillHouseConfMsgUpdateLog(houseBaseMsg.getFid(), houseUpdateHistoryLogDto.getListHouseConfMsg(), list);
		//整体保存 
		this.houseUpdateHistoryLogServiceImpl.saveHouseBaseUpdateLog(houseUpdateHistoryLogDto, list);
		return dto.toJsonString();
	}

	/**
	 * 
	 * 查询 所有房源 待修改实体
	 *
	 * @author yd
	 * @created 2017年7月7日 上午11:05:12
	 *
	 * @param waitUpdateHouseInfoDto
	 * @return
	 */
	@Override
	public String findWaitUpdateHouseInfo(String  waitUpdateHouseInfoDto){

		DataTransferObject dto = new DataTransferObject();

		try {
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto =  new HouseUpdateHistoryLogDto();
			WaitUpdateHouseInfoDto waitUpdateHouseInfo= JsonEntityTransform.json2Object(waitUpdateHouseInfoDto, WaitUpdateHouseInfoDto.class);

			if(Check.NuNObjs(waitUpdateHouseInfo)||Check.NuNStr(waitUpdateHouseInfo.getHouseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("【房源信息修改保存-查询待修改房源数据】参数错误");
				return dto.toJsonString();
			}
			String roomFid = waitUpdateHouseInfo.getRoomFid();
			String houseFid = waitUpdateHouseInfo.getHouseFid();

			HouseBaseMsgEntity oldHouseBaseMsg=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseFid);

			//判断房源是否存在
			if(Check.NuNObj(oldHouseBaseMsg)||Check.NuNObj(oldHouseBaseMsg.getHouseStatus())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto.toJsonString();
			}


			int rentWay = oldHouseBaseMsg.getRentWay();
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);
			if(Check.NuNObj(rentWayEnum)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("【房源信息修改保存-查询待修改房源数据】房源出租方式错误");
				return dto.toJsonString();
			}



			if(rentWay == RentWayEnum.HOUSE.getCode()){

				//判断是否符合状态  待发布 不做记录
				if (HouseStatusEnum.DFB.getCode() == oldHouseBaseMsg.getHouseStatus()) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房源状态错误");
					LogUtil.info(logger, "【保存房源更新记录-房态错误】houseBaseFid={},houseStatu={}",oldHouseBaseMsg.getFid(),oldHouseBaseMsg.getHouseStatus());
					return dto.toJsonString();
				}
			}

			/******获取当前房源的待审核字段的信息，如果有待审核信息，将用 "待审核的newValue" 作为 "待修改的值"。@Author:lusp  @Date:2017/8/9******/
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog=new HouseUpdateFieldAuditNewlogEntity();
			houseUpdateFieldAuditNewlog.setHouseFid(houseFid);
			houseUpdateFieldAuditNewlog.setRentWay(rentWay);
			houseUpdateFieldAuditNewlog.setRoomFid(roomFid);
			houseUpdateFieldAuditNewlog.setFieldAuditStatu(0);
			List<HouseFieldAuditLogVo> list = troyHouseMgtServiceImpl.findHouseUpdateFieldAuditNewlogByCondition(houseUpdateFieldAuditNewlog);
			/******获取当前房源的待审核字段的信息，如果有待审核信息，将用 "待审核的newValue" 作为 "待修改的值"。@Author:lusp  @Date:2017/8/9******/


			//如果 roomFid 为null  还是 分租 只能是 保存位置信息  这块没有 roomFid 就判断不了 房态了
			if(!Check.NuNStr(roomFid)){
				//判断房间是否存在
				HouseRoomMsgEntity oldHouseRoomMsg=houseManageServiceImpl.getHouseRoomByFid(roomFid);
				if(Check.NuNObj(oldHouseRoomMsg)||Check.NuNObj(oldHouseRoomMsg.getRoomStatus())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房间不存在");
					return dto.toJsonString();
				}

				if(rentWay == RentWayEnum.ROOM.getCode()){
					//判断是否符合状态  待发布 不做记录
					if (HouseStatusEnum.DFB.getCode() == oldHouseRoomMsg.getRoomStatus()) {
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("房间状态错误");
						LogUtil.info(logger, "【保存房源更新记录-房态错误】houseRoomFid={},roomStatu={}",oldHouseRoomMsg.getFid(),oldHouseRoomMsg.getRoomStatus());
						return dto.toJsonString();
					}
					HouseRoomExtEntity oldHouseRoomExt = houseIssueServiceImpl.getRoomExtByRoomFid(roomFid);
					oldHouseRoomExt = HouseUtils.FilterNotAuditField(oldHouseRoomExt,list);//过滤待审核字段 @Author:lusp  @Date:2017/8/9
					houseUpdateHistoryLogDto.setOldHouseRoomExt(oldHouseRoomExt);
				}
				oldHouseRoomMsg = HouseUtils.FilterNotAuditField(oldHouseRoomMsg,list);//过滤待审核字段 @Author:lusp  @Date:2017/8/9
				houseUpdateHistoryLogDto.setOldHouseRoomMsg(oldHouseRoomMsg);
				houseUpdateHistoryLogDto.setRoomFid(roomFid);
			}

			//房源扩展信息
			HouseBaseExtEntity oldHouseBaseExt = houseIssueServiceImpl.getHouseBaseExtByHouseBaseFid(houseFid);
			//房源描述信息
			HouseDescEntity oldHouseDesc = houseIssueServiceImpl.findhouseDescEntityByHouseFid(houseFid);
			//房源物理信息
			HousePhyMsgEntity oldHousePhyMsg = houseIssueServiceImpl.findHousePhyMsgByHouseBaseFid(houseFid);

			/******************过滤待审核字段 @Author:lusp  @Date:2017/8/9******************/
			oldHouseBaseMsg = HouseUtils.FilterNotAuditField(oldHouseBaseMsg,list);
			oldHouseDesc = HouseUtils.FilterNotAuditField(oldHouseDesc,list);
			oldHousePhyMsg = HouseUtils.FilterNotAuditField(oldHousePhyMsg,list);
			oldHouseBaseExt = HouseUtils.FilterNotAuditField(oldHouseBaseExt,list);
			/******************过滤待审核字段 @Author:lusp  @Date:2017/8/9******************/


			houseUpdateHistoryLogDto.setOldHouseBaseMsg(oldHouseBaseMsg);
			houseUpdateHistoryLogDto.setOldHouseDesc(oldHouseDesc);
			houseUpdateHistoryLogDto.setOldHousePhyMsg(oldHousePhyMsg);
			houseUpdateHistoryLogDto.setOldHouseBaseExt(oldHouseBaseExt);
			houseUpdateHistoryLogDto.setRentWay(rentWay);
			houseUpdateHistoryLogDto.setHouseFid(houseFid);

			dto.putValue("houseUpdateHistoryLogDto", houseUpdateHistoryLogDto);
		}catch (Exception e){
			LogUtil.error(logger,"error:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}


		return dto.toJsonString();
	}



}
