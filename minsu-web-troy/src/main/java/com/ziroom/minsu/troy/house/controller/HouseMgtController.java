package com.ziroom.minsu.troy.house.controller;


import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.*;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jfif.JfifDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.ziroom.minsu.entity.conf.GuardAreaEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.*;
import com.ziroom.minsu.services.basedata.dto.GuardAreaRequest;
import com.ziroom.minsu.services.basedata.dto.JpushRequest;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.cms.api.inner.ShortChainMapService;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.house.api.inner.*;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.*;
import com.ziroom.minsu.troy.common.util.HouseBaseUtils;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.customer.AuditStatusEnum;
import com.ziroom.minsu.valenum.customer.JpushPersonType;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.houseaudit.HouseAuditCauseEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.productrules.*;
import com.ziroom.minsu.valenum.traderules.*;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.*;

/**
 * 
 * <p>房源管理controller</p>
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
@RequestMapping("house/houseMgt")
public class HouseMgtController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseMgtController.class);

	private static String ZERO_STRING = "0";

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name ="house.houseBusinessService")
	private HouseBusinessService houseBusinessService;

	@Resource(name="house.houseGuardService")
	private HouseGuardService houseGuardService;

	@Resource(name ="basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name ="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name ="basedata.guardAreaService")
	private GuardAreaService guardAreaService;

	@Resource(name ="basedata.employeeService")
	private EmployeeService employeeService;

	@Resource(name ="basedata.permissionOperateService")
	private PermissionOperateService permissionOperateService;

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name="customer.customerMsgManagerService")
	private CustomerMsgManagerService customerMsgManagerService;

	@Resource(name="customer.telExtensionService")
	private TelExtensionService telExtensionService;

	@Resource(name = "house.houseSurveyService")
	private HouseSurveyService houseSurveyService;

	@Resource(name="storageService")
	private StorageService storageService;

	@Resource(name="photographer.troyPhotogBookService")
	private TroyPhotogBookService troyPhotogBookService;

	@Autowired
	private RedisOperations redisOperations;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${pic_size}'.trim()}")
	private String picSize;

	@Value("#{'${pic_water_m}'.trim()}")
	private String picWaterM;

	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;

	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;

	@Value("#{'${upper_limit_rate}'.trim()}")
	private String upperLimitRate;

	@Value("#{'${lower_limit_rate}'.trim()}")
	private String lowerLimitRate;

	@Value("#{'${OPEN_MINSU_APP_MYHOUSE}'.trim()}")
	private String jumpAppShortUrl;

	@Value("#{'${EUNOMIA_URL}'.trim()}")
	private String EUNOMIA_URL;
	
	@Value("#{'${SENSITIVE_URL}'.trim()}")
	private String SENSITIVE_URL;
	
	@Value("#{'${EUNOMIA_USERNAME}'.trim()}")
	private String EUNOMIA_USERNAME;
	
	@Value("#{'${EUNOMIA_PASSWORD}'.trim()}")
	private String EUNOMIA_PASSWORD;

	@Value("#{'${MAPP_URL}'.trim()}")
	private String MAPP_URL;
	
/*	@Value("#{'${sms_to_all_house_list}'.trim()}")
	private String sms_to_all_house_list;
	*/
    @Resource(name = "house.abHouseService")
    private AbHouseService abHouseService;
    
	@Resource(name = "cms.shortChainMapService")
	private ShortChainMapService shortChainMapService;

	/** 灵活定价的配置*/
	private static List<String> gapFlexlist = new ArrayList<String>();
	static {
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020001.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020002.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020003.getValue());
	}

	
	@Resource(name = "house.houseUpdateHistoryLogService")
	private HouseUpdateHistoryLogService houseUpdateHistoryLogService;

	/**
	 * 
	 * 房源管理-跳转房源信息查询页面
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午4:41:31
	 *
	 * @param request
	 */
	@RequestMapping("listHouseMsg")
	public void listHouseMsg(HttpServletRequest request) {
		cascadeDistricts(request);
		// 房源状态类型
		request.setAttribute("houseStatusMap", HouseStatusEnum.getValidEnumMap());
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
		request.setAttribute("causeMap", HouseAuditCauseEnum.getValidEnumMap());
		request.setAttribute("houseChannel", HouseChannelEnum.getEnumMapAll());
		request.setAttribute("surveyResult", SurveyResultEnum.getTotalmap());
	}

	/**
	 * 
	 * 获取行政区域列表
	 *
	 * @author liujun
	 * @created 2016年5月7日
	 *
	 * @param request
	 */
	private void cascadeDistricts(HttpServletRequest request) {
		String resultJson = confCityService.getConfCitySelect();
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "confCityService.getConfCitySelect接口调用失败,结果:{}", resultJson);
		}
		List<TreeNodeVo> cityTreeList = dto.parseData("list", new TypeReference<List<TreeNodeVo>>(){});
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	};

	/**
	 * 
	 * 房源管理-查询房源信息列表页
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:43:25
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseMsg")
	@ResponseBody
	public PageResult showHouseMsg(HouseRequestDto houseRequest,HttpServletRequest request) {
		if (Check.NuNObj(houseRequest)) {
			return new PageResult();
		}
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonHouseMsgNew(houseRequest);
	}

	/**
	 * 查询房源列表信息
	 * TODO 
	 *
	 * @author zl
	 * @created 2016年11月21日 上午11:31:11
	 *
	 * @param houseRequest
	 * @return
	 */
	private PageResult showCommonHouseMsgNew(HouseRequestDto houseRequest) {
		try {
			//判断是否合租
			if (houseRequest.getRentWay() == RentWayEnum.ROOM.getCode()) {
				exchangeCondition(houseRequest);
			}

			Map<String,CustomerBaseMsgEntity> landlordUidMap = new HashMap<String,CustomerBaseMsgEntity>();
			// 房东姓名或房东手机不为空,调用用户库查询房东uid
			if(!Check.NuNStr(houseRequest.getLandlordName()) || !Check.NuNStr(houseRequest.getLandlordMobile())){
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(houseRequest.getLandlordName());
				paramDto.setCustomerMobile(houseRequest.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);

				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断调用状态
				if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<CustomerBaseMsgEntity> customerList = customerDto.parseData("listCustomerBaseMsg",
						new TypeReference<List<CustomerBaseMsgEntity>>() {});
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(customerList)){
					LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<String> landlordUidList = new ArrayList<String>();
				for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
					landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
					landlordUidList.add(customerBaseMsg.getUid());
				}
				houseRequest.setLandlordUidList(landlordUidList);
			}
			String resultJson = troyHouseMgtService.searchHouseMsgListNew(JsonEntityTransform.Object2Json(houseRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(houseRequest));
				return new PageResult();
			}

			List<HouseResultNewVo> houseMsgList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseResultNewVo.class);
			for (HouseResultNewVo houseResultVo : houseMsgList) {
				CustomerBaseMsgEntity customer = null;
				if(Check.NuNMap(landlordUidMap)){
					String customerJson = customerInfoService.getCustomerInfoByUid(houseResultVo.getLandlordUid());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerJson);
					if (dto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "调用接口失败,landlordUid={}", houseResultVo.getLandlordUid());
					} else {
						customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
					}
				} else {
					customer = landlordUidMap.get(houseResultVo.getLandlordUid());
				}

				if(!Check.NuNObj(customer)){
					houseResultVo.setLandlordName(customer.getRealName());
					houseResultVo.setLandlordMobile(customer.getCustomerMobile());
				}

				houseResultVo.setAuditCause(HouseAuditCauseEnum.getNameStr(houseResultVo.getAuditCause()));
				
				//填充房间类型roomType
				if(!Check.NuNObj(houseResultVo.getRoomType())){
						houseResultVo.setRoomTypeStr(RoomTypeEnum.getEnumByCode(houseResultVo.getRoomType()).getName());
				}
			}

			PageResult pageResult = new PageResult();
			pageResult.setRows(houseMsgList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		} 
	}


	/**
	 * 
	 * 查询房源列表信息(不区分houseFid和RoomFid，尽量用showCommonHouseMsgNew)
	 *
	 * @author liujun
	 * @created 2016年5月10日
	 *
	 * @param houseRequest
	 * @return
	 */
	private PageResult showCommonHouseMsg(HouseRequestDto houseRequest) {
		try {
			//判断是否合租
			if (houseRequest.getRentWay() == RentWayEnum.ROOM.getCode()) {
				exchangeCondition(houseRequest);
			}

			Map<String,CustomerBaseMsgEntity> landlordUidMap = new HashMap<String,CustomerBaseMsgEntity>();
			// 房东姓名或房东手机不为空,调用用户库查询房东uid
			if(!Check.NuNStr(houseRequest.getLandlordName()) || !Check.NuNStr(houseRequest.getLandlordMobile())){
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(houseRequest.getLandlordName());
				paramDto.setCustomerMobile(houseRequest.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);

				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断调用状态
				if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<CustomerBaseMsgEntity> customerList = customerDto.parseData("listCustomerBaseMsg",
						new TypeReference<List<CustomerBaseMsgEntity>>() {});
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(customerList)){
					LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<String> landlordUidList = new ArrayList<String>();
				for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
					landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
					landlordUidList.add(customerBaseMsg.getUid());
				}
				houseRequest.setLandlordUidList(landlordUidList);
			}
			//调整排序规则
			houseRequest.setOrderByType(1);
			String resultJson = troyHouseMgtService.searchHouseMsgList(JsonEntityTransform.Object2Json(houseRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(houseRequest));
				return new PageResult();
			}

			List<HouseResultVo> houseMsgList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseResultVo.class);
			for (HouseResultVo houseResultVo : houseMsgList) {
				CustomerBaseMsgEntity customer = null;
				if(Check.NuNMap(landlordUidMap)){
					String customerJson = customerInfoService.getCustomerInfoByUid(houseResultVo.getLandlordUid());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerJson);
					if (dto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "调用接口失败,landlordUid={}", houseResultVo.getLandlordUid());
					} else {
						customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
					}
				} else {
					customer = landlordUidMap.get(houseResultVo.getLandlordUid());
				}

				if(!Check.NuNObj(customer)){
					houseResultVo.setLandlordName(customer.getRealName());
					houseResultVo.setLandlordMobile(customer.getCustomerMobile());
				}

				// 查询维护管家 
				// 目前页面不需要显示手机号,站不需要调用接口
				/*if(!Check.NuNStr(houseResultVo.getEmpGuardCode())){
					String guardEmp = employeeService.findEmployeeByEmpCode(houseResultVo.getEmpGuardCode());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(guardEmp);
					if (dto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}", houseResultVo.getEmpGuardCode());
					} else {
						EmployeeEntity empPush =SOAResParseUtil.getValueFromDataByKey(guardEmp, "employee", EmployeeEntity.class);
						if(!Check.NuNObj(guardEmp)){
							houseResultVo.setEmpGuardName(empPush.getEmpName());
							houseResultVo.setEmpGuardMobile(empPush.getEmpMobile());
						}
					}
				}*/

				// 查询地推管家
				// 目前页面不需要显示手机号,站不需要调用接口
				/*if(!Check.NuNStr(houseResultVo.getEmpPushCode())){
					String pushEmp = employeeService.findEmployeeByEmpCode(houseResultVo.getEmpPushCode());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(pushEmp);
					if (dto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}", houseResultVo.getEmpPushCode());
					} else {
						EmployeeEntity empPush =SOAResParseUtil.getValueFromDataByKey(pushEmp, "employee", EmployeeEntity.class);
						if(!Check.NuNObj(pushEmp)){
							houseResultVo.setEmpPushName(empPush.getEmpName());
							houseResultVo.setEmpPushMobile(empPush.getEmpMobile());
						}
					}
				}*/

				//填充房间类型roomType
				if(!Check.NuNObj(houseResultVo.getRoomType())){
					RoomTypeEnum enumByCode = RoomTypeEnum.getEnumByCode(houseResultVo.getRoomType());
					if(!Check.NuNObj(enumByCode)){
						houseResultVo.setRoomTypeStr(enumByCode.getName());
					}
					LogUtil.info(LOGGER, "showCommonHouseMsg方法  房间类型,roomType={} enumByCode={}", houseResultVo.getRoomType(),enumByCode);
				}
				
				houseResultVo.setAuditCause(HouseAuditCauseEnum.getNameStr(houseResultVo.getAuditCause()));
			}

			PageResult pageResult = new PageResult();
			pageResult.setRows(houseMsgList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		} 
	}

	/**
	 * 合租转换查询条件
	 *
	 * @author liujun
	 * @created 2016年4月14日 下午6:20:32
	 *
	 * @param houseRequest
	 */
	private void exchangeCondition(HouseRequestDto houseRequest) {
		houseRequest.setLandlordName(houseRequest.getLandlordNameS());
		houseRequest.setLandlordMobile(houseRequest.getLandlordMobileS());
		houseRequest.setHouseName(houseRequest.getHouseNameS());
		houseRequest.setHouseStatus(houseRequest.getHouseStatusS());
		houseRequest.setCameramanMobile(houseRequest.getCameramanMobileS());
		houseRequest.setCameramanName(houseRequest.getCameramanNameS());
		houseRequest.setIsPic(houseRequest.getIsPicS());
		houseRequest.setNationCode(houseRequest.getNationCodeS());
		houseRequest.setProvinceCode(houseRequest.getProvinceCodeS());
		houseRequest.setCityCode(houseRequest.getCityCodeS());
		houseRequest.setZoName(houseRequest.getZoNameS());
		houseRequest.setHouseType(houseRequest.getHouseTypeS());
		houseRequest.setIsWeight(houseRequest.getIsWeightS());
	}

	/**
	 * 
	 * 房源管理-跳转房源管家审核页面
	 *
	 * @author liujun
	 * @created 2016年4月12日 上午11:33:11
	 *
	 * @param request
	 */
	@Deprecated // modified by liujun 2017-02-22
	@RequestMapping("listHouseMsgInfo")
	public void listHouseMsgInfo(HttpServletRequest request) {
		cascadeDistricts(request);
		// 房源状态类型
		Map<Integer, String> houseStatusMap = new LinkedHashMap<Integer, String>();
		houseStatusMap.put(HouseStatusEnum.YFB.getCode(), HouseStatusEnum.YFB.getName());
		houseStatusMap.put(HouseStatusEnum.XXSHWTG.getCode(), HouseStatusEnum.XXSHWTG.getName());
		houseStatusMap.put(HouseStatusEnum.ZPSHWTG.getCode(), HouseStatusEnum.ZPSHWTG.getName());
		request.setAttribute("houseStatusMap", houseStatusMap);
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
		request.setAttribute("causeMap", HouseAuditCauseEnum.getEnumMap());
	}

	/**
	 * 
	 * 房源管理-查询房源管家审核列表页
	 *
	 * @author liujun
	 * @created 2016年4月12日 上午11:39:35
	 *
	 * @param houseRequest
	 * @return
	 */
	@Deprecated // modified by liujun 2017-02-22
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseMsgInfo")
	@ResponseBody
	public PageResult showHouseMsgInfo(HouseRequestDto houseRequest,HttpServletRequest request) {
		if(Check.NuNObj(houseRequest.getHouseStatus())){
			List<Integer> houseStatusList = new ArrayList<Integer>();
			houseStatusList.add(HouseStatusEnum.YFB.getCode());
			houseStatusList.add(HouseStatusEnum.XXSHWTG.getCode());
			houseStatusList.add(HouseStatusEnum.ZPSHWTG.getCode());
			houseRequest.setHouseStatusList(houseStatusList);
		}
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonHouseMsg(houseRequest);
	}

	/**
	 * 
	 * 房源管理-跳转房源品质审核页面
	 *
	 * @author liujun
	 * @created 2016年4月12日 上午11:39:51
	 *
	 * @param request
	 */
	@RequestMapping("listHouseMsgPic")
	public void listHouseMsgPic(HttpServletRequest request) {
		cascadeDistricts(request);

		// 房源状态类型
		Map<Integer, String> houseStatusMap = new LinkedHashMap<Integer, String>();
		houseStatusMap.put(HouseStatusEnum.YFB.getCode(), HouseStatusEnum.YFB.getName());
		request.setAttribute("houseStatusMap", houseStatusMap);
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
		request.setAttribute("causeMap", HouseAuditCauseEnum.getValidEnumMap());
		request.setAttribute("houseChannel", HouseChannelEnum.getEnumMapAll());
	}

	/**
	 * 
	 * 房源管理-查询房源品质审核列表页
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:43:25
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseMsgPic")
	@ResponseBody
	public PageResult showHouseMsgPic(HouseRequestDto houseRequest,HttpServletRequest request) {
		if(Check.NuNObj(houseRequest.getHouseStatus())){
			List<Integer> houseStatusList = new ArrayList<Integer>();
			houseStatusList.add(HouseStatusEnum.YFB.getCode());
			houseRequest.setHouseStatusList(houseStatusList);
		}
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonHouseMsg(houseRequest);
	}

	/**
	 * 
	 * 房源管理-跳转房源下架管理页面
	 *
	 * @author liujun
	 * @created 2016年4月12日 上午11:39:51
	 *
	 * @param request
	 */
	@RequestMapping("listHouseMsgOnline")
	public void listHouseMsgOnline(HttpServletRequest request) {
		cascadeDistricts(request);
		// 房源状态类型
		Map<Integer, String> houseStatusMap = new LinkedHashMap<Integer, String>();
		houseStatusMap.put(HouseStatusEnum.SJ.getCode(), HouseStatusEnum.SJ.getName());
		houseStatusMap.put(HouseStatusEnum.QZXJ.getCode(), HouseStatusEnum.QZXJ.getName());
		request.setAttribute("houseStatusMap", houseStatusMap);
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
	}

	/**
	 * 
	 * 房源管理-查询上架房源列表页
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:43:25
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseMsgOnline")
	@ResponseBody
	public PageResult showHouseMsgOnline(HouseRequestDto houseRequest,HttpServletRequest request) {
		List<Integer> houseStatusList = new ArrayList<Integer>();
		houseStatusList.add(HouseStatusEnum.SJ.getCode());
		houseStatusList.add(HouseStatusEnum.QZXJ.getCode());
		houseRequest.setHouseStatusList(houseStatusList);
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonHouseMsg(houseRequest);
	}

	/**
	 * 
	 * 房源管理-展示房源详情
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:45:50
	 *
	 * @param request
	 * @param houseFid
	 * @param rentWay
	 * @throws SOAParseException 
	 */
	@RequestMapping("houseDetail")
	public void houseDetail(HttpServletRequest request, String houseFid, Integer rentWay) throws SOAParseException {
		request.setAttribute("isDisplay", true);
		double start = System.currentTimeMillis() ; 
		getHouseDetail(request, houseFid, rentWay);
		double end = System.currentTimeMillis() ; 
		LogUtil.info(LOGGER, "houseDetail——getHouseDetail执行时间：{}", end-start);
		
		/**************获取待审核房源信息，如果有待审核信息并进行填充处理  @author:lusp @date:2017/7/31*************/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		try{
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				houseUpdateFieldAuditNewlogEntity.setRoomFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
				String resultJson = troyHouseMgtService.searchRoomDetailByFid(houseFid);
				HouseMsgVo houseDetail = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseMsgVo.class);
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseDetail.getFid());
			}
			houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
			String auditLogListJson = troyHouseMgtService.getHouseUpdateFieldAuditNewlogByCondition(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity));
			DataTransferObject auditLogListDto = JsonEntityTransform.json2DataTransferObject(auditLogListJson);
			if(auditLogListDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"getHouseDetail(),获取待审核字段失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity),auditLogListDto.getMsg());
				return;
			}
			List<HouseFieldAuditLogVo> list = SOAResParseUtil.getListValueFromDataByKey(auditLogListJson,"list",HouseFieldAuditLogVo.class);
			if(!Check.NuNCollection(list)){
				HouseBaseMsgEntity oldHouseBaseMsg = new HouseBaseMsgEntity();
				HouseBaseExtEntity oldHouseBaseExt = new HouseBaseExtEntity();
				HouseDescEntity oldHouseDesc = new HouseDescEntity();
				HouseRoomMsgEntity oldHouseRoomMsg = new HouseRoomMsgEntity();
				HouseRoomExtEntity oldHouseRoomExt = new HouseRoomExtEntity();

				HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();
				HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();

				HouseBaseMsgEntity houseBaseMsg = (HouseBaseMsgEntity) request.getAttribute("houseBaseMsg");
				HouseBaseExtEntity houseBaseExt = (HouseBaseExtEntity) request.getAttribute("houseBaseExt");
				HouseDescEntity houseDesc = (HouseDescEntity) request.getAttribute("houseDesc");

				this.FilterNotAuditField(houseBaseMsg,oldHouseBaseMsg,list,request);
				this.FilterNotAuditField(houseBaseExt,oldHouseBaseExt,list,request);
				this.FilterNotAuditField(houseDesc,oldHouseDesc,list,request);

				request.setAttribute("houseBaseMsg",houseBaseMsg);
				request.setAttribute("houseBaseExt",houseBaseExt);
				request.setAttribute("oldHouseBaseMsg",oldHouseBaseMsg);
				request.setAttribute("oldHouseBaseExt",oldHouseBaseExt);

				if(rentWay == RentWayEnum.ROOM.getCode()){
					List<RoomMsgVo> roomList = (List<RoomMsgVo>)request.getAttribute("roomList");
					if(!Check.NuNCollection(roomList)){
						BeanUtils.copyProperties(roomList.get(0),houseRoomMsgEntity);
						BeanUtils.copyProperties(roomList.get(0).getRoomExtEntity(),houseRoomExtEntity);
						this.FilterNotAuditField(houseRoomMsgEntity,oldHouseRoomMsg,list,request);
						this.FilterNotAuditField(houseRoomExtEntity,oldHouseRoomExt,list,request);
						BeanUtils.copyProperties(houseRoomMsgEntity,roomList.get(0));
						BeanUtils.copyProperties(houseRoomExtEntity,roomList.get(0).getRoomExtEntity());
					}
					request.setAttribute("roomList",roomList);
					request.setAttribute("oldHouseRoomMsg",oldHouseRoomMsg);
					request.setAttribute("oldHouseRoomExt",oldHouseRoomExt);
					//分组出来房屋守则
					oldHouseDesc.setHouseRules(oldHouseRoomExt.getRoomRules());
					houseDesc.setHouseRules(roomList.get(0).getRoomExtEntity().getRoomRules());
				}
				request.setAttribute("houseDesc",houseDesc);
				request.setAttribute("oldHouseDesc",oldHouseDesc);
			}
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
		}
		/**************获取待审核房源信息，如果有待审核信息并进行填充处理  @author:lusp @date:2017/7/31*************/
	}

	/**
	 * 
	 * 获取房源详情信息
	 *
	 * @author liujun
	 * @created 2016年4月15日 下午3:23:59
	 *
	 * @param request
	 * @param houseFid
	 * @param rentWay
	 * @throws SOAParseException 
	 */

	private void getHouseDetail(HttpServletRequest request, String houseFid, Integer rentWay) throws SOAParseException {

		double start = System.currentTimeMillis() ;
		// 初始化城市
		cascadeDistricts(request);

		// 图片服务器地址
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("houseFid", houseFid);
		request.setAttribute("rentWay", rentWay);
		double end = System.currentTimeMillis() ; 
		LogUtil.info(LOGGER, "getHouseDetail——cascadeDistricts执行时间：{}", end-start);
		start = System.currentTimeMillis() ;
		String resultJson = "";
		if(rentWay == RentWayEnum.HOUSE.getCode() ){
			resultJson = troyHouseMgtService.searchHouseDetailByFid(houseFid);
		}else if (rentWay == RentWayEnum.ROOM.getCode()) {
			resultJson = troyHouseMgtService.searchRoomDetailByFid(houseFid);
		}
		end = System.currentTimeMillis() ; 
		LogUtil.info(LOGGER, "getHouseDetail——searchHouseDetailByFid或者searchRoomDetailByFid执行时间：{},出租方式rentWay={}", end-start,rentWay);
		start = System.currentTimeMillis() ;
		HouseMsgVo houseDetail = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseMsgVo.class);

		// 城市code
		String cityCode = null;
		HouseBaseMsgEntity houseBaseMsg = null;
		HouseBaseExtEntity houseBaseExt = null;
		if(!Check.NuNObj(houseDetail)){
			houseBaseMsg = new HouseBaseMsgEntity();
			BeanUtils.copyProperties(houseDetail, houseBaseMsg);
			request.setAttribute("houseBaseMsg", houseBaseMsg);

			String houseStauStr="";
			// 房源日价格
			if(!Check.NuNObj(houseBaseMsg) && !Check.NuNObj(houseBaseMsg.getLeasePrice())){
				BigDecimal housePrice = BigDecimal.valueOf(houseBaseMsg.getLeasePrice()).divide(BigDecimal.valueOf(100));
				request.setAttribute("housePrice", housePrice);
			}
			if(!Check.NuNObj(houseBaseMsg)&&houseBaseMsg.getRentWay().intValue() == RentWayEnum.HOUSE.getCode()){
				houseStauStr = HouseStatusEnum.getHouseStatusByCode(houseBaseMsg.getHouseStatus()).getName();
				request.setAttribute("houseStauStr", houseStauStr);
			}

			//查询房源实勘信息
			HouseSurveyMsgEntity houseSurveyMsg = this.findHouseSurveyMsg(houseBaseMsg.getFid());
			request.setAttribute("houseSurveyMsg", houseSurveyMsg);

			//查询房源维护管家
			HouseGuardRelEntity houseGuardRel= this.findHouseGuardRelEntity(houseBaseMsg.getFid());
			request.setAttribute("houseGuardRel", houseGuardRel);

			//根据房东uid调客户库接口查询房东信息
			String customerJson=customerMsgManagerService.getCustomerBaseMsgEntitybyUid(houseDetail.getLandlordUid());
			CustomerBaseMsgEntity customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customer", CustomerBaseMsgEntity.class);
			request.setAttribute("landlord", customer);

			// 房源物理信息
			HousePhyMsgEntity housePhyMsg = houseDetail.getHousePhyMsg();
			request.setAttribute("housePhyMsg", housePhyMsg);

			// 房源基础信息扩展
			houseBaseExt = houseDetail.getHouseBaseExt();
			HouseRoomExtEntity roomExt = null;
			if(rentWay.intValue()==RentWayEnum.ROOM.getCode()){
				roomExt = this.getRoomBaseAndExtInfo(houseFid,roomExt);
			}
			if(!Check.NuNObj(roomExt)){
				this.exchageRoomExtInfo(houseBaseExt, roomExt);
			}
			
			request.setAttribute("houseBaseExt", houseBaseExt);

			// 退订政策@HouseBaseExtEntity#checkOutRulesCode
			String checkOutRulesCode = "";
			// 押金规则@HouseBaseExtEntity#depositRulesCode
			end = System.currentTimeMillis() ;
			LogUtil.info(LOGGER, "getHouseDetail——getCutomerVo执行时间：{}", end-start);
			start = System.currentTimeMillis() ;
			if (!Check.NuNObj(houseBaseExt)) {
				checkOutRulesCode = houseBaseExt.getCheckOutRulesCode();

				// 退订政策-showName
				TradeRulesEnum005Enum tradeRulesEnum005Enum = TradeRulesEnum005Enum.getEnumByValue(checkOutRulesCode);
				String checkOutRulesName = tradeRulesEnum005Enum == null ? null : tradeRulesEnum005Enum.getName();
				request.setAttribute("checkOutRulesName", checkOutRulesName);
				request.setAttribute("checkOutRulesCode", checkOutRulesCode);

				//退订政策
				if(!Check.NuNStr(checkOutRulesCode)){
					//严格退订
					if(TradeRulesEnum005Enum.TradeRulesEnum005001.getValue().equals(checkOutRulesCode)){
						request.setAttribute("calculation", TradeRulesEnum005001Enum.TradeRulesEnum005001001.getName());
						String tradeRulesEnum005001001001Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.getValue());
						String tradeRulesEnum005001001001Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005001001001Json, "textValue", String.class);
						request.setAttribute("unregName1",
								TradeRulesEnum005001001Enum.TradeRulesEnum005001001001.getName());
						request.setAttribute("unregVal1", tradeRulesEnum005001001001Value);

						String tradeRulesEnum005001001002Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005001001Enum.TradeRulesEnum005001001002.getValue());
						String tradeRulesEnum005001001002Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005001001002Json, "textValue", String.class);
						request.setAttribute("unregName2",
								TradeRulesEnum005001001Enum.TradeRulesEnum005001001002.getName());
						request.setAttribute("unregVal2", tradeRulesEnum005001001002Value);

						String tradeRulesEnum005001001003Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.getValue());
						String tradeRulesEnum005001001003Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005001001003Json, "textValue", String.class);
						request.setAttribute("unregName3",
								TradeRulesEnum005001001Enum.TradeRulesEnum005001001003.getName());
						request.setAttribute("unregVal3", tradeRulesEnum005001001003Value);							
					}

					//适中退订
					if(TradeRulesEnum005Enum.TradeRulesEnum005002.getValue().equals(checkOutRulesCode)){
						request.setAttribute("calculation", TradeRulesEnum005002Enum.TradeRulesEnum005002001.getName());
						String tradeRulesEnum005002001001Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.getValue());
						String tradeRulesEnum005002001001Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005002001001Json, "textValue", String.class);
						request.setAttribute("unregName1",
								TradeRulesEnum005002001Enum.TradeRulesEnum005002001001.getName());
						request.setAttribute("unregVal1", tradeRulesEnum005002001001Value);

						String tradeRulesEnum005002001002Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005002001Enum.TradeRulesEnum005002001002.getValue());
						String tradeRulesEnum005002001002Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005002001002Json, "textValue", String.class);
						request.setAttribute("unregName2",
								TradeRulesEnum005002001Enum.TradeRulesEnum005002001002.getName());
						request.setAttribute("unregVal2", tradeRulesEnum005002001002Value);

						String tradeRulesEnum005002001003Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.getValue());
						String tradeRulesEnum005002001003Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005002001003Json, "textValue", String.class);
						request.setAttribute("unregName3",
								TradeRulesEnum005002001Enum.TradeRulesEnum005002001003.getName());
						request.setAttribute("unregVal3", tradeRulesEnum005002001003Value);			
					}

					//灵活退订
					if(TradeRulesEnum005Enum.TradeRulesEnum005003.getValue().equals(checkOutRulesCode)){
						request.setAttribute("calculation", TradeRulesEnum005003Enum.TradeRulesEnum005003001.getName());
						String tradeRulesEnum005003001001Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.getValue());
						String tradeRulesEnum005003001001Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005003001001Json, "textValue", String.class);
						request.setAttribute("unregName1",
								TradeRulesEnum005003001Enum.TradeRulesEnum005003001001.getName());
						request.setAttribute("unregVal1", tradeRulesEnum005003001001Value);

						String tradeRulesEnum005003001002Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005003001Enum.TradeRulesEnum005003001002.getValue());
						String tradeRulesEnum005003001002Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005003001002Json, "textValue", String.class);
						request.setAttribute("unregName2",
								TradeRulesEnum005003001Enum.TradeRulesEnum005003001002.getName());
						request.setAttribute("unregVal2", tradeRulesEnum005003001002Value);

						String tradeRulesEnum005003001003Json = cityTemplateService.getTextValue(cityCode,
								TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.getValue());
						String tradeRulesEnum005003001003Value = SOAResParseUtil.getValueFromDataByKey(
								tradeRulesEnum005003001003Json, "textValue", String.class);
						request.setAttribute("unregName3",
								TradeRulesEnum005003001Enum.TradeRulesEnum005003001003.getName());
						request.setAttribute("unregVal3", tradeRulesEnum005003001003Value);
					}
				}
				if(Check.NuNStrStrict(houseBaseExt.getDetailAddress())){
					StringBuffer addr=new StringBuffer("");
					if(!Check.NuNStr(houseBaseExt.getBuildingNum())){
						addr.append(houseBaseExt.getBuildingNum()+"号楼");
					}
					if(!Check.NuNStr(houseBaseExt.getUnitNum())){
						addr.append(houseBaseExt.getUnitNum()+"单元");
					}
					if(!Check.NuNStr(houseBaseExt.getFloorNum())){
						addr.append(houseBaseExt.getFloorNum()+"层");
					}
					if(!Check.NuNStr(houseBaseExt.getHouseNum())){
						addr.append(houseBaseExt.getHouseNum()+"号");
					}
					houseBaseExt.setDetailAddress(addr.toString());
				}
			}
			end = System.currentTimeMillis() ;
			LogUtil.info(LOGGER, "getHouseDetail——退订政策 执行时间：{}", end-start);
			start = System.currentTimeMillis() ;
			// 房源描述信息
			HouseDescEntity houseDesc = houseDetail.getHouseDesc();
			//初始化字数
			if(!Check.NuNObj(houseDesc)){
                if(!Check.NuNObj(roomExt) && !Check.NuNStr(roomExt.getRoomRules())){
                	houseDesc.setHouseRules(roomExt.getRoomRules());
                }
				request.setAttribute("houseDescNum", Check.NuNStrStrict(houseDesc.getHouseDesc())?0:houseDesc.getHouseDesc().length());
				request.setAttribute("houseRulesNum", Check.NuNStrStrict(houseDesc.getHouseRules())?0:houseDesc.getHouseRules().length());
				request.setAttribute("houseAroundDescNum", Check.NuNStrStrict(houseDesc.getHouseAroundDesc())?0:houseDesc.getHouseAroundDesc().length());
				request.setAttribute("addtionalInfoNum", Check.NuNStrStrict(houseDesc.getAddtionalInfo())?0:houseDesc.getAddtionalInfo().length());
			}
			request.setAttribute("houseDesc", houseDesc);

			//获取照片信息 分类显示
			HousePicDto housePicDto = new HousePicDto();
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				housePicDto.setHouseBaseFid(houseFid);
			} else {
				housePicDto.setHouseRoomFid(houseFid);
			}
			//房源照片信息

			String picJson=troyHouseMgtService.housePicAuditList(JsonEntityTransform.Object2Json(housePicDto));


			DataTransferObject picDto = JsonEntityTransform.json2DataTransferObject(picJson);
			HousePicAuditVo housePicAuditVo =picDto.parseData("housePicAuditVo", new TypeReference<HousePicAuditVo>() {});
			request.setAttribute("housePicAuditVo", housePicAuditVo);


			// 房源配置信息
			List<HouseConfMsgEntity> houseConfList = houseDetail.getHouseConfList();
			if (!Check.NuNCollection(houseConfList)) {
				end = System.currentTimeMillis() ;
				LogUtil.info(LOGGER, "getHouseDetail——housePicAuditList 执行时间：{}", end-start);
				start = System.currentTimeMillis() ;
				/** 配套设施 **/
				String ancillaryFacilityJson = cityTemplateService.getSelectSubDic(cityCode,
						ProductRulesEnum.ProductRulesEnum002.getValue());
				List<EnumVo> ancillaryFacilityList = SOAResParseUtil
						.getListValueFromDataByKey(ancillaryFacilityJson, "subDic", EnumVo.class);

				// 配套设施-电器
				List<String> electricList = getSelectSubDic(houseConfList, ancillaryFacilityList,
						ProductRulesEnum002Enum.ProductRulesEnum002001.getValue());
				request.setAttribute("electricList", electricList);

				// 配套设施-卫浴
				List<String> bathroomList = getSelectSubDic(houseConfList, ancillaryFacilityList,
						ProductRulesEnum002Enum.ProductRulesEnum002002.getValue());
				request.setAttribute("bathroomList", bathroomList);

				// 配套设施-设施
				List<String> facilityList = getSelectSubDic(houseConfList, ancillaryFacilityList,
						ProductRulesEnum002Enum.ProductRulesEnum002003.getValue());
				request.setAttribute("facilityList", facilityList);
				/** 配套设施 **/
				end = System.currentTimeMillis() ;
				LogUtil.info(LOGGER, "getHouseDetail——配套设施 执行时间：{}", end-start);
				/** 服务 **/
				start = System.currentTimeMillis() ;
				String serviceJson = cityTemplateService.getSelectEnum(cityCode,
						ProductRulesEnum.ProductRulesEnum0015.getValue());
				List<EnumVo> serviceEnumList = SOAResParseUtil
						.getListValueFromDataByKey(serviceJson, "selectEnum", EnumVo.class);
				List<String> serviceSelectedList = getSelecEnum(houseConfList, serviceEnumList,
						ProductRulesEnum.ProductRulesEnum0015.getValue());
				request.setAttribute("serviceList", serviceSelectedList);
				/** 服务 **/
				end = System.currentTimeMillis() ;
				LogUtil.info(LOGGER, "getHouseDetail——获取服务 执行时间：{}", end-start);
				start = System.currentTimeMillis() ;
				/** 优惠规则 **/
				// 3天折扣率
				/*String discount3 = getSelectValue(houseConfList,
						ProductRulesEnum0012Enum.ProductRulesEnum0012001.getValue());
				request.setAttribute("discount3", discount3);

				// 7天折扣率
				String discount7 = getSelectValue(houseConfList,
						ProductRulesEnum0012Enum.ProductRulesEnum0012002.getValue());
				request.setAttribute("discount7", discount7);

				// 30天折扣率
				String discount30 = getSelectValue(houseConfList,
						ProductRulesEnum0012Enum.ProductRulesEnum0012003.getValue());
				request.setAttribute("discount30", discount30);
				end = System.currentTimeMillis() ;*/
				LogUtil.info(LOGGER, "getHouseDetail——折扣率 执行时间：{}", end-start);


				for (HouseConfMsgEntity houseConfMsgEntity : houseConfList){
					String dicCode = houseConfMsgEntity.getDicCode();

					if (dicCode.equals(ProductRulesEnum020.ProductRulesEnum020001.getValue()) && houseConfMsgEntity.getIsDel() ==0){
						float v = Float.parseFloat(houseConfMsgEntity.getDicVal()) * 100;
						request.setAttribute("ProductRulesEnum020001",v+"%");
						continue;
					}
					if (dicCode.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue()) && houseConfMsgEntity.getIsDel() ==0){
						float v = Float.parseFloat(houseConfMsgEntity.getDicVal()) * 100;
						request.setAttribute("ProductRulesEnum020002",v+"%");
						continue;
					}
					if (dicCode.equals(ProductRulesEnum020.ProductRulesEnum020003.getValue()) && houseConfMsgEntity.getIsDel() ==0){
						float v = Float.parseFloat(houseConfMsgEntity.getDicVal()) * 100;
						request.setAttribute("ProductRulesEnum020003",v+"%");
						continue;
					}

					if (dicCode.equals(ProductRulesEnum0019.ProductRulesEnum0019001.getValue()) && houseConfMsgEntity.getIsDel() ==0){
						float v = Float.parseFloat(houseConfMsgEntity.getDicVal());
						if (v <= 0){
							continue;
						}
						request.setAttribute("ProductRulesEnum0019001",v+"%");
						continue;
					}

					if (dicCode.equals(ProductRulesEnum0019.ProductRulesEnum0019002.getValue()) && houseConfMsgEntity.getIsDel() ==0){
						float v = Float.parseFloat(houseConfMsgEntity.getDicVal());
						if (v <= 0){
							continue;
						}
						request.setAttribute("ProductRulesEnum0019002",v+"%");
						continue;
					}
				}


				start = System.currentTimeMillis() ;

				HouseConfMsgEntity houseConfMsg = null;
				
				HouseBaseVo houseBaseVo = new HouseBaseVo();
				houseBaseVo.setHouseFid(houseBaseMsg.getFid());
				houseBaseVo.setRentWay(rentWay);
				houseBaseVo.setRoomFid(houseFid);
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseIssueService.findHouseOrRoomDeposit(JsonEntityTransform.Object2Json(houseBaseVo)));
				

				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "获取房源押金异常houseBaseFid={},msg={}",houseFid, dto.getMsg());
					houseConfMsg = new HouseConfMsgEntity();
					houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
					houseConfMsg.setDicVal("0");
				}else{
					houseConfMsg = dto.parseData("houseConfMsg", new TypeReference<HouseConfMsgEntity>() {});
				}

				LogUtil.info(LOGGER, "获取押金:houseConfMsg={},houseFid={}", JsonEntityTransform.Object2Json(houseConfMsg),houseFid);

				//TODO 获取押金规则需要改动
				houseConfMsg.setDicVal(DataFormat.formatHundredPriceInt(Integer.valueOf(houseConfMsg.getDicVal())));
				request.setAttribute("depositConfMsg", houseConfMsg);


				/** 优惠规则 **/
				end = System.currentTimeMillis() ;
				LogUtil.info(LOGGER, "getHouseDetail——押金规则  执行时间：{}", end-start);
				start = System.currentTimeMillis() ;
			}

			// 房源房间信息
			List<RoomMsgVo> roomList = houseDetail.getRoomList();
			for (RoomMsgVo room : roomList) {
				Integer roomPrice = room.getRoomPrice();
				if(Check.NuNObj(roomPrice)){
					continue;
				}
				BigDecimal leasePrice = BigDecimal.valueOf(roomPrice).divide(BigDecimal.valueOf(100));
				room.setLeasePrice(leasePrice);
				if(!Check.NuNObj(houseBaseMsg)&&houseBaseMsg.getRentWay().intValue() == RentWayEnum.ROOM.getCode()){
					room.setHouseStauStr(HouseStatusEnum.getHouseStatusByCode(room.getRoomStatus()).getName());
				}
			}
			request.setAttribute("roomList", roomList);
		}

		// 出租类型
		request.setAttribute("rentWayMap", RentWayEnum.getEnumMap());

		// 房源状态
		request.setAttribute("houseStatusMap", HouseStatusEnum.getEnumMap());
		end = System.currentTimeMillis() ;
		LogUtil.info(LOGGER, "getHouseDetail——房源房间信息  执行时间：{}", end-start);

		start = System.currentTimeMillis() ;
		// 房源类型
		String houseTypeJson = cityTemplateService.getSelectEnum(cityCode, ProductRulesEnum.ProductRulesEnum001.getValue());
		List<EnumVo> houseTypeList = SOAResParseUtil
				.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("houseTypeList", houseTypeList);
		end = System.currentTimeMillis() ;
		LogUtil.info(LOGGER, "getHouseDetail——房源类型  执行时间：{}", end-start);
		start = System.currentTimeMillis() ;
		//下单类型
		String orderTypeJson = cityTemplateService
				.getSelectEnum(cityCode, ProductRulesEnum.ProductRulesEnum0010.getValue());
		List<EnumVo> orderTypeList = SOAResParseUtil.getListValueFromDataByKey(orderTypeJson, "selectEnum",
				EnumVo.class);
		request.setAttribute("orderTypeList", orderTypeList);
		end = System.currentTimeMillis() ;
		LogUtil.info(LOGGER, "getHouseDetail——下单类型  执行时间：{}", end-start);
		start = System.currentTimeMillis() ;
		//民宿分类
		/*String homestayJson = cityTemplateService.getSelectEnum(cityCode, ProductRulesEnum.ProductRulesEnum0013.getValue());
		List<EnumVo> homestayList = SOAResParseUtil.getListValueFromDataByKey(homestayJson, "selectEnum", EnumVo.class);
		request.setAttribute("homestayList", homestayList);*/

		//床类型
		String bedTypeJson = cityTemplateService.getSelectEnum(cityCode, ProductRulesEnum.ProductRulesEnum005.getValue());
		List<EnumVo> bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("bedTypeList", bedTypeList);

		//床规格
		String bedSizeJson = cityTemplateService.getSelectEnum(cityCode, ProductRulesEnum.ProductRulesEnum006.getValue());
		List<EnumVo> bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
		request.setAttribute("bedSizeList", bedSizeList);

		//被单更换
		/*String sheetReplaceJson = cityTemplateService.getSelectEnum(cityCode, ProductRulesEnum.ProductRulesEnum0014.getValue());
		List<EnumVo> sheetReplaceList = SOAResParseUtil.getListValueFromDataByKey(sheetReplaceJson, "selectEnum", EnumVo.class);
		request.setAttribute("sheetReplaceList", sheetReplaceList);*/
		end = System.currentTimeMillis() ;
		LogUtil.info(LOGGER, "getHouseDetail——民宿分类~被单更换  执行时间：{}", end-start);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		//房源状态
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));

		//房源渠道枚举
		request.setAttribute("houseChannel", HouseChannelEnum.getEnumMap());
		int status = -1;
		if (rentWay == RentWayEnum.HOUSE.getCode()){
			status = houseDetail.getHouseStatus();
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			List<RoomMsgVo> roomList = houseDetail.getRoomList();
			if (!Check.NuNCollection(roomList)){
				status = roomList.get(0).getRoomStatus();
			}
		}
		if (status == HouseStatusEnum.SJ.getCode()){
			request.setAttribute("isSj",true);
		}

		//摄影师预约状态
		String resultPhotoBookOrder = troyPhotogBookService.findBookOrderByHouseFid(houseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultPhotoBookOrder);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "troyPhotogBookService.findBookOrderByHouseFid error:{}", resultPhotoBookOrder);
		} else {
			PhotographerBookOrderEntity photographerBookOrderEntity = SOAResParseUtil.getValueFromDataByKey(resultPhotoBookOrder,"photographerBookOrderEntity" ,PhotographerBookOrderEntity.class);
			if(photographerBookOrderEntity == null){
				request.setAttribute("bookOrderStatuResult", null);
			}else{
				request.setAttribute("bookOrderStatuResult", photographerBookOrderEntity.getBookOrderStatu());
			}
		}
		request.setAttribute("bookOrderStatusMap", BookOrderStatuEnum.getEnummap());
		
		//房间信息审核未通过次数
		HouseOperateLogEntity houseOperateLogEntity = new HouseOperateLogEntity();
		if (rentWay == RentWayEnum.HOUSE.getCode()){
			houseOperateLogEntity.setHouseBaseFid(houseFid);
		}
		if(rentWay == RentWayEnum.ROOM.getCode()){
			houseOperateLogEntity.setRoomFid(houseFid);
		}
		houseOperateLogEntity.setFromStatus(HouseStatusEnum.YFB.getCode());
		houseOperateLogEntity.setToStatus(HouseStatusEnum.ZPSHWTG.getCode());
		String resultHouseOperateLogEntityString = JsonEntityTransform.Object2Json(houseOperateLogEntity);
		String findHouseAuditNoLogTimeJson = houseIssueService.findHouseAuditNoLogTime(resultHouseOperateLogEntityString);
		int findHouseAuditNoLogTime = SOAResParseUtil.getIntFromDataByKey(findHouseAuditNoLogTimeJson, "count");
		request.setAttribute("findHouseAuditNoLogTime", findHouseAuditNoLogTime);

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
	 */
	public HouseRoomExtEntity getRoomBaseAndExtInfo(String roomFid, HouseRoomExtEntity houseRoomExt){
		String roomExtJson = houseIssueService.getRoomExtByRoomFid(roomFid);
		DataTransferObject roomExtDto = JsonEntityTransform.json2DataTransferObject(roomExtJson);
		if(roomExtDto.getCode()==DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "houseIssueService.getRoomExtByRoomFid错误,roomFid={},结果:{}", roomFid, roomExtJson);
		}else{
			houseRoomExt = roomExtDto.parseData("roomExt", new TypeReference<HouseRoomExtEntity>() {});
		}
		return houseRoomExt;
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
	
	/**
	 * 根据房源fid获取房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param houseBaseFid
	 * @throws SOAParseException
	 * @return
	 */
	private HouseSurveyMsgEntity findHouseSurveyMsg(String houseBaseFid) throws SOAParseException {
		String resultJson = houseSurveyService.findHouseSurveyMsgByHouseFid(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "houseSurveyService.findHouseSurveyMsgByHouseFid error:{}", resultJson);
			return null;
		} else {
			HouseSurveyMsgEntity surveyEntity = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseSurveyMsgEntity.class);
			return surveyEntity;
		}
	}

	/**
	 * 获取指定房源配置项对应值的名称集合
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午4:36:03
	 *
	 * @param houseConfList
	 * @param enumVoList
	 * @param dicCode
	 * @return
	 */
	private List<String> getSelectSubDic(List<HouseConfMsgEntity> houseConfList, List<EnumVo> enumVoList, String dicCode) {
		List<String> list = new ArrayList<String>();
		for (HouseConfMsgEntity houseConfMsg : houseConfList) {
			if (Check.NuNStr(houseConfMsg.getDicVal()) || !dicCode.equals(houseConfMsg.getDicCode())) {
				continue;
			}
			for (EnumVo enumVo : enumVoList) {
				if(!dicCode.equals(enumVo.getKey())){
					continue;
				}
				List<EnumVo> values = enumVo.getSubEnumVals();
				for (EnumVo value : values) {
					if (houseConfMsg.getDicVal().equals(value.getKey())) {
						list.add(value.getText());
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取指定房源配置项对应值的名称集合
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午4:36:03
	 *
	 * @param houseConfList
	 * @param enumVoList
	 * @param dicCode
	 * @return
	 */
	private List<String> getSelecEnum(List<HouseConfMsgEntity> houseConfList, List<EnumVo> enumVoList, String dicCode) {
		List<String> list = new ArrayList<String>();
		for (HouseConfMsgEntity houseConfMsg : houseConfList) {
			if (Check.NuNStr(houseConfMsg.getDicVal()) || !dicCode.equals(houseConfMsg.getDicCode())) {
				continue;
			}
			for (EnumVo enumVo : enumVoList) {
				if(houseConfMsg.getDicVal().equals(enumVo.getKey())){
					list.add(enumVo.getText());
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 获取指定房源配置项的对应值
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午5:14:28
	 *
	 * @param houseConfList
	 * @param dicCode
	 * @return
	 */
	private String getSelectValue(List<HouseConfMsgEntity> houseConfList, String dicCode) {
		for (HouseConfMsgEntity houseConfMsg : houseConfList) {
			if (!Check.NuNStr(dicCode) && dicCode.equals(houseConfMsg.getDicCode())) {
				return houseConfMsg.getDicVal();
			}
		}
		return null;
	}

	/**
	 * 
	 * 房源管理-强制下架房源
	 *
	 * @author liujun
	 * @created 2016年4月12日 下午5:13:22
	 *
	 * @param houseBaseFid
	 * @return
	 */
	@RequestMapping("forceDownHouse")
	@ResponseBody
	public DataTransferObject forceDownHouse(String houseBaseFid, String mobile, String remark) {
		try {
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.forceDownHouse(houseBaseFid, operaterFid, remark);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.SUCCESS){
				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getHouseName()) 
						|| Check.NuNStr(houseBaseMsg.getHouseSn())) {
					return dto;
				}
				//2017-1.16版本，修改消息，添加推送消息
				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getUid()) || Check.NuNStr(landlord.getCustomerMobile())) {
					return dto;
				}
				Map<String,String> paramMap = new HashMap<>();
				paramMap.put("{1}",houseBaseMsg.getHouseName());
				paramMap.put("{2}",houseBaseMsg.getHouseSn());
				this.sendSmsToLandlord(paramMap, landlord.getCustomerMobile(),"房源或房间强制下架提醒房东");
				//推送消息
				Map<String,String> pushParamMap = new HashMap<>();
				pushParamMap.put("{1}",houseBaseMsg.getHouseName());
				pushParamMap.put("{2}",houseBaseMsg.getHouseSn());
				pushMsgToLandlord(pushParamMap, landlord.getUid(), RentWayEnum.HOUSE.getCode(), houseBaseFid, null,"房源强制下架提醒房东");
				
			}
			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 房源管理-强制下架房间
	 *
	 * @author liujun
	 * @created 2016年4月12日 下午5:13:22
	 *
	 * @param houseRoomFid
	 * @return
	 */
	@RequestMapping("forceDownRoom")
	@ResponseBody
	public DataTransferObject forceDownRoom(String houseRoomFid, String mobile, String remark) {
		try {
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.forceDownRoom(houseRoomFid, operaterFid, remark);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.SUCCESS){
				HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(houseRoomFid);
				if (Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getRoomName()) 
						|| Check.NuNStr(houseRoomMsg.getRoomSn())) {
					return dto;
				}
				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseRoomMsg.getHouseBaseFid());
				if (Check.NuNObj(houseBaseMsg)
						|| Check.NuNStr(houseBaseMsg.getHouseSn())) {
					return dto;
				}
				//2017-1.16版本，修改消息，添加推送消息
				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getUid()) || Check.NuNStr(landlord.getCustomerMobile())) {
					return dto;
				}
				
				Map<String,String> paramMap = new HashMap<>();
				paramMap.put("{1}",houseRoomMsg.getRoomName());
				paramMap.put("{2}",houseRoomMsg.getRoomSn());
				this.sendSmsToLandlord(paramMap, landlord.getCustomerMobile(),"房源或房间强制下架提醒房东");
				//推送消息
				Map<String,String> pushParamMap = new HashMap<>();
				pushParamMap.put("{1}",houseRoomMsg.getRoomName());
				pushParamMap.put("{2}",houseRoomMsg.getRoomSn());
				pushMsgToLandlord(pushParamMap, landlord.getUid(), RentWayEnum.ROOM.getCode(), houseBaseMsg.getFid(), houseRoomFid,"房源强制下架提醒房东");
			}
			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 房源管理-重新发布房源
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	@RequestMapping("reIssueHouse")
	@ResponseBody
	public DataTransferObject reIssueHouse(String houseBaseFid, String remark) {
		try {
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.reIssueHouse(houseBaseFid, operaterFid, remark);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 房源管理-重新发布房间
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param houseRoomFid
	 * @return
	 */
	@RequestMapping("reIssueRoom")
	@ResponseBody
	public DataTransferObject reIssueRoom(String houseRoomFid, String remark) {
		try {
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.reIssueRoom(houseRoomFid, operaterFid, remark);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 小区信息列表
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午5:32:31
	 *
	 * @param request
	 */
	@RequestMapping("housePhyMsgList")
	public void housePhyMsgList(HttpServletRequest request){
		cascadeDistricts(request);
	}

	/**
	 * 
	 * 小区列表数据
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午6:33:36
	 *
	 * @param housePhyListDto
	 * @return
	 */
	@RequestMapping("showHousePhyMsgList")
	@ResponseBody
	public PageResult showHousePhyMsgList(HousePhyListDto housePhyListDto){
		try {
			String resultJson = troyHouseMgtService.searchHousePhyMsgList(JsonEntityTransform
					.Object2Json(housePhyListDto));
			PageResult pageResult = new PageResult();
			pageResult.setRows(SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HousePhyMsgEntity.class));
			pageResult.setTotal(SOAResParseUtil.getLongFromDataByKey(resultJson, "count"));
			return pageResult;
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}

	/**
	 * 
	 * 更新楼盘基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月12日 下午11:56:51
	 *
	 * @param housePhyListDto
	 */
	@RequestMapping("updateHousePhyMsg")
	@ResponseBody
	public DataTransferObject updateHousePhyMsg(HousePhyListDto housePhyListDto){
		try {
			String resultJson=null;
			if(!Check.NuNStr(housePhyListDto.getNewPhyFid())){
				resultJson=troyHouseMgtService.updateHouseBasePhyFid(housePhyListDto.getNewPhyFid(), housePhyListDto.getOldPhyFid());
			}else{
				HousePhyMsgEntity housePhyMsgEntity=new HousePhyMsgEntity();
				housePhyMsgEntity.setZoMobile(housePhyListDto.getZoMobile());
				housePhyMsgEntity.setZoName(housePhyListDto.getZoName());
				housePhyMsgEntity.setFid(housePhyListDto.getOldPhyFid());
				resultJson=troyHouseMgtService.updateHousePhyMsg(JsonEntityTransform.Object2Json(housePhyMsgEntity));
			}
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 房源管理-房源管家审核
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:45:50
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@Deprecated // modified by liujun 2017-02-22
	@RequestMapping("auditHouseInfo")
	public void auditHouseInfo(HttpServletRequest request, String houseFid, Integer rentWay) throws SOAParseException {
		double start = System.currentTimeMillis() ;
		request.setAttribute("isShow", false);
		getHouseDetail(request, houseFid, rentWay);
		double end = System.currentTimeMillis() ;
		LogUtil.info(LOGGER, "auditHouseInfo-getHouseDetail 执行时间={}", end-start);
	}

	/**
	 * 
	 * 房源管理-房源管家审核通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseBaseFid
	 * @param remark
	 * @return
	 */
	@Deprecated // modified by liujun 2017-02-22
	@RequestMapping("approveHouseInfo")
	@ResponseBody
	public DataTransferObject approveHouseInfo(String houseBaseFid, String remark, String addtionalInfo, Integer houseChannel) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(houseBaseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源逻辑id不能为空");
				return dto;
			}

			if(!Check.NuNObj(houseChannel) && HouseChannelEnum.CH_ZHIYING.getCode()!=houseChannel && HouseChannelEnum.CH_FANGDONG.getCode()!=houseChannel){
				LogUtil.info(LOGGER,"房源渠道不正确,houseChannel={}", houseChannel);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源渠道不正确");
				return dto;
			}

			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.approveHouseInfo(houseBaseFid, operaterFid, remark, addtionalInfo);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getHouseName())
						|| Check.NuNStr(houseBaseMsg.getLandlordUid()) || Check.NuNStr(houseBaseMsg.getHouseSn())) {
					return dto;
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getRealName())) {
					return dto;
				}

				houseBaseMsg.setHouseChannel(houseChannel);
				troyHouseMgtService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));//更新房源渠道

				//给地推管家和维护管家发送短信 
				/*this.sendMsgToHouseGuard(houseBaseFid, landlord.getRealName(), houseBaseMsg.getHouseName(), houseBaseMsg.getHouseSn(),
						MessageTemplateCodeEnum.HOUSE_ZO_AUDIT_SUCCESS.getCode(), MessageTemplateCodeEnum.HOUSE_ZO_AUDIT_SUCCESS.getCode());*/

			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}


	/**
	 * 根据房源逻辑id查询房源基本信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param houseBaseFid
	 * @throws SOAParseException 
	 * @return
	 */
	private HouseBaseMsgEntity findHouseBaseMsgEntity(String houseBaseFid) throws SOAParseException {
		String resultJson = houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "houseIssueService.searchHouseBaseMsgByFid错误,houseBaseFid={},结果:{}",
					houseBaseFid, resultJson);
			return null;
		} else {
			HouseBaseMsgEntity houseBaseMsg = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", HouseBaseMsgEntity.class);
			return houseBaseMsg;
		}
	}

	/**
	 * 根据客户uid查询客户信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param uid
	 * @throws SOAParseException 
	 * @return
	 */
	private CustomerBaseMsgEntity findCustomerBaseMsgEntity(String uid) throws SOAParseException {
		String resultJson = customerInfoService.getCustomerInfoByUid(uid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "customerInfoService.getCustomerInfoByUid错误,uid={},结果:{}",
					uid, resultJson);
			return null;
		} else {
			CustomerBaseMsgEntity customer = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "customerBase", CustomerBaseMsgEntity.class);
			return customer;
		}
	}

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
	private HouseGuardRelEntity findHouseGuardRelEntity(String houseBaseFid) throws SOAParseException {
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
	}

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
	private EmployeeEntity findEmployeeEntity(String empCode) throws SOAParseException {
		String resultJson = employeeService.findEmployeeByEmpCode(empCode);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}", empCode);
			return null;
		} else {
			EmployeeEntity empPush =SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
			return empPush;
		}
	}

	/**
	 * 
	 * 根据员工表fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午10:09:48
	 *
	 * @param empCode
	 * @return
	 * @throws SOAParseException
	 */
	private EmployeeEntity findEmployeeEntityByFid(String empCode) throws SOAParseException {
		String resultJson = employeeService.findEmployeByEmpFid(empCode);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}", empCode);
			return null;
		} else {
			EmployeeEntity empPush =SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
			return empPush;
		}
	}

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
	private void sendSms(String mobile, Map<String, String> paramsMap, int smsCode) {
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile(mobile);
		smsRequest.setParamsMap(paramsMap);
		smsRequest.setSmsCode(String.valueOf(smsCode));
		smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	}

	/**
	 * 
	 * 房源管理-房源管家审核不通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseBaseFid
	 * @param remark
	 * @return
	 */
	@Deprecated // modified by liujun 2017-02-22
	@RequestMapping("unApproveHouseInfo")
	@ResponseBody
	public DataTransferObject unApproveHouseInfo(String houseBaseFid, String remark, String addtionalInfo) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(houseBaseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源逻辑id不能为空");
				return dto;
			}
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService
					.unApproveHouseInfo(houseBaseFid, operaterFid, remark, addtionalInfo);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getHouseName())
						|| Check.NuNStr(houseBaseMsg.getLandlordUid()) || Check.NuNStr(houseBaseMsg.getHouseSn())) {
					return dto;
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord)) {
					return dto;
				}

				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("{1}", houseBaseMsg.getHouseName());
				paramMap.put("{2}", houseBaseMsg.getHouseSn());
				if(!Check.NuNStr(landlord.getCustomerMobile())){
					//发送短信给房东
					this.sendSms(landlord.getCustomerMobile(), paramMap,
							MessageTemplateCodeEnum.HOUSE_BUTLER_AUDIT_FAIL.getCode());
				}

				try{
					//发送极光推送
					JpushRequest jpushRequest = new JpushRequest();
					jpushRequest.setParamsMap(paramMap);
					jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
					jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
					jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_BUTLER_AUDIT_FAIL.getCode()));
					jpushRequest.setTitle("房源审核通知");
					jpushRequest.setUid(landlord.getUid());
					//自定义消息
					Map<String, String> extrasMap = new HashMap<>();
					extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
					extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
					extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
					extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
					extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
					String url = MAPP_URL+String.format(JpushConst.HOUSE_ROOM_DETAIL_URL,houseBaseFid,"",0);
					extrasMap.put("url",url);
					jpushRequest.setExtrasMap(extrasMap);
					smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
				}catch (Exception e){
					LogUtil.error(LOGGER,"极光推送失败e={}",e);
				}

				if (Check.NuNStr(landlord.getRealName())) {
					return dto;
				}

				/*	HouseGuardRelEntity houseGuardRel = this.findHouseGuardRelEntity(houseBaseFid);
				if(!Check.NuNObj(houseGuardRel)){
					if(!Check.NuNStr(houseGuardRel.getEmpGuardCode())){
						EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRel.getEmpGuardCode());
						if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", landlord.getRealName());
							paramsMap.put("{2}", houseBaseMsg.getHouseName());
							paramsMap.put("{3}", houseBaseMsg.getHouseSn());
							this.sendSms(empGuard.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_ZO_AUDIT_FAIL.getCode());
						}
					}
				}*/
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 房源管理-房间信息审核通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseRoomFid
	 * @param remark
	 * @return
	 */
	@Deprecated // modified by liujun 2017-02-22
	@RequestMapping("approveRoomInfo")
	@ResponseBody
	public DataTransferObject approveRoomInfo(String houseRoomFid, String remark, String addtionalInfo, Integer houseChannel) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(houseRoomFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间逻辑id不能为空");
				return dto;
			}

			if(!Check.NuNObj(houseChannel) && HouseChannelEnum.CH_ZHIYING.getCode()!=houseChannel && HouseChannelEnum.CH_FANGDONG.getCode()!=houseChannel){
				LogUtil.info(LOGGER,"房源渠道不正确,houseChannel={}", houseChannel);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源渠道不正确");
				return dto;
			}

			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.approveRoomInfo(houseRoomFid, operaterFid, remark, addtionalInfo);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(houseRoomFid);
				if (Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getHouseBaseFid())
						|| Check.NuNStr(houseRoomMsg.getRoomName()) || Check.NuNStr(houseRoomMsg.getRoomSn())) {
					return dto;
				}

				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseRoomMsg.getHouseBaseFid());
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getLandlordUid())) {
					return dto;
				}

				houseBaseMsg.setHouseChannel(houseChannel);
				troyHouseMgtService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));//更新房源渠道	

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getRealName())) {
					return dto;
				}

				//给地推管家和维护管家发送短信
				/*this.sendMsgToHouseGuard(houseRoomMsg.getHouseBaseFid(), landlord.getRealName(), houseRoomMsg.getRoomName(), houseRoomMsg.getRoomSn(),
						MessageTemplateCodeEnum.HOUSE_ZO_AUDIT_SUCCESS.getCode(), MessageTemplateCodeEnum.HOUSE_ZO_AUDIT_SUCCESS.getCode());*/
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 根据房间逻辑id查询房间信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param houseRoomFid
	 * @throws SOAParseException 
	 * @return
	 */
	private HouseRoomMsgEntity findHouseRoomMsgEntity(String houseRoomFid) throws SOAParseException {
		String resultJson = houseIssueService.searchHouseRoomMsgByFid(houseRoomFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "houseIssueService.searchHouseRoomMsgByFid错误,houseRoomFid={},结果:{}",
					houseRoomFid, resultJson);
			return null;
		} else {
			HouseRoomMsgEntity houseRoomMsg = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", HouseRoomMsgEntity.class);
			return houseRoomMsg;
		}
	}

	/**
	 * 
	 * 房源管理-房间信息审核不通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseRoomFid
	 * @param remark
	 * @return
	 */
	@Deprecated // modified by liujun 2017-02-22
	@RequestMapping("unApproveRoomInfo")
	@ResponseBody
	public DataTransferObject unApproveRoomInfo(String houseRoomFid, String remark, String addtionalInfo) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(houseRoomFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间逻辑id不能为空");
				return dto;
			}
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.unApproveRoomInfo(houseRoomFid, operaterFid, remark, addtionalInfo);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(houseRoomFid);
				if (Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getHouseBaseFid())
						|| Check.NuNStr(houseRoomMsg.getRoomName()) || Check.NuNStr(houseRoomMsg.getRoomSn())) {
					return dto;
				}

				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseRoomMsg.getHouseBaseFid());
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getLandlordUid())) {
					return dto;
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord)) {
					return dto;
				}

				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("{1}", houseRoomMsg.getRoomName());
				paramMap.put("{2}", houseRoomMsg.getRoomSn());
				if(!Check.NuNStr(landlord.getCustomerMobile())){
					//发送短信给房东
					this.sendSms(landlord.getCustomerMobile(), paramMap,
							MessageTemplateCodeEnum.HOUSE_BUTLER_AUDIT_FAIL.getCode());
				}

				if (Check.NuNStr(landlord.getRealName())) {
					return dto;
				}

				try{
					//发送极光推送
					JpushRequest jpushRequest = new JpushRequest();
					jpushRequest.setParamsMap(paramMap);
					jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
					jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
					jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_BUTLER_AUDIT_FAIL.getCode()));
					jpushRequest.setTitle("房源审核通知");
					jpushRequest.setUid(landlord.getUid());
					//自定义消息
					Map<String, String> extrasMap = new HashMap<>();
					extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
					extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_1);
					extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
					extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
					extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
					String url = MAPP_URL+String.format(JpushConst.HOUSE_ROOM_DETAIL_URL,houseRoomMsg.getHouseBaseFid(),houseBaseMsg.getFid(),1);
					extrasMap.put("url",url);

					jpushRequest.setExtrasMap(extrasMap);
					smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
				}catch (Exception e){
					LogUtil.error(LOGGER,"极光推送失败e={}",e);
				}


				/*HouseGuardRelEntity houseGuardRel = this.findHouseGuardRelEntity(houseRoomMsg.getHouseBaseFid());
				if(!Check.NuNObj(houseGuardRel)){
					if(!Check.NuNStr(houseGuardRel.getEmpGuardCode())){
						EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRel.getEmpGuardCode());
						if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", landlord.getRealName());
							paramsMap.put("{2}", houseRoomMsg.getRoomName());
							paramsMap.put("{3}", houseRoomMsg.getRoomSn());
							this.sendSms(empGuard.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_ZO_AUDIT_FAIL.getCode());
						}
					}
				}*/
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 房源管理-房源品质审核通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseBaseFid
	 * @param remark
	 * @return
	 */
	@RequestMapping("approveHousePic")
	@ResponseBody
	public DataTransferObject approveHousePic(String landlordUid, String houseBaseFid, String remark,
			String auditCause, Integer houseChannel, String houseQualityGrade) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(!Check.NuNObj(houseChannel) && HouseChannelEnum.CH_ZHIYING.getCode()!=houseChannel && HouseChannelEnum.CH_FANGDONG.getCode()!=houseChannel){
				LogUtil.info(LOGGER,"房源渠道不正确,houseChannel={}", houseChannel);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源渠道不正确");
				return dto;
			}

			if (Check.NuNStr(houseQualityGrade)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源品质登记不能为空");
				return dto;
			}

			if(Check.NuNStr(landlordUid)){
				LogUtil.error(LOGGER,"房东uid不能为空,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东uid不能为空");
				return dto;
			}

			String customerJson = customerInfoService.getCustomerInfoByUid(landlordUid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if(customerDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"customerInfoService.getCustomerInfoByUid接口调用失败,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("查询房东信息失败");
				return dto;
			}

			CustomerBaseMsgEntity landlord = SOAResParseUtil
					.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
			if(Check.NuNObj(landlord)){
				LogUtil.error(LOGGER,"房东信息不存在,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东信息不存在");
				return dto;
			}

			if(landlord.getAuditStatus() != AuditStatusEnum.COMPLETE.getCode()){
				LogUtil.info(LOGGER,"房东认证信息未通过,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东认证信息未通过");
				return dto;
			}

			//保存房源渠道
			String houseBaseDetailJson = houseIssueService.searchHouseBaseMsgByFid(houseBaseFid); 
			DataTransferObject houseBaseDetailDto = JsonEntityTransform.json2DataTransferObject(houseBaseDetailJson);
			if(houseBaseDetailDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"查询房源信息失败,houseBaseFid={}", houseBaseFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("查询房源信息失败");
				return dto;
			}

			HouseBaseMsgEntity houseBase = SOAResParseUtil.getValueFromDataByKey(houseBaseDetailJson, "obj", HouseBaseMsgEntity.class);
			if (Check.NuNObj(houseBase)) {
				LogUtil.info(LOGGER,"房源不存在,houseBaseFid={}", houseBaseFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto;
			}

			/*houseBase.setHouseChannel(houseChannel);
			String upResult = troyHouseMgtService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBase));
			DataTransferObject upResultDto = JsonEntityTransform.json2DataTransferObject(upResult);
			if(upResultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"保存房源渠道信息失败,houseBaseFid={}", houseBaseFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存房源渠道信息失败");
				return dto;
			}*/
			HouseBaseExtDto houseBaseExtDto = new HouseBaseExtDto();
			houseBaseExtDto.setFid(houseBase.getFid());
			houseBaseExtDto.setHouseChannel(houseChannel);

			HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
			houseBaseExt.setHouseBaseFid(houseBase.getFid());
			houseBaseExt.setHouseQualityGrade(houseQualityGrade);
			houseBaseExtDto.setHouseBaseExt(houseBaseExt);
			String upResult = troyHouseMgtService.updateHouseBaseAndExt(JsonEntityTransform.Object2Json(houseBaseExtDto));
			DataTransferObject upResultDto = JsonEntityTransform.json2DataTransferObject(upResult);
			if(upResultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"保存房源渠道信息失败,houseBaseFid={}", houseBaseFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存房源渠道信息失败");
				return dto;
			}

			String operaterFid = UserUtil.getCurrentUserFid();
			HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
			auditDto.setHouseFid(houseBaseFid);
			auditDto.setOperaterFid(operaterFid);
			auditDto.setRemark(remark);
			auditDto.setAuditCause(auditCause);
			String resultJson = troyHouseMgtService.approveHousePic(JsonEntityTransform.Object2Json(auditDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return this.extraOperate(landlord, houseBaseFid, RentWayEnum.HOUSE.getCode(), operaterFid, dto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 扩展操作
	 * 1.给房东绑定400电话
	 * 2.向房东发送房源上架短信
	 * 3.向房东推送房源上架消息
	 *
	 * @author liujun
	 * @created 2016年5月13日
	 *
	 * @param landlord
	 * @param houseFid
	 * @param rentWay
	 * @param operaterFid
	 * @param dto
	 * @throws SOAParseException 
	 * @return
	 */
	private DataTransferObject extraOperate(CustomerBaseMsgEntity landlord, String houseFid,
			int rentWay, String operaterFid, DataTransferObject dto) throws SOAParseException {
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "品质审核返回信息,landlordUid={},houseFid={},rentWay={},operaterFid={},msg={}", 
					landlord.getUid(), houseFid, rentWay, operaterFid,dto.getMsg());
			return dto;
		}

		//异步绑定银行卡信息
		telExtensionService.bindZiroomPhoneAsynchronous(landlord.getUid(), operaterFid);

		if(Check.NuNStr(houseFid)){
			LogUtil.info(LOGGER, "houseFid is null or blank");
			return dto;
		}

		String houseName = "";
		String houseSn = "";
		Integer roomType=0;
		AbHouseRelateEntity abHouse=new AbHouseRelateEntity();
		if(rentWay == RentWayEnum.ROOM.getCode()){
			HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(houseFid);
			if (Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getRoomName())
					|| Check.NuNStr(houseRoomMsg.getRoomSn())) {
				LogUtil.info(LOGGER, "houseRoomMsg:{},houseRoomFid={}", JsonEntityTransform.Object2Json(houseRoomMsg), houseFid);
				return dto;
			}
			houseFid = houseRoomMsg.getHouseBaseFid();
			houseName = houseRoomMsg.getRoomName();
			houseSn = houseRoomMsg.getRoomSn();
			roomType = houseRoomMsg.getRoomType();
			abHouse.setHouseFid(houseFid);
			abHouse.setRentWay(rentWay);
			abHouse.setRoomFid(houseRoomMsg.getFid());
		} else if (rentWay == RentWayEnum.HOUSE.getCode()) {
			HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseFid);
			if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getHouseName())
					|| Check.NuNStr(houseBaseMsg.getHouseSn())) {
				LogUtil.info(LOGGER, "houseBaseMsg:{},houseBaseFid={}", JsonEntityTransform.Object2Json(houseBaseMsg), houseFid);
				return dto;
			}
			houseName = houseBaseMsg.getHouseName();
			houseSn = houseBaseMsg.getHouseSn();
			abHouse.setHouseFid(houseFid);
			abHouse.setRentWay(rentWay);
		} else {
			LogUtil.info(LOGGER, "出租方式异常");
			return dto;
		}

		Map<String, String> paramsMap = new HashMap<>();
		paramsMap.put("{1}", houseName);
		paramsMap.put("{2}", houseSn);
		
		//paramsMap.put("{3}", sms_to_all_house_list);
		
		//查询房源房源有没有配置ical日历链接	
		String abResultJson=abHouseService.findAbHouseByHouse(JsonEntityTransform.Object2Json(abHouse));
		AbHouseRelateEntity abHouseRelateEntity=SOAResParseUtil.getValueFromDataByKey(abResultJson, "obj", AbHouseRelateEntity.class);
		// 系统推送消息
		try{
			JpushRequest jpushRequest = new JpushRequest();
			jpushRequest.setParamsMap(paramsMap);
			jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
			jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
			if(!Check.NuNObj(abHouseRelateEntity)){
				if(roomType==RoomTypeEnum.HALL_TYPE.getCode()){
					jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_ONLINE_AB_ROOMTYPE.getCode()));
					jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_ONLINE_AB_ROOMTYPE.getName());
				}else{
					jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_ONLINE_AB.getCode()));
					jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_ONLINE_AB.getName());
				}
			} else {
				if(roomType==RoomTypeEnum.HALL_TYPE.getCode()){
					jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_ONLINE_AB_ROOMTYPE.getCode()));
					jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_ONLINE_AB_ROOMTYPE.getName());
				}else{
					jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_ONLINE_MSG.getCode()));
					jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_ONLINE_MSG.getName());
				}
			}
			jpushRequest.setUid(landlord.getUid());
			//自定义消息
			Map<String, String> extrasMap = new HashMap<>();
			extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
			extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_6);
			extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
			extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
			extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
			jpushRequest.setExtrasMap(extrasMap);
			smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
		}catch(Exception e){
			LogUtil.info(LOGGER, "极光推送异常");
		}

		if(!Check.NuNStr(landlord.getCustomerMobile())){
			String shortLink = null;
			String result = shortChainMapService.getMinsuHomeJump();
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
			if(resultDto.getCode()==DataTransferObject.SUCCESS){
			     shortLink = resultDto.parseData("shortLink", new TypeReference<String>() {});
			}
			
			Map<String, String> houseOnlineParam = new HashMap<>();
			houseOnlineParam.put("{1}", houseName);
			houseOnlineParam.put("{2}", houseSn);
			houseOnlineParam.put("{3}", shortLink);
			// 发送短信
			if(!Check.NuNObj(abHouseRelateEntity)){
				if(roomType==RoomTypeEnum.HALL_TYPE.getCode()){
					this.sendSms(landlord.getCustomerMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_ONLINE_AB_ROOMTYPE.getCode());
				}else{
					this.sendSms(landlord.getCustomerMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_ONLINE_AB.getCode());
				}
			} else {
				if(roomType==RoomTypeEnum.HALL_TYPE.getCode()){
					this.sendSms(landlord.getCustomerMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_ONLINE_AB_ROOMTYPE.getCode());
				}else{
					this.sendSms(landlord.getCustomerMobile(), houseOnlineParam, MessageTemplateCodeEnum.HOUSE_ONLINE.getCode());
				}
			}
		}

		//给地推管家和维护管家发送短信
		/*this.sendMsgToHouseGuard(houseFid, landlord.getRealName(), houseName, houseSn, 
				MessageTemplateCodeEnum.HOUSE_QA_AUDIT_SUCCESS.getCode() ,MessageTemplateCodeEnum.HOUSE_QA_AUDIT_SUCCESS.getCode());*/

		return dto;
	}

	/**
	 * 
	 * 房源管理-房源品质审核不通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseBaseFid
	 * @param remark
	 * @return
	 */
	@RequestMapping("unApproveHousePic")
	@ResponseBody
	public DataTransferObject unApproveHousePic(String houseBaseFid, String remark, String auditCause) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(houseBaseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源逻辑id不能为空");
				return dto;
			}
			String operaterFid = UserUtil.getCurrentUserFid();
			HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
			auditDto.setHouseFid(houseBaseFid);
			auditDto.setOperaterFid(operaterFid);
			auditDto.setRemark(remark);
			auditDto.setAuditCause(auditCause);
			String resultJson = troyHouseMgtService.unApproveHousePic(JsonEntityTransform.Object2Json(auditDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getFid()) 
						|| Check.NuNObj(houseBaseMsg.getRentWay()) || Check.NuNStr(houseBaseMsg.getLandlordUid())) {
					return dto;
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getUid()) || Check.NuNStr(landlord.getCustomerMobile())) {
					return dto;
				}
				//给地推管家和维护管家发送短信
				/*this.sendMsgToHouseGuard(houseBaseFid, landlord.getRealName(), houseBaseMsg.getHouseName(), houseBaseMsg.getHouseSn(),
						MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL.getCode(), MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL.getCode());*/
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("{1}", remark);
				this.sendSmsToLandlord(paramsMap, landlord.getCustomerMobile(),"品质审核未通过通知");
				//推送消息
				Map<String, String> pushParamsMap = new HashMap<String, String>();
				pushParamsMap.put("{1}", remark);
				this.pushMsgToLandlord(pushParamsMap, landlord.getUid(), houseBaseMsg.getRentWay(), houseBaseMsg.getFid(), null,"品质审核未通过通知");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
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
	 * @param empPushCode
	 * @param empGuardCode
	 */
	/*	private void sendMsgToHouseGuard(String houseBaseFid, String realName, 
			String houseName, String houseSn, int empPushCode, int empGuardCode){
		try {
			HouseGuardRelEntity houseGuardRel = this.findHouseGuardRelEntity(houseBaseFid);
			if(!Check.NuNObj(houseGuardRel)){
				if(!Check.NuNStr(houseGuardRel.getEmpPushCode())){
					EmployeeEntity empPush = this.findEmployeeEntity(houseGuardRel.getEmpPushCode());
					if(!Check.NuNObj(empPush) && !Check.NuNStr(empPush.getEmpMobile())){
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("{1}", realName);
						paramsMap.put("{2}", houseName);
						paramsMap.put("{3}", houseSn);
						this.sendSms(empPush.getEmpMobile(), paramsMap, empPushCode);
					}
				}

				if(!Check.NuNStr(houseGuardRel.getEmpGuardCode())
						&& !houseGuardRel.getEmpGuardCode().equals(houseGuardRel.getEmpPushCode())){
					EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRel.getEmpGuardCode());
					if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("{1}", realName);
						paramsMap.put("{2}", houseName);
						paramsMap.put("{3}", houseSn);
						this.sendSms(empGuard.getEmpMobile(), paramsMap,empGuardCode);
					}
				}
			}
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "向地推管家和维护管家发送短信异常e={}", e);
		}

	}*/

	/**
	 * 向房东发送短信
	 *
	 * @author liujun
	 * @created 2017年2月27日
	 *
	 * @param auditCause
	 * @param mobile 
	 */
	private void sendSmsToLandlord(Map<String, String> paramsMap, String mobile,String source) {
		String shortLink = null;
		String result = shortChainMapService.getMinsuHomeJump();
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(result);
		if(resultDto.getCode()==DataTransferObject.SUCCESS){
		     shortLink = resultDto.parseData("shortLink", new TypeReference<String>() {});
		}
		
		if("品质审核未通过通知".equals(source)){
			paramsMap.put("{2}", shortLink); // 添加唤醒App链接
			LogUtil.info(LOGGER, "品质审核未通过通知,shortLink={},paramsMap={}", shortLink,JsonEntityTransform.Object2Json(paramsMap));
			this.sendSms(mobile, paramsMap, MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_LANDLORD_SMS.getCode());
		}else{
			paramsMap.put("{3}", shortLink); // 添加唤醒App链接
			LogUtil.info(LOGGER, "房源或房间强制下架提醒房东,shortLink={},paramsMap={}", shortLink,JsonEntityTransform.Object2Json(paramsMap));
			this.sendSms(mobile, paramsMap, MessageTemplateCodeEnum.HOUSE_FORCE_OFFLINE.getCode());
		}
	}
	
	/**
	 * 向房东推送消息
	 *
	 * @author liujun
	 * @param uid 
	 * @param auditCause 
	 * @param houseBaseFid 
	 * @param houseRoomFid 
	 * @param rentWay 
	 * @created 2017年2月27日
	 *
	 */
	private void pushMsgToLandlord(Map<String, String> paramsMap, String uid, Integer rentWay, String houseBaseFid, String houseRoomFid,String source) {
		LogUtil.info(LOGGER, "推送消息入参，,uid={},rentWay={},source={}", uid, rentWay, source);
		// 系统推送消息
		try {
			JpushRequest jpushRequest = new JpushRequest();
			if("品质审核未通过通知".equals(source)){
				jpushRequest.setParamsMap(paramsMap);
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_LANDLORD_MSG.getCode()));
				jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_LANDLORD_MSG.getName());
				LogUtil.info(LOGGER, "品质审核未通过推送，   jpushRequest={}", JsonEntityTransform.Object2Json(jpushRequest));
			}else{
				jpushRequest.setParamsMap(paramsMap);
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_FORCE_OFFLINE_MSG.getCode()));
				jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_FORCE_OFFLINE_MSG.getName());
				LogUtil.info(LOGGER, "强制下架推送，   jpushRequest={}", JsonEntityTransform.Object2Json(jpushRequest));
			}
			
			/*JpushRequest jpushRequest = new JpushRequest();
			jpushRequest.setParamsMap(paramsMap);
			jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
			jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
			jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_LANDLORD_MSG.getCode()));
			jpushRequest.setTitle(MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL_LANDLORD_MSG.getName());
			jpushRequest.setUid(uid);
			//自定义消息
			Map<String, String> extrasMap = new HashMap<>();
			extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
			extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_9);
			extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
			extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
			extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
			extrasMap.put("houseBaseFid",houseBaseFid);
			extrasMap.put("rentWay",rentWay.toString());
			if(rentWay == RentWayEnum.ROOM.getCode()){
				extrasMap.put("roomFid",houseRoomFid);
			}
			String url = MAPP_URL + String.format(JpushConst.HOUSE_ROOM_DETAIL_URL, houseBaseFid, houseRoomFid, rentWay);
			extrasMap.put("url",url);
			jpushRequest.setExtrasMap(extrasMap);*/
			
			
			
			
			jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
			jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
			jpushRequest.setUid(uid);
			//自定义消息
			Map<String, String> extrasMap = new HashMap<>();
			extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
			extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_6);
			extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
			extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
			extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
			extrasMap.put("houseBaseFid",houseBaseFid);
			extrasMap.put("rentWay",rentWay.toString());
			if(rentWay == RentWayEnum.ROOM.getCode()){
				extrasMap.put("roomFid",houseRoomFid);
			}
			jpushRequest.setExtrasMap(extrasMap);
			smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
		} catch (Exception e) {
			LogUtil.info(LOGGER, "极光推送异常");
		}
	}

	/**
	 * 
	 * 房源管理-房间信息审核通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param landlordUid
	 * @param remark
	 * @return
	 */
	@RequestMapping("approveRoomPic")
	@ResponseBody
	public DataTransferObject approveRoomPic(String landlordUid, String houseRoomFid, String remark, String auditCause,
			Integer houseChannel, String houseQualityGrade) {
		DataTransferObject dto = new DataTransferObject();
		try {

			if( !Check.NuNObj(houseChannel) && HouseChannelEnum.CH_ZHIYING.getCode()!=houseChannel && HouseChannelEnum.CH_FANGDONG.getCode()!=houseChannel){
				LogUtil.info(LOGGER,"房源渠道不正确,houseChannel={}", houseChannel);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源渠道不正确");
				return dto;
			}

			if(Check.NuNStr(landlordUid)){
				LogUtil.error(LOGGER,"房东uid不能为空,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东uid不能为空");
				return dto;
			}

			String customerJson = customerInfoService.getCustomerInfoByUid(landlordUid);
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if(customerDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"customerInfoService.getCustomerInfoByUid接口调用失败,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("查询房东信息失败");
				return dto;
			}

			CustomerBaseMsgEntity landlord = SOAResParseUtil
					.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
			if(Check.NuNObj(landlord)){
				LogUtil.error(LOGGER,"房东信息不存在,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东信息不存在");
				return dto;
			}

			if(landlord.getAuditStatus() != AuditStatusEnum.COMPLETE.getCode()){
				LogUtil.info(LOGGER,"房东认证信息未通过,landlordUid={}", landlordUid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房东认证信息未通过");
				return dto;
			}

			String houseJson =troyHouseMgtService.searchRoomDetailByFid(houseRoomFid);			
			DataTransferObject HouseDto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if(HouseDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"查询房源信息失败,houseRoomFid={}", houseRoomFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("查询房源信息失败");
				return dto;
			}

			HouseMsgVo houseVo= SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseMsgVo.class);
			if (Check.NuNObj(houseVo)) {
				LogUtil.info(LOGGER,"房源不存在,houseRoomFid={}", houseRoomFid);
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房源不存在");
				return dto;
			}

			/*houseVo.setHouseChannel(houseChannel);
			HouseBaseMsgEntity hBaseMsgEntity = new HouseBaseMsgEntity();
			BeanUtils.copyProperties(houseVo, hBaseMsgEntity);

			String upResult = troyHouseMgtService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(hBaseMsgEntity));
			DataTransferObject upResultDto = JsonEntityTransform.json2DataTransferObject(upResult);
			if(upResultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"保存房源渠道信息失败,houseBaseFid={}", hBaseMsgEntity.getFid());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存房源渠道信息失败");
				return dto;
			}*/

			HouseBaseExtDto houseBaseExtDto = new HouseBaseExtDto();
			houseBaseExtDto.setFid(houseVo.getFid());
			houseBaseExtDto.setHouseChannel(houseChannel);

			HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
			houseBaseExt.setHouseBaseFid(houseVo.getFid());
			houseBaseExt.setHouseQualityGrade(houseQualityGrade);
			houseBaseExtDto.setHouseBaseExt(houseBaseExt);
			String upResult = troyHouseMgtService.updateHouseBaseAndExt(JsonEntityTransform.Object2Json(houseBaseExtDto));
			DataTransferObject upResultDto = JsonEntityTransform.json2DataTransferObject(upResult);
			if(upResultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"保存房源渠道信息失败,houseBaseFid={}", houseVo.getFid());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存房源渠道信息失败");
				return dto;
			}

			String operaterFid = UserUtil.getCurrentUserFid();
			HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
			auditDto.setHouseFid(houseRoomFid);
			auditDto.setOperaterFid(operaterFid);
			auditDto.setRemark(remark);
			auditDto.setAuditCause(auditCause);
			String resultJson = troyHouseMgtService.approveRoomPic(JsonEntityTransform.Object2Json(auditDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return this.extraOperate(landlord, houseRoomFid, RentWayEnum.ROOM.getCode(), operaterFid, dto);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 房源管理-房间信息审核不通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseRoomFid
	 * @param remark
	 * @return
	 */
	@RequestMapping("unApproveRoomPic")
	@ResponseBody
	public DataTransferObject unApproveRoomPic(String houseRoomFid, String remark, String auditCause) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(houseRoomFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("房间逻辑id不能为空");
				return dto;
			}
			String operaterFid = UserUtil.getCurrentUserFid();
			HouseQualityAuditDto auditDto = new HouseQualityAuditDto();
			auditDto.setHouseFid(houseRoomFid);
			auditDto.setOperaterFid(operaterFid);
			auditDto.setRemark(remark);
			auditDto.setAuditCause(auditCause);
			String resultJson = troyHouseMgtService.unApproveRoomPic(JsonEntityTransform.Object2Json(auditDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(houseRoomFid);
				if (Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getFid())
						|| Check.NuNStr(houseRoomMsg.getHouseBaseFid())) {
					return dto;
				}

				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseRoomMsg.getHouseBaseFid());
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getFid()) 
						|| Check.NuNObj(houseBaseMsg.getRentWay())|| Check.NuNStr(houseBaseMsg.getLandlordUid())) {
					return dto;
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getUid()) || Check.NuNStr(landlord.getCustomerMobile())) {
					return dto;
				}

				//给地推管家和维护管家发送短信
				/*this.sendMsgToHouseGuard(houseRoomMsg.getHouseBaseFid(), landlord.getRealName(), houseRoomMsg.getRoomName(), houseRoomMsg.getRoomSn(),
						MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL.getCode(), MessageTemplateCodeEnum.HOUSE_QA_AUDIT_FAIL.getCode());*/
				Map<String, String> smsParamsMap = new HashMap<String, String>();
				smsParamsMap.put("{1}", remark);
				this.sendSmsToLandlord(smsParamsMap, landlord.getCustomerMobile(),"品质审核未通过通知");
				//推送消息
				Map<String, String> pushParamsMap = new HashMap<String, String>();
				pushParamsMap.put("{1}", remark);
				this.pushMsgToLandlord(pushParamsMap, landlord.getUid(), houseBaseMsg.getRentWay(), houseBaseMsg.getFid(),
						houseRoomMsg.getFid(),"品质审核未通过通知");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 上传图片详情
	 *
	 * @author bushujie
	 * @created 2016年4月14日 下午3:14:12
	 *
	 * @param request
	 * @param housePicDto
	 * @throws SOAParseException 
	 */
	@RequestMapping("housePicAuditDetail")
	public void upHousePicAuditDetail(HttpServletRequest request,HousePicDto housePicDto) throws SOAParseException{
		if (!Check.NuNStr(housePicDto.getCameramanName())){
			try {
				housePicDto.setCameramanName(URLDecoder.decode(housePicDto.getCameramanName(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String resultJson=troyHouseMgtService.housePicAuditListForTroy(JsonEntityTransform.Object2Json(housePicDto));
		DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		HousePicAuditVo housePicAuditVo =resultDto.parseData("housePicAuditVo", new TypeReference<HousePicAuditVo>() {});
		//根据房东uid调客户库接口查询房东信息
		String customerJson=customerMsgManagerService.getCutomerVo(housePicAuditVo.getLandlordUid());
		CustomerVo customer=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerVo", CustomerVo.class);
		if(!Check.NuNObj(customer)){
			housePicAuditVo.setLandlordName(customer.getRealName());
			housePicAuditVo.setLandlordMobile(customer.getCustomerMobile());
		}
		//户型
		StringBuffer houseModel=new StringBuffer();
		houseModel.append(housePicAuditVo.getRoomNum()).append("室");
		houseModel.append(housePicAuditVo.getHallNum()).append("厅");
		houseModel.append(housePicAuditVo.getToiletNum()).append("卫");
		houseModel.append(housePicAuditVo.getKitchenNum()).append("厨");
		houseModel.append(housePicAuditVo.getBalconyNum()).append("阳台");
		housePicAuditVo.setHouseModel(houseModel.toString());
		Map<Integer, String> rentWayEnum=RentWayEnum.getEnumMap();
		housePicAuditVo.setRentWayStr(rentWayEnum.get(housePicAuditVo.getRentWay()));
		if(!Check.NuNCollection(housePicAuditVo.getRoomPicList())){
			for(HousePicVo vo:housePicAuditVo.getRoomPicList()){
				vo.setPicType(RentWayEnum.HOUSE.getCode());
			}
		}

		DataTransferObject dto = this.getValidParams();
		if(dto.getCode() == DataTransferObject.SUCCESS){
			@SuppressWarnings("unchecked")
			Map<String, EnumVo> ruleMap = (Map<String, EnumVo>) dto.getData().get("ruleMap");
			StringBuilder picRules = new StringBuilder("照片上传标准:");
			if (ruleMap.containsKey("minPixel")) {
				picRules.append(ruleMap.get("minPixel").getText());
				picRules.append(ruleMap.get("minPixel").getKey());
				picRules.append(";");
			}
			if (ruleMap.containsKey("minDpi")) {
				picRules.append(ruleMap.get("minDpi").getText());
				picRules.append(ruleMap.get("minDpi").getKey());
				picRules.append("DPI;");
			}
			if (ruleMap.containsKey("picScale")) {
				picRules.append(ruleMap.get("picScale").getText());
				picRules.append(ruleMap.get("picScale").getKey());
				picRules.append(";");
			}
			if (ruleMap.containsKey("maxSize")) {
				picRules.append(ruleMap.get("maxSize").getText());
				picRules.append(ruleMap.get("maxSize").getKey());
				picRules.append("KB");
			}
			request.setAttribute("picRules", picRules);
		}
		if (!Check.NuNStr(housePicDto.getCameramanName()) && !Check.NuNStr(housePicDto.getCameramanMobile())){
			housePicAuditVo.setCameramanName(housePicDto.getCameramanName());
			housePicAuditVo.setCameramanMobile(housePicDto.getCameramanMobile());
		}

		request.setAttribute("housePicAuditVo", housePicAuditVo);
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		request.setAttribute("picWaterM", picWaterM);
		request.setAttribute("houseRoomFid", housePicDto.getHouseRoomFid());
		request.setAttribute("operateType",request.getParameter("operateType"));
	}

	/**
	 * 
	 * 上传图片到服务器
	 *
	 * @author bushujie
	 * @created 2016年4月15日 上午11:37:18
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadHousePic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String uploadHousePic(@RequestParam MultipartFile[] file,HttpServletRequest request) throws IOException{
		DataTransferObject dto=new DataTransferObject();
		try {
			String houseBaseFid=request.getParameter("houseBaseFid");
			String roomFid=request.getParameter("roomFid");
			String picType=request.getParameter("picType");
			String operateType = request.getParameter("operateType");
			//如果是合租唯一标示单独房间
			String houseRoomFid=request.getParameter("houseRoomFid");

			if (Check.NuNStr(picType)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型不能为空");
				return dto.toJsonString();
			}

			/**判断图片数量是否超过限制开始**/
			HousePicDto housePicDto=new HousePicDto();
			housePicDto.setHouseBaseFid(houseBaseFid);
			housePicDto.setHouseRoomFid(roomFid);
			int picTypeInt = Integer.valueOf(picType).intValue();
			housePicDto.setPicType(picTypeInt);
			String countResultJson=troyHouseMgtService.findHousePicCountByType(JsonEntityTransform.Object2Json(housePicDto));
			DataTransferObject countDto=JsonEntityTransform.json2DataTransferObject(countResultJson);
			String countStr=countDto.parseData("count", new TypeReference<String>() {});
			Integer count=0;
			if(!Check.NuNStr(countStr)){
				count=Integer.valueOf(countStr);
			}

			HousePicTypeEnum enumeration = HousePicTypeEnum.getEnumByCode(picTypeInt);
			if (Check.NuNObj(enumeration)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型错误");
				return dto.toJsonString();
			}

			if ((file.length + count) > enumeration.getMax()) {
				dto=new DataTransferObject();
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("超过上传图片的最大限制");
				return dto.toJsonString();
			}
			/**判断图片数量是否超过限制结束**/

			DataTransferObject validDto = this.getValidParams();
			if(validDto.getCode() == DataTransferObject.ERROR){
				return validDto.toJsonString();
			}
			@SuppressWarnings("unchecked")
			Map<String, Integer> validMap = (Map<String, Integer>) validDto.getData().get("validMap");

			Map<Integer, String> housePicTypeEnum=HousePicTypeEnum.getEnumMap();
			List<HousePicMsgEntity> picList=new ArrayList<HousePicMsgEntity>();
			List<HouseExif> uploadFailedList = new ArrayList<HouseExif>();
			String errorMsg = "";
			//循环上传图片
			for(MultipartFile mulfile:file){
				// 校验照片是否符合上传规则

				DataTransferObject validateDto = new DataTransferObject();
				try {
					validateDto = this.validatePicture(mulfile, validMap);					
				} catch (Exception e) {
					LogUtil.error(LOGGER, "图片检验异常:{}",e);
					FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, mulfile.getOriginalFilename(),mulfile.getBytes(), housePicTypeEnum.get(picType), 0l,mulfile.getOriginalFilename());
					LogUtil.info(LOGGER, "异常校验图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}",mulfile.getOriginalFilename(),mulfile.getContentType(),mulfile.isEmpty(),mulfile.getName());
					LogUtil.info(LOGGER, "异常校验图片上传返回信息:{}", JsonEntityTransform.Object2Json(fileResponse));
					HouseExif houseExif = new HouseExif();
					houseExif.setPicName(mulfile.getOriginalFilename());
					houseExif.setPicSize(BigDecimalUtil.div(mulfile.getSize(), 1024));
					uploadFailedList.add(houseExif);
					errorMsg ="图片校验异常";
					continue;
				}

				HouseExif houseExif = (HouseExif) validateDto.getData().get("houseExif");
				if(validateDto.getCode() == DataTransferObject.ERROR){
					uploadFailedList.add(houseExif);
					errorMsg = validateDto.getMsg();
					continue;
				}

				FileInfoResponse fileResponse=storageService.upload(storageKey, storageLimit, mulfile.getOriginalFilename(),mulfile.getBytes(), housePicTypeEnum.get(picType), 0l,mulfile.getOriginalFilename());
				if(!"0".equals(fileResponse.getResponseCode())){
					LogUtil.info(LOGGER, "图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}",mulfile.getOriginalFilename(),mulfile.getContentType(),mulfile.isEmpty(),mulfile.getName());
					LogUtil.error(LOGGER, "上传图片异常:{},房源id:{}, 房间id:{}",fileResponse.getErrorInfo(), houseBaseFid,houseRoomFid);
					continue;
				}
				HousePicMsgEntity housePicMsgEntity=new HousePicMsgEntity();
				BeanUtils.copyProperties(houseExif, housePicMsgEntity);
				housePicMsgEntity.setFid(UUIDGenerator.hexUUID());
				housePicMsgEntity.setHouseBaseFid(houseBaseFid);
				housePicMsgEntity.setRoomFid(roomFid);
				housePicMsgEntity.setPicType(picTypeInt);
				housePicMsgEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
				//用于区分上传照片来源
				if(!Check.NuNStrStrict(operateType)){
					housePicMsgEntity.setOperateType(Integer.parseInt(operateType));
				}else{
					housePicMsgEntity.setOperateType(1);
				}
				housePicMsgEntity.setPicName(fileResponse.getFile().getOriginalFilename());
				housePicMsgEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
				housePicMsgEntity.setPicServerUuid(fileResponse.getFile().getUuid());
				picList.add(housePicMsgEntity);
			}
			//保存图片信息
			housePicDto.setPicList(picList);
			//设置单独房间唯一标示
			housePicDto.setHouseRoomFid(houseRoomFid);
			String resultJson=houseIssueService.newSaveHousePicMsgList(JsonEntityTransform.Object2Json(housePicDto));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if (resultDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.info(LOGGER, "上传照片调用接口失败,参数:{},错误信息resultDto={}", JsonEntityTransform.Object2Json(picList),resultDto.getMsg());
			}
			dto=JsonEntityTransform.json2DataTransferObject(resultJson);
			dto.putValue("list", picList);
			if(!Check.NuNCollection(uploadFailedList)){
				dto.setErrCode(2);//用于前台判断
				dto.setMsg(errorMsg);
				dto.putValue("uploadFailedList", uploadFailedList);
				dto.putValue("count", uploadFailedList.size());
			}
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto.toJsonString();
		} 
	}

	/**
	 * 校验照片是否符合上传规则
	 *
	 * @author liujun
	 * @created 2016年9月14日
	 *
	 * @param mulfile
	 * @param validMap 
	 * @return
	 * @throws ImageProcessingException 
	 * @throws IOException 
	 * @throws MetadataException 
	 */
	private DataTransferObject validatePicture(MultipartFile mulfile, Map<String, Integer> validMap)
			throws ImageProcessingException, IOException, MetadataException {
		DataTransferObject dto = new DataTransferObject();
		HouseExif exif = new HouseExif();
		if(Check.NuNMap(validMap)){
			return dto;
		}

		String contentType = mulfile.getContentType();
		if(!"image/jpeg".equalsIgnoreCase(contentType)&&!"image/pjpeg".equalsIgnoreCase(contentType)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("照片类型错误");
			return dto;
		}

		Metadata metadata = ImageMetadataReader.readMetadata(mulfile.getInputStream());
		Directory jpegDirectory = null;
		int widthPixel = -1;
		int heightPixel  = -1;
		if(metadata.containsDirectoryOfType(JpegDirectory.class)){
			jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
			LogUtil.info(LOGGER, "当前图片像素widthPixel={},heightPixel={},jpegDirectory={}", jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH),jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT),jpegDirectory);
			widthPixel = Check.NuNStrStrict(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH))?-1:Integer.valueOf(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH) );
			heightPixel = Check.NuNStrStrict(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT))?-1:Integer.valueOf(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT));
		}


		StringBuilder sb = new StringBuilder();
		sb.append(mulfile.getOriginalFilename());
		sb.append("不符合照片上传规则:");

		if (widthPixel * heightPixel  != 0&&validMap.containsKey("minPixel") && widthPixel * heightPixel < validMap.get("minPixel").intValue()) {
			dto.setErrCode(DataTransferObject.ERROR);
			sb.append("当前照片");
			sb.append(widthPixel * heightPixel);
			sb.append("像素小于最小值");
			sb.append(validMap.get("minPixel").intValue());
			sb.append("像素;");
		}


		int widthDpi = -1;
		int heightDpi = -1;
		String widthDpiStr = "-1";
		String heightDpiStr = "-1";
		if (metadata.containsDirectoryOfType(JfifDirectory.class)) {
			Directory jfifDirectory = metadata.getFirstDirectoryOfType(JfifDirectory.class);
			widthDpiStr = jfifDirectory.getString(JfifDirectory.TAG_RESX);
			heightDpiStr = jfifDirectory.getString(JfifDirectory.TAG_RESY);
		} else if (metadata.containsDirectoryOfType(ExifIFD0Directory.class)) {
			Directory exifIFD0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			widthDpiStr = exifIFD0.getString(ExifDirectoryBase.TAG_X_RESOLUTION);
			heightDpiStr = exifIFD0.getString(ExifDirectoryBase.TAG_Y_RESOLUTION);
		}

		if(!Check.NuNStrStrict(widthDpiStr)) widthDpi = Integer.valueOf(widthDpiStr);
		if(!Check.NuNStrStrict(heightDpiStr)) heightDpi = Integer.valueOf(heightDpiStr);

		//因为部分jpg/jepg图片dpi为-1,这些图片暂不进行分辨率校验
		if(widthDpi != -1 && heightDpi != -1){
			if (validMap.containsKey("minDpi")
					&& (widthDpi < validMap.get("minDpi").intValue() || heightDpi < validMap.get("minDpi").intValue())) {
				dto.setErrCode(DataTransferObject.ERROR);
				sb.append("当前照片分辨率");
				sb.append(Math.min(widthDpi, heightDpi));
				sb.append("DPI小于最小分辨率");
				sb.append(validMap.get("minDpi").intValue());
				sb.append("DPI;");
			}

		}
		exif.setWidthDpi(widthDpi);
		exif.setHeightDpi(heightDpi);

		if (validMap.containsKey("widthScale") && validMap.containsKey("heightScale")) {
			// 宽度值
			int widthScale = validMap.get("widthScale").intValue();
			// 高度值
			int heightScale = validMap.get("heightScale").intValue();

			//上限比例
			double maxScale = BigDecimalUtil.div(widthScale
					* Double.valueOf(upperLimitRate).doubleValue(), heightScale, 10);
			//下限比例
			double minScale = BigDecimalUtil.div(widthScale
					* Double.valueOf(lowerLimitRate).doubleValue(), heightScale, 10);
			// 照片比例
			double picScale = 0.0d;
			if (heightPixel > widthPixel) {
				int temp = widthScale;
				widthScale = heightScale;
				heightScale = temp;
				picScale = BigDecimalUtil.div(heightPixel, widthPixel, 10);
			} else {
				picScale = BigDecimalUtil.div(widthPixel, heightPixel, 10);
			}

			/**
			 *
			 * 照片比例限制
			 */
			if (picScale < minScale || picScale > maxScale) {
				dto.setErrCode(DataTransferObject.ERROR);
				sb.append("当前照片比例");
				sb.append(widthPixel);
				sb.append(":");
				sb.append(heightPixel);
				sb.append("不等于上传比例");
				sb.append(widthScale);
				sb.append(":");
				sb.append(heightScale);
				sb.append(";");
			}

		}
		exif.setWidthPixel(widthPixel);
		exif.setHeightPixel(heightPixel);

		double picSize = BigDecimalUtil.div(mulfile.getSize(), 1024);
		if (validMap.containsKey("maxSize") && picSize > validMap.get("maxSize").intValue()) {
			dto.setErrCode(DataTransferObject.ERROR);
			sb.append("当前照片大小");
			sb.append(picSize);
			sb.append("KB");
			sb.append("大于照片最大上传大小");
			sb.append(validMap.get("maxSize").intValue());
			sb.append("KB;");
		}
		exif.setPicSize(picSize);
		exif.setPicName(mulfile.getOriginalFilename());
		dto.setMsg(sb.toString());
		dto.putValue("houseExif", exif);
		return dto;
	}

	/**
	 * 获取照片校验规则
	 *
	 * @author liujun
	 * @created 2016年9月13日
	 *
	 * @return 
	 * @throws SOAParseException 
	 */
	private DataTransferObject getValidParams() throws SOAParseException {
		DataTransferObject dto = new DataTransferObject();
		Map<String, Integer> validMap = new HashMap<String, Integer>();
		Map<String, EnumVo> ruleMap = new HashMap<String, EnumVo>();
		String minPixelJson = cityTemplateService.getEffectiveSelectEnum(null,
				ProductRulesEnum0017Enum.ProductRulesEnum0017001.getValue());
		dto = JsonEntityTransform.json2DataTransferObject(minPixelJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum接口失败,dicCode={}", 
					ProductRulesEnum0017Enum.ProductRulesEnum0017001.getValue());
			return dto;
		}
		List<EnumVo> minPixelVoList = SOAResParseUtil.getListValueFromDataByKey(minPixelJson, "selectEnum", EnumVo.class);

		if(Check.NuNCollection(minPixelVoList) || minPixelVoList.size() > 1){
			LogUtil.error(LOGGER, "[照片规则]最小像素属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]最小像素属性值错误");
			return dto;
		} else {
			String minPixelStr = minPixelVoList.get(0).getKey();
			if(Check.NuNStr(minPixelStr) || (!ZERO_STRING.equals(minPixelStr) && minPixelStr.indexOf("*") == -1)){
				LogUtil.error(LOGGER, "[照片规则]最小像素属性值错误");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("[照片规则]最小像素属性值错误");
				return dto;
			}

			if(!ZERO_STRING.equals(minPixelStr)){// 属性值为0表示不限制
				String[] pixelArray = minPixelStr.split("\\*");
				Integer minPixel = Integer.parseInt(pixelArray[0]) * Integer.parseInt(pixelArray[1]);
				validMap.put("minPixel", minPixel);
				ruleMap.put("minPixel", minPixelVoList.get(0));
			}
		}

		String minDpiJson = cityTemplateService.getEffectiveSelectEnum(null,
				ProductRulesEnum0017Enum.ProductRulesEnum0017002.getValue());
		dto = JsonEntityTransform.json2DataTransferObject(minDpiJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum接口失败,dicCode={}", 
					ProductRulesEnum0017Enum.ProductRulesEnum0017002.getValue());
			return dto;
		}
		List<EnumVo> minDpiVoList = SOAResParseUtil.getListValueFromDataByKey(minDpiJson, "selectEnum", EnumVo.class);

		if (Check.NuNCollection(minDpiVoList) || minDpiVoList.size() > 1) {
			LogUtil.error(LOGGER, "[照片规则]最小分辨率属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]最小分辨率属性值错误");
			return dto;
		} else {
			String minDpiStr = minDpiVoList.get(0).getKey();
			if(Check.NuNStr(minDpiStr)){
				LogUtil.error(LOGGER, "[照片规则]最小分辨率属性值错误");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("[照片规则]最小分辨率属性值错误");
				return dto;
			}

			if(!ZERO_STRING.equals(minDpiStr)){// 属性值为0表示不限制
				Integer minDpi = Integer.valueOf(minDpiStr); 
				validMap.put("minDpi", minDpi);
				ruleMap.put("minDpi", minDpiVoList.get(0));
			}
		}

		String picScaleJson = cityTemplateService.getEffectiveSelectEnum(null,
				ProductRulesEnum0017Enum.ProductRulesEnum0017003.getValue());
		dto = JsonEntityTransform.json2DataTransferObject(picScaleJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum接口失败,dicCode={}", 
					ProductRulesEnum0017Enum.ProductRulesEnum0017003.getValue());
			return dto;
		}
		List<EnumVo> picScaleVoList = SOAResParseUtil.getListValueFromDataByKey(picScaleJson, "selectEnum", EnumVo.class);

		if(Check.NuNCollection(picScaleVoList) || picScaleVoList.size() > 1){
			LogUtil.error(LOGGER, "[照片规则]照片比例属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]照片比例属性值错误");
			return dto;
		} else {
			String picScaleStr = picScaleVoList.get(0).getKey();
			if(Check.NuNStr(picScaleStr) || (!ZERO_STRING.equals(picScaleStr) && picScaleStr.indexOf(":") == -1)){
				LogUtil.error(LOGGER, "[照片规则]照片比例属性值错误");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("[照片规则]照片比例属性值错误");
				return dto;
			}

			if(!ZERO_STRING.equals(picScaleStr)){// 属性值为0表示不限制
				String[] scaleArray = picScaleStr.split(":");
				Integer widthScale = Integer.valueOf(scaleArray[0]); 
				Integer heightScale = Integer.valueOf(scaleArray[1]); 
				validMap.put("widthScale", widthScale);
				validMap.put("heightScale", heightScale);
				ruleMap.put("picScale", picScaleVoList.get(0));
			}
		}

		String maxSizeJson = cityTemplateService.getEffectiveSelectEnum(null,
				ProductRulesEnum0017Enum.ProductRulesEnum0017004.getValue());
		dto = JsonEntityTransform.json2DataTransferObject(maxSizeJson);
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum接口失败,dicCode={}", 
					ProductRulesEnum0017Enum.ProductRulesEnum0017004.getValue());
			return dto;
		}
		List<EnumVo> maxSizeVoList = SOAResParseUtil.getListValueFromDataByKey(maxSizeJson, "selectEnum", EnumVo.class);

		if(Check.NuNCollection(maxSizeVoList) || maxSizeVoList.size() > 1){
			LogUtil.error(LOGGER, "[照片规则]照片最大上传属性值错误");
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("[照片规则]照片最大上传属性值错误");
			return dto;
		} else {
			String maxSizeStr = maxSizeVoList.get(0).getKey();
			if(Check.NuNStr(maxSizeStr)){
				LogUtil.error(LOGGER, "[照片规则]照片最大上传属性值错误");
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("[照片规则]照片最大上传属性值错误");
				return dto;
			}

			if(!ZERO_STRING.equals(maxSizeStr)){// 属性值为0表示不限制
				Integer maxSize = Integer.valueOf(maxSizeStr); 
				validMap.put("maxSize", maxSize);
				ruleMap.put("maxSize", maxSizeVoList.get(0));
			}
		}

		dto.putValue("validMap", validMap);
		dto.putValue("ruleMap", ruleMap);
		return dto;
	}

	/**
	 * 房源管理-跳转房源照片修改审核页面
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午11:59:02
	 *
	 * @param request
	 */
	@RequestMapping("listModifiedPic")
	public void listModifiedPic(HttpServletRequest request) {
		cascadeDistricts(request);
	}

	/**
	 * 
	 * 房源管理-查询房源信息审核列表页
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午11:59:19
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showModifiedPic")
	@ResponseBody
	public PageResult showModifiedPic(HouseRequestDto houseRequest,HttpServletRequest request) {
		List<Integer> houseStatusList = new ArrayList<Integer>();
		houseStatusList.add(HouseStatusEnum.SJ.getCode());
		houseRequest.setHouseStatusList(houseStatusList);
		try {
			//判断是否合租
			if (houseRequest.getRentWay() == RentWayEnum.ROOM.getCode()) {
				exchangeCondition(houseRequest);
			}

			Map<String,CustomerBaseMsgEntity> landlordUidMap = new HashMap<String,CustomerBaseMsgEntity>();
			// 房东姓名或房东手机不为空,调用用户库查询房东uid
			if(!Check.NuNStr(houseRequest.getLandlordName()) || !Check.NuNStr(houseRequest.getLandlordMobile())){
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(houseRequest.getLandlordName());
				paramDto.setCustomerMobile(houseRequest.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);

				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断调用状态
				if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<CustomerBaseMsgEntity> customerList = customerDto.parseData("listCustomerBaseMsg",
						new TypeReference<List<CustomerBaseMsgEntity>>() {});
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(customerList)){
					LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<String> landlordUidList = new ArrayList<String>();
				for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
					landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
					landlordUidList.add(customerBaseMsg.getUid());
				}
				houseRequest.setLandlordUidList(landlordUidList);
			}
			//特殊权限参数赋值
			Integer roleType=(Integer) request.getAttribute("roleType");
			if(!Check.NuNObj(roleType)){
				houseRequest.setRoleType(roleType);
			}
			houseRequest.setEmpCode((String) request.getAttribute("empCode"));
			houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
			String resultJson = troyHouseMgtService.searchPicUnapproveedHouseList(JsonEntityTransform.Object2Json(houseRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(houseRequest));
				return new PageResult();
			}

			List<HouseResultVo> houseMsgList = resultDto.parseData("list", new TypeReference<List<HouseResultVo>>() {});
			for (HouseResultVo houseResultVo : houseMsgList) {
				CustomerBaseMsgEntity customer = null;
				if(Check.NuNMap(landlordUidMap)){
					String customerJson = customerInfoService.getCustomerInfoByUid(houseResultVo.getLandlordUid());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerJson);
					if (dto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "调用接口失败,landlordUid={}", houseResultVo.getLandlordUid());
						continue;
					}
					customer = dto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
				} else {
					customer = landlordUidMap.get(houseResultVo.getLandlordUid());
				}
				if(Check.NuNObj(customer)){
					continue;
				}
				houseResultVo.setLandlordName(customer.getRealName());
				houseResultVo.setLandlordMobile(customer.getCustomerMobile());
				
				//填充房间类型roomType
				if(!Check.NuNObj(houseResultVo.getRoomType())){
						houseResultVo.setRoomTypeStr(RoomTypeEnum.getEnumByCode(houseResultVo.getRoomType()).getName());
				}
			}

			PageResult pageResult = new PageResult();
			pageResult.setRows(houseMsgList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}

	/**
	 * 
	 * 房源管理-跳转房源照片修改审核页面
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午11:59:02
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("auditModifiedPic")
	public void auditModifiedPic(HttpServletRequest request, String houseFid, Integer rentWay) throws SOAParseException {
		getHouseDetail(request, houseFid, rentWay);

		/**************获取待审核房源信息，如果有待审核信息并进行填充处理  @author:lusp @date:2017/7/31*************/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		try{
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				houseUpdateFieldAuditNewlogEntity.setRoomFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
				String resultJson = troyHouseMgtService.searchRoomDetailByFid(houseFid);
				HouseMsgVo houseDetail = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseMsgVo.class);
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseDetail.getFid());
			}
			houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
			String auditLogListJson = troyHouseMgtService.getHouseUpdateFieldAuditNewlogByCondition(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity));
			DataTransferObject auditLogListDto = JsonEntityTransform.json2DataTransferObject(auditLogListJson);
			if(auditLogListDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"getHouseDetail(),获取待审核字段失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity),auditLogListDto.getMsg());
				return;
			}
			List<HouseFieldAuditLogVo> list = SOAResParseUtil.getListValueFromDataByKey(auditLogListJson,"list",HouseFieldAuditLogVo.class);
			if(!Check.NuNCollection(list)){
				HouseBaseMsgEntity oldHouseBaseMsg = new HouseBaseMsgEntity();
				HouseBaseExtEntity oldHouseBaseExt = new HouseBaseExtEntity();
				HouseDescEntity oldHouseDesc = new HouseDescEntity();
				HouseRoomMsgEntity oldHouseRoomMsg = new HouseRoomMsgEntity();
				HouseRoomExtEntity oldHouseRoomExt = new HouseRoomExtEntity();

				HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();
				HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();

				HouseBaseMsgEntity houseBaseMsg = (HouseBaseMsgEntity) request.getAttribute("houseBaseMsg");
				HouseBaseExtEntity houseBaseExt = (HouseBaseExtEntity) request.getAttribute("houseBaseExt");
				HouseDescEntity houseDesc = (HouseDescEntity) request.getAttribute("houseDesc");

				this.FilterNotAuditField(houseBaseMsg,oldHouseBaseMsg,list,request);
				this.FilterNotAuditField(houseBaseExt,oldHouseBaseExt,list,request);
				this.FilterNotAuditField(houseDesc,oldHouseDesc,list,request);

				request.setAttribute("houseBaseMsg",houseBaseMsg);
				request.setAttribute("houseBaseExt",houseBaseExt);
				request.setAttribute("houseDesc",houseDesc);
				request.setAttribute("oldHouseBaseMsg",oldHouseBaseMsg);
				request.setAttribute("oldHouseBaseExt",oldHouseBaseExt);
				request.setAttribute("oldHouseDesc",oldHouseDesc);
				
				/**
				 * 情况1，房屋守则修改了，但老值是空，2，房屋守则没修改==》这两种情况无法区分==》导致没有修改，但是让然显示修改 了---------开始
				 */
				request.setAttribute("hasHouseRule",0);
				request.setAttribute("hasRoomRule",0);
				for (HouseFieldAuditLogVo houseFieldAuditLogVo : list) {
					if(houseFieldAuditLogVo.getFieldPath().equals(HouseUpdateLogEnum.House_Desc_House_Rules.getFieldPath())){
						request.setAttribute("hasHouseRule",1);
					}
					if(houseFieldAuditLogVo.getFieldPath().equals(HouseUpdateLogEnum.House_Room_Ext_Room_Rules.getFieldPath())){
						request.setAttribute("hasRoomRule",1);
					}
				}
				/**
				 * 情况1，房屋守则修改了，但老值是空，2，房屋守则没修改==》这两种情况无法区分==》导致没有修改，但是让然显示修改 了---------结束
				 */
				
				if(rentWay == RentWayEnum.ROOM.getCode()){
					List<RoomMsgVo> roomList = (List<RoomMsgVo>)request.getAttribute("roomList");
					if(!Check.NuNCollection(roomList)){
						BeanUtils.copyProperties(roomList.get(0),houseRoomMsgEntity);
						String roomExtJson = houseIssueService.getRoomExtByRoomFid(houseFid);
						houseRoomExtEntity=SOAResParseUtil.getValueFromDataByKey(roomExtJson, "roomExt", HouseRoomExtEntity.class);
						this.FilterNotAuditField(houseRoomMsgEntity,oldHouseRoomMsg,list,request);
						this.FilterNotAuditField(houseRoomExtEntity,oldHouseRoomExt,list,request);
						BeanUtils.copyProperties(houseRoomMsgEntity,roomList.get(0));
					}
					request.setAttribute("roomList",roomList);
					request.setAttribute("houseRoomExtEntity", houseRoomExtEntity);
					request.setAttribute("oldHouseRoomMsg",oldHouseRoomMsg);
					request.setAttribute("oldHouseRoomExt",oldHouseRoomExt);
				}
				//如果默认图片有更改，则需查询图片详细信息，展示图片供审核人员审核
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					if(!Check.NuNStrStrict(oldHouseBaseExt.getDefaultPicFid())){
						String oldHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(oldHouseBaseExt.getDefaultPicFid());
						DataTransferObject oldHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(oldHousePicMsgJson);
						if(oldHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity oldHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(oldHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(oldHousePicMsgEntity)){
								request.setAttribute("oldHouseDefaultPicMsg",oldHousePicMsgEntity);
							}
						}
						String newHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(houseBaseExt.getDefaultPicFid());
						DataTransferObject newHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(newHousePicMsgJson);
						if(newHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity newHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(newHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(newHousePicMsgEntity)){
								request.setAttribute("newHouseDefaultPicMsg",newHousePicMsgEntity);
							}
						}
					}
				}else if(rentWay == RentWayEnum.ROOM.getCode()){
					if(!Check.NuNStrStrict(oldHouseRoomMsg.getDefaultPicFid())){
						String oldHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(oldHouseRoomMsg.getDefaultPicFid());
						DataTransferObject oldHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(oldHousePicMsgJson);
						if(oldHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity oldHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(oldHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(oldHousePicMsgEntity)){
								request.setAttribute("oldHouseDefaultPicMsg",oldHousePicMsgEntity);
							}
						}
						String newHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(houseRoomMsgEntity.getDefaultPicFid());
						DataTransferObject newHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(newHousePicMsgJson);
						if(newHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity newHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(newHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(newHousePicMsgEntity)){
								request.setAttribute("newHouseDefaultPicMsg",newHousePicMsgEntity);
							}
						}
					}
				}

			}
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
		}
		/**************获取待审核房源信息，如果有待审核信息并进行填充处理  @author:lusp @date:2017/7/31*************/

		HouseBaseDto houseBaseDto=new HouseBaseDto();
		houseBaseDto.setHouseFid(houseFid);
		houseBaseDto.setRentWay(rentWay);
		String resultJson=troyHouseMgtService.findNoAuditHousePicList(JsonEntityTransform.Object2Json(houseBaseDto));
		List<HousePicMsgEntity> picList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "picList", HousePicMsgEntity.class);
		request.setAttribute("noAuditPic", picList);
		request.setAttribute("rentWay", rentWay);
	}

	/**
	 * 
	 * 房源管理-房间照片修改审核通过
	 *
	 * @author liujun
	 * @created 2016年4月13日 下午11:47:15
	 *
	 * @param houseFid
	 * @param remark
	 * @return
	 */
	@RequestMapping("approveModifiedPic")
	@ResponseBody
	public DataTransferObject approveModifiedPic(String houseFid, String rentWay, String remark) {
		try {
			String operaterFid = UserUtil.getCurrentUserFid();
			String resultJson = troyHouseMgtService.approveModifiedPic(houseFid, rentWay, operaterFid, remark);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 删除房源图片
	 *
	 * @author bushujie
	 * @created 2016年4月16日 下午3:33:16
	 *
	 * @param roomFid
	 * @return
	 */
	@RequestMapping("deleteHousePic")
	@ResponseBody
	public DataTransferObject deleteHousePic(HousePicDto housePicDto,String roomFid,Integer rentWay){
		DataTransferObject dto=new DataTransferObject();
		/**判断图片是否是最后一张开始**/
		/*String countResultJson=troyHouseMgtService.findHousePicCountByType(JsonEntityTransform.Object2Json(housePicDto));
		DataTransferObject countDto=JsonEntityTransform.json2DataTransferObject(countResultJson);
		String countStr=countDto.parseData("count", new TypeReference<String>() {});
		Integer count=0;
		if(!Check.NuNStr(countStr)){
			count=Integer.valueOf(countStr);
		}
		if(count<=1){
			dto=new DataTransferObject();
			dto.setErrCode(1);
			dto.setMsg("最后一张图片不能删除");
			return dto;
		}
		//判断是否默认图片
		String fid=null;
		if(RentWayEnum.HOUSE.getCode()==rentWay){
			fid=housePicDto.getHouseBaseFid();
		} else if(RentWayEnum.ROOM.getCode()==rentWay) {
			fid=roomFid;
			housePicDto.setHouseRoomFid(roomFid);
		}
		String numResultJson=houseIssueService.newIsDefaultPic(housePicDto.getHousePicFid(),fid,rentWay);
		DataTransferObject numDto=JsonEntityTransform.json2DataTransferObject(numResultJson);
		String numStr=numDto.parseData("num", new TypeReference<String>() {});
		Integer num=0;
		if(!Check.NuNStr(numStr)){
			num=Integer.valueOf(numStr);
		}
		if(num>0){
			dto=new DataTransferObject();
			dto.setErrCode(1);
			dto.setMsg("请重新设置一张默认图片后删除！");
			return dto;
		}*/
		/**判断图片是否是最后一张结束**/

		if(!Check.NuNStr(roomFid)){
			housePicDto.setHouseRoomFid(roomFid);
		}
		housePicDto.setPicSource(1);
		String resultJson=houseIssueService.deleteHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
		dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		return dto;
	}

	/**
	 * 
	 * 批量删除照片
	 *
	 * @author lunan
	 * @created 2016年10月28日 下午2:57:47
	 *
	 * @param housePicDto
	 * @param picFids
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	@RequestMapping("batchDeletes")
	@ResponseBody
	public DataTransferObject batchDeletes(HousePicDto housePicDto,String picFids,String roomFid,Integer rentWay){
		DataTransferObject dto = new DataTransferObject();
		/*LogUtil.info(LOGGER, "传入要全部删除的图片fid参数：{}", picFids);
		if(Check.NuNObj(picFids)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("没有图片可以删除！");
			return dto;
		}
		String[] picFidArr = picFids.split("-");
		for (String fid : picFidArr) {
			housePicDto.getPicFidS().add(fid);
		}*/
		if(!Check.NuNStr(roomFid)){
			housePicDto.setHouseRoomFid(roomFid);
		}
		String resultJson = houseIssueService.delAllHousePicMsgByFid(JsonEntityTransform.Object2Json(housePicDto));
		dto=JsonEntityTransform.json2DataTransferObject(resultJson);
		return dto;
	}

	/**
	 * 
	 * 房源管理-跳转房源品质审核
	 *
	 * @author liujun
	 * @created 2016年4月18日 下午9:00:26
	 *
	 * @param request
	 * @param houseFid
	 * @param rentWay
	 * @throws SOAParseException 
	 */
	@RequestMapping("auditHousePic")
	public void auditHousePic(HttpServletRequest request,String houseName, String landlordUid, String houseFid, Integer rentWay) throws SOAParseException {
		double start = System.currentTimeMillis() ;
		request.setAttribute("houseName", houseName);
		request.setAttribute("landlordUid", landlordUid);
		/*request.setAttribute("approveEnumMap", HouseAuditCauseEnum.Approve.getEnumMap());*/
		request.setAttribute("rejectEnumMap", HouseAuditCauseEnum.REJECT.getEnumMap());
		request.setAttribute("auditFlag",1);
		getHouseDetail(request, houseFid, rentWay);

		/**************获取待审核房源信息，如果有待审核信息并进行填充处理  @author:lusp @date:2017/7/31*************/
		HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
		try{
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				houseUpdateFieldAuditNewlogEntity.setRoomFid(houseFid);
				houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
				String resultJson = troyHouseMgtService.searchRoomDetailByFid(houseFid);
				HouseMsgVo houseDetail = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseMsgVo.class);
				houseUpdateFieldAuditNewlogEntity.setHouseFid(houseDetail.getFid());
			}
			houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(0);
			String auditLogListJson = troyHouseMgtService.getHouseUpdateFieldAuditNewlogByCondition(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity));
			DataTransferObject auditLogListDto = JsonEntityTransform.json2DataTransferObject(auditLogListJson);
			if(auditLogListDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"getHouseDetail(),获取待审核字段失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity),auditLogListDto.getMsg());
				return;
			}
			List<HouseFieldAuditLogVo> list = SOAResParseUtil.getListValueFromDataByKey(auditLogListJson,"list",HouseFieldAuditLogVo.class);
			if(!Check.NuNCollection(list)){
				HouseBaseMsgEntity oldHouseBaseMsg = new HouseBaseMsgEntity();
				HouseBaseExtEntity oldHouseBaseExt = new HouseBaseExtEntity();
				HouseDescEntity oldHouseDesc = new HouseDescEntity();
				HouseRoomMsgEntity oldHouseRoomMsg = new HouseRoomMsgEntity();
				HouseRoomExtEntity oldHouseRoomExt = new HouseRoomExtEntity();

				HouseRoomMsgEntity houseRoomMsgEntity = new HouseRoomMsgEntity();
				HouseRoomExtEntity houseRoomExtEntity = new HouseRoomExtEntity();

				HouseBaseMsgEntity houseBaseMsg = (HouseBaseMsgEntity) request.getAttribute("houseBaseMsg");
				HouseBaseExtEntity houseBaseExt = (HouseBaseExtEntity) request.getAttribute("houseBaseExt");
				HouseDescEntity houseDesc = (HouseDescEntity) request.getAttribute("houseDesc");

				this.FilterNotAuditField(houseBaseMsg,oldHouseBaseMsg,list,request);
				this.FilterNotAuditField(houseBaseExt,oldHouseBaseExt,list,request);
				this.FilterNotAuditField(houseDesc,oldHouseDesc,list,request);

				request.setAttribute("houseBaseMsg",houseBaseMsg);
				request.setAttribute("houseBaseExt",houseBaseExt);
				request.setAttribute("oldHouseBaseMsg",oldHouseBaseMsg);
				request.setAttribute("oldHouseBaseExt",oldHouseBaseExt);

				if(rentWay == RentWayEnum.ROOM.getCode()){
					List<RoomMsgVo> roomList = (List<RoomMsgVo>)request.getAttribute("roomList");
					if(!Check.NuNCollection(roomList)){
						BeanUtils.copyProperties(roomList.get(0),houseRoomMsgEntity);
						BeanUtils.copyProperties(roomList.get(0).getRoomExtEntity(),houseRoomExtEntity);
						this.FilterNotAuditField(houseRoomMsgEntity,oldHouseRoomMsg,list,request);
						this.FilterNotAuditField(houseRoomExtEntity,oldHouseRoomExt,list,request);
						BeanUtils.copyProperties(houseRoomMsgEntity,roomList.get(0));
						BeanUtils.copyProperties(houseRoomExtEntity,roomList.get(0).getRoomExtEntity());
					}
					request.setAttribute("roomList",roomList);
					request.setAttribute("oldHouseRoomMsg",oldHouseRoomMsg);
					request.setAttribute("oldHouseRoomExt",oldHouseRoomExt);
					//分组出来房屋守则
					oldHouseDesc.setHouseRules(oldHouseRoomExt.getRoomRules());
					houseDesc.setHouseRules(roomList.get(0).getRoomExtEntity().getRoomRules());
				}
				request.setAttribute("houseDesc",houseDesc);
				request.setAttribute("oldHouseDesc",oldHouseDesc);
				
				//如果默认图片有更改，则需查询图片详细信息，展示图片供审核人员审核
				if(rentWay == RentWayEnum.HOUSE.getCode()){
					HouseFieldAuditLogVo picVo=getFieldAuditLogVoByKey(list, HouseUpdateLogEnum.House_Base_Ext_Default_Pic_Fid.getFieldPath());
					if(!Check.NuNObj(picVo)){
						String oldHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(picVo.getOldValue());
						DataTransferObject oldHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(oldHousePicMsgJson);
						if(oldHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity oldHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(oldHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(oldHousePicMsgEntity)){
								request.setAttribute("oldHouseDefaultPicMsg",oldHousePicMsgEntity);
							}
						}
						String newHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(picVo.getNewValue());
						DataTransferObject newHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(newHousePicMsgJson);
						if(newHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity newHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(newHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(newHousePicMsgEntity)){
								request.setAttribute("newHouseDefaultPicMsg",newHousePicMsgEntity);
							}
						}
					}
				}else if(rentWay == RentWayEnum.ROOM.getCode()){
					HouseFieldAuditLogVo picVo=getFieldAuditLogVoByKey(list, HouseUpdateLogEnum.House_Room_Msg_Default_Pic_Fid.getFieldPath());
					if(!Check.NuNObj(picVo)){
						String oldHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(picVo.getOldValue());
						DataTransferObject oldHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(oldHousePicMsgJson);
						if(oldHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity oldHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(oldHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(oldHousePicMsgEntity)){
								request.setAttribute("oldHouseDefaultPicMsg",oldHousePicMsgEntity);
							}
						}
						String newHousePicMsgJson = houseIssueService.searchHousePicMsgByFid(picVo.getNewValue());
						DataTransferObject newHousePicMsgDto = JsonEntityTransform.json2DataTransferObject(newHousePicMsgJson);
						if(newHousePicMsgDto.getCode() == DataTransferObject.SUCCESS){
							HousePicMsgEntity newHousePicMsgEntity = SOAResParseUtil.getValueFromDataByKey(newHousePicMsgJson,"obj",HousePicMsgEntity.class);
							if(!Check.NuNObj(newHousePicMsgEntity)){
								request.setAttribute("newHouseDefaultPicMsg",newHousePicMsgEntity);
							}
						}
					}
				}
			}
			//品质审核校验      欧诺弥亚项目     获取AccessToken
			String accessToken = sensitiveWordCheck();
			request.setAttribute("access_token", accessToken);
			request.setAttribute("sensitiveUrl", SENSITIVE_URL);
		}catch (Exception e){
			LogUtil.error(LOGGER,"error:{}",e);
		}
		/**************获取待审核房源信息，如果有待审核信息并进行填充处理  @author:lusp @date:2017/7/31*************/

		double end = System.currentTimeMillis() ;
		LogUtil.info(LOGGER, "auditHousePic-getHouseDetail 执行时间={}", end-start);
	}
	
	//查询审核记录
	private HouseFieldAuditLogVo getFieldAuditLogVoByKey(List<HouseFieldAuditLogVo> list,String fieldPath){
		for (HouseFieldAuditLogVo houseFieldAuditLogVo : list) {
			if(houseFieldAuditLogVo.getFieldPath().equals(fieldPath)){
				return houseFieldAuditLogVo;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 敏感词校验公用方法
	 *
	 * @author loushuai
	 * @created 2017年11月21日 下午8:16:31
	 *
	 * @return String
	 */
	public String  sensitiveWordCheck(){
		 //欧诺弥亚项目     获取AccessToken
		String accessToken = null;
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("username", EUNOMIA_USERNAME);
			param.put("password", EUNOMIA_PASSWORD);
			LogUtil.info(LOGGER, "敏感词校验方法   sensitiveWordCheck username={},password={},EUNOMIA_URL={}", EUNOMIA_USERNAME,EUNOMIA_PASSWORD,EUNOMIA_URL);
			String result  = CloseableHttpUtil.sendFormPost(EUNOMIA_URL, param);
			LogUtil.info(LOGGER, "敏感词校验方法   sensitiveWordCheck result={}", result);
			 if(!Check.NuNStr(result)){
					JSONObject resultObj = JSONObject.parseObject(result);
					int code  = resultObj.getIntValue("code");
					if(code==10000){
						JSONObject jsonObject = resultObj.getJSONObject("data");
						String uid = (String) jsonObject.get("userId");
						String token = (String) jsonObject.get("token");
						accessToken = uid+"#"+token;
					}
			 }
		} catch (Exception e) {
			LogUtil.info(LOGGER, "敏感词校验方法  sensitiveWordCheck， 发生错误", e);
			return accessToken;
		}
		return accessToken;
	}

	public static <T extends Object> void FilterNotAuditField(T newObj,T oldObj,List<HouseFieldAuditLogVo> houseFieldAuditLogVos,HttpServletRequest request)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException,ParseException {
		if(Check.NuNObj(houseFieldAuditLogVos)||Check.NuNObj(newObj)||Check.NuNObj(oldObj)){
			return ;
		}
		Class<?> clazz = newObj.getClass();
		String className = clazz.getName();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields){
			String fieldName = field.getName();
			if("serialVersionUID".equals(fieldName)){
				continue;
			}
			String setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			String getMethodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			Class<?> type = field.getType();
			Method setMethod = clazz.getMethod(setMethodName,type);
			Method getMethod = clazz.getMethod(getMethodName);
			String fieldFullName = className+"."+fieldName;
			for(HouseFieldAuditLogVo houseFieldAuditLogVo:houseFieldAuditLogVos){
				if(houseFieldAuditLogVo.getFieldPath().trim().equals(fieldFullName.trim())){
					setMethod.invoke(oldObj,getMethod.invoke(newObj));
					setMethod.invoke(newObj, StringUtils.string2Object(houseFieldAuditLogVo.getNewValue(),type));
					request.setAttribute(fieldName+"_auditLogId",houseFieldAuditLogVo.getId());
					break;
				}
			}
		}
	}

	/**
	 * 
	 * 判断房源或房间是否已锁定
	 *
	 * @author bushujie
	 * @created 2016年4月23日 下午9:56:49
	 *
	 * @param businessType MSG_AUDIT：信息审核，PIC_AUDIT: 图片审核
	 * @param rentWay   ENTIRE_HOUSE:整租房源, PART_ROOM:合租房间
	 * @param fid 业务fid
	 * @return
	 */
	@RequestMapping("judgeHouseOrRoomLock")
	@ResponseBody
	public DataTransferObject judgeHouseOrRoomLock(String businessType,String rentWay,String fid ){
		DataTransferObject dto=new DataTransferObject();
		CurrentuserVo currentuserEntity=UserUtil.getFullCurrentUser();
		//CurrentuserVo currentuserEntity=(CurrentuserVo) UserUtil.getCurrentUser();
		String key=RedisKeyConst.getHouseLockKey(businessType,rentWay,fid);
		String lockUserJson= null;
		try {
			lockUserJson=redisOperations.get(key);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"redis错误e={}", e);
		}
		if(Check.NuNObj(lockUserJson)){
			try {
				redisOperations.setex(key, RedisKeyConst.HOUSE_LOCK_CACHE_TIME, JsonEntityTransform.Object2Json(currentuserEntity));
			} catch (Exception e) {
				LogUtil.error(LOGGER,"redis错误e={}", e);
			}
			return dto;
		} else {
			CurrentuserVo lockUser=JsonEntityTransform.json2Object(lockUserJson, CurrentuserVo.class);
			if(!lockUser.getFid().equals(currentuserEntity.getFid())){
				dto.setErrCode(1);
				dto.setMsg("房源已被"+ lockUser.getUserAccount() +"[" +  lockUser.getFullName()+"]锁定");
				return dto;
			}
		}
		return dto;
	}

	/**
	 * 房源管理-跳转房源排序权重管理
	 *
	 * @author liujun
	 * @created 2016年4月23日 上午12:20:52
	 *
	 * @param request
	 */
	@RequestMapping("houseWeightList")
	public void listHouseMsgWeight(HttpServletRequest request) {
		// 房源类型
		String houseTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
		request.setAttribute("houseTypeJson", houseTypeJson);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseTypeJson);
		List<EnumVo> houseTypeList = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});


		String  houseManagerMaxString = cityTemplateService.getTextValue(null, ProductRulesEnum.ProductRulesEnum0023.getValue());
		Integer houseManagerMax = SOAResParseUtil.getIntFromDataByKey(houseManagerMaxString, "textValue"); 
		houseManagerMax = Check.NuNObj(houseManagerMax)?5:houseManagerMax;
		request.setAttribute("houseManagerMax", houseManagerMax);  
		request.setAttribute("houseTypeList", houseTypeList);
		request.setAttribute("rentWayJson", JsonEntityTransform.Object2Json(RentWayEnum.getEnumMap()));
	}

	/**
	 * 
	 * 房源管理-查询房源排序权重管理
	 *
	 * @author liujun
	 * @created 2016年4月23日 上午12:22:14
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseWeight")
	@ResponseBody
	public PageResult showHouseMsgWeight(HouseRequestDto houseRequest,HttpServletRequest request) {
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonHouseMsg(houseRequest);
	}

	/**
	 * 
	 * 房源管理-更新房源权重分值
	 *
	 * @author liujun
	 * @created 2016年4月23日 上午12:22:14
	 *
	 * @param houseWeightDto
	 * @return
	 */
	@RequestMapping("editHouseWeight")
	@ResponseBody
	public DataTransferObject editHouseWeight(HouseWeightDto houseWeightDto) {
		try {
			String resultJson = troyHouseMgtService.batchEditHouseWeight(JsonEntityTransform.Object2Json(houseWeightDto));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			DataTransferObject dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}

	/**
	 * 
	 * 插入房源信息页
	 *
	 * @author bushujie
	 * @created 2016年5月3日 下午5:08:53
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("insertHouseMsg")
	public void insertHouseMsg(HttpServletRequest request) throws SOAParseException{
		long start1 = System.currentTimeMillis();
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		long end1 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "insertHouseMsg-getConfCitySelect->time={}", end1 - start1);
		//入住人数限制
		String limitJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
		List<EnumVo> limitList=SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);
		request.setAttribute("limitList", limitList);

		//配套设施列表
		String facilityJson=cityTemplateService.getSelectSubDic(null,ProductRulesEnum.ProductRulesEnum002.getValue());
		List<EnumVo> facilityList=SOAResParseUtil.getListValueFromDataByKey(facilityJson, "subDic", EnumVo.class);
		request.setAttribute("facilityList", facilityList);

		//房源分类
		String houseTypeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
		List<EnumVo> homeTypeList=SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("homeTypeList", homeTypeList);
		long end2 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "insertHouseMsg-入住人数-配套设置列表-房源分类->time={}", end2 - end1);
		//民宿分类
		String homestayJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0013.getValue());
		List<EnumVo> homestayList=SOAResParseUtil.getListValueFromDataByKey(homestayJson, "selectEnum", EnumVo.class);
		request.setAttribute("homestayList", homestayList);

		//优惠规则列表
		String discountJson=cityTemplateService.getSelectSubDic(null,ProductRulesEnum.ProductRulesEnum0012.getValue());
		List<EnumVo> discountList=SOAResParseUtil.getListValueFromDataByKey(discountJson, "subDic", EnumVo.class);

		request.setAttribute("discountList", discountList);
		//押金规则列表
		//		String depositJson=cityTemplateService.getSelectSubDic(null,ProductRulesEnum.ProductRulesEnum008.getValue());
		//		List<EnumVo> depositList=SOAResParseUtil.getListValueFromDataByKey(depositJson, "subDic", EnumVo.class);
		//		request.setAttribute("depositList", depositList);

		request.setAttribute("depositMin",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN);
		request.setAttribute("depositMax",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX);

		//退订政策
		String checkOutJson=cityTemplateService.getSelectSubDic(null,TradeRulesEnum.TradeRulesEnum005.getValue());
		List<EnumVo> checkOutList=SOAResParseUtil.getListValueFromDataByKey(checkOutJson, "subDic", EnumVo.class);
		if(!Check.NuNCollection(checkOutList)){
			List<EnumVo> checkOutListNew= new ArrayList<>();
			for (EnumVo enumVo : checkOutList) {
				if (!enumVo.getKey().equals(TradeRulesEnum005Enum.TradeRulesEnum005004.getValue()) && !enumVo.getKey().equals(TradeRulesEnum005Enum.TradeRulesEnum005005.getValue())) {
					checkOutListNew.add(enumVo);
				}
			}
			checkOutList = checkOutListNew;
		}
		request.setAttribute("checkOutList", checkOutList);
		long end3 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "insertHouseMsg-民宿分类-优惠规则列表-押金规则列表-退订政策->time={}", end3 - end2);
		//入住时间
		String checkInTimeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum003.getValue());
		List<EnumVo> checkInTimeList=SOAResParseUtil.getListValueFromDataByKey(checkInTimeJson, "selectEnum", EnumVo.class);
		request.setAttribute("checkInTimeList", checkInTimeList);

		//退订时间
		String checkOutTimeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum004.getValue());
		List<EnumVo> checkOutTimeList=SOAResParseUtil.getListValueFromDataByKey(checkOutTimeJson, "selectEnum", EnumVo.class);
		request.setAttribute("checkOutTimeList", checkOutTimeList);

		//床类型
		String bedTypeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
		List<EnumVo> bedTypeList=SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
		request.setAttribute("bedTypeList", bedTypeList);	
		long end4 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "insertHouseMsg-入住时间-退订时间-床类型->time={}", end4 - end3);

		//床规格
		String bedSizeJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
		List<EnumVo> bedSizeList=SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
		request.setAttribute("bedSizeList", bedSizeList);	


		//服务列表
		String serviceJson=cityTemplateService.getSelectEnum(null,ProductRulesEnum.ProductRulesEnum0015.getValue());
		List<EnumVo> serviceList=SOAResParseUtil.getListValueFromDataByKey(serviceJson, "selectEnum", EnumVo.class);
		request.setAttribute("serviceList", serviceList);

		//房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		request.setAttribute("priceLow", priceLow);

		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);

		//灵活间隙定价设置
		request.setAttribute("gapList",searchGapFlexList());

		long end5 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "insertHouseMsg-入住时间-退订时间-床类型->time={}", end5 - end4);

		LogUtil.info(LOGGER, "insertHouseMsg->Alltime={}", end5 - start1);
	}

	/**
	 * 
	 * 获取灵活定价配置信息
	 *
	 * @author liujun
	 * @created 2016年12月26日
	 *
	 * @return
	 * @throws SOAParseException
	 */
	private List<HouseConfMsgEntity> searchGapFlexList() throws SOAParseException {
		List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
		for(String gapPrice : gapFlexlist){
			/** 在配置对象中添加要插入的value*/
			HouseConfMsgEntity confEntity = new HouseConfMsgEntity();
			confEntity.setDicCode(gapPrice);
			String textValue = cityTemplateService.getTextValue(null, gapPrice);
			String text = SOAResParseUtil.getValueFromDataByKey(textValue, "textValue", String.class);
			confEntity.setDicVal(text);
			/** 放入集合*/
			confList.add(confEntity);
		}
		return confList;
	}

	/**
	 * 
	 * 保存房源信息
	 *
	 * @author bushujie
	 * @created 2016年5月4日 下午3:21:20
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("saveHouseMsg")
	@ResponseBody
	public DataTransferObject saveHouseMsg(@ModelAttribute HouseInputDto houseInputDto, HttpServletRequest request) throws SOAParseException{	
		LogUtil.info(LOGGER,"保存房源参数："+JsonEntityTransform.Object2Json(houseInputDto));
		DataTransferObject resultDto = new DataTransferObject();
		if(Check.NuNObj(houseInputDto.getHouseBase()) || Check.NuNStr(houseInputDto.getHouseBase().getLandlordUid())){
			resultDto.setErrCode(DataTransferObject.ERROR);
			resultDto.setMsg("房东uid不能为空");
			return resultDto;
		}

		String realNameNick = houseInputDto.getRoomNameNick();
		if(!Check.NuNStr(realNameNick)){
			realNameNick = realNameNick+",";
			String realName[] = realNameNick.split("minsu_se,");
			houseInputDto.setRoomName(Arrays.asList(realName));
		}
		long start1 = System.currentTimeMillis();
		String landlordUid = houseInputDto.getHouseBase().getLandlordUid();
		String customerJson = customerInfoService.getCustomerInfoByUid(landlordUid);
		DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
		long end1 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "saveHouseMsg-getCustomerInfoByUid->time={}", end1 - start1);

		if(customerDto.getCode() == DataTransferObject.ERROR){
			resultDto.setErrCode(DataTransferObject.ERROR);
			resultDto.setMsg("查询房东信息失败");
			return resultDto;
		}
		CustomerBaseMsgEntity customerBase = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {
		});

		if(Check.NuNObj(customerBase)){
			resultDto.setErrCode(DataTransferObject.ERROR);
			resultDto.setMsg("房东信息不存在");
			return resultDto;
		}


		//押金校验
		List<String> depositList=houseInputDto.getDepositList();
		if(Check.NuNCollection(depositList)){
			resultDto.setErrCode(DataTransferObject.ERROR);
			resultDto.setMsg("押金不能为空");
			return resultDto;
		}

		List<String> deposits= new ArrayList<String>();

		for (String depositStr : depositList) {

			int depositVal =Check.NuNStrStrict(depositStr)?0:Integer.parseInt(depositStr);
			if(depositVal<SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN
					||depositVal>SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX){
				resultDto.setErrCode(DataTransferObject.ERROR);
				resultDto.setMsg("押金范围在"+SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN+"元到"+SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX+"元");
				return resultDto;
			}

			//所有押金规则都按固定收取
			deposits.add(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue()+"-"+String.valueOf(depositVal*100));			
		}
		HouseBaseExtEntity houseExt = houseInputDto.getHouseExt();
		houseExt.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
		houseInputDto.setHouseExt(houseExt);
		houseInputDto.setDepositList(deposits);


		long start2 = System.currentTimeMillis();
		StringBuffer houseAddr=new StringBuffer();
		if(SysConst.nation_code.equals(houseInputDto.getHousePhy().getNationCode())){
			if(!Check.NuNStr(houseInputDto.getHousePhy().getCityCode())){
				String codeNameJson=confCityService.getCityNameByCode(houseInputDto.getHousePhy().getCityCode());
				DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(codeNameJson);
				houseAddr.append(dto.getData().get("cityName"));
			}
			if(!Check.NuNStr(houseInputDto.getHousePhy().getAreaCode())){
				String codeNameJson=confCityService.getCityNameByCode(houseInputDto.getHousePhy().getAreaCode());
				DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(codeNameJson);
				houseAddr.append(dto.getData().get("cityName"));
			}
		}else{
			if(!Check.NuNStr(houseInputDto.getHousePhy().getProvinceCode())){
				String codeNameJson=confCityService.getCityNameByCode(houseInputDto.getHousePhy().getProvinceCode());
				DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(codeNameJson);
				houseAddr.append(dto.getData().get("cityName"));
			}
		}
		
		
		long end2 = System.currentTimeMillis();
		LogUtil.info(LOGGER, "saveHouseMsg-getCityNameByCode->time={}", end2 - start2);

		if(!Check.NuNStr(houseInputDto.getHouseExt().getHouseStreet())){
			houseAddr.append(houseInputDto.getHouseExt().getHouseStreet());
		}
		if(!Check.NuNStr(houseInputDto.getHousePhy().getCommunityName())){
			houseAddr.append(houseInputDto.getHousePhy().getCommunityName());
			houseAddr.append(" ");
		}
		if (!Check.NuNStr(houseInputDto.getHouseExt().getBuildingNum())
				&& !ZERO_STRING.equals(houseInputDto.getHouseExt().getBuildingNum())) {
			houseAddr.append(houseInputDto.getHouseExt().getBuildingNum());
			houseAddr.append("号楼");
		}
		if(!Check.NuNStr(houseInputDto.getHouseExt().getUnitNum()) 
				&& !ZERO_STRING.equals(houseInputDto.getHouseExt().getUnitNum())){
			houseAddr.append(houseInputDto.getHouseExt().getUnitNum());
			houseAddr.append("单元");
		}
		if(!Check.NuNStr(houseInputDto.getHouseExt().getFloorNum())
				&& !ZERO_STRING.equals(houseInputDto.getHouseExt().getFloorNum())){
			houseAddr.append(houseInputDto.getHouseExt().getFloorNum());
			houseAddr.append("层");
		}
		if(!Check.NuNStr(houseInputDto.getHouseExt().getHouseNum())
				&& !ZERO_STRING.equals(houseInputDto.getHouseExt().getHouseNum())){
			houseAddr.append(houseInputDto.getHouseExt().getHouseNum());
			houseAddr.append("号");
		}
		houseInputDto.getHouseBase().setHouseAddr(houseAddr.toString());
		houseInputDto.getHouseBase().setHouseSource(HouseSourceEnum.TROY.getCode());//设置房源来源

		try {
			long start3 = System.currentTimeMillis();

			//添加房源维护管家
			HouseGuardRelEntity houseGuardRelEntity = setHouseGuardRel(houseInputDto.getHousePhy(), customerBase, houseInputDto);
			houseInputDto.setHouseGuardRel(houseGuardRelEntity);
			List<HouseConfMsgEntity> gapPriceList = this.searchGapFlexList();
			houseInputDto.setGapPriceList(gapPriceList);
			String resultJosn=troyHouseMgtService.houseInput(JsonEntityTransform.Object2Json(houseInputDto));

			long end3 = System.currentTimeMillis();
			LogUtil.info(LOGGER, "saveHouseMsg-houseInput(保存房源数据，更新客户为房东)->time={}", end3 - start3);

			LogUtil.info(LOGGER, "saveHouseMsg->ALLtime={}", end3 - start1);

			LogUtil.info(LOGGER,"保存房源返回值："+resultJosn);
			resultDto = JsonEntityTransform.json2DataTransferObject(resultJosn);
			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				//获取房源fid
				String houseBaseFid=SOAResParseUtil.getStrFromDataByKey(resultJosn, "houseBaseFid");
				CustomerBaseMsgEntity customerBaseMsgEntity=new CustomerBaseMsgEntity();
				customerBaseMsgEntity.setUid(houseInputDto.getHouseBase().getLandlordUid());
				customerBaseMsgEntity.setIsLandlord(1);
				//设置客户为房东
				customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBaseMsgEntity));

				//判断是否插入房源商机表记录
				long start4=System.currentTimeMillis();
				/*houseBusinessInput(houseInputDto, houseBaseFid);*/ //modified by liujun 2017-02-21
				long end4=System.currentTimeMillis();
				LogUtil.info(LOGGER, "houseBusinessInput->ALLtime={}", end4 - start4);

				/*	if(!Check.NuNObj(houseGuardRelEntity) && !Check.NuNStr(customerBase.getRealName()) 
						&& !Check.NuNStr(houseInputDto.getHouseBase().getHouseName())){
					HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
					if (Check.NuNObj(houseBaseMsg)) {
						resultDto.setErrCode(DataTransferObject.ERROR);
						resultDto.setMsg("房源基本信息不存在");
						return resultDto;
					}
					if(!Check.NuNStr(houseGuardRelEntity.getEmpPushCode())){
						EmployeeEntity empPush = this.findEmployeeEntity(houseGuardRelEntity.getEmpPushCode());
						if(!Check.NuNObj(empPush) && !Check.NuNStr(empPush.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", customerBase.getRealName());
							paramsMap.put("{2}", houseInputDto.getHouseBase().getHouseName());
							paramsMap.put("{3}", houseBaseMsg.getHouseSn());
							this.sendSms(empPush.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_RELEASE.getCode());
						}
					}

					if(!Check.NuNStr(houseGuardRelEntity.getEmpGuardCode()) 
							&& !houseGuardRelEntity.getEmpGuardCode().equals(houseGuardRelEntity.getEmpPushCode())){
						EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRelEntity.getEmpGuardCode());
						if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", customerBase.getRealName());
							paramsMap.put("{2}", houseInputDto.getHouseBase().getHouseName());
							paramsMap.put("{3}", houseBaseMsg.getHouseSn());
							this.sendSms(empGuard.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_RELEASE.getCode());
						}
					}
				}*/

			}
			return resultDto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			resultDto.setErrCode(DataTransferObject.ERROR);
			resultDto.setMsg(e.getMessage());
			return resultDto;
		}
	}

	/**
	 * 
	 *  获取房源运营专员
	 *
	 * @author yd
	 * @created 2016年7月9日 下午4:34:37
	 *
	 * @param housePhyMsg
	 * @param houseInputDto 
	 * @param customerBase
	 * @return
	 * @throws SOAParseException  
	 */
	private HouseGuardRelEntity setHouseGuardRel(HousePhyMsgEntity housePhyMsg, CustomerBaseMsgEntity customerBase,
			HouseInputDto houseInputDto) throws SOAParseException {
		HouseGuardRelEntity houseGuardRelEntity  = null;
		if (Check.NuNObj(housePhyMsg) || Check.NuNObj(customerBase)) {
			return houseGuardRelEntity;
		}
    	
		// 地推管家岗位已取消 modified by liujun 2017-02-24
    	/*//查询是否存在地推管家,存在分配该管家给该房源作为维护管家
    	if(!Check.NuNStr(customerBase.getCustomerMobile())){
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
						houseInputDto.getHouseBase().setHouseChannel(HouseChannelEnum.CH_DITUI.getCode());
						houseGuardRelEntity = new HouseGuardRelEntity();
						houseGuardRelEntity.setFid(UUIDGenerator.hexUUID());
						houseGuardRelEntity.setCreateFid(customerBase.getUid());
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
    	
    	// 区域管家关系表中随机分配运营专员
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
    				houseGuardRelEntity.setCreateFid(customerBase.getUid());
    				houseGuardRelEntity.setCreateDate(new Date());
    				houseGuardRelEntity.setLastModifyDate(new Date());
    				houseGuardRelEntity.setIsDel(0);
    			}
    		}
		}

		return houseGuardRelEntity;
	}

	/**
	 * 
	 * 插入摄影师信息
	 *
	 * @author bushujie
	 * @created 2016年5月11日 上午11:31:50
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("insertCameramanMsg")
	@ResponseBody
	public DataTransferObject insertCameramanMsg(HttpServletRequest request){
		String houseBaseFid=request.getParameter("houseBaseFid");
		String cameramanName=request.getParameter("cameramanName");
		String cameramanMobile=request.getParameter("cameramanMobile");
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(cameramanName)){
			dto.setErrCode(1);
			dto.setMsg("摄影师姓名为空");
			return dto;
		}
		if(cameramanName.length()>20){
			dto.setErrCode(1);
			dto.setMsg("摄影师姓名过长");
			return dto;
		}
		if(Check.NuNStr(cameramanMobile)){
			dto.setErrCode(1);
			dto.setMsg("摄影师手机号码为空");
			return dto;
		}
		if(!RegExpUtil.isMobilePhoneNum(cameramanMobile)){
			dto.setErrCode(1);
			dto.setMsg("手机号码不正确");
			return dto;
		}

		/*HouseBaseMsgEntity houseBaseMsgEntity=new HouseBaseMsgEntity();
		houseBaseMsgEntity.setFid(houseBaseFid);
		houseBaseMsgEntity.setCameramanName(cameramanName);
		houseBaseMsgEntity.setCameramanMobile(cameramanMobile);
		String resultJson=troyHouseMgtService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsgEntity));*/


		HouseBaseExtDto houseBaseExt = new HouseBaseExtDto();
		houseBaseExt.setFid(houseBaseFid);
		houseBaseExt.setCameramanName(cameramanName);
		houseBaseExt.setCameramanMobile(cameramanMobile);

		HouseBaseExtEntity houseBaseExtEntity = new HouseBaseExtEntity();
		houseBaseExtEntity.setHouseBaseFid(houseBaseFid);
		houseBaseExtEntity.setIsLandlordPic(1);
		houseBaseExt.setHouseBaseExt(houseBaseExtEntity);
		String resultJson = troyHouseMgtService.updateHouseBaseAndExt(JsonEntityTransform.Object2Json(houseBaseExt));

		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * 
	 * 设置默认图片
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午8:43:02
	 *
	 * @param picDto
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("defaultPic")
	@ResponseBody
	public DataTransferObject setDefaultPic(HousePicDto picDto) throws SOAParseException{
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(picDto.getHousePicFid())){
			dto.setErrCode(1);
			dto.setMsg("图片Id为空");
			return dto;
		}
		//查询默认图片旧值   去掉审核逻辑
		HouseUpdateHistoryLogDto houseUpdateHistoryLogDto=new HouseUpdateHistoryLogDto();
		houseUpdateHistoryLogDto.setSourceType(HouseSourceEnum.TROY.getCode());
		houseUpdateHistoryLogDto.setCreateType(CreaterTypeEnum.GUARD.getCode());
		houseUpdateHistoryLogDto.setCreaterFid(UserUtil.getFullCurrentUser().getEmpCode());
		houseUpdateHistoryLogDto.setHouseFid(picDto.getHouseBaseFid());
		String houseBaseExtJson=houseIssueService.searchHouseBaseAndExtByFid(picDto.getHouseBaseFid());
		HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(houseBaseExtJson, "obj", HouseBaseExtDto.class);
		houseUpdateHistoryLogDto.setRentWay(houseBaseExtDto.getRentWay());
		if (houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()) {
			houseUpdateHistoryLogDto.setOldHouseBaseExt(houseBaseExtDto.getHouseBaseExt());
		} else if(houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
			houseUpdateHistoryLogDto.setRoomFid(picDto.getHouseRoomFid());
			String roomJson=houseIssueService.searchHouseRoomMsgByFid(picDto.getHouseRoomFid());
			HouseRoomMsgEntity roomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
			houseUpdateHistoryLogDto.setOldHouseRoomMsg(roomMsgEntity);
		}
		picDto.setPicSource(1);
		//设置默认图片
		String resultJson = houseIssueService.updateHouseDefaultPic(JsonEntityTransform.Object2Json(picDto));
		DataTransferObject resuleDto=JsonEntityTransform.json2DataTransferObject(resultJson);
		//去掉审核逻辑
		if(resuleDto.getCode()==DataTransferObject.SUCCESS){
			if(houseBaseExtDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				HouseBaseExtEntity newHouseBaseExtEntity=new HouseBaseExtEntity();
				newHouseBaseExtEntity.setDefaultPicFid(picDto.getHousePicFid());
				houseUpdateHistoryLogDto.setHouseBaseExt(newHouseBaseExtEntity);
			} else if(houseBaseExtDto.getRentWay()==RentWayEnum.ROOM.getCode()) {
				HouseRoomMsgEntity newHouseRoomMsgEntity =new HouseRoomMsgEntity();
				newHouseRoomMsgEntity.setDefaultPicFid(picDto.getHousePicFid());
				houseUpdateHistoryLogDto.setHouseRoomMsg(newHouseRoomMsgEntity);
			}

			if (!Check.NuNObj(picDto.getOperateSource())) {
				houseUpdateHistoryLogDto.setOperateSource(picDto.getOperateSource());
			}

			houseUpdateHistoryLogService.saveHistoryLog(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));
			System.out.println(JsonEntityTransform.Object2Json(houseUpdateHistoryLogDto));

		}
		return resuleDto;
	}

	/**
	 * 
	 * 房源管理-查询房源审核列表页
	 *
	 * @author liujun
	 * @created 2016年6月8日
	 *
	 * @param requestDto
	 * @return
	 */
	@RequestMapping("showHouseOperateLogList")
	@ResponseBody
	public PageResult showHouseOperateLogList(HouseOpLogDto requestDto) {
		List<Integer> fromList = new ArrayList<Integer>();
		fromList.add(HouseStatusEnum.YFB.getCode());
		fromList.add(HouseStatusEnum.XXSHWTG.getCode());
		fromList.add(HouseStatusEnum.XXSHTG.getCode());
		fromList.add(HouseStatusEnum.ZPSHWTG.getCode());
		fromList.add(HouseStatusEnum.SJ.getCode());
		fromList.add(HouseStatusEnum.XJ.getCode());
		fromList.add(HouseStatusEnum.QZXJ.getCode());
		requestDto.setFromList(fromList);

		try {
			String resultJson = troyHouseMgtService.searchHouseOperateLogList(
					JsonEntityTransform.Object2Json(requestDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "#searchHouseOperateLogList接口调用失败,参数:{}",
						JsonEntityTransform.Object2Json(requestDto));
				return new PageResult();
			}

			List<HouseOperateLogVo> operateLogList = dto.parseData("list",
					new TypeReference<List<HouseOperateLogVo>>() {});
			for (HouseOperateLogVo houseOperateLogVo : operateLogList) {
				if(0 == houseOperateLogVo.getOperateType()){//非业务员操作
					CustomerBaseMsgEntity creater = findCustomerBaseMsgEntity(houseOperateLogVo.getCreateFid());
					if(!Check.NuNObj(creater)){
						houseOperateLogVo.setCreateName(creater.getRealName());
					}
				}else{//业务员操作
					String userJson = permissionOperateService.searchCurrentuserByUid(houseOperateLogVo.getCreateFid());
					DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(userJson);
					if(dto1.getCode() == DataTransferObject.ERROR){
						LogUtil.info(LOGGER, "customerInfoService.getCustomerInfoByUid错误,uid={},结果:{}",
								houseOperateLogVo.getCreateFid(), resultJson);
					} else {
						CurrentuserEntity user = SOAResParseUtil
								.getValueFromDataByKey(userJson, "user", CurrentuserEntity.class);
						if(!Check.NuNObj(user)){
							EmployeeEntity employeeEntity = findEmployeeEntityByFid(user.getEmployeeFid());
							if(!Check.NuNObj(employeeEntity)){
								houseOperateLogVo.setCreateName(employeeEntity.getEmpName());
							}
						}
					}
				}
				houseOperateLogVo.setAuditCause(HouseAuditCauseEnum.getNameStr(houseOperateLogVo.getAuditCause()));
			}
			PageResult pageResult = new PageResult();
			pageResult.setRows(operateLogList);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}

	/**
	 * 
	 * 房源管理-跳转房源照片上传页面
	 *
	 * @author bushujie
	 * @created 2016年4月12日 上午11:39:51
	 *
	 * @param request
	 */
	@RequestMapping("listHouseMsgUpPic")
	public void listHouseMsgUpPic(HttpServletRequest request) {
		cascadeDistricts(request);

		// 房源状态类型
		Map<Integer, String> houseStatusMap = new LinkedHashMap<Integer, String>();
		houseStatusMap.put(HouseStatusEnum.ZPSHWTG.getCode(), HouseStatusEnum.ZPSHWTG.getName());
		houseStatusMap.put(HouseStatusEnum.XJ.getCode(), HouseStatusEnum.XJ.getName());
		houseStatusMap.put(HouseStatusEnum.SJ.getCode(), HouseStatusEnum.SJ.getName());
		houseStatusMap.put(HouseStatusEnum.YFB.getCode(), HouseStatusEnum.YFB.getName());
		request.setAttribute("houseStatusMap", houseStatusMap);
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
		request.setAttribute("causeMap", HouseAuditCauseEnum.getValidEnumMap());
		request.setAttribute("houseChannel", HouseChannelEnum.getEnumMapAll());
	}

	/**
	 * 
	 * 房源管理-查询房源照片上传列表
	 *
	 * @author bushujie
	 * @created 2016年4月11日 下午9:43:25
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseMsgUpPic")
	@ResponseBody
	public PageResult showHouseMsgUpPic(HouseRequestDto houseRequest,HttpServletRequest request) {
		if(Check.NuNObj(houseRequest.getHouseStatus())){
			List<Integer> houseStatusList = new ArrayList<Integer>();
			houseStatusList.add(HouseStatusEnum.ZPSHWTG.getCode());
			houseStatusList.add(HouseStatusEnum.XJ.getCode());
			houseStatusList.add(HouseStatusEnum.SJ.getCode());
			houseStatusList.add(HouseStatusEnum.YFB.getCode());
			houseRequest.setHouseStatusList(houseStatusList);
		}
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonHouseMsg(houseRequest);
	}

	/**
	 * 
	 * 房源管理-查询房源信息修改列表
	 *
	 * @author bushujie
	 * @created 2016年4月11日 下午9:43:25
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseMsgList")
	@ResponseBody
	public PageResult showHouseMsgList(HouseRequestDto houseRequest,HttpServletRequest request) {
		if(Check.NuNObj(houseRequest.getHouseStatus())){
			List<Integer> houseStatusList = new ArrayList<Integer>();

			//			houseStatusList.add(HouseStatusEnum.XXSHTG.getCode());

			houseStatusList.add(HouseStatusEnum.ZPSHWTG.getCode());
			houseStatusList.add(HouseStatusEnum.XJ.getCode());
			houseStatusList.add(HouseStatusEnum.SJ.getCode());
			houseStatusList.add(HouseStatusEnum.YFB.getCode());
			houseRequest.setHouseStatusList(houseStatusList);
		}
		//特殊权限参数赋值
		Integer roleType=(Integer) request.getAttribute("roleType");
		if(!Check.NuNObj(roleType)){
			houseRequest.setRoleType(roleType);
		}
		houseRequest.setEmpCode((String) request.getAttribute("empCode"));
		houseRequest.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
		return showCommonUpdateHouseMsg(houseRequest);
	}
	
	/**
	 * 
	 * 查询房源修改列表信息
	 *
	 * @author yd
	 * @created 2017年11月2日 下午5:21:05
	 *
	 * @param houseRequest
	 * @return
	 */
	private PageResult showCommonUpdateHouseMsg(HouseRequestDto houseRequest) {
		try {
			//判断是否合租
			if (houseRequest.getRentWay() == RentWayEnum.ROOM.getCode()) {
				exchangeCondition(houseRequest);
			}

			Map<String,CustomerBaseMsgEntity> landlordUidMap = new HashMap<String,CustomerBaseMsgEntity>();
			// 房东姓名或房东手机不为空,调用用户库查询房东uid
			if(!Check.NuNStr(houseRequest.getLandlordName()) || !Check.NuNStr(houseRequest.getLandlordMobile())){
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(houseRequest.getLandlordName());
				paramDto.setCustomerMobile(houseRequest.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);

				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断调用状态
				if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<CustomerBaseMsgEntity> customerList = customerDto.parseData("listCustomerBaseMsg",
						new TypeReference<List<CustomerBaseMsgEntity>>() {});
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(customerList)){
					LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<String> landlordUidList = new ArrayList<String>();
				for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
					landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
					landlordUidList.add(customerBaseMsg.getUid());
				}
				houseRequest.setLandlordUidList(landlordUidList);
			}
			String resultJson = troyHouseMgtService.searchUpateHouseMsgList(JsonEntityTransform.Object2Json(houseRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(houseRequest));
				return new PageResult();
			}

			List<HouseResultVo> houseMsgList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseResultVo.class);
			for (HouseResultVo houseResultVo : houseMsgList) {
				CustomerBaseMsgEntity customer = null;
				if(Check.NuNMap(landlordUidMap)){
					String customerJson = customerInfoService.getCustomerInfoByUid(houseResultVo.getLandlordUid());
					DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerJson);
					if (dto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "调用接口失败,landlordUid={}", houseResultVo.getLandlordUid());
					} else {
						customer = SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
					}
				} else {
					customer = landlordUidMap.get(houseResultVo.getLandlordUid());
				}

				if(!Check.NuNObj(customer)){
					houseResultVo.setLandlordName(customer.getRealName());
					houseResultVo.setLandlordMobile(customer.getCustomerMobile());
				}

				//填充房间类型roomType
				if(!Check.NuNObj(houseResultVo.getRoomType())){
						houseResultVo.setRoomTypeStr(RoomTypeEnum.getEnumByCode(houseResultVo.getRoomType()).getName());
				}
				
				houseResultVo.setAuditCause(HouseAuditCauseEnum.getNameStr(houseResultVo.getAuditCause()));
			}

			PageResult pageResult = new PageResult();
			pageResult.setRows(houseMsgList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		} 
	}

	/**
	 * 
	 * 房源管理--房源信息修改列表页
	 *
	 * @author bushujie
	 * @created 2016年4月12日 上午11:39:51
	 *
	 * @param request
	 */
	@RequestMapping("upHouseMsgList")
	public void upHouseMsgList(HttpServletRequest request) {
		cascadeDistricts(request);

		// 房源状态类型
		Map<Integer, String> houseStatusMap = new LinkedHashMap<Integer, String>();

		//屏蔽管家审核通过
		//		houseStatusMap.put(HouseStatusEnum.XXSHTG.getCode(), HouseStatusEnum.XXSHTG.getName());

		houseStatusMap.put(HouseStatusEnum.ZPSHWTG.getCode(), HouseStatusEnum.ZPSHWTG.getName());
		houseStatusMap.put(HouseStatusEnum.XJ.getCode(), HouseStatusEnum.XJ.getName());
		houseStatusMap.put(HouseStatusEnum.SJ.getCode(), HouseStatusEnum.SJ.getName());
		houseStatusMap.put(HouseStatusEnum.YFB.getCode(), HouseStatusEnum.YFB.getName());
		request.setAttribute("houseStatusMap", houseStatusMap);
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
	}

	/**
	 * 
	 * 房源管理-房源信息修改详情
	 *
	 * @author liujun
	 * @created 2016年4月11日 下午9:45:50
	 *
	 * @param request
	 * @param houseFid
	 * @param rentWay
	 * @throws SOAParseException 
	 */
	@RequestMapping("upHouseDetail")
	public void upHouseDetail(HttpServletRequest request, String houseFid, Integer rentWay) throws SOAParseException {
		request.setAttribute("isDisplay", true);
		double start = System.currentTimeMillis() ; 
		getHouseDetail(request, houseFid, rentWay);
		double end = System.currentTimeMillis() ; 
		LogUtil.info(LOGGER, "upHouseDetail——getHouseDetail执行时间：{}", end-start);

	}

	/**
	 * 
	 * 更新房源可修改信息
	 *
	 * @author bushujie
	 * @created 2016年6月22日 下午2:21:50
	 *
	 * @param houseInputDto
	 * @return
	 */
	@RequestMapping("upHouseMsg")
	@ResponseBody
	public DataTransferObject upHouseMsg(HouseInputDto houseInputDto){

		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(houseInputDto)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数对象为空");
			return dto;
		} 
        houseInputDto.getHouseExt().setCheckOutRulesCode(null);
        if(!Check.NuNObj(UserUtil.getFullCurrentUser())){
            houseInputDto.setEmpCode(UserUtil.getFullCurrentUser().getEmpCode());
        }
		String resultJson =troyHouseMgtService.upHouseMsg(JsonEntityTransform.Object2Json(houseInputDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * 更新房源渠道
	 * @author jixd
	 * @created 2016年10月26日 下午2:21:50
	 * @param houseBaseMsgEntity
	 * @return
	 */
	@RequestMapping("upHouseChannel")
	@ResponseBody
	public DataTransferObject upHouseChannel(HouseBaseMsgEntity houseBaseMsgEntity){
		String resultJson = troyHouseMgtService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsgEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * 更新房源品质
	 * @author jixd
	 * @created 2016年10月31日 下午2:21:50
	 * @param houseFid
	 * @param qualityGrade
	 * @return
	 */
	@RequestMapping("upHouseQualityGrade")
	@ResponseBody
	public DataTransferObject upHouseQualityGrade(String houseFid,String qualityGrade){
		HouseBaseExtDto extDto = new HouseBaseExtDto();
		extDto.setFid(houseFid);
		HouseBaseExtEntity baseExtEntity = new HouseBaseExtEntity();
		baseExtEntity.setHouseBaseFid(houseFid);
		baseExtEntity.setHouseQualityGrade(qualityGrade);
		extDto.setHouseBaseExt(baseExtEntity);
		String resultJson = troyHouseMgtService.updateHouseBaseAndExt(JsonEntityTransform.Object2Json(extDto));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * 
	 * 删除未审核图片
	 *
	 * @author bushujie
	 * @created 2016年6月24日 上午10:25:13
	 *
	 * @param fid
	 * @param serverUUID
	 * @return
	 */
	@RequestMapping("delNoAuditHousePic")
	@ResponseBody
	public DataTransferObject delNoAuditHousePic(String fid,String serverUUID){
		//		try{
		//			storageService.delete(serverUUID);
		//		}catch(Exception e){
		//			LogUtil.info(LOGGER, "删除服务器图片服务异常！");
		//		}
		String resultJson=troyHouseMgtService.delNoAuditHousePic(fid);
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}

	/**
	 * @description: 上架房源信息修改审核完成
	 * @author: lusp
	 * @date: 2017/8/16 10:34
	 * @params: request
	 * @return:
	 */
	@RequestMapping("auditNoHousePic")
	@ResponseBody
	public DataTransferObject auditNoHousePic(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			String[] fids=request.getParameterValues("picFids");
			String rentWayStr = request.getParameter("rentWay");
			String mobile = request.getParameter("landlordMobile");
			String landlordUid = request.getParameter("landlordUid");
			String houseBaseFid = request.getParameter("houseBaseFid");
			String roomFid = request.getParameter("houseOrRoomFid");
			String remarkMsg = request.getParameter("remarkMsg");
			if(Check.NuNStrStrict(rentWayStr)||Check.NuNStrStrict(mobile)||Check.NuNStrStrict(houseBaseFid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			Integer rentWay = Integer.valueOf(rentWayStr);
			if(Check.NuNObj(rentWay)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			
			//1. 审核照片  2. 将房源待审核字段新值写进相应的表中，同时更改审核字段状态为审核通过
			List<String> picFids= new ArrayList<String>();
			if(fids!=null){
				for(String fid:fids){
					picFids.add(fid);
				}
			}
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
			houseUpdateFieldAuditNewlogEntity.setHouseFid(houseBaseFid);
			houseUpdateFieldAuditNewlogEntity.setRentWay(rentWay);
			if(rentWay == RentWayEnum.ROOM.getCode()){
				houseUpdateFieldAuditNewlogEntity.setRoomFid(roomFid);
			}
			HouseUpdateFieldAuditDto houseUpdateFieldAuditDto = new HouseUpdateFieldAuditDto();
			houseUpdateFieldAuditDto.setHouseUpdateFieldAuditNewlog(houseUpdateFieldAuditNewlogEntity);
			houseUpdateFieldAuditDto.setPicFids(picFids);
			String resultJson = troyHouseMgtService.approveGroundingHouseInfo(JsonEntityTransform.Object2Json(houseUpdateFieldAuditDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.ERROR){
				return dto;
			}


			//发送短信告知房东照片已审核通过
			String houseName = "";
			String houseSn = "";
			if (rentWay == RentWayEnum.HOUSE.getCode()){
//				houseName = request.getParameter("houseName");
				houseSn = request.getParameter("houseSn");
				String houseBaseJson = troyHouseMgtService.findHouseBaseByHouseSn(houseSn);
				DataTransferObject houseBaseDto = JsonEntityTransform.json2DataTransferObject(houseBaseJson);
				if (houseBaseDto.getCode() == DataTransferObject.ERROR){
					return houseBaseDto;
				}
				HouseBaseMsgEntity houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseBaseJson,"houseBase",HouseBaseMsgEntity.class);
				if (!Check.NuNObj(houseBaseMsgEntity)){
					houseName = houseBaseMsgEntity.getHouseName();
				}
			}
			if (rentWay == RentWayEnum.ROOM.getCode()) {
				HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(roomFid);
				if (!Check.NuNObj(houseRoomMsg)) {
					houseName = houseRoomMsg.getRoomName();
					houseSn = houseRoomMsg.getRoomSn();
				}
			}
			//获取短信文案
			String auditPassFieldStrs  = HouseBaseUtils.fillHouseAuditSms(resultJson,  picFids.size());
			//当驳回原因为空时，表示全部审核通过，发送审核通过短信
			if (Check.NuNStrStrict(remarkMsg)&&!Check.NuNStrStrict(auditPassFieldStrs)&&!Check.NuNStr(houseName) ) {
				Map<String,String> paramMap = new HashMap<>();
				paramMap.put("{1}",houseName);
				paramMap.put("{2}",auditPassFieldStrs);
				sendSms(mobile, paramMap, MessageTemplateCodeEnum.HOUSE_SJ_MODIFY_AUDIT_PASS.getCode());

				//发送极光推送
				JpushRequest jpushRequest = new JpushRequest();
				jpushRequest.setParamsMap(paramMap);
				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_SJ_MODIFY_AUDIT_PASS.getCode()));
				jpushRequest.setTitle("房源信息审核通知");
				jpushRequest.setUid(landlordUid);
				//自定义消息
				Map<String, String> extrasMap = new HashMap<>();
				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_9);
				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
				extrasMap.put("houseBaseFid",houseBaseFid);
				extrasMap.put("rentWay",rentWay.toString());
				if(rentWay == RentWayEnum.ROOM.getCode()){
					extrasMap.put("roomFid",roomFid);
				}
				jpushRequest.setExtrasMap(extrasMap);
				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
			}
			//当驳回原因不为空时并且auditPassFieldStrs为空，表示全部未审核通过，发送全部未审核通过通知
			else if (!Check.NuNStrStrict(remarkMsg)&&Check.NuNStrStrict(auditPassFieldStrs)&&!Check.NuNStr(houseName)) {
				Map<String,String> paramMap = new HashMap<>();
				paramMap.put("{1}",houseName);
				paramMap.put("{2}",remarkMsg);
				sendSms(mobile, paramMap, MessageTemplateCodeEnum.HOUSE_SJ_MODIFY_AUDIT_REJECT.getCode());

				//发送极光推送
				JpushRequest jpushRequest = new JpushRequest();
				jpushRequest.setParamsMap(paramMap);
				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_SJ_MODIFY_AUDIT_REJECT.getCode()));
				jpushRequest.setTitle("房源信息审核通知");
				jpushRequest.setUid(landlordUid);
				//自定义消息
				Map<String, String> extrasMap = new HashMap<>();
				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_9);
				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
				extrasMap.put("houseBaseFid",houseBaseFid);
				extrasMap.put("rentWay",rentWay.toString());
				if(rentWay == RentWayEnum.ROOM.getCode()){
					extrasMap.put("roomFid",roomFid);
				}
				jpushRequest.setExtrasMap(extrasMap);
				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
			}
			//当驳回原因不为空时并且auditPassFieldStrs不为空，表示部分审核通过以及部分未通过，发送部分审核通过部分未通过通知
			else if (!Check.NuNStrStrict(remarkMsg)&&!Check.NuNStrStrict(auditPassFieldStrs)&&!Check.NuNStr(houseName)) {
				Map<String,String> paramMap = new HashMap<>();
				paramMap.put("{1}",houseName);
				paramMap.put("{2}",auditPassFieldStrs);
				paramMap.put("{3}",remarkMsg);
				sendSms(mobile, paramMap, MessageTemplateCodeEnum.HOUSE_SJ_MODIFY_AUDIT_BF_REJECT.getCode());

				//发送极光推送
				JpushRequest jpushRequest = new JpushRequest();
				jpushRequest.setParamsMap(paramMap);
				jpushRequest.setJpushPersonType(JpushPersonType.ONE.getCode());
				jpushRequest.setMessageType(MessageTypeEnum.MESSAGE.getCode());
				jpushRequest.setSmsCode(String.valueOf(MessageTemplateCodeEnum.HOUSE_SJ_MODIFY_AUDIT_BF_REJECT.getCode()));
				jpushRequest.setTitle("房源信息审核通知");
				jpushRequest.setUid(landlordUid);
				//自定义消息
				Map<String, String> extrasMap = new HashMap<>();
				extrasMap.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
				extrasMap.put(JpushConst.MSG_SUB_TYPE_KEY, JpushConst.MSG_SUB_TYPE_VALULE_9);
				extrasMap.put(JpushConst.MSG_HAS_RESPONSE,"1");
				extrasMap.put(JpushConst.MSG_TAG_TYPE,JpushConst.MSG_TARGET_LAN);
				extrasMap.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
				extrasMap.put("houseBaseFid",houseBaseFid);
				extrasMap.put("rentWay",rentWay.toString());
				if(rentWay == RentWayEnum.ROOM.getCode()){
					extrasMap.put("roomFid",roomFid);
				}
				jpushRequest.setExtrasMap(extrasMap);
				smsTemplateService.jpushByCode(JsonEntityTransform.Object2Json(jpushRequest));
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "auditNoHousePic error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 插入房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月11日 下午8:46:23
	 *
	 * @param houseInputDto
	 * @param houseBaseFid
	 * @throws SOAParseException 
	 */
	@Deprecated // modified by liujun 2017年2月21日
	private void houseBusinessInput(HouseInputDto houseInputDto,String houseBaseFid) throws SOAParseException{
		String custJson=customerInfoService.getCustomerInfoByUid(houseInputDto.getHouseBase().getLandlordUid());
		CustomerBaseMsgEntity customer=SOAResParseUtil.getValueFromDataByKey(custJson, "customerBase", CustomerBaseMsgEntity.class);
		if(!Check.NuNObj(customer)&&!Check.NuNStr(customer.getCustomerMobile())){
			HouseBusinessMsgExtDto dto=new HouseBusinessMsgExtDto();
			dto.setLandlordMobile(customer.getCustomerMobile());
			String businessJson=houseBusinessService.findHouseBusExtByCondition(JsonEntityTransform.Object2Json(dto));
			List<HouseBusinessMsgExtEntity> listExtEntities=SOAResParseUtil.getListValueFromDataByKey(businessJson, "listExtEntities", HouseBusinessMsgExtEntity.class);
			if(!Check.NuNCollection(listExtEntities)){
				HouseBusinessInputDto inputDto=new HouseBusinessInputDto();
				//商机基本信息
				inputDto.getBusinessMsg().setNationCode(houseInputDto.getHousePhy().getNationCode());
				inputDto.getBusinessMsg().setProvinceCode(houseInputDto.getHousePhy().getProvinceCode());
				inputDto.getBusinessMsg().setCityCode(houseInputDto.getHousePhy().getCityCode());
				inputDto.getBusinessMsg().setAreaCode(houseInputDto.getHousePhy().getAreaCode());
				inputDto.getBusinessMsg().setCommunityName(houseInputDto.getHousePhy().getCommunityName());
				inputDto.getBusinessMsg().setHouseAddr(houseInputDto.getHouseBase().getHouseAddr());
				inputDto.getBusinessMsg().setHouseBaseFid(houseBaseFid);
				inputDto.getBusinessMsg().setRentWay(houseInputDto.getHouseBase().getRentWay());
				inputDto.getBusinessMsg().setCreateFid(UserUtil.getCurrentUserFid());
				//商机来源信息
				inputDto.getBusinessSource().setBusniessSource(1);
				inputDto.getBusinessSource().setIsJobArea(1);
				inputDto.getBusinessSource().setCreateFid(UserUtil.getCurrentUserFid());
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
		}
	}

	/**
	 * @description: 驳回待审核的房源信息（上架房源单个字段审核驳回）
	 * @author: lusp
	 * @date: 2017/8/3 14:29
	 * @params: request
	 * @return:
	 */
	@RequestMapping("rejectHouseField")
	@ResponseBody
	public DataTransferObject rejectHouseField(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			String id = request.getParameter("id");
			if(Check.NuNStrStrict(id)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			HouseUpdateFieldAuditNewlogEntity houseUpdateFieldAuditNewlogEntity = new HouseUpdateFieldAuditNewlogEntity();
			houseUpdateFieldAuditNewlogEntity.setId(Integer.valueOf(id));
			houseUpdateFieldAuditNewlogEntity.setFieldAuditStatu(2);
			String rejectNumJson = troyHouseMgtService.updateHouseUpdateFieldAuditNewlogById(JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity));
			dto = JsonEntityTransform.json2DataTransferObject(rejectNumJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"rejectHouseField(),更新审核字段为驳回状态失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(houseUpdateFieldAuditNewlogEntity),dto.getMsg());
				dto.setMsg("系统异常");
				return dto;
			}
		}catch (Exception e) {
			LogUtil.error(LOGGER, "rejectHouseField error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto;
	}

	/**
	 * @description: 驳回图片审核，将图片的审核状态改为审核拒绝
	 * @author: lusp
	 * @date: 2017/8/4 14:14
	 * @params: request
	 * @return:
	 */
	@RequestMapping("rejectHousePic")
	@ResponseBody
	public DataTransferObject rejectHousePic(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		try {
			String fid = request.getParameter("fid");
			if(Check.NuNStrStrict(fid)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			HousePicMsgEntity housePicMsgEntity=new HousePicMsgEntity();
			housePicMsgEntity.setFid(fid);
			housePicMsgEntity.setAuditStatus(2);
			String resultJson=troyHouseMgtService.updateHousePicMsg(JsonEntityTransform.Object2Json(housePicMsgEntity));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"rejectHousePic(),更新照片审核字段为驳回状态失败, params:{},errorMsg:{}",JsonEntityTransform.Object2Json(housePicMsgEntity),dto.getMsg());
				dto.setMsg("系统异常");
				return dto;
			}
		}catch (Exception e) {
			LogUtil.error(LOGGER, "rejectHouseField error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常");
		}
		return dto;
	}


}
