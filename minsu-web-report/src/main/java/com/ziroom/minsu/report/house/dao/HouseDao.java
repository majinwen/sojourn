package com.ziroom.minsu.report.house.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.house.dto.HouseRequest;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.house.entity.HouseStatusDataEntity;
import com.ziroom.minsu.report.board.dto.EmpStatsRequest;
import com.ziroom.minsu.report.house.valenum.HouseRequestTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.houseDao")
public class HouseDao {

	
    private String SQLID="report.houseDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 计算订单数量
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long countDataInfoDao(OrderRequest afiRequest){
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	Long result = mybatisDaoContext.countBySlave(SQLID + "countOrderNum", paramMap);
    	return result;
    }
    
    /**
     * 整租与分租当前状态统计数量
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long countEntireRentDao(HouseRequest houseRequest){
    	
    	Long result = 0l;
    	Map<String, Object> paramMap = new HashMap<String, Object>(6);
    	if(Check.NuNObj(houseRequest)){
    		return result;
    	}
    	
    	if(!Check.NuNObj(houseRequest.getHouseStatus())){
    		paramMap.put("houseStatus", houseRequest.getHouseStatus());
    	}
    	
    	if(!Check.NuNObj(houseRequest.getToStatus())){
    		paramMap.put("toStatus", houseRequest.getToStatus());
    	}
    	
    	if(Check.NuNStr(houseRequest.getCityCode())){
    		return result;
    	}
    	paramMap.put("landlordUid", houseRequest.getLandlordUid());
    	paramMap.put("empGuardCode", houseRequest.getEmpGuardCode());
    	paramMap.put("cityCode", houseRequest.getCityCode());
    	paramMap.put("beginTime", houseRequest.getBeginTime());
    	paramMap.put("endTime", houseRequest.getEndTime());
    	
    	if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
    		result = mybatisDaoContext.countBySlave(SQLID + "countEntireRentOperNum", paramMap);
    	}else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
    		result = mybatisDaoContext.countBySlave(SQLID + "countSubRentOperNum", paramMap);
    	}else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_ENTIRE.getCode()){
    		result = mybatisDaoContext.countBySlave(SQLID + "countSubEntireRentOperNum", paramMap);
    	}
    	return result;
    }
    
    
    /**
     * 整租与分租 发布 并 审核通过 ，发布并且品质审核驳回等等 统计查询
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long countLimitEntireRentDao(HouseRequest houseRequest){
    	
    	Long result = 0l;
    	if(Check.NuNObj(houseRequest)){
    		return result;
    	}
    	
    	if(Check.NuNObj(houseRequest.getHouseStatus())){
    		return result;
    	}
    	
    	if(Check.NuNStr(houseRequest.getCityCode())){
    		return result;
    	}
    	
    	Map<String, Object> paramMap = new HashMap<String, Object>(6);
    	paramMap.put("houseStatus", houseRequest.getHouseStatus());
    	paramMap.put("toStatus", houseRequest.getToStatus());
    	paramMap.put("cityCode", houseRequest.getCityCode());
    	paramMap.put("beginTime", houseRequest.getBeginTime());
    	paramMap.put("endTime", houseRequest.getEndTime());
    	
    	if(houseRequest.getRentWay() == HouseRequestTypeEnum.ENTIRE_RENT.getCode()){
    		result = mybatisDaoContext.countBySlave(SQLID + "countLimitEntireRentOperNum", paramMap);
    	}else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_RENT.getCode()){
    		result = mybatisDaoContext.countBySlave(SQLID + "countLimitSubRentOperNum", paramMap);
    	}else if(houseRequest.getRentWay() == HouseRequestTypeEnum.SUB_ENTIRE.getCode()){
    		result = mybatisDaoContext.countBySlave(SQLID + "countLimitSubEntireRentOperNum", paramMap);
    	}
    	return result;
    }
    
    /**
     * 
     * 房源日状态变化
     *
     * @author bushujie
     * @created 2016年9月29日 下午4:44:39
     *
     * @param paramMap
     * @return
     */
    public List<HouseStatusDataEntity> getHouseDayStatus(Map<String, Object> paramMap){
    	return mybatisDaoContext.findAll(SQLID+"getHouseDayStatus", HouseStatusDataEntity.class, paramMap);
    }
    
    /**
     * 
     * 房源fid查询房源创建时间
     *
     * @author bushujie
     * @created 2016年9月30日 下午3:03:53
     *
     * @param houseFid
     * @return
     */
    public Date getHouseCreateDate(String houseFid){
    	return mybatisDaoContext.findOneSlave(SQLID+"getHouseCreateDate", Date.class, houseFid);
    }
    
    /**
     * 
     * 房间fid查询房间创建时间
     *
     * @author bushujie
     * @created 2016年9月30日 下午3:15:07
     *
     * @param roomFid
     * @return
     */
    public Date getRoomCreateDate(String roomFid){
    	return mybatisDaoContext.findOneSlave(SQLID+"getRoomCreateDate", Date.class, roomFid);
    }

    /**
     * 查询员工地推上架房源和已发布房源
     * @author jixd
     * @created 2017年01月18日 11:14:08
     * @param
     * @return
     */
    public long countHousePushNum(EmpStatsRequest request){
        Map<String, Object> paramMap=new HashMap<String, Object>();
        paramMap.put("houseStatus", request.getHouseStatus());
        paramMap.put("empCode", request.getEmpCode());
        paramMap.put("startTime", request.getStartTime());
        paramMap.put("endTime", request.getEndTime());
        return mybatisDaoContext.count(SQLID + "countHousePushNum",paramMap);
    }
}
