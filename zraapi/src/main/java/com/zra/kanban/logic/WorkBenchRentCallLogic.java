package com.zra.kanban.logic;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.utils.StrUtils;
import com.zra.kanban.entity.WorkBenchRentCall;
import com.zra.kanban.service.WorkBenchRentCallService;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.service.EmployeeService;
import com.zra.system.service.UserAccountService;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by lixn49 on 2016/12/29.
 */
@Component
public class WorkBenchRentCallLogic {
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(WorkBenchRentCallLogic.class);

	@Autowired
	private WorkBenchRentCallService workBenchRentCallService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	* @Author lixn49
	* @Date 2016/12/29 15:21
	* @Description   房租催缴
	*/
	public List<WorkBenchRentCall> selectVoucherRemind(AudContractQueryDto queryDto){
		if (userAccountService.isZO(queryDto.getUserId())) {
			EmployeeEntity employee = employeeService.getEmployeeByUserId(queryDto.getUserId());
			queryDto.setZoCode(employee.getCode());
		}
		
		List<WorkBenchRentCall> workBenchRentCalls=new ArrayList<>();
		workBenchRentCalls=workBenchRentCallService.selectVoucherRemind(queryDto);
		if(workBenchRentCalls!=null && workBenchRentCalls.size()>0){
			for (WorkBenchRentCall workBenchRentCall : workBenchRentCalls) {
				Date oughtPaymentDate = workBenchRentCall.getOughtPaymentDate();
				if(StrUtils.isNotNullOrBlank(oughtPaymentDate)){
					workBenchRentCall.setOughtPaymentDateDisplay(DateUtilFormate.formatDateToString(oughtPaymentDate,DateUtilFormate.DATEFORMAT_4));
				}
				if (workBenchRentCall.getOughtTotalAmount() != null) {
					workBenchRentCall.setOutghtTotalAmountDisplay(workBenchRentCall.getOughtTotalAmount().toString());
				} else {
					workBenchRentCall.setOutghtTotalAmountDisplay("0.00");
				}
				if (workBenchRentCall.getActualTotalAmount() != null) {
					workBenchRentCall.setActualTotalAmountDisplay(workBenchRentCall.getActualTotalAmount().toString());
				} else {
					workBenchRentCall.setActualTotalAmountDisplay("0.00");
				}
			}
		}
		return workBenchRentCalls;
	}
}
