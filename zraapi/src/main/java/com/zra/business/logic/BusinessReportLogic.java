package com.zra.business.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.business.service.BusinessReportService;
import com.zra.common.dto.business.AnswerRateQueryParamDto;
import com.zra.common.dto.business.BoProjectReportShowDto;
import com.zra.common.dto.business.BoReportCountDto;
import com.zra.common.dto.business.BoReportQueryParamDto;
import com.zra.common.dto.business.BoZoReportShowDto;
import com.zra.common.dto.house.ProjectDto;
import com.zra.common.enums.BoSourceType;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.StrUtils;
import com.zra.house.logic.ProjectLogic;
import com.zra.kanban.entity.dto.KanbanRateDto;
import com.zra.marketing.logic.MkNumberLogic;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.logic.ProjectZOLogic;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.logic.EmployeeLogic;

/**
 * @author tianxf9
 *
 */
@Component
public class BusinessReportLogic {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(BusinessReportLogic.class);
    @Autowired
    private BusinessReportService businessReportService;
    @Autowired
    private ProjectLogic projectLogic;
    @Autowired
    private ProjectZOLogic projectZOLogic;

    @Autowired
    private EmployeeLogic employeeLogic;
    
    @Autowired
    private MkNumberLogic mkNumberLogic;
	
	/**
	 * 查询项目商机报表
	 * @author tianxf9
	 * @param paramDto
	 * @return
	 */
	public List<BoProjectReportShowDto> getProjectReportShowDtos(BoReportQueryParamDto paramDto) {
		
		Map<String,BoProjectReportShowDto> reportShowDtoMap = new HashMap<String,BoProjectReportShowDto>();
		//设置项目信息已经查询日期处理
		if(!this.setProjectMsg(reportShowDtoMap, paramDto)){
			return null;
		}
		
		//设置约看
		this.setPrjYueKanMsg(reportShowDtoMap, paramDto);
		//查询带看量
		this.setPrjDaiKanMsg(reportShowDtoMap, paramDto);
		//约看及时跟进率
        this.setYueKanDealRate(reportShowDtoMap, paramDto);
        
        //设置客源量 wangws21 2017-1-18
        this.setKylCount(reportShowDtoMap, paramDto);
        //设置回访量
        this.setVisitCount(reportShowDtoMap, paramDto);
        //设置回访率
        this.setVisitRate(reportShowDtoMap, paramDto);
        //设置成交量
        this.setDealCount(reportShowDtoMap, paramDto);
        //设置转换率 ：约看-带看转换率，带看-成交转换率
        this.setConversionRate(reportShowDtoMap, paramDto);
        //设置新增和遗留约看量
        this.setNewOrOldYueKanCount(reportShowDtoMap, paramDto);
        
        List<BoProjectReportShowDto> result = new ArrayList<BoProjectReportShowDto>();
        for(String key:reportShowDtoMap.keySet()) {
        	result.add(reportShowDtoMap.get(key));
        }
        
        //设置400来电数量，接听率
        setAnswerRate(result,paramDto);
        
		return result;
	}
	
