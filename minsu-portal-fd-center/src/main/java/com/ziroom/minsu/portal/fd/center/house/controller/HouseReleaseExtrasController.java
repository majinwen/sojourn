package com.ziroom.minsu.portal.fd.center.house.controller;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.portal.fd.center.house.service.HouseUpdateLogService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CheckIdCardUtils;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDescDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseDescDto;
import com.ziroom.minsu.services.house.dto.HouseUpdateHistoryLogDto;
import com.ziroom.minsu.services.house.entity.HouseBaseVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum0020;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005001001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005002001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005002Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005003001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005003Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005004001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005004Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005005001Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum005Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesVo;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zl
 * @version 1.0
 *
 */
@RequestMapping("houseReleaseExtras")
@Controller
public class HouseReleaseExtrasController {

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name = "fd.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;

	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;
	
	private static Logger  LOGGER = LoggerFactory.getLogger(HouseReleaseExtrasController.class);


	/**
	 * 发布房源 --》补充信息
	 * 
	 * @author zl
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("initExtrasInfo/{houseBaseFid}")
	public String  initExtrasInfo(@PathVariable String houseBaseFid, String roomFid,HttpServletRequest request,ModelMap map) {
	       
		try {		
			request.setAttribute("roomFid", roomFid);
			String houseBaseExtJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
			DataTransferObject houseBaseExtDto = JsonEntityTransform.json2DataTransferObject(houseBaseExtJson);
			HouseBaseExtDto houseBaseExt = null;
			if (houseBaseExtDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.info(LOGGER, "houseIssueService.searchHouseBaseAndExtByFid错误,houseBaseFid={},结果:{}", houseBaseFid, houseBaseExtJson);

			} else {
			     houseBaseExt = SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
				if(!Check.NuNObj(houseBaseExt)){
					request.setAttribute("clickStep", HouseIssueStepEnum.SIX.getCode());
					if(houseBaseExt.getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
						request.setAttribute("nowStep", houseBaseExt.getOperateSeq());
					}else{
						request.setAttribute("nowStep", HouseIssueStepEnum.FIVE.getCode());
					}
				}
			}
			String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
			//如果是分租
			if(RentWayEnum.ROOM.getCode()==houseBaseExt.getRentWay().intValue() && "1".equals(zkSysValue)){//
				getRoomBaseAndExtInfo(houseBaseFid, roomFid, request,houseBaseExt);
			}else{
				getHouseBaseAndExtInfo(houseBaseFid, request, houseBaseExt);
			}
		
			//预定类型 
			String orderTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0010.getValue());
			DataTransferObject orderTypeDto = JsonEntityTransform.json2DataTransferObject(orderTypeJson);
			if (orderTypeDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
						ProductRulesEnum.ProductRulesEnum0010.getValue());
			}else{
				List<EnumVo> orderTypeList = orderTypeDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
				map.put("orderTypeList", orderTypeList);
			}
	
			//民宿类型
			/*String homeStayJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0013.getValue());
			DataTransferObject homeStayDto = JsonEntityTransform.json2DataTransferObject(homeStayJson);
			if (homeStayDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
						ProductRulesEnum.ProductRulesEnum0013.getValue());
			}else{
				List<EnumVo> homeStayList = homeStayDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
				map.put("homeStayList", homeStayList);
			}*/
	
