/**
 * @FileName: TenantHouseServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2016年4月30日 下午10:40:47
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.house.dao.*;
import com.ziroom.minsu.services.house.dto.HouseConfParamsDto;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.HousePicDto;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>客户端房源业务实现</p>
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
@Service("house.tenantHouseServiceImpl")
public class TenantHouseServiceImpl {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TenantHouseServiceImpl.class);
	
	@Resource(name="house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;
	
	@Resource(name="house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;
	
	@Resource(name="house.housePicMsgDao")
	private HousePicMsgDao housePicMsgDao;
	
	@Resource(name="house.houseConfMsgDao")
	private HouseConfMsgDao houseConfMsgDao;
	
	@Resource(name="house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;
	
	@Resource(name="house.houseStatisticsMsgDao")
	private HouseStatisticsMsgDao houseStatisticsMsgDao;
	
	@Resource(name="house.houseRoomExtDao")
	private HouseRoomExtDao houseRoomExtDao;
	/**
	 * 
	 * 客端获取房源详细信息
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午10:45:29
	 *
	 * @param houseDetailDto
	 * @return
	 */
	public TenantHouseDetailVo getHouseDetail(HouseDetailDto houseDetailDto){
		TenantHouseDetailVo tenantHouseDetailVo=houseBaseMsgDao.getHouseDetail(houseDetailDto);
		if (Check.NuNObj(tenantHouseDetailVo)) {
			return null;
		}
		HousePicMsgEntity housePicMsgEntity=housePicMsgDao.findLandlordHouseDefaultPic(houseDetailDto.getFid());
		//新默认图片未审核查询旧默认图片
		if(Check.NuNObj(housePicMsgEntity)){
			housePicMsgEntity=housePicMsgDao.findOldHouseDefaultPic(houseDetailDto.getFid());
		}
		//图片处理
		if(!Check.NuNObj(housePicMsgEntity)){
			tenantHouseDetailVo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
		}
		//出租方式赋值
		tenantHouseDetailVo.setRentWayName(RentWayEnum.getEnumMap().get(tenantHouseDetailVo.getRentWay()));
		return tenantHouseDetailVo;
	}
	
	/**
	 * 
	 * 客端获取独立房间详细信息
	 *
	 * @author bushujie
	 * @created 2016年4月30日 下午11:25:22
	 *
	 * @param houseDetailDto
	 * @return
	 */
	public TenantHouseDetailVo getHouseRoomDetail(HouseDetailDto houseDetailDto){
		TenantHouseDetailVo tenantHouseDetailVo=houseRoomMsgDao.getHouseRoomDetail(houseDetailDto);
		if (Check.NuNObj(tenantHouseDetailVo)) {
			return null;
		}
		HouseRoomExtEntity roomExtEntity = houseRoomExtDao.getByRoomfid(houseDetailDto.getFid());
		if(!Check.NuNObj(roomExtEntity)){//从roomExt获取对象为空
			this.exchageRoomExtInfo(tenantHouseDetailVo,roomExtEntity);
		}
		//图片处理
		HousePicMsgEntity housePicMsgEntity=housePicMsgDao.findLandlordRoomDefaultPic(houseDetailDto.getFid());
		//房间新默认图片未审核查询旧默认图片
		if(Check.NuNObj(housePicMsgEntity)){
			housePicMsgEntity=housePicMsgDao.findOldRoomDefaultPic(houseDetailDto.getFid());
		}
		if(!Check.NuNObj(housePicMsgEntity)){
			tenantHouseDetailVo.setDefaultPic(housePicMsgEntity.getPicBaseUrl()+housePicMsgEntity.getPicSuffix());
		}
		//出租方式赋值
		if(!Check.NuNObj(tenantHouseDetailVo)){
            //出租方式
            RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(tenantHouseDetailVo.getRentWay());
            //房间类型
            RoomTypeEnum roomTypeEnum = RoomTypeEnum.getEnumByCode(tenantHouseDetailVo.getRoomType());
            // 是独立房间，并且是共享客厅
            if (RentWayEnum.ROOM.equals(rentWayEnum) && RoomTypeEnum.HALL_TYPE.equals(roomTypeEnum)) {
                tenantHouseDetailVo.setRentWayName(RentWayEnum.HALL.getName());
            } else {
                tenantHouseDetailVo.setRentWayName(rentWayEnum.getName());
            }
		}
		return tenantHouseDetailVo;
	}
	
	/**
	 * 
	 * 将roomExt对象中的字段信息替换掉tenantHouseDetailVo中的信息
	 *
	 * @author loushuai
	 * @created 2017年6月22日 下午3:29:56
	 *
	 * @param tenantHouseDetailVo
	 * @param roomExtEntity
	 */
	public void exchageRoomExtInfo(TenantHouseDetailVo tenantHouseDetailVo, HouseRoomExtEntity roomExtEntity){
        if(!Check.NuNObj(roomExtEntity.getOrderType())){
        	tenantHouseDetailVo.setOrderType(roomExtEntity.getOrderType());
		}
		if(!Check.NuNStr(roomExtEntity.getCheckOutRulesCode())){
			tenantHouseDetailVo.setCheckOutRulesCode(roomExtEntity.getCheckOutRulesCode());
		}
		if(!Check.NuNStr(roomExtEntity.getDepositRulesCode())){
			tenantHouseDetailVo.setDepositRulesCode(roomExtEntity.getDepositRulesCode());
		}
		if(!Check.NuNStr(roomExtEntity.getRoomRules())){
			tenantHouseDetailVo.setHouseRules(roomExtEntity.getRoomRules());
		}
		if(!Check.NuNObj(roomExtEntity.getMinDay())){
			tenantHouseDetailVo.setMinDay(roomExtEntity.getMinDay());
		}
		if(!Check.NuNStr(roomExtEntity.getCheckInTime())){
			tenantHouseDetailVo.setCheckInTime(roomExtEntity.getCheckInTime());
		}
		if(!Check.NuNStr(roomExtEntity.getCheckOutTime())){
			tenantHouseDetailVo.setCheckOutTime(roomExtEntity.getCheckOutTime());
		}
	}
	/**
	 * 
	 * 房源或者房间图片列表
	 *
	 * @author bushujie
	 * @created 2016年5月2日 上午10:50:00
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public List<MinsuEleEntity> getHousePicList(String fid,Integer rentWay,Integer houseStatus){
		List<HousePicMsgEntity> list=null;
		//整租取房源图片
		HousePicDto housePicDto=new HousePicDto();
		if(rentWay==0){
			housePicDto.setHouseBaseFid(fid);
			if(HouseStatusEnum.SJ.getCode()!=houseStatus){
				list=housePicMsgDao.findHousePicMsgList(housePicDto);
			} else {
				list=housePicMsgDao.findHouseDetailPicMsgList(housePicDto);
			}
		}
		//分租取房间图片
		if(rentWay==1){
			HouseRoomMsgEntity houseRoomMsgEntity=houseRoomMsgDao.getHouseRoomByFid(fid);
			if(HouseStatusEnum.SJ.getCode()!=houseStatus){
				housePicDto.setHouseBaseFid(houseRoomMsgEntity.getHouseBaseFid());
				housePicDto.setHouseRoomFid(fid);
				list=housePicMsgDao.findHousePicMsgList(housePicDto);
			} else {
				list=housePicMsgDao.findHouseDetailPicListByRoomFid(fid,houseRoomMsgEntity.getHouseBaseFid());
			}
		}
		//查询房源基本信息 
		//HouseBaseMsgEntity houseBaseMsgEntity=houseBaseMsgDao.getHouseBaseMsgEntityByFid(housePicDto.getHouseBaseFid());
		//图片信息整合
		List<MinsuEleEntity> picList=new LinkedList<MinsuEleEntity>();
		if(!Check.NuNCollection(list)){
			for(HousePicMsgEntity pic:list){
//				if(pic.getPicType()==HousePicTypeEnum.KT.getCode()&&houseBaseMsgEntity.getHallNum()==0){
//					continue;
//				}
//				if(pic.getPicType()==HousePicTypeEnum.WSJ.getCode()&&houseBaseMsgEntity.getToiletNum()==0){
//					continue;
//				}
                MinsuEleEntity ele = new MinsuEleEntity();
                ele.setEleKey(HousePicTypeEnum.getEnumMap().get(pic.getPicType()));
                ele.setEleValue(pic.getPicBaseUrl()+pic.getPicSuffix());
				picList.add(ele);
			}
		}
		return picList;
	}
	
	/**
	 * 
	 * 房源配置信息查询
	 *
	 * @author bushujie
	 * @created 2016年5月2日 下午12:24:36
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public String getHouseDepositRulesValue(String fid,Integer rentWay,String dicCode ){
		String houseBaseFid=null;
		if(rentWay==0){
			houseBaseFid=fid;
		} else {
			houseBaseFid=houseRoomMsgDao.findHouseRoomMsgByFid(fid).getHouseBaseFid();
		}
		HouseConfParamsDto paramsDto = new HouseConfParamsDto();
		paramsDto.setDicCode(dicCode);
		paramsDto.setHouseBaseFid(houseBaseFid);
		if(rentWay == RentWayEnum.ROOM.getCode()){
			paramsDto.setRoomFid(fid);
		}
		paramsDto.setRentWay(rentWay);
		
		List<HouseConfMsgEntity> list = houseConfMsgDao.findHouseConfValidList(paramsDto);
		
		if(Check.NuNCollection(list)){
			return null;
		}
		return list.get(0).getDicVal();
	}
	
	/**
	 * 
	 * 查询房间或者房源床信息列表
	 *
	 * @author bushujie
	 * @created 2016年5月5日 下午10:18:23
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public List<HouseBedNumVo> getBedNumByHouseFid(String fid,Integer rentWay){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		if(rentWay==0){
			paramMap.put("houseBaseFid", fid);
		}else if(rentWay==1){
			paramMap.put("roomFid", fid);
		}
		return houseBedMsgDao.getBedNumByHouseFid(paramMap);
	}
	
	/**
	 * 
	 * 模糊查询房源配套设施
	 *
	 * @author bushujie
	 * @created 2016年5月5日 下午11:31:45
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public List<HouseConfVo> findHouseConfListByCode(String fid,Integer rentWay,String dicCode){
		String houseBaseFid=null;
		if(rentWay==0){
			houseBaseFid=fid;
		}else if(rentWay==1){
			HouseRoomMsgEntity room=houseRoomMsgDao.getHouseRoomByFid(fid);
			houseBaseFid=room.getHouseBaseFid();
		}
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("dicCode", dicCode);
		return houseConfMsgDao.findHouseConfVoList(paramMap);
	}
	
	/**
	 * 
	 * 保存房源浏览量统计记录
	 *
	 * @author bushujie
	 * @created 2016年5月15日 上午2:33:06
	 *
	 * @param houseStatisticsMsgEntity
	 */
	public void insertHouseStatisticsMsg(HouseStatisticsMsgEntity houseStatisticsMsgEntity){
		houseStatisticsMsgDao.insertHouseStatisticsMsg(houseStatisticsMsgEntity);
	}
	/**
	 * 
	 * 更新房源浏览量
	 *
	 * @author bushujie
	 * @created 2016年5月15日 上午2:47:59
	 *
	 * @param houseStatisticsMsgEntity
	 */
	public void updateHouseStatisticsMsgPv(HouseStatisticsMsgEntity houseStatisticsMsgEntity){
		houseStatisticsMsgDao.updateHouseStatisticsMsgPvByParam(houseStatisticsMsgEntity);
	}
	
	/**
	 * 
	 * 查询房源浏览量
	 *
	 * @author bushujie
	 * @created 2016年5月15日 上午2:57:52
	 *
	 * @param houseStatisticsMsgEntity
	 */
	public HouseStatisticsMsgEntity getHouseStatisticsMsgByParam(HouseStatisticsMsgEntity houseStatisticsMsgEntity){
		return houseStatisticsMsgDao.getHouseStatisticsMsgByParam(houseStatisticsMsgEntity);
	}
	
	/**
	 * 
	 * 获取当前房源pv
	 *
	 * @author yd
	 * @created 2016年11月28日 下午2:05:43
	 *
	 * @param houseBaseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public int getHousePv(String houseBaseFid,String roomFid,int rentWay,RedisOperations redisOperations){

		//获取缓存值
		String housePv = "0";
		if(!Check.NuNStrStrict(houseBaseFid)&&!Check.NuNObj(redisOperations)){

			HouseStatisticsMsgEntity houseStatisticsMsg = new HouseStatisticsMsgEntity();
			String key="";
			houseStatisticsMsg.setHouseBaseFid(houseBaseFid);
			houseStatisticsMsg.setRentWay(rentWay);
			if(rentWay==RentWayEnum.HOUSE.getCode()){
				key=RedisKeyConst.getHouseKey(houseBaseFid,rentWay);
			}
			if(rentWay==RentWayEnum.ROOM.getCode()&&Check.NuNStrStrict(roomFid)){
				key=RedisKeyConst.getHouseKey(houseBaseFid,roomFid,rentWay);
				houseStatisticsMsg.setRoomFid(roomFid);
			}

			try {
				housePv=redisOperations.get(key);
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误,e:{}",e);
			}

			if(!Check.NuNStr(housePv)){
				return Integer.valueOf(housePv);
			}
			housePv = "0";
			houseStatisticsMsg = this.getHouseStatisticsMsgByParam(houseStatisticsMsg);
			if(!Check.NuNObj(houseStatisticsMsg)
					&&!Check.NuNObj(houseStatisticsMsg.getHousePv())){
				return houseStatisticsMsg.getHousePv();
			}

		}

		return Integer.valueOf(housePv);
	}
}
