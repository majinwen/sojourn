/**
 * @FileName: HouseManageServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2016年4月2日 下午8:18:53
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;



import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseDayRevenueEntity;
import com.ziroom.minsu.entity.house.HouseMonthRevenueEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HousePriceConfEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.RoomMonthRevenueEntity;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.constant.HouseMessageConst;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseBedMsgDao;
import com.ziroom.minsu.services.house.dao.HouseConfMsgDao;
import com.ziroom.minsu.services.house.dao.HouseDayRevenueDao;
import com.ziroom.minsu.services.house.dao.HouseMonthRevenueDao;
import com.ziroom.minsu.services.house.dao.HouseOperateLogDao;
import com.ziroom.minsu.services.house.dao.HousePicMsgDao;
import com.ziroom.minsu.services.house.dao.HousePriceConfDao;
import com.ziroom.minsu.services.house.dao.HousePriceWeekConfDao;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;
import com.ziroom.minsu.services.house.dao.RoomMonthRevenueDao;
import com.ziroom.minsu.services.house.dto.HouseBaseExtRequest;
import com.ziroom.minsu.services.house.dto.HouseBaseListDto;
import com.ziroom.minsu.services.house.dto.HousePhyListDto;
import com.ziroom.minsu.services.house.dto.HousePriceConfDto;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.dto.LandlordRevenueDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.dto.SmartLockDto;
import com.ziroom.minsu.services.house.entity.HouseBaseListVo;
import com.ziroom.minsu.services.house.entity.HouseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseMonthRevenueVo;
import com.ziroom.minsu.services.house.entity.HouseRoomListVo;
import com.ziroom.minsu.services.house.entity.HouseRoomVo;
import com.ziroom.minsu.services.house.entity.LandlordRevenueVo;
import com.ziroom.minsu.services.house.entity.OrderNeedHouseVo;
import com.ziroom.minsu.services.house.entity.SearchTerm;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.valenum.common.WeekEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>房源管理业务层</p>
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
@Service("house.houseManageServiceImpl")
public class HouseManageServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseManageServiceImpl.class);
	
	private static final String YEAR_FORMAT_PATTERN = "yyyy";
	
	private static final String MONTH_FORMAT_PATTERN = "MM";

	@Resource(name="house.messageSource")
	private MessageSource messageSource;
	
	@Resource(name="house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;
	
	@Resource(name="house.houseRoomMsgDao")
	private HouseRoomMsgDao houseRoomMsgDao;
	
	@Resource(name="house.houseOperateLogDao")
	private HouseOperateLogDao houseOperateLogDao;
	
	@Resource(name="house.housePriceConfDao")
	private HousePriceConfDao housePriceConfDao;
	
	@Resource(name="house.houseBedMsgDao")
	private HouseBedMsgDao houseBedMsgDao;
	
	@Resource(name="house.housePicMsgDao")
	private HousePicMsgDao housePicMsgDao;
	
	@Resource(name="house.houseDayRevenueDao")
	private HouseDayRevenueDao houseDayRevenueDao;
	
	@Resource(name="house.houseMonthRevenueDao")
	private HouseMonthRevenueDao houseMonthRevenueDao;
	
	@Resource(name="house.roomMonthRevenueDao")
	private RoomMonthRevenueDao roomMonthRevenueDao;
	
	@Resource(name="house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;
	
	@Resource(name="house.housePriceWeekConfDao")
	private HousePriceWeekConfDao housePriceWeekConfDao;
	
	@Resource(name ="house.houseConfMsgDao")
	private HouseConfMsgDao houseConfMsgDao;
	
	
	/**
	 * 
	 * 下架房源操作(或者取消发布)
	 *
	 * @author bushujie
	 * @created 2016年4月2日 下午8:44:43
	 *
	 * @param houseBaseMsgEntity
	 */
	public int upDownHouse(HouseBaseMsgEntity houseBaseMsgEntity,String landlordUid,Integer toStatus){
		//Integer toStatus = HouseStatusEnum.XJ.getCode();
		
		//保存下架日志
		HouseOperateLogEntity houseOperateLogEntity=new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setHouseBaseFid(houseBaseMsgEntity.getFid());
		houseOperateLogEntity.setFromStatus(houseBaseMsgEntity.getHouseStatus());
		houseOperateLogEntity.setToStatus(toStatus);
		houseOperateLogEntity.setCreateFid(landlordUid);
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);
		
		// 级联下架该房源下所有房间
		List<HouseRoomMsgEntity> roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(houseBaseMsgEntity.getFid());
		for (HouseRoomMsgEntity houseRoomMsg : roomList) {
			// 级联下架该房间下所有床位
			List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomMsg.getFid());
			for (HouseBedMsgEntity houseBedMsg : bedList) {
				houseBedMsg.setBedStatus(toStatus);
				houseBedMsg.setLastModifyDate(new Date());
				houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
			}
			houseRoomMsg.setRoomStatus(toStatus);
			houseRoomMsg.setLastModifyDate(new Date());
			houseRoomMsgDao.updateHouseRoomMsg(houseRoomMsg);
		}
		
		//房源下架状态更新
		HouseBaseMsgEntity newBaseMsgEntity=new HouseBaseMsgEntity();
		newBaseMsgEntity.setHouseStatus(toStatus);
		newBaseMsgEntity.setFid(houseBaseMsgEntity.getFid());
		newBaseMsgEntity.setLastModifyDate(new Date());
		return houseBaseMsgDao.updateHouseBaseMsg(newBaseMsgEntity);
	}
	
	/**
	 * 
	 * 下架房间操作(或者取消发布)
	 *
	 * @author bushujie
	 * @created 2016年4月2日 下午9:38:52
	 *
	 * @param houseRoomMsgEntity
	 * @return
	 */
	public int upDownHouseRoom(HouseRoomMsgEntity houseRoomMsgEntity,String landlordUid,Integer toStatus){
		//Integer toStatus = HouseStatusEnum.XJ.getCode();
		
		//保存下架日志
		HouseOperateLogEntity houseOperateLogEntity=new HouseOperateLogEntity();
		houseOperateLogEntity.setFid(UUIDGenerator.hexUUID());
		houseOperateLogEntity.setHouseBaseFid(houseRoomMsgEntity.getHouseBaseFid());
		houseOperateLogEntity.setRoomFid(houseRoomMsgEntity.getFid());
		houseOperateLogEntity.setFromStatus(houseRoomMsgEntity.getRoomStatus());
		houseOperateLogEntity.setToStatus(toStatus);
		houseOperateLogEntity.setCreateFid(landlordUid);
		houseOperateLogDao.insertHouseOperateLog(houseOperateLogEntity);
		
		// 级联下架该房间下所有床位
		List<HouseBedMsgEntity> bedList = houseBedMsgDao.findBedListByRoomFid(houseRoomMsgEntity.getFid());
		for (HouseBedMsgEntity houseBedMsg : bedList) {
			houseBedMsg.setBedStatus(toStatus);
			houseBedMsg.setLastModifyDate(new Date());
			houseBedMsgDao.updateHouseBedMsg(houseBedMsg);
		}
		
		//房间下架操作
		HouseRoomMsgEntity newRoomMsgEntity=new HouseRoomMsgEntity();
		newRoomMsgEntity.setFid(houseRoomMsgEntity.getFid());
		newRoomMsgEntity.setRoomStatus(toStatus);
		newRoomMsgEntity.setLastModifyDate(new Date());
		return houseRoomMsgDao.updateHouseRoomMsg(newRoomMsgEntity);
	}
	
	/**
	 * 
	 * 刷新房源
	 *
	 * @author 
	 * @created 2016年4月2日 下午9:54:30
	 *
	 * @param houseBaseMsgEntity
	 * @return
	 */
	public int refreshHouse(String houseBaseFid){
		HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setFid(houseBaseFid);
		houseBaseMsgEntity.setRefreshDate(new Date());
		return houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
	}
	
	/**
	 * 
	 * 查询房源基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午12:14:00
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseBaseMsgEntity getHouseBaseMsgEntityByFid(String houseBaseFid){
        return houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
    }

	/**
	 * 根据房源编号集合获取房源信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 11:25
	 */
	public List<HouseBaseMsgEntity> getHouseBaseListByHouseSns(List<String> houseSns){
		return houseBaseMsgDao.getHouseBaseListByHouseSns(houseSns);
	}

	/**
	 * 根据你房间编号集合查询房间信息集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/11/21 14:19
	 */
	public List<HouseRoomMsgEntity> getRoomBaseListByRoomSns(List<String> roomSns){
		return houseRoomMsgDao.getRoomBaseListByRoomSns(roomSns);
	}


    /**
     * 通过房间id获取当前房源的信息
     * @author afi
     * @param roomFid
     * @return
     */
    public HouseBaseMsgEntity getHouseBaseMsgEntityByRoomFid(String roomFid){
        return houseBaseMsgDao.getHouseBaseMsgEntityByRoomFid(roomFid);
    }


    /**
	 * 
	 * 条件查询房源列表
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午6:05:44
	 *
	 * @param HouseBaseListDto
	 * @return
	 */
	public PagingResult<HouseBaseListVo> findHouseBaseList(HouseBaseListDto houseBaseListDto){
		return houseBaseMsgDao.findHouseBaseList(houseBaseListDto);
	}
	
	/**
	 * 
	 * 条件查询房间列表
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午6:29:39
	 *
	 * @param HouseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomListVo> findHouseRoomList(HouseBaseListDto houseBaseListDto){
		return houseRoomMsgDao.findHouseRoomList(houseBaseListDto);	
	}


    /**
     * 通过房源获取当前房源下的房间列表
     * @author afi
     * @param houseFid
     * @return
     */
    public List<HouseRoomListVo>  getRoomListByHouseFid(String houseFid){
        return houseRoomMsgDao.getRoomListByHouseFid(houseFid);
    }

	/**
	 * 
	 * 获取房间详细信息
	 *
	 * @author bushujie
	 * @created 2016年4月3日 下午10:05:33
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HouseRoomMsgEntity getHouseRoomByFid(String houseRoomFid){
		return houseRoomMsgDao.getHouseRoomByFid(houseRoomFid);
	}
	
	/**
	 * 
	 * 房源或者房间特殊价格获取
	 *
	 * @author bushujie
	 * @created 2016年4月5日 上午10:34:45
	 *
	 * @param leaseCalendarDto
	 * @return
	 */
	public List<SpecialPriceVo> findSpecialPriceList(LeaseCalendarDto leaseCalendarDto){
		
		//查询房源或者房间特殊价格
		List<SpecialPriceVo> specialPriceList= housePriceConfDao.findSpecialPriceList(leaseCalendarDto);
		
		//查询房源或者房间所有的周末价格
		List<HousePriceWeekConfEntity> weekPriceList=null;
		if (RentWayEnum.ROOM.getCode()==leaseCalendarDto.getRentWay()) {
			weekPriceList=housePriceWeekConfDao.findSpecialPriceList(null, leaseCalendarDto.getHouseRoomFid(),leaseCalendarDto.getIsValid());
		}else {
			weekPriceList=housePriceWeekConfDao.findSpecialPriceList(leaseCalendarDto.getHouseBaseFid(),null,leaseCalendarDto.getIsValid());
		}
		
		if(Check.NuNObj(weekPriceList) ||weekPriceList.size()==0 ){
			return specialPriceList;
		}
		
		Map<String, SpecialPriceVo> specialMap= new HashMap<>();
		String format = "yyyy-MM-dd";
		//将房源或者房间特殊价格转化为map
		if(!Check.NuNObj(specialPriceList) ||specialPriceList.size()>0 ){
			 for (SpecialPriceVo specialPriceVo:specialPriceList) {
				if (!Check.NuNObj(specialPriceVo) && !Check.NuNObj(specialPriceVo.getSetPrice()) ) {
					specialMap.put(DateUtil.dateFormat(specialPriceVo.getSetDate(), format), specialPriceVo);
				}
			}
		}
		
		//没有设置特殊价格的按照起止时间补充周末特殊价格
		Calendar cursor = Calendar.getInstance(); 
		cursor.setTime(leaseCalendarDto.getStartDate());
		for (; ;) { 
			if(cursor.getTime().after(leaseCalendarDto.getEndDate())){ 
				break;
			}
			if(Check.NuNObj(specialMap.get(DateUtil.dateFormat(cursor.getTime(), format)))){
				for(HousePriceWeekConfEntity weekPrice:weekPriceList){
					if (!Check.NuNObj(weekPrice) && !Check.NuNObj(weekPrice.getPriceVal())) { 
						if(WeekEnum.getWeek(cursor.getTime()).getNumber()==weekPrice.getSetWeek()){
							if(Check.NuNObj(specialMap.get(DateUtil.dateFormat(cursor.getTime(), format)))){//只添加当天最新的规则
								SpecialPriceVo addPriceVo = new SpecialPriceVo();
								addPriceVo.setSetDate(cursor.getTime());
								addPriceVo.setSetPrice(weekPrice.getPriceVal());
								specialMap.put(DateUtil.dateFormat(addPriceVo.getSetDate(), format), addPriceVo); 
							}
						}
					}
				}
			}
			
			cursor.add(Calendar.DATE, 1);
		}
		
		return new ArrayList<SpecialPriceVo>(specialMap.values()); 
	}
	
	/**
	 * 
	 * 设置房源房间特殊价格
	 *
	 * @author bushujie
	 * @created 2016年4月5日 下午12:04:57
	 *
	 * @param housePriceConfEntity
	 */
	public void setSpecialPrice(HousePriceConfDto housePriceConfDto){
		//存在更新
		HousePriceConfEntity housePriceConf = housePriceConfDao.findHousePriceConfByDate(housePriceConfDto);
		if(Check.NuNObj(housePriceConf)){
			//新增
			housePriceConfDto.setFid(UUIDGenerator.hexUUID());
			housePriceConfDao.insertHousePriceConf(housePriceConfDto);
		}else{
			//更新
			housePriceConfDto.setFid(housePriceConf.getFid());
			housePriceConfDto.setCreateUid(null);
			housePriceConfDao.updateHousePriceConf(housePriceConfDto);
		}
	}

    /**
     * 获取当前的特殊价格
     * @author afi
     * @param housePriceConfDto
     * @return
     */
    public HousePriceConfEntity findHousePriceConfByDate(HousePriceConfDto housePriceConfDto){
        return housePriceConfDao.findHousePriceConfByDate(housePriceConfDto);
    }
    
    /**
     * 查询某天是否按照星期配置了特殊价格，并返回
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param leaseCalendarDto
     * @return
     */
    public HousePriceWeekConfEntity findHousePriceWeekConfByDate(HousePriceWeekConfDto weekPriceConfDto ){
    	
    	if (Check.NuNObj(weekPriceConfDto.getDay())) {
			return null;
		}
    	
    	HousePriceWeekConfEntity entity=null;
    	if(RentWayEnum.HOUSE.getCode()==weekPriceConfDto.getRentWay()){
    		entity=housePriceWeekConfDao.findHousePriceWeekConfByDate(weekPriceConfDto.getHouseBaseFid(), null, weekPriceConfDto.getDay());
		} else if(RentWayEnum.ROOM.getCode()==weekPriceConfDto.getRentWay()) {
			entity=housePriceWeekConfDao.findHousePriceWeekConfByDate(null, weekPriceConfDto.getHouseRoomFid(), weekPriceConfDto.getDay());
		}
    	
        return entity;
    }
    
    /**
     * 按照星期设置特殊价格
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param housePriceWeekConf
     * @return
     */
    public int saveHousePriceWeekConf(HousePriceWeekConfDto weekPriceConfDto) {
    	LeaseCalendarDto leaseCalendarDto = new LeaseCalendarDto();
    	leaseCalendarDto.setHouseBaseFid(weekPriceConfDto.getHouseBaseFid());
    	leaseCalendarDto.setHouseRoomFid(weekPriceConfDto.getHouseRoomFid());
    	leaseCalendarDto.setStartDate(weekPriceConfDto.getStartDate());
		leaseCalendarDto.setEndDate(weekPriceConfDto.getEndDate());
		leaseCalendarDto.setRentWay(weekPriceConfDto.getRentWay());
		
		//查询房源或者房间特殊价格
		List<SpecialPriceVo> specialPriceList = housePriceConfDao.findSpecialPriceList(leaseCalendarDto);
		if (!Check.NuNCollection(specialPriceList)) {// 按照周末规则更新特殊价格
			Set<Integer> weeks = weekPriceConfDto.getSetWeeks();// 要更新的星期
			for (SpecialPriceVo specialPrice : specialPriceList) {
				if (weeks.contains(WeekEnum.getWeek(specialPrice.getSetDate()).getNumber())) {
					housePriceConfDao.deleteHousePriceConfByFid(specialPrice.getFid());// 删除设置星期的特殊价格
				}
			}
		}
	 
		if (RentWayEnum.HOUSE.getCode() == weekPriceConfDto.getRentWay()) {
			return housePriceWeekConfDao.saveHousePriceWeekConf(weekPriceConfDto.getCreateUid(), weekPriceConfDto.getHouseBaseFid(),
					null, weekPriceConfDto.getWeeksList(), weekPriceConfDto.getPriceVal(),leaseCalendarDto.getIsValid());
		} else if (RentWayEnum.ROOM.getCode() == weekPriceConfDto.getRentWay()) {
			return housePriceWeekConfDao.saveHousePriceWeekConf(weekPriceConfDto.getCreateUid(), weekPriceConfDto.getHouseBaseFid(),
					weekPriceConfDto.getHouseRoomFid(), weekPriceConfDto.getWeeksList(), weekPriceConfDto.getPriceVal(),leaseCalendarDto.getIsValid());
		}
    	return 0; 
    }



	/**
	 * 
	 * 查询房源详情
	 *
	 * @author bushujie
	 * @created 2016年4月6日 上午12:12:33
	 *
	 * @param houseBaseFid
	 * @param landlordUid
	 * @return
	 */
	public HouseDetailVo findHouseDetail(String houseBaseFid, String landlordUid){
		return houseBaseMsgDao.findHouseDetail(houseBaseFid, landlordUid);
	}
	
	public HouseBaseMsgEntity getHouseBaseMsgEntityByLandlordUid(String landlordUid){
		HouseBaseMsgEntity houseBaseMsg = null;
		houseBaseMsg = houseBaseMsgDao.findHouseWholeByLandlordUid(landlordUid);
		if(Check.NuNObj(houseBaseMsg)){
			houseBaseMsg = houseBaseMsgDao.findHouseSubletByLandlordUid(landlordUid);
		}
		return houseBaseMsg;
	}
	
	/**
	 * 
	 * 查询订单需要房源信息
	 *
	 * @author bushujie
	 * @created 2016年4月6日 下午7:52:38
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public OrderNeedHouseVo findOrderNeedHouseVo(String fid,Integer rentWay){
		OrderNeedHouseVo orderNeedHouseVo=null;
		LogUtil.info(LOGGER, "房源fid={},rentWay={}", fid,rentWay);
		if(rentWay== RentWayEnum.HOUSE.getCode()){
			orderNeedHouseVo=houseBaseMsgDao.findOrderNeedHouseVo(fid);
			HousePicMsgEntity housePicMsgEntity=housePicMsgDao.findHouseDefaultPic(fid);
			//新默认图片未审核查询旧默认图片
			if(Check.NuNObj(housePicMsgEntity)){
				housePicMsgEntity=housePicMsgDao.findOldHouseDefaultPic(fid);
			}
			orderNeedHouseVo.setHouseDefaultPic(housePicMsgEntity);
			return orderNeedHouseVo;
		}else if(rentWay==RentWayEnum.ROOM.getCode()){
			orderNeedHouseVo=houseRoomMsgDao.getOrderNeedHouseVoByRoomFid(fid);
			//图片处理
			HousePicMsgEntity housePicMsgEntity=housePicMsgDao.findRoomDefaultPic(fid);
			//房间新默认图片未审核查询旧默认图片
			if(Check.NuNObj(housePicMsgEntity)){
				housePicMsgEntity=housePicMsgDao.findOldRoomDefaultPic(fid);
			}
			orderNeedHouseVo.setHouseDefaultPic(housePicMsgEntity);
			List<HouseConfMsgEntity>  listConf= orderNeedHouseVo.getHouseConfList();
			
			//去掉 长租  灵活定价 不符合的项
			if(!Check.NuNCollection(listConf)){
				List<HouseConfMsgEntity>  listRemove = new ArrayList<HouseConfMsgEntity>();
				for (HouseConfMsgEntity houseConfMsgEntity : listConf) {
					if(!Check.NuNStrStrict(houseConfMsgEntity.getDicCode())
							&&(houseConfMsgEntity.getDicCode().startsWith(ProductRulesEnum.ProductRulesEnum020.getValue())
							||houseConfMsgEntity.getDicCode().startsWith(ProductRulesEnum.ProductRulesEnum0019.getValue()))){
						if(Check.NuNStrStrict(houseConfMsgEntity.getRoomFid())
								||!houseConfMsgEntity.getRoomFid().equals(fid)){
							listRemove.add(houseConfMsgEntity);
						}
					}
				}
				listConf.removeAll(listRemove);
				orderNeedHouseVo.setHouseConfList(listConf);
			}
			return orderNeedHouseVo;
		}else if(rentWay==RentWayEnum.BED.getCode()){
			return houseBedMsgDao.getOrderNeedHouseVoByBedFid(fid);
		}
		
		return orderNeedHouseVo;
	}
	/**
	 * 
	 * 更新房源基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月6日 下午11:55:37
	 *
	 * @param houseBaseMsgEntity
	 * @return
	 */
	public int updateHouseBaseMsg(HouseBaseMsgEntity houseBaseMsgEntity){
		return houseBaseMsgDao.updateHouseBaseMsg(houseBaseMsgEntity);
	}
	
	/**
	 * 
	 * 获取房东房源包含小区列表
	 *
	 * @author bushujie
	 * @created 2016年4月18日 下午8:44:18
	 *
	 * @param landlordUid
	 * @return
	 */
	public List<SearchTerm> getCommunityListByLandlordUid(String landlordUid){
		return houseBaseMsgDao.getCommunityListByLandlordUid(landlordUid);
	}

	/**
	 * 获取房东房间(含房源默认房间)列表
	 *
	 * @author liujun
	 * @created 2016年4月19日 上午11:20:31
	 *
	 * @param houseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> findHouseRoomVoList(HouseBaseListDto houseBaseListDto) {
		return houseRoomMsgDao.findHouseRoomVoList(houseBaseListDto);
	}

	/**
	 * 获取房间默认图片（房东端）
	 *
	 * @author liujun
	 * @created 2016年4月20日 下午9:55:52
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public HousePicMsgEntity findLandlordRoomDefaultPic(String houseRoomFid) {
		return housePicMsgDao.findLandlordRoomDefaultPic(houseRoomFid);
	}

	/**
	 * 查询房东收益
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午3:10:59
	 *
	 * @param landlordUid
	 * @return
	 */
	public LandlordRevenueVo findLandLordRevenue(String landlordUid) {
		LandlordRevenueVo landlordRevenueVo = new LandlordRevenueVo();
		Date currentDate = new Date();
		
		//累计收益
		Integer totalRevenue = houseDayRevenueDao.getAddUpRevenueByLandlordUid(landlordUid);
		landlordRevenueVo.setTotalRevenue(totalRevenue);
		
		//当月收益
		Integer monthRevenue = houseDayRevenueDao.getMonthRevenueByLandlordUid(landlordUid,
				Integer.valueOf(DateUtil.dateFormat(currentDate, MONTH_FORMAT_PATTERN)));
		landlordRevenueVo.setMonthRevenue(monthRevenue);
		
		//本周收益
		Integer weekRevenue = houseDayRevenueDao.getWeekRevenueByLandlordUid(landlordUid,
				DateUtil.getFirstDayOfWeek(currentDate), DateUtil.getLastDayOfWeek(currentDate));
		landlordRevenueVo.setWeekRevenue(weekRevenue);
		
		//本年度各月收益
		List<LandlordRevenueVo> monthRevenueList = houseDayRevenueDao.getMonthRevenueListByLandlordUid(
				landlordUid, Integer.valueOf(DateUtil.dateFormat(currentDate, YEAR_FORMAT_PATTERN)));
		landlordRevenueVo.setMonthRevenueList(monthRevenueList);
		return landlordRevenueVo; 
	}
	
	/**
	 * 
	 * 插入房源日收益
	 *
	 * @author bushujie
	 * @created 2016年4月26日 下午9:42:28
	 *
	 * @param houseDayRevenueEntity
	 */
	public void insertLandlordRevenue(HouseDayRevenueEntity houseDayRevenueEntity){
		houseDayRevenueDao.insertHouseDayRevenue(houseDayRevenueEntity);
	}

	/**
	 * 查询房东房源月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午10:16:38
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public List<LandlordRevenueVo> findHouseRevenueListByLandlordUid(LandlordRevenueDto landlordRevenueDto) {
		return houseDayRevenueDao.findHouseRevenueListByLandlordUid(landlordRevenueDto);
	}
	
	/**
	 * 查询房源各月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月26日 下午10:16:38
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public List<HouseMonthRevenueVo> findMonthRevenueListByLandlordUidAndHouseBaseFid(LandlordRevenueDto landlordRevenueDto) {
		Integer currentYear = Integer.valueOf(DateUtil.dateFormat(new Date(), YEAR_FORMAT_PATTERN));
		
		//判断是否当前年 不是当前年可以直接从房源月收益表中查询所有数据
		boolean isCurrent = false;
		if (currentYear.compareTo(landlordRevenueDto.getStatisticsDateYear()) == 0) {
			isCurrent = true;
		}
		
		//查询房源月收益列表
		List<HouseMonthRevenueVo> voList = houseMonthRevenueDao.findMonthRevenueListByHouseBaseFid(landlordRevenueDto);
		if(isCurrent){
			HouseMonthRevenueVo vo = this.calculateRevenue(landlordRevenueDto);
			if(!Check.NuNObj(vo)){
				voList.add(vo);
			}
		}
		
		return voList;
	}
	
	/**
	 * 计算房源月收益
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午11:30:48
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public HouseMonthRevenueVo calculateRevenue(LandlordRevenueDto landlordRevenueDto) {
		//计算房源日收益表当月整租收益
		HouseMonthRevenueEntity houseMonthRevenue = houseDayRevenueDao
				.findHouseMonthRevenueByHouseBaseFid(landlordRevenueDto);
		
		//计算房源日收益表当月合租收益
		List<RoomMonthRevenueEntity> roomMonthRevenueList = houseDayRevenueDao
				.findRoomMonthRevenueListByHouseBaseFid(landlordRevenueDto);
		
		HouseMonthRevenueVo vo = null;
		List<HouseRoomMsgEntity> roomList = null;
		if (!Check.NuNObj(houseMonthRevenue) && !Check.NuNCollection(roomMonthRevenueList)) {
			//既有整租又有合租 整租收益分摊
			roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(landlordRevenueDto.getHouseBaseFid());
			vo = this.spiltRevenue(houseMonthRevenue, roomMonthRevenueList, roomList);
			vo.setRevenueType(HouseConstant.HOUSE_REVENUE_TYPE_MIX);
			vo.setHouseName(landlordRevenueDto.getHouseName());
		} else if (Check.NuNObj(houseMonthRevenue) && !Check.NuNCollection(roomMonthRevenueList)) {
			//只有合租
			roomList = houseRoomMsgDao.findRoomListByHouseBaseFid(landlordRevenueDto.getHouseBaseFid());
			vo = this.assembleRevenue(roomMonthRevenueList, roomList);
			vo.setRevenueType(HouseConstant.HOUSE_REVENUE_TYPE_ROOM);
			vo.setHouseBaseFid(landlordRevenueDto.getHouseBaseFid());
			vo.setStatisticsDateYear(landlordRevenueDto.getStatisticsDateYear());
			vo.setStatisticsDateMonth(landlordRevenueDto.getStatisticsDateMonth());
			vo.setHouseName(landlordRevenueDto.getHouseName());
		} else if (!Check.NuNObj(houseMonthRevenue) && Check.NuNCollection(roomMonthRevenueList)) {
			//只有整租
			vo = new HouseMonthRevenueVo();
			BeanUtils.copyProperties(houseMonthRevenue, vo);
			vo.setRevenueType(HouseConstant.HOUSE_REVENUE_TYPE_HOUSE);
			vo.setHouseName(landlordRevenueDto.getHouseName());
		}
		
		return vo;
	}
	
	/**
	 * 整租转合租分摊金额
	 *
	 * @author liujun
	 * @created 2016年4月27日 下午4:04:27
	 *
	 * @param houseMonthRevenue
	 * @param roomMonthRevenueList
	 * @param roomList
	 * @return 
	 */
	private HouseMonthRevenueVo spiltRevenue(HouseMonthRevenueEntity houseMonthRevenue,
			List<RoomMonthRevenueEntity> roomMonthRevenueList, List<HouseRoomMsgEntity> roomList) {
		HouseMonthRevenueVo vo = new HouseMonthRevenueVo();
		BeanUtils.copyProperties(houseMonthRevenue, vo);
		
		float totalRoomPrice = 0.0f;
		// 如果房间价格为0,从集合中删除该房间
		Iterator<HouseRoomMsgEntity> it = roomList.iterator();
		while (it.hasNext()) {
			HouseRoomMsgEntity houseRoomMsg = it.next();
			if (houseRoomMsg.getRoomPrice() == null || houseRoomMsg.getRoomPrice() == 0) {
				LogUtil.error(LOGGER, "error:houseRoomFid = {},roomPrice is {}", houseRoomMsg.getFid(),
						houseRoomMsg.getRoomPrice());
				it.remove();
				continue;
			}
			totalRoomPrice += houseRoomMsg.getRoomPrice();
		}
		
		if (totalRoomPrice != 0.0f) {
			int spiltPrice = 0;// 拆分金额
			int totalPrice = 0;// 已拆分金额
			for (int i = 0, j = roomList.size(); i < j; i++) {
				HouseRoomMsgEntity houseRoomMsg = roomList.get(i);
				if (i == j - 1) {
					spiltPrice = houseMonthRevenue.getMonthRevenue() - totalPrice;
				} else {
					spiltPrice = Math.round((houseRoomMsg.getRoomPrice() / totalRoomPrice) * houseMonthRevenue.getMonthRevenue());
					totalPrice += spiltPrice;
				}
				
				boolean flag = false;
				for (RoomMonthRevenueEntity roomMonthRevenue : roomMonthRevenueList) {
					if(houseRoomMsg.getFid().equals(roomMonthRevenue.getRoomFid())){
						flag = true;
						roomMonthRevenue.setRoomName(houseRoomMsg.getRoomName());
						roomMonthRevenue.setHouseShareRevenue(spiltPrice);
					}
				}
				
				if(!flag){
					RoomMonthRevenueEntity roomMonthRevenue = new RoomMonthRevenueEntity();
					roomMonthRevenue.setRoomFid(houseRoomMsg.getFid());
					roomMonthRevenue.setRoomName(houseRoomMsg.getRoomName());
					roomMonthRevenue.setRoomMonthRevenue(spiltPrice);
					vo.getRoomMonthRevenueList().add(roomMonthRevenue);
				}
			}
		}else {
			LogUtil.error(LOGGER, "error:houseHouseFid = {},roomPrice list is null or zero",
					houseMonthRevenue.getHouseBaseFid());
		}
		
		vo.getRoomMonthRevenueList().addAll(roomMonthRevenueList);
		return vo;
	}

	/**
	 * 设置房间名称
	 *
	 * @author liujun
	 * @created 2016年4月27日 下午9:55:51
	 *
	 * @param roomMonthRevenueList
	 * @param roomList
	 * @return 
	 */
	private HouseMonthRevenueVo assembleRevenue(List<RoomMonthRevenueEntity> roomMonthRevenueList,
			List<HouseRoomMsgEntity> roomList) {
		HouseMonthRevenueVo vo = new HouseMonthRevenueVo();
		for (RoomMonthRevenueEntity roomMonthRevenue : roomMonthRevenueList) {
			Iterator<HouseRoomMsgEntity> it = roomList.iterator();
			while (it.hasNext()) {
				HouseRoomMsgEntity houseRoomMsg = it.next();
				if (houseRoomMsg.getFid().equals(roomMonthRevenue.getRoomFid())) {
					roomMonthRevenue.setRoomName(houseRoomMsg.getRoomName());
					it.remove();
				}
			}
		}
		vo.getRoomMonthRevenueList().addAll(roomMonthRevenueList);
		return vo;
	}

	/**
	 * 查询房东月收益列表
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午4:53:14
	 *
	 * @param landlordRevenueDto
	 * @return
	 */
	public List<LandlordRevenueVo> findMonthRevenueListByLandlordUid(LandlordRevenueDto landlordRevenueDto) {
		return houseDayRevenueDao.getMonthRevenueListByLandlordUid(landlordRevenueDto.getLandlordUid(),
				landlordRevenueDto.getStatisticsDateYear());
	}
	
	/**
	 * 
	 * 查询房源逻辑id集合
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午10:35:25
	 *
	 * @param statisticsDateYear
	 * @param statisticsDateMonth
	 * @return
	 */
	public List<String> findHouseBaseFidListFromHouseDayRevenue(Integer statisticsDateYear, Integer statisticsDateMonth) {
		return houseDayRevenueDao.findHouseBaseFidListFromHouseDayRevenue(statisticsDateYear, statisticsDateMonth);
	}

	/**
	 * 查询房源逻辑id集合
	 *
	 * @author liujun
	 * @created 2016年4月28日 下午11:10:00
	 *
	 * @param statisticsDateYear
	 * @param statisticsDateMonth
	 * @return
	 */
	public List<String> findHouseBaseFidListFromHouseMonthRevenue(Integer statisticsDateYear,
			Integer statisticsDateMonth) {
		return houseMonthRevenueDao.findHouseBaseFidListFromHouseMonthRevenue(statisticsDateYear, statisticsDateMonth);
	}

	/**
	 * 新增房源月收益与房间月收益
	 *
	 * @author liujun
	 * @created 2016年4月29日 上午12:05:14
	 *
	 * @param vo
	 */
	public void insertHouseMonthRevenueVo(HouseMonthRevenueVo houseMonthRevenue) {
		String houseMonthRevenueFid = UUIDGenerator.hexUUID();
		houseMonthRevenue.setFid(houseMonthRevenueFid);
		houseMonthRevenue.setCreateDate(new Date());
		houseMonthRevenueDao.insertHouseMonthRevenue(houseMonthRevenue);
		
		List<RoomMonthRevenueEntity> roomMonthRevenueList = houseMonthRevenue.getRoomMonthRevenueList();
		for (RoomMonthRevenueEntity roomMonthRevenue : roomMonthRevenueList) {
			roomMonthRevenue.setFid(UUIDGenerator.hexUUID());
			roomMonthRevenue.setHouseMonthRevenueFid(houseMonthRevenueFid);
			roomMonthRevenue.setCreateDate(new Date());
			roomMonthRevenueDao.insertRoomMonthRevenue(roomMonthRevenue);
		}
	}

	/**
	 * 逻辑删除房源月收益与房间月收益集合
	 *
	 * @author liujun
	 * @created 2016年4月29日 上午12:08:12
	 *
	 * @param landlordRevenueDto
	 */
	public void logicDeleteHouseMonthRevenueByHouseBaseFid(LandlordRevenueDto landlordRevenueDto) {
		HouseMonthRevenueEntity houseMonthRevenue = houseMonthRevenueDao.findOneHouseMonthRevenue(landlordRevenueDto);
		if(Check.NuNObj(houseMonthRevenue)){
			return;
		}
		houseMonthRevenue.setIsDel(HouseConstant.IS_DEL);
		houseMonthRevenue.setLastModifyDate(new Date());
		houseMonthRevenueDao.updateHouseMonthRevenue(houseMonthRevenue);
		
		List<RoomMonthRevenueEntity> roomMonthRevenueList = roomMonthRevenueDao
				.findRoomMonthRevenueListByHouseMonthRevenueFid(houseMonthRevenue.getFid());
		for (RoomMonthRevenueEntity roomMonthRevenue : roomMonthRevenueList) {
			roomMonthRevenue.setIsDel(HouseConstant.IS_DEL);
			roomMonthRevenue.setLastModifyDate(new Date());
			roomMonthRevenueDao.updateRoomMonthRevenue(roomMonthRevenue);
		}
	}
	
	/**
	 * 
	 * 查询房源默认图片(房东端)
	 *
	 * @author bushujie
	 * @created 2016年5月17日 下午10:37:38
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicMsgEntity getLandlordHouseDefaultPic(String houseBaseFid) {
		return housePicMsgDao.findLandlordHouseDefaultPic(houseBaseFid);
	} 
	
	/**
	 * 房东端房源列表（房源维度查询）
	 *
	 * @author bushujie
	 * @created 2016年6月14日 上午11:20:31
	 *
	 * @param houseBaseListDto
	 * @return
	 */
	public PagingResult<HouseRoomVo> getLandlordHouseList(HouseBaseListDto houseBaseListDto) {
		return houseBaseMsgDao.getLandlordHouseList(houseBaseListDto);
	}

	/**
	 * 
	 * 判断当前houseSn是否存在
	 * 返回数0，代表没有  大于0代表存在
	 *
	 * @author yd
	 * @created 2016年6月20日 下午8:44:27
	 *
	 * @param houseSn
	 * @return
	 */
	public Long countByHouseSn(String houseSn){
    	return houseBaseMsgDao.countByHouseSn(houseSn);
	}
	
	/**
	 * 
	 * 判断当前roomSn是否存在
	 * 返回数0，代表没有  大于0代表存在
	 *
	 * @author yd
	 * @created 2016年6月20日 下午8:44:27
	 *
	 * @param roomSn
	 * @return
	 */
	public Long countByRoomSn(String roomSn){
	  	return houseRoomMsgDao.countByRoomSn(roomSn);
	}
	
	/**
	 * 
	 * 房源是否有智能锁
	 *
	 * @author liujun
	 * @created 2016年6月24日
	 *
	 * @param roomSn
	 * @return
	 */
	public boolean isHasSmartLock(String houseBaseFid){
		HouseBaseMsgEntity houseBaseMsg = houseBaseMsgDao.getHouseBaseMsgEntityByFid(houseBaseFid);
		if(Check.NuNObj(houseBaseMsg) || Check.NuNObj(houseBaseMsg.getIsLock())){
			return false;
		}
		return SysConst.IS_TRUE == houseBaseMsg.getIsLock();
	}

	/**
	 * 绑定智能锁
	 *
	 * @author liujun
	 * @created 2016年6月27日
	 *
	 * @param smartLockDto
	 * @return 
	 */
	public void bindSmartLock(SmartLockDto smartLockDto) {
		if(!Check.NuNStr(smartLockDto.getHouseFid())){
    		HouseBaseMsgEntity houseBaseMsg = this.getHouseBaseMsgEntityByFid(smartLockDto.getHouseFid());
    		if (Check.NuNObj(houseBaseMsg)) {
    			String msg = MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_BASE_NULL);
    			StringBuilder sb = new StringBuilder(50);
				sb.append(smartLockDto.getHouseFid()).append(msg);
    			LogUtil.info(LOGGER, "error:{},houseBaseFid={}", msg, smartLockDto.getHouseFid());
    			throw new BusinessException(sb.toString());
    		} else {
    			if(Check.NuNObj(houseBaseMsg.getIsLock()) || houseBaseMsg.getIsLock() == SysConst.IS_NOT_TRUE){
    				houseBaseMsg.setIsLock(SysConst.IS_TRUE);
    				houseBaseMsg.setLastModifyDate(new Date());
    				this.updateHouseBaseMsg(houseBaseMsg);
    			}
    		}
    	}
		
		if(!Check.NuNCollection(smartLockDto.getRoomFidList())){
			for (String roomFid : smartLockDto.getRoomFidList()) {
				HouseRoomMsgEntity housRoomMsg = this.getHouseRoomByFid(roomFid);
				if(Check.NuNObj(housRoomMsg)){
					String msg = MessageSourceUtil.getChinese(messageSource, HouseMessageConst.HOUSE_ROOM_NULL);
					StringBuilder sb = new StringBuilder(50);
					sb.append(roomFid).append(msg);
					LogUtil.info(LOGGER, "error:{},houseRoomFid={}", msg, roomFid);
					throw new BusinessException(sb.toString());
				} else {
					// TODO 本期只增加外门锁
				}
			}
		}
	}
	
	/**
	 * 
	 * 查询默认图片
	 *
	 * @author bushujie
	 * @created 2016年6月27日 下午8:56:48
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HousePicMsgEntity getHouseDefaultPic(String houseBaseFid) {
		return housePicMsgDao.findHouseDefaultPic(houseBaseFid);
	} 
	
	/**
	 * 
	 * 计算房源房间数目之和
	 *
	 * @author liyingjie
	 * @created 2016年6月27日 下午8:56:48
	 *
	 * @param uid
	 * @return
	 */
	public Long countHouseRoomNum(String uid) {
		long res = 0l;
		if(Check.NuNStr(uid)){
			return res ;
		}
		res = houseBaseMsgDao.countHouseNum(uid)+houseBaseMsgDao.countRoomNum(uid);
		return res;
	} 
	
	/**
	 * 
	 * 整租房源数量
	 *
	 * @author jixd
	 * @created 2016年7月30日 下午3:38:44
	 *
	 * @param uid
	 * @return
	 */
	public Long countZHouseNumByUid(String uid){
		return houseBaseMsgDao.countHouseNum(uid);
	}
	/**
	 * 
	 * 分租房源数量
	 *
	 * @author jixd
	 * @created 2016年7月30日 下午3:39:06
	 *
	 * @param uid
	 * @return
	 */
	public Long countRoomNumByUid(String uid){
		return houseBaseMsgDao.countRoomNum(uid);
	}
	
	/**
	 * 
	 * 获取房源列表
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午4:15:53
	 *
	 * @return
	 */
	public PagingResult<HouseRoomVo> getHousePCList(HouseBaseListDto param){
		return houseBaseMsgDao.getHousePCList(param);
	}
	
	/**
	 * 
	 * 获取日历上的房源列表
	 *
	 * @author busj
	 * @created 2017年10月28日 下午4:15:53
	 *
	 * @return
	 */
	public PagingResult<HouseRoomVo> getCalendarHousePCList(HouseBaseListDto param){
		return houseBaseMsgDao.getCalendarHousePCList(param);
	}
	
	/**
	 * 
	 * 获取房间列表
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午4:19:59
	 *
	 * @param param
	 * @return
	 */
	public PagingResult<HouseRoomVo> getRoomPCList(HouseBaseListDto param){
		return houseRoomMsgDao.getRoomPCList(param);
	}
	
	/**
	 * 
	 * 获取日历房间列表
	 *
	 * @author busj
	 * @created 2017年10月28日 下午4:19:59
	 *
	 * @param param
	 * @return
	 */
	public PagingResult<HouseRoomVo> getCalendarRoomPCList(HouseBaseListDto param){
		return houseRoomMsgDao.getCalendarRoomPCList(param);
	}
	
	/**
	 * 
	 * 根据房源Fid查询房间的数量
	 *
	 * @author jixd
	 * @created 2016年8月1日 下午6:21:38
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public Long countRoomNumByHouseFid(String houseBaseFid){
		return houseRoomMsgDao.countRoomNumByHouseFid(houseBaseFid);
	}
	
	/**
	 * 
	 * 查询房源列表
	 *
	 * @author liyingjie
	 * @created 2016年4月9日 下午9:19:14
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseBaseMsgEntity> findHouseByPhy(HousePhyListDto dto){
		
		if(Check.NuNObj(dto)){
			return new ArrayList<>();
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nationCode", dto.getNationCode());
		paramMap.put("provinceCode", dto.getProvinceCode());
		paramMap.put("cityCode", dto.getCityCode());
		paramMap.put("areaCode", dto.getAreaCode());
		
		return houseBaseMsgDao.findHouseByPhy(paramMap);
	}
	
	/**
	 * 
	 * 分页查询房源基本信息扩展信息
	 *
	 * @author liujun
	 * @created 2016年7月25日
	 *
	 * @param request
	 * @return
	 */
	public PagingResult<HouseBaseExtEntity> findHouseBaseExtListByCondition(HouseBaseExtRequest request){
		return houseBaseExtDao.findHouseBaseExtListByCondition(request);
	}
	
	/**
	 * 条件查询 返回数量
	 * @param houseRoomMsgDto
	 * @return
	 */
	public Long countByCondition(HouseRoomMsgEntity houseRoomMsgDto){
		return this.houseRoomMsgDao.countByCondition(houseRoomMsgDto);
	}
	
	/**
	 * 
	 * 通过房源fid 查询当前房源下房间fid集合，按创建id正序
	 *
	 * @author yd
	 * @created 2016年8月20日 下午1:55:17
	 *
	 * @param houseFid
	 * @return
	 */
	public List<String>  getRooFidListByHouseFid(String houseFid){
		return houseRoomMsgDao.getRooFidListByHouseFid(houseFid);
	}
	
	/**
	 * 条件查询 返回数量
	 * @param params
	 * @return
	 */
	public Long countByRoomInfo(Map<String, Object> params ){
		return  this.houseRoomMsgDao.countByRoomInfo(params);
	}
	
	/**
	 * 
	 * 获取图片集合根据房源fid和房间fid
	 *
	 * @author jixd
	 * @created 2016年9月1日 下午6:00:57
	 *
	 * @return
	 */
	public List<HousePicMsgEntity> findPicListByHouseAndRoomFid(String fid,Integer rentWay){
		List<HousePicMsgEntity> list = new ArrayList<>();
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			list = housePicMsgDao.findHousePicList(fid);
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			HouseRoomMsgEntity houseRoomMsgEntity = houseRoomMsgDao.findHouseRoomMsgByFid(fid);
			list = housePicMsgDao.findHousePicListByRoomFid(fid, houseRoomMsgEntity.getHouseBaseFid());
		}
		
		return list;
	}

	
	/**
	 * 当前房源 无默认照片 查询卧室、客厅、室外三个区域中第一张上传的照片
	 *
	 * @author yd
	 * @created 2016年10月18日 下午2:55:25
	 *
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public HousePicMsgEntity findHousePicFirstByHouseFid(String houseFid,String roomFid,int rentWay){
		return housePicMsgDao.findHousePicFirstByHouseFid(houseFid, roomFid, rentWay);
	}


	/**
	 *
	 * @author jixd
	 * @created 2016年10月18日 下午6:00:57
	 * @param houseFid
	 * @param roomFid
	 * @return
	 */
	public List<HousePriceWeekConfEntity> findWeekPriceByFid(String houseFid,String roomFid){
		return housePriceWeekConfDao.findSpecialPriceList(houseFid,roomFid,null);
	}
	
	/**
	 * 查询房东最后一次修改日历时间
	 * @author zl
	 * @param paramJson
	 * @return
	 */
	public Date getLastModifyCalendarDate(String landlordUid){
		return housePriceConfDao.getLastModifyCalendarDate(landlordUid);
	}
	
	/**
	 * 
	 * 更新房源周末价格信息列表
	 * 仅限于priceVal isDel isValid字段
	 *
	 * @author liujun
	 * @created 2016年12月7日
	 *
	 * @param housePriceWeekConf
	 * @return
	 */
	public int updateHousePriceWeekListByFid(List<HousePriceWeekConfEntity> weekendPriceList){
		int upNum = 0;
		for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
			upNum += housePriceWeekConfDao.updateHousePriceWeekConfByFid(housePriceWeekConf);
		}
		return upNum;
	}

	/**
	 * @Description: 根据houseDefaultPicFid 查询roomFid 集合
	 * @Author: lusp
	 * @Date: 2017/7/17 17:02
	 * @Params: defaultPicFid
	 */
	public List<String>  getRoomFidListByDefaultPicFid(String defaultPicFid){
		return houseRoomMsgDao.getRoomFidListByDefaultPicFid(defaultPicFid);
	}

	/**
	 * @Description: 根据图片fid 查询对应的roomFid
	 * @Author: lusp
	 * @Date: 2017/7/17 19:35
	 * @Params: PicFid
	 */
	public String  getRoomFidByPicFid(String picFid){
		return houseRoomMsgDao.getRoomFidByPicFid(picFid);
	}

}
