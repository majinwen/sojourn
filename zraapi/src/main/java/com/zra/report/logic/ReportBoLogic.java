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
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.MathUtils;
import com.zra.common.utils.SysConstant;
import com.zra.house.entity.HouseTypeEntity;
import com.zra.house.entity.dto.PageDto;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.logic.HouseTypeLogic;
import com.zra.house.logic.ProjectLogic;
import com.zra.report.entity.ReportBoEntity;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.entity.dto.ReportBoDto;
import com.zra.report.service.ReportBoService;

/**
 * 报表商机数据逻辑层
 * @author huangy168@ziroom.com
 * @Date 2016年10月28日
 * @Time 下午6:24:45
 */
@Component
public class ReportBoLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportBoLogic.class);
	
	@Autowired
	private ReportBoService reportBoService;
	
	@Autowired
	private ProjectLogic projectLogic;
	
	@Autowired
	private HouseTypeLogic houseTypeLogic;
	
	/**
	 * 获取报表商机数据总数
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int getReportBoCount(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report bo count.", projectId);
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportBoDto reportBoDto = new ReportBoDto();
		reportBoDto.setProjectId(projectId);
		reportBoDto.setBeginTime(beginTime);
		reportBoDto.setEndTime(endTime);
		
		return reportBoService.getReportBoCount(reportBoDto);
	}
	
	/**
	 * 分页获取报表商机数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportBoMapByPage(String projectId, String beginTime, String endTime, PageDto page) {
		LOGGER.info("Begin to get project:{}'s report bo list by page.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportBoDto reportBoDto = new ReportBoDto();
		reportBoDto.setProjectId(projectId);
		reportBoDto.setBeginTime(beginTime);
		reportBoDto.setEndTime(endTime);
		
		// 1、获取查询时间列表
		List<String> dateList = reportBoService.getReportBoTimeSpanByPage(reportBoDto, page);
		if (dateList != null && dateList.size() > 0) {
			// 2、查询某时间的统计数据
			for (String date : dateList) {
				reportBoDto.setRecordDate(date);
				List<ReportBoEntity> reportBoList = reportBoService.getReportBoList(reportBoDto);
				// 3、映射日期、户型对应数据
				Map<String, Object> reportBoMap = new LinkedHashMap<>();
				ReportBoDto allHouseTypeBo = new ReportBoDto();	// 统计所有户型
				if (reportBoList != null && reportBoList.size() > 0) {
					reportBoMap.put("ALL", allHouseTypeBo);
					for (ReportBoEntity reportBo : reportBoList) {
						ReportBoDto boDto = convertEntity2Dto(reportBo);
						
						//wangws21 2017-1-18 客源-带看转化率显示
						if(boDto.getKylCount() == 0){
						    boDto.setOrder2seeRate("-");
						}
						
						reportBoMap.put(reportBo == null ? null : reportBo.getHouseTypeId(), boDto);
						
						allHouseTypeBo.setBusinessCount(MathUtils.add(allHouseTypeBo.getBusinessCount(), boDto.getBusinessCount()));
						//wangws21 2017-1-18 添加客源量
						allHouseTypeBo.setKylCount(MathUtils.add(allHouseTypeBo.getKylCount(), boDto.getKylCount()));
						allHouseTypeBo.setSeeCount(MathUtils.add(allHouseTypeBo.getSeeCount(), boDto.getSeeCount()));
						allHouseTypeBo.setDealCount(MathUtils.add(allHouseTypeBo.getDealCount(), boDto.getDealCount()));
						// 新签量					
						allHouseTypeBo.setLongSignCount(MathUtils.add(allHouseTypeBo.getLongSignCount(), boDto.getLongSignCount()));
						allHouseTypeBo.setShortSignCount1(MathUtils.add(allHouseTypeBo.getShortSignCount1(), boDto.getShortSignCount1()));
						allHouseTypeBo.setShortSignCount2(MathUtils.add(allHouseTypeBo.getShortSignCount2(), boDto.getShortSignCount2()));
						allHouseTypeBo.setSignTotal(MathUtils.add(allHouseTypeBo.getSignTotal(), boDto.getSignTotal()));
						
						// 所有户型实际出房价格
						allHouseTypeBo.setLongActualPrice(MathUtils.add(allHouseTypeBo.getLongActualPrice(), boDto.getLongActualPrice()).toString());
						allHouseTypeBo.setShortActualPrice1(MathUtils.add(allHouseTypeBo.getShortActualPrice1(), boDto.getShortActualPrice1()).toString());
						allHouseTypeBo.setShortActualPrice2(MathUtils.add(allHouseTypeBo.getShortActualPrice2(), boDto.getShortActualPrice2()).toString());
						allHouseTypeBo.setActualPriceTotal(MathUtils.add(allHouseTypeBo.getActualPriceTotal(), boDto.getActualPriceTotal()).toString());
					}
					//allHouseTypeBo.setOrder2seeRate(MathUtils.mul(MathUtils.div(allHouseTypeBo.getSeeCount().toString(), allHouseTypeBo.getBusinessCount().toString(), 4).toString(), "100").setScale(2).toString());
					//wangws21 2017-1-18  客源－带看转化率
                    if(allHouseTypeBo.getKylCount() !=null && allHouseTypeBo.getKylCount()!=0){
                        allHouseTypeBo.setOrder2seeRate(MathUtils.mul(MathUtils.div(allHouseTypeBo.getSeeCount().toString(), allHouseTypeBo.getKylCount().toString(), 4).toString(), "100").setScale(2).toString());
                    }else{
                        allHouseTypeBo.setOrder2seeRate("-");
                    }
					allHouseTypeBo.setSee2dealRate(MathUtils.mul(MathUtils.div(allHouseTypeBo.getDealCount().toString(), allHouseTypeBo.getSeeCount().toString(), 4).toString(), "100").setScale(2).toString());
					
					// 计算平均出房价格
					allHouseTypeBo.setLongAveragePrice(MathUtils.div(allHouseTypeBo.getLongActualPrice(), allHouseTypeBo.getLongSignCount().toString(), 2).toString());
					allHouseTypeBo.setShortAveragePrice1(MathUtils.div(allHouseTypeBo.getShortActualPrice1(), allHouseTypeBo.getShortSignCount1().toString(), 2).toString());
					allHouseTypeBo.setShortAveragePrice2(MathUtils.div(allHouseTypeBo.getShortActualPrice2(), allHouseTypeBo.getShortSignCount2().toString(), 2).toString());
					allHouseTypeBo.setPriceAverageTotal(MathUtils.div(allHouseTypeBo.getActualPriceTotal(), allHouseTypeBo.getSignTotal().toString(), 2).toString());
				}
				
				resultMap.put(date, reportBoMap);
			}
		}
		
		return resultMap;
	}
	
	/**
	 * 获取报表商机数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String, Map<String, Object>> getReportBoMap(String projectId, String beginTime, String endTime) {
		LOGGER.info("Begin to get project:{}'s report bo list.", projectId);
		Map<String, Map<String, Object>> resultMap = new LinkedHashMap<String, Map<String,Object>>();
		
		if (StringUtils.isBlank(projectId)) {
			LOGGER.error("The project id is empty.");
			throw new ResultException(ErrorEnum.MSG_PARAM_NULL);
		}
		ReportBoDto reportBoDto = new ReportBoDto();
		reportBoDto.setProjectId(projectId);
		reportBoDto.setBeginTime(beginTime);
		reportBoDto.setEndTime(endTime);
		
		Map<String, Object> reportBoMap = new LinkedHashMap<>();
		ReportBoDto allHouseTypeBo = new ReportBoDto();	// 统计所有户型
		
		// 1、获取报表商机数据列表
		List<ReportBoEntity> reportBoList = reportBoService.getReportBoListByTimeSpan(reportBoDto);
		if (reportBoList != null && reportBoList.size() > 0) {
			reportBoMap.put("ALL", allHouseTypeBo);
			
			for (ReportBoEntity reportBo : reportBoList) {
				ReportBoDto boDto = convertEntity2Dto(reportBo);
				// 1.1、统计各户型总计
				if (reportBoMap.containsKey(boDto.getHouseTypeId())) {
					ReportBoDto tempDto = (ReportBoDto) reportBoMap.get(boDto.getHouseTypeId());
					
					tempDto.setBusinessCount(MathUtils.add(tempDto.getBusinessCount(), boDto.getBusinessCount()));
					//wangws21 2017-1-18 添加客源量
					tempDto.setKylCount(MathUtils.add(tempDto.getKylCount(), boDto.getKylCount()));
					tempDto.setSeeCount(MathUtils.add(tempDto.getSeeCount(), boDto.getSeeCount()));
					tempDto.setDealCount(MathUtils.add(tempDto.getDealCount(), boDto.getDealCount()));
					// 新签量
					tempDto.setLongSignCount(MathUtils.add(tempDto.getLongSignCount(), boDto.getLongSignCount()));
					tempDto.setShortSignCount1(MathUtils.add(tempDto.getShortSignCount1(), boDto.getShortSignCount1()));
					tempDto.setShortSignCount2(MathUtils.add(tempDto.getShortSignCount2(), boDto.getShortSignCount2()));
					tempDto.setSignTotal(MathUtils.add(tempDto.getSignTotal(), boDto.getSignTotal()));
					
					// 实际出房价格
					tempDto.setLongActualPrice(MathUtils.add(tempDto.getLongActualPrice(), boDto.getLongActualPrice()).toString());
					tempDto.setShortActualPrice1(MathUtils.add(tempDto.getShortActualPrice1(), boDto.getShortActualPrice1()).toString());
					tempDto.setShortActualPrice2(MathUtils.add(tempDto.getShortActualPrice2(), boDto.getShortActualPrice2()).toString());
					tempDto.setActualPriceTotal(MathUtils.add(tempDto.getActualPriceTotal(), boDto.getActualPriceTotal()).toString());
					
					reportBoMap.put(boDto.getHouseTypeId(), tempDto);
				} else {
					reportBoMap.put(boDto.getHouseTypeId(), boDto);
				}
			}
			
			// 2、计算所有户型数据、转化率等
			for (Entry<String, Object> entry : reportBoMap.entrySet()) {
				ReportBoDto tempDto = (ReportBoDto) entry.getValue();
				if (!"ALL".equals(entry.getKey())) {
					//tempDto.setOrder2seeRate(MathUtils.mul(MathUtils.div(tempDto.getSeeCount().toString(), tempDto.getBusinessCount().toString(), 4).toString(), "100").setScale(2).toString());
					//wangws21 2017-1-18  客源－带看转化率
				    if(tempDto.getKylCount() !=null && tempDto.getKylCount().intValue()!=0){
					    tempDto.setOrder2seeRate(MathUtils.mul(MathUtils.div(tempDto.getSeeCount().toString(), tempDto.getKylCount().toString(), 4).toString(), "100").setScale(2).toString());
					}else{
					    tempDto.setOrder2seeRate("-");
					}
					tempDto.setSee2dealRate(MathUtils.mul(MathUtils.div(tempDto.getDealCount().toString(), tempDto.getSeeCount().toString(), 4).toString(), "100").setScale(2).toString());
					
					tempDto.setLongAveragePrice(MathUtils.div(tempDto.getLongActualPrice().toString(), tempDto.getLongSignCount().toString(), 2).toString());
					tempDto.setShortAveragePrice1(MathUtils.div(tempDto.getShortActualPrice1().toString(), tempDto.getShortSignCount1().toString(), 2).toString());
					tempDto.setShortAveragePrice2(MathUtils.div(tempDto.getShortActualPrice2().toString(), tempDto.getShortSignCount2().toString(), 2).toString());
					tempDto.setPriceAverageTotal(MathUtils.div(tempDto.getActualPriceTotal().toString(), tempDto.getSignTotal().toString(), 2).toString());
				}
				
				allHouseTypeBo.setBusinessCount(MathUtils.add(allHouseTypeBo.getBusinessCount(), tempDto.getBusinessCount()));
				allHouseTypeBo.setKylCount(MathUtils.add(allHouseTypeBo.getKylCount(), tempDto.getKylCount()));
				allHouseTypeBo.setSeeCount(MathUtils.add(allHouseTypeBo.getSeeCount(), tempDto.getSeeCount()));
				allHouseTypeBo.setDealCount(MathUtils.add(allHouseTypeBo.getDealCount(), tempDto.getDealCount()));
				// 新签量			
				allHouseTypeBo.setLongSignCount(MathUtils.add(allHouseTypeBo.getLongSignCount(), tempDto.getLongSignCount()));
				allHouseTypeBo.setShortSignCount1(MathUtils.add(allHouseTypeBo.getShortSignCount1(), tempDto.getShortSignCount1()));
				allHouseTypeBo.setShortSignCount2(MathUtils.add(allHouseTypeBo.getShortSignCount2(), tempDto.getShortSignCount2()));
				allHouseTypeBo.setSignTotal(MathUtils.add(allHouseTypeBo.getSignTotal(), tempDto.getSignTotal()));
				
				// 所有户型实际出房价格
				allHouseTypeBo.setLongActualPrice(MathUtils.add(allHouseTypeBo.getLongActualPrice(), tempDto.getLongActualPrice()).toString());
				allHouseTypeBo.setShortActualPrice1(MathUtils.add(allHouseTypeBo.getShortActualPrice1(), tempDto.getShortActualPrice1()).toString());
				allHouseTypeBo.setShortActualPrice2(MathUtils.add(allHouseTypeBo.getShortActualPrice2(), tempDto.getShortActualPrice2()).toString());
				allHouseTypeBo.setActualPriceTotal(MathUtils.add(allHouseTypeBo.getActualPriceTotal(), tempDto.getActualPriceTotal()).toString());
			}
			//allHouseTypeBo.setOrder2seeRate(MathUtils.mul(MathUtils.div(allHouseTypeBo.getSeeCount().toString(), allHouseTypeBo.getBusinessCount().toString(), 4).toString(), "100").setScale(2).toString());
			//wangws21 2017-1-18  客源－带看转化率
			if(allHouseTypeBo.getKylCount() !=null && allHouseTypeBo.getKylCount().intValue()!=0){
			    allHouseTypeBo.setOrder2seeRate(MathUtils.mul(MathUtils.div(allHouseTypeBo.getSeeCount().toString(), allHouseTypeBo.getKylCount().toString(), 4).toString(), "100").setScale(2).toString());
            }else{
                allHouseTypeBo.setOrder2seeRate("-");
            }
			allHouseTypeBo.setSee2dealRate(MathUtils.mul(MathUtils.div(allHouseTypeBo.getDealCount().toString(), allHouseTypeBo.getSeeCount().toString(), 4).toString(), "100").setScale(2).toString());
		
			// 计算平均出房价格
			allHouseTypeBo.setLongAveragePrice(MathUtils.div(allHouseTypeBo.getLongActualPrice().toString(), allHouseTypeBo.getLongSignCount().toString(), 2).toString());
			allHouseTypeBo.setShortAveragePrice1(MathUtils.div(allHouseTypeBo.getShortActualPrice1().toString(), allHouseTypeBo.getShortSignCount1().toString(), 2).toString());
			allHouseTypeBo.setShortAveragePrice2(MathUtils.div(allHouseTypeBo.getShortActualPrice2().toString(), allHouseTypeBo.getShortSignCount2().toString(), 2).toString());
			allHouseTypeBo.setPriceAverageTotal(MathUtils.div(allHouseTypeBo.getActualPriceTotal().toString(), allHouseTypeBo.getSignTotal().toString(), 2).toString());
			
			resultMap.put("total", reportBoMap);
		}
		
		return resultMap;
	}
	
	/**
	 * 转化实例为dto
	 * @param entity
	 * @return
	 */
	private ReportBoDto convertEntity2Dto(ReportBoEntity entity) {
		ReportBoDto boDto = new ReportBoDto();
		if (entity != null) {
			boDto.setReportBoId(entity.getReportBoId());
			boDto.setProjectId(entity.getProjectId());
			boDto.setRecordDate(DateUtil.DateToStr(entity.getRecordDate(), DateUtil.TIME_FORMAT));
			boDto.setHouseTypeId(entity.getHouseTypeId());
			boDto.setBusinessCount(entity.getBusinessCount());
			boDto.setOrder2seeRate(entity.getOrder2seeRate().toString());
			boDto.setSeeCount(entity.getSeeCount());
			boDto.setDealCount(entity.getDealCount());
			boDto.setSee2dealRate(entity.getSee2dealRate().toString());
			boDto.setLongSignCount(entity.getLongSignCount());
			boDto.setShortSignCount1(entity.getShortSignCount1());
			boDto.setShortSignCount2(entity.getShortSignCount2());
			boDto.setSignTotal(entity.getSignTotal());
			boDto.setLongAveragePrice(entity.getLongAveragePrice().toString());
			boDto.setShortAveragePrice1(entity.getShortAveragePrice1().toString());
			boDto.setShortAveragePrice2(entity.getShortAveragePrice2().toString());
			boDto.setPriceAverageTotal(entity.getPriceAverageTotal().toString());
			boDto.setLongActualPrice(entity.getLongActualPrice().toString());
			boDto.setShortActualPrice1(entity.getShortActualPrice1().toString());
			boDto.setShortActualPrice2(entity.getShortActualPrice2().toString());
			boDto.setActualPriceTotal(entity.getActualPriceTotal().toString());
			boDto.setIsDel(entity.getIsDel());
			boDto.setCreateTime(DateUtil.DateToStr(entity.getCreateTime(), DateUtil.TIME_FORMAT));
			boDto.setDeleteTime(DateUtil.DateToStr(entity.getDeleteTime(), DateUtil.TIME_FORMAT));
			boDto.setUpdateTime(DateUtil.DateToStr(entity.getUpdateTime(), DateUtil.TIME_FORMAT));
			boDto.setCreateId(entity.getCreateId());
			boDto.setUpdateId(entity.getUpdateId());
			boDto.setDeleteId(entity.getDeleteId());
			//wangws21 2017-1-18  添加客源量
			boDto.setKylCount(entity.getKylCount());
		}
		
		return boDto;
	}
	
	
	/**
	 * 存储运营报表中的商机数据
	 * @author tianxf9
	 * @param recodeDate yyyy-MM-dd
	 * @param projectId
	 * @return
	 */
	public int saveReportBoDate(String recordDate) {
		
		String startDate = recordDate + " 00:00:00";
		String endDate = recordDate +" 23:59:59";
		//获取所有项目
		List<ProjectListReturnDto> projectList = projectLogic.getProjectList();
		//获取所有户型
		List<HouseTypeEntity> houseTypeList = houseTypeLogic.getAllHouseType();
		//项目-户型map
		Map<String,List<HouseTypeEntity>> houseTypeMap = new HashMap<String,List<HouseTypeEntity>>();
	
		for(HouseTypeEntity houseType:houseTypeList) {
			
			if(houseTypeMap.get(houseType.getProjectId())==null) {
				List<HouseTypeEntity> houseTypes = new ArrayList<HouseTypeEntity>();
				houseTypes.add(houseType);
				houseTypeMap.put(houseType.getProjectId(), houseTypes);
			}else {
				houseTypeMap.get(houseType.getProjectId()).add(houseType);
			}
		}
		
		//新增实体
		List<ReportBoEntity> insertEntityList = new ArrayList<ReportBoEntity>();
		//遍历所有项目
		for(ProjectListReturnDto project:projectList) {
			Map<String,ReportBoEntity> reportBoEntityMap = new HashMap<String,ReportBoEntity>();
			String projectId = project.getProjId();
			LOGGER.info("====保存商机数据params===startDate="+startDate+";endDate="+endDate+""+";projectId="+projectId);
			if(houseTypeMap.get(projectId)!=null) {
				
				List<HouseTypeEntity> houseTypes = houseTypeMap.get(projectId);
				for(HouseTypeEntity houseType:houseTypes) {
					
					ReportBoEntity entity = new ReportBoEntity();
					entity.setReportBoId(KeyGenUtils.genKey());
					entity.setProjectId(projectId);
					entity.setHouseTypeId(houseType.getId());
					try {
						entity.setRecordDate(DateUtil.castString2Date(recordDate, DateUtil.DATE_FORMAT));
					} catch (ParseException e) {
						LOGGER.error("日期处理错误", e);
					}
					entity.setCreateId(SysConstant.ADMINID);
					entity.setCreateTime(new Date());
					entity.setIsDel(0);
					reportBoEntityMap.put(houseType.getId(), entity);
				}
				//设置商机新增量
				setBusinessCount(reportBoEntityMap,startDate,endDate,projectId);
				//设置商机 客源量  wangws21 2017-1-18
				setBusinessKYLCount(reportBoEntityMap, startDate, endDate, projectId);
                //设置带看量，约看带看转换率
				setSeeBusinessCount(reportBoEntityMap,startDate,endDate,projectId);
				//设置新签量
				setSignCount(reportBoEntityMap,startDate,endDate,projectId);
				//设置商机成交量
				getDealBusinessCount(reportBoEntityMap,startDate,endDate,projectId);
				//带看-成交转换率
				setSee2DealRate(reportBoEntityMap);
				//设置实际出房价
				setActualPrice(reportBoEntityMap, startDate, endDate, projectId);
				//设置平均出房价
				setAveragePrice(reportBoEntityMap);
	  
			}
			
			//遍历map
			for(String key:reportBoEntityMap.keySet()) {
				insertEntityList.add(reportBoEntityMap.get(key));
			}
	
		}
		
		//保存商机报表
		return this.reportBoService.saveReportBoEntitys(insertEntityList);
		
	}
	
	
	/**
	 * 设置新增商机量
	 * @author tianxf9
	 * @param reportBoEntityMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void setBusinessCount(Map<String,ReportBoEntity> reportBoEntityMap,String startDate,String endDate,String projectId) {
		
		//获取新增商机量
		List<BaseReportDto> businessCounts = this.reportBoService.getNewBusinessCount(startDate, endDate, projectId);
		
		for(BaseReportDto businessCount:businessCounts) {
			if(businessCount.getHouseTypeId()==null) {
				ReportBoEntity entity = new ReportBoEntity();
				entity.setReportBoId(KeyGenUtils.genKey());
				entity.setProjectId(projectId);
				entity.setHouseTypeId(businessCount.getHouseTypeId());
				try {
					entity.setRecordDate(DateUtil.castString2Date(startDate, DateUtil.DATE_FORMAT));
				} catch (ParseException e) {
					LOGGER.error("日期处理错误", e);
				}
				entity.setCreateId(SysConstant.ADMINID);
				entity.setCreateTime(new Date());
				entity.setIsDel(0);
				entity.setBusinessCount(businessCount.getCount());
				reportBoEntityMap.put("NULL", entity);
			}else if(businessCount.getHouseTypeId().equals("")) {
				ReportBoEntity entity = new ReportBoEntity();
				entity.setReportBoId(KeyGenUtils.genKey());
				entity.setProjectId(projectId);
				entity.setHouseTypeId(businessCount.getHouseTypeId());
				try {
					entity.setRecordDate(DateUtil.castString2Date(startDate, DateUtil.DATE_FORMAT));
				} catch (ParseException e) {
					LOGGER.error("日期处理错误", e);
				}
				entity.setCreateId(SysConstant.ADMINID);
				entity.setCreateTime(new Date());
				entity.setIsDel(0);
				entity.setBusinessCount(businessCount.getCount());
				reportBoEntityMap.put(businessCount.getHouseTypeId(), entity);
			}else {
				if(reportBoEntityMap.get(businessCount.getHouseTypeId())!=null) {
					reportBoEntityMap.get(businessCount.getHouseTypeId()).setBusinessCount(businessCount.getCount());
				}
				
			}
		}
		
	}
	
	/**
     * 设置商机客源量 2017-1-17.
     * @author wangws21
     * @param reportBoEntityMap
     * @param startDate
     * @param endDate
     * @param projectId
     */
    private void setBusinessKYLCount(Map<String,ReportBoEntity> reportBoEntityMap,String startDate,String endDate,String projectId) {
        
        //获取新增商机客源量
        List<BaseReportDto> businessCounts = this.reportBoService.getBusinessKYLCount(startDate, endDate, projectId);
        
        for(BaseReportDto kylCount:businessCounts) {
            if(kylCount.getHouseTypeId()==null) {
                kylCount.setHouseTypeId("NULL");
            }
            
            ReportBoEntity entity = reportBoEntityMap.get(kylCount.getHouseTypeId());
            if(entity == null){
                ReportBoEntity newentity = new ReportBoEntity();
                newentity.setReportBoId(KeyGenUtils.genKey());
                newentity.setProjectId(projectId);
                newentity.setHouseTypeId(kylCount.getHouseTypeId());
                try {
                    newentity.setRecordDate(DateUtil.castString2Date(startDate, DateUtil.DATE_FORMAT));
                } catch (ParseException e) {
                    LOGGER.error("日期处理错误", e);
                }
                newentity.setCreateId(SysConstant.ADMINID);
                newentity.setCreateTime(new Date());
                newentity.setIsDel(0);
                newentity.setKylCount(kylCount.getCount());
                reportBoEntityMap.put(kylCount.getHouseTypeId(), newentity);
            } else {
                reportBoEntityMap.get(kylCount.getHouseTypeId()).setKylCount(kylCount.getCount());
            }
        }
        
    }
	
	/**
	 * 获取商机成交量
	 * @author tianxf9
	 * @param reportBoEntityMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void getDealBusinessCount(Map<String,ReportBoEntity> reportBoEntityMap,String startDate,String endDate,String projectId) {
		List<BaseReportDto> businessCounts = this.reportBoService.getDealBusinessCount(startDate, endDate, projectId);
		for(BaseReportDto reportDto:businessCounts) {
			ReportBoEntity entity = reportBoEntityMap.get(reportDto.getHouseTypeId());
			if(entity!=null) {
				entity.setDealCount(reportDto.getCount());
				reportBoEntityMap.put(reportDto.getHouseTypeId(), entity);
			}	
		}
	}
	
	/**
	 * 设置带看量，约看带看转换率
	 * @author tianxf9
	 * @param reportBoEntityMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void setSeeBusinessCount(Map<String,ReportBoEntity> reportBoEntityMap,String startDate,String endDate,String projectId) {
		//获取带看量
		List<BaseReportDto> businessCounts = this.reportBoService.getSeeBusinessCount(startDate, endDate, projectId);
		
		String houseTypeId = null;
		for(BaseReportDto businessCount:businessCounts) {
			if(businessCount.getHouseTypeId()==null) {
				houseTypeId = "NULL";
			}else {
				houseTypeId = businessCount.getHouseTypeId();
			}
			
			ReportBoEntity entity = reportBoEntityMap.get(houseTypeId);
			if(entity!=null) {
				reportBoEntityMap.get(houseTypeId).setSeeCount(businessCount.getCount());
				BigDecimal bd = new BigDecimal(0);
				/*if(entity.getBusinessCount()!=0) {
					bd =  new BigDecimal(entity.getSeeCount()).divide(new BigDecimal(entity.getBusinessCount()), 4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}*/
				if(entity.getKylCount() !=null && entity.getKylCount().intValue()!=0){
				    bd =  new BigDecimal(MathUtils.mul(MathUtils.div(entity.getSeeCount().toString(), entity.getKylCount().toString(), 4).toString(), "100").setScale(2).toString());
                }
				reportBoEntityMap.get(houseTypeId).setOrder2seeRate(bd);
			}else {
				ReportBoEntity newentity = new ReportBoEntity();
				newentity.setReportBoId(KeyGenUtils.genKey());
				newentity.setProjectId(projectId);
				newentity.setHouseTypeId(businessCount.getHouseTypeId());
				try {
					newentity.setRecordDate(DateUtil.castString2Date(startDate, DateUtil.DATE_FORMAT));
				} catch (ParseException e) {
					LOGGER.error("日期处理错误", e);
				}
				newentity.setCreateId(SysConstant.ADMINID);
				newentity.setCreateTime(new Date());
				newentity.setIsDel(0);
				newentity.setSeeCount(businessCount.getCount());
				reportBoEntityMap.put(houseTypeId, newentity);
			}
			
			
		}
	}
	
	/**
	 * 设置新签量
	 * @author tianxf9
	 * @param reportBoEntityMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void setSignCount(Map<String,ReportBoEntity> reportBoEntityMap,String startDate,String endDate,String projectId) {
		
		//获取新签量
		List<BaseReportDto> businessCounts = this.reportBoService.getSignCount(startDate, endDate, projectId);
		
		for(BaseReportDto businessCount:businessCounts) {
			
			
			ReportBoEntity entity = reportBoEntityMap.get(businessCount.getHouseTypeId());
			Integer signTotalCount = entity.getSignTotal().intValue() + businessCount.getCount();
			
			if(businessCount.getConRentYear()==1||businessCount.getConRentYear()==2||businessCount.getConRentYear()==3) {
				Integer signShortCount1 = entity.getShortSignCount1().intValue() + businessCount.getCount();
				entity.setShortSignCount1(signShortCount1);
			}else if(businessCount.getConRentYear()==4||businessCount.getConRentYear()==5||businessCount.getConRentYear()==6) {
				Integer signShortCount2 = entity.getShortSignCount2().intValue() + businessCount.getCount();
				entity.setShortSignCount2(signShortCount2);
			}else {
				Integer signLongCount = entity.getLongSignCount().intValue() + businessCount.getCount();
				entity.setLongSignCount(signLongCount);
			}
			
			entity.setSignTotal(signTotalCount);
			reportBoEntityMap.put(businessCount.getHouseTypeId(), entity);
		}
	}
	
	/**
	 * 计算带看成交转换率
	 * @author tianxf9
	 * @param reportBoEntityMap
	 */
	public void setSee2DealRate(Map<String,ReportBoEntity> reportBoEntityMap) {
		
		for(String houseTypeId:reportBoEntityMap.keySet()) {
			
			ReportBoEntity entity = reportBoEntityMap.get(houseTypeId);
			BigDecimal bd = new BigDecimal(0);
			if(entity.getSeeCount().intValue()!=0) {
				bd =  new BigDecimal(entity.getDealCount()).divide(new BigDecimal(entity.getSeeCount()),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			}
			
			entity.setSee2dealRate(bd);
			
			reportBoEntityMap.put(houseTypeId, entity);
		}
	}
	
	/**
	 * 设置实际出房价
	 * @author tianxf9
	 * @param reportBoEntityMap
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 */
	public void setActualPrice(Map<String,ReportBoEntity> reportBoEntityMap,String startDate,String endDate,String projectId) {
		
		List<BaseReportDto> businessCounts = this.reportBoService.getActualPrice(startDate, endDate, projectId);
		
		for(BaseReportDto businessCount:businessCounts) {
			
			ReportBoEntity entity = reportBoEntityMap.get(businessCount.getHouseTypeId());
			BigDecimal actualPriceTotal = entity.getActualPriceTotal();
			if(businessCount.getActualPrice()!=null) {
				actualPriceTotal = entity.getActualPriceTotal().add(businessCount.getActualPrice());
				entity.setActualPriceTotal(actualPriceTotal);
				
				if(businessCount.getConRentYear()==1||businessCount.getConRentYear()==2||businessCount.getConRentYear()==3) {
					
					BigDecimal actualPriceShort1 = entity.getShortActualPrice1().add(businessCount.getActualPrice());
					entity.setShortActualPrice1(actualPriceShort1);
				}else if(businessCount.getConRentYear()==4||businessCount.getConRentYear()==5||businessCount.getConRentYear()==6) {
					
					BigDecimal actualPriceShort2 = entity.getShortActualPrice2().add(businessCount.getActualPrice());
					entity.setShortActualPrice2(actualPriceShort2);
				}else {
					
					BigDecimal actualPriceLong = entity.getLongActualPrice().add(businessCount.getActualPrice());
					entity.setLongActualPrice(actualPriceLong);
				}
			}
			
			
			reportBoEntityMap.put(businessCount.getHouseTypeId(), entity);
		}
	}
	
	/**
	 * 设置平均出房价
	 * @author tianxf9
	 * @param reportBoEntityMap
	 */
	public void setAveragePrice(Map<String,ReportBoEntity> reportBoEntityMap) {
		
		for(String key:reportBoEntityMap.keySet()) {
			
			ReportBoEntity entity = reportBoEntityMap.get(key);
			BigDecimal longAveragePrice = new BigDecimal(0);
			if(entity.getLongSignCount().intValue()!=0) {
				longAveragePrice = entity.getLongActualPrice().divide(new BigDecimal(entity.getLongSignCount()),2);
			}
			entity.setLongAveragePrice(longAveragePrice);
			
			BigDecimal shortAveragePrice1 = new BigDecimal(0);
			if(entity.getShortSignCount1().intValue()!=0) {
				shortAveragePrice1 = entity.getShortActualPrice1().divide(new BigDecimal(entity.getShortSignCount1()),2);
			}
			entity.setShortAveragePrice1(shortAveragePrice1);
			
			BigDecimal shortAveragePrice2 = new BigDecimal(0);
			if(entity.getShortSignCount2().intValue()!=0) {
				shortAveragePrice2 = entity.getShortActualPrice2().divide(new BigDecimal(entity.getShortSignCount2()),2);
			}
			entity.setShortAveragePrice2(shortAveragePrice2);
			
			BigDecimal totalAveragerPrivce = new BigDecimal(0);
			if(entity.getSignTotal().intValue()!=0) {
				totalAveragerPrivce = entity.getActualPriceTotal().divide(new BigDecimal(entity.getSignTotal()),2);
			}
			entity.setPriceAverageTotal(totalAveragerPrivce);
			
			reportBoEntityMap.put(key, entity);
		}
	}
	
	/**
	 * 根据记录时间删除商机数据
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delReportBoDataByRecordDate(String recordDate) {
		return this.reportBoService.delByRecordDate(recordDate);
	}
	
	
	/**
	 * 带看次数
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getSeeCountForEmpReport(String startDate,String endDate,String projectId) {
			return this.reportBoService.getSeeBusinessCount(startDate, endDate, projectId);
	}
	
}