	/**
	 * 设置400来电数量以及接听数量
	 * @author tianxf9
	 * @param result
	 */
	public void setAnswerRate(List<BoProjectReportShowDto> results,BoReportQueryParamDto paramDto) {
		
		try {
			AnswerRateQueryParamDto newParamDto = new AnswerRateQueryParamDto();
			//工作时间 前一天的19:00:00-22:00:00；当天09:00:00-18:59:59 modify by tianxf9
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = formatter.parse(paramDto.getStartDate());
			Date startYdate = DateUtil.getYesterDay(startDate);
			String startYStr = formatter.format(startYdate);
			newParamDto.setStartDate(startYStr+" 19:00:00");
			newParamDto.setEndDate(paramDto.getEndDate().substring(0, 10)+" 18:59:59");
			
			for(BoProjectReportShowDto result:results) {
				List<String> numbers = new ArrayList<>(); 
				//项目销售电话
				ProjectDto projectDto = this.projectLogic.getProjectDtoById(result.getProjectId());
				LOGGER.info("【查询的项目信息】{}，【项目的联系电话】{}",projectDto,projectDto.getMarketTel()==null?"电话为空":projectDto.getMarketTel());
				String marketTel = projectDto.getMarketTel();
				if(marketTel!=null) {
					String[] marketTelArray = marketTel.split(",");
					if(marketTelArray.length==2) {
						numbers.add(marketTelArray[1]);
					}
				}
				
				//项目线下渠道400电话
				List<String> lineNumbers = this.mkNumberLogic.getNumberByProId(result.getProjectId());
				numbers.addAll(lineNumbers);
				
				if(CollectionUtils.isNotEmpty(numbers)) {
					//获取项目工作时间的来电量
					newParamDto.setNumbers(numbers);
					newParamDto.setSuccess(false);
					Integer callCount = this.businessReportService.getProjectCallCountService(newParamDto);
					result.setCallCount(callCount);
					//项目工作时间接听量
					newParamDto.setSuccess(true);
					Integer answerCount = this.businessReportService.getProjectCallCountService(newParamDto);
					
					if(callCount.intValue()==0) {
						result.setAnswerRate(-1);
					}else {
				      	double answerRateD = ((double)answerCount/(double)callCount)*100;
			            BigDecimal bd = BigDecimal.valueOf(answerRateD);
			            answerRateD = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			        	result.setAnswerRate(answerRateD);
					}
				}else {
					result.setCallCount(0);
					result.setAnswerRate(-1);
				}
			}
		} catch (ParseException e) {
			LOGGER.error("查询项目商机接听率出错！", e);
		}
		
	}


