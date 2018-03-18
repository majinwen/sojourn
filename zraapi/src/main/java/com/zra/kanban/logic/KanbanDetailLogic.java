package com.zra.kanban.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.house.ProjectDto;
import com.zra.common.dto.kanban.KanbanQueryDto;
import com.zra.common.dto.kanban.SecondDataDetailDto;
import com.zra.common.dto.kanban.SecondDataShowDto;
import com.zra.common.enums.CycleTypeEnum;
import com.zra.common.utils.DateTool;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.SysConstant;
import com.zra.house.logic.ProjectLogic;
import com.zra.kanban.entity.KanbanDetail;
import com.zra.kanban.entity.dto.KanBanCountDto;
import com.zra.kanban.entity.dto.PaymentInfoDto;
import com.zra.kanban.service.KanbanDetailService;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.logic.ProjectZOLogic;

/**
 * 目标看板详细数据
 * 
 * @author tianxf9
 *
 */
@Component
public class KanbanDetailLogic {

	@Autowired
	private ProjectLogic projectLogic;

	@Autowired
	private ProjectZOLogic projectZOLogic;

	@Autowired
	private KanbanDetailService detailService;

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(KanbanDetailLogic.class);

	public int saveKanbanDetail(Date startDate, Date endDate, int cycleType) {

		Map<String, KanbanDetail> kanbanDetailMap = new HashMap<String, KanbanDetail>();
		// 获取所有项目
		List<ProjectDto> projectList = projectLogic.getAllProjectList();
		for (ProjectDto projectDto : projectList) {
			String projectId = projectDto.getId();
			List<ProjectZODto> projectZODtos = projectZOLogic.getProjectZOsByProId(projectId);
			for (ProjectZODto projectZODto : projectZODtos) {
				KanbanDetail kanbanDetail = new KanbanDetail(KeyGenUtils.genKey(), projectId,
						projectZODto.getProjectZOId(), projectZODto.getProjectZOName(), SysConstant.ADMINID, new Date(),
						startDate, endDate, (byte) cycleType, (byte) 0);
				kanbanDetailMap.put(projectId + "_" + projectZODto.getProjectZOId(), kanbanDetail);
			}

			// 每个项目中添加一个zoId为All的数据来记录项目所有
			KanbanDetail kanbanDetail = new KanbanDetail(KeyGenUtils.genKey(), projectId, "ALL", "ALL",
					SysConstant.ADMINID, new Date(), startDate, endDate, (byte) cycleType, (byte) 0);
			kanbanDetailMap.put(projectId + "_ALL", kanbanDetail);
		}

		// 设置约看平均处理时长
		setYKhandTimeAvge(kanbanDetailMap, startDate, endDate, cycleType);
		// 设置退租量
		setQuitCount(kanbanDetailMap, startDate, endDate, cycleType);
		// 设置续约量
		setRenewCount(kanbanDetailMap, startDate, endDate, cycleType);
		// 设置新签量
		setNewSignCount(kanbanDetailMap, startDate, endDate, cycleType);
		// 设置空置房源数量
		setEmptyCount(kanbanDetailMap, startDate, endDate, cycleType);
		// 设置平均提前回款天数
		setEarlyPaymentAvgdays(kanbanDetailMap, startDate, endDate, cycleType);
		// 保存数据
		List<KanbanDetail> detailEntitys = new ArrayList<KanbanDetail>();
		for (String key : kanbanDetailMap.keySet()) {
			detailEntitys.add(kanbanDetailMap.get(key));
		}

		return this.detailService.saveEntitys(detailEntitys);
	}

