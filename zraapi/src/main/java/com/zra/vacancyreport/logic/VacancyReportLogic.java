package com.zra.vacancyreport.logic;

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

import com.zra.common.constant.RoomStateEnum;
import com.zra.common.constant.SurTypeEnum;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.SysConstant;
import com.zra.house.entity.RoomInfoEntity;
import com.zra.house.logic.RoomInfoLogic;
import com.zra.rentcontract.dto.RentContractDto;
import com.zra.rentcontract.logic.RentContractLogic;
import com.zra.report.entity.dto.BaseReportDto;
import com.zra.report.logic.ReportBoLogic;
import com.zra.vacancyreport.entity.VacancyReportEntity;
import com.zra.vacancyreport.entity.dto.RoomInfoDto;
import com.zra.vacancyreport.service.VacancyReportService;

/**
 * 空置报表逻辑层
 * 
 * @author tianxf9
 *
 */

@Component
public class VacancyReportLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(VacancyReportLogic.class);

	@Autowired
	private VacancyReportService reportService;

	@Autowired
	private RentContractLogic contractLogic;

	@Autowired
	private RoomInfoLogic roomLogic;

	@Autowired
	private ReportBoLogic boReportLogic;

	/**
	 * 获取今天空置报表数据
	 * 
	 * @author tianxf9
	 * @param tdateStr
	 * @return
	 */
	public Map<String, Object> getVacancyReportEntitys(String tdateStr, String projectId, boolean isSave) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 查询房间信息
		String yesterday = null;
		Date tday = null;
		try {
			yesterday = DateUtil.getYesterDay(tdateStr);
			tday = DateUtil.castString2Date(tdateStr, DateUtil.DATE_FORMAT);
		} catch (ParseException e) {
			LOGGER.error("处理日期出错", e);
			resultMap.put("status", "fail");
			resultMap.put("result_message", "处理日期出错");
			resultMap.put("data", null);
			return resultMap;
		}

		// 获取7天内带看次数
		Map<String, Integer> seeCountMap = this.getSeeCount(tday, projectId);
		// 获取此刻该项目下所有的房间
		List<RoomInfoEntity> roomEntitys = this.roomLogic.getRoomEntitys(projectId);
		//
		List<VacancyReportEntity> reportEntitys = new ArrayList<VacancyReportEntity>();
		for (RoomInfoEntity roomEntity : roomEntitys) {
			RoomInfoDto roomEmpInfoDto = this.reportService.getRoomInfoService(yesterday, projectId,
					roomEntity.getId());
			if (roomEmpInfoDto != null) {
				// 昨天有数据
				VacancyReportEntity reportEntity = getEmptyNum(tday, roomEmpInfoDto, seeCountMap);
				reportEntitys.add(reportEntity);
			} else {
				// 昨天没有数据
				if (this.reportService.isInit(projectId, roomEntity.getId())) {
					// 初始化
					VacancyReportEntity reportEntity = initReportEntitysData(tday, projectId, seeCountMap, roomEntity);
					reportEntitys.add(reportEntity);
				} else {
					// 非初始化，查询不出结果，为了早一点发现自动任务没有执行成功
					LOGGER.info("========项目：" + projectId + ";房间:" + roomEntity.getId() + ";日期：" + tdateStr
							+ "====空置报表历史数据保存失败！原因：" + yesterday + "数据为空！请先初始化" + yesterday + "日的数据");
					if (!isSave) {
						resultMap.put("status", "fail");
						resultMap.put("result_message", "获取项目：" + projectId + ";房间：" + roomEntity.getId() + ";"
								+ tdateStr + "日的空置报表失败！原因：" + yesterday + "数据为空！请先初始化" + yesterday + "日的数据");
						resultMap.put("data", null);
						return resultMap;
					}
				}
			}

		}

		int rows = 0;
		if (isSave) {
			rows = rows + this.reportService.saveReportEntitys(reportEntitys);
		} else {
			resultMap.put("status", "success");
			resultMap.put("result_message", "");
			resultMap.put("data", reportEntitys);
			return resultMap;
		}

		if (rows > 0) {
			resultMap.put("status", "success");
			resultMap.put("result_message", "项目：" + projectId + ";" + tdateStr + "日：空置报表保存成功！");
			resultMap.put("data", rows);
		} else {
			resultMap.put("status", "fail");
			resultMap.put("result_message", "项目：" + projectId + ";" + tdateStr + "日：空置报表保存失败！");
			resultMap.put("data", rows);
		}
		return resultMap;

	}

	public Map<String, Object> getHistoryVacancyReport(String projectId, String dateStr) {

		Map<String, Object> resultMap = new HashMap<>();

		List<VacancyReportEntity> reportEntitys = this.reportService.getReportEntityByProjectId(dateStr, projectId);

		if (null == reportEntitys || reportEntitys.isEmpty()) {
			resultMap.put("status", "fail");
			resultMap.put("result_message", "获取" + dateStr + "报表信息失败，请重新获取或初始化数据");
			resultMap.put("data", null);
		} else {
			try {
				Date date = DateUtil.castString2Date(dateStr, DateUtil.DATE_FORMAT);

				resultMap.put("status", "success");
				resultMap.put("result_message", "");
				resultMap.put("data", reportEntitys);
			} catch (Exception e) {
				LOGGER.error("处理日期出错", e);
				resultMap.put("status", "fail");
				resultMap.put("result_message", "处理日期出错");
				resultMap.put("data", null);
				return resultMap;
			}
		}
		return resultMap;
	}

	/**
	 * 初始化
	 * 
	 * @author tianxf9
	 * @param tday
	 * @param projectId
	 * @return
	 */
	public VacancyReportEntity initReportEntitysData(Date tday, String projectId, Map<String, Integer> seeCountMap,
			RoomInfoEntity roomEntity) {

		VacancyReportEntity reportEntity = new VacancyReportEntity();
		reportEntity.setReportEmptyId(KeyGenUtils.genKey());
		reportEntity.setRoomId(roomEntity.getId());
		reportEntity.setRoomNum(roomEntity.getRoomNumber());
		reportEntity.setProjectId(roomEntity.getProjectId());
		reportEntity.setHouseTypeId(roomEntity.getHouseTypeId());
		reportEntity.setRentType(roomEntity.getRentType());
		reportEntity.setRoomState(roomEntity.getCurrentState());
		reportEntity.setRecordDate(tday);
		reportEntity.setCreaterId(SysConstant.ADMINID);
		reportEntity.setCreateTime(new Date());
		reportEntity.setIsDel(0);
		String currentState = roomEntity.getCurrentState();
		String roomId = roomEntity.getId();
		if (seeCountMap != null && seeCountMap.size() > 0) {
			reportEntity.setSeeNum(seeCountMap.get(roomEntity.getHouseTypeId()));
		}
		// 配置中/待租中/可预订并且不存在有效合同
		if (currentState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
				|| currentState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode())) ||((currentState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&!isExistRentContract(roomId,tday)))) {
			reportEntity.setEmptyNum(initWORCEmptyNum(roomEntity.getId(), tday));
			reportEntity.setIsEmpty(0);
			// 已出租/可预订并且存在有效合同
		} else if (currentState.equals(String.valueOf(RoomStateEnum.RENTAL.getCode())) ||((currentState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&isExistRentContract(roomId,tday)))) {
			int empNum = ininRentalEmptyNum(roomEntity.getId(), tday);
			if (empNum >= 0) {
				reportEntity.setIsEmpty(0);
				reportEntity.setEmptyNum(empNum);
			} else {
				reportEntity.setIsEmpty(1);
				reportEntity.setEmptyNum(empNum);
			}
			// 非（待租中/配置中/已出租）
		} else {
			reportEntity.setIsEmpty(1);
			reportEntity.setEmptyNum(-1);
		}

		return reportEntity;
	}

	/**
	 * 待租中/配置中初始化控制天数
	 * 
	 * @author tianxf9
	 * @param roomId
	 * @return
	 */
	public int initWORCEmptyNum(String roomId, Date tday) {
		// 联查最近一次解约类型为非三天不满意的解约合同
		RentContractDto contractDto = this.contractLogic.getSurContractInfoByRoomId(roomId, "N");
		int time = 0;
		Date oldDay = null;
		try {
			if (contractDto == null) {
				RoomInfoEntity roomEntity = this.roomLogic.getRoomEntity(roomId);
				oldDay = roomEntity.getCreateTime();
				time = DateUtil.daysBetween(tday, oldDay);

				return time + 1;
			} else {
				if (contractDto.getSurType().equals(SurTypeEnum.NORMAL.getCode())) {
					// 合同未到期
					if (tday.compareTo(contractDto.getConEndDate()) <= 0) {
						return 0;
					} else {
						// 合同到期-比较解约日期和合同日期
						if (contractDto.getConEndDate().compareTo(contractDto.getReleaseDate()) <= 0) {
							oldDay = contractDto.getReleaseDate();
						} else {
							oldDay = contractDto.getConEndDate();
						}
					}

					if (contractDto.getConEndDate().compareTo(contractDto.getReleaseDate()) >= 0) {
						oldDay = DateUtil.getNextDay(contractDto.getConEndDate());
					} else {
						oldDay = DateUtil.getNextDay(contractDto.getReleaseDate());
					}

				} else {
					oldDay = DateUtil.getNextDay(contractDto.getReleaseDate());
				}

				time = DateUtil.daysBetween(tday, oldDay);
				if (time < 0) {
					return 0;
				} else {
					return time + 1;
				}
			}
		} catch (ParseException e) {
			LOGGER.error("初始化房间roomId=" + roomId + "空置天数出错！", e);
		}
		return -1;
	}

	/**
	 * 初始化已出租房间的空置天数
	 * 
	 * @author tianxf9
	 * @param roomId
	 * @param tday
	 * @return
	 */
	public int ininRentalEmptyNum(String roomId, Date tday) {
		// 根据roomId和rentDate时间获取该房间在该时间的所有已签约的合同和在该时间没有到期的已续约的合同
		String rentDateStr = DateUtil.DateToStr(tday, DateUtil.DATE_FORMAT);
		RentContractDto contractDtos = this.contractLogic.getContractInfo(roomId, rentDateStr);
		if (contractDtos != null) {
			Date conStartDate = contractDtos.getConStartDate();
			if (conStartDate.compareTo(tday) <= 0) {
				return -1;
			} else {
				return initWORCEmptyNum(roomId,tday);
			}
		} else {
			// 房间状态为已出租并且没有合同的情况将其计算到已出租中（有走线下的合同）
			return -1;
		}

	}

	/**
	 * 计算今天库存信息,昨天信息不为空的情况下
	 * 
	 * @author tianxf9
	 * @param tday
	 * @return
	 */
	public VacancyReportEntity getEmptyNum(Date tday, RoomInfoDto roomInfoDto, Map<String, Integer> seeCountMap) {

		VacancyReportEntity reportEntity = new VacancyReportEntity();
		String roomState = roomInfoDto.getRoomState();
		String yRoomState = roomInfoDto.getyRoomState();
		reportEntity.setReportEmptyId(KeyGenUtils.genKey());
		reportEntity.setRoomId(roomInfoDto.getRoomId());
		reportEntity.setRoomNum(roomInfoDto.getRoomNum());
		reportEntity.setHouseTypeId(roomInfoDto.getHouseTypeId());
		reportEntity.setProjectId(roomInfoDto.getProjectId());
		reportEntity.setRentType(roomInfoDto.getRentType());
		reportEntity.setRoomState(roomState);
		reportEntity.setRecordDate(tday);
		reportEntity.setCreaterId(SysConstant.ADMINID);
		reportEntity.setCreateTime(new Date());
		reportEntity.setIsDel(0);
		if (seeCountMap != null) {
			reportEntity.setSeeNum(seeCountMap.get(roomInfoDto.getHouseTypeId()));
		}

		// 昨天房间状态是待租中或者配置中或者可预订并且没有有效合同
		if (yRoomState != null && (yRoomState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
				|| yRoomState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode())) || (roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&!isExistRentContract(roomInfoDto.getRoomId(),tday)))) {
			// 今天房间状态待租中或者配置中
			if (roomState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
					|| roomState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode())) ||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&!isExistRentContract(roomInfoDto.getRoomId(),tday))) {
				if(roomInfoDto.getyEmptyNum()==0) {
					//昨天是待租中并且空置天数为0说明是提前解约的情况
					int empNum = getRentalTOWCEmptyNum(roomInfoDto.getRoomId(), tday);
					reportEntity.setEmptyNum(empNum);
				}else {
					reportEntity.setEmptyNum(roomInfoDto.getyEmptyNum() + 1);
				}
				reportEntity.setIsEmpty(0);
				// 今天房间已出租
			} else if (roomState.equals(String.valueOf(RoomStateEnum.RENTAL.getCode())) ||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&isExistRentContract(roomInfoDto.getRoomId(),tday))) {
				int empNum = getTORentalEmptyNum(roomInfoDto.getRoomId(), roomInfoDto.getyEmptyNum(), tday);
				if (empNum < 0) {
					reportEntity.setIsEmpty(1);
					reportEntity.setEmptyNum(empNum);
				} else {
					reportEntity.setIsEmpty(0);
					reportEntity.setEmptyNum(empNum);
				}

			}else {
				reportEntity.setIsEmpty(1);
				reportEntity.setEmptyNum(-1);
			}
			// 昨天房间状态是已出租
		} else if (yRoomState != null && (yRoomState.equals(String.valueOf(RoomStateEnum.RENTAL.getCode()))||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&isExistRentContract(roomInfoDto.getRoomId(),tday)))) {
			// 今天房间状态待租中或者配置中
			if (roomState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
					|| roomState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode())) ||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&!isExistRentContract(roomInfoDto.getRoomId(),tday))) {
				// 计算空置天数
				int empNum = getRentalTOWCEmptyNum(roomInfoDto.getRoomId(), tday);
				reportEntity.setIsEmpty(0);
				reportEntity.setEmptyNum(empNum);
				// 今天房间已出租
			} else if (roomState.equals(String.valueOf(RoomStateEnum.RENTAL.getCode())) ||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&isExistRentContract(roomInfoDto.getRoomId(),tday))) {
				int empNum = getRentTORentalEmptyNum(roomInfoDto.getRoomId(), roomInfoDto.getyEmptyNum(), tday);
				if (empNum < 0) {
					reportEntity.setIsEmpty(1);
					reportEntity.setEmptyNum(empNum);
				} else {
					reportEntity.setIsEmpty(0);
					reportEntity.setEmptyNum(empNum);
				}
				
			}else {
				reportEntity.setIsEmpty(1);
				reportEntity.setEmptyNum(-1);
			}
			// 昨天房间状态 非(已出租/待租中/配置中)
		} else {
			// 今天房间状态待租中或者配置中
			if (roomState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
					|| roomState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode())) ||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&!isExistRentContract(roomInfoDto.getRoomId(),tday))) {
				// 计算空置天数
				reportEntity.setIsEmpty(0);
				reportEntity.setEmptyNum(1);
			// 今天房间已出租 或 者房间状态为可预订并且存在有效合同
			} else if (roomState.equals(String.valueOf(RoomStateEnum.RENTAL.getCode())) ||(roomState.equals(String.valueOf(RoomStateEnum.BOOKABLE.getCode()))&&isExistRentContract(roomInfoDto.getRoomId(),tday))) {
				// 计算空置天数
				int empNum = getTORentalEmptyNum(roomInfoDto.getRoomId(), roomInfoDto.getyEmptyNum(), tday);
				if (empNum < 0) {
					reportEntity.setIsEmpty(1);
					reportEntity.setEmptyNum(empNum);
				} else {
					reportEntity.setIsEmpty(0);
					reportEntity.setEmptyNum(empNum);
				}
				
			}else {
				reportEntity.setIsEmpty(1);
				reportEntity.setEmptyNum(-1);
			}
		}

		return reportEntity;
	}

	/**
	 * 非已出租-》已出租计算空置天数
	 * 
	 * @author tianxf9
	 */
	public int getTORentalEmptyNum(String roomId, int yEmptyNum, Date tdate) {
		// 根据roomId和rentDate时间获取该房间在该时间的所有已签约的合同和在该时间没有到期的已续约的合同
		String rentDateStr = DateUtil.DateToStr(tdate, DateUtil.DATE_FORMAT);
		RentContractDto contractDtos = this.contractLogic.getContractInfo(roomId, rentDateStr);
		if (contractDtos != null) {
			Date conStartDate = contractDtos.getConStartDate();
			if (conStartDate.compareTo(tdate) <= 0) {
				return -1;
			} else {
				if (yEmptyNum >= 0) {
					// yEmptyNum 大于等于零代表昨天房间状态为配置中/待租中
					return yEmptyNum + 1;
				} else {
					// yEmptyNum 小于零表昨天房间状态为非（配置中/待租中）
					return 1;
				}
			}
		} else {
			// 房间状态为已出租并且没有合同的情况将其计算到已出租中（有走线下的合同）
			return -1;
		}
	}

	/**
	 * 已出租-》已出租
	 * 
	 * @param roomId
	 * @param tdate
	 * @return
	 */
	public int getRentTORentalEmptyNum(String roomId, int yEmptyNum, Date tdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String tdateStr = sdf.format(tdate);
		// 查询今天房间有没有解约合同
		List<RentContractDto> surContractDto = this.contractLogic.getCurrentSurContractInfo(roomId, tdateStr);
		// 查询今天房间签约的合同
		RentContractDto contractDto = this.contractLogic.getCurrentContractInfo(roomId, tdateStr);
		// 查询该房间对应的已签约合同和已续约且没有到期到期的合同中起租日期的最小值
		RentContractDto contractDto1 = this.contractLogic.getContractInfo(roomId, tdateStr);

		if (surContractDto == null || surContractDto.size() <= 0) {
			if (contractDto1 != null) {
				Date conStartDate = contractDto1.getConStartDate();
				if (conStartDate.compareTo(tdate) <= 0) {
					return -1;
				} else {
					return yEmptyNum + 1;
				}
			} else {
				// 没有对应的合同算作已出租
				return -1;
			}
		} else {
			// 三天不满意
			if (surContractDto.get(0).getSurType().equals(SurTypeEnum.THREE_DAYS_NOT_SATISFIED.getCode())) {
				int time = 0;
				Date conDate = DateUtil.getYesterDay(surContractDto.get(0).getConSignDate());// 合同签约日期的昨天
				String conDateStr = sdf.format(conDate);
				// 根据三天满意的签约日期获取昨天历史记录
				VacancyReportEntity reportEntity = this.reportService.getVacRptEntity(roomId, conDateStr);
				if (reportEntity != null) {
					String roomState = reportEntity.getRoomState();
					try {
						time = DateUtil.daysBetween(tdate, conDate);
					} catch (ParseException e) {
						LOGGER.error("日期处理出现错误，空置天数会有误差：recordDate：" + tdate + ";roomId:" + roomId, e);
					}
					// 配置中/待租中
					if (roomState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
							&& roomState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode()))) {
						return reportEntity.getEmptyNum() + time;
					} else {
						return time;
					}
				} else {
					RoomInfoEntity roomEntity = this.roomLogic.getRoomEntity(roomId);
					try {
						time = DateUtil.daysBetween(tdate, roomEntity.getCreateTime());
					} catch (ParseException e) {
						LOGGER.error("日期处理出现错误，空置天数会有误差：recordDate：" + tdate + ";roomId:" + roomId, e);
					}
					return time + 1;
				}
			} else {

				if (contractDto != null) {
					Date conStartDate = contractDto.getConStartDate();
					if (conStartDate.compareTo(tdate) <= 0) {
						return -1;
					} else {
						return 0;
					}
				} else {
					// 没有对应的合同算作已出租
					return -1;
				}

			}

		}

	}

	/**
	 * 已出租-》配置中/待租中 空置天数计算
	 * 
	 * @author tianxf9
	 * @return
	 */
	public int getRentalTOWCEmptyNum(String roomId, Date tday) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		RentContractDto contractDto = this.contractLogic.getSurContractInfoByRoomId(roomId, "Y");
		Date conDate = null;
		int time = 0;
		if (contractDto != null) {
			// 三天不满意
			if (contractDto.getSurType().equals(SurTypeEnum.THREE_DAYS_NOT_SATISFIED.getCode())) {
				conDate = DateUtil.getYesterDay(contractDto.getConSignDate());// 合同签约日期的昨天
				String conDateStr = sdf.format(conDate);
				// 根据日期获取昨天历史记录
				VacancyReportEntity reportEntity = this.reportService.getVacRptEntity(roomId, conDateStr);
				if (reportEntity != null) {
					String roomState = reportEntity.getRoomState();
					try {
						time = DateUtil.daysBetween(tday, conDate);
					} catch (ParseException e) {
						LOGGER.error("日期处理出现错误，空置天数会有误差：recordDate：" + tday + ";roomId:" + roomId, e);
					}
					// 配置中/待租中
					if (roomState.equals(String.valueOf(RoomStateEnum.WAITRENT.getCode()))
							|| roomState.equals(String.valueOf(RoomStateEnum.CONFIG.getCode()))) {
						return reportEntity.getEmptyNum() + time;
					} else {
						return time;
					}
				} else {
					RoomInfoEntity roomEntity = this.roomLogic.getRoomEntity(roomId);
					try {
						time = DateUtil.daysBetween(tday, roomEntity.getCreateTime());
					} catch (ParseException e) {
						LOGGER.error("日期处理出现错误，空置天数会有误差：recordDate：" + tday + ";roomId:" + roomId, e);
					}
					return time + 1;
				}
			} else {
				// 正常退
				if (contractDto.getSurType().equals(SurTypeEnum.NORMAL.getCode())) {
					return 0;
					// 单解，非正退，换租，转租
				} else {
					conDate = DateUtil.getNextDay(contractDto.getReleaseDate());// 解约日期第二天
				}

				try {
					time = DateUtil.daysBetween(tday, conDate);
				} catch (ParseException e) {
					LOGGER.error("日期处理出现错误，空置天数会有误差：recordDate：" + tday + ";roomId:" + roomId, e);
				}
				if (time >= 0) {
					return time + 1;
				} else {
					return 0;
				}

			}
		} else {
			// 目前看不存在这样的情况：房间状态由已出租变成待租中而且该房间没有对应的有效的解约协议，如果出现将空置天数设置为-1方便排查
			LOGGER.info("=======空置天数=-1：出现了房间状态由已出租变成待租中/配置中而且该房间没有有效的解约协议");
			return -1;
		}
	}

	/**
	 * 获取每个户型的7天内带看次数
	 * 
	 * @author tianxf9
	 * @param tday
	 * @return
	 */
	public Map<String, Integer> getSeeCount(Date tday, String projectId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(DateUtil.getProSevenDay(tday)) + " 00:00:00";
		String endDate = sdf.format(tday) + " 23:59:59";
		List<BaseReportDto> list = this.boReportLogic.getSeeCountForEmpReport(startDate, endDate, projectId);
		Map<String, Integer> seeCountMap = new HashMap<String, Integer>();
		for (BaseReportDto reportDto : list) {
			seeCountMap.put(reportDto.getHouseTypeId(), reportDto.getCount());
		}
		return seeCountMap;
	}

	/**
	 * 删除
	 * 
	 * @author tianxf9
	 * @param dateStr
	 * @return
	 */
	public int delReportEmptyByRecordDate(String dateStr, String projectId) {
		return this.reportService.delReportEntitysByDate(dateStr, projectId);
	}
	
	/**
	 * 判断是否存在有效合同
	 * @author tianxf9
	 * @param roomId
	 * @param recordDate
	 * @return
	 */
	public boolean isExistRentContract(String roomId,Date today) {
		
		String rentDateStr = DateUtil.DateToStr(today, DateUtil.DATE_FORMAT);
		RentContractDto contractDto = this.contractLogic.getContractInfo(roomId, rentDateStr);
		if(contractDto!=null&&contractDto.getConStartDate()!=null) {
			return true;
		}else {
			return false;
		}
	}

}
