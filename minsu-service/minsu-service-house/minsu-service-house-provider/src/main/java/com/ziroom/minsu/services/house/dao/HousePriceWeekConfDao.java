package com.ziroom.minsu.services.house.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * 
 * <p>房源星期价格配置dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Repository("house.housePriceWeekConfDao")
public class HousePriceWeekConfDao {


    private String SQLID="house.housePriceWeekConfDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


	/**
     * 按照星期设置特殊价格
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param createUid
     * @param houseBaseFid
     * @param roomFid
     * @param weeks
     * @param price
     * @return
     */
	public int saveHousePriceWeekConf(String createUid, String houseBaseFid, String roomFid, List<Integer> weeks, int price,Integer isValid) {
		int num = 0;
		if (Check.NuNStr(createUid) || (Check.NuNStr(houseBaseFid) && Check.NuNStr(roomFid)) || price <= 0 || Check.NuNCollection(weeks)
				|| weeks.size() == 0) {
			return 0;
		}
		
    	HousePriceWeekConfEntity housePricedto = new HousePriceWeekConfEntity();
    	if(!Check.NuNStr(houseBaseFid)){
    		housePricedto.setHouseBaseFid(houseBaseFid);
    	}
    	if(!Check.NuNStr(roomFid)){
    		housePricedto.setRoomFid(roomFid);
    	}
    	delHousePriceWeekConfByHouseFid(housePricedto);//删除以前的设置
    	
		for (int wk : weeks) {
			HousePriceWeekConfEntity housePricedConf = new HousePriceWeekConfEntity();
			housePricedConf.setCreateDate(new Date());
			housePricedConf.setCreateUid(createUid);
			housePricedConf.setFid(UUIDGenerator.hexUUID());
			housePricedConf.setHouseBaseFid(houseBaseFid);
			housePricedConf.setLastModifyDate(new Date());
			housePricedConf.setPriceVal(price);
			housePricedConf.setRoomFid(roomFid);
			housePricedConf.setSetWeek(wk);
			housePricedConf.setIsValid(YesOrNoEnum.YES.getCode());
			if(!Check.NuNObj(isValid)){
				housePricedConf.setIsValid(isValid);
			}
			
			num += mybatisDaoContext.save(SQLID + "insertHousePriceWeekConf", housePricedConf);
		}
    	
		return num;
	}
    

    /**
     * 查询某天是否按照星期配置了特殊价格，并返回
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param houseBaseFid
     * @param roomFid
     * @param day
     * @return
     */
    public HousePriceWeekConfEntity findHousePriceWeekConfByDate(String houseBaseFid,String roomFid,Date day){
    	
    	if( (Check.NuNStr(houseBaseFid)&&Check.NuNStr(roomFid)) || Check.NuNObj(day)){
    		return null;
    	}
    	
    	LeaseCalendarDto housePricedto = new LeaseCalendarDto();
    	
    	if(!Check.NuNStr(houseBaseFid)){
    		housePricedto.setHouseBaseFid(houseBaseFid);
    	}
    	if(!Check.NuNStr(roomFid)){
    		housePricedto.setHouseRoomFid(roomFid);
    	}
    	
    	housePricedto.setNowDate(day);
    	
    	return mybatisDaoContext.findOne(SQLID+"findHousePriceWeekConfByDateHouseFid", HousePriceWeekConfEntity.class, housePricedto);
    }

    
    /**
     * 按照房源或者房间查询星期价格配置列表
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param houseBaseFid
     * @param roomFid
     * @return
     */
	public List<HousePriceWeekConfEntity> findSpecialPriceList(String houseBaseFid, String roomFid,Integer isValid) {
		if ((Check.NuNStrStrict(houseBaseFid) && Check.NuNStrStrict(roomFid))) {
			return null;
		}
		
		LeaseCalendarDto housePricedto = new LeaseCalendarDto();
		if (!Check.NuNStrStrict(houseBaseFid)) {
			housePricedto.setHouseBaseFid(houseBaseFid);
		}
		if (!Check.NuNStrStrict(roomFid)) {
			housePricedto.setHouseRoomFid(roomFid);
		}
		if(!Check.NuNObj(isValid)){
			housePricedto.setIsValid(isValid);
		}
		return mybatisDaoContext.findAll(SQLID+"findHousePriceWeekConfListByHouseFid", HousePriceWeekConfEntity.class, housePricedto);
	}
     
    
    /**
     * 按照fid删除特殊价格
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param housePriceWeekConf
     * @return
     */
    public int delHousePriceWeekConfByFid(HousePriceWeekConfEntity housePriceWeekConf) {
    	if( Check.NuNStr(housePriceWeekConf.getFid())){
    		return 0;
    	}
    	return mybatisDaoContext.update(SQLID+"delHousePriceWeekConfByFid", housePriceWeekConf);
    }
    
    /**
     * 按照房源或者房间fid删除特殊价格
     * 
     * @author zl
     * @created 2016年9月9日
     * 
     * @param housePriceWeekConf
     * @return
     */
    public int delHousePriceWeekConfByHouseFid(HousePriceWeekConfEntity housePriceWeekConf) {
		if (Check.NuNStr(housePriceWeekConf.getHouseBaseFid()) && Check.NuNStr(housePriceWeekConf.getRoomFid())) {
			return 0;
		}
    	return mybatisDaoContext.update(SQLID+"delHousePriceWeekConfByHouseFid", housePriceWeekConf);
    }
    
    /**
     * 
     * 更新房源周末价格信息
     * 仅限于priceVal isDel isValid字段
     *
     * @author liujun
     * @created 2016年12月7日
     *
     * @param housePriceWeekConf
     * @return
     */
    public int updateHousePriceWeekConfByFid(HousePriceWeekConfEntity housePriceWeekConf) {
    	if (Check.NuNStr(housePriceWeekConf.getFid())) {
    		return 0;
    	}
    	return mybatisDaoContext.update(SQLID+"updateHousePriceWeekConfByFid", housePriceWeekConf);
    }
    
    /**
     * 
     * 合租转整租删除合租周末价格配置
     *
     * @author bushujie
     * @created 2017年7月21日 上午11:24:46
     *
     * @param houseBaseFid
     */
    public void delRoomPriceWeekByHouseBaseFid(String houseBaseFid){
    	Map<String, Object> paramMap=new HashMap<String, Object>();
    	paramMap.put("houseBaseFid", houseBaseFid);
    	mybatisDaoContext.delete(SQLID+"delRoomPriceWeekByHouseBaseFid", paramMap);
    }
    
}