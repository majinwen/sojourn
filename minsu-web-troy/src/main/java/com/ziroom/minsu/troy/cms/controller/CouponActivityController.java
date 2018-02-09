package com.ziroom.minsu.troy.cms.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.*;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.cms.api.inner.ActivityFullService;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.troy.cms.service.CityService;
import com.ziroom.minsu.troy.cms.service.GroupService;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.cms.*;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>cms活动相关</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月26日
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("activity")
public class CouponActivityController {
	
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CouponActivityController.class);


    @Resource(name="api.cityService")
    private CityService cityService;



    @Resource(name="api.groupService")
    private GroupService groupService;


	@Resource(name="cms.activityService")
	private ActivityService activityService;
	

	@Resource(name="cms.activityFullService")
    private ActivityFullService activityFullService;

    @Resource(name = "customer.customerRoleService")
    private CustomerRoleService customerRoleService;

    @Resource(name="house.troyHouseMgtService")
    private TroyHouseMgtService troyHouseMgtService;
    
    @Resource(name="basedata.zkSysService")
    private ZkSysService zkSysService;
    
    /**
     * 新增活动
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping(value = "/toSaveActivity", method = RequestMethod.GET)
    public ModelAndView toSaveActivity(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/activity/couponActivitySave");
        ActivityFullEntity ac = new ActivityFullEntity();
        List<Map> openList = cityService.getOpenCityList();
        mv.addObject("warnPhone",zkSysService.getZkSysValue("minsu", "warnPhone"));
        mv.addObject("activity", ac);
        mv.addObject("cityList", openList);
        mv.addObject("groupList",groupService.getAllGroup());
        return mv;
    }



    /**
     * 修改活动
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping(value = "/toUpdateActivity", method = RequestMethod.GET)
    public ModelAndView toUpdateActivity(HttpServletRequest request){
    	
        ModelAndView mv = new ModelAndView("/activity/couponActivityUpdate");
        return toActivityInfo(request,mv);
    }

    
    /**
     * 修改活动
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping(value = "/toQueryActivity", method = RequestMethod.GET)
    public ModelAndView toQueryActivity(HttpServletRequest request){
    	
        ModelAndView mv = new ModelAndView("/activity/couponActivityInfo");
        return toActivityInfo(request,mv);
    }

    /**
     * 
     * 查询优惠卷活动详情
     *
     * @author yd
     * @created 2016年10月12日 下午3:33:25
     *
     * @param request
     * @param mv
     * @return
     */
    private ModelAndView toActivityInfo(HttpServletRequest request, ModelAndView mv ){
    	
    	 
        String actSn = request.getParameter("activitySn");
        String resultJson = activityFullService.getActivityFullBySn(actSn);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        ActivityFullEntity ac  = resultDto.parseData("full", new TypeReference<ActivityFullEntity>() {});
        if (!Check.NuNObj(ac)){
            Integer times = ac.getTimes();
            if (!Check.NuNObj(times) && times > 0){
                ac.setLimitTime(YesOrNoEnum.YES.getCode());
            }else {
                ac.setLimitTime(YesOrNoEnum.NO.getCode());
            }
        }
        List<ActivityCityEntity> cityEntityList = resultDto.parseData("cityList", new TypeReference<List<ActivityCityEntity>>() {});
        if(!Check.NuNCollection(cityEntityList)){
            String cityCodeStr = "";
            for(int i = 0;i<cityEntityList.size() ; i++){
                if(i == 0){
                    cityCodeStr += cityEntityList.get(i).getCityCode();
                }else{
                    cityCodeStr += ","+cityEntityList.get(i).getCityCode();
                }
            }
            if ("0".equals(cityCodeStr)){
                mv.addObject("all","1");
            }else {
                mv.addObject("all","0");
            }
            mv.addObject("cityCodeStr",cityCodeStr);
        }
        // 金额数据库精确到分
        if (ac.getActType() == ActTypeEnum.CACHE.getCode()) {
            ac.setActLimit(ac.getActLimit()==null?0:ac.getActLimit()/100);
            ac.setActCut(ac.getActCut()==null?0:ac.getActCut()/ 100);
        }else if(ac.getActType() == ActTypeEnum.FREE.getCode()){
            ac.setActLimit(ac.getActLimit()==null?0:ac.getActLimit()/ 100);
            ac.setActMax(ac.getActMax()==null?0:ac.getActMax()/ 100);
        }

        String houseSns = "";
        if (ac.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
            DataTransferObject limitHouseDto = JsonEntityTransform.json2DataTransferObject(activityFullService.findLimitHouseByActsn(ac.getActSn()));
            List<ActivityHouseEntity> list = limitHouseDto.parseData("list", new TypeReference<List<ActivityHouseEntity>>() {});
            for (ActivityHouseEntity houseEntity : list){
                String houseSn = houseEntity.getHouseSn();
                if (Check.NuNStr(houseSns)){
                    houseSns += houseSn;
                }else{
                    houseSns += ","+houseSn;
                }
            }
        }

        List<Map> openList = cityService.getOpenCityList();
        mv.addObject("warnPhone",zkSysService.getZkSysValue("minsu", "warnPhone"));
        mv.addObject("activity", ac);
        mv.addObject("cityList", openList);
        mv.addObject("groupList",groupService.getAllGroup());
        mv.addObject("houseSns",houseSns);
        return mv;
    }




    @RequestMapping(value = "/checkActivityCode", method = RequestMethod.POST)
    @ResponseBody
    public  DataTransferObject checkActivityCode(HttpServletRequest request,String code){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动码为空");
            return dto;
        }
        if(!SnUtil.checkActSn(code)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("不合法的活动码");
            return dto;
        }

        ActivityGroupEntity groupEntity = groupService.getGroupBySN(code);
        if (!Check.NuNObj(groupEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动码和组码重复，当前组名称："+groupEntity.getGroupName());
            return dto;
        }

        String resultJson = activityService.getActivityBySn(code);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        if (resultDto.getCode() != DataTransferObject.SUCCESS){
            return resultDto;
        }
        ActivityEntity acie = resultDto.parseData("activity", new TypeReference<ActivityEntity>() {});
    	if(!Check.NuNObj(acie)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("活动码重复【"+acie.getActName()+":"+acie.getActSn()+"】");
    	}
        return dto;
    }


    /**
     * 将当前的参数填充到活动对象
     * @param request
     * @param ac
     */
    private void transRequest2Ac(HttpServletRequest request,ActivityFullEntity ac,DataTransferObject dto){
        if (Check.NuNObjs(request,ac)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return;
        }
        if (Check.NuNObj(ac.getCouponTimeType())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("异常的优惠券限制类型");
            return;
        }else {
            CouponTimeTypeEnum couponTimeTypeEnum = CouponTimeTypeEnum.getByCode(ac.getCouponTimeType());
            if (Check.NuNObj(couponTimeTypeEnum)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("异常的优惠券限制类型");
                return;
            }
            if (couponTimeTypeEnum.getCode() == CouponTimeTypeEnum.FIX.getCode()){
                //固定时间
                ac.setCouponTimeLast(0);
            }else {
                ac.setCouponStartTime(null);
                ac.setCouponEndTime(null);
            }
        }
        // 金额数据库精确到分
        if(ac.getActType() == ActTypeEnum.CACHE.getCode()){
            ac.setActLimit(ac.getActLimit() * 100);
            ac.setActMax(null);
            ac.setActCut(ac.getActCut() * 100);
        }else if(ac.getActType() == ActTypeEnum.FREE.getCode() || ac.getActType() == ActTypeEnum.CUT.getCode()){
//            ac.setActLimit(ac.getActLimit() * 100);
            ac.setActMax(ac.getActMax() * 100);
        }
        try {
            //各种日期设置
            String sActStartTime = request.getParameter("sActStartTime");
            if(!Check.NuNStr(sActStartTime)){
                ac.setActStartTime(DateUtil.parseDate(sActStartTime, "yyyy-MM-dd HH:mm:ss"));
            }

            String  sActEndTime = request.getParameter("sActEndTime");
            if(!Check.NuNStr(sActEndTime)){
                ac.setActEndTime(DateUtil.parseDate(sActEndTime, "yyyy-MM-dd HH:mm:ss"));
            }

            //固定的限制时间 不做时间的限制
            if (ValueUtil.getintValue(ac.getCouponTimeType()) != CouponTimeTypeEnum.COUNT.getCode()){
                String sCouponStartTime = request.getParameter("sCouponStartTime");
                if(!Check.NuNStr(sCouponStartTime)){
                    ac.setCouponStartTime(DateUtil.parseDate(sCouponStartTime, "yyyy-MM-dd HH:mm:ss"));
                }

                String sCouponEndTime = request.getParameter("sCouponEndTime");
                if(!Check.NuNStr(sCouponEndTime)){
                    ac.setCouponEndTime(DateUtil.parseDate(sCouponEndTime, "yyyy-MM-dd HH:mm:ss"));
                }
            }


            if(ac.getIsCheckTime() == 1){
                String sCheckInTime = request.getParameter("sCheckInTime");
                if(!Check.NuNStr(sCheckInTime)){
                    ac.setCheckInTime(DateUtil.parseDate(sCheckInTime, "yyyy-MM-dd HH:mm:ss"));
                }
                String sCheckOutTime = request.getParameter("sCheckOutTime");
                if(!Check.NuNStr(sCheckOutTime)){
                    ac.setCheckOutTime(DateUtil.parseDate(sCheckOutTime, "yyyy-MM-dd HH:mm:ss"));
                }
            }
        } catch (ParseException e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
    }



    /**
     * 新增活动
     * @author afi
     * @created 2016年9月11日 上午10:29:18
     * @param request
     */
    @RequestMapping(value = "/saveActivityInfo", method = RequestMethod.POST)
    @ResponseBody
    public DataTransferObject saveActivityInfo(HttpServletRequest request,@ModelAttribute ActivityFullEntity ac) {
        DataTransferObject dto = new DataTransferObject();

        String userFid = UserUtil.getCurrentUserFid();
        ac.setCreateId(userFid);
        //设置cityCode
        String allCityCode = request.getParameter("allCityCode");
        String []cityCodeArray = request.getParameterValues("cityCodeArray");
        String limitHouseSns = request.getParameter("limitHouseSns");
        if (ac.getIsLimitHouse() == YesOrNoEnum.YES.getCode()){
            if (Check.NuNStr(limitHouseSns)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("限制房源编号不能为空");
                return dto;
            }else{
                dto = checkLimitHouse(limitHouseSns);
            }
        }

        if (dto.getCode() != DataTransferObject.SUCCESS){
            return dto;
        }
        //将请求的参数转化对象
        this.transRequest2Ac(request,ac,dto);
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return dto;
        }
        ac.setActStatus(ActStatusEnum.DISABLE.getCode());//活动状态 未启用
        ac.setIsCoupon(IsCouponEnum.NO.getCode());//是否已生成优惠券
        ac.setActKind(ActKindEnum.COUPON.getCode());
        String paramJson = JsonEntityTransform.Object2Json(ac);
        String resultJson = activityService.saveActCoupon(paramJson,cityService.getCityCode(allCityCode,cityCodeArray),limitHouseSns);
        LogUtil.info(LOGGER, "resultJson:{}", resultJson);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        return resultDto;
    }

    /**
     *
     * @author jixd
     * @created 2016年11月21日 10:10:18
     * @param
     * @return
     */
    @RequestMapping("/checkLimitHouse")
    @ResponseBody
    public DataTransferObject checkLimitHouse(String houseSns){
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(houseSns)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房源编号为空");
            return dto;
        }
        String[] houseSnArr = houseSns.split(",");
        for (String houseSn : houseSnArr){

            if (houseSn.contains("Z")){
                DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(troyHouseMgtService.findHouseBaseByHouseSn(houseSn));
                HouseBaseMsgEntity houseBase = houseDto.parseData("houseBase", new TypeReference<HouseBaseMsgEntity>() {});
                if (Check.NuNObj(houseBase)){
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("房源不存在，编号："+houseSn);
                    return dto;
                }
            }else if (houseSn.contains("F")){
                DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(troyHouseMgtService.findHouseRoomMsgByRoomSn(houseSn));
                HouseRoomMsgEntity roomBase = roomDto.parseData("roomBase", new TypeReference<HouseRoomMsgEntity>() {});
                if (Check.NuNObj(roomBase)){
                    dto.setErrCode(DataTransferObject.ERROR);
                    dto.setMsg("房间不存在，编号："+houseSn);
                    return dto;
                }
            }else{
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("格式错误，编号："+houseSn);
                return dto;
            }
        }
        return dto;
    }



    /**
     * 保存活动
     * @author afi
     * @created 2016年9月11日 上午10:29:18
     * @param request
     */
    @RequestMapping(value = "/updateActCoupon", method = RequestMethod.POST)
    @ResponseBody
    public DataTransferObject updateActCoupon(HttpServletRequest request,@ModelAttribute ActivityFullEntity ac) {
        DataTransferObject dto = new DataTransferObject();
        //设置cityCode
        String allCityCode = request.getParameter("allCityCode");
        String []cityCodeArray = request.getParameterValues("cityCodeArray");
        //将请求的参数转化对象
        this.transRequest2Ac(request,ac,dto);
        if (dto.getCode() != DataTransferObject.SUCCESS){
            return dto;
        }
        //设置入住时间
        if(ac.getIsCheckTime() == YesOrNoEnum.NO.getCode()){
            ac.setCheckInTime(null);
            ac.setCheckOutTime(null);
        }
        String paramJson = JsonEntityTransform.Object2Json(ac);
        String resultJson = activityService.updateActCoupon(paramJson,cityService.getCityCode(allCityCode,cityCodeArray));
        LogUtil.info(LOGGER, "resultJson:{}", resultJson);
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
        return resultDto;
    }