	/**
	 * 设置约看平均处理时长
	 * 
	 * @author tianxf9
	 * @param kanbanDetailMap
	 * @param startDate
	 * @param endDate
	 * @param cycleType
	 */
	public void setYKhandTimeAvge(Map<String, KanbanDetail> kanbanDetailMap, Date startDate, Date endDate,
			int cycleType) {

		String startDateStr = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
		String endDateStr = DateUtil.DateToStr(endDate, DateUtil.DATE_FORMAT);
		startDateStr = startDateStr + " 00:00:00";
		endDateStr = endDateStr + " 23:59:59";
		
		//获取项目的约看平均处理时长
		List<KanBanCountDto> avgHandTimePro = this.detailService.getAvgHandTime(startDateStr, endDateStr, "N");
		//获取管家的约看平均处理时长
		List<KanBanCountDto> avgHandTimeZo = this.detailService.getAvgHandTime(startDateStr, endDateStr, "Y");
		for (KanBanCountDto zoAvgHandTime : avgHandTimeZo) {
			
			String key = "";
            if(zoAvgHandTime.getProjectId()!=null&&zoAvgHandTime.getZoId()!=null&&!zoAvgHandTime.getZoId().equals("")) { 
            	key = zoAvgHandTime.getProjectId()+"_"+zoAvgHandTime.getZoId();
            }else {
            	continue;
            }
            
			if (kanbanDetailMap.get(key) != null) {
				//设置管家商机约看平均处理时长
				KanbanDetail detailTemp = kanbanDetailMap.get(key);
				detailTemp.setYkDealLong(zoAvgHandTime.getSumLong());
				detailTemp.setBoNewCount(zoAvgHandTime.getCount());
				BigDecimal avgLong = zoAvgHandTime.getSumLong().divide(new BigDecimal(zoAvgHandTime.getCount()), 2, BigDecimal.ROUND_HALF_UP);
			    detailTemp.setYkDealAvglong(avgLong);
			} else {
				// 如果现在的项目和管家没有也要存储进去，可能是离职管家的，或者管家为空的
				KanbanDetail kanbanDetailTemp = new KanbanDetail(KeyGenUtils.genKey(),
						zoAvgHandTime.getProjectId(),zoAvgHandTime.getZoId(),
						zoAvgHandTime.getZoName(), SysConstant.ADMINID, new Date(), startDate, endDate,
						(byte) cycleType, (byte) 0);
				// 设置商机平均约看时间相关信息
				kanbanDetailTemp.setBoNewCount(zoAvgHandTime.getCount());
				kanbanDetailTemp.setYkDealLong(zoAvgHandTime.getSumLong());
				BigDecimal avgLong = zoAvgHandTime.getSumLong().divide(new BigDecimal(zoAvgHandTime.getCount()), 2, BigDecimal.ROUND_HALF_UP);
				kanbanDetailTemp.setYkDealAvglong(avgLong);
				kanbanDetailMap.put(key, kanbanDetailTemp);
			}

		}

		Map<String,KanBanCountDto> avgHandTiemProMap = new HashMap<String,KanBanCountDto>();
		for(KanBanCountDto tempDto:avgHandTimePro) {
			avgHandTiemProMap.put(tempDto.getProjectId(), tempDto);
		}
		// 设置项目的商机平均处理时长的相关信息
		for (String key : kanbanDetailMap.keySet()) {
			KanbanDetail detail = kanbanDetailMap.get(key);
			if (detail.getZoId() != null && detail.getZoId().equals("ALL")) {
				if(avgHandTiemProMap.get(detail.getProjectId())!=null && avgHandTiemProMap.get(detail.getProjectId()).getCount()!=0) {
					detail.setBoNewCount(avgHandTiemProMap.get(detail.getProjectId()).getCount());
					detail.setYkDealLong(avgHandTiemProMap.get(detail.getProjectId()).getSumLong());
					BigDecimal avgLong = avgHandTiemProMap.get(detail.getProjectId()).getSumLong().divide(new BigDecimal(avgHandTiemProMap.get(detail.getProjectId()).getCount()), 2, BigDecimal.ROUND_HALF_UP);
					detail.setYkDealAvglong(avgLong);
				}
			}
		}

	}

