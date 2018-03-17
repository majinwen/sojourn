package com.ziroom.minsu.mapp.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityFullEntity;
import com.ziroom.minsu.entity.cms.ActivityGroupEntity;
import com.ziroom.minsu.entity.evaluate.EvaluateOrderEntity;
import com.ziroom.minsu.entity.evaluate.LandlordEvaluateEntity;
import com.ziroom.minsu.entity.evaluate.StatsHouseEvaEntity;
import com.ziroom.minsu.entity.house.TonightDiscountEntity;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.mapp.common.abs.AbstractController;
import com.ziroom.minsu.mapp.common.constant.MappConst;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.header.Header;
import com.ziroom.minsu.mapp.common.logic.ParamCheckLogic;
import com.ziroom.minsu.mapp.common.logic.ValidateResult;
import com.ziroom.minsu.mapp.house.service.HouseService;
import com.ziroom.minsu.mapp.house.vo.HouseEvaluate;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.cms.api.inner.ActivityFullService;
import com.ziroom.minsu.services.cms.api.inner.ActivityGroupService;
import com.ziroom.minsu.services.cms.entity.CouponItemVo;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.DecimalCalculate;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluateRequest;
import com.ziroom.minsu.services.evaluate.dto.StatsHouseEvaRequest;
import com.ziroom.minsu.services.evaluate.entity.TenantEvaluateVo;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.HousePriorityDto;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseDetailNewVo;
import com.ziroom.minsu.services.house.entity.HousePriorityVo;
import com.ziroom.minsu.services.house.entity.HouseTonightPriceInfoVo;
import com.ziroom.minsu.services.house.entity.StatsHouseEvaVo;
import com.ziroom.minsu.services.house.entity.TenantEvalVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.house.entity.ToNightDiscount;
import com.ziroom.minsu.services.order.api.inner.HouseCommonService;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.LandHouseRequest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.LocationTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.house.ConfPicMappingEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.RoomTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.search.LabelTipsEnum;
import com.ziroom.minsu.valenum.search.LabelTipsStyleEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005001001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005002001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005003001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>客端房源相关接口</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("tenantHouse")
public class TenantHouseController extends AbstractController{
	
	@Resource(name="house.tenantHouseService")
	private TenantHouseService tenantHouseService;
	
	@Resource(name="evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;
	
	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name="mapp.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;
	
	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name = "search.searchServiceApi")
    private SearchService searchService;
	
	@Resource(name="mapp.houseService")
	private HouseService houseService;
	
	@Resource(name = "order.houseCommonService")
	private HouseCommonService houseCommonService;

	@Resource(name="cms.activityFullService")
	private ActivityFullService activityFullService;

	@Resource(name = "cms.activityGroupService")
	private ActivityGroupService activityGroupService;
	
	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${list_small_pic}'.trim()}")
	private String list_small_pic;
	
	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;
	
	@Value("#{'${default_icon_size}'.trim()}")
	private String defaultIconSize;
	
