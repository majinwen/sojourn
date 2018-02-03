package com.ziroom.minsu.services.house.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseBizMsgEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * 
 * <p>房源公共逻辑dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改时间	修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseCommonLogicDao")
public class HouseCommonLogicDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseCommonLogicDao.class);

	@Resource(name = "house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;
	
	@Resource(name = "house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;

	@Resource(name = "house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;
	
	@Resource(name = "house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;
	
	@Resource(name = "house.houseBizMsgDao")
	private HouseBizMsgDao houseBizMsgDao;
	
	/**
	 * 判断是否品质审核未通过修改房源信息 
	 * 1.如果是品质审核未通过修改,级联修改房间/床位状态,返回true
	 * 2.如果非品质审核未通过修改,直接返回false
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public boolean updateHouseStatus(String houseBaseFid) {
		HouseBaseMsgEntity houseBaseMsg = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
		return updateHouseStatus(houseBaseMsg);
	}
	
	/**
	 * 判断是否品质审核未通过修改房源信息 
	 * 1.如果是品质审核未通过修改,级联修改房间/床位状态,返回true
	 * 2.如果非品质审核未通过修改,直接返回false
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param houseBaseMsg
	 * @return
	 */
	public boolean updateHouseStatus(HouseBaseMsgEntity houseBaseMsg) {
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNObj(houseBaseMsg.getRentWay())) {
			Integer rentWay = houseBaseMsg.getRentWay();
			Integer houseStatus = houseBaseMsg.getHouseStatus();
			if (RentWayEnum.HOUSE.getCode() == rentWay.intValue() && !Check.NuNObj(houseStatus)
					&& HouseStatusEnum.ZPSHWTG.getCode() == houseStatus.intValue()) {
				this.saveHouseOperateLogByLandlord(houseBaseMsg, HouseStatusEnum.YFB.getCode());
				this.cascadingHouseStatus(houseBaseMsg, HouseStatusEnum.YFB.getCode());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 业务保存房源操作日志
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:25:37
	 *
	 * @param houseBaseMsg
	 * @param paramMap
	 * @param status @HouseStatusEnum
	 */
	public void saveHouseOperateLogByBiz(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap, int status) {
		HouseOperateLogEntity houseOperateLog = new HouseOperateLogEntity();
		houseOperateLog.setFid(UUIDGenerator.hexUUID());
		houseOperateLog.setHouseBaseFid(houseBaseMsg.getFid());
		houseOperateLog.setFromStatus(houseBaseMsg.getHouseStatus());
		houseOperateLog.setToStatus(status);
		houseOperateLog.setOperateType(HouseConstant.HOUSE_LOG_OPERATE_TYPE_BUSINESS);
		houseOperateLog.setCreateDate(new Date());
		if (!Check.NuNMap(paramMap)) {
			houseOperateLog.setRemark((String) paramMap.get("remark"));
			houseOperateLog.setAuditCause((String) paramMap.get("auditCause"));
			houseOperateLog.setCreateFid((String) paramMap.get("operaterFid"));
		}
		houseOperateLogDao.insertHouseOperateLog(houseOperateLog);
		
		// 同步房源业务信息
		this.mergeHouseBizMsg(houseBaseMsg, paramMap, status);
		LogUtil.info(LOGGER, "houseBaseFid={},fromStatus={},toStatus={}", houseBaseMsg.getFid(), houseBaseMsg.getHouseStatus(), status);
	}
	
	/**
	 * 
	 * 房东保存房源操作日志
	 *
	 * @author yd
	 * @created 2016年8月25日 下午1:50:27
	 *
	 * @param houseBaseMsg
	 * @param status
	 */
	public void saveHouseOperateLogByLandlord(HouseBaseMsgEntity houseBaseMsg, int status) {
		HouseOperateLogEntity houseOperateLog = new HouseOperateLogEntity();
		houseOperateLog.setFid(UUIDGenerator.hexUUID());
		houseOperateLog.setHouseBaseFid(houseBaseMsg.getFid());
		houseOperateLog.setFromStatus(houseBaseMsg.getHouseStatus());
		houseOperateLog.setToStatus(status);
		houseOperateLog.setOperateType(HouseConstant.HOUSE_LOG_OPERATE_TYPE_LANDLORD);
		houseOperateLog.setCreateFid(houseBaseMsg.getLandlordUid());
		houseOperateLog.setCreateDate(new Date());
		houseOperateLogDao.insertHouseOperateLog(houseOperateLog);
		
		// 同步房源业务信息
		this.mergeHouseBizMsg(houseBaseMsg, null, status);
		LogUtil.info(LOGGER, "houseBaseFid={},fromStatus={},toStatus={}", houseBaseMsg.getFid(),
				houseBaseMsg.getHouseStatus(), status);
	}
	
	/**
	 * 级联更新房源下房间及房间下床位状态
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:17:30
	 *
	 * @param houseBaseMsg
	 * @param status
	 */
	public void cascadingHouseStatus(HouseBaseMsgEntity houseBaseMsg, int status) {
		List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseMsg.getFid());
		if(!Check.NuNCollection(roomList)){
			for (HouseRoomMsgEntity houseRoomMsg : roomList) {
				List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomMsg.getFid());
				if(!Check.NuNCollection(bedList)){
					for (HouseBedMsgEntity houseBedMsg : bedList) {
						houseBedMsg.setBedStatus(status);
						houseBedMsg.setLastModifyDate(new Date());
						houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
					}
					houseRoomMsg.setRoomStatus(status);
					houseRoomMsg.setLastModifyDate(new Date());
					houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
				}
			}
		}
	}
	
	/**
	 * 同步房源业务信息
	 *
	 * @author liujun
	 * @created 2017年2月28日
	 *
	 * @param houseBaseMsg
	 * @param paramMap
	 * @param status
	 */
	public void mergeHouseBizMsg(HouseBaseMsgEntity houseBaseMsg, Map<String, Object> paramMap, int status) {
		HouseBizMsgEntity houseBizMsg = houseBizMsgDao.getHouseBizMsgByHouseFid(houseBaseMsg.getFid());
		boolean isNew = false;// 业务表是否新建
		boolean isEmpty = false;// paramMap是否为空
		Date currDate = new Date();
		if (Check.NuNObj(houseBizMsg)) {
			isNew = true;
			houseBizMsg = new HouseBizMsgEntity();
			houseBizMsg.setFid(UUIDGenerator.hexUUID());
			houseBizMsg.setHouseBaseFid(houseBaseMsg.getFid());
			houseBizMsg.setRentWay(RentWayEnum.HOUSE.getCode());
			houseBizMsg.setCreateDate(currDate);
			if (Check.NuNMap(paramMap)) {
				isEmpty = true;
			} else {
				houseBizMsg.setCreateFid((String) paramMap.get("operaterFid"));
			}
		}
		
		if (status == HouseStatusEnum.YFB.getCode()) {
			houseBizMsg.setLastDeployDate(currDate);
			if (isNew) {
				//查询最早的发布时间
				Date firstDeployDate=getFirstChangeStateDate(HouseStatusEnum.DFB.getCode(), HouseStatusEnum.YFB.getCode(), houseBaseMsg.getFid(), null);
				if(Check.NuNObj(firstDeployDate)){
					houseBizMsg.setFirstDeployDate(currDate);
				} else {
					houseBizMsg.setFirstDeployDate(firstDeployDate);
				}
			}
		} else if (status == HouseStatusEnum.SJ.getCode()) {
			houseBizMsg.setLastUpDate(currDate);
			//查询最早的一次上架时间
			Date firstUpDate=getFirstChangeStateDate(HouseStatusEnum.YFB.getCode(), HouseStatusEnum.SJ.getCode(), houseBaseMsg.getFid(), null);
			if (isNew) {
				if(Check.NuNObj(firstUpDate)){
					houseBizMsg.setFirstUpDate(currDate);
				} else {
					houseBizMsg.setFirstUpDate(firstUpDate);
				}
				//新建的话首次发布时间录入
				Date firstDeployDate=getFirstChangeStateDate(HouseStatusEnum.DFB.getCode(), HouseStatusEnum.YFB.getCode(), houseBaseMsg.getFid(), null);
				if(Check.NuNObj(firstDeployDate)){
					houseBizMsg.setFirstDeployDate(currDate);
				} else {
					houseBizMsg.setFirstDeployDate(firstDeployDate);
				}
			//首次上架时间为空
			} else if(Check.NuNObj(houseBizMsg.getFirstUpDate())){
				if(Check.NuNObj(firstUpDate)){
					houseBizMsg.setFirstUpDate(currDate);
				} else {
					houseBizMsg.setFirstUpDate(firstUpDate);
				}
			}
		} else if (status == HouseStatusEnum.ZPSHWTG.getCode()) {
			houseBizMsg.setRefuseDate(currDate);
			if (isEmpty) {
				paramMap = new HashMap<String, Object>();
			}
			String refuseReason = Check.NuNObj(paramMap.get("auditCause")) ? "" : (String) paramMap.get("auditCause");
			houseBizMsg.setRefuseReason(refuseReason );
			String refuseMark = Check.NuNObj(paramMap.get("remark")) ? "" : (String) paramMap.get("remark");
			houseBizMsg.setRefuseMark(refuseMark);
		}
		
		if (isNew) {
			houseBizMsgDao.insertHouseBizMsg(houseBizMsg);
		} else {
			houseBizMsgDao.updateHouseBizMsgByHouseFid(houseBizMsg);
		}
	}
	
	/**
	 * 
	 * 查询首次状态变化时间
	 *
	 * @author bushujie
	 * @created 2017年6月22日 下午4:40:30
	 *
	 * @param fromStatus
	 * @param toStatus
	 * @param houseFid
	 * @param roomFid
	 * @return
	 */
	private Date getFirstChangeStateDate(Integer fromStatus,Integer toStatus,String houseFid,String roomFid){
		Map<String, Object> paMap=new HashMap<String, Object>();
		paMap.put("fromStatus", fromStatus);
		paMap.put("toStatus", toStatus);
		paMap.put("houseFid", houseFid);
		if(!Check.NuNStr(roomFid)){
			paMap.put("roomFid", roomFid);
		}
		HouseOperateLogEntity houseOperateLogEntity=houseOperateLogDao.findFirstChangeStauts(paMap);
		if(!Check.NuNObj(houseOperateLogEntity)){
			return houseOperateLogEntity.getCreateDate();
		} else {
			return null;
		}
	}

	/**
	 * 判断是否品质审核未通过修改房间信息 
	 * 1.如果是品质审核未通过修改,级联修改房间/床位状态,返回true
	 * 2.如果非品质审核未通过修改,直接返回false
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param roomFid
	 * @return
	 */
	public boolean updateRoomStatus(String roomFid) {
		HouseRoomMsgEntity houseRoomMsg = houseRoomMsgDao.getHouseRoomByFid(roomFid);
		return updateRoomStatus(houseRoomMsg);
	}
	
	/**
	 * 判断是否品质审核未通过修改房间信息 
	 * 1.如果是品质审核未通过修改,级联修改房间/床位状态,返回true
	 * 2.如果非品质审核未通过修改,直接返回false
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param houseRoomMsg
	 * @return
	 */
	public boolean updateRoomStatus(HouseRoomMsgEntity houseRoomMsg) {
		if (!Check.NuNObj(houseRoomMsg) && !Check.NuNObj(houseRoomMsg.getHouseBaseFid())) {
			HouseBaseMsgEntity houseBaseMsg = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseRoomMsg.getHouseBaseFid());
			Integer rentWay = houseBaseMsg.getRentWay();
			Integer roomStatus = houseRoomMsg.getRoomStatus();
			if (RentWayEnum.ROOM.getCode() == rentWay.intValue() && !Check.NuNObj(roomStatus)
					&& HouseStatusEnum.ZPSHWTG.getCode() == roomStatus.intValue()) {
				this.saveRoomOperateLogByLandlord(houseRoomMsg, HouseStatusEnum.YFB.getCode(), houseBaseMsg.getLandlordUid());
				this.cascadingRoomStatus(houseRoomMsg, HouseStatusEnum.YFB.getCode());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 业务保存房间操作日志
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:30:17
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @param status @HouseStatusEnum
	 */
	public void saveRoomOperateLogByBiz(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap, int status) {
		HouseOperateLogEntity roomOperateLog = new HouseOperateLogEntity();
		roomOperateLog.setFid(UUIDGenerator.hexUUID());
		roomOperateLog.setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
		roomOperateLog.setRoomFid(houseRoomMsg.getFid());
		roomOperateLog.setFromStatus(houseRoomMsg.getRoomStatus());
		roomOperateLog.setToStatus(status);
		roomOperateLog.setOperateType(HouseConstant.HOUSE_LOG_OPERATE_TYPE_BUSINESS);
		roomOperateLog.setCreateDate(new Date());
		if (!Check.NuNMap(paramMap)) {
			roomOperateLog.setRemark((String) paramMap.get("remark"));
			roomOperateLog.setAuditCause((String) paramMap.get("auditCause"));
			roomOperateLog.setCreateFid((String) paramMap.get("operaterFid"));
		}
		houseOperateLogDao.insertHouseOperateLog(roomOperateLog);
		
		// 同步房间业务信息
		this.mergeRoomBizMsg(houseRoomMsg, paramMap, status);
		LogUtil.info(LOGGER, "houseRoomFid={},fromStatus={},toStatus={}", houseRoomMsg.getFid(), houseRoomMsg.getRoomStatus(), status);
	}
	
	/**
	 * 房东保存房间操作日志
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:30:17
	 *
	 * @param houseRoomMsg
	 * @param status @HouseStatusEnum
	 * @param operaterFid 
	 */
	public void saveRoomOperateLogByLandlord(HouseRoomMsgEntity houseRoomMsg, int status, String operaterFid) {
		HouseOperateLogEntity roomOperateLog = new HouseOperateLogEntity();
		roomOperateLog.setFid(UUIDGenerator.hexUUID());
		roomOperateLog.setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
		roomOperateLog.setRoomFid(houseRoomMsg.getFid());
		roomOperateLog.setFromStatus(houseRoomMsg.getRoomStatus());
		roomOperateLog.setToStatus(status);
		roomOperateLog.setOperateType(HouseConstant.HOUSE_LOG_OPERATE_TYPE_LANDLORD);
		roomOperateLog.setCreateFid(operaterFid);
		roomOperateLog.setCreateDate(new Date());
		houseOperateLogDao.insertHouseOperateLog(roomOperateLog);
		
		// 同步房间业务信息
		this.mergeRoomBizMsg(houseRoomMsg, null, status);
		LogUtil.info(LOGGER, "houseRoomFid={},fromStatus={},toStatus={}", houseRoomMsg.getFid(), houseRoomMsg.getRoomStatus(), status);
	}

	/**
	 * 级联更新房间床位状态
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午5:22:15
	 *
	 * @param houseRoomMsg
	 * @param status
	 */
	public int cascadingRoomStatus(HouseRoomMsgEntity houseRoomMsg, int status) {
		int count = 0;
		List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomMsg.getFid());
		for (HouseBedMsgEntity houseBedMsg : bedList) {
			houseBedMsg.setBedStatus(status);
			houseBedMsg.setLastModifyDate(new Date());
			count += houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
		}
		return count;
	}

	/**
	 * 同步房间业务信息
	 *
	 * @author liujun
	 * @created 2017年2月28日
	 *
	 * @param houseRoomMsg
	 * @param paramMap
	 * @param status
	 */
	public void mergeRoomBizMsg(HouseRoomMsgEntity houseRoomMsg, Map<String, Object> paramMap, int status) {
		HouseBizMsgEntity houseBizMsg = houseBizMsgDao.getHouseBizMsgByRoomFid(houseRoomMsg.getFid());
		boolean isNew = false;// 业务表是否新建
		boolean isEmpty = false;// paramMap是否为空
		Date currDate = new Date();
		if (Check.NuNObj(houseBizMsg)) {
			isNew = true;
			houseBizMsg = new HouseBizMsgEntity();
			houseBizMsg.setFid(UUIDGenerator.hexUUID());
			houseBizMsg.setHouseBaseFid(houseRoomMsg.getHouseBaseFid());
			houseBizMsg.setRoomFid(houseRoomMsg.getFid());
			houseBizMsg.setRentWay(RentWayEnum.ROOM.getCode());
			houseBizMsg.setCreateDate(currDate);
			if (Check.NuNMap(paramMap)) {
				isEmpty = true;
			} else {
				houseBizMsg.setCreateFid((String) paramMap.get("operaterFid"));
			}
		}
		
		if (status == HouseStatusEnum.YFB.getCode()) {
			houseBizMsg.setLastDeployDate(currDate);
			if (isNew) {
				//查询最早的发布时间
				Date firstDeployDate=getFirstChangeStateDate(HouseStatusEnum.DFB.getCode(), HouseStatusEnum.YFB.getCode(), houseRoomMsg.getHouseBaseFid(), houseRoomMsg.getFid());
				if(Check.NuNObj(firstDeployDate)){
					houseBizMsg.setFirstDeployDate(currDate);
				} else {
					houseBizMsg.setFirstDeployDate(firstDeployDate);
				}
			}
		} else if (status == HouseStatusEnum.SJ.getCode()) {
			houseBizMsg.setLastUpDate(currDate);
			//查询最早的上架时间
			Date firstUpDate=getFirstChangeStateDate(HouseStatusEnum.YFB.getCode(), HouseStatusEnum.SJ.getCode(), houseRoomMsg.getHouseBaseFid(), houseRoomMsg.getFid());
			if (isNew) {
				if(Check.NuNObj(firstUpDate)){
					houseBizMsg.setFirstUpDate(currDate);
				} else {
					houseBizMsg.setFirstUpDate(firstUpDate);
				}
				//新建的话录入首次发布时间
				Date firstDeployDate=getFirstChangeStateDate(HouseStatusEnum.DFB.getCode(), HouseStatusEnum.YFB.getCode(), houseRoomMsg.getHouseBaseFid(), houseRoomMsg.getFid());
				if(Check.NuNObj(firstDeployDate)){
					houseBizMsg.setFirstDeployDate(currDate);
				} else {
					houseBizMsg.setFirstDeployDate(firstDeployDate);
				}
			} else if(Check.NuNObj(houseBizMsg.getFirstUpDate())) {
				if(Check.NuNObj(firstUpDate)){
					houseBizMsg.setFirstUpDate(currDate);
				} else {
					houseBizMsg.setFirstUpDate(firstUpDate);
				}
			}
		} else if (status == HouseStatusEnum.ZPSHWTG.getCode()) {
			houseBizMsg.setRefuseDate(currDate);
			if (isEmpty) {
				paramMap = new HashMap<String, Object>();
			}
			String refuseReason = Check.NuNObj(paramMap.get("auditCause")) ? "" : (String) paramMap.get("auditCause");
			houseBizMsg.setRefuseReason(refuseReason );
			String refuseMark = Check.NuNObj(paramMap.get("remark")) ? "" : (String) paramMap.get("remark");
			houseBizMsg.setRefuseMark(refuseMark);
		}
		
		if (isNew) {
			houseBizMsgDao.insertHouseBizMsg(houseBizMsg);
		} else {
			houseBizMsgDao.updateHouseBizMsgByRoomFid(houseBizMsg);
		}
	}
}
