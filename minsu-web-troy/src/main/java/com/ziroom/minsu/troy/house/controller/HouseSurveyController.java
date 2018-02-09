package com.ziroom.minsu.troy.house.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;
import com.ziroom.minsu.entity.house.HouseSurveyPicMsgEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBaseMsgPicEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.house.api.inner.HouseIssueService;
import com.ziroom.minsu.services.house.api.inner.HouseSurveyService;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.survey.dto.SurveyPicDto;
import com.ziroom.minsu.services.house.survey.dto.SurveyRequestDto;
import com.ziroom.minsu.services.house.survey.entity.HouseSurveyVo;
import com.ziroom.minsu.troy.auth.menu.EvaluateAuthUtils;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.house.SurveyOperateTypeEnum;
import com.ziroom.minsu.valenum.house.SurveyPicTypeEnum;
import com.ziroom.minsu.valenum.house.SurveyRecordStatusEnum;
import com.ziroom.minsu.valenum.house.SurveyResultEnum;
import com.ziroom.tech.storage.client.domain.FileInfoResponse;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * 
 * <p>
 * 房源实勘管理controller
 * </p>
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
@RequestMapping("house/houseSurvey")
public class HouseSurveyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseSurveyController.class);

	@Resource(name = "house.houseSurveyService")
	private HouseSurveyService houseSurveyService;

	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;

	@Resource(name = "house.houseIssueService")
	private HouseIssueService houseIssueService;

	@Resource(name ="basedata.confCityService")
	private ConfCityService confCityService;

	@Resource(name = "storageService")
	private StorageService storageService;

	@Autowired
	private RedisOperations redisOperations;

	@Value("#{'${pic_base_addr}'.trim()}")
	private String picBaseAddr;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${pic_size}'.trim()}")
	private String picSize;

	@Value("#{'${storage_key}'.trim()}")
	private String storageKey;

	@Value("#{'${storage_limit}'.trim()}")
	private String storageLimit;
	

	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;

	/**
	 * 
	 * 房源实勘管理-跳转需要实勘房源信息列表
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param request
	 */
	@RequestMapping("listSurveyHouse")
	public void listSurveyHouse(HttpServletRequest request) {
		cascadeDistricts(request);
		request.setAttribute("surveyResultMap", SurveyResultEnum.getTotalmap());
		request.setAttribute("surveyResultJson", JsonEntityTransform.Object2Json(SurveyResultEnum.getTotalmap()));
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
	 * 房源实勘管理-查询实勘房源信息列表
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param requestDto
	 * @param request
	 * @return
	 */
	@RequestMapping("showSurveyHouse")
	@ResponseBody
	public PageResult showSurveyHouse(SurveyRequestDto requestDto, HttpServletRequest request) {
		try {
			Object authMenu = request.getAttribute("authMenu");
			requestDto.setRoleType(RoleTypeEnum.ADMIN.getCode());
			
			Map<String, CustomerBaseMsgEntity> landlordUidMap = new HashMap<String, CustomerBaseMsgEntity>();
			// 房东姓名或房东手机不为空,调用用户库查询房东uid
			if (!Check.NuNStr(requestDto.getLandlordName()) || !Check.NuNStr(requestDto.getLandlordMobile())) {
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(requestDto.getLandlordName());
				paramDto.setCustomerMobile(requestDto.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);

				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断代用状态
				if (customerDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "customerInfoService#selectByCondition接口调用失败,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<CustomerBaseMsgEntity> customerList = SOAResParseUtil.getListValueFromDataByKey(customerJsonArray,
						"listCustomerBaseMsg", CustomerBaseMsgEntity.class);
				// 如果查询结果为空,直接返回数据
				if (Check.NuNCollection(customerList)) {
					LogUtil.info(LOGGER, "返回客户信息为空,参数:{}", JsonEntityTransform.Object2Json(paramDto));
					return new PageResult();
				}
				List<String> landlordUidList = new ArrayList<String>();
				for (CustomerBaseMsgEntity customerBaseMsg : customerList) {
					landlordUidMap.put(customerBaseMsg.getUid(), customerBaseMsg);
					landlordUidList.add(customerBaseMsg.getUid());
				}
				requestDto.setLandlordUidList(landlordUidList);
			}

			Map<String, EmployeeEntity> empCodeMap = new HashMap<String, EmployeeEntity>();
			// 管家姓名、管家系统号或管家手机不为空,调用基础库查询管家系统号
			if (!Check.NuNStr(requestDto.getEmpGuardName()) || !Check.NuNStr(requestDto.getEmpGuardCode())
					|| !Check.NuNStr(requestDto.getEmpGuardMobile())) {
				EmployeeRequest empRequest = new EmployeeRequest();
				empRequest.setEmpCode(requestDto.getEmpGuardCode());
				empRequest.setEmpName(requestDto.getEmpGuardName());
				empRequest.setEmpMobile(requestDto.getEmpGuardMobile());
				String empJsonArray = employeeService.findEmployeeByCondition(JsonEntityTransform.Object2Json(empRequest));
				DataTransferObject empDto = JsonEntityTransform.json2DataTransferObject(empJsonArray);
				// 判断代用状态
				if (empDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "employeeService#findEmployeeByCondition接口调用失败,参数:{}",
							JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<EmployeeEntity> empList = empDto.parseData("list", new TypeReference<List<EmployeeEntity>>() {
				});
				// 如果查询结果为空,直接返回数据
				if (Check.NuNCollection(empList)) {
					LogUtil.info(LOGGER, "返回管家信息为空,参数:{}", JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<String> listZoCode = new ArrayList<String>();
				for (EmployeeEntity emp : empList) {
					empCodeMap.put(emp.getEmpCode(), emp);
					listZoCode.add(emp.getEmpCode());
				}
				requestDto.setGuardCodeList(listZoCode);
			}

			empCodeMap.clear();
			// 管家姓名、管家系统号或管家手机不为空,调用基础库查询管家系统号
			if (!Check.NuNStr(requestDto.getEmpPushName()) || !Check.NuNStr(requestDto.getEmpPushCode())
					|| !Check.NuNStr(requestDto.getEmpPushMobile())) {
				EmployeeRequest empRequest = new EmployeeRequest();
				empRequest.setEmpCode(requestDto.getEmpPushCode());
				empRequest.setEmpName(requestDto.getEmpPushName());
				empRequest.setEmpMobile(requestDto.getEmpPushMobile());
				String empJsonArray = employeeService.findEmployeeByCondition(JsonEntityTransform.Object2Json(empRequest));
				DataTransferObject empDto = JsonEntityTransform.json2DataTransferObject(empJsonArray);
				// 判断代用状态
				if (empDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "employeeService#findEmployeeByCondition接口调用失败,参数:{}",
							JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<EmployeeEntity> empList = empDto.parseData("list", new TypeReference<List<EmployeeEntity>>() {
				});
				// 如果查询结果为空,直接返回数据
				if (Check.NuNCollection(empList)) {
					LogUtil.info(LOGGER, "返回管家信息为空,参数:{}", JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<String> listZoCode = new ArrayList<String>();
				for (EmployeeEntity emp : empList) {
					empCodeMap.put(emp.getEmpCode(), emp);
					listZoCode.add(emp.getEmpCode());
				}
				requestDto.setPushCodeList(listZoCode);
			}

			//菜单添加权限
			if(!addAuthData(authMenu, requestDto)){
				return null;
			}
			String resultJson = houseSurveyService.findSurveyHouseListByPage(JsonEntityTransform.Object2Json(requestDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "houseSurveyService#findSurveyHouseListByPage接口调用失败,结果:{}", resultJson);
				return new PageResult();
			}

			List<HouseSurveyVo> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseSurveyVo.class);
			PageResult pageResult = new PageResult();
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "showSurveyHouse error:{},params:{}", e, JsonEntityTransform.Object2Json(requestDto));
			return new PageResult();
		}
	}
	
	/**
	 * 
	 * 评价权限配置
	 *
	 * @author yd
	 * @created 2016年10月31日 下午3:51:31
	 *
	 * @param authMenu
	 * @param evaluateRequest
	 */
	private boolean addAuthData(Object authMenu,SurveyRequestDto requestDto){
		boolean addFlag = false;
		// 权限过滤
		if (!Check.NuNObj(authMenu)) {
			AuthMenuEntity authMenuEntity = (AuthMenuEntity) authMenu;
			if (!Check.NuNObj(authMenuEntity.getRoleType()) && authMenuEntity.getRoleType().intValue() > 0) {
				requestDto.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto = EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if (authDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.error(LOGGER, "当前菜单类型：{},权限异常error={}", "查看评价", authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if (Check.NuNCollection(fids)) {
						LogUtil.error(LOGGER, "当前菜单类型：{},无权限，fids={}", "查看评价", fids);
						return addFlag;
					}
					requestDto.setListFid(fids);

				} catch (SOAParseException e) {
					LogUtil.error(LOGGER, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * 房源实勘管理-展示实勘详情信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param request
	 * @param houseBaseFid
	 * @throws SOAParseException
	 */
	@RequestMapping("surveyDetail")
	public void surveyDetail(HttpServletRequest request, String houseBaseFid) throws SOAParseException {
		HouseSurveyMsgEntity houseSurveyMsg = this.findHouseSurveyMsg(houseBaseFid);
		request.setAttribute("houseSurveyMsg", houseSurveyMsg);
		if (!Check.NuNObj(houseSurveyMsg)) {
			List<HouseSurveyPicMsgEntity> picList = this.findSurveyPicList(houseSurveyMsg.getFid());
			request.setAttribute("picList", picList);
			
			String surveyEmpFid = houseSurveyMsg.getSurveyEmpFid();
			EmployeeEntity surveyEmp = this.findEmployeeEntityByFid(surveyEmpFid);
			request.setAttribute("surveyEmp", surveyEmp);
			
			String auditEmpFid = houseSurveyMsg.getAuditEmpFid();
			EmployeeEntity auditEmp = this.findEmployeeEntityByFid(auditEmpFid);
			request.setAttribute("auditEmp", auditEmp);
		}

		HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
		request.setAttribute("houseBaseMsg", houseBaseMsg);
		
		// 只有独立房间才显示房间编号集合
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNObj(houseBaseMsg.getRentWay())
				&& houseBaseMsg.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
			String roomsSn = this.getRoomsSnByHouseFid(houseBaseFid);
			request.setAttribute("roomsSn", roomsSn);
		}
		
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNStr(houseBaseMsg.getLandlordUid())) {
			CustomerBaseMsgEntity landlord = findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
			request.setAttribute("landlord", landlord);
		}

		request.setAttribute("surveyResultMap", SurveyResultEnum.getEnumMap());
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
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
	 * 根据房源fid获取
	 *
	 * @author liujun
	 * @created 2016年11月18日
	 *
	 * @param houseBaseFid
	 * @throws SOAParseException 
	 * @return
	 */
	private String getRoomsSnByHouseFid(String houseBaseFid) throws SOAParseException {
		String resultJson = houseIssueService.searchRoomListByHouseBaseFid(houseBaseFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "houseIssueService.searchRoomListByHouseBaseFid错误,houseBaseFid={},结果:{}", houseBaseFid, resultJson);
			return null;
		} else {
			List<HouseRoomMsgEntity> roomList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseRoomMsgEntity.class);
			int num = 0;
			StringBuilder sb = new StringBuilder();
			for (HouseRoomMsgEntity houseRoomMsgEntity : roomList) {
				if (!Check.NuNObj(houseRoomMsgEntity.getRoomStatus())
						&& houseRoomMsgEntity.getRoomStatus().intValue() > HouseStatusEnum.DFB.getCode()) {
					if (num == 0) {
						sb.append(houseRoomMsgEntity.getRoomSn());
					} else {
						sb.append(",").append(houseRoomMsgEntity.getRoomSn());
					}
					num++;
				}
			}
			return sb.toString();
		}
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
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "houseIssueService.searchHouseBaseMsgByFid错误,houseBaseFid={},结果:{}", houseBaseFid, resultJson);
			return null;
		} else {
			HouseBaseMsgEntity houseBaseMsg = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseBaseMsgEntity.class);
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
	 * 根据实勘fid获取实勘图片列表
	 *
	 * @author liujun
	 * @created 2016年11月21日
	 *
	 * @param surveyFid
	 * @throws SOAParseException 
	 * @return
	 */
	private List<HouseSurveyPicMsgEntity> findSurveyPicList(String surveyFid) throws SOAParseException {
		SurveyPicDto picDto = new SurveyPicDto();
		picDto.setSurveyFid(surveyFid);
		String resultJson = houseSurveyService.findSurveyPicListByType(JsonEntityTransform.Object2Json(picDto));
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "houseSurveyService.findSurveyPicListBySurveyFid error:{}", resultJson);
			return null;
		} else {
			List<HouseSurveyPicMsgEntity> picList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseSurveyPicMsgEntity.class);
			return picList;
		}
	}

	/**
	 * 
	 * 根据员工表fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午10:09:48
	 *
	 * @param empFid
	 * @throws SOAParseException
	 * @return
	 */
	private EmployeeEntity findEmployeeEntityByFid(String empFid) throws SOAParseException {
		String resultJson = employeeService.findEmployeByEmpFid(empFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.error(LOGGER, "employeeService#findEmployeeEntityByFid调用接口失败,empFid={}", empFid);
			return null;
		} else {
			EmployeeEntity employee =SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
			return employee;
		}
	}
	
	/**
	 * 
	 * 房源实勘管理-上传实勘表
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param request
	 * @param houseBaseFid
	 * @throws SOAParseException
	 */
	@RequestMapping("uploadHouseSurvey")
	public void uploadHouseSurvey(HttpServletRequest request, String houseBaseFid) throws SOAParseException {
		HouseSurveyMsgEntity houseSurveyMsg = this.findHouseSurveyMsg(houseBaseFid);
		HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
		// 只有独立房间才显示房间编号集合
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNObj(houseBaseMsg.getRentWay())
				&& houseBaseMsg.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
			String roomsSn = this.getRoomsSnByHouseFid(houseBaseFid);
			request.setAttribute("roomsSn", roomsSn);
		}
		
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNStr(houseBaseMsg.getLandlordUid())) {
			CustomerBaseMsgEntity landlord = findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
			request.setAttribute("landlord", landlord);
		}
		
		if (Check.NuNObj(houseSurveyMsg)) {// 新增房源实勘草稿
			if (!Check.NuNObj(houseBaseMsg)) {
				houseSurveyMsg = new HouseSurveyMsgEntity();
				houseSurveyMsg.setFid(UUIDGenerator.hexUUID());
				houseSurveyMsg.setHouseBaseFid(houseBaseFid);
				houseSurveyMsg.setCreateFid(UserUtil.getEmployeeFid());
				houseSurveyService.insertHouseSurveyMsg(JsonEntityTransform.Object2Json(houseSurveyMsg));
			}
		} else {
			List<HouseSurveyPicMsgEntity> picList = this.findSurveyPicList(houseSurveyMsg.getFid());
			request.setAttribute("picList", picList);
		}
		
		EmployeeEntity emp = this.findEmployeeEntityByFid(UserUtil.getEmployeeFid());
		
		request.setAttribute("houseBaseFid", houseBaseFid);
		request.setAttribute("employee", emp);
		request.setAttribute("houseSurveyMsg", houseSurveyMsg);
		request.setAttribute("houseBaseMsg", houseBaseMsg);
		request.setAttribute("surveyResultMap", SurveyResultEnum.getEnumMap());
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
	}

	/**
	 * 
	 * 房源实勘管理-上传图片
	 *
	 * @author bushujie
	 * @created 2016年4月15日 上午11:37:18
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadSurveyPic", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadSurveyPic(@RequestParam MultipartFile[] file, HttpServletRequest request) throws IOException {
		DataTransferObject dto = new DataTransferObject();
		try {
			String surveyFid = request.getParameter("surveyFid");
			String picType = request.getParameter("picType");

			if (Check.NuNStr(picType)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型不能为空");
				return dto.toJsonString();
			}

			/** 判断图片数量是否超过限制开始 **/
			SurveyPicDto surveyPicDto = new SurveyPicDto();
			surveyPicDto.setSurveyFid(surveyFid);
			int picTypeInt = Integer.valueOf(picType).intValue();
			surveyPicDto.setPicType(picTypeInt);
			String countResultJson = houseSurveyService.findPicCountByType(JsonEntityTransform.Object2Json(surveyPicDto));
			Integer count = SOAResParseUtil.getIntFromDataByKey(countResultJson, "count");

			SurveyPicTypeEnum enumeration = SurveyPicTypeEnum.getEnumByCode(picTypeInt);
			if (Check.NuNObj(enumeration)) {
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片类型错误");
				return dto.toJsonString();
			}

			if ((file.length + count) > enumeration.getMax()) {
				dto = new DataTransferObject();
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("超过上传图片的最大限制");
				return dto.toJsonString();
			}
			/** 判断图片数量是否超过限制结束 **/

			String createFid = UserUtil.getEmployeeFid();
			List<HouseSurveyPicMsgEntity> picList = new ArrayList<HouseSurveyPicMsgEntity>();
			// 循环上传图片
			for (MultipartFile mulfile : file) {
				FileInfoResponse fileResponse = storageService.upload(storageKey, storageLimit, mulfile.getOriginalFilename(),
						mulfile.getBytes(), enumeration.getName(), 0l, mulfile.getOriginalFilename());
				if (!"0".equals(fileResponse.getResponseCode())) {
					LogUtil.info(LOGGER, "图片全名称:{},图片类型:{}, 图片文件是否为空:{},图片名称:{}", mulfile.getOriginalFilename(),
							mulfile.getContentType(), mulfile.isEmpty(), mulfile.getName());
					LogUtil.error(LOGGER, "上传图片异常:{},房源实勘id:{}, 图片类型:{}", fileResponse.getErrorInfo(), surveyFid, enumeration.getName());
					continue;
				}
				HouseSurveyPicMsgEntity picEntity = new HouseSurveyPicMsgEntity();
				picEntity.setSurveyFid(surveyFid);
				picEntity.setPicType(picTypeInt);
				picEntity.setPicBaseUrl(fileResponse.getFile().getUrlBase());
				picEntity.setPicName(fileResponse.getFile().getOriginalFilename());
				picEntity.setPicSuffix(fileResponse.getFile().getUrlExt());
				picEntity.setPicServerUuid(fileResponse.getFile().getUuid());
				picEntity.setCreateFid(createFid);
				picList.add(picEntity);
			}
			String resultJson = houseSurveyService.saveSurveyPicMsgList(JsonEntityTransform.Object2Json(picList));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			dto.putValue("list", picList);
			return dto.toJsonString();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "uploadSurveyPic error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto.toJsonString();
		}
	}
	
	/**
	 * 
	 * 房源实勘管理-删除图片
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param picDto
	 * @return
	 */
	@RequestMapping("deleteSurveyPic")
	@ResponseBody
	public DataTransferObject deleteSurveyPic(String surveyPicFid){
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = houseSurveyService.findHouseSurveyPicMsgByFid(surveyPicFid);
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (resultDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "houseSurveyService.findHouseSurveyPicMsgByFid error:{}", resultJson);
				return resultDto;
			}
			PhotographerBaseMsgPicEntity picEntity = SOAResParseUtil
					.getValueFromDataByKey(resultJson, "obj", PhotographerBaseMsgPicEntity.class);
			if(!Check.NuNObj(picEntity)){
				//删除该图片
				picEntity.setIsDel(YesOrNoEnum.YES.getCode());
				resultJson = houseSurveyService.updateHouseSurveyPicMsg(JsonEntityTransform.Object2Json(picEntity));
				resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
				if (resultDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.info(LOGGER, "houseSurveyService.updateHouseSurveyPicMsg error:{}", resultJson);
					return resultDto;
				}
//				boolean del = false;
//				try {
//					del = storageService.delete(picEntity.getPicServerUuid());
//				} catch (Exception e) {
//					LogUtil.info(LOGGER, "storageService.delete:{},error:{}", del, e);
//				}
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("图片不存在");
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "deleteSurveyPic error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		} 
		return dto;
	}
	
	/**
	 * 
	 * 房源实勘管理-保存实勘表信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param picDto
	 * @return
	 */
	@RequestMapping("saveHouseSurvey")
	@ResponseBody
	public DataTransferObject saveHouseSurvey(String jsonStr){
		DataTransferObject dto = new DataTransferObject();
		try {
			HouseSurveyMsgEntity houseSurveyMsg = JsonEntityTransform.json2Object(jsonStr, HouseSurveyMsgEntity.class);
			houseSurveyMsg.setRecordStatus(SurveyRecordStatusEnum.NORMAL.getCode());
			String resultJson = houseSurveyService.updateHouseSurveyMsg(JsonEntityTransform.Object2Json(houseSurveyMsg));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveHouseSurvey error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		} 
		return dto;
	}
	
	/**
	 * 
	 * 房源实勘管理-跳转修改实勘信息页面
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param request
	 * @param houseBaseFid
	 * @throws SOAParseException
	 */
	@RequestMapping("editHouseSurvey")
	public void editHouseSurvey(HttpServletRequest request, String houseBaseFid) throws SOAParseException {
		HouseSurveyMsgEntity houseSurveyMsg = this.findHouseSurveyMsg(houseBaseFid);
		request.setAttribute("houseSurveyMsg", houseSurveyMsg);
		if (!Check.NuNObj(houseSurveyMsg)) {
			List<HouseSurveyPicMsgEntity> picList = this.findSurveyPicList(houseSurveyMsg.getFid());
			request.setAttribute("picList", picList);
			
			String surveyEmpFid = houseSurveyMsg.getSurveyEmpFid();
			EmployeeEntity surveyEmp = this.findEmployeeEntityByFid(surveyEmpFid);
			request.setAttribute("surveyEmp", surveyEmp);
		}

		HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
		request.setAttribute("houseBaseMsg", houseBaseMsg);

		// 只有独立房间才显示房间编号集合
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNObj(houseBaseMsg.getRentWay())
				&& houseBaseMsg.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
			String roomsSn = this.getRoomsSnByHouseFid(houseBaseFid);
			request.setAttribute("roomsSn", roomsSn);
		}
		
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNStr(houseBaseMsg.getLandlordUid())) {
			CustomerBaseMsgEntity landlord = findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
			request.setAttribute("landlord", landlord);
		}

		request.setAttribute("surveyResultMap", SurveyResultEnum.getEnumMap());
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
	}

	/**
	 * 
	 * 房源实勘管理-修改实勘表信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param picDto
	 * @return
	 */
	@RequestMapping("updateHouseSurvey")
	@ResponseBody
	public DataTransferObject updateHouseSurvey(String jsonStr){
		DataTransferObject dto = new DataTransferObject();
		String operateFid = UserUtil.getEmployeeFid();
		if (Check.NuNStr(operateFid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请重新登录");
		}
		try {
			HouseSurveyMsgEntity houseSurveyMsg = JsonEntityTransform.json2Object(jsonStr, HouseSurveyMsgEntity.class);
			houseSurveyMsg.setOperateFid(operateFid);
			houseSurveyMsg.setOperateType(SurveyOperateTypeEnum.UPDATE.getCode());
			String resultJson = houseSurveyService.updateHouseSurveyMsg(JsonEntityTransform.Object2Json(houseSurveyMsg));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "saveHouseSurvey error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		} 
		return dto;
	}

	/**
	 * 
	 * 房源实勘管理-判断房源实勘信息是否锁定
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param businessType
	 * @param rentWay
	 * @param fid
	 * @return
	 */
	@RequestMapping("judgeSurveyLock")
	@ResponseBody
	public DataTransferObject judgeSurveyLock(String surveyFid){
		DataTransferObject dto = new DataTransferObject();
		try {
			CurrentuserVo currentuserEntity =UserUtil.getFullCurrentUser();
			String key = RedisKeyConst.getHouseLockKey(surveyFid);
			String lockUserJson = redisOperations.get(key);
			if (Check.NuNObj(lockUserJson)) {
				redisOperations.setex(key, RedisKeyConst.HOUSE_LOCK_CACHE_TIME, JsonEntityTransform.Object2Json(currentuserEntity));
			} else {
				CurrentuserVo lockUser = JsonEntityTransform.json2Object(lockUserJson, CurrentuserVo.class);
				if (!lockUser.getFid().equals(currentuserEntity.getFid())) {
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("实勘表已被" + lockUser.getUserAccount() + "[" + lockUser.getFullName() + "]锁定");
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "judgeSurveyLock error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		}
		return dto;
	}

	/**
	 * 
	 * 房源实勘管理-跳转审阅实勘信息页面
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param request
	 * @param houseBaseFid
	 * @throws SOAParseException
	 */
	@RequestMapping("toAuditHouseSurvey")
	public String toEditHouseSurvey(HttpServletRequest request, String houseBaseFid) throws SOAParseException {
		HouseSurveyMsgEntity houseSurveyMsg = this.findHouseSurveyMsg(houseBaseFid);
		request.setAttribute("houseSurveyMsg", houseSurveyMsg);
		if (!Check.NuNObj(houseSurveyMsg)) {
			List<HouseSurveyPicMsgEntity> picList = this.findSurveyPicList(houseSurveyMsg.getFid());
			request.setAttribute("picList", picList);
			
			String surveyEmpFid = houseSurveyMsg.getSurveyEmpFid();
			EmployeeEntity surveyEmp = this.findEmployeeEntityByFid(surveyEmpFid);
			request.setAttribute("surveyEmp", surveyEmp);
		}

		HouseBaseMsgEntity houseBaseMsg = this.findHouseBaseMsgEntity(houseBaseFid);
		request.setAttribute("houseBaseMsg", houseBaseMsg);
		
		// 只有独立房间才显示房间编号集合
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNObj(houseBaseMsg.getRentWay())
				&& houseBaseMsg.getRentWay().intValue() == RentWayEnum.ROOM.getCode()) {
			String roomsSn = this.getRoomsSnByHouseFid(houseBaseFid);
			request.setAttribute("roomsSn", roomsSn);
		}
				
		if (!Check.NuNObj(houseBaseMsg) && !Check.NuNStr(houseBaseMsg.getLandlordUid())) {
			CustomerBaseMsgEntity landlord = findCustomerBaseMsgEntity(houseBaseMsg.getLandlordUid());
			request.setAttribute("landlord", landlord);
		}

		request.setAttribute("surveyResultMap", SurveyResultEnum.getEnumMap());
		request.setAttribute("picBaseAddr", picBaseAddr);
		request.setAttribute("picBaseAddrMona", picBaseAddrMona);
		request.setAttribute("picSize", picSize);
		return "house/houseSurvey/auditHouseSurvey";
	}

	/**
	 * 
	 * 房源实勘管理-审阅实勘表信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param picDto
	 * @return
	 */
	@RequestMapping("auditHouseSurvey")
	@ResponseBody
	public DataTransferObject auditHouseSurvey(String jsonStr){
		DataTransferObject dto = new DataTransferObject();
		String auditEmpFid = UserUtil.getEmployeeFid();
		if (Check.NuNStr(auditEmpFid)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请重新登录");
		}
		try {
			HouseSurveyMsgEntity houseSurveyMsg = JsonEntityTransform.json2Object(jsonStr, HouseSurveyMsgEntity.class);
			houseSurveyMsg.setAuditDate(new Date());
			houseSurveyMsg.setAuditEmpFid(auditEmpFid);
			houseSurveyMsg.setIsAudit(YesOrNoEnum.YES.getCode());;
			houseSurveyMsg.setOperateFid(auditEmpFid);
			houseSurveyMsg.setOperateType(SurveyOperateTypeEnum.AUDIT.getCode());
			EmployeeEntity employee = this.findEmployeeEntityByFid(auditEmpFid);
			if (!Check.NuNObj(employee)) {
				houseSurveyMsg.setAuditEmpName(employee.getEmpName());
			}
			String resultJson = houseSurveyService.updateHouseSurveyMsg(JsonEntityTransform.Object2Json(houseSurveyMsg));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "auditHouseSurvey error:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
		} 
		return dto;
	}

}