	/**
	 * 设置退租量
	 * 
	 * @author tianxf9
	 * @param kanbanDetailMap
	 * @param startDate
	 * @param endDate
	 * @param cycleType
	 */
	public void setQuitCount(Map<String, KanbanDetail> kanbanDetailMap, Date startDate, Date endDate, int cycleType) {

		String startDateStr = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
		String endDateStr = DateUtil.DateToStr(endDate, DateUtil.DATE_FORMAT);
		// 得到每个管家退租量
		List<KanBanCountDto> quitCountZo = this.detailService.getQuitCountService(startDateStr, endDateStr,"Y");

		// 给没有管家设置退租量
		for (KanBanCountDto kanbanCount : quitCountZo) {
			String keyMap = "";
			if (kanbanCount.getProjectId() != null&&kanbanCount.getZoId() != null) {
				keyMap = kanbanCount.getProjectId() + "_"+kanbanCount.getZoId();
			} else {
				continue;
			}

			if (kanbanDetailMap.get(keyMap) != null) {
				KanbanDetail temp = kanbanDetailMap.get(keyMap);
				temp.setQuitCount(kanbanCount.getCount());
			} else {
				// 如果现在的项目和管家没有也要存储进去，可能是离职管家的，或者管家为空的
				KanbanDetail kanbanDetailTemp = new KanbanDetail(KeyGenUtils.genKey(), kanbanCount.getProjectId(),
						kanbanCount.getZoId(), kanbanCount.getZoName(), SysConstant.ADMINID, new Date(), startDate,
						endDate, (byte) cycleType, (byte) 0);
				// 设置
				kanbanDetailTemp.setQuitCount(kanbanCount.getCount());
				kanbanDetailMap.put(keyMap, kanbanDetailTemp);
			}

		}

		// 设置项目的退租量
		// 获取项目退租量
		// 得到每个管家退租量
		List<KanBanCountDto> quitCountPro = this.detailService.getQuitCountService(startDateStr, endDateStr,"N");
		// 退租量map projectID为Key,项目对应退租量为value
		Map<String, Integer> quitCountProMap = new HashMap<String, Integer>();
		for (KanBanCountDto countDto : quitCountPro) {
			quitCountProMap.put(countDto.getProjectId(), countDto.getCount());
		}

		// 设置项目退租量
		for (String key : kanbanDetailMap.keySet()) {
			KanbanDetail temp = kanbanDetailMap.get(key);
			if (temp.getZoId() != null && temp.getZoId() == "ALL" && quitCountProMap.get(temp.getProjectId())!=null) {
				temp.setQuitCount(quitCountProMap.get(temp.getProjectId()));
			}
		}
	}

	/**
	 * 设置续约量
	 * 
	 * @author tianxf9
	 * @param kanbanDetailMap
	 * @param startDate
	 * @param endDate
	 * @param cycleType
	 */
	public void setRenewCount(Map<String, KanbanDetail> kanbanDetailMap, Date startDate, Date endDate, int cycleType) {

		String startDateStr = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
		String endDateStr = DateUtil.DateToStr(endDate, DateUtil.DATE_FORMAT);
		// 获取管家续约量
		List<KanBanCountDto> renewCountZo = this.detailService.getRenewCountService(startDateStr, endDateStr,"Y");
		// 获取项目续约量
		List<KanBanCountDto> renewCountPro = this.detailService.getRenewCountService(startDateStr, endDateStr,"N");
		
		// 存放每个项目的续约量key=projectId
		Map<String, Integer> projectRenewCountMap = new HashMap<String, Integer>();
		for(KanBanCountDto countDto:renewCountPro) {
			projectRenewCountMap.put(countDto.getProjectId(), countDto.getCount());
		}

		//设置管家续约量
		for (KanBanCountDto countDto : renewCountZo) {
			
			String keyMap = "";
			if (countDto.getProjectId() != null) {
				keyMap = countDto.getProjectId() + "_" +countDto.getZoId();
			} else {
				continue;
			}
			
			if (kanbanDetailMap.get(keyMap) != null) {
				
				KanbanDetail tempDto = kanbanDetailMap.get(keyMap);
				tempDto.setRenewCount(countDto.getCount());
			} else {
				// 如果现在的项目和管家没有也要存储进去，可能是离职管家的，或者管家为空的
				KanbanDetail kanbanDetailTemp = new KanbanDetail(KeyGenUtils.genKey(), countDto.getProjectId(), countDto.getZoId(), countDto.getZoName(),
						SysConstant.ADMINID, new Date(), startDate, endDate, (byte) cycleType, (byte) 0);
				// 设置续约量
				kanbanDetailTemp.setRenewCount(countDto.getCount());
				kanbanDetailMap.put(keyMap, kanbanDetailTemp);
			}

		}

		// 设置项目的续约量
		for (String key : kanbanDetailMap.keySet()) {
			KanbanDetail temp = kanbanDetailMap.get(key);
			if (temp.getZoId() != null && temp.getZoId().equals("ALL") &&projectRenewCountMap.get(temp.getProjectId())!=null) {
				temp.setRenewCount(projectRenewCountMap.get(temp.getProjectId()));
			}
		}
	}

