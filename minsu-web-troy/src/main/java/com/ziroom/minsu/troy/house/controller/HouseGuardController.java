package com.ziroom.minsu.troy.house.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.ConfCityEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseGuardLogEntity;
import com.ziroom.minsu.entity.sys.CurrentuserCityEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.house.api.inner.HouseGuardService;
import com.ziroom.minsu.services.house.constant.HouseConstant;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.dto.HouseGuardParam;
import com.ziroom.minsu.services.house.entity.HouseGuardVo;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.house.HouseStatusEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * 
 * <p>房源专员controller</p>
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
@RequestMapping("house/houseGuard")
public class HouseGuardController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseGuardController.class);
	
	@Resource(name="house.houseGuardService")
	private HouseGuardService houseGuardService;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	/**
	 * 
	 * 房源管理-跳转房源专员关系页面
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param request
	 */
	@RequestMapping("listHouseGuard")
	public void listHouseMsg(HttpServletRequest request) {
		cascadeDistricts(request);
		// 房源状态类型
		request.setAttribute("houseStatusMap", HouseStatusEnum.getValidEnumMap());
		request.setAttribute("houseStatusJson", JsonEntityTransform.Object2Json(HouseStatusEnum.getEnumMap()));
		request.setAttribute("rentWayMap", RentWayEnum.getEnumMap());
	}
	

	/**
	 * 
	 * 获取行政区域列表
	 *
	 * @author liujun
	 * @created 2016年7月5日
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
	 * 房源管理-查询房源专员关系列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showHouseGuard")
	@ResponseBody
	public PageResult showHouseGuard(HouseGuardDto houseGuardDto,HttpServletRequest request) {
		try {
			Map<String,CustomerBaseMsgEntity> landlordUidMap = new HashMap<String,CustomerBaseMsgEntity>();
			// 房东姓名、昵称或房东手机不为空,调用用户库查询房东uid
			if (!Check.NuNStr(houseGuardDto.getLandlordName()) || !Check.NuNStr(houseGuardDto.getLandlordNickname())
					|| !Check.NuNStr(houseGuardDto.getLandlordMobile())) {
				CustomerBaseMsgDto paramDto = new CustomerBaseMsgDto();
				paramDto.setRealName(houseGuardDto.getLandlordName());
				paramDto.setNickName(houseGuardDto.getLandlordNickname());
				paramDto.setCustomerMobile(houseGuardDto.getLandlordMobile());
				paramDto.setIsLandlord(HouseConstant.IS_TRUE);
				
				String customerJsonArray = customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(paramDto));
				DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJsonArray);
				// 判断代用状态
				if(customerDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "customerInfoService#selectByCondition接口调用失败,参数:{}", 
							JsonEntityTransform.Object2Json(paramDto));
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
				houseGuardDto.setListLandlordUid(landlordUidList);
			}
			
			Map<String,EmployeeEntity> empCodeMap = new HashMap<String,EmployeeEntity>();
			// 管家姓名、管家系统号或管家手机不为空,调用基础库查询管家系统号
			if (!Check.NuNStr(houseGuardDto.getZoName()) || !Check.NuNStr(houseGuardDto.getZoCode())
					|| !Check.NuNStr(houseGuardDto.getZoMobile())) {
				EmployeeRequest empRequest = new EmployeeRequest();
				empRequest.setEmpCode(houseGuardDto.getZoCode());
				empRequest.setEmpName(houseGuardDto.getZoName());
				empRequest.setEmpMobile(houseGuardDto.getZoMobile());
				String empJsonArray = employeeService.findEmployeeByCondition(JsonEntityTransform.Object2Json(empRequest));
				DataTransferObject empDto = JsonEntityTransform.json2DataTransferObject(empJsonArray);
				// 判断代用状态
				if(empDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "employeeService#findEmployeeByCondition接口调用失败,参数:{}", 
							JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<EmployeeEntity> empList = empDto.parseData("list",
						new TypeReference<List<EmployeeEntity>>() {});
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(empList)){
					LogUtil.info(LOGGER, "返回管家信息为空,参数:{}", JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<String> listZoCode = new ArrayList<String>();
				for (EmployeeEntity emp : empList) {
					empCodeMap.put(emp.getEmpCode(), emp);
					listZoCode.add(emp.getEmpCode());
				}
				houseGuardDto.setListZoCode(listZoCode);
			}
			//特殊权限参数赋值
			Integer roleType=(Integer) request.getAttribute("roleType");
			if(!Check.NuNObj(roleType)){
				houseGuardDto.setRoleType(roleType);
			}
			houseGuardDto.setEmpCode((String) request.getAttribute("empCode"));
			houseGuardDto.setUserCityList((List<CurrentuserCityEntity>) request.getAttribute("userCityList"));
			String resultJson = houseGuardService.searchHouseGuardList(JsonEntityTransform.Object2Json(houseGuardDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseGuardService#searchHouseGuardList接口调用失败,结果:{}", resultJson);
			}
			
			List<HouseGuardVo> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "rows", HouseGuardVo.class);
			for (HouseGuardVo houseGuardVo : list) {
				// 补全信息
				this.completeInfo(landlordUidMap, empCodeMap, houseGuardVo);
			}
			PageResult pageResult = new PageResult();
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{},params:{}", e, JsonEntityTransform.Object2Json(houseGuardDto));
			return new PageResult();
		}
	}
	
	/**
	 * 补全信息
	 *
	 * @author liujun
	 * @created 2016年7月7日
	 *
	 * @param landlordUidMap
	 * @param empCodeMap
	 * @param houseGuardVo
	 */
	private void completeInfo(Map<String, CustomerBaseMsgEntity> landlordUidMap,
			Map<String, EmployeeEntity> empCodeMap, HouseGuardVo houseGuardVo) {
		// 查询房东信息
		CustomerBaseMsgEntity customer = null;
		if(Check.NuNMap(landlordUidMap)){
			String customerJson = customerInfoService.getCustomerInfoByUid(houseGuardVo.getLandlordUid());
			DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
			if (customerDto.getCode() == DataTransferObject.ERROR) {
				LogUtil.info(LOGGER, "customerInfoService#getCustomerInfoByUid调用接口失败,landlordUid={}",
						houseGuardVo.getLandlordUid());
			} else {
				customer = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
			}
		} else {
			customer = landlordUidMap.get(houseGuardVo.getLandlordUid());
		}
		
		// 查询管家信息
		// EmployeeEntity empPush = null;
		EmployeeEntity empGuard = null;
		if(Check.NuNMap(empCodeMap)){
			/*if (!Check.NuNStr(houseGuardVo.getEmpPushCode())) {
				String empPushJson = employeeService.findEmployeeByEmpCode(houseGuardVo.getEmpPushCode());
				DataTransferObject empPushDto = JsonEntityTransform.json2DataTransferObject(empPushJson);
				if (empPushDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}",
							houseGuardVo.getEmpPushCode());
				} else {
					empPush = empPushDto.parseData("employee", new TypeReference<EmployeeEntity>() {});
				}
			}*/
			
			if (!Check.NuNStr(houseGuardVo.getEmpGuardCode())) {
				String empGuardJson = employeeService.findEmployeeByEmpCode(houseGuardVo.getEmpGuardCode());
				DataTransferObject empGuardDto = JsonEntityTransform.json2DataTransferObject(empGuardJson);
				if (empGuardDto.getCode() == DataTransferObject.ERROR) {
					LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}",
							houseGuardVo.getEmpGuardCode());
				} else {
					empGuard = empGuardDto.parseData("employee", new TypeReference<EmployeeEntity>() {});
				}
			}
		} else {
			/*empPush = empCodeMap.get(houseGuardVo.getEmpPushCode());*/
			empGuard = empCodeMap.get(houseGuardVo.getEmpGuardCode());
		}
		
		if(!Check.NuNObj(customer)){
			houseGuardVo.setLandlordName(customer.getRealName());
			houseGuardVo.setLandlordNickname(customer.getNickName());
			houseGuardVo.setLandlordMobile(customer.getCustomerMobile());
		}
		
		/*if(!Check.NuNObj(empPush)){
			houseGuardVo.setEmpPushMobile(empPush.getEmpMobile());
		}*/
		
		if(!Check.NuNObj(empGuard)){
			houseGuardVo.setEmpGuardMobile(empGuard.getEmpMobile());
		}
	}


	/**
	 * 
	 * 房源管理-查询房源专员关系详情
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseRequest
	 * @return
	 */
	@RequestMapping("houseGuardDetail")
	public void toHouseGuard(HttpServletRequest request, String houseBaseFid) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String resultJson = houseGuardService.searchHouseGuardDetail(houseBaseFid);
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if (dto.getCode() == DataTransferObject.ERROR) {
				LogUtil.error(LOGGER, "houseGuardService#searchHouseGuardDetail接口调用失败,结果:{},houseBaseFid:{}", 
						resultJson, houseBaseFid);
			} else {
				HouseGuardVo vo = SOAResParseUtil.getValueFromDataByKey(resultJson, "obj", HouseGuardVo.class);
				if(!Check.NuNObj(vo)){
					this.completeInfo(null, null, vo);
				}
				request.setAttribute("houseGuardVo", vo);
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{},houseBaseFid:{}", e, houseBaseFid);
		}
	}

	/**
	 * 
	 * 房源管理-分页查询房源专员关系日志列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseRequest
	 * @return
	 */
	@RequestMapping("showHouseGuardLogList")
	@ResponseBody
	public PageResult showHouseGuardLogList(HouseGuardDto houseGuardDto) {
		try {
			// 房源专员关系逻辑id为空
			if(Check.NuNStr(houseGuardDto.getHouseGuardFid())){
				return new PageResult();
			}
			
			String resultJson = houseGuardService.searchHouseGuardLogList(JsonEntityTransform.Object2Json(houseGuardDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseGuardService#searchHouseGuardLogList接口调用失败,结果:{}", resultJson);
				return new PageResult();
			}
			
			List<HouseGuardLogEntity> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "rows", HouseGuardLogEntity.class);
			for (HouseGuardLogEntity houseGuardLog : list) {
				if(!Check.NuNStr(houseGuardLog.getOldGuardCode())){
					String empGuardJson = employeeService.findEmployeeByEmpCode(houseGuardLog.getOldGuardCode());
					DataTransferObject empGuardDto = JsonEntityTransform.json2DataTransferObject(empGuardJson);
					if (empGuardDto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}",
								houseGuardLog.getOldGuardCode());
					} else {
						EmployeeEntity empGuard = SOAResParseUtil.getValueFromDataByKey(empGuardJson, "employee", EmployeeEntity.class);
						if(!Check.NuNObj(empGuard)){
							houseGuardLog.setOldGuardName(empGuard.getEmpName());
							houseGuardLog.setOldGuardMobile(empGuard.getEmpMobile());
						}
					}
				}
				/*if(!Check.NuNStr(houseGuardLog.getOldPushCode())){
					String empPushJson = employeeService.findEmployeeByEmpCode(houseGuardLog.getOldPushCode());
					DataTransferObject empPushDto = JsonEntityTransform.json2DataTransferObject(empPushJson);
					if (empPushDto.getCode() == DataTransferObject.ERROR) {
						LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empCode={}",
								houseGuardLog.getOldPushCode());
					} else {
						EmployeeEntity empPush = SOAResParseUtil.getValueFromDataByKey(empPushJson, "employee", EmployeeEntity.class);
						if(!Check.NuNObj(empPush)){
							houseGuardLog.setOldPushName(empPush.getEmpName());
							houseGuardLog.setOldPushMobile(empPush.getEmpMobile());
						}
					}
				}*/
			}
			PageResult pageResult = new PageResult();
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{},params:{}", e, JsonEntityTransform.Object2Json(houseGuardDto));
			return new PageResult();
		}
	}
	
	/**
	 * 
	 * 房源管理-跳转修改房源专员关系列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseRequest
	 * @return
	 */
	@RequestMapping("editHouseGuard")
	public void editHouseGuard(HttpServletRequest request, HouseGuardDto houseGuardDto) {
		try {
			String resultJson = houseGuardService.searchHouseGuardList(JsonEntityTransform.Object2Json(houseGuardDto));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseGuardService#searchHouseGuardList接口调用失败,参数:{},结果:{}", 
						JsonEntityTransform.Object2Json(houseGuardDto), resultJson);
			}
			
			List<HouseGuardVo> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "rows", HouseGuardVo.class);
			if(!Check.NuNCollection(list)){
				Map<String, String> codeMap = new HashMap<String, String>();
				codeMap.put("nationCode", list.get(0).getNationCode());
				codeMap.put("provinceCode", list.get(0).getProvinceCode());
				codeMap.put("cityCode", list.get(0).getCityCode());
				codeMap.put("areaCode", list.get(0).getAreaCode());
				String cityJson = confCityService.getCityNameByCodeList(JsonEntityTransform.Object2Json(codeMap.values()));
				DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
				if(cityDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "confCityService#getCityNameByCodeList调用失败,参数:{}", 
							JsonEntityTransform.Object2Json(codeMap));
				}else{
					List<ConfCityEntity> cityList = cityDto.parseData("cityList", new TypeReference<List<ConfCityEntity>>(){});
					for (ConfCityEntity confCityEntity : cityList) {
						if(codeMap.get("nationCode").equals(confCityEntity.getCode())){
							request.setAttribute("nationCode", confCityEntity.getShowName());
						}
						if(codeMap.get("provinceCode").equals(confCityEntity.getCode())){
							request.setAttribute("provinceCode", confCityEntity.getShowName());
						}
						if(codeMap.get("cityCode").equals(confCityEntity.getCode())){
							request.setAttribute("cityCode", confCityEntity.getShowName());
						}
						if(codeMap.get("areaCode").equals(confCityEntity.getCode())){
							request.setAttribute("areaCode", confCityEntity.getShowName());
						}
					}
				}
				
				// 补全信息
				for (HouseGuardVo houseGuardVo : list) {
					this.completeInfo(null, null, houseGuardVo);
				}
			}
			request.setAttribute("data", list);
			request.setAttribute("jsonData", JsonEntityTransform.Object2Json(list));
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

	/**
	 * 
	 * 房源管理-新增(更新)房源专员关系
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseRequest
	 * @return
	 */
	@RequestMapping("updateHouseGuard")
	@ResponseBody
	public DataTransferObject mergeHouseGuard(HouseGuardParam houseGuardParam) {
		DataTransferObject dto = new DataTransferObject();
		try {
			String createFid = UserUtil.getCurrentUserFid();
			houseGuardParam.setCreateFid(createFid);
			LogUtil.info(LOGGER, "参数:{}", JsonEntityTransform.Object2Json(houseGuardParam));
			String resultJson = houseGuardService.batchMergeHouseGuardRel(JsonEntityTransform.Object2Json(houseGuardParam));
			dto = JsonEntityTransform.json2DataTransferObject(resultJson);
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "houseGuardService#batchUpdateHouseGuard接口调用失败,结果:{}", resultJson);
			}
			return dto;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{},params:{}", e, JsonEntityTransform.Object2Json(houseGuardParam));
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg(e.getMessage());
			return dto;
		}
	}
}
