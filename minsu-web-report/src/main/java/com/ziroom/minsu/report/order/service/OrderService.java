package com.ziroom.minsu.report.order.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dao.HouseDayOrderAcceptNumDao;
import com.ziroom.minsu.report.house.dao.HouseDayOrderNumDao;
import com.ziroom.minsu.report.house.entity.HouseDayOrderAcceptNumEntity;
import com.ziroom.minsu.report.house.entity.HouseDayOrderNumEntity;
import com.ziroom.minsu.report.house.entity.HouseDayPayOrderEntity;
import com.ziroom.minsu.report.order.dao.OrderDao;
import com.ziroom.minsu.report.order.dao.OrderDetailDao;
import com.ziroom.minsu.report.order.dao.OrderEvaluateDao;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.order.vo.HouseOrderInfoVo;
import com.ziroom.minsu.report.order.vo.OrderCountVo;
import com.ziroom.minsu.report.order.vo.OrderStaticsVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>订单 OrderService</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
@Service("report.orderService")
public class OrderService implements ReportService <HouseOrderInfoVo,OrderRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Resource(name="report.orderDao")
    private OrderDao orderDao;


	@Resource(name = "report.orderEvaluateDao")
	private OrderEvaluateDao orderEvaluateDao;


    @Resource(name="report.orderDetailDao")
    private OrderDetailDao orderDetailDao;
    
    @Resource(name="report.houseDayOrderNumDao")
    private HouseDayOrderNumDao houseDayOrderNumDao;
    
    @Resource(name="report.houseDayOrderAcceptNumDao")
    private HouseDayOrderAcceptNumDao houseDayOrderAcceptNumDao;
    
    
	@Override
	public Long countDataInfo(OrderRequest par) {
		Long result = orderDao.countDataInfoDao(par);
		return result;
	}
	
	@Override
	public PagingResult<HouseOrderInfoVo> getPageInfo(OrderRequest request) {
		PagingResult<HouseOrderInfoVo> result = new PagingResult<HouseOrderInfoVo>();
		result = orderDetailDao.getHouseOrderInfo(request,request.getRequestType());
		return  result;
    }
	
	/**
	 * 查询订单统计相关 对外接口
	 * @author liyingjie
	 * @param par
	 * @return
	 */
	public OrderStaticsVo getOrderStaticsVo(OrderRequest par){
		if(Check.NuNObj(par)){
		   return new OrderStaticsVo();	
		}
		//接口
		OrderStaticsVo result = this.orderStaticsService(par);
		Long statyNight = this.orderStayNightService(par);
		Double statyNigthMoney = this.orderstayNightMoneyService(par);
		
		result.setStayNight(ValueUtil.getintValue(statyNight));
		result.setStayNightServiceMoney(statyNigthMoney);
		
		return result;
	}









	/**
	 * 计算当前的订单数量
	 * @author afi
	 * @return
	 */
	public List<OrderCountVo> getOrderCityCountInfo(String startTime,String endTime){
		return orderDao.getOrderCityCountInfo(startTime,endTime);
	}
	
	/**
	 * 查询订单统计相关
	 * @author liyingjie
	 * @param par
	 * @return
	 */
	private OrderStaticsVo orderStaticsService(OrderRequest par) {
		List<OrderStaticsVo> resultList = orderDao.orderStaticsDao(par);
		if(Check.NuNCollection(resultList)){
			return new OrderStaticsVo();
		}
		return resultList.get(0);
	}

	/**
	 * 查询订单入住间夜
	 * @author liyingjie
	 * @param par
	 * @return
	 */
	private Long orderStayNightService(OrderRequest par) {
		Long result = orderDao.orderStayNight(par);
		return result;
	}
	
	/**
	 * 查询订单入住间夜服务费
	 * @author liyingjie
	 * @param par
	 * @return
	 */
	private Double orderstayNightMoneyService(OrderRequest par) {
		Double result = orderDao.orderStayNightMoney(par);
		return result;
	}
	
	/**
     * 完成订单 并且可评价
     * @author liyingjie
     * @param afiRequest
     * @return
     */
	private Long accomplishOrderNumInfoService(OrderRequest afiRequest){
    	Long result = orderDao.accomplishOrderNumInfo(afiRequest);
		return result;
    }
	
