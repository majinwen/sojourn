package com.ziroom.minsu.report.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.basedata.dto.CityRequest;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderEvaluateDetailRequest;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.order.service.OrderEvaluateDetailService;
import com.ziroom.minsu.report.order.service.OrderService;
import com.ziroom.minsu.report.order.vo.OrderStaticsVo;
import com.ziroom.minsu.services.common.page.GroupPageResult;

/**
 * <p>注释的模板</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/30.
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource(name = "report.orderService")
    OrderService orderService;


    @Resource(name = "report.orderEvaluateDetailService")
    OrderEvaluateDetailService orderEvaluateDetailService;
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;
    
    /**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
    
    

    /**
     * 到订单信息页面
     * @param request
     * @return
     */
    @RequestMapping("/toOrderList")
    public  ModelAndView toOrderList(HttpServletRequest request){
    	ModelAndView maView  = new ModelAndView("/order/orderList");
    	return  maView;
    }

    
    
    /**
     * @param paramRequest
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/dataList")
    @ResponseBody
    public PagingResult orderDataList(OrderRequest paramRequest, HttpServletRequest request) {
        PagingResult pagingResult =  orderService.getPageInfo(paramRequest);
        return pagingResult;
    }
    
    /**
     * @param paramRequest
     * @param request
     * @return
     */
   @RequestMapping("/dataListExcel")
   public void orderDataExcelList(OrderRequest paramRequest,HttpServletResponse response) {
        DealExcelUtil test = new DealExcelUtil(orderService,paramRequest, null, "order");
        test.exportFile(response);
    }
   
   /**
    * 到订单统计信息页面
    * @param request
    * @return
    */
   @RequestMapping("/toOrderStaticsList")
   public  ModelAndView toOrderInfoList(HttpServletRequest request){
   	    ModelAndView maView  = new ModelAndView("/order/orderStaticsList");
	   	List<Map<String,String>> cityMapList = this.getCityList(); 
	    maView.addObject("cityList", cityMapList);
	    return  maView;
   }
   /**
    * 到订单评价统计页面
    * @param request
    * @return
    */
   @RequestMapping("/toOrderEvaluateList")
   public  ModelAndView toOrderEvaluateList(HttpServletRequest request,Model model){
   	    ModelAndView maView  = new ModelAndView("/order/orderEvaluateDetailList");
       List<ConfCityEntity> list = confCityService.getNations();
       model.addAttribute("nationList", list);
	    return  maView;
   }
   
   /**
    * 整租房源、订单、评价统计页面
    * @param request
    * @return
    */
   @RequestMapping("/toEntireHouseOrderInfoList")
   public  ModelAndView toEntireHouseOrderInfoList(HttpServletRequest request){
   	    ModelAndView maView  = new ModelAndView("/order/entireHouseOrderList");
   	    List<Map<String,String>> cityMapList = this.getCityList(); 
	    maView.addObject("cityList", cityMapList);
	    return  maView;
   }
   
   /**
    * 合租房源、订单、评价统计页面
    * @param request
    * @return
    */
   @RequestMapping("/toSubHouseOrderInfoList")
   public  ModelAndView toSubHouseOrderInfoList(HttpServletRequest request){
   	    ModelAndView maView  = new ModelAndView("/order/subHouseOrderList");
   	    List<Map<String,String>> cityMapList = this.getCityList(); 
	    maView.addObject("cityList", cityMapList);
	    return  maView;
   }
   
   
   /**
    * 订单统计页面
    * @param paramRequest
    * @param request
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/orderStaticsDataList")
   @ResponseBody
   public PagingResult orderStaticsDataList(OrderRequest paramRequest, HttpServletRequest request) {
       LogUtil.info(LOGGER, "orderStaticsDataList param:{}", JsonEntityTransform.Object2Json(paramRequest));
   	   PagingResult result = new PagingResult();
       try{
		   if(Check.NuNObj(paramRequest) || Check.NuNStr(paramRequest.getCityCode())){
		        this.dealOrderStaticsDataList(paramRequest,result);
		   }else {
		   		OrderStaticsVo resultVo = orderService.getOrderStaticsVo(paramRequest);
		   		resultVo.setCityCode(paramRequest.getCityCode());
		   		ConfCityEntity cityEntity = confCityService.getCityByCode(paramRequest.getCityCode());
		   		resultVo.setCityName(cityEntity.getShowName());
		   		List<OrderStaticsVo> resultList = new ArrayList<OrderStaticsVo>(1);
		   		resultList.add(resultVo);
		       	result.setRows(resultList);
		        result.setTotal(1);
		   }
       }catch(Exception ex){
       	   LogUtil.error(LOGGER, "orderStaticsDataList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
       }
   	
   	   return result;
   }
   
   
   /**
    * 订单评价统计页面
    * @param paramRequest
    * @param request
    * @return
    */
   @SuppressWarnings({ "rawtypes"})
	@RequestMapping("/orderEvaluateDataList")
   @ResponseBody
   public GroupPageResult orderEvaluateDataList(OrderEvaluateDetailRequest paramRequest, HttpServletRequest request)throws Exception{
       LogUtil.info(LOGGER, "orderEvaluateDataList param:{}", JsonEntityTransform.Object2Json(paramRequest));

       if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
       PagingResult pagingResult =  orderEvaluateDetailService.getOrderEvaluateDetailBypage(paramRequest);
       //转化成列表
       GroupPageResult groupPageResult = new GroupPageResult(pagingResult,paramRequest);
       return groupPageResult;
   }




   
   /**
    * 订单统计页面
    * @param paramRequest
    * @param request
    * @return
    */
   @SuppressWarnings({ "rawtypes" })
	@RequestMapping("/houseOrderDataList")
   @ResponseBody
   public PagingResult houseOrderDataList(OrderRequest paramRequest, HttpServletRequest request) {
       LogUtil.info(LOGGER, "houseOrderDataList param:{}", JsonEntityTransform.Object2Json(paramRequest));
   	   PagingResult result = new PagingResult();
       try{
		   result = orderService.getPageInfo(paramRequest);
       }catch(Exception ex){
       	   LogUtil.error(LOGGER, "houseOrderDataList param:{},error:{}", JsonEntityTransform.Object2Json(paramRequest),ex);
       }
   	
   	   return result;
   }
   
   
   /**
    * 订单统计excel导出
    * @param paramRequest
    * @param request
    * @return
    */
  @RequestMapping("/orderStaticsExcelList")
  public void orderStaticsExcelList(OrderRequest paramRequest,HttpServletResponse response) {
       DealExcelUtil test = new DealExcelUtil(orderService,paramRequest, null, "orderStaticsExcelList-oper");
       List<BaseEntity> resultList = this.getOrderStaticsDataList(paramRequest);
       test.exportExcelFile(response,resultList);
   }
   
  /**
   * 订单评价统计excel导出
   * @param paramRequest
   * @param request
   * @return
   */
 @RequestMapping("/orderEvaluateExcelList")
 public void orderEvaluateExcelList(OrderEvaluateDetailRequest paramRequest,HttpServletResponse response) throws Exception{


     String title = "evaluate-detail";
     if (!Check.NuNStr(paramRequest.getStarTimeStr())){
         title += paramRequest.getStarTimeStr();
         paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
     }
     if (!Check.NuNStr(paramRequest.getEndTimeStr())){
    	 title+="-";
         title += paramRequest.getEndTimeStr();
         paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
     }     
     
     DealExcelUtil test = new DealExcelUtil(orderEvaluateDetailService,paramRequest, null, title);
     test.exportExcel(response,"getOrderEvaluateDetailBypage","订单评价数据汇总");
     

  }
   
 
 /**
  * 房源订单统计excel导出
  * @param paramRequest
  * @param request
  * @return
  */
