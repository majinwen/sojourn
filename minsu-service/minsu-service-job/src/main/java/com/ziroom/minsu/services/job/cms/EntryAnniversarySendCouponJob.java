/**
 * @FileName: EntryAnniversarySendCouponJob.java
 * @Package com.ziroom.minsu.services.job.cms
 * 
 * @author loushuai
 * @created 2018年1月2日 下午3:38:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.job.cms;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.search.DateTerm;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.dto.BindCouponMobileRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.message.api.inner.HuanxinImRecordService;

/**
 * <p>入职周年灌券，规则：每天跑一次，入职一周年，100元优惠券券，入职两周年200元券  对每一个员工的相应手机号，发放对应金额的优惠券。
 *    N=（当前年份-入职年份）x100，当（当前年份-入职年份）>=5时，N=500。</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class EntryAnniversarySendCouponJob extends AsuraJob{

	private static final Logger LOGGER = LoggerFactory.getLogger(EntryAnniversarySendCouponJob.class);

	/**
	 * 入职周年纪念灌券
	 * 1，每天中午11点执行
	 * 0 0 11 * * ?
	 */
	@Override
	public void run(JobExecutionContext arg0) {
		int page = 0;
		int year = 0;
		try {
			ResourceBundle resource = ResourceBundle.getBundle("job");
			String EMPLOYEE_JOIN_TIME_LIST_URL=resource.getString("EMPLOYEE_JOIN_TIME_LIST_URL");
			//获取今天，一年前的今日，两年前的今日......的入职时间，根据入职时查询入职的员工及其入职周年
			Date now = new Date();
			//最早的自如入职时间
			Date earliestTime = null;
			//每年“此日期”的时间
			Date time = null;
			try {
				//now = DateUtil.parseDate("2018-11-21", "yyyy-MM-dd");
				earliestTime = DateUtil.parseDate("2001-10-22", "yyyy-MM-dd");
			} catch (ParseException e1) {
				LogUtil.error(LOGGER, "【EntryAnniversarySendCouponJob】page={}, year={}, earliestTime={}", page, year, earliestTime);
				return;
			}
			do {
					year++;
					 //一年前的今日
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					calendar.add(Calendar.YEAR, -year);
					time = calendar.getTime();
					String oneYearAgoTime = DateUtil.dateFormat(time, "YYYYMMdd");
					
					String result = null;
					String errorCode  = "5000";
					JSONArray  dataArray = null;
					do {
							page++;
							LogUtil.info(LOGGER, "【EntryAnniversarySendCouponJob】page={}, year={}", page, year);
							//获取year周年的员工信息列表
							StringBuffer buffer = new StringBuffer();
							buffer.append("?pageNo=");
							buffer.append(page);
							buffer.append("&pageSize=");
							buffer.append(10);
							buffer.append("&startJoinTime=");
							buffer.append(Integer.valueOf(oneYearAgoTime));
							buffer.append("&endJoinTime=");
							buffer.append(Integer.valueOf(oneYearAgoTime));
							String param = buffer.toString();
							String requestUrl = EMPLOYEE_JOIN_TIME_LIST_URL+param;
							LogUtil.info(LOGGER, "获取入职时间列表请求参数， EMPLOYEE_JOIN_TIME_LIST_URL={},param={},requestUrl={}", EMPLOYEE_JOIN_TIME_LIST_URL, param, requestUrl);
							result = CloseableHttpUtil.sendGet(requestUrl, null);
							LogUtil.info(LOGGER, "获取入职时间列表请求结果，requestUrl={}, result={}",requestUrl ,result);
							
							//根据对应的周年，发放对应的优惠券
							if(!Check.NuNStr(result)){
								JSONObject resultObj = JSONObject.parseObject(result);
								errorCode  = resultObj.getString("errorCode");
								if("20000".equals(errorCode)){
									JSONObject data = resultObj.getJSONObject("data");
								    dataArray = data.getJSONArray("list");
								    if(!dataArray.isEmpty()){
								    	for(int j=0;j<dataArray.size();j++){
											JSONObject obj = dataArray.getJSONObject(j);
											if(!Check.NuNObj(obj.getString("empCode")) && !Check.NuNObj(obj.getString("phone"))){
												try {
													//开始灌券
													switch (year) {
														case 1:
															//灌100元券
															pullActivityByMobile(obj.getString("phone"), "HR1801");
															break;
														case 2:
															//灌200元券
															pullActivityByMobile(obj.getString("phone"), "HR1802");
															break;
														case 3:
															//灌300元券
															pullActivityByMobile(obj.getString("phone"), "HR1803");
															break;
														case 4:
															//灌400元券
															pullActivityByMobile(obj.getString("phone"), "HR1804");
															break;
														case 5:
															//灌500元券
															pullActivityByMobile(obj.getString("phone"), "HR1805");
															break;
														default:
															//大于500的，灌500元券
															pullActivityByMobile(obj.getString("phone"), "HR1805");
															break;
													}
												} catch (Exception e) {
													LogUtil.error(LOGGER, "周年纪念灌券job,循环发券部分发生异常,继续下次循环 ,e={},year={}",e ,year);
													continue;
												}
											}
										}
								    }
								}
							}
					} while (!Check.NuNStr(result) && "20000".equals(errorCode) && !dataArray.isEmpty());
				//进入外层循环的时候，page置为0，year不能置为0
				page=0;
			} while (time.after(earliestTime));
			LogUtil.info(LOGGER, "EntryAnniversarySendCouponJob, earliestTime={}，year={}", earliestTime,year);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, "EntryAnniversarySendCouponJob, 周年纪念灌券job发生异常 ",e);
		}
	}
	
	/**
	 * 
	 * 根据电话灌券
	 *
	 * @author loushuai
	 * @created 2018年1月3日 下午1:52:25
	 *
	 * @param phone
	 * @param actSn
	 */
	public static void pullActivityByMobile(String phone, String actSn){
		ActCouponService actCouponService = (ActCouponService) ApplicationContext.getContext().getBean("job.actCouponService");
		if(Check.NuNObj(actCouponService)){
			LogUtil.error(LOGGER, "actCouponService");
		}
		MobileCouponRequest mcRequest = new MobileCouponRequest();
		mcRequest.setActSn(actSn);
		mcRequest.setMobile(phone);
		String resultJson = JsonEntityTransform.Object2Json(mcRequest);
		LogUtil.info(LOGGER, "周年纪念灌券job，开始灌券={}", resultJson);
		actCouponService.pullActivityByMobile(resultJson);
	}
	
	/*public static void main(String[] args) {
		int page = 0;
		int year = 0;
		try {
			ResourceBundle resource = ResourceBundle.getBundle("job");
			String EMPLOYEE_JOIN_TIME_LIST_URL=resource.getString("EMPLOYEE_JOIN_TIME_LIST_URL");
			//获取今天，一年前的今日，两年前的今日......的入职时间，根据入职时查询入职的员工及其入职周年
			Date now = new Date();
			//最早的自如入职时间
			Date earliestTime = null;
			//每年“此日期”的时间
			Date time = null;
			try {
				now = DateUtil.parseDate("2018-11-21", "yyyy-MM-dd");
				earliestTime = DateUtil.parseDate("2001-10-22", "yyyy-MM-dd");
			} catch (ParseException e1) {
				LogUtil.error(LOGGER, "【EntryAnniversarySendCouponJob】page={}, year={}, earliestTime={}", page, year, earliestTime);
				return;
			}
			do {
					year++;
					 //一年前的今日
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(now);
					calendar.add(Calendar.YEAR, -year);
					time = calendar.getTime();
					String oneYearAgoTime = DateUtil.dateFormat(time, "YYYYMMdd");
					
					String result = null;
					String errorCode  = "5000";
					JSONArray  dataArray = null;
					do {
							page++;
							LogUtil.info(LOGGER, "【EntryAnniversarySendCouponJob】page={}, year={}", page, year);
							//获取year周年的员工信息列表
							StringBuffer buffer = new StringBuffer();
							buffer.append("?pageNo=");
							buffer.append(page);
							buffer.append("&pageSize=");
							buffer.append(10);
							buffer.append("&startJoinTime=");
							buffer.append(Integer.valueOf(oneYearAgoTime));
							buffer.append("&endJoinTime=");
							buffer.append(Integer.valueOf(oneYearAgoTime));
							String param = buffer.toString();
							String requestUrl = EMPLOYEE_JOIN_TIME_LIST_URL+param;
							LogUtil.info(LOGGER, "获取入职时间列表请求参数， EMPLOYEE_JOIN_TIME_LIST_URL={},param={},requestUrl={}", EMPLOYEE_JOIN_TIME_LIST_URL, param, requestUrl);
							result = CloseableHttpUtil.sendGet(requestUrl, null);
							LogUtil.info(LOGGER, "获取入职时间列表请求结果，requestUrl={}, result={}",requestUrl ,result);
							
							//根据对应的周年，发放对应的优惠券
							if(!Check.NuNStr(result)){
								JSONObject resultObj = JSONObject.parseObject(result);
								errorCode  = resultObj.getString("errorCode");
								if("20000".equals(errorCode)){
									JSONObject data = resultObj.getJSONObject("data");
								    dataArray = data.getJSONArray("list");
								    if(!dataArray.isEmpty()){
								    	for(int j=0;j<dataArray.size();j++){
											JSONObject obj = dataArray.getJSONObject(j);
											if(!Check.NuNObj(obj.getString("empCode")) && !Check.NuNObj(obj.getString("phone"))){
												try {
													//开始灌券
													switch (year) {
														case 1:
															//灌100元券
															pullActivityByMobile(obj.getString("phone"), "HR1801");
															break;
														case 2:
															//灌200元券
															pullActivityByMobile(obj.getString("phone"), "HR1802");
															break;
														case 3:
															//灌300元券
															pullActivityByMobile(obj.getString("phone"), "HR1803");
															break;
														case 4:
															//灌400元券
															pullActivityByMobile(obj.getString("phone"), "HR1804");
															break;
														case 5:
															//灌500元券
															pullActivityByMobile(obj.getString("phone"), "HR1805");
															break;
														default:
															//大于500的，灌500元券
															pullActivityByMobile(obj.getString("phone"), "HR1805");
															break;
													}
												} catch (Exception e) {
													LogUtil.error(LOGGER, "周年纪念灌券job,循环发券部分发生异常,继续下次循环 ,e={},year={}",e ,year);
													continue;
												}
											}
										}
								    }
								}
							}
					} while (!Check.NuNStr(result) && "20000".equals(errorCode) && !dataArray.isEmpty());
				//进入外层循环的时候，page置为0，year不能置为0
				page=0;
			} while (time.after(earliestTime));
			LogUtil.info(LOGGER, "EntryAnniversarySendCouponJob, earliestTime={}，year={}", earliestTime,year);
		} catch (NumberFormatException e) {
			LogUtil.error(LOGGER, "EntryAnniversarySendCouponJob, 周年纪念灌券job发生异常 ",e);
		}
	}*/
}
