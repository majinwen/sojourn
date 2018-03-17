package com.ziroom.minsu.mapp.house.controller;

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
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.entity.house.HouseOperateLogEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HousePicMsgEntity;
import com.ziroom.minsu.entity.house.HousePriceWeekConfEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.mapp.common.abs.AbstractController;
import com.ziroom.minsu.mapp.common.constant.MappMessageConst;
import com.ziroom.minsu.mapp.common.header.Header;
import com.ziroom.minsu.mapp.common.logic.ParamCheckLogic;
import com.ziroom.minsu.mapp.common.util.UserUtil;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.utils.CheckIdCardUtils;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.HouseBaseDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.dto.HouseBaseParamsDto;
import com.ziroom.minsu.services.house.dto.HouseDescDto;
import com.ziroom.minsu.services.house.dto.HouseOpLogSpDto;
import com.ziroom.minsu.services.house.dto.HousePriceWeekConfDto;
import com.ziroom.minsu.services.house.dto.SpecialPriceDto;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;
import com.ziroom.minsu.services.house.entity.HouseBaseVo;
import com.ziroom.minsu.valenum.common.WeekendPriceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HousePicTypeEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum008Enum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum020;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * <p>房源发布控制</p>
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
@RequestMapping("houseIssue")
@Controller
public class HouseReleaseController  extends AbstractController{

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseReleaseController.class);

	private static String ZERO_STRING = "0";

	@Resource(name="house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name="house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name="house.houseGuardService")
	private HouseGuardService houseGuardService;

	@Resource(name="basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Resource(name="basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;

	@Resource(name="basedata.smsTemplateService")
	private SmsTemplateService smsTemplateService;

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Autowired
	private RedisOperations redisOperations;

	@Resource(name="mapp.messageSource")
	private MessageSource messageSource;

	@Resource(name="mapp.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

	/** 存放灵活定价的配置*/
	private static List<String> gapFlexlist = new ArrayList<String>();
	static {
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020001.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020002.getValue());
		gapFlexlist.add(ProductRulesEnum020.ProductRulesEnum020003.getValue());
	}

	/**
	 *
	 * 跳转补充信息页面
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param map
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/showOptionalInfo")
	public String showOptionalInfo(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String houseRoomFid = request.getParameter("houseRoomFid");
		String flag = request.getParameter("flag");
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("houseRoomFid", houseRoomFid);
		map.put("flag", flag);
		map.put("houseFive", request.getParameter("houseFive")==null?"1":request.getParameter("houseFive"));

		String resultJson = houseIssueService.searchHouseBaseMsgByFid(houseBaseFid);
		DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (houseDto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "houseIssueService.searchHouseBaseMsgByFid错误,houseBaseFid={},结果:{}", houseBaseFid, resultJson);
		} else {
			try {
				HouseBaseMsgEntity houseBaseMsg = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseBaseMsgEntity.class);
				if(!Check.NuNObj(houseBaseMsg)){
					request.setAttribute("houseBase", houseBaseMsg);
					request.setAttribute("status", houseBaseMsg.getHouseStatus());
				}
			} catch (Exception e) {
				LogUtil.info(LOGGER, "error:{}", e);
			}
		}

		if (String.valueOf(RentWayEnum.ROOM.getCode()).equals(rentWay) && !Check.NuNStrStrict(houseRoomFid)) {
			String roomJson = houseIssueService.searchHouseRoomMsgByFid(houseRoomFid);
			DataTransferObject roomDto = JsonEntityTransform.json2DataTransferObject(roomJson);
			if (roomDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.info(LOGGER, "houseIssueService.searchHouseRoomMsgByFid错误,houseRoomFid={},结果:{}", houseRoomFid, roomJson);
			} else {
				try {
					HouseRoomMsgEntity houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
					if(!Check.NuNObj(houseRoomMsg)){
						request.setAttribute("status", houseRoomMsg.getRoomStatus());
					}
				} catch (Exception e) {
					LogUtil.info(LOGGER, "error:{}", e);
				}
			}
		}

		// 房源或者房间默认图片查询
		String picJson = null;
		if (String.valueOf(RentWayEnum.HOUSE.getCode()).equals(rentWay)) {
			picJson = houseIssueService.findDefaultPic(houseBaseFid, RentWayEnum.HOUSE.getCode());
		} else if (String.valueOf(RentWayEnum.ROOM.getCode()).equals(rentWay)) {
			picJson = houseIssueService.findDefaultPic(houseRoomFid, RentWayEnum.ROOM.getCode());
		}
		String defaultPic = SOAResParseUtil.getStrFromDataByKey(picJson, "picBaseUrl");
		request.setAttribute("defaultPic", PicUtil.getSpecialPic(picBaseAddrMona, defaultPic, detail_big_pic));
		return "house/houseIssue/optionalInfo";
	}

	/**
	 *
	 * 跳转位置信息页面
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toLocationMsg")
	public String toLocationMsg(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String resultJson = houseIssueService.searchHouseDescAndBaseExt(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "houseIssueService.searchHouseDescAndBaseExt接口调用失败,houseBaseFid={}", houseBaseFid);
		}else{
			HouseDescDto location = dto.parseData("obj", new TypeReference<HouseDescDto>() {});
			if(Check.NuNObj(location) || Check.NuNStr(location.getHouseAroundDesc())){
				map.put("isComplete", 0);// 未完成
			}else{
				map.put("isComplete", 1);// 已完成
			}
			map.put("location", location);
		}
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("houseRoomFid", request.getParameter("houseRoomFid"));
		map.put("flag", request.getParameter("flag"));
		return "house/houseIssue/locationMsg";
	}

	/**
	 *
	 * 跳转房源周边情况页面
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toAroundDesc")
	public String toAroundDesc(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String resultJson = houseIssueService.searchHouseDescAndBaseExt(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "houseIssueService.searchHouseDescAndBaseExt接口调用失败,houseBaseFid={}", houseBaseFid);
		}else{
			HouseDescDto location = dto.parseData("obj", new TypeReference<HouseDescDto>() {});
			map.put("location", location);
		}
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("houseRoomFid", request.getParameter("houseRoomFid"));
		map.put("flag", request.getParameter("flag"));
		return "house/houseIssue/aroundDesc";
	}

	/**
	 *
	 * 保存位置信息
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateLocationMsg")
	@ResponseBody
	public DataTransferObject updateLocationMsg(HttpServletRequest request,HouseDescDto paramDto) {
		LogUtil.info(LOGGER, "参数:{}", JsonEntityTransform.Object2Json(paramDto));
		DataTransferObject dto = new DataTransferObject();
		String upType = request.getParameter("upType");
		try {
			if(Check.NuNStr(paramDto.getHouseBaseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto;
			}

			String housePhyJson = houseIssueService.searchHousePhyMsgByHouseBaseFid(paramDto.getHouseBaseFid());
			dto = JsonEntityTransform.json2DataTransferObject(housePhyJson);
			HousePhyMsgEntity housePhyMsg = null;
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseIssueService.searchHousePhyMsgByHouseBaseFid,结果:{}", housePhyJson);
			}else {
				housePhyMsg = dto.parseData("obj", new TypeReference<HousePhyMsgEntity>() {});
			}

			String baseExtJson = houseIssueService.searchHouseBaseAndExtByFid(paramDto.getHouseBaseFid());
			dto = JsonEntityTransform.json2DataTransferObject(baseExtJson);
			HouseBaseExtDto houseBaseExt = null;
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseIssueService.searchHouseBaseAndExtByFid调用失败,结果:{}", baseExtJson);
			} else {
				houseBaseExt = dto.parseData("obj", new TypeReference<HouseBaseExtDto>() {});
			}
			if(Check.NuNStr(upType)){
				paramDto.setBuildingNum(Check.NuNStr(paramDto.getBuildingNum())?"":paramDto.getBuildingNum());
				paramDto.setUnitNum(Check.NuNStr(paramDto.getUnitNum())?"":paramDto.getUnitNum());
				paramDto.setFloorNum(Check.NuNStr(paramDto.getFloorNum())?"":paramDto.getFloorNum());
				paramDto.setHouseNum(Check.NuNStr(paramDto.getHouseNum())?"":paramDto.getHouseNum());
			}

			// 更新房源地址
			HouseBaseMsgEntity houseBaseMsg = this.assembleHouseAddr(paramDto, housePhyMsg, houseBaseExt);
			if(!Check.NuNStr(houseBaseMsg.getHouseAddr())){
				String resultJson = houseIssueService.updateHouseBaseMsg(JsonEntityTransform.Object2Json(houseBaseMsg));
				dto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(dto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "houseIssueService.updateHouseBaseMsg调用失败,结果:{}", resultJson);
				}
			}

			String resultJson = houseIssueService.updateHouseDescAndBaseExt(JsonEntityTransform.Object2Json(paramDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseIssueService.updateHouseDescAndBaseExt调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
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
	 * 拼接房源地址
	 *
	 * @author liujun
	 * @created 2016年5月30日
	 *
	 * @param paramDto
	 * @param housePhyMsg
	 * @param houseBaseExt
	 * @return
	 */
	private HouseBaseMsgEntity assembleHouseAddr(HouseDescDto paramDto, HousePhyMsgEntity housePhyMsg,
												 HouseBaseExtDto houseBaseExt) {
		Integer operateSeq = null;
		HouseBaseMsgEntity houseBaseMsg = new HouseBaseMsgEntity();
		if(Check.NuNStr(paramDto.getHouseAroundDesc())){
			StringBuilder houseAddr = new StringBuilder();
			Map<String, String> map = null;

			if(!Check.NuNObj(housePhyMsg)){
				map = new HashMap<String, String>();
				map.put("cityCode", housePhyMsg.getCityCode());
				map.put("areaCode", housePhyMsg.getAreaCode());
				map.put("communityName", housePhyMsg.getCommunityName());
				String resultJson = confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(map.values()));
				DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if(cityDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "confCityService.getCityNameByCodeList调用失败,结果:{}", resultJson);
				}else{
					List<ConfCityEntity> cityList = cityDto.parseData("cityList", new TypeReference<List<ConfCityEntity>>(){});
					for (ConfCityEntity confCityEntity : cityList) {
						if(map.get("cityCode").equals(confCityEntity.getCode()) && !Check.NuNStr(confCityEntity.getShowName())){
							houseAddr.append(confCityEntity.getShowName());
						}
						if(map.get("areaCode").equals(confCityEntity.getCode()) && !Check.NuNStr(confCityEntity.getShowName())){
							houseAddr.append(confCityEntity.getShowName());
						}
					}
				}

			}

			if (!Check.NuNObj(houseBaseExt)) {
				if (!Check.NuNObj(houseBaseExt.getHouseBaseExt())
						&& !Check.NuNStr(houseBaseExt.getHouseBaseExt().getHouseStreet())) {
					houseAddr.append(houseBaseExt.getHouseBaseExt().getHouseStreet());
				}
				operateSeq = houseBaseExt.getOperateSeq();
			}

			if(!Check.NuNStr(map.get("communityName"))){
				houseAddr.append(map.get("communityName")+" ");
			}

			if(!Check.NuNObj(paramDto)){
				if(!Check.NuNStr(paramDto.getBuildingNum()) && !ZERO_STRING.equals(paramDto.getBuildingNum())){
					houseAddr.append(paramDto.getBuildingNum() + "号楼");
				}
				if(!Check.NuNStr(paramDto.getUnitNum()) && !ZERO_STRING.equals(paramDto.getUnitNum())){
					houseAddr.append(paramDto.getUnitNum() + "单元");
				}
				if(!Check.NuNStr(paramDto.getFloorNum()) && !ZERO_STRING.equals(paramDto.getFloorNum())){
					houseAddr.append(paramDto.getFloorNum() + "层");
				}
				if(!Check.NuNStr(paramDto.getHouseNum()) && !ZERO_STRING.equals(paramDto.getHouseNum())){
					houseAddr.append(paramDto.getHouseNum() + "号");
				}
			}

			houseBaseMsg.setHouseAddr(houseAddr.toString());
		}

		houseBaseMsg.setFid(paramDto.getHouseBaseFid());
		houseBaseMsg.setOperateSeq(operateSeq);
		houseBaseMsg.setLastModifyDate(new Date());
		return houseBaseMsg;
	}

	/**
	 *
	 * 跳转交易信息页面
	 *
	 *  押金规则 说明：
	 *   1. 支持房东输入
	 *   2. 默认当前房源的普通房租（发布房源时 给默认  如果当前输入则给输入值  范围：0到4*99999）
	 *   3. 当前保存都是 按固定
	 * 查询：a.  当前如果是固定金额 返回当前值  b. 当前如果是按天收取  去获取当前房价返回  一旦修改成固定收取
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toExchangeMsg")
	public String toExchangeMsg(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String houseRoomFid = request.getParameter("houseRoomFid");

		//申请类型
		String orderTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0010.getValue());
		DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(orderTypeJson);
		if (dto1.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
					ProductRulesEnum.ProductRulesEnum0010.getValue());
		}else{
			List<EnumVo> orderTypeList = dto1.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			map.put("orderTypeList", orderTypeList);
		}

		//民宿类型
		String homeStayJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0013.getValue());
		DataTransferObject dto2 = JsonEntityTransform.json2DataTransferObject(homeStayJson);
		if (dto2.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
					ProductRulesEnum.ProductRulesEnum0013.getValue());
		}else{
			List<EnumVo> homeStayList = dto2.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			map.put("homeStayList", homeStayList);
		}

		//押金获取 以及退订政策
		findHouseDeposit(houseBaseFid,houseRoomFid,Integer.valueOf(rentWay),map);
		//退订政策
		String checkOutJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
		DataTransferObject dto4 = JsonEntityTransform.json2DataTransferObject(checkOutJson);
		if (dto4.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,houseBaseFid={},code={}", houseBaseFid,
					TradeRulesEnum.TradeRulesEnum005.getValue());
		}else{
			List<EnumVo> checkOutList = dto4.parseData("subDic", new TypeReference<List<EnumVo>>() {});
			map.put("checkOutList", checkOutList);
		}

		map.put("houseFive", request.getParameter("houseFive")==null?"1":request.getParameter("houseFive"));
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("houseRoomFid",houseRoomFid );
		map.put("depositMin",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN);
		map.put("depositMax",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX);
		map.put("flag", request.getParameter("flag"));
		return "house/houseIssue/exchangeMsg";
	}

	/**
	 *
	 *  获取当前房源的押金配置
	 *
	 * @author yd
	 * @created 2016年11月16日 下午3:46:51
	 *
	 * @param houseFid  房源fid 或 房间fid
	 * @param rentWay
	 * @return
	 */
	private  HouseConfMsgEntity  findHouseDeposit(String houseFid,String roomFid ,int rentWay,ModelMap map){

		HouseConfMsgEntity houseConfMsg = null;

		HouseBaseVo houseBaseVo = new HouseBaseVo();
		houseBaseVo.setHouseFid(houseFid);
		houseBaseVo.setRentWay(rentWay);
		houseBaseVo.setRoomFid(roomFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseIssueService.findHouseOrRoomDeposit(JsonEntityTransform.Object2Json(houseBaseVo)));
		
		if(dto.getCode() == DataTransferObject.ERROR){
			LogUtil.info(LOGGER, "获取房东押金异常msg={}", dto.getMsg());
			houseConfMsg = new HouseConfMsgEntity();
			houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
			houseConfMsg.setDicVal("0");
			map.put("houseConfMsg", houseConfMsg);
			return houseConfMsg;
		}
		houseConfMsg = dto.parseData("houseConfMsg", new TypeReference<HouseConfMsgEntity>() {
		});

		LogUtil.info(LOGGER, "获取押金:houseConfMsg={},houseFid={}", JsonEntityTransform.Object2Json(houseConfMsg),houseFid);

		houseConfMsg.setDicVal(DataFormat.formatHundredPriceInt(Integer.valueOf(houseConfMsg.getDicVal())));
		map.put("houseConfMsg", houseConfMsg);
		map.put("exchange", dto.parseData("exchange", new TypeReference<HouseBaseExtEntity>() {
		}));
		return houseConfMsg;

	}
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
	 * 去价格设置页面(整租分租是一个页面)
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/2 15:55
	 */
	@RequestMapping("${LOGIN_UNAUTH}/toPriceDetail")
	public String toPriceDetail(HttpServletRequest request,SpecialPriceDto sp){
		DataTransferObject dto = new DataTransferObject();
		HouseConfMsgEntity confMsgEntity = new HouseConfMsgEntity();
		Map<String, String> paramMap = new HashMap<String, String>();
		String flag = request.getParameter("flag");
		request.setAttribute("flag",flag);
		request.setAttribute("houseBaseFid",sp.getHouseBaseFid());
		request.setAttribute("houseRoomFid",sp.getHouseRoomFid());
		request.setAttribute("rentWay",sp.getRentWay());
		int weekendPriceFlag = 0;// 是否设置周末价格标示 0:未设置 1:已设置有效 2:已设置无效
		request.setAttribute("weekendPriceFlag", weekendPriceFlag);
		request.setAttribute("weekendData", JsonEntityTransform.Object2Json(WeekendPriceEnum.getWeekenddata()));
		try{
		/** 查询是否在页面展示灵活定价和长租优惠的开关判断*/
		Integer longTermLimit =null;
		//长租天数 设置
		String longTermLimitStr = cityTemplateService.getTextValue(null, TradeRulesEnum0020.TradeRulesEnum0020001.getValue());
		if(!Check.NuNObj(longTermLimitStr)){
			longTermLimit = SOAResParseUtil.getValueFromDataByKey(longTermLimitStr, "textValue", Integer.class);
		}else{
			longTermLimit = 1000;
		}
		request.setAttribute("longTermLimit", longTermLimit);
		/** 去模板查询灵活定价配置，返回给页面展示*/
		request.setAttribute("gapList",searchGapFlexList());
		/** */
		//房源价格限制
		String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
		String priceLow = SOAResParseUtil.getValueFromDataByKey(priceLowJson, "textValue", String.class);
		request.setAttribute("priceLow", priceLow);

		String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
		String priceHigh = SOAResParseUtil.getValueFromDataByKey(priceHighJson, "textValue", String.class);
		request.setAttribute("priceHigh", priceHigh);
		if (Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("传入参数有误");
			return "error/error";
		} else {
			confMsgEntity.setHouseBaseFid(sp.getHouseBaseFid());
			paramMap.put("houseFid", sp.getHouseBaseFid());
		}

		if (Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode() == sp.getRentWay())) {
			//此种情况视为房间第一次建立还没有产生roomFid所以价格设置也是第一次
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
			//此情景是用户第一发布分租独立房间，设置过价格又一次进入的情况，需要从保存在redis的数据中返回给页面
			String redisRoomFid = request.getParameter("redisRoomFid");
			String key = RedisKeyConst.getConfigKey("room"+UserUtil.getCurrentUserFid()+redisRoomFid);
			try{
				String priceJson = redisOperations.get(key);
				if(!Check.NuNObj(priceJson)){
					SpecialPriceDto redisSp = SOAResParseUtil.getValueFromDataByKey(priceJson, "specialPriceDto", SpecialPriceDto.class);
					request.setAttribute("redisSp",redisSp);
					if (!Check.NuNObj(redisSp) && !Check.NuNCollection(redisSp.getSetTime())) {
						List<Integer> setWeek = new ArrayList<Integer>();
						for (String string : redisSp.getSetTime()) {
							setWeek.add(Integer.valueOf(string));
						}
						WeekendPriceEnum weekendPrice = WeekendPriceEnum.getEnumByColl(setWeek);
						if (!Check.NuNObj(weekendPrice)) {
							request.setAttribute("weekendPriceValue", weekendPrice.getValue());
							request.setAttribute("weekendPriceText", weekendPrice.getText());
						}
					}
				}
			}catch (Exception e){
				LogUtil.error(LOGGER, "redis错误,e:{}", e);
			}
			return "house/houseIssue/priceDetail";
		}else if(RentWayEnum.ROOM.getCode() == sp.getRentWay()){
			confMsgEntity.setRoomFid(sp.getHouseRoomFid());
			paramMap.put("roomFid", sp.getHouseRoomFid());
		}
		//判断插入还是更新的标志  0：插入   1：更新
		Integer statFlag = 0;
		//根据房源fid房间fid查询数据库是否有灵活定价记录以及长期租住优惠以及优惠折扣率(不论开关是否打开)
		confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getParentValue());
		String resultJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> confList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseConfMsgEntity.class);
		if(!Check.NuNCollection(confList)){
			statFlag = 1;
		}
		request.setAttribute("statFlag",statFlag);
		//分别给页面返回灵活定价，折扣优惠的集合
		//开关打开   is_del : 0,表示打开开关  2,表示关闭开关
		confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
		confMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
		String sevenJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> sevenList = SOAResParseUtil.getListValueFromDataByKey(sevenJson, "list", HouseConfMsgEntity.class);
		if(!Check.NuNCollection(sevenList)){
			request.setAttribute("sevenRate",sevenList.get(0));
		}else{
			request.setAttribute("sevenRate",null);
		}
		confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
		String thirtyJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> thirtyList = SOAResParseUtil.getListValueFromDataByKey(thirtyJson, "list", HouseConfMsgEntity.class);
		if(!Check.NuNCollection(thirtyList)){
			request.setAttribute("thirtyRate",thirtyList.get(0));
		}
		//查询有没有设置 今日特惠折扣
		confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020001.getValue());
		String gapFlexJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> gapFlexList = SOAResParseUtil.getListValueFromDataByKey(gapFlexJson, "list", HouseConfMsgEntity.class);
		
		request.setAttribute("gapFlexList",gapFlexList);
		
		//查询有没有设置 空置间夜自动折扣
		confMsgEntity.setDicCode(ProductRulesEnum020.ProductRulesEnum020002.getValue());
		String gapFlexTJson = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> gapFlexTList = SOAResParseUtil.getListValueFromDataByKey(gapFlexTJson, "list", HouseConfMsgEntity.class);
		request.setAttribute("gapFlexTList",gapFlexTList);

		//开关关闭 的情况取值
		confMsgEntity.setIsDel(IsDelEnum.PRIORITY.getCode());
		confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
		String seven = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> sevenRate = SOAResParseUtil.getListValueFromDataByKey(seven, "list", HouseConfMsgEntity.class);
		if(!Check.NuNCollection(sevenRate)){
			request.setAttribute("seven",sevenRate.get(0));
		}
		confMsgEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
		String thirty = houseIssueService.findGapAndFlexPrice(JsonEntityTransform.Object2Json(confMsgEntity));
		List<HouseConfMsgEntity> thirtyRate = SOAResParseUtil.getListValueFromDataByKey(thirty, "list", HouseConfMsgEntity.class);
		if(!Check.NuNCollection(thirtyRate)){
			request.setAttribute("thirty",thirtyRate.get(0));
		}
		//通过判断rentWay,分别取房间房源表中查询普通价格

		if(RentWayEnum.ROOM.getCode()==sp.getRentWay()){
			String roomJson = houseIssueService.searchHouseRoomMsgByFid(sp.getHouseRoomFid());
			HouseRoomMsgEntity roomEntity = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
			request.setAttribute("roomPrice",roomEntity.getRoomPrice());
		}else if(RentWayEnum.HOUSE.getCode()==sp.getRentWay()){
			String house = houseIssueService.searchHouseBaseMsgByFid(sp.getHouseBaseFid());
			HouseBaseMsgEntity houseEntyty = SOAResParseUtil.getValueFromDataByKey(house, "obj", HouseBaseMsgEntity.class);
			request.setAttribute("housePrice",houseEntyty.getLeasePrice());
		}

		String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
		List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil
				.getListValueFromDataByKey(weekendPriceJson, "list", HousePriceWeekConfEntity.class);
		request.setAttribute("weekendPriceList", weekendPriceList);


		if (!Check.NuNCollection(weekendPriceList)) {
			weekendPriceFlag = 2;
			List<Integer> setWeek = new ArrayList<Integer>();
			Set<Integer> priceVal = new HashSet<Integer>();
			for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
				if (!Check.NuNObj(housePriceWeekConf.getIsDel())
						&& housePriceWeekConf.getIsDel().intValue() == YesOrNoEnum.NO.getCode()
						&& !Check.NuNObj(housePriceWeekConf.getIsValid())
						&& housePriceWeekConf.getIsValid().intValue() == YesOrNoEnum.YES.getCode()) {
					weekendPriceFlag = 1;
				}
				// 设置时间集合
				if (!Check.NuNObj(housePriceWeekConf.getSetWeek())) {
					setWeek.add(housePriceWeekConf.getSetWeek());
				}
				// 周末价格
				if (!Check.NuNObj(housePriceWeekConf.getPriceVal())) {
					priceVal.add(housePriceWeekConf.getPriceVal());
				}
			}

			WeekendPriceEnum weekendPrice = WeekendPriceEnum.getEnumByColl(setWeek);
			if (!Check.NuNObj(weekendPrice)) {
				request.setAttribute("weekendPriceValue", weekendPrice.getValue());
				request.setAttribute("weekendPriceText", weekendPrice.getText());
			}

			if (priceVal.size() == 1) {
				request.setAttribute("weekendPrice", priceVal.iterator().next());
			}
		}
		request.setAttribute("weekendPriceFlag", weekendPriceFlag);

		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return "house/houseIssue/priceDetail";
	}

	/**
	 * 保存价格设置
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 11:02
	 */
	@RequestMapping("${LOGIN_UNAUTH}/savePriceSet")
	@ResponseBody
	public DataTransferObject savePriceSet(HttpServletRequest request, SpecialPriceDto sp,HouseRoomMsgEntity room) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if (Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("传入参数有误");
				return dto;
			}
			String uid = UserUtil.getCurrentUserFid();
			if (Check.NuNStr(uid)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("登录用户信息不存在");
				return dto;
			}
			//处理下null字符串的问题
			if("null".equals(sp.getHouseRoomFid())){
				sp.setHouseRoomFid(null);
			}
			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");
			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

			//提前校验防止没有房间fid跳转房间信息页面后参数不正确
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

			if (Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode() == sp.getRentWay())) {
				// 此种情况是房间第一次建立时没有房间fid要先设置价格的情况，需要利用缓存处理
				sp.setHouseRoomFid(UUIDGenerator.hexUUID());
				// 获得redis的key
				String key = RedisKeyConst.getConfigKey("room" + uid + sp.getHouseRoomFid());
				dto.putValue("specialPriceDto", sp);
				//保存上个房间页面填写的值
				dto.putValue("room",room);
				dto.putValue("redis",1);
				dto.putValue("orPrice",price);
				try {
					redisOperations.setex(key.toString(), RedisKeyConst.ROOM_NEW_CACHE_TIME, dto.toJsonString());
				} catch (Exception e) {
					dto.setErrCode(DataTransferObject.ERROR);
					LogUtil.error(LOGGER, "redis错误,e:{}", e);
				}
				return dto;
			}

			if (sp.isInsert()) {
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
			} else if (sp.isUpdate()) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("houseFid", sp.getHouseBaseFid());
				paramMap.put("roomFid", sp.getHouseRoomFid());
				String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
				List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil
						.getListValueFromDataByKey(weekendPriceJson, "list", HousePriceWeekConfEntity.class);
				if (!Check.NuNCollection(weekendPriceList)) {
					for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
						housePriceWeekConf.setIsValid(YesOrNoEnum.NO.getCode());
						housePriceWeekConf.setRentWay(sp.getRentWay());
					}
					houseManageService.updateHousePriceWeekListByFid(JsonEntityTransform.Object2Json(weekendPriceList));
				}
			}
			if(sp.getFullDayRate()==1&&!Check.NuNObj(sp.getSevenDiscountRate())){
				if(sp.getSevenDiscountRate().intValue() <= 0 || sp.getSevenDiscountRate().intValue() >= 100){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("输入的满7天折扣率非法");
					return dto;
				}
			}
			if(sp.getFullDayRate()==1&&!Check.NuNObj(sp.getThirtyDiscountRate())){
				if(sp.getThirtyDiscountRate().intValue() <= 0 || sp.getThirtyDiscountRate().intValue() >= 100){
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("输入的满30天折扣率非法");
					return dto;
				}
			}
			List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();
			//关于灵活定价和折扣率的处理
			confList = confListAddM(sp,dto,confList);
			/** 插入配置表操作(插入和修改分离)*/
			houseIssueService.saveHouseConfList(JsonEntityTransform.Object2Json(confList));
			//更新普通价格(普通价格需要上限和下限的判断)
			updateOrdinaryPrice(sp);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/** 关于添加灵活间隙定价，折扣率到集合的方法*/
	private List<HouseConfMsgEntity> confListAddM(SpecialPriceDto sp,DataTransferObject dto,List<HouseConfMsgEntity> confList) throws SOAParseException {
//		/** 灵活定价间隙价格插入三条记录，用户每设置一个折扣率增加插入一条*/
//		//灵活以及间隙价格开关判断(0：关闭   1：打开)
//		Integer gapFlexIsDel = IsDelEnum.PRIORITY.getCode();//默认开关灵活定价以及间隙价格开关是关闭的
//		if(sp.getGapAndFlexiblePrice()==1){
//			gapFlexIsDel = IsDelEnum.NOT_DEL.getCode(); //如果用户打开了开关  is_del 值为0  表示默认值也就是打开开关
//		}
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
			//默认关闭灵活定价
			confEntity.setIsDel(IsDelEnum.PRIORITY.getCode());
			//4月7号修改，判断是否开启今日特惠折扣
			if(gapPrice.equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())&&sp.getDayDiscount()==1){
				confEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			}
			if((gapPrice.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue())||gapPrice.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue()))&&sp.getFlexDiscount()==1){
				confEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
			}
			/** 放入集合*/
			confList.add(confEntity);
		}
		/** 折扣率判断*/
		Integer fullDayIsDel = IsDelEnum.PRIORITY.getCode();//默认满天折扣率的开关也是关闭的
		if(sp.getFullDayRate()==1){
			fullDayIsDel = IsDelEnum.NOT_DEL.getCode();//用户打开了开关
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
		if(sp.getFullDayRate()==1&&!Check.NuNObj(sp.getSevenDiscountRate())){
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
		if(sp.getFullDayRate()==1&&!Check.NuNObj(sp.getThirtyDiscountRate())){
			//用户输入了满30天折扣率
			thirtyEntity.setDicVal(String.valueOf(sp.getThirtyDiscountRate()));
		}else{
			//如果用户没有输入，给一个默认值-1代表用户未设置具体的折扣率
			thirtyEntity.setDicVal(String.valueOf(-1));
		}
		thirtyEntity.setIsDel(fullDayIsDel);
		/** 放入集合*/
		confList.add(thirtyEntity);

		return confList;
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

	/**
	 * 修改价格设置
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 11:02
	 */
	@RequestMapping("${LOGIN_UNAUTH}/updatePriceSet")
	@ResponseBody
	public DataTransferObject updatePriceSet(HttpServletRequest request,SpecialPriceDto sp){
		DataTransferObject dto = new DataTransferObject();
		//处理null字符串的问题
		if("null".equals(sp.getHouseRoomFid())){
			sp.setHouseRoomFid(null);
		}
		//控制灵活间隙价格开关实体
		HouseConfMsgEntity gapFlexEntity = new HouseConfMsgEntity();
		if(Check.NuNObj(sp.getHouseBaseFid()) || Check.NuNObj(sp.getRentWay())){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("传入参数有误");
			return dto;
		} else { 
			gapFlexEntity.setHouseBaseFid(sp.getHouseBaseFid()); 
		}
		if(Check.NuNObj(sp.getHouseRoomFid()) && (RentWayEnum.ROOM.getCode()==sp.getRentWay()) ){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("未传入房间fid");
			return dto;
		} else { 
			gapFlexEntity.setRoomFid(sp.getHouseRoomFid()); 
		}
		try{
			//房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");

			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");
			if (sp.isInsert()) {
				String uid = UserUtil.getCurrentUserFid();
				if (Check.NuNStr(uid)) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("请重新登录");
					return dto;
				}
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
			} else if (sp.isUpdate()) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("houseFid", sp.getHouseBaseFid());
				paramMap.put("roomFid", sp.getHouseRoomFid());
				String weekendPriceJson = houseManageService.findWeekPriceByFid(JsonEntityTransform.Object2Json(paramMap));
				List<HousePriceWeekConfEntity> weekendPriceList = SOAResParseUtil
						.getListValueFromDataByKey(weekendPriceJson, "list", HousePriceWeekConfEntity.class);
				if (!Check.NuNCollection(weekendPriceList)) {
					for (HousePriceWeekConfEntity housePriceWeekConf : weekendPriceList) {
						housePriceWeekConf.setIsValid(YesOrNoEnum.NO.getCode());
						housePriceWeekConf.setRentWay(sp.getRentWay());
					}
					houseManageService.updateHousePriceWeekListByFid(JsonEntityTransform.Object2Json(weekendPriceList));
				}
			}
			//如果用户打开了灵活间隙价格开关
			if(sp.getGapAndFlexiblePrice() == 1){
				gapFlexEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());//更新为打开 0
			}else{
				gapFlexEntity.setIsDel(IsDelEnum.PRIORITY.getCode());//更新为关闭 2
			}
			//今日特惠价格和空置间夜自动折扣是否开启判断 4月7号
			List<HouseConfMsgEntity> confList=new ArrayList<>();
			for(String gapFlex:gapFlexlist){
				HouseConfMsgEntity houseConfMsgEntity=new HouseConfMsgEntity();
				houseConfMsgEntity.setHouseBaseFid(sp.getHouseBaseFid());
				houseConfMsgEntity.setRoomFid(sp.getHouseRoomFid());
				houseConfMsgEntity.setDicCode(gapFlex);
				houseConfMsgEntity.setIsDel(IsDelEnum.PRIORITY.getCode());
				if(gapFlex.equals(ProductRulesEnum020.ProductRulesEnum020001.getValue())&&sp.getDayDiscount()==1){
					houseConfMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
				}
				if((gapFlex.equals(ProductRulesEnum020.ProductRulesEnum020002.getValue())||gapFlex.equals(ProductRulesEnum020.ProductRulesEnum020003.getValue()))&&sp.getFlexDiscount()==1){
					houseConfMsgEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
				}
				confList.add(houseConfMsgEntity);
			}
			//更新灵活间隙价格开关状态
			houseIssueService.updateHouseConfList(JsonEntityTransform.Object2Json(confList));
			//根据fid 和用户输入的折扣率去更新数据
			houseIssueService.saveOrUpHouseConf(JsonEntityTransform.Object2Json(sp));
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
			//更新普通价格
			updateOrdinaryPrice(sp);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 *
	 * 跳转退订政策页面
	 *
	 * @author 杨东
	 * @created 2016年5月28日
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toCheckOutRules")
	public String toCheckOutRules(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String checkOutRulesCode = request.getParameter("checkOutRulesCode");
		//退订政策
		String checkOutRulesJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkOutRulesJson);
		Integer longTermLimit =null;
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,houseBaseFid={},code={}", houseBaseFid,
					TradeRulesEnum.TradeRulesEnum005.getValue());
		}else{
			List<Map<String, Object>> checkOutList = new ArrayList<Map<String,Object>>();
			List<EnumVo> checkOutRulesList = dto.parseData("subDic", new TypeReference<List<EnumVo>>() {});

			boolean isNew = false;//新版本 修改退订政策
			
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

				String dicCode = "";
				if(TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue().startsWith(checkOutRules.getKey())){
					dicCode = TradeRulesEnum005001Enum.TradeRulesEnum005001001.getValue();
				}else if(TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue().startsWith(checkOutRules.getKey())){
					dicCode = TradeRulesEnum005003Enum.TradeRulesEnum005003001.getValue();
				}else if(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue().startsWith(checkOutRules.getKey())){
					dicCode = TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue();
				}else{
					dicCode = TradeRulesEnum005002Enum.TradeRulesEnum005002001.getValue();
				}
				
				String checkOutRulesValJson = cityTemplateService.getSelectSubDic(null, dicCode);
				DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(checkOutRulesValJson);
				if (dto1.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,houseBaseFid={},code={}",
							houseBaseFid, dicCode);
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
                        String commStr = tradeRulesVo.getCommonShowName();
                        if(!Check.NuNStrStrict(commStr)){
                        	commStr = commStr.replaceAll("\n\n", "</br>");
                        	tradeRulesVo.setCommonShowName(commStr);
                        }
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
			map.put("checkOutList", checkOutList);
		}

		map.put("longTermLimit", longTermLimit);
		map.put("houseFive", request.getParameter("houseFive")==null?"2": request.getParameter("houseFive"));
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("checkOutRulesCode", checkOutRulesCode);
		map.put("houseRoomFid", request.getParameter("houseRoomFid"));
		map.put("flag", request.getParameter("flag"));
		return "house/houseIssue/checkOutRules";
	}


	/**
	 *
	 * 保存交易信息
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateExchangeMsg")
	@ResponseBody
	public DataTransferObject updateExchangeMsg(HttpServletRequest request,HouseBaseExtVo paramDto) {
		LogUtil.info(LOGGER, "参数:{}", JsonEntityTransform.Object2Json(paramDto));
		DataTransferObject dto = new DataTransferObject();
		try {

			if(Check.NuNStr(paramDto.getHouseBaseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto;
			}

			//押金校验
			List<HouseConfMsgEntity> houseConfList = paramDto.getHouseConfList();
			if(!Check.NuNCollection(houseConfList)){

				List<HouseConfMsgEntity> confList = new ArrayList<HouseConfMsgEntity>();

				HouseConfMsgEntity con = houseConfList.get(0);
				if(con.getDicCode().equals(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue())){
					String val = con.getDicVal();
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
					con.setDicVal(String.valueOf(depositVal*100));//转化成分
				}
				confList.add(con);
				paramDto.setHouseConfList(confList);
			}
			//所有押金规则都按固定收取
			paramDto.setDepositRulesCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
			String resultJson = houseIssueService.mergeHouseBaseExtAndHouseConfList(JsonEntityTransform.Object2Json(paramDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "调用接口失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
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
	 * 跳转入住信息页面
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toCheckInMsg")
	public String toCheckInMsg(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");

		//最小入住天数
		String minDayJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0016.getValue());
		DataTransferObject dto1 = JsonEntityTransform.json2DataTransferObject(minDayJson);
		if (dto1.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
					ProductRulesEnum.ProductRulesEnum0016.getValue());
		}else{
			List<EnumVo> minDayList = dto1.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			map.put("minDayList", minDayList);
		}


		//入住时间
		String checkInTimeJson = cityTemplateService.getSelectEnum(null,
				ProductRulesEnum.ProductRulesEnum003.getValue());
		DataTransferObject dto2 = JsonEntityTransform.json2DataTransferObject(checkInTimeJson);
		if (dto2.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},,code={}", houseBaseFid,
					ProductRulesEnum.ProductRulesEnum003.getValue());
		}else{
			List<EnumVo> checkInTimeList = dto2.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			map.put("checkInTimeList", checkInTimeList);
		}

		//退房时间
		String checkOutTimeJson = cityTemplateService.getSelectEnum(null,
				ProductRulesEnum.ProductRulesEnum004.getValue());
		DataTransferObject dto3 = JsonEntityTransform.json2DataTransferObject(checkOutTimeJson);
		if (dto3.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
					ProductRulesEnum.ProductRulesEnum004.getValue());
		}else{
			List<EnumVo> checkOutTimeList = dto3.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			map.put("checkOutTimeList", checkOutTimeList);
		}

		//被单更换
		String sheetReplaceJson = cityTemplateService.getSelectEnum(null,
				ProductRulesEnum.ProductRulesEnum0014.getValue());
		DataTransferObject dto4 = JsonEntityTransform.json2DataTransferObject(sheetReplaceJson);
		if (dto4.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", houseBaseFid,
					ProductRulesEnum.ProductRulesEnum0014.getValue());
		}else{
			List<EnumVo> sheetReplaceList = dto4.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			map.put("sheetReplaceList", sheetReplaceList);
		}

		String resultJson = houseIssueService.searchHouseDescAndBaseExt(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "houseIssueService.searchHouseBaseAndExtByFid调用失败,houseBaseFid={}", houseBaseFid);
		} else {
			HouseDescDto checkIn = dto.parseData("obj", new TypeReference<HouseDescDto>() {});
			//查询数据库中房屋可选守则列表
			String result = troyHouseMgtService.searchHouseConfListByFidAndCode(houseBaseFid, ProductRulesEnum.ProductRulesEnum0024.getValue());
			List<String> listFindByFidAndCode = null;
			try {
				listFindByFidAndCode = SOAResParseUtil.getListValueFromDataByKey(result, "list", String.class);
			} catch (SOAParseException e) {
				LogUtil.error(LOGGER, "查询房屋可选守则异常e={}", e);
			}
			if (!Check.NuNObj(checkIn)) {
				HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
				BeanUtils.copyProperties(checkIn, houseBaseExt);
				map.put("houseBaseExt", houseBaseExt);
				if(Check.NuNStr(checkIn.getHouseRules()) && listFindByFidAndCode.size() == 0){
					map.put("isComplete", 0);// 未完成
				}else{
					map.put("isComplete", 1);// 已完成
				}
			}
		}
		map.put("houseFive", request.getParameter("houseFive")==null?"1":request.getParameter("houseFive"));
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("houseRoomFid", request.getParameter("houseRoomFid"));
		map.put("flag", request.getParameter("flag"));
		return "house/houseIssue/checkInMsg";
	}
	
	/**
	 * 
	 * 跳转房屋守则可选+自定义页面
	 *
	 * @author baiwei
	 * @created 2017年4月6日 上午10:46:59
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toSelectHouseRules")
	public String toSelectHouseRules(HttpServletRequest request){
		String houseBaseFid = request.getParameter("houseBaseFid");
		//房屋可选守则列表
		String selectHouseRulesJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0024.getValue());
		List<EnumVo> selectHouseRulesList = null;
		try {
			selectHouseRulesList = SOAResParseUtil.getListValueFromDataByKey(selectHouseRulesJson, "selectEnum", EnumVo.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "查询房屋可选守则异常e={}", e);
		}
		//查询数据库中房屋可选守则列表
		String result = troyHouseMgtService.searchHouseConfListByFidAndCode(houseBaseFid, ProductRulesEnum.ProductRulesEnum0024.getValue());
		List<String> listFindByFidAndCode = null;
		try {
			listFindByFidAndCode = SOAResParseUtil.getListValueFromDataByKey(result, "list", String.class);
		} catch (SOAParseException e) {
			LogUtil.error(LOGGER, "查询房屋可选守则异常e={}", e);
		}
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("selectHouseRulesList", selectHouseRulesList);
		request.setAttribute("listFindByFidAndCode", listFindByFidAndCode);
		return "house/houseIssue/selectHouseRules";
	}
	
	/**
	 * 
	 * 更新房屋守则中的可选守则
	 *
	 * @author baiwei
	 * @created 2017年4月6日 下午8:55:50
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateSelectHouseRules")
	@ResponseBody
	public DataTransferObject updateSelectHouseRules(HttpServletRequest request){
		
		DataTransferObject dto = new DataTransferObject();
		String houseBaseFid = request.getParameter("houseBaseFid");
		String houseRoomFid = request.getParameter("houseRoomFid");
		String selectRulesArray = request.getParameter("selectRules");  
		if(Check.NuNStr(houseBaseFid)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("房源fid不存在");
			return dto;
		}
		List<HouseConfMsgEntity> listHouseRules = new ArrayList<HouseConfMsgEntity>(); 
		String[] selectRules = selectRulesArray.split(",");
		for(int i = 0; i < selectRules.length; i++){
			HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
			String value = selectRules[i];
			houseConfMsg.setHouseBaseFid(houseBaseFid);
			houseConfMsg.setRoomFid(houseRoomFid);
			houseConfMsg.setDicCode(ProductRulesEnum.ProductRulesEnum0024.getValue());
			houseConfMsg.setDicVal(value);
			listHouseRules.add(houseConfMsg);
		}
		if(Check.NuNCollection(listHouseRules)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("请选择房屋守则");
			return dto;
		}
		dto = JsonEntityTransform.json2DataTransferObject(this.houseIssueService.updateSelectHouseRules(JsonEntityTransform.Object2Json(listHouseRules)));
		return dto;
	}
	
	/**
	 *
	 * 跳转房屋守则页面
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/toHouseRules")
	public String toHouseRules(HttpServletRequest request,ModelMap map) {
		String houseBaseFid = request.getParameter("houseBaseFid");
		String rentWay = request.getParameter("rentWay");
		String resultJson = houseIssueService.searchHouseDescAndBaseExt(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "houseIssueService.searchHouseBaseAndExtByFid调用失败,houseBaseFid={}", houseBaseFid);
		} else {
			HouseDescDto check = dto.parseData("obj", new TypeReference<HouseDescDto>() {});
			if (!Check.NuNObj(check)) {
				map.put("houseRules", check.getHouseRules());
			}
		}
		map.put("houseFive", request.getParameter("houseFive")==null?"1":request.getParameter("houseFive"));
		map.put("houseBaseFid", houseBaseFid);
		map.put("rentWay", rentWay);
		map.put("houseRoomFid", request.getParameter("houseRoomFid"));
		map.put("flag", request.getParameter("flag"));
		return "house/houseIssue/houseRules";
	}

	/**
	 *
	 * 保存入住信息
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateCheckInMsg")
	@ResponseBody
	public DataTransferObject updateCheckInMsg(HttpServletRequest request,HouseDescDto paramDto) {
		LogUtil.info(LOGGER, "参数:{}", JsonEntityTransform.Object2Json(paramDto));
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(paramDto.getHouseBaseFid())){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_BASE_FID_NULL));
				LogUtil.error(LOGGER, dto.toJsonString());
				return dto;
			}

			String resultJson = houseIssueService.updateHouseDescAndBaseExt(JsonEntityTransform.Object2Json(paramDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseIssueService.updateHouseDescAndBaseExt调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
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
	 * 下架房源
	 *
	 * @author liujun
	 * @created 2016年6月1日
	 *
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/downHouse")
	@ResponseBody
	public DataTransferObject downHouse(String houseFid, String landlordUid, int rentWay) {
		LogUtil.info(LOGGER, "参数:houseFid={},landlordUid={},rentWay={}", houseFid, landlordUid, rentWay);
		DataTransferObject dto = new DataTransferObject();
		String resultJson = null;
		try {
			if(rentWay == RentWayEnum.HOUSE.getCode()){
				resultJson = houseManageService.upDownHouse(houseFid, landlordUid);
			}else if(rentWay == RentWayEnum.ROOM.getCode()){
				resultJson = houseManageService.upDownHouseRoom(houseFid, landlordUid);
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_RENTWAY_ERROR));
				return dto;
			}

			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseManageService.upDownHouse调用失败,参数:houseFid={},landlordUid={},rentWay={}",
						houseFid, landlordUid, rentWay);
				return dto;
			}

			//从发布到上线 任何一个环节都不给管家发消息了
		/*	if (!Check.NuNStr(houseFid) && !Check.NuNObj(rentWay) && !Check.NuNStr(landlordUid)) {
				String houseName = "";
				String houseSn = "";
				if (rentWay == RentWayEnum.HOUSE.getCode()) {
					HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseFid);
					if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getHouseName())
							|| Check.NuNStr(houseBaseMsg.getHouseSn())) {
						return dto;
					}
					houseName = houseBaseMsg.getHouseName();
					houseSn = houseBaseMsg.getHouseSn();
				} else if (rentWay == RentWayEnum.ROOM.getCode()){
					HouseRoomMsgEntity houseRoomMsg = this.findHouseRoomMsgEntity(houseFid);
					if (Check.NuNObj(houseRoomMsg) || Check.NuNStr(houseRoomMsg.getHouseBaseFid())
							|| Check.NuNStr(houseRoomMsg.getRoomName()) || Check.NuNStr(houseRoomMsg.getRoomSn())) {
						return dto;
					}
					houseFid = houseRoomMsg.getHouseBaseFid();
					houseName = houseRoomMsg.getRoomName();
					houseSn = houseRoomMsg.getRoomSn();
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(landlordUid);
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getRealName())) {
					return dto;
				}

				HouseGuardRelEntity houseGuardRel = this.findHouseGuardRelEntity(houseFid);
				if(!Check.NuNObj(houseGuardRel)){
					if(!Check.NuNStr(houseGuardRel.getEmpPushCode())){
						EmployeeEntity empPush = this.findEmployeeEntity(houseGuardRel.getEmpPushCode());
						if(!Check.NuNObj(empPush) && !Check.NuNStr(empPush.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", landlord.getRealName());
							paramsMap.put("{2}", houseName);
							paramsMap.put("{3}", houseSn);
							this.sendSms(empPush.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_DOWN.getCode());
						}
					}

					if(!Check.NuNStr(houseGuardRel.getEmpGuardCode())
							&& !houseGuardRel.getEmpGuardCode().equals(houseGuardRel.getEmpPushCode())) {
						EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRel.getEmpGuardCode());
						if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", landlord.getRealName());
							paramsMap.put("{2}", houseName);
							paramsMap.put("{3}", houseSn);
							this.sendSms(empGuard.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_DOWN.getCode());
						}
					}
				}
			}*/
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.info(LOGGER, "下架房源-结果:{}", resultJson);
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
	/*private HouseRoomMsgEntity findHouseRoomMsgEntity(String houseRoomFid) throws SOAParseException {
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
	}*/

	/**
	 *
	 * 列表页进入，发布房源
	 *
	 * @author jixd
	 * @created 2016年6月15日 上午10:41:17
	 *
	 * @param houseBaseDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/issueHouseInDetail")
	@ResponseBody
	public DataTransferObject issueHouseInDetail(HouseBaseDto houseBaseDto){
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNObj(houseBaseDto)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.PARAM_NULL));
			LogUtil.info(LOGGER, "error:{}", dto.toJsonString());
			return dto;
		}
		if (Check.NuNStr(houseBaseDto.getHouseFid())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.HOUSE_BASE_FID_NULL));
			LogUtil.info(LOGGER, "error:{}", dto.toJsonString());
			return dto;
		}
		if (Check.NuNStr(houseBaseDto.getLandlordUid())) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MappMessageConst.LANDLORDUID_NULL));
			LogUtil.info(LOGGER, "error:{}", dto.toJsonString());
			return dto;
		}
		try {
			Map<Integer, String> errorMsgMap = new TreeMap<Integer, String>();
			boolean isUploadable = this.validatePicNumByType(houseBaseDto.getHouseFid(), houseBaseDto.getRentWay(),
					houseBaseDto.getRoomFid(), errorMsgMap);
			if (!isUploadable) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg(assembleErrorMsg(errorMsgMap));
				return dto;
			}

			/****************校验是否已经设置默认照片,M站兼容（@Date:2017.07.14 @Author:lusp）**************/
			HouseBaseParamsDto houseBaseParamsDto = new HouseBaseParamsDto();
			houseBaseParamsDto.setHouseBaseFid(houseBaseDto.getHouseFid());
			houseBaseParamsDto.setRentWay(houseBaseDto.getRentWay());
			houseBaseParamsDto.setRoomFid(houseBaseDto.getRoomFid());
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

			String resultJson = houseIssueService.issueHouseInDetail(JsonEntityTransform.Object2Json(houseBaseDto));
			LogUtil.debug(LOGGER, "M站——列表页进入，发布房源,结果:{}",resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			//从发布到上线 任何一个环节都不给管家发消息了
			/*if (dto.getCode() == DataTransferObject.SUCCESS) {
				HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseDto.getHouseFid());
				if (Check.NuNObj(houseBaseMsg) || Check.NuNStr(houseBaseMsg.getHouseName())
						|| Check.NuNStr(houseBaseMsg.getLandlordUid()) || Check.NuNStr(houseBaseMsg.getHouseSn())) {
					return dto;
				}

				CustomerBaseMsgEntity landlord = this.findCustomerBaseMsgEntity(houseBaseDto.getLandlordUid());
				if (Check.NuNObj(landlord) || Check.NuNStr(landlord.getRealName())) {
					return dto;
				}

				HouseGuardRelEntity houseGuardRel = this.findHouseGuardRelEntity(houseBaseDto.getHouseFid());
				if(!Check.NuNObj(houseGuardRel)){
					if(!Check.NuNStr(houseGuardRel.getEmpPushCode())){
						EmployeeEntity empPush = this.findEmployeeEntity(houseGuardRel.getEmpPushCode());
						if(!Check.NuNObj(empPush) && !Check.NuNStr(empPush.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", landlord.getRealName());
							paramsMap.put("{2}", houseBaseMsg.getHouseName());
							paramsMap.put("{3}", houseBaseMsg.getHouseSn());
							this.sendSms(empPush.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_RELEASE.getCode());
						}
					}

					if (!Check.NuNStr(houseGuardRel.getEmpGuardCode())
							&& !houseGuardRel.getEmpGuardCode().equals(houseGuardRel.getEmpPushCode())) {
						EmployeeEntity empGuard = this.findEmployeeEntity(houseGuardRel.getEmpGuardCode());
						if(!Check.NuNObj(empGuard) && !Check.NuNStr(empGuard.getEmpMobile())){
							Map<String, String> paramsMap = new HashMap<String, String>();
							paramsMap.put("{1}", landlord.getRealName());
							paramsMap.put("{2}", houseBaseMsg.getHouseName());
							paramsMap.put("{3}", houseBaseMsg.getHouseSn());
							this.sendSms(empGuard.getEmpMobile(), paramsMap, MessageTemplateCodeEnum.HOUSE_RELEASE.getCode());
						}
					}
				}
			}*/
		} catch (Exception e) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			LogUtil.info(LOGGER, "error:{}", e);
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
					errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)+ "张");
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
						errorMsgMap.put(Integer.MAX_VALUE, "照片总数不能少于" + (HousePicTypeEnum.MINIMUM_NUM_OF_HOUSE_PIC -subNum)+ "张");
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
	 * 根据房源逻辑id查询房源基本信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param houseBaseFid
	 * @throws SOAParseException
	 * @return
	 */
	/*private HouseBaseMsgEntity findHouseBaseMsgEntity(String houseBaseFid) throws SOAParseException {
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
	}*/

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
	/*private CustomerBaseMsgEntity findCustomerBaseMsgEntity(String uid) throws SOAParseException {
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
	 * 根据员工编号查询员工信息
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param empCode
	 * @return
	 * @throws SOAParseException
	 */
	/*private EmployeeEntity findEmployeeEntity(String empCode) throws SOAParseException {
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
	 * 发送短信
	 *
	 * @author liujun
	 * @created 2016年7月22日
	 *
	 * @param mobile
	 * @param paramsMap
	 * @param smsCode
	 */
