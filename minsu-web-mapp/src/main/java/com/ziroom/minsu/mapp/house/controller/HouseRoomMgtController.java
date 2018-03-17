package com.ziroom.minsu.mapp.house.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBedMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.mapp.common.util.UserUtil;
import com.ziroom.minsu.mapp.house.vo.BedTypeSizeVo;
import com.ziroom.minsu.mapp.house.vo.HouseBedMsgVo;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.HouseBusinessService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.dto.HouseRoomMsgDto;
import com.ziroom.minsu.services.house.dto.RoomBedZDto;
import com.ziroom.minsu.services.house.dto.SpecialPriceDto;
import com.ziroom.minsu.services.house.entity.RoomMsgVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

/**
 * 
 * <p>m站房间管理</p>
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
@RequestMapping("/roomMgt")
@Controller
public class HouseRoomMgtController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseRoomMgtController.class);

	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Autowired
	private RedisOperations redisOperations;
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="house.houseBusinessService")
	private HouseBusinessService houseBusinessService;

	/** 存放灵活定价的配置*/
	private static List<String> gapFlexlist = new ArrayList<String>();
	static {
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020001.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020002.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020003.getValue());
	}
	/**
	 * 虚拟房间的fid前缀
	 */
	private static  final String tmp_flag_pre = "new_";
	
	/**
	 * 虚拟房间的fid前缀
	 */
	private static  final String min_room_price = "60";

	/**
	 * 
	 * m站-户型信息页面
	 *
	 * @author liujun
	 * @created 2016年8月23日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/houseApartment")
	public String listHouseApartmentDetail(HttpServletRequest request, ModelMap map){
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseRoomFid = request.getParameter("houseRoomFid");
		String flag = request.getParameter("flag");

		//返回结果
		HouseBaseMsgEntity houseBaseMsgEntity = null;
		List<HouseRoomMsgEntity> roomList = null;
		if(!Check.NuNStr(houseBaseFid)){
			//基本户型信息
			String houseJson = houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if(houseDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "houseIssueService.searchHouseBaseMsgByFid接口错误,houseBaseFid:{}", houseBaseFid);
			} else {
				try {
					houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseMsgEntity.class);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "houseIssueService.searchHouseBaseMsgByFid接口错误,houseBaseFid:{}", houseBaseFid);
				}
			}

			//基本户型信息
			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
			DataTransferObject roomListDto = JsonEntityTransform.json2DataTransferObject(roomListJson);
			if(roomListDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER, "houseIssueService.searchRoomListByHouseBaseFid接口错误,houseBaseFid:{}", houseBaseFid);
			} else {
				try {
					roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list", HouseRoomMsgEntity.class);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "houseIssueService.searchRoomListByHouseBaseFid接口错误,houseBaseFid:{}", houseBaseFid);
				}
			}
			LogUtil.debug(LOGGER, "listHouseApartment roomResultJson:{}", JsonEntityTransform.Object2Json(roomList));
		}

		if(Check.NuNObj(houseBaseMsgEntity) || Check.NuNObj(houseBaseMsgEntity.getRentWay())){
			return "error/error";
		}

		map.put("houseBaseMsg", houseBaseMsgEntity);
		map.put("houseRoomFid", houseRoomFid);
		map.put("flag", flag);
		if(houseBaseMsgEntity.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()){
			List<Object> list = new ArrayList<>();
			for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
				Map<String, Object> room = houseRoomMsgEntity.toMap();
				boolean isComplete = checkBedNumByRoomFid(houseRoomMsgEntity.getFid()) != 0;
				room.put("isComplete", isComplete);
				list.add(room);
			}
			map.put("roomList", list);
			return "/house/houseIssue/apartInfoZ";
		} else if(houseBaseMsgEntity.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
			map.put("roomList", roomList);
			return "/house/houseIssue/apartInfoF";
		} else {
			return "error/error";
		}

	}

	/**
	 * 功能，更新户型（room_num）信息，以及删除多余的房间信息（房间删除，相关床位也要删除）  这些操作都在一个事物中执行
	 *     整租必须保存至少一个房间
	 *   说明：
	 * 1.初始化，房源不存在，直接提示不让添加房间
	 * 2.房源存在，带出户型，户型情况：A. 0室   B. 大于 0 室
	 * 3. A.0室处理   房间默认没有  当选择户型，保存入库，  并带出相应的房间展示位置（页面dom操作）
	 * 4. B.大于0室，选择户型，保存入库
	 *       效果： 选择X室， 下面展示相应X个房间（当前已存在且未删除房间数量Y<=X， 若Y<X,dom展示Z = X-Y个房间出来）
	 *     设 数据库当前已保存且未删除房间数量为 M(X>=M>=0)
	 *     4.1 选择X室，数据库查询M
	 *         若M<=X,展示M个房间，展示X-M个dom（房间数量不够，用dom元素展示，不保存入库）的房间数量
	 *         若M>X, 按照房间创建时间排序，删除M-X个房间，查询返回聊表FID,取前M-X个fid逻辑删除
	 * @author yd
	 * @created 2016年8月19号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/deleteHouseRooms")
	@ResponseBody
	public DataTransferObject  deleteHouseRooms(HttpServletRequest request,@ModelAttribute("houseBaseMsg")HouseBaseMsgEntity houseBaseMsg){


		DataTransferObject dto = null;
		if(Check.NuNObj(houseBaseMsg)){
			dto  = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto;
		}

		if(Check.NuNObj(houseBaseMsg.getFid())){
			dto  = new DataTransferObject();
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源信息错误");
			return dto;
		}

		dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.updateHouseAndDelRoom(JsonEntityTransform.Object2Json(houseBaseMsg)));

		return dto;
	}




	/**
	 * 获取当前的房间列表
	 * @author afi
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/roomList")
	public ModelAndView listRoom(HttpServletRequest request){
		//路径
		ModelAndView mv = new ModelAndView("/house/apartment/roomList");

		String houseBaseFid = request.getParameter("houseBaseFid");
		LogUtil.info(LOGGER, "listHouseApartment houseBaseFid:{}", houseBaseFid);
		//返回结果
		HouseBaseMsgEntity houseBaseMsgEntity = null;
		List<HouseRoomMsgEntity> roomList = null;
		if(!Check.NuNStr(houseBaseFid)){
			//基本户型信息
			String houseBaseResultJson =	houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseBaseResultJson);
			houseBaseMsgEntity = dto.parseData("obj", new TypeReference<HouseBaseMsgEntity>(){});
			LogUtil.debug(LOGGER, "listHouseApartment houseBaseResultJson:{}", JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			//基本户型信息
			String roomResultJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
			dto = JsonEntityTransform.json2DataTransferObject(roomResultJson);
			roomList = dto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>(){});
			LogUtil.debug(LOGGER, "listHouseApartment roomResultJson:{}", JsonEntityTransform.Object2Json(roomList));
		}else{
			houseBaseMsgEntity = new HouseBaseMsgEntity();
			roomList = new ArrayList<HouseRoomMsgEntity>(1);
		}
		mv.addObject("houseBaseMsg", houseBaseMsgEntity);
		mv.addObject("roomList", roomList);
		return mv;
	}

	/**
	 * m站-更新户型基本信息
	 * 
	 * @author liyingjie
	 * @created 2016年5月25日
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveHouseBaseInfo")
	@ResponseBody
	@Deprecated
	public DataTransferObject updateHouseBaseInfo(HttpServletRequest request,@ModelAttribute("houseBaseMsg")HouseBaseMsgEntity houseBaseMsg){
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNObj(houseBaseMsg)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		//几室几厅几卫几阳台
		String fid = houseBaseMsg.getFid();
		if(Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		String resultJson = houseIssueService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));
		dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		dto.putValue("houseBaseMsg", houseBaseMsg);
		return dto;
	}
	/**
	 * m站-到房间详细信息
	 * @author liyingjie
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	/*@RequestMapping("${LOGIN_UNAUTH}/toRoomDetail")
	public ModelAndView toRoomDetail(HttpServletRequest request) throws SOAParseException{
		//路径
		ModelAndView mv = new ModelAndView("/house/apartment/roomDetail");
		String roomId = request.getParameter("roomFid");
		String houseBaseFid = request.getParameter("houseBaseFid");

		String houseBaseResultJson =	houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseBaseResultJson);
		HouseBaseMsgEntity houseBaseMsgEntity = dto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {
		});

		if(!Check.NuNStr(roomId)){
			if(roomId.startsWith(tmp_flag_pre)){
				//当前的是虚拟房间 直接从缓存获取
				String cacheJson = null;
				try {
					cacheJson = redisOperations.get(roomId);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误e={}",e);
				}
				if(!StringUtils.isBlank(cacheJson)){
					HouseRoomMsgEntity houseRoomMsg = JsonEntityTransform.json2Object(cacheJson,HouseRoomMsgEntity.class);
					//显示为分
					int money = houseRoomMsg.getRoomPrice();
					money = money/100;
					houseRoomMsg.setRoomPrice(money);

					mv.addObject("houseRoomMsg", houseRoomMsg);
				}else{
					HouseRoomMsgEntity room = new HouseRoomMsgEntity();
					room.setFid(tmp_flag_pre + UUIDGenerator.hexUUID());
					mv.addObject("houseRoomMsg", room);
				}
				mv.addObject("isSetBed", 0);

			}else {
				//当前的不是虚拟房间的 直接从数据库获取
				String resultJson = houseIssueService.searchHouseRoomMsgByFid(roomId);
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				HouseRoomMsgEntity houseRoomMsg = dto.parseData("obj", new TypeReference<HouseRoomMsgEntity>(){});
				if(!Check.NuNObj(houseRoomMsg.getRoomPrice()) && houseRoomMsg.getRoomPrice() == 0){
					houseRoomMsg.setRoomPrice(null);
				}else{
					//显示为分
					int money = houseRoomMsg.getRoomPrice();
					money = money/100;
					houseRoomMsg.setRoomPrice(money);
				}
				if(!Check.NuNObj(houseRoomMsg.getRoomArea()) && houseRoomMsg.getRoomArea() == 0){
					houseRoomMsg.setRoomArea(null);
				}
				mv.addObject("houseRoomMsg", houseRoomMsg);
				//                if(Check.NuNObj(houseRoomMsg)){
				//                	 mv.addObject("isSetBed", 0);
				//                }else{
				//                	 mv.addObject("isSetBed", 1);
				//                }
			}
		}else{
			HouseRoomMsgEntity room = new HouseRoomMsgEntity();
			room.setFid(tmp_flag_pre + UUIDGenerator.hexUUID());
			mv.addObject("houseRoomMsg", room);
		}
		mv.addObject("isSetBed", checkBedNumByRoomFid(roomId));
		mv.addObject("houseBaseFid", houseBaseFid);
		mv.addObject("rentWay", houseBaseMsgEntity.getRentWay());
		mv.addObject("flag", request.getParameter("flag"));
		mv.addObject("houseRoomFid",request.getParameter("houseRoomFid"));
		//房源最低价格限制
		String priceLimitJson=cityTemplateService.getTextValue(null, ProductRulesEnum.ProductRulesEnum007.getValue());
		String priceLimit=SOAResParseUtil.getValueFromDataByKey(priceLimitJson, "textValue", String.class);
		mv.addObject("priceLimit", priceLimit);
		return mv;
	}*/
	@RequestMapping("${LOGIN_UNAUTH}/toRoomDetail")
	public String toRoomDetail(HttpServletRequest request, ModelMap map) throws SOAParseException, UnsupportedEncodingException {
		//路径
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String flag = request.getParameter("flag");
		String roomFid = request.getParameter("roomFid");
		/** 添加分租房间时，用于第一次添加价格保存在redia的情境中(start) */
		String houseRoomFid = request.getParameter("houseRoomFid");
		request.setAttribute("roomFid",houseRoomFid);
		String priceFlag = "";
		priceFlag = request.getParameter("priceFlag")==null?"0":"1";
		request.setAttribute("priceFlag",priceFlag);
		String roomPrice = request.getParameter("orPrice");
		if(priceFlag.equals("1")){
			request.setAttribute("roomPrice",roomPrice);
			HouseRoomMsgEntity houseRoomMsgRedis = new HouseRoomMsgEntity();
			String roomName = request.getParameter("roomName");
			roomName = java.net.URLDecoder.decode(roomName,"UTF-8");
			houseRoomMsgRedis.setRoomName(roomName);
			if(!Check.NuNObj(request.getParameter("roomArea"))){
				houseRoomMsgRedis.setRoomArea(Double.parseDouble(request.getParameter("roomArea")));
			}
			if(!Check.NuNObj(request.getParameter("limit"))){
				houseRoomMsgRedis.setCheckInLimit(Integer.parseInt(request.getParameter("limit")));
			}
			if(!Check.NuNObj(request.getParameter("cfee"))){
				houseRoomMsgRedis.setRoomCleaningFees(Integer.parseInt(request.getParameter("cfee")));
			}
			if(!Check.NuNObj(request.getParameter("isToilet"))){
				houseRoomMsgRedis.setIsToilet(Integer.parseInt(request.getParameter("isToilet")));
			}
			request.setAttribute("houseRoomMsg",houseRoomMsgRedis);
		}
		/** 添加分租房间时，用于第一次添加价格保存在redia的情境中(end) */
		//1 床类型
		String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
		DataTransferObject bedTypeDto = JsonEntityTransform.json2DataTransferObject(bedTypeJson);
		if(bedTypeDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum005.getValue());
			return "error/error";
		}

		List<EnumVo> bedTypeList = null;
		try {
			bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			map.put("bedTypeList", bedTypeList);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={},e:{}",
					ProductRulesEnum.ProductRulesEnum005.getValue(), e);
			return "error/error";
		}

		//2 床规格
		String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
		DataTransferObject bedSizeDto = JsonEntityTransform.json2DataTransferObject(bedSizeJson);
		if(bedSizeDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum006.getValue());
			return "error/error";
		}
		List<EnumVo> bedSizeList = null;
		try {
			bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
			map.put("bedSizeList", bedSizeList);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={},e:{}",
					ProductRulesEnum.ProductRulesEnum006.getValue(), e);
			return "error/error";
		}

		Map<String,String> typeMap = new HashMap<>();
		if(!Check.NuNCollection(bedTypeList)){
			for(EnumVo bedType: bedTypeList){
				typeMap.put(ValueUtil.getStrValue(bedType.getKey()),bedType.getText());
			}
		}

		Map<String,String> sizeMap = new HashMap<>();
		if(!Check.NuNCollection(bedSizeList)){
			for(EnumVo bedSize: bedSizeList){
				sizeMap.put(ValueUtil.getStrValue(bedSize.getKey()), bedSize.getText());
			}
		}

		//3 房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		DataTransferObject priceLowDto = JsonEntityTransform.json2DataTransferObject(priceLowJson);
		if(priceLowDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}", ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			return "error/error";
		}

		try {
			String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
			map.put("priceLow", priceLow);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}", ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			return "error/error";
		}
		
		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		DataTransferObject priceHighDto = JsonEntityTransform.json2DataTransferObject(priceHighJson);
		if(priceHighDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}", ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			return "error/error";
		}
		
		try {
			String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
			map.put("priceHigh", priceHigh);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}", ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			return "error/error";
		}
		
		//4 入住人数限制
		String limitJson= cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
		DataTransferObject limitDto = JsonEntityTransform.json2DataTransferObject(limitJson);
		if(limitDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum009.getValue());
			return "error/error";
		}
		try {
			List<EnumVo> limitList = SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);
			map.put("limitList", limitList);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={},e:{}",
					ProductRulesEnum.ProductRulesEnum009.getValue(), e);
			return "error/error";
		}
		
		//5 清洁费
		String cleaningFeesJson = cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
		DataTransferObject cleaningFeesDto = JsonEntityTransform.json2DataTransferObject(cleaningFeesJson);
		if(cleaningFeesDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}",
					TradeRulesEnum.TradeRulesEnum0019.getValue());
			return "error/error";
		}
		double cleanPer = 0;
		try {
			String cleaningFees = SOAResParseUtil.getValueFromDataByKey(cleaningFeesJson, "textValue", String.class);
			if (!Check.NuNStr(cleaningFees) ) {
				cleanPer = Double.parseDouble(cleaningFees);//清洁费比例 
			} 
			map.put("cleanPer", cleanPer);
		} catch (Exception e) {
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={},e:{}",
					TradeRulesEnum.TradeRulesEnum0019.getValue(), e);
			return "error/error";
		}
		//第一发布独立房间，未存入数据库，且数据在缓存中
		if(Check.NuNStrStrict(roomFid) && !Check.NuNStrStrict(houseRoomFid)){
			String key = RedisKeyConst.getConfigKey("room"+UserUtil.getCurrentUserFid()+houseRoomFid);
			try{
				String priceJson = redisOperations.get(key);
				if(!Check.NuNObj(priceJson)){
					SpecialPriceDto sp = SOAResParseUtil.getValueFromDataByKey(priceJson, "specialPriceDto", SpecialPriceDto.class);
					StringBuilder sb = new StringBuilder("已设置固定价格");
					if(sp.getGapAndFlexiblePrice()==1){
						sb.append("、空置间夜自动定价");
					}
					//周末价格
					if(sp.isInsert()){
						sb.append("、周末价格");
					}
					if(sp.getFullDayRate()==1){
						sb.append("、折扣设置");
					}
					request.setAttribute("priceSetting", sb.toString());
				}
			}catch (Exception e){
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
		}
		if(!Check.NuNStr(roomFid)){
			String roomJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
			DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
			if(roomDto.getCode() == DataTransferObject.ERROR){
				LOGGER.error("houseIssueService.searchHouseRoomMsgByFid接口错误,roomId={},返回结果:{}",
						roomFid, roomJson);
				return "error/error";
			}
			try {
				HouseRoomMsgEntity houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				map.put("houseRoomMsg", houseRoomMsg);
				/** 关于价格设置的开始*/
				if(!Check.NuNObj(houseRoomMsg)){
					roomPrice = String.valueOf(houseRoomMsg.getRoomPrice());
				}
				
				if (!Check.NuNObj(roomPrice)) {
					StringBuilder sb = new StringBuilder("已设置固定价格");
					// 分别给页面返回灵活定价，折扣优惠的集合
					HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
					confMsgEntity.setHouseBaseFid(houseBaseFid);
					confMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
					confMsgEntity.setRoomFid(roomFid);
					confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020001.getParentValue());
					String gapFlexJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
					List<HouseConfMsgEntity> gapFlexList = SOAResParseUtil.getListValueFromDataByKey(gapFlexJson, "list",
							HouseConfMsgEntity.class);
					if (!Check.NuNCollection(gapFlexList)) {
						sb.append("、空置间夜自动定价");
					}

					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("houseFid", houseBaseFid);
					paramMap.put("roomFid", roomFid);
					String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
					List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil.getListValueFromDataByKey(weekendPriceJson, "list",
							HousePriceWeekConfEntity.class);
					if (!Check.NuNCollection(weekendPriceList)) {
						for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
							if (!Check.NuNObj(housePriceWeekConf.getIsDel())
									&& housePriceWeekConf.getIsDel().intValue() == YesOrNoEnum.NO.getCode()
									&& !Check.NuNObj(housePriceWeekConf.getIsValid())
									&& housePriceWeekConf.getIsValid().intValue() == YesOrNoEnum.YES.getCode()) {
								sb.append("、周末价格");
								break;
							}
						}
					}

					confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
					String sevenJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
					List<HouseConfMsgEntity> sevenList = SOAResParseUtil.getListValueFromDataByKey(sevenJson, "list", HouseConfMsgEntity.class);

					confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
					String thirtyJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
					List<HouseConfMsgEntity> thirtyList = SOAResParseUtil.getListValueFromDataByKey(thirtyJson, "list", HouseConfMsgEntity.class);
					if (!Check.NuNCollection(sevenList) || !Check.NuNCollection(thirtyList)) {
						sb.append("、折扣设置");
					}
					request.setAttribute("priceSetting", sb.toString());
				}


				/** 关于价格设置的结束*/
			} catch (Exception e) {
				LOGGER.error("houseIssueService.searchHouseRoomMsgByFid接口错误,roomId={},返回结果:{}",
						roomFid, roomJson);
				return "error/error";
			}

			String bedListJson = houseIssueService.searchBedListByRoomFid(roomFid);
			DataTransferObject bedListDto = JsonEntityTransform.json2DataTransferObject(bedListJson);
			if(bedListDto.getCode() == DataTransferObject.ERROR){
				LOGGER.error("houseIssueService.searchBedListByRoomFid接口错误,roomId={},返回结果:{}",
						roomFid, bedListJson);
				return "error/error";
			}
			try {
				List<HouseBedMsgVo> bedList = SOAResParseUtil.getListValueFromDataByKey(bedListJson, "list", HouseBedMsgVo.class);
				List<HouseBedMsgVo> voList = new ArrayList<HouseBedMsgVo>();
				for (HouseBedMsgVo vo : bedList) {
					vo.setBedTypeDis(typeMap.get(ValueUtil.getStrValue(vo.getBedType())));
					vo.setBedSizeDis(sizeMap.get(ValueUtil.getStrValue(vo.getBedSize())));
					voList.add(vo);
				}
				map.put("bedList", voList);
			} catch (Exception e) {
				LOGGER.error("houseIssueService.searchBedListByRoomFid接口错误,roomId={},返回结果:{}",
						roomFid, roomJson);
				return "error/error";
			}
		}
		String rulerData = bedTypeAndSiz2Json(bedTypeList, bedSizeList);
		map.put("rulerData", rulerData);
		map.put("houseBaseFid", houseBaseFid);
		map.put("houseRoomFid", roomFid);
		map.put("rentWay", rentWay);
		map.put("flag", flag);
		return "house/houseIssue/roomDetail";
	}


	/**
	 * 通过房间获取当前的床铺的数量
	 * @author afi
	 * @param roomFid
	 * @return
	 */
	private int checkBedNumByRoomFid(String roomFid){
		int rst = 0;
		if(Check.NuNStr(roomFid)){
			return rst;
		}
		String json = houseIssueService.countBedByRoomFid(roomFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(json);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			rst = ValueUtil.getintValue(dto.getData().get("count"));
			if(rst > 0){
				rst = 1;
			}
		}
		return rst;
	}



	/**
	 * 保存临时房间信息到缓存
	 * @author afi
	 * @param request
	 * @param houseRoomMsg
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveTmpInf")
	@ResponseBody
	@Deprecated
	public DataTransferObject saveTmpInf(HttpServletRequest request,HouseRoomMsgDto houseRoomMsg){
		DataTransferObject dto = new DataTransferObject();

		CustomerVo user = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		if(Check.NuNObj(user)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("无法获取当前用户信息");
			return dto;
		}

		if(Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getFid()) || Check.NuNStr(houseRoomMsg.getHouseBaseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		if(!Check.NuNStr(houseRoomMsg.getRoomName())&&houseRoomMsg.getRoomName().length()>=30){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间名称字数不能超过30字");
			return dto;
		}
		String fid = houseRoomMsg.getFid();
		/*if(!fid.startsWith(tmp_flag_pre)){
           //不需要替换缓存，直接返回成功
            return dto;
        }*/

		if(fid.startsWith(tmp_flag_pre)){
			//不需要替换缓存，直接返回成功
			fid = fid.substring(tmp_flag_pre.length());
			houseRoomMsg.setFid(fid);

		}
		if(!Check.NuNObj(houseRoomMsg.getRoomPrice())){
			houseRoomMsg.setRoomPrice(houseRoomMsg.getRoomPrice() * 100);
		}
		String key = RedisKeyConst.getConfigKey(fid);
		try {
			String cache= null;
			try {
				cache=redisOperations.get(key);
				if(!StringUtils.isBlank(cache)){
					LogUtil.info(LOGGER,"当前缓存存在，直接删除：fid：{}",fid);
					redisOperations.del(key);
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "redis错误e={}",e);
			}

			//保存到数据库
			String mergeJson = houseIssueService.mergeCheckHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoomMsg),user.getUid());
			dto = JsonEntityTransform.json2DataTransferObject(mergeJson);
			if(dto.getCode() == DataTransferObject.SUCCESS){
				dto.putValue("houseRoomMsg", houseRoomMsg);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER, "保存 房间临时信息异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}


	/**
	 * 保存房间信息-直接保存到数据库
	 * @author afi
	 * @param request
	 * @param houseRoomMsg
	 * @return
	 */
	/*@RequestMapping("${LOGIN_UNAUTH}/saveRoomInfo")
	@ResponseBody
	public DataTransferObject saveRoomInfo(HttpServletRequest request,HouseRoomMsgDto houseRoomMsg){
		CustomerVo user = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		DataTransferObject dto = null;
		if(Check.NuNObj(user)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("无法获取当前用户信息");
			return dto;
		}
		if(Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getFid()) || Check.NuNStr(houseRoomMsg.getHouseBaseFid())){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		if(!Check.NuNStr(houseRoomMsg.getRoomName())&&houseRoomMsg.getRoomName().length()>=30){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间名称字数不能超过30字");
			return dto;
		}
		//当前的缓存
		String cacheKey = null;
		String fid = houseRoomMsg.getFid();
		String fidReal = fid;
		if(fid.startsWith(tmp_flag_pre)){
			cacheKey = RedisKeyConst.getConfigKey(fid);
			fidReal = fid.substring(tmp_flag_pre.length());
		}
		houseRoomMsg.setFid(fidReal);
		try {
			if(!Check.NuNObj(houseRoomMsg.getRoomPrice())){
				houseRoomMsg.setRoomPrice(houseRoomMsg.getRoomPrice() * 100);
			}
			String mergeJson = houseIssueService.mergeCheckHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoomMsg),user.getUid());
			dto = JsonEntityTransform.json2DataTransferObject(mergeJson);
			if(dto.getCode() == DataTransferObject.SUCCESS && !Check.NuNStr(cacheKey)){
				try {
					//保存成功，直接删除当前的缓存信息
					redisOperations.del(cacheKey);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误e={}",e);
				}
			}
			dto.putValue("roomFid", fidReal);
		}catch(Exception e){
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "保存房间信息到数据库异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}*/
	
	/**
	 * 保存房间信息-直接保存到数据库
	 * @author afi
	 * @param request
	 * @param houseRoomMsg
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveRoomInfo")
	@ResponseBody
	public DataTransferObject saveRoomInfo(HttpServletRequest request, RoomMsgVo roomMsgVo){
		String uid = UserUtil.getCurrentUserFid();
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(uid)){
			LogUtil.error(LOGGER, "saveRoomInfo：保存分租房间信息，用户信息不存在");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("登录用户信息不存在");
			return dto;
		}
		if(Check.NuNObj(roomMsgVo)){
			LogUtil.error(LOGGER, "saveRoomInfo：保存分租房间信息，参数异常");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数不能为空");
			return dto;
		}
		LogUtil.info(LOGGER, "saveRoomInfo：保存分租房间信息,当前参数roomMsgVo={}", roomMsgVo.toJsonStr());
		if(Check.NuNStr(roomMsgVo.getHouseBaseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间逻辑id不能为空");
			return dto;
		}
		if(Check.NuNCollection(roomMsgVo.getBedList())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("床位不能为空");
			return dto;
		}
		if(Check.NuNStr(roomMsgVo.getRoomName())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间名称不能为空");
			return dto;
		}
		if(roomMsgVo.getRoomName().length() > 30){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间名称不能超过30字");
			return dto;
		}
		//房间价格限定
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");
		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

		if(Check.NuNObj(roomMsgVo.getRoomCleaningFees())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("清洁费不能为空");
			return dto;
		}
		
		/*String cleanPerJson = cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
		Double cleanPer = SOAResParseUtil.getDoubleFromDataByKey(cleanPerJson, "textValue");
		if(Check.NuNObj(cleanPer)){
			cleanPer = 0.0d;
		}
		if(roomMsgVo.getRoomCleaningFees() > roomMsgVo.getRoomPrice()*cleanPer){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("清洁费不能超过最高比例");
			return dto;
		}*/

		/** 需要判断是否第一次添加房间，需要去缓存中拿到roomFid和价格的设置*/
		SpecialPriceDto sp = null;
		String key = RedisKeyConst.getConfigKey("room"+UserUtil.getCurrentUserFid()+roomMsgVo.getHouseRoomFid());
		try{
			String priceJson = redisOperations.get(key);
			if(Check.NuNObj(priceJson)){
				roomMsgVo.setHouseRoomFid(UUIDGenerator.hexUUID());
			}else{
				sp = SOAResParseUtil.getValueFromDataByKey(priceJson, "specialPriceDto", SpecialPriceDto.class);
				if (Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("传入参数有误");
					return dto;
				}
				//校验周末价格START
				if (sp.isInsert()) {
					if (Check.NuNCollection(sp.getSetTime())) {
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("周末时间不能为空");
						return dto;
					}

					if (Check.NuNObj(sp.getSpecialPrice())) {
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("周末价格不能为空");
						return dto;
					}

					if (!Check.NuNObjs(priceLow, priceHigh)
							&& (sp.getSpecialPrice().intValue() < priceLow.intValue()
									|| sp.getSpecialPrice().intValue() > priceHigh.intValue())) {
						dto.setErrCode(DataTransferObject.ERROR);
						dto.setMsg("周末价格超出限制");
						return dto;
					}
				//校验周末价格END
				}
				//普通价格判断
				Integer price = 0;
				if(sp.getRentWay()==RentWayEnum.ROOM.getCode() && !Check.NuNObj(sp.getRoomPrice())){
					price = sp.getRoomPrice();
				}
				if(sp.getRentWay()==RentWayEnum.HOUSE.getCode() && !Check.NuNObj(sp.getLeasePrice())){
					price = sp.getLeasePrice();
				}
				if (!Check.NuNObjs(priceLow, priceHigh)
						&& (price.intValue() < priceLow.intValue() 
								|| price.intValue() > priceHigh.intValue())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("普通价格超出限制");
					return dto;
				}
				
				//判断是否开启折扣设置 未开启不判断
				if(sp.getFullDayRate()==1){
					if(!Check.NuNObjs(sp.getSevenDiscountRate(),sp.getThirtyDiscountRate())){
						if(sp.getSevenDiscountRate().intValue() <= 0 || sp.getSevenDiscountRate().intValue() >= 100){
							dto.setErrCode(DataTransferObject.ERROR);
							dto.setMsg("输入的满7天折扣率非法");
							return dto;
						}
						if(sp.getThirtyDiscountRate().intValue() <= 0 || sp.getThirtyDiscountRate().intValue() >= 100){
							dto.setErrCode(DataTransferObject.ERROR);
							dto.setMsg("输入的满30天折扣率非法");
							return dto;
						}
					}
				}
				List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
				//关于灵活定价和折扣率的处理
				confList = confListAddM(sp,dto,confList);
				/** 插入配置表操作(插入和修改分离)*/
				houseIssueService.saveHouseConfList(JsonEntityTransform.Object2Json(confList));
				//设置普通价格(普通价格需要上限和下限的判断)
				roomMsgVo.setRoomPrice(sp.getRoomPrice()*100);
			}
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER, "异常错误,e:{}", e);
		}

		try {
			roomMsgVo.setCreateUid(uid);
			houseIssueService.mergeRoomAndBedList(JsonEntityTransform.Object2Json(roomMsgVo));
			
			//保存周末价格START
			if (!Check.NuNObj(sp) && sp.isInsert()) {
				HousePriceWeekConfDto weekPriceDto = new HousePriceWeekConfDto();
				weekPriceDto.setCreateUid(uid);
				weekPriceDto.setRentWay(sp.getRentWay());
				weekPriceDto.setPriceVal((int)BigDecimalUtil.mul(sp.getSpecialPrice().intValue(), 100));
				weekPriceDto.setHouseBaseFid(sp.getHouseBaseFid());
				if(sp.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
					weekPriceDto.setHouseRoomFid(sp.getHouseRoomFid());
				}
				
				Set<Integer> weekSet = new HashSet<>();
				List<String> weekList = sp.getSetTime();
				for (String weekStr : weekList) {
					weekSet.add(Integer.valueOf(weekStr));
				}
				weekPriceDto.setSetWeeks(weekSet);
				houseManageService.saveHousePriceWeekConf(JsonEntityTransform.Object2Json(weekPriceDto));
			}
			//保存周末价格END
		}catch(Exception e){
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "保存房间信息到数据库异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}

	/** 更新普通价格*/
	private void updateOrdinaryPrice(SpecialPriceDto sp){
		if(!Check.NuNObj(sp.getHouseBaseFid()) && sp.getRentWay()==RentWayEnum.HOUSE.getCode()){
			//整租，去base表更新房价
			HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
			houseBase.setFid(sp.getHouseBaseFid());
			houseBase.setLeasePrice(sp.getLeasePrice()*100);
			houseIssueService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBase));
		}else if(!Check.NuNObj(sp.getHouseRoomFid()) && sp.getRentWay()==RentWayEnum.ROOM.getCode()){
			//分租，去room表更新房价
			HouseRoomMsgEntity houseRoom = new HouseRoomMsgEntity();
			houseRoom.setFid(sp.getHouseRoomFid());
			houseRoom.setRoomPrice(sp.getRoomPrice()*100);
			houseIssueService.updateHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoom));
		}
	}

	/** 关于添加灵活间隙定价，折扣率到集合的方法*/
	private List<HouseConfMsgEntity> confListAddM(SpecialPriceDto sp,DataTransferObject dto,List<HouseConfMsgEntity> confList) throws SOAParseException {
		/** 灵活定价间隙价格插入三条记录，用户每设置一个折扣率增加插入一条*/
		//灵活以及间隙价格开关判断(0：关闭   1：打开)
		Integer gapFlexIsDel = IsDelEnum.PRIORITY.getCode();//默认开关灵活定价以及间隙价格开关是关闭的 2
		if(sp.getGapAndFlexiblePrice()==1){
			gapFlexIsDel = IsDelEnum.NOT_DEL.getCode(); //如果用户打开了开关 0
		}
		//循环置入三种不同的价格
		for(String gapPrice : gapFlexlist){
			/** 在配置对象中添加要插入的value*/
			HouseConfMsgEntity confEntity = new HouseConfMsgEntity();
			confEntity.setFid(UUIDGenerator.hexUUID());
			confEntity.setHouseBaseFid(sp.getHouseBaseFid());
			if(!Check.NuNStrStrict(sp.getHouseRoomFid()) && RentWayEnum.ROOM.getCode()==sp.getRentWay()){
				confEntity.setRoomFid(sp.getHouseRoomFid());
			}else{
				confEntity.setRoomFid(null);
			}
			confEntity.setBedFid(null);
			confEntity.setDicCode(gapPrice);
			String textValue = cityTemplateService.getTextValue(null, gapPrice);
			String text = SOAResParseUtil.getValueFromDataByKey(textValue, "textValue", String.class);
			confEntity.setDicVal(text);
			//此处开关由用户是否打开开关的行为控制
			confEntity.setIsDel(gapFlexIsDel);
			/** 放入集合*/
			confList.add(confEntity);
		}
		/** 折扣率判断*/
		Integer fullDayIsDel = IsDelEnum.PRIORITY.getCode();//默认满天折扣率的开关也是关闭的 2
		if(sp.getFullDayRate()==1){
			fullDayIsDel = IsDelEnum.NOT_DEL.getCode();//用户打开了开关 0
		}
		//满七天的判断处理
		HouseConfMsgEntity sevenEntity = new HouseConfMsgEntity();
		sevenEntity.setFid(UUIDGenerator.hexUUID());
		sevenEntity.setHouseBaseFid(sp.getHouseBaseFid());
		if(!Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode()==sp.getRentWay()) ){
			sevenEntity.setRoomFid(sp.getHouseRoomFid());
		}
		sevenEntity.setBedFid(null);
		sevenEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
		if(!Check.NuNObj(sp.getSevenDiscountRate())){
			//如果用户输入满7天折扣率
			sevenEntity.setDicVal(String.valueOf(sp.getSevenDiscountRate()));
		}else{
			//如果用户没有输入，给一个默认值-1代表用户未设置具体的折扣率
			sevenEntity.setDicVal(String.valueOf(-1));
		}
		sevenEntity.setIsDel(fullDayIsDel);
		/** 放入集合*/
		confList.add(sevenEntity);
		//满30天的判断处理
		HouseConfMsgEntity thirtyEntity = new HouseConfMsgEntity();
		thirtyEntity.setFid(UUIDGenerator.hexUUID());
		thirtyEntity.setHouseBaseFid(sp.getHouseBaseFid());
		if(!Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode()==sp.getRentWay()) ){
			thirtyEntity.setRoomFid(sp.getHouseRoomFid());
		}
		thirtyEntity.setBedFid(null);
		thirtyEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
		if(!Check.NuNObj(sp.getThirtyDiscountRate())){
			//用户输入了满30天折扣率
			thirtyEntity.setDicVal(String.valueOf(sp.getThirtyDiscountRate()));
		}else{
			//如果用户没有输入，给一个默认值-1
			thirtyEntity.setDicVal(String.valueOf(-1));
		}
		thirtyEntity.setIsDel(fullDayIsDel);
		/** 放入集合*/
		confList.add(thirtyEntity);

		return confList;
	}

	/**
	 * 
	 * 分租  户型下一步，去更新步骤 为第5步，更新条件：a.当前房源为待发布状态   b.当前步骤为第4步
	 *
	 * @author yd
	 * @created 2016年9月8日 上午10:11:59
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/updateHouseBase")
	@ResponseBody
	public DataTransferObject updateHouseBase(HttpServletRequest request){
		
		String houseBaseFid = request.getParameter("houseBaseFid");
		HouseBaseMsgEntity houseBase = new HouseBaseMsgEntity();
		houseBase.setFid(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseManageService.updateHouseBaseOpSeq(JsonEntityTransform.Object2Json(houseBase)));
		return dto;
	}
	/**
	 * 逻辑删除 房间信息
	 * @author afi
	 * @param request
	 * @param roomFid
	 * @return
	 */
	/*@RequestMapping("${LOGIN_UNAUTH}/delRoomInfo")
	@ResponseBody
	public DataTransferObject delRoomInfo(HttpServletRequest request){
		String roomFid = request.getParameter("roomFid");
		DataTransferObject dto = null;
		if(Check.NuNObj(roomFid)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		try {
			String mergeJson = houseIssueService.deleteCheckHouseRoomMsgByFid(roomFid);
			dto = JsonEntityTransform.json2DataTransferObject(mergeJson);
		}catch(Exception e){
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "删除房间信息异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}*/
	@RequestMapping("${LOGIN_UNAUTH}/delRoomInfo")
	@ResponseBody
	public DataTransferObject delRoomInfo(HttpServletRequest request){
		String roomFid = request.getParameter("roomFid");
		DataTransferObject dto = null;
		if(Check.NuNObj(roomFid)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		try {
			String mergeJson = houseIssueService.deleteHouseRoomMsgByFid(roomFid);
			dto = JsonEntityTransform.json2DataTransferObject(mergeJson);
		}catch(Exception e){
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "删除房间信息异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}



	/**
	 * 保存当前的缓存信息
	 * 通过key保存房间信息
	 * @author afi
	 * @param dto
	 */
	@Deprecated
	private void saveRoomInfoByKey(DataTransferObject dto,String key,String uid){
		if(dto.getCode() == DataTransferObject.SUCCESS){
			if(!key.startsWith(tmp_flag_pre)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return;
			}
			String cacheKey = RedisKeyConst.getConfigKey(key);
			try {
				String cacheJson = null;
				try {
					cacheJson = redisOperations.get(cacheKey);
				} catch (Exception e) {
					LogUtil.error(LOGGER, "redis错误e={}",e);
				}
				if(StringUtils.isBlank(cacheJson)){
					LogUtil.info(LOGGER, "当前缓存的key{}不存在", key);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("当前缓存的key不存在");
					return;
				}

				//将缓存中的对象转化成对象
				HouseRoomMsgDto houseRoomMsg = JsonEntityTransform.json2Object(cacheJson,HouseRoomMsgDto.class);
				if(Check.NuNObj(houseRoomMsg)){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("缓存中的数据异常");
					return;
				}
				String realRoomFid = key.substring(tmp_flag_pre.length());
				houseRoomMsg.setFid(realRoomFid);
				//直接保存房间信息打数据库
				if(!Check.NuNObj(houseRoomMsg.getRoomPrice())){
					houseRoomMsg.setRoomPrice(houseRoomMsg.getRoomPrice() * 100);
				}
				String mergeJson = houseIssueService.mergeCheckHouseRoomMsg(JsonEntityTransform.Object2Json(houseRoomMsg),uid);
				DataTransferObject saveDto = JsonEntityTransform.json2DataTransferObject(mergeJson);
				if(saveDto.getCode() == DataTransferObject.SUCCESS){
					try {
						//保存成功，直接删除当前的缓存信息
						redisOperations.del(cacheKey);
					} catch (Exception e) {
						LogUtil.error(LOGGER, "redis错误e={}",e);
					}
				}else {
					dto.setErrCode(saveDto.getCode());
					dto.setMsg(saveDto.getMsg());
				}
				dto.putValue("roomFid", realRoomFid);
			}catch(Exception e){
				dto = new DataTransferObject();
				LogUtil.error(LOGGER, "保存房间信息到数据库异常error:{}", e);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(e.getMessage());
				return;
			}
		}
	}


	/**
	 * 
	 * m站-床位列表页面
	 *
	 * @author liyingjie
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toListBed")
	@Deprecated
	public ModelAndView toListBed(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("/house/apartment/bedList");
		//到床位列表页面
		String roomFidString = request.getParameter("roomFid");
		//获取房源列表的来源
		String pageType = ValueUtil.getStrValue(request.getParameter("pageType"));
		mv.addObject("pageType",pageType);
		List<HouseBedMsgVo>  voList = new ArrayList<>();
		if (!Check.NuNStr(roomFidString) && !roomFidString.startsWith(tmp_flag_pre)) {
			//如果当前的roomFid飞空，并且不是临时房间 直接从数据库获取床信息
			String resultJson = houseIssueService.searchBedListByRoomFid(roomFidString);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<HouseBedMsgEntity> bedList = dto.parseData("list", new TypeReference<List<HouseBedMsgEntity>>() {
			});
			//2 床类型
			String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
			//3 床规格
			String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
			List<EnumVo> bedTypeList = null;
			Map<String,String> typeMap = new HashMap<>();
			List<EnumVo> bedSizeList = null;
			Map<String,String> sizeMap = new HashMap<>();
			try {
				bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
				bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
			} catch (SOAParseException e) {
				e.printStackTrace();
			}
			if(!Check.NuNCollection(bedTypeList)){
				for(EnumVo bedType: bedTypeList){
					typeMap.put(ValueUtil.getStrValue(bedType.getKey()),bedType.getText());
				}
			}
			if(!Check.NuNCollection(bedSizeList)){
				for(EnumVo bedSize: bedSizeList){
					sizeMap.put(ValueUtil.getStrValue(bedSize.getKey()), bedSize.getText());
				}
			}
			if(!Check.NuNCollection(bedList)){
				for(HouseBedMsgEntity bedEle: bedList){
					HouseBedMsgVo vo = new HouseBedMsgVo();
					BeanUtils.copyProperties(bedEle,vo);
					vo.setBedTypeDis(typeMap.get(ValueUtil.getStrValue(bedEle.getBedType())));
					vo.setBedSizeDis(sizeMap.get(ValueUtil.getStrValue(bedEle.getBedSize())));
					voList.add(vo);
				}
			}
		}
		mv.addObject("roomFid", request.getParameter("roomFid"));
		mv.addObject("houseBaseFid", request.getParameter("houseBaseFid"));
		mv.addObject("bedList", voList);
		mv.addObject("flag", request.getParameter("flag"));
		return mv;
	}

	/**
	 * 
	 * m站-到床位新增页面
	 *
	 * @author liyingjie
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toBedDetail")
	public ModelAndView toBedDetail(HttpServletRequest request,String houseBaseFid,String roomFid,String pageType){

		ModelAndView mv = new ModelAndView("/house/apartment/addBed");

		mv.addObject("houseBaseFid", houseBaseFid);
		mv.addObject("roomFid", roomFid);
		mv.addObject("pageType", pageType);

		//1 床的基本信息
		HouseBedMsgEntity houseBedMsg = null;
		String bedFid = request.getParameter("bedFid");
		if(!Check.NuNStr(bedFid)){
			String resultJson = houseIssueService.searchHouseBedMsgByFid(bedFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			houseBedMsg = dto.parseData("obj", new TypeReference<HouseBedMsgEntity>() {});
		}else {
			houseBedMsg = new HouseBedMsgEntity();
		}
		mv.addObject("houseBedMsg", houseBedMsg);

		//2 床类型
		String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
		//3 床规格
		String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
		List<EnumVo> bedTypeList = null;
		List<EnumVo> bedSizeList = null;
		try {
			bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
		} catch (SOAParseException e) {
			e.printStackTrace();
		}
		mv.addObject("bedTypeList", bedTypeList);
		mv.addObject("bedSizeList", bedSizeList);
		mv.addObject("flag", request.getParameter("flag"));
		return mv;
	}


	/**
	 * 保存床铺信息
	 * 如果当前的床铺信息对应的是临时的房间
	 * 需要将当前的房间信息落地，并替换当前的床关联的roomFid
	 * @author afi
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/saveBed")
	@ResponseBody
	@Deprecated
	public DataTransferObject saveBed(HttpServletRequest request,HouseBedMsgEntity bed){
		DataTransferObject dto = new DataTransferObject();

		CustomerVo user = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		if(Check.NuNObj(user)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("无法获取当前用户信息");
			return dto;
		}

		if(Check.NuNObj(bed) || Check.NuNStr(bed.getRoomFid()) || Check.NuNStr(bed.getHouseBaseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		//当前的缓存
		String cacheKey = null;
		String roomFid = bed.getRoomFid();
		String roomFidReal = roomFid;
		if(roomFid.startsWith(tmp_flag_pre)){
			cacheKey = roomFid;
			roomFidReal = roomFid.substring(tmp_flag_pre.length());
		}
		bed.setRoomFid(roomFidReal);
		try {
			if(!Check.NuNStr(cacheKey)){
				//当前的缓存存在
				//直接保存缓存信息
				saveRoomInfoByKey(dto,roomFid,user.getUid());
				if(dto.getCode() != DataTransferObject.SUCCESS){
					//房间信息保存失败
					return dto;
				}
				bed.setRoomFid(roomFidReal);
			}
			String mergeJson = houseIssueService.mergeHouseBedMsg(JsonEntityTransform.Object2Json(bed));
			DataTransferObject mergeDto = JsonEntityTransform.json2DataTransferObject(mergeJson);
			if(mergeDto.getCode() == DataTransferObject.SUCCESS){
				dto.putValue("roomFid",roomFidReal);
				dto.putValue("houseBaseFid",bed.getHouseBaseFid());
			}else {
				dto.setErrCode(mergeDto.getCode());
				dto.setMsg(mergeDto.getMsg());
			}
		}catch(Exception e){
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "保存房间信息到数据库异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}


	/**
	 * 保存床铺信息
	 * 如果当前的床铺信息对应的是临时的房间
	 * 需要将当前的房间信息落地，并替换当前的床关联的roomFid
	 * @author afi
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/delBed")
	@ResponseBody
	public DataTransferObject delBed(HttpServletRequest request,String bedFid){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(bedFid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数异常");
			return dto;
		}
		try {
			String mergeJson = houseIssueService.deleteHouseBedMsgByFid(bedFid);
			dto = JsonEntityTransform.json2DataTransferObject(mergeJson);
		}catch(Exception e){
			dto = new DataTransferObject();
			LogUtil.error(LOGGER, "删除房信息异常error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
		return dto;
	}



	/**
	 * m站-到房间详细信息
	 * @author liyingjie
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@Deprecated
	@RequestMapping("${LOGIN_UNAUTH}/roommateDetail")
	public ModelAndView toUpdateRoomDetail(HttpServletRequest request) throws SOAParseException{
		//路径
		ModelAndView mv = new ModelAndView("/house/apartment/roommateDetail");
		String roomId = request.getParameter("roomFid");
		String houseBaseFid = request.getParameter("houseBaseFid");
		DataTransferObject dto = null;

		if(Check.NuNStr(houseBaseFid)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("houseBaseFid为空");
		}
		if(Check.NuNStr(roomId)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("roomId为空");
		}
		String houseBaseResultJson =	houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		dto = JsonEntityTransform.json2DataTransferObject(houseBaseResultJson);
		HouseBaseMsgEntity houseBaseMsgEntity = dto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {
		});

		//当前的不是虚拟房间的 直接从数据库获取
		String resultJson = houseIssueService.searchHouseRoomMsgByFid(roomId);
		dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		HouseRoomMsgEntity houseRoomMsg = dto.parseData("obj", new TypeReference<HouseRoomMsgEntity>(){});
		if(!Check.NuNObj(houseRoomMsg.getRoomPrice()) && houseRoomMsg.getRoomPrice() == 0){
			houseRoomMsg.setRoomPrice(null);
		}else{
			//显示为分
			int money = houseRoomMsg.getRoomPrice();
			money = money/100;
			houseRoomMsg.setRoomPrice(money);
		}
		if(!Check.NuNObj(houseRoomMsg.getRoomArea()) && houseRoomMsg.getRoomArea() == 0){
			houseRoomMsg.setRoomArea(null);
		}
		mv.addObject("houseRoomMsg", houseRoomMsg);
		mv.addObject("isSetBed", checkBedNumByRoomFid(roomId));
		mv.addObject("houseBaseMsg", houseBaseMsgEntity);
		
		// 房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		mv.addObject("priceLow", priceLow);

		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);
		mv.addObject("priceHigh", priceHigh);
		return mv;
	}



	/**
	 * 跳转到修改房间名称，房间面积，床铺信息页面
	 * @author lishaochuan
	 * @create 2016年6月1日下午9:35:39
	 * @param request
	 * @return
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toRoomDetailBed")
	public ModelAndView toRoomDetailBed(HttpServletRequest request){
		//路径
		ModelAndView mv = new ModelAndView("/house/apartment/roomDetailBed");
		String roomFid = request.getParameter("roomFid");
		//当前的不是虚拟房间的 直接从数据库获取
		String resultJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		HouseRoomMsgEntity houseRoomMsg = dto.parseData("obj", new TypeReference<HouseRoomMsgEntity>(){});

		mv.addObject("roomFid", roomFid);
		mv.addObject("houseBaseFid", houseRoomMsg.getHouseBaseFid());
		mv.addObject("roomName", houseRoomMsg.getRoomName());
		mv.addObject("roomArea", houseRoomMsg.getRoomArea());
		mv.addObject("isSetBed", checkBedNumByRoomFid(roomFid));
		mv.addObject("houseRoomMsg", houseRoomMsg);

		return mv;
	}


	/**
	 * 
	 * 整租添加房间以及床位信息
	 *
	 * @author yd
	 * @created 2016年8月23日 下午5:38:12
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/roomBedZ")
	public String toRoomBedZIndex(HttpServletRequest request,ModelMap map){

		//路径
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String flag = request.getParameter("flag");
		String roomFid = request.getParameter("roomFid");

		//1 床类型
		String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
		DataTransferObject bedTypeDto = JsonEntityTransform.json2DataTransferObject(bedTypeJson);
		if(bedTypeDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum005.getValue());
			return "error/error";
		}

		List<EnumVo> bedTypeList = null;
		try {
			bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			map.put("bedTypeList", bedTypeList);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum005.getValue());
			return "error/error";
		}

		//2 床规格
		String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
		DataTransferObject bedSizeDto = JsonEntityTransform.json2DataTransferObject(bedSizeJson);
		if(bedSizeDto.getCode() == DataTransferObject.ERROR){
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum006.getValue());
			return "error/error";
		}
		List<EnumVo> bedSizeList = null;
		try {
			bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
			map.put("bedSizeList", bedSizeList);
		} catch (SOAParseException e) {
			LOGGER.error("cityTemplateService.getSelectEnum接口错误,dicCode={}",
					ProductRulesEnum.ProductRulesEnum006.getValue());
			return "error/error";
		}

		Map<String,String> typeMap = new HashMap<>();
		if(!Check.NuNCollection(bedTypeList)){
			for(EnumVo bedType: bedTypeList){
				typeMap.put(ValueUtil.getStrValue(bedType.getKey()),bedType.getText());
			}
		}

		Map<String,String> sizeMap = new HashMap<>();
		if(!Check.NuNCollection(bedSizeList)){
			for(EnumVo bedSize: bedSizeList){
				sizeMap.put(ValueUtil.getStrValue(bedSize.getKey()), bedSize.getText());
			}
		}


		if(!Check.NuNStr(roomFid)){
			String roomJson = houseIssueService.searchHouseRoomMsgByFid(roomFid);
			DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
			if(roomDto.getCode() == DataTransferObject.ERROR){
				LOGGER.error("houseIssueService.searchHouseRoomMsgByFid接口错误,roomId={},返回结果:{}",
						roomFid, roomJson);
				return "error/error";
			}
			try {
				HouseRoomMsgEntity houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				map.put("houseRoomMsg", houseRoomMsg);
			} catch (Exception e) {
				LOGGER.error("houseIssueService.searchHouseRoomMsgByFid接口错误,roomId={},返回结果:{}",
						roomFid, roomJson);
				return "error/error";
			}

			String bedListJson = houseIssueService.searchBedListByRoomFid(roomFid);
			DataTransferObject bedListDto = JsonEntityTransform.json2DataTransferObject(bedListJson);
			if(bedListDto.getCode() == DataTransferObject.ERROR){
				LOGGER.error("houseIssueService.searchBedListByRoomFid接口错误,roomId={},返回结果:{}",roomFid, bedListJson);
				return "error/error";
			}
			try {
				List<HouseBedMsgEntity> listHouseBedMsg = bedListDto.parseData("list", new TypeReference<List<HouseBedMsgEntity>>() {
				});
				if(!Check.NuNCollection(listHouseBedMsg)){
					
					List<HouseBedMsgVo> bedList =  new ArrayList<HouseBedMsgVo>();
					for (HouseBedMsgEntity houseBedMsgEntity : listHouseBedMsg) {
						
						HouseBedMsgVo vo = new HouseBedMsgVo();
						BeanUtils.copyProperties(houseBedMsgEntity, vo);
						vo.setBedTypeDis(typeMap.get(ValueUtil.getStrValue(vo.getBedType())));
						vo.setBedSizeDis(sizeMap.get(ValueUtil.getStrValue(vo.getBedSize())));
						
						bedList.add(vo);
					}
					map.put("bedList", bedList);
				}
				
			} catch (Exception e) {
				LOGGER.error("houseIssueService.searchBedListByRoomFid接口错误,roomId={},返回结果:{}",
						roomFid, roomJson);
				return "error/error";
			}
		}
		String rulerData = bedTypeAndSiz2Json(bedTypeList, bedSizeList);

		map.put("roomFid", roomFid);
		map.put("rulerData", rulerData);
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("flag", flag);
		map.put("index", request.getParameter("index"));
		return "/house/houseIssue/addRoomBedZ";
	}

	/**
	 * 
	 * 床类型 以及 大小 json 格式
	 *
	 * @author yd
	 * @created 2016年8月23日 下午7:46:59
	 *
	 * @param bedTypeList
	 * @param bedSizeList
	 */
	private String bedTypeAndSiz2Json(List<EnumVo> bedTypeList,List<EnumVo> bedSizeList){
		List<BedTypeSizeVo> listTypeVos  = new ArrayList<BedTypeSizeVo>();
		if(!Check.NuNCollection(bedTypeList)&&!Check.NuNCollection(bedSizeList)){
			List<BedTypeSizeVo> listSizeVos  = new ArrayList<BedTypeSizeVo>();
			for (EnumVo size : bedSizeList) {
				BedTypeSizeVo  bedSize = new BedTypeSizeVo();
				bedSize.setText(size.getText());
				bedSize.setValue(size.getKey());
				listSizeVos.add(bedSize);
			}

			for (EnumVo enumVo : bedTypeList) {
				BedTypeSizeVo  bedTypeSizeVo = new BedTypeSizeVo();
				bedTypeSizeVo.setText(enumVo.getText());
				bedTypeSizeVo.setValue(enumVo.getKey());
				bedTypeSizeVo.setChildren(listSizeVos);
				listTypeVos.add(bedTypeSizeVo);
			}
		}

		return JsonEntityTransform.Object2Json(listTypeVos);
	}

	/**
	 * 
	 * 整租保存数房间床位信息
	 * 
	 * 说明：
	 * 1. roomId 为null时 需要新添加房间信息以及床位信息
	 * 2. typeSize 组成 床位fid+床类型+床大小
	 * 3. roomId 床位信息不为null的时候，需要修改或添加床位信息（由于床位信息 现在没法修改，只有添加，若床位的bedFid 没有的话，直接添加，存在，不粗要处理）
	 *
	 * @author yd
	 * @created 2016年8月23日 下午9:05:37
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveRoomBedZ")
	@ResponseBody
	public String saveRoomBedZ(HttpServletRequest request){

		DataTransferObject dto = new DataTransferObject();
		String typeSize = request.getParameter("typeSize");
		String houseBaseFid = request.getParameter("houseBaseFid");
		String roomFid = request.getParameter("roomFid");


		if(Check.NuNStr(typeSize)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请添加床位");
			return dto.toJsonString();
		}

		String[] typeSizeArr = typeSize.split(",");
		if(typeSizeArr.length<=0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请添加床位");
			return dto.toJsonString();
		}

		//3 房源最低价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		DataTransferObject priceLowDto = JsonEntityTransform.json2DataTransferObject(priceLowJson);
		if (priceLowDto.getCode() == DataTransferObject.ERROR) {
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}", ProductRulesEnum021Enum.ProductRulesEnum021001);
			return "error/error";
		}
		String priceLow = min_room_price;
		try {
			 priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		} catch (SOAParseException e) {
			priceLow = min_room_price;
			LOGGER.error("cityTemplateService.getTextValue接口错误,dicCode={}", ProductRulesEnum021Enum.ProductRulesEnum021001);
			return "error/error";
		}

		RoomBedZDto roomBedZDto = new RoomBedZDto();
		roomBedZDto.setHouseBaseFid(houseBaseFid);
		roomBedZDto.setRoomFid(roomFid);
		roomBedZDto.setRoomPrice(Integer.valueOf(priceLow)*1000);

		List<HouseBedMsgEntity> listMsgEntities = new ArrayList<HouseBedMsgEntity>();
		for (String ts : typeSizeArr) {
			String[] bedArr = ts.split("_");
			HouseBedMsgEntity houseBedMsgEntity = new HouseBedMsgEntity();
			houseBedMsgEntity.setBedSize(Integer.valueOf(bedArr[1]));
			houseBedMsgEntity.setBedType(Integer.valueOf(bedArr[0]));
			if(!Check.NuNStr(bedArr[2])&&!"0".equals(bedArr[2])){
				houseBedMsgEntity.setFid(bedArr[2]);
			}
			listMsgEntities.add(houseBedMsgEntity);
		}
		roomBedZDto.setListBeds(listMsgEntities);
		
		CustomerVo customerVo = (CustomerVo) request.getAttribute("customerVo");
		
		roomBedZDto.setCreatedFid(customerVo.getUid());

		dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.saveRoomBedZ(JsonEntityTransform.Object2Json(roomBedZDto)));
		return dto.toJsonString();
	}
	
	
	/**
	 * 
	 * 整租校验 房源是否有床位
	 * 说明：
	 * 整租房源发布
	 * 说明：这里是 M站发布整租房源 第5步，选择户型，添加完房间后，点击下一步的功能，如果校验通过，房源状态从 待发布 切换成 已发布
	 * 根据房源fid或房间fid查询床位数量
	 * 处理逻辑： 
	 * 1.校验房源/房间基础信息是否存在
	 * 2.校验是否达到发布条件（整租 至少有一个完善房间信息）
	 * 3.修改房源状态 从待发布 到 已发布  并且所有房间状态 也和房源状态统一
	 * 4.修改房态后，记录房态切换日志
	 * 5.写入商机
	 * 
	 * @author yd
	 * @created 2016年8月24日 下午1:50:58
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/checkHouseBed")
	@ResponseBody
	public String checkHouseBed(HttpServletRequest request){
		
		
		DataTransferObject dto = new DataTransferObject();
		String houseBaseFid = request.getParameter("houseBaseFid");
		
		if(Check.NuNObj(houseBaseFid)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请完善房间信息");
			
			return dto.toJsonString();
		}
		
		dto = JsonEntityTransform.json2DataTransferObject(houseIssueService.countBedNumByHouseFid(houseBaseFid,"0"));
		return dto.toJsonString();
	}
	
}