	@Value("#{'${BAIDU_AK}'.trim()}")
	private String baiduAk;
	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TenantHouseController.class);

	/**
	 * 房源详情活动号
	 */
	private static final String GROUP_SN = "XQYLQ1701";
	
	/**
	 * 
	 * 房源详细信息
	 *
	 * @author liujun
	 * @created 2016年6月15日
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/houseDetail")
	public String houseDetail(HttpServletRequest request, HouseDetailDto houseDetailDto){
		DataTransferObject dto = new DataTransferObject();
		try {
			// pc端访问
			/*if (!HttpSourceUtil.check(request)) {
				return "redirect:http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer";
			}*/
			
			//判断是否是预览按钮跳转
			String sourceFrom=request.getParameter("sourceFrom");
			if(!Check.NuNStr(sourceFrom) && "previewBtn".equals(sourceFrom)){
				request.setAttribute("sourceFrom", sourceFrom);
			}
			
			//预览返回时需要房源fid和房间fid
			String houseBaseFid=request.getParameter("houseBaseFid");
			if(!Check.NuNStr(houseBaseFid)){
				request.setAttribute("houseBaseFid", houseBaseFid);
			}
			String houseRoomFid=request.getParameter("houseRoomFid");
			if(!Check.NuNStr(houseRoomFid)){
				request.setAttribute("houseRoomFid", houseRoomFid);
			}
			String rentWay=request.getParameter("rentWay");
			if(!Check.NuNStr(rentWay)){
				request.setAttribute("rentWay", rentWay);
			}
			
			String paramJson = JsonEntityTransform.Object2Json(houseDetailDto);
			LogUtil.info(LOGGER, "参数:{}", paramJson);
			
			ValidateResult<HouseDetailDto> validateResult =
	                paramCheckLogic.checkParamValidate(paramJson, HouseDetailDto.class);
	        if (!validateResult.isSuccess()) {
	        	LogUtil.info(LOGGER, "错误信息:{},参数:{}", validateResult.getDto().getMsg(), paramJson);
				return "error/error";
	        }
			
			//查询房源详情
			String resultJson = tenantHouseService.houseDetail(paramJson);
			
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "tenantHouseService#houseDetail接口调用失败,参数:{},结果:{}", paramJson, dto.toJsonString());
				return "error/error";
			}
			
			TenantHouseDetailVo tenantHouseDetailVo = dto.parseData("houseDetail", new TypeReference<TenantHouseDetailVo>() {});
			if(Check.NuNObj(tenantHouseDetailVo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_OR_ROOM_NULL));
				return "error/error";
			}
			
			tenantHouseDetailVo.setHouseDesc(StringUtils.filterSpecialCharacter(tenantHouseDetailVo.getHouseDesc(), StringUtils.specialKey_N));
			//设置房源浏览量
			tenantHouseService.statisticalPv(paramJson);
			
			// 默认图片处理
			tenantHouseDetailVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona,
					tenantHouseDetailVo.getDefaultPic(), detail_big_pic));
			
			// 图片列表处理
			if (!Check.NuNCollection(tenantHouseDetailVo.getPicList())) {
				for (String picUrl : tenantHouseDetailVo.getPicList()) {
					picUrl = PicUtil.getSpecialPic(picBaseAddrMona, picUrl, detail_big_pic);
				}
			}
			
			if (!Check.NuNCollection(tenantHouseDetailVo.getPicDisList())) {
				for (MinsuEleEntity entity : tenantHouseDetailVo.getPicDisList()) {
					entity.setEleValue(PicUtil.getSpecialPic(picBaseAddrMona, entity.getEleValue(), detail_big_pic));
				}
			}
            
			//查询评论数和星级判断
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			if (tenantHouseDetailVo.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				statsHouseEvaRequest.setHouseFid(tenantHouseDetailVo.getFid());
			} else if (tenantHouseDetailVo.getRentWay() == RentWayEnum.ROOM.getCode()) {
				statsHouseEvaRequest.setRoomFid(tenantHouseDetailVo.getFid());
			} else {
				LogUtil.error(LOGGER, "error:{}", MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_RENTWAY_ERROR));
			}
			
			statsHouseEvaRequest.setRentWay(tenantHouseDetailVo.getRentWay());
			String evaluateCountJson = evaluateOrderService.queryStatsHouseEvaByCondition(
					JsonEntityTransform.Object2Json(statsHouseEvaRequest));
			
			dto = JsonEntityTransform.json2DataTransferObject(evaluateCountJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "evaluateOrderService#queryStatsHouseEvaByCondition接口调用失败,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(statsHouseEvaRequest), dto.toJsonString());
			}
			
			List<StatsHouseEvaEntity> evaluateStats = dto.parseData("listStatsHouseEvaEntities",
					new TypeReference<List<StatsHouseEvaEntity>>() {});
			if(!Check.NuNCollection(evaluateStats)){
				LogUtil.info(LOGGER,"查询评论数结果:{}", JsonEntityTransform.Object2Json(evaluateStats));
				StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
				Float sumStar = (statsHouseEvaEntity.getHouseCleanAva() + statsHouseEvaEntity.getDesMatchAva()
						+ statsHouseEvaEntity.getSafeDegreeAva() + statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity
						.getCostPerforAva()) / 5;
				BigDecimal bigDecimal = new BigDecimal(sumStar.toString());
				tenantHouseDetailVo.setGradeStar(bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
				tenantHouseDetailVo.setEvaluateCount(statsHouseEvaEntity.getEvaTotal());
			}else {
				// 默认5星
				tenantHouseDetailVo.setGradeStar(new BigDecimal(5).floatValue());
				tenantHouseDetailVo.setEvaluateCount(0);
            }

			//押金规则名称
			String dicResultJson = cityTemplateService.getSelectEnum(null, tenantHouseDetailVo.getDepositRulesCode());
			dto = JsonEntityTransform.json2DataTransferObject(dicResultJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService#getSelectEnum接口调用失败,dicCode:{},结果:{}",
						tenantHouseDetailVo.getDepositRulesCode(), dto.toJsonString());
			}
			
			List<EnumVo> list = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			for (EnumVo vo : list) {
				if (vo.getKey().equals(tenantHouseDetailVo.getDepositRulesValue())) {
					tenantHouseDetailVo.setDepositRulesName(vo.getText());
					break;
				}
			}
			
			//房东信息获取
			String landlordJson = customerMsgManagerService.getCutomerVo(tenantHouseDetailVo.getLandlordUid());
			dto = JsonEntityTransform.json2DataTransferObject(landlordJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "customerMsgManagerService#getCutomerVo接口调用失败,landlordUid={},结果:{}",
						tenantHouseDetailVo.getLandlordUid(), dto.toJsonString());
			}
			
			CustomerVo landlord = dto.parseData("customerVo", new TypeReference<CustomerVo>() {});
			if(!Check.NuNObj(landlord)){
				tenantHouseDetailVo.setLandlordIcon(landlord.getUserPicUrl());
                //默认显示昵称，如果没有显示真实姓名
                if(Check.NuNStr(landlord.getNickName())){
                    tenantHouseDetailVo.setLandlordName(landlord.getRealName());
                }else {
                    tenantHouseDetailVo.setLandlordName(landlord.getNickName());
                }
                StringBuilder sb = new StringBuilder(landlord.getHostNumber());
                if(!Check.NuNStr(landlord.getZiroomPhone())){
                	sb.append(landlord.getZiroomPhone());
                }
				tenantHouseDetailVo.setLandlordMobile(sb.toString());
			}
			
			//获取评论信息
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			if (tenantHouseDetailVo.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				evaluateRequest.setHouseFid(tenantHouseDetailVo.getFid());
			} else if (tenantHouseDetailVo.getRentWay() == RentWayEnum.ROOM.getCode()) {
				evaluateRequest.setRoomFid(tenantHouseDetailVo.getFid());
			} else {
				LogUtil.error(LOGGER, "error:{}", MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_RENTWAY_ERROR));
			}
			evaluateRequest.setRentWay(tenantHouseDetailVo.getRentWay());
			evaluateRequest.setLimit(1);
			
			String evaluatePageJson = evaluateOrderService.queryTenantEvaluateByPage(
					JsonEntityTransform.Object2Json(evaluateRequest));
			dto = JsonEntityTransform.json2DataTransferObject(evaluatePageJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "evaluateOrderService#queryTenantEvaluateByPage接口调用失败,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(evaluateRequest), dto.toJsonString());
			}
			
			List<TenantEvaluateVo> evaList = dto.parseData("listTenantEvaluateVo", new TypeReference<List<TenantEvaluateVo>>() {});
			if(!Check.NuNCollection(evaList)){
				TenantEvalVo tenantEvalVo = new TenantEvalVo();
				// 以后获取用户名
				String customerJson = customerMsgManagerService.getCutomerVo(evaList.get(0).getEvaUserUid());
				dto = JsonEntityTransform.json2DataTransferObject(customerJson);
				//判断服务是否有错误
				if(dto.getCode()==DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "customerMsgManagerService#getCutomerVo接口调用失败,evaUserUid={},结果:{}",
							evaList.get(0).getEvaUserUid(), dto.toJsonString());
				}
				CustomerVo customer = dto.parseData("customerVo", new TypeReference<CustomerVo>() {});
				if (!Check.NuNObj(customer)) {
					tenantEvalVo.setCustomerName(customer.getRealName());
				}
				tenantEvalVo.setEvalContent(evaList.get(0).getContent());
				tenantEvalVo.setEvalDate(DateUtil.dateFormat(evaList.get(0).getCreateTime()));
				tenantEvalVo.setCustomerIcon(customer.getUserPicUrl());
				tenantHouseDetailVo.setTenantEvalVo(tenantEvalVo);
			}
			
			//组装床信息
			String bedSizeJson = cityTemplateService.getSelectEnum(null,
					ProductRulesEnum.ProductRulesEnum006.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(bedSizeJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService#getSelectEnum接口调用失败,dicCode:{},结果:{}",
						ProductRulesEnum.ProductRulesEnum006.getValue(), dto.toJsonString());
			}
			List<EnumVo> bedSizelist = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			
			String bedTypeJson = cityTemplateService.getSelectEnum(null,
					ProductRulesEnum.ProductRulesEnum005.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(bedTypeJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService#getSelectEnum接口调用失败,dicCode:{},结果:{}",
						ProductRulesEnum.ProductRulesEnum005.getValue(), dto.toJsonString());
			}
			List<EnumVo> bedTypelist = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			
			if (!Check.NuNCollection(tenantHouseDetailVo.getBedList())) {
				for (HouseBedNumVo bedVo : tenantHouseDetailVo.getBedList()) {
					for (EnumVo enumVo : bedSizelist) {
						if (enumVo.getKey().equals(bedVo.getBedSize().toString())) {
							bedVo.setBedSizeName(enumVo.getText());
							break;
						}
					}
					for (EnumVo enumVo : bedTypelist) {
						if (enumVo.getKey().equals(bedVo.getBedType().toString())) {
							bedVo.setBedTypeName(enumVo.getText());
							break;
						}
					}
				}
			}
			
			//配套设施组装
			String facilityJson = cityTemplateService.getSelectSubDic(null,
					ProductRulesEnum.ProductRulesEnum002.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(facilityJson);
			//判断服务是否有错误
			if(dto.getCode()==DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "cityTemplateService#getSelectSubDic接口调用失败,dicCode:{},结果:{}",
						ProductRulesEnum.ProductRulesEnum002.getValue(), dto.toJsonString());
			}
			List<EnumVo> facilitylist = dto.parseData("subDic", new TypeReference<List<EnumVo>>() {});
			for (HouseConfVo vo : tenantHouseDetailVo.getFacilityList()) {
				for (EnumVo enumVo : facilitylist) {
					if (enumVo.getKey().equals(vo.getDicCode())) {
						for (EnumVo valVo : enumVo.getSubEnumVals()) {
							if (valVo.getKey().equals(vo.getDicValue())) {
								vo.setDicName(valVo.getText());
								break;
							}
						}
						break;
					}
				}
			}
			//服务
			String serveJson = cityTemplateService
					.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0015.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(serveJson);
			//判断服务是否有错误
			if(dto.getCode()==DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "cityTemplateService#getSelectEnum接口调用失败,dicCode:{},结果:{}",
						ProductRulesEnum.ProductRulesEnum0015.getValue(), dto.toJsonString());
			}
			List<EnumVo> servelist = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			for (HouseConfVo vo : tenantHouseDetailVo.getServeList()) {
				for (EnumVo enumVo : servelist) {
					if (enumVo.getKey().equals(vo.getDicValue())) {
						vo.setDicName(enumVo.getText());
						break;
					}
				}
			}
			
			HouseDetailNewVo houseDetailNewVo = new HouseDetailNewVo();
			
			//房源的夹心价格设置
			setHousePriorityDate(tenantHouseDetailVo,houseDetailNewVo,houseDetailDto);
			
			//设置今夜特价价格
			TonightDiscountEntity houseDiscountEntity= new TonightDiscountEntity();
			setTonigDiscount(tenantHouseDetailVo, houseDiscountEntity, houseDetailDto);
			
			//地址处理
			tenantHouseDetailVo.setHouseAddr(tenantHouseDetailVo.getHouseAddr().split(" ")[0]);
			//退订政策组装
			if(!Check.NuNStr(tenantHouseDetailVo.getCheckOutRulesCode())){
				//适中退订
				if(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue().equals(tenantHouseDetailVo.getCheckOutRulesCode())){
					String tradeRulesEnum005002001001Json=cityTemplateService.getTextValue(null,TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.getValue());
					String tradeRulesEnum005002001001Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005002001001Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText1(Integer.valueOf(tradeRulesEnum005002001001Value));
					String tradeRulesEnum005002001002Json=cityTemplateService.getTextValue(null,TradeRulesEnum005002001Enum.TradeRulesEnum005002001002.getValue());
					String tradeRulesEnum005002001002Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005002001002Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText2("如取消订单，扣除"+tradeRulesEnum005002001002Value+"倍首日房租");
					String tradeRulesEnum005002001003Json=cityTemplateService.getTextValue(null,TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.getValue());
					String tradeRulesEnum005002001003Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005002001003Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText3("如提前退房，扣除"+tradeRulesEnum005002001003Value+"倍首日房租");
				}
				//严格退订
				if(TradeRulesEnum005Enum.TradeRulesEnum005001.getValue().equals(tenantHouseDetailVo.getCheckOutRulesCode())){
					String tradeRulesEnum005001001001Json=cityTemplateService.getTextValue(null,TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.getValue());
					String tradeRulesEnum005001001001Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005001001001Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText1(Integer.valueOf(tradeRulesEnum005001001001Value));
					String tradeRulesEnum005001001002Json=cityTemplateService.getTextValue(null,TradeRulesEnum005001001Enum.TradeRulesEnum005001001002.getValue());
					String tradeRulesEnum005001001002Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005001001002Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText2("如取消订单，扣除"+tradeRulesEnum005001001002Value+"倍首日房租");
					String tradeRulesEnum005001001003Json=cityTemplateService.getTextValue(null,TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.getValue());
					String tradeRulesEnum005001001003Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005001001003Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText3("如提前退房，扣除"+tradeRulesEnum005001001003Value+"倍首日房租");
				}
				//灵活退订
				if(TradeRulesEnum005Enum.TradeRulesEnum005003.getValue().equals(tenantHouseDetailVo.getCheckOutRulesCode())){
					String tradeRulesEnum005003001001Json=cityTemplateService.getTextValue(null,TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.getValue());
					String tradeRulesEnum005003001001Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005003001001Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText1(Integer.valueOf(tradeRulesEnum005003001001Value));
					String tradeRulesEnum005003001002Json=cityTemplateService.getTextValue(null,TradeRulesEnum005003001Enum.TradeRulesEnum005003001002.getValue());
					String tradeRulesEnum005003001002Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005003001002Json, "textValue", String.class);
					tenantHouseDetailVo.setUnregText2("如取消订单，扣除"+tradeRulesEnum005003001002Value+"倍首日房租");
					String tradeRulesEnum005003001003Json=cityTemplateService.getTextValue(null,TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.getValue());
					String tradeRulesEnum005003001003Value=SOAResParseUtil.getValueFromDataByKey(tradeRulesEnum005003001003Json, "textValue", String.class);;	
					tenantHouseDetailVo.setUnregText3("如提前退房，扣除"+tradeRulesEnum005003001003Value+"倍首日房租");
				}
			}
			
			Map<String, Object> mainMap = new LinkedHashMap<String, Object>();
			if(!Check.NuNObj(tenantHouseDetailVo.getRoomNum()) && tenantHouseDetailVo.getRoomNum() != 0){
				mainMap.put("fangjian", new StringBuilder().append(tenantHouseDetailVo.getRoomNum())
						.append("房间").toString());
			}
			if(!Check.NuNCollection(tenantHouseDetailVo.getBedList())){
				Integer bedNum = 0;
				for (HouseBedNumVo bed : tenantHouseDetailVo.getBedList()) {
					bedNum += bed.getBedNum();
				}
				mainMap.put("chuang", new StringBuilder().append(bedNum)
						.append("张床"));
			}
			if(tenantHouseDetailVo.getIstoilet() != 0){
				mainMap.put("weishengjian", new StringBuilder("独卫").toString());
			}
			if (tenantHouseDetailVo.getIstoilet() == 0 && !Check.NuNObj(tenantHouseDetailVo.getToiletNum())
					&& tenantHouseDetailVo.getToiletNum() != 0) {
				mainMap.put("weishengjian", new StringBuilder().append(tenantHouseDetailVo.getToiletNum())
						.append("卫").toString());
			}
			
			Map<String, String> electricMap = new LinkedHashMap<String, String>();
			Map<String, String> bathroomMap = new LinkedHashMap<String, String>();
			Map<String, String> facilityMap = new LinkedHashMap<String, String>();
			if (!Check.NuNCollection(tenantHouseDetailVo.getFacilityList())) {
				for (HouseConfVo vo : tenantHouseDetailVo.getFacilityList()) {
					if (ConfPicMappingEnum.getTotalMap().containsKey(vo.getDicPic())) {
						mainMap.put(ConfPicMappingEnum.getTotalMap().get(vo.getDicPic()), vo.getDicName());
					}
					if (ConfPicMappingEnum.ELECTRIC.getMapping().containsKey(vo.getDicPic())) {
						electricMap.put(ConfPicMappingEnum.ELECTRIC.getMapping().get(vo.getDicPic()), vo.getDicName());
					}
					if (ConfPicMappingEnum.BATHROOM.getMapping().containsKey(vo.getDicPic())) {
						bathroomMap.put(ConfPicMappingEnum.BATHROOM.getMapping().get(vo.getDicPic()), vo.getDicName());
					}
					if (ConfPicMappingEnum.FACILITY.getMapping().containsKey(vo.getDicPic())) {
						facilityMap.put(ConfPicMappingEnum.FACILITY.getMapping().get(vo.getDicPic()), vo.getDicName());
					}
				}
			}
			
			/*LandHouseRequest landRequest = new LandHouseRequest();
			BeanUtils.copyProperties(houseDetailDto, landRequest);
			landRequest.setLandlordUid(tenantHouseDetailVo.getLandlordUid());
			this.landList(request, landRequest);*/
			
			request.setAttribute("baiduAk", baiduAk);
			request.setAttribute("mainMap", mainMap);
			request.setAttribute("displaMore", !Check.NuNCollection(tenantHouseDetailVo.getFacilityList()));
			request.setAttribute("electricJson", JsonEntityTransform.Object2Json(electricMap));
			request.setAttribute("bathroomJson", JsonEntityTransform.Object2Json(bathroomMap));
			request.setAttribute("facilityJson", JsonEntityTransform.Object2Json(facilityMap));
			request.setAttribute("serviceConfMap", ConfPicMappingEnum.SERVICE.getMapping());
			request.setAttribute("houseDetailVo", tenantHouseDetailVo);
            //优惠券展示
            request.setAttribute("isCouponShow",showCouponMsg(request));

            /**
             * 增加共享客厅校验
             */
            if (tenantHouseDetailVo.getRentWay() == RentWayEnum.ROOM.getCode() && !Check.NuNObj(tenantHouseDetailVo.getRoomType()) && tenantHouseDetailVo.getRoomType() == RoomTypeEnum.HALL_TYPE.getCode()) {
                tenantHouseDetailVo.setRentWayName(RentWayEnum.HALL.getName());
            }
            
			LogUtil.debug(LOGGER, "返回结果:{}", JsonEntityTransform.Object2Json(tenantHouseDetailVo));
			
			if(!Check.NuNStr(rentWay)&&Integer.valueOf(rentWay) == RentWayEnum.ROOM.getCode()){
				request.setAttribute("isTogetherLandlord", tenantHouseDetailVo.getIsTogetherLandlord()==0?"不与房东同住\n":"与房东同住\n");
			}
			
			try {
				Header header = getHeader(request);
				String uid = getUserId(request);
				houseService.saveLocation(uid,header,getIpAddress(request), LocationTypeEnum.SHARE,houseDetailDto.getFid(),houseDetailDto.getRentWay());
			}catch (Exception e){
				LogUtil.error(LOGGER, "收藏房源保存用户位置信息异常，error = {}", e);
			}
			
		} catch (Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
		}
		return "share/houseDetail";
	}

	/**
	 * 展示优惠券列表
	 * @author jixd
	 * @created 2017年06月19日 14:45:54
	 * @param
	 * @return
	 */
	private int showCouponMsg(HttpServletRequest request) {
		int isShow = 1;
		try{
            String resultJson = activityGroupService.getGroupBySN(GROUP_SN);
            DataTransferObject dto = new DataTransferObject();
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if (resultDto.getCode() == DataTransferObject.ERROR){
                isShow = 0;
                return isShow;
            }
            ActivityGroupEntity groupEntity = resultDto.parseData("obj", new TypeReference<ActivityGroupEntity>() {});
            if (Check.NuNObj(groupEntity)){
                isShow = 0;
                return isShow;
            }
            //活动未开启
            if (groupEntity.getIsValid() == YesOrNoEnum.NO.getCode()){
                isShow = 0;
                return isShow;
            }

            DataTransferObject listDto = JsonEntityTransform.json2DataTransferObject(activityFullService.listActivityFullByGroupSn(GROUP_SN));
            if (listDto.getCode() == DataTransferObject.ERROR){
                isShow = 0;
                return isShow;
            }
            List<ActivityFullEntity> list = listDto.parseData("list", new TypeReference<List<ActivityFullEntity>>() {});
            if (Check.NuNCollection(list)){
                isShow = 0;
                return isShow;
            }

            Collections.sort(list, new Comparator<ActivityEntity>() {
                @Override
                public int compare(ActivityEntity o1, ActivityEntity o2) {
                    return o1.getActCut() - o2.getActCut();
                }
            });

            List<CouponItemVo> couponList = new ArrayList<>();
            List<String> barList = new ArrayList<>();

            for (ActivityFullEntity activityEntity : list){
                CouponItemVo couponItemVo = new CouponItemVo();
                couponItemVo.setActSn(activityEntity.getActSn());
                couponItemVo.setMoney(String.valueOf(BigDecimalUtil.div(activityEntity.getActCut(),100.0,1)));
                couponItemVo.setDesc(String.format(MappConst.ACT_COUPON_DETAIL_MSG,activityEntity.getActLimit()/100,activityEntity.getCouponTimeLast()));
                couponList.add(couponItemVo);

                barList.add(String.format(MappConst.ACT_COUPON_LITTLE_MSG, activityEntity.getActLimit()/100, activityEntity.getActCut()/100));
            }
            request.setAttribute("barList",barList);
            request.setAttribute("couponList",couponList);
        }catch (Exception e){
		    LogUtil.error(LOGGER,"分享房源详情 查询优惠券异常e={}",e);
		    isShow = 0;
        }
		return isShow;
	}
	
	
	
	/**
	 * 设置今夜特价价格(分享页面)
	 *
	 * @author baiwei
	 * @created 2017年5月17日 下午2:08:48
	 *
	 * @param tenantHouseDetailVo
	 * @param houseDiscountEntity 
	 * @param houseDetailDto
	 */
	private void setTonigDiscount(TenantHouseDetailVo tenantHouseDetailVo,TonightDiscountEntity houseDiscountEntity, HouseDetailDto houseDetailDto) {
		if(!Check.NuNObj(houseDetailDto)){
			int rentWay = houseDetailDto.getRentWay();
			try {
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					houseDiscountEntity.setHouseFid(houseDetailDto.getFid());
				}
				if(rentWay == RentWayEnum.ROOM.getCode()){
					houseDiscountEntity.setRoomFid(houseDetailDto.getFid());
				}
				houseDiscountEntity.setRentWay(rentWay);
				String paramJson = JsonEntityTransform.Object2Json(houseDiscountEntity);
				String result = houseCommonService.getEffectiveOfJYTJInfo(paramJson);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "获取当前房源今夜特价信息失败,参数paramDto={},msg={}", paramJson,dto.getMsg());
					return;
				}
				HouseTonightPriceInfoVo houseTonightPriceInfoVo = SOAResParseUtil.getValueFromDataByKey(result, "data", HouseTonightPriceInfoVo.class);
				if(!Check.NuNObj(houseTonightPriceInfoVo)){
					//今夜特价未开始
					if(!Check.NuNObj(houseTonightPriceInfoVo.getCountdownBegin())&&houseTonightPriceInfoVo.getCountdownBegin()>0){
						tenantHouseDetailVo.setToNightDiscountStatus(0);
					}
					//今夜特价已生效
					if(houseTonightPriceInfoVo.isEffective()){	
						tenantHouseDetailVo.setToNightDiscountStatus(1);
					}
					ToNightDiscount toNightDiscount=new ToNightDiscount();
					toNightDiscount.setOpenTime(DateUtil.dateFormat(houseTonightPriceInfoVo.getStartTime(), "HH:mm"));
					toNightDiscount.setTonightPrice(DecimalCalculate.mul(tenantHouseDetailVo.getHousePrice().toString(), houseTonightPriceInfoVo.getDiscount()+"").intValue());
					tenantHouseDetailVo.setToNightDiscount(toNightDiscount);
					if(!Check.NuNObj(houseTonightPriceInfoVo.getStartTime())){
						tenantHouseDetailVo.setToNightDiscountStartTime(houseTonightPriceInfoVo.getStartTime().getTime());
					}
					if(!Check.NuNObj(houseTonightPriceInfoVo.getEndTime())){
						tenantHouseDetailVo.setToNightDiscountEndTime(houseTonightPriceInfoVo.getEndTime().getTime());
					}
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "获取房源今日特价异常,e:{}", e);
			}
		}
		
	}



	/**
	 * 
	 * 设置房源夹心价格
	 *
	 * @author yd
	 * @created 2016年12月8日 上午10:29:14
	 * @param tenantHouseDetailVo
	 * @param houseDetailNewVo
	 */
	private void setHousePriorityDate(TenantHouseDetailVo tenantHouseDetailVo, HouseDetailNewVo houseDetailNewVo, HouseDetailDto paramDto){

		if(!Check.NuNObj(tenantHouseDetailVo)&&!Check.NuNObj(paramDto)){

			int rentWay = paramDto.getRentWay();
			String startTimeParam = paramDto.getStartTime();
			String endTimeParam = paramDto.getEndTime();
			try {
				HousePriorityDto housePriorityDt = new HousePriorityDto();
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					housePriorityDt.setHouseBaseFid(paramDto.getFid());
				}
				if(rentWay == RentWayEnum.ROOM.getCode()){
					housePriorityDt.setHouseRoomFid(paramDto.getFid());
				}

				housePriorityDt.setRentWay(rentWay);
				housePriorityDt.setNowDate(new Date());
				
				Date tillDate = tenantHouseDetailVo.getTillDate();
				
				Date curDate = new Date();
				//是否填写日期
				boolean isW = true;
				if(Check.NuNStr(startTimeParam)){
					//没有设时间默认当天
					startTimeParam = DateUtil.dateFormat(curDate);
					paramDto.setStartTime(startTimeParam);
					isW = false;
				}
				String todayStr = DateUtil.dateFormat(curDate,"yyyy-MM-dd");

				//查看搜索时间间隔 判断显示优惠规则
				int searchDayCount = 0;
				if (!Check.NuNStr(startTimeParam) && !Check.NuNStr(endTimeParam)){
					Date startTime = DateUtil.parseDate(startTimeParam,"yyyy-MM-dd");
					Date endTime =  DateUtil.parseDate(endTimeParam,"yyyy-MM-dd");
					searchDayCount = DateSplitUtil.countDateSplit(startTime,endTime);
				}else if (!Check.NuNStr(startTimeParam) && Check.NuNStr(endTimeParam)){ 
					searchDayCount = 1;
				} 

				Date priceDate = DateUtil.parseDate(paramDto.getStartTime(), "yyyy-MM-dd");
				
				//日期往前 推2天  搜索日期范围 只能是 从当前时间  到 截至时间  如果 不是 默认是当前时间
				Date endDate =  DateSplitUtil.jumpDate(priceDate, ProductRulesEnum020.ProductRulesEnum020003.getDayNum()+1);
				
				housePriorityDt.setStartDate(curDate);
				housePriorityDt.setEndDate(endDate);
				housePriorityDt.setTillDate(tillDate);
				
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.houseCommonService.findPriorityDate(JsonEntityTransform.Object2Json(housePriorityDt)));

				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "查看日历,夹心价格获取失败,参数paramDto={},msg={}", JsonEntityTransform.Object2Json(paramDto),dto.getMsg());
					return ;
				}

				Map<String,HousePriorityVo> housePriorityMap  = dto.parseData("housePriorityMap", new TypeReference<Map<String,HousePriorityVo> >() {
				});
				if(!Check.NuNMap(housePriorityMap)){
                    LogUtil.info(LOGGER,"获取夹心价格集合={}", JsonEntityTransform.Object2Json(housePriorityMap));
					HousePriorityVo housePriorityVo = housePriorityMap.get(DateUtil.dateFormat(priceDate, "yyyy-MM-dd"));
					LogUtil.info(LOGGER, "房源详情价格,房源fid={},rentWay={},当前价格price={}", paramDto.getFid(),rentWay,tenantHouseDetailVo.getHousePrice());
					boolean isChangePrice = false;
					if(!Check.NuNObj(housePriorityVo)){

						if ( !Check.NuNObj(houseDetailNewVo)){
							List<LabelTipsEntity> labelTipsList = houseDetailNewVo.getLabelTipsList();
							int index = labelTipsList.size();
							if (housePriorityVo.getPriorityCode().equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())){
								//满足今日特惠条件
								if (todayStr.equals(startTimeParam) && searchDayCount == 1){
									LogUtil.info(LOGGER,"满足今日特惠");
									LabelTipsEntity labelEntity = new LabelTipsEntity();
									labelEntity.setIndex(++index);
									labelEntity.setName(LabelTipsEnum.IS_TODAY_DISCOUNT.getName());
									labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
									labelTipsList.add(labelEntity);
									houseDetailNewVo.setOriginalPrice(tenantHouseDetailVo.getHousePrice());
									isChangePrice = true;
								}
							}

							if (isW && housePriorityVo.getPriorityCode().equals(ProductRulesEnum020.ProductRulesEnum020002.getValue())){
								if (searchDayCount == 1){
									LogUtil.info(LOGGER,"满足灵活定价一天");
									LabelTipsEntity labelEntity = new LabelTipsEntity();
									labelEntity.setIndex(++index);
									labelEntity.setName(LabelTipsEnum.IS_JIAXIN_DISCOUNT1.getName());
									labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
									labelTipsList.add(labelEntity);
									houseDetailNewVo.setOriginalPrice(tenantHouseDetailVo.getHousePrice());
									isChangePrice = true;
								}
							}

							if (isW && housePriorityVo.getPriorityCode().equals(ProductRulesEnum020.ProductRulesEnum020003.getValue())){
								if (searchDayCount == 2 || searchDayCount ==1){
									LogUtil.info(LOGGER,"满足灵活定价两天");
									LabelTipsEntity labelEntity = new LabelTipsEntity();
									labelEntity.setIndex(++index);
									labelEntity.setName(LabelTipsEnum.IS_JIAXIN_DISCOUNT2.getName());
									labelEntity.setTipsType(LabelTipsStyleEnum.ONLY_WORDS.getCode());
									labelTipsList.add(labelEntity);
									houseDetailNewVo.setOriginalPrice(tenantHouseDetailVo.getHousePrice());
									isChangePrice = true;
								}
							}
                            houseDetailNewVo.setLabelTipsList(labelTipsList);
						}
						//LogUtil.info(LOGGER,"返回结果result={}",JsonEntityTransform.Object2Json(houseDetailNewVo));
						if (isChangePrice || Check.NuNStr(startTimeParam) || todayStr.equals(startTimeParam)){
							tenantHouseDetailVo.setHousePrice(DataFormat.getPriorityPrice(housePriorityVo.getPriorityDiscount(), tenantHouseDetailVo.getHousePrice()));
						}
						LogUtil.info(LOGGER, "房源详情价格,夹心后价格priorityPrice={}",tenantHouseDetailVo.getHousePrice());
					}
				}

			} catch (Exception e) {
				LogUtil.error(LOGGER, "查看房源详情-夹心价格设置失败paramDto={},e={}", JsonEntityTransform.Object2Json(paramDto),e);
			}

		}
	}
	
	
	
	/**
     * 房源的搜索
     * 
     * @param request
     * @param response
     * @return
     */
    public void landList(HttpServletRequest request, LandHouseRequest landRequest) {
        //参数校验
        if(Check.NuNStr(landRequest.getLandlordUid())){
			LogUtil.error(LOGGER, "参数:{}",
					MessageSourceUtil.getChinese(messageSource, MappMessageConst.LANDLORDUID_NULL));
			return;
        }
        try {
            //获取搜索结果
			String resultJson = searchService.getLandHouseList(defaultIconSize,
					JsonEntityTransform.Object2Json(landRequest));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            if(dto.getCode() == DataTransferObject.ERROR){
            	LogUtil.error(LOGGER, "searchService#getLandHouseList调用接口失败,参数:{},结果:{}", 
            			JsonEntityTransform.Object2Json(landRequest), dto.toJsonString());
            }
            Integer total = Integer.valueOf(dto.getData().get("total").toString());
            List<Object> list =  dto.parseData("list", new TypeReference<List<Object>>() {});
            request.setAttribute("total", total);
            request.setAttribute("list", list);
            LogUtil.info(LOGGER, "返回结果:{}", JsonEntityTransform.Object2Json(list));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "landList par :{} e:{}", JsonEntityTransform.Object2Json(landRequest), e);
        }
    }
	
	/**
	 * 
	 * 房源评价列表
	 *
	 * @author liujun
	 * @created 2016年6月15日
	 *
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/houseEvaluate")
	@ResponseBody
	public DataTransferObject houseEvaluate(HouseEvaluate houseEvaluate){
		DataTransferObject dto = new DataTransferObject();
		try {
			String paramJson = JsonEntityTransform.Object2Json(houseEvaluate);
			LogUtil.info(LOGGER, "参数:{}", paramJson);

			ValidateResult<HouseEvaluate> validateResult =
	                paramCheckLogic.checkParamValidate(paramJson, HouseEvaluate.class);
	        if (!validateResult.isSuccess()) {
	        	LogUtil.error(LOGGER, "错误信息:{}", validateResult.getDto().getMsg());
				return validateResult.getDto();
	        }

			// 评价综合信息查询
			StatsHouseEvaRequest statsHouseEvaRequest = new StatsHouseEvaRequest();
			if (houseEvaluate.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				statsHouseEvaRequest.setHouseFid(houseEvaluate.getFid());
			} else if (houseEvaluate.getRentWay() == RentWayEnum.ROOM.getCode()) {
				statsHouseEvaRequest.setRoomFid(houseEvaluate.getFid());
			} else {
				LogUtil.error(LOGGER, "error:{}",
						MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_RENTWAY_ERROR));
			}
			statsHouseEvaRequest.setRentWay(houseEvaluate.getRentWay());
			String evaluateCountJson = evaluateOrderService.queryStatsHouseEvaByCondition(
					JsonEntityTransform.Object2Json(statsHouseEvaRequest));
			
			dto = JsonEntityTransform.json2DataTransferObject(evaluateCountJson);
			//判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "evaluateOrderService#queryStatsHouseEvaByCondition接口调用失败,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(statsHouseEvaRequest), dto.toJsonString());
				return dto;
			}
			
			List<StatsHouseEvaEntity> evaluateStats = dto.parseData("listStatsHouseEvaEntities",
					new TypeReference<List<StatsHouseEvaEntity>>() {});
			// 定义返回参数
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if (!Check.NuNCollection(evaluateStats)) {
				StatsHouseEvaEntity statsHouseEvaEntity = evaluateStats.get(0);
				Float sumStar = (statsHouseEvaEntity.getHouseCleanAva() + statsHouseEvaEntity.getDesMatchAva()
						+ statsHouseEvaEntity.getSafeDegreeAva() + statsHouseEvaEntity.getTrafPosAva() + statsHouseEvaEntity
						.getCostPerforAva()) / 5;
				BigDecimal bigDecimal = new BigDecimal(sumStar.toString());
				StatsHouseEvaVo statsHouseEvaVo = new StatsHouseEvaVo();
				statsHouseEvaVo.setTotalAvgGrade(bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
				statsHouseEvaVo.setHouseCleanAva(statsHouseEvaEntity.getHouseCleanAva());
				statsHouseEvaVo.setDesMatchAva(statsHouseEvaEntity.getDesMatchAva());
				statsHouseEvaVo.setSafeDegreeAva(statsHouseEvaEntity.getSafeDegreeAva());
				statsHouseEvaVo.setTrafPosAva(statsHouseEvaEntity.getTrafPosAva());
				statsHouseEvaVo.setCostPerforAva(statsHouseEvaEntity.getCostPerforAva());
				returnMap.put("statsHouseEva", statsHouseEvaVo);
			}
			
			// 房源评价列表
			EvaluateRequest evaluateRequest = new EvaluateRequest();
			if (houseEvaluate.getRentWay() == RentWayEnum.HOUSE.getCode()) {
				evaluateRequest.setHouseFid(houseEvaluate.getFid());
			} else if (houseEvaluate.getRentWay() == RentWayEnum.ROOM.getCode()) {
				evaluateRequest.setRoomFid(houseEvaluate.getFid());
			} else {
				LogUtil.error(LOGGER, "error:{}",
						MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_RENTWAY_ERROR));
			}
			evaluateRequest.setRentWay(houseEvaluate.getRentWay());
			evaluateRequest.setLimit(houseEvaluate.getLimit());
			evaluateRequest.setPage(houseEvaluate.getPage());
			String evaluatePageJson = evaluateOrderService.queryTenantEvaluateByPage(
					JsonEntityTransform.Object2Json(evaluateRequest));
			dto = JsonEntityTransform.json2DataTransferObject(evaluatePageJson);
			// 判断服务是否有错误
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "evaluateOrderService#queryTenantEvaluateByPage接口调用失败,参数:{},结果:{}",
						JsonEntityTransform.Object2Json(evaluateRequest), dto.toJsonString());
				return dto;
			}
			List<TenantEvaluateVo> evaList = dto.parseData("listTenantEvaluateVo",
					new TypeReference<List<TenantEvaluateVo>>() {});
			List<TenantEvalVo> tenantEvalVos = new ArrayList<TenantEvalVo>();
			if (!Check.NuNCollection(evaList)) {
				for (TenantEvaluateVo vo : evaList) {
					TenantEvalVo tenantEvalVo = new TenantEvalVo();
					// 以后获取用户名
					String customerJson = customerMsgManagerService.getCutomerVo(vo.getEvaUserUid());
					dto = JsonEntityTransform.json2DataTransferObject(customerJson);
					//判断服务是否有错误
					if(dto.getCode()==DataTransferObject.ERROR){
						LogUtil.error(LOGGER, "customerMsgManagerService#getCutomerVo接口调用失败,evaUserUid={},结果:{}",
								vo.getEvaUserUid(), dto.toJsonString());
					}
					CustomerVo customer = dto.parseData("customerVo", new TypeReference<CustomerVo>() {});
					if (!Check.NuNObj(customer)) {
						tenantEvalVo.setCustomerName(customer.getRealName());
					}
					tenantEvalVo.setEvalContent(vo.getContent());
					tenantEvalVo.setEvalDate(DateUtil.dateFormat(vo.getCreateTime()));
					
					// 查询订单关联房东对房客的评价
					EvaluateRequest evaRequest = new EvaluateRequest();
					evaRequest.setEvaUserType(UserTypeEnum.LANDLORD.getUserType());
					evaRequest.setOrderSn(vo.getOrderSn());
					String landlordEvalJson = evaluateOrderService.queryEvaluateByOrderSn(
							JsonEntityTransform.Object2Json(evaRequest));
					dto = JsonEntityTransform.json2DataTransferObject(landlordEvalJson);
					//判断服务是否有错误
					if(dto.getCode()==DataTransferObject.ERROR){
						LogUtil.error(LOGGER, "customerMsgManagerService#getCutomerVo接口调用失败,evaUserUid={},结果:{}",
								vo.getEvaUserUid(), dto.toJsonString());
					}
					Map<String, Object> landlordEvalMap = dto.parseData("customerVo", new TypeReference<Map<String, Object>>() {});

					List<EvaluateOrderEntity> listOrderEvaluateOrderEntities = null;
					if(landlordEvalMap.get("listOrderEvaluateOrder") !=null){
						listOrderEvaluateOrderEntities = JsonEntityTransform.json2ObjectList(JsonEntityTransform.Object2Json(landlordEvalMap.get("listOrderEvaluateOrder")), EvaluateOrderEntity.class);
						if(!Check.NuNCollection(listOrderEvaluateOrderEntities)){
							for (EvaluateOrderEntity evaluateOrderEntity : listOrderEvaluateOrderEntities) {
								if(evaluateOrderEntity.getEvaUserType().intValue() == UserTypeEnum.LANDLORD.getUserType()&&evaluateOrderEntity.getEvaStatu().intValue() == EvaluateStatuEnum.ONLINE.getEvaStatuCode()){
									if(!Check.NuNMap(landlordEvalMap)&&!Check.NuNObj( landlordEvalMap.get("landlordEvaluate"))){
										LandlordEvaluateEntity landlordEvaluateEntity=JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(landlordEvalMap.get("landlordEvaluate")),LandlordEvaluateEntity.class);
										tenantEvalVo.setLandlordEvalContent(landlordEvaluateEntity.getContent());
										break;
									}
								}
							}

						} 
					}

					tenantEvalVos.add(tenantEvalVo);
				}
			}
			returnMap.put("evaList", tenantEvalVos);
			
			dto.setErrCode(DataTransferObject.SUCCESS);
			dto.putValue("returnMap", returnMap);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "返回结果:{}", dto.toJsonString());
		return dto;
	}
	
	/**
	 * 
	 * 跳转引导页
	 *
	 * @author liujun
	 * @created 2016年9月7日
	 *
	 * @param request
	 * @param houseDetailDto
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/guide")
	public String guide(HttpServletRequest request){
		return "share/guide";
	}
	
}
