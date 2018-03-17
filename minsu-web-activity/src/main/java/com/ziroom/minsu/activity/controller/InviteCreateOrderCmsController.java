/**
 * @FileName: InviteCreateOrderCmsController.java
 * @Package com.ziroom.minsu.activity.controller
 * 
 * @author loushuai
 * @created 2017年12月1日 下午6:54:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.activity.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.cms.api.inner.InviteCreateOrderCmsService;
import com.ziroom.minsu.services.cms.constant.InviterCreateOrderConst;
import com.ziroom.minsu.services.cms.dto.InviteCmsRequest;
import com.ziroom.minsu.services.cms.entity.BeInviterInfo;
import com.ziroom.minsu.services.cms.entity.InviterDetailVo;
import com.ziroom.minsu.services.customer.api.inner.CustomerChatService;
import com.ziroom.minsu.services.customer.dto.CustomerChatRequest;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.BeInviterStatusInfoRequest;
import com.ziroom.minsu.services.order.entity.BeInviterStatusInfoVo;
import com.ziroom.minsu.valenum.cms.BeInviterStatusEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

/**
 * <p>邀请好友下单</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/inviteCreateOrderCms")
@Controller
public class InviteCreateOrderCmsController {

	@Resource(name="cms.inviteCreateOrderCmsService")
	private InviteCreateOrderCmsService inviteCreateOrderCmsService;
	
	@Resource(name="customer.customerChatService")
	private CustomerChatService customerChatService;
	
	@Resource(name="order.orderCommonService")
	private OrderCommonService orderCommonService;
	
	
	 /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(InviteCreateOrderCmsController.class);
    
    /**
     * 
     * 邀请人详情页
     *
     * @author loushuai
     * @created 2017年12月5日 上午11:55:08
     *
     * @param response
     * @param inviterDetailRequest
     * @param request
     * @throws IOException
     */
    @RequestMapping("inviteOthersDetail")
    @ResponseBody
    public void inviteOthersDetail(HttpServletResponse response, InviteCmsRequest inviterDetailRequest, HttpServletRequest request) throws IOException{
    	long start = System.currentTimeMillis();
    	LogUtil.info(LOGGER, "inviteOthersDetail uid={}, actSn={}", JsonEntityTransform.Object2Json(inviterDetailRequest));
    	DataTransferObject dto = new DataTransferObject();
    	response.setContentType("text/plain");
    	String callBackName = request.getParameter("callback");
    	if(Check.NuNObj(inviterDetailRequest)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("参数为空");
    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    		return;
    	}
    	String uid = inviterDetailRequest.getUid();
    	if(Check.NuNStr(uid)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("uid为空");
    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    		return;
    	}
    	/*String actSn = inviterDetailRequest.getActSn();
    	if(Check.NuNStr(actSn)){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("actSn为空");
    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    		return;
    	}*/
    	String result = inviteCreateOrderCmsService.getInviterDetail(JsonEntityTransform.Object2Json(inviterDetailRequest));
    	DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
    	if(resultDto.getCode()==DataTransferObject.ERROR){
    		dto.setErrCode(DataTransferObject.ERROR);
    		dto.setMsg("获取积分失败");
    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    		return;
    	}
    	InviterDetailVo inviterDetailVo = resultDto.parseData("inviterDetailVo", new TypeReference<InviterDetailVo>() {});
    	//填充被邀请人的头像，昵称，预期积分
    	if(!Check.NuNObj(inviterDetailVo) && (!Check.NuNCollection(inviterDetailVo.getBeInviteUidList()))){
    		
    		//所有被邀请人信息集合
			List<BeInviterInfo> beInviterInfoList = new ArrayList<BeInviterInfo>();
			//已经预定被邀请人信息集合
			List<BeInviterInfo> hasOrderGivePointsList = new ArrayList<BeInviterInfo>();
			//紧紧是接收邀请未下单的被邀请人信息集合
			List<BeInviterInfo> onlyAccetList = new ArrayList<BeInviterInfo>();
			//已经过期被邀请人信息集合
			List<BeInviterInfo> hasExpireGivePointsList = new ArrayList<BeInviterInfo>();
			
			List<BeInviterInfo> hasnotGivePointsList = inviterDetailVo.getHasnotGivePointsList();
			List<String> hasnotGivePointsUidList = inviterDetailVo.getHasnotGivePointsUidList();
			if(!Check.NuNCollection(hasnotGivePointsUidList)){
				//填充被邀请人状态，展示文案，及预期奖金
				BeInviterStatusInfoRequest param = new BeInviterStatusInfoRequest();
				param.setBeInviterInfoList(hasnotGivePointsUidList);
				//param.setOrderStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
				String beInviterStatusInfoJson = orderCommonService.getBeInviterStatusInfo(JsonEntityTransform.Object2Json(param));
				LogUtil.info(LOGGER, "inviteOthersDetail方法，获取被邀请人的订单状况结果={}", JsonEntityTransform.Object2Json(beInviterStatusInfoJson));
				DataTransferObject beInviterStatusInfoDto  = JsonEntityTransform.json2DataTransferObject(beInviterStatusInfoJson);
				if(beInviterStatusInfoDto.getCode()==DataTransferObject.SUCCESS){
					List<BeInviterStatusInfoVo> beInviterStatusInfos = beInviterStatusInfoDto.parseData("beInviterStatusInfos", new TypeReference<List<BeInviterStatusInfoVo>>() {});
					for (BeInviterStatusInfoVo beInviterStatusInfoVo : beInviterStatusInfos) {
						for (BeInviterInfo beInviterInfo : hasnotGivePointsList) {
							if(beInviterStatusInfoVo.getBeInviterUid().equals(beInviterInfo.getBeInviterUid())){
								Date date = new Date();
								if(beInviterStatusInfoVo.getOrderNum()>0 && beInviterInfo.getIsGiveInviterPoints()==YesOrNoEnum.NO.getCode() 
											&& beInviterStatusInfoVo.getOrderCreateTime().before(beInviterInfo.getExpiryTime())
											&& beInviterStatusInfoVo.getOrderCreateTime().after(beInviterInfo.getInviteTime())){
										beInviterInfo.setInviteStatusCode(BeInviterStatusEnum.HAS_CREATE_ORDER.getStatusCode());
										beInviterInfo.setInviteStatusName(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_CREATE_ORDER.getStatusCode()).getStatusName());
										beInviterInfo.setInviteStatusShow(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_CREATE_ORDER.getStatusCode()).getStatusShow());
										hasOrderGivePointsList.add(beInviterInfo);
								}else if(date.after(beInviterInfo.getExpiryTime()) && beInviterStatusInfoVo.getOrderNum() == 0){
									beInviterInfo.setInviteStatusCode(BeInviterStatusEnum.HAS_INVALID.getStatusCode());
									beInviterInfo.setInviteStatusName(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_INVALID.getStatusCode()).getStatusName());
									beInviterInfo.setInviteStatusShow(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_INVALID.getStatusCode()).getStatusShow());
									beInviterInfo.setExpectBonus(BeInviterStatusEnum.getByStatusCode(BeInviterStatusEnum.HAS_INVALID.getStatusCode()).getStatusName());
									beInviterInfo.setExpectBonusShow(null);
									hasExpireGivePointsList.add(beInviterInfo);
								}else{
									onlyAccetList.add(beInviterInfo);
								}
							}
						}
					}
				}
			}
			List<BeInviterInfo> hasGivePointsList = inviterDetailVo.getHasGivePointsList();
			beInviterInfoList.addAll(hasGivePointsList);
			beInviterInfoList.addAll(hasOrderGivePointsList);
			beInviterInfoList.addAll(onlyAccetList);
			beInviterInfoList.addAll(hasExpireGivePointsList);
			List<String> beInviterUidList = new ArrayList<String>();
			for (BeInviterInfo beInviterInfo : beInviterInfoList) {
				beInviterUidList.add(beInviterInfo.getBeInviterUid());
			}
			List<CustomerVo> customerVoList = null;
    		//获取被邀请人的头像，昵称
    		CustomerChatRequest chatRequest = new CustomerChatRequest();
			chatRequest.setUidList(beInviterUidList);
			String userPicAndNickName = customerChatService.getListUserPicAndNickName(JsonEntityTransform.Object2Json(chatRequest));
			DataTransferObject userPicDto  = JsonEntityTransform.json2DataTransferObject(userPicAndNickName);
			if(userPicDto.getCode()==DataTransferObject.SUCCESS){
				customerVoList = userPicDto.parseData("list", new TypeReference<List<CustomerVo>>() {});
				for (CustomerVo customerVo : customerVoList) {
					for (BeInviterInfo beInviterInfo : beInviterInfoList) {
						if(customerVo.getUid().equals(beInviterInfo.getBeInviterUid())){
							beInviterInfo.setBeInviterUid(customerVo.getUid());
							beInviterInfo.setHeadUrl(customerVo.getUserPicUrl());
							beInviterInfo.setNickName(customerVo.getNickName());
						}
					}
				}
			}
			
			inviterDetailVo.setBeInviterInfoList(beInviterInfoList);
    	}
    	dto.putValue("inviterDetailVo", inviterDetailVo);
    	response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
    	long end = System.currentTimeMillis();
    	LogUtil.info(LOGGER, "inviteOthersDetail耗时={}", end-start);
    }
    
    /**
     * 
     * 获取邀请码
     *
     * @author loushuai
     * @created 2017年12月5日 上午11:55:32
     *
     * @param response
     * @param inviteUid
     * @param request
     */
    @RequestMapping("getInviteCode")
    @ResponseBody
    public void getInviteCode(HttpServletResponse response, InviteCmsRequest inviterDetailRequest, HttpServletRequest request){
    	LogUtil.info(LOGGER, "getInviteCode inviterDetailRequest={}", JsonEntityTransform.Object2Json(inviterDetailRequest));
    	try {
			DataTransferObject dto = new DataTransferObject();
			response.setContentType("text/plain");
			String callBackName = request.getParameter("callback");
			if(Check.NuNObj(inviterDetailRequest)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			if(Check.NuNStr(inviterDetailRequest.getUid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("uid为空");
				response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
				return;
			}
			String result = inviteCreateOrderCmsService.getOrInitInviteCode(JsonEntityTransform.Object2Json(inviterDetailRequest));
			response.getWriter().write(callBackName + "("+result+")");
		} catch (IOException e) {
			LogUtil.info(LOGGER, "getInviteCode 获取邀请码异常  e={}", e);
		}
    }
    
    /**
     * 
     * 获取邀请人积分可兑换列表
     *
     * @author loushuai
     * @created 2017年12月5日 上午11:55:32
     *
     * @param response
     * @param inviteUid
     * @param request
     */
    @RequestMapping("getCouponList")
    @ResponseBody
    public void getCouponList(HttpServletResponse response, InviteCmsRequest inviteCmsRequest, HttpServletRequest request){
    	LogUtil.info(LOGGER, "getCouponList inviteCmsRequest={}", JsonEntityTransform.Object2Json(inviteCmsRequest));
    	try {
			DataTransferObject dto = new DataTransferObject();
			response.setContentType("text/plain");
			String callBackName = request.getParameter("callback");
			if(Check.NuNObj(inviteCmsRequest)){
	    		dto.setErrCode(DataTransferObject.ERROR);
	    		dto.setMsg("参数为空");
	    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	    		return;
	    	}
	    	if(Check.NuNStr(inviteCmsRequest.getUid())){
	    		dto.setErrCode(DataTransferObject.ERROR);
	    		dto.setMsg("邀请人uid为空");
	    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	    		return;
	    	}
	    	inviteCmsRequest.setGroupSn(InviterCreateOrderConst.inviterGroupSn);
			String result = inviteCreateOrderCmsService.getCouponList(JsonEntityTransform.Object2Json(inviteCmsRequest));
			DataTransferObject resultDto  = JsonEntityTransform.json2DataTransferObject(result);
			response.getWriter().write(callBackName + "("+resultDto.toJsonString()+")");
		} catch (IOException e) {
			LogUtil.info(LOGGER, "getCouponList 获取邀请人积分可兑换列表异常  e={}", e);
		}
    }
    
    
    /**
     * 
     * 邀请人发起积分兑换
     *
     * @author loushuai
     * @created 2017年12月5日 上午11:55:32
     *
     * @param response
     * @param inviteUid
     * @param request
     */
    @RequestMapping("pointsExchange")
    @ResponseBody
    public void pointsExchange(HttpServletResponse response, InviteCmsRequest inviteCmsRequest, HttpServletRequest request){
    	LogUtil.info(LOGGER, "getCouponList参数   uid={},groupSn={},actSn={},points={}", JsonEntityTransform.Object2Json(inviteCmsRequest));
    	try {
			DataTransferObject dto = new DataTransferObject();
			response.setContentType("text/plain");
			String callBackName = request.getParameter("callback");
			if(Check.NuNObj(inviteCmsRequest)){
	    		dto.setErrCode(DataTransferObject.ERROR);
	    		dto.setMsg("参数为空");
	    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	    		return;
	    	}
	    	if(Check.NuNStr(inviteCmsRequest.getUid())){
	    		dto.setErrCode(DataTransferObject.ERROR);
	    		dto.setMsg("邀请人uid为空");
	    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	    		return;
	    	}
	    	if(Check.NuNStr(inviteCmsRequest.getActSn())){
	    		dto.setErrCode(DataTransferObject.ERROR);
	    		dto.setMsg("现金券对应的活动号为空");
	    		response.getWriter().write(callBackName + "("+dto.toJsonString()+")");
	    		return;
	    	}
	    	inviteCmsRequest.setGroupSn(InviterCreateOrderConst.inviterGroupSn);
			String result = inviteCreateOrderCmsService.pointsExchange(JsonEntityTransform.Object2Json(inviteCmsRequest));
			DataTransferObject resultDto  = JsonEntityTransform.json2DataTransferObject(result);
			response.getWriter().write(callBackName + "("+resultDto.toJsonString()+")");
		} catch (IOException e) {
			LogUtil.info(LOGGER, "getCouponList 获取邀请人积分可兑换列表异常  e={}", e);
		}
    }
}
