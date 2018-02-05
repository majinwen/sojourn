
package com.ziroom.minsu.api.house.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.constant.ApiMessageConst;
import com.ziroom.minsu.api.common.constant.ConstDef;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.header.Header;
import com.ziroom.minsu.api.common.logic.ParamCheckLogic;
import com.ziroom.minsu.api.common.logic.ValidateResult;
import com.ziroom.minsu.api.common.util.BaseMethodUtil;
import com.ziroom.minsu.api.house.service.HouseService;
import com.ziroom.minsu.api.house.service.HouseUpdateLogService;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.house.*;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.basedata.entity.EnumVo;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeSimpleVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.entity.FieldSelectListVo;
import com.ziroom.minsu.services.common.entity.FieldSelectVo;
import com.ziroom.minsu.services.common.entity.FieldTextValueVo;
import com.ziroom.minsu.services.common.entity.FieldTextVo;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.DecimalCalculate;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.house.api.inner.HouseIssueAppService;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseManageService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.dto.*;
import com.ziroom.minsu.services.house.entity.HouseBaseDetailVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.services.house.entity.HouseFieldAuditLogVo;
import com.ziroom.minsu.services.house.entity.RoomMsgVo;
import com.ziroom.minsu.services.house.issue.dto.*;
import com.ziroom.minsu.services.house.issue.vo.*;
import com.ziroom.minsu.valenum.common.WeekendPriceEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.common.YesOrNoOrFrozenEnum;
import com.ziroom.minsu.valenum.house.*;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0019;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0027;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum021Enum;
import com.ziroom.minsu.valenum.traderules.*;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * <p>发布房源</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zl
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/houseIssue")
public class LandlordHouseIssueController extends AbstractController {


	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LandlordHouseIssueController.class);

	@Resource(name = "house.houseManageService")
	private HouseManageService houseManageService;

	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name = "api.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	@Resource(name="basedata.staticResourceService")
	private StaticResourceService staticResourceService;

	@Resource(name="basedata.zkSysService")
    private ZkSysService zkSysService;

	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "basedata.cityTemplateService")
	private CityTemplateService cityTemplateService;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String pic_base_addr_mona;

	@Value("#{'${pic_size}'.trim()}")
	private String pic_size;

	@Value("#{'${LOGIN_UNAUTH}'.trim()}")
	private String LOGIN_UNAUTH;

	//每项之间的分隔符
	private static final String splist = ",";
	//每项中子项的分隔符
	private static final String subSplist = "_";

	@Resource(name="house.houseIssueAppService")
	private HouseIssueAppService houseIssueAppService;

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	@Resource(name = "api.houseService")
	private HouseService houseService;

	@Resource(name = "api.houseUpdateLogService")
	private HouseUpdateLogService houseUpdateLogService;
	

	@Value("#{'${crm_validList}'.trim()}")
	private String crm_validList;

	/**
     *
     * 初始化房源类型和位置信息（发布房源第一步1-1和1-2）
     *
	 * @author bushujie
	 * @created 2017年6月19日 上午10:31:41
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initTypeLocation")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initTypeLocation(HttpServletRequest request){
		try {
			Header header=getHeader(request);
			LogUtil.info(LOGGER, "[initTypeLocation]初始化房源类型和位置header：" + header);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initTypeLocation]初始化房源类型和位置参数：" + paramJson);
			HouseBaseVo houseBaseVo=JsonEntityTransform.json2Object(paramJson, HouseBaseVo.class);
			HousePhyExtVo housePhyExtVo=new HousePhyExtVo();
			//查询房源状态
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			Integer bizNum=0;
			//如果houseBaseFid不为空查询房源基础新
			if(!Check.NuNStr(houseBaseVo.getHouseBaseFid())){

				if(Check.NuNObj(houseBaseVo.getRentWay()) || Check.NuNObj(RentWayEnum.getRentWayByCode(houseBaseVo.getRentWay()))){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式错误"), HttpStatus.OK);
				}

				String housePhyExtJson=houseIssueAppService.searchHousePhyAndExt(houseBaseVo.getHouseBaseFid());
				housePhyExtVo=SOAResParseUtil.getValueFromDataByKey(housePhyExtJson,"housePhyExtVo", HousePhyExtVo.class);
				if(Check.NuNObj(housePhyExtVo)||Check.NuNObj(housePhyExtVo.getHouseBaseMsgEntity())){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
				}
				bizNum=SOAResParseUtil.getIntFromDataByKey(housePhyExtJson, "bizNum");
				if(houseBaseVo.getRentWay()==RentWayEnum.HOUSE.getCode()){
					houseStatus=housePhyExtVo.getHouseBaseMsgEntity().getHouseStatus();
				}
			}
			if(!Check.NuNObj(houseBaseVo.getRentWay())&&houseBaseVo.getRentWay()==RentWayEnum.ROOM.getCode()&&!Check.NuNObj(houseBaseVo.getRoomFid())){
				String roomJson=houseIssueService.searchHouseRoomMsgByFid(houseBaseVo.getRoomFid());
				HouseRoomMsgEntity roomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				if(Check.NuNObj(roomMsgEntity)){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
				}
				houseStatus=roomMsgEntity.getRoomStatus();
			}
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(houseBaseVo.getHouseBaseFid(), houseBaseVo.getRoomFid(), houseBaseVo.getRentWay(), 0);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_Nation_Code.getFieldPath())){
				housePhyExtVo.getHousePhyMsgEntity().setNationCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_Nation_Code.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_Province_Code.getFieldPath())){
				housePhyExtVo.getHousePhyMsgEntity().setProvinceCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_Province_Code.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_City_Code.getFieldPath())){
				housePhyExtVo.getHousePhyMsgEntity().setCityCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_City_Code.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Phy_Msg_Area_Code.getFieldPath())){
				housePhyExtVo.getHousePhyMsgEntity().setAreaCode(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Phy_Msg_Area_Code.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_House_Street.getFieldPath())){
				housePhyExtVo.getHouseBaseExtEntity().setHouseStreet(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_House_Street.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Detail_Address.getFieldPath())){
				housePhyExtVo.getHouseBaseExtEntity().setDetailAddress(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Detail_Address.getFieldPath()).getNewValue());
			}
			/**要审核字段替换结束**/
			HouseTypeLocationVo vo=new HouseTypeLocationVo();
			//查询新版本开关
			Integer isOpenNew=0;
			String isOpenNewS =zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());
			if(!Check.NuNStr(isOpenNewS)){
				isOpenNew=Integer.valueOf(isOpenNewS);
			}

			/**
			 *yanb 解决map赋值remove改变原对象的bug
			 */
			//赋值出租方式
			FieldSelectListVo rentWay=new FieldSelectListVo();
			Map<Integer,String> rentWayEnumMap=RentWayEnum.getEnumMap();
			Map<Integer, String> rentWayMap = new HashMap<>();
			rentWayMap.putAll(rentWayEnumMap);



			/**
			 * yanb 共享客厅
			 * 开关+版本控制+友家合同验证控制是否屏蔽
			 * 返回时共享客厅默认选中
			 */

			//检查共享客厅开关是否开启
			Integer isOpenHall = 0;
			String isOpenHallS = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_hall_switch.getType(), EnumMinsuConfig.minsu_hall_switch.getCode());
			LogUtil.info(LOGGER,"共享客厅开关:{}", isOpenHallS);
			if (!Check.NuNStr(isOpenHallS)) {
				isOpenHall = Integer.valueOf(isOpenHallS);
			}
			//出租方式(-1不代表任何出租方式，用判断)
			Integer upRentWay=-1;
			if(!Check.NuNObj(housePhyExtVo.getHouseBaseMsgEntity())){
				upRentWay = housePhyExtVo.getHouseBaseMsgEntity().getRentWay();
				if (upRentWay == RentWayEnum.ROOM.getCode()) {
					String roomTypeJson = houseIssueService.isHallByHouseBaseFid(housePhyExtVo.getHouseBaseMsgEntity().getFid());
					Integer roomType = SOAResParseUtil.getIntFromDataByKey(roomTypeJson, "isHall");
					if (roomType == RoomTypeEnum.HALL_TYPE.getCode()) {
						upRentWay = RentWayEnum.HALL.getCode();
					}
				}
			}
			//可以用共享客厅的最低版本号
			Integer limitVersionCode=100025;
			//只有当开关校验,版本校验,合同校验都通过时,才会有共享客厅入口
			if (isOpenHall == YesOrNoEnum.NO.getCode() || header.getVersionCode() <= limitVersionCode) {
				if(upRentWay!=RentWayEnum.HALL.getCode()){
					rentWayMap.remove(RentWayEnum.HALL.getCode());
				}
			} else {
				String uid = getUserId(request);
				Integer isValidContract = this.validContract(uid);
				if (isValidContract==YesOrNoEnum.NO.getCode()) {
					if(upRentWay!=RentWayEnum.HALL.getCode()){
						rentWayMap.remove(RentWayEnum.HALL.getCode());
					}
				}
			}
			LogUtil.info(LOGGER,"出租方式集合:{}", rentWayMap);

			for(int rw:rentWayMap.keySet()){
				if(!Check.NuNObj(housePhyExtVo.getHouseBaseMsgEntity())){
					if(upRentWay==rw){
						rentWay.getList().add(new FieldSelectVo<Integer>(rw, RentWayEnum.getRentWayByCode(rw).getAppRentWayName(), RentWayEnum.getRentWayByCode(rw).getRentWayDesc(), true));
					}else if(HouseStatusEnum.DFB.getCode()==houseStatus&&bizNum==0){
						if(isOpenNew==0){
							rentWay.setIsEdit(false);
						} else {
							rentWay.setIsEdit(true);
						}
						rentWay.getList().add(new FieldSelectVo<Integer>(rw, RentWayEnum.getRentWayByCode(rw).getAppRentWayName(), RentWayEnum.getRentWayByCode(rw).getRentWayDesc(), false));
					}
				} else {
					rentWay.getList().add(new FieldSelectVo<Integer>(rw, RentWayEnum.getRentWayByCode(rw).getAppRentWayName(), RentWayEnum.getRentWayByCode(rw).getRentWayDesc(), false));
				}
			}
			vo.setHouseRentWay(rentWay);
			//赋值房源类型
			FieldSelectListVo houseType= new FieldSelectListVo();
			String houseTypeJson=cityTemplateService.getEffectiveSelectEnum(null, ProductRulesEnum.ProductRulesEnum001.getValue());
			List<EnumVo> houseTypeList=SOAResParseUtil.getListValueFromDataByKey(houseTypeJson, "selectEnum", EnumVo.class);
			if(!Check.NuNCollection(houseTypeList)){
				for(EnumVo type:houseTypeList){
					if(!Check.NuNObj(housePhyExtVo.getHouseBaseMsgEntity())&&type.getKey().equals(housePhyExtVo.getHouseBaseMsgEntity().getHouseType()+"")){
						houseType.getList().add(new FieldSelectVo<Integer>(Integer.valueOf(type.getKey()), type.getText(), true));
					} else {
						houseType.getList().add(new FieldSelectVo<Integer>(Integer.valueOf(type.getKey()), type.getText(), false));
					}
				}
			}
			//判断房源类型是否能修改（审核中和已上架不能改房源类型）
			if(HouseStatusEnum.YFB.getCode()==houseStatus||HouseStatusEnum.SJ.getCode()==houseStatus){
				houseType.setIsEdit(false);
			}
			vo.setHouseType(houseType);
			//城市区域信息赋值
			if(!Check.NuNObj(housePhyExtVo.getHousePhyMsgEntity())){
				StringBuilder codeSb=new StringBuilder(housePhyExtVo.getHousePhyMsgEntity().getNationCode());
				codeSb.append(",").append(housePhyExtVo.getHousePhyMsgEntity().getProvinceCode()).append(",").append(housePhyExtVo.getHousePhyMsgEntity().getCityCode()).append(",")
                        .append(housePhyExtVo.getHousePhyMsgEntity().getAreaCode());
                List<String> codeList=Arrays.asList(codeSb.toString().split(","));
				codeList=new ArrayList<String>(codeList);
				//直辖市集合查询
				String  municipalitiesJson= zkSysService.getZkSysValue(EnumMinsuConfig.minsu_Municipalities.getType(), EnumMinsuConfig.minsu_Municipalities.getCode());
				List<String> municipalitieList=Arrays.asList(municipalitiesJson.split(","));
				//处理国外和直辖市
				if(!codeList.get(0).equals("100000")){
					codeList.remove(codeList.get(1));
				} else if(municipalitieList.contains(codeList.get(1))) {
					codeList.remove(codeList.get(2));
				}
				String codeNameJson=confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeList));
				LogUtil.info(LOGGER, "地址code查询名称列表:{}", codeNameJson);
				List<ConfCityEntity> confCityList=SOAResParseUtil.getListValueFromDataByKey(codeNameJson, "cityList", ConfCityEntity.class);
				List<String> nameList=new ArrayList<String>();
				for(String code:codeList){
					for(ConfCityEntity city:confCityList){
						if(city.getCode().equals(code)){
							nameList.add(city.getShowName());
							break;
						}
					}
				}
				LogUtil.info(LOGGER, "城市名称列表:{}", JsonEntityTransform.Object2Json(nameList));
				//判断位置信息是否能修改（审核中和已上架不能改位置信息）
				if(HouseStatusEnum.YFB.getCode()==houseStatus||HouseStatusEnum.SJ.getCode()==houseStatus){
					vo.setRegionMsg(new FieldTextValueVo<String>(String.join(",", codeList), String.join(",", nameList), false));
				} else {
					vo.setRegionMsg(new FieldTextValueVo<String>(String.join(",", codeList), String.join(",", nameList), true));
				}
				//小区名称赋值
				vo.setCommunityName(new FieldTextVo<String>(HouseStatusEnum.SJ.getCode()==houseStatus?false:true, housePhyExtVo.getHousePhyMsgEntity().getCommunityName()));
				//百度经纬度赋值
				vo.setBaiduLocation(new FieldTextVo<String>(true, housePhyExtVo.getHousePhyMsgEntity().getLongitude()+","+housePhyExtVo.getHousePhyMsgEntity().getLatitude()));
				//谷歌经纬度赋值
				vo.setGoogleLocation(new FieldTextVo<String>(true, housePhyExtVo.getHousePhyMsgEntity().getGoogleLongitude()+","+housePhyExtVo.getHousePhyMsgEntity().getLatitude()));
			}
			//赋值街道和门牌号信息
			if(!Check.NuNObj(housePhyExtVo.getHouseBaseExtEntity())){
				vo.setHouseStreet(new FieldTextVo<String>(HouseStatusEnum.SJ.getCode()==houseStatus?false:true, housePhyExtVo.getHouseBaseExtEntity().getHouseStreet()));
				vo.setHouseNumber(new FieldTextVo<String>(HouseStatusEnum.SJ.getCode()==houseStatus?false:true, housePhyExtVo.getHouseBaseExtEntity().getDetailAddress()));
			}
			/**
			 * yanb 共享客厅第一步文案
			 */
			String hallContent = staticResourceService.findStaticResourceByCode("HALL_CONTENT");
			StaticResourceVo staticResourceVo = SOAResParseUtil.getValueFromDataByKey(hallContent, "StaticResourceEntity", StaticResourceVo.class);
			if (!Check.NuNObj(staticResourceVo)) {
				vo.setHallContent(staticResourceVo.getResContent());
			}

			LogUtil.info(LOGGER, "初始化房源类型位置信息返回值:{}", JsonEntityTransform.Object2Json(vo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(vo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "初始化房源类型位置信息错误，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
     *
     * 类型和位置保存（发布房源第一步1-1和1-2）
     *
	 * @author bushujie
	 * @created 2017年6月20日 上午11:40:37
	 *
	 * @param request
	 * @return
     * @throws SOAParseException
     */
    @RequestMapping(value = "/${LOGIN_AUTH}/saveTypeLocation")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveTypeLocation(HttpServletRequest request) {
		try {
			//获取房东uid
			String uid = getUserId(request);
			Header header=getHeader(request);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空："+ConstDef.DECRYPT_PARAM_ATTRIBUTE), HttpStatus.OK);
			}
			LogUtil.info(LOGGER, "[saveTypeLocation]参数：" + paramJson);
			HouseTypeLocationDto paramDto = JsonEntityTransform.json2Object(paramJson, HouseTypeLocationDto.class);
			//参数验证
			if(Check.NuNObjs(paramDto.getRentWay(),paramDto.getHouseType(),paramDto.getRegionCode(),paramDto.getHouseStreet())){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式、房源类型和区域不能为空"), HttpStatus.OK);
            }
            if(paramDto.getHouseStreet().length()>50){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("街道超出50字："+paramDto.getHouseStreet()), HttpStatus.OK);
			}
			if(!Check.NuNStr(paramDto.getCommunityName())&&paramDto.getCommunityName().length()>50){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("小区名称超出50字："+paramDto.getHouseStreet()), HttpStatus.OK);
			}
			if(!Check.NuNStr(paramDto.getHouseNumber())&&paramDto.getHouseNumber().length()>50){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("单元、楼号、楼层、门牌号超出50字："+paramDto.getHouseStreet()), HttpStatus.OK);
			}
			//如果是合租，判断是否有其他房间在上架状态
			if(paramDto.getRentWay()==RentWayEnum.ROOM.getCode()){
				boolean yesNo=false;
				String roomJson= houseIssueService.searchRoomListByHouseBaseFid(paramDto.getHouseBaseFid());
				List<HouseRoomMsgEntity> roomList=SOAResParseUtil.getListValueFromDataByKey(roomJson, "list", HouseRoomMsgEntity.class);
				if(!Check.NuNCollection(roomList)){
					for(HouseRoomMsgEntity room:roomList){
                        if (HouseStatusEnum.SJ.getCode() == room.getRoomStatus() || HouseStatusEnum.YFB.getCode() == room.getRoomStatus()) {
                            yesNo = true;
                            break;
						}
					}
				}
				if(yesNo){
                    String errorMsg = "此套房源下存在已上架或审核中房间，请下架或取消发布后修改";
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(errorMsg), HttpStatus.OK);
                }
			}
			//直辖市集合查询
			String  municipalitiesJson= zkSysService.getZkSysValue(EnumMinsuConfig.minsu_Municipalities.getType(), EnumMinsuConfig.minsu_Municipalities.getCode());
			List<String> municipalitieList=Arrays.asList(municipalitiesJson.split(","));
			//判断如果是国外房源处理下regionCode
			if(!paramDto.getRegionCode().split(",")[0].equals("100000")){
				StringBuilder regionCode=new StringBuilder(paramDto.getRegionCode().split(",")[0]);
				regionCode.append(",");
				//查询城市对象
				String cityJson=confCityService.getConfCityByCode(paramDto.getRegionCode().split(",")[1]);
				ConfCityEntity cityEntity=SOAResParseUtil.getValueFromDataByKey(cityJson, "cityEntity", ConfCityEntity.class);
				regionCode.append(cityEntity.getPcode()).append(",").append(cityEntity.getCode()).append(",").append(" ");
				paramDto.setRegionCode(regionCode.toString());
			} else {
				if(municipalitieList.contains(paramDto.getRegionCode().split(",")[1])){
					StringBuilder regionCode=new StringBuilder(paramDto.getRegionCode().split(",")[0]);
					regionCode.append(",").append(paramDto.getRegionCode().split(",")[1]).append(",");
					String cityJson=confCityService.getConfCityByCode(paramDto.getRegionCode().split(",")[2]);
					ConfCityEntity cityEntity=SOAResParseUtil.getValueFromDataByKey(cityJson, "cityEntity", ConfCityEntity.class);
					regionCode.append(cityEntity.getPcode()).append(",").append(cityEntity.getCode());
					paramDto.setRegionCode(regionCode.toString());
				}
			}
			paramDto.setLandlordUid(uid);
			//判断来源
			if(header.getOsType().equals("1")){
				paramDto.setHouseSource(3);
			} else if(header.getOsType().equals("2")) {
				paramDto.setHouseSource(2);
			}

			//保存修改记录 --- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(paramDto.getHouseBaseFid(),null, paramDto.getRentWay());

			//查询运营专员信息
			paramDto.setHouseGuardRel(houseService.setHouseGuardRel(paramDto.getRegionCode(), uid));

			LogUtil.info(LOGGER,"查询的运营管家新：{}",JsonEntityTransform.Object2Json(paramDto.getHouseGuardRel()));

			//保存或修改房源的类型和位置信息
			/**
			 * 调用方法的业务有修改
			 * @author yanb
			 * @created 2017年11月21日 22:05:37
			 */
			String resultJson=houseIssueAppService.saveHousePhyAndExt(JsonEntityTransform.Object2Json(paramDto));
			LogUtil.info(LOGGER,"saveTypeLocation 保存返回={}",resultJson);

			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (resultDto.getCode() == DataTransferObject.ERROR){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
			}
			//保存修改记录 --- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setHouseFid(paramDto.getHouseBaseFid());
				houseUpdateHistoryLogDto.setRentWay(paramDto.getRentWay());
				houseUpdateLogService.saveHistoryLog(paramDto,houseUpdateHistoryLogDto);
			}
			HouseBaseVo houseBaseVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBaseVo", HouseBaseVo.class);
			LogUtil.info(LOGGER, "保存房源类型位置信息返回值:{}", JsonEntityTransform.Object2Json(houseBaseVo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseBaseVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "保存房源类型位置信息错误，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}




	/**
	 * 房源描述初始化（发布房源第二步2-1）
     *
     *
     * @author bushujie
	 * @created 2017年6月21日 上午11:26:41
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initHouseDesc")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initHouseDesc(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initHouseDesc]参数：" + paramJson);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空："+ConstDef.DECRYPT_PARAM_ATTRIBUTE), HttpStatus.OK);
			}
			HouseBaseVo houseBaseVo=JsonEntityTransform.json2Object(paramJson, HouseBaseVo.class);
			//判断housebaseFid是否为空
			if(Check.NuNObj(houseBaseVo.getHouseBaseFid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源fid不能为空"), HttpStatus.OK);
			}
			String resultJson=houseIssueService.findHouseInputDetail(houseBaseVo.getHouseBaseFid());
			HouseBaseDetailVo houseBaseDetailVo=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseDetail", HouseBaseDetailVo.class);
			//查询房源状态
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			//组装返回信息
			HouseDescVo vo=new HouseDescVo();
			if(houseBaseVo.getRentWay()==RentWayEnum.HOUSE.getCode()){
				String houseNameTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSENAME_TEXT");
				StaticResourceVo houseNameText=SOAResParseUtil.getValueFromDataByKey(houseNameTextJson, "res", StaticResourceVo.class);
				if(!Check.NuNObj(houseBaseDetailVo.getHouseStatus())){
					houseStatus=houseBaseDetailVo.getHouseStatus();
				}
				if(HouseStatusEnum.YFB.getCode()==houseStatus){
					vo.setHouseName(new FieldTextValueVo<String>(houseBaseDetailVo.getHouseName(), houseNameText.getResContent(),false, ""));
				} else {
					vo.setHouseName(new FieldTextValueVo<String>(houseBaseDetailVo.getHouseName(), houseNameText.getResContent(),true, ""));
				}
			}else if(houseBaseVo.getRentWay()==RentWayEnum.ROOM.getCode()&&!Check.NuNStr(houseBaseVo.getRoomFid())){
				String roomJson=houseIssueService.searchHouseRoomMsgByFid(houseBaseVo.getRoomFid());
				HouseRoomMsgEntity roomMsgEntity=SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				if(Check.NuNObj(roomMsgEntity)){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
				}
				houseStatus=roomMsgEntity.getRoomStatus();
			}
			//查询房源描述配置
			String houseDescTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEDESC_TEXT");
			StaticResourceVo houseDescText=SOAResParseUtil.getValueFromDataByKey(houseDescTextJson, "res", StaticResourceVo.class);
			String houseDescExplainJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEDESC_EXPLAIN");
			StaticResourceVo houseDescExplain=SOAResParseUtil.getValueFromDataByKey(houseDescExplainJson, "res", StaticResourceVo.class);
			if(HouseStatusEnum.YFB.getCode()==houseStatus){
				vo.setHouseDesc(new FieldTextValueVo<String>(houseBaseDetailVo.getHouseDesc(), houseDescText.getResContent(),false, houseDescExplain.getResContent()));
			} else {
				vo.setHouseDesc(new FieldTextValueVo<String>(houseBaseDetailVo.getHouseDesc(), houseDescText.getResContent(),true, houseDescExplain.getResContent()));
			}
			//查询房源周边信息配置
			String houseAroundTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_TEXT");
			StaticResourceVo houseAroundText=SOAResParseUtil.getValueFromDataByKey(houseAroundTextJson, "res", StaticResourceVo.class);
			String houseAroundExplainJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_EXPLAIN");
			StaticResourceVo houseAroundExplain=SOAResParseUtil.getValueFromDataByKey(houseAroundExplainJson, "res", StaticResourceVo.class);
			if(HouseStatusEnum.YFB.getCode()==houseStatus){
				vo.setHouseAroundDesc(new FieldTextValueVo<String>(houseBaseDetailVo.getHouseAroundDesc(), houseAroundText.getResContent(),false, houseAroundExplain.getResContent()));
			} else {
				vo.setHouseAroundDesc(new FieldTextValueVo<String>(houseBaseDetailVo.getHouseAroundDesc(), houseAroundText.getResContent(),true, houseAroundExplain.getResContent()));
			}
			LogUtil.info(LOGGER, "房源描述初始化返回值:{}", JsonEntityTransform.Object2Json(vo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(vo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源描述初始化错误，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
     *
     * 房源描述保存（发布房源第二步2-1）
     *
	 * @author bushujie
	 * @created 2017年6月21日 下午2:33:31
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveHouseDesc")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveHouseDesc(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveHouseDesc]参数：" + paramJson);
			HouseBaseDetailVo houseBaseDetailVo=JsonEntityTransform.json2Object(paramJson, HouseBaseDetailVo.class);
			if(RentWayEnum.HOUSE.getCode()==houseBaseDetailVo.getRentWay()){
				if(Check.NuNStr(houseBaseDetailVo.getHouseName())){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源名称不能为空"), HttpStatus.OK);
				}else if(houseBaseDetailVo.getHouseName().length()<10||houseBaseDetailVo.getHouseName().length()>30){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源名称应为10-30个字符"), HttpStatus.OK);
				}
			}
			if(Check.NuNStr(houseBaseDetailVo.getHouseDesc())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源描述不能为空"), HttpStatus.OK);
			}else if(houseBaseDetailVo.getHouseDesc().length()<100||houseBaseDetailVo.getHouseDesc().length()>1000){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源描述应为100-1000个字符"), HttpStatus.OK);
			}
			if(Check.NuNStr(houseBaseDetailVo.getHouseAroundDesc())){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源周边情况不能为空"), HttpStatus.OK);
            } else if (houseBaseDetailVo.getHouseAroundDesc().length() < 100 || houseBaseDetailVo.getHouseAroundDesc().length() > 1000) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源周边情况应为100-1000个字符"), HttpStatus.OK);
            }
            houseBaseDetailVo.setFid(houseBaseDetailVo.getHouseBaseFid());
			houseIssueAppService.saveHouseDesc(JsonEntityTransform.Object2Json(houseBaseDetailVo));
			HouseBaseVo vo=new HouseBaseVo();
			vo.setHouseBaseFid(houseBaseDetailVo.getFid());
			vo.setRentWay(houseBaseDetailVo.getRentWay());
			LogUtil.info(LOGGER, "房源描述保返回值:{}", JsonEntityTransform.Object2Json(vo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(vo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源描述保存错误，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}

	/**
     *
     * 房源价格初始化（发布房源第二步2-2（整租））
     *
	 * @author bushujie
	 * @created 2017年6月23日 上午10:44:43
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings({"unchecked" })
	@RequestMapping(value = "/${LOGIN_AUTH}/initHousePrice")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initHousePrice(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initHousePrice]参数：" + paramJson);
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空"), HttpStatus.OK);
			}
			Map<String, String> paramMap=(Map<String, String>) JsonEntityTransform.json2Map(paramJson);
			String fid=paramMap.get("houseBaseFid");
			if(Check.NuNStr(fid)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源标识不能为空"), HttpStatus.OK);
			}
			String resultJson=houseIssueAppService.searchHouseConfAndPrice(fid);
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBaseExtDto", HouseBaseExtDto.class);
			HousePriceVo housePriceVo=new HousePriceVo();
			//封装房源价格户型数据
			if(!Check.NuNObj(houseBaseExtDto)){
				housePriceVo.setHouseArea(new FieldTextValueVo<Integer>(houseBaseExtDto.getHouseArea().intValue(), houseBaseExtDto.getHouseArea()+"平米", true));
				//房源户型
				StringBuilder modelText=new StringBuilder();
				StringBuilder modeValue=new StringBuilder();
				modelText.append(houseBaseExtDto.getRoomNum()).append("室 ").append(houseBaseExtDto.getHallNum()).append("厅 ").append(houseBaseExtDto.getToiletNum()).append("卫 ").append(houseBaseExtDto.getKitchenNum())
                        .append("厨 ").append(houseBaseExtDto.getBalconyNum()).append("阳台");
                modeValue.append(houseBaseExtDto.getRoomNum()).append(",").append(houseBaseExtDto.getHallNum()).append(",").append(houseBaseExtDto.getToiletNum()).append(",").append(houseBaseExtDto.getKitchenNum())
                        .append(",").append(houseBaseExtDto.getBalconyNum());
                housePriceVo.setHouseModel(new FieldTextValueVo<String>(modeValue.toString(), modelText.toString(), true));
				housePriceVo.setRoomNum(houseBaseExtDto.getRoomNum());
				housePriceVo.setParlorNum(houseBaseExtDto.getHallNum());
				housePriceVo.setToiletNum(houseBaseExtDto.getToiletNum());
				housePriceVo.setKitchenNum(houseBaseExtDto.getKitchenNum());
				housePriceVo.setBalconyNum(houseBaseExtDto.getBalconyNum());
			}
			//查询入住人数限制
			String checkInLimitJson=cityTemplateService.getEffectiveSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
			List<EnumVo> list=SOAResParseUtil.getListValueFromDataByKey(checkInLimitJson, "selectEnum", EnumVo.class);
			for(EnumVo vo:list){
				if(!Check.NuNObjs(houseBaseExtDto,houseBaseExtDto.getHouseBaseExt())&&vo.getKey().equals(houseBaseExtDto.getHouseBaseExt().getCheckInLimit()+"")){
					housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(Integer.valueOf(vo.getKey()), vo.getText(), true));
				} else {
					housePriceVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(Integer.valueOf(vo.getKey()), vo.getText(), false));
				}
			}
			//初始化便利设施
			Map<String, Object> resultMap=new HashMap<String,Object>();
            initFacilities(fid, null, resultMap);
            housePriceVo.setHouseFacility((FieldTextValueVo<String>) resultMap.get("houseFacility"));
            housePriceVo.setGroupFacilityList((List) resultMap.get("houseFacilityGroup"));
			/**价格初始化开始**/
			Map<String, Object>  priceMap=new HashMap<String,Object>();
            houseService.initSetPrice(priceMap, houseBaseExtDto.getLeasePrice(), houseBaseExtDto.getHouseCleaningFees(), houseBaseExtDto.getFid(), null, houseBaseExtDto.getRentWay(), null);
            if (!Check.NuNObj(priceMap.get("leasePrice"))) {
                housePriceVo.setLeasePrice((FieldTextVo<Integer>) priceMap.get("leasePrice"));
			}
			housePriceVo.setMinPrice((Integer) priceMap.get("minPrice"));
			housePriceVo.setMaxPrice((Integer) priceMap.get("maxPrice"));
			if(!Check.NuNObj(priceMap.get("cleaningFees"))){
				housePriceVo.setCleaningFees((FieldTextVo<Integer>) priceMap.get("cleaningFees"));
			}
			housePriceVo.setCleaningFeesPer((Integer) priceMap.get("cleaningFeesPer"));
			housePriceVo.setWeekendList((FieldSelectListVo) priceMap.get("weekendList"));
			housePriceVo.setWeekendPriceSwitch((Integer) priceMap.get("weekendPriceSwitch"));
			if(!Check.NuNObj(priceMap.get("weekendPrice"))){
				housePriceVo.setWeekendPrice((FieldTextVo<Integer>) priceMap.get("weekendPrice"));
			}
			housePriceVo.setFullDayRateSwitch((Integer) priceMap.get("fullDayRateSwitch"));
			if(!Check.NuNObj(priceMap.get("sevenDiscountRate"))){
				housePriceVo.setSevenDiscountRate((FieldTextVo<Double>) priceMap.get("sevenDiscountRate"));
			}
			if(!Check.NuNObj(priceMap.get("thirtyDiscountRate"))){
				housePriceVo.setThirtyDiscountRate((FieldTextVo<Double>) priceMap.get("thirtyDiscountRate"));
			}
			/**价格初始化结束**/
			//床类型列表
			String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
			List<EnumVo> bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			for(EnumVo vo:bedTypeList){
				housePriceVo.getBedTypeList().getList().add(new FieldSelectVo<String>(vo.getKey(), vo.getText(),getBedTypeDes(Integer.valueOf(vo.getKey())), false));
			}
			//房源房间初始化
			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseExtDto.getFid());
			List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list",HouseRoomMsgEntity.class);
			for(HouseRoomMsgEntity room:roomList){
				Map<String, Object> roomMap=new HashMap<>();
				roomMap.put("roomFid", room.getFid());
				String bedListJson = houseIssueService.searchBedListByRoomFid(room.getFid());
				List<HouseBedMsgEntity> bedMsgList = SOAResParseUtil.getListValueFromDataByKey(bedListJson, "list", HouseBedMsgEntity.class);
				List<FieldTextValueVo<Integer>> bedList=new ArrayList<>();
				for(EnumVo vo:bedTypeList){
					Integer typeNum=0;
					for(HouseBedMsgEntity bed:bedMsgList){
						if(vo.getKey().equals(bed.getBedType()+"")){
							typeNum++;
						}
					}
					if(typeNum>0){
						FieldTextValueVo<Integer> bed=new FieldTextValueVo<Integer>(typeNum, vo.getKey(), true);
						bedList.add(bed);
					}
				}
				roomMap.put("bedList", bedList);
				housePriceVo.getHouseRoomList().add(roomMap);
			}
			// 房源户型限制信息
			Map<String, Integer> houseLimitMap = new HashMap<>();
			String houseLimitJson = cityTemplateService.getTextListByLikeCodes(null,ProductRulesEnum.ProductRulesEnum0027.getValue());
			List<MinsuEleEntity> houseLimitList = SOAResParseUtil.getListValueFromDataByKey(houseLimitJson, "confList",MinsuEleEntity.class);

			if (!Check.NuNCollection(houseLimitList)) {
				for (MinsuEleEntity minsuEleEntity : houseLimitList) {
					houseLimitMap.put(minsuEleEntity.getEleKey(), ValueUtil.getintValue(minsuEleEntity.getEleValue()));
				}
			}
			housePriceVo.setMaxRoom(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()));
			housePriceVo.setMaxParlor(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()));
			housePriceVo.setMaxToilet(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()));
			housePriceVo.setMaxKitchen(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()));
			housePriceVo.setMaxBalcony(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()));
			housePriceVo.setHouseBaseFid(houseBaseExtDto.getFid());
			housePriceVo.setRentWay(houseBaseExtDto.getRentWay());
			String bedLimitJson = cityTemplateService.getTextValue(null,ProductRulesEnum.ProductRulesEnum0028.getValue());
			String bedLimitStr = SOAResParseUtil.getStrFromDataByKey(bedLimitJson, "textValue");
			if (!Check.NuNStr(bedLimitStr)) {
				housePriceVo.setMaxBedNumLimit(Integer.valueOf(bedLimitStr));
			}
			LogUtil.info(LOGGER, "房源价格初始化返回值:{}", JsonEntityTransform.Object2Json(housePriceVo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(housePriceVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源价格初始化错误，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}
	/**
	 *
	 * 房源价格保存
	 * 发布房源第二步2-2整租
	 *
	 * @author bushujie
	 * @created 2017年6月27日 上午10:13:31
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveHousePrice")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveHousePrice(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveHousePrice]参数：" + paramJson);
			if (Check.NuNObj(paramJson)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空"), HttpStatus.OK);
			}
			/**判断参数类型是否正确**/
			if(BaseMethodUtil.isClassByJsonKey(paramJson, "leasePrice", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("基础价格不合法"), HttpStatus.OK);
			}
			if(BaseMethodUtil.isClassByJsonKey(paramJson, "houseArea", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("面积不合法"), HttpStatus.OK);
			}
			if(BaseMethodUtil.isContainKey(paramJson, "cleaningFees")&&BaseMethodUtil.isClassByJsonKey(paramJson, "cleaningFees", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务费不合法"), HttpStatus.OK);
			}
			if(BaseMethodUtil.isContainKey(paramJson, "weekendPriceVal")&&BaseMethodUtil.isClassByJsonKey(paramJson, "weekendPriceVal", String.class)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("周末价格不合法"), HttpStatus.OK);
			}
			/**判断参数类型是否正确**/
			HousePriceDto housePriceDto = JsonEntityTransform.json2Object(paramJson, HousePriceDto.class);
			if (Check.NuNStr(housePriceDto.getHouseBaseFid())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源fid不能为空"), HttpStatus.OK);
			}
			if (Check.NuNObj(housePriceDto.getRentWay())) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租类型不能为空"), HttpStatus.OK);
			}

			Integer fullDayRateSwitch = housePriceDto.getFullDayRateSwitch();
			//长租折扣开关打开，校验折扣格式
			if (!Check.NuNObj(fullDayRateSwitch) && fullDayRateSwitch == YesOrNoEnum.YES.getCode()) {
				Double thirtyDiscountRate = housePriceDto.getThirtyDiscountRate();
				Double sevenDiscountRate = housePriceDto.getSevenDiscountRate();
				if (Check.NuNObjs(thirtyDiscountRate, sevenDiscountRate) ) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("入住满30天折扣, 入住满7天折扣不能同时为空"), HttpStatus.OK);
				}
				if (!Check.NuNObj(thirtyDiscountRate) && !thirtyDiscountRate.toString().matches("\\d{1}\\.\\d{1}")) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("入住满30天折扣格式不正确"), HttpStatus.OK);
				}
				if (!Check.NuNObj(sevenDiscountRate) && !sevenDiscountRate.toString().matches("\\d{1}\\.\\d{1}")) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("入住满7天折扣格式不正确"), HttpStatus.OK);
				}
			}

			//校验周末价格
			Integer weekendPriceSwitch = housePriceDto.getWeekendPriceSwitch();
			if (!Check.NuNObj(weekendPriceSwitch) && weekendPriceSwitch == YesOrNoEnum.YES.getCode()) {
				Double weekendPriceVal = housePriceDto.getWeekendPriceVal();
				if (Check.NuNObj(weekendPriceVal) ) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写基础价格"), HttpStatus.OK);
				}
				if (StringUtils.isNumeric(weekendPriceVal.toString())) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("周末价格格式只能为数字"), HttpStatus.OK);
				}
				//房源价格限制
				String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
				Integer priceLow = SOAResParseUtil.getIntFromDataByKey(priceLowJson, "textValue");

				String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
				Integer priceHigh = SOAResParseUtil.getIntFromDataByKey(priceHighJson, "textValue");

				if (!Check.NuNObj(priceLow) && weekendPriceVal.intValue() < priceLow.intValue()) {
					String msg = "每晚价格不能低于"+ priceLow.intValue() +"元";
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(msg), HttpStatus.OK);
				}

				if (!Check.NuNObj(priceHigh) && weekendPriceVal.intValue() > priceHigh.intValue()) {
					String msg = "每晚价格不能高于"+ priceHigh.intValue() +"元";
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(msg), HttpStatus.OK);
				}
				String weekendPriceType = housePriceDto.getWeekendPriceType();
				if (!WeekendPriceEnum.FRI_SAT.getValue().equals(weekendPriceType)
						&& !WeekendPriceEnum.SAT_SUN.getValue().equals(weekendPriceType)
						&& !WeekendPriceEnum.FRI_SAT_SUN.getValue().equals(weekendPriceType)) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("周末价格类型不正确"), HttpStatus.OK);
				}
			}
			//获取房东uid
			String uid = getUserId(request);
			housePriceDto.setCreateFid(uid);
			housePriceDto.setStep(HouseIssueStepEnum.THREE.getCode());
			/**判断床数量**/
			Integer bedNum=0;
			if(!Check.NuNCollection(housePriceDto.getHouseRoomList())){
				for(HouseRoomVo vo:housePriceDto.getHouseRoomList()){
					if(!Check.NuNStr(vo.getBedMsg())){
						for(String bed:vo.getBedMsg().split(",")){
							Integer num=Integer.valueOf(bed.split("_")[1]);
							bedNum+=num;
						}
					}
				}
			}
			if(bedNum==0){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间内至少有一张床"), HttpStatus.OK);
			}
			/**判断床数量**/
			String resultJson=houseIssueAppService.saveHousePrice(JsonEntityTransform.Object2Json(housePriceDto));
			LogUtil.info(LOGGER,"saveHousePrice 保存返回结果={}",resultJson);
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(resultJson);

			if(dto.getCode()==1){
				LogUtil.error(LOGGER, "整租发布房源第二步服务错误");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
			}
			HouseBaseVo houseBaseVo=new HouseBaseVo();
			houseBaseVo.setHouseBaseFid(housePriceDto.getHouseBaseFid());
			houseBaseVo.setRentWay(housePriceDto.getRentWay());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseBaseVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "，saveHousePrice 整租发布房源第二步 error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}
	/**
     *
     * 初始化国家地区
     *
	 * @author zl
	 * @created 2017年6月15日 下午2:23:14
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initCityTree")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initCityTree(HttpServletRequest request) {

		try {
			String resultJson = confCityService.getConfCitySelectForLandlord();
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			List<TreeNodeVo> cityTreeList = resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {});
			//LogUtil.info(LOGGER, "房东开通城市：{}", resultJson);
			List<TreeNodeSimpleVo> resultList = new ArrayList<TreeNodeSimpleVo>();

			//递归重建城市树结构
			if(!Check.NuNCollection(cityTreeList)){
				for (TreeNodeVo countryVo : cityTreeList) {
					TreeNodeSimpleVo country = new TreeNodeSimpleVo();
					loadCityTree(country,countryVo);
					resultList.add(country);
				}
			}
			String  municipalitiesJson= zkSysService.getZkSysValue(EnumMinsuConfig.minsu_Municipalities.getType(), EnumMinsuConfig.minsu_Municipalities.getCode());
			List<String> municipalitieList=Arrays.asList(municipalitiesJson.split(","));
			//处理国外树结构
			//LogUtil.info(LOGGER, "房东开通城市：{}", JsonEntityTransform.Object2Json(resultList));
			if(!Check.NuNCollection(resultList)){
				for (TreeNodeSimpleVo vo:resultList) {
					if(!vo.getCode().equals("100000")){
						List<TreeNodeSimpleVo> leafList=new ArrayList<TreeNodeSimpleVo>();
						if(!Check.NuNCollection(vo.getNodes())){
							searchLeafNode(vo, leafList);
						}
						vo.setNodes(leafList);
					}
					//直辖市处理
					if(!Check.NuNCollection(vo.getNodes())){
						for(TreeNodeSimpleVo city:vo.getNodes()){
							if(municipalitieList.contains(city.getCode())){
								List<TreeNodeSimpleVo> leafList=new ArrayList<TreeNodeSimpleVo>();
								searchLeafNode(city, leafList);
								city.setNodes(leafList);
							}
						}
					}
				}
			}
			//LogUtil.info(LOGGER, "房东开通城市：{}", JsonEntityTransform.Object2Json(resultList));
            Map<String, Object> result = new HashMap<>();
            result.put("cityTreeList", resultList);

			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "国家地区异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
     *
     * 递归重建城市树结构
     *
	 * @author bushujie
	 * @created 2017年6月20日 上午10:47:52
	 *
	 * @param country
	 * @param countryVo
	 */
	private void loadCityTree(TreeNodeSimpleVo country,TreeNodeVo countryVo){
		country.setCode(countryVo.getCode());
		country.setLevel(countryVo.getLevel());
		country.setText(countryVo.getText());
		if(!Check.NuNCollection(countryVo.getNodes())){
			List<TreeNodeSimpleVo> resultList = new ArrayList<TreeNodeSimpleVo>();
			for(TreeNodeVo vo:countryVo.getNodes()){
				TreeNodeSimpleVo cy=new TreeNodeSimpleVo();
				loadCityTree(cy, vo);
				resultList.add(cy);
			}
			country.setNodes(resultList);
		}
	}

	/**
     *
     * 找到叶子节点
     *
	 * @author bushujie
	 * @created 2017年6月20日 上午11:04:54
	 *
	 * @param vo
	 * @param leafList
	 */
	private void searchLeafNode(TreeNodeSimpleVo vo,List<TreeNodeSimpleVo> leafList){
		if(!Check.NuNCollection(vo.getNodes())){
			for (TreeNodeSimpleVo v:vo.getNodes()) {
				searchLeafNode(v, leafList);
			}
		} else {
			leafList.add(vo);
		}
	}



	/**
     *
     * 2-2 分租房源基础信息初始化
     *
	 * @author zl
	 * @created 2017年6月16日 上午10:17:17
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initRoomModel")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initRoomModel(HttpServletRequest request) {

		try {
			Header header = getHeader(request);
			String uid = getUserId(request);

            Map<String, Object> result = new HashMap<>();

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initRoomModel]uid={},参数={}", uid, paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
            HouseBaseParamsDto requestDto = validateResult.getResultObj();

			HouseBaseExtDto houseBaseMsgEntity = null;
			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(requestDto.getHouseBaseFid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
			}

			if (Check.NuNObj(houseBaseMsgEntity)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
			}

            Map<String, Boolean> notEditFiled = new HashMap<>();
            String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(requestDto.getHouseBaseFid());
            dto = JsonEntityTransform.json2DataTransferObject(roomListJson);
            List<HouseRoomMsgEntity> roomList = null;
            if (dto.getCode() == DataTransferObject.SUCCESS) {
                roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list", HouseRoomMsgEntity.class);
            }
            if (!Check.NuNCollection(roomList)) {
                for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
                    if (houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.YFB.getCode()) {
                        notEditFiled.put("all", false);
                        result.put("immutableMsg", "此套房源下存在审核中房间，请取消发布后修改");
                        break;
                    }
                    if (houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()) {
                        notEditFiled.put("houseModel", false);
                        result.put("immutableMsg", "此套房源下存在已上架房间，请下架后修改");
                        break;
                    }

                }
            }
			FieldSelectListVo livewithLandList = new FieldSelectListVo();
			FieldSelectVo<Integer> livewithLandYES = new FieldSelectVo<Integer>(YesOrNoEnum.YES.getCode(),
					YesOrNoEnum.YES.getName(), false);
			FieldSelectVo<Integer> livewithLandNO = new FieldSelectVo<Integer>(YesOrNoEnum.NO.getCode(),
					YesOrNoEnum.NO.getName(), false);

			if (!Check.NuNObj(houseBaseMsgEntity) && !Check.NuNObj(houseBaseMsgEntity.getHouseBaseExt())
					&& !Check.NuNObj(houseBaseMsgEntity.getHouseBaseExt().getIsTogetherLandlord())) {
				if (houseBaseMsgEntity.getHouseBaseExt().getIsTogetherLandlord() == YesOrNoEnum.YES.getCode()) {
					livewithLandYES.setIsSelect(true);
				} else if (houseBaseMsgEntity.getHouseBaseExt().getIsTogetherLandlord() == YesOrNoEnum.NO.getCode()) {
					livewithLandNO.setIsSelect(true);
				}

			}
			livewithLandList.getList().add(livewithLandYES);
			livewithLandList.getList().add(livewithLandNO);
			livewithLandList.setIsEdit(true);
            Boolean livewithLandEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("isTogetherLandlordList");
            if (!Check.NuNObj(livewithLandEdit)) {
                livewithLandList.setIsEdit(livewithLandEdit);
            }
            result.put("isTogetherLandlordList", livewithLandList);

			//初始化户型信息
            initHouseLimit(houseBaseMsgEntity, notEditFiled, result);

			// 便利设施
            initFacilities(requestDto.getHouseBaseFid(), notEditFiled, result);

			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "分租房源基础信息初始化异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
     *
     * 初始化房源户型信息
     *
	 * @author zl
	 * @created 2017年6月20日 上午11:28:41
	 *
	 * @param houseBaseMsgEntity
     * @param notEditFiled
     * @param resultMap
     */
    private void initHouseLimit(HouseBaseExtDto houseBaseMsgEntity, Map<String, Boolean> notEditFiled, Map<String, Object> resultMap) {

		if (Check.NuNObj(resultMap)) {
			return;
		}

		try {

			// 房源户型限制信息
			List<MinsuEleEntity> houseLimitList = null;
			Map<String, Integer> houseLimitMap = new HashMap<>();

			// 查房源户型限制信息
			String houseLimitJson = cityTemplateService.getTextListByLikeCodes(null,
					ProductRulesEnum.ProductRulesEnum0027.getValue());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseLimitJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseLimitList = SOAResParseUtil.getListValueFromDataByKey(houseLimitJson, "confList",
						MinsuEleEntity.class);
			}

			if (!Check.NuNCollection(houseLimitList)) {
				for (MinsuEleEntity minsuEleEntity : houseLimitList) {
					houseLimitMap.put(minsuEleEntity.getEleKey(), ValueUtil.getintValue(minsuEleEntity.getEleValue()));
				}
			}

			StringBuilder houseModelStr = new StringBuilder();
			StringBuilder houseModelValStr = new StringBuilder();

			if (!Check.NuNObj(houseBaseMsgEntity)) {
				houseModelStr.append(houseBaseMsgEntity.getRoomNum()).append("室")
                        .append(houseBaseMsgEntity.getHallNum()).append("厅").append(houseBaseMsgEntity.getToiletNum())
                        .append("卫").append(houseBaseMsgEntity.getKitchenNum()).append("厨")
                        .append(houseBaseMsgEntity.getBalconyNum()).append("阳台");

				houseModelValStr.append(houseBaseMsgEntity.getRoomNum()).append(splist)
                        .append(houseBaseMsgEntity.getHallNum()).append(splist)
                        .append(houseBaseMsgEntity.getToiletNum()).append(splist)
                        .append(houseBaseMsgEntity.getKitchenNum()).append(splist)
                        .append(houseBaseMsgEntity.getBalconyNum());

				resultMap.put("roomNum", houseBaseMsgEntity.getRoomNum());
				resultMap.put("parlorNum", houseBaseMsgEntity.getHallNum());
				resultMap.put("toiletNum", houseBaseMsgEntity.getToiletNum());
				resultMap.put("kitchenNum",houseBaseMsgEntity.getKitchenNum());
				resultMap.put("balconyNum",houseBaseMsgEntity.getBalconyNum());
			}else{
				houseModelStr.append(0).append("室")
                        .append(0).append("厅").append(0)
                        .append("卫").append(0).append("厨")
                        .append(0).append("阳台");

				houseModelValStr.append(0).append(splist)
                        .append(0).append(splist)
                        .append(0).append(splist)
                        .append(0).append(splist)
                        .append(0);

				resultMap.put("roomNum", 0);
				resultMap.put("parlorNum", 0);
				resultMap.put("toiletNum", 0);
				resultMap.put("kitchenNum",0);
				resultMap.put("balconyNum",0);
			}
			FieldTextValueVo<String> houseModelField = new FieldTextValueVo<String>(houseModelValStr.toString(),
					houseModelStr.toString(), true);
            if (!Check.NuNMap(notEditFiled)) {
                Boolean houseModelEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("houseModel");
                if (!Check.NuNObj(houseModelEdit)) {
                    houseModelField.setIsEdit(houseModelEdit);
                }
            }
            resultMap.put("houseModel", houseModelField);

			resultMap.put("maxRoom", houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()));
			resultMap.put("maxParlor", houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()));
			resultMap.put("maxToilet", houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()));
			resultMap.put("maxKitchen", houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()));
			resultMap.put("maxBalcony", houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()));


		} catch (Exception e) {
			LogUtil.error(LOGGER, "初始化房源户型信息异常，error = {}", e);
		}

	}



	/**
     *
     * 初始化便利设施
     *
	 * @author zl
	 * @created 2017年6月19日 下午2:55:00
	 *
	 * @param houseBaseFid
     * @param notEditFiled
     * @param resultMap
     */
    private void initFacilities(String houseBaseFid, Map<String, Boolean> notEditFiled, Map<String, Object> resultMap) {

		if (Check.NuNObjs(houseBaseFid, resultMap)) {
			return;
		}

		try {
			// 房源已选择配套设施
			List<HouseConfVo> houseFacilityList = null;
			// 房源已选择服务
			List<HouseConfVo> houseServeListList = null;

			// 查询房源已选配套设施和服务列表
			String facilityJson = houseIssueService.findHouseFacilityAndService(houseBaseFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(facilityJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseFacilityList = SOAResParseUtil.getListValueFromDataByKey(facilityJson, "facilityList",
						HouseConfVo.class);
				houseServeListList = SOAResParseUtil.getListValueFromDataByKey(facilityJson, "serveList",
						HouseConfVo.class);
			}

			List<HouseConfVo> facilityList = new ArrayList<>();
			if (!Check.NuNCollection(houseFacilityList)) {
				facilityList.addAll(houseFacilityList);
			}
			if (!Check.NuNCollection(houseServeListList)) {
				facilityList.addAll(houseServeListList);
			}

			StringBuilder facilityNames = new StringBuilder();
			StringBuilder facilityValues = new StringBuilder();
			for (HouseConfVo houseConfVo : facilityList) {
				//查询配置名称
				facilityNames.append(houseConfVo.getDicName()).append(splist);
				facilityValues.append(houseConfVo.getDicCode()).append(subSplist).append(houseConfVo.getDicValue())
                        .append(splist);
            }

			// 配套设施初始化列表
			String initfacilityJson = cityTemplateService.getSelectSubDic(null,
					ProductRulesEnum.ProductRulesEnum002.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(initfacilityJson);
			List<EnumVo> initfacilityGroupList = null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				initfacilityGroupList = SOAResParseUtil.getListValueFromDataByKey(initfacilityJson, "subDic",
						EnumVo.class);
			}
			// 服务列表
			String initserviceJson = cityTemplateService.getEffectiveSelectEnum(null,
					ProductRulesEnum.ProductRulesEnum0015.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(initserviceJson);
			List<EnumVo> initserviceList = null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				initserviceList = SOAResParseUtil.getListValueFromDataByKey(initserviceJson, "selectEnum", EnumVo.class);
			}

			List<Map<String, Object>> groupFacilityList = new ArrayList<>();
			if (!Check.NuNCollection(initfacilityGroupList)) {
				for (EnumVo groupEnumVo : initfacilityGroupList) {
					Map<String, Object> groupMap = new HashMap<>();
					groupMap.put("name", groupEnumVo.getText());
					FieldSelectListVo groupFieldList = new FieldSelectListVo();
					if (!Check.NuNCollection(groupEnumVo.getSubEnumVals())) {
						for (EnumVo enumVo : groupEnumVo.getSubEnumVals()) {
							String key = groupEnumVo.getKey() + subSplist + enumVo.getKey();
							FieldSelectVo<String> groupFieldListSelect = new FieldSelectVo<String>(key,
									enumVo.getText(), false);
							if (facilityValues.indexOf(key) >= 0) {
								groupFieldListSelect.setIsSelect(true);
							}
							groupFieldList.getList().add(groupFieldListSelect);
						}
					}
					groupMap.put("facilitiesList", groupFieldList);
					groupFacilityList.add(groupMap);
				}
			}

			Map<String, Object> fuwu = new HashMap<>();
			fuwu.put("name", ProductRulesEnum.ProductRulesEnum0015.getName());
			FieldSelectListVo fuwuFieldList = new FieldSelectListVo();
			if (!Check.NuNCollection(initserviceList)) {
				for (EnumVo enumVo : initserviceList) {
					String key = ProductRulesEnum.ProductRulesEnum0015.getValue() + subSplist + enumVo.getKey();
					FieldSelectVo<String> groupFieldListSelect = new FieldSelectVo<String>(key, enumVo.getText(),
							false);
					if (facilityValues.indexOf(key) >= 0) {
						groupFieldListSelect.setIsSelect(true);
					}
					fuwuFieldList.getList().add(groupFieldListSelect);
				}
			}
			fuwu.put("facilitiesList", fuwuFieldList);
			groupFacilityList.add(fuwu);

			resultMap.put("houseFacilityGroup", groupFacilityList);

			if (facilityNames.length() > 0) {
				facilityNames.delete(facilityNames.length() - 1, facilityNames.length());
			}
			if (facilityValues.length() > 0) {
				facilityValues.delete(facilityValues.length() - 1, facilityValues.length());
			}

			FieldTextValueVo<String> facilityField = new FieldTextValueVo<String>(facilityValues.toString(),
					facilityNames.toString(), true);
            if (!Check.NuNMap(notEditFiled)) {
                Boolean houseFacilityEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("houseFacility");
                if (!Check.NuNObj(houseFacilityEdit)) {
                    facilityField.setIsEdit(houseFacilityEdit);
                }
            }
            resultMap.put("houseFacility", facilityField);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "初始化便利设施异常，houseBaseFid={}，error = {}",houseBaseFid, e);
		}

	}

	/**
     *
     * 发布房源分租第二步2-2保存
     *
	 * @author zl
	 * @created 2017年6月16日 下午5:20:37
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveRoomModel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveRoomModel(HttpServletRequest request) {

		try {
			Header header = getHeader(request);
			String uid = getUserId(request);

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveRoomModel]uid={}，参数={}", uid, paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
            HouseBaseParamsDto requestDto = validateResult.getResultObj();
            Map<String, Object> paramMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);

			Integer isTogetherLandlord = null;
			Integer roomNum =null;
			Integer parlorNum =null;
			Integer toiletNum =null;
			Integer kitchenNum =null;
			Integer balconyNum =null;
			String supportArray =null;
			Integer roomType = null;
			try {
				isTogetherLandlord = (Integer) paramMap.get("isTogetherLandlord");
				roomNum = (Integer) paramMap.get("roomNum");
				parlorNum = (Integer) paramMap.get("parlorNum");
				toiletNum = (Integer) paramMap.get("toiletNum");
				kitchenNum = (Integer) paramMap.get("kitchenNum");
				balconyNum = (Integer) paramMap.get("balconyNum");
                supportArray = (String) paramMap.get("supportArray");
				roomType = (Integer) paramMap.get("roomType");
			} catch (Exception e) {
				LogUtil.error(LOGGER, "参数错误，paramMap={}，error = {}",paramMap, e);
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
			}

			if (Check.NuNObj(isTogetherLandlord)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择是否与房东同住"), HttpStatus.OK);
			}
			if (Check.NuNStr(supportArray)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择便利设施"), HttpStatus.OK);
			}
			if (Check.NuNObjs(roomNum, parlorNum, toiletNum, kitchenNum, balconyNum)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择户型"), HttpStatus.OK);
			}

			HouseBaseExtDto houseBaseMsgEntity = null;
			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(requestDto.getHouseBaseFid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
			}

			if (Check.NuNObj(houseBaseMsgEntity)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
			}

			if(roomNum <houseBaseMsgEntity.getHouseBaseExt().getRentRoomNum()){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间数不能少于可出租房间数"), HttpStatus.OK);
			}

			List<HouseConfMsgEntity> listHouseConfMsg = new ArrayList<HouseConfMsgEntity>();
			String[] conMsgArray = supportArray.split(splist);
			for (String string : conMsgArray) {
				String[] conArray = string.split(subSplist);
				if (conArray.length == 2) {
					HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
					houseConfMsg.setHouseBaseFid(requestDto.getHouseBaseFid());
					houseConfMsg.setDicCode(string.split(subSplist)[0]);
					houseConfMsg.setDicVal(string.split(subSplist)[1]);
					listHouseConfMsg.add(houseConfMsg);
				}

			}
			if (Check.NuNCollection(listHouseConfMsg)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择便利设施"), HttpStatus.OK);
			}

			// 房源户型限制信息
			List<MinsuEleEntity> houseLimitList = null;
			Map<String, Integer> houseLimitMap = new HashMap<>();
			String houseLimitJson = cityTemplateService.getTextListByLikeCodes(null,
					ProductRulesEnum.ProductRulesEnum0027.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(houseLimitJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseLimitList = SOAResParseUtil.getListValueFromDataByKey(houseLimitJson, "confList",
						MinsuEleEntity.class);
			}

			if (!Check.NuNCollection(houseLimitList)) {
				for (MinsuEleEntity minsuEleEntity : houseLimitList) {
					houseLimitMap.put(minsuEleEntity.getEleKey(), ValueUtil.getintValue(minsuEleEntity.getEleValue()));
				}
			}

			if (isTogetherLandlord != YesOrNoEnum.YES.getCode() && isTogetherLandlord != YesOrNoEnum.NO.getCode()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("是否与房东同住选择错误"), HttpStatus.OK);
			}
			/**
			 * yanb 修改增加共享客厅的判断
			 * 共享客厅的户型最小为1
			 */
			if (Check.NuNObj(roomType)) {
				roomType = 0;
			}
			if ((roomType!=RoomTypeEnum.HALL_TYPE.getCode()&&roomNum < 2) || (!Check.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()))
					&& roomNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()))||(roomType==RoomTypeEnum.HALL_TYPE.getCode()&&roomNum < 1)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间数量不正确"), HttpStatus.OK);
			}
			if (parlorNum < 0 || (!Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()))
					&& parlorNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("客厅数量不正确"), HttpStatus.OK);
			}
			if (toiletNum < 1 || (!Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()))
					&& toiletNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("卫生间数量不正确"), HttpStatus.OK);
			}
			if (kitchenNum < 0 || (!Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()))
					&& kitchenNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("厨房数量不正确"), HttpStatus.OK);
			}
			if (balconyNum < 0 || (!Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()))
					&& balconyNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("阳台数量不正确"), HttpStatus.OK);
			}

			houseBaseMsgEntity.setBalconyNum(balconyNum);
			houseBaseMsgEntity.setHallNum(parlorNum);
			houseBaseMsgEntity.setKitchenNum(kitchenNum);
			houseBaseMsgEntity.setRoomNum(roomNum);
			houseBaseMsgEntity.setToiletNum(toiletNum);
			houseBaseMsgEntity.getHouseBaseExt().setIsTogetherLandlord(isTogetherLandlord);
			if(houseBaseMsgEntity.getOperateSeq()<HouseIssueStepEnum.THREE.getCode()){
				houseBaseMsgEntity.setOperateSeq(HouseIssueStepEnum.THREE.getCode());
			}


			HouseAndConfDto houseAndConfDto = new HouseAndConfDto();
			houseAndConfDto.setHouseBaseExtDto(houseBaseMsgEntity);
			houseAndConfDto.setHouseConfMsgList(listHouseConfMsg);

			/**
			 *
			 * @author yanb
			 * @created 2017年11月20日 00:28:58
			 * 修改dto中增加了houseHallVo
			 */
			String resultJson = houseIssueAppService.saveHouseMsgAndConf(JsonEntityTransform.Object2Json(houseAndConfDto));

            LogUtil.info(LOGGER, "[saveRoomModel] result={}", resultJson);
            dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			HouseHallVo houseHallVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseHallVo", HouseHallVo.class);

			if (dto.getCode() == DataTransferObject.SUCCESS) {
				if (houseHallVo != null) {
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseHallVo), HttpStatus.OK);
				}
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(null), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("保存失败，请稍后再试"), HttpStatus.OK);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "[saveRoomModel]异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}


	/**
     *
     * 分租第二步2-3初始化房间列表
     *
	 * @author zl
	 * @created 2017年6月19日 上午10:26:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initRoomList")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initRoomList(HttpServletRequest request) {

		try {
			Header header = getHeader(request);
			String uid = getUserId(request);

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initRoomList]uid={},参数={}",uid, paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseBaseParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();

			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(requestDto.getHouseBaseFid());
			DataTransferObject dto =JsonEntityTransform.json2DataTransferObject(houseJson);
			HouseBaseExtDto houseBaseExt =null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseBaseExt = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
			}
			if(Check.NuNObj(houseBaseExt)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"),	HttpStatus.OK);
			}

			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(requestDto.getHouseBaseFid());
			dto =JsonEntityTransform.json2DataTransferObject(roomListJson);
			List<HouseRoomMsgEntity> roomList =null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list",HouseRoomMsgEntity.class);
			}

            Map<String, Boolean> notEditFiled = new HashMap<>();
            if (!Check.NuNCollection(roomList)) {
                for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
                    if (houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.YFB.getCode()) {
                        notEditFiled.put("all", false);
                        break;
                    }
                    if (houseRoomMsgEntity.getRoomStatus() == HouseStatusEnum.SJ.getCode()) {
                        notEditFiled.put("rentRoomNum", false);
                        notEditFiled.put("houseRoomList", false);
                        break;
                    }

                }
            }



			Map<String, Object> result = new HashMap<>();

			FieldTextVo<Integer> rentRoomNumField = new FieldTextVo<Integer>(true, 0);
			if (!Check.NuNObj(houseBaseExt.getHouseBaseExt())&& !Check.NuNObj(houseBaseExt.getHouseBaseExt().getRentRoomNum()) && houseBaseExt.getHouseBaseExt().getRentRoomNum()>0) {
				rentRoomNumField.setValue(houseBaseExt.getHouseBaseExt().getRentRoomNum());
            }
            if (!Check.NuNCollection(roomList) && roomList.size() > rentRoomNumField.getValue()) {//兼容，防止脏数据
                rentRoomNumField.setValue(roomList.size());
			}
            Boolean rentRoomNumEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("rentRoomNum");
            if (!Check.NuNObj(rentRoomNumEdit)) {
                rentRoomNumField.setIsEdit(rentRoomNumEdit);
            }
            result.put("rentRoomNum", rentRoomNumField);
            result.put("maxRentRoomNum", houseBaseExt.getRoomNum());
			result.put("houseBaseFid", requestDto.getHouseBaseFid());
			result.put("rentWay", requestDto.getRentWay());

			List<FieldTextValueVo<String>> resultRoomList = new ArrayList<>();
			if (!Check.NuNCollection(roomList)) {
                Boolean roomFieldEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("houseRoomList");
                for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
                    FieldTextValueVo<String> roomField = new FieldTextValueVo<String>(houseRoomMsgEntity.getFid(), houseRoomMsgEntity.getRoomName(), true);
                    if (!Check.NuNObj(roomFieldEdit)) {
                        roomField.setIsEdit(roomFieldEdit);
                    }
                    resultRoomList.add(roomField);
                }
            }
            result.put("houseRoomList", resultRoomList);
            LogUtil.info(LOGGER, "[initRoomList] result={}", JsonEntityTransform.Object2Json(result));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询分租房间列表异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
     *
     * 分租第二步2-3单间信息初始化
     *
	 * @author zl
	 * @created 2017年6月19日 上午10:26:13
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initRoomMsg")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initRoomMsg(HttpServletRequest request) {

		try {
			String uid = getUserId(request);

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initRoomMsg]uid={},参数={}",uid, paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseBaseParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();
			DataTransferObject dto =null;
			HouseRoomMsgEntity houseRoomMsg = null;
			if (!Check.NuNStr(requestDto.getRoomFid())) {
				String roomJson = houseIssueService.searchHouseRoomMsgByFid(requestDto.getRoomFid());
				dto =JsonEntityTransform.json2DataTransferObject(roomJson);
				if(dto.getCode()==DataTransferObject.SUCCESS){
					houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
				}else{
					LogUtil.error(LOGGER, "查询分租房间信息错误，result = {}",roomJson );
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
				}
				if(Check.NuNObj(houseRoomMsg)){
					return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间不存在"), HttpStatus.OK);
				}
			}
			
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), 0);
			LogUtil.info(LOGGER, "要替换的结果：{}",JsonEntityTransform.Object2Json(houseFieldAuditMap));
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())){
				houseRoomMsg.setRoomName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Area.getFieldPath())){
				houseRoomMsg.setRoomArea(Double.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Area.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Check_In_Limit.getFieldPath())){
				houseRoomMsg.setCheckInLimit(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Check_In_Limit.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Cleaning_Fees.getFieldPath())){
				houseRoomMsg.setRoomCleaningFees(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Cleaning_Fees.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Price.getFieldPath())){
				houseRoomMsg.setRoomPrice(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Price.getFieldPath()).getNewValue()));
			}
			LogUtil.info(LOGGER, "替换完的结果：{}",JsonEntityTransform.Object2Json(houseRoomMsg));
			/**要审核字段替换结束**/
            Map<String, Object> result = new HashMap<>();
            Map<String, Boolean> notEditFiled = new HashMap<>();
            if (!Check.NuNObj(houseRoomMsg)) {
                if (houseRoomMsg.getRoomStatus() == HouseStatusEnum.YFB.getCode()) {
                    notEditFiled.put("all", false);
                }
                if (houseRoomMsg.getRoomStatus() == HouseStatusEnum.SJ.getCode()) {
                    notEditFiled.put("houseArea", false);
                }
            }


			FieldTextVo<String> roomNameField = new FieldTextVo<String>(true,"");
			if(!Check.NuNObj(houseRoomMsg) &&!Check.NuNStr(houseRoomMsg.getRoomName())){
				roomNameField.setValue(houseRoomMsg.getRoomName());
			}
            Boolean roomNameEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("roomName");
            if (!Check.NuNObj(roomNameEdit)) {
                roomNameField.setIsEdit(roomNameEdit);
            }
            //判断字段是否在审核中
            if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseRoomMsg.getRoomStatus()){
				roomNameField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
			}
            result.put("roomName", roomNameField);

			FieldTextVo<Integer> roomAreafield = new FieldTextVo<Integer>(true,0);
			if(!Check.NuNObj(houseRoomMsg) &&!Check.NuNObj(houseRoomMsg.getRoomArea())){
				roomAreafield.setValue(houseRoomMsg.getRoomArea().intValue());
			}
            Boolean houseAreaEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("houseArea");
            if (!Check.NuNObj(houseAreaEdit)) {
                roomAreafield.setIsEdit(houseAreaEdit);
            }
            //判断字段是否在审核中
            if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Area.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseRoomMsg.getRoomStatus()){
            	roomAreafield.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
            }
            result.put("houseArea", roomAreafield);

			//是否独立卫生间
			FieldSelectListVo isToiletList = new FieldSelectListVo();
			FieldSelectVo<Integer> isToiletListYES = new FieldSelectVo<Integer>(YesOrNoEnum.YES.getCode(),YesOrNoEnum.YES.getName(), false);
			FieldSelectVo<Integer> isToiletListNO = new FieldSelectVo<Integer>(YesOrNoEnum.NO.getCode(),YesOrNoEnum.NO.getName(), false);
			if(!Check.NuNObj(houseRoomMsg) &&!Check.NuNObj(houseRoomMsg.getIsToilet())){
				if (houseRoomMsg.getIsToilet() == YesOrNoEnum.YES.getCode()) {
					isToiletListYES.setIsSelect(true);
				} else if (houseRoomMsg.getIsToilet() == YesOrNoEnum.NO.getCode()) {
					isToiletListNO.setIsSelect(true);
                }
            }
            isToiletList.getList().add(isToiletListYES);
			isToiletList.getList().add(isToiletListNO);
			isToiletList.setIsEdit(true);
            Boolean isToiletListEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("isToiletList");
            if (!Check.NuNObj(isToiletListEdit)) {
                isToiletList.setIsEdit(isToiletListEdit);
            }
            result.put("isToiletList", isToiletList);

			//入住人数限制
			String limitJson= cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(limitJson);
			FieldSelectListVo limitPersonList = new FieldSelectListVo();
			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<EnumVo> limitList = SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);
				if (!Check.NuNCollection(limitList)) {
					for (EnumVo enumVo : limitList) {
						FieldSelectVo<Integer> limitListSelect = new FieldSelectVo<Integer>(ValueUtil.getintValue(enumVo.getKey()),enumVo.getText(), false);
						if(!Check.NuNObj(houseRoomMsg) && !Check.NuNObj(houseRoomMsg.getCheckInLimit()) && houseRoomMsg.getCheckInLimit()==ValueUtil.getintValue(enumVo.getKey())){
							limitListSelect.setIsSelect(true);
						}
						limitPersonList.getList().add(limitListSelect);
					}
				}

            }
            limitPersonList.setIsEdit(true);
            Boolean limitPersonListEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("limitPersonList");
            if (!Check.NuNObj(limitPersonListEdit)) {
                limitPersonList.setIsEdit(limitPersonListEdit);
            }
            
            
			//查询房源是否共享客厅
			String isHallJson=houseIssueService.isHallByHouseBaseFid(requestDto.getHouseBaseFid());
			Integer isHall=SOAResParseUtil.getIntFromDataByKey(isHallJson, "isHall");
			//发布第一次共享客厅设置入住人限制为默认不选择
			if(isHall==RoomTypeEnum.HALL_TYPE.getCode()&&Check.NuNStr(houseRoomMsg.getRoomName())){
				for(FieldSelectVo limitF:limitPersonList.getList()){
					limitF.setIsSelect(false);
				}
			}
			result.put("limitPersonList", limitPersonList);
			//初始化床位信息
            initBedInfo(result, requestDto.getRoomFid(), notEditFiled,isHall);

			//价格初始化
			Integer leasePrice =null;
			Integer cleaningFees =null;
			if(!Check.NuNObj(houseRoomMsg) ){
				leasePrice =houseRoomMsg.getRoomPrice();
				cleaningFees =houseRoomMsg.getRoomCleaningFees();
			}
            houseService.initSetPrice(result, leasePrice, cleaningFees, requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), notEditFiled);

            LogUtil.info(LOGGER, "[initRoomMsg] result={}", JsonEntityTransform.Object2Json(result));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询单间信息初始化数据异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
     *
     * 初始化床位信息
     *
	 * @author zl
	 * @created 2017年6月20日 上午10:05:14
	 *
	 * @param resultMap
	 * @param roomFid
	 */
    private void initBedInfo(Map<String, Object> resultMap, String roomFid, Map<String, Boolean> notEditFiled, Integer roomType) {

		if (Check.NuNObj(resultMap)) {
			return;
		}

		try {
			DataTransferObject dto =null;
			//已经设置的床位
			List<HouseBedMsgEntity> bedMsgList = new ArrayList<>();
            Map<Integer, Set<String>> bedTypeFids = new HashMap<>();
            if (!Check.NuNStr(roomFid)) {
                String bedListJson = houseIssueService.searchBedListByRoomFid(roomFid);
				dto = JsonEntityTransform.json2DataTransferObject(bedListJson);
				if(dto.getCode() == DataTransferObject.SUCCESS){
					bedMsgList = SOAResParseUtil.getListValueFromDataByKey(bedListJson, "list", HouseBedMsgEntity.class);
                }

				if (!Check.NuNCollection(bedMsgList)) {
                    for (HouseBedMsgEntity bedMsg : bedMsgList) {
                        Set<String> set = bedTypeFids.get(bedMsg.getBedType());
                        if (Check.NuNObj(set)) {
							set = new HashSet<>();
						}
						set.add(bedMsg.getFid());
						bedTypeFids.put(bedMsg.getBedType(), set);
					}
				}
			}

			String bedLimitJson = cityTemplateService.getTextValue(null,ProductRulesEnum.ProductRulesEnum0028.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(bedLimitJson);
			Integer maxBedNumLimit = null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				String bedLimitStr = SOAResParseUtil.getStrFromDataByKey(bedLimitJson, "textValue");
				if (!Check.NuNStr(bedLimitStr)) {
					maxBedNumLimit = Integer.valueOf(bedLimitStr);
					resultMap.put("maxBedNumLimit", maxBedNumLimit);
				}
			}

			//床类型
			String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(bedTypeJson);
			List<EnumVo> bedTypeList = null;
			if(dto.getCode() == DataTransferObject.SUCCESS){
				bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			}
			FieldSelectListVo bedTypeSelectList = new FieldSelectListVo();
			List<Map<String, Object>> bedTypeMsgList = new ArrayList<>();
			if (!Check.NuNCollection(bedTypeList)) {
				for (EnumVo enumVo : bedTypeList) {
					Map<String, Object> bedTypeMsg = new HashMap<>();
					bedTypeMsg.put("maxNum", maxBedNumLimit);
					Set<String> bedfids = bedTypeFids.get(ValueUtil.getintValue(enumVo.getKey()));
					if (bedfids==null) {
						bedfids = new HashSet<>();
					}
					bedTypeMsg.put("bedfids", bedfids);
					bedTypeMsg.put("num", bedfids.size());
					bedTypeMsg.put("bedType", ValueUtil.getintValue(enumVo.getKey()));
					bedTypeMsgList.add(bedTypeMsg);

					FieldSelectVo<Integer> bedTypeSelect = new FieldSelectVo<Integer>(ValueUtil.getintValue(enumVo.getKey()),enumVo.getText(), false);
					bedTypeSelect.setExplain(getBedTypeDes(bedTypeSelect.getValue()));
					if (!Check.NuNCollection(bedMsgList)) {
						for (HouseBedMsgEntity bedMsg : bedMsgList) {
							if (bedMsg.getBedType()==ValueUtil.getintValue(enumVo.getKey())) {
								bedTypeSelect.setIsSelect(true);
							}
						}
                    }
                    bedTypeSelectList.getList().add(bedTypeSelect);
                }
			}
			bedTypeSelectList.setIsEdit(true);
            Boolean bedTypeSelectListEdit = !Check.NuNObj(notEditFiled.get("all")) ? notEditFiled.get("all") : notEditFiled.get("bedTypeList");
            if (!Check.NuNObj(bedTypeSelectListEdit)) {
                bedTypeSelectList.setIsEdit(bedTypeSelectListEdit);
            }
            resultMap.put("bedTypeList", bedTypeSelectList);
            resultMap.put("bedTypeMsgList", bedTypeMsgList);



			//床规格
			//			String bedSizeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum006.getValue());
			//			dto = JsonEntityTransform.json2DataTransferObject(bedSizeJson);
			//			List<EnumVo> bedSizeList = null;
			//			if(dto.getCode() == DataTransferObject.SUCCESS){
			//				bedSizeList = SOAResParseUtil.getListValueFromDataByKey(bedSizeJson, "selectEnum", EnumVo.class);
            //			}
            //
            //			FieldSelectListVo bedSizeSelectList = new FieldSelectListVo();
            //			if (!Check.NuNCollection(bedSizeList)) {
			//				for (EnumVo enumVo : bedSizeList) {
			//					FieldSelectVo<Integer> bedSizeSelect = new FieldSelectVo<Integer>(ValueUtil.getintValue(enumVo.getKey()),enumVo.getText(), false);
			//					if (Check.NuNCollection(bedMsgList)) {
			//						for (HouseBedMsgEntity bedMsg : bedMsgList) {
			//							if (!Check.NuNObj(bedMsg.getBedSize()) && bedMsg.getBedSize()==ValueUtil.getintValue(enumVo.getKey())) {
			//								bedSizeSelect.setIsSelect(true);
			//							}
			//						}
			//					}
            //
            //					bedSizeSelectList.getList().add(bedSizeSelect);
            //				}
			//			}
			//			bedSizeSelectList.setIsEdit(true);
			//			resultMap.put("bedSizeList", bedSizeSelectList);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "初始化床位信息异常，error = {}", e);
		}

	}

	/**
     *
     * 获取床型描述
     *
	 * @author zl
	 * @created 2017年6月20日 上午10:40:31
	 *
	 * @param bedType
	 * @return
	 */
	private String getBedTypeDes(int bedType){
		String desc="";
		if (bedType==1) {//单人
			desc="宽1.5米以下";
		}else if (bedType==2) {//双人
            desc = "宽1.5米或以上";
        } else if (bedType == 3) {//双层

		}else if (bedType==4) {//榻榻米

        } else if (bedType == 5) {//沙发床

        } else if (bedType == 6) {//双人大床
            desc = "宽1.8米以上";
        }
        return desc;
	}







	/**
     *
     * 分租第二步2-3合租单间信息保存
     *
	 * @author zl
	 * @created 2017年6月19日 上午10:31:19
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveRoomMsg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveRoomMsg(HttpServletRequest request) {

		try {
			Header header = getHeader(request);
			String uid = getUserId(request);

			if (Check.NuNStr(uid)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("非法操作"),HttpStatus.OK);
			}

			DataTransferObject dto = new DataTransferObject();

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveRoomMsg]uid={},参数={}",uid,paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();

			DataTransferObject paramDto = assembleAndValidRoomParam( request,  requestDto);

			if (Check.NuNObj(paramDto) ) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
            }
            if (paramDto.getCode() == DataTransferObject.ERROR) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(paramDto.getMsg()), HttpStatus.OK);
            }

			RoomMsgVo roomMsgVo=paramDto.parseData("roomMsgVo", new TypeReference<RoomMsgVo>() {});
			HouseBaseExtDto houseBaseExtDto = paramDto.parseData("houseBaseExtDto", new TypeReference<HouseBaseExtDto>() {});
			//长租折扣
			Integer fullDayRateSwitch = paramDto.parseData("fullDayRateSwitch", new TypeReference<Integer>() {});
			List<HouseConfMsgEntity> fullDayRateList = paramDto.parseData("fullDayRateList", new TypeReference<List<HouseConfMsgEntity>>() {});
			//周末价格
			Integer weekendPriceSwitch = paramDto.parseData("weekendPriceSwitch", new TypeReference<Integer>() {});
			List<HousePriceWeekConfEntity> weekendPriceList  = paramDto.parseData("weekendPriceList", new TypeReference<List<HousePriceWeekConfEntity>>() {});
			//床位
			List<HouseBedMsgEntity> delbedList = paramDto.parseData("delbedList", new TypeReference<ArrayList<HouseBedMsgEntity>>() {});//要删除的床位
			List<HouseBedMsgEntity> addbedList = paramDto.parseData("addbedList", new TypeReference<ArrayList<HouseBedMsgEntity>>() {});//新增的床位


			AssembleRoomMsgDto assembleRoomMsgDto = new AssembleRoomMsgDto();
			assembleRoomMsgDto.setFullDayRateList(fullDayRateList);
			assembleRoomMsgDto.setFullDayRateSwitch(fullDayRateSwitch);
			assembleRoomMsgDto.setHouseBaseExtDto(houseBaseExtDto);
			assembleRoomMsgDto.setRoomMsgVo(roomMsgVo);
			assembleRoomMsgDto.setWeekendPriceList(weekendPriceList);
			assembleRoomMsgDto.setWeekendPriceSwitch(weekendPriceSwitch);
			assembleRoomMsgDto.setAddbedList(addbedList);
			assembleRoomMsgDto.setDelbedList(delbedList);


			//保存修改记录 --- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay());

			String resultJson = houseIssueAppService.saveAssembleRoomMsg(JsonEntityTransform.Object2Json(assembleRoomMsgDto));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode()==DataTransferObject.ERROR){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            } else {
                String roomFid = SOAResParseUtil.getStrFromDataByKey(resultJson, "roomFid");
				requestDto.setRoomFid(roomFid);
				LogUtil.info(LOGGER, "合租单间信息保存返回数据，result={}", JsonEntityTransform.Object2Json(requestDto));

				//保存修改记录 --- 保存
				if(!Check.NuNObj(houseUpdateHistoryLogDto)){
					houseUpdateHistoryLogDto.setHouseFid(requestDto.getHouseBaseFid());
					houseUpdateHistoryLogDto.setRentWay(requestDto.getRentWay());
					houseUpdateHistoryLogDto.setRoomFid(requestDto.getRoomFid());
					houseUpdateLogService.saveHistoryLog(assembleRoomMsgDto, request, houseUpdateHistoryLogDto, requestDto);
				}
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(requestDto), HttpStatus.OK);
			}

		} catch (Exception e) {
			LogUtil.error(LOGGER, "合租单间信息保存异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
     *
     * 校验和组装房间参数
     *
	 * @author zl
	 * @created 2017年6月22日 上午10:32:55
	 *
	 * @param request
	 * @param requestDto
	 * @return
	 */
	private DataTransferObject assembleAndValidRoomParam(HttpServletRequest request, HouseBaseParamsDto requestDto) {

		DataTransferObject paramDto = validateRoomParam(assembleRoomParam( request, requestDto));

		if (Check.NuNObj(paramDto)) {
			return new DataTransferObject(DataTransferObject.ERROR, "参数错误",null);
		}
		if (paramDto.getCode() == DataTransferObject.ERROR) {
			return paramDto;
		}

		try {
			RoomMsgVo roomMsgVo=paramDto.parseData("roomMsgVo", new TypeReference<RoomMsgVo>() {});
			Integer weekendPriceSwitch = paramDto.parseData("weekendPriceSwitch", new TypeReference<Integer>() {});
			Integer weekPrice =paramDto.parseData("weekPrice", new TypeReference<Integer>() {});
			Set<Integer> weekdays = paramDto.parseData("weekdays", new TypeReference<Set<Integer>>() {});
			Integer fullDayRateSwitch = paramDto.parseData("fullDayRateSwitch", new TypeReference<Integer>() {});
			Double sevenDiscountRate = paramDto.parseData("sevenDiscountRate", new TypeReference<Double>() {});
			Double thirtyDiscountRate = paramDto.parseData("thirtyDiscountRate", new TypeReference<Double>() {});
			List<HouseBedMsgEntity> delbedList = paramDto.parseData("delbedList", new TypeReference<ArrayList<HouseBedMsgEntity>>() {});//要删除的床位
			List<HouseBedMsgEntity> addbedList = paramDto.parseData("addbedList", new TypeReference<ArrayList<HouseBedMsgEntity>>() {});//新增的床位

			//周末价格
			List<HousePriceWeekConfEntity> weekendPriceList  = new ArrayList<>();
			if(!Check.NuNCollection(weekdays)){
				for (int wk : weekdays) {
					HousePriceWeekConfEntity housePricedConf = new HousePriceWeekConfEntity();
					housePricedConf.setCreateDate(new Date());
					housePricedConf.setCreateUid(roomMsgVo.getCreateUid());
					housePricedConf.setFid(UUIDGenerator.hexUUID());
					housePricedConf.setHouseBaseFid(requestDto.getHouseBaseFid());
					housePricedConf.setLastModifyDate(new Date());
					housePricedConf.setPriceVal(weekPrice);
					housePricedConf.setRoomFid(roomMsgVo.getHouseRoomFid());
                    housePricedConf.setSetWeek(wk);
                    housePricedConf.setRentWay(requestDto.getRentWay());

					if(weekendPriceSwitch==YesOrNoEnum.YES.getCode()){
						housePricedConf.setIsValid(YesOrNoOrFrozenEnum.YES.getCode());
					}else{
						housePricedConf.setIsValid(YesOrNoOrFrozenEnum.NO.getCode());
					}

					weekendPriceList.add(housePricedConf);
				}

            }
            paramDto.putValue("weekendPriceList", weekendPriceList);


			//长租折扣
            Map<String, HouseConfMsgEntity> upFullDayRate = new HashMap<>();
            HouseConfMsgEntity sevenEntity = new HouseConfMsgEntity();
            sevenEntity.setFid(UUIDGenerator.hexUUID());
            sevenEntity.setHouseBaseFid(requestDto.getHouseBaseFid());
            sevenEntity.setRoomFid(roomMsgVo.getHouseRoomFid());//新增的在保存时需要出处理
            sevenEntity.setBedFid(null);
            sevenEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019001.getValue());
            if (!Check.NuNObj(sevenDiscountRate)) {
                sevenEntity.setDicVal(String.valueOf(sevenDiscountRate));
            } else {
                sevenEntity.setDicVal(String.valueOf(-1));//兼容以前
            }
            sevenEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
            upFullDayRate.put(ProductRulesEnum0019.ProductRulesEnum0019001.getValue(), sevenEntity);

            HouseConfMsgEntity thirtyEntity = new HouseConfMsgEntity();
            thirtyEntity.setFid(UUIDGenerator.hexUUID());
            thirtyEntity.setHouseBaseFid(requestDto.getHouseBaseFid());
            thirtyEntity.setRoomFid(roomMsgVo.getHouseRoomFid());
            thirtyEntity.setBedFid(null);
            thirtyEntity.setDicCode(ProductRulesEnum0019.ProductRulesEnum0019002.getValue());
            if (!Check.NuNObj(thirtyDiscountRate)) {
                thirtyEntity.setDicVal(String.valueOf(thirtyDiscountRate));
            } else {
                thirtyEntity.setDicVal(String.valueOf(-1));//兼容以前
            }
            thirtyEntity.setIsDel(YesOrNoOrFrozenEnum.NO.getCode());
            upFullDayRate.put(ProductRulesEnum0019.ProductRulesEnum0019002.getValue(), thirtyEntity);

            List<HouseConfMsgEntity> fullDayRateList = new ArrayList<>(upFullDayRate.values());

			paramDto.putValue("fullDayRateList", fullDayRateList);

		} catch (Exception e) {
			paramDto.setErrCode(DataTransferObject.ERROR);
			paramDto.setMsg("服务错误");
			LogUtil.error(LOGGER, "合租单间信息保存异常，error = {}", e);
		}

		return paramDto;
	}


	/**
     *
     * 校验房间参数
     *
	 * @author zl
	 * @created 2017年6月22日 上午11:19:42
	 *
	 * @param paramDto
	 * @return
	 */
	private DataTransferObject validateRoomParam(DataTransferObject paramDto) {

		if (Check.NuNObj(paramDto)) {
			return new DataTransferObject(DataTransferObject.ERROR, "参数错误",null);
		}
		if (paramDto.getCode() == DataTransferObject.ERROR) {
			return paramDto;
		}

		try {

			Integer rentRoomNum = paramDto.parseData("rentRoomNum", new TypeReference<Integer>() {});
			RoomMsgVo roomMsgVo=paramDto.parseData("roomMsgVo", new TypeReference<RoomMsgVo>() {});
			Integer weekendPriceSwitch = paramDto.parseData("weekendPriceSwitch", new TypeReference<Integer>() {});
			Integer weekPrice =paramDto.parseData("weekPrice", new TypeReference<Integer>() {});
			Set<Integer> weekdays = paramDto.parseData("weekdays", new TypeReference<Set<Integer>>() {});
			Integer fullDayRateSwitch = paramDto.parseData("fullDayRateSwitch", new TypeReference<Integer>() {});
			Double sevenDiscountRate = paramDto.parseData("sevenDiscountRate", new TypeReference<Double>() {});
			Double thirtyDiscountRate = paramDto.parseData("thirtyDiscountRate", new TypeReference<Double>() {});
			List<HouseBedMsgEntity> delbedList = paramDto.parseData("delbedList", new TypeReference<ArrayList<HouseBedMsgEntity>>() {});//要删除的床位
			List<HouseBedMsgEntity> addbedList = paramDto.parseData("addbedList", new TypeReference<ArrayList<HouseBedMsgEntity>>() {});//新增的床位

			LogUtil.info(LOGGER, "[validateRoomParam]校验参数：{}",paramDto.toJsonString() );

			if (Check.NuNObj(roomMsgVo)) {
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("参数错误");
				return paramDto;
			}

			HouseBaseExtDto houseBaseMsgEntity = null;
			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(roomMsgVo.getHouseBaseFid());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
			}
			if (Check.NuNObj(houseBaseMsgEntity)) {
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房源不存在");
                return paramDto;
            }

			if(Check.NuNObj(rentRoomNum) || rentRoomNum<1 ||rentRoomNum >houseBaseMsgEntity.getRoomNum()){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("可租房间数不正确");
				return paramDto;
			}
			if(rentRoomNum >houseBaseMsgEntity.getRoomNum()){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("可租房间数不能超过设置的户型");
				return paramDto;
			}

            houseBaseMsgEntity.getHouseBaseExt().setRentRoomNum(rentRoomNum);
            paramDto.putValue("houseBaseExtDto", houseBaseMsgEntity);

			//已经保存的房间
			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(roomMsgVo.getHouseBaseFid());
			dto = JsonEntityTransform.json2DataTransferObject(roomListJson);
			List<HouseRoomMsgEntity> roomList =new ArrayList<>();
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				dto.putValue("list", roomList);
				roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list", HouseRoomMsgEntity.class);
			}
			if(Check.NuNStr(roomMsgVo.getHouseRoomFid()) && roomList.size()>rentRoomNum){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("添加的房间数已经超过可租房间数");
				return paramDto;
			}

            HouseRoomMsgEntity currentRoom = null;
            if (!Check.NuNStr(roomMsgVo.getHouseRoomFid()) && Check.NuNCollection(roomList)) {
                paramDto.setErrCode(DataTransferObject.ERROR);
                paramDto.setMsg("当前房间不存在");
                return paramDto;
            } else if (!Check.NuNStr(roomMsgVo.getHouseRoomFid()) && !Check.NuNCollection(roomList)) {
                for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
                    if (roomMsgVo.getHouseRoomFid().equals(houseRoomMsgEntity.getFid())) {
                        currentRoom = houseRoomMsgEntity;
                        break;
                    }
                }
                if (Check.NuNObj(currentRoom)) {
                    paramDto.setErrCode(DataTransferObject.ERROR);
                    paramDto.setMsg("当前房间不存在");
                    return paramDto;
                }

            }

			if(Check.NuNStr(roomMsgVo.getRoomName()) || roomMsgVo.getRoomName().length()<10 ){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间名称过短");
				return paramDto;
			}
			if(roomMsgVo.getRoomName().length()>30){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间名称过长");
				return paramDto;
			}
            if (!Check.NuNObj(currentRoom) && currentRoom.getRoomStatus() == HouseStatusEnum.YFB.getCode()
                    && !roomMsgVo.getRoomName().equals(currentRoom.getRoomName())) {
                paramDto.setErrCode(DataTransferObject.ERROR);
                paramDto.setMsg("当前状态房间名称不可修改");
                return paramDto;
            }

			if(Check.NuNObj(roomMsgVo.getRoomArea()) || roomMsgVo.getRoomArea()<0 ){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间面积不正确");
				return paramDto;
			}
			if(roomMsgVo.getRoomArea()>100000){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间面积过大");
				return paramDto;
			}

            if (!Check.NuNObj(currentRoom) && (currentRoom.getRoomStatus() == HouseStatusEnum.YFB.getCode() || currentRoom.getRoomStatus() == HouseStatusEnum.SJ.getCode())
                    && !roomMsgVo.getRoomArea().equals(currentRoom.getRoomArea())) {
                paramDto.setErrCode(DataTransferObject.ERROR);
                paramDto.setMsg("当前状态房间面积不可修改");
                return paramDto;
            }

			// 房源价格限制
			String priceLowJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021001.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(priceLowJson);
			Integer priceLow =null;
			if(dto.getCode() == DataTransferObject.SUCCESS){
				priceLow = Integer.valueOf(SOAResParseUtil.getStrFromDataByKey(priceLowJson, "textValue"));
			}

			String priceHighJson = cityTemplateService.getTextValue(null, ProductRulesEnum021Enum.ProductRulesEnum021002.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(priceHighJson);
			Integer priceHigh =null;
			if(dto.getCode() == DataTransferObject.SUCCESS){
				priceHigh = Integer.valueOf(SOAResParseUtil.getStrFromDataByKey(priceHighJson, "textValue"));
			}

			if(Check.NuNObj(roomMsgVo.getRoomPrice()) ){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间基础价格不正确");
				return paramDto;
			}

			if(!Check.NuNObj(priceLow) && priceLow>roomMsgVo.getRoomPrice()){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间基础价格过低");
				return paramDto;
			}
			if(!Check.NuNObj(priceHigh) && priceHigh<roomMsgVo.getRoomPrice()){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("房间基础价格过高");
				return paramDto;
			}
            if (!Check.NuNObj(currentRoom) && currentRoom.getRoomStatus() == HouseStatusEnum.YFB.getCode()
                    && roomMsgVo.getRoomPrice() != currentRoom.getRoomPrice()) {
                paramDto.setErrCode(DataTransferObject.ERROR);
                paramDto.setMsg("当前状态房间基础价格不可修改");
                return paramDto;
            }

            //周末价格
            if (Check.NuNObj(weekendPriceSwitch)) {
                paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("周末价格开关设置不正确");
				return paramDto;
			}
			if (weekendPriceSwitch==YesOrNoEnum.YES.getCode() && (
					Check.NuNObj(weekPrice) || weekdays.size()==0 || Check.NuNObj(WeekendPriceEnum.getEnumByColl(weekdays)))) {
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("周末价格设置不正确");
				return paramDto;
			}
			if (weekendPriceSwitch==YesOrNoEnum.YES.getCode() && !Check.NuNObj(priceLow) && priceLow>weekPrice){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("周末价格过低");
				return paramDto;
            }
            if (weekendPriceSwitch == YesOrNoEnum.YES.getCode() && !Check.NuNObj(priceHigh) && priceHigh < weekPrice) {
                paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("周末价格过高");
				return paramDto;
			}

			//长租折扣
			if (Check.NuNObj(fullDayRateSwitch) ) {
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("长租折扣开关设置不正确");
				return paramDto;
			}
			if (fullDayRateSwitch==YesOrNoEnum.YES.getCode() && Check.NuNObj(sevenDiscountRate) && Check.NuNObj(thirtyDiscountRate)){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("长租折扣设置不正确");
				return paramDto;
			}
			if (fullDayRateSwitch==YesOrNoEnum.YES.getCode() &&  !Check.NuNObj(sevenDiscountRate) && sevenDiscountRate<0.1){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("周租折扣设置过小");
				return paramDto;
			}
			if (fullDayRateSwitch==YesOrNoEnum.YES.getCode() &&  !Check.NuNObj(thirtyDiscountRate) && thirtyDiscountRate<0.1){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("月租折扣设置过小");
				return paramDto;
            }
            if (fullDayRateSwitch == YesOrNoEnum.YES.getCode() && !Check.NuNObj(sevenDiscountRate) && sevenDiscountRate > 9.9) {
                paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("周租折扣设置过大");
				return paramDto;
			}
			if (fullDayRateSwitch==YesOrNoEnum.YES.getCode() &&  !Check.NuNObj(thirtyDiscountRate) && thirtyDiscountRate>9.9){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("月租折扣设置过大");
				return paramDto;
            }

			//清洁费
			if(!Check.NuNObj(roomMsgVo.getRoomCleaningFees()) && roomMsgVo.getRoomCleaningFees()>0){
				String cleaningFeesPerJson = cityTemplateService.getTextValue(null, TradeRulesEnum.TradeRulesEnum0019.getValue());
				dto = JsonEntityTransform.json2DataTransferObject(cleaningFeesPerJson);
				if(dto.getCode() == DataTransferObject.SUCCESS){
					Double cleaningFeesPer = Double.valueOf(SOAResParseUtil.getStrFromDataByKey(cleaningFeesPerJson, "textValue"));
					if(!Check.NuNObj(cleaningFeesPer) ){
						int cleaningFeesPerInt = new Double(Math.ceil(cleaningFeesPer)).intValue();
						if(roomMsgVo.getRoomCleaningFees() > (roomMsgVo.getRoomPrice()*cleaningFeesPerInt)){
							paramDto.setErrCode(DataTransferObject.ERROR);
							paramDto.setMsg("清洁费设置不正确");
							return paramDto;
						}
					}
				}
			}
			if(Check.NuNObj(roomMsgVo.getIsToilet()) || (roomMsgVo.getIsToilet()!=YesOrNoEnum.YES.getCode() && roomMsgVo.getIsToilet()!=YesOrNoEnum.NO.getCode()) ){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("是否独卫设置不正确");
				return paramDto;
			}
			if(Check.NuNObj(roomMsgVo.getCheckInLimit()) ){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("人数限制设置不正确");
				return paramDto;
			}

			String bedLimitJson = cityTemplateService.getTextValue(null,ProductRulesEnum.ProductRulesEnum0028.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(bedLimitJson);
			Integer maxBedNumLimit = null;
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				String bedLimitStr = SOAResParseUtil.getStrFromDataByKey(bedLimitJson, "textValue");
				if (!Check.NuNStr(bedLimitStr)) {
					maxBedNumLimit = Integer.valueOf(bedLimitStr);
				}
			}

			int bedSize = 0;

			if(addbedList!=null){
				bedSize+=addbedList.size();
			}
			if(roomMsgVo.getBedList()!=null){
				bedSize+=roomMsgVo.getBedList().size();
			}

			if(bedSize>maxBedNumLimit){
				paramDto.setErrCode(DataTransferObject.ERROR);
				paramDto.setMsg("床位数量超过限制");
				return paramDto;
			}


		} catch (Exception e) {
			paramDto.setErrCode(DataTransferObject.ERROR);
			paramDto.setMsg("参数校验失败");
			LogUtil.error(LOGGER, "房间参数校验失败，param={}，e={}", JsonEntityTransform.Object2Json(paramDto), e);
		}

		return paramDto;
    }


	/**
     *
     * 组装单个房间参数
     *
	 * @author zl
	 * @created 2017年6月20日 下午6:05:27
	 *
	 * @param request
	 * @return
	 */
	private DataTransferObject assembleRoomParam(HttpServletRequest request, HouseBaseParamsDto requestDto) {
		DataTransferObject dto = new DataTransferObject();

		try {

            String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);

			Map<String, Object> paramsMap = (Map<String, Object>) JsonEntityTransform.json2Map(paramJson);

			//房间数量
			Integer rentRoomNum = null;
			if(!Check.NuNObj(paramsMap.get("rentRoomNum"))){
				rentRoomNum = Integer.valueOf(String.valueOf(paramsMap.get("rentRoomNum")));
			}

			RoomMsgVo roomMsgVo = new RoomMsgVo();
			roomMsgVo.setHouseBaseFid(requestDto.getHouseBaseFid());
			roomMsgVo.setHouseRoomFid(requestDto.getRoomFid());
			roomMsgVo.setCreateUid(getUserId(request));
			if(!Check.NuNObj(paramsMap.get("roomName"))){
				roomMsgVo.setRoomName((String) paramsMap.get("roomName"));
			}

			if(!Check.NuNObj(paramsMap.get("houseArea"))){
				roomMsgVo.setRoomArea(Double.valueOf(String.valueOf(paramsMap.get("houseArea"))));
			}
			if(!Check.NuNObj(paramsMap.get("leasePrice"))){
				roomMsgVo.setRoomPrice(Integer.valueOf(String.valueOf(paramsMap.get("leasePrice"))));
			}
			//周末价格
			Integer weekendPriceSwitch =null;
			if(!Check.NuNObj(paramsMap.get("weekendPriceSwitch"))){
				weekendPriceSwitch =Integer.valueOf(String.valueOf(paramsMap.get("weekendPriceSwitch")));
			}
			Set<Integer> weekdays = new HashSet<>();
			Integer weekPrice =null;
			if(!Check.NuNObj(paramsMap.get("weekPrice"))){
				weekPrice = Integer.valueOf(String.valueOf(paramsMap.get("weekPrice")));
			}
			String weekdaysStr =null;
			if(!Check.NuNObj(paramsMap.get("weekdays"))){
				weekdaysStr = (String) paramsMap.get("weekdays");
			}
			if(!Check.NuNObj(weekPrice) && !Check.NuNStr(weekdaysStr)){
				String[] wks = weekdaysStr.split(splist);
				if (wks.length>0) {
					for (String wkr : wks) {
						weekdays.add(Integer.valueOf(wkr));
					}
                }
            }

			//长期折扣
			Integer fullDayRateSwitch = null;
			if(!Check.NuNObj(paramsMap.get("fullDayRateSwitch"))){
				fullDayRateSwitch = Integer.valueOf(String.valueOf(paramsMap.get("fullDayRateSwitch")));
			}
			Double sevenDiscountRate =null;
			if(!Check.NuNObj(paramsMap.get("sevenDiscountRate"))){
				sevenDiscountRate = Double.valueOf(String.valueOf(paramsMap.get("sevenDiscountRate")));
			}
			Double thirtyDiscountRate =null;
			if(!Check.NuNObj(paramsMap.get("thirtyDiscountRate"))){
				thirtyDiscountRate = Double.valueOf(String.valueOf(paramsMap.get("thirtyDiscountRate")));
			}
            if (!Check.NuNObj(sevenDiscountRate) && sevenDiscountRate == 0) {
                sevenDiscountRate = null;
            }
            if (!Check.NuNObj(thirtyDiscountRate) && thirtyDiscountRate == 0) {
                thirtyDiscountRate = null;
            }
            //清洁费
            if (Check.NuNObj(paramsMap.get("cleaningFees"))) {
				roomMsgVo.setRoomCleaningFees(0);
			} else {
				roomMsgVo.setRoomCleaningFees(Integer.valueOf(String.valueOf(paramsMap.get("cleaningFees"))));
			}
			if(!Check.NuNObj(paramsMap.get("isToilet"))){
				roomMsgVo.setIsToilet(Integer.valueOf(String.valueOf(paramsMap.get("isToilet"))));
			}
			if(!Check.NuNObj(paramsMap.get("checkInLimit"))){
				roomMsgVo.setCheckInLimit(Integer.valueOf(String.valueOf(paramsMap.get("checkInLimit"))));
			}

			//床位
			List<HouseBedMsgEntity> delbedList = new ArrayList<>();//要删除的床位
			List<HouseBedMsgEntity> addbedList = new ArrayList<>();//新增的床位
			String bedArraysStr=null;
			if(!Check.NuNObj(paramsMap.get("bedArrays"))){
				bedArraysStr = (String) paramsMap.get("bedArrays");
			}
			if (!Check.NuNStr(bedArraysStr)) {
				List<HouseBedMsgEntity> bedList = new ArrayList<>();
				String[] bedArrays = bedArraysStr.split(splist);
				if (bedArrays != null && bedArrays.length > 0) {
					for (String bedstrs : bedArrays) {
						String[] beds = bedstrs.split(subSplist);
						if (beds.length < 2) {
							continue;
						}
						int bedType = Integer.valueOf(beds[0]);
						int num = Integer.valueOf(beds[1]);
						String[] bedfids = null;//原先保存的床的fid
						if (beds.length > 2) {
							bedfids = beds[2].split("\\|");
						}

						if(bedfids!=null && bedfids.length>num){//数量减少，需要删除length-num个
							for(int i=1;i<=bedfids.length-num;i++){
								HouseBedMsgEntity bedMsgEntity = new HouseBedMsgEntity();
								bedMsgEntity.setBedType(bedType);
								bedMsgEntity.setFid(bedfids[i - 1]);
								bedMsgEntity.setHouseBaseFid(requestDto.getHouseBaseFid());
								bedMsgEntity.setLastModifyDate(new Date());
								bedMsgEntity.setRoomFid(roomMsgVo.getHouseRoomFid());//新增为空，需要在后面的步骤赋值
								bedMsgEntity.setIsDel(YesOrNoEnum.YES.getCode());
								delbedList.add(bedMsgEntity);
							}
						}else {
							for (int i = 1; i <= num; i++) {
								HouseBedMsgEntity bedMsgEntity = new HouseBedMsgEntity();
								bedMsgEntity.setBedType(bedType);
								if (!Check.NuNObj(bedfids) && i <= bedfids.length) {
									bedMsgEntity.setFid(bedfids[i - 1]);
								}
								bedMsgEntity.setHouseBaseFid(requestDto.getHouseBaseFid());
								bedMsgEntity.setLastModifyDate(new Date());
								bedMsgEntity.setRoomFid(roomMsgVo.getHouseRoomFid());//新增为空，需要在后面的步骤赋值

								if(Check.NuNStr(bedMsgEntity.getFid())){//新增,先不生成fid,保存的时候统一生成
									addbedList.add(bedMsgEntity);
								}else{//不用动
									bedList.add(bedMsgEntity);
								}

							}

						}

					}
				}

				roomMsgVo.setBedList(bedList);
				roomMsgVo.setBedNum(bedList.size());
			}

			dto.putValue("rentRoomNum", rentRoomNum);
			dto.putValue("roomMsgVo", roomMsgVo);
			dto.putValue("weekendPriceSwitch", weekendPriceSwitch);
			dto.putValue("weekPrice", weekPrice);
			dto.putValue("weekdays", weekdays);
			dto.putValue("fullDayRateSwitch", fullDayRateSwitch);
			dto.putValue("sevenDiscountRate", sevenDiscountRate);
			dto.putValue("thirtyDiscountRate", thirtyDiscountRate);
			dto.putValue("delbedList", delbedList);
			dto.putValue("addbedList", addbedList);


		} catch (Exception e) {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.error(LOGGER, "转化房间参数失败，param={}，e={}", paramJson, e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
		}

		return dto;

	}


	/**
     *
     * 查询交易信息初始化数据
     *
	 * @author zl
	 * @created 2017年6月14日 下午3:54:14
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/${LOGIN_AUTH}/initDealPolicy")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initDealPolicy(HttpServletRequest request) {

		try {

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initDealPolicy]参数：" + paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseBaseParamsDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();

			DataTransferObject dto = new DataTransferObject();
			Map<String, Object> result = new HashMap<>();

			//已入库的值
			HouseBaseExtDto houseBaseExtDto =null;
            HouseRoomExtEntity houseRoomExtEntity = null;
            List<HouseRoomMsgEntity> roomList = new ArrayList<>();
            //申请类型
            OrderTypeEnum orderType = null;
            //退订政策
			String checkOutRulesCode = null;
			String houseRules=null;
			Integer houseStatus=HouseStatusEnum.DFB.getCode();
			if(requestDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				String houseJson = houseIssueService.searchHouseBaseAndExtByFid(requestDto.getHouseBaseFid());
				dto = JsonEntityTransform.json2DataTransferObject(houseJson);
				if (dto.getCode() == DataTransferObject.SUCCESS) {
					houseBaseExtDto = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
					if(!Check.NuNObj(houseBaseExtDto) && !Check.NuNObj(houseBaseExtDto.getHouseBaseExt())){
						orderType = OrderTypeEnum.getOrderTypeByCode(houseBaseExtDto.getHouseBaseExt().getOrderType());
						checkOutRulesCode = houseBaseExtDto.getHouseBaseExt().getCheckOutRulesCode();
                    }
					houseStatus=houseBaseExtDto.getHouseStatus();
                }

				String houseDescJson = houseIssueService.searchHouseDescAndBaseExt(requestDto.getHouseBaseFid());
				dto = JsonEntityTransform.json2DataTransferObject(houseDescJson);
				if (dto.getCode() == DataTransferObject.SUCCESS) {
					HouseDescDto houseDesc = dto.parseData("obj", new TypeReference<HouseDescDto>() {});
					if (!Check.NuNObj(houseDesc)) {
						houseRules=houseDesc.getHouseRules();
					}
                }

			}else if(requestDto.getRentWay()==RentWayEnum.ROOM.getCode()){
				if(!Check.NuNStr(requestDto.getRoomFid())){
					String roomExtJson = houseIssueService.getRoomExtByRoomFid(requestDto.getRoomFid());
					dto = JsonEntityTransform.json2DataTransferObject(roomExtJson);
					if (dto.getCode() == DataTransferObject.SUCCESS) {
						houseRoomExtEntity = SOAResParseUtil.getValueFromDataByKey(roomExtJson, "roomExt", HouseRoomExtEntity.class);
					}
					//查询房源状态
					String roomJson = houseIssueService.searchHouseRoomMsgByFid(requestDto.getRoomFid());
					DataTransferObject roomDto =JsonEntityTransform.json2DataTransferObject(roomJson);
					HouseRoomMsgEntity houseRoomMsg = null;
					if(roomDto.getCode()==DataTransferObject.SUCCESS){
						houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
					}
					if(!Check.NuNObj(houseRoomMsg)){
						houseStatus=houseRoomMsg.getRoomStatus();
					}
				}else{//发布的过程中为空，随便取一个房间
					String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(requestDto.getHouseBaseFid());
					dto = JsonEntityTransform.json2DataTransferObject(roomListJson);

					if (dto.getCode() == DataTransferObject.SUCCESS) {
						dto.putValue("list", roomList);
						roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list", HouseRoomMsgEntity.class);

                        if (!Check.NuNCollection(roomList)) {
                            for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
                                String roomExtJson = houseIssueService.getRoomExtByRoomFid(houseRoomMsgEntity.getFid());
								dto = JsonEntityTransform.json2DataTransferObject(roomExtJson);
								if (dto.getCode() == DataTransferObject.SUCCESS) {
									houseRoomExtEntity = SOAResParseUtil.getValueFromDataByKey(roomExtJson, "roomExt", HouseRoomExtEntity.class);
									if(!Check.NuNObj(houseRoomExtEntity)){
										requestDto.setRoomFid(houseRoomExtEntity.getRoomFid());
										break;
									}
								}
                            }

						}

					}

				}


                if (!Check.NuNObj(houseRoomExtEntity)) {
                    orderType = OrderTypeEnum.getOrderTypeByCode(houseRoomExtEntity.getOrderType());
					checkOutRulesCode = houseRoomExtEntity.getCheckOutRulesCode();
					houseRules=houseRoomExtEntity.getRoomRules();

                } else {//兼容老版本，待20170717发版之后可删掉

					String houseJson = houseIssueService.searchHouseBaseAndExtByFid(requestDto.getHouseBaseFid());
					dto = JsonEntityTransform.json2DataTransferObject(houseJson);
					if (dto.getCode() == DataTransferObject.SUCCESS) {
						houseBaseExtDto = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
						if(!Check.NuNObj(houseBaseExtDto) && !Check.NuNObj(houseBaseExtDto.getHouseBaseExt())){
							orderType = OrderTypeEnum.getOrderTypeByCode(houseBaseExtDto.getHouseBaseExt().getOrderType());
							checkOutRulesCode = houseBaseExtDto.getHouseBaseExt().getCheckOutRulesCode();
                        }
                    }

					String houseDescJson = houseIssueService.searchHouseDescAndBaseExt(requestDto.getHouseBaseFid());
					dto = JsonEntityTransform.json2DataTransferObject(houseDescJson);
					if (dto.getCode() == DataTransferObject.SUCCESS) {
						HouseDescDto houseDesc = dto.parseData("obj", new TypeReference<HouseDescDto>() {});
						if (!Check.NuNObj(houseDesc)) {
							houseRules=houseDesc.getHouseRules();
						}
                    }

				}


			}
			
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), 0);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Order_Type.getFieldPath())){
				orderType=OrderTypeEnum.getOrderTypeByCode(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Order_Type.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Order_Type.getFieldPath())){
				orderType=OrderTypeEnum.getOrderTypeByCode(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Order_Type.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Check_Out_Rules_Code.getFieldPath())){
				checkOutRulesCode=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Check_Out_Rules_Code.getFieldPath()).getNewValue();
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Check_Out_Rules_Code.getFieldPath())){
				checkOutRulesCode=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Check_Out_Rules_Code.getFieldPath()).getNewValue();
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Room_Rules.getFieldPath())){
				houseRules=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Room_Rules.getFieldPath()).getNewValue();
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Rules.getFieldPath())){
				houseRules=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Rules.getFieldPath()).getNewValue();
			}
			/**要审核字段替换结束**/

			//申请类型待选列表
			if(Check.NuNObj(orderType)){//默认立即预订
				orderType= OrderTypeEnum.CURRENT;
			}
			String orderTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0010.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(orderTypeJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				List<EnumVo> orderTypeList = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});

				FieldSelectListVo orderTypeFieldList = new FieldSelectListVo();
				if(!Check.NuNCollection(orderTypeList)){
					for (EnumVo enumVo : orderTypeList) {
						int type=ValueUtil.getintValue(enumVo.getKey());
						FieldSelectVo<Integer> orderTypeField = new FieldSelectVo<Integer>(type,enumVo.getText(),false);
						if(type==OrderTypeEnum.CURRENT.getCode()){
                            orderTypeField.setExplain(OrderTypeEnum.CURRENT.getDefautExplain());
                        } else if (type == OrderTypeEnum.ORDINARY.getCode()) {
                            orderTypeField.setExplain(OrderTypeEnum.ORDINARY.getDefautExplain());
                        }

						if(!Check.NuNObj(orderType) && type==orderType.getCode()){
							orderTypeField.setIsSelect(true);
						}
						orderTypeFieldList.getList().add(orderTypeField);
					}
                }
                orderTypeFieldList.setIsEdit(true);
                result.put("orderType", orderTypeFieldList);
            }else{
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum调用失败,houseBaseFid={},code={}", requestDto.getHouseBaseFid(),
						ProductRulesEnum.ProductRulesEnum0010.getValue());
			}


			//退订政策
			if(Check.NuNStr(checkOutRulesCode)){//默认适中
				checkOutRulesCode= TradeRulesEnum005Enum.TradeRulesEnum005002.getValue();
			}
			initCancellationPolicy( requestDto,checkOutRulesCode,result);

            //房屋守则
            initHouseRules(requestDto, houseRules, result);
            //判断房屋守则是否在审核中
            if(!Check.NuNObj(result.get("houseRules"))){
	            FieldTextValueVo<String> houseRulesField=(FieldTextValueVo<String>) result.get("houseRules");
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Room_Rules.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
					houseRulesField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
				}
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Rules.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
					houseRulesField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
				}
				result.put("houseRules", houseRulesField);
            }
            //押金获取
            com.ziroom.minsu.services.house.entity.HouseBaseVo houseBaseVo = new com.ziroom.minsu.services.house.entity.HouseBaseVo();
            houseBaseVo.setHouseFid(requestDto.getHouseBaseFid());
			houseBaseVo.setRentWay(requestDto.getRentWay());
			houseBaseVo.setRoomFid(requestDto.getRoomFid());
			String depositMoneyJson=houseIssueService.findHouseOrRoomDeposit(JsonEntityTransform.Object2Json(houseBaseVo));
			HouseConfMsgEntity houseConfMsg=SOAResParseUtil.getValueFromDataByKey(depositMoneyJson, "houseConfMsg", HouseConfMsgEntity.class);
			if(!Check.NuNObj(houseConfMsg)&&!Check.NuNObj(houseConfMsg.getDicVal())){
				houseConfMsg.setDicVal(DecimalCalculate.div(houseConfMsg.getDicVal(), "100", 0).toString());
			}
