/**
 * @FileName: HouseBaseMsgInputController.java
 * @Package com.ziroom.minsu.mapp.house.controller
 * 
 * @author bushujie
 * @created 2016年5月25日 下午6:02:02
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.*;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomFinishStatusEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * <p>房源基本信息录入</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/houseInput")
public class HouseBaseMsgInputController {


	private static final Logger LOGGER = LoggerFactory.getLogger(HouseBaseMsgInputController.class);

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService; 

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name="house.houseBusinessService")
	private HouseBusinessService houseBusinessService;
	
	
	@Resource(name="house.houseGuardService")
	private HouseGuardService houseGuardService;
	

	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;
	
	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;
	
	@Resource(name = "house.houseManageService")
	private HouseManageService  houseManageService;

	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;

	/**
	 * 
	 * 录入房源信息--房源详情页
	 * 说明：
	 *
	 * @author bushujie
	 * @created 2016年5月25日 下午9:51:13
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/findHouseDetail")
	public String findHouseDetail(HttpServletRequest request) throws SOAParseException{
		String houseBaseFid=request.getParameter("houseBaseFid");
		String rentWay=request.getParameter("rentWay");
		//更新标志 1：更新操作
		String flag=request.getParameter("flag");
		flag=Check.NuNStr(flag)?"0":"1";

		if(Check.NuNStr(houseBaseFid)||Check.NuNStr(rentWay)){
			LogUtil.error(LOGGER, "当前房源houseBaseFid={}，rentWay={}", houseBaseFid,rentWay);
			throw new BusinessException("房源fid不存在，或者出租方式不存在");
		}
		String resultJson =houseIssueService.findHouseInputDetail(houseBaseFid);
		LogUtil.info(LOGGER, "录入房源查询房源详情结果:"+resultJson);
		HouseBaseDetailVo houseBaseDetailVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", HouseBaseDetailVo.class);
		//显示为元
		if(!Check.NuNObj(houseBaseDetailVo.getLeasePrice())){
			int money = houseBaseDetailVo.getLeasePrice();
			money = money/100;
			houseBaseDetailVo.setLeasePrice(money);
		}


		if(!Check.NuNObj(houseBaseDetailVo)&&!Check.NuNObj(houseBaseDetailVo.getTillDate())){
			houseBaseDetailVo.setTillDateStr(DateUtil.dateFormat(houseBaseDetailVo.getTillDate(), "yyyy-MM-dd"));
		}
		//入住人数限制
		String limitJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
		LogUtil.info(LOGGER, "入住人数限制枚举结果:"+limitJson);
		List<EnumVo> limitList=SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);

		//查询房源配套设施和服务列表
		String result=houseIssueService.findHouseFacilityAndService(houseBaseFid);

		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);

		//是否已有配置设施 1=无 2=有
		String support = "1"; 
		if(dto.getCode()==DataTransferObject.SUCCESS){
			//房源已选择配套设施
			List<HouseConfVo> houseFacilityList=SOAResParseUtil.getListValueFromDataByKey(result,"facilityList",HouseConfVo.class);
			//房源已选择服务
			List<HouseConfVo> houseServeListList=SOAResParseUtil.getListValueFromDataByKey(result,"serveList",HouseConfVo.class);

			if(!Check.NuNCollection(houseFacilityList)||!Check.NuNCollection(houseServeListList)){
				support = "2";
			}
		}
		//设置默认价格
		if(Check.NuNObj(houseBaseDetailVo.getLeasePrice())){
			houseBaseDetailVo.setLeasePrice(0);
		}
		request.setAttribute("flag", flag);
		request.setAttribute("support", support);
		request.setAttribute("limitList", limitList);
		
		/*Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);*/
		
		request.setAttribute("newDate", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		request.setAttribute("rentWay", rentWay);
		request.setAttribute("houseBaseFid" ,houseBaseFid);
		
		if (!Check.NuNObj(houseBaseDetailVo)) {//处理周边描述默认空格
			String houseAroundDesc=houseBaseDetailVo.getHouseAroundDesc();
			if(houseAroundDesc!=null){
				houseAroundDesc=houseAroundDesc.trim();
				houseAroundDesc=houseAroundDesc.length()==0?null:houseAroundDesc;
				houseBaseDetailVo.setHouseAroundDesc(houseAroundDesc); 
			}
		}
		
		request.setAttribute("houseBaseDetailVo", houseBaseDetailVo);

		//房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		request.setAttribute("priceLow", priceLow);
		
		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);

		//整套按照房源计算清洁费
		double cleanPer =0;
		String cleaningFeesJson = cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
		String cleaningFeesLimitStr=SOAResParseUtil.getValueFromDataByKey(cleaningFeesJson, "textValue", String.class);
		if (!Check.NuNStr(cleaningFeesLimitStr) ) {
			cleanPer = Double.parseDouble(cleaningFeesLimitStr);//清洁费比例 
		} 
		request.setAttribute("cleanPer", cleanPer);
		int cleaningFees=0;
		if(!Check.NuNObj(houseBaseDetailVo.getHouseCleaningFees())){
			cleaningFees=houseBaseDetailVo.getHouseCleaningFees()/100;
		}
		request.setAttribute("cleaningFees",cleaningFees );
		
		/** 关于价格设置的开始*/
		String houseRoomFid = request.getParameter("houseRoomFid");
		if(!Check.NuNObj(houseBaseDetailVo.getLeasePrice()) && houseBaseDetailVo.getLeasePrice().intValue() != 0){
			StringBuilder sb = new StringBuilder("已设置固定价格");
			// 分别给页面返回灵活定价，折扣优惠的集合
			HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
			confMsgEntity.setHouseBaseFid(houseBaseFid);
			confMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			//查询是否设置 今日特惠折扣
			confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020001.getValue());
			String gapFlexJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> gapFlexList = SOAResParseUtil.getListValueFromDataByKey(gapFlexJson, "list",
					HouseConfMsgEntity.class);
			if (!Check.NuNCollection(gapFlexList)) {
				sb.append("、今日特惠折扣");
			}
			
			//查询是否设置 空置间夜自动折扣
			confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020002.getValue());
			String gapFlexTJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
			List<HouseConfMsgEntity> gapFlexTList = SOAResParseUtil.getListValueFromDataByKey(gapFlexTJson, "list",
					HouseConfMsgEntity.class);
			if(!Check.NuNCollection(gapFlexTList)){
				sb.append("、空置间夜自动折扣");
			}
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("houseFid", houseBaseFid);
			paramMap.put("roomFid", houseRoomFid);
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


		if(!Check.NuNStr(flag)&&Integer.valueOf(flag)==1){
			boolean isSj = this.isSJ(houseBaseFid, Integer.parseInt(rentWay));
			request.setAttribute("isSj", isSj);
			request.setAttribute("houseRoomFid", houseRoomFid);

			return "/house/upHouseDetail";
		}

		return "/house/houseDetail";
	}

	/**
	 * 
	 * 校验房源是否已发布(房源面积不能填写)
	 * 1.整租 房源状态发布
	 * 2.合租,如果房源下有房间则为发布状态
	 *
	 * @author jixd
	 * @created 2016年6月16日 下午3:08:19
	 *
	 * @param houseBaseFid
	 * @param rentWay
	 * @return
	 */
	private boolean isSJ(String houseBaseFid,Integer rentWay){
		boolean flag = false;
		if(rentWay == RentWayEnum.HOUSE.getCode()){
			String houseBaseJson = houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
			DataTransferObject houseBaseDto = JsonEntityTransform.json2DataTransferObject(houseBaseJson);
			HouseBaseMsgEntity houseBaseMsgEntity = houseBaseDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
			Integer houseStatus = houseBaseMsgEntity.getHouseStatus();
			if(houseStatus == HouseStatusEnum.SJ.getCode()){
				flag = true;
			}
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
			DataTransferObject roomListDto = JsonEntityTransform.json2DataTransferObject(roomListJson);
			List<HouseRoomMsgEntity> roomList = roomListDto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>() {});
			if(!Check.NuNCollection(roomList)){
				for(HouseRoomMsgEntity roomMsg : roomList){
					if(roomMsg.getRoomStatus() == HouseStatusEnum.SJ.getCode()){
						flag = true;
						break;
					}
				}
			}
		}

		return flag;
	}

	/**
	 * 
	 * 录入房源信息--配套设施服务列表
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午2:44:18
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/findFacilityList")
	@ResponseBody
	public DataTransferObject findFacilityList(HttpServletRequest request){

		String houseBaseFid=request.getParameter("houseBaseFid");

		String rentWay=request.getParameter("rentWay");
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(houseBaseFid)||Check.NuNStr(rentWay)){
			LogUtil.error(LOGGER, "当前房源houseBaseFid={}，rentWay={}", houseBaseFid,rentWay);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数错误");
			return dto;
		}	
		//配套设施列表
		String facilityJson=cityTemplateService.getSelectSubDic(null,ProductRulesEnum.ProductRulesEnum002.getValue());
		List<EnumVo> facilityList;
		try {
			dto = JsonEntityTransform.json2DataTransferObject(facilityJson);

			if(dto.getCode()==DataTransferObject.ERROR){
				return dto;
			}
			facilityList = SOAResParseUtil.getListValueFromDataByKey(facilityJson, "subDic", EnumVo.class);

			//服务列表
			String serviceJson=cityTemplateService.getEffectiveSelectEnum(null,ProductRulesEnum.ProductRulesEnum0015.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(serviceJson);
			if(dto.getCode()==DataTransferObject.ERROR){
				return dto;
			}
			List<EnumVo> serviceList=SOAResParseUtil.getListValueFromDataByKey(serviceJson, "selectEnum", EnumVo.class);

			//查询房源配套设施和服务列表
			String resultJson=houseIssueService.findHouseFacilityAndService(houseBaseFid);

			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode()==DataTransferObject.ERROR){
				return dto;
			}
			//房源已选择配套设施
			List<HouseConfVo> houseFacilityList=SOAResParseUtil.getListValueFromDataByKey(resultJson,"facilityList",HouseConfVo.class);
			//房源已选择服务
			List<HouseConfVo> houseServeListList=SOAResParseUtil.getListValueFromDataByKey(resultJson,"serveList",HouseConfVo.class);

			dto.putValue("facilityList", facilityList);
			dto.putValue("serviceList", serviceList);
			dto.putValue("houseFacilityList", houseFacilityList);
			dto.putValue("houseServeListList", houseServeListList);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "查询房源配套设施异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询异常");
		}
		return dto;
	}

	/**
	 * 
	 * 发布房源操作
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午10:32:47
	 *
	 * @param houseBaseDetailVo
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/issueHouse")
	@ResponseBody
	public DataTransferObject issueHouse(HouseBaseDetailVo houseBaseDetailVo,HttpServletRequest request){
		DataTransferObject dto  = new DataTransferObject();

		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String tillDateStr = request.getParameter("tillDateStr");
		String houseBaseFid=request.getParameter("houseBaseFid");

		String houseName = houseBaseDetailVo.getHouseName();
		if(!Check.NuNStr(houseName) && houseName.trim().length() > 30){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源名称需小于30字");
			return dto;
		}
		houseBaseDetailVo.setFid(houseBaseFid);
		houseBaseDetailVo.setLandlordUid(customerVo.getUid());
		try {
			houseBaseDetailVo.setTillDate(DateUtil.parseDate(SysConst.House.TILL_DATE, "yyyy-MM-dd"));
			dto  = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.updateHouseInputDetail(JsonEntityTransform.Object2Json(houseBaseDetailVo)));
			if(houseBaseDetailVo.getIsIssue()==1){
				CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
				customerBaseMsgEntity.setUid(houseBaseDetailVo.getLandlordUid());
				customerBaseMsgEntity.setIsLandlord(1);
				//设置客户为房东
				customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
				//发布房源判断是否录入商机
				/*houseBusinessInput(houseBaseFid,houseBaseDetailVo.getLandlordUid());*/ // modified by liujun 2017年2月21日
			}	
		} catch (Exception e) {
			LogUtil.error(LOGGER, "更新房源信息异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("更新异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 发布房源第四步后退到第三步，信息的保存(整租，分租不做此需求)
	 *
	 * @author lunan
	 * @created 2016年10月18日 下午2:25:03
	 *
	 * @param houseBaseDetailVo
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveInfo")
	@ResponseBody
	public DataTransferObject saveInfo(HouseBaseDetailVo houseBaseDetailVo,HttpServletRequest request){
		DataTransferObject dto  = new DataTransferObject();
		CustomerVo customerVo = CustomerVoUtils.getCusotmerVoFromSesstion(request);
		String tillDateStr = SysConst.House.TILL_DATE;
		String houseBaseFid=request.getParameter("houseBaseFid");
		houseBaseDetailVo.setFid(houseBaseFid);
		houseBaseDetailVo.setLandlordUid(customerVo.getUid());
		try {
			houseBaseDetailVo.setTillDate(DateUtil.parseDate(tillDateStr, "yyyy-MM-dd"));
			dto  = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.updateHouseInfo(JsonEntityTransform.Object2Json(houseBaseDetailVo)));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "更新房源信息异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("更新异常");
		}
		return dto;
	}

	/**
	 * 
	 *  发布房源操作
	 * 发布流程：
	 * 1. 修改房源基础信息
	 * 2. 房源默认值设置
	 * 3. 保存默认优惠规则
	 * 4. 保存默认押金规则
	 * 5. 保存房源操作日志 （ 整租保存房源  分租保存每个房间 ）
	 * 6. 级联更新房间 以及 床位的状态 （整租 和 分租  一样）
	 * 7. 商机录入
	 * 8. 给地推和维护管家发送短信
	 *
	 * @author yd
	 * @created 2016年8月25日 下午5:03:48
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/releaseHouse")
	@ResponseBody
	public DataTransferObject releaseHouse(HttpServletRequest request){


		DataTransferObject dto = new DataTransferObject();

		String houseBaseFid=request.getParameter("houseBaseFid");
		String rentWay=request.getParameter("rentWay");
		String roomFid=request.getParameter("houseRoomFid");
		CustomerVo customerVo = (CustomerVo) request.getAttribute("customerVo");

		try {
			Map<Integer, String> errorMsgMap = new TreeMap<Integer, String>();
			boolean isUploadable = this.validatePicNumByType(houseBaseFid, Integer.valueOf(rentWay), roomFid, errorMsgMap);
			if (!isUploadable) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(assembleErrorMsg(errorMsgMap));
				return dto;
			}

			/****************校验是否已经设置默认照片,M站兼容（@Date:2017.07.14 @Author:lusp）**************/
			HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
			houseBaseParamsDto.setHouseBaseFid(houseBaseFid);
			houseBaseParamsDto.setRentWay(Integer.valueOf(rentWay));
			houseBaseParamsDto.setRoomFid(roomFid);
			String isSetDefaultPicJson = houseIssueService.isSetDefaultPic(JsonEntityTransform.Object2Json(houseBaseParamsDto));
			DataTransferObject isSetDefaultPicDto = JsonEntityTransform.json2DataTransferObject(isSetDefaultPicJson);
			if (isSetDefaultPicDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "issueHouseInDetail(),获取房源或房间是否设置了封面照片失败,msg:{}",isSetDefaultPicDto.getMsg());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(assembleErrorMsg(errorMsgMap));
				return dto;
			}
			Boolean hasDefault = SOAResParseUtil.getBooleanFromDataByKey(isSetDefaultPicJson,"hasDefault");
			if(!hasDefault){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("请给房源或者每个房间设置封面照片");
				return dto;
			}
			/****************校验是否已经设置默认照片，M站兼容**************/

			dto  = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.releaseHouse(houseBaseFid));
			if(dto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
				customerBaseMsgEntity.setUid(customerVo.getUid());
				customerBaseMsgEntity.setIsLandlord(1);
				//设置客户为房东
				customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));
				//发布房源判断是否录入商机
				/*houseBusinessInput(houseBaseFid,customerVo.getUid());*/ // modified by liujun 2017年2月21日
			}	
		} catch (Exception e) {
			LogUtil.error(LOGGER, "更新房源信息异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("更新异常");
		}
		return dto;
	}

	/**
	 * by类型校验是否上传照片且照片数量达到最少限制数量
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param houseBaseFid
	 * @param rentWay 
	 * @param houseRoomFid
	 * @param errorMsgMap 
	 * @throws SOAParseException 
	 * @return
	 */
	private boolean validatePicNumByType(String houseBaseFid, Integer rentWay, String houseRoomFid, Map<Integer, String> errorMsgMap) throws SOAParseException {
		String picJson = houseIssueService.searchHousePicMsgListByHouseFid(houseBaseFid);
		String houseBaseJsonString=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJsonString, "obj", HouseBaseMsgEntity.class);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(picJson);
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			//不算的照片数量
			int subNum=0;
			List<HousePicMsgEntity> picList = SOAResParseUtil.getListValueFromDataByKey(picJson, "list", HousePicMsgEntity.class);
			Map<String, List<HousePicMsgEntity>> wsMap = new HashMap<String, List<HousePicMsgEntity>>();
			List<HousePicMsgEntity> ktList = new ArrayList<HousePicMsgEntity>();
			List<HousePicMsgEntity> cfList = new ArrayList<HousePicMsgEntity>();
			List<HousePicMsgEntity> wsjList = new ArrayList<HousePicMsgEntity>();
			List<HousePicMsgEntity> swList = new ArrayList<HousePicMsgEntity>();
			for (HousePicMsgEntity housePicMsgEntity : picList) {
				Integer picType = housePicMsgEntity.getPicType();
				if (!Check.NuNObj(picType)) {
					if (picType.intValue() == HousePicTypeEnum.WS.getCode()) {
						this.filterRoomPic(wsMap, houseBaseFid, rentWay, houseRoomFid, housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.KT.getCode()) {
						ktList.add(housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.CF.getCode()) {
						cfList.add(housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.WSJ.getCode()) {
						wsjList.add(housePicMsgEntity);
					} else if (picType.intValue() == HousePicTypeEnum.SW.getCode()) {
						swList.add(housePicMsgEntity);
					}
				}
			}
			
			boolean flag = true;
			int totalPicNum = 0;// 整套出租:所有图片 独立房间: 房间图片 + 公区图片
			int ktPicNum = ktList.size();
			//如果客厅为0 不进行判断
			if(houseBaseMsgEntity.getHallNum()!=0){
				totalPicNum += ktPicNum;
				if (ktPicNum < HousePicTypeEnum.KT.getMin()) {
					flag = false;
					errorMsgMap.put(HousePicTypeEnum.KT.getCode(), "客厅至少" + HousePicTypeEnum.KT.getMin() + "张照片");
				}
			} else {
				subNum+=HousePicTypeEnum.KT.getMin();
			}
			int cfPicNum = cfList.size();
			totalPicNum += cfPicNum;
			if (cfPicNum < HousePicTypeEnum.CF.getMin()) {
				flag = false;
				errorMsgMap.put(HousePicTypeEnum.CF.getCode(), "厨房至少" + HousePicTypeEnum.CF.getMin() + "张照片");
			}
			int wsjPicNum = wsjList.size();
			//如果卫生间为0 不进行判断
			if(houseBaseMsgEntity.getToiletNum()!=0){
				totalPicNum += wsjPicNum;
				if (wsjPicNum < HousePicTypeEnum.WSJ.getMin()) {
					flag = false;
					errorMsgMap.put(HousePicTypeEnum.WSJ.getCode(), "卫生间至少" + HousePicTypeEnum.WSJ.getMin() + "张照片");
				}
			} else {
				subNum+=HousePicTypeEnum.WSJ.getMin();
			}
			int swPicNum = swList.size();
			totalPicNum += swPicNum;
			if (swPicNum < HousePicTypeEnum.SW.getMin()) {
				flag = false;
				errorMsgMap.put(HousePicTypeEnum.SW.getCode(), "室外至少" + HousePicTypeEnum.SW.getMin() + "张照片");
			}
			if (Check.NuNMap(wsMap)) {
				flag = false;
				errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
			}
			if (rentWay == RentWayEnum.HOUSE.getCode()) {
				for (Entry<String, List<HousePicMsgEntity>> entry : wsMap.entrySet()) {
					int wsPicNum = entry.getValue().size();
					totalPicNum += wsPicNum;
					if (wsPicNum < HousePicTypeEnum.WS.getMin()) {
						flag = false;
						errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
					}
				}
				if (totalPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)) {
					flag = false;
					errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum) + "张");
				}
			} else if (rentWay == RentWayEnum.ROOM.getCode()) {
				for (Entry<String, List<HousePicMsgEntity>> entry : wsMap.entrySet()) {
					int wsPicNum = entry.getValue().size();
					if (wsPicNum < HousePicTypeEnum.WS.getMin()) {
						flag = false;
						errorMsgMap.put(HousePicTypeEnum.WS.getCode(), "卧室至少" + HousePicTypeEnum.WS.getMin() + "张照片");
					}
					if (totalPicNum + wsPicNum < (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)) {
						flag = false;
						errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum) + "张");
					}
				}
			}
			return flag;
		}
		return false;
	}

	/**
	 * 筛选指定房间照片
	 *
	 * @author liujun
	 * @created 2017年3月2日
	 *
	 * @param wsMap
	 * @param houseBaseFid 
	 * @param houseRoomFid 
	 * @param housePicMsgEntity
	 * @param isEmpty
	 * @throws SOAParseException 
	 */
	private void filterRoomPic(Map<String, List<HousePicMsgEntity>> wsMap, String houseBaseFid, Integer rentWay, String houseRoomFid,
			HousePicMsgEntity housePicMsgEntity) throws SOAParseException {
		if (!Check.NuNStrStrict(housePicMsgEntity.getRoomFid())) {
			if (rentWay == RentWayEnum.HOUSE.getCode() || (rentWay == RentWayEnum.ROOM.getCode() && Check.NuNStrStrict(houseRoomFid))) {
				if (Check.NuNMap(wsMap)) {
					String resultJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
					if (dto.getCode() == DataTransferObject.SUCCESS) {
						List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomMsgEntity.class);
						for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
							List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
							wsMap.put(houseRoomMsgEntity.getFid(), wsList);
						}
					}
				}
				if (wsMap.containsKey(housePicMsgEntity.getRoomFid())) {
					List<HousePicMsgEntity> wsList = wsMap.get(housePicMsgEntity.getRoomFid());
					wsList.add(housePicMsgEntity);
				}
			} else if (rentWay == RentWayEnum.ROOM.getCode() && !Check.NuNStrStrict(houseRoomFid)) {
				if (Check.NuNMap(wsMap)) {
					List<HousePicMsgEntity> wsList = new ArrayList<HousePicMsgEntity>();
					wsMap.put(houseRoomFid, wsList);
				}
				if (houseRoomFid.equals(housePicMsgEntity.getRoomFid())) {
					List<HousePicMsgEntity> wsList = wsMap.get(houseRoomFid);
					wsList.add(housePicMsgEntity);
				}								
			}
		}
	}
	
	/**
	 * 拼接错误信息字符串
	 *
	 * @author liujun
	 * @created 2017年3月10日
	 * @param errorMsgMap 
	 *
	 * @return
	 */
	private String assembleErrorMsg(Map<Integer, String> errorMsgMap) {
		int temp = 0;
		StringBuilder sb = new StringBuilder();
		for (String errorMsg : errorMsgMap.values()) {
			if (temp == 0) {
				sb.append(errorMsg);
			} else {
				sb.append("、").append(errorMsg);
			}
			temp++;
		}
		return sb.toString();
	}

	/**
	 * 
	 * 给地推管家和维护管家发送短信
	 *
	 * @author yd
	 * @created 2016年10月25日 下午2:59:34
	 *
	 * @param houseBaseFid
	 * @param realName
	 * @param houseName
	 * @param houseSn
	 */
	/*private void sendMsgTohouseGuard(String houseBaseFid,String realName,String houseName,Integer rentWay,String houseSn){
		try {
			if (rentWay == RentWayEnum.ROOM.getCode()){
				String roomJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
				DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
				if (roomDto.getCode() == DataTransferObject.ERROR){
					return;
				}
				List<HouseRoomMsgEntity> roomList = roomDto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>() {});
				//第一次分租发布 房间都为已发布状态
				if (Check.NuNCollection(roomList)){
					return;
				}
				int count = roomList.size();
				HouseRoomMsgEntity houseRoomMsgEntity = roomList.get(0);
				String roomName = houseRoomMsgEntity.getRoomName();
				String roomSn = houseRoomMsgEntity.getRoomSn();
				houseName = roomName + "等" + count +"个房间";
				houseSn = roomSn + "等";
			}

			HouseGuardRelEntity houseGuardRel = this.findHouseGuardRelEntity(houseBaseFid);
			if(!Check.NuNObj(houseGuardRel)){
				if(!Check.NuNStr(houseGuardRel.getEmpPushCode())){
					EmployeeEntity empPush = this.findEmployeeEntity(houseGuardRel.getEmpPushCode());
					if(!Check.NuNObj(empPush) && !Check.NuNStr(empPush.getEmpMobile())){
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("{1}", realName);
						paramsMap.put("{2}", houseName);
						paramsMap.put("{3}", houseSn);
						this.sendSms(empPush.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_LANDLORD_RELEASE.getCode());
					}
				}
				
				if(!Check.NuNStr(houseGuardRel.getEmpGuardCode())
						&&!houseGuardRel.getEmpGuardCode().equals(houseGuardRel.getEmpPushCode())){
					EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRel.getEmpGuardCode());
					if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("{1}", realName);
						paramsMap.put("{2}", houseName);
						paramsMap.put("{3}", houseSn);
						this.sendSms(empGuard.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_LANDLORD_RELEASE.getCode());
					}
				}
			}
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "向地推管家和维护管家发送短信异常e={}", e);
		}
		
	}*/
	
	
	/**
	 * 发送短信
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param mobile
	 * @param paramsMap
	 * @param smsCode
	 */
	/*private void sendSms(String mobile, Map<String, String> paramsMap, int smsCode) {
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile(mobile);
		smsRequest.setParamsMap(paramsMap);
		smsRequest.setSmsCode(String.valueOf(smsCode));
		smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	}*/	

	/**
	 * 根据员工编号查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param empCode
	 * @return
	 * @throws SOAParseException 
	 */