@RequestMapping("/houseOrderExcelList")
public void houseOrderExcelList(OrderRequest paramRequest,HttpServletResponse response) {
     DealExcelUtil test = new DealExcelUtil(orderService,paramRequest, null, "houseOrderExcelList-oper");
     test.exportZipFile(response,"getPageInfo");
 }
   
   /**
    * 处理返回数据
    * @param request
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
	private void dealOrderStaticsDataList(OrderRequest paramRequest,PagingResult result){
   	   CityRequest cityRequest = new CityRequest();
   	   cityRequest.setLimit(paramRequest.getLimit());
   	   cityRequest.setPage(paramRequest.getPage());
       PagingResult pagingResult =  confCityService.getPageInfo(cityRequest);
       List<ConfCityEntity> cityList = pagingResult.getRows();
       List<OrderStaticsVo> resultList = new ArrayList<OrderStaticsVo>(cityList.size());
       for(ConfCityEntity city : cityList){
       	if(Check.NuNStr(city.getCode())){
       		continue;
       	}
       	paramRequest.setCityCode(city.getCode());
       	OrderStaticsVo resultVo = orderService.getOrderStaticsVo(paramRequest);
       	resultVo.setCityCode(city.getCode());
       	resultVo.setCityName(city.getShowName());
       	resultList.add(resultVo);
       }
       result.setRows(resultList);
       result.setTotal(pagingResult.getTotal());
   }

   
   /**
    * 获取城市列表
    * @author liyingjie
    * @return
    */
   private List<Map<String,String>> getCityList(){
	   CityRequest cityRequest = new CityRequest();
       List<ConfCityEntity> cityList = confCityService.getOpenCity(cityRequest);
       List<Map<String,String>> cityMapList = new ArrayList<Map<String,String>>(cityList.size()); 
       Map<String,String> paramMap = new HashMap<String, String>(2);
       for(ConfCityEntity cEntity : cityList){
       	paramMap = new HashMap<String, String>(2);
       	paramMap.put("name", cEntity.getShowName());
       	paramMap.put("code", cEntity.getCode());
       	cityMapList.add(paramMap);
       }
       return cityMapList;
   }


   
   /**
    * 获取返回data数据
    * @param paramRequest
    * @return
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
   private List<BaseEntity> getOrderStaticsDataList(OrderRequest paramRequest){
       //here
   	   CityRequest cityRequest = new CityRequest();
   	   cityRequest.setLimit(Constant.PAGE_LIMIT);
       cityRequest.setPage(paramRequest.getPage());
       PagingResult pagingResult =  confCityService.getPageInfo(cityRequest);
       List<ConfCityEntity> cityList = pagingResult.getRows();
       List<BaseEntity> resultList = new ArrayList<BaseEntity>(cityList.size());
       for(ConfCityEntity city : cityList){
       	if(Check.NuNStr(city.getCode())){
       		continue;
       	}
       	paramRequest.setCityCode(city.getCode());
       	OrderStaticsVo resultVo = orderService.getOrderStaticsVo(paramRequest);
       	resultVo.setCityCode(city.getCode());
       	resultVo.setCityName(city.getShowName());
       	resultList.add(resultVo);
       }
       return resultList;
   }
}
