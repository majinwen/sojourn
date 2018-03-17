package com.ziroom.minsu.mapp.house.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.services.common.utils.ZkUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.mapp.common.enumvalue.SourceTypeEnum;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.GuardAreaService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.entity.entityenum.GoogleBaiduCoordinateEnum;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.HouseBusinessService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.entity.HouseMsgVo;
import com.ziroom.minsu.valenum.base.MapTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseIssueStepEnum;
import com.ziroom.minsu.valenum.house.HouseSourceEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;

/**
 * <p>房源发布：房源类型、出租方式、物理信息及地图</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年5月25日
 * @since 1.0
 * @version 1.0
 */
@RequestMapping("houseDeploy")
@Controller
public class HouseDeployController {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseDeployController.class);
	
	private static String ZERO_STRING = "0";
	
	@Value("${BAIDU_AK}")
	private String baiduAk;
	
	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;
	
	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "basedata.guardAreaService")
	private GuardAreaService guardAreaService;
	
	@Resource(name = "house.houseBusinessService")
	private HouseBusinessService houseBusinessService;


	@Resource(name="basedata.zkSysService")
	private ZkSysService zkSysService;
	/**
	 * 跳转【1.房源类型】页面
	 * @author lishaochuan
	 * @create 2016年5月25日下午5:54:05
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toHouseType")
	public String toHouseType(HttpServletRequest request) throws SOAParseException{
		//提示升级
		String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
		if ("1".equals(zkSysValue)){
			return "common/upgrade";
		}

		String houseBaseFid = request.getParameter("houseBaseFid");
		
		//房源分类
		String houseTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
		List<EnumVo> houseTypeList = SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("houseTypeList", houseTypeList);
		return "house/houseIssue/houseType";
	}
	
	
	
	/**
	 * 跳转【2.出租方式】页面
	 * @author lishaochuan
	 * @create 2016年5月25日下午8:52:09
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toRentWay")
	public String toRentWay(HttpServletRequest request){
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseType = request.getParameter("houseType");
		
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("houseType", houseType);
		
		return "house/houseIssue/rentWay";
	}
	
	
	/**
	 * 跳转【3.位置信息】页面
	 * @author lishaochuan
	 * @create 2016年5月25日下午9:32:45
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toLocation")
	public String toLocation(HttpServletRequest request) throws SOAParseException{
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseType = request.getParameter("houseType");
		String rentWay = request.getParameter("rentWay");
		if(Check.NuNStr(houseType) || Check.NuNStr(rentWay)){
			LogUtil.error(LOGGER, "当前房源类型或租住方式不存在,houseBaseFid={},houseType={},rentWay={}", houseBaseFid, houseType, rentWay);
			throw new BusinessException("房源fid不存在，或者出租方式不存在");
		}
		
		
		if (!Check.NuNStr(houseBaseFid)) {
			// 如果有houseBaseFid，则需带出位置信息
			
			DataTransferObject phyDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHousePhyMsgByHouseBaseFid(houseBaseFid));
			HousePhyMsgEntity phy = SOAResParseUtil.getValueFromDataByKey(phyDto.toJsonString(), "obj", HousePhyMsgEntity.class);
			
			String nationCode = phy.getNationCode();
			String provinceCode = phy.getProvinceCode();
			String cityCode = phy.getCityCode();
			String areaCode = phy.getAreaCode();
			String housePhyFid =phy.getFid();
			
			List<String> codeList = new ArrayList<String>();
			codeList.add(nationCode);
			codeList.add(provinceCode);
			codeList.add(cityCode);
			codeList.add(areaCode);
			String locationName = this.getLocationNameByCodeList(codeList, true);
			
			// 地区
			request.setAttribute("nationCode", nationCode);
			request.setAttribute("provinceCode", provinceCode);
			request.setAttribute("cityCode", cityCode);
			request.setAttribute("areaCode", areaCode);
			request.setAttribute("locationName", locationName);
			request.setAttribute("housePhyFid", housePhyFid);
			
			// 小区名称
			String communityName = phy.getCommunityName();
			request.setAttribute("communityName", communityName);
			
			
			DataTransferObject extDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid));
			HouseBaseExtDto baseExt = SOAResParseUtil.getValueFromDataByKey(extDto.toJsonString(), "obj", HouseBaseExtDto.class);
			
			// 街道名称
			String houseStreet = baseExt.getHouseBaseExt().getHouseStreet();
			request.setAttribute("houseStreet", houseStreet);
			
			// 楼号门牌号
			String detailAddress = baseExt.getHouseBaseExt().getDetailAddress();
			request.setAttribute("detailAddress", detailAddress);
		}
		

		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("houseType", houseType);
		request.setAttribute("rentWay", rentWay);
		request.setAttribute("baiduAk", baiduAk);
		
		String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
		//mapType =ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
		if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
			return "house/houseIssue/locationGoogle";
		}
			
		return "house/houseIssue/location";
	}
	
	
	/**
	 * 跳转【3.位置信息】页面，从详细页面跳回的
	 * @author lishaochuan
	 * @create 2016年5月26日下午12:04:32
	 * @param request
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toLocationFromOthers")
	public String toLocationFromOthers(HttpServletRequest request) throws SOAParseException{
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseType = request.getParameter("houseType");
		String rentWay = request.getParameter("rentWay");
		
		// 房源类型、租住方式
		request.setAttribute("houseType", houseType);
		request.setAttribute("rentWay", rentWay);
		
		String nationCode = request.getParameter("nationCode");
		String provinceCode = request.getParameter("provinceCode");
		String cityCode = request.getParameter("cityCode");
		String areaCode = request.getParameter("areaCode");
		
		// 地区
		List<String> codeList = new ArrayList<String>();
		codeList.add(nationCode);
		codeList.add(cityCode);
		if(SysConst.nation_code.equals(nationCode)){
			codeList.add(provinceCode);
			codeList.add(areaCode);
		}
		
		String locationName = this.getLocationNameByCodeList(codeList, true);
		request.setAttribute("nationCode", nationCode);
		request.setAttribute("provinceCode", provinceCode);
		request.setAttribute("cityCode", cityCode);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("locationName", locationName);
		
		// 小区名称
		String communityName = request.getParameter("communityName");
		request.setAttribute("communityName", communityName);
		
		// 街道
		String houseStreet = request.getParameter("houseStreet");
		request.setAttribute("houseStreet", houseStreet);
		
		//楼号-门牌号信息 
		String detailAddress = request.getParameter("detailAddress");
		request.setAttribute("detailAddress", detailAddress);
		
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("baiduAk", baiduAk);
		String housePhyFid = request.getParameter("housePhyFid");
		request.setAttribute("housePhyFid", housePhyFid);
		
		String flagStr=request.getParameter("flag");
		Integer flag=0;
		if(!Check.NuNStr(flagStr)){
			flag=Integer.valueOf(flagStr);
		}
		request.setAttribute("flag", flag);
		request.setAttribute("houseRoomFid", request.getParameter("houseRoomFid"));
		String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
		if(flag==1){
			if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
				return "house/upLocationGoogle";
			}
			return "house/upLocation";
		}
			
		if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
			return "house/houseIssue/locationGoogle";
		}
		return "house/houseIssue/location";
	}
	
	
	/**
	 * 跳转【3.位置信息】页面，直接根据houseBaseFid跳转到第3页
	 * @author lishaochuan
	 * @create 2016年5月30日上午1:48:12
	 * @param request
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toLocationThree")
	public String toLocationThree(HttpServletRequest request) throws SOAParseException{
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseRoomFid = request.getParameter("houseRoomFid");
		String flagStr=request.getParameter("flag");
		Integer flag=0;
		if(!Check.NuNStr(flagStr)){
			flag=Integer.valueOf(flagStr);
		}
		if(Check.NuNStr(houseBaseFid)){
			LogUtil.error(LOGGER, "当前房源houseBaseFid={}", houseBaseFid);
			throw new BusinessException("房源fid不存在");
		}
		
		DataTransferObject phyDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHousePhyMsgByHouseBaseFid(houseBaseFid));
		HousePhyMsgEntity phy = SOAResParseUtil.getValueFromDataByKey(phyDto.toJsonString(), "obj", HousePhyMsgEntity.class);
		
		String nationCode = phy.getNationCode();
		String provinceCode = phy.getProvinceCode();
		String cityCode = phy.getCityCode();
		String areaCode = phy.getAreaCode();
		String housePhyFid =phy.getFid();
		
		List<String> codeList = new ArrayList<String>();
		codeList.add(nationCode);
		codeList.add(cityCode);
		if(SysConst.nation_code.equals(nationCode)){
			codeList.add(provinceCode);
			codeList.add(areaCode);
		}
		
		String locationName = this.getLocationNameByCodeList(codeList, true);
		
		// 地区
		request.setAttribute("nationCode", nationCode);
		request.setAttribute("provinceCode", provinceCode);
		request.setAttribute("cityCode", cityCode);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("locationName", locationName);
		request.setAttribute("housePhyFid", housePhyFid);
		
		
		// 小区名称
		String communityName = phy.getCommunityName();
		request.setAttribute("communityName", communityName);
		
		
		DataTransferObject extDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid));
		HouseBaseExtDto baseExt = SOAResParseUtil.getValueFromDataByKey(extDto.toJsonString(), "obj", HouseBaseExtDto.class);
		
		// 街道名称
		String houseStreet = baseExt.getHouseBaseExt().getHouseStreet();
		request.setAttribute("houseStreet", houseStreet);
		
		// 楼号-门牌号信息 
		String detailAddress = baseExt.getHouseBaseExt().getDetailAddress();
		if(Check.NuNStr(detailAddress) && flag==1){
			String houseBaseExtJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
			HouseBaseExtEntity houseBaseExt=null;
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
			if(!Check.NuNObj(houseBaseExtDto)){
				if(!Check.NuNObj(houseBaseExtDto.getHouseBaseExt())){
					houseBaseExt = houseBaseExtDto.getHouseBaseExt();
				}
			}
			if (houseBaseExt!=null) {				
				StringBuilder detailAddressBuilder = new StringBuilder();
				detailAddressBuilder.append(Check.NuNStr(houseBaseExt.getBuildingNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getBuildingNum()) ? "" : (houseBaseExt.getBuildingNum() + "号楼"));
				detailAddressBuilder.append(Check.NuNStr(houseBaseExt.getUnitNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getUnitNum()) ? "" : (houseBaseExt.getUnitNum() + "单元"));
				detailAddressBuilder.append(Check.NuNStr(houseBaseExt.getFloorNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getFloorNum()) ? "" : (houseBaseExt.getFloorNum() + "层"));
				detailAddressBuilder.append(Check.NuNStr(houseBaseExt.getHouseNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getHouseNum()) ? "" : (houseBaseExt.getHouseNum() + "号"));
				
				detailAddress = detailAddressBuilder.toString();
			}
			
			detailAddress = detailAddress.replaceAll(" ", "");//不能有空格
			
		}
		request.setAttribute("detailAddress", detailAddress);
		
		// 房源类型
		request.setAttribute("houseType", baseExt.getHouseType());
		
		// 租住方式
		request.setAttribute("rentWay", baseExt.getRentWay());

		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("houseRoomFid", houseRoomFid);
		request.setAttribute("baiduAk", baiduAk);
		
		request.setAttribute("houseRoomFid", request.getParameter("houseRoomFid"));
		request.setAttribute("flag", flag);
		
		String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
		//判断是否是更新
		if(flag==1){
			if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
				request.setAttribute("longitude",phy.getGoogleLongitude());
				request.setAttribute("latitude",  phy.getGoogleLatitude());
				return "house/upLocationGoogle";
			}
			request.setAttribute("longitude",phy.getLongitude());
			request.setAttribute("latitude",  phy.getLatitude());
			return "house/upLocation";
		}
			
		if(MapTypeEnum.GOOGLE.getCode().equals(mapType)){
			return "house/houseIssue/locationGoogle";
		}
		return "house/houseIssue/location";
	}
	
	
	
	/**
	 * 根据cityCodeList获取地区名称
	 * @author lishaochuan
	 * @create 2016年5月26日下午12:07:47
	 * @param codeList
	 * @return
	 * @throws SOAParseException
	 */
	private String getLocationNameByCodeList(List<String> codeList, boolean needBlank) throws SOAParseException{
		DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeList)));
		List<ConfCityEntity> cityList = SOAResParseUtil.getListValueFromDataByKey(cityDto.toJsonString(), "cityList", ConfCityEntity.class);
		
		String nationName = "";
		String provinceName = "";
		String cityName = "";
		String areaName = "";
		for (ConfCityEntity confCityEntity : cityList) {
			if(confCityEntity.getLevel() == 1){
				nationName = confCityEntity.getShowName();
			}
			if(confCityEntity.getLevel() == 2){
				provinceName = confCityEntity.getShowName();
			}
			if(confCityEntity.getLevel() == 3){
				cityName = confCityEntity.getShowName();
			}
			if(confCityEntity.getLevel() == 4){
				areaName = confCityEntity.getShowName();
			}
		}
		if(needBlank){
			return nationName + " " + provinceName + " " + cityName + " " + areaName;
		}else{
			return nationName + provinceName + cityName + areaName;
		}
	}
	
	
	/**
	 * 跳转【3.1位置信息-地区】页面
	 * 跳转【3.2位置信息-小区名称】页面
	 * 跳转【3.3位置信息-街道信息】页面
	 * @author lishaochuan
	 * @create 2016年5月25日下午11:04:46
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toLocationOthers")
	public String toLocationOthers(HttpServletRequest request){
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseType = request.getParameter("houseType");
		String rentWay = request.getParameter("rentWay");
		String nationCode = request.getParameter("nationCode");
		String provinceCode = request.getParameter("provinceCode");
		String cityCode = request.getParameter("cityCode");
		String areaCode = request.getParameter("areaCode");
		String communityName = request.getParameter("communityName");
		String houseStreet = request.getParameter("houseStreet");
		String flag=request.getParameter("flag");
		String houseRoomFid=request.getParameter("houseRoomFid");
		String detailAddress=request.getParameter("detailAddress");
		String housePhyFid=request.getParameter("housePhyFid");
		
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("houseType", houseType);
		request.setAttribute("rentWay", rentWay);
		request.setAttribute("nationCode", nationCode);
		request.setAttribute("provinceCode", provinceCode);
		request.setAttribute("cityCode", cityCode);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("communityName", communityName);
		request.setAttribute("houseStreet", houseStreet);
		request.setAttribute("detailAddress", detailAddress);
		
		request.setAttribute("flag", flag);
		request.setAttribute("houseRoomFid", houseRoomFid);
		request.setAttribute("housePhyFid", housePhyFid);
		
		String loationPage = request.getParameter("loationPage");
		if("1".equals(loationPage)){
			//String resultJson = confCityService.getConfCitySelect();
			String resultJson = confCityService.getConfCitySelectForLandlord();
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<TreeNodeVo> cityTreeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
			request.setAttribute("cityTreeList", cityTreeList);
			
			return "house/houseIssue/locationArea";
		}
		if("2".equals(loationPage)){
			return "house/houseIssue/locationStreet";
		}
		if("3".equals(loationPage)){
			return "house/houseIssue/locationCommunity";
		}
		if("4".equals(loationPage)){
			return "house/houseIssue/locationDetailAddress";
		}
		return "";
	}
	
	
	/**
	 * 保存房源信息
	 * @author lishaochuan
	 * @create 2016年5月26日下午2:04:19
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/saveHouseFirst")
	@ResponseBody
	public DataTransferObject saveHouseFirst(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
			String customerResultJson = customerInfoService.getCustomerInfoByUid(customerVo.getUid());
			CustomerBaseMsgEntity customerBase = SOAResParseUtil.getValueFromDataByKey(customerResultJson, "customerBase",CustomerBaseMsgEntity.class);
			// 校验发布房源资质认证
			this.canDeployHouse(customerBase, dto);
			if(dto.getCode() != DataTransferObject.SUCCESS){
				return dto;
			}
			
			String operateSeq = request.getParameter("operateSeq");
			String houseBaseFid = request.getParameter("houseBaseFid");
			String houseType = request.getParameter("houseType");
			String rentWay = request.getParameter("rentWay");
			String nationCode = request.getParameter("nationCode");
			String provinceCode = request.getParameter("provinceCode");
			String cityCode = request.getParameter("cityCode");
			//日本的区域code 没有
			String areaCode = request.getParameter("areaCode")==null?"":request.getParameter("areaCode");
			String communityName = request.getParameter("communityName");
			String houseStreet = request.getParameter("houseStreet");
			String longitude = request.getParameter("longitude");
			String latitude = request.getParameter("latitude");
			String detailAddress = request.getParameter("detailAddress");
			String flag = request.getParameter("flag");
			String housePhyFid = request.getParameter("housePhyFid");
			String houseRoomFid=request.getParameter("houseRoomFid");
			
			if(Check.NuNStr(houseType)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("房源类型不能为空");
				return dto;
			}
			
			if(Check.NuNStr(rentWay)){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("出租方式不能为空");
				return dto;
			}
			
			if(!Check.NuNStr(communityName) && communityName.length()>50){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("社区名称过长");
				return dto;
			}
			
			if(!Check.NuNStr(houseStreet) && houseStreet.length()>50){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("街道名称过长");
				return dto;
			}
			
			if(!Check.NuNStr(detailAddress) && detailAddress.length()>50){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg("楼号门牌号过长");
				return dto;
			}
			
			
			// 房源基础信息
			HouseMsgVo houseMsgVo = new HouseMsgVo();
		
			houseMsgVo.setFid(houseBaseFid);
			houseMsgVo.setHouseType(ValueUtil.getintValue(houseType));
			houseMsgVo.setRentWay(ValueUtil.getintValue(rentWay));
			houseMsgVo.setHouseSource(HouseSourceEnum.MSIT.getCode());
			houseMsgVo.setHouseStatus(HouseStatusEnum.DFB.getCode());
			if(!Check.NuNStr(houseBaseFid)){
				String resultJson = houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
				HouseBaseMsgEntity houseBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseBaseMsgEntity.class);
				if(!customerVo.getUid().equals(houseBaseMsgEntity.getLandlordUid())){
					LogUtil.error(LOGGER, "权限非法，修改的不是本人的房源，修改人uid:{}, 房源landlordUid:{}", customerVo.getUid(), houseBaseMsgEntity.getLandlordUid());
					dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("权限非法");
						return dto;
				}
				// 限制只有待发布状态或信息审核未通过状态才能修改房源
				houseMsgVo.setOldStatus(houseBaseMsgEntity.getHouseStatus());
				//判断是否更新状态
				if(!Check.NuNObj(houseBaseMsgEntity.getHouseStatus())){
					houseMsgVo.setHouseStatus(null);
				}
				//整租逻辑
				if(houseMsgVo.getRentWay()==RentWayEnum.HOUSE.getCode()){
					//已发布(审核中),上架 这几个状态不允许修改地址信息
					if(houseBaseMsgEntity.getHouseStatus() == 11 || houseBaseMsgEntity.getHouseStatus()==20  || houseBaseMsgEntity.getHouseStatus()==40 || houseBaseMsgEntity.getHouseStatus()==41 || houseBaseMsgEntity.getHouseStatus()==50){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("此状态下的位置信息不能修改");
						return dto;
					}
				//分租逻辑	
				} else if(houseMsgVo.getRentWay()==RentWayEnum.ROOM.getCode()&&!Check.NuNObj(houseRoomFid)) {
					String roomJson=houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
					List<HouseRoomMsgEntity> roomList=SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomMsgEntity.class);
					boolean isChange=false;
					for(HouseRoomMsgEntity room:roomList){
						if(room.getRoomStatus()== 11 || room.getRoomStatus()==20  || room.getRoomStatus()==40 || room.getRoomStatus()==41 || room.getRoomStatus()==50){
							isChange=true;
							break;
						}
					}
					if(isChange){
						dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
						dto.setMsg("此状态下的位置信息不能修改!");
						return dto;
					}
				}
				
			}else{
				//新增保存 步骤和完成率
				houseMsgVo.setOperateSeq(1);
				houseMsgVo.setIntactRate(HouseIssueStepEnum.ONE.getValue());
			}
			String uid = customerVo.getUid();
			houseMsgVo.setLandlordUid(uid);
			
			String mapType = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_mapType.getType(), EnumMinsuConfig.minsu_mapType.getCode());
			// 房源物理信息
			HousePhyMsgEntity housePhyMsg = new HousePhyMsgEntity();
			housePhyMsg.setNationCode(nationCode);
			housePhyMsg.setProvinceCode(provinceCode);
			housePhyMsg.setCityCode(cityCode);
			housePhyMsg.setAreaCode(areaCode);
			housePhyMsg.setCommunityName(communityName);
			
			String mapTypeReal = mapType;
			if(MapTypeEnum.PC_GOOLGE_M_BAIDU.getCode().equals(mapType)){
				mapTypeReal = MapTypeEnum.BAIDU.getCode();
			}
			GoogleBaiduCoordinateEnum.HousePhyMsgEntity.googleBaiduCoordinateTranfor(housePhyMsg,ValueUtil.getdoubleValue(latitude),ValueUtil.getdoubleValue(longitude),mapTypeReal);
			if(!Check.NuNStr(housePhyFid)){
				housePhyMsg.setFid(housePhyFid);
			}
			
			houseMsgVo.setHousePhyMsg(housePhyMsg);
			
			HouseGuardRelEntity houseGuardRelEntity =  null;
			if(Check.NuNStr(houseBaseFid)){
				houseGuardRelEntity = setHouseGuardRel(housePhyMsg, customerVo, houseMsgVo);
				houseMsgVo.setHouseGuardRel(houseGuardRelEntity);
			}
			
			HouseBaseExtEntity houseBaseExt = null;
			if(Check.NuNStr(houseBaseFid)){
				// 房源扩展信息
				houseBaseExt = new HouseBaseExtEntity();
				houseBaseExt.setHouseBaseFid(houseBaseFid);
				
			}else{
				String houseBaseExtJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
				HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
				if(!Check.NuNObj(houseBaseExtDto)){
					if(!Check.NuNObj(houseBaseExtDto.getHouseBaseExt())){
						houseBaseExt = houseBaseExtDto.getHouseBaseExt();
					}
				}
			}
			
			houseBaseExt.setHouseStreet(houseStreet);
			houseBaseExt.setDetailAddress(detailAddress);
			
			houseMsgVo.setHouseBaseExt(houseBaseExt);
			
			// 拼房源地址字段，houseAddr
			List<String> codeList = new ArrayList<String>();
			codeList.add(housePhyMsg.getCityCode());
			if(SysConst.nation_code.equals(nationCode)){
				codeList.add(housePhyMsg.getAreaCode());
			}
			
			
			StringBuilder houseAddr = new StringBuilder();
			
			/*
			 * ####################################################################################
			 * 20160818 zl
			 * 地址详情修改为：地区+街道+小区+地址详情，街道和小区相同时只拼接一个
			 * ####################################################################################
			 ***********************************************************************************************************
				houseAddr.append(this.getLocationNameByCodeList(codeList, false));
				houseAddr.append(houseBaseExt.getHouseStreet());
				houseAddr.append(housePhyMsg.getCommunityName()+" ");
				houseAddr.append(Check.NuNStr(houseBaseExt.getBuildingNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getBuildingNum()) ? "" : (houseBaseExt.getBuildingNum() + "号楼"));
				houseAddr.append(Check.NuNStr(houseBaseExt.getUnitNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getUnitNum()) ? "" : (houseBaseExt.getUnitNum() + "单元"));
				houseAddr.append(Check.NuNStr(houseBaseExt.getFloorNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getFloorNum()) ? "" : (houseBaseExt.getFloorNum() + "层"));
				houseAddr.append(Check.NuNStr(houseBaseExt.getHouseNum()) 
						|| ZERO_STRING.equals(houseBaseExt.getHouseNum()) ? "" : (houseBaseExt.getHouseNum() + "号"));
			 ************************************************************************************************************
			 */
			houseAddr.append(this.getLocationNameByCodeList(codeList, false));
			houseAddr.append(houseBaseExt.getHouseStreet());
			if(!houseBaseExt.getHouseStreet().equals(housePhyMsg.getCommunityName())){
				houseAddr.append(housePhyMsg.getCommunityName());
			}
			houseAddr.append(" ");
			if(!Check.NuNStr(detailAddress)){
				detailAddress=detailAddress.replaceAll(" ", "");//不能有空格
				houseAddr.append(detailAddress);
			}
			
			houseMsgVo.setHouseAddr(houseAddr.toString().trim());
			
			String sourceType = request.getParameter("sourceType");//请求来源
			if(!Check.NuNStr(sourceType)){
				SourceTypeEnum sourceTypeEnum = SourceTypeEnum.getSourceTypeEnumByCode(Integer.valueOf(sourceType));
				if(!Check.NuNObj(sourceTypeEnum)){
					houseMsgVo.setHouseSource(sourceTypeEnum.getCode());
				}
			}
			
			String houseMsgVoJson = JsonEntityTransform.Object2Json(houseMsgVo);
			LogUtil.info(LOGGER, "保存房源信息saveHouseFirst，请求参数：houseMsgVo={}", houseMsgVoJson);
			String resultJson = houseIssueService.mergeHouseBaseAndPhyAndExt(houseMsgVoJson);
			LogUtil.info(LOGGER, "保存房源信息saveHouseFirst，返回结果，resultJson={}", resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if(dto.getCode() == DataTransferObject.SUCCESS){
				dto.putValue("houseBaseFid", dto.getData().get("houseBaseFid"));
				dto.putValue("rentWay", rentWay);
				dto.putValue("flag", flag);
				dto.putValue("houseRoomFid", request.getParameter("houseRoomFid"));
			}
			
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("服务错误");
		}
		return dto;
		
	}
	
	
	/**
	 * 
	 * 获取房源运营专员
	 *
	 * @author yd
	 * @created 2016年7月9日 下午4:34:37
	 *
	 * @param housePhyMsg
	 * @param customerVo
	 * @param houseMsgVo 
	 * @return
	 * @throws SOAParseException  
	 */
	private HouseGuardRelEntity setHouseGuardRel(HousePhyMsgEntity housePhyMsg, CustomerVo customerVo,
			HouseMsgVo houseMsgVo) throws SOAParseException {
		HouseGuardRelEntity houseGuardRelEntity  = null;
		if (Check.NuNObj(housePhyMsg) || Check.NuNObj(customerVo)) {
			return houseGuardRelEntity;
		}
    	
		// 地推管家岗位已取消 modified by liujun 2017-02-24
    	/*//查询是否存在地推管家,存在分配该管家给该房源作为维护管家
    	if(!Check.NuNStr(customerVo.getShowMobile())){//不带星号，完整的手机号
    		HouseBusinessMsgExtDto houseBusinessMsgExt = new HouseBusinessMsgExtDto();
    		houseBusinessMsgExt.setLandlordMobile(customerVo.getShowMobile());
    		String pushJson = this.houseBusinessService
    				.findHouseBusExtByCondition(JsonEntityTransform.Object2Json(houseBusinessMsgExt));
    		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(pushJson);
    		
    		if(dto.getCode() == DataTransferObject.SUCCESS){
				List<HouseBusinessMsgExtEntity> listExtEntities = SOAResParseUtil
						.getListValueFromDataByKey(pushJson, "listExtEntities", HouseBusinessMsgExtEntity.class);
				for (HouseBusinessMsgExtEntity houseBusines : listExtEntities) {
					if (!Check.NuNStr(houseBusines.getDtGuardCode())) {
						//如果有地推管家则渠道为地推
						houseMsgVo.setHouseChannel(HouseChannelEnum.CH_DITUI.getCode());
						houseGuardRelEntity = new HouseGuardRelEntity();
						houseGuardRelEntity.setFid(UUIDGenerator.hexUUID());
						houseGuardRelEntity.setCreateFid(customerVo.getUid());
						houseGuardRelEntity.setCreateDate(new Date());
						houseGuardRelEntity.setLastModifyDate(new Date());
						houseGuardRelEntity.setIsDel(0);
						houseGuardRelEntity.setEmpGuardCode(houseBusines.getDtGuardCode());
						houseGuardRelEntity.setEmpGuardName(houseBusines.getDtGuardName());
						houseGuardRelEntity.setEmpPushCode(houseBusines.getDtGuardCode());
						houseGuardRelEntity.setEmpPushName(houseBusines.getDtGuardName());
						break;
					}
				}
    		}
    		
    	}*/
    	
    	// 区域专员关系表中随机分配运营专员
    	if (Check.NuNObj(houseGuardRelEntity)) {
    		GuardAreaRequest guardAreaRequest = new GuardAreaRequest();
    		guardAreaRequest.setAreaCode(housePhyMsg.getAreaCode());
    		guardAreaRequest.setCityCode(housePhyMsg.getCityCode());
    		guardAreaRequest.setNationCode(housePhyMsg.getNationCode());
    		guardAreaRequest.setProvinceCode(housePhyMsg.getProvinceCode());
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
    				houseGuardRelEntity.setCreateFid(customerVo.getUid());
    				houseGuardRelEntity.setCreateDate(new Date());
    				houseGuardRelEntity.setLastModifyDate(new Date());
    				houseGuardRelEntity.setIsDel(0);
    			}
    		}
		}
    	
		return houseGuardRelEntity;
	}
	
	/**
	 * 校验发布房源资质认证
	 * @author lishaochuan
	 * @create 2016年5月29日上午3:29:34
	 * @param customerBase
	 * @param dto
	 */
	private void canDeployHouse(CustomerBaseMsgEntity customerBase, DataTransferObject dto){
		if(customerBase.getIsContactAuth() != YesOrNoEnum.YES.getCode()){
			LogUtil.error(LOGGER, "联系方式尚未认证,customerBase:{}", customerBase);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("联系方式尚未认证");
			return;
		}
		if(customerBase.getIsIdentityAuth() != YesOrNoEnum.YES.getCode()){
			LogUtil.error(LOGGER, "身份信息尚未认证,customerBase:{}", customerBase);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("身份信息尚未认证");
			return;
		}
		if(customerBase.getIsUploadIcon() != YesOrNoEnum.YES.getCode()){
			LogUtil.error(LOGGER, "尚未上传头像,customerBase:{}", customerBase);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("尚未上传头像");
			return;
		}
	}
	
}
