/**
 * @FileName: EmpTargetController.java
 * @Package com.ziroom.minsu.report.board.controller
 * 
 * @author bushujie
 * @created 2017年1月12日 下午5:49:51
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.sys.EmployeeEntity;
import com.ziroom.minsu.report.board.dto.EmpStatsRequest;
import com.ziroom.minsu.report.board.dto.EmpTargetItemRequest;
import com.ziroom.minsu.report.board.entity.EmpTargetEntity;
import com.ziroom.minsu.report.board.entity.EmpTargetLogEntity;
import com.ziroom.minsu.report.board.service.BaseDataService;
import com.ziroom.minsu.report.board.service.BaseDataServiceForExcel;
import com.ziroom.minsu.report.board.service.EmpDataService;
import com.ziroom.minsu.report.board.service.EmpTargetService;
import com.ziroom.minsu.report.board.vo.EmpTargetItem;
import com.ziroom.minsu.report.board.vo.EmpTargetItemData;
import com.ziroom.minsu.report.board.vo.EmpTargetStatItem;
import com.ziroom.minsu.report.common.util.DateUtils;
import com.ziroom.minsu.report.common.util.DealExcelUtil;
import com.ziroom.minsu.report.common.util.UserUtil;
import com.ziroom.minsu.services.basedata.api.inner.EmployeeService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("empTarget")
public class EmpTargetController {
	
	@Resource(name="report.BaseDataService")
	private BaseDataService baseDataService;

	@Resource(name="report.empTargetService")
	private EmpTargetService empTargetService;

	@Resource(name="basedata.employeeService")
	private EmployeeService employeeService;

    @Resource(name="report.empDataService")
    private EmpDataService empDataService;

	@Resource(name="report.BaseDataServiceFoexcel")
	private BaseDataServiceForExcel baseDataServiceForExcel;

    private static final String DATE_SHORT_FORMAT = "yyyy-MM";
	private static final String DATE_LONG_FORMAT = "yyyy-MM-dd";
	/**
	 * 导出数据
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2017/1/13 18:14 houseDetail/houseDetailExcel?rentWay=0&beginTime=&endTime=&methodName=getHouseFilterInfo
	 */
	@RequestMapping("/dataListToExcel")
	@ResponseBody
	public void dataListToExcel(EmpTargetItemRequest empTargetItemRequest,HttpServletResponse response){
		DealExcelUtil dealExcelUtil = new DealExcelUtil(baseDataServiceForExcel,empTargetItemRequest, null, "emp_data_list");
		dealExcelUtil.exportZipFile(response,empTargetItemRequest.getMethodName());//指定调用的方法名
	}

	/**
	 * 
	 * 员工目标列表页
	 *
	 * @author bushujie
	 * @created 2017年1月12日 下午6:00:22
	 *
	 * @return
	 */
	@RequestMapping("showList")
	public String toEmpTargetList(){
		return "/board/emp/empTargetList";
	}
	
	/**
	 * 
	 * 员工目标分页数据
	 *
	 * @author bushujie
	 * @created 2017年1月12日 下午6:24:41
	 *
	 * @param guardAreaRequest
	 * @return
	 */
	@RequestMapping("dataList")
	@ResponseBody
	public JSONObject dataList(EmpTargetItemRequest empTargetItemRequest){
		if(empTargetItemRequest.getLimit()==50){
			empTargetItemRequest.setLimit(5);
		}
		
		PagingResult<EmpTargetItem> pagingResult=baseDataService.empPageList(empTargetItemRequest);
		
		List<EmpTargetItemData> dataList=baseDataService.empTargetList(empTargetItemRequest, pagingResult.getRows());
		
        JSONObject result = new JSONObject();
        
		Long size = pagingResult.getTotal();
		Long totalpages = 0L;
		if(!Check.NuNObj(size)){
			if(size % empTargetItemRequest.getLimit() == 0){
				totalpages = size/empTargetItemRequest.getLimit();
			}else {
				totalpages = size/empTargetItemRequest.getLimit() + 1;
			}
		}
		result.put("totalpages", totalpages);
		result.put("currPage", empTargetItemRequest.getPage());
		result.put("totalCount", 0);
		result.put("dataList",dataList);
		return result;
	}

	/**
	 *
	 * 插入或者更新专员月份目标
	 *
	 * @author bushujie
	 * @created 2017年1月13日 下午5:55:17
	 *
	 * @param empTargetEntity
	 * @return
	 * @throws SOAParseException
	 */
	@RequestMapping("insertUpData")
	@ResponseBody
	public DataTransferObject insertUpData(EmpTargetEntity empTargetEntity) throws SOAParseException{
		DataTransferObject dto=new DataTransferObject();
		String resultJson=employeeService.findEmployeeByEmpCode(empTargetEntity.getEmpCode());
		EmployeeEntity employeeEntity=SOAResParseUtil.getValueFromDataByKey(resultJson, "employee", EmployeeEntity.class);
		empTargetEntity.setCreateEmpName(employeeEntity.getEmpName());
		empTargetService.insertUpEmpTarget(empTargetEntity, UserUtil.getUpsUserMsg());
//		List<EmpTargetLogEntity> logList=empTargetService.findCityTargetLog(empTargetFid);
//		dto.putValue("logList", logList);
		return dto;
	}

	/**
	 *
	 *  专员code和月份查询目标
	 *
	 * @author bushujie
	 * @created 2017年1月16日 上午10:50:57
	 *
	 * @param targetMonth
	 * @param empCode
	 * @return
	 */
	@RequestMapping("findEmpTargetByMcode")
	@ResponseBody
	public DataTransferObject findEmpTargetByMcode(String targetMonth,String empCode){
		DataTransferObject dto=new DataTransferObject();
		EmpTargetEntity empTargetEntity=empTargetService.findEmpTargetByMcode(targetMonth, empCode);
		if(!Check.NuNObj(empTargetEntity)){
			List<EmpTargetLogEntity> logList=empTargetService.findCityTargetLog(empTargetEntity.getFid());
			dto.putValue("logList", logList);
		}
		dto.putValue("empTarget", empTargetEntity);
		return dto;
	}

	/**
	 * 员工目标统计页面
	 * @author jixd
	 * @created 2017年01月16日 11:02:01
	 * @param
	 * @return
	 */
	@RequestMapping("/showStatistics")
	public String empTargetStatistics(){
		return "/board/emp/empStatistics";
	}

	/**
	 * 员工统计
	 * @author jixd
	 * @created 2017年01月16日 11:11:44
	 * @param
	 * @return
	 */
	@RequestMapping("/dataStatistic")
	@ResponseBody
	public JSONObject dataTargetStatistics(EmpTargetItemRequest empTargetItemRequest){

        if (Check.NuNStr(empTargetItemRequest.getTargetMonth())){
            empTargetItemRequest.setTargetMonth(DateUtil.dateFormat(new Date(),DATE_SHORT_FORMAT));
        }
        PagingResult<EmpTargetItem> pagingResult=baseDataService.findGaurdAreaByPage(empTargetItemRequest);
        JSONObject result = new JSONObject();

        Long size = pagingResult.getTotal();
        Long totalpages = 0L;
        if(!Check.NuNObj(size)){
            if(size % empTargetItemRequest.getLimit() == 0){
                totalpages = size/empTargetItemRequest.getLimit();
            }else {
                totalpages = size/empTargetItemRequest.getLimit() + 1;
            }
        }

        List<EmpTargetItem> empList = pagingResult.getRows();
        String targetMonth = empTargetItemRequest.getTargetMonth();
        List<EmpTargetStatItem> statList = new ArrayList<>();

        try {
            Date date = DateUtil.parseDate(targetMonth, DATE_SHORT_FORMAT);
            Date monthFirstDay = DateUtils.getMonthFirstDay(date);
            Date monthLastDay = DateUtils.getMonthLastDay(monthFirstDay);
            EmpStatsRequest empStatsRequest = new EmpStatsRequest();
            String startTime = DateUtil.dateFormat(monthFirstDay,DATE_LONG_FORMAT);
            String endTime = DateUtil.dateFormat(monthLastDay,DATE_LONG_FORMAT);
            for (EmpTargetItem item : empList){
				empStatsRequest.setStartTime(startTime);
				empStatsRequest.setEndTime(endTime);
                EmpTargetStatItem statItem = new EmpTargetStatItem();
                statItem.setEmpCode(item.getEmpCode());
                statItem.setEmpName(item.getEmpName());
                empStatsRequest.setEmpCode(item.getEmpCode());
				statItem.setTargetHouseNum(item.getTargetHouseNum());
                empDataService.getEmpStatsHouseNum(empStatsRequest, statItem);
                statList.add(statItem);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result.put("totalpages", totalpages);
        result.put("currPage", empTargetItemRequest.getPage());
        result.put("totalCount", statList.size());
        result.put("dataList", statList);
        return result;

	}




}
