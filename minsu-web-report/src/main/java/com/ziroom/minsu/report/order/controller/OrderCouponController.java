package com.ziroom.minsu.report.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderCouponRequest;
import com.ziroom.minsu.report.order.service.OrderCouponService;
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
@RequestMapping("/orderCoupon")
public class OrderCouponController {



    @Resource(name = "report.orderCouponService")
    OrderCouponService orderCouponService;
    
    @Resource(name="report.confCityService")
    private ConfCityService confCityService;


    /**
     * 到订单统计信息页面
     * @param request
     * @return
     */
    @RequestMapping("/toOrderCouponList")
    public ModelAndView toOrderCouponList(HttpServletRequest request){
        ModelAndView maView  = new ModelAndView("/orderCoupon/orderCouponList");
		List<ConfCityEntity> list = confCityService.getNations();
		maView.addObject("nationList", list);
        return  maView;
    }


    /**
     * @param paramRequest
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/orderCouponList")
    @ResponseBody
    public GroupPageResult orderCouponList(OrderCouponRequest paramRequest, HttpServletRequest request) throws Exception{

        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        PagingResult pagingResult =  orderCouponService.getOrderCouponBypage(paramRequest);
        //转化成列表
        GroupPageResult groupPageResult = new GroupPageResult(pagingResult,paramRequest);
        return groupPageResult;
    }





    /**
     * 订单统计导出 导出
     *
     * @author afi
     * @create 2016/12/20 15:06
     * @param
     * @return
     */
    @RequestMapping("/orderCouponListExcel")
    public void orderCouponListExcel(HttpServletResponse response, OrderCouponRequest paramRequest) throws Exception{
        String title = "Coupon";
        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            title += paramRequest.getStarTimeStr();
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
        	title+="-";
            title += paramRequest.getEndTimeStr();
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
        
        DealExcelUtil test = new DealExcelUtil(orderCouponService,paramRequest, null, title);
        test.exportExcel(response,"getOrderCouponBypage","优惠劵数据汇总");
        
    }

}
