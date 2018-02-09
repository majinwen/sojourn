package com.ziroom.minsu.troy.customerserve.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.message.entity.MsgChatVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.finance.entity.RefuseOrderVo;
import com.ziroom.minsu.services.finance.entity.RemindOrderVo;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.entity.HouseCityVo;
import com.ziroom.minsu.services.message.api.inner.MsgBaseService;
import com.ziroom.minsu.services.message.entity.MsgNotReplyVo;
import com.ziroom.minsu.services.order.api.inner.CustomerServeService;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

/**
 * <p>
 * 客服平台
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lishaochuan on 2016年8月2日
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("customerserve")
public class CustomerServeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServeController.class);

	@Resource(name = "order.customerServeService")
	private CustomerServeService customerServeService;
	
	@Resource(name="basedata.confCityService")
    private ConfCityService confCityService;
	
	@Resource(name="message.msgBaseService")
	private MsgBaseService msgBaseService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;

	/**
	 * 跳转申请预定提醒页面
	 * @author lishaochuan
	 * @create 2016年8月2日上午11:26:28
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toRemindOrder")
	public String toRemindOrder(HttpServletRequest request, HttpServletResponse response) {
        //获取申请预定提醒时限
        request.setAttribute("remindMinute", 0);
        try {
	        String remindStrJson = cityTemplateService.getEffectiveSelectEnum(null, String.valueOf(TradeRulesEnum.TradeRulesEnum0017.getValue())); 
	        DataTransferObject remindDto = JsonEntityTransform.json2DataTransferObject(remindStrJson);
	        if(remindDto.getCode() == DataTransferObject.SUCCESS){
				List<EnumVo> remindList = SOAResParseUtil.getListValueFromDataByKey(remindStrJson, "selectEnum", EnumVo.class);
				if (remindList.size() > 0) {
					int remindMinute = ValueUtil.getintValue(remindList.get(0).getKey());
					request.setAttribute("remindMinute", remindMinute);
				}
			}
        } catch (SOAParseException e) {
        	LogUtil.error(LOGGER, "e:{}", e);
        }
        
		return "/customerserve/remindOrder";
	}

	/**
	 * 获取房东未回复的申请预定
	 * @author lishaochuan
	 * @create 2016年8月2日下午3:00:24
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("getNotReplyList")
	@ResponseBody
	public PageResult getNotReplyList(PageRequest pageRequest){
		String resultJson = customerServeService.getRemindOrderList(JsonEntityTransform.Object2Json(pageRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<RemindOrderVo> list = resultDto.parseData("list", new TypeReference<List<RemindOrderVo>>() {});
		
		//获取城市信息
        String cityJson = confCityService.getOpenCityMap();
        DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
        Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});
        for (RemindOrderVo remindOrderVo : list) {
        	remindOrderVo.setCityName(cityMap.get(remindOrderVo.getCityCode()));
        	remindOrderVo.setNotReplyTime(DateSplitUtil.getDiffTimeStr(remindOrderVo.getCreateTime(), new Date()));
		}
		
		PageResult pageResult = new PageResult();
		pageResult.setRows(list);
		pageResult.setTotal(ValueUtil.getlongValue(resultDto.getData().get("total")));
		return pageResult;
	}
	
	
	
	/**
	 * 查询房东拒绝的申请预定（12小时以内）
	 * @author lishaochuan
	 * @create 2016年8月3日下午2:33:26
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("getRefuseOrderList")
	@ResponseBody
	public PageResult getRefuseOrderList(PageRequest pageRequest){
		String resultJson = customerServeService.getRefuseOrderList(JsonEntityTransform.Object2Json(pageRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<RefuseOrderVo> list = resultDto.parseData("list", new TypeReference<List<RefuseOrderVo>>() {});
		
		//获取城市信息
        String cityJson = confCityService.getOpenCityMap();
        DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
        Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});
        for (RefuseOrderVo refuseOrderVo : list) {
        	refuseOrderVo.setCityName(cityMap.get(refuseOrderVo.getCityCode()));
		}
		
		PageResult pageResult = new PageResult();
		pageResult.setRows(list);
		pageResult.setTotal(ValueUtil.getlongValue(resultDto.getData().get("total")));
		return pageResult;
	}
	
	
	/**
	 * troy查询房东未回复的IM记录（1小时以内）
	 * @author lishaochuan
	 * @create 2016年8月4日下午3:33:07
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("getNotReplyIMList")
	@ResponseBody
	public PageResult getNotReplyIMList(PageRequest pageRequest){
		String resultJson = msgBaseService.getNotReplyList(JsonEntityTransform.Object2Json(pageRequest));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		List<MsgNotReplyVo> list = resultDto.parseData("list", new TypeReference<List<MsgNotReplyVo>>() {});
		
		List<String> houseFidList = new ArrayList<String>();
		List<String> roomFidList = new ArrayList<String>();
		Set<String> uidSet = new HashSet<String>();
		for (MsgNotReplyVo msgNotReplyVo : list) {
			//解析消息内容json
			try {
				if(!Check.NuNStr(msgNotReplyVo.getMsgContent())){
					JSONObject jsonObject = JSONObject.parseObject(msgNotReplyVo.getMsgContent());
					String msgContent = jsonObject.getString("msgContent");
					if(!Check.NuNStr(msgContent)){
						msgNotReplyVo.setMsgContent(msgContent);
					}
				}
			} catch (Exception e) {
				LogUtil.info(LOGGER, "error:{}", e);
			}
			if(RentWayEnum.HOUSE.getCode() == msgNotReplyVo.getRentWay()){
				houseFidList.add(msgNotReplyVo.getHouseFid());
			}else if(RentWayEnum.ROOM.getCode() == msgNotReplyVo.getRentWay()){
				roomFidList.add(msgNotReplyVo.getHouseFid());
			}
			uidSet.add(msgNotReplyVo.getTenantUid());
			uidSet.add(msgNotReplyVo.getLandlordUid());
		}
		
		// 获取用户信息
		for (String uid : uidSet) {
			try {
				//TODO:后续修改成一次调用，查询uidList
				String customerJson = customerMsgManagerService.getCutomerVo(uid);
				CustomerVo customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
				if(!Check.NuNObj(customer)){
					for (MsgNotReplyVo msgNotReplyVo : list) {
						if(customer.getUid().equals(msgNotReplyVo.getTenantUid())){
							msgNotReplyVo.setTenantName(customer.getRealName());
							msgNotReplyVo.setTenantTel(customer.getShowMobile());
						}
						if(customer.getUid().equals(msgNotReplyVo.getLandlordUid())){
							msgNotReplyVo.setLandlordName(customer.getRealName());
							msgNotReplyVo.setLandlordTel(customer.getShowMobile());
						}
					}
				}
			} catch (SOAParseException e) {
				LogUtil.info(LOGGER, "error:{}", e);
			}
		}
		
		
		if(!Check.NuNCollection(list)){
			if(!Check.NuNCollection(roomFidList)){
				//获取房间信息
				for (String roomFid : roomFidList) {
					String roomJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
					DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
					HouseRoomMsgEntity houseRoomMsgEntity = roomDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
					
					if(!Check.NuNObj(houseRoomMsgEntity)){
						for (MsgNotReplyVo msgNotReplyVo : list) {
							if(!Check.NuNStr(msgNotReplyVo.getHouseFid())
									&&!Check.NuNStr(roomFid)
									&&msgNotReplyVo.getHouseFid().equals(roomFid)){
								msgNotReplyVo.setHouseFid(houseRoomMsgEntity.getHouseBaseFid());
								msgNotReplyVo.setHouseName(houseRoomMsgEntity.getRoomName());
								break;
							}
						}
						houseFidList.add(houseRoomMsgEntity.getHouseBaseFid());
					}
				
				}
			}

			if(!Check.NuNCollection(houseFidList)){
				// 获取房源信息
				String houseJson = troyHouseMgtService.getHouseCityVoByFids(JsonEntityTransform.Object2Json(houseFidList));
				DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
				List<HouseCityVo> houseCityVoList = houseDto.parseData("houseCityVoList", new TypeReference<List<HouseCityVo>>() {});
				
				if(!Check.NuNCollection(houseCityVoList)){
					for (MsgNotReplyVo msgNotReplyVo : list) {
						for (HouseCityVo houseCityVo : houseCityVoList) {
							if(!Check.NuNStr(msgNotReplyVo.getHouseFid())
									&&!Check.NuNStr(houseCityVo.getFid())
									&&msgNotReplyVo.getHouseFid().equals(houseCityVo.getFid())){
								if(Check.NuNStr(msgNotReplyVo.getHouseName())){
									msgNotReplyVo.setHouseName(houseCityVo.getHouseName());
								}
								msgNotReplyVo.setNationCode(houseCityVo.getNationCode());
								msgNotReplyVo.setCityCode(houseCityVo.getCityCode());
							}
						}
					}
				}
				
			}


			
			//获取城市信息
			String cityJson = confCityService.getOpenCityMap();
			DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
			Map<String, String> cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});
			for (MsgNotReplyVo notReplyVo : list) {
				notReplyVo.setCityName(cityMap.get(notReplyVo.getCityCode()));
			}
			
			//设置房东未回复时间
			for (MsgNotReplyVo msgNotReplyVo : list) {
				msgNotReplyVo.setNotReplyTime(DateSplitUtil.getDiffTimeStr(msgNotReplyVo.getCreateTime(), new Date()));
			}
		}
        
		
		PageResult pageResult = new PageResult();
		pageResult.setRows(list);
		pageResult.setTotal(ValueUtil.getlongValue(resultDto.getData().get("total")));
		return pageResult;
	}

	/**
	 * 展示双方列表内容
	 * @author jixd
	 * @created 2017年04月12日 11:06:31
	 * @param
	 * @return
	 */
	@RequestMapping("/listBothChatInfo")
	@ResponseBody
	public DataTransferObject listBothChatInfo(String houseMsgFid){
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStr(houseMsgFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("fid为空");
			return dto;
		}
		try{
			String resultJson = msgBaseService.listChatBoth(houseMsgFid);
			LogUtil.info(LOGGER,"listBothChatInfo 查询聊天列表返回结果={}",resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<MsgChatVo> list = resultDto.parseData("list", new TypeReference<List<MsgChatVo>>() {});
			CustomerVo tenVo = null;
			CustomerVo lanVo = null;
			for (MsgChatVo vo : list){
				String tenUid = vo.getTenantUid();
				String landUid = vo.getLandlordUid();
				Integer senType = vo.getMsgSenderType();
				if (Check.NuNObj(tenVo) && senType == UserTypeEnum.TENANT_HUANXIN.getUserType()){
					String customerJson = customerMsgManagerService.getCutomerVo(tenUid);
					tenVo = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
					if (!Check.NuNObj(tenVo) && Check.NuNStr(tenVo.getNickName())){
						tenVo.setNickName("房客");
					}
				}
				if (Check.NuNObj(lanVo) && senType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
					String customerJson = customerMsgManagerService.getCutomerVo(landUid);
					lanVo = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
					if (!Check.NuNObj(lanVo) && Check.NuNStr(lanVo.getNickName())){
						tenVo.setNickName("房东");
					}
				}
				if (senType == UserTypeEnum.TENANT_HUANXIN.getUserType()){
					vo.setHeadPic(tenVo.getUserPicUrl());
					vo.setNickName(tenVo.getNickName());
				}
				if (senType == UserTypeEnum.LANDLORD_HUAXIN.getUserType()){
					vo.setHeadPic(lanVo.getUserPicUrl());
					vo.setNickName(lanVo.getNickName());
				}
				String content = vo.getContent();
				if (!Check.NuNStr(content)){
					JSONObject object = JSONObject.parseObject(content);
					String msgContent = object.getString("msgContent");
					vo.setContent(msgContent);
				}
				dto.putValue("list",list);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER,"【listBothChatInfo】查询im聊天记录异常e={}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
}
