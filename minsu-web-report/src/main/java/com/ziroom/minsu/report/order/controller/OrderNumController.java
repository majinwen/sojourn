package com.ziroom.minsu.report.order.controller;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderNumRequest;
import com.ziroom.minsu.report.order.service.OrderNumService;
import com.ziroom.minsu.services.common.page.GroupPageResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
@RequestMapping("/orderNum")
public class OrderNumController {



    @Resource(name = "report.orderNumService")
    OrderNumService orderNumService;


    @Resource(name="report.confCityService")
    private ConfCityService confCityService;

    /**
     * 到订单统计信息页面
     * @param request
     * @return
     */
    @RequestMapping("/toOrderNumList")
    public ModelAndView toOrderNumList(HttpServletRequest request,Model model){
        ModelAndView maView  = new ModelAndView("/orderNum/orderNumList");

        List<ConfCityEntity> list = confCityService.getNations();
        model.addAttribute("nationList", list);

        return  maView;
    }


    /**
     * @param paramRequest
     * @param request
     * @return
     */
    @RequestMapping("/orderNumList")
    @ResponseBody
    public GroupPageResult orderNumList(OrderNumRequest paramRequest, HttpServletRequest request) throws Exception{

        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }

        PagingResult pagingResult =  orderNumService.getOrderNumBypage(paramRequest);
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
    @RequestMapping("/orderNumListExcel")
    public void orderNumListExcel(HttpServletResponse response, OrderNumRequest paramRequest) throws Exception{
        String title = "evaluate";
        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            title += paramRequest.getStarTimeStr();
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }
        title+="-";
        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            title += paramRequest.getEndTimeStr();
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyy-MM-dd HH:mm:ss"));
        }


        DealExcelUtil dealExcelUtil = new DealExcelUtil(null, null, null,title);
        paramRequest.setLimit(Constant.PAGE_LIMIT);
        PagingResult pagingResult =  orderNumService.getOrderNumBypage(paramRequest);
        List<BaseEntity> list = new ArrayList<>();
        list.addAll(pagingResult.getRows());
        dealExcelUtil.exportExcelFile(response, list);
    }

}