//			else {
//				houseConfMsg = new HouseConfMsgEntity();
//				houseConfMsg.setDicCode(ProductRulesEnum008Enum.ProductRulesEnum008002.getValue());
//				houseConfMsg.setDicVal("0");
//
//				if(requestDto.getRentWay()==RentWayEnum.HOUSE.getCode() && !Check.NuNObj(houseBaseExtDto)){
//					houseConfMsg.setDicVal(DecimalCalculate.div(String.valueOf(houseBaseExtDto.getLeasePrice()), "100", 0).toString());
//				}else if(requestDto.getRentWay()==RentWayEnum.ROOM.getCode()){
//
//                    Integer priceMax = null;
//                    if (!Check.NuNCollection(roomList)) {
//                        for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
//                            if (Check.NuNObj(priceMax)) {
//                                priceMax = houseRoomMsgEntity.getRoomPrice();
//                            } else if (!Check.NuNObj(houseRoomMsgEntity.getRoomPrice()) && priceMax < houseRoomMsgEntity.getRoomPrice()) {
//                                priceMax = houseRoomMsgEntity.getRoomPrice();
//                            }
//                        }
//                    }
//
//                    if (!Check.NuNObj(priceMax)) {
//                        houseConfMsg.setDicVal(DecimalCalculate.div(String.valueOf(priceMax), "100", 0).toString());
//                    }
//
//				}
//			}
            FieldTextVo<Integer> depositMoneyField = new FieldTextVo<>(true, Integer.valueOf(houseConfMsg.getDicVal()));
            result.put("depositMoney", depositMoneyField);
            result.put("depositMin",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN);
			result.put("depositMax",SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX);

			LogUtil.info(LOGGER, "[initDealPolicy]result={}", JsonEntityTransform.Object2Json(result));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);

		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询交易信息初始化数据异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
     *
     * 初始化房屋守则
     *
	 * @author zl
	 * @created 2017年6月26日 下午8:06:51
	 *
	 * @param requestDto
	 * @param houseRules
	 * @param resultMap
	 */
	private void initHouseRules(HouseBaseParamsDto requestDto,String houseRules,Map<String, Object> resultMap){

		try {
			if(Check.NuNObj(resultMap)){
				return;
			}
			String houseRulesTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEROULES_TEXT");
			StaticResourceVo houseRulesTextVo=SOAResParseUtil.getValueFromDataByKey(houseRulesTextJson, "res", StaticResourceVo.class);
			String houseRulesText = "";
			if(!Check.NuNObj(houseRulesTextVo)){
				houseRulesText = houseRulesTextVo.getResContent();
			}
            String houseRulesExplainJson = staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEROULES_EXPLAIN");
            StaticResourceVo houseRulesExplainVo = SOAResParseUtil.getValueFromDataByKey(houseRulesExplainJson, "res", StaticResourceVo.class);
            String houseRulesExplain = "";
            if (!Check.NuNObj(houseRulesExplainVo)) {
                houseRulesExplain = houseRulesExplainVo.getResContent();
            }
            FieldTextValueVo<String> houseRulesField = new FieldTextValueVo<String>(houseRules, houseRulesText, true, houseRulesExplain);
            resultMap.put("houseRules", houseRulesField);

			//已经选择列表
			List<HouseConfMsgEntity> listFindByFidAndCode =null;
			HouseConfParamsDto paramsDto = new HouseConfParamsDto();
			paramsDto.setHouseBaseFid(requestDto.getHouseBaseFid());
			paramsDto.setRoomFid(requestDto.getRoomFid());
			paramsDto.setRentWay(requestDto.getRentWay());
			paramsDto.setDicCode(ProductRulesEnum.ProductRulesEnum0024.getValue());

			String houseRulesJson = houseIssueService.findHouseConfValidList(JsonEntityTransform.Object2Json(paramsDto));

			LogUtil.info(LOGGER, "[initHouseRules]params={},result={}", JsonEntityTransform.Object2Json(paramsDto),houseRulesJson);

			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseRulesJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				listFindByFidAndCode = SOAResParseUtil.getListValueFromDataByKey(houseRulesJson, "list", HouseConfMsgEntity.class);
			}else{
				LogUtil.error(LOGGER, "troyHouseMgtService.findHouseConfValidList查询房屋守则失败,houseBaseFid={},code={}", requestDto.getHouseBaseFid(),
						ProductRulesEnum.ProductRulesEnum0024.getValue());
			}

            StringBuilder selectNames = new StringBuilder();
            StringBuilder selectValues = new StringBuilder();
			//房屋守则待选列表
			String selectHouseRulesJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum0024.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(selectHouseRulesJson);
			FieldSelectListVo houseRulesSelectFieldList = new FieldSelectListVo();
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				List<EnumVo> selectHouseRulesList = SOAResParseUtil.getListValueFromDataByKey(selectHouseRulesJson, "selectEnum", EnumVo.class);

				if(!Check.NuNCollection(selectHouseRulesList)){
					for (EnumVo enumVo : selectHouseRulesList) {
						FieldSelectVo<String> houseRulesSelect = new FieldSelectVo<String>(enumVo.getKey(),enumVo.getText(),false);
						if(!Check.NuNCollection(listFindByFidAndCode)){
							for (HouseConfMsgEntity conf : listFindByFidAndCode) {
								if(conf.getDicVal().equals(enumVo.getKey())){
									houseRulesSelect.setIsSelect(true);
									selectNames.append(enumVo.getText()).append(subSplist);
									selectValues.append(enumVo.getKey()).append(subSplist);
									break;
								}
							}
						}
                        houseRulesSelectFieldList.getList().add(houseRulesSelect);
                    }

                }

			}else{
				LogUtil.error(LOGGER, "cityTemplateService.getSelectEnum查询房屋可选守则失败,houseBaseFid={},code={}", requestDto.getHouseBaseFid(),
						ProductRulesEnum.ProductRulesEnum0024.getValue());
            }
            houseRulesSelectFieldList.setIsEdit(true);
            resultMap.put("houseRulesSelect", houseRulesSelectFieldList);

            if (selectNames.length() > 0) {
                selectNames.delete(selectNames.length() - 1, selectNames.length());
			}
			if (selectValues.length() > 0) {
				selectValues.delete(selectValues.length() - 1, selectValues.length());
			}
			FieldTextValueVo<String> rulesSelectField = new FieldTextValueVo<String>(selectValues.toString(),
					selectNames.toString(), true);
			resultMap.put("houseRulesSelectValues", rulesSelectField);

        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询房屋守则失败，param={}，e={}", JsonEntityTransform.Object2Json(requestDto),e);
		}


	}


	/**
     *
     * 退订政策初始化
     *
	 * @author zl
	 * @created 2017年6月26日 下午7:57:47
	 *
	 * @param requestDto
	 * @param checkOutRulesCode
	 * @param resultMap
	 */
	private void initCancellationPolicy(HouseBaseParamsDto requestDto,String checkOutRulesCode,Map<String, Object> resultMap){
		try {

			if(Check.NuNObj(resultMap)){
				return;
			}

			//退订政策
			FieldSelectListVo cancellationPolicyList = new FieldSelectListVo();
			List<Map<String, String>> unSelectAbleList = new ArrayList<>();

			String checkOutRulesJson = cityTemplateService.getSelectSubDic(null, TradeRulesEnum.TradeRulesEnum005.getValue());
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(checkOutRulesJson);

            Integer longTermLimit = null;
            if (dto.getCode() == DataTransferObject.ERROR) {
                LogUtil.error(LOGGER, "cityTemplateService.getSelectSubDic调用失败,houseBaseFid={},code={}", requestDto.getHouseBaseFid(),
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
								requestDto.getHouseBaseFid(), dicCode);
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

                            //挪到外面提示
//							if(!Check.NuNObj(longTermLimit)){
//								tradeRulesVo.setLongTermLimit(longTermLimit);
//							}
                            TradeRulesEnum005004001Enum.showContext(tradeRulesVo);
                            String commStr = tradeRulesVo.getCommonShowName();
							if(!Check.NuNStrStrict(commStr)){
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

					String explain = "";
                    if (!Check.NuNStr(tradeRulesVo.getCheckInPreNameM())) {
                        explain += tradeRulesVo.getCheckInPreNameM() + "\r\n";
                    }
                    if(!Check.NuNStr(tradeRulesVo.getCheckInOnNameM())){
						explain += tradeRulesVo.getCheckInOnNameM() + "\r\n";
					}
					if(!Check.NuNStr(tradeRulesVo.getCheckOutEarlyNameM())){
						explain += tradeRulesVo.getCheckOutEarlyNameM() + "\r\n";
					}
					if(!Check.NuNStr(tradeRulesVo.getCommonShowName())){
						explain += tradeRulesVo.getCommonShowName();
					}

					if (!Check.NuNStr(tradeRulesVo.getExplain())) {
						explain = explain + "\r\n" + tradeRulesVo.getExplain();
					}

                    explain = explain.replaceAll(";", "；");
                    if (dicCode.equals(TradeRulesEnum005004Enum.TradeRulesEnum005004001.getValue())) {
                        Map<String, String> text = new HashMap<>();
						text.put("text", checkOutRules.getText());
						text.put("value", checkOutRules.getKey());
						text.put("explain", explain);
						unSelectAbleList.add(text);
					}else{
						FieldSelectVo<String> checkOutRulesField = new FieldSelectVo<String>(checkOutRules.getKey(),checkOutRules.getText(),false);
                        checkOutRulesField.setExplain(explain);
                        if (!Check.NuNStr(checkOutRulesCode) && checkOutRulesCode.equals(checkOutRules.getKey())) {
                            checkOutRulesField.setIsSelect(true);
						}
						cancellationPolicyList.getList().add(checkOutRulesField);
                    }

				}
			}

            cancellationPolicyList.setIsEdit(true);
            resultMap.put("cancellationPolicy", cancellationPolicyList);
            resultMap.put("unSelectcancellationPolicy", unSelectAbleList);

			String cancelpolicyText = "";
            if (!Check.NuNObj(longTermLimit)) {
                cancelpolicyText = String.format(TradeRulesEnum005005001Enum.TradeRulesEnum005005001005.getName(), longTermLimit);
            }
            resultMap.put("cancellationPolicyTips", cancelpolicyText);
        } catch (Exception e) {
			LogUtil.error(LOGGER, "查询退订政策失败，param={}，e={}", JsonEntityTransform.Object2Json(requestDto),e);
		}
	}


	/**
     *
     * 保存退订政策数据
     *
	 * @author zl
	 * @created 2017年6月14日 下午3:54:14
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/${LOGIN_AUTH}/saveDealPolicy", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveDealPolicy(HttpServletRequest request) {

		try {

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveDealPolicy]参数={}", paramJson);
			//判断参数是否为空
			if(Check.NuNStr(paramJson)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数不能为空"), HttpStatus.OK);
			}
			//判断参数类型是否正确
			if(!BaseMethodUtil.isNumberKey(paramJson, "depositMoney")){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("押金参数不合法"), HttpStatus.OK);
			}
			ValidateResult<CancellationPolicyDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, CancellationPolicyDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
			}
			CancellationPolicyDto requestDto = validateResult.getResultObj();
			if(Check.NuNObj(RentWayEnum.getRentWayByCode(requestDto.getRentWay()))){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式不正确"), HttpStatus.OK);
			}
			if(Check.NuNObj(requestDto.getOrderType()) || Check.NuNObj(OrderTypeEnum.getOrderTypeByCode(requestDto.getOrderType()))){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("预定类型不正确"), HttpStatus.OK);
			}
			//			if(Check.NuNStr(requestDto.getHouseRules())|| Check.NuNStr(requestDto.getHouseRulesArray()) || requestDto.getHouseRulesArray().split(splist).length==0){
			//				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房屋守则参数错误"), HttpStatus.OK);
			//			}
			int depositMin = SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MIN;
			int depositMax = SysConst.Deposit.LANDLORDUID_DEPOSIT_RULES_CODE_MAX;
			if(!Check.NuNObj(requestDto.getDepositMoney()) && requestDto.getDepositMoney()<depositMin ){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("押金不正确"), HttpStatus.OK);
			}
			if(!Check.NuNObj(requestDto.getDepositMoney()) &&requestDto.getDepositMoney()>depositMax){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("押金设置过大"), HttpStatus.OK);
			}

            if (Check.NuNObj(requestDto.getDepositMoney())) {//如果不填默认为0
                requestDto.setDepositMoney(0);
			}

            if (Check.NuNStr(requestDto.getCancellationPolicy()) || Check.NuNObj(TradeRulesEnum005Enum.getEnumByValue(requestDto.getCancellationPolicy()))) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("退订政策设置不正确"), HttpStatus.OK);
			}

			if(requestDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				requestDto.setStep(HouseIssueStepEnum.FOUR.getCode());
			}else if(requestDto.getRentWay()==RentWayEnum.ROOM.getCode()){
				requestDto.setStep(HouseIssueStepEnum.FIVE.getCode());
			}
			//保存修改记录 --- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay());

			String resultJson = houseIssueService.saveDealPolicy(JsonEntityTransform.Object2Json(requestDto));

            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
            LogUtil.info(LOGGER, "[saveDealPolicy]result={}", dto.toJsonString());

			if(dto.getCode() == DataTransferObject.SUCCESS){
				//保存修改记录 ---- 保存
				if(!Check.NuNObj(houseUpdateHistoryLogDto)){
					houseUpdateHistoryLogDto.setHouseFid(requestDto.getHouseBaseFid());
					houseUpdateHistoryLogDto.setRentWay(requestDto.getRentWay());
					houseUpdateHistoryLogDto.setRoomFid(requestDto.getRoomFid());
					houseUpdateLogService.saveHistoryLog(requestDto,request,houseUpdateHistoryLogDto);
				}
			}
			LogUtil.info(LOGGER, "保存退订政策返回值： {}", JsonEntityTransform.Object2Json(dto));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(dto), HttpStatus.OK);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "保存退订政策数据异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}


	/**
     *
     * 第四步 初始化入住信息页面
     *
	 * @author zl
	 * @created 2017年6月15日 上午10:52:36
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/${LOGIN_AUTH}/initCheckInMsg")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initCheckInMsg(HttpServletRequest request) {

		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "【initCheckInMsg】参数={}", paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();

			String resultJson = houseIssueAppService.searchHouseCheckInMsg(JsonEntityTransform.Object2Json(requestDto));
			LogUtil.info(LOGGER,"searchHouseCheckInMsg result={}",resultJson);
			DataTransferObject resutDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (resutDto.getCode() == DataTransferObject.ERROR){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resutDto), HttpStatus.OK);
			}

			Integer minDay = (Integer)resutDto.getData().get("minDay");
			String checkInTime = (String) resutDto.getData().get("checkInTime");
			String checkOutTime = (String) resutDto.getData().get("checkOutTime");
			Map<String, Object> result = new HashMap<>();
			
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), 0);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Min_Day.getFieldPath())){
				minDay=Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Min_Day.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Min_Day.getFieldPath())){
				minDay=Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Min_Day.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Check_In_Time.getFieldPath())){
				checkInTime=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Check_In_Time.getFieldPath()).getNewValue();
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Check_In_Time.getFieldPath())){
				checkInTime=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Check_In_Time.getFieldPath()).getNewValue();
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Check_Out_Time.getFieldPath())){
				checkOutTime=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Check_Out_Time.getFieldPath()).getNewValue();
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Ext_Check_Out_Time.getFieldPath())){
				checkOutTime=houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Ext_Check_Out_Time.getFieldPath()).getNewValue();
			}
			/**要审核字段替换结束**/

			result.put("minDay", listSelectValue(ProductRulesEnum.ProductRulesEnum0016.getValue(), String.valueOf(minDay)));
			result.put("checkInTime", listSelectValue(ProductRulesEnum.ProductRulesEnum003.getValue(), checkInTime));
			result.put("checkOutTime",listSelectValue(ProductRulesEnum.ProductRulesEnum004.getValue(),checkOutTime));
			LogUtil.info(LOGGER,"initCheckInMsg 结果={}",JsonEntityTransform.Object2Json(result));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "initCheckInMsg，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
	 *
	 * 根据枚举code获取列表的值
	 *
	 */
	private FieldSelectListVo listSelectValue(String code,String selectValue){
		FieldSelectListVo fieldSelectListVo = new FieldSelectListVo();
		String orderTypeJson = cityTemplateService.getSelectEnum(null, code);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(orderTypeJson);
		if (dto.getCode() == DataTransferObject.SUCCESS) {
			List<EnumVo> list = dto.parseData("selectEnum", new TypeReference<List<EnumVo>>() {});
			for (EnumVo enumVo : list){
				FieldSelectVo mindayField = null;
				if (enumVo.getKey().equals(selectValue)){
					mindayField = new FieldSelectVo(enumVo.getKey(),enumVo.getText(),true);
				}else{
					mindayField = new FieldSelectVo(enumVo.getKey(),enumVo.getText(),false);
				}
				fieldSelectListVo.getList().add(mindayField);
			}
		}
		fieldSelectListVo.setIsEdit(true);
		return fieldSelectListVo;
	}

	/**
     *
     * 保存入住信息
     *
	 * @author zl
	 * @created 2017年6月15日 上午11:04:05
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveCheckInMsg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveCheckInMsg(HttpServletRequest request) {

		try {
			Header header = getHeader(request);
            String uid = getUserId(request);

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveCheckInMsg]参数：" + paramJson);

			ValidateResult<HouseCheckInMsgDto> validateResult =
					paramCheckLogic.checkParamValidate(paramJson, HouseCheckInMsgDto.class);
            if (!validateResult.isSuccess()) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
                        HttpStatus.OK);
			}
			HouseCheckInMsgDto requestDto = validateResult.getResultObj();

			if(requestDto.getRentWay()==RentWayEnum.HOUSE.getCode()){
				requestDto.setStep(HouseIssueStepEnum.FIVE.getCode());
			}else if(requestDto.getRentWay()==RentWayEnum.ROOM.getCode()){
				requestDto.setStep(HouseIssueStepEnum.SIX.getCode());
			}

			//保存修改记录 ---- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay());

			String resultJson = houseIssueAppService.saveHouseCheckInMsg(JsonEntityTransform.Object2Json(requestDto));

			//返回数据
			LogUtil.info(LOGGER,"saveCheckInMsg 保存结果={}",resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);

			if(resultDto.getCode() == DataTransferObject.SUCCESS){
				//保存修改记录 ---- 保存
				if(!Check.NuNObj(houseUpdateHistoryLogDto)){
					houseUpdateHistoryLogDto.setHouseFid(requestDto.getHouseBaseFid());
					houseUpdateHistoryLogDto.setRentWay(requestDto.getRentWay());
					houseUpdateHistoryLogDto.setRoomFid(requestDto.getRoomFid());
					houseUpdateLogService.saveHistoryLog(requestDto, request,houseUpdateHistoryLogDto);
				}
			}
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveCheckInMsg，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}


	/**
	 * 删除房间信息
	 * @author jixd
	 * @created 2017年06月28日 19:39:52
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/updateRoomNumAndRoomMsg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> updateRoomNumAndRoomMsg(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "【updateRoomNumAndRoomMsg】参数 ={}", paramJson);
			HouseRoomUpDto houseRoomUpDto = JsonEntityTransform.json2Object(paramJson,HouseRoomUpDto.class);
			if (Check.NuNStr(houseRoomUpDto.getHouseBaseFid())){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源fid为空"), HttpStatus.OK);
			}
			houseRoomUpDto.setStep(HouseIssueStepEnum.FOUR.getCode());
			String resultJson = houseIssueAppService.updateRoomNumAndRoomMsg(JsonEntityTransform.Object2Json(houseRoomUpDto));
			LogUtil.info(LOGGER,"【updateRoomNumAndRoomMsg】 结果={}",resultJson);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(resultDto), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "updateRoomNumAndRoomMsg，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 *
	 * 整租描述及基础信息修改接口
	 *
	 * @author lusp
	 * @created 2017年6月28日 上午10:44:43
	 * @param request
	 * @return
	 */
	@SuppressWarnings({"unchecked" })
	@RequestMapping(value = "/${LOGIN_AUTH}/initDescAndBaseInfoEntire")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initDescAndBaseInfoEntire(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initDescAndBaseInfoEntire]参数：" + paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();
			String houseBaseFid = requestDto.getHouseBaseFid();
			Integer rentWay = requestDto.getRentWay();

			if(rentWay != RentWayEnum.HOUSE.getCode()){
				LogUtil.error(LOGGER, "initDescAndBaseInfoEntire(),app前端回传出租方式异常，整租初始化接口回传出租方式不为整租，参数 : {}", paramJson);
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
			}

			String housePhyExtJson=houseIssueAppService.searchHousePhyAndExt(houseBaseFid);
			HousePhyExtVo housePhyExtVo=SOAResParseUtil.getValueFromDataByKey(housePhyExtJson,"housePhyExtVo", HousePhyExtVo.class);
			if(Check.NuNObj(housePhyExtVo)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
			}
			Integer houseStatus = housePhyExtVo.getHouseBaseMsgEntity().getHouseStatus();

			String resultJson=houseIssueAppService.searchHouseConfAndPrice(houseBaseFid);
			HouseBaseExtDto houseBaseExtDto=SOAResParseUtil.getValueFromDataByKey(resultJson, "houseBaseExtDto", HouseBaseExtDto.class);
			HouseDescAndBaseInfoVo houseDescAndBaseInfoVo = new HouseDescAndBaseInfoVo();
			
			/***********************************组装房源描述信息***********************************/
			String houseDescJson=houseIssueService.findHouseInputDetail(houseBaseFid);
			HouseBaseDetailVo houseBaseDetailVo=SOAResParseUtil.getValueFromDataByKey(houseDescJson, "houseDetail", HouseBaseDetailVo.class);
			
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), 0);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath())){
				houseBaseDetailVo.setHouseName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath())){
				houseBaseDetailVo.setHouseDesc(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath())){
				houseBaseDetailVo.setHouseAroundDesc(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Area.getFieldPath())){
				houseBaseExtDto.setHouseArea(Double.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_House_Area.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Check_In_Limit.getFieldPath())){
				houseBaseExtDto.getHouseBaseExt().setCheckInLimit(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Check_In_Limit.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath())){
				houseBaseExtDto.setRoomNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())){
				houseBaseExtDto.setHallNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath())){
				houseBaseExtDto.setToiletNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath())){
				houseBaseExtDto.setKitchenNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath())){
				houseBaseExtDto.setBalconyNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath()).getNewValue()));
			}
			/**要审核字段替换结束**/
			
			if(rentWay==RentWayEnum.HOUSE.getCode()){
				String houseNameTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSENAME_TEXT");
				StaticResourceVo houseNameText=SOAResParseUtil.getValueFromDataByKey(houseNameTextJson, "res", StaticResourceVo.class);
				FieldTextValueVo<String> houseNameField=new FieldTextValueVo<String>(houseBaseDetailVo.getHouseName(), houseNameText.getResContent(),true, "");
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_House_Name.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
					houseNameField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
				}
				houseDescAndBaseInfoVo.setHouseName(houseNameField);
			}
			//查询房源描述配置
			String houseDescTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEDESC_TEXT");
			StaticResourceVo houseDescText=SOAResParseUtil.getValueFromDataByKey(houseDescTextJson, "res", StaticResourceVo.class);
			String houseDescExplainJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEDESC_EXPLAIN");
			StaticResourceVo houseDescExplain=SOAResParseUtil.getValueFromDataByKey(houseDescExplainJson, "res", StaticResourceVo.class);
			FieldTextValueVo<String> houseDescField=new FieldTextValueVo<String>(houseBaseDetailVo.getHouseDesc(), houseDescText.getResContent(),true, houseDescExplain.getResContent());
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
				houseDescField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
			}
			houseDescAndBaseInfoVo.setHouseDesc(houseDescField);
			//查询房源周边信息配置
			String houseAroundTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_TEXT");
			StaticResourceVo houseAroundText=SOAResParseUtil.getValueFromDataByKey(houseAroundTextJson, "res", StaticResourceVo.class);
			String houseAroundExplainJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_EXPLAIN");
			StaticResourceVo houseAroundExplain=SOAResParseUtil.getValueFromDataByKey(houseAroundExplainJson, "res", StaticResourceVo.class);
			FieldTextValueVo<String> houseAroundField=new FieldTextValueVo<String>(houseBaseDetailVo.getHouseAroundDesc(), houseAroundText.getResContent(),true, houseAroundExplain.getResContent());
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
				houseAroundField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
			}
			houseDescAndBaseInfoVo.setHouseAroundDesc(houseAroundField);
			/***********************************组装房源描述信息***********************************/


			//封装房源户型数据
			if(!Check.NuNObj(houseBaseExtDto)){
				houseDescAndBaseInfoVo.setHouseArea(new FieldTextValueVo<Double>(houseBaseExtDto.getHouseArea(), houseBaseExtDto.getHouseArea()+"平米", true));
				//房源户型
				StringBuilder modelText=new StringBuilder();
				StringBuilder modeValue=new StringBuilder();
				modelText.append(houseBaseExtDto.getRoomNum()).append("室 ").append(houseBaseExtDto.getHallNum()).append("厅 ").append(houseBaseExtDto.getToiletNum()).append("卫 ").append(houseBaseExtDto.getKitchenNum())
                        .append("厨 ").append(houseBaseExtDto.getBalconyNum()).append("阳台");
                modeValue.append(houseBaseExtDto.getRoomNum()).append(",").append(houseBaseExtDto.getHallNum()).append(",").append(houseBaseExtDto.getToiletNum()).append(",").append(houseBaseExtDto.getKitchenNum())
                        .append(",").append(houseBaseExtDto.getBalconyNum());
                houseDescAndBaseInfoVo.setHouseModel(new FieldTextValueVo<String>(modeValue.toString(), modelText.toString(), true));
				houseDescAndBaseInfoVo.setRoomNum(houseBaseExtDto.getRoomNum());
				houseDescAndBaseInfoVo.setParlorNum(houseBaseExtDto.getHallNum());
				houseDescAndBaseInfoVo.setToiletNum(houseBaseExtDto.getToiletNum());
				houseDescAndBaseInfoVo.setKitchenNum(houseBaseExtDto.getKitchenNum());
				houseDescAndBaseInfoVo.setBalconyNum(houseBaseExtDto.getBalconyNum());
			}
			//查询入住人数限制
			String checkInLimitJson=cityTemplateService.getEffectiveSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
			List<EnumVo> list=SOAResParseUtil.getListValueFromDataByKey(checkInLimitJson, "selectEnum", EnumVo.class);
			for(EnumVo vo:list){
				if(!Check.NuNObjs(houseBaseExtDto,houseBaseExtDto.getHouseBaseExt())&&vo.getKey().equals(houseBaseExtDto.getHouseBaseExt().getCheckInLimit()+"")){
					houseDescAndBaseInfoVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(Integer.valueOf(vo.getKey()), vo.getText(), true));
				} else {
					houseDescAndBaseInfoVo.getCheckInLimit().getList().add(new FieldSelectVo<Integer>(Integer.valueOf(vo.getKey()), vo.getText(), false));
				}
			}
			//初始化便利设施
			Map<String, Object> resultMap=new HashMap<String,Object>();
            initFacilities(houseBaseFid, null, resultMap);
            houseDescAndBaseInfoVo.setHouseFacility((FieldTextValueVo<String>) resultMap.get("houseFacility"));
            houseDescAndBaseInfoVo.setGroupFacilityList((List) resultMap.get("houseFacilityGroup"));
			//床类型列表
			String bedTypeJson = cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
			List<EnumVo> bedTypeList = SOAResParseUtil.getListValueFromDataByKey(bedTypeJson, "selectEnum", EnumVo.class);
			for(EnumVo vo:bedTypeList){
				houseDescAndBaseInfoVo.getBedTypeList().getList().add(new FieldSelectVo<String>(vo.getKey(), vo.getText(),getBedTypeDes(Integer.valueOf(vo.getKey())), false));
			}
			//房源房间初始化
			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseExtDto.getFid());
			List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list",HouseRoomMsgEntity.class);
			for(HouseRoomMsgEntity room:roomList){
				Map<String, Object> roomMap=new HashMap<>();
				roomMap.put("roomFid", room.getFid());
				String bedListJson = houseIssueService.searchBedListByRoomFid(room.getFid());
				List<HouseBedMsgEntity> bedMsgList = SOAResParseUtil.getListValueFromDataByKey(bedListJson, "list", HouseBedMsgEntity.class);
				List<FieldTextValueVo<Integer>> bedList=new ArrayList<>();
				for(EnumVo vo:bedTypeList){
					Integer typeNum=0;
					for(HouseBedMsgEntity bed:bedMsgList){
						if(vo.getKey().equals(bed.getBedType()+"")){
							typeNum++;
						}
					}
					if(typeNum>0){
						FieldTextValueVo<Integer> bed=new FieldTextValueVo<Integer>(typeNum, vo.getKey(), true);
						bedList.add(bed);
					}
				}
				roomMap.put("bedList", bedList);
				houseDescAndBaseInfoVo.getHouseRoomList().add(roomMap);
			}
			// 房源户型限制信息
			Map<String, Integer> houseLimitMap = new HashMap<>();
			String houseLimitJson = cityTemplateService.getTextListByLikeCodes(null,ProductRulesEnum.ProductRulesEnum0027.getValue());
			List<MinsuEleEntity> houseLimitList = SOAResParseUtil.getListValueFromDataByKey(houseLimitJson, "confList",MinsuEleEntity.class);

			if (!Check.NuNCollection(houseLimitList)) {
				for (MinsuEleEntity minsuEleEntity : houseLimitList) {
					houseLimitMap.put(minsuEleEntity.getEleKey(), ValueUtil.getintValue(minsuEleEntity.getEleValue()));
				}
			}
			houseDescAndBaseInfoVo.setMaxRoom(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()));
			houseDescAndBaseInfoVo.setMaxParlor(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()));
			houseDescAndBaseInfoVo.setMaxToilet(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()));
			houseDescAndBaseInfoVo.setMaxKitchen(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()));
			houseDescAndBaseInfoVo.setMaxBalcony(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()));
			houseDescAndBaseInfoVo.setHouseBaseFid(houseBaseExtDto.getFid());
			houseDescAndBaseInfoVo.setRentWay(houseBaseExtDto.getRentWay());

			if(HouseStatusEnum.SJ.getCode() == houseStatus){
				houseDescAndBaseInfoVo.getHouseModel().setIsEdit(false);
				houseDescAndBaseInfoVo.getHouseArea().setIsEdit(false);
			}
			LogUtil.info(LOGGER, "修改初始化户型描述：" + JsonEntityTransform.Object2Json(houseDescAndBaseInfoVo));
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseDescAndBaseInfoVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "房源管理描述及基础信息回显失败，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 * 分租描述及基础信息修改接口
	 * @author lusp
	 * @created 2017年6月29日 上午10:17:17
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/initDescAndBaseInfoSublet")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> initDescAndBaseInfoSublet(HttpServletRequest request) {

		try {
			String uid = getUserId(request);

			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[initDescAndBaseInfoSublet]uid={},参数={}", uid, paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();

			if (Check.NuNStr(requestDto.getRoomFid()) || Check.NuNObj(requestDto.getRentWay())
					|| Check.NuNObj(RentWayEnum.getRentWayByCode(requestDto.getRentWay()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
			}

			String houseBaseFid = requestDto.getHouseBaseFid();
            String roomFid = requestDto.getRoomFid();

			HouseBaseExtDto houseBaseMsgEntity = null;
			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
			}

			if (Check.NuNObj(houseBaseMsgEntity)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
			}

			Map<String, Object> result = new HashMap<>();
			//查询房源是否共享客厅
			String isHallJson=houseIssueService.isHallByHouseBaseFid(houseBaseFid);
			Integer isHall=SOAResParseUtil.getIntFromDataByKey(isHallJson, "isHall");
			result.put("roomType", isHall);
			/***********************************组装房源描述信息***********************************/
			String houseDescJson=houseIssueService.findHouseInputDetail(houseBaseFid);
			HouseBaseDetailVo houseBaseDetailVo=SOAResParseUtil.getValueFromDataByKey(houseDescJson, "houseDetail", HouseBaseDetailVo.class);
			/**要审核字段替换开始**/
			Map<String , HouseFieldAuditLogVo> houseFieldAuditMap= houseService.houseFieldAuditLogVoConvertMap(requestDto.getHouseBaseFid(), requestDto.getRoomFid(), requestDto.getRentWay(), 0);
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath())){
				houseBaseDetailVo.setHouseDesc(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath())){
				houseBaseDetailVo.setHouseAroundDesc(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath()).getNewValue());
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Is_Together_Landlord.getFieldPath())){
				houseBaseMsgEntity.getHouseBaseExt().setCheckInLimit(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Is_Together_Landlord.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Ext_Rent_Room_Num.getFieldPath())){
				houseBaseMsgEntity.getHouseBaseExt().setRentRoomNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Ext_Rent_Room_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath())){
				houseBaseMsgEntity.setRoomNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Room_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath())){
				houseBaseMsgEntity.setHallNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Hall_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath())){
				houseBaseMsgEntity.setToiletNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Toilet_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath())){
				houseBaseMsgEntity.setKitchenNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Kitchen_Num.getFieldPath()).getNewValue()));
			}
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath())){
				houseBaseMsgEntity.setBalconyNum(Integer.valueOf(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Base_Msg_Balcony_Num.getFieldPath()).getNewValue()));
			}
			/**要审核字段替换结束**/