/*	private EmployeeEntity findEmployeeEntity(String empCode) throws SOAParseException {
		String resultJson = employeeService.findEmployeeByEmpCode(empCode);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}", empCode);
			return null;
		} else {
			EmployeeEntity empPush =SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
			return empPush;
		}
	}*/
	
	/**
	 * 根据房源fid获取房源管家关系信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param houseBaseFid
	 * @throws SOAParseException 
	 * @return
	 */
	/*private HouseGuardRelEntity findHouseGuardRelEntity(String houseBaseFid) throws SOAParseException {
		String resultJson = houseGuardService.findHouseGuardRelByHouseBaseFid(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "houseGuardService.findHouseGuardRelByHouseBaseFid错误,houseBaseFid={},结果:{}",
					houseBaseFid, resultJson);
			return null;
		} else {
			HouseGuardRelEntity houseGuardRel = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "houseGuardRel", HouseGuardRelEntity.class);
			return houseGuardRel;
		}
	}*/

	/**
	 * 
	 * 保存房源的配套设施，以及服务
	 *
	 * @author yd
	 * @created 2016年5月28日 下午4:33:40
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveConMsg")
	@ResponseBody
	public DataTransferObject saveConMsg(HttpServletRequest request){


		DataTransferObject dto = new DataTransferObject();
		String houseBaseFid=request.getParameter("houseBaseFid");
		String supportArray = request.getParameter("supportArray");

		if(Check.NuNStr(supportArray)){
			LogUtil.info(LOGGER, "当前更新为配置信息为空");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请选择配置信息");
			return dto;
		}

		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源fid不存在");
			return dto;
		}
		List<HouseConfMsgEntity> listHouseConfMsg = new ArrayList<HouseConfMsgEntity>();
		String[] conMsgArray = supportArray.split(",");
		for (String string : conMsgArray) {

			String[] conArray = string.split("_");
			if(conArray.length == 2){
				HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
				houseConfMsg.setHouseBaseFid(houseBaseFid);
				houseConfMsg.setDicCode(string.split("_")[0]);
				houseConfMsg.setDicVal(string.split("_")[1]);
				listHouseConfMsg.add(houseConfMsg);
			}

		}
		if(Check.NuNCollection(listHouseConfMsg)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请选择配套设施");
			return dto;
		}
		dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.updateHouseConf(JsonEntityTransform.Object2Json(listHouseConfMsg)));
		return dto;
	}

	/**
	 * 
	 * 到发布房源完成页面
	 *
	 * @author yd
	 * @created 2016年5月28日 下午11:02:10
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/goToFinished")
	public String goToFinished(HttpServletRequest request){
		String houseBaseFid=request.getParameter("houseBaseFid");
		String rentWay=request.getParameter("rentWay");
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("rentWay", rentWay);
		request.setAttribute("flag", request.getParameter("flag") == null?"0":request.getParameter("flag") );
		return "/house/publishSuccess";
	}

	/**
	 * 
	 * 访问地址：http://10.30.26.26:8080/minsu-web-mapp/houseInput/43e881/goToUpHousePic?houseBaseFid=8a9084df54d3646f0154d372bba00016
	 * 
	 * 跳转到房源图片上传页
	 *
	 * @author bushujie
	 * @created 2016年5月29日 下午5:42:07
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/goToUpHousePic")
	public String goToUpHousePic(HttpServletRequest request){
		String houseBaseFid=request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String houseRoomFid = request.getParameter("houseRoomFid");
		HousePicDto housePicDto=new HousePicDto();
		housePicDto.setHouseBaseFid(houseBaseFid);
		if("null".equals(houseRoomFid)){
			housePicDto.setHouseRoomFid(null);
		} else {
			housePicDto.setHouseRoomFid(houseRoomFid);
		}
		String resultJson=troyHouseMgtService.housePicAuditList(JsonEntityTransform.Object2Json(housePicDto));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		HousePicAuditVo housePicAuditVo =resultDto.parseData("housePicAuditVo", new TypeReference<HousePicAuditVo>() {});
		LogUtil.info(LOGGER, "房源图片列表："+JsonEntityTransform.Object2Json(housePicAuditVo));

		//统计房间图片数量，合租房间图片数量在页面判断
		List<HousePicVo> roomPicList = housePicAuditVo.getRoomPicList();
		List<HousePicVo> housePicList = housePicAuditVo.getHousePicList();
		Integer houseAllPicCount = 0;
		if(Integer.parseInt(rentWay) == RentWayEnum.HOUSE.getCode()){
			for(HousePicVo pic:roomPicList){
				houseAllPicCount += pic.getPicList().size();
			}
			for(HousePicVo pic:housePicList){
				houseAllPicCount += pic.getPicList().size();
			}
		}
		
		//房源或者房间默认图片查询
		String picJson=null;
		//获取房源的审核状态，来提示上架后的房源图片需要审核
		if(Integer.parseInt(rentWay) == RentWayEnum.HOUSE.getCode()){
			//整租获取房源状态
			DataTransferObject houseBaseMsgDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(houseBaseFid));
			HouseBaseMsgEntity houseMsgEntity = houseBaseMsgDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {});
			request.setAttribute("houseStatus", houseMsgEntity.getHouseStatus());
			
			picJson=houseIssueService.findDefaultPic(houseBaseFid, RentWayEnum.HOUSE.getCode());
		}

		if(Integer.parseInt(rentWay) == RentWayEnum.ROOM.getCode()&&!Check.NuNStr(houseRoomFid)&&!"null".equals(houseRoomFid)){
			DataTransferObject roomBaseMsgDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseRoomMsgByFid(houseRoomFid));
			HouseRoomMsgEntity roomMsgEntity = roomBaseMsgDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
			request.setAttribute("houseStatus", roomMsgEntity.getRoomStatus());
			
			picJson=houseIssueService.findDefaultPic(houseRoomFid, RentWayEnum.ROOM.getCode());
		}
		
		if (!Check.NuNStr(picJson) && !Check.NuNCollection(roomPicList)) {// 处理默认图片，排序图片列表
			String defaultPic = SOAResParseUtil.getStrFromDataByKey(picJson, "picBaseUrl");
			
			if(!Check.NuNStr(defaultPic)){
				if (!Check.NuNCollection(roomPicList)) {
					for (HousePicVo picVo : roomPicList) {
						if (!Check.NuNCollection(picVo.getPicList())) {
	
							for (int i = 0; i < picVo.getPicList().size(); i++) {
								HousePicMsgEntity housePicMsg = picVo.getPicList().get(i);
								if (defaultPic.contains(housePicMsg.getPicBaseUrl())
										|| housePicMsg.getPicBaseUrl().contains(defaultPic)) {
									Collections.swap(picVo.getPicList(), 0, i);
								}
	
							}
						}
	
					}
				}
				if (!Check.NuNCollection(roomPicList)) {
					for (HousePicVo picVo : housePicList) {
						if (!Check.NuNCollection(picVo.getPicList())) {
	
							for (int i = 0; i < picVo.getPicList().size(); i++) {
								HousePicMsgEntity housePicMsg = picVo.getPicList().get(i);
								if (defaultPic.contains(housePicMsg.getPicBaseUrl())
										|| housePicMsg.getPicBaseUrl().contains(defaultPic)) {
									Collections.swap(picVo.getPicList(), 0, i);
								}
	
							}
						}
	
					}
				}
			}
		}
		 
		request.setAttribute("houseAllPicCount", houseAllPicCount);
		request.setAttribute("roomPicList", roomPicList);
		request.setAttribute("picTypeList", housePicList);
		request.setAttribute("roomNum", roomPicList==null?0:roomPicList.size());
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", detail_big_pic);
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("rentWay", rentWay);
		request.setAttribute("houseRoomFid", houseRoomFid);
		request.setAttribute("flag", request.getParameter("flag"));
		request.setAttribute("pageType", request.getParameter("pageType"));
		return "/house/upHousePic";
	}



	/**
	 * 
	 * 跳转房源修改页
	 *
	 * @author bushujie
	 * @created 2016年5月30日 下午3:40:33
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/goToHouseUpdate")
	public String goToHouseUpdate(HttpServletRequest request) throws SOAParseException{
		String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
		//提示升级
		if ("1".equals(zkSysValue)){
			return "common/upgrade";
		}

		String houseBaseFid=request.getParameter("houseBaseFid");
		String houseRoomFid=request.getParameter("houseRoomFid");
		Integer rentWayInt=0;
		String rentWay=request.getParameter("rentWay");
		if(!Check.NuNStr(rentWay)){
			rentWayInt=Integer.valueOf(rentWay);
		}
		String resultJson=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		String upTitle = "修改房源";
		HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseBaseMsgEntity.class);
		//如果待发布需要跳转到相应步骤
		/*if(houseBaseMsgEntity.getHouseStatus()==HouseStatusEnum.DFB.getCode()
				||houseBaseMsgEntity.getHouseStatus()==HouseStatusEnum.XXSHWTG.getCode()){
			return "redirect:/houseInput/43e881/findHouseDetail?houseBaseFid="+houseBaseFid+"&rentWay="+rentWay;  
		}*/
		String houseNo = houseBaseMsgEntity.getHouseSn();
		request.setAttribute("houseStauts", HouseStatusEnum.getHouseStatusByCode(houseBaseMsgEntity.getHouseStatus()).getShowStatusName());
		request.setAttribute("status", houseBaseMsgEntity.getHouseStatus());
		
		
		int addressStatus=YesOrNoOrFrozenEnum.YES.getCode();
		if(RentWayEnum.ROOM.getCode()==rentWayInt){
			String roomListStr = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
			List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(roomListStr, "list", HouseRoomMsgEntity.class);
			if (!Check.NuNCollection(roomList)) {
				for(HouseRoomMsgEntity room:roomList){
					if(room.getRoomStatus()==20  || room.getRoomStatus()==40 || room.getRoomStatus()==41 || room.getRoomStatus()==50){
						addressStatus=YesOrNoOrFrozenEnum.NO.getCode();
						break;
					}
					if(room.getRoomStatus()== 11){
						addressStatus=YesOrNoOrFrozenEnum.FROZEN.getCode();
						break;
					}
				} 
			}
		}else if(RentWayEnum.HOUSE.getCode()==rentWayInt){			
			if(houseBaseMsgEntity.getHouseStatus()==20  || houseBaseMsgEntity.getHouseStatus()==40 || houseBaseMsgEntity.getHouseStatus()==41 || houseBaseMsgEntity.getHouseStatus()==50){
				addressStatus=YesOrNoOrFrozenEnum.NO.getCode();
			}
			if(houseBaseMsgEntity.getHouseStatus()==11 ){
				addressStatus=YesOrNoOrFrozenEnum.FROZEN.getCode();
			}
		}
		request.setAttribute("addressStatus", addressStatus);
		
		if(RentWayEnum.ROOM.getCode()==rentWayInt&&!Check.NuNStr(houseRoomFid)&&!"null".equals(houseRoomFid)){
			String roomJson=houseIssueService.searchHouseRoomMsgByFid(houseRoomFid);
			HouseRoomMsgEntity houseRoomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
			houseNo = houseRoomMsgEntity.getRoomSn();
			if(Check.NuNStr(houseRoomMsgEntity.getRoomName())) houseRoomMsgEntity.setRoomName(upTitle);
			request.setAttribute("houseRoom", houseRoomMsgEntity);
			if(Check.NuNObj(houseRoomMsgEntity.getRoomStatus())){
				request.setAttribute("houseStauts", null);
			}else{
				request.setAttribute("houseStauts", HouseStatusEnum.getHouseStatusByCode(houseRoomMsgEntity.getRoomStatus()).getShowStatusName());
			}
			request.setAttribute("status", houseRoomMsgEntity.getRoomStatus());
		}

		if(Check.NuNStr(houseBaseMsgEntity.getHouseName())) houseBaseMsgEntity.setHouseName(upTitle);
		request.setAttribute("houseNo", houseNo);
		request.setAttribute("houseBase", houseBaseMsgEntity);
		request.setAttribute("houseRoomFid", houseRoomFid);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", detail_big_pic);
		request.setAttribute("rentWay", rentWay);
		request.setAttribute("rentStyle", RentWayEnum.getEnumMap().get(rentWayInt));

		String houseTypeKey = houseBaseMsgEntity.getHouseType().toString();
		String houseTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
		List<EnumVo> houseTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
		for(EnumVo vo:houseTypeList){
			if(vo.getKey().equals(houseTypeKey)){
				request.setAttribute("houseStyle", vo.getText());
				break;
			}
		}
		
		request.setAttribute("houseTypeList", houseTypeList);
		request.setAttribute("houseTypeKey", houseTypeKey);
		//房源或者房间默认图片查询
		String picJson=null;
		if(RentWayEnum.HOUSE.getCode()==rentWayInt){
			picJson=houseIssueService.findDefaultPic(houseBaseFid, rentWayInt);
		}else if(RentWayEnum.ROOM.getCode()==rentWayInt) {
			picJson=houseIssueService.findDefaultPic(houseRoomFid, rentWayInt);
		}
		String defaultPic=SOAResParseUtil.getStrFromDataByKey(picJson, "picBaseUrl");
		request.setAttribute("defaultPic", PicUtil.getSpecialPic(picBaseAddrMona, defaultPic, detail_big_pic));

		//房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		request.setAttribute("priceLow", priceLow);
		
		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);

		//如果审核未通过查询审核未通过原因
		if(RentWayEnum.HOUSE.getCode()==rentWayInt){

			picJson=houseIssueService.findDefaultPic(houseBaseFid, rentWayInt);
		}else if(RentWayEnum.ROOM.getCode()==rentWayInt) {
			picJson=houseIssueService.findDefaultPic(houseRoomFid, rentWayInt);
		}

		//查找房源的相关信息完整度
		HouseBaseVo vo = new HouseBaseVo();
		vo.setHouseFid(houseBaseFid);
		vo.setRoomFid(houseRoomFid);
		vo.setRentWay(rentWayInt);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.houseInfoSituation(JsonEntityTransform.Object2Json(vo)));

		HouseInfoStaSit houseInfoStaSit = null;
		//增加可选房屋守则判断,对可选信息状态进行赋值		
		List<HouseConfMsgEntity> listFindByFidAndCode =null;
		HouseConfParamsDto paramsDto = new HouseConfParamsDto();
		paramsDto.setHouseBaseFid(houseBaseFid);
		paramsDto.setRoomFid(houseRoomFid);
		paramsDto.setRentWay(rentWayInt);
		paramsDto.setDicCode(ProductRulesEnum.ProductRulesEnum0024.getValue());		
		String houseRulesJson = houseIssueService.findHouseConfValidList(JsonEntityTransform.Object2Json(paramsDto));		
		DataTransferObject houseRulesDto = JsonEntityTransform.json2DataTransferObject(houseRulesJson);
		if (houseRulesDto.getCode() == DataTransferObject.SUCCESS) {
			listFindByFidAndCode = SOAResParseUtil.getListValueFromDataByKey(houseRulesJson, "list", HouseConfMsgEntity.class);
		}else{
			LogUtil.error(LOGGER, "troyHouseMgtService.findHouseConfValidList查询房屋守则失败,params={}",JsonEntityTransform.Object2Json(paramsDto));
		}
		
		if(dto.getCode() == DataTransferObject.SUCCESS){
			houseInfoStaSit = dto.parseData("houseInfoStaSit", new TypeReference<HouseInfoStaSit>() {
			});
			if(listFindByFidAndCode.size() != 0){
				houseInfoStaSit.setExtStatusSit(RoomFinishStatusEnum.FINISH.getCode());
			}
		}
		if(houseInfoStaSit == null) {
			houseInfoStaSit = new HouseInfoStaSit();
			houseInfoStaSit.setExtStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
			houseInfoStaSit.setHouseStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
			houseInfoStaSit.setRoomStatusSit(RoomFinishStatusEnum.UN_FINISH.getCode());
		}
		request.setAttribute("houseInfoStaSit", houseInfoStaSit);
		//查询房源扩展信息
		String houseBaseExtJson=houseIssueService.getHouseBaseExtByHouseBaseFid(houseBaseFid);
		HouseBaseExtEntity houseBaseExtEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "houseBaseExt", HouseBaseExtEntity.class);
		request.setAttribute("houseBaseExt", houseBaseExtEntity);
		return "/house/upHouseBase";
	}

	/**
	 * 
	 * ajax 方式修改房源类型
	 *
	 * @author jixd
	 * @created 2016年6月16日 下午1:23:00
	 *
	 * @param houseBaseMsgEntity
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateHouseType")
	@ResponseBody
	public DataTransferObject updateHouseType(HouseBaseMsgEntity houseBaseMsgEntity){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("参数为空");
			return dto;
		}
		try{
			String resultJson = houseIssueService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsgEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return dto;
		}catch(Exception e){
			LogUtil.error(LOGGER, "更新房源类型异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("更新异常");
		}
		return dto;
	}

	/**
	 * 
	 * 发布房源添加商机
	 *
	 * @author bushujie
	 * @created 2016年7月11日 下午10:35:04
	 *
	 * @param houseBaseFid
	 * @param landlordUid
	 * @throws SOAParseException
	 */
	@Deprecated // modified by liujun 2017年2月21日
	private void houseBusinessInput(String houseBaseFid,String landlordUid) throws SOAParseException{
		String custJson=customerInfoService.getCustomerInfoByUid(landlordUid);
		CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(custJson, "customerBase", CustomerBaseMsgEntity.class);
		if(!Check.NuNObj(customer)&&!Check.NuNStr(customer.getCustomerMobile())){
			HouseBusinessMsgExtDto dto=new HouseBusinessMsgExtDto();
			dto.setLandlordMobile(customer.getCustomerMobile());
			String businessJson=houseBusinessService.findHouseBusExtByCondition(JsonEntityTransform.Object2Json(dto));
			List<HouseBusinessMsgExtEntity> listExtEntities=SOAResParseUtil.getListValueFromDataByKey(businessJson, "listExtEntities", HouseBusinessMsgExtEntity.class);
			String houseBaseJson=houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
			HouseBaseMsgEntity houseBase=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseMsgEntity.class);
			String housePhyJson=houseIssueService.searchHousePhyMsgByHouseBaseFid(houseBaseFid);
			HousePhyMsgEntity housePhy=SOAResParseUtil.getValueFromDataByKey(housePhyJson, "obj", HousePhyMsgEntity.class);
			if(!Check.NuNCollection(listExtEntities)){
				HouseBusinessInputDto inputDto=new HouseBusinessInputDto();
				//商机基本信息
				inputDto.getBusinessMsg().setNationCode(housePhy.getNationCode());
				inputDto.getBusinessMsg().setProvinceCode(housePhy.getProvinceCode());
				inputDto.getBusinessMsg().setCityCode(housePhy.getCityCode());
				inputDto.getBusinessMsg().setAreaCode(housePhy.getAreaCode());
				inputDto.getBusinessMsg().setCommunityName(housePhy.getCommunityName());
				inputDto.getBusinessMsg().setHouseAddr(houseBase.getHouseAddr());
				inputDto.getBusinessMsg().setHouseBaseFid(houseBaseFid);
				inputDto.getBusinessMsg().setRentWay(houseBase.getRentWay());
				inputDto.getBusinessMsg().setCreateFid(landlordUid);
				//商机来源信息
				inputDto.getBusinessSource().setBusniessSource(1);
				inputDto.getBusinessSource().setIsJobArea(1);
				inputDto.getBusinessSource().setCreateFid(landlordUid);
				//商机扩展信息
				inputDto.getBusinessExt().setLandlordName(listExtEntities.get(0).getLandlordName());
				inputDto.getBusinessExt().setLandlordMobile(listExtEntities.get(0).getLandlordMobile());
				inputDto.getBusinessExt().setLandlordNickName(listExtEntities.get(0).getLandlordNickName());
				inputDto.getBusinessExt().setLandlordQq(listExtEntities.get(0).getLandlordQq());
				inputDto.getBusinessExt().setLandlordType(listExtEntities.get(0).getLandlordType());
				inputDto.getBusinessExt().setLandlordEmail(listExtEntities.get(0).getLandlordEmail());
				inputDto.getBusinessExt().setLandlordWechat(listExtEntities.get(0).getLandlordWechat());
				inputDto.getBusinessExt().setDtGuardCode(listExtEntities.get(0).getDtGuardCode());
				inputDto.getBusinessExt().setDtGuardName(listExtEntities.get(0).getDtGuardName());
				inputDto.getBusinessExt().setManagerCode(listExtEntities.get(0).getManagerCode());
				inputDto.getBusinessExt().setManagerName(listExtEntities.get(0).getManagerName());
				inputDto.getBusinessExt().setIsMeet(listExtEntities.get(0).getIsMeet());
				inputDto.getBusinessExt().setBusinessPlan(3);
				houseBusinessService.insertHouseBusiness(JsonEntityTransform.Object2Json(inputDto));
			}
			
			//从发布到上线 任何一个环节都不给管家发消息了
			/*if(!Check.NuNObj(houseBase) && !Check.NuNObj(houseBase.getHouseSn())){
				this.sendMsgTohouseGuard(houseBaseFid, customer.getRealName(), 
						houseBase.getHouseName(),houseBase.getRentWay(),houseBase.getHouseSn());
			}*/
		
		}
	}
}
