package com.zra.report.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.utils.DateUtil;
import com.zra.report.logic.ReportBoLogic;
import com.zra.report.logic.ReportPaymentLogic;
import com.zra.report.logic.ReportRenewLogic;
import com.zra.report.logic.ReportStockLogic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Component
@Path("/report")
@Api(value="/report")
public class ReportSourceResource {
	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportResource.class);
	
	@Autowired
	private ReportBoLogic reportBoLogic;
	
	@Autowired
	private ReportRenewLogic reportRenewLogic;
	
	@Autowired
	private ReportStockLogic reportStockLogic;
	
	@Autowired
	private ReportPaymentLogic reportPaymentLogic;
	
	@GET
	@Path("/reportBo/save/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "存储运营报表商机数据", notes = "存储运营报表商机数据", response = Response.class)
	public int saveReportBoData(@QueryParam("startDateStr") @ApiParam(name = "startDateStr", value = "开始日期(yyyy-MM-dd)") String startDateStr,@QueryParam("endDateStr") @ApiParam(name = "endDateStr", value = "结束日期(yyyy-MM-dd)") String endDateStr) {
		LOGGER.info("======初始化运营报表保存商机数据接口=====param: startDate= "+startDateStr+";endDate="+endDateStr);
		int  rowCount = 0;
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtil.castString2Date(startDateStr, DateUtil.DATE_FORMAT);
			endDate = DateUtil.castString2Date(endDateStr, DateUtil.DATE_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error("日期处理错误", e);
			return 0;
		}
		while(startDate.getTime()<=endDate.getTime()) {
			String recordDate = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
			LOGGER.info("======删除"+recordDate+"日的数据");
			reportBoLogic.delReportBoDataByRecordDate(recordDate);
			LOGGER.info("======重新生成"+recordDate+"日的数据");
			rowCount = rowCount + this.reportBoLogic.saveReportBoDate(recordDate);
		    Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(startDate); 
		    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
		    startDate=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		}
		LOGGER.info("======初始化运营报表保存商机数据接口结束=============");
		return rowCount;
	}
	
	
	@GET
	@Path("/reportRenew/save/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "存储运营报表续约数据", notes = "存储运营报表续约数据", response = Response.class)
	public int saveReportRenewData(@QueryParam("startDateStr") @ApiParam(name = "startDateStr", value = "开始日期(yyyy-MM-dd)") String startDateStr,@QueryParam("endDateStr") @ApiParam(name = "endDateStr", value = "结束日期(yyyy-MM-dd)") String endDateStr) {
		LOGGER.info("======初始化运营报表保存续约数据接口=====param: startDate= "+startDateStr+";endDate="+endDateStr);
		int  rowCount = 0;
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtil.castString2Date(startDateStr, DateUtil.DATE_FORMAT);
			endDate = DateUtil.castString2Date(endDateStr, DateUtil.DATE_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error("日期处理错误", e);
			return 0;
		}
		while(startDate.getTime()<=endDate.getTime()) {
			String recordDate = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
			LOGGER.info("======删除"+recordDate+"日的数据");
			reportRenewLogic.delReportRenewDate(recordDate);
			LOGGER.info("======重新生成"+recordDate+"日的数据");
			rowCount = rowCount + this.reportRenewLogic.saveReportRenewDate(recordDate);
		    Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(startDate); 
		    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
		    startDate=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		}
		LOGGER.info("======初始化运营报表保存续约数据接口结束=============");
		return rowCount;
	}
	
	
	
	@GET
	@Path("/reportStock/save/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "存储运营报表库存数据", notes = "存储运营报库存约数据", response = Response.class)
	public int saveReportStockData(@QueryParam("startDateStr") @ApiParam(name = "startDateStr", value = "开始日期(yyyy-MM-dd)") String startDateStr,@QueryParam("endDateStr") @ApiParam(name = "endDateStr", value = "结束日期(yyyy-MM-dd)") String endDateStr) {
		LOGGER.info("======初始化运营报表保存库存数据接口=====param: startDate= "+startDateStr+";endDate="+endDateStr);
		int  rowCount = 0;
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtil.castString2Date(startDateStr, DateUtil.DATE_FORMAT);
			endDate = DateUtil.castString2Date(endDateStr, DateUtil.DATE_FORMAT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error("日期处理错误", e);
			return 0;
		}
		while(startDate.getTime()<=endDate.getTime()) {
			String recordDate = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
			LOGGER.info("======删除"+recordDate+"日的数据");
			reportStockLogic.delEntitysByRecordDate(recordDate);
			LOGGER.info("======重新生成"+recordDate+"日的数据");
			rowCount = rowCount + this.reportStockLogic.saveReportStockCount(recordDate);
		    Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(startDate); 
		    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
		    startDate=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		}
		LOGGER.info("======初始化运营报表保存库存数据接口结束=============");
		return rowCount;
	}
	
	@GET
	@Path("/reportPayment/save/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "存储运营报表回款数据", notes = "存储运营报回款数据", response = Response.class)
	public int saveReportPaymentData(@QueryParam("endDateStr") @ApiParam(name = "endDateStr", value = "结束日期(yyyy-MM-dd)") String endDateStr) {
		int  rowCount = 0;
        Date date= null;
        try {
			date = DateUtil.castString2Date(endDateStr, DateUtil.DATE_FORMAT);
		} catch (ParseException e) {
			LOGGER.error("====日期处理错误！=====", e);
			return 0;
		}//取当前时间
        //取昨天（endDate）
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
        Date endDate =calendar.getTime(); //这个时间就是日期往后推一天的结果 
        //取月初
        calendar.setTime(endDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startDate =calendar.getTime();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = formatter.format(startDate);
        endDateStr = formatter.format(endDate);
        LOGGER.info("======初始化运营报表保存回款数据接口=====param: startDate= "+startDateStr+";endDate="+endDateStr);
		reportPaymentLogic.updateReportPaymentDaily(startDateStr,endDateStr);
		LOGGER.info("======初始化运营报表保存回款数据接口结束=============");
		return rowCount;
	}

}