    /**
	 * 设置项目信息
	 * @author tianxf9
	 * @param showDtoMap
	 * @param paramDto
	 */
	public boolean setProjectMsg(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		
		String projectIdStr = null;
		if(paramDto.getProjectIdStr()==null||paramDto.getProjectIdStr().equals("")) {
			//查询出当前用户
		    List<ProjectDto> projectDtos = this.projectLogic.getProjectListByUser(paramDto.getUserId(), paramDto.getCityId());
		    for(ProjectDto dto:projectDtos) {
		    	BoProjectReportShowDto showDto = new BoProjectReportShowDto();
		    	showDto.setProjectId(dto.getId());
		    	showDto.setProjectName(dto.getName());
		    	//覆盖原来商机
		    	reportShowDtoMap.put(dto.getId(), showDto);
		    	if(projectIdStr==null) {
		    		projectIdStr = "'"+dto.getId()+"'";
		    	}else {
		    		projectIdStr = projectIdStr+",'"+dto.getId()+"'";
		    	}
		    }
		}else {
			//根据项目id查询项目名称
			ProjectDto projectDto = this.projectLogic.getProjectDtoById(paramDto.getProjectIdStr());
			BoProjectReportShowDto showDto = new BoProjectReportShowDto();
			showDto.setProjectId(projectDto.getId());
			showDto.setProjectName(projectDto.getName());
			//覆盖原来商机
			reportShowDtoMap.put(projectDto.getId(), showDto);
			
			projectIdStr = "'"+projectDto.getId()+"'";	
		}
		//设置插查询项目Id
		paramDto.setProjectIdStr(projectIdStr);
		//设置查询日期
		paramDto.setStartDate(paramDto.getStartDate()+" 00:00:00");
		paramDto.setEndDate(paramDto.getEndDate()+" 23:59:59");
		if(projectIdStr==null) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 设置项目约看量信息
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setPrjYueKanMsg(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		
		//查询约看量
		List<BoReportCountDto> yueKanCountDtos = this.businessReportService.getYueKanCountList(paramDto);
		
		//设置约看
		for(BoReportCountDto countDto:yueKanCountDtos) {
			BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
			Byte source = countDto.getSource();
			//设置约看量
			if(source==BoSourceType.TEL400.getIndex()) {
				showDto.setYueKanCC(countDto.getCount());
			}else if(source==BoSourceType.ONLINE.getIndex()) {
				showDto.setYueKanOnLine(countDto.getCount());
			}else if(source==BoSourceType.CLOUDSALE.getIndex()) {
				showDto.setYueKanCloud(countDto.getCount());
			}else{
				showDto.setYueKanOther(countDto.getCount());
			}
			
			//累加各个约看量
			showDto.setYueKanTotal(showDto.getYueKanTotal()+countDto.getCount());
			//覆盖原来的商机
			reportShowDtoMap.put(countDto.getProjectId(),showDto);
		}
		
	}
	
	/**
	 * 设置项目带看量信息
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setPrjDaiKanMsg(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		//查询带看量
		List<BoReportCountDto> daiKanCountDtos = this.businessReportService.getDaiKanCountList(paramDto);
		
		//设置带看量
		for(BoReportCountDto countDto:daiKanCountDtos) {
			BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
			Byte source = countDto.getSource();
			//设置带看量
			if(source == BoSourceType.TEL400.getIndex()) {
				showDto.setDaiKanCC(countDto.getCount());
			}else if(source == BoSourceType.ONLINE.getIndex()) {
				showDto.setDaiKanOnLine(countDto.getCount());
			}else if(source == BoSourceType.CLOUDSALE.getIndex()) {
				showDto.setDaiKanCloud(countDto.getCount());
			}else{
				showDto.setDaiKanOther(countDto.getCount());
			}
			
			//累加各个来源的带看量
			showDto.setDaiKanTotal(showDto.getDaiKanTotal()+countDto.getCount());
			//覆盖原来商机
			reportShowDtoMap.put(countDto.getProjectId(),showDto);
			
		}
		
	}
	
	/**
	 * 设置约看及时跟进率
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setYueKanDealRate(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		//约看及时跟进率
		List<BoReportCountDto>  timeOutOrDaiYuKans = this.businessReportService.getTimeOutAndStayYueKan(paramDto);
		//wangws21  2017-1-9  解决商机及时跟进率实际100% 显示为0%的问题
		for(Entry<String, BoProjectReportShowDto> e : reportShowDtoMap.entrySet()){
		    BoProjectReportShowDto showDto = e.getValue();
            if(showDto.getYueKanTotal() != 0){
                e.getValue().setYueKanToGJRate(100D);
            }
		}
		
        for(BoReportCountDto countDto:timeOutOrDaiYuKans) {
        	BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
        	double yueKanToGJRate = (1-((double)countDto.getCount()/(double)showDto.getYueKanTotal()))*100;  
        	BigDecimal bd = BigDecimal.valueOf(yueKanToGJRate);
        	double newYueKanToGJRate = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();  
        	showDto.setYueKanToGJRate(newYueKanToGJRate);
        	//覆盖原来商机
			reportShowDtoMap.put(countDto.getProjectId(),showDto);
        }
	}
	
	/**
	 * 设置回访量
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setVisitCount(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		 //回访量
        List<BoReportCountDto>  visitCount = this.businessReportService.getVisitCount(paramDto);
        for(BoReportCountDto countDto:visitCount) {
        	if(countDto!=null) {
        	BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
        	showDto.setVisitCount(countDto.getCount());
        	//覆盖原来商机
			reportShowDtoMap.put(countDto.getProjectId(),showDto);
        	}
        }
	}
	
	/**
	 * 设置回访率
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setVisitRate(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
        //经历过回访的
        List<BoReportCountDto>  throughVisitCount = this.businessReportService.getThroughVisitCount(paramDto);
        //计算回访率
		for(BoReportCountDto countDto:throughVisitCount) {
			BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
			double visitRate = 0.0;
			if(countDto.getCount()!=0) {
			visitRate = (((double)showDto.getVisitCount())/((double)countDto.getCount()))*100;
			}
			BigDecimal bd = BigDecimal.valueOf(visitRate);
			double newVisitRate = bd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			showDto.setVisitRate(newVisitRate);
	     	//覆盖原来商机
			reportShowDtoMap.put(countDto.getProjectId(),showDto);
		}
	}
	
	/**
	 * 设置成交量
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setDealCount(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		//成交量
		List<BoReportCountDto>  dealCount = this.businessReportService.getDealCount(paramDto);
		for(BoReportCountDto countDto:dealCount) {
			BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
			showDto.setDealCount(countDto.getCount());
	     	//覆盖原来商机
			reportShowDtoMap.put(countDto.getProjectId(),showDto);
		}
	}
	
	/**
	 * 设置转换率 ：约看-带看转换率，带看-成交转换率
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setConversionRate(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
        //约看-带看转换率
        for(String projectId:reportShowDtoMap.keySet()) {
        	BoProjectReportShowDto showDto = reportShowDtoMap.get(projectId);
        	double yuKanToDaikanRate = 0.0;
        	
        	if(showDto.getYueKanTotal()!=0) {
        	yuKanToDaikanRate=(((double)showDto.getDaiKanTotal())/((double)showDto.getYueKanTotal()))*100;
        	}
        	
        	String kylToDaikanRate = "-";
        	if(showDto.getKylTotal() != 0){
        	    double dd = (((double)showDto.getDaiKanTotal())/((double)showDto.getKylTotal()))*100;
        	    BigDecimal kylbd = BigDecimal.valueOf(dd);
        	    kylToDaikanRate = kylbd.setScale(1,BigDecimal.ROUND_HALF_UP).toString();
        	}
        	showDto.setKylToDaiKanRate(kylToDaikanRate);
        	
        	double daiKanToDealRate =0.0;
        	if(showDto.getDaiKanTotal()!=0) {
        	daiKanToDealRate = (((double)showDto.getDealCount())/((double)showDto.getDaiKanTotal()))*100;
        	}
        	
        	BigDecimal yueKanbd = BigDecimal.valueOf(yuKanToDaikanRate);
        	double newYueKanToDaiKanRate = yueKanbd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue(); 
        	
			BigDecimal daiKanbd = BigDecimal.valueOf(daiKanToDealRate);
			double newDaiKanToDealRate = daiKanbd.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			
        	showDto.setYueKanToDaiKanRate(newYueKanToDaiKanRate);
    		showDto.setDaiKanToDealRate(newDaiKanToDealRate);
        	//覆盖原来商机
			reportShowDtoMap.put(projectId,showDto);
        }
	}
	
	/**
	 * 设置新增和遗留约看量
	 * @author tianxf9
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
	public void setNewOrOldYueKanCount(Map<String,BoProjectReportShowDto> reportShowDtoMap,BoReportQueryParamDto paramDto) {
		//查询新增约看量
		List<BoReportCountDto> newCountDto = this.businessReportService.getNewYueKanCount(paramDto);
		//查询遗留约看量
		List<BoReportCountDto> oldCountDto = this.businessReportService.getOldYueKanCount(paramDto);
		
		for(BoReportCountDto newDto:newCountDto) {
			BoProjectReportShowDto showDto = reportShowDtoMap.get(newDto.getProjectId());
			showDto.setNewYueKanTotal(newDto.getCount());
			reportShowDtoMap.put(newDto.getProjectId(), showDto);
		}
		
		for(BoReportCountDto oldDto:oldCountDto) {
			BoProjectReportShowDto showDto = reportShowDtoMap.get(oldDto.getProjectId());
			showDto.setOldYueKanTotal(oldDto.getCount());
			reportShowDtoMap.put(oldDto.getProjectId(),showDto);
		}
		
	}

	/**
	 * 管家商机统计报表
	 * @author wangws21 2016-8-18
	 * @param reportQueryParamDto 查询参数实体
	 * @return List<BoZoReportShowDto>
	 */
	public List<BoZoReportShowDto> getZoBusinessReportList(BoReportQueryParamDto reportQueryParamDto) {
		
		//存放数据  要value 需要保持顺序
		Map<String,BoZoReportShowDto> zoReportMap = new LinkedHashMap<String,BoZoReportShowDto>();
		
		//项目名称
		ProjectDto project = this.projectLogic.getProjectById(reportQueryParamDto.getProjectIdStr());
		String projectId=project.getId(),projectName = project.getName();
		
		//没选择管家 传进来的是all
		String projectZoId = reportQueryParamDto.getProjectZoId();
		//查询一个管家的数据     
		if(StrUtils.isNotNullOrBlank(projectZoId) && !projectZoId.equals("all")){
			BoZoReportShowDto zoReportShowDto = new BoZoReportShowDto(projectId,projectName,reportQueryParamDto.getProjectZoId());
			EmployeeEntity employee = this.employeeLogic.getEmployeeById(projectZoId);
			zoReportShowDto.setZoPhone(employee.getMobile());
			zoReportShowDto.setZoName(employee.getName());
			zoReportShowDto.setYuKanToGJRate(100);
			zoReportShowDto.setKylToDaikanRate("-");
			zoReportMap.put(projectZoId, zoReportShowDto);
		}else{ //查询项目下所有管家的数据
			//所有zo
			List<ProjectZODto> projectZoList = this.projectZOLogic.getProjectZOsByProId(reportQueryParamDto.getProjectIdStr());
			for(ProjectZODto projectZo:projectZoList){
				BoZoReportShowDto zoReportShowDto = new BoZoReportShowDto(projectId,projectName,projectZo.getProjectZOId());
				zoReportShowDto.setZoPhone(projectZo.getZrojectZoPhone());
				zoReportShowDto.setZoName(projectZo.getProjectZOName());
				zoReportShowDto.setYuKanToGJRate(100);
				zoReportShowDto.setKylToDaikanRate("-");
				zoReportMap.put(projectZo.getProjectZOId(), zoReportShowDto);
			}
		}
		
		
		//修正项目id sql 使用in查询并且该字段数据为字符串
		reportQueryParamDto.setProjectIdStr("'"+projectId+"'");
		
		//设置查询日期
		reportQueryParamDto.setStartDate(reportQueryParamDto.getStartDate()+" 00:00:00");
		reportQueryParamDto.setEndDate(reportQueryParamDto.getEndDate()+" 23:59:59");
		
		//约看量
		List<BoReportCountDto> yueKanCountList = this.businessReportService.getYueKanCountList(reportQueryParamDto);
		for(int i=0;i<yueKanCountList.size();i++){
			BoReportCountDto reportCountDto = yueKanCountList.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			reportShowDto.setZoName(reportCountDto.getProjectZoName());
			reportShowDto.setYuKanTotal(reportCountDto.getCount());
		}
		
		//约看及时跟进率
		List<BoReportCountDto> timeOutAndStayYueKan = this.businessReportService.getTimeOutAndStayYueKan(reportQueryParamDto);
		
		//当查询超时处理结果为空时，约看及时跟进率应该为100%，现在是0%
		for(int i=0;i<timeOutAndStayYueKan.size();i++){
			BoReportCountDto reportCountDto = timeOutAndStayYueKan.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			
			double yuKanToGJRate = 0;
			if(reportShowDto.getYuKanTotal()!=0){
				yuKanToGJRate = (1-(double)reportCountDto.getCount()/reportShowDto.getYuKanTotal())*100;  
			}
			yuKanToGJRate = BigDecimal.valueOf(yuKanToGJRate).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			reportShowDto.setYuKanToGJRate(yuKanToGJRate);

		}
		
		//客源量
        List<BoReportCountDto> kylCountlist = this.businessReportService.getKylCount(reportQueryParamDto);
        for(int i=0;i<kylCountlist.size();i++){
            BoReportCountDto reportCountDto = kylCountlist.get(i);
            BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
            if(reportShowDto==null) continue;
            reportShowDto.setKylTotal(reportCountDto.getCount());
            //有客源量的默认为0%
            if(reportCountDto.getCount() != 0){
                reportShowDto.setKylToDaikanRate("0");
            }
        }
		
		//带看量
		List<BoReportCountDto> daiKanCountList = this.businessReportService.getDaiKanCountList(reportQueryParamDto);
		for(int i=0;i<daiKanCountList.size();i++){
			BoReportCountDto reportCountDto = daiKanCountList.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			reportShowDto.setDaiKanTotal(reportCountDto.getCount());
			
			//约看带看转化率
			double yuKanToDaiKanRate = 0;
			if(reportShowDto.getYuKanTotal()!=0){
				yuKanToDaiKanRate = (double)reportCountDto.getCount()/reportShowDto.getYuKanTotal()*100;  
			}
			yuKanToDaiKanRate = BigDecimal.valueOf(yuKanToDaiKanRate).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			reportShowDto.setYuKanToDaiKanRate(yuKanToDaiKanRate);
			
			//客源量带看转化率
            if(reportShowDto.getKylTotal() !=0 ){
                String kylToDaiKanRate = new BigDecimal((double)reportCountDto.getCount()/reportShowDto.getKylTotal()*100).setScale(1,BigDecimal.ROUND_HALF_UP).toString();  
                reportShowDto.setKylToDaikanRate(kylToDaiKanRate);
            }
		}
		
		//回访量
		List<BoReportCountDto> visitCount = this.businessReportService.getVisitCount(reportQueryParamDto);
		for(int i=0;i<visitCount.size();i++){
			BoReportCountDto reportCountDto = visitCount.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			reportShowDto.setVisitCount(reportCountDto.getCount());
			
		}
		//回访率
		List<BoReportCountDto> throughVisitCount = this.businessReportService.getThroughVisitCount(reportQueryParamDto);
		for(int i=0;i<throughVisitCount.size();i++){
			BoReportCountDto reportCountDto = throughVisitCount.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			double visitRate = 0;
			if(reportCountDto.getCount()!=0){
				visitRate = (double)reportShowDto.getVisitCount()/reportCountDto.getCount()*100;  
			}
			visitRate = BigDecimal.valueOf(visitRate).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			reportShowDto.setVisitRate(visitRate);
		}
		//成交量
		List<BoReportCountDto> dealCount = this.businessReportService.getDealCount(reportQueryParamDto);
		for(int i=0;i<dealCount.size();i++){
			BoReportCountDto reportCountDto = dealCount.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			reportShowDto.setDealCount(reportCountDto.getCount());
			
			//带看-成交转化率
			double daiKanToDealRate = 0;
			if(reportShowDto.getDaiKanTotal()!=0){
				daiKanToDealRate = (double)reportCountDto.getCount()/reportShowDto.getDaiKanTotal()*100;  
			}
			daiKanToDealRate = BigDecimal.valueOf(daiKanToDealRate).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			reportShowDto.setDaiKanToDealRate(daiKanToDealRate);
		}
		
		//首次完结占比
		List<BoReportCountDto> firstUnDealCount = this.businessReportService.getFirstUnDealCount(reportQueryParamDto);
		Map<String,BoReportCountDto> firstUnDealCountMap = new HashMap<String,BoReportCountDto>();
		for(BoReportCountDto reportCountDto:firstUnDealCount){
			firstUnDealCountMap.put(reportCountDto.getProjectZoId(), reportCountDto);
		}
		List<BoReportCountDto> unDealCount = this.businessReportService.getUnDealCount(reportQueryParamDto);
		for(int i=0;i<unDealCount.size();i++){
			BoReportCountDto reportCountDto = unDealCount.get(i);
			BoZoReportShowDto reportShowDto = zoReportMap.get(reportCountDto.getProjectZoId());
			if(reportShowDto==null) continue;
			
			BoReportCountDto firstUnDealC = firstUnDealCountMap.get(reportCountDto.getProjectZoId());
			
			double wanJieRate = 0;
			if(firstUnDealC!=null && reportCountDto.getCount()!=0){
				wanJieRate = (double)firstUnDealC.getCount()/reportCountDto.getCount()*100;  
			}
			wanJieRate = BigDecimal.valueOf(wanJieRate).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
			reportShowDto.setWanJieRate(wanJieRate);
		}
		
		Iterator<BoZoReportShowDto> iterator = zoReportMap.values().iterator();
		List<BoZoReportShowDto> zoReportShowList = new ArrayList<BoZoReportShowDto>();
		while (iterator.hasNext()) {
			BoZoReportShowDto BoZoReportShow = iterator.next();
			zoReportShowList.add(BoZoReportShow);
		}
		
		//设置管家的来电量以及接听率
		setZOAnswerRate(zoReportShowList,reportQueryParamDto);
		return zoReportShowList;
	}
	
	
	/**
	 * 设置管家的来电量，接听率
	 * @author tianxf9
	 * @param zoReportShowList
	 * @param paramDto
	 */
	public void setZOAnswerRate(List<BoZoReportShowDto> zoReportShowList,BoReportQueryParamDto paramDto) {
		
		try {
			AnswerRateQueryParamDto newParamsDto = new AnswerRateQueryParamDto();
			//工作时间 前一天的19:00:00-22:00:00；当天09:00:00-18:59:59 modify by tianxf9
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = formatter.parse(paramDto.getStartDate());
			Date startYdate = DateUtil.getYesterDay(startDate);
			String startYStr = formatter.format(startYdate);
			newParamsDto.setStartDate(startYStr+" 19:00:00");
			newParamsDto.setEndDate(paramDto.getEndDate().substring(0, 10)+" 18:59:59");
			
			List<String> numbers = new ArrayList<String>();
			for(BoZoReportShowDto showDto:zoReportShowList) {
				numbers.add(showDto.getZoPhone());
			}
			newParamsDto.setNumbers(numbers);
			
			//获取管家工作时间400来电数量
			newParamsDto.setSuccess(false);
			List<BoReportCountDto> callCountDtos = this.businessReportService.getZOCallCountService(newParamsDto);
			//key = zoPhone,value=count;
			Map<String,Integer> callCountMap = new HashMap<>();
			for(BoReportCountDto callCountDto:callCountDtos) {
				callCountMap.put(callCountDto.getZoPhone(), callCountDto.getCount());
			}
			
			//获取管家工作时间接听数量
			newParamsDto.setSuccess(true);
			List<BoReportCountDto> answerCountDtos = this.businessReportService.getZOCallCountService(newParamsDto);
			Map<String,Integer> answerCountMap = new HashMap<String,Integer>();
			for(BoReportCountDto answerCountDto:answerCountDtos) {
				answerCountMap.put(answerCountDto.getZoPhone(), answerCountDto.getCount());
			}
			
			//设置管家来电数量以及接听率
			for(BoZoReportShowDto reportShowDto:zoReportShowList) {
				Integer callCount = callCountMap.get(reportShowDto.getZoPhone())==null?0:callCountMap.get(reportShowDto.getZoPhone());
				reportShowDto.setCallCount(callCount);
				if(callCount==null||callCount.intValue()==0) {
					reportShowDto.setAnswerRate(-1);
				}else {
					Integer answerCount = (answerCountMap.get(reportShowDto.getZoPhone())==null)?0:answerCountMap.get(reportShowDto.getZoPhone());
					double answerRate = (double)answerCount/(double)callCount*100;  
					answerRate = BigDecimal.valueOf(answerRate).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					reportShowDto.setAnswerRate(answerRate);
				}
			}
		} catch (ParseException e) {
			LOGGER.error("查询管家400来工作时间接听率出错！", e);
		}
		
	}
	
	/**
	 * 获取时间范围内商机及时跟进率（提供给目标看板）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getBoGJRate(BoReportQueryParamDto paramDto) {
		
		//获取所有项目
		List<ProjectDto> projectList = this.projectLogic.getAllProjectList();
		//获取时间段内所有项目的新增商机量
		List<BoReportCountDto> newBoCounts =  this.businessReportService.getNewYueKanCount(paramDto);
		//获取时间段内所有项目的约看没及时处理的商机
		List<BoReportCountDto> timeOutBoCounts = this.businessReportService.getTimeOutYueKan(paramDto);
		
		
		Map<String,Integer> newBoCountMap = new HashMap<String,Integer>();
		Map<String,Integer> timeOutBoCountMap = new HashMap<String,Integer>();
		
		for(BoReportCountDto newBoCount:newBoCounts) {
			newBoCountMap.put(newBoCount.getProjectId(), newBoCount.getCount());
		}
		
		for(BoReportCountDto timeOutBoCount:timeOutBoCounts) {
			timeOutBoCountMap.put(timeOutBoCount.getProjectId(), timeOutBoCount.getCount());
		}
		
		List<KanbanRateDto> boGJRateDtos = new ArrayList<KanbanRateDto>();
		
		for(ProjectDto projectDto:projectList) {
			
			KanbanRateDto boGJRateDto = new KanbanRateDto();
			boGJRateDto.setProjectId(projectDto.getId());
			if(newBoCountMap.get(projectDto.getId())!=null&&newBoCountMap.get(projectDto.getId()).intValue()!=0) {
			   
			    if(timeOutBoCountMap.get(projectDto.getId())!=null&&timeOutBoCountMap.get(projectDto.getId()).intValue()!=0) {
			    	 BigDecimal timeOutRate = new BigDecimal(timeOutBoCountMap.get(projectDto.getId())).divide(new BigDecimal(newBoCountMap.get(projectDto.getId())),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			    	 boGJRateDto.setRate(new BigDecimal(100).subtract(timeOutRate));
			    }else {
			    	boGJRateDto.setRate(new BigDecimal(100));
			    }
			    
			}else {
				boGJRateDto.setRate(new BigDecimal(0));
			}
			
			boGJRateDtos.add(boGJRateDto);
		}
		
		return boGJRateDtos;
	}
	
	/**
	 * wangws21 2017-1-18 设置客源量.
	 * @param reportShowDtoMap
	 * @param paramDto
	 */
    private void setKylCount(Map<String, BoProjectReportShowDto> reportShowDtoMap, BoReportQueryParamDto paramDto) {
        //查询客源量
        List<BoReportCountDto> newCountDto = this.businessReportService.getKylCount(paramDto);
        
        for(BoReportCountDto countDto:newCountDto) {
            BoProjectReportShowDto showDto = reportShowDtoMap.get(countDto.getProjectId());
            Byte source = countDto.getSource();
            //设置客源量
            if(source==BoSourceType.TEL400.getIndex()) {
                showDto.setKylCC(countDto.getCount());
            }else if(source==BoSourceType.ONLINE.getIndex()) {
                showDto.setKylOnLine(countDto.getCount());
            }else if(source==BoSourceType.CLOUDSALE.getIndex()) {
                showDto.setKylCloud(countDto.getCount());
            }else{
                showDto.setKylOther(countDto.getCount());
            }
            
            //累加各个客源量
            showDto.setKylTotal(showDto.getKylTotal()+countDto.getCount());
        }
    }
}

