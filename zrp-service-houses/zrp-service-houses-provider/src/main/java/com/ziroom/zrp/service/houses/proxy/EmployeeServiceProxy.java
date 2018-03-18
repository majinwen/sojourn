package com.ziroom.zrp.service.houses.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.service.EmployeeServiceImpl;
/**
 * <p>查询ZO信息代理类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月30日
 * @since 1.0
 */
@Component("houses.employeeServiceProxy")
public class EmployeeServiceProxy implements EmployeeService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceProxy.class);
	
	@Resource(name="houses.employeeServiceImpl")
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Override
	public String findEmployeeById(String fid) {
		LogUtil.info(LOGGER, "【findEmployeeById】通过ID查询ZO信息入参：{}", fid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("ID为空！");
			return dto.toJsonString();
		}
		try{
			EmployeeEntity employeeEntity = employeeServiceImpl.findEmployeeById(fid);
			LogUtil.info(LOGGER, "【findEmployeeById】查询ZO信息返回信息：{}", JSONObject.toJSON(employeeEntity));	
			if(Check.NuNObj(employeeEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未查询到ZO信息");
				return dto.toJsonString();
			}
			dto.putValue("employeeEntity", employeeEntity);
			return dto.toJsonString();
		}catch(Exception e){
			LogUtil.info(LOGGER, "【findEmployeeById】出错：{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常！");
			return dto.toJsonString();
		}
	}
	
	@Override
	public String findEmployeeByCode(String fcode) {
		LogUtil.info(LOGGER, "【findEmployeeByCode】入参：{}", fcode);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fcode)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("fcode参数为空！");
			return dto.toJsonString();
		}
		try{
			EmployeeEntity employeeEntity = employeeServiceImpl.findEmployeeByCode(fcode);
			LogUtil.info(LOGGER, "【findEmployeeByCode】通过fcode查询ZO信息返回：{}", JSONObject.toJSON(employeeEntity));	
			if(Check.NuNObj(employeeEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未查询到ZO信息");
				return dto.toJsonString();
			}
			dto.putValue("employeeEntity", employeeEntity);
			return dto.toJsonString();
		}catch(Exception e){
			LogUtil.info(LOGGER, "【findEmployeeByCode】出错：{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统异常！");
			return dto.toJsonString();
		}
	}
	

}
