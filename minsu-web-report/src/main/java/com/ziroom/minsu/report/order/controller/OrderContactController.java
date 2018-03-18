package com.ziroom.minsu.report.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.service.ConfCityService;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.order.dto.OrderContactRequest;
import com.ziroom.minsu.report.order.service.OrderContactService;
import com.ziroom.minsu.report.order.vo.OrderContactVo;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;

/**
 * <p>订单联系列表</p>
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
@RequestMapping("/orderContact")
public class OrderContactController {


    @Resource(name="basedata.cityRegionService")
    private CityRegionService cityRegionService;
    
    @Resource(name = "report.confCityService")
    ConfCityService confCityService;

    @Resource(name = "report.orderContactService")
    OrderContactService orderContactService;


    /**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderContactController.class);

    /**
     * 调到订单联系人
     * @param
     * @return
     * @author afi
     * @create 2017/3/7 16:31
     */
    @RequestMapping("/toOrderContactList")
    public String toOrderContactList(Model model) {
        List<ConfCityEntity> listNation = confCityService.getNations();
        model.addAttribute("nationList", listNation);
        DataTransferObject resultJson = JsonEntityTransform.json2DataTransferObject(cityRegionService.fillAllRegion());
        List<CityRegionEntity> list = resultJson.parseData("list", new TypeReference<List<CityRegionEntity>>() {});
        model.addAttribute("regionList",list);
        List<Map<String,String>> cityMapList = confCityService.getCityListInfo();
        model.addAttribute("cityList", cityMapList);
        return "/order/orderContact";
    }


    /**
     * 查询订单联系信息
     * @param
     * @return
     * @author afi
     * @create 2017/3/7 16:32
     */
    @RequestMapping("/orderContactList")
    @ResponseBody
    public PagingResult orderContactList(OrderContactRequest request) {
        PagingResult result = new PagingResult();
        try {
            PagingResult<OrderContactVo> orderInformationVoList = orderContactService.getOrderContactByPage(request);
            result.setRows(orderInformationVoList.getRows());
            result.setTotal(orderInformationVoList.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return result;
    }


    /**
     * 导出订单信息报表
     * @author afi
     * @create 2017/3/8 14:07
     * @param
     * @return
     */
    @RequestMapping("/orderContactListExcel")
    public void orderContactListExcel(OrderContactRequest request, HttpServletResponse response) { 
        
	    DealExcelUtil dealExcelUtil = new DealExcelUtil(null, null, null, "orderContact");
        List<OrderContactVo> orderContactList = orderContactService.getOrderContactList(request);
        List<BaseEntity> list = new ArrayList<>();
        list.addAll(orderContactList);
        dealExcelUtil.exportExcelFile(response, list, DateSplitUtil.timestampPattern);
	    
    }

}
