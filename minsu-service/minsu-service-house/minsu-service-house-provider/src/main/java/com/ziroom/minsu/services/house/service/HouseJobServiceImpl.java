/**
 * @FileName: HouseJobServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2016年4月9日 下午9:24:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.house.dao.*;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseStatsDayMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseDfbNoticeDto;
import com.ziroom.minsu.services.house.entity.RoomLandlordVo;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>定时任务相关业务实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Service("house.houseJobServiceImpl")
public class HouseJobServiceImpl {
	
	@Resource(name="house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;
	
	@Resource(name="house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;
	
	@Resource(name="house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;
	
	@Resource(name="house.houseStatsMsgDao")
	private HouseStatsDayMsgDao houseStatsMsgDao;

	@Resource(name="house.houseStatisticsMsgDao")
	private HouseStatisticsMsgDao houseStatisticsMsgDao;


	
	
	/**
	 * 
	 * 查询超时未审核房源(整租)
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午9:29:39
	 *
	 * @param houseStatus
	 * @param limitDate
	 * @return
	 */
	public List<HouseBaseMsgEntity> findOverAuditLimitHouse(Integer houseStatus,Date limitDate){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseStatus", houseStatus);
		paramMap.put("limitDate", limitDate);
		paramMap.put("rentWay", RentWayEnum.HOUSE.getCode());
		return houseBaseMsgDao.findOverAuditLimitHouse(paramMap);
	}
	
	/**
	 * 
	 * 更新超时未审核房源为已上架状态(整租)
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午10:11:42
	 *
	 * @param fid
	 */
	public void updateHouseSJ(String fid){
		HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setFid(fid);
		houseBaseMsgEntity.setHouseStatus(HouseStatusEnum.SJ.getCode());
		houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
		//添加更新状态日志
		HouseOperateLogEntity houseOperateLogEntity=new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setHouseBaseFid(fid);
		houseOperateLogEntity.setFromStatus(HouseStatusEnum.YFB.getCode());
		houseOperateLogEntity.setToStatus(HouseStatusEnum.SJ.getCode());
		houseOperateLogEntity.setOperateType(1);
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);
	}
	
	/**
	 * 
	 * 查询超时未审核房间列表
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午10:48:21
	 *
	 * @param houseStatus
	 * @param limitDate
	 * @return
	 */
	public List<RoomLandlordVo>  findOverAuditLimitRoom(Integer houseStatus,Date limitDate){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("roomStatus", houseStatus);
		paramMap.put("limitDate", limitDate);
		paramMap.put("rentWay", RentWayEnum.ROOM.getCode());
		return houseRoomMsgDao.findOverAuditLimitRoom(paramMap);
	}
	
	/**
	 * 
	 * 更新超时未审核房间为已上架状态（合租）
	 *
	 * @author bushujie
	 * @created 2016年4月9日 下午11:01:31
	 *
	 * @param fid
	 */
	public void updateRoomSJ(String fid){
		HouseRoomMsgEntity houseRoomMsgEntity=new HouseRoomMsgEntity();
		houseRoomMsgEntity.setFid(fid);
		houseRoomMsgEntity.setRoomStatus(HouseStatusEnum.SJ.getCode());
		houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsgEntity);
		//插入更新状态日志
		HouseOperateLogEntity houseOperateLogEntity=new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setRoomFid(fid);
		houseOperateLogEntity.setFromStatus(HouseStatusEnum.YFB.getCode());
		houseOperateLogEntity.setToStatus(HouseStatusEnum.SJ.getCode());
		houseOperateLogEntity.setOperateType(1);
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);
	}
	
	 /**
     * 
     * 查询需要 发送短信和极光消息的短信 的房源
     *
     * @author yd
     * @created 2016年11月22日 下午4:46:06
     *
     * @param cutTime 当前时间
     * @return
     */
	public List<HouseDfbNoticeDto>  findNoticeLanDfbHouseMsg(String cutTime,Integer day){
		return this.houseBaseMsgDao.findNoticeLanDfbHouseMsg(cutTime, day);
	}

	/**
	 * 保存房源统计信息列表
	 *
	 * @author liujun
	 * @created 2016年12月5日
	 *
	 * @param statsList
	 */
	public void saveHouseStatsList(List<HouseStatsDayMsgEntity> statsList) {
		for (HouseStatsDayMsgEntity houseStatsMsgEntity : statsList) {
			String fid = this.generateFid(houseStatsMsgEntity);
			houseStatsMsgEntity.setFid(fid);
			houseStatsMsgDao.insertHouseStatisticsMsg(houseStatsMsgEntity);
		}
	}

	/**
	 * 生成主键
	 *
	 * @author liujun
	 * @created 2016年12月5日
	 *
	 * @param houseStatsMsgEntity
	 * @return
	 */
	private String generateFid(HouseStatsDayMsgEntity houseStatsMsgEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(houseStatsMsgEntity.getHouseFid())
		  .append(houseStatsMsgEntity.getRentWay())
		  .append(houseStatsMsgEntity.getStatsDate());
		return MD5Util.MD5Encode(sb.toString());
	}

	/**
	 * 条件删除房源统计信息
	 *
	 * @author liujun
	 * @created 2016年12月5日
	 *
	 * @param statsDate
	 */
	public void deleteHouseStatsMsgByStatsDate(Date statsDate) {
		houseStatsMsgDao.deleteHouseStatsMsgByStatsDate(statsDate);
	}


	/**
	 * 获取当前房东的房源数量
	 * 按照房源的维度去统计
	 * @author afi
	 * @param landUid
	 * @return
	 */
	public Long countLandHouseNum(String landUid) {
		if (Check.NuNStr(landUid)) {
			return 0L;
		}
		return houseStatisticsMsgDao.countLandHouseNum(landUid);
	}



	/**
	 * 获取当前房东的房源数量
	 * 按照房源的sku
	 * @author afi
	 * @param landUid
	 * @return
	 */
	public Long countLandHouseSkuNum(String landUid) {
		if (Check.NuNStr(landUid)) {
			return 0L;
		}
		return houseStatisticsMsgDao.countLandHouseSkuNum(landUid);
	}


}