//			if(rentWay==RentWayEnum.HOUSE.getCode()){
//				String houseNameTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSENAME_TEXT");
//				StaticResourceVo houseNameText=SOAResParseUtil.getValueFromDataByKey(houseNameTextJson, "res", StaticResourceVo.class);
//				result.put("houseName",new FieldTextValueVo<String>(houseBaseDetailVo.getHouseName(), houseNameText.getResContent(),true, ""));
//			}
			
			/*****************************初始化房间信息*****************************/
			String roomJson = houseIssueService.searchHouseRoomMsgByFid(requestDto.getRoomFid());
			DataTransferObject roomDto =JsonEntityTransform.json2DataTransferObject(roomJson);
			HouseRoomMsgEntity houseRoomMsg = null;
			if(roomDto.getCode()==DataTransferObject.SUCCESS){
				houseRoomMsg = SOAResParseUtil.getValueFromDataByKey(roomJson, "obj", HouseRoomMsgEntity.class);
			}
			if(Check.NuNObj(houseRoomMsg)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间不存在"), HttpStatus.OK);
			}
			Integer houseStatus=houseRoomMsg.getRoomStatus();
            //查询房源描述配置
            String houseDescTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEDESC_TEXT");
			StaticResourceVo houseDescText=SOAResParseUtil.getValueFromDataByKey(houseDescTextJson, "res", StaticResourceVo.class);
			String houseDescExplainJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEDESC_EXPLAIN");
			StaticResourceVo houseDescExplain=SOAResParseUtil.getValueFromDataByKey(houseDescExplainJson, "res", StaticResourceVo.class);
			FieldTextValueVo<String> houseDescFiled=new FieldTextValueVo<String>(houseBaseDetailVo.getHouseDesc(), houseDescText.getResContent(),true, houseDescExplain.getResContent()); 
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Desc.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
				houseDescFiled.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
			}
			result.put("houseDesc",houseDescFiled);
			//查询房源周边信息配置
			String houseAroundTextJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_TEXT");
			StaticResourceVo houseAroundText=SOAResParseUtil.getValueFromDataByKey(houseAroundTextJson, "res", StaticResourceVo.class);
			String houseAroundExplainJson=staticResourceService.findStaticResByResCode("HOUSE_ISSUE_HOUSEAROUND_EXPLAIN");
			StaticResourceVo houseAroundExplain=SOAResParseUtil.getValueFromDataByKey(houseAroundExplainJson, "res", StaticResourceVo.class);
			FieldTextValueVo<String> houseAroundField=new FieldTextValueVo<String>(houseBaseDetailVo.getHouseAroundDesc(), houseAroundText.getResContent(),true, houseAroundExplain.getResContent());
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Desc_House_Around_Desc.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
				houseAroundField.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
			}
			result.put("houseAroundDesc",houseAroundField);
			/***********************************组装房源描述信息***********************************/

			FieldSelectListVo livewithLandList = new FieldSelectListVo();
			FieldSelectVo<Integer> livewithLandYES = new FieldSelectVo<Integer>(YesOrNoEnum.YES.getCode(),
					YesOrNoEnum.YES.getName(), false);
			FieldSelectVo<Integer> livewithLandNO = new FieldSelectVo<Integer>(YesOrNoEnum.NO.getCode(),
					YesOrNoEnum.NO.getName(), false);
			if (!Check.NuNObj(houseBaseMsgEntity) && !Check.NuNObj(houseBaseMsgEntity.getHouseBaseExt())
					&& !Check.NuNObj(houseBaseMsgEntity.getHouseBaseExt().getIsTogetherLandlord())) {
				if (houseBaseMsgEntity.getHouseBaseExt().getIsTogetherLandlord() == YesOrNoEnum.YES.getCode()) {
					livewithLandYES.setIsSelect(true);
				} else {
					livewithLandNO.setIsSelect(true);
				}
				FieldTextVo<Integer> livewithLandField = new FieldTextVo<Integer>(true,
						houseBaseMsgEntity.getHouseBaseExt().getIsTogetherLandlord());
				result.put("isTogetherLandlord", livewithLandField);
			}
			livewithLandList.getList().add(livewithLandYES);
			livewithLandList.getList().add(livewithLandNO);
			livewithLandList.setIsEdit(true);
			result.put("isTogetherLandlordList", livewithLandList);
			//初始化户型信息
            initHouseLimit(houseBaseMsgEntity, null, result);
            // 便利设施
            initFacilities(houseBaseFid, null, result);

            /**********初始化可出租房间数（t_house_base_ext表中的rent_room_num）**********/
            FieldTextVo<Integer> rentRoomNum = new FieldTextVo<Integer>(false,
                    houseBaseMsgEntity.getHouseBaseExt().getRentRoomNum());
            result.put("rentRoomNum", rentRoomNum);
            /*****************************初始化可出租房间数*****************************/

			/**房间名称替换**/
			if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())){
				houseBaseDetailVo.setHouseName(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
			}
			/**房间名称替换**/
			if(!Check.NuNStr(houseRoomMsg.getRoomName())){
				FieldTextVo<String> field = new FieldTextVo<String>(true,houseRoomMsg.getRoomName());
				if(houseFieldAuditMap.containsKey(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath())&&HouseStatusEnum.SJ.getCode()==houseStatus){
					field.setAuditMsg(ApiMessageConst.FIELD_AUDIT_MSG);
					field.setValue(houseFieldAuditMap.get(HouseUpdateLogEnum.House_Room_Msg_Room_Name.getFieldPath()).getNewValue());
				}
				result.put("roomName", field);
			}
			if(!Check.NuNObj(houseRoomMsg.getRoomArea())){
				FieldTextVo<Double> field = new FieldTextVo<Double>(false,houseRoomMsg.getRoomArea());
				result.put("houseArea", field);
			}
			//是否独立卫生间
			FieldSelectListVo isToiletList = new FieldSelectListVo();
			FieldSelectVo<Integer> isToiletListYES = new FieldSelectVo<Integer>(YesOrNoEnum.YES.getCode(),YesOrNoEnum.YES.getName(), false);
			FieldSelectVo<Integer> isToiletListNO = new FieldSelectVo<Integer>(YesOrNoEnum.NO.getCode(),YesOrNoEnum.NO.getName(), false);
			if(!Check.NuNObj(houseRoomMsg.getIsToilet())){
				if (houseRoomMsg.getIsToilet() == YesOrNoEnum.YES.getCode()) {
					isToiletListYES.setIsSelect(true);
				} else {
					isToiletListNO.setIsSelect(true);
				}
				FieldTextVo<Integer> isToiletField = new FieldTextVo<Integer>(true,houseRoomMsg.getIsToilet());
				result.put("isToilet", isToiletField);
			}
			isToiletList.getList().add(isToiletListYES);
			isToiletList.getList().add(isToiletListNO);
			isToiletList.setIsEdit(true);
			result.put("isToiletList", isToiletList);
			//入住人数限制
			String limitJson= cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum009.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(limitJson);
			FieldSelectListVo limitPersonList = new FieldSelectListVo();
			if(dto.getCode() == DataTransferObject.SUCCESS){
				List<EnumVo> limitList = SOAResParseUtil.getListValueFromDataByKey(limitJson, "selectEnum", EnumVo.class);
				if (!Check.NuNCollection(limitList)) {
					for (EnumVo enumVo : limitList) {
						FieldSelectVo<Integer> limitListSelect = new FieldSelectVo<Integer>(ValueUtil.getintValue(enumVo.getKey()),enumVo.getText(), false);
						if(!Check.NuNObj(houseRoomMsg.getCheckInLimit()) && houseRoomMsg.getCheckInLimit()==ValueUtil.getintValue(enumVo.getKey())){
							limitListSelect.setIsSelect(true);
						}
						limitPersonList.getList().add(limitListSelect);
					}
				}
			}
			limitPersonList.setIsEdit(true);
			result.put("limitPersonList", limitPersonList);
			if(!Check.NuNObj(houseRoomMsg.getCheckInLimit())){
				FieldTextVo<Integer> limitPersonField = new FieldTextVo<Integer>(true,houseRoomMsg.getCheckInLimit());
				result.put("limitPerson", limitPersonField);
			}
			//初始化床位信息
//            initBedInfo(result, requestDto.getRoomFid(), null);

			/*房间信息状态计算    （0=未完成  1=完成） 校验当前房源下 所有房间是否都有床位，有就完成，无就未完成
			  计算公式：
	          整租，分租都校验是否当前房间有床位，即可，有就是完成，无就是未完成*/
            String roomBedListJson = houseIssueService.searchBedListByRoomFid(roomFid);
            DataTransferObject roomBedListDto = JsonEntityTransform.json2DataTransferObject(roomBedListJson);
            if(roomBedListDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "根据roomFid获取床列表失败",roomBedListDto.getMsg());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("系统异常"),HttpStatus.OK);
			}
			List<HouseBedMsgEntity> roomBedList = null;
			try {
				roomBedList = SOAResParseUtil.getListValueFromDataByKey(roomBedListJson,"list",HouseBedMsgEntity.class);
			} catch (SOAParseException e) {
				LogUtil.error(LOGGER, "SOAResParseUtil 转换异常",e);
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncryptFail("系统异常"),
						HttpStatus.OK);
			}
			if(!Check.NuNCollection(roomBedList)){
				result.put("roomStatus", RoomFinishStatusEnum.FINISH.getCode());
			}else{
				result.put("roomStatus", RoomFinishStatusEnum.UN_FINISH.getCode());
			}

			//判断该房源状态，如果为已上架，则户型不可修改，如果为待审核（非常规操作下会出现），也是不可修改
			if(!Check.NuNObj(houseStatus)){
				if(houseStatus == HouseStatusEnum.SJ.getCode()){
					((FieldTextValueVo<String>) result.get("houseModel")).setIsEdit(false);
					result.put("immutableMsg", "已上架房源无法修改");
				}else if(houseStatus == HouseStatusEnum.YFB.getCode()){
					((FieldTextValueVo<String>) result.get("houseModel")).setIsEdit(false);
					result.put("immutableMsg", "审核中房源无法修改");
				}
			}

            /*****************************初始化房间信息*****************************/
            LogUtil.info(LOGGER, "initDescAndBaseInfoSublet 结果={}", JsonEntityTransform.Object2Json(result));
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(result), HttpStatus.OK);
        } catch (Exception e) {
			LogUtil.error(LOGGER, "分租房源管理描述及基础信息初始化异常，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}

	}

	/**
	 *
	 * 房源管理时描述及基础信息保存（整租）
	 *
	 * @author lusp
	 * @created 2017年6月29日 上午10:13:31
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveDescAndBaseInfoEntire")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveDescAndBaseInfoEntire(HttpServletRequest request) {
		try {
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "saveDescAndBaseInfoEntire(),参数：" + paramJson);

			ValidateResult<HouseDescAndBaseInfoDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseDescAndBaseInfoDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseDescAndBaseInfoDto houseDescAndBaseInfoDto = validateResult.getResultObj();

			/*********************房源名称、描述长度校验*********************/
			if(houseDescAndBaseInfoDto.getHouseName().length()<10||houseDescAndBaseInfoDto.getHouseName().length()>30){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源名称应为10-30个字符"), HttpStatus.OK);
			}
			if(houseDescAndBaseInfoDto.getHouseDesc().length()<100||houseDescAndBaseInfoDto.getHouseDesc().length()>1000){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源描述应为100-1000个字符"), HttpStatus.OK);
			}
			if(houseDescAndBaseInfoDto.getHouseAroundDesc().length()<100||houseDescAndBaseInfoDto.getHouseAroundDesc().length()>1000){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源周边描述应为100-1000个字符"), HttpStatus.OK);
			}
			/*********************房源名称、描述长度校验*********************/

			//获取房东uid
			String uid = getUserId(request);
			houseDescAndBaseInfoDto.setCreateFid(uid);

			/**判断床数量**/
			Integer bedNum=0;
			if(!Check.NuNCollection(houseDescAndBaseInfoDto.getHouseRoomList())){
				for(HouseRoomVo vo:houseDescAndBaseInfoDto.getHouseRoomList()){
					if(!Check.NuNStr(vo.getBedMsg())){
						for(String bed:vo.getBedMsg().split(",")){
							Integer num=Integer.valueOf(bed.split("_")[1]);
							bedNum+=num;
						}
					}
				}
			}
			if(bedNum==0){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间内至少有一张床"), HttpStatus.OK);
			}

			//保存修改记录 ---- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(houseDescAndBaseInfoDto.getHouseBaseFid(), houseDescAndBaseInfoDto.getRoomFid(), houseDescAndBaseInfoDto.getRentWay());

			String resultJson = houseIssueAppService.saveHouseDescAndBaseInfoEntire(JsonEntityTransform.Object2Json(houseDescAndBaseInfoDto));
			DataTransferObject dto=JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode()==DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "saveDescAndBaseInfoEntire(), 整租房源管理第二步保存错误");
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
			}

			//保存修改记录 ---- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setHouseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
				houseUpdateHistoryLogDto.setRentWay(houseDescAndBaseInfoDto.getRentWay());
				houseUpdateHistoryLogDto.setRoomFid(houseDescAndBaseInfoDto.getRoomFid());
				houseUpdateLogService.saveHistoryLog(houseDescAndBaseInfoDto, request,houseUpdateHistoryLogDto);
			}

			HouseBaseVo houseBaseVo=new HouseBaseVo();
			houseBaseVo.setHouseBaseFid(houseDescAndBaseInfoDto.getHouseBaseFid());
			houseBaseVo.setRentWay(houseDescAndBaseInfoDto.getRentWay());
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(houseBaseVo), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveDescAndBaseInfoEntire(), 整租房源管理第二步保存错误 error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}


	/**
	 *
	 * 房源管理时描述及基础信息保存（分租）
	 *
	 * @author lusp
	 * @created 2017年6月29日 上午11:13:31
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/saveDescAndBaseInfoSublet")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> saveDescAndBaseInfoSublet(HttpServletRequest request) {
		try {
			String uid = getUserId(request);
			String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
			LogUtil.info(LOGGER, "[saveDescAndBaseInfoSublet]uid={}，参数={}", uid, paramJson);

			ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
					HouseBaseParamsDto.class);
			if (!validateResult.isSuccess()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
						HttpStatus.OK);
			}
			HouseBaseParamsDto requestDto = validateResult.getResultObj();
			Map<String, Object> paramMap=(Map<String, Object>) JsonEntityTransform.json2Map(paramJson);


			String houseBaseFid = null;
			String roomFid = null;
			Integer rentWay = null;
			String houseDesc = null;
			String houseAroundDesc = null;
			Integer isTogetherLandlord =  null;
			Integer roomNum = null;
			Integer parlorNum = null;
			Integer toiletNum = null;
			Integer kitchenNum = null;
			Integer balconyNum = null;
			String supportArray = null;
			try {
				houseBaseFid = (String) paramMap.get("houseBaseFid");
				roomFid = (String) paramMap.get("roomFid");
				rentWay = (Integer) paramMap.get("rentWay");
				houseDesc = (String) paramMap.get("houseDesc");
				houseAroundDesc = (String) paramMap.get("houseAroundDesc");
				isTogetherLandlord =  (Integer) paramMap.get("isTogetherLandlord");
				roomNum = (Integer) paramMap.get("roomNum");
				parlorNum = (Integer) paramMap.get("parlorNum");
				toiletNum = (Integer) paramMap.get("toiletNum");
				kitchenNum = (Integer) paramMap.get("kitchenNum");
				balconyNum = (Integer) paramMap.get("balconyNum");
				supportArray = (String) paramMap.get("supportArray");
			} catch (Exception e) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
			}

			if (Check.NuNStr(roomFid)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数错误"), HttpStatus.OK);
			}
			if (Check.NuNObj(isTogetherLandlord)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择是否与房东同住"), HttpStatus.OK);
			}
			if (Check.NuNStr(supportArray)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择便利设施"), HttpStatus.OK);
			}
			if (Check.NuNObjs(roomNum, parlorNum, toiletNum, kitchenNum, balconyNum)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择户型"), HttpStatus.OK);
			}
			if(Check.NuNStr(houseDesc)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写房源描述"), HttpStatus.OK);
			}else if(houseDesc.length()<100||houseDesc.length()>1000){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源描述应为100-1000个字符"), HttpStatus.OK);
			}
			if(Check.NuNStr(houseAroundDesc)){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请填写房源周边情况"), HttpStatus.OK);
			}else if(houseAroundDesc.length()<100||houseAroundDesc.length()>1000){
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源周边描述应为100-1000个字符"), HttpStatus.OK);
			}
			HouseBaseExtDto houseBaseMsgEntity = null;
			String houseJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(houseJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(houseJson, "obj", HouseBaseExtDto.class);
			}

			if (Check.NuNObj(houseBaseMsgEntity)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
			}

			/* 组装房源描述及周边信息 */
			HouseBaseDetailVo houseBaseDetailVo=new HouseBaseDetailVo();
			houseBaseDetailVo.setFid(houseBaseFid);
			houseBaseDetailVo.setRentWay(rentWay);
			houseBaseDetailVo.setHouseDesc(houseDesc);
			houseBaseDetailVo.setHouseAroundDesc(houseAroundDesc);
			/* 组装房源描述及周边信息 */

			/*校验房间数量，修改房间数量不能小于现有房间数量*/
