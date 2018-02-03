/**
 * @FileName: HouseBusinessServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2016年7月6日 下午10:20:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBusinessMsgEntity;
import com.ziroom.minsu.entity.house.HouseBusinessMsgExtEntity;
import com.ziroom.minsu.entity.house.HouseBusinessSourceEntity;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseBusinessMsgDao;
import com.ziroom.minsu.services.house.dao.HouseBusinessMsgExtDao;
import com.ziroom.minsu.services.house.dao.HouseBusinessSourceDao;
import com.ziroom.minsu.services.house.dto.HouseBusinessDto;
import com.ziroom.minsu.services.house.dto.HouseBusinessInputDto;
import com.ziroom.minsu.services.house.dto.HouseBusinessMsgExtDto;
import com.ziroom.minsu.services.house.entity.HouseBusinessListVo;

/**
 * <p>房源商机业务实现</p>
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
@Service("house.houseBusinessServiceImpl")
public class HouseBusinessServiceImpl {
	
	@Resource(name="house.houseBusinessMsgDao")
	private HouseBusinessMsgDao houseBusinessMsgDao;
	
	@Resource(name="house.houseBusinessMsgExtDao")
	private HouseBusinessMsgExtDao houseBusinessMsgExtDao;
	
	@Resource(name="house.houseBusinessSourceDao")
	private HouseBusinessSourceDao houseBusinessSourceDao;
	
	@Resource(name="house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;
	
	
	/**
	 * 
	 * 分页查询房源商机列表
	 *
	 * @author bushujie
	 * @created 2016年7月6日 下午5:53:31
	 *
	 * @param houseBusinessDto
	 * @return
	 */
	public PagingResult<HouseBusinessListVo> findBusinessList(HouseBusinessDto houseBusinessDto){
		return houseBusinessMsgDao.findBusinessList(houseBusinessDto);
	}
	
	/**
	 * 
	 *根据房东查询地推管家员工号
	 *
	 * @author bushujie
	 * @created 2016年7月8日 上午11:31:51
	 *
	 * @param landlordName
	 * @param landlordMobile
	 * @return
	 */
	public String findDtGuardCodeByLandlord(String landlordMobile ){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("landlordMobile", landlordMobile);
		return houseBusinessMsgExtDao.findDtGuardCodeByLandlord(paramMap);
	}
	
	/**
	 * 
	 * 插入房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月8日 下午3:19:33
	 *
	 * @param houseBusinessInputDto
	 */
	public void insertHouseBusiness(HouseBusinessInputDto houseBusinessInputDto){
		//判断房源关联商机是否存在
		if(!Check.NuNStr(houseBusinessInputDto.getBusinessMsg().getHouseBaseFid())){
			HouseBusinessMsgEntity businessMsgEntity=houseBusinessMsgDao.findBusinessMsgByHouseFid(houseBusinessInputDto.getBusinessMsg().getHouseBaseFid());
			if(!Check.NuNObj(businessMsgEntity)){
				return;
			}
		}
		HouseBusinessMsgEntity houseBusinessMsgEntity=houseBusinessInputDto.getBusinessMsg();
		houseBusinessMsgEntity.setFid(UUIDGenerator.hexUUID());
		//生成商机编码
		houseBusinessMsgEntity.setBusniessSn(houseBusinessMsgEntity.getCityCode()+System.currentTimeMillis()+"S");
		HouseBusinessMsgExtEntity houseBusinessMsgExtEntity=houseBusinessInputDto.getBusinessExt();
		houseBusinessMsgExtEntity.setFid(UUIDGenerator.hexUUID());
		houseBusinessMsgExtEntity.setBusinessFid(houseBusinessMsgEntity.getFid());
		HouseBusinessSourceEntity houseBusinessSourceEntity=houseBusinessInputDto.getBusinessSource();
		houseBusinessSourceEntity.setBusinessFid(houseBusinessMsgEntity.getFid());
		houseBusinessSourceEntity.setFid(UUIDGenerator.hexUUID());
		//保存房源商机主表信息
		houseBusinessMsgDao.insertHouseBusinessMsg(houseBusinessMsgEntity);
		//保存房源商机扩展表信息
		houseBusinessMsgExtDao.insertBusinessMsgExtEntity(houseBusinessMsgExtEntity);
		//保存房源商机来源表信息
		houseBusinessSourceDao.insertHouseBusinessSourceEntity(houseBusinessSourceEntity);
	}
	
	/**
	 * 
	 * 查询房源商机详情
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午12:25:48
	 *
	 * @param businessFid
	 * @return
	 */
	public HouseBusinessInputDto findHouseBusinessDetailByFid(String businessFid){
		HouseBusinessInputDto houseBusinessInputDto=new HouseBusinessInputDto();
		houseBusinessInputDto.setBusinessMsg(houseBusinessMsgDao.findBusinessMsgEntityByFid(businessFid));
		houseBusinessInputDto.setBusinessExt(houseBusinessMsgExtDao.findHouseBusinessMsgExtByBusinessFid(businessFid));
		houseBusinessInputDto.setBusinessSource(houseBusinessSourceDao.findBusinessSourceByBusinessFid(businessFid));
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getReleaseDate())){
			houseBusinessInputDto.setReleaseDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getReleaseDate(), "yyyy-MM-dd"));
		}
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getMakeCheckDate())){
			houseBusinessInputDto.setMakeCheckDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getMakeCheckDate(), "yyyy-MM-dd"));
		}
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getPutawayDate())){
			houseBusinessInputDto.setPutawayDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getPutawayDate(), "yyyy-MM-dd"));
		}
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getRealCheckDate())){
			houseBusinessInputDto.setRealCheckDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getRealCheckDate(), "yyyy-MM-dd"));
		}
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getMakePhotoDate())){
			houseBusinessInputDto.setMakePhotoDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getMakePhotoDate(), "yyyy-MM-dd"));
		}
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getRealPhotoDate())){
			houseBusinessInputDto.setRealPhotoDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getRealPhotoDate(), "yyyy-MM-dd"));
		}
		if(!Check.NuNObj(houseBusinessInputDto.getBusinessMsg().getRegisterDate())){
			houseBusinessInputDto.setRegisterDate(DateUtil.dateFormat(houseBusinessInputDto.getBusinessMsg().getRegisterDate(), "yyyy-MM-dd"));
		}
		return houseBusinessInputDto;
	}
	
	/**
	 * 
	 * 更新房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午5:24:21
	 *
	 * @param houseBusinessInputDto
	 */
	public void updateHouseBusiness(HouseBusinessInputDto houseBusinessInputDto){
		houseBusinessMsgDao.updateHouseBusinessMsg(houseBusinessInputDto.getBusinessMsg());
		houseBusinessMsgExtDao.updateBusinessMsgExt(houseBusinessInputDto.getBusinessExt());
		houseBusinessSourceDao.updateHouseBusinessSource(houseBusinessInputDto.getBusinessSource());
		//判断是否更新地推管家
		if(!houseBusinessInputDto.getBusinessExt().getDtGuardCode().equals(houseBusinessInputDto.getOldDtGuardCode())&&!Check.NuNStr(houseBusinessInputDto.getBusinessExt().getLandlordMobile())){
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put("dtGuardCode", houseBusinessInputDto.getBusinessExt().getDtGuardCode());
			paramMap.put("dtGuardName", houseBusinessInputDto.getBusinessExt().getDtGuardName());
			paramMap.put("oldDtGuardCode", houseBusinessInputDto.getOldDtGuardCode());
			paramMap.put("landlordMobile",houseBusinessInputDto.getBusinessExt().getLandlordMobile());
			houseBusinessMsgExtDao.updateDtGuard(paramMap);
		}
	}
	
	/**
	 * 
	 * 删除房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午5:53:27
	 *
	 * @param businessFid
	 */
	public void delHouseBusiness(String businessFid){
		HouseBusinessMsgEntity houseBusinessMsgEntity=new HouseBusinessMsgEntity();
		houseBusinessMsgEntity.setFid(businessFid);
		houseBusinessMsgEntity.setIsDel(1);
		houseBusinessMsgDao.updateHouseBusinessMsg(houseBusinessMsgEntity);
		houseBusinessSourceDao.delHouseBusinessSource(businessFid);
	}
	
	 /**
     * 
     * 条件查询 商机扩展信息
     * 
     * 查询对象不能为null
     *
     * @author yd
     * @created 2016年7月9日 下午2:27:25
     *
     * @param houseBusinessMsgExtDto
     * @return
     */
    public List<HouseBusinessMsgExtEntity>  findHouseBusExtByCondition(HouseBusinessMsgExtDto houseBusinessMsgExtDto){
    	return houseBusinessMsgExtDao.findHouseBusExtByCondition(houseBusinessMsgExtDto);
    }
    
    /**
     * 
     * 查询房东uid对应房源数量
     *
     * @author bushujie
     * @created 2016年7月22日 下午3:09:15
     *
     * @param uid
     * @return
     */
    public int findHouseCountByUid(String uid){
    	return houseBaseMsgDao.findHouseCountByUid(uid);
    }
}
