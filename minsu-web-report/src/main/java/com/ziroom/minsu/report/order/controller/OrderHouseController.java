package com.ziroom.minsu.report.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderHouseRequest;
import com.ziroom.minsu.report.order.service.OrderHouseService;
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
@RequestMapping("/orderHouse")
public class OrderHouseController {



    @Resource(name = "report.orderHouseService")
    OrderHouseService orderHouseService;


    @Resource(name="report.confCityService")
    private ConfCityService confCityService;

    /**
     * 到订单信息页面
     * @param request
     * @return
     */
    @RequestMapping("/toOrderHouseList")
    public ModelAndView toOrderHouseList(HttpServletRequest request,Model model){

        List<ConfCityEntity> list = confCityService.getNations();
        model.addAttribute("nationList", list);
        ModelAndView maView  = new ModelAndView("/orderHouse/orderHouseList");
        return  maView;
    }


    /**
     * @param paramRequest
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping("/orderHouseList")
    @ResponseBody
    public GroupPageResult orderHouseList(OrderHouseRequest paramRequest, HttpServletRequest request) throws Exception{

        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        PagingResult pagingResult =  orderHouseService.getOrderHouseBypage(paramRequest);
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
    @RequestMapping("/orderHouseListExcel")
    public void orderHouseListExcel(HttpServletResponse response, OrderHouseRequest paramRequest) throws Exception{
        String title = "house";
        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            title += paramRequest.getStarTimeStr();
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
        	title+="-";
            title += paramRequest.getEndTimeStr();
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        DealExcelUtil test = new DealExcelUtil(orderHouseService,paramRequest, null, title);
        test.exportExcel(response,"getOrderHouseBypage","房源订单数据汇总");
        
    }

}
