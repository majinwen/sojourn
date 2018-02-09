package com.ziroom.minsu.troy.staticMgt.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;

/**
 * <p>公共操作service</p>
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
@Service("staticMgt.commonService")
public class CommonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

	@Resource(name ="basedata.employeeService")
	private EmployeeService employeeService;
	
	/**
	 * 
	 * 根据员工表fid查询员工信息
	 *
	 * @author lunan
	 * @created 2016年8月19日 上午10:09:48
	 *
	 * @param empFid
	 * @return
	 * @throws SOAParseException
	 */
	public EmployeeEntity findEmployeeEntityByFid(String empFid) throws SOAParseException {
		String resultJson = employeeService.findEmployeByEmpFid(empFid);
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
		if (dto.getCode() == DataTransferObject.ERROR) {
			LogUtil.info(LOGGER, "employeeService#findEmployeeByEmpCode调用接口失败,empFid={}", empFid);
			return null;
		} else {
			EmployeeEntity emp =SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
			return emp;
		}
	}

}
