package com.zra.report.logic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.constant.HouseTypeConstant;
import com.zra.common.constant.QueryDateTypeEnum;
import com.zra.house.entity.dto.PageDto;
import com.zra.house.service.HouseTypeService;
import com.zra.report.constant.ReportDataTypeEnum;
import com.zra.report.entity.dto.BaseResponse;
import com.zra.report.entity.dto.ReportBoDto;
import com.zra.report.entity.dto.ReportDto;
import com.zra.report.entity.dto.ReportHouseTypeDto;
import com.zra.report.entity.dto.ReportPaymentDto;
import com.zra.report.entity.dto.ReportRenewDto;
import com.zra.report.entity.dto.ReportStockDto;
/**
 * 报表逻辑层
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午6:24:45
 */
@Component
public class ReportLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportLogic.class);
	
	@Autowired
	private ReportBoLogic reportBoLogic;
	
	@Autowired
	private ReportStockLogic reportStockLogic;
	
	@Autowired
	private ReportRenewLogic reportRenewLogic;
	
	@Autowired
	private ReportPaymentLogic reportPaymentLogic;
	
	@Autowired
	private HouseTypeService houseTypeService;
	
	/**
	 * 获取报表详情数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param dateType
	 * @param type
	 * @param page
	 * @return
	 */
	public BaseResponse<List<ReportDto>> getReportDetailList(String projectId, String beginTime, String endTime, Integer dateType, String type, PageDto page) {		
		Set<String> dateSet = new LinkedHashSet<>();
		Set<String> houseTypeSet = new LinkedHashSet<>();
		
		Map<String, Map<String, Object>> reportBoMap = null;
		Map<String, Map<String, Object>> reportStockMap = null;
		Map<String, Map<String, Object>> reportRenewMap = null;
		
		// 1、分别获取各类型报表数据
		if (ReportDataTypeEnum.REPORT_ALL.getCode().equals(type)) {
			reportBoMap = reportBoLogic.getReportBoMapByPage(projectId, beginTime, endTime, page);
			reportStockMap = reportStockLogic.getReportStockMapByPage(projectId, beginTime, endTime, page);
			reportRenewMap = reportRenewLogic.getReportRenewMapByPage(projectId, beginTime, endTime, page);
			
			dateSet = reportBoMap.keySet();
		} else {
			String[] typeArr = type.split(",");
			for (int i = 0; i < typeArr.length; i++) {
				if (ReportDataTypeEnum.REPORT_BO.getCode().equals(typeArr[i])) {
					reportBoMap = reportBoLogic.getReportBoMapByPage(projectId, beginTime, endTime, page);
					
					if (dateSet == null || dateSet.size() <= 0) {
						dateSet = reportBoMap.keySet();
					}
				} else if (ReportDataTypeEnum.REPORT_STOCK.getCode().equals(typeArr[i])) {
					reportStockMap = reportStockLogic.getReportStockMapByPage(projectId, beginTime, endTime, page);
					
					if (dateSet == null || dateSet.size() <= 0) {
						dateSet = reportStockMap.keySet();
					}
				} else if (ReportDataTypeEnum.REPORT_RENEW.getCode().equals(typeArr[i])) {
					reportRenewMap = reportRenewLogic.getReportRenewMapByPage(projectId, beginTime, endTime, page);
					
					if (dateSet == null || dateSet.size() <= 0) {
						dateSet = reportRenewMap.keySet();
					}
				} else {
					LOGGER.error("Unsupported report type!");
					continue;
				}
			}
		}
		
		// 2、获取涉及的所有户型
		for (String recordDate : dateSet) {
			if (reportBoMap != null && !reportBoMap.isEmpty() && reportBoMap.get(recordDate) != null) {
				houseTypeSet.addAll(reportBoMap.get(recordDate).keySet());
			}
			if (reportStockMap != null && !reportStockMap.isEmpty() && reportStockMap.get(recordDate) != null) {
				houseTypeSet.addAll(reportStockMap.get(recordDate).keySet());
			}
			if (reportRenewMap != null && !reportRenewMap.isEmpty() && reportRenewMap.get(recordDate) != null) {
				houseTypeSet.addAll(reportRenewMap.get(recordDate).keySet());
			}
		}
		
		// 3、进行数据合并
		BaseResponse<List<ReportDto>> response = new BaseResponse<>();
		
		if (dateSet.isEmpty()) {
			response.setData(null);
			response.setResult(0);
			response.setMessage("数据为空");
		} else {
			List<ReportDto> resultList = new ArrayList<>();
			for (String recordDate : dateSet) {
				ReportDto reportDto = new ReportDto();
				reportDto.setRecordDate(recordDate);
				
				List<ReportHouseTypeDto> houseTypeList = new ArrayList<>();
				for (String houseType : houseTypeSet) {
					// 3.1、过滤无户型的数据（目前只有商机）
					if (HouseTypeConstant.EMPTY_TYPE.equals(houseType) || HouseTypeConstant.NULL_TYPE.equals(houseType)) {
						continue;
					}
					
					boolean hasData = false;	// 当天该户型是否有数据
					ReportHouseTypeDto houseTypeDto = new ReportHouseTypeDto();
					houseTypeDto.setHouseTypeId(houseType);
					if (!"ALL".equals(houseType)) {
						houseTypeDto.setHouseTypeName(houseTypeService.findHouseTypeById(houseType).getHouseTypeName());
					} else {
						houseTypeDto.setHouseTypeName("所有户型");
					}
					
					if (reportBoMap != null && reportBoMap.get(recordDate) != null && reportBoMap.get(recordDate).get(houseType) != null) {
						houseTypeDto.setReportBo((ReportBoDto) reportBoMap.get(recordDate).get(houseType));
						
						hasData = true;
					}
					if (reportStockMap != null && reportStockMap.get(recordDate) != null && reportStockMap.get(recordDate).get(houseType) != null) {
						houseTypeDto.setReportStock((ReportStockDto) reportStockMap.get(recordDate).get(houseType));
						
						hasData = true;
					}
					if (reportRenewMap != null && reportRenewMap.get(recordDate) != null && reportRenewMap.get(recordDate).get(houseType) != null) {
						houseTypeDto.setReportRenew((ReportRenewDto) reportRenewMap.get(recordDate).get(houseType));
						
						hasData = true;
					}
					
					if (!hasData) {
						continue;
					}
					
					// 塞空数据，方便前端处理
					if (type.indexOf(ReportDataTypeEnum.REPORT_ALL.getCode()) >= 0) {
						if (houseTypeDto.getReportBo() == null) {
							houseTypeDto.setReportBo(new ReportBoDto());
						}
						if (houseTypeDto.getReportStock() == null) {
							houseTypeDto.setReportStock(new ReportStockDto());
						}
						if (houseTypeDto.getReportRenew() == null) {
							houseTypeDto.setReportRenew(new ReportRenewDto());
						}
						
						houseTypeDto.setReportPayment(new ReportPaymentDto());
					}
					
					if (type.indexOf(ReportDataTypeEnum.REPORT_BO.getCode()) >= 0) {
						if (houseTypeDto.getReportBo() == null) {
							houseTypeDto.setReportBo(new ReportBoDto());
						}
					}
					if (type.indexOf(ReportDataTypeEnum.REPORT_STOCK.getCode()) >= 0) {
						if (houseTypeDto.getReportStock() == null) {
							houseTypeDto.setReportStock(new ReportStockDto());
						}
					}
					if (type.indexOf(ReportDataTypeEnum.REPORT_RENEW.getCode()) >= 0) {
						if (houseTypeDto.getReportRenew() == null) {
							houseTypeDto.setReportRenew(new ReportRenewDto());
						}
					}
					if (type.indexOf(ReportDataTypeEnum.REPORT_PAYMENT.getCode()) >= 0) {
						houseTypeDto.setReportPayment(new ReportPaymentDto());
					}
					
					houseTypeList.add(houseTypeDto);
				}
				
				reportDto.setReportHouseTypeList(houseTypeList);
				resultList.add(reportDto);
			}
			response.setData(resultList);
			response.setResult(1);
			response.setMessage("请求成功");
		}
		
		return response;
	}
	
	
	/**
	 * 获取报表总计数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param dateType
	 * @param type
	 * @return
	 */
	public BaseResponse<ReportDto> getReportTotalList(String projectId, String beginTime, String endTime, Integer dateType, String type) {		
		Set<String> houseTypeSet = new LinkedHashSet<>();
		
		Map<String, Map<String, Object>> reportBoMap = null;
		Map<String, Map<String, Object>> reportStockMap = null;
		Map<String, Map<String, Object>> reportRenewMap = null;
		Map<String, Map<String, Object>> reportPaymentMap = null;
		
		// 1、分别获取各类型报表数据
		if (ReportDataTypeEnum.REPORT_ALL.getCode().equals(type)) {
			reportBoMap = reportBoLogic.getReportBoMap(projectId, beginTime, endTime);
			reportStockMap = reportStockLogic.getReportStockMap(projectId, beginTime, endTime);
			reportRenewMap = reportRenewLogic.getReportRenewMap(projectId, beginTime, endTime);
			if (QueryDateTypeEnum.MONTH.getCode().intValue() == dateType.intValue() ||
					QueryDateTypeEnum.LASTMONTH.getCode().intValue() == dateType.intValue()) {
				reportPaymentMap = reportPaymentLogic.getReportPaymentMap(projectId, beginTime, endTime);
			}
		} else {
			String[] typeArr = type.split(",");
			for (int i = 0; i < typeArr.length; i++) {
				if (ReportDataTypeEnum.REPORT_BO.getCode().equals(typeArr[i])) {
					reportBoMap = reportBoLogic.getReportBoMap(projectId, beginTime, endTime);
				} else if (ReportDataTypeEnum.REPORT_STOCK.getCode().equals(typeArr[i])) {
					reportStockMap = reportStockLogic.getReportStockMap(projectId, beginTime, endTime);
				} else if (ReportDataTypeEnum.REPORT_RENEW.getCode().equals(typeArr[i])) {
					reportRenewMap = reportRenewLogic.getReportRenewMap(projectId, beginTime, endTime);
				} else if (ReportDataTypeEnum.REPORT_PAYMENT.getCode().equals(typeArr[i])) {
					if (QueryDateTypeEnum.MONTH.getCode().intValue() == dateType.intValue() ||
							QueryDateTypeEnum.LASTMONTH.getCode().intValue() == dateType.intValue()) {
						reportPaymentMap = reportPaymentLogic.getReportPaymentMap(projectId, beginTime, endTime);
					}
				}  else {
					LOGGER.error("Unsupported report type!");
					continue;
				}
			}
		}
		
		// 2、获取涉及的所有户型
		if (reportBoMap != null && !reportBoMap.isEmpty()) {
			houseTypeSet.addAll(reportBoMap.get("total").keySet());
		}
		if (reportStockMap != null && !reportStockMap.isEmpty()) {
			houseTypeSet.addAll(reportStockMap.get("total").keySet());
		}
		if (reportRenewMap != null && !reportRenewMap.isEmpty()) {
			houseTypeSet.addAll(reportRenewMap.get("total").keySet());
		}
		if (reportPaymentMap != null && !reportPaymentMap.isEmpty()) {
			houseTypeSet.addAll(reportPaymentMap.get("total").keySet());
		}
		
		// 3、进行数据合并	
		BaseResponse<ReportDto> response = new BaseResponse<>();
		ReportDto reportDto = new ReportDto();
		if (QueryDateTypeEnum.TODAY.getCode().intValue() == dateType.intValue() ||
				QueryDateTypeEnum.YESTERDAY.getCode().intValue() == dateType.intValue()) {
			reportDto.setRecordDate(beginTime);
		} else {
			reportDto.setRecordDate("总计");
		}

		List<ReportHouseTypeDto> houseTypeList = new ArrayList<>();
		// TODO 数据为空时也要返回空对象给前端  方便前端联调-----后续需要优化
		if (houseTypeSet.isEmpty()) {
			response.setData(null);
			response.setResult(0);
			response.setMessage("数据为空");
		} else {
			for (String houseType : houseTypeSet) {
				// 3.1、过滤无户型的数据（目前只有商机）
				if (HouseTypeConstant.EMPTY_TYPE.equals(houseType) || HouseTypeConstant.NULL_TYPE.equals(houseType)) {
					continue;
				}
				ReportHouseTypeDto houseTypeDto = new ReportHouseTypeDto();
				houseTypeDto.setHouseTypeId(houseType);
				if (!"ALL".equals(houseType)) {
					houseTypeDto.setHouseTypeName(houseTypeService.findHouseTypeById(houseType).getHouseTypeName());
				} else {
					houseTypeDto.setHouseTypeName("所有户型");
				}
				
				if (reportBoMap != null && reportBoMap.get("total") != null && reportBoMap.get("total").get(houseType) != null) {
					houseTypeDto.setReportBo((ReportBoDto) reportBoMap.get("total").get(houseType));
				} else {
					if (ReportDataTypeEnum.REPORT_ALL.getCode().equals(type) || type.indexOf(ReportDataTypeEnum.REPORT_BO.getCode()) >= 0) {
						houseTypeDto.setReportBo(new ReportBoDto());
					}
				}
				if (reportStockMap != null &&reportStockMap.get("total") != null && reportStockMap.get("total").get(houseType) != null) {
					houseTypeDto.setReportStock((ReportStockDto) reportStockMap.get("total").get(houseType));
				} else {
					if (ReportDataTypeEnum.REPORT_ALL.getCode().equals(type) ||type.indexOf(ReportDataTypeEnum.REPORT_STOCK.getCode()) >= 0) {
						houseTypeDto.setReportStock(new ReportStockDto());
					}
				}
				if (reportRenewMap != null && reportRenewMap.get("total") != null && reportRenewMap.get("total").get(houseType) != null) {
					houseTypeDto.setReportRenew((ReportRenewDto) reportRenewMap.get("total").get(houseType));
				} else {
					if (ReportDataTypeEnum.REPORT_ALL.getCode().equals(type) || type.indexOf(ReportDataTypeEnum.REPORT_RENEW.getCode()) >= 0) {
						houseTypeDto.setReportRenew(new ReportRenewDto());
					}
				}
				if (reportPaymentMap != null && reportPaymentMap.get("total") != null && reportPaymentMap.get("total").get(houseType) != null) {
					houseTypeDto.setReportPayment((ReportPaymentDto) reportPaymentMap.get("total").get(houseType));
				} else {
					if (ReportDataTypeEnum.REPORT_ALL.getCode().equals(type) || type.indexOf(ReportDataTypeEnum.REPORT_PAYMENT.getCode()) >= 0) {
						houseTypeDto.setReportPayment(new ReportPaymentDto());
					}
				}
				
				houseTypeList.add(houseTypeDto);
			}
			
			response.setResult(1);
			response.setMessage("请求成功");
			
			reportDto.setReportHouseTypeList(houseTypeList);
			response.setData(reportDto);
		}
		
		return response;
	}
}