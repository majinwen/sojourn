package com.zra.kanban.service;

import com.zra.common.dto.kanban.AudContractQueryDto;
import com.zra.common.utils.ZraConst;
import com.zra.kanban.dao.WorkBenchRentCallMapper;
import com.zra.kanban.entity.WorkBenchRentCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lixn49 on 2016/12/29.
 */
@Service
public class WorkBenchRentCallService {


	@Autowired
	private WorkBenchRentCallMapper workBenchRentCallMapper;


	/**
	* @Author lixn49
	* @Date 2016/12/29 15:08
	* @Description   房租催缴
	*/
	public List<WorkBenchRentCall> selectVoucherRemind(AudContractQueryDto queryDto){
		/*获取当前的日期，并且获取七天后的日期*/
		Date rentRemindDate =  new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, ZraConst.DELAY_RENT_REMIND_DAYS);
		rentRemindDate=cal.getTime();
		queryDto.setNotifyDate(rentRemindDate);  //获取到的七天后的日期
		return workBenchRentCallMapper.selectVoucherRemind(queryDto);
	}


}
