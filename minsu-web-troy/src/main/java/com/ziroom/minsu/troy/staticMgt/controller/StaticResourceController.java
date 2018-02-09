package com.ziroom.minsu.troy.staticMgt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;
import com.ziroom.minsu.services.basedata.api.inner.StaticResourceService;
import com.ziroom.minsu.services.basedata.dto.EmployeeRequest;
import com.ziroom.minsu.services.basedata.dto.StaticResourceRequest;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.StaticResourceVo;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.staticMgt.service.CommonService;
import com.ziroom.minsu.valenum.top.StaticResourceTypeEnum;
import com.ziroom.tech.storage.client.service.StorageService;

/**
 * 
 * <p>静态资源controller</p>
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
@RequestMapping("staticMgt")
public class StaticResourceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceController.class);

	@Resource(name="basedata.staticResourceService")
	private StaticResourceService staticResourceService;

	@Resource(name ="basedata.employeeService")
	private EmployeeService employeeService;
	
	@Resource(name ="staticMgt.commonService")
	private CommonService commonService;

	@Resource(name="storageService")
	private StorageService storageService; 

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

	/**
	 * 
	 * 静态资源管理-跳转静态资源页面
	 *
	 * @author liujun
	 * @created 2017年3月17日
	 *
	 * @param request
	 */
	@RequestMapping("staticResourceList")
	public void listHouseMsg(HttpServletRequest request) {
		request.setAttribute("staticResourceTypeMap", StaticResourceTypeEnum.getEnumMap());
		request.setAttribute("staticResourceTypeJson", JsonEntityTransform.Object2Json(StaticResourceTypeEnum.getEnumMap()));
	}
	
	/**
	 * 
	 * 静态资源管理-添加主题
	 *
	 * @author lunan
	 * @created 2017年3月20日
	 *
	 * @param request
	 */
	@RequestMapping("saveStaticResource")
	public String addStaticResource(HttpServletRequest request,@ModelAttribute StaticResourceVo staticResourceVo) {
		CurrentuserVo custVo =UserUtil.getFullCurrentUser();
		if (Check.NuNObj(custVo)) {
			return "redirect:/staticMgt/staticResourceList";
		}
		staticResourceVo.setCreateFid(custVo.getEmployeeFid());
		if(!Check.NuNObj(staticResourceVo)){
			if( staticResourceVo.getResType()==2 && Check.NuNStr(staticResourceVo.getMainPicParam())){
				LogUtil.info(LOGGER,"保存失败,图片参数未提交 ,picParam:{}",staticResourceVo.getMainPicParam());
				return "redirect:/staticMgt/staticResourceList";
			}
			DataTransferObject dto =  null;
			dto = JsonEntityTransform.json2DataTransferObject(staticResourceService.saveStaticEntity(JsonEntityTransform.Object2Json(staticResourceVo)));
			if(dto.getCode() == DataTransferObject.ERROR){
				LogUtil.info(LOGGER,"保存失败");
			}else{
				LogUtil.info(LOGGER,"保存成功");
			}
		}
		return "redirect:/staticMgt/staticResourceList";
	}
	
	/**
	 * 到添加静态资源主题页面
	 *
	 * @author lunan
	 * @created 2017年3月20日
	 *
	 */
	@RequestMapping("addStaticResource")
	public void addStaticResource(HttpServletRequest request){
		request.setAttribute("staticResourceTypeMap", StaticResourceTypeEnum.getEnumMap());
		/*String editFlag = request.getParameter("editFlag")==null?null:request.getParameter("editFlag");//修改标志 1=修改  其他是添加
		
		if(editFlag!=null&&"1".equals(editFlag)){
			String staticFid =  request.getParameter("staticFid");
			if(StringUtils.isNotBlank(staticFid)){
				
			}
		}*/
		
	}
	
	/**
	 * 
	 * 静态资源管理-查询静态资源列表
	 *
	 * @author liujun
	 * @created 2017年3月17日
	 *
	 * @param houseRequest
	 * @param request
	 * @return
	 */
	@RequestMapping("showResourceList")
	@ResponseBody
	public PageResult showResourceList(StaticResourceRequest resourceRequest) {
		if (Check.NuNObj(resourceRequest)) {
			return new PageResult();
		}
		try {
			Map<String,EmployeeEntity> empMap = new HashMap<String,EmployeeEntity>();
			// 创建人姓名不为空,查询创建人FID
			if(!Check.NuNStr(resourceRequest.getCreatorName())){
				EmployeeRequest empRequest = new EmployeeRequest();
				empRequest.setEmpName(resourceRequest.getCreatorName());

				String empJsonArray = employeeService.findEmployeeByCondition(JsonEntityTransform.Object2Json(empRequest));
				DataTransferObject empDto = JsonEntityTransform.json2DataTransferObject(empJsonArray);
				// 判断调用状态
				if(empDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<EmployeeEntity> empList = SOAResParseUtil.getListValueFromDataByKey(empJsonArray, "list", EmployeeEntity.class);
				// 如果查询结果为空,直接返回数据
				if(Check.NuNCollection(empList)){
					LogUtil.info(LOGGER, "返回创建人信息为空,参数:{}", JsonEntityTransform.Object2Json(empRequest));
					return new PageResult();
				}
				List<String> createFidList = new ArrayList<String>();
				for (EmployeeEntity emp : empList) {
					empMap.put(emp.getFid(), emp);
					createFidList.add(emp.getFid());
				}
				resourceRequest.setCreateFidList(createFidList);
			}
			String resultJson = staticResourceService.findStaticResourceListByPage(JsonEntityTransform.Object2Json(resourceRequest));
			DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
			// 判断调用状态
			if(resultDto.getCode() == DataTransferObject.ERROR){
				LogUtil.error(LOGGER, "接口调用失败,参数:{}", JsonEntityTransform.Object2Json(resourceRequest));
				return new PageResult();
			}

			List<StaticResourceVo> voList = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", StaticResourceVo.class);
			for (StaticResourceVo staticResourceVo : voList) {
				EmployeeEntity emp = null;
				if (!Check.NuNStrStrict(staticResourceVo.getCreateFid())) {
					if(empMap.containsKey(staticResourceVo.getCreateFid())){
						emp = empMap.get(staticResourceVo.getCreateFid());
						staticResourceVo.setCreatorName(emp.getEmpName());
					} else {
						emp = commonService.findEmployeeEntityByFid(staticResourceVo.getCreateFid());
						if(!Check.NuNObj(emp)){
							empMap.put(emp.getFid(), emp);
							staticResourceVo.setCreatorName(emp.getEmpName());
						}
					}
				}
			}

			PageResult pageResult = new PageResult();
			pageResult.setRows(voList);
			pageResult.setTotal(Long.valueOf(resultDto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			return new PageResult();
		} 
	}
	
	/**
	 * 
	 * 静态资源修改页
	 *
	 * @author bushujie
	 * @created 2017年7月5日 下午6:32:27
	 *
	 * @param request
	 * @param fid
	 * @throws SOAParseException 
	 */
	@RequestMapping("editStaticResource")
	public void editStaticResource(HttpServletRequest request,String resCode) throws SOAParseException{
		String staticResJson=staticResourceService.findStaticResByResCode(resCode);
		StaticResourceVo vo=SOAResParseUtil.getValueFromDataByKey(staticResJson, "res", StaticResourceVo.class);
		request.setAttribute("vo", vo);
		request.setAttribute("staticResourceTypeMap", StaticResourceTypeEnum.getEnumMap());
	}
	
	/**
	 * 
	 * 更新静态资源
	 *
	 * @author bushujie
	 * @created 2017年7月6日 上午11:37:13
	 *
	 * @param staticResourceEntity
	 */
	@RequestMapping("updateStaticResource")
	@ResponseBody
	public DataTransferObject updateStaticResource(StaticResourceVo staticResourceVo){
		String resutJson=staticResourceService.updateStaticResource(JsonEntityTransform.Object2Json(staticResourceVo));
		return JsonEntityTransform.json2DataTransferObject(resutJson);
	}
}