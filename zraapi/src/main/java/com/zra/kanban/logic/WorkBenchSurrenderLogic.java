package com.zra.kanban.logic;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.common.utils.DateUtilFormate;
import com.zra.kanban.entity.WorkBenchSurrender;
import com.zra.kanban.service.WorkBenchSurrenderService;
import com.zra.system.entity.EmployeeEntity;
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
public class WorkBenchSurrenderLogic {
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(WorkBenchSurrenderLogic.class);

	@Autowired
	private WorkBenchSurrenderService surrenderService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * @Author lixn49
	 * @Date 2016/12/26 11:32
	 * @Description   根据项目id 未审核通过的解约协议
	 */
	public List<WorkBenchSurrender> selectNoAudSurrendertList(AudContractQueryDto queryDto){
		List<WorkBenchSurrender> resultList = new ArrayList<>();
		
		if (userAccountService.isZO(queryDto.getUserId())) {
			EmployeeEntity employee = employeeService.getEmployeeByUserId(queryDto.getUserId());
			queryDto.setZoCode(employee.getCode());
		}
		
		resultList = surrenderService.selectNoAudSurrendertList(queryDto);
		if (resultList != null && resultList.size() > 0) {
			for (WorkBenchSurrender entity : resultList) {
				if (entity.getRentauditdate() != null) {
					entity.setRentAuditDateDisplay(DateUtilFormate.formatDateToString(entity.getRentauditdate(), DateUtilFormate.DATEFORMAT_4));
				}
			}
		}
		return resultList;
	}
}
