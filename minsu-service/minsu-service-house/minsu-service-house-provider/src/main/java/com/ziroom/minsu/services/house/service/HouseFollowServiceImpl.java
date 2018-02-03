/**
 * @FileName: HouseFollowServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2017年2月22日 下午8:08:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseFollowEntity;
import com.ziroom.minsu.entity.house.HouseFollowLogEntity;
import com.ziroom.minsu.entity.house.HouseLockEntity;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dao.HouseFollowDao;
import com.ziroom.minsu.services.house.dao.HouseFollowLogDao;
import com.ziroom.minsu.services.house.dao.HouseLockDao;
import com.ziroom.minsu.services.house.dto.HouseFollowDto;
import com.ziroom.minsu.services.house.dto.HouseFollowLogDto;
import com.ziroom.minsu.services.house.dto.HouseFollowSaveDto;
import com.ziroom.minsu.services.house.dto.HouseFollowUpdateDto;
import com.ziroom.minsu.services.house.entity.HouseFollowVo;
import com.ziroom.minsu.valenum.house.FollowStatusEnum;
import com.ziroom.minsu.valenum.house.FollowTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>房源跟进业务实现</p>
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
@Service("house.houseFollowServiceImpl")
public class HouseFollowServiceImpl {
	
	/**
	 * 日志对象
	 */
	private final static Logger logger = LoggerFactory.getLogger(HouseFollowServiceImpl.class);
	
	@Resource(name="house.houseFollowDao")
	private HouseFollowDao houseFollowDao;
	
	@Resource(name="house.houseFollowLogDao")
	private HouseFollowLogDao houseFollowLogDao;
	
	@Resource(name="house.houseLockDao")
	private HouseLockDao houseLockDao;
	
	
	/**
	 * 
	 * 插入房源跟进主表
	 *
	 * @author bushujie
	 * @created 2017年2月22日 下午8:14:34
	 *
	 * @param houseFollowEntity
	 */
	public void insertHouseFollow(HouseFollowEntity houseFollowEntity){
		houseFollowDao.insertHouseFollow(houseFollowEntity);
	}
	
	/**
	 * 
	 *  查询满足客服跟进条件，未生成客服跟进未审核通过房源的记录（条件：未通过原因非房源品质原因且审核未通过超过12小时）
	 *
	 * @author bushujie
	 * @created 2017年2月22日 下午8:17:04
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseFollowVo> findServicerFollowHouseList(Map<String, Object> paramMap){
		return houseFollowDao.findServicerFollowHouseList(paramMap);
	}
	
	/**
	 * 
	 *  分页查询客服跟进未审核通过房源列表
	 *
	 * @author bushujie
	 * @created 2017年2月24日 上午10:36:18
	 *
	 * @param houseFollowDto
	 * @return
	 */
	public Map<String, Object> findServicerFollowHouseList(HouseFollowDto houseFollowDto){
		List<HouseFollowVo> dataList=new ArrayList<HouseFollowVo>();
		//分页查询客服跟进未审核通过房源房东列表
		PagingResult<HouseFollowVo> result=houseFollowDao.findServicerFollowLandlordList(houseFollowDto);
		for(HouseFollowVo vo:result.getRows()){
			houseFollowDto.setLandlordUid(vo.getLandlordUid());
			//房东下客服跟进房源列表
			dataList.addAll(houseFollowDao.findServicerFollowHouseListByLandlord(houseFollowDto));
		}
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		resultMap.put("count", result.getTotal());
		return resultMap;
	}
	
	/**
	 * 
	 * 客服锁并且为没有主跟进记录表添加记录
	 *
	 * @author bushujie
	 * @created 2017年2月25日 下午2:58:38
	 *
	 * @param followSaveDto
	 * @return
	 */
	public HouseFollowEntity lockAndSaveHouseFollow(HouseFollowSaveDto followSaveDto,DataTransferObject dto){
		String followFid=followSaveDto.getFollowFid();
		HouseFollowEntity houseFollowEntity=null;
		//如果主跟进记录不存在，保存主跟进记录
		if (Check.NuNStr(followFid)||followFid.equals("undefined")) {
			if(followSaveDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				houseFollowEntity=houseFollowDao.findHouseFollowMsgZ(followSaveDto.getHouseSn());
				followFid=MD5Util.MD5Encode(houseFollowEntity.getHouseBaseFid()+DateUtil.dateFormat(houseFollowEntity.getAuditStatusTime(), "yyyyMMddHHmmss"));
			} else if(followSaveDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
				houseFollowEntity=houseFollowDao.findHouseFollowMsgF(followSaveDto.getHouseSn());
				followFid=MD5Util.MD5Encode(houseFollowEntity.getRoomFid()+DateUtil.dateFormat(houseFollowEntity.getAuditStatusTime(), "yyyyMMddHHmmss"));
			}
			houseFollowEntity.setFid(followFid);
			houseFollowEntity.setFollowType(FollowTypeEnum.FYWSHTG.getCode());
			houseFollowEntity.setFollowStatus(FollowStatusEnum.KFDGJ.getCode());
			houseFollowEntity.setCreateFid(followSaveDto.getCreateFid());
			if(houseFollowDao.getHouseFollowByKey(followFid)==null){
				houseFollowDao.insertHouseFollow(houseFollowEntity);
			}
		}
		dto.putValue("followFid",followFid);
		//客服锁房源跟进记录
		//查询是否有锁存在
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("lockedFid", followFid);
		paramMap.put("lockerFid", followSaveDto.getCreateFid());
		paramMap.put("houseLockCode", followSaveDto.getHouseLockCode());
		paramMap.put("lockFailDate", DateUtil.dateFormat(new Date(), "yyyyMMddHHmm"));
		List<HouseLockEntity> lockList=houseLockDao.findHouseLock(paramMap);
		if(!Check.NuNCollection(lockList)){
			dto.putValue("lockerFid", lockList.get(0).getLockerFid());
		} else {
			houseLockDao.deleteHouseLock(paramMap);
			HouseLockEntity houseLockEntity=new HouseLockEntity();
			houseLockEntity.setHouseLockCode(followSaveDto.getHouseLockCode());
			houseLockEntity.setLockerFid(followSaveDto.getCreateFid());
			houseLockEntity.setLockedFid(followFid);
			houseLockEntity.setLockFailDate(DateUtil.dateFormat(DateUtils.addMinutes(new Date(), HouseConstant.HOUSE_LOCK_WAIT_MINUTE),"yyyyMMddHHmm"));
			houseLockEntity.setFid(MD5Util.MD5Encode(houseLockEntity.getHouseLockCode()+houseLockEntity.getLockedFid()+houseLockEntity.getLockFailDate()));
			houseLockEntity.setCreateFid(followSaveDto.getCreateFid());
			houseLockDao.insertHouseLock(houseLockEntity);
		}
		return houseFollowEntity;
	}
	
	/**
	 * 
	 * 查询房源跟进详情
	 *
	 * @author bushujie
	 * @created 2017年2月27日 上午11:44:02
	 *
	 * @param houseFollowSaveDto
	 * @return
	 */
	public HouseFollowVo houseFollowDetail(HouseFollowSaveDto houseFollowSaveDto){
		if(houseFollowSaveDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
			return houseFollowDao.findHouseFollowDetailZ(houseFollowSaveDto.getFollowFid());
		} else if(houseFollowSaveDto.getRentWay()==RentWayEnum.ROOM.getCode()){
			return houseFollowDao.findHouseFollowDetailF(houseFollowSaveDto.getFollowFid());
		}
		return null;
	}
	
	/**
	 * 
	 * 插入房源跟进明细
	 *
	 * @author bushujie
	 * @created 2017年2月27日 下午8:13:50
	 *
	 * @param houseFollowLogEntity
	 */
	public void insertHouseFollowLog(HouseFollowLogDto houseFollowLogDto){
		HouseFollowEntity houseFollowEntity=new HouseFollowEntity();
		houseFollowEntity.setFid(houseFollowLogDto.getFollowFid());
		houseFollowEntity.setFollowStatus(houseFollowLogDto.getToStatus());
		if(!Check.NuNObj(houseFollowLogDto.getFollowEndCause())){
			houseFollowEntity.setFollowEndCause(houseFollowLogDto.getFollowEndCause());
		}
		//更新跟进主表状态
		houseFollowDao.updateHouseFollow(houseFollowEntity);
		//插入跟进明细
		HouseFollowLogEntity houseFollowLogEntity=new HouseFollowLogEntity();
		houseFollowLogEntity.setFid(houseFollowLogDto.getFid());
		houseFollowLogEntity.setFollowDesc(houseFollowLogDto.getFollowDesc());
		houseFollowLogEntity.setFollowEmpCode(houseFollowLogDto.getFollowEmpCode());
		houseFollowLogEntity.setFollowEmpName(houseFollowLogDto.getFollowEmpName());
		houseFollowLogEntity.setFollowUserFid(houseFollowLogDto.getFollowUserFid());
		houseFollowLogEntity.setFollowFid(houseFollowLogDto.getFollowFid());
		houseFollowLogEntity.setFromStatus(houseFollowLogDto.getFromStatus());
		houseFollowLogEntity.setToStatus(houseFollowLogDto.getToStatus());
		houseFollowLogDao.insertHouseFollowLog(houseFollowLogEntity);
		//删除锁记录
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("houseLockCode",houseFollowLogDto.getHouseLockCode());
		paramMap.put("lockerFid", houseFollowLogDto.getFollowUserFid());
		paramMap.put("lockedFid", houseFollowLogDto.getFollowFid());
		houseLockDao.deleteHouseLock(paramMap);
	}
	
	/**
	 * 
	 * 分页查询专员房源跟进列表
	 *
	 * @author bushujie
	 * @created 2017年2月28日 下午12:06:59
	 *
	 * @param houseFollowDto
	 * @return
	 */ 
	public Map<String, Object> findAttacheFollowHouseList(HouseFollowDto houseFollowDto){
		List<HouseFollowVo> dataList=new ArrayList<HouseFollowVo>();
		//分页查询客服跟进未审核通过房源房东列表
		PagingResult<HouseFollowVo> result=houseFollowDao.findAttacheFollowLandlordList(houseFollowDto);
		for(HouseFollowVo vo:result.getRows()){
			if(!Check.NuNObj(vo)){
				houseFollowDto.setLandlordUid(vo.getLandlordUid());
				//房东下客服跟进房源列表
				dataList.addAll(houseFollowDao.findAttacheFollowHouseListByLandlord(houseFollowDto));
			}
		
		}
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("dataList", dataList);
		resultMap.put("count", result.getTotal());
		return resultMap;
	}
	
	/**
	 * 
	 * fid查询房源跟进记录
	 *
	 * @author bushujie
	 * @created 2017年3月15日 下午9:57:32
	 *
	 * @param fid
	 * @return
	 */
	public HouseFollowEntity getHouseFollowEntityByFid(String fid){
		return houseFollowDao.getHouseFollowByKey(fid);
	}
	
	 /**
     * 
     * 后台：更新房源跟进主表
     *
     * @author bushujie
     * @created 2017年2月27日 下午8:48:38
     *
     * @param houseFollowEntity
     */
    public void updateHouseFollow(HouseFollowUpdateDto houseFollowUpdateDto){
    	
    	if(!Check.NuNObj(houseFollowUpdateDto)
    			&&!Check.NuNStr(houseFollowUpdateDto.getFid())
    			&&!Check.NuNObj(houseFollowUpdateDto.getFollowStatus())){
    	LogUtil.info(logger, "【更新房源跟进主表housefollow】fid={},followStatusOld={},followStatus={}", houseFollowUpdateDto.getFid(),houseFollowUpdateDto.getFollowStatusOld(),houseFollowUpdateDto.getFollowStatus());	
    	HouseFollowEntity houseFollowEntity = new HouseFollowEntity();
    	houseFollowEntity.setFid(houseFollowUpdateDto.getFid());
    	houseFollowEntity.setFollowStatus(houseFollowUpdateDto.getFollowStatus());
    	int i = this.houseFollowDao.updateHouseFollow(houseFollowEntity);
    	if(i>0){
    		//插入跟进明细
    		HouseFollowLogDto houseFollowLogDto =houseFollowUpdateDto.getHouseFollowLogDto();
    		
    		if(!Check.NuNObj(houseFollowLogDto)){
    			HouseFollowLogEntity houseFollowLogEntity= new HouseFollowLogEntity();
        		houseFollowLogEntity.setFid(UUIDGenerator.hexUUID());
        		houseFollowLogEntity.setFollowDesc(houseFollowLogDto.getFollowDesc());
        		houseFollowLogEntity.setFollowEmpCode(houseFollowLogDto.getFollowEmpCode());
        		houseFollowLogEntity.setFollowEmpName(houseFollowLogDto.getFollowEmpName());
        		houseFollowLogEntity.setFollowUserFid(houseFollowLogDto.getFollowUserFid());
        		houseFollowLogEntity.setFollowFid(houseFollowEntity.getFid());
        		houseFollowLogEntity.setFromStatus(houseFollowUpdateDto.getFollowStatusOld());
        		houseFollowLogEntity.setToStatus(houseFollowUpdateDto.getFollowStatus());
        		houseFollowLogDao.insertHouseFollowLog(houseFollowLogEntity);
    		}
    	
    	}
    	}
    }
}
