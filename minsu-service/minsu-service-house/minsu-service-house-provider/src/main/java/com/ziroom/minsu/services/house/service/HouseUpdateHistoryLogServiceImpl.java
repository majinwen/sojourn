/**
 * @FileName: HouseUpdateHistoryLogImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author yd
 * @created 2017年7月3日 下午4:41:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditManagerEntity;
import com.ziroom.minsu.entity.house.HouseUpdateFieldAuditNewlogEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryExtLogEntity;
import com.ziroom.minsu.entity.house.HouseUpdateHistoryLogEntity;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditManagerDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateFieldAuditNewlogDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateHistoryExtLogDao;
import com.ziroom.minsu.services.house.dao.HouseUpdateHistoryLogDao;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.entity.HouseRoomListVo;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.house.CreaterTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.IsTextEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>房源基本信息修改记录 事务层</p>
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

@Service("house.houseUpdateHistoryLogServiceImpl")
public class HouseUpdateHistoryLogServiceImpl {
	
	private static Logger LOGGER = LoggerFactory.getLogger(HouseUpdateHistoryLogServiceImpl.class) ;
	
	@Resource(name = "house.houseUpdateHistoryLogDao")
	private HouseUpdateHistoryLogDao houseUpdateHistoryLogDao;
	
	@Resource(name = "house.houseUpdateHistoryExtLogDao")
	private HouseUpdateHistoryExtLogDao houseUpdateHistoryExtLogDao;
	
	@Resource(name = "house.houseUpdateFieldAuditNewlogDao")
	private HouseUpdateFieldAuditNewlogDao houseUpdateFieldAuditNewlogDao;
	
	@Resource(name = "house.houseUpdateFieldAuditManagerDao")
	private HouseUpdateFieldAuditManagerDao houseUpdateFieldAuditManagerDao;

	@Resource(name = "house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;

	@Resource(name = "house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;
	
	
	/**
	 * 
	 * 保存 
	 * 1. 保存 修改历史 HouseUpdateHistoryLog 如果 是大字段 则存入大字段表
	 * 2. 更新 最新记录表
	 *
	 * @author yd
	 * @created 2017年6月30日 下午4:34:42
	 *
	 * @param houseUpdateHistoryLog
	 */
	public int saveHouseUpdateHistoryLog(HouseUpdateHistoryLogEntity houseUpdateHistoryLog){
		
		if(!Check.NuNObj(houseUpdateHistoryLog)){
			
			if(Check.NuNStr(houseUpdateHistoryLog.getFid())) houseUpdateHistoryLog.setFid(UUIDGenerator.hexUUID());
			//此字段 必传 在上层做校验
			int isTest = houseUpdateHistoryLog.getIsText();
			int i =  0;
			if(isTest == IsTextEnum.IS_TEST.getCode()){
				
				LogUtil.info(LOGGER, "【保存房源修改大字段】isTest={},houseFid={},roomFid={},rentWay={},fieldPathKey={},", isTest,houseUpdateHistoryLog.getHouseFid(),houseUpdateHistoryLog.getRoomFid()
						,houseUpdateHistoryLog.getRentWay(),houseUpdateHistoryLog.getFieldPathKey());
				HouseUpdateHistoryExtLogEntity houseUpdateHistoryExtLog = new HouseUpdateHistoryExtLogEntity();
				houseUpdateHistoryExtLog.setFid(houseUpdateHistoryLog.getFid());
				houseUpdateHistoryExtLog.setNewValue(houseUpdateHistoryLog.getNewValue());
				houseUpdateHistoryExtLog.setOldValue(houseUpdateHistoryLog.getOldValue());
				houseUpdateHistoryLog.setNewValue("");
				houseUpdateHistoryLog.setOldValue("");
				
				this.houseUpdateHistoryExtLogDao.saveHouseUpdateHistoryExtLog(houseUpdateHistoryExtLog);
			}
			i = this.houseUpdateHistoryLogDao.saveHouseUpdateHistoryLog(houseUpdateHistoryLog);
			//t_house_update_field_audit_newlog 审核字段的最新记录 只做第一次插入  这里状态更改在 审核时候 才会状态更改  
			if(i>0){

				/*********处理审核未通过和上架情况下的审核记录的插入和更新。@Author:lusp  @Date:2017/8/8*********/
				//根据houseFid、rentWay、roomFid 来统一判断房源状态
				String houseFid = houseUpdateHistoryLog.getHouseFid();
				String roomFid = houseUpdateHistoryLog.getRoomFid();
				Integer rentWay = houseUpdateHistoryLog.getRentWay();
				HouseBaseMsgEntity houseBaseMsgEntity = null;
				if(Check.NuNStrStrict(houseFid)){
					LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),保存到房源审核记录表时出错houseFid为空，houseFid:{}",houseFid);
					return i;
				}
				if(Check.NuNObj(rentWay)){
					houseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
					if(Check.NuNObj(houseBaseMsgEntity)){
						LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),根据houseFid获取房源基础信息为空，houseFid:{}",houseFid);
						return i;
					}
					rentWay = houseBaseMsgEntity.getRentWay();
				}
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					Integer houseStatus = null;
					if(Check.NuNObj(houseBaseMsgEntity)){
						houseBaseMsgEntity = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseFid);
						if(Check.NuNObj(houseBaseMsgEntity)){
							LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),根据houseFid获取房源基础信息为空，houseFid:{}",houseFid);
							return i;
						}
						houseStatus = houseBaseMsgEntity.getHouseStatus();
					}else {
						houseStatus = houseBaseMsgEntity.getHouseStatus();
					}
					i = this.saveHouseAuditField(houseUpdateHistoryLog,houseStatus,i);
				}else if(rentWay == RentWayEnum.ROOM.getCode()){
					if(!Check.NuNStrStrict(roomFid)){
						HouseRoomMsgEntity houseRoomMsgEntity = houseRoomMsgDao.getHouseRoomByFid(roomFid);
						if(Check.NuNObj(houseRoomMsgEntity)){
							LogUtil.error(LOGGER,"saveHouseUpdateHistoryLog(),根据roomFid获取房间基础信息为空，roomFid:{}",roomFid);
							return i;
						}
						Integer houseStatus = houseRoomMsgEntity.getRoomStatus();
						i = this.saveHouseAuditField(houseUpdateHistoryLog,houseStatus,i);
					}else{
						boolean hasSJ = false;
						boolean hasZPSHWTG = false;
						List<HouseRoomListVo> roomListVos = houseRoomMsgDao.getRoomListByHouseFid(houseFid);
						if(!Check.NuNCollection(roomListVos)){
							for (HouseRoomListVo houseRoomListVo:roomListVos){
								if(houseRoomListVo.getRoomStatus()== HouseStatusEnum.SJ.getCode()){
									hasSJ = true;
									break;
								}
							}
							for (HouseRoomListVo houseRoomListVo:roomListVos){
								if(houseRoomListVo.getRoomStatus()== HouseStatusEnum.ZPSHWTG.getCode()){
									hasZPSHWTG = true;
									break;
								}
							}
						}
						if(hasZPSHWTG){
							i = this.saveHouseAuditField(houseUpdateHistoryLog,HouseStatusEnum.ZPSHWTG.getCode(),i);
						}
						if(hasSJ){
							i = this.saveHouseAuditField(houseUpdateHistoryLog,HouseStatusEnum.SJ.getCode(),i);
						}
					}
				}
				/*********处理审核未通过和上架情况下的审核记录的插入和更新。@Author:lusp  @Date:2017/8/8*********/

			}
			
			return i;
		}
		
		
		return 0;
	}

	/**
	 * @description: 根据房源状态以及房源基础信息保存审核记录表
	 * @author: lusp
	 * @date: 2017/8/9 16:58
	 * @params: houseUpdateHistoryLog,houseStatus,i
	 * @return:
	 */
	private int saveHouseAuditField(HouseUpdateHistoryLogEntity houseUpdateHistoryLog,Integer houseStatus,int i){
		HouseUpdateFieldAuditManagerEntity houseUpdateFieldAuditManagerEntity = new HouseUpdateFieldAuditManagerEntity();
		houseUpdateFieldAuditManagerEntity.setFid(MD5Util.MD5Encode(houseUpdateHistoryLog.getFieldPath(), "UTF-8"));
		if(houseStatus == HouseStatusEnum.ZPSHWTG.getCode()){
			houseUpdateFieldAuditManagerEntity.setType(0);
		}else if(houseStatus == HouseStatusEnum.SJ.getCode()){
			houseUpdateFieldAuditManagerEntity.setType(1);
		}
		houseUpdateFieldAuditManagerEntity = this.houseUpdateFieldAuditManagerDao.findHouseUpdateFieldAuditManagerByFidAndType(houseUpdateFieldAuditManagerEntity);
		if(Check.NuNObj(houseUpdateFieldAuditManagerEntity)){
			LogUtil.info(LOGGER, "【保存房源修改审核字段】当前字段非审核字段：fieldPath={}", houseUpdateHistoryLog.getFieldPath());
			return i;
		}
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog = this.houseUpdateFieldAuditNewlogDao.findHouseUpdateFieldAuditNewlogByFid(houseUpdateHistoryLog.getFieldPathKey());
		if(Check.NuNObj(houseUpdateFieldAuditNewlog)){
			houseUpdateFieldAuditNewlog = new HouseUpdateFieldAuditNewlogEntity();
			houseUpdateFieldAuditNewlog.setFieldPath(houseUpdateHistoryLog.getFieldPath());
			houseUpdateFieldAuditNewlog.setFieldDesc(houseUpdateHistoryLog.getFieldDesc());
			houseUpdateFieldAuditNewlog.setFid(houseUpdateHistoryLog.getFieldPathKey());
			houseUpdateFieldAuditNewlog.setCreaterFid(houseUpdateHistoryLog.getCreaterFid());
			houseUpdateFieldAuditNewlog.setCreaterType(houseUpdateHistoryLog.getCreaterType());
			if (!Check.NuNObj(houseUpdateHistoryLog.getOperateSource()) && houseUpdateHistoryLog.getOperateSource() == CreaterTypeEnum.GUARD.getCode()) {
				houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.COMPLETE.getCode());
			} else {
				houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
			}
			houseUpdateFieldAuditNewlog.setHouseFid(houseUpdateHistoryLog.getHouseFid());
			houseUpdateFieldAuditNewlog.setRoomFid(houseUpdateHistoryLog.getRoomFid());
			houseUpdateFieldAuditNewlog.setRentWay(houseUpdateHistoryLog.getRentWay());
			i = this.houseUpdateFieldAuditNewlogDao.saveHouseUpdateFieldAuditNewlog(houseUpdateFieldAuditNewlog);
		}else{
			if (!Check.NuNObj(houseUpdateHistoryLog.getOperateSource()) && houseUpdateHistoryLog.getOperateSource() == CreaterTypeEnum.GUARD.getCode()) {
				houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.COMPLETE.getCode());
			} else {
				houseUpdateFieldAuditNewlog.setFieldAuditStatu(AuditStatusEnum.SUBMITAUDIT.getCode());
			}
			i = this.houseUpdateFieldAuditNewlogDao.updateHouseUpdateFieldAuditNewlogByFid(houseUpdateFieldAuditNewlog);
		}

		return i;

	}
	 
	
	/**
	 * 
	 * 填充 房源表修改记录
	 *
	 * @author yd
	 * @created 2017年7月4日 下午5:47:23
	 *
	 * @param houseBaseMsg
	 * @param list
	 */
	public  void  saveHouseBaseUpdateLog(HouseUpdateHistoryLogDto houseUpdateHistoryLogDto,List<HouseUpdateHistoryLogEntity> list){
		
		if(!Check.NuNObjs(houseUpdateHistoryLogDto)&&!Check.NuNCollection(list)){
			for (HouseUpdateHistoryLogEntity houseUpdateHistoryLog : list) {
				houseUpdateHistoryLog.setCreaterFid(houseUpdateHistoryLogDto.getCreaterFid());
        		houseUpdateHistoryLog.setCreaterType(houseUpdateHistoryLogDto.getCreateType());
            	houseUpdateHistoryLog.setHouseFid(houseUpdateHistoryLogDto.getHouseFid());
            	houseUpdateHistoryLog.setRoomFid(houseUpdateHistoryLogDto.getRoomFid());
        		houseUpdateHistoryLog.setRentWay(houseUpdateHistoryLogDto.getRentWay());
        		if(!Check.NuNObj(houseUpdateHistoryLog.getRentWay())){
        			if(houseUpdateHistoryLog.getRentWay()==RentWayEnum.HOUSE.getCode()&&"com.ziroom.minsu.entity.house.HouseBaseExtEntity.defaultPicFid".equals(houseUpdateHistoryLog.getFieldPath())){
        				houseUpdateHistoryLog.setRoomFid(null);
        			}
        		}
        		if (!Check.NuNObj(houseUpdateHistoryLogDto.getOperateSource())){
        			houseUpdateHistoryLog.setOperateSource(houseUpdateHistoryLogDto.getOperateSource());
				}

            	String fieldPathKey = MD5Util.MD5Encode(houseUpdateHistoryLog.getHouseFid()+houseUpdateHistoryLog.getRoomFid()+houseUpdateHistoryLog.getRentWay()
            			+houseUpdateHistoryLog.getFieldPath(), "UTF-8");
        		houseUpdateHistoryLog.setFieldPathKey(fieldPathKey);
            	houseUpdateHistoryLog.setSourceType(houseUpdateHistoryLogDto.getSourceType());
            	saveHouseUpdateHistoryLog(houseUpdateHistoryLog);
			}
		}
	}
	
}
