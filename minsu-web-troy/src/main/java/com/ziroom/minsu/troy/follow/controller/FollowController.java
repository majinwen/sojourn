/**
 * @FileName: OrderController.java
 * @Package com.ziroom.minsu.troy.conf
 *
 * @author liyingjie
 * @created 2016年3月22日 下午10:03:17
 *
 * Copyright 2011-2015
 */
package com.ziroom.minsu.troy.follow.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.OrderFollowLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.api.inner.OrderFollowService;
import com.ziroom.minsu.services.order.dto.OrderFollowRequest;
import com.ziroom.minsu.services.order.entity.OrderDetailVo;
import com.ziroom.minsu.services.order.entity.OrderFollowVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.follow.vo.OrderFollowLogVO;
import com.ziroom.minsu.troy.follow.vo.OrderFollowVO;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.FollowStatusEnum;
import com.ziroom.minsu.valenum.order.OrderPayStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>跟进的相关controller</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @since 1.0
 * @version 1.0
 * @param
 */
@Controller
@RequestMapping("follow/")
public class FollowController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(FollowController.class);

	@Resource(name="order.orderCommonService")
	private OrderCommonService orderCommonService;

	@Resource(name="order.orderFollowService")
	private OrderFollowService orderFollowService;

	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;


	/**
	 * 跳转订单跟进列表
	 * @author lishaochuan
	 * @create 2016/12/23 9:20
	 * @param
	 * @return
	 */
	@RequestMapping("/listFollow")
	public ModelAndView listFollow(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/follow/follow");
		return mv;
	}

	/**
	 * 查询订单跟进列表
	 * @author lishaochuan
	 * @create 2016/12/23 9:20
	 * @param
	 * @return
	 */
	@RequestMapping("/followDataList")
	@ResponseBody
	public String followDataList(@ModelAttribute("paramRequest") OrderFollowRequest paramRequest)throws  Exception {
		String resultJson = orderFollowService.getOrderFollowByPage(JsonEntityTransform.Object2Json(paramRequest));
		List<OrderFollowVo> orderHouseList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderList", OrderFollowVo.class);

		//获取城市信息
		String cityJson = confCityService.getOpenCityMap();
		DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
		Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});
		for (OrderFollowVo orderFollowVo : orderHouseList) {
			orderFollowVo.setCityName(cityMap.get(orderFollowVo.getCityCode()));
			if(!Check.NuNObj(orderFollowVo.getFollowStatus())){
				orderFollowVo.setFollowPeopleStatus(orderFollowVo.getCreateName()+"："+FollowStatusEnum.getNameByCode(orderFollowVo.getFollowStatus()));
			}else {
				orderFollowVo.setFollowPeopleStatus("未跟进");
			}
			if (!Check.NuNObj(orderFollowVo.getRentWay()) && orderFollowVo.getRentWay() == RentWayEnum.ROOM.getCode()){
				orderFollowVo.setHouseName(orderFollowVo.getRoomName());
			}
		}

		Long size = SOAResParseUtil.getLongFromDataByKey(resultJson,"size");
		JSONObject result = new JSONObject();
		Long totalpages = 0L;
		if(!Check.NuNObj(size)){
			if(size % paramRequest.getLimit() == 0){
				totalpages = size/paramRequest.getLimit();
			}else {
				totalpages = size/paramRequest.getLimit() + 1;
			}
		}
		result.put("totalpages", totalpages);
		result.put("currPage", paramRequest.getPage());
		result.put("totalCount", 0);
		result.put("dataList", orderHouseList);
		return result.toString();
	}



	/**
	 * 保存跟进信息
	 * @author afi
	 * @param follow
	 * @return
	 */
	@RequestMapping("saveFollow")
	@ResponseBody
	public DataTransferObject saveFollow(HttpServletRequest request,OrderFollowLogEntity follow){
		if(!Check.NuNObj(follow)){
			LogUtil.info(LOGGER, "follow:{}", JsonEntityTransform.Object2Json(follow));
		}

		DataTransferObject dto = new DataTransferObject();
		CurrentuserVo currentuserVo = UserUtil.getFullCurrentUser();
		if (Check.NuNObj(currentuserVo)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请登录");
			return dto;
		}
		if (Check.NuNObjs(follow.getOrderSn(), follow.getFollowStatus(), follow.getContent() )   ){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		follow.setCreateName(currentuserVo.getUserAccount());
		follow.setCreateFid(currentuserVo.getFid());

		OrderInfoVo orderInfoVo = null;
		DataTransferObject dtoOrder  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(follow.getOrderSn()));
		if(dtoOrder.getCode() == DataTransferObject.SUCCESS){
			orderInfoVo = dtoOrder.parseData("orderInfoVo", new TypeReference<OrderDetailVo>() {});
		}
		if (Check.NuNObj(orderInfoVo)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("当前订单不存在");
			return dto;
		}
		follow.setOrderStatus(orderInfoVo.getOrderStatus());
		DataTransferObject dtoFollow  = JsonEntityTransform.json2DataTransferObject(this.orderFollowService.saveOrderFollow(JsonEntityTransform.Object2Json(follow)));
		dto.setErrCode(dtoFollow.getCode());
		dto.setMsg(dtoFollow.getMsg());
		return dto;
	}


	/**
	 * 获取跟进详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getFollowDetail", method = RequestMethod.GET)
	public ModelAndView getFollowDetail(HttpServletRequest request){

		ModelAndView mv = new ModelAndView("/follow/followDetail");
		String orderSn = request.getParameter("orderSn");
		DataTransferObject dto  = JsonEntityTransform.json2DataTransferObject(this.orderCommonService.getOrderInfoByOrderSn(orderSn));

		OrderInfoVo orderInfoVo = null;
		if(dto.getCode() == DataTransferObject.SUCCESS){
			orderInfoVo = dto.parseData("orderInfoVo", new TypeReference<OrderDetailVo>() {});
		}
		OrderFollowVO vo = new OrderFollowVO();
		if (!Check.NuNObj(orderInfoVo)){
			BeanUtils.copyProperties(orderInfoVo,vo);
			OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfoVo.getOrderStatus());
			if (!Check.NuNObj(orderStatusEnum)){
				vo.setOrderStatusName(orderStatusEnum.getStatusName());
			}
			OrderPayStatusEnum orderPayStatusEnum = OrderPayStatusEnum.getPayStatusByCode(orderInfoVo.getPayStatus());
			if (!Check.NuNObj(orderStatusEnum)){
				vo.setPayStatusName(orderPayStatusEnum.getStatusName());
			}
		}
		mv.addObject("detail",vo);


		DataTransferObject dtoFollow  = JsonEntityTransform.json2DataTransferObject(this.orderFollowService.getOrderFollowListByOrderSn(orderSn));

		List<OrderFollowLogEntity> list = null;
		if(dtoFollow.getCode() == DataTransferObject.SUCCESS){
			list = dtoFollow.parseData("list", new TypeReference<List<OrderFollowLogEntity>>() {});
		}
		int has = 0;
		int i = 0;
		List<OrderFollowLogVO>  showList = new ArrayList<>();
		if (!Check.NuNCollection(list)){
			for (OrderFollowLogEntity followLogEntity : list) {
				i ++;
				if (!Check.NuNObj(followLogEntity.getFollowStatus()) && followLogEntity.getFollowStatus() == FollowStatusEnum.OVER.getCode()){
					has = 1;
				}
				OrderFollowLogVO ele = new OrderFollowLogVO();
				BeanUtils.copyProperties(followLogEntity,ele);
				OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(followLogEntity.getOrderStatus());
				if (!Check.NuNObj(orderStatusEnum)){
					ele.setOrderStatusName(orderStatusEnum.getStatusName());
				}
				ele.setIndex(i);
				showList.add(ele);
			}
		}
		mv.addObject("has",has);
		mv.addObject("list",showList);
		return mv;
	}



}