//
//
//
//    /**
//     * 保存活动
//     * @author liyingjie
//     * @created 2016年6月15日 上午10:29:18
//     *
//     * @param request
//     */
//    @RequestMapping("/addActivityInfo")
//    public String addActivityInfo(HttpServletRequest request,@ModelAttribute ActivityFullEntity actCouponVo) {
//
//        Map<String, Object> resMap = new HashMap<String,Object>();
//        //先做copy
//
//        //设置cityCode
//        String allCityCode = request.getParameter("allCityCode");
//        String []cityCodeArray = request.getParameterValues("cityCodeArray");
//        // 金额数据库精确到分
//        if(actCouponVo.getActType() == ActTypeEnum.CACHE.getCode()){
//        	actCouponVo.setActLimit(actCouponVo.getActLimit() * 100);
//        	actCouponVo.setActMax(null);
//        	actCouponVo.setActCut(actCouponVo.getActCut() * 100);
//        }else if(actCouponVo.getActType() == ActTypeEnum.FREE.getCode()){
//        	//actCouponVo.setActLimit(actCouponVo.getActLimit() * 100);
//        	actCouponVo.setActMax(actCouponVo.getActMax() * 100);
//        }
//
//
//        try {
//            //各种日期设置
//            String sActStartTime = request.getParameter("sActStartTime");
//            if(!Check.NuNStr(sActStartTime)){
//            	actCouponVo.setActStartTime(DateUtil.parseDate(sActStartTime, "yyyy-MM-dd"));
//            }
//
//            String  sActEndTime = request.getParameter("sActEndTime");
//            if(!Check.NuNStr(sActEndTime)){
//            	actCouponVo.setActEndTime(DateUtil.parseDate(sActEndTime, "yyyy-MM-dd"));
//            }
//
//            String sCouponStartTime = request.getParameter("sCouponStartTime");
//            if(!Check.NuNStr(sCouponStartTime)){
//            	actCouponVo.setCouponStartTime(DateUtil.parseDate(sCouponStartTime, "yyyy-MM-dd"));
//            }
//
//            String sCouponEndTime = request.getParameter("sCouponEndTime");
//            if(!Check.NuNStr(sCouponEndTime)){
//            	actCouponVo.setCouponEndTime(DateUtil.parseDate(sCouponEndTime, "yyyy-MM-dd"));
//            }
//
//            if(actCouponVo.getIsCheckTime() == 1){
//                String sCheckInTime = request.getParameter("sCheckInTime");
//                if(!Check.NuNStr(sCheckInTime)){
//                	actCouponVo.setCheckInTime(DateUtil.parseDate(sCheckInTime, "yyyy-MM-dd"));
//                }
//
//                String sCheckOutTime = request.getParameter("sCheckOutTime");
//                if(!Check.NuNStr(sCheckOutTime)){
//                	actCouponVo.setCheckOutTime(DateUtil.parseDate(sCheckOutTime, "yyyy-MM-dd"));
//                }
//            }
//
//
//        } catch (ParseException e) {
//            LogUtil.error(LOGGER, "error:{}", e);
//        }
//
//        String resultJson = "";
//        String editFlag = request.getParameter("editFlag");
//        if(editFlag.equals("new")){
//        	actCouponVo.setActStatus(1);//活动状态 未启用
//        	actCouponVo.setIsCoupon(0);//是否已生成优惠券
//        	actCouponVo.setActKind(1);
//        	String paramJson = JsonEntityTransform.Object2Json(actCouponVo);
//            resultJson = activityService.saveActCoupon(paramJson,getCityCode(allCityCode,cityCodeArray));
//        }else if(editFlag.equals("update")){
//        	if(actCouponVo.getIsCheckTime() == YesOrNoEnum.NO.getCode()){
//        		actCouponVo.setCheckInTime(null);
//        		actCouponVo.setCheckOutTime(null);
//        	}
//        	resultJson = activityService.saveActCoupon(JsonEntityTransform.Object2Json(actCouponVo),getCityCode(allCityCode,cityCodeArray));
//        }
//        LogUtil.info(LOGGER, "resultJson:{}", resultJson);
//
//        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
//        if(resultDto.getCode() == 0){
//            resMap.put("status", "success");
//        }else{
//            resMap.put("status", "fail");
//            resMap.put("message", resultDto.getMsg());
//        }
//
//        return "redirect:/activity/activityList";
//    }





}
