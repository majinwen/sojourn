package com.zra.task.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.zra.task.fourooTelephoneTask.FourooTelephoneTask;
import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.business.logic.BusinessLogic;
import com.zra.common.utils.TaskConcurrentControl;
import com.zra.house.dao.RoomInfoMapper;
import com.zra.house.logic.RoomInfoLogic;
import com.zra.task.business.EvaluateRemindSmsTask;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wangws21 2016年8月26日
 * 定时任务手动测试接口
 */
@Component
@Path("/task")
@Api(value="/task")
public class TaskResources {
	
	@Autowired
    private BusinessLogic businessLogic;
	
	@Autowired
	private RoomInfoLogic roomInfoLogic;

	@Autowired
	private FourooTelephoneTask  fourooTelephone;

    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(EvaluateRemindSmsTask.class);

	@GET
	@Path("/testSendYkRemindSms/v1")
	@Produces("application/json")
	@ApiOperation(value="测试预约提醒定时任务",httpMethod = "GET")
	public String testSendYkRemindSms() {
		 if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
            businessLogic.sendYkRemindSms();
            return "启动任务成功";
        } else {
           return "sendYkRemindSms未获得任务锁，任务取消";
        }
    }
	@GET
	@Path("/testBindPhoneTask/v1")
	public String testBindPhoneTask(){
		fourooTelephone.bindPhoneTask();
		return "任务启动";
	}
	/**
	 * 修改房间状态为可预订.
	 * @param startDate 处理哪一天数据到当前时间数据
	 * @return
	 */
	@GET
	@Path("/modifyPreStatus/v1")
	@Produces("application/json")
	public Response modifyPreStatus() {
        roomInfoLogic.modifyPreStatus();
        return Response.ok().build();
	}
	
	@GET
	@Path("/testSendEvaluateRemindSms/v1")
	@Produces("application/json")
	@ApiOperation(value = "测试评价推送定时任务", httpMethod = "GET")
	public String testSendEvaluateRemindSms() {
		if (TaskConcurrentControl.getLock() != null && TaskConcurrentControl.getLock().equals("1")) {
			businessLogic.sendEvaluateRemindSms();
			return "启动任务成功";
		} else {
			return "sendYkRemindSms未获得任务锁，任务取消";
		}
	}


}