//			String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
//			List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list", HouseRoomMsgEntity.class);
//			Integer roomListSize = roomList.size();
//			if(roomNum < roomListSize){
//				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("户型中室的数量不能小于可出" +
//						"租房间数量"), HttpStatus.OK);
//			}
            /*校验房间数量，修改房间数量不能小于现有房间数量*/

			/*校验房间数量，修改房间数量不能小于可出租房间数（t_house_base_ext表中的rent_room_num）*/
            Integer rentRoomNum = houseBaseMsgEntity.getHouseBaseExt().getRentRoomNum();
            if (roomNum < rentRoomNum) {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("户型中室的数量不能小于可出" +
						"租房间数量"), HttpStatus.OK);
			}
			/*校验房间数量，修改房间数量不能小于现有房间数量*/

			List<HouseConfMsgEntity> listHouseConfMsg = new ArrayList<HouseConfMsgEntity>();
			String[] conMsgArray = supportArray.split(splist);
			for (String string : conMsgArray) {
				String[] conArray = string.split(subSplist);
				if (conArray.length == 2) {
					HouseConfMsgEntity houseConfMsg = new HouseConfMsgEntity();
					houseConfMsg.setHouseBaseFid(requestDto.getHouseBaseFid());
					houseConfMsg.setDicCode(string.split(subSplist)[0]);
					houseConfMsg.setDicVal(string.split(subSplist)[1]);
					listHouseConfMsg.add(houseConfMsg);
				}

			}
			if (Check.NuNCollection(listHouseConfMsg)) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请选择便利设施"), HttpStatus.OK);
			}

			// 房源户型限制信息
			List<MinsuEleEntity> houseLimitList = null;
			Map<String, Integer> houseLimitMap = new HashMap<>();
			String houseLimitJson = cityTemplateService.getTextListByLikeCodes(null,
					ProductRulesEnum.ProductRulesEnum0027.getValue());
			dto = JsonEntityTransform.json2DataTransferObject(houseLimitJson);
			if (dto.getCode() == DataTransferObject.SUCCESS) {
				houseLimitList = SOAResParseUtil.getListValueFromDataByKey(houseLimitJson, "confList",
						MinsuEleEntity.class);
			}

			if (!Check.NuNCollection(houseLimitList)) {
				for (MinsuEleEntity minsuEleEntity : houseLimitList) {
					houseLimitMap.put(minsuEleEntity.getEleKey(), ValueUtil.getintValue(minsuEleEntity.getEleValue()));
				}
			}

			if (isTogetherLandlord != YesOrNoEnum.YES.getCode() && isTogetherLandlord != YesOrNoEnum.NO.getCode()) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("是否与房东同住选择错误"), HttpStatus.OK);
			}
			if (roomNum < 0 || (Check.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()))
					&& roomNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027001.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房间数超出范围"), HttpStatus.OK);
			}
			if (parlorNum < 0 || (Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()))
					&& parlorNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027002.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("客厅数超出范围"), HttpStatus.OK);
			}
			if (toiletNum < 0 || (Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()))
					&& toiletNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027003.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("厕所数超出范围"), HttpStatus.OK);
			}
			if (kitchenNum < 0 || (Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()))
					&& kitchenNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027004.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("厨房数超出范围"), HttpStatus.OK);
			}
			if (balconyNum < 0 || (Check
					.NuNObj(houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()))
					&& balconyNum > houseLimitMap.get(ProductRulesEnum0027.ProductRulesEnum0027005.getValue()))) {
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("阳台数超出范围"), HttpStatus.OK);
			}


			//判断该房源下有没有上架或待审核的房间，如果有比对传入的户型与数据库中户型是否一致，若不一致，则返回提示，不可修改
			boolean yesNo = false;
            String roomListJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
            List<HouseRoomMsgEntity> roomListTemp = SOAResParseUtil.getListValueFromDataByKey(roomListJson, "list", HouseRoomMsgEntity.class);
            if (!Check.NuNCollection(roomListTemp)) {
                for (HouseRoomMsgEntity room : roomListTemp) {
                    if (HouseStatusEnum.SJ.getCode() == room.getRoomStatus()||HouseStatusEnum.YFB.getCode() == room.getRoomStatus()) {
						yesNo = true;
                        break;
                    }
                }
            }
            if(yesNo){
            	//从数据库中查询户型数据，与穿入户型数据比较
				String houseBaseAndExtJson = houseIssueService.searchHouseBaseAndExtByFid(houseBaseFid);
				HouseBaseExtDto houseBaseExt = SOAResParseUtil.getValueFromDataByKey(houseBaseAndExtJson,"obj",HouseBaseExtDto.class);
				if(!Check.NuNObj(houseBaseExt)){
					if(roomNum!=houseBaseExt.getRoomNum()||parlorNum!=houseBaseExt.getHallNum()
							||toiletNum!=houseBaseExt.getToiletNum()||kitchenNum!=houseBaseExt.getKitchenNum()
							||balconyNum!=houseBaseExt.getBalconyNum()){
						return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("户型修改失败,此房源下存在已上架或审核中房间"), HttpStatus.OK);
					}
				}
			}


			houseBaseMsgEntity.setBalconyNum(balconyNum);
			houseBaseMsgEntity.setHallNum(parlorNum);
			houseBaseMsgEntity.setKitchenNum(kitchenNum);
			houseBaseMsgEntity.setRoomNum(roomNum);
			houseBaseMsgEntity.setToiletNum(toiletNum);
			houseBaseMsgEntity.getHouseBaseExt().setIsTogetherLandlord(isTogetherLandlord);
			//houseBaseMsgEntity.setOperateSeq(3);

			HouseDescAndConfDto houseDescAndConfDto = new HouseDescAndConfDto();
			houseDescAndConfDto.setHouseBaseDetailVo(houseBaseDetailVo);
			houseDescAndConfDto.setHouseBaseExtDto(houseBaseMsgEntity);
			houseDescAndConfDto.setHouseConfMsgList(listHouseConfMsg);
			houseDescAndConfDto.setRoomFid(roomFid);


			//保存修改记录 ---- 查询历史数据
			HouseUpdateHistoryLogDto houseUpdateHistoryLogDto = houseUpdateLogService.findWaitUpdateHouseInfo(houseBaseFid, roomFid, rentWay);

			String saveResult = houseIssueAppService.saveHouseDescAndBaseInfoSublet(JsonEntityTransform.Object2Json(houseDescAndConfDto));
			dto = JsonEntityTransform.json2DataTransferObject(saveResult);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "saveDescAndBaseInfoSublet(),房源管理第二部保存失败，error = {}", dto.getMsg());
				return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
			}

			//保存修改记录 ---- 保存
			if(!Check.NuNObj(houseUpdateHistoryLogDto)){
				houseUpdateHistoryLogDto.setHouseFid(houseBaseFid);
				houseUpdateHistoryLogDto.setRentWay(rentWay);
				houseUpdateHistoryLogDto.setRoomFid(roomFid);
				houseUpdateLogService.saveHistoryLog(houseDescAndConfDto, request,houseUpdateHistoryLogDto,roomFid);
			}

			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveDescAndBaseInfoSublet(),房源管理第二部保存失败，error = {}", e);
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"), HttpStatus.OK);
		}
	}



	/**
	 * 查询分租 房间列表
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年07月10日 09:02:39
	 */
	@RequestMapping(value = "/${LOGIN_AUTH}/houseFRooms")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto> houseFRooms(HttpServletRequest request) {
		String uid = getUserId(request);
		String paramJson = (String) request.getAttribute(ConstDef.DECRYPT_PARAM_ATTRIBUTE);
        LogUtil.info(LOGGER, "[houseFRooms]uid={}，参数={}", uid, paramJson);

		ValidateResult<HouseBaseParamsDto> validateResult = paramCheckLogic.checkParamValidate(paramJson,
				HouseBaseParamsDto.class);
		if (!validateResult.isSuccess()) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(validateResult.getDto().getMsg()),
					HttpStatus.OK);
		}
		HouseBaseParamsDto requestDto = validateResult.getResultObj();
		Integer rentWay = requestDto.getRentWay();
		String houseBaseFid = requestDto.getHouseBaseFid();

		if (rentWay == RentWayEnum.HOUSE.getCode()) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式错误"), HttpStatus.OK);
		}
		DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchHouseBaseMsgByFid(houseBaseFid));
		if (houseDto.getCode() == DataTransferObject.ERROR) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(houseDto), HttpStatus.OK);
		}
		HouseBaseMsgEntity houseBaseMsgEntity = houseDto.parseData("obj", new TypeReference<HouseBaseMsgEntity>() {
		});
		if (Check.NuNObj(houseBaseMsgEntity)) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("房源不存在"), HttpStatus.OK);
		}
		if (houseBaseMsgEntity.getRentWay() != requestDto.getRentWay()) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("出租方式错误"), HttpStatus.OK);
		}
		DataTransferObject roomListDto = JsonEntityTransform.json2DataTransferObject(houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid));
		if (roomListDto.getCode() == DataTransferObject.ERROR) {
			return new ResponseEntity<>(ResponseSecurityDto.responseEncrypt(roomListDto), HttpStatus.OK);
		}
		List<HouseRoomMsgEntity> list = roomListDto.parseData("list", new TypeReference<List<HouseRoomMsgEntity>>() {
		});
		List<Map<String, String>> resultList = new ArrayList<>();
		for (HouseRoomMsgEntity roomMsgEntity : list) {
			Map<String, String> map = new HashMap<>();
			map.put("roomFid", roomMsgEntity.getFid());
			map.put("roomName", roomMsgEntity.getRoomName());
			resultList.add(map);
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("houseBaseFid", requestDto.getHouseBaseFid());
		resultMap.put("rooms", resultList);
        LogUtil.info(LOGGER, "【houseFRooms】返回值={}", JsonEntityTransform.Object2Json(resultMap));
        return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(resultMap), HttpStatus.OK);
    }

	/**
	 * 调用第三方接口
	 * 查询是否有存在友家有效合同
	 * @param uid
	 * @return
	 */
	private Integer validContract(String uid) {
		try {
			LogUtil.debug(LOGGER, "调用友家接口[获取有效合同列表]参数：" + uid);
			//接口地址
			String api = crm_validList + "?uid=" + uid;

			String result = CloseableHttpsUtil.sendGet(api, null);
			if (!Check.NuNStr(result)) {
				JSONObject resultObj = JSONObject.parseObject(result);
				int errorCode  = resultObj.getIntValue("error_code");
				if(errorCode == DataTransferObject.SUCCESS){
					JSONObject dataJson = JSONObject.parseObject(resultObj.getString("data"));
					JSONArray contentArray = dataJson.getJSONArray("content");
					if(!contentArray.isEmpty()){
						for(int j=0;j<contentArray.size();j++){
							JSONObject obj = contentArray.getJSONObject(j);
							if (obj.containsKey("contractState") && !Check.NuNStr(obj.getString("contractState")) && obj.getString("contractState").equals("ysh")) {
								return 1;
							}
						}
					}
				}
			}
			LogUtil.info(LOGGER,"调用友家接口[获取有效合同列表]返回信息:{}",result);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "调用友家接口[获取有效合同列表]异常url={},e", e);
		}
		return 0;
	}

}

