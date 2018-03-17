/**
 * @FileName: HouseController.java
 * @Package com.ziroom.minsu.portal.search.house.controller
 * 
 * @author jixd
 * @created 2016年8月5日 下午3:51:23
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.house.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.traderules.*;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.google.gson.JsonObject;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.order.HouseLockEntity;
import com.ziroom.minsu.portal.fd.center.house.dto.HouseDetailPcDto;
import com.ziroom.minsu.portal.fd.center.house.vo.LandlordIntroducePcVo;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.evaluate.api.inner.EvaluateOrderService;
import com.ziroom.minsu.services.evaluate.dto.EvaluatePCRequest;
import com.ziroom.minsu.services.evaluate.entity.EvaluateBothItemVo;
import com.ziroom.minsu.services.evaluate.entity.EvaluateHouseDetailVo;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.TenantHouseService;
import com.ziroom.minsu.services.house.dto.HouseDetailDto;
import com.ziroom.minsu.services.house.dto.LeaseCalendarDto;
import com.ziroom.minsu.services.house.entity.CalendarMonth;
import com.ziroom.minsu.services.house.entity.CalendarResponseVo;
import com.ziroom.minsu.services.house.entity.HouseBedNumVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.LeaseCalendarVo;
import com.ziroom.minsu.services.house.entity.SpecialPriceVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailPcVo;
import com.ziroom.minsu.services.house.entity.TenantHouseDetailVo;
import com.ziroom.minsu.services.order.api.inner.OrderUserService;
import com.ziroom.minsu.services.order.dto.HouseLockRequest;
import com.ziroom.minsu.services.order.dto.NeedPayFeeRequest;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

/**
 * <p>房源相关</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("/")
@Controller
public class HousePreviewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HousePreviewController.class);
	private static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
	private static final String DATE_FORMAT_MONTH = "yyyy-MM";

	@Value("#{'${BAIDU_AK}'.trim()}")
	private String BAIDU_AK;


	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;
	
	@Value("#{'${pic_size}'.trim()}")
	private String pic_size;
	
	@Value("#{'${pic_size_120_120}'.trim()}")
	private String pic_size_120_120;
	
	@Value("#{'${pic_default_head_url}'.trim()}")
	private String pic_default_head_url;
	
	@Value("#{'${pic_default_house_url}'.trim()}")
	private String pic_default_house_url;
	
	
	@Resource(name="house.tenantHouseService")
	private TenantHouseService tenantHouseService;
	
	@Resource(name = "customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;
	
	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name="evaluate.evaluateOrderService")
	private EvaluateOrderService evaluateOrderService;
	
	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="order.orderUserService")
	private OrderUserService orderUserService;
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	/**
	 * 
	 * 展示房源详情页面
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午4:03:03
	 *
	 * @return
	 */
	@RequestMapping("/preview/{rentWay}/{fid}")
	public String showHouseDetail(Model model,@PathVariable Integer rentWay,@PathVariable String fid){
		HouseDetailPcDto detailRequest = new HouseDetailPcDto();
		detailRequest.setFid(fid);
		detailRequest.setRentWay(rentWay);
		//
		TenantHouseDetailPcVo tenantHouseDetailPcVo = this.getHouseDetail(detailRequest);
		
		// 组装图片处理
		this.fillPicture(tenantHouseDetailPcVo);
		
		// 获取房东个人信息
		LandlordIntroducePcVo introducePcVo = this.getLandIntroduce(tenantHouseDetailPcVo.getLandlordUid());
		
		// 组装押金规则名称
		this.fillDepositRulesName(tenantHouseDetailPcVo);
		
		// 组装床信息
		this.fillBedList(tenantHouseDetailPcVo);
		
		// 组装配套设施
		this.fillFacilityList(tenantHouseDetailPcVo);
		
		// 组装服务信息
		this.fillServeList(tenantHouseDetailPcVo);
		
		// 组装退订政策
		this.fillCheckOutRules(tenantHouseDetailPcVo);
		
		//地址处理
		tenantHouseDetailPcVo.setHouseAddr(tenantHouseDetailPcVo.getHouseAddr().split(" ")[0]);
		//处理入住时间
		String inTime = tenantHouseDetailPcVo.getCheckInTime();
		String outTime = tenantHouseDetailPcVo.getCheckOutTime();
		if(!Check.NuNStr(inTime)){
			tenantHouseDetailPcVo.setCheckInTime("0".equals(inTime) ? "":inTime.substring(0, 2));
		}
		if(!Check.NuNStr(outTime)){
			tenantHouseDetailPcVo.setCheckOutTime("0".equals(outTime) ? "":outTime.substring(0, 2));
		}
		
		//查询房源评价信息
		//EvaluateHouseDetailVo houseEvaInfo = this.getHouseEvaInfo(tenantHouseDetailPcVo);
		
		//处理换行符在页面展示
		if(!Check.NuNStr(tenantHouseDetailPcVo.getHouseAroundDesc())){
			tenantHouseDetailPcVo.setHouseAroundDesc(tenantHouseDetailPcVo.getHouseAroundDesc().replaceAll("\n", "<br/>"));
		}
		if(!Check.NuNStr(tenantHouseDetailPcVo.getHouseDesc())){
			tenantHouseDetailPcVo.setHouseDesc(tenantHouseDetailPcVo.getHouseDesc().replaceAll("\n", "<br/>"));
		}
		if(!Check.NuNStr(tenantHouseDetailPcVo.getHouseRules())){
			tenantHouseDetailPcVo.setHouseRules(tenantHouseDetailPcVo.getHouseRules().replaceAll("\n", "<br/>"));
		}
		
		//model.addAttribute("houseEvaInfo", houseEvaInfo);
		model.addAttribute("detailVo", tenantHouseDetailPcVo);
		model.addAttribute("lanInfo", introducePcVo);
		model.addAttribute("BAIDU_AK",BAIDU_AK);
		return "house/previewHouse";
	}
	
	
	
	
	/**
	 * 
	 * 房源评价列表
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:47:00
	 *
	 * @return
	 */
	@RequestMapping("/house/evaList")
	@ResponseBody
	public DataTransferObject toHouseEvaList(EvaluatePCRequest request){
		DataTransferObject dto = new DataTransferObject();
		try{
			Integer rentWay = request.getRentWay();
			if(Check.NuNObj(request.getRentWay())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto;
			}
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				if(Check.NuNStr(request.getHouseFid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房源FID为空");
					return dto;
				}
			}
			if(rentWay == RentWayEnum.ROOM.getCode()){
				if(Check.NuNStr(request.getRoomFid())){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("房间FID为空");
					return dto;
				}
			}
			if(Check.NuNObj(request.getPage())){
				request.setPage(1);
			}
			request.setLimit(5);
			dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryHouseDetailEvaPage(JsonEntityTransform.Object2Json(request)));
			List<EvaluateBothItemVo> evaList = dto.parseData("evaList", new TypeReference<List<EvaluateBothItemVo>>() {});
			for(EvaluateBothItemVo eva : evaList){
				String uid = eva.getUid();
				String customerJson = customerMsgManagerService.getCutomerVo(uid);
				CustomerVo customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
				if (!Check.NuNObj(customer)) {
					eva.setUserName(customer.getNickName());
					eva.setUserPic(customer.getUserPicUrl());
				}else{
					eva.setUserName("房客");
					eva.setUserPic(pic_default_head_url);
				}
			}
			dto.putValue("evaList", evaList);
		}catch(Exception e){
			LogUtil.error(LOGGER, "获取对房源评价异常,e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 对房东评价列表
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午10:48:49
	 *
	 * @return
	 */
	@RequestMapping("/house/tolanList")
	@ResponseBody
	public DataTransferObject toLanEvaList(EvaluatePCRequest request){
		DataTransferObject dto = new DataTransferObject();
		try{
			String landlordUid = request.getLandlordUid();
			if(Check.NuNStr(landlordUid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东UID为空");
				return dto;
			}
			if(Check.NuNObj(request.getPage())){
				request.setPage(1);
			}
			request.setLimit(5);
			dto = JsonEntityTransform.json2DataTransferObject(evaluateOrderService.queryLanEvaPage(JsonEntityTransform.Object2Json(request)));
		}catch(Exception e){
			LogUtil.error(LOGGER, "获取对房东评价异常,e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 查询房源评价信息
	 *
	 * @author jixd
	 * @created 2016年8月8日 上午11:03:12
	 *
	 * @return
	 */
	public EvaluateHouseDetailVo getHouseEvaInfo(TenantHouseDetailPcVo tenantHouseDetailPcVo){
		EvaluatePCRequest request = new EvaluatePCRequest();
		request.setRentWay(tenantHouseDetailPcVo.getRentWay());
		if(tenantHouseDetailPcVo.getRentWay() == RentWayEnum.HOUSE.getCode()){
			request.setHouseFid(tenantHouseDetailPcVo.getFid());
		}
		if(tenantHouseDetailPcVo.getRentWay() == RentWayEnum.ROOM.getCode()){
			request.setRoomFid(tenantHouseDetailPcVo.getFid());
		}
		String evaInfo = evaluateOrderService.houseDetailEvaInfo(JsonEntityTransform.Object2Json(request));
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(evaInfo);
		if(dto.getCode() == DataTransferObject.SUCCESS){
			return dto.parseData("evaHouseVo", new TypeReference<EvaluateHouseDetailVo>() {});
		}
		
		return new EvaluateHouseDetailVo();
	}
	
	/**
	 * 
	 * 获取房源详情
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:57:52
	 *
	 * @param houseDetailPcDto
	 * @return
	 */
	private TenantHouseDetailPcVo getHouseDetail(HouseDetailPcDto houseDetailPcDto){
		try {
			HouseDetailDto houseDetailDto = new HouseDetailDto();
			houseDetailDto.setFid(houseDetailPcDto.getFid());
			houseDetailDto.setRentWay(houseDetailPcDto.getRentWay());
			
			//查询房源详情
			String resultJson=tenantHouseService.houseDetail(JsonEntityTransform.Object2Json(houseDetailDto));
	
			//判断服务是否有错误
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (houseDto.getCode() == 1){
				LogUtil.error(LOGGER, "服务错误:{}", resultJson);
				return null;
			}
			
			TenantHouseDetailVo tenantHouseDetailVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", TenantHouseDetailVo.class);
			TenantHouseDetailPcVo tenantHouseDetailPcVo = new TenantHouseDetailPcVo();
			BeanUtils.copyProperties(tenantHouseDetailVo, tenantHouseDetailPcVo);
			return tenantHouseDetailPcVo;
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取房源详情错误,e:{}", e);
			return null;
		}
	}
	
	
	
	
	/**
	 * 
	 * 组装图片
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:58:12
	 *
	 * @param tenantHouseDetailPcVo
	 */
	private void fillPicture(TenantHouseDetailPcVo tenantHouseDetailPcVo) {

		String defaultPicBase = tenantHouseDetailPcVo.getDefaultPic();
		if(!Check.NuNStr(tenantHouseDetailPcVo.getDefaultPic())){
			tenantHouseDetailPcVo.setDefaultPic(PicUtil.getSpecialPic(picBaseAddrMona, tenantHouseDetailPcVo.getDefaultPic(), pic_size));
		}
		
		List<String> list = new ArrayList<>();
		JSONObject json = new JSONObject();
		json.put("fid", tenantHouseDetailPcVo.getFid());
		json.put("rentWay", tenantHouseDetailPcVo.getRentWay());
		
		String picListJson = houseManageService.findPicListByHouseAndRoomFid(json.toJSONString());
		DataTransferObject picListDto = JsonEntityTransform.json2DataTransferObject(picListJson);
		if (picListDto.getCode() == DataTransferObject.SUCCESS) {
			List<HousePicMsgEntity> picList = picListDto.parseData("list", new TypeReference<List<HousePicMsgEntity>>() {});
			if(!Check.NuNCollection(picList)){
				for(HousePicMsgEntity picMsg : picList){
					if(defaultPicBase != null && !"".equals(defaultPicBase) && picMsg.getPicBaseUrl().equals(defaultPicBase)){
						list.add(0,PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size));
					}else{
						list.add(PicUtil.getFullPic(picBaseAddrMona, picMsg.getPicBaseUrl(), picMsg.getPicSuffix(), pic_size));
					}
				}
			}
		}
		if(Check.NuNCollection(list)){
			list.add(pic_default_house_url);
		}
		tenantHouseDetailPcVo.setPicList(list);
		// 图片列表处理
		/*if (!Check.NuNCollection(tenantHouseDetailPcVo.getPicList())) {
			List<String> picList = new ArrayList<String>();
			for (String picUrl : tenantHouseDetailPcVo.getPicList()) {
				picList.add(PicUtil.getSpecialPic(picBaseAddrMona, picUrl, pic_size));
			}
			tenantHouseDetailPcVo.setPicList(picList);
		}
		if (Check.NuNCollection(tenantHouseDetailPcVo.getPicList())) {
			tenantHouseDetailPcVo.getPicList().add(tenantHouseDetailPcVo.getDefaultPic());
		}
		if (!Check.NuNCollection(tenantHouseDetailPcVo.getPicDisList())) {
			for (MinsuEleEntity eleEntity : tenantHouseDetailPcVo.getPicDisList()) {
				eleEntity.setEleValue(PicUtil.getSpecialPic(picBaseAddrMona, eleEntity.getEleValue(), pic_size));
			}
		} else {
			MinsuEleEntity eleEntity = new MinsuEleEntity();
			eleEntity.setEleKey("卧室");
			eleEntity.setEleValue(tenantHouseDetailPcVo.getDefaultPic());
			tenantHouseDetailPcVo.getPicDisList().add(eleEntity);
		}*/
		
		
	}
	
	
	/**
	 * 
	 * 房东个人介绍
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:58:24
	 *
	 * @param landlordUid
	 * @return
	 */
	private LandlordIntroducePcVo getLandIntroduce(String landlordUid){
		LandlordIntroducePcVo introducePcVo = new LandlordIntroducePcVo();
		try{
			// 获取房东个人信息
			String customerBase = customerMsgManagerService.getCustomerDetailImage(landlordUid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerBase);
			if(customerDto.getCode() != DataTransferObject.SUCCESS){
				LogUtil.error(LOGGER, "服务错误:{}", customerBase);
				return null;
			}
			CustomerDetailImageVo customerMsg = SOAResParseUtil.getValueFromDataByKey(customerBase, "customerImageVo", CustomerDetailImageVo.class);
			List<CustomerPicMsgEntity> customerPicList = customerMsg.getCustomerPicList();
			introducePcVo.setNickName(customerMsg.getNickName());
			introducePcVo.setHeadPicUrl(pic_default_head_url);
			if(!Check.NuNCollection(customerPicList)){
				for(CustomerPicMsgEntity picMsgEntity : customerPicList){
					if(picMsgEntity.getPicType() == CustomerPicTypeEnum.YHTX.getCode()){
						if(!Check.NuNStr(picMsgEntity.getPicServerUuid())){
							//如果是用户头像
							String headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picMsgEntity.getPicBaseUrl(), picMsgEntity.getPicSuffix(), pic_size_120_120);
							introducePcVo.setHeadPicUrl(headPicUrl);
						}
					}
				}
			}
			//查询房东的个人介绍
			DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(this.customerMsgManagerService.selectCustomerExtByUid(landlordUid));
			if(introduceDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity customerBaseMsgExt = introduceDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(customerBaseMsgExt)){
					introducePcVo.setIntroduce(customerBaseMsgExt.getCustomerIntroduce());
				}
			}
			return introducePcVo;
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取房东个人信息错误,e:{}", e);
			return introducePcVo;
		}
	}
	
	
	/**
	 * 
	 * 组装押金规则
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:58:42
	 *
	 * @param tenantHouseDetailPcVo
	 */
	private void fillDepositRulesName(TenantHouseDetailPcVo tenantHouseDetailPcVo){
		try{
			// 组装押金规则名称
			String dicResultJson = cityTemplateService.getSelectEnum(null, tenantHouseDetailPcVo.getDepositRulesCode());
			List<EnumVo> list = SOAResParseUtil.getListValueFromDataByKey(dicResultJson, "selectEnum", EnumVo.class);
			for (EnumVo vo : list) {
				if (vo.getKey().equals(tenantHouseDetailPcVo.getDepositRulesValue())) {
					tenantHouseDetailPcVo.setDepositRulesName(vo.getText());
					break;
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "组装押金规则名称错误,e:{}", e);
		}
	}
	
	/**
	 * 
	 * 组装床铺信息
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:58:58
	 *
	 * @param tenantHouseDetailPcVo
	 */
	private void fillBedList(TenantHouseDetailPcVo tenantHouseDetailPcVo){
		try{
			//组装床信息
			String bedTypeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
			List<EnumVo> bedTypelist=SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			if(!Check.NuNCollection(tenantHouseDetailPcVo.getBedList())){
				Map<Integer,Integer> numMap = new HashMap<>();
				for (HouseBedNumVo bedVo : tenantHouseDetailPcVo.getBedList()) {
					Integer bedType = bedVo.getBedType();
					Integer oNum = bedVo.getBedNum();
					if(numMap.containsKey(bedType)){
						Integer bedNum = numMap.get(bedType);
						numMap.put(bedType, bedNum+oNum);
					}else{
						numMap.put(bedType,oNum);
					}
					
				}
				//计算床数量
				List<HouseBedNumVo> houseBedVo = new ArrayList<>();
				for(Map.Entry<Integer, Integer> entry : numMap.entrySet()){
					HouseBedNumVo vo = new HouseBedNumVo();
					for (EnumVo enumVo : bedTypelist) {
						if(entry.getKey().toString().equals(enumVo.getKey())){
							vo.setBedNum(entry.getValue());
							vo.setBedTypeName(enumVo.getText());
							vo.setBedType(entry.getKey());
							houseBedVo.add(vo);
							break;
						}
					}
				}
				tenantHouseDetailPcVo.setBedList(houseBedVo);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "组装床信息错误,e:{}", e);
		}
	}
	
	
	/**
	 * 
	 * 组装配套设置
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:59:26
	 *
	 * @param tenantHouseDetailPcVo
	 */
	private void fillFacilityList(TenantHouseDetailPcVo tenantHouseDetailPcVo){
		try{
			//组装配套设施
			String facilityJson=cityTemplateService.getSelectSubDic(null, ProductRulesEnum.ProductRulesEnum002.getValue());
			List<EnumVo> facilitylist=SOAResParseUtil.getListValueFromDataByKey(facilityJson, "subDic", EnumVo.class);
			for(HouseConfVo vo:tenantHouseDetailPcVo.getFacilityList()){
				for (EnumVo enumVo : facilitylist) {
					if(vo.getDicCode().equals(enumVo.getKey())){
						for (EnumVo valVo : enumVo.getSubEnumVals()) {
							if(vo.getDicValue().equals(valVo.getKey())){
								vo.setDicName(valVo.getText());
								break;
							}
						}
						break;
					}
				}
			}
			//删除配套设施沙发
			for(HouseConfVo vo:tenantHouseDetailPcVo.getFacilityList()){
				if(vo.getDicCode().equals("ProductRulesEnum002003")&&vo.getDicValue().equals("1")){
					tenantHouseDetailPcVo.getFacilityList().remove(vo);
					break;
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "组装配套设施错误,e:{}", e);
		}
	}
	
	
	/**
	 * 
	 * 组装服务信息
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:59:38
	 *
	 * @param tenantHouseDetailPcVo
	 */
	private void fillServeList(TenantHouseDetailPcVo tenantHouseDetailPcVo) {
		try {
			// 服务
			String serveJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0015.getValue());
			List<EnumVo> servelist = SOAResParseUtil.getListValueFromDataByKey(serveJson, "selectEnum", EnumVo.class);
			for (HouseConfVo vo : tenantHouseDetailPcVo.getServeList()) {
				for (EnumVo enumVo : servelist) {
					if (vo.getDicValue().equals(enumVo.getKey())) {
						vo.setDicName(enumVo.getText());
						break;
					}
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "组装服务信息错误,e:{}", e);
		}
	}
	
	
	/**
	 * 
	 * 组装退订政策
	 *
	 * @author jixd
	 * @created 2016年8月5日 下午3:59:53
	 *
	 * @param tenantHouseDetailPcVo
	 */
	private void fillCheckOutRules(TenantHouseDetailPcVo tenantHouseDetailPcVo) {

		try {
			//退订政策
			String checkOutRulesJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkOutRulesJson);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,code={}",
						TradeRulesEnum.TradeRulesEnum005.getValue());
			}else{
				List<EnumVo> checkOutRulesList = dto.parseData("subDic", new TypeReference<List<EnumVo>>() {});


				//退订政策显示
				for (EnumVo checkOutRules : checkOutRulesList) {

					if(!checkOutRules.getKey().equals(tenantHouseDetailPcVo.getCheckOutRulesCode())){
						continue;
					}

					Map<String, Object> checkOutRulesMap = new HashMap<String, Object>();

					TradeRulesVo tradeRulesVo  = new TradeRulesVo();

					String dicCode = null;
					if(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue();
					}else if(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue();
					}else if(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue();
					}else if(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue().startsWith(checkOutRules.getKey())){
						dicCode = TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue();
					}
					String checkOutRulesValJson = cityTemplateService.getSelectSubDic(null, dicCode);
					DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(checkOutRulesValJson);
					if (dto1.getCode() == DataTransferObject.ERROR) {
						LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,code={}",
								dicCode);
					}else{
						List<EnumVo> checkOutRulesValList = dto1.parseData("subDic", new TypeReference<List<EnumVo>>() {});


						int i = 0;
						for (EnumVo checkOutRulesVal : checkOutRulesValList) {

							if(!Check.NuNCollection(checkOutRulesVal.getSubEnumVals())){
								i++;
								checkOutRulesMap.put("checkOutRulesVal" + i, checkOutRulesVal.getSubEnumVals().get(0).getKey());
							}

						}
					}

					checkOutRulesMap.put("checkOutRulesName", checkOutRules.getText());
					checkOutRulesMap.put("checkOutRulesCode", checkOutRules.getKey());
					tradeRulesVo.setUnregText1(Integer.valueOf(String.valueOf(checkOutRulesMap.get("checkOutRulesVal1"))));
					tradeRulesVo.setUnregText2(String.valueOf(checkOutRulesMap.get("checkOutRulesVal2")));
					tradeRulesVo.setUnregText3(String.valueOf(checkOutRulesMap.get("checkOutRulesVal3")));

					//严格
					if(dicCode.equals(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue())){

						TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005001001Enum.TradeRulesEnum005001001002.trans1ShowNameOld(tradeRulesVo);

					}
					//适中
					if(dicCode.equals(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue())){
						TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005002001Enum.TradeRulesEnum005002001002.trans1ShowNameOld(tradeRulesVo);

					}
					//灵活
					if(dicCode.equals(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue())){
						TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.trans1ShowNameOld(tradeRulesVo);
						TradeRulesEnum005003001Enum.TradeRulesEnum005003001002.trans1ShowNameOld(tradeRulesVo);

					}

					//新版本 修改退订政策
					if(!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal4"))
							&&!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal5"))
							&&!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal6"))){

						tradeRulesVo.setUnregText4(String.valueOf(checkOutRulesMap.get("checkOutRulesVal4")));
						tradeRulesVo.setUnregText5(String.valueOf(checkOutRulesMap.get("checkOutRulesVal5")));
						tradeRulesVo.setUnregText6(String.valueOf(checkOutRulesMap.get("checkOutRulesVal6")));
						tradeRulesVo.setUnregText7(ValueUtil.getStrValue(checkOutRulesMap.get("checkOutRulesVal7")));
						//严格
						if(dicCode.equals(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue())){

							TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005001001Enum.TradeRulesEnum005001001005.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.commonShowName(tradeRulesVo);

						}
						//适中
						if(dicCode.equals(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue())){
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001005.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.commonShowName(tradeRulesVo);
						}
						//灵活
						if(dicCode.equals(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue())){
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001005.trans1ShowName(tradeRulesVo);
							TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.commonShowName(tradeRulesVo);
						}
						
						Integer longTermLimit =null;
						try {
		        			//长租天数 设置
		        			String longTermLimitStr = cityTemplateService.getTextValue(null,TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
		        			longTermLimit = SOAResParseUtil.getValueFromDataByKey(longTermLimitStr, "textValue", Integer.class);
		        		} catch (Exception e) {
		        			LogUtil.error(LOGGER, "长租入住最小天数查询失败e={}", e);
		        		}
						
						// 长租
						if(dicCode.equals(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue())){
							if(!Check.NuNObj(longTermLimit)){
								tradeRulesVo.setLongTermLimit(longTermLimit);
							}
							TradeRulesEnum005004001Enum.showContext(tradeRulesVo);
						}
						
						 //添加说明
		                tradeRulesVo.setExplain(TradeRulesEnum005005001Enum.getDefautExplain(longTermLimit));
		                
					}

					tenantHouseDetailPcVo.setUnregText1(tradeRulesVo.getUnregText1());
					tenantHouseDetailPcVo.setUnregText2(tradeRulesVo.getCheckInOnNameM());
					tenantHouseDetailPcVo.setUnregText3(tradeRulesVo.getCheckOutEarlyNameM());
					StringBuffer sb = new StringBuffer();
					sb.append("<li><i></i>");
					sb.append(tradeRulesVo.getCheckInPreNameM());
					sb.append("</li>");
					sb.append("<li><i></i>");
					sb.append(tradeRulesVo.getCheckInOnNameM());
					sb.append("</li>");
					sb.append("<li><i></i>");
					sb.append(tradeRulesVo.getCheckOutEarlyNameM());
					sb.append("</li>");

					if(!Check.NuNStr(tradeRulesVo.getCommonShowName())){
						sb.append("<li><i></i>")
						.append(tradeRulesVo.getCommonShowName())
						.append("</li>");
					}
					
					if (!Check.NuNStr(tradeRulesVo.getExplain())) {
						sb.append("<br/><li>").append(TradeRulesEnum005Enum.TradeRulesEnum005005.getName()).append(":</li>");
						String[] terms = tradeRulesVo.getExplain().split(";</br>");
						for(int i=0;i<terms.length;i++){
							if (Check.NuNStr(terms[i])) {
								continue;
							}
							if(!terms[i].endsWith("。")){
								sb.append("<li><i></i>").append(terms[i]).append(";</li>");
							}else{
								sb.append("<li><i></i>").append(terms[i]).append("</li>");
							}
						}
					}
					
					tenantHouseDetailPcVo.setTradeRulesShowName(sb.toString());
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "获取退订政策异常e={}", e);
		}


	}




	/**
	 * 	
	 * 获取出租日历
	 *
	 * @author jixd
	 * @created 2016年8月8日 下午7:57:34
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/house/leaseCalendar")
	@ResponseBody
	public DataTransferObject leaseCalendar(HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		String startStr = request.getParameter("startDate");
		String endStr = request.getParameter("endDate");
		Integer rentWay = Integer.valueOf(request.getParameter("rentWay"));
		try {
			if(Check.NuNStr(startStr)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("开始时间为空");
				return dto;
			}
			if(Check.NuNStr(endStr)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("结束时间为空");
				return dto;
			}
			if(Check.NuNObj(rentWay)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto;
			}
			if(Check.NuNStr(request.getParameter("fid"))){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("fid为空");
				return dto;
			}
			//开始时间
			Date startDate = DateUtil.parseDate(startStr, DATE_FORMAT_DAY);
			//结束时间
			Date endDate = DateUtil.parseDate(endStr, DATE_FORMAT_DAY);
			
			String fid = request.getParameter("fid");
			// 房源逻辑id
			String houseBaseFid = null;
			// 房间逻辑id
			String houseRoomFid = null;
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				houseBaseFid = fid;
			}
			if(rentWay == RentWayEnum.ROOM.getCode()){
				houseRoomFid = fid;
				//查询该房间房源的fid，查询出租日历
				DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseRoomMsgByFid(fid));
				if(roomDto.getCode() == DataTransferObject.SUCCESS){
					HouseRoomMsgEntity roomEntity = roomDto.parseData("obj", new TypeReference<HouseRoomMsgEntity>() {});
					houseBaseFid = roomEntity.getHouseBaseFid();
				}else{
					return roomDto;
				}
			}
			/** 查询出租日历特殊价格 **/
			LeaseCalendarDto requestDto = new LeaseCalendarDto();
			requestDto.setHouseBaseFid(houseBaseFid);
            if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                requestDto.setHouseRoomFid(houseRoomFid);
            }
			requestDto.setStartDate(startDate);
			requestDto.setEndDate(endDate);
			requestDto.setRentWay(rentWay);
			
			String houseParams = JsonEntityTransform.Object2Json(requestDto);
			LogUtil.info(LOGGER, "房源参数：{}", houseParams);
			String houseJson = houseManageService.leaseCalendar(houseParams);
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			//判断调用状态
			if(houseDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("查询出错日历特殊价格出错");
				return dto;
			}
			/** 查询出租日历特殊价格 **/
			
			/** 查询出租日历出租状态 **/
			HouseLockRequest lockRequest = new HouseLockRequest();
			lockRequest.setFid(houseBaseFid);
			if(!Check.NuNObj(rentWay) && rentWay == RentWayEnum.ROOM.getCode()){
                lockRequest.setRoomFid(houseRoomFid);
            }

			lockRequest.setRentWay(rentWay);
			lockRequest.setStarTime(startDate);
			lockRequest.setEndTime(endDate);
			
			String orderParams = JsonEntityTransform.Object2Json(lockRequest);
			LogUtil.info(LOGGER, "订单参数：{}", orderParams);
			String orderJson = orderUserService.getHouseLockInfo(orderParams);
			DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			//判断调用状态
			if(orderDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "出租状态获取失败");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租状态获取失败");
				return dto;
			}
			/** 查询出租日历出租状态 **/
			
			/** 封装返回信息**/
			LeaseCalendarVo leaseCalendarVo = houseDto.parseData("calendarData",new TypeReference<LeaseCalendarVo>() {});
			
			Map<String, LinkedHashMap<String,CalendarResponseVo>> monthMap=new LinkedHashMap<String, LinkedHashMap<String,CalendarResponseVo>>();
			for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(startDate, endDate) + 1; i < j; i++) {
				CalendarResponseVo vo = new CalendarResponseVo();
				Date addStart = DateUtil.getTime(startDate, i);
				String dateMonth = DateUtil.dateFormat(addStart, DATE_FORMAT_MONTH);
				String dateDay = DateUtil.dateFormat(addStart, DATE_FORMAT_DAY);
				vo.setDate(dateDay);
				vo.setPrice(leaseCalendarVo.getUsualPrice());
				LinkedHashMap<String, CalendarResponseVo> dayMap=monthMap.get(dateMonth);
				if(!Check.NuNMap(dayMap)){
					dayMap.put(dateDay, vo);
				} else {
					dayMap=new LinkedHashMap<String, CalendarResponseVo>();
					dayMap.put(dateDay, vo);
					monthMap.put(dateMonth, dayMap);
				}
			}
			
			//设置特殊价格
			List<SpecialPriceVo> specialPriceList = leaseCalendarVo.getSpecialPriceList();
			for (SpecialPriceVo specialPriceVo : specialPriceList) {
				Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_MONTH));
				if(!Check.NuNMap(map)){
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(specialPriceVo.getSetDate(), DATE_FORMAT_DAY));
					if(!Check.NuNObj(vo)){
						vo.setPrice(specialPriceVo.getSetPrice());
					}
				}
			}
			//房源锁定信息列表
			List<HouseLockEntity> houseLockList = orderDto.parseData("houseLock",new TypeReference<List<HouseLockEntity>>() {});
			//设置房源状态
			for (HouseLockEntity houseLockEntity : houseLockList) {
				Map<String, CalendarResponseVo> map=monthMap.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_MONTH));
				if(!Check.NuNMap(map)){
					CalendarResponseVo vo = map.get(DateUtil.dateFormat(houseLockEntity.getLockTime(), DATE_FORMAT_DAY));
					if(!Check.NuNObj(vo)){
						vo.setStatus(houseLockEntity.getLockType());
					}
				}
			}
			//对象封装
			List<CalendarMonth> calendarList=new ArrayList<CalendarMonth>();
			for(String key:monthMap.keySet()){
				CalendarMonth calendarMonth=new CalendarMonth();
				calendarMonth.setMonthStr(key);
				calendarMonth.getCalendarList().addAll(monthMap.get(key).values());
				calendarList.add(calendarMonth);
			}
			/** 封装返回信息**/
			LogUtil.info(LOGGER, "结果：" + JsonEntityTransform.Object2Json(calendarList));
			
			dto.putValue("list",calendarList);
			dto.putValue("tillDate", DateUtil.dateFormat(leaseCalendarVo.getTillDate(), DATE_FORMAT_DAY));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【房源详情出租日历异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 获取日期数组
	 *
	 * @author jixd
	 * @created 2016年8月9日 下午3:04:58
	 *
	 * @return
	 */
	@RequestMapping("/house/dateArr")
	@ResponseBody
	public DataTransferObject getDateArr(String start,String end){
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(start)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("开始时间为空");
			return dto;
		}
		if(Check.NuNStr(end)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("结束时间为空");
			return dto;
		}
		List<String> list = new ArrayList<>();
		try{
			Date startDate = DateUtil.parseDate(start,DATE_FORMAT_DAY);
			Date endDate = DateUtil.parseDate(end,DATE_FORMAT_DAY);
			for (int i = 0, j = DateUtil.getDatebetweenOfDayNum(startDate, endDate) + 1; i < j; i++) {
				Date addStart = DateUtil.getTime(startDate, i);
				String dateDay = DateUtil.dateFormat(addStart, DATE_FORMAT_DAY);
				list.add("td_"+dateDay);
			}
			dto.putValue("dateList", list);
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取日期数组异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
	
	/**
	 * 
	 * 根据出租日历获取房费金额
	 *
	 * @author jixd
	 * @created 2016年8月10日 下午2:36:11
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/house/needPay")
	@ResponseBody
	public DataTransferObject getNeedPay(HttpServletRequest request){
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String fid = request.getParameter("fid");
		String rentWay = request.getParameter("rentWay");
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNStr(fid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源FID为空");
				return dto;
			}
			if(Check.NuNStr(rentWay)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("出租方式为空");
				return dto;
			}
			if(Check.NuNObj(startTime)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("开始时间为空");
				return dto;
			}
			if(Check.NuNObj(endTime)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("结束时间为空");
				return dto;
			}
			NeedPayFeeRequest payRequest = new NeedPayFeeRequest();
			payRequest.setFid(fid);
			payRequest.setStartTime(DateUtil.parseDate(startTime, "yyyy-MM-dd"));
			payRequest.setEndTime(DateUtil.parseDate(endTime, "yyyy-MM-dd"));
			payRequest.setRentWay(Integer.parseInt(rentWay));
			dto = JsonEntityTransform.json2DataTransferObject(orderUserService.getNeedPayFee(JsonEntityTransform.Object2Json(payRequest)));
		}catch(Exception e){
			LogUtil.error(LOGGER, "【获取租金异常】e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务异常");
		}
		return dto;
	}
}