/*	private void sendSms(String mobile, Map<String, String> paramsMap, int smsCode) {
		SmsRequest smsRequest = new SmsRequest();
		smsRequest.setMobile(mobile);
		smsRequest.setParamsMap(paramsMap);
		smsRequest.setSmsCode(String.valueOf(smsCode));
		smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
	}*/

	/**
	 *
	 * 逻辑删除房源
	 *
	 * @author jixd
	 * @created 2016年6月14日 下午1:49:13
	 *
	 * @param houseBaseDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/delHouse")
	@ResponseBody
	public DataTransferObject delHouse(HouseBaseDto houseBaseDto) {
		DataTransferObject dto = new DataTransferObject();
		String roomFid = houseBaseDto.getRoomFid();
		if(Check.NuNStr(roomFid) || "null".equals(roomFid)){
			houseBaseDto.setRoomFid(null);
		}
		try {
            houseBaseDto.setLandlordUid(UserUtil.getCurrentUserFid());
            String resultJson = houseIssueService.deleteHouseOrRoomByFid(JsonEntityTransform.Object2Json(houseBaseDto));
			LogUtil.info(LOGGER, "结果：{}", resultJson);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER,"删除房源操作异常:{}",dto.getMsg());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "删除房源操作异常:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 *
	 * 取消发布
	 *
	 * @author jixd
	 * @created 2016年6月14日 下午1:52:43
	 *
	 * @param houseBaseDto
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/cancleHouse")
	@ResponseBody
	public DataTransferObject cancleHouse(HouseBaseDto houseBaseDto) {
		String paramJson = JsonEntityTransform.Object2Json(houseBaseDto);
		LogUtil.info(LOGGER, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		String resultJson = null;
		try {
			String cancleJson = houseIssueService.cancleHouseOrRoomByFid(paramJson);
			dto = JsonEntityTransform.json2DataTransferObject(cancleJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"取消发布异常:{}",dto.getMsg());
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		LogUtil.debug(LOGGER, "结果:{}", resultJson);
		return dto;
	}

	/**
	 *
	 * 获取房源操作日志（审核未通过原因）
	 *
	 * @author jixd
	 * @created 2016年6月16日 上午9:19:24
	 *
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/getHouseOperLog")
	@ResponseBody
	public DataTransferObject getHouseOperLog(HouseOpLogSpDto houseOpLog){
		DataTransferObject dto = new DataTransferObject();
		try{
			houseOpLog.setPage(1);
			houseOpLog.setLimit(1);
			houseOpLog.setOperateType(1);
			houseOpLog.setToStatus(HouseStatusEnum.XXSHWTG.getCode());

			if("null".equals(houseOpLog.getRoomFid())){
				houseOpLog.setRoomFid(null);
			}
			String operListJson = houseIssueService.findOperateLogList(JsonEntityTransform.Object2Json(houseOpLog));
			DataTransferObject logdto = JsonEntityTransform.json2DataTransferObject(operListJson);
			List<HouseOperateLogEntity> operList = logdto.parseData("list", new TypeReference<List<HouseOperateLogEntity>>() {});
			if(operList.size() < 1){
				dto.putValue("remark", "");
				return dto;
			}
			HouseOperateLogEntity houseOperateLogEntity = operList.get(0);
			String remark = houseOperateLogEntity == null ? "":houseOperateLogEntity.getRemark();
			dto.putValue("remark", remark);
			return dto;
		}catch(Exception e){
			LogUtil.error(LOGGER, "查询房源审核原因异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("查询日志异常");
		}
		return dto;
	}	
	
	/**
	 * 
	 * 更新房源扩展属性
	 *
	 * @author bushujie
	 * @created 2017年4月5日 下午5:09:07
	 *
	 * @param houseBaseExtEntity
	 * @return
	 */
	@RequestMapping("/${LOGIN_UNAUTH}/updateHouseBaseExtByHouseBaseFid")
	@ResponseBody
	public DataTransferObject updateHouseBaseExtByHouseBaseFid(HttpServletRequest request,HouseBaseExtEntity houseBaseExtEntity){
		
		Header header = getHeader(request);
		String uid = getUserId(request);
		LogUtil.info(LOGGER, "更新房源扩展属性，uid={}，header={}，data={}",uid,JsonEntityTransform.Object2Json(header),JsonEntityTransform.Object2Json(houseBaseExtEntity));
		
		String resultJson=houseIssueService.updateHouseBaseExtByHouseBaseFid(JsonEntityTransform.Object2Json(houseBaseExtEntity));
		return JsonEntityTransform.json2DataTransferObject(resultJson);
	}
}
