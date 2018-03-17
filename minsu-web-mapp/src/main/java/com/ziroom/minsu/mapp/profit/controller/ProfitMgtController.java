package com.ziroom.minsu.mapp.profit.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.order.api.inner.OrderLoadlordService;
import com.ziroom.minsu.services.order.api.inner.ProfitService;
import com.ziroom.minsu.services.order.dto.OrderProfitRequest;
import com.ziroom.minsu.services.order.entity.LandlordOrderItemVo;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * <p>m站收益管理</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingj
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/profitMgt")
@Controller
public class ProfitMgtController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfitMgtController.class);

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${default_head_size}'.trim()}")
	private String default_head_size;
	
	@Resource(name="order.profitService")
	private ProfitService profitService;

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name="order.orderLoadlordService")
	private OrderLoadlordService orderLoadlordService;

    @Resource(name = "basedata.zkSysService")
    private ZkSysService zkSysService;
    /**
	 * 
	 * m站-到我的收益列表
	 *
	 * @author liyingjie
	 * @created 2016年6月26日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/profit")
	public ModelAndView toMyProfit(HttpServletRequest request){

		//路径
		 ModelAndView mv = new ModelAndView("/profit/houseProfit");
		
		 CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		 String uid = "";
		 if(!Check.NuNObj(customerVo)){
			 uid = customerVo.getUid();
		 }
		 
        Calendar cal = Calendar.getInstance();
	  	int  month = cal.get(Calendar.MONTH) + 1;
		mv.addObject("curMonth", month);
		mv.addObject("landlordUid", uid);
		mv.addObject("mouthMap", DateSplitUtil.getCycleMonth());
		return mv;
	}

	
	/**
	 * 
	 * 查询房东房源列表
	 *
	 * @author liujun
	 * @created 2016年6月26日
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/houseRoomList")
	@ResponseBody
	public DataTransferObject houseRoomList(HttpServletRequest request,OrderProfitRequest param) {
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = "";
			if(!Check.NuNObj(customerVo)){
				 uid = customerVo.getUid();
		    }
			int page = Integer.parseInt(request.getParameter("page"));
			OrderProfitRequest paramRequest = new OrderProfitRequest();
			paramRequest.setUid(uid);
			paramRequest.setPage(param.getPage());
			paramRequest.setMonth(param.getMonth());
			paramRequest.setLimit(5);
			paramRequest.setPage(page);

            if (Check.NuNStrStrict(param.getRoomFid())){
                paramRequest.setRoomFid(null);
            }
			String paramJson = JsonEntityTransform.Object2Json(paramRequest);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			String resultJson = profitService.getUserHouseList(paramJson);
			LogUtil.debug(LOGGER,"返回结果："+resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.UNKNOWN_ERROR));
			LogUtil.error(LOGGER,"houseRoomList 返回结果：{}",JsonEntityTransform.Object2Json(param));
			return dto;
		}
	}
	
	
	/**
	 * 
	 * 查询收益
	 *
	 * @author liyingjie
	 * @created 2016年6月26日
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/houseProfit")
	@ResponseBody
	public DataTransferObject houseProfit(HttpServletRequest request,OrderProfitRequest param) {
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = "";
			if(!Check.NuNObj(customerVo)){
				 uid = customerVo.getUid();
		    }
			param.setUid(uid);
			
			if (!Check.NuNStr(param.getRoomFid())){
                if (param.getRoomFid().toLowerCase().equals("null")){
                    param.setRoomFid(null);
                }
            }else {
                param.setRoomFid(null);
            }
			
			String paramJson = JsonEntityTransform.Object2Json(param);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			String resultJson = profitService.getUserAllHouseMonthProfit(paramJson);
			LogUtil.debug(LOGGER,"返回结果："+resultJson);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.UNKNOWN_ERROR));
			LogUtil.error(LOGGER,"houseProfit 返回结果：{}",JsonEntityTransform.Object2Json(param));
			return dto;
		}
	}
	
	
	/**
	 *
	 * m站-到我的收益列表
	 *
	 * @author liyingjie
	 * @created 2016年6月26日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toProfitOrderList")
	public ModelAndView toProfitOrderList(HttpServletRequest request){
        try {
            //提示升级
            String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
			Integer versionCode = (Integer) request.getSession().getAttribute("versionCodeInit");
			if ("1".equals(zkSysValue) && !Check.NuNObj(versionCode) && versionCode.intValue() < 100018) {
				ModelAndView mv = new ModelAndView("common/upgrade");
                return mv;
            }
            //路径
            ModelAndView mv = new ModelAndView("/profit/profitOrderlist");

            String houseFid = request.getParameter("houseBaseFid");
            String roomFid = request.getParameter("houseRoomFid");
            if (!Check.NuNStr(roomFid)) {
                if (roomFid.toLowerCase().equals("null")) {
                    roomFid = "";
                }
            }
            String houseRoomName = "";
            int month = Integer.valueOf(request.getParameter("month"));
            int rentWay = Integer.valueOf(request.getParameter("rentWay"));

            if (rentWay == 0) {
                houseRoomName = request.getParameter("houseName");
            } else {
                houseRoomName = request.getParameter("roomName");
            }
            OrderProfitRequest param = new OrderProfitRequest();
            param.setHouseFid(houseFid);
            if (rentWay == RentWayEnum.ROOM.getCode()) {
                param.setRoomFid(roomFid);
            } else {
                param.setRoomFid("");
            }
            param.setType(1);
            param.setMonth(month);
            mv.addObject("month", month);
            mv.addObject("roomFid", roomFid);
            mv.addObject("houseFid", houseFid);
            mv.addObject("rentWay", rentWay);
            mv.addObject("houseRoomName", houseRoomName);
            mv.addObject("mouthMap", DateSplitUtil.getCycleMonth());
            return mv;
        } catch (Exception e) {
            LogUtil.error(LOGGER, "toProfitOrderList 异常={}", e);
            ModelAndView mv = new ModelAndView("error/error");
            return mv;
        }

	}

	/**
	 *
	 * 查询房东房源列表
	 *
	 * @author liyingjie
	 * @created 2016年6月26日
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/profitOrderDataList")
	@ResponseBody
	public DataTransferObject profitOrderList(HttpServletRequest request,OrderProfitRequest param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = "";
			if(!Check.NuNObj(customerVo)){
				 uid = customerVo.getUid();
			}
			param.setUid(uid);
			param.setLimit(5);
			if (!Check.NuNStr(param.getRoomFid())){
				if (param.getRoomFid().toLowerCase().equals("null")){
					param.setRoomFid(null);
				}
			}else {
				param.setRoomFid(null);
			}
			String paramJson = JsonEntityTransform.Object2Json(param);
			LogUtil.info(LOGGER, "profitOrderList 参数:{}", paramJson);
			String resultJson = orderLoadlordService.queryProfitOrderList(paramJson);
			LogUtil.debug(LOGGER,"profitOrderList 返回结果："+resultJson);

			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<OrderInfoVo> orderInfoList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "orderList", OrderInfoVo.class);
			List<LandlordOrderItemVo> orderList = getListLandlordOrderVo(orderInfoList);
			dto.putValue("orderList", orderList);
			return dto;
		} catch (Exception e) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.UNKNOWN_ERROR));
			LogUtil.error(LOGGER,"profitOrderList 返回结果：{}",JsonEntityTransform.Object2Json(param));
			return dto;
		}
	}


	/**
	 *
	 * 滑动获取 房源每月收益
	 *
	 * @author liyingjie
	 * @created 2016年6月26日
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/houseMonthProfit")
	@ResponseBody
	public DataTransferObject houseMonthProfit(HttpServletRequest request,OrderProfitRequest param) {
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String uid = "";
			if(!Check.NuNObj(customerVo)){
				 uid = customerVo.getUid();
			}
			param.setUid(uid);

			if (!Check.NuNStr(param.getRoomFid())){
				if (param.getRoomFid().toLowerCase().equals("null")){
					param.setRoomFid(null);
				}
			}else {
				param.setRoomFid(null);
			}

			String paramJson = JsonEntityTransform.Object2Json(param);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			String resultJson = profitService.getHouseMonthProfit(paramJson);
			LogUtil.debug(LOGGER,"返回结果："+resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return dto;

		} catch (Exception e) {
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.UNKNOWN_ERROR));
			LogUtil.error(LOGGER,"houseMonthProfit 返回结果：{}",JsonEntityTransform.Object2Json(param));
			return dto;
		}
	}





	/**
	 *
	 * 封装房客端订单列表返回数据
	 *
	 * @author jixd
	 * @created 2016年5月9日 下午3:25:04
	 *
	 * @param landlordOrderList
	 * @return
	 */
	private List<LandlordOrderItemVo> getListLandlordOrderVo(List<OrderInfoVo> landlordOrderList){

		List<LandlordOrderItemVo> listOrderVos = new ArrayList<LandlordOrderItemVo>();
		if(!Check.NuNCollection(landlordOrderList)){
			for (OrderInfoVo orderInfoVo : landlordOrderList) {
				LandlordOrderItemVo itemVo =  new LandlordOrderItemVo();
				itemVo.setOrderSn(orderInfoVo.getOrderSn());
				itemVo.setContactsNum(orderInfoVo.getPeopleNum());
				itemVo.setStartTimeStr(DateUtil.dateFormat(orderInfoVo.getStartTime()));
				itemVo.setEndTimeStr(DateUtil.dateFormat(orderInfoVo.getEndTime()));
				itemVo.setHouseAddr(orderInfoVo.getHouseAddr());
				//根据整租还是合租显示房源名称
				int rentWay = orderInfoVo.getRentWay();
				itemVo.setRentWay(rentWay);
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					itemVo.setHouseName(orderInfoVo.getHouseName());
				}else if(rentWay == RentWayEnum.ROOM.getCode()){
					itemVo.setHouseName(orderInfoVo.getRoomName());
				}else if(rentWay == RentWayEnum.BED.getCode()){
					itemVo.setHouseName(orderInfoVo.getRoomName());
				}else{
					itemVo.setHouseName(orderInfoVo.getHouseName());
				}

				itemVo.setOrderStatus(orderInfoVo.getOrderStatus());
				itemVo.setHousingDay(orderInfoVo.getPeopleNum());
				//显示订单状态名称
				OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderInfoVo.getOrderStatus());
				if(!Check.NuNObj(orderStatusEnum)){
					itemVo.setOrderStatusShowName(orderStatusEnum.getShowName(orderInfoVo));
				}

				itemVo.setUserUid(orderInfoVo.getUserUid());
				itemVo.setLandlordUid(orderInfoVo.getLandlordUid());
				itemVo.setNeedMoney(orderInfoVo.getNeedPay()/100.00);
				itemVo.setUserName(orderInfoVo.getUserName());
				//房源照片
				itemVo.setHousePicUrl(PicUtil.getSpecialPic(picBaseAddrMona, orderInfoVo.getPicUrl(), default_head_size));
				int evaStatus = orderInfoVo.getEvaStatus();
				//如果是待评价 和房客已评价 则显示 去评价
				if(evaStatus == OrderEvaStatusEnum.WAITINT_EVA.getCode() || evaStatus == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()){
					itemVo.setEvaStatus(0);
				}else{
					itemVo.setEvaStatus(1);
				}
				listOrderVos.add(itemVo);
			}
		}
		return listOrderVos;
	}
}