//    /**
//     * #房东评价量 房客评价量
//     * @author liyingjie
//     * @param afiRequest
//     * @return
//     */
//	private Long lanUserEvaOrderNumInfoService(OrderRequest afiRequest,int userType){
//		afiRequest.setEvaUserType(userType);
//    	Long result = orderDao.lanUserEvaOrderNumInfo(afiRequest);
//		return result;
//    }
    
    /**
     * #房客总评分
     * @author liyingjie
     * @param afiRequest
     * @return
     */
    private Long avgUserScoreInfoService(OrderRequest afiRequest){
    	Long result = orderDao.sumUserScoreInfo(afiRequest);
		return result;
    }
    

    
    /**
     * 
     * 日订单量统计
     *
     * @author bushujie
     * @created 2016年9月24日 下午4:32:53
     *
     * @return
     */
    public void createOrderDayNum(Date startTime){
    	Date nowTime=new Date();
    	int almost=DateUtil.getDatebetweenOfDayNum(startTime, nowTime);
    	System.err.println("相差天数："+almost);
    	for(int i=0;i<almost;i++){
    		Date statisticsSdate=DateUtils.addDays(startTime, i);
    		System.out.println(DateUtil.dateFormat(statisticsSdate, "yyyy-MM-dd"));
    		Date statisticsEdate=DateUtils.addDays(statisticsSdate, 1);
    		Map<String, Object> paramMap=new HashMap<String, Object>();
    		paramMap.put("beginTime", statisticsSdate);
    		paramMap.put("endTime", statisticsEdate);
    		System.err.println(JsonEntityTransform.Object2Json(paramMap));
    		List<HouseDayOrderNumEntity> list=orderDao.createOrderDayNum(paramMap);
    		System.err.println(JsonEntityTransform.Object2Json(list));
    		//保存日订单量统计数据
    		for(HouseDayOrderNumEntity dayOrderNumEntity:list){
    			dayOrderNumEntity.setStatisticsDate(statisticsSdate);
    			dayOrderNumEntity.setCreateDate(statisticsEdate);
    			dayOrderNumEntity.setFid(UUIDGenerator.hexUUID());
    			try {
    				houseDayOrderNumDao.insertHouseDayOrderNum(dayOrderNumEntity);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
    		}
    	}
    }
    

    /**
     * 日支付订单量统计
     * @author liyingjie
     * @created 2016年9月24日 下午4:32:53
     * @return
     */
	public List<HouseDayPayOrderEntity> getHouseDayPayOrderNum(Date startTime,Date endTime) {
		if (Check.NuNObj(startTime) || Check.NuNObj(endTime)) {
			return new ArrayList<HouseDayPayOrderEntity>(1);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("beginTime", startTime);
		paramMap.put("endTime", endTime);
		List<HouseDayPayOrderEntity> list = orderDao.getHouseDayOrderNum(paramMap);
		return list;
	}
    
    /**
     * 
     * 房源日订单接受量
     *
     * @author bushujie
     * @created 2016年9月27日 下午7:54:03
     *
     * @param startTime
     */
    public void dayOrderAcceptNum(Date startTime){
    	Date nowTime=new Date();
    	int almost=DateUtil.getDatebetweenOfDayNum(startTime, nowTime);
    	System.err.println("相差天数："+almost);
    	for(int i=0;i<almost;i++){
    		Date statisticsSdate=DateUtils.addDays(startTime, i);
    		System.out.println(DateUtil.dateFormat(statisticsSdate, "yyyy-MM-dd"));
    		Date statisticsEdate=DateUtils.addDays(statisticsSdate, 1);
    		Map<String, Object> paramMap=new HashMap<String, Object>();
    		paramMap.put("beginTime", statisticsSdate);
    		paramMap.put("endTime", statisticsEdate);
    		paramMap.put("fromStatus", 10);
    		paramMap.put("toStatus", 20);
    		System.err.println(JsonEntityTransform.Object2Json(paramMap));
    		List<HouseDayOrderAcceptNumEntity> list=orderDao.getHouseDayOrderAcceptNum(paramMap);
    		System.err.println(JsonEntityTransform.Object2Json(list));
    		//保存日订单量统计数据
    		for(HouseDayOrderAcceptNumEntity acceptOrderNumEntity:list){
    			acceptOrderNumEntity.setStatisticsDate(statisticsSdate);
    			acceptOrderNumEntity.setCreateDate(statisticsEdate);
    			acceptOrderNumEntity.setFid(UUIDGenerator.hexUUID());
    			try{
    				houseDayOrderAcceptNumDao.insertDayOrderAcceptNum(acceptOrderNumEntity);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
}
