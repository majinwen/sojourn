/**
 * @FileName: HouseIssueController.java
 * @Package com.ziroom.minsu.portal.fd.center.customer.controller
 * 
 * @author bushujie
 * @created 2016年8月5日 上午10:31:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.portal.fd.center.house.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.portal.fd.center.common.utils.UserUtils;
import com.ziroom.minsu.portal.fd.center.house.service.HouseUpdateLogService;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.GuardAreaService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.FacilityConfVo;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CoordinateTransforUtils;
import com.ziroom.minsu.services.common.utils.DecimalCalculate;
import com.ziroom.minsu.services.common.utils.Gps;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.house.api.inner.HouseBusinessService;
import com.ziroom.minsu.services.house.api.inner.HouseIssuePcService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.valenum.base.CityLevelEnum;
import com.ziroom.minsu.valenum.base.MapTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseConfStyleEnum;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.HouseUpdateLogEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源发布流程</p>
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
@RequestMapping("houseIssue")
@Controller
public class HouseIssueController {
	
	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name="house.houseIssuePcService")
	private HouseIssuePcService houseIssuePcService;
	
	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;
	
	@Value("#{'${BAIDU_AK}'.trim()}")
	private String baiduAk;
	
	@Resource(name = "basedata.guardAreaService")
	private GuardAreaService guardAreaService;
	
	@Resource(name = "house.houseBusinessService")
	private HouseBusinessService houseBusinessService;
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;


	@Resource(name = "fd.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(HouseIssueController.class);


	/**
	 * 
	 * 
	 *
	 * @author bushujie
	 * @created 2016年8月17日 下午8:44:20
	 *
	 * @return
	 */
	@RequestMapping("houseIssueOrAuth")
	public String houseIssueOrAuth(){
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
        String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
        CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});

        //身份信息
        Integer isIdentityAuth = entity.getIsIdentityAuth();
        //联系方式
        Integer isContactAuth = entity.getIsContactAuth();
        //真实头像
        Integer isUploadIcon = entity.getIsUploadIcon();
        
        //头像 个人介绍  昵称 
        Integer isFinishHead = YesOrNoEnum.NO.getCode();
        
        String nickName = entity.getNickName();
        Integer isFinishNickName = YesOrNoEnum.NO.getCode();
        if(!Check.NuNStr(nickName)){
        	isFinishNickName = YesOrNoEnum.YES.getCode();
        }
        Integer isFinishIntroduce = YesOrNoEnum.NO.getCode();
        DataTransferObject customerExtDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(uid));
        if(customerExtDto.getCode() == DataTransferObject.SUCCESS){
        	CustomerBaseMsgExtEntity customerBaseMsgExt = customerExtDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
        	if(!Check.NuNObj(customerBaseMsgExt) && !Check.NuNStr(customerBaseMsgExt.getCustomerIntroduce())){
        		isFinishIntroduce = YesOrNoEnum.YES.getCode();
        	}
        }
		
        if(isUploadIcon == YesOrNoEnum.YES.getCode() 
                && isFinishNickName == YesOrNoEnum.YES.getCode() 
                && isFinishIntroduce == YesOrNoEnum.YES.getCode()){
        	isFinishHead = YesOrNoEnum.YES.getCode();
        }
		
        int fullFlag = YesOrNoEnum.NO.getCode();
        if(isIdentityAuth == YesOrNoEnum.YES.getCode()
                && isContactAuth == YesOrNoEnum.YES.getCode()
                && isFinishHead == YesOrNoEnum.YES.getCode()){
            fullFlag = YesOrNoEnum.YES.getCode();
        }

        if(fullFlag == YesOrNoEnum.YES.getCode()){
            //如果当前已经填写完成,并且不需要校验，直接跳过走发布房源的流程
            return "redirect:/houseIssue/locationMsg";
        }else {
            return "redirect:/customer/dataAuthPage";
        }
	}
	
	/**
	 * 
	 * 初始化房源位置信息
	 *
	 * @author bushujie
	 * @created 2016年8月5日 下午6:02:07
	 *
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping(value = "/locationMsg")
	public String houseLocationMsg(HttpServletRequest request) throws SOAParseException{
		//房源分类
		String houseTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
		List<EnumVo> houseTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("houseTypeList", houseTypeList);
		
		request.setAttribute("baiduAk", baiduAk);
		request.setAttribute("clickStep", HouseIssueStepEnum.ONE.getCode());
		request.setAttribute("nowStep", 0);

		String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
		if(MapTypeEnum.BAIDU.getCode().equals(mapType)){
			return "/houseIssue/houseLocationMsg";
		}
		if(MapTypeEnum.GOOGLE.getCode().equals(mapType)
				||MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
			return "/houseIssue/houseLocationMsgGoogle";
		}
		return null;
	}
	
	/**
	 * 
	 * 更新房源位置信息
	 *
	 * @author bushujie
	 * @created 2016年8月22日 下午8:57:08
	 *
	 * @param fid
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping(value = "/locationMsg/{fid}")
	public String upLocationMsg(@PathVariable String fid,String roomFid, HttpServletRequest request) throws SOAParseException{
		request.setAttribute("roomFid", roomFid);
		//查询房源物理信息
		String phyJson=houseIssueService.searchHousePhyMsgByHouseBaseFid(fid);
		HousePhyMsgEntity housePhyMsgEntity=SOAResParseUtil.getValueFromDataByKey(phyJson, "obj", HousePhyMsgEntity.class);
		//查询房源基本信息
		String houseBaseJson=houseIssueService.searchHouseBaseAndExtByFid(fid);
		HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseJson, "obj", HouseBaseExtDto.class);
		/**要审核字段替换开始**/
		Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseUpdateLogService.houseFieldAuditLogVoConvertMap(houseBaseExtDto.getFid(), roomFid, houseBaseExtDto.getRentWay(), 0);
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_Nation_Code.getFieldPath())){
			housePhyMsgEntity.setNationCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_Nation_Code.getFieldPath()).getNewValue());
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_Province_Code.getFieldPath())){
			housePhyMsgEntity.setProvinceCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_Province_Code.getFieldPath()).getNewValue());
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_City_Code.getFieldPath())){
			housePhyMsgEntity.setCityCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_City_Code.getFieldPath()).getNewValue());
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_Area_Code.getFieldPath())){
			housePhyMsgEntity.setAreaCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_Area_Code.getFieldPath()).getNewValue());
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_House_Street.getFieldPath())){
			houseBaseExtDto.getHouseBaseExt().setHouseStreet(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_House_Street.getFieldPath()).getNewValue());
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Detail_Address.getFieldPath())){
			houseBaseExtDto.getHouseBaseExt().setDetailAddress(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Detail_Address.getFieldPath()).getNewValue());
		}
		/**要审核字段替换结束**/
		StringBuilder codeArr=new StringBuilder();
		StringBuilder nameArr=new StringBuilder();
		String nationJson=confCityService.getCityNameByCode(housePhyMsgEntity.getNationCode());
		codeArr.append(housePhyMsgEntity.getNationCode()).append(",");
		nameArr.append(SOAResParseUtil.getStrFromDataByKey(nationJson, "cityName")).append(",");
		String cityJson=confCityService.getCityNameByCode(housePhyMsgEntity.getCityCode());
		codeArr.append(housePhyMsgEntity.getCityCode());
		nameArr.append(SOAResParseUtil.getStrFromDataByKey(cityJson, "cityName"));
		if(SysConst.nation_code.equals(housePhyMsgEntity.getNationCode())){
			codeArr.append(",");
			nameArr.append(",");
            String areaJson=confCityService.getCityNameByCode(housePhyMsgEntity.getAreaCode());
            codeArr.append(housePhyMsgEntity.getAreaCode());
            nameArr.append(SOAResParseUtil.getStrFromDataByKey(areaJson, "cityName"));
		}
		request.setAttribute("codeArr", codeArr.toString());
		request.setAttribute("nameArr", nameArr.toString());
		request.setAttribute("housePhy", housePhyMsgEntity);
		request.setAttribute("houseBase", houseBaseExtDto);
		request.setAttribute("baiduAk", baiduAk);
		request.setAttribute("clickStep", HouseIssueStepEnum.ONE.getCode());
		if(houseBaseExtDto.getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
			request.setAttribute("nowStep", houseBaseExtDto.getOperateSeq());
		}else{
			request.setAttribute("nowStep", HouseIssueStepEnum.ONE.getCode());
		}

		//房源分类
		String houseTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
		List<EnumVo> houseTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("houseTypeList", houseTypeList);
		for(EnumVo vo:houseTypeList){
			if(vo.getKey().equals(houseBaseExtDto.getHouseType()+"")){
				request.setAttribute("houseTypeName", vo.getText());
				break;
			}
		}
		//判断部分信息是否能修改
		if(RentWayEnum.HOUSE.getCode()==houseBaseExtDto.getRentWay()){
			if (isModify(houseBaseExtDto.getHouseStatus())) {
				request.setAttribute("isModify", "0");
			} else {
				request.setAttribute("isModify", "1");
			}
		}else if(RentWayEnum.ROOM.getCode()==houseBaseExtDto.getRentWay()){
			String resultJson=houseIssueService.searchRoomListByHouseBaseFid(houseBaseExtDto.getFid());
			List<HouseRoomMsgEntity> roomList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomMsgEntity.class);
			request.setAttribute("isModify", "0");
			if(!Check.NuNCollection(roomList)){
				for(HouseRoomMsgEntity room : roomList){
					if(!isModify(room.getRoomStatus())){
						request.setAttribute("isModify", "1");
						break;
					}
				}
			}
		}
		
		//下架和强制下架能修改地址信息
		if( houseBaseExtDto.getHouseStatus() == HouseStatusEnum.XJ.getCode() || houseBaseExtDto.getHouseStatus() == HouseStatusEnum.QZXJ.getCode()){
			request.setAttribute("isModify", "0");
		}

		String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
		if(MapTypeEnum.BAIDU.getCode().equals(mapType)){
			if(!Check.NuNObjs(housePhyMsgEntity.getGoogleLatitude(),housePhyMsgEntity.getGoogleLongitude()) && Check.NuNObjs(housePhyMsgEntity.getLongitude(), housePhyMsgEntity.getLatitude())){
				Gps baiduGps = CoordinateTransforUtils.wgs84_To_bd09(housePhyMsgEntity.getGoogleLatitude(), housePhyMsgEntity.getGoogleLongitude());
				housePhyMsgEntity.setLatitude(baiduGps.getWgLat());
				housePhyMsgEntity.setLongitude(baiduGps.getWgLon());
			}
			return "/houseIssue/houseLocationMsg";
		}
		if(MapTypeEnum.GOOGLE.getCode().equals(mapType)
				||MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
			if(Check.NuNObjs(housePhyMsgEntity.getGoogleLatitude(),housePhyMsgEntity.getGoogleLongitude()) && !Check.NuNObjs(housePhyMsgEntity.getLongitude(), housePhyMsgEntity.getLatitude())){
				Gps googleGps = CoordinateTransforUtils.bd09_To_Gps84(housePhyMsgEntity.getLatitude(), housePhyMsgEntity.getLongitude());
				housePhyMsgEntity.setGoogleLatitude(googleGps.getWgLat());
				housePhyMsgEntity.setGoogleLongitude(googleGps.getWgLon());
			}
			return "/houseIssue/houseLocationMsgGoogle";
		}
		return null;
	}
	/**
	 *
	 * 房源基本信息
	 *
	 * @author bushujie
	 * @created 2016年8月5日 上午11:27:36
	 *
	 * @param fid
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping(value = "/basicMsg/{fid}")
	public String  houseBasicMsg(@PathVariable String fid, String roomFid, HttpServletRequest request) throws SOAParseException{
		//roomFid直接传到页面
		request.setAttribute("roomFid", roomFid);
		String resultJson=houseIssueService.searchHouseBaseAndExtByFid(fid);
		HouseBaseExtDto houseBaseExt=SOAResParseUtil.getValueFromDataByKey(resultJson, "obj",HouseBaseExtDto.class);
		request.setAttribute("houseBaseExt", houseBaseExt);
		/**要审核字段替换开始**/
		Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseUpdateLogService.houseFieldAuditLogVoConvertMap(fid, roomFid, houseBaseExt.getRentWay(), 0);
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Area.getFieldPath())){
			houseBaseExt.setHouseArea(Double.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Area.getFieldPath()).getNewValue()));
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Check_In_Limit.getFieldPath())){
			houseBaseExt.getHouseBaseExt().setCheckInLimit(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Check_In_Limit.getFieldPath()).getNewValue()));
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_house_Cleaning_Fees.getFieldPath())){
			houseBaseExt.setHouseCleaningFees(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_house_Cleaning_Fees.getFieldPath()).getNewValue()));
		}
		if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Lease_Price.getFieldPath())){
			houseBaseExt.setLeasePrice(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Lease_Price.getFieldPath()).getNewValue()));
		}
		/**要审核字段替换结束**/
		//入住人数限制
		String limitJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
		List<EnumVo> limitList=SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);
		request.setAttribute("limitList", limitList);
		if(!Check.NuNObj(houseBaseExt.getHouseBaseExt())){
			for(EnumVo vo:limitList){
				if(vo.getKey().equals(houseBaseExt.getHouseBaseExt().getCheckInLimit()+"")){
					request.setAttribute("checkInLimitName", vo.getText());
					break;
				}
			}
		}
		
		//清洁费比例
		String cleaningFeesJson=cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
		String cleaningFees=SOAResParseUtil.getStrFromDataByKey(cleaningFeesJson, "textValue");
		if(!Check.NuNStr(cleaningFees)){
			request.setAttribute("cleaningFees", Double.valueOf(cleaningFees));
		}
		if(!Check.NuNObj(houseBaseExt.getLeasePrice())){
			request.setAttribute("leasePrice",DecimalCalculate.div(houseBaseExt.getLeasePrice().toString(), "100", 2).doubleValue());
		}
		request.setAttribute("clickStep", HouseIssueStepEnum.TWO.getCode());
		if(houseBaseExt.getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
			request.setAttribute("nowStep", houseBaseExt.getOperateSeq());
		}else{
			request.setAttribute("nowStep", HouseIssueStepEnum.ONE.getCode());
		}
		
		
		//房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		request.setAttribute("priceLow", priceLow);
		
		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);
		
		//部分信息不可修改
		if (isModify(houseBaseExt.getHouseStatus())) {
			request.setAttribute("isModify", "0");
		}else{
			request.setAttribute("isModify", "1");
		}
		
		return "/houseIssue/houseBasicMsg";
	}


	/**
	 * 获取开通的城市列表(房东)
	 * @author lishaochuan
	 * @create 2017/2/27 18:06
	 * @param 
	 * @return 
	 */
	@RequestMapping("getOpenNationLandlord")
	@ResponseBody
	public List<Map> getOpenNationLandlord() throws SOAParseException{
		String resultJson = confCityService.getOpenNationLandlord();
		List<Map> openNationList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", Map.class);
		return openNationList;
	}

	/**
	 * 
	 * 父code查询开通城市列表
	 *
	 * @author bushujie
	 * @created 2016年8月5日 下午7:51:43
	 *
	 * @param code
	 * @return
	 * @throws SOAParseException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getSubAreaByParentCode")
	@ResponseBody
	public List<Map> getSubAreaByParentCode(String code,String level) throws SOAParseException{
		List<Map> openCityList=new ArrayList<Map>();
		//国家
		if(!Check.NuNStr(level) && String.valueOf(CityLevelEnum.NATION.getCode()).equals(level)){
			//String resultJson=confCityService.getOpenCity();
			String resultJson = confCityService.getOpenCityLandlord4Nation(code);
			openCityList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", Map.class);
			return openCityList;
		} else {
			String resultJson=confCityService.searchAreaListForLan(code);
			List<ConfCityEntity> areaList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", ConfCityEntity.class);
			for(ConfCityEntity cf:areaList){
				Map<String,String> map=new HashMap<String, String>();
				map.put("code", cf.getCode());
				map.put("name", cf.getShowName());
				openCityList.add(map);
			}
		}
		return openCityList;
	}

	/**
	 * 
	 * 保存房源位置信息
	 *  1. 如果是国外  不做区域code的判空
	 * @author bushujie
	 * @created 2016年8月6日 下午2:36:59
	 *
	 * @param housePhyPcDto
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("insertHouseLocationMsg")
	@ResponseBody
	public DataTransferObject insertHouseLocationMsg(HousePhyPcDto housePhyPcDto) throws SOAParseException{
		DataTransferObject dto=new DataTransferObject();
		//获取登录用户uid
		String uid=UserUtils.getCurrentUid();
		housePhyPcDto.setUid(uid);
		//校验必须填写的参数nationCode，cityCode，area_code,build_code
        if(Check.NuNStr(housePhyPcDto.getNationCode())){
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("请选择国家");
			return dto;
		}
        if(Check.NuNStr(housePhyPcDto.getCityCode())){
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("请选择城市");
        	return dto;
        }
        if(Check.NuNStr(housePhyPcDto.getAreaCode())
        		&&SysConst.nation_code.equals(housePhyPcDto.getNationCode())){
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("请选择行政区");
        	return dto;
        }
        if(Check.NuNStr(housePhyPcDto.getHouseStreet())){
        	dto.setErrCode(DataTransferObject.ERROR);
        	dto.setMsg("请填写房屋所在街道");
        	return dto;
        }
        //查询省级code
        String cityResult=confCityService.getConfCityByCode(housePhyPcDto.getCityCode());
        housePhyPcDto.setUid(uid);
        ConfCityEntity cityEntity=SOAResParseUtil.getValueFromDataByKey(cityResult, "cityEntity", ConfCityEntity.class);
        if(!Check.NuNObj(cityEntity)){
            housePhyPcDto.setProvinceCode(cityEntity.getPcode());
        }
		if(!SysConst.nation_code.equals(housePhyPcDto.getNationCode())){
			housePhyPcDto.setAreaCode("");
		}		
		//设置查询管家信息
		String customerResultJson = customerInfoService.getCustomerInfoByUid(uid);
		CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(customerResultJson, "customerBase",CustomerBaseMsgEntity.class);
		housePhyPcDto.setHouseGuardRel(setHouseGuardRel(housePhyPcDto,customerBase));


		// 如果是谷歌地图，保存时也修改百度坐标
		if(!Check.NuNObjs(housePhyPcDto.getGoogleLatitude(),housePhyPcDto.getGoogleLongitude()) && Check.NuNObjs(housePhyPcDto.getLongitude(), housePhyPcDto.getLatitude())){
			Gps baiduGps = CoordinateTransforUtils.wgs84_To_bd09(housePhyPcDto.getGoogleLatitude(), housePhyPcDto.getGoogleLongitude());
			housePhyPcDto.setLatitude(baiduGps.getWgLat());
			housePhyPcDto.setLongitude(baiduGps.getWgLon());
		}
		// 如果是百度地图，保存时也修改谷歌坐标
		if(Check.NuNObjs(housePhyPcDto.getGoogleLatitude(),housePhyPcDto.getGoogleLongitude()) && !Check.NuNObjs(housePhyPcDto.getLongitude(), housePhyPcDto.getLatitude())){
			Gps googleGps = CoordinateTransforUtils.bd09_To_Gps84(housePhyPcDto.getLatitude(), housePhyPcDto.getLongitude());
			housePhyPcDto.setGoogleLatitude(googleGps.getWgLat());
			housePhyPcDto.setGoogleLongitude(googleGps.getWgLon());
		}

		//保存修改记录 --- 查询历史数据
		HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(housePhyPcDto.getHouseBaseFid(),null, housePhyPcDto.getRentWay());
		//房源发布第一步保存信息
		String resultJson=houseIssuePcService.insertOrUpdateHouseLocation(JsonEntityTransform.Object2Json(housePhyPcDto));
		
		dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		
		if(dto.getCode() == DataTransferObject.SUCCESS){
			//保存修改记录 --- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setHouseFid(housePhyPcDto.getHouseBaseFid());
				houseUpdateHistoryLogDto.setRentWay(housePhyPcDto.getRentWay());
				houseUpdateLogService.saveHistoryLog(housePhyPcDto,houseUpdateHistoryLogDto);
			}
		}
		
		return dto;
	}
	
	/**
	 * 
	 * 房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年8月9日 下午2:16:46
	 *
	 * @param fid
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping(value = "/configMsg/{fid}")
	public String  houseConfigMsg(@PathVariable String fid,String roomFid, HttpServletRequest request) throws SOAParseException{
		//roomFid直接放到页面中，不做任何处理
		request.setAttribute("roomFid", roomFid);
		//查询房源基本信息
		String resultJson=houseIssueService.searchHouseBaseAndExtByFid(fid);
		HouseBaseExtDto houseBaseExt=SOAResParseUtil.getValueFromDataByKey(resultJson, "obj",HouseBaseExtDto.class);
		//配套设施列表
		String facilityJson=cityTemplateService.getSelectSubDic(null,ProductRulesEnum.ProductRulesEnum002.getValue());
		List<EnumVo> facilityList=SOAResParseUtil.getListValueFromDataByKey(facilityJson, "subDic", EnumVo.class);
		//服务列表
		String serviceJson=cityTemplateService.getEffectiveSelectEnum(null,ProductRulesEnum.ProductRulesEnum0015.getValue());
		List<EnumVo> serviceList=SOAResParseUtil.getListValueFromDataByKey(serviceJson, "selectEnum", EnumVo.class);
		//获取房源选中配套设施
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", fid);
		paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum002.getValue());
		String houseConfJson=houseIssuePcService.findHouseConfigByPcode(JsonEntityTransform.Object2Json(paramMap));
		List<HouseConfVo> confList=SOAResParseUtil.getListValueFromDataByKey(houseConfJson, "confList",HouseConfVo.class);
		List<String> houseConfStr=new ArrayList<String>();
		for(HouseConfVo vo:confList){
			houseConfStr.add(vo.getDicCode()+vo.getDicValue());
		}
		//获取房源选中服务
		paramMap.put("dicCode", ProductRulesEnum.ProductRulesEnum0015.getValue());
		String houseServerJson=houseIssuePcService.findHouseConfigByPcode(JsonEntityTransform.Object2Json(paramMap));
		List<HouseConfVo> serverList=SOAResParseUtil.getListValueFromDataByKey(houseServerJson, "confList",HouseConfVo.class);
		List<String> serverStr=new ArrayList<String>();
		for(HouseConfVo vo:serverList){
			serverStr.add(vo.getDicCode()+vo.getDicValue());
		}
		//整合数据
		List<FacilityConfVo> list=new ArrayList<FacilityConfVo>();
		//配套设施
		for(EnumVo vo:facilityList){
			FacilityConfVo confVo=new FacilityConfVo();
			confVo.setCode(vo.getKey());
			confVo.setName(vo.getText());
			for(EnumVo v:vo.getSubEnumVals()){
				FacilityConfVo fVo=new FacilityConfVo();
				fVo.setCode(vo.getKey());
				fVo.setName(v.getText());
				fVo.setValue(v.getKey());
				if(houseConfStr.contains(fVo.getCode()+fVo.getValue())){
					fVo.setSelected(1);
				} else {
					fVo.setSelected(0);
				}
				fVo.setStyleCss(HouseConfStyleEnum.getStyleCssByValue(fVo.getCode()+fVo.getValue()));
				confVo.getConfList().add(fVo);
			}
			list.add(confVo);
		}
		//服务
		FacilityConfVo confVo=new FacilityConfVo();
		confVo.setCode(ProductRulesEnum.ProductRulesEnum0015.getValue());
		confVo.setName(ProductRulesEnum.ProductRulesEnum0015.getName());
		for(EnumVo vo:serviceList){
			FacilityConfVo fVo=new FacilityConfVo();
			fVo.setCode(confVo.getCode());
			fVo.setName(vo.getText());
			fVo.setValue(vo.getKey());
			if(serverStr.contains(fVo.getCode()+fVo.getValue())){
				fVo.setSelected(1);
			} else {
				fVo.setSelected(0);
			}
			fVo.setStyleCss(HouseConfStyleEnum.getStyleCssByValue(fVo.getCode()+fVo.getValue()));
			confVo.getConfList().add(fVo);
		}
		list.add(confVo);
		request.setAttribute("confList", list);
		request.setAttribute("houseBaseFid", fid);
		request.setAttribute("clickStep", HouseIssueStepEnum.THREE.getCode());
		if(houseBaseExt.getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
			request.setAttribute("nowStep", houseBaseExt.getOperateSeq());
		}else{
			request.setAttribute("nowStep", HouseIssueStepEnum.TWO.getCode());
		}
		return "/houseIssue/houseConfigMsg";	
	}
	
	/**
	 * 
	 * 更新房源基本信息和扩展信息
	 *
	 * @author bushujie
	 * @created 2016年8月9日 下午6:55:15
	 *
	 * @param houseBaseDto
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("updateHouseBaseAndExt")
	@ResponseBody
	public DataTransferObject updateHouseBaseAndExt(HouseBasePcDto houseBasePcDto,HttpServletRequest request) throws ParseException{
		DataTransferObject dto=new DataTransferObject();
		//赋值扩展信息
		HouseBaseExtEntity houseBaseExtEntity=new HouseBaseExtEntity();
		houseBaseExtEntity.setHouseBaseFid(houseBasePcDto.getFid());
		houseBaseExtEntity.setCheckInLimit(houseBasePcDto.getCheckInLimit());
		houseBaseExtEntity.setIsTogetherLandlord(houseBasePcDto.getIsTogetherLandlord());
		//赋值基础信息
		HouseBaseExtDto houseBaseExtDto=new HouseBaseExtDto();
		BeanUtils.copyProperties(houseBasePcDto, houseBaseExtDto);
		if(!Check.NuNObj(houseBasePcDto.getDayPrice())){
			houseBaseExtDto.setLeasePrice(DecimalCalculate.mul(houseBasePcDto.getDayPrice().toString(), "100").intValue());
		}
		if(!Check.NuNObj(houseBasePcDto.getClearPrice())){
			houseBaseExtDto.setHouseCleaningFees(DecimalCalculate.mul(houseBasePcDto.getClearPrice().toString(), "100").intValue());
		}
		//houseBaseExtDto.setTillDate(DateUtil.parseDate(houseBasePcDto.getDateLimit(), "yyyy-MM-dd"));
		houseBaseExtDto.setHouseBaseExt(houseBaseExtEntity);
		//步骤和完整率
	/*	if(houseBasePcDto.getOperateSeq()==null||houseBasePcDto.getOperateSeq()<HouseIssueStepEnum.TWO.getCode()){
			houseBaseExtDto.setOperateSeq(HouseIssueStepEnum.TWO.getCode());
			houseBaseExtDto.setIntactRate(HouseIssueStepEnum.TWO.getValue());
		}*/
		if(houseBasePcDto.getOperateSeq()==null||(!Check.NuNObj(houseBasePcDto.getOperateSeq()) && houseBasePcDto.getOperateSeq().intValue() < HouseIssueStepEnum.SEVEN.getCode())){
			houseBaseExtDto.setIntactRate(HouseIssueStepEnum.TWO.getValue());
		}
		//判断清洁费比例
		String cleaningFees=request.getParameter("cleaningFees");
		if(!Check.NuNStr(cleaningFees)&&!Check.NuNObj(houseBasePcDto.getClearPrice())){
			if(Math.floor(houseBasePcDto.getDayPrice()*Double.valueOf(cleaningFees))<houseBasePcDto.getClearPrice()){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("您的清洁费太高了，最高可设置为"+Math.floor(houseBasePcDto.getDayPrice()*Double.valueOf(cleaningFees))+"元");
				return dto;
			}
		}
		
		//保存修改记录 --- 查询历史数据
		HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(houseBaseExtDto.getFid(),null, houseBaseExtDto.getRentWay());

		String resultJson=houseIssueService.updateHouseBaseAndExt(JsonEntityTransform.Object2Json(houseBaseExtDto));
		dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		
		if(dto.getCode() == DataTransferObject.SUCCESS){
			//保存修改记录 --- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setHouseFid(houseBasePcDto.getFid());
				houseUpdateHistoryLogDto.setRentWay(houseBasePcDto.getRentWay());
				if(houseBasePcDto.getRentWay()==RentWayEnum.ROOM.getCode()){
					houseUpdateHistoryLogDto.setRoomFid(request.getParameter("roomFid"));
				}
				houseUpdateLogService.saveHistoryLog(houseBaseExtDto,houseUpdateHistoryLogDto);
			}
		}
	
		return dto;
	}
	
	/**
	 * 
	 * 保存或者更新房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年8月11日 上午11:09:36
	 *
	 * @param confCode
	 * @param fid
	 * @return
	 */
	@RequestMapping("insertOrUpHouseConf")
	@ResponseBody
	public DataTransferObject insertOrUpHouseConf(String confCode,String fid){
		List<HouseConfMsgEntity> confList=new ArrayList<HouseConfMsgEntity>();
		if(!Check.NuNStr(confCode)){
			String[] confArr=confCode.split(",");
			for(String conf:confArr){
				HouseConfMsgEntity confMsgEntity=new HouseConfMsgEntity();
				confMsgEntity.setFid(UUIDGenerator.hexUUID());
				confMsgEntity.setHouseBaseFid(fid);
				confMsgEntity.setDicCode(conf.split(":")[0]);
				confMsgEntity.setDicVal(conf.split(":")[1]);
				confList.add(confMsgEntity);
			}
		}
		String resultJson=houseIssuePcService.updateHouseConf(JsonEntityTransform.Object2Json(confList));
		DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		dto.putValue("confCode", confCode);
		return dto;
	}

	/**
	 * 选择添加整租或者分租房间信息
	 *
	 * @param fid
	 * @return
	 * @throws SOAParseException
	 * @author bushujie
	 * @created 2016年8月17日 下午9:33:57
	 */
	@RequestMapping(value = "/toWholeOrSublet/{fid}")
	public String toWholeOrSublet(@PathVariable String fid, String roomFid) throws SOAParseException {
		//查询房源基本信息
		String resultJson = houseIssueService.searchHouseBaseAndExtByFid(fid);
		HouseBaseExtDto houseBaseExt = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseBaseExtDto.class);
		if (RentWayEnum.HOUSE.getCode() == houseBaseExt.getRentWay()) {
			return "redirect:/houseIssue/roomWhole/" + fid;
		} else if (RentWayEnum.ROOM.getCode() == houseBaseExt.getRentWay()) {
			return "redirect:/houseIssue/rooms/" + fid + "?roomFid=" + (Check.NuNStr(roomFid) ? "" : roomFid);
		}
		return null;
	}
	/**
	 * 
	 * 整租房间信息
	 *
	 * @author bushujie
	 * @created 2016年8月11日 下午3:47:24
	 *
	 * @param fid
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping(value = "/roomWhole/{fid}")
	public String houseRoomWhole(@PathVariable String fid,HttpServletRequest request) throws SOAParseException{
		String roomListJson = houseIssuePcService.findHouseRoomWithBedsList(fid);
		HouseRoomsWithBedsPcDto houseRoom=SOAResParseUtil.getValueFromDataByKey(roomListJson, "roomList", HouseRoomsWithBedsPcDto.class);
		//2 床类型
		String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
		//3 床规格 去掉床规格
		String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
		List<EnumVo> bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
		/*List<EnumVo> bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);*/
		request.setAttribute("bedTypeList", bedTypeList);
		/*request.setAttribute("bedSizeList", bedSizeList);*/
		request.setAttribute("houseRoom", houseRoom);
		request.setAttribute("clickStep", HouseIssueStepEnum.FIVE.getCode());
		if(houseRoom.getHouseBaseMsgEntity().getOperateSeq().intValue()>=HouseIssueStepEnum.SEVEN.getCode()){
			request.setAttribute("nowStep", houseRoom.getHouseBaseMsgEntity().getOperateSeq());
		}else{
			request.setAttribute("nowStep", HouseIssueStepEnum.FOUR.getCode());
		}
		//房源户型不能修改
		if (isModify(houseRoom.getHouseBaseMsgEntity().getHouseStatus())) {
			request.setAttribute("isModify", "0");
		}else{
			request.setAttribute("isModify", "1");
		}
		/**要审核字段替换开始**/
		if(HouseStatusEnum.ZPSHWTG.getCode() == houseRoom.getHouseBaseMsgEntity().getHouseStatus()){
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap = houseFieldAuditLogVoConvertMap(fid, null, RentWayEnum.HOUSE.getCode(), 0);
			if(Check.NuNMap(houseFieldAuditMap)){
				return "/houseIssue/houseRoomWhole";
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath())){
				houseRoom.getHouseBaseMsgEntity().setRoomNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())){
				houseRoom.getHouseBaseMsgEntity().setHallNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath())){
				houseRoom.getHouseBaseMsgEntity().setToiletNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath())){
				houseRoom.getHouseBaseMsgEntity().setKitchenNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath())){
				houseRoom.getHouseBaseMsgEntity().setBalconyNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath()).getNewValue()));
			}
		}
		/**要审核字段替换结束**/
		return "/houseIssue/houseRoomWhole";
	}
	
	/**
	 * 
	 * 整租删除房间
	 *
	 * @author bushujie
	 * @created 2016年8月16日 下午8:35:38
	 *
	 * @param roomFid
	 * @return
	 */
	@RequestMapping("delZroomByFid")
	@ResponseBody
	public DataTransferObject delZroomByFid(String roomFids){
		DataTransferObject dto=new DataTransferObject();
		if(!Check.NuNStr(roomFids)){
			for(String roomFid:roomFids.split(",")){
				String resultJson=houseIssuePcService.delZRoomByFid(roomFid);
				dto=JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode()!=0){
					return dto;
				}
			}
		}
		return dto;
	}
	
	/**
	 * 
	 * 保存或更新整租房间信息
	 *
	 * @author bushujie
	 * @created 2016年8月16日 下午9:20:59
	 *
	 * @param houseInfo
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("saveOrUpdateZroom")
	@ResponseBody
	public DataTransferObject saveOrUpdateZroom(String houseInfo) throws SOAParseException{
		LogUtil.info(LOGGER, "方法【saveOrUpdateZroom】，保存整租房间信息参数houseInfo:{}", houseInfo);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(houseInfo)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			return dto;
		}
		JSONObject houseObj = JSONObject.parseObject(houseInfo);
		JSONArray roomsArray = houseObj.getJSONArray("rooms");
		String houseFid = houseObj.getString("houseFid");
		Integer toiletNum = houseObj.getInteger("toiletNum");
		Integer roomNum=houseObj.getInteger("roomNum");
		if(Check.NuNObj(toiletNum) || toiletNum.intValue() == 0){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("卫生间数量至少为1");
			return dto;
		}
		if(Check.NuNStr(houseFid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房源Fid为空");
			return dto;
		}
		if(Check.NuNObj(roomsArray)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间信息为空");
			return dto;
		}
		//判断房间数量是否大于户型
		if(!Check.NuNObj(roomsArray)&&roomNum<roomsArray.size()){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间数量不能大于户型");
			return dto;
		}
		String resultJson = houseIssuePcService.saveOrUpdateZroom(houseInfo);
		dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		dto.putValue("houseBaseFid", houseFid);
	
		return dto;
	}
	
	/**
	 * 
	 * 是否可以修改
	 * 
	 *
	 * @author jixd
	 * @created 2016年8月31日 上午11:53:00
	 *
	 * @param status
	 * @return
	 */
	private boolean isModify(int status){
		return status == HouseStatusEnum.DFB.getCode() || status == HouseStatusEnum.XXSHWTG.getCode()
				|| status == HouseStatusEnum.ZPSHWTG.getCode() || status == HouseStatusEnum.XJ.getCode()
				|| status == HouseStatusEnum.QZXJ.getCode();
	}
	
	/**
	 * 
	 * 获取房源运营专员
	 *
	 * @author bushujie
	 * @created 2016年8月24日 上午11:58:07
	 *
	 * @param housePhyMsg
	 * @param customerVo
	 * @return
	 * @throws SOAParseException 
	 */
	private HouseGuardRelEntity setHouseGuardRel(HousePhyPcDto housePhyPcDto,CustomerBaseMsgEntity customerBase) throws SOAParseException{
		HouseGuardRelEntity houseGuardRelEntity  = null;
		if(Check.NuNObj(housePhyPcDto)||Check.NuNObj(customerBase)) {
			return houseGuardRelEntity;
		}
    	
		// 地推管家岗位已取消 modified by liujun 2017-02-24
    	/*if(!Check.NuNStr(customerBase.getCustomerMobile())){
    		HouseBusinessMsgExtDto houseBusinessMsgExt = new HouseBusinessMsgExtDto();
    		houseBusinessMsgExt.setLandlordMobile(customerBase.getCustomerMobile());
    		String pushJson = this.houseBusinessService
    				.findHouseBusExtByCondition(JsonEntityTransform.Object2Json(houseBusinessMsgExt));
    		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(pushJson);
    		if(dto.getCode() == DataTransferObject.SUCCESS){
				List<HouseBusinessMsgExtEntity> listExtEntities = SOAResParseUtil
						.getListValueFromDataByKey(pushJson, "listExtEntities", HouseBusinessMsgExtEntity.class);
				for (HouseBusinessMsgExtEntity houseBusines : listExtEntities) {
					if (!Check.NuNStr(houseBusines.getDtGuardCode())) {
						//如果有地推管家则渠道为地推
						housePhyPcDto.setHouseChannel(HouseChannelEnum.CH_DITUI.getCode());
						houseGuardRelEntity = new HouseGuardRelEntity();
						houseGuardRelEntity.setFid(UUIDGenerator.hexUUID());
						houseGuardRelEntity.setCreateFid(customerBase.getUid());
						houseGuardRelEntity.setEmpPushCode(houseBusines.getDtGuardCode());
						houseGuardRelEntity.setEmpPushName(houseBusines.getDtGuardName());
						houseGuardRelEntity.setEmpGuardCode(houseBusines.getDtGuardCode());
						houseGuardRelEntity.setEmpGuardName(houseBusines.getDtGuardName());
						break;
					}
				}
    		}
    	}*/
    	
    	// 区域专员关系表中随机分配运营专员
    	if (Check.NuNObj(houseGuardRelEntity)) {
    		GuardAreaRequest guardAreaRequest = new GuardAreaRequest();
    		guardAreaRequest.setAreaCode(housePhyPcDto.getAreaCode());
    		guardAreaRequest.setCityCode(housePhyPcDto.getCityCode());
    		guardAreaRequest.setNationCode(housePhyPcDto.getNationCode());
    		guardAreaRequest.setProvinceCode(housePhyPcDto.getProvinceCode());
    		guardAreaRequest.setIsDel(IsDelEnum.NOT_DEL.getCode());
    		String guardJson = this.guardAreaService.findGuardAreaByCode(JsonEntityTransform.Object2Json(guardAreaRequest));
    		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(guardJson);
    		
    		if(dto.getCode() ==DataTransferObject.SUCCESS){
    			GuardAreaEntity guardArea = SOAResParseUtil.getValueFromDataByKey(guardJson, "guardArea", GuardAreaEntity.class);
    			if(!Check.NuNObj(guardArea)){
    				houseGuardRelEntity = new HouseGuardRelEntity();
    				houseGuardRelEntity.setFid(UUIDGenerator.hexUUID());
    				houseGuardRelEntity.setEmpGuardCode(guardArea.getEmpCode());
    				houseGuardRelEntity.setEmpGuardName(guardArea.getEmpName());
    				houseGuardRelEntity.setCreateFid(customerBase.getUid());
    			}
    		}
		}
    	
		return houseGuardRelEntity;
	}
	
	/**
	 * 
	 * 待审核字段值map
	 *
	 * @author bushujie
	 * @created 2017年8月2日 上午9:59:15
	 *
	 * @param list
	 * @return
	 * @throws SOAParseException 
	 */
	public Map<String , HouseFieldAuditLogVo> houseFieldAuditLogVoConvertMap(String houseFid,String roomFid,Integer rentWay,Integer fieldAuditStatu) throws SOAParseException{
		Map<String , HouseFieldAuditLogVo> resultMap=new HashMap<String,HouseFieldAuditLogVo>();
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlog=new HouseUpdateFieldAuditNewlogEntity();
		houseUpdateFieldAuditNewlog.setHouseFid(houseFid);
		houseUpdateFieldAuditNewlog.setRentWay(rentWay);
		houseUpdateFieldAuditNewlog.setRoomFid(roomFid);
		houseUpdateFieldAuditNewlog.setFieldAuditStatu(fieldAuditStatu);
		String resultJson=troyHouseMgtService.getHouseUpdateFieldAuditNewlogByCondition(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlog));
		List<HouseFieldAuditLogVo> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseFieldAuditLogVo.class);
		for(HouseFieldAuditLogVo vo:list){
			resultMap.put(vo.getFieldPath(), vo);
		}
		return resultMap;
	}


}
