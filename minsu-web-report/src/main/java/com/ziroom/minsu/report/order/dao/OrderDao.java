package com.ziroom.minsu.report.order.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.house.entity.HouseDayOrderAcceptNumEntity;
import com.ziroom.minsu.report.house.entity.HouseDayOrderNumEntity;
import com.ziroom.minsu.report.house.entity.HouseDayPayOrderEntity;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.order.entity.OrderEntity;
import com.ziroom.minsu.report.order.vo.OrderCountVo;
import com.ziroom.minsu.report.order.vo.OrderStaticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
@Repository("report.orderDao")
public class OrderDao {

    private String SQLID="report.orderDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前用户的收藏信息
     * @author afi
     * @param uid
     * @return
     */
    public List<String> testFind(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "findTest", String.class, uid);
    }

    /**
     * 分页查询
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public PagingResult<OrderEntity> getPageInfo(OrderRequest afiRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(afiRequest.getLimit());
        pageBounds.setPage(afiRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getPageInfo", OrderEntity.class, afiRequest, pageBounds);
    }
    
    
    
    /**
     * 计算订单数量
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long countDataInfoDao(OrderRequest afiRequest){
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	Long result = mybatisDaoContext.count(SQLID + "countOrderNum", paramMap);
    	return result;
    }


	/**
	 * 计算当前的订单数量
	 * @author afi
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OrderCountVo> getOrderCityCountInfo(String startTime,String endTime){

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (!Check.NuNStr(startTime)){
			paramMap.put("beginTime", startTime);
		}
		if (!Check.NuNStr(endTime)){
			paramMap.put("endTime", endTime);
		}
		return mybatisDaoContext.findAll(SQLID + "getOrderCityCountInfo", OrderCountVo.class,paramMap);
	}



    /**
     * 计算订单统计相关
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public List<OrderStaticsVo> orderStaticsDao(OrderRequest afiRequest){
    	if(Check.NuNObj(afiRequest)){
    		return new ArrayList<OrderStaticsVo>();
    	}
    	
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", afiRequest.getCityCode());
    	paramMap.put("beginTime", afiRequest.getBeginTime());
    	paramMap.put("endTime", afiRequest.getEndTime());
    	
    	return mybatisDaoContext.findAll(SQLID + "orderStaticsInfo", OrderStaticsVo.class, paramMap);
    }
    
    /**
     * 入住间夜统计
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long orderStayNight(OrderRequest afiRequest){
    	Long result = 0l;
    	if(Check.NuNObj(afiRequest)){
    		return result;
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", afiRequest.getCityCode());
    	paramMap.put("beginTime", afiRequest.getBeginTime());
    	paramMap.put("endTime", afiRequest.getEndTime());
    	
    	result = mybatisDaoContext.countBySlave(SQLID + "stayNightInfo", paramMap);
    	return result;
    }
    
    /**
     * 入住间夜服务费统计
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Double orderStayNightMoney(OrderRequest afiRequest){
		Double result = 0.0;
    	if(Check.NuNObj(afiRequest)){
    		return result;
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", afiRequest.getCityCode());
    	paramMap.put("beginTime", afiRequest.getBeginTime());
    	paramMap.put("endTime", afiRequest.getEndTime());
    	result = mybatisDaoContext.findOne(SQLID + "stayNightMoneyInfo",Double.class, paramMap);
    	return result;
    }
    
    /**
     * 完成订单 并且可评价
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long accomplishOrderNumInfo(OrderRequest afiRequest){
    	Long result = 0l;
    	if(Check.NuNObj(afiRequest)){
    		return result;
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", afiRequest.getCityCode());
    	paramMap.put("beginTime", afiRequest.getBeginTime());
    	paramMap.put("endTime", afiRequest.getEndTime());
    	result = mybatisDaoContext.countBySlave(SQLID + "accomplishOrderNumInfo", paramMap);
    	return result;
    }
    
    /**
     * #房东评价量 房客评价量
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long lanUserEvaOrderNumInfo(OrderRequest afiRequest){
    	Long result = 0l;
    	if(Check.NuNObj(afiRequest)){
    		return result;
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", afiRequest.getCityCode());
    	paramMap.put("beginTime", afiRequest.getBeginTime());
    	paramMap.put("endTime", afiRequest.getEndTime());
    	paramMap.put("evaUserType", afiRequest.getEvaUserType());
    	result = mybatisDaoContext.countBySlave(SQLID + "lanUserEvaOrderNumInfo", paramMap);
    	return result;
    }
    
    /**
     * #房客总评分
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    public Long sumUserScoreInfo(OrderRequest afiRequest){
    	Long result = 0l;
    	if(Check.NuNObj(afiRequest)){
    		return result;
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", afiRequest.getCityCode());
    	paramMap.put("beginTime", afiRequest.getBeginTime());
    	paramMap.put("endTime", afiRequest.getEndTime());
    	result = mybatisDaoContext.countBySlave(SQLID + "sumUserScoreInfo", paramMap);
    	return result;
    }
    
//    /**
//     * #房东总评分
//     * @author liyingjie
//     * @param afiRequest
//     * @return
//     */
//    public List<OrderEvaluateVo> sumLanScoreInfo(OrderRequest afiRequest){
//
//    	if(Check.NuNObj(afiRequest)){
//    		return new ArrayList<OrderEvaluateVo>();
//    	}
//    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
//    	paramMap.put("cityCode", afiRequest.getCityCode());
//    	paramMap.put("beginTime", afiRequest.getBeginTime());
//    	paramMap.put("endTime", afiRequest.getEndTime());
//
//    	return mybatisDaoContext.findAll(SQLID + "sumLanScoreInfo",OrderEvaluateVo.class ,paramMap);
//    }
    
    /**
     * 
     * 查询日期内创建订单量
     *
     * @author bushujie
     * @created 2016年9月24日 下午4:26:12
     *
     * @param paramMap
     * @return
     */
    public List<HouseDayOrderNumEntity> createOrderDayNum(Map<String, Object> paramMap){
    	return mybatisDaoContext.findAll(SQLID+"createOrderDayNum", HouseDayOrderNumEntity.class, paramMap);
    }
    
    
    /**
     * 获取支付订单量信息
     * @author liyingjie
     * @param paramMap
     * @return
     */
    public List<HouseDayPayOrderEntity> getHouseDayOrderNum(Map<String, Object> paramMap){
    	if(Check.NuNObj(paramMap) || Check.NuNObj(paramMap.get("beginTime")) || Check.NuNObj(paramMap.get("endTime"))){
    		return new ArrayList<HouseDayPayOrderEntity>(1);
    	}
       return mybatisDaoContext.findAll(SQLID+"getHouseDayOrderNum", HouseDayPayOrderEntity.class, paramMap);
       
    }
    
    /**
     * 获取房源日订单支付量
     * @param paramMap
     * @return
     */
    public List<HouseDayOrderAcceptNumEntity> getHouseDayOrderAcceptNum(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"orderDayAcceptNum", HouseDayOrderAcceptNumEntity.class, paramMap);
    }
}
