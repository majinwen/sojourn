/**
 * @FileName: CmsController.java
 * @Package com.ziroom.minsu.troy.cms.controller
 * 
 * @author liyingjie
 * @created 2016年6月15日 下午10:03:17
 * 
 * Copyright 2011-2015 
 */
package com.ziroom.minsu.troy.cms.controller;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import com.ziroom.minsu.entity.cms.ActivityHouseEntity;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.ActivityFullService;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.dto.CancelCouponDto;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>cms相关controller</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 * @param
 */
@Controller
@RequestMapping("activity")
public class CouponListController {


	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(CouponListController.class);

	
	@Resource(name="cms.activityFullService")
    private ActivityFullService activityFullService;

    @Resource(name = "cms.actCouponService")
    private ActCouponService actCouponService;




    /**
     * 优惠券详情
     * @author afi
     * @param request
     * @param couponSn
     * @return
     */
    @RequestMapping("/couponDetail")
    public ModelAndView couponDetail(HttpServletRequest request,String couponSn){
        ModelAndView mv = new ModelAndView("/activity/couponDetail");
        ActCouponUserEntity  actCouponUserEntity = this.getCouponFullInfo(couponSn);

        mv.addObject("detail", actCouponUserEntity);
        if (!Check.NuNObj(actCouponUserEntity)){
            if (Check.NuNObj(actCouponUserEntity.getCheckOutTime()) && Check.NuNObj(actCouponUserEntity.getCheckInTime())){
                mv.addObject("check", 1);
            }
            if (Check.NuNObj(actCouponUserEntity.getCheckOutTime())){
                mv.addObject("checkOutTime", "1");
            }
            if (Check.NuNObj(actCouponUserEntity.getCheckInTime())){
                mv.addObject("checkInTime", "1");
            }

            if (!Check.NuNObj(actCouponUserEntity.getUid())){
                mv.addObject("has", "1");
            }
        }
        if (!Check.NuNObj(actCouponUserEntity)){
            ActivityFullEntity  ac = this.getAcFullInfo(actCouponUserEntity.getActSn());
            mv.addObject("ac", ac);
        }
        if (actCouponUserEntity.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
            String houseSns = "";
            DataTransferObject limitHouseDto = JsonEntityTransform.json2DataTransferObject(activityFullService.findLimitHouseByActsn(actCouponUserEntity.getActSn()));
            List<ActivityHouseEntity> list = limitHouseDto.parseData("list", new TypeReference<List<ActivityHouseEntity>>() {});
            for (ActivityHouseEntity houseEntity : list){
                String houseSn = houseEntity.getHouseSn();
                if (Check.NuNStr(houseSns)){
                    houseSns += houseSn;
                }else{
                    houseSns += ","+houseSn;
                }
            }
            mv.addObject("houseSns",houseSns);
        }
        return mv;
    }




    /**
     * 获取优惠券信息
     * @author afi
     * @param actSn
     * @return
     */
    private ActivityFullEntity getAcFullInfo(String actSn){
        if(Check.NuNStr(actSn)){
            return null;
        }
        String resultJson = activityFullService.getActivityFullBySn(actSn);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        ActivityFullEntity ac  = resultDto.parseData("full", new TypeReference<ActivityFullEntity>() {});
        return ac;
    }


    /**
     * 获取优惠券信息
     * @author afi
     * @param couponSn
     * @return
     */
    private ActCouponUserEntity getCouponFullInfo(String couponSn){
        if(Check.NuNStr(couponSn)){
            return null;
        }
        ActCouponUserEntity couponUserVo = null;
        //获取cms的优惠券信息
        String couponResultJson = actCouponService.getActCouponUserVoByCouponSn(couponSn);
        DataTransferObject couponDto = JsonEntityTransform.json2DataTransferObject(couponResultJson);
        if(couponDto.getCode() != DataTransferObject.SUCCESS){
            LogUtil.info(logger, "获取优惠券信息失败：{}", couponDto.toJsonString());
            return couponUserVo;
        }else {
            couponUserVo = couponDto.parseData("obj", new TypeReference<ActCouponUserEntity>() {});
        }
        return couponUserVo;
    }

    /**
     * 活动列表
     *
     * @author liyingjie
     * @created 2016年6月15日
     *
     */
    @RequestMapping("/toCouponList")
    public ModelAndView toCuponList(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/couponList");
        return mv;
    }



    /**
     * 活动列表
     *
     * @author liyingjie
     * @created 2016年6月15日
     *
     */
    @RequestMapping("/couponDataList")
    @ResponseBody
    public PageResult couponDataList(@ModelAttribute("paramRequest") ActCouponRequest paramRequest ,HttpServletRequest request){
        String resultJson = activityFullService.getCouponFullList(JsonEntityTransform.Object2Json(paramRequest));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if (resultDto.getCode() == DataTransferObject.SUCCESS){

            List<ActCouponUserEntity> actCouponList = resultDto.parseData("list", new TypeReference<List<ActCouponUserEntity>>() {});
            //返回结果
            PageResult pageResult = new PageResult();
            pageResult.setRows(actCouponList);
            pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));

            return pageResult;
        }else {
            return new PageResult();
        }

    }

    /**
     * 优惠券作废
     *
     * @param couponSn
     * @param remark
     * @return com.asura.framework.base.entity.DataTransferObject
     * 备注:service参数待优化 调用查询优惠券的方法和SQL待优化
     * @author yanb
     * @created 2017年10月23日 20:50:50
     */
    @RequestMapping("/cancelCoupon")
    @ResponseBody
    public DataTransferObject cancelCoupon(CancelCouponDto cancelCouponDto) {
        DataTransferObject dto = new DataTransferObject();
        try {
            cancelCouponDto.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
            cancelCouponDto.setEmpName(UserUtil.getFullCurrentUser().getFullName());
            /*
            String empCode = UserUtil.getFullCurrentUser().getEmpCode();
            String empName = UserUtil.getFullCurrentUser().getFullName();
            */

            String resultJson = actCouponService.cancelCoupon(JsonEntityTransform.Object2Json(cancelCouponDto));
            dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            //判断返回的结果
            if (dto.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(logger, "作废操作失败,参数:{}", JsonEntityTransform.Object2Json(dto));
                return dto;
            }
            int upNum = SOAResParseUtil.getIntFromDataByKey(resultJson, "upNum");
            dto.putValue("upNum", upNum);
        } catch (Exception e) {
            LogUtil.error(logger, "cancelCoupon error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg(e.getMessage());
        }
        return dto;
    }




}
