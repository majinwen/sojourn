/**
 * @FileName: ActivityController.java
 * @Package com.ziroom.minsu.activity.controller
 *
 * @author bushujie
 * @created 2016年5月13日 下午3:04:55
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RegExpUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.activity.constant.SmsTemplateEnum;
import com.ziroom.minsu.activity.enums.ChristmasDateEnum;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.cms.api.inner.ActivityApplyService;
import com.ziroom.minsu.services.cms.api.inner.ActivityChristmasApplyService;
import com.ziroom.minsu.services.cms.dto.ActivityApplyRequest;
import com.ziroom.minsu.services.cms.dto.ChristmasApplyRequest;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>活动申请Controller</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年6月30日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/activityApply")
@Controller
public class ActivityApplyController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ActivityApplyController.class);

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name="cms.activityApplyService")
	private ActivityApplyService activityApplyService;

	@Resource(name="cms.activityChristmasApplyService")
	private ActivityChristmasApplyService activityChristmasApplyService;

	@Resource(name = "basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;



    /**
     * 跳转种子房东初始页面
     * @author lishaochuan
     * @create 2016年6月30日上午11:28:24
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        return "seedAppy/index";
    }


    /**
     * 跳转种子房东初始页面
     * @author lishaochuan
     * @create 2016年6月30日上午11:28:24
     * @param request
     * @return
     */
    @RequestMapping("hetong")
    public String hetong(HttpServletRequest request){
        return "seedAppy/hetong";
    }


	/**
	 * 跳转种子房东申请页面
	 * @author lishaochuan
	 * @create 2016年6月30日上午11:28:24
	 * @param request
	 * @return
	 */
	@RequestMapping("toSeedApply")
	public String toOrderRemark(HttpServletRequest request){
		cascadeDistricts(request);
		return "seedAppy/seedApply";
	}



	/**
	 * 保存种子房东申请
	 * @author lishaochuan
	 * @create 2016年6月30日下午2:24:08
	 * @param request
	 * @return
	 */
	@RequestMapping("saveSeedApply")
	@ResponseBody
	public DataTransferObject saveApply(HttpServletRequest requestm, String apply){
		LogUtil.info(LOGGER, "参数：{}", apply);
		DataTransferObject dto = new DataTransferObject();
		try{
			if (Check.NuNStr(apply)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}
			ActivityApplyRequest applyRequest = JsonEntityTransform.json2Object(apply, ActivityApplyRequest.class);
			if(Check.NuNStr(applyRequest.getCustomerName())){
				dto.setErrCode(1);
				dto.setMsg("请输入姓名");
				return dto;
			}
			if(applyRequest.getCustomerName().length()>20){
				dto.setErrCode(1);
				dto.setMsg("姓名过长");
				return dto;
			}
			if(Check.NuNStr(applyRequest.getCustomerMoblie())){
				dto.setErrCode(1);
				dto.setMsg("请输入联系方式");
				return dto;
			}
			if(!RegExpUtil.isMobilePhoneNum(applyRequest.getCustomerMoblie())){
				dto.setErrCode(1);
				dto.setMsg("请输入正确的手机号");
				return dto;
			}
			if(Check.NuNStr(applyRequest.getCityCode())){
				dto.setErrCode(1);
				dto.setMsg("请选择城市");
				return dto;
			}
			if(Check.NuNStr(applyRequest.getAreaCode())){
				dto.setErrCode(1);
				dto.setMsg("请选择区域");
				return dto;
			}
			if(Check.NuNStr(applyRequest.getCustomerIntroduce()) || applyRequest.getCustomerIntroduce().trim().length()<100){
				dto.setErrCode(1);
				dto.setMsg("房东个人介绍不能少于100字！");
				return dto;
			}
			if(!Check.NuNStr(applyRequest.getCustomerIntroduce()) && applyRequest.getCustomerIntroduce().trim().length()>500){
				dto.setErrCode(1);
				dto.setMsg("房东个人介绍不能超过500字！");
				return dto;
			}
			if(Check.NuNStr(applyRequest.getHouseStory()) || applyRequest.getHouseStory().trim().length()<100){
				dto.setErrCode(1);
				dto.setMsg("房源故事不能少于100字！");
				return dto;
			}
			if(!Check.NuNStr(applyRequest.getHouseStory()) && applyRequest.getHouseStory().trim().length()>500){
				dto.setErrCode(1);
				dto.setMsg("房源故事不能超过500字！");
				return dto;
			}
			if(Check.NuNCollection(applyRequest.getHouseUrlList())){
				dto.setErrCode(1);
				dto.setMsg("请添加房源链接！");
				return dto;
			}
			if(applyRequest.getHouseUrlList().size()>1){
				dto.setErrCode(1);
				dto.setMsg("只能添加一个精选的房源链接！");
				return dto;
			}

			for (String url : applyRequest.getHouseUrlList()) {
				if(Check.NuNStr(url)){
					dto.setErrCode(1);
					dto.setMsg("请添加房源链接");
					return dto;
				}
				if(url.length()>100){
					dto.setErrCode(1);
					dto.setMsg("房源链接太长了");
					return dto;
				}

				Pattern p = Pattern.compile("^(http(s)?://)[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*",Pattern.CASE_INSENSITIVE );
				Matcher m = p.matcher(url);
		        if(!m.find()){
		        	dto.setErrCode(1);
					dto.setMsg("房源URL不正确，请填写标准的URL");
					return dto;
		        }
			}

			if(Check.NuNStr(applyRequest.getGiftAddress())){
				dto.setErrCode(1);
				dto.setMsg("请填写礼包收货地址！");
				return dto;
			}
			if(!Check.NuNStr(applyRequest.getGiftAddress()) && applyRequest.getGiftAddress().trim().length()>50){
				dto.setErrCode(1);
				dto.setMsg("礼包收货地址过长！");
				return dto;
			}

			if(!Check.NuNStr(applyRequest.getRemark()) && applyRequest.getRemark().trim().length()>100){
				dto.setErrCode(1);
				dto.setMsg("备注过长！");
				return dto;
			}


//			if(Check.NuNStr(applyRequest.getHouseScore())){
//				dto.setErrCode(1);
//				dto.setMsg("请填写房源平均评分");
//				return dto;
//			}
//			if(applyRequest.getHouseScore().length()>10){
//				dto.setErrCode(1);
//				dto.setMsg("评分过长");
//				return dto;
//			}
//			if(Check.NuNObj(applyRequest.getHouseNum())){
//				dto.setErrCode(1);
//				dto.setMsg("请填写房源数量");
//				return dto;
//			}
//			if(applyRequest.getHouseNum() > 99){
//				dto.setErrCode(1);
//				dto.setMsg("房源数量太大了");
//				return dto;
//			}
//			if(Check.NuNObj(applyRequest.getIsZlan())){
//				dto.setErrCode(1);
//				dto.setMsg("请选择是否自如民宿房东");
//				return dto;
//			}

			String resultJson = activityApplyService.saveApply(apply);

			LogUtil.info(LOGGER, "结果：{}", resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("操作失败");
		}
		return dto;
	}

	/**
	 *
	 * 保存天使房东 pc(端)
	 *
	 * @author jixd
	 * @created 2016年9月19日 下午3:24:11
	 *
	 * @param request
	 * @param apply
	 * @return
	 */
	@RequestMapping("/saveApplyForPC")
	public void saveApplyForPC(HttpServletRequest request,HttpServletResponse response,String apply){
		response.setContentType("text/plain");
		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
//		response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "2000" ); // time from request to response before timeout
		String callBackName = request.getParameter("callbackparam");
		DataTransferObject dto = this.saveApply(request, apply);
		try {
			if (Check.NuNStr(callBackName)) {
				response.getWriter().write(dto.toJsonString());
			}else{
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取给房东开通的城市
	 * @author zl
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getCity")
	public void getCity(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/plain");
		String callBackName = request.getParameter("callbackparam");
		try {

			String resultJson = confCityService.getConfCitySelectForLandlord();
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<TreeNodeVo> cityList = dto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
			DataTransferObject resultDto = new DataTransferObject();
			if (!Check.NuNCollection(cityList)) {
				List<Map<String, Object>> cityMapList= new ArrayList<Map<String, Object>>();
				for (TreeNodeVo treeNodeVo : cityList) {//国家
					List<TreeNodeVo> provices = treeNodeVo.getNodes();
					if (!Check.NuNCollection(provices)) {
						for (TreeNodeVo proviceNode : provices) {//省份
							List<TreeNodeVo> citys = proviceNode.getNodes();
							if (!Check.NuNCollection(citys)) {
								for (TreeNodeVo cityNode : citys) {//城市
									Map<String, Object> cityMap = new HashMap<String, Object>();
									cityMap.put("text", cityNode.getText());
									cityMap.put("code", cityNode.getCode());
									List<TreeNodeVo> areas = cityNode.getNodes();
									if (!Check.NuNCollection(areas)) {
										List<Map<String, Object>> areaList= new ArrayList<Map<String, Object>>();
										for (TreeNodeVo area : areas) {//区域
											Map<String, Object> areaMap = new HashMap<String, Object>();
											areaMap.put("text", area.getText());
											areaMap.put("code", area.getCode());
											areaList.add(areaMap);
										}
										cityMap.put("list", areaList);
									}
									cityMapList.add(cityMap);
								}
							}

						}
					}

				}
				resultDto.putValue("cityList", cityMapList);
			}

			if (Check.NuNStr(callBackName)) {
				response.getWriter().write(resultDto.toJsonString());
			}else{
				response.getWriter().write(callBackName + "("+resultDto.toJsonString()+")");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 跳转成功页
	 * @author lishaochuan
	 * @create 2016年6月30日下午5:09:53
	 * @param request
	 * @return
	 */
	@RequestMapping("toSuccess")
	public String toSuccess(HttpServletRequest request){
		return "seedAppy/ok";
	}

	/**
	 * 获取行政区域列表
	 * @author lishaochuan
	 * @create 2016年6月30日上午11:29:13
	 * @param request
	 */
	private void cascadeDistricts(HttpServletRequest request) {
		String resultJson = confCityService.getConfCitySelect();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<TreeNodeVo> cityTreeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	};


	/**
	 * 圣诞活动报名申请
	 *
	 * @param
	 * @return
	 * @author lishaochuan
	 * @create 2016/12/9 14:46
	 */
	@RequestMapping("/christmasApply")
	public void christmasApply(HttpServletRequest request,HttpServletResponse response,ChristmasApplyRequest apply){
		response.setContentType("text/plain");
		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
		String callback = request.getParameter("callback");
		DataTransferObject dto = this.christmasApply(apply);

		LogUtil.info(LOGGER, "par:{}", JsonEntityTransform.Object2Json(apply));

		try {
			if (Check.NuNStr(callback)) {
				response.getWriter().write(dto.toJsonString());
			}else{
				response.getWriter().write(callback + "("+dto.toJsonString()+")");
			}
		} catch (IOException e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

	/**
	 * 圣诞活动报名申请
	 * @param apply
	 * @return
     */
	private DataTransferObject christmasApply(ChristmasApplyRequest apply) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if (Check.NuNObj(apply) || Check.NuNObj(apply.getHouseSn())
					|| Check.NuNObj(apply.getHouseName()) || Check.NuNObj(apply.getMobile())
					|| Check.NuNObj(apply.getActivityDate()) || Check.NuNObj(apply.getApplyReason())) {
				dto.setErrCode(1);
				dto.setMsg("参数错误！");
				return dto;
			}
			if(!RegExpUtil.isMobilePhoneNum(apply.getMobile())){
				dto.setErrCode(1);
				dto.setMsg("请输入正确的手机号");
				return dto;
			}
			if(apply.getApplyReason().length() > 500){
				dto.setErrCode(1);
				dto.setMsg("申请理由不能超过500字哦");
				return dto;
			}
			if(!DateUtil.isDate(apply.getActivityDate())){
				dto.setErrCode(1);
				dto.setMsg("活动日期存在问题哦");
				return dto;
			}

			// 参与活动日期时间校验
			boolean activityDateFlag = ChristmasDateEnum.checkInputActivityDateStr(apply.getActivityDate());
			if(!activityDateFlag){
				dto.setErrCode(1);
				dto.setMsg("哎呦，今天的申请已经结束啦，下拉页面领取民宿优惠券，或是换一个奇妙夜申请吧！");
				return dto;
			}

			String resultJson = activityChristmasApplyService.apply(JsonEntityTransform.Object2Json(apply));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if(dto.getCode() == DataTransferObject.SUCCESS){
				// 发短信
				this.sendMsg4ChristmasApply(apply);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("操作失败");
		}
		return dto;
	}


	private void sendMsg4ChristmasApply(ChristmasApplyRequest apply){
		try {
			//匹配当前固定的模板信息
			String msg = SmsTemplateEnum.CHRISTMAS_APPLY_SUCCESS.getName();
			msg = msg.replace("{activityDate}", apply.getActivityDate());
			msg = msg.replace("{houseName}", apply.getHouseName());
			msg = msg.replace("{awardDate}", ChristmasDateEnum.getAwardTime(apply.getActivityDate()));

			SmsRequest smsRequest = new SmsRequest();
			smsRequest.setMobile(apply.getMobile());
			smsRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.EMPTY.getCode()));
			Map<String, String> conMap = new HashMap<>();
			conMap.put("{1}", msg);
			smsRequest.setParamsMap(conMap);
			this.smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
		}catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}


}
