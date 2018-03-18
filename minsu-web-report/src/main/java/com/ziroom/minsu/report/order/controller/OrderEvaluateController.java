package com.ziroom.minsu.report.order.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderEvaluate2Request;
import com.ziroom.minsu.report.order.dto.OrderEvaluateRequest;
import com.ziroom.minsu.report.order.dto.OrderFollowQueryRequest;
import com.ziroom.minsu.report.order.service.OrderEvaluateService;
import com.ziroom.minsu.report.order.vo.OrderEvaluateVo;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.common.page.GroupPageResult;

/**
 * <p>订单评价</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/orderEval")
public class OrderEvaluateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvaluateController.class);

    @Resource(name = "report.orderEvaluateService")
    OrderEvaluateService orderEvaluateService;

    @Resource(name="report.confCityService")
    private ConfCityService confCityService;

    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;

    /**
     * 到订单信息页面
     * @param request
     * @return
     */
    @RequestMapping("/toOrderEvaList")
    public ModelAndView toOrderEvaList(HttpServletRequest request,Model model){

        List<ConfCityEntity> list = confCityService.getNations();
        model.addAttribute("nationList", list);

        ModelAndView maView  = new ModelAndView("/orderEval/orderEvaList");
        return  maView;
    }


    /**
     * @param paramRequest
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/orderEvaList")
    @ResponseBody
    public GroupPageResult orderEvaList(OrderEvaluateRequest paramRequest, HttpServletRequest request) throws Exception{

        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        PagingResult pagingResult =  orderEvaluateService.getOrderEvaluateBypage(paramRequest);
        //转化成列表
        GroupPageResult groupPageResult = new GroupPageResult(pagingResult,paramRequest);
        return groupPageResult;
    }





    /**
     * 评价信息导出 导出
     *
     * @author afi
     * @create 2016/12/20 15:06
     * @param
     * @return
     */
    @RequestMapping("/orderEvaListExcel")
    public void orderEvaListExcel(HttpServletResponse response, OrderEvaluateRequest paramRequest) throws Exception{
        String title = "evaluate";
        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            title += paramRequest.getStarTimeStr();
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
        	title+="-";
            title += paramRequest.getEndTimeStr();
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
        
        DealExcelUtil test = new DealExcelUtil(orderEvaluateService,paramRequest, null, title);
        test.exportExcel(response,"getOrderEvaluateBypage","订单评分分数数据汇总");
    }






    /**
     * 跳转订单评价报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:31
     */
    @RequestMapping("/toOrderEvaluate")
    public String toOrderEvaluate(Model model) {
        DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
        List<CityRegionEntity> list = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
        model.addAttribute("regionList",list);
        List<Map<String,String>> cityMapList = confCityService.getCityListInfo();
        model.addAttribute("cityList", cityMapList);
        return "/orderEval/orderEvaluateList";
    }


    /**
     * 分页查询订单评价报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:32
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getOrderEvaluate")
    @ResponseBody
    public PagingResult getOrderEvaluate(OrderEvaluate2Request request) {
    	fillCreateTime(request);
    	LogUtil.info(LOGGER, "getOrderEvaluate  param:{}", JsonEntityTransform.Object2Json(request));
        PagingResult result = new PagingResult();
        try {
            PagingResult<OrderEvaluateVo> orderEvaluate = orderEvaluateService.getOrderEvaluate(request);
            result.setRows(orderEvaluate.getRows());
            result.setTotal(orderEvaluate.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return result;
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
    public  void fillCreateTime(OrderEvaluate2Request request){
    	if(Check.NuNStr(request.getCreateStartTime())){
    		Calendar calendar  = Calendar.getInstance();
    		calendar.setTime(new Date());
    		calendar.add(Calendar.MONTH, -1);
    		String dateFormat = DateUtil.timestampFormat(calendar.getTime());
    		request.setCreateStartTime(dateFormat);
    	}
    }
    
    /**
     * 导出订单评价报表
     *
     * @author lishaochuan
     * @create 2017/3/8 14:07
     * @param
     * @return
     */
    @RequestMapping("/orderEvaluateExcel")
    public void orderEvaluateExcel(OrderEvaluate2Request request, HttpServletResponse response) {
        
        DealExcelUtil test = new DealExcelUtil(orderEvaluateService,request, null, "orderEvaluate");
        test.exportExcelOrZip(response,"getOrderEvaluate","订单评价报表");
        
    }
}