	/**
	 * 设置新签量
	 * 
	 * @author tianxf9
	 * @param kanbanDetailMap
	 * @param startDate
	 * @param endDate
	 * @param cycleType
	 */
	public void setNewSignCount(Map<String, KanbanDetail> kanbanDetailMap, Date startDate, Date endDate,
			int cycleType) {

		String startDateStr = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
		String endDateStr = DateUtil.DateToStr(endDate, DateUtil.DATE_FORMAT);
		// 查询每个管家的新签量
		List<KanBanCountDto> newSignCountZo = this.detailService.getNewSignCountService(startDateStr, endDateStr,"Y");
		//查询项目的新签量
		List<KanBanCountDto> newSignCountPro = this.detailService.getNewSignCountService(startDateStr, endDateStr,"N");

		// 存放项目的新签量 key = projectId,value = 新签量
		Map<String, Integer> projectNewSignCountMap = new HashMap<String, Integer>();
		for(KanBanCountDto newSignCount:newSignCountPro) {
			projectNewSignCountMap.put(newSignCount.getProjectId(), newSignCount.getCount());
		}
		
		for (KanBanCountDto newSignCount : newSignCountZo) {
			String keyMap = "";
			if (newSignCount.getProjectId() != null && newSignCount.getZoId() != null) {
				keyMap = newSignCount.getProjectId() + "_" +newSignCount.getZoId();
			} else {
				continue;
			}
			
			// 设置每个管家新签量
			if(kanbanDetailMap.get(keyMap)!=null) {
				kanbanDetailMap.get(keyMap).setSignNewCount(newSignCount.getCount());	
			}else {
				KanbanDetail detail = new KanbanDetail(KeyGenUtils.genKey(), newSignCount.getProjectId(), newSignCount.getZoId(), newSignCount.getZoName(),
						SysConstant.ADMINID, new Date(), startDate, endDate, (byte) cycleType, (byte) 0);
				//设置新签
				detail.setSignNewCount(newSignCount.getCount());
				kanbanDetailMap.put(keyMap, detail);
			}
		}
		
		// 设置项目的新签量
		for (String key : kanbanDetailMap.keySet()) {
			KanbanDetail temp = kanbanDetailMap.get(key);
			if (temp.getZoId() != null && temp.getZoId().equals("ALL")&&projectNewSignCountMap.get(temp.getProjectId())!=null) {
				temp.setSignNewCount(projectNewSignCountMap.get(temp.getProjectId()));
			}
		}

	}