			//退房规则
			String checkOutJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
			DataTransferObject checkOutDto = JsonEntityTransform.json2DataTransferObject(checkOutJson);
			if (checkOutDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,houseBaseFid={},code={}", houseBaseFid, 
						TradeRulesEnum.TradeRulesEnum005.getValue());
			}else{
				List<EnumVo> checkOutList = checkOutDto.parseData("subDic", new TypeReference<List<EnumVo>>() {});
				map.put("checkOutListOld", checkOutList);
			}
			checkOutRulesShow(request, map);
	
			//最小入住天数
			String minDayJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0016.getValue());
			DataTransferObject minDayDto = JsonEntityTransform.json2DataTransferObject(minDayJson);
			if (minDayDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
						ProductRulesEnum.ProductRulesEnum0016.getValue());
			}else{
				List<EnumVo> minDayList = minDayDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
				map.put("minDayList", minDayList);
			}
	
	
			//入住时间
			String checkInTimeJson = cityTemplateService.getSelectEnum(null,
					ProductRulesEnum.ProductRulesEnum003.getValue());
			DataTransferObject checkInTimeDto = JsonEntityTransform.json2DataTransferObject(checkInTimeJson);
			if (checkInTimeDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},,code={}", houseBaseFid,
						ProductRulesEnum.ProductRulesEnum003.getValue());
			}else{
				List<EnumVo> checkInTimeList = checkInTimeDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
				map.put("checkInTimeList", checkInTimeList);
			}
	
			//退房时间
			String checkOutTimeJson = cityTemplateService.getSelectEnum(null,
					ProductRulesEnum.ProductRulesEnum004.getValue());
			DataTransferObject checkOutTimeDto = JsonEntityTransform.json2DataTransferObject(checkOutTimeJson);
			if (checkOutTimeDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
						ProductRulesEnum.ProductRulesEnum004.getValue());
			}else{
				List<EnumVo> checkOutTimeList = checkOutTimeDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
				map.put("checkOutTimeList", checkOutTimeList);
			}
	
			//被单更换
			/*String sheetReplaceJson = cityTemplateService.getSelectEnum(null,
					ProductRulesEnum.ProductRulesEnum0014.getValue());
			DataTransferObject sheetReplaceDto = JsonEntityTransform.json2DataTransferObject(sheetReplaceJson);
			if (sheetReplaceDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
						ProductRulesEnum.ProductRulesEnum0014.getValue());
			}else{
				List<EnumVo> sheetReplaceList = sheetReplaceDto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
				map.put("sheetReplaceList", sheetReplaceList);
			}*/
	
	
			HouseConfMsgEntity houseConfMsg = null;
			
			HouseBaseVo houseBaseVo = new HouseBaseVo();
			String zkSysValueForDeposit = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
			if("0".equals(zkSysValueForDeposit) && !Check.NuNStr(roomFid)){
				//如果新版本控制开关没有打开  && roomFid不为空（意味着不是第一次发布）,则设置rentWay=0，这样就能根据houseExt表中的押金code后去houseConf中的押金值
				houseBaseVo.setHouseFid(houseBaseFid);
				houseBaseVo.setRentWay(RentWayEnum.HOUSE.getCode());
			}else{
				//1，如果新版本控制开关没有打开  && roomFid为空（意味着这是第一次发布）    2，如果新版本打开了
				//如果是上述1  2  这两种情况，着正常走下去
				houseBaseVo.setHouseFid(houseBaseFid);
				houseBaseVo.setRentWay(houseBaseExt.getRentWay());
				houseBaseVo.setRoomFid(roomFid);
			}
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseIssueService.findHouseOrRoomDeposit(JsonEntityTransform.Object2Json(houseBaseVo)));
	
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "获取房源押金异常houseBaseFid={},msg={}",houseBaseFid, dto.getMsg());
				houseConfMsg = new HouseConfMsgEntity();
				houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
				houseConfMsg.setDicVal("0");
			}else{
				houseConfMsg = dto.parseData("houseConfMsg", new TypeReference<HouseConfMsgEntity>() {});
			}
	
			LogUtil.info(LOGGER, "获取押金:houseConfMsg={},houseFid={}", JsonEntityTransform.Object2Json(houseConfMsg),houseBaseFid);
	
			houseConfMsg.setDicVal(DataFormat.formatHundredPriceInt(Integer.valueOf(houseConfMsg.getDicVal())));
			map.put("houseConfMsg", houseConfMsg);
			//		map.put("exchange", dto.parseData("exchange", new TypeReference<HouseBaseExtEntity>() {	}));
			map.put("depositMin",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN);
			map.put("depositMax",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX);
		
		} catch (Exception e) {
			LogUtil.info(LOGGER, "error:{}", e);
		}
		return  "houseIssue/houseReleaseExtrasMsg";
	}


	/**
	 * 退订政策展示
	 */
	private void checkOutRulesShow(HttpServletRequest request,ModelMap map){
		//退订政策
		String checkOutRulesJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkOutRulesJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,code={}",
					TradeRulesEnum.TradeRulesEnum005.getValue());
			return ;
		}
		List<Map<String, Object>> checkOutList = new ArrayList<Map<String,Object>>();
		List<EnumVo> checkOutRulesList = dto.parseData("subDic", new TypeReference<List<EnumVo>>() {});

		boolean isNew = false;//新版本 修改退订政策
		
		Integer longTermLimit =null;
		
		try {
			//长租天数 设置
			String longTermLimitStr = cityTemplateService.getTextValue(null,TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
			longTermLimit = SOAResParseUtil.getValueFromDataByKey(longTermLimitStr, "textValue", Integer.class); 
		} catch (Exception e) {
			LogUtil.error(LOGGER, "长租入住最小天数查询失败e={}", e);
		}
		
		//退订政策显示
		for (EnumVo checkOutRules : checkOutRulesList) {
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
            }else{
                LogUtil.error(LOGGER, "error:{}", JsonEntityTransform.Object2Json(checkOutRules));
                return ;
            }
			
			String checkOutRulesValJson = cityTemplateService.getSelectSubDic(null, dicCode);
			DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(checkOutRulesValJson);
			if (dto1.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,code={}"
						, dicCode);
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
			if(!Check.NuNStrStrict(String.valueOf(checkOutRulesMap.get("checkOutRulesVal1")))){
				tradeRulesVo.setUnregText1(Integer.valueOf(String.valueOf(checkOutRulesMap.get("checkOutRulesVal1"))));
			}
			tradeRulesVo.setUnregText2(String.valueOf(checkOutRulesMap.get("checkOutRulesVal2")));
			tradeRulesVo.setUnregText3(String.valueOf(checkOutRulesMap.get("checkOutRulesVal3")));

			//严格
			if(dicCode.equals(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue())){

				TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.trans1ShowNameOld(tradeRulesVo);
				TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.trans1ShowNameOld(tradeRulesVo);
				TradeRulesEnum005001001Enum.TradeRulesEnum005001001002.trans1ShowNameOld(tradeRulesVo);

				checkOutRulesMap.put("checkOutRulesVal1", tradeRulesVo.getCheckInPreNameM());
				checkOutRulesMap.put("checkOutRulesVal2", tradeRulesVo.getCheckInOnNameM());
				checkOutRulesMap.put("checkOutRulesVal3", tradeRulesVo.getCheckOutEarlyNameM());

			}
			//适中
			if(dicCode.equals(TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue())){
				TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.trans1ShowNameOld(tradeRulesVo);
				TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.trans1ShowNameOld(tradeRulesVo);
				TradeRulesEnum005002001Enum.TradeRulesEnum005002001002.trans1ShowNameOld(tradeRulesVo);

				checkOutRulesMap.put("checkOutRulesVal1", tradeRulesVo.getCheckInPreNameM());
				checkOutRulesMap.put("checkOutRulesVal2", tradeRulesVo.getCheckInOnNameM());
				checkOutRulesMap.put("checkOutRulesVal3", tradeRulesVo.getCheckOutEarlyNameM());
			}
			//灵活
			if(dicCode.equals(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue())){
				TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.trans1ShowNameOld(tradeRulesVo);
				TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.trans1ShowNameOld(tradeRulesVo);
				TradeRulesEnum005003001Enum.TradeRulesEnum005003001002.trans1ShowNameOld(tradeRulesVo);

				checkOutRulesMap.put("checkOutRulesVal1", tradeRulesVo.getCheckInPreNameM());
				checkOutRulesMap.put("checkOutRulesVal2", tradeRulesVo.getCheckInOnNameM());
				checkOutRulesMap.put("checkOutRulesVal3", tradeRulesVo.getCheckOutEarlyNameM());
			}

			
			
			//新版本 修改退订政策
			if(!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal4"))
					&&!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal5"))
					&&!Check.NuNObj(checkOutRulesMap.get("checkOutRulesVal6"))){

				isNew =true;
				
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
				
				// 长租
                if(dicCode.equals(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue())){
                	if(!Check.NuNObj(longTermLimit)){
                		tradeRulesVo.setLongTermLimit(longTermLimit);
                	}
                    TradeRulesEnum005004001Enum.showContext(tradeRulesVo);
                }
                
				checkOutRulesMap.put("checkOutRulesVal1", tradeRulesVo.getCheckInPreNameM());
				checkOutRulesMap.put("checkOutRulesVal2", tradeRulesVo.getCheckInOnNameM());
				checkOutRulesMap.put("checkOutRulesVal3", tradeRulesVo.getCheckOutEarlyNameM());
				checkOutRulesMap.put("checkOutRulesVal4", tradeRulesVo.getCommonShowName());
			}

			checkOutRulesMap.put("tradeRulesVo", tradeRulesVo);
			checkOutList.add(checkOutRulesMap);
		}
		
		//说明
        if(isNew){
        	Map<String, Object> checkOutRulesMap = new HashMap<String, Object>();
        	checkOutRulesMap.put("checkOutRulesName", TradeRulesEnum005Enum.TradeRulesEnum005005.getName());
			checkOutRulesMap.put("checkOutRulesCode", TradeRulesEnum005Enum.TradeRulesEnum005005.getValue());
        	checkOutRulesMap.put("explain", TradeRulesEnum005005001Enum.getDefautExplain(longTermLimit));
        	checkOutList.add(checkOutRulesMap);
        }
        map.put("longTermLimit", longTermLimit);
		map.put("checkOutList", checkOutList);
	}

	/**
	 * 
	 * 保存房源补充信息
	 *
	 * @author zl 
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/saveExtrasInfo")
	@ResponseBody
	public DataTransferObject saveExtrasInfo(HttpServletRequest request,HouseBaseExtDescDto paramDto){

		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(paramDto.getHouseBaseFid())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源fid为空");
			LogUtil.error(LOGGER, dto.toJsonString());
			return dto;
		}

		//押金校验
		HouseConfMsgEntity houseConf = paramDto.getHouseConfMsgEntity();
		if (!Check.NuNObj(houseConf)) {
			if(houseConf.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
				String val = houseConf.getDicVal();
				if(!CheckIdCardUtils.isNum(val)){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("请输入正常的押金");
					return dto;
				}
				int depositVal =Check.NuNStrStrict(val)?0:Integer.parseInt(val);
				if(depositVal<SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN
						||depositVal>SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("押金范围在"+SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN+"元到"+SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX+"元");
					return dto;
				}
				houseConf.setDicVal(String.valueOf(depositVal*100));//转化成分
			}
			paramDto.setHouseConfMsgEntity(houseConf);
		}

		//所有押金规则都按固定收取
		paramDto.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());

		try {
			
			//保存修改记录 --- 查询历史数据
			String houseBaseJson=houseIssueService.searchHouseBaseMsgByFid(paramDto.getHouseBaseFid());
			HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseMsgEntity.class);
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(paramDto.getHouseBaseFid(),paramDto.getRoomFid(),houseBaseMsgEntity.getRentWay());
			String houseJson = houseIssueService.mergeHouseExtAndDesc(JsonEntityTransform.Object2Json(paramDto));
			dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseIssueService.mergeHouseExtAndDesc调用失败,结果:{}", houseJson);
			}
			
			//保存修改记录 --- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setHouseFid(paramDto.getHouseBaseFid());
				houseUpdateHistoryLogDto.setRentWay(houseBaseMsgEntity.getRentWay());
				houseUpdateHistoryLogDto.setRoomFid(paramDto.getRoomFid());
				houseUpdateLogService.saveHistoryLog(paramDto,houseUpdateHistoryLogDto);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}

		LogUtil.info(LOGGER, "结果:{}", JsonEntityTransform.Object2Json(dto));
		return dto;

	}
	
	/**
	 * 
	 * 获取房源基本信息和扩展信息——整租
	 *
	 * @author loushuai 
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 * @throws SOAParseException 
	 */
	public void getHouseBaseAndExtInfo(String houseBaseFid,HttpServletRequest request, HouseBaseExtDto houseBaseExt ) throws SOAParseException{
		if(Check.NuNObj(houseBaseExt.getHouseBaseExt().getCheckOutTime())||houseBaseExt.getHouseBaseExt().getCheckOutTime().equals("0")){
			houseBaseExt.getHouseBaseExt().setCheckOutTime(HouseConstant.DEFAULT_CHECKOUT_TIME);
		}
		if(Check.NuNObj(houseBaseExt.getHouseBaseExt().getCheckInTime())||houseBaseExt.getHouseBaseExt().getCheckInTime().equals("0")){
			houseBaseExt.getHouseBaseExt().setCheckInTime(HouseConstant.DEFAULT_CHECKIN_TIME);
		}
		request.setAttribute("houseBaseExtDto", houseBaseExt);
		String houseDescJson = houseIssueService.searchHouseDescAndBaseExt(houseBaseFid);
		DataTransferObject houseDescDto = JsonEntityTransform.json2DataTransferObject(houseDescJson);
		if (houseDescDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "houseIssueService.searchHouseDescAndBaseExt错误,houseBaseFid={},结果:{}", houseBaseFid, houseDescJson);
		} else {
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseUpdateLogService.houseFieldAuditLogVoConvertMap(houseBaseFid, null, houseBaseExt.getRentWay(), 0);
			HouseDescDto houseDesc = SOAResParseUtil.getValueFromDataByKey(houseDescJson, "obj", HouseDescDto.class);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Rules.getFieldPath())){
				houseDesc.setHouseRules(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Rules.getFieldPath()).getNewValue());
			}
			if(!Check.NuNObj(houseDesc)){
				request.setAttribute("houseDescDto", houseDesc);
			}
		}
	}
	
	/**
	 * 
	 * 获取房源基本信息和扩展信息——分租
	 *
	 * @author loushuai 
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 * @throws SOAParseException 
	 */
	public void getRoomBaseAndExtInfo(String houseBaseFid,String roomFid, HttpServletRequest request, HouseBaseExtDto houseBaseExt) throws SOAParseException{
		HouseRoomExtEntity houseRoomExt=null;
		if(!Check.NuNStr(roomFid)){
			String roomExtJson = houseIssueService.getRoomExtByRoomFid(roomFid);
			houseRoomExt=SOAResParseUtil.getValueFromDataByKey(roomExtJson, "roomExt", HouseRoomExtEntity.class);
		} else {
			String roomsJson=houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
			List<HouseRoomMsgEntity> roomList=SOAResParseUtil.getListValueFromDataByKey(roomsJson, "list", HouseRoomMsgEntity.class);
			if(!Check.NuNCollection(roomList)){
				String roomExtJ = houseIssueService.getRoomExtByRoomFid(roomList.get(0).getFid());
				houseRoomExt=SOAResParseUtil.getValueFromDataByKey(roomExtJ, "roomExt", HouseRoomExtEntity.class);
			}
		}
		Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseUpdateLogService.houseFieldAuditLogVoConvertMap(houseBaseFid, roomFid, houseBaseExt.getRentWay(), 0);
		HouseDescDto houseDesc = new HouseDescDto();
		if(!Check.NuNObj(houseRoomExt)){
			houseDesc.setHouseRules(houseRoomExt.getRoomRules());
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Room_Rules.getFieldPath())){
			houseDesc.setHouseRules(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Room_Rules.getFieldPath()).getNewValue());
		}
		request.setAttribute("houseDescDto", houseDesc);
		if(!Check.NuNObj(houseRoomExt)){
			this.exchageRoomExtInfo(houseBaseExt.getHouseBaseExt(), houseRoomExt);
		}
		if(Check.NuNObj(houseBaseExt.getHouseBaseExt().getCheckOutTime())||houseBaseExt.getHouseBaseExt().getCheckOutTime().equals("0")){
			houseBaseExt.getHouseBaseExt().setCheckOutTime(HouseConstant.DEFAULT_CHECKOUT_TIME);
		}
		if(Check.NuNObj(houseBaseExt.getHouseBaseExt().getCheckInTime())||houseBaseExt.getHouseBaseExt().getCheckInTime().equals("0")){
			houseBaseExt.getHouseBaseExt().setCheckInTime(HouseConstant.DEFAULT_CHECKIN_TIME);
		}
		request.setAttribute("houseBaseExtDto", houseBaseExt);
	}
	
	/**
	 * 
	 * 将roomExt对象中的字段信息替换掉tenantHouseDetailVo中的信息
	 *
	 * @author loushuai
	 * @created 2017年6月22日 下午3:29:56
	 *
	 * @param tenantHouseDetailVo
	 * @param roomExtEntity
	 */
	public void exchageRoomExtInfo(HouseBaseExtEntity houseBaseExt, HouseRoomExtEntity roomExtEntity){
        if(!Check.NuNObj(roomExtEntity.getOrderType())){
        	houseBaseExt.setOrderType(roomExtEntity.getOrderType());
		}
		if(!Check.NuNStr(roomExtEntity.getCheckOutRulesCode())){
			houseBaseExt.setCheckOutRulesCode(roomExtEntity.getCheckOutRulesCode());
		}
		if(!Check.NuNStr(roomExtEntity.getDepositRulesCode())){
			houseBaseExt.setDepositRulesCode(roomExtEntity.getDepositRulesCode());
		}
		if(!Check.NuNObj(roomExtEntity.getMinDay())){
			houseBaseExt.setMinDay(roomExtEntity.getMinDay());
		}
		if(!Check.NuNStr(roomExtEntity.getCheckInTime())){
			houseBaseExt.setCheckInTime(roomExtEntity.getCheckInTime());
		}
		if(!Check.NuNStr(roomExtEntity.getCheckOutTime())){
			houseBaseExt.setCheckOutTime(roomExtEntity.getCheckOutTime());
		}
	}


}
