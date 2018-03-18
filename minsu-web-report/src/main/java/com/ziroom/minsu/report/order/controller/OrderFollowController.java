package com.ziroom.minsu.report.order.controller;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.order.dto.OrderDataStatisticsRequest;
import com.ziroom.minsu.report.order.dto.OrderFollowQueryRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.order.service.OrderFollowService;
import com.ziroom.minsu.report.order.vo.OrderFollowNumVo;
import com.ziroom.minsu.report.order.vo.OrderFollowQueryVo;
import com.ziroom.minsu.report.order.vo.OrderFollowVo;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.Collator;
import java.util.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/15 11:20
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/orderfollow")
public class OrderFollowController {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderFollowController.class);

    @Resource(name = "report.orderfollowservice")
    OrderFollowService orderFollowService;

    @Resource(name = "report.confCityService")
    ConfCityService confCityService;


    /**
     * 获取城市列表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:46
     */
    private List<Map<String, String>> getCityList() {
        CityRequest cityRequest = new CityRequest();
        List<ConfCityEntity> cityList = confCityService.getOpenCity(cityRequest);
        List<Map<String, String>> cityMapList = new ArrayList<Map<String, String>>(cityList.size());
        Map<String, String> paramMap = new HashMap<String, String>(2);
        for (ConfCityEntity cEntity : cityList) {
            paramMap = new HashMap<String, String>(2);
            paramMap.put("name", cEntity.getShowName());
            paramMap.put("code", cEntity.getCode());
            cityMapList.add(paramMap);
        }


        Collections.sort(cityMapList, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return Collator.getInstance(Locale.CHINESE).compare(o1.get("name"), o2.get("name"));
            }
        });

        return cityMapList;
    }


    /**
     * 跳转 订单客服数据统计
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:46
     */
    @RequestMapping("/toOrderDataStatistics")
    public String toOrderList(HttpServletRequest request) {
        List<Map<String, String>> cityMapList = this.getCityList();
        request.setAttribute("cityList", cityMapList);
        return "/order/orderDataStatistics";
    }

    /**
     * 订单客服数据统计
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/15 15:38
     */
    @RequestMapping("/orderDataStatistics")
    @ResponseBody
    public PagingResult orderDataStatistics(HttpServletRequest request, OrderDataStatisticsRequest orderDataStatisticsRequest) {
        LogUtil.info(LOGGER, "param:{}", JsonEntityTransform.Object2Json(orderDataStatisticsRequest));
        PagingResult result = new PagingResult();
        try {
            if (Check.NuNObj(orderDataStatisticsRequest)) {
                return result;
            }
            PagingResult<OrderFollowVo> orderFollowVos = orderFollowService.orderDataStatistics(orderDataStatisticsRequest);
            result.setRows(orderFollowVos.getRows());
            result.setTotal(orderFollowVos.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return result;
    }


    /**
     * 订单客服数据统计 导出
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:45
     */
    @RequestMapping("/orderDataStatisticsExcel")
    public void orderDataStatisticsExcel(OrderDataStatisticsRequest paramRequest, HttpServletResponse response) {
        String[] headers = null;

        List<String> listArray = new ArrayList<>();
        if (!Check.NuNObj(paramRequest.getCity())) {
            ConfCityEntity cityEntity = confCityService.getCityByCode(paramRequest.getCity());
            listArray.add("城市：" + cityEntity.getShowName());
        }
        if (!Check.NuNObj(paramRequest.getStartTime())) {
            listArray.add("订单创建开始时间：");
            listArray.add(paramRequest.getStartTime());
        }
        if (!Check.NuNObj(paramRequest.getEndTime())) {
            listArray.add("订单创建结束时间：");
            listArray.add(paramRequest.getEndTime());
        }
        if (listArray.size() > 0) {
            headers = listArray.toArray(new String[listArray.size()]);
        }
        
        DealExcelUtil test = new DealExcelUtil(orderFollowService,paramRequest, null, "orderNum");
        test.exportExcelOrZip(response,"orderDataStatistics","订单客服数据统计表");
        
    }


    /**
     * 跳转 跟进订单状态数据统计表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:52
     */
    @RequestMapping("/toOrdeFollowrStatusNum")
    public String toOrdeFollowrStatusNum(HttpServletRequest request) {
        List<Map<String, String>> cityMapList = this.getCityList();
        request.setAttribute("cityList", cityMapList);
        return "/order/ordeFollowrStatusList";
    }

    /**
     * 跟进订单状态数据统计表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:49
     */
    @ResponseBody
    @RequestMapping("/followOrderStatusNum")
    public PagingResult followOrderStatusNum(HttpServletRequest request, OrderDataStatisticsRequest orderDataStatisticsRequest) {
        LogUtil.info(LOGGER, "param:{}", JsonEntityTransform.Object2Json(orderDataStatisticsRequest));
        PagingResult<OrderFollowNumVo> orderFollowNumVos = new PagingResult();
        try {
            if (Check.NuNObj(orderDataStatisticsRequest)) {
                return orderFollowNumVos;
            }
            orderFollowNumVos = orderFollowService.followOrderStatusNum(orderDataStatisticsRequest);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return orderFollowNumVos;
    }


    /**
     * 跟进订单状态数据统计表 导出
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 15:14
     */
    @RequestMapping("/followOrderStatusNumExcel")
    public void followOrderStatusNumExcel(OrderDataStatisticsRequest paramRequest, HttpServletResponse response) {
        String[] headers = null;

        List<String> listArray = new ArrayList<>();
        if (!Check.NuNObj(paramRequest.getCity())) {
            ConfCityEntity cityEntity = confCityService.getCityByCode(paramRequest.getCity());
            listArray.add("城市：" + cityEntity.getShowName());
        }
        if (!Check.NuNObj(paramRequest.getStartTime())) {
            listArray.add("订单创建开始时间：");
            listArray.add(paramRequest.getStartTime());
        }
        if (!Check.NuNObj(paramRequest.getEndTime())) {
            listArray.add("订单创建结束时间：");
            listArray.add(paramRequest.getEndTime());
        }
        if (listArray.size() > 0) {
            headers = listArray.toArray(new String[listArray.size()]);
        }

        DealExcelUtil test = new DealExcelUtil(orderFollowService,paramRequest, headers, "orderFollowNum");
        test.exportZipFile(response,"followOrderStatusNum");
        
    }





    /**
     * 跳转 客服跟进数据报表查询功能
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2016/12/16 14:46
     */
    @RequestMapping("/toOrderFollowrList")
    public String toOrderFollowrList(HttpServletRequest request) {
        TreeMap<Integer, String> orderStatusMap = new TreeMap<>();
        for (final OrderStatusEnum status : OrderStatusEnum.values()) {
            orderStatusMap.put(status.getOrderStatus(), status.getStatusName());
        }
        request.setAttribute("orderStatusMap", orderStatusMap);

        List<Map<String, String>> cityMapList = this.getCityList();
        request.setAttribute("cityList", cityMapList);
        return "/order/orderFollowList";
    }


    /**
     * 客服跟进数据报表查询功能
     * 
     * @author lishaochuan
     * @create 2016/12/19 18:09
     * @param 
     * @return 
     */
    @ResponseBody
    @RequestMapping("/orderFollowList")
    public PagingResult orderFollowList(HttpServletRequest request, OrderFollowQueryRequest orderFollowQueryRequest) {
    	fillCreateTime(orderFollowQueryRequest);
    	LogUtil.info(LOGGER, "orderFollowList方法   param:{}", JsonEntityTransform.Object2Json(orderFollowQueryRequest));
        PagingResult result = new PagingResult();
        try {
            if (Check.NuNObj(orderFollowQueryRequest)) {
                return result;
            }
            PagingResult<OrderFollowQueryVo> orderFollowQuery = orderFollowService.getOrderFollowQuery(orderFollowQueryRequest);
            List<OrderFollowQueryVo> rows = orderFollowQuery.getRows();
            //获取所有国家
            List<ConfCityEntity> nations = confCityService.getNations();
            //获取所有开通城市
            CityRequest par = new CityRequest();
            List<ConfCityEntity> cityListInfo = confCityService.getOpenCity(par);
            
            for (OrderFollowQueryVo row : rows) {
                try {
                    row.setPayStatusName(OrderPayStatusEnum.getPayStatusByCode(row.getPayStatus()).getStatusName());
                    row.setOrderMoney(row.getOrderMoney()/100);
                    row.setRealMoney(row.getRealMoney()/100);
                    row.setOrderStatusName(OrderStatusEnum.getOrderStatusByCode(row.getOrderStatus()).getStatusName());
                    if(!Check.NuNObj(row.getFollowOrderStatus()) && !Check.NuNObj(OrderStatusEnum.getOrderStatusByCode(row.getFollowOrderStatus()))){
                        row.setFollowOrderStatusName(OrderStatusEnum.getOrderStatusByCode(row.getFollowOrderStatus()).getStatusName());
                    }
                    
                    //填充国家名称
                    for (ConfCityEntity temp : nations) {
                    	if(!Check.NuNStr(row.getNationCode())){
                    		if(row.getNationCode().equals(temp.getCode())){
                    			row.setNationName(temp.getShowName());
                    			break;
    						}
                    	}
					}
                    //填充城市名称
                    for (ConfCityEntity temp : cityListInfo) {
                    	if(!Check.NuNStr(row.getCityCode())){
                    		if(row.getCityCode().equals(temp.getCode())){
                    			row.setCityName(temp.getShowName());
                    			break;
    						}
                    	}
					}
                }catch (Exception e){
                    LogUtil.error(LOGGER, "e:{}", e);
                }
            }
            result.setRows(rows);
            result.setTotal(orderFollowQuery.getTotal());
            LogUtil.info(LOGGER, "orderFollowList方法    nations={},cityListInfo={}", JsonEntityTransform.Object2Json(nations),JsonEntityTransform.Object2Json(cityListInfo));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return result;
    }



    /**
     * 客服跟进数据报表查询功能 导出
     *
     * @author lishaochuan
     * @create 2016/12/20 15:06
     * @param
     * @return
     */
    @RequestMapping("/orderFollowListExcel")
    public void orderFollowListExcel(HttpServletResponse response, OrderFollowQueryRequest orderFollowQueryRequest) {
    	DealExcelUtil dealExcelUtil = new DealExcelUtil(null, null, null, "orderFollowNum");
        orderFollowQueryRequest.setLimit(Constant.PAGE_LIMIT);
        PagingResult<OrderFollowQueryVo> orderFollowQuery = orderFollowService.getOrderFollowQuery(orderFollowQueryRequest);
        List<OrderFollowQueryVo> rows = orderFollowQuery.getRows();
        //获取所有国家
        List<ConfCityEntity> nations = confCityService.getNations();
        //获取所有开通城市
        CityRequest par = new CityRequest();
        List<ConfCityEntity> cityListInfo = confCityService.getOpenCity(par);
        
        for (OrderFollowQueryVo row : rows) {
            try {
                row.setPayStatusName(OrderPayStatusEnum.getPayStatusByCode(row.getPayStatus()).getStatusName());
                row.setOrderMoney(row.getOrderMoney()/100);
                row.setRealMoney(row.getRealMoney()/100);
                row.setOrderStatusName(OrderStatusEnum.getOrderStatusByCode(row.getOrderStatus()).getStatusName());
                if(!Check.NuNObj(row.getFollowOrderStatus()) && !Check.NuNObj(OrderStatusEnum.getOrderStatusByCode(row.getFollowOrderStatus()))){
                    row.setFollowOrderStatusName(OrderStatusEnum.getOrderStatusByCode(row.getFollowOrderStatus()).getStatusName());
                }
                
              //填充国家名称
                for (ConfCityEntity temp : nations) {
                	if(!Check.NuNStr(row.getNationCode())){
                		if(row.getNationCode().equals(temp.getCode())){
                			row.setNationName(temp.getShowName());
                			break;
						}
                	}
				}
                //填充城市名称
                for (ConfCityEntity temp : cityListInfo) {
                	if(!Check.NuNStr(row.getCityCode())){
                		if(row.getCityCode().equals(temp.getCode())){
                			row.setCityName(temp.getShowName());
                			break;
						}
                	}
				}
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
        }
        List<BaseEntity> list = new ArrayList<>();
        list.addAll(rows);
        dealExcelUtil.exportExcelFile(response, list, DateSplitUtil.timestampPattern);
    }
    
    /**
     * 
     * 订单创建时间如果为空，默认是当前2个月前
     *
     * @author ls
     * @created 2017年12月18日 下午7:27:30
     *
     * @param orderFollowQueryRequest
     */
    public  void fillCreateTime(OrderFollowQueryRequest orderFollowQueryRequest){
    	if(Check.NuNStr(orderFollowQueryRequest.getCreateStartTime())){
    		Calendar calendar  = Calendar.getInstance();
    		calendar.setTime(new Date());
    		calendar.add(Calendar.MONTH, -1);
    		String dateFormat = DateUtil.timestampFormat(calendar.getTime());
    		orderFollowQueryRequest.setCreateStartTime(dateFormat);
    	}
    }

}
