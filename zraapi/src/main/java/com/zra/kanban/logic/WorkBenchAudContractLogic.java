package com.zra.kanban.logic;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.common.utils.DateUtilFormate;
import com.zra.kanban.entity.WorkBenchAudContract;
import com.zra.kanban.service.WorkBenchAudContractService;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.entity.UserAccountEntity;
import com.zra.system.service.EmployeeService;
import com.zra.system.service.UserAccountService;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixn49 on 2016/12/26.
 */
@Component
public class WorkBenchAudContractLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(WorkBenchAudContractLogic.class);

	@Autowired
	private WorkBenchAudContractService audContractService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * @Author lixn49
	 * @Date 2016/12/26 11:32
	 * @Description   根据项目id 未审核通过的合同
	 */
	public List<WorkBenchAudContract> selectNoAudContractList(AudContractQueryDto queryDto){
		List<WorkBenchAudContract> resultList = new ArrayList<>();
				
		if (userAccountService.isZO(queryDto.getUserId())) {
			EmployeeEntity employee = employeeService.getEmployeeByUserId(queryDto.getUserId());
			queryDto.setZoCode(employee.getCode());
		}
		
		resultList = audContractService.selectNoAudContractList(queryDto);
		if (resultList != null && resultList.size() > 0) {
			for (WorkBenchAudContract entity : resultList) {
				if (entity.getConAuditDate() != null) {
					entity.setConAuditDateDisplay(DateUtilFormate.formatDateToString(entity.getConAuditDate(), DateUtilFormate.DATEFORMAT_4));
				}
			}
		}
		return resultList;
	}
}
