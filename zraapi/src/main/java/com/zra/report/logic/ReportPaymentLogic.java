package com.zra.report.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ziroom.platform.harmonia.utils.StringUtils;
import com.zra.common.dto.house.HouseTypeDto;
import com.zra.common.error.ResultException;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.MathUtils;
import com.zra.common.utils.SysConstant;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.service.ProjectService;
import com.zra.kanban.entity.dto.KanbanRateDto;
import com.zra.report.entity.ReportPaymenTaskEntity;
import com.zra.report.entity.ReportPaymentEntity;
import com.zra.report.entity.dto.ReportPaymentDto;
import com.zra.report.entity.dto.ReportPaymentForKBDto;
import com.zra.report.service.ReportPaymentService;

/**
 * 报表回款数据逻辑层
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午6:24:45
 */
@Component
public class ReportPaymentLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportPaymentLogic.class);
	
	@Autowired
	private ReportPaymentService reportPaymentService;
	
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 获取报表回款数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportPaymentMap(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report payment list.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException("The project id is empty.", 400);
		}
		String recordMonth1 = beginTime.substring(0, 7);
		String recordMonth2 = endTime.substring(0, 7);
		if (!recordMonth1.equals(recordMonth2)) {
			LOGGER.error("The beginTime and endTime are not in the same month.");
			throw new ResultException("The beginTime and endTime are not in the same month.", 400);
		}
				
		ReportPaymentDto reportPaymentDto = new ReportPaymentDto();
		reportPaymentDto.setProjectId(projectId);
		reportPaymentDto.setRecordDate(recordMonth1);
		
		Map<String, Object> reportPaymentMap = new LinkedHashMap<>();
		ReportPaymentDto allHouseTypePayment = new ReportPaymentDto();	// 统计所有户型
		
		// 1、获取报表回款数据列表
		List<ReportPaymentEntity> reportPaymentList = reportPaymentService.getReportPaymentByRecordDate(reportPaymentDto);
		if (reportPaymentList != null && reportPaymentList.size() > 0) {
			reportPaymentMap.put("ALL", allHouseTypePayment);
			
			for (ReportPaymentEntity reportPayment : reportPaymentList) {
				ReportPaymentDto paymentDto = convertEntity2Dto(reportPayment);
				reportPaymentMap.put(paymentDto.getHouseTypeId(), paymentDto);
				
				// 单量
				allHouseTypePayment.setPaymentCount(MathUtils.add(allHouseTypePayment.getPaymentCount(), paymentDto.getPaymentCount()));
				allHouseTypePayment.setVoucherCount(MathUtils.add(allHouseTypePayment.getVoucherCount(), paymentDto.getVoucherCount()));
				allHouseTypePayment.setDelayVoucherCount(MathUtils.add(allHouseTypePayment.getDelayVoucherCount(), paymentDto.getDelayVoucherCount()));
				//add by tianxf9 计算时间范围内的
				allHouseTypePayment.setPaymentCountRange(MathUtils.add(allHouseTypePayment.getPaymentCountRange(), paymentDto.getPaymentCountRange()));
				
				// 金额
				allHouseTypePayment.setPaymentAmount(MathUtils.add(allHouseTypePayment.getPaymentAmount(), paymentDto.getPaymentAmount()).toString());
				allHouseTypePayment.setVoucherAmount(MathUtils.add(allHouseTypePayment.getVoucherAmount(), paymentDto.getVoucherAmount()).toString());
				allHouseTypePayment.setDelayVoucherAmount(MathUtils.add(allHouseTypePayment.getDelayVoucherAmount(), paymentDto.getDelayVoucherAmount()).toString());
			}
			
			//计算总回款率
			if (allHouseTypePayment.getPaymentCount() == 0) {
				allHouseTypePayment.setVoucherRate(new BigDecimal(0).setScale(2).toString());
			} else {
				allHouseTypePayment.setVoucherRate(MathUtils.mul(MathUtils.div(allHouseTypePayment.getVoucherCount(), allHouseTypePayment.getPaymentCount(), 4).toString(), "100").setScale(2).toString());
			}
			
			//计算及时回款率
			if(allHouseTypePayment.getPaymentCountRange().intValue()==0) {
				allHouseTypePayment.setVoucherIntimeRate(new BigDecimal(0).setScale(2).toString());
			}else {
				// 及时回款单量
				Integer intimeVoucherCount = MathUtils.sub(allHouseTypePayment.getPaymentCountRange(), allHouseTypePayment.getDelayVoucherCount());
				allHouseTypePayment.setVoucherIntimeRate(MathUtils.mul(MathUtils.div(intimeVoucherCount, allHouseTypePayment.getPaymentCountRange(), 4).toString(), "100").setScale(2).toString());
			}
			resultMap.put("total", reportPaymentMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 每天统计回款报表数据
	 * @param recordDate 统计的日期
	 */
	public void updateReportPaymentDaily(String startDate,String endDate) {
		String recordDate = startDate.substring(0,7);
		ReportPaymentDto queryDto = new ReportPaymentDto();
		
		// 获取所有项目列表
		List<ProjectListReturnDto> projectList = projectService.getProjectList();
		if (projectList != null && projectList.size() > 0) {
			 for (ProjectListReturnDto projectDto : projectList) {
				 queryDto.setProjectId(projectDto.getProjId());
				 queryDto.setRecordDate(recordDate);
				 List<ReportPaymentEntity> existedPayment = reportPaymentService.getReportPaymentByRecordDate(queryDto);
				 // 当月数据是否存在，是：更新   否：新增
				 if (existedPayment != null && existedPayment.size() > 0) {
					 if (addOrUpdateReportPayment(projectDto.getProjId(), recordDate, existedPayment,startDate,endDate)) {
						 LOGGER.info("Update payment report succeed!");
					 } else {
						 LOGGER.error("Update payment report failed!");
					 }
				 } else {
					 if (addOrUpdateReportPayment(projectDto.getProjId(), recordDate, null,startDate,endDate)) {
						 LOGGER.info("Add payment report succeed!");
					 } else {
						 LOGGER.error("Add payment report failed!");
					 }
				 }
			 }
		}
	}
	
	/**
	 * 新增or更新回款报表数据
	 * @param projectId
	 * @param recordDate
	 * @param isNew
	 * @return
	 */
	private boolean addOrUpdateReportPayment(String projectId, String recordDate, List<ReportPaymentEntity> existedPayment,String startDate,String endDate) {
		Map<String, Object> houseTypeMap = new LinkedHashMap<String, Object>();
		
		List<ReportPaymenTaskEntity> paymentCountList = reportPaymentService.getPaymentCount(projectId, recordDate);
		List<ReportPaymenTaskEntity> paymentAmountList = reportPaymentService.getPaymentAmount(projectId, recordDate);
		List<ReportPaymenTaskEntity> voucherCountList = reportPaymentService.getVoucherCount(projectId, recordDate);
		List<ReportPaymenTaskEntity> voucherAmountList = reportPaymentService.getVoucherAmount(projectId, recordDate);
		//and by tianxf9
		List<ReportPaymenTaskEntity> paymentCountRangeList = reportPaymentService.getPaymentCountRange(projectId, startDate,endDate);
		List<ReportPaymenTaskEntity> paymentAmountRangeList = reportPaymentService.getPaymentAmountRange(projectId, startDate,endDate);
		//end by tianxf9
		List<ReportPaymenTaskEntity> intimeVoucherCountList = reportPaymentService.getIntimeVoucherCount(projectId, startDate,endDate);
		List<ReportPaymenTaskEntity> intimeVoucherAmountList = reportPaymentService.getIntimeVoucherAmount(projectId, startDate,endDate);
		
		// 按户型统计各项数据.
		if (paymentCountList != null && paymentCountList.size() > 0) {
			for (ReportPaymenTaskEntity taskEntity : paymentCountList) {
				ReportPaymentEntity paymentEntity = null;
				if (houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity) houseTypeMap.get(taskEntity.getHouseTypeId());
				} else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setPaymentCount(taskEntity.getCount().intValue());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		if (paymentAmountList != null && paymentAmountList.size() > 0) {
			for (ReportPaymenTaskEntity taskEntity : paymentAmountList) {
				ReportPaymentEntity paymentEntity = null;
				if (houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity) houseTypeMap.get(taskEntity.getHouseTypeId());
				} else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setPaymentAmount(taskEntity.getCount());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		//时间段内应收回款单量
		if(paymentCountRangeList!=null&&paymentCountRangeList.size()>0) {
			for(ReportPaymenTaskEntity taskEntity:paymentCountRangeList) {
				ReportPaymentEntity paymentEntity = null;
				if(houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity)houseTypeMap.get(taskEntity.getHouseTypeId());
				}else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setPaymentCountRange(taskEntity.getCount().intValue());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		//时间段内应收回款金额
		if(paymentAmountRangeList!=null&&paymentAmountRangeList.size()>0) {
			for(ReportPaymenTaskEntity taskEntity:paymentAmountRangeList) {
				ReportPaymentEntity paymentEntity = null;
				if(houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity)houseTypeMap.get(taskEntity.getHouseTypeId());
				}else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setPaymentAmountRange(taskEntity.getCount());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		if (voucherCountList != null && voucherCountList.size() > 0) {
			for (ReportPaymenTaskEntity taskEntity : voucherCountList) {
				ReportPaymentEntity paymentEntity = null;
				if (houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity) houseTypeMap.get(taskEntity.getHouseTypeId());
				} else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setVoucherCount(taskEntity.getCount().intValue());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		if (voucherAmountList != null && voucherAmountList.size() > 0) {
			for (ReportPaymenTaskEntity taskEntity : voucherAmountList) {
				ReportPaymentEntity paymentEntity = null;
				if (houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity) houseTypeMap.get(taskEntity.getHouseTypeId());
				} else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setVoucherAmount(taskEntity.getCount());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		if (intimeVoucherCountList != null && intimeVoucherCountList.size() > 0) {
			for (ReportPaymenTaskEntity taskEntity : intimeVoucherCountList) {
				ReportPaymentEntity paymentEntity = null;
				if (houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity) houseTypeMap.get(taskEntity.getHouseTypeId());
				} else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setIntimeVoucherCount(taskEntity.getCount().intValue());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		if (intimeVoucherAmountList != null && intimeVoucherAmountList.size() > 0) {
			for (ReportPaymenTaskEntity taskEntity : intimeVoucherAmountList) {
				ReportPaymentEntity paymentEntity = null;
				if (houseTypeMap.containsKey(taskEntity.getHouseTypeId())) {
					paymentEntity = (ReportPaymentEntity) houseTypeMap.get(taskEntity.getHouseTypeId());
				} else {
					paymentEntity = new ReportPaymentEntity();
					paymentEntity.setHouseTypeId(taskEntity.getHouseTypeId());
				}
				
				paymentEntity.setIntimeVoucherAmount(taskEntity.getCount());
				houseTypeMap.put(taskEntity.getHouseTypeId(), paymentEntity);
			}
		}
		
		
		if (houseTypeMap.isEmpty()) {
			LOGGER.error("The project:{} has no data in date:{}", projectId, recordDate);
		}
		
		
		// 当月数据不为空时先删除当月所有数据
		if (existedPayment != null) {
			LOGGER.info("Begin to delete all datas of month:{}", recordDate);
			delEntitysByRecordDate(recordDate);
			LOGGER.info("Delete all datas end.");
		}
		
		// 新增当月数据
		if (doAddReportPayment(projectId, recordDate, houseTypeMap)) {
			LOGGER.info("Add new payment report succeed!");
		} else {
			LOGGER.error("Add new payment report failed!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 新增回款报表数据
	 * @param projectId
	 * @param recordDate
	 * @param houseTypeMap
	 * @return
	 */
	private boolean doAddReportPayment(String projectId, String recordDate, Map<String, Object> houseTypeMap) {
		// 获取项目的所有户型
		List<HouseTypeDto> houseTypeList = projectService.getHouseTypeByProjectId(projectId);
		if (houseTypeList != null && houseTypeList.size() > 0) {
			for (HouseTypeDto houseTypeDto : houseTypeList) {
				ReportPaymentEntity newPayment = new ReportPaymentEntity();
				newPayment.setReportPaymentId(KeyGenUtils.genKey());
				newPayment.setProjectId(projectId);
				try {
					newPayment.setRecordDate(DateUtil.castString2Date(recordDate, DateUtil.MONTH_FORMAT));
				} catch (ParseException e) {
					LOGGER.error("Failed to format date.");
					return false;
				}
				newPayment.setHouseTypeId(houseTypeDto.getId());
				// 户型是否有统计值
				if (houseTypeMap.containsKey(houseTypeDto.getId())) {
					ReportPaymentEntity entity = (ReportPaymentEntity) houseTypeMap.get(houseTypeDto.getId());
					
					// 单量部分
					newPayment.setPaymentCount(entity.getPaymentCount());
					newPayment.setVoucherCount(entity.getVoucherCount());
					//更改未及时回款定义（时间段内应收回款-及时回款）
					newPayment.setDelayVoucherCount(MathUtils.sub(entity.getPaymentCountRange(), entity.getIntimeVoucherCount()));
					//新增时间段内的应回款单量
					newPayment.setPaymentCountRange(entity.getPaymentCountRange());
					if (newPayment.getPaymentCount() == 0) {
						newPayment.setVoucherRate(new BigDecimal(0).setScale(2));
					} else {
						newPayment.setVoucherRate(MathUtils.mul(MathUtils.div(entity.getVoucherCount(), entity.getPaymentCount(), 4).toString(), "100").setScale(2));
					}
					
					if(newPayment.getPaymentCountRange()==0) {
						newPayment.setVoucherIntimeRate(new BigDecimal(0).setScale(2));
					}else {
						newPayment.setVoucherIntimeRate(MathUtils.mul(MathUtils.div(entity.getIntimeVoucherCount(), entity.getPaymentCountRange(), 4).toString(), "100").setScale(2));
					}
					
					// 金额部分
					newPayment.setPaymentAmount(entity.getPaymentAmount());
					newPayment.setVoucherAmount(entity.getVoucherAmount());
					newPayment.setDelayVoucherAmount(entity.getPaymentAmountRange().subtract(entity.getIntimeVoucherAmount()));
					//新增时间段内的应回款金额
					newPayment.setPaymentAmountRange(entity.getPaymentAmountRange());
				} else {
					// 单量部分
					newPayment.setPaymentCount(0);
					newPayment.setPaymentCountRange(0);
					newPayment.setVoucherCount(0);
					newPayment.setDelayVoucherCount(0);
					newPayment.setVoucherRate(new BigDecimal(0));
					newPayment.setVoucherIntimeRate(new BigDecimal(0));
					// 金额部分
					newPayment.setPaymentAmount(new BigDecimal(0));
					newPayment.setPaymentAmountRange(new BigDecimal(0));
					newPayment.setVoucherAmount(new BigDecimal(0));
					newPayment.setDelayVoucherAmount(new BigDecimal(0));
				}
				
				newPayment.setIsDel(0);
				newPayment.setCreateTime(new Date());
				
				reportPaymentService.insertReportPayment(newPayment);
			}
		}
		
		return true;
	}
	
	/**
	 * 更新回款报表数据
	 * @param projectId
	 * @param recordDate
	 * @param existedPayment
	 * @param houseTypeMap
	 * @return
	 */
	@Deprecated
	private boolean doUpdateReportPayment(String projectId, String recordDate, List<ReportPaymentEntity> existedPayment, Map<String, Object> houseTypeMap) {
		// 获取项目所有户型
		List<HouseTypeDto> houseTypeList = projectService.getHouseTypeByProjectId(projectId);
		Map<String, HouseTypeDto> houseTypeMapper = convertList2Map(houseTypeList);
		
		// 1、更新当月数据
		for (ReportPaymentEntity oldPayment : existedPayment) {
			if (houseTypeMapper.containsKey(oldPayment.getHouseTypeId())) {
				houseTypeMapper.remove(oldPayment.getHouseTypeId());
			}
			
			ReportPaymentEntity paymentData = (ReportPaymentEntity) houseTypeMap.get(oldPayment.getHouseTypeId());
			if (paymentData == null) {
				continue;
			} 
			
			try {
				oldPayment.setRecordDate(DateUtil.castString2Date(recordDate, DateUtil.DATE_FORMAT));
			} catch (ParseException e) {
				LOGGER.error("Failed to format date.");
				return false;
			}
			// 单量部分
			oldPayment.setPaymentCount(MathUtils.add(oldPayment.getPaymentCount(), paymentData.getPaymentCount()));
			oldPayment.setVoucherCount(MathUtils.add(oldPayment.getVoucherCount(), paymentData.getVoucherCount()));
			oldPayment.setDelayVoucherCount(MathUtils.sub(oldPayment.getPaymentCount(), oldPayment.getVoucherCount()));
			if (oldPayment.getPaymentCount() == 0) {
				oldPayment.setVoucherRate(new BigDecimal(0).setScale(2));
				oldPayment.setVoucherIntimeRate(new BigDecimal(0).setScale(2));
			} else {
				oldPayment.setVoucherRate(MathUtils.mul(MathUtils.div(oldPayment.getVoucherCount(), oldPayment.getPaymentCount(), 4).toString(), "100").setScale(2));
				oldPayment.setVoucherIntimeRate(MathUtils.mul(MathUtils.sub("1", MathUtils.div(oldPayment.getDelayVoucherCount(), oldPayment.getPaymentCount(), 4).toString()).toString(), "100").setScale(2));
			}
			// 金额部分
			oldPayment.setPaymentAmount(oldPayment.getPaymentAmount().add(paymentData.getPaymentAmount()));
			oldPayment.setVoucherAmount(oldPayment.getVoucherAmount().add(paymentData.getVoucherAmount()));
			oldPayment.setDelayVoucherAmount(oldPayment.getPaymentAmount().subtract(oldPayment.getVoucherAmount()));
			oldPayment.setUpdateTime(new Date());
			
			reportPaymentService.updateReportPayment(oldPayment);
		}
		
		// 2、增加当月新增户型的数据
		for (Entry<String, HouseTypeDto> entry : houseTypeMapper.entrySet()) {
			ReportPaymentEntity newPayment = new ReportPaymentEntity();
			newPayment.setReportPaymentId(KeyGenUtils.genKey());
			newPayment.setProjectId(projectId);
			try {
				newPayment.setRecordDate(DateUtil.castString2Date(recordDate, DateUtil.DATE_FORMAT));
			} catch (ParseException e) {
				LOGGER.error("Failed to format date.");
				return false;
			}
			newPayment.setHouseTypeId(entry.getKey());

			if (houseTypeMap.containsKey(entry.getKey())) {
				// 2.2、新增有数据的户型
				ReportPaymentEntity entity = (ReportPaymentEntity) houseTypeMap.get(entry.getKey());
				
				// 单量部分
				newPayment.setPaymentCount(entity.getPaymentCount());
				newPayment.setVoucherCount(entity.getVoucherCount());
				newPayment.setDelayVoucherCount(MathUtils.sub(newPayment.getPaymentCount(), newPayment.getVoucherCount()));
				newPayment.setVoucherRate(MathUtils.mul(MathUtils.div(newPayment.getVoucherCount(), newPayment.getPaymentCount(), 4).toString(), "100").setScale(2));
				newPayment.setVoucherIntimeRate(MathUtils.mul(MathUtils.sub("1", MathUtils.div(newPayment.getDelayVoucherCount(), newPayment.getPaymentCount(), 4).toString()).toString(), "100").setScale(2));
				// 金额部分
				newPayment.setPaymentAmount(entity.getPaymentAmount());
				newPayment.setVoucherAmount(entity.getVoucherAmount());
				newPayment.setDelayVoucherAmount(newPayment.getPaymentAmount().subtract(newPayment.getVoucherAmount()));
			} else {
				// 2.3、新增默认数据的户型
				
				// 单量部分
				newPayment.setPaymentCount(0);
				newPayment.setVoucherCount(0);
				newPayment.setDelayVoucherCount(0);
				newPayment.setVoucherRate(new BigDecimal(0));
				newPayment.setVoucherIntimeRate(new BigDecimal(0));
				// 金额部分
				newPayment.setPaymentAmount(new BigDecimal(0));
				newPayment.setVoucherAmount(new BigDecimal(0));
				newPayment.setDelayVoucherAmount(new BigDecimal(0));
			}
			
			newPayment.setIsDel(0);
			newPayment.setCreateTime(new Date());
			
			reportPaymentService.insertReportPayment(newPayment);
		}	
		
		return true;
	}
	
	private Map<String, HouseTypeDto> convertList2Map(List<HouseTypeDto> houseTypeList) {
		Map<String, HouseTypeDto> resultMap = new HashMap<String, HouseTypeDto>();
		
		if (houseTypeList != null && houseTypeList.size() > 0) {
			for (HouseTypeDto houseTypeDto : houseTypeList) {
				resultMap.put(houseTypeDto.getId(), houseTypeDto);
			}
		}
		
		return resultMap;
	}
	
	/**
	 * 转化实例为dto
	 * @param entity
	 * @return
	 */
	private ReportPaymentDto convertEntity2Dto(ReportPaymentEntity entity) {
		ReportPaymentDto paymentDto = new ReportPaymentDto();
		if (entity != null) {
			paymentDto.setReportPaymentId(entity.getReportPaymentId());
			paymentDto.setProjectId(entity.getProjectId());
			paymentDto.setRecordDate(DateUtil.DateToStr(entity.getRecordDate(), DateUtil.TIME_FORMAT));
			paymentDto.setHouseTypeId(entity.getHouseTypeId());
			paymentDto.setPaymentCount(entity.getPaymentCount());
			paymentDto.setVoucherCount(entity.getVoucherCount());
			paymentDto.setDelayVoucherCount(entity.getDelayVoucherCount());
			paymentDto.setVoucherRate(entity.getVoucherRate().toString());
			paymentDto.setVoucherIntimeRate(entity.getVoucherIntimeRate().toString());
			paymentDto.setPaymentAmount(entity.getPaymentAmount().toString());
			paymentDto.setVoucherAmount(entity.getVoucherAmount().toString());
			paymentDto.setDelayVoucherAmount(entity.getDelayVoucherAmount().toString());
			paymentDto.setIsDel(entity.getIsDel());
			paymentDto.setCreateTime(DateUtil.DateToStr(entity.getCreateTime(), DateUtil.TIME_FORMAT));
			paymentDto.setDeleteTime(DateUtil.DateToStr(entity.getDeleteTime(), DateUtil.TIME_FORMAT));
			paymentDto.setUpdateTime(DateUtil.DateToStr(entity.getUpdateTime(), DateUtil.TIME_FORMAT));
			paymentDto.setCreateId(entity.getCreateId());
			paymentDto.setUpdateId(entity.getUpdateId());
			paymentDto.setDeleteId(entity.getDeleteId());
		}
		
		return paymentDto;
	}
	
	/**
	 * 删除记录根据记录时间
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delEntitysByRecordDate(String recordDate) {
		return reportPaymentService.delEntitysByRecordDate(recordDate, new Date(), SysConstant.ADMINID);
	}
	
	
	/**
	 * 获取所有项目的实际回款率
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getVoucherCountRateForKB(String startDate,String endDate) {
		// 获取所有项目列表
		List<ProjectListReturnDto> projectList = projectService.getProjectList();
		
		//获取所有项目时间段内应收款单量
		List<ReportPaymentForKBDto> paymentCountList = this.reportPaymentService.getPaymentCountForKB(startDate, endDate);
		Map<String,Integer> paymentCountMap = new HashMap<>();
		for(ReportPaymentForKBDto paymentCount:paymentCountList) {
			paymentCountMap.put(paymentCount.getProjectId(), paymentCount.getCount());
		}
		//获取所有项目时间段内收款单的时间收款单量
		List<ReportPaymentForKBDto> voucherCountList = this.reportPaymentService.getVoucherCountForKB(startDate, endDate);
		Map<String,Integer> voucherCountMap = new HashMap<>();
		for(ReportPaymentForKBDto voucherCount:voucherCountList) {
			voucherCountMap.put(voucherCount.getProjectId(), voucherCount.getCount());
		}
		
		List<KanbanRateDto> kanbanRateDtos = new ArrayList<>();
		for(ProjectListReturnDto projectDto:projectList) {
			KanbanRateDto kanbanDto = new KanbanRateDto();
			kanbanDto.setProjectId(projectDto.getProjId());
			if(paymentCountMap.get(projectDto.getProjId())!=null&&paymentCountMap.get(projectDto.getProjId()).intValue()!=0) {
				if(voucherCountMap.get(projectDto.getProjId())!=null&&voucherCountMap.get(projectDto.getProjId()).intValue()!=0) {
					BigDecimal rate = new BigDecimal(voucherCountMap.get(projectDto.getProjId())).divide(new BigDecimal(paymentCountMap.get(projectDto.getProjId())), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					kanbanDto.setRate(rate);
				}else {
					kanbanDto.setRate(new BigDecimal(0));
				}
			}else {	
				kanbanDto.setRate(new BigDecimal(-1));
			}
			
			kanbanRateDtos.add(kanbanDto);
		}
		
		return kanbanRateDtos;
	}
}