	/**
	 * 设置空置房源数量，空置房源值统计到项目维度
	 * 
	 * @author tianxf9
	 * @param kanbanDetailMap
	 * @param startDate
	 * @param endDate
	 * @param cycleType
	 */
	public void setEmptyCount(Map<String, KanbanDetail> kanbanDetailMap, Date startDate, Date endDate, int cycleType) {

		String endDateStr = DateUtil.DateToStr(endDate, DateUtil.DATE_FORMAT);
		endDateStr = endDateStr + " 00:00:00";
		// 查询0~5天空置房源
		List<KanBanCountDto> empNumCount1 = this.detailService.getEmptyCountService(endDateStr, 0, 5);
		// 用来存空置房源 key=projectId,value=0~5天空置房源
		Map<String, Integer> empNumCountMap1 = new HashMap<String, Integer>();
		for (KanBanCountDto empNum : empNumCount1) {
			empNumCountMap1.put(empNum.getProjectId(), empNum.getCount());
		}

		// 查询6~10天空置房源
		List<KanBanCountDto> empNumCount2 = this.detailService.getEmptyCountService(endDateStr, 6, 10);
		// 用来存空置房源 key=projectId,value=6~10天空置房源
		Map<String, Integer> empNumCountMap2 = new HashMap<String, Integer>();
		for (KanBanCountDto empNum : empNumCount2) {
			empNumCountMap2.put(empNum.getProjectId(), empNum.getCount());
		}

		// 查询11~15天空置房源
		List<KanBanCountDto> empNumCount3 = this.detailService.getEmptyCountService(endDateStr, 11, 15);
		// 用来存空置房源 key=projectId,value=11~15天空置房源
		Map<String, Integer> empNumCountMap3 = new HashMap<String, Integer>();
		for (KanBanCountDto empNum : empNumCount3) {
			empNumCountMap3.put(empNum.getProjectId(), empNum.getCount());
		}

		// 查询15天以上空置房源
		List<KanBanCountDto> empNumCount4 = this.detailService.getEmptyCountService(endDateStr, 16, -1);
		// 用来存空置房源 key=projectId,value=15天以上空置房源
		Map<String, Integer> empNumCountMap4 = new HashMap<String, Integer>();
		for (KanBanCountDto empNum : empNumCount4) {
			empNumCountMap4.put(empNum.getProjectId(), empNum.getCount());
		}

		// 设置空置房源
		for (String key : kanbanDetailMap.keySet()) {
			KanbanDetail temp = kanbanDetailMap.get(key);
			if (temp.getZoId() != null && temp.getZoId().equals("ALL")) {
				// 设置0~5天空置房源
				if(empNumCountMap1.get(temp.getProjectId())!=null) {
					temp.setEmptyRoomRange1(empNumCountMap1.get(temp.getProjectId()));
				}
				// 设置6~10天空置房源
				if(empNumCountMap2.get(temp.getProjectId())!=null) {
					temp.setEmptyRoomRange2(empNumCountMap2.get(temp.getProjectId()));
				}
				// 设置11~15天空置房源
				if(empNumCountMap3.get(temp.getProjectId())!=null) {
					temp.setEmptyRoomRange3(empNumCountMap3.get(temp.getProjectId()));
				}
				// 15天以上空置房源
				if(empNumCountMap4.get(temp.getProjectId())!=null) {
					temp.setEmptyRoomRange4(empNumCountMap4.get(temp.getProjectId()));
				}
			}
		}

	}

