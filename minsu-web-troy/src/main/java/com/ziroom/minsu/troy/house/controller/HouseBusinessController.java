/**
 * @FileName: HouseBusinessController.java
 * @Package com.ziroom.minsu.troy.house.controller
 * 
 * @author bushujie
 * @created 2016年7月7日 上午10:13:03
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.house.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.house.api.inner.HouseBusinessService;
import com.ziroom.minsu.services.house.dto.HouseBusinessDto;
import com.ziroom.minsu.services.house.dto.HouseBusinessInputDto;
import com.ziroom.minsu.services.house.entity.HouseBusinessListVo;
import com.ziroom.minsu.troy.common.util.UserUtil;

/**
 * <p>房源商机controller</p>
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
@Controller
@RequestMapping("house/houseBusiness")
public class HouseBusinessController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseMgtController.class);
	
	@Resource(name="house.houseBusinessService")
	private HouseBusinessService houseBusinessService;
	
	@Resource(name = "basedata.confCityService")
	private ConfCityService confCityService;
	
	@Resource(name = "basedata.employeeService")
	private EmployeeService employeeService;
	
	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	
	
	/**
	 * 
	 * 房源商机列表分页查询
	 *
	 * @author bushujie
	 * @created 2016年7月8日 下午7:50:18
	 *
	 * @param request
	 * @throws SOAParseException
	 */
	@RequestMapping("houseBusinessList")
	public void houseBusinessList(HttpServletRequest request) throws SOAParseException{
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
	}
	
	/**
	 * 
	 * 查询房源商机列表
	 *
	 * @author bushujie
	 * @created 2016年7月7日 上午11:14:18
	 *
	 * @return
	 * @throws SOAParseException 
	 */
	@RequestMapping("showHouseBusinessList")
	@ResponseBody
	public PageResult showHouseBusinessList (HouseBusinessDto houseBusinessDto) {
		try{
			//如果地推管家手机号不为空，根据手机号查询员工编号
			 if(!Check.NuNStr(houseBusinessDto.getDtGuardMobile())){
				 EmployeeRequest emp=new EmployeeRequest();
				 emp.setEmpMobile(houseBusinessDto.getDtGuardMobile());
				 String empJson=employeeService.findEmployeeByCondition(JsonEntityTransform.Object2Json(emp));
				 List<EmployeeEntity> list=SOAResParseUtil.getListValueFromDataByKey(empJson, "list", EmployeeEntity.class);
				 if(!Check.NuNCollection(list)){
					 houseBusinessDto.setDtGuardCode(list.get(0).getEmpCode());
				 } else {
					 houseBusinessDto.setDtGuardCode("无数据");
				}
			 }
			String resultJson= houseBusinessService.houseBusinessList(JsonEntityTransform.Object2Json(houseBusinessDto));
			List<HouseBusinessListVo> list=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", HouseBusinessListVo.class);
			//循环查询地推管家手机号
			for(HouseBusinessListVo vo:list){
				String empJson=employeeService.findEmployeeByEmpCode(vo.getDtGuardCode());
				EmployeeEntity emp=SOAResParseUtil.getValueFromDataByKey(empJson, "employee", EmployeeEntity.class);
				if(!Check.NuNObj(emp)){
					vo.setDtGuardMobile(emp.getEmpMobile());
				}
			}
			long total=SOAResParseUtil.getLongFromDataByKey(resultJson, "total");
			PageResult pageResult=new PageResult();
			pageResult.setRows(list);
			pageResult.setTotal(total);
			return pageResult;
		} catch( Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		}
	}
	
	/**
	 * 
	 * 房源商机添加页
	 *
	 * @author bushujie
	 * @created 2016年7月7日 下午1:57:43
	 *
	 * @param request
	 * @throws SOAParseException 
	 */
	@RequestMapping("houseBusinessAdd")
	public void houseBusinessAdd(HttpServletRequest request) throws SOAParseException{
		String resultJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		request.setAttribute("registerDate", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
	}
	
	/**
	 * 
	 * 保存房源
	 *
	 * @author bushujie
	 * @created 2016年7月7日 下午2:47:28
	 *
	 * @param businessMsg
	 * @param businessExt
	 * @param businessSource
	 * @return
	 */
	@RequestMapping("saveHouseBusiness")
	@ResponseBody
	public DataTransferObject saveHouseBusiness(HouseBusinessInputDto houseBusinessInputDto){
		DataTransferObject dto=new DataTransferObject();
		try{
			if(!Check.NuNStr(houseBusinessInputDto.getReleaseDate())){
				houseBusinessInputDto.getBusinessMsg().setReleaseDate(DateUtil.parseDate(houseBusinessInputDto.getReleaseDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getPutawayDate())){
				houseBusinessInputDto.getBusinessMsg().setPutawayDate(DateUtil.parseDate(houseBusinessInputDto.getPutawayDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getMakeCheckDate())){
				houseBusinessInputDto.getBusinessMsg().setMakeCheckDate(DateUtil.parseDate(houseBusinessInputDto.getMakeCheckDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getRealCheckDate())){
				houseBusinessInputDto.getBusinessMsg().setRealCheckDate(DateUtil.parseDate(houseBusinessInputDto.getRealCheckDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getMakePhotoDate())){
				houseBusinessInputDto.getBusinessMsg().setMakePhotoDate(DateUtil.parseDate(houseBusinessInputDto.getMakePhotoDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getRealPhotoDate())){
				houseBusinessInputDto.getBusinessMsg().setRealPhotoDate(DateUtil.parseDate(houseBusinessInputDto.getRealPhotoDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getRegisterDate())){
				houseBusinessInputDto.getBusinessMsg().setRegisterDate(DateUtil.parseDate(houseBusinessInputDto.getRegisterDate(), "yyyy-MM-dd"));
			}
			StringBuffer houseAddr=new StringBuffer();
			if(!Check.NuNStr(houseBusinessInputDto.getBusinessMsg().getCityCode())){
				String codeNameJson=confCityService.getCityNameByCode(houseBusinessInputDto.getBusinessMsg().getCityCode());
				DataTransferObject cityDto=JsonEntityTransform.json2DataTransferObject(codeNameJson);
				houseAddr.append(cityDto.getData().get("cityName"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getBusinessMsg().getAreaCode())){
				String codeNameJson=confCityService.getCityNameByCode(houseBusinessInputDto.getBusinessMsg().getAreaCode());
				DataTransferObject cityDto=JsonEntityTransform.json2DataTransferObject(codeNameJson);
				houseAddr.append(cityDto.getData().get("cityName"));
			}
			houseAddr.append(houseBusinessInputDto.getBusinessMsg().getHouseAddr());
			houseBusinessInputDto.getBusinessMsg().setHouseAddr(houseAddr.toString());
			houseBusinessInputDto.getBusinessMsg().setCreateFid(UserUtil.getCurrentUserFid());
			houseBusinessInputDto.getBusinessSource().setCreateFid(UserUtil.getCurrentUserFid());
			//查询用户表中是否存在此房东
			String customerJson=customerInfoService.getCustomerByMobile(houseBusinessInputDto.getBusinessExt().getLandlordMobile());
			CustomerBaseMsgEntity customerBaseMsgEntity=SOAResParseUtil.getValueFromDataByKey(customerJson, "customerBase", CustomerBaseMsgEntity.class);
			if(!Check.NuNObj(customerBaseMsgEntity)&&customerBaseMsgEntity.getIsLandlord()==1&&customerBaseMsgEntity.getIsDel()==0){
				String houseCountJson=houseBusinessService.findHouseCountByUid(customerBaseMsgEntity.getUid());
				int houseCount=SOAResParseUtil.getIntFromDataByKey(houseCountJson, "count");
				if(houseCount>0){
					dto.setErrCode(1);
					dto.setMsg("该房东在客户表中已经存在!");
					return dto;
				}
			}
			//查询判断房东的地推管家员工号
			HouseBusinessDto houseBusinessDto=new HouseBusinessDto();
			houseBusinessDto.setLandlordMobile(houseBusinessInputDto.getBusinessExt().getLandlordMobile());
			String resultJson=houseBusinessService.findDtGuardCodeByLandlord(JsonEntityTransform.Object2Json(houseBusinessDto));
			String dtGuardCode=SOAResParseUtil.getStrFromDataByKey(resultJson, "dtGuardCode");
			CurrentuserVo currentuserVo=UserUtil.getFullCurrentUser();
			if(!Check.NuNStr(dtGuardCode)){
				if(!dtGuardCode.equals(currentuserVo.getEmpCode())){
					LogUtil.info(LOGGER, "地推管家员工号{}，登录管家员工号{}", dtGuardCode,currentuserVo.getEmpCode());
					dto.setErrCode(1);
					dto.setMsg("该房东在房源商机表中已经存在");
					return dto;
				}
			}
			//地推管家赋值
			houseBusinessInputDto.getBusinessExt().setDtGuardCode(currentuserVo.getEmpCode());
			houseBusinessInputDto.getBusinessExt().setDtGuardName(currentuserVo.getFullName());
			String insertJson=houseBusinessService.insertHouseBusiness(JsonEntityTransform.Object2Json(houseBusinessInputDto));
			return JsonEntityTransform.json2DataTransferObject(insertJson);
		} catch( Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务异常，请联系管理员!");
			return dto;
		}
	}
	
	/**
	 * 
	 * 房源商机详情
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午2:50:22
	 *
	 * @param request
	 * @param businessFid
	 * @throws SOAParseException 
	 */
	@RequestMapping("showHouseBusinessDetail")
	public void showHouseBusinessDetail(HttpServletRequest request,String businessFid) throws SOAParseException{
		//城市列表
		String cityJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(cityJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		//商机详情
		String resultJson=houseBusinessService.findHouseBusinessDetailByFid(businessFid);
		HouseBusinessInputDto houseBusinessInputDto=SOAResParseUtil.getValueFromDataByKey(resultJson, "businessInfo", HouseBusinessInputDto.class);
		request.setAttribute("businessInfo", houseBusinessInputDto);
	}
	
	/**
	 * 
	 * 房源商机修改页
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午4:36:42
	 *
	 * @param request
	 * @param businessFid
	 * @param flag
	 * @throws SOAParseException 
	 */
	@RequestMapping("houseBusinessUpdate")
	public void houseBusinessUpdate(HttpServletRequest request,String businessFid,int flag) throws SOAParseException{
		//城市列表
		String cityJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(cityJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		//商机详情
		String resultJson=houseBusinessService.findHouseBusinessDetailByFid(businessFid);
		HouseBusinessInputDto houseBusinessInputDto=SOAResParseUtil.getValueFromDataByKey(resultJson, "businessInfo", HouseBusinessInputDto.class);
		request.setAttribute("businessInfo", houseBusinessInputDto);
		request.setAttribute("flag", flag);
	}
	
	/**
	 * 
	 * 更新房源商机信息
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午5:12:29
	 *
	 * @param houseBusinessInputDto
	 */
	@RequestMapping("updateHouseBusiness")
	@ResponseBody
	public DataTransferObject updateHouseBusiness(HouseBusinessInputDto houseBusinessInputDto){
		DataTransferObject dto=new DataTransferObject();
		try{
			if(!Check.NuNStr(houseBusinessInputDto.getReleaseDate())){
				houseBusinessInputDto.getBusinessMsg().setReleaseDate(DateUtil.parseDate(houseBusinessInputDto.getReleaseDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getPutawayDate())){
				houseBusinessInputDto.getBusinessMsg().setPutawayDate(DateUtil.parseDate(houseBusinessInputDto.getPutawayDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getMakeCheckDate())){
				houseBusinessInputDto.getBusinessMsg().setMakeCheckDate(DateUtil.parseDate(houseBusinessInputDto.getMakeCheckDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getRealCheckDate())){
				houseBusinessInputDto.getBusinessMsg().setRealCheckDate(DateUtil.parseDate(houseBusinessInputDto.getRealCheckDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getMakePhotoDate())){
				houseBusinessInputDto.getBusinessMsg().setMakePhotoDate(DateUtil.parseDate(houseBusinessInputDto.getMakePhotoDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getRealPhotoDate())){
				houseBusinessInputDto.getBusinessMsg().setRealPhotoDate(DateUtil.parseDate(houseBusinessInputDto.getRealPhotoDate(), "yyyy-MM-dd"));
			}
			if(!Check.NuNStr(houseBusinessInputDto.getRegisterDate())){
				houseBusinessInputDto.getBusinessMsg().setRegisterDate(DateUtil.parseDate(houseBusinessInputDto.getRegisterDate(), "yyyy-MM-dd"));
			}
			String resultJson=houseBusinessService.updateHouseBusiness(JsonEntityTransform.Object2Json(houseBusinessInputDto));
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务异常，请联系管理员!");
			return dto;
		}
	}
	
	/**
	 * 
	 * 删除房源商机
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午6:10:44
	 *
	 * @param businessFid
	 * @return
	 */
	@RequestMapping("delHouseBusiness")
	@ResponseBody
	public DataTransferObject delHouseBusiness(String businessFid){
		DataTransferObject dto=new DataTransferObject();
		try{
			String resultJson =houseBusinessService.delHouseBusiness(businessFid);
			return JsonEntityTransform.json2DataTransferObject(resultJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(1);
			dto.setMsg("服务异常，请联系管理员!");
			return dto;
		}
	}
	
	/**
	 * 
	 * 房源商机特殊修改页
	 *
	 * @author bushujie
	 * @created 2016年7月9日 下午4:36:42
	 *
	 * @param request
	 * @param businessFid
	 * @param flag
	 * @throws SOAParseException 
	 */
	@RequestMapping("houseBusinessSpecialUpdate")
	public void houseBusinessSpecialUpdate(HttpServletRequest request,String businessFid,int flag) throws SOAParseException{
		//城市列表
		String cityJson=confCityService.getConfCitySelect();
		List<TreeNodeVo> cityTreeList=SOAResParseUtil.getListValueFromDataByKey(cityJson, "list", TreeNodeVo.class);
		request.setAttribute("cityTreeList", JsonEntityTransform.Object2Json(cityTreeList));
		//商机详情
		String resultJson=houseBusinessService.findHouseBusinessDetailByFid(businessFid);
		HouseBusinessInputDto houseBusinessInputDto=SOAResParseUtil.getValueFromDataByKey(resultJson, "businessInfo", HouseBusinessInputDto.class);
		request.setAttribute("businessInfo", houseBusinessInputDto);
		request.setAttribute("flag", flag);
	}
}
