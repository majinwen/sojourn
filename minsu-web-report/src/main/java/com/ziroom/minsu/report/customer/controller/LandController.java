package com.ziroom.minsu.report.customer.controller;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.report.customer.dto.LandRequest;
import com.ziroom.minsu.report.order.service.OrderEvaluateService;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * <p>房东的统计信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/2/13.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/land")
public class LandController {



    @Resource(name = "report.orderEvaluateService")
    OrderEvaluateService orderEvaluateService;


    /**
     * 到订单信息页面
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/toLandList")
    public ModelAndView toLandList(HttpServletRequest request){
        ModelAndView maView  = new ModelAndView("/land/landList");
        return  maView;
    }


    /**
     * 房东信息统计
     * @author afi
     * @param paramRequest
     * @param request
     * @return
     */
//    @RequestMapping("/landList")
//    @ResponseBody
//    public GroupPageResult landList(LandRequest paramRequest, HttpServletRequest request) throws Exception{
//
//        //转化当前的时间格式
//        this.transTime(paramRequest);
//
//        PagingResult pagingResult =  orderEvaluateService.getOrderEvaluateBypage(paramRequest);
//        //转化成列表
//        GroupPageResult groupPageResult = new GroupPageResult(pagingResult,paramRequest);
//        return groupPageResult;
//    }





    /**
     * 转化当前的时间格式
     * @author afi
     * @param paramRequest
     * @throws ParseException
     */
    private String  getTimeTitle(String org,LandRequest paramRequest) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append(ValueUtil.getStrValue(org));

        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            sb.append(paramRequest.getStarTimeStr());
        }
        sb.append("-");
        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            sb.append(paramRequest.getEndTimeStr());
        }
        //
        return sb.toString();
    }



    /**
     * 转化当前的时间格式
     * @author afi
     * @param paramRequest
     * @throws ParseException
     */
    private void transTime(LandRequest paramRequest) throws ParseException {
        if (!Check.NuNStr(paramRequest.getStarTimeStr())){
            paramRequest.setStarTime(DateUtil.parseDate(paramRequest.getStarTimeStr(),"yyyy-MM-dd"));
        }

        if (!Check.NuNStr(paramRequest.getEndTimeStr())){
            paramRequest.setEndTime(DateUtil.parseDate(paramRequest.getEndTimeStr(),"yyyy-MM-dd"));
        }
    }



}