	public void setEarlyPaymentAvgdays(Map<String, KanbanDetail> kanbanDetailMap, Date startDate, Date endDate,
			int cycleType) {
		
			String startDateStr = DateUtil.DateToStr(startDate, DateUtil.DATE_FORMAT);
			String endDateStr = DateUtil.DateToStr(endDate, DateUtil.DATE_FORMAT);
			// 查询时间段内每个管家的每个应收账单回款天数信息
			List<PaymentInfoDto> paymentInfoZos = this.detailService.getPaymentAvgDaysService(startDateStr, endDateStr,"Y");
			// 设置每个管家的应收账单数量和回款天数
			for (PaymentInfoDto paymentInfoZo : paymentInfoZos) {
                
				String key = "";
				if(paymentInfoZo.getZoId()!=null&&paymentInfoZo.getZoName()!=null) {
					key = paymentInfoZo.getProjectId()+"_"+paymentInfoZo.getZoId();
				}else {
					continue;
				}
				
				if (kanbanDetailMap.get(key) != null) {
					KanbanDetail detailTemp = kanbanDetailMap.get(key);
					detailTemp.setPaymentCount(paymentInfoZo.getCount());
					detailTemp.setEarlyTotalDays(paymentInfoZo.getSubDays());
					if (paymentInfoZo.getCount() != 0) {
						BigDecimal avgDays = new BigDecimal(paymentInfoZo.getSubDays()).divide(
								new BigDecimal(paymentInfoZo.getCount()), 2, BigDecimal.ROUND_HALF_UP);
						detailTemp.setEarlyPaymentAvgdays(avgDays);
					}
					
				} else {
					// 如果现在的项目和管家没有也要存储进去，可能是离职管家的，或者管家为空的
					KanbanDetail kanbanDetailTemp = new KanbanDetail(KeyGenUtils.genKey(), paymentInfoZo.getProjectId(),
							paymentInfoZo.getZoId(), paymentInfoZo.getZoName(), SysConstant.ADMINID, new Date(), startDate, endDate,
							(byte) cycleType, (byte) 0);
					// 设置应收账单数量和回款天数
					kanbanDetailTemp.setPaymentCount(paymentInfoZo.getCount());
					kanbanDetailTemp.setEarlyTotalDays(paymentInfoZo.getSubDays());
					if (paymentInfoZo.getCount() != 0) {
						BigDecimal avgDays = new BigDecimal(paymentInfoZo.getSubDays()).divide(
								new BigDecimal(paymentInfoZo.getCount()), 2, BigDecimal.ROUND_HALF_UP);
						kanbanDetailTemp.setEarlyPaymentAvgdays(avgDays);
					}

					kanbanDetailMap.put(key, kanbanDetailTemp);
				}
			}
         
            //查询每个项目的应收账单数据和提前回款天数
			List<PaymentInfoDto> paymentInfoPros = this.detailService.getPaymentAvgDaysService(startDateStr, endDateStr,"N");
			Map<String,PaymentInfoDto> paymentInfoMapPro = new HashMap<String,PaymentInfoDto>();
			for(PaymentInfoDto dto:paymentInfoPros) {
				paymentInfoMapPro.put(dto.getProjectId(), dto);
			}
			// 设置每个项目的应收回款单量和提前回款天数
			for (String key : kanbanDetailMap.keySet()) {
				KanbanDetail tempDetail = kanbanDetailMap.get(key);
				if (tempDetail.getZoId() != null && tempDetail.getZoId().equals("ALL")) {

					PaymentInfoDto projectCountDto = paymentInfoMapPro.get(tempDetail.getProjectId());
					if (projectCountDto != null) {
						tempDetail.setPaymentCount(projectCountDto.getCount());
						tempDetail.setEarlyTotalDays(projectCountDto.getSubDays());
						if (projectCountDto.getCount() != null
								&& projectCountDto.getCount().intValue() != 0) {
							BigDecimal avgDays = new BigDecimal(projectCountDto.getSubDays()).divide(
									new BigDecimal(projectCountDto.getCount()), 2,
									BigDecimal.ROUND_HALF_UP);
							tempDetail.setEarlyPaymentAvgdays(avgDays);
						}
						kanbanDetailMap.put(key, tempDetail);
					}
					}
				}
	}

	/**
	 * @author tianxf9
	 * @author 自动任务逻辑
	 * @param currentTime
	 */
	public int taskCreateDetail(Date currentTime) {

		int rows = 0;
		try {
			for (int type = 1; type <= 4; type++) {
				Date startDate = DateTool.getStartDate(currentTime, type);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String startDateStr = sdf.format(startDate);
				String endDateStr = sdf.format(currentTime);
				LOGGER.info("======删除startDate = " + startDateStr + "的" + CycleTypeEnum.getByCode(type)
						+ "(二级数据)=======================");
				rows = this.detailService.updateByConditions(startDateStr, type);
				LOGGER.info("======生成startDate = " + startDateStr + ";endDate=" + endDateStr + "的"
						+ CycleTypeEnum.getByCode(type) + "(二级数据)=======================");
				this.saveKanbanDetail(startDate, currentTime, type);
			}
		} catch (ParseException e) {
			LOGGER.error("保存目标看板二级数据明细出错", e);
		}
		
		return rows;
	}
	
	
	/**
	 * 查询目标看板二级数据
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	public List<SecondDataShowDto> getSecondData(KanbanQueryDto queryDto) {
		//根据当前登录人和登录城市查询项目
		if(queryDto.getProjectId()==null) {
			List<ProjectDto> projectList = this.projectLogic.getProjectListByUser(queryDto.getUserId(), queryDto.getCityId());
			List<String> projectIdList = new ArrayList<String>();
		    for(ProjectDto projectDto:projectList) {
		    	projectIdList.add(projectDto.getId());
		    }
			queryDto.setProjectId(projectIdList);
		}
		return this.detailService.getSecondDataService(queryDto);
	}
	
	/**
	 * 查询目标看板二级数据详细信息
	 * @author tianxf9
	 * @param queryDto
	 * @return
	 */
	public List<SecondDataDetailDto> getSeconDataDetail(KanbanQueryDto queryDto) {
		return this.detailService.getSecondDataDetailService(queryDto);
	}

}
