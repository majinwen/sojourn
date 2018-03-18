package com.ziroom.minsu.report.order.controller;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderFinanceRequest;
import com.ziroom.minsu.report.order.dto.OrderFreshRequest;
import com.ziroom.minsu.report.order.dto.OrderInformationRequest;
import com.ziroom.minsu.report.order.dto.OrderRequest;
import com.ziroom.minsu.report.order.service.OrderDetailService;
import com.ziroom.minsu.report.order.vo.OrderFinanceVo;
import com.ziroom.minsu.report.order.vo.OrderFreshVo;
import com.ziroom.minsu.report.order.vo.OrderInformationVo;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/orderDetail")
public class OrderDetailController {
    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailController.class);

    @Resource(name = "report.orderDetailService")
    OrderDetailService orderDetailService;


    @Resource(name = "report.confCityService")
    ConfCityService confCityService;

    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;

    /**
     * 订单统计excel导出
     *
     * @param orderRequest
     * @param response
     */
    @RequestMapping("/orderDetailExcel")
    public void orderDetailExcel(OrderRequest orderRequest, HttpServletResponse response) {
        DealExcelUtil dealExcelUtil = new DealExcelUtil(orderDetailService, orderRequest, null, "order-oper");
        dealExcelUtil.exportZipFile(response, orderRequest.getMethodName());//指定调用的方法名
    }


    /**
     * 跳转订单信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:31
     */
    @RequestMapping("/toOrderInformation")
    public String toOrderInformation(Model model) {
        this.setModelRegionCity(model);
        return "/order/orderInformation";
    }


    /**
     * 查询订单信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:32
     */
    @RequestMapping("/getOrderInformation")
    @ResponseBody
    public PagingResult getOrderInformation(OrderInformationRequest request) {
        PagingResult result = new PagingResult();
    	// PagingResult<OrderInformationVo> orderInformationVoList =null;
        try {
        	PagingResult<OrderInformationVo>  orderInformationVoList = this.getOrderInformationVoList(request);
            result.setRows(Check.NuNCollection(orderInformationVoList.getRows())? new ArrayList<OrderInformationVo>() : orderInformationVoList.getRows());
            result.setTotal(orderInformationVoList.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return result;
    }


    /**
     * 导出订单信息报表
     * 
     * @author lishaochuan
     * @create 2017/3/8 14:07
     * @param 
     * @return 
     */
    @RequestMapping("/orderInformationExcel")
    public void orderInformationExcel(OrderInformationRequest request, HttpServletResponse response) {
    	DealExcelUtil dealExcelUtil = new DealExcelUtil(orderDetailService, request, null, "order-Information");
        dealExcelUtil.exportExcelOrZip(response, "getOrderInformation", "订单信息报表");
    }


    /**
     * 分页查询订单信息报表
     *
     * @author lishaochuan
     * @create 2017/3/8 14:07
     * @param 
     * @return 
     */
    private PagingResult<OrderInformationVo> getOrderInformationVoList(OrderInformationRequest request){
        PagingResult<OrderInformationVo> orderInformationVoList = orderDetailService.getOrderInformation(request);
        if(Check.NuNCollection(orderInformationVoList.getRows())){
        	
        	return new PagingResult<OrderInformationVo>();
        }
        return orderInformationVoList;
    }





    /**
     * 跳转订单财务报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:31
     */
    @RequestMapping("/toOrderFinance")
    public String toOrderFinance(Model model) {
        this.setModelRegionCity(model);
        return "/order/orderFinance";
    }


    /**
     * 分页查询订单财务报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:32
     */
    @RequestMapping("/getOrderFinance")
    @ResponseBody
    public PagingResult getOrderFinance(OrderFinanceRequest request) {
        PagingResult result = new PagingResult();
        try {
            PagingResult<OrderFinanceVo> orderFinanceVoPagingResult = orderDetailService.getOrderFinance(request);
            if(Check.NuNCollection(orderFinanceVoPagingResult.getRows())){
            	orderFinanceVoPagingResult.setRows(new ArrayList<OrderFinanceVo>());
            }
            result.setRows(orderFinanceVoPagingResult.getRows());
            result.setTotal(orderFinanceVoPagingResult.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return result;
    }

    /**
     * 导出订单财务报表
     *
     * @author lishaochuan
     * @create 2017/3/8 14:07
     * @param
     * @return
     */
    @RequestMapping("/orderFinanceExcel")
    public void orderFinanceExcel(OrderFinanceRequest request, HttpServletResponse response) {

        DealExcelUtil dealExcelUtil = new DealExcelUtil(orderDetailService,request, null, "orderFinance");
        dealExcelUtil.exportZipFile(response,"getOrderFinance");

    }



    /**
     * 跳转刷单信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/7 16:31
     */
    @RequestMapping("/toOrderFresh")
    public String toOrderFresh(Model model) {
        this.setModelRegionCity(model);
        return "/order/orderFresh";
    }


    /**
     * 跳转刷单信息报表
     *
     * @author lishaochuan
     * @create 2017/3/14 10:12
     * @param
     * @return
     */
    @RequestMapping("/getOrderFresh")
    @ResponseBody
    public PagingResult<OrderFreshVo> getOrderFresh(OrderFreshRequest request) {
        try {
            if(!Check.NuNObj(request.getCouponMoneyAllStart())){
                request.setCouponMoneyAllStart(request.getCouponMoneyAllStart() * 100);
            }
            if(!Check.NuNObj(request.getCouponMoneyAllEnd())){
                request.setCouponMoneyAllEnd(request.getCouponMoneyAllEnd() * 100);
            }
            PagingResult<OrderFreshVo> orderFresh = orderDetailService.getOrderFresh(request);
            if(Check.NuNCollection(orderFresh.getRows())){
            	orderFresh.setRows(new ArrayList<OrderFreshVo>());
            }
            return orderFresh;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return new PagingResult();
    }


    /**
     * 导出刷单信息报表
     *
     * @author lishaochuan
     * @create 2017/3/14 10:18
     * @param
     * @return
     */
    @RequestMapping("/orderFreshExcel")
    public void orderFreshExcel(OrderFreshRequest request, HttpServletResponse response) {
        DealExcelUtil dealExcelUtil = new DealExcelUtil(null, null, null, "orderFinance");
        request.setPage(1);
        request.setLimit(2000);
        PagingResult<OrderFreshVo> orderFinanceVoPagingResult = this.getOrderFresh(request);

        List<BaseEntity> list = new ArrayList<>();
        if(!Check.NuNCollection(orderFinanceVoPagingResult.getRows())){
        	list.addAll(orderFinanceVoPagingResult.getRows());
        }
        dealExcelUtil.exportExcelFile(response, list, DateSplitUtil.timestampPattern);
    }

    
    
    /**
     * 为model设置大区、城市
     *
     * @author lishaochuan
     * @create 2017/3/14 10:08
     * @param 
     * @return 
     */
    private void setModelRegionCity(Model model) {
        DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
        List<CityRegionEntity> list = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
        model.addAttribute("regionList",list);
        List<Map<String,String>> cityMapList = confCityService.getCityListInfo();
        model.addAttribute("cityList", cityMapList);
    }

}
