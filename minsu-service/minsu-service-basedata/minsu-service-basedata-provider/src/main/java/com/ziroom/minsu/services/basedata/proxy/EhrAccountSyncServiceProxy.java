/**
 * @FileName: EhrAccountSyncServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author jixd
 * @created 2016年4月23日 下午3:17:15
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.sys.CurrentuserEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.MapUrlParamUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.services.basedata.api.inner.EhrAccountSyncService;
import com.ziroom.minsu.services.basedata.service.EmployeeServiceImpl;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;

/**
 * <p>Ehr同步</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.ehrAccountSyncServiceProxy")
public class EhrAccountSyncServiceProxy implements EhrAccountSyncService{
	//日志
	private static final Logger LOGGER = LoggerFactory.getLogger(EhrAccountSyncServiceProxy.class);


	@Resource(name="basedata.smsTemplateProxy")
	private SmsTemplateService smsTemplateService;
	
	@Value(value = "${EMPLOYEE.EHR_EMPLOYEE_URL}")
    private String EHR_EMPLOYEE_URL;
	
	@Value(value="${EMPLOYEE.EHR_EMPCODE_URL}")
	private String EHR_EMPCODE_URL;
	
	@Resource(name="basedata.employeeServiceImpl")
	private EmployeeServiceImpl employeeServiceImpl;

	@Value(value="${UPS_SYNC_URL}")
	private String UPS_SYNC_URL;
	
	//每页数据
	private static final Integer SIZE = 100;
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.basedata.api.inner.EhrAccountSyncService#syncEmployeeTask()
	 */
	@Override
	public void syncEmployeeTask(int diffday) {
		Calendar calendar=Calendar.getInstance(); 
		final Date endDate = new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-diffday);
		final Date startDate = calendar.getTime();
		//启用线程异步执行，可以快速响应，不影响dubbo调用，否则会超时
        Thread task = new Thread(new Runnable() {
			@Override
			public void run() {
				//开始时间
				doEmployeeSync(DateUtil.dateFormat(startDate),DateUtil.dateFormat(endDate));
			}
		});
        SendThreadPool.execute(task);
	}
	/**
	 * 
	 * Ehr数据同步执行方法
	 *
	 * @author jixd
	 * @created 2016年4月27日 上午11:45:14
	 *
	 * @param startDate
	 * @param endDate
	 */
	private void doEmployeeSync(String startDate,String endDate){


		String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE.getCode());
		int page = 1;
		int totalPage = 0;
		int totalCount = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(SIZE));
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("isEnable", "1");
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Accept","application/json");
		LogUtil.info(LOGGER, "入参{}", JSONObject.toJSONString(map));
		String result = CloseableHttpUtil.sendGet(EHR_EMPLOYEE_URL+"?"+MapUrlParamUtil.getUrlParamsByMap(map), headerMap);
		LogUtil.info(LOGGER,"请求返回结果result={}",result);
		JSONObject jsonObj = JSONObject.parseObject(result);
		String message = jsonObj.getString("message");
		String status = jsonObj.getString("status");
		//failure 请求失败，success 请求成功
		if("success".equals(status)){
			//显示条数
			totalCount = jsonObj.getInteger("totleCount");
			//总页数
			totalPage = (totalCount + SIZE - 1) / SIZE;
		}else{
			if("没有查到相关数据".equals(message)){
				LogUtil.info(LOGGER, "同步接口结果info={}", message);
			}else{
				LogUtil.error(LOGGER, "同步接口请求出错error={}", message);
				msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.JOB_NOTICE_F.getCode());
			}
		}
		
		if(totalPage > 0){
			for(;page <= totalPage;page++){
				LogUtil.info(LOGGER, "当前页={}",page+":"+totalPage);
				if(page != 1){
					//翻页查询
					map.put("page", String.valueOf(page));
					LogUtil.info(LOGGER, "入参{}", JSONObject.toJSONString(map));
					result = CloseableHttpUtil.sendGet(EHR_EMPLOYEE_URL+"?"+MapUrlParamUtil.getUrlParamsByMap(map), headerMap);
					if (Check.NuNStr(result)) {
						LogUtil.error(LOGGER,"EHR接口返回数据为空");
						continue;
					}
				}
				//略过信息有问题的用户
				try {
					startSyncEmployee(result);
				} catch (Exception e) {
					LogUtil.info(LOGGER, "不完整信息：{}", result);
				}
			}
		}

		try {
			SmsRequest smsRequest  = new SmsRequest();
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("{1}", "Ehr员工数据同步");
			paramsMap.put("{2}", "更新数据"+String.valueOf(totalCount)+"条");
			smsRequest.setParamsMap(paramsMap);
			smsRequest.setMobile(ZkUtil.getZkSysValue(EnumMinsuConfig.minsu_mobileList.getType(),EnumMinsuConfig.minsu_mobileList.getCode()));
			smsRequest.setSmsCode(msgCode);
			smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
		}catch (Exception e){
			LogUtil.error(LOGGER,"定时任务发送短信失败：e：{}",e);
		}



	}
	
	/**
	 * 
	 * 同步处理方法
	 *
	 * @author jixd
	 * @created 2016年6月6日 下午3:23:18
	 *
	 * @param result
	 */
	private void startSyncEmployee(String result){
		JSONObject jsonObj = JSONObject.parseObject(result);
		String message = jsonObj.getString("message");
		String status = jsonObj.getString("status");
		//failure 请求失败，sucess 请求成功
		if("success".equals(status) || "sucess".equals(status)){
			JSONArray jsonArr = jsonObj.getJSONArray("data");
			for(int  i = 0;i < jsonArr.size();i++){
				JSONObject temp = jsonArr.getJSONObject(i);
				
				if(!isZiroomCode(temp.getString("setId"))){
					continue;
				}
				
				EmployeeEntity entity = new EmployeeEntity();
				//姓名
				entity.setEmpName(temp.getString("name"));
				//员工号
				entity.setEmpCode(temp.getString("emplid"));
				//邮箱
				entity.setEmpMail(temp.getString("email"));
				//电话
				entity.setEmpMobile(temp.getString("phone"));
				//是否删除
				entity.setIsDel(0);
				//ehr城市code
				entity.setEhrCityCode(String.valueOf(temp.getIntValue("cityCode")));
				//部门code
				entity.setDepartCode(temp.getString("deptCode"));
				//部门名称
				entity.setDepartName(temp.getString("dept"));
				//职位描述
				entity.setPostName(temp.getString("descr"));
				//职称编码
				entity.setPostCode(temp.getString("jobCode"));
				//0为有效状态
				entity.setEmpValid(0);
				//中心编号
				entity.setCenterCode(temp.getString("centerCode"));
				//中心名称
				entity.setCenter(temp.getString("center"));
				//组code
				entity.setGroupCode(temp.getString("groupCode"));
				//名称
				entity.setGroupName(temp.getString("group"));
				//分公司代码
				entity.setBranceCompanyCode(temp.getString("setId"));
				//创建时间
				//entity.setCreateDate(new Date());
				//最后修改时间
				entity.setLastModifyDate(new Date());
				entity.setFid(UUIDGenerator.hexUUID());


				EmployeeEntity employeeEntity = employeeServiceImpl.findEmployeeByEmpCode(entity.getEmpCode());
				CurrentuserEntity currentUser = new CurrentuserEntity();
				currentUser.setFid(UUIDGenerator.hexUUID());
				currentUser.setEmployeeFid(entity.getFid());
				String email = entity.getEmpMail();
				currentUser.setUserAccount(email==null ? "":email.substring(0, email.indexOf("@")));
				//0正常 1停用
				currentUser.setAccountStatus(0);
				//currentUser.setCreateDate(new Date());
				//是否是管理员 0 否 1是
				currentUser.setIsAdmin(0);
				//是否删除 0 否 1 是
				currentUser.setIsDel(0);
				currentUser.setLastModifyDate(new Date());
				//导入创建
				currentUser.setCreateFid("001");

				employeeServiceImpl.syncEhrEmployee(entity,currentUser);
				if (Check.NuNObj(employeeEntity)){
					//对应用户信息
					Map<String,String> map = new HashMap<>();
					map.put("Accept","application/json");
					try{
						Map<String,String> paramMap = new HashMap<>();
						paramMap.put("currentUser",JSONObject.toJSONString(currentUser));
						paramMap.put("empJson",JSONObject.toJSONString(entity));
						String s = CloseableHttpUtil.sendFormPost(UPS_SYNC_URL,paramMap,map);
						LogUtil.info(LOGGER,"同步ups数据返回结果result={}",s);
					}catch (Exception e){
						LogUtil.error(LOGGER,"【同步ups用户数据异常】",e);
					}

				}


			}
		}else{
			if("没有查到相关数据".equals(message)){
				LogUtil.info(LOGGER, "同步接口结果info={}", message);
			}else{
				LogUtil.error(LOGGER, "同步接口请求出错error={}", message);
			}
		}
	}
	
	/**
	 * 
	 * 判断是否是自如公司员工
	 *
	 * @author jixd
	 * @created 2016年4月27日 下午1:15:48
	 *
	 * @param code
	 * @return
	 */
	private boolean isZiroomCode(String code){
		if(code.equals("HL011") || code.equals("HL018") || code.equals("HL025")){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		Calendar calendar=Calendar.getInstance(); 
		Date endDate = new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-330);
		Date startDate = calendar.getTime();
		System.out.println(DateUtil.dateFormat(startDate));
	}
	
	
	/**
	 * 根据员工号同步员工信息
	 */
	@Override
	public String syncSingleEmployee(String empCode) {
		LogUtil.info(LOGGER, "syncSingleEmployee empCode{}", empCode);
		DataTransferObject dto=new DataTransferObject();
		Map<String, Object> map = new HashMap<>();
		String param = "["+empCode+"]";
		map.put("userCode", param);
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Accept","application/json");
		String result = CloseableHttpUtil.sendGet(EHR_EMPCODE_URL+"?"+MapUrlParamUtil.getUrlParamsByMap(map), headerMap);
		try {
			startSyncEmployee(result);
		} catch (Exception e) {
			dto.setErrCode(1);
			dto.setMsg("员工信息不完整！");
			LogUtil.info(LOGGER, "不完整信息：{}", result);
		}
		return dto.toJsonString();
	}
}
