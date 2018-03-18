package com.ziroom.minsu.report.cms.controller;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.cms.dto.CouponInfoRequest;
import com.ziroom.minsu.report.cms.service.CouponInfoService;
import com.ziroom.minsu.report.cms.vo.CouponInfoVo;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2017/3/15 11:20
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/couponInfo")
public class CouponInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponInfoController.class);

    @Resource(name = "report.couponInfoService")
    CouponInfoService couponInfoService;


    /**
     * 跳转优惠券信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/15 11:22
     */
    @RequestMapping("/toCouponInfo")
    public String toCouponInfo() {
        return "/cms/couponInfo";
    }


    /**
     * 查询优惠券信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/15 11:25
     */
    @RequestMapping("/getCouponInfo")
    @ResponseBody
    public PagingResult<CouponInfoVo> getOrderFresh(CouponInfoRequest request) {
        try {
            return couponInfoService.getPageInfo(request);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return new PagingResult();
    }


    /**
     * 导出优惠券信息报表
     *
     * @param
     * @return
     * @author lishaochuan
     * @create 2017/3/15 11:26
     */
    @RequestMapping("/couponInfoExcel")
    public void couponInfoExcel(CouponInfoRequest request, HttpServletResponse response) {
    	
    	
    	DealExcelUtil dealExcelUtil = new DealExcelUtil(couponInfoService,request,null,"coupon_data_list");
		dealExcelUtil.exportZipFile(response,"getPageInfo");//指定调用的方法名
    	
		
    }
}
