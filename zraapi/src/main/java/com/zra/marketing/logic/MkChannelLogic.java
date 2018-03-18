package com.zra.marketing.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zra.common.dto.marketing.MkChannelDataDto;
import com.zra.common.dto.marketing.MkChannelDto;
import com.zra.common.dto.marketing.MkChannelLineDto;
import com.zra.common.dto.marketing.MkChannelQueryDto;
import com.zra.common.dto.marketing.MkLineChannelShowDto;
import com.zra.common.dto.marketing.MkNumberDto;
import com.zra.common.dto.marketing.PhonePreserveDto;
import com.zra.common.dto.marketing.SchedulePersonDto;
import com.zra.common.enums.MkChannelTypeEunm;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.utils.KeyGenUtils;
import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.house.logic.ProjectLogic;
import com.zra.marketing.entity.MkChannelEntity;
import com.zra.marketing.entity.MkNumberEntity;
import com.zra.marketing.entity.dto.MkChannelCountDto;
import com.zra.marketing.service.MkChannelService;
import com.zra.projectZO.ProjectZODto;
import com.zra.projectZO.logic.ProjectZOLogic;
import com.zra.syncc.entity.PhonePreserveEntity;
import com.zra.syncc.logic.PhonePreserveLogic;

/**
 * 渠道统计逻辑层
 * 
 * @author tianxf9
 *
 */
@Component
public class MkChannelLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(MkChannelLogic.class);

	@Autowired
	private MkChannelService channelService;

	@Autowired
	private MkNumberLogic numLogic;

	@Autowired
	private ProjectZOLogic projectZOLogic;

	@Autowired
	private PhonePreserveLogic phonePreserveLogic;
	
	@Autowired
	private ProjectLogic projectLogic;

	/**
	 * 设置渠道统计
	 * 
	 * @author tianxf9
	 * @param showDto
	 * @param channelType
	 */
	public List<MkChannelDataDto> getMkChannelData(MkChannelQueryDto queryDto) {

		List<MkChannelDataDto> resultCount = new ArrayList<MkChannelDataDto>();

		String startDate = queryDto.getStartDate();
		String endDate = queryDto.getEndDate();
		String cityId = queryDto.getCityId();
		int channelType = queryDto.getChannelType();

		startDate = startDate + " 00:00:00";
		endDate = endDate + " 23:59:59";
		List<MkChannelCountDto> yueKanCount = this.channelService.getYueKanCountService(startDate, endDate, channelType,
				cityId);
		List<MkChannelCountDto> touristsCount = this.channelService.getTouristsCountService(startDate, endDate,
				channelType, cityId);
		List<MkChannelCountDto> dealCount = this.channelService.getDealCountService(startDate, endDate, channelType,
				cityId);

		if (yueKanCount != null) {

			Map<String, Integer> touristsCountMap = new HashMap<String, Integer>();
			for (MkChannelCountDto countDto : touristsCount) {
				touristsCountMap.put(countDto.getChannelBid(), countDto.getCount());
			}

			Map<String, Integer> dealCountMap = new HashMap<String, Integer>();
			for (MkChannelCountDto countDto : dealCount) {
				dealCountMap.put(countDto.getChannelBid(), countDto.getCount());
			}

			for (MkChannelCountDto countDto : yueKanCount) {
				MkChannelDataDto dataDto = new MkChannelDataDto();
				dataDto.setChannelBid(countDto.getChannelBid());
				dataDto.setChannelName(countDto.getChannelName());
				dataDto.setYueKanCount(countDto.getCount());
				if (touristsCountMap.get(countDto.getChannelBid()) != null) {
					dataDto.setTouristsCount(touristsCountMap.get(countDto.getChannelBid()));
				} else {
					dataDto.setTouristsCount(0);
				}
				
				if(dealCountMap.get(countDto.getChannelBid())!=null) {
					dataDto.setDealCount(dealCountMap.get(countDto.getChannelBid()));
				}else {
					dataDto.setDealCount(0);
				}
				
				if (touristsCountMap.get(countDto.getChannelBid()) != null
						&& touristsCountMap.get(countDto.getChannelBid()).intValue() != 0) {
					BigDecimal rate = new BigDecimal(dataDto.getDealCount())
							.divide(new BigDecimal(dataDto.getTouristsCount()), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(100));
					dataDto.setTouristsToDealRate(rate);
				} else {
					dataDto.setTouristsToDealRate(new BigDecimal(-1));
				}
				resultCount.add(dataDto);
			}

		}

		return resultCount;
	}

	/**
	 * 线上渠道维护---提交
	 * 
	 * @author tianxf9
	 * @param channelDtos
	 * @return
	 */
	public boolean saveOnLineChannels(List<MkChannelDto> channelDtos) {

		if (channelDtos != null && channelDtos.size() > 0) {
			List<MkChannelEntity> updateEntitys = new ArrayList<MkChannelEntity>();
			List<MkChannelEntity> insertEntitys = new ArrayList<MkChannelEntity>();
			for (MkChannelDto channelDto : channelDtos) {
				MkChannelEntity entity = new MkChannelEntity();
				entity.setChannelContent(channelDto.getChannelContent());
				entity.setChannelName(channelDto.getChannelName());
				entity.setChannelType(MkChannelTypeEunm.ONLINE.getIndex());
				entity.setCityId(channelDto.getCityId());
				entity.setChannelBid(channelDto.getChannelBid());
				if (channelDto.getChannelBid() == null || channelDto.getChannelBid().equals("")) {
					entity.setChannelBid(KeyGenUtils.genKey());
					entity.setCreateId(channelDto.getUserId());
					entity.setIsDel((byte) 0);
					entity.setCreateTime(new Date());
					insertEntitys.add(entity);
				} else {
					entity.setUpdateId(channelDto.getUserId());
					entity.setUpdateTime(new Date());
					updateEntitys.add(entity);
				}
			}

			this.channelService.saveOnLineChannelEntitys(insertEntitys, updateEntitys);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 线下渠道维护-提交
	 * 
	 * @author tianxf9
	 * @param entity
	 * @return
	 */
	public Map saveLineChannel(MkChannelLineDto entity) {

		Map resultMap = new HashMap();
		resultMap.put("status", "fail");
		resultMap.put("data", "修改失败！");
		int rows = 0;
		int ccrows = 0;
		if (entity != null) {
            
			
			String channelName = entity.getChannelName();
			String channelBid = entity.getChannelBid();
			if(isExistChannelName(channelName, MkChannelTypeEunm.LINE.getIndex().intValue(),channelBid)) {
				resultMap.put("status", "fail");
				resultMap.put("data", "渠道名称不可重复");
				return resultMap;
			}
			
			if(isExistNumbers(entity)) {
				resultMap.put("status", "fail");
				resultMap.put("data", "分机号和项目销售电话重复");
				return resultMap;
			}
			List<MkNumberEntity> numberEntitys = new ArrayList<MkNumberEntity>();
			List<MkNumberDto> channelNumberDtos = entity.getChannelNumber();
			List<String> numberList = new ArrayList<String>();
			if (channelNumberDtos != null && channelNumberDtos.size() > 0 && channelNumberDtos.get(0) != null) {
				for (MkNumberDto numberDto : channelNumberDtos) {
					numberList.add(numberDto.getPhoneNum());
					MkNumberEntity numEntity = new MkNumberEntity();
					numEntity.setChannelBid(entity.getChannelBid());
					numEntity.setCreateId(entity.getUserId());
					numEntity.setCreateTime(new Date());
					numEntity.setIsDel((byte) 0);
					numEntity.setNumber(numberDto.getPhoneNum());
					numEntity.setProjectId(numberDto.getProjectId());
					if(numberDto.getNumberBid()==null||numberDto.getNumberBid().equals("")) {
						numEntity.setNumberBid(KeyGenUtils.genKey());
					}else {
						numEntity.setNumberBid(numberDto.getNumberBid());
					}
					numberEntitys.add(numEntity);
				}

				// 判断该渠道下的分机号是否和其他渠道的重复
				resultMap = isExistNumber(numberList, entity.getChannelBid());
				if (resultMap.get("status") == "fail") {
					return resultMap;
				}

				if (entity.getChannelBid() != null && !entity.getChannelBid().equals("")) {
					//更新
					resultMap = this.updateChannel(entity, numberEntitys);
				} else {
					//新增
					resultMap = this.insertChannel(entity, numberEntitys);
				}
			}
		}

		return resultMap;
	}

	/**
	 * 更新渠道
	 * 
	 * @author tianxf9
	 * @param entity
	 * @param numberEntitys
	 * @return
	 */
	public Map updateChannel(MkChannelLineDto entity, List<MkNumberEntity> numberEntitys) {

		boolean ccresult = true;
		int rows = 0;
		MkChannelEntity channelEntity = new MkChannelEntity();
		channelEntity.setChannelName(entity.getChannelName());
		channelEntity.setChannelBid(entity.getChannelBid());
		channelEntity.setUpdateId(entity.getUserId());
		channelEntity.setUpdateTime(new Date());
		// 根据channelBid获取原有numberEntitys
		List<MkNumberEntity> oldNumberEntitys = this.numLogic.getNumEntitysByChannelBid(entity.getChannelBid());
		// 获取需要删除的
		String oldNumbers = (oldNumberEntitys.size()>0)?JSON.toJSONString(oldNumberEntitys):"";
		LOGGER.info("oldNumbers="+oldNumbers+";newNumbers="+JSON.toJSONString(numberEntitys));
		List<MkNumberEntity> delNumberEntitys = this.getDelList(oldNumberEntitys, numberEntitys);
		// 调用400
		// 删除
		List<String> extensionNumbers = new ArrayList<String>();
		if (delNumberEntitys != null && delNumberEntitys.size() > 0) {
			for (MkNumberEntity numEntity : delNumberEntitys) {
				extensionNumbers.add(numEntity.getNumber());
			}
		}
        
		if(extensionNumbers.size()>0) {
			ccresult = this.phonePreserveLogic.deletePhonePreserveByExtNum(extensionNumbers);
		}
		
		// 获取需要更新的
		List<MkNumberEntity> updateNumberEntitys = this.getUpdateList(oldNumberEntitys, numberEntitys);
		if (updateNumberEntitys != null && updateNumberEntitys.size() > 0) {
			List<PhonePreserveDto> updatePresonveDto = getPhoneDtoByMkNums(updateNumberEntitys,
					channelEntity.getChannelName());
			List<PhonePreserveEntity> updatePersonEntitys = null;
			if(updatePresonveDto!=null) {
				updatePersonEntitys = this.phoneDtoToEntity(updatePresonveDto);
			}
			
			if(updatePersonEntitys!=null&&updatePersonEntitys.size()>0&&ccresult) {
				ccresult = this.phonePreserveLogic.updatePhonePreserve(updatePersonEntitys);
			}
			
		}

		// 获取需要新增的
		List<MkNumberEntity> insertNumberEntitys = this.getInsertEntitys(oldNumberEntitys, numberEntitys);
		if (insertNumberEntitys != null && insertNumberEntitys.size() > 0) {
			List<PhonePreserveDto> insertPresonveDto = getPhoneDtoByMkNums(insertNumberEntitys,
					channelEntity.getChannelName());
			List<PhonePreserveEntity> insertPersonEntitys = null;
			if(insertPresonveDto!=null) {
				insertPersonEntitys = this.phoneDtoToEntity(insertPresonveDto);
			}
			
			if(insertPersonEntitys!=null&&insertPersonEntitys.size()>0&&ccresult) {
				ccresult = this.phonePreserveLogic.savePhonePreserve(insertPersonEntitys);
			}
		}
		if (ccresult) {
			rows = this.channelService.updateLineChannel(channelEntity, numberEntitys);
		}

		Map resultMap = new HashMap();
		if (!ccresult) {
			resultMap.put("status", "fail");
			resultMap.put("data", "操作CC库失败！");
		} else if (rows == 0) {
			resultMap.put("status", "fail");
			resultMap.put("data", "操作zra库失败！");
		} else {
			resultMap.put("status", "success");
			resultMap.put("data", "操作成功！");
		}

		return resultMap;

	}

	/**
	 * 新增渠道
	 * 
	 * @author tianxf9
	 * @param entity
	 * @param numberEntitys
	 * @return
	 */
	public Map insertChannel(MkChannelLineDto entity, List<MkNumberEntity> numberEntitys) {

		boolean ccresult = true;
		int rows = 0;
		MkChannelEntity channelEntity = new MkChannelEntity();
		String channelBid = KeyGenUtils.genKey();
		for (MkNumberEntity temp : numberEntitys) {
			temp.setChannelBid(channelBid);
		}
		channelEntity.setChannelBid(channelBid);
		channelEntity.setCreateId(entity.getUserId());
		channelEntity.setCreateTime(new Date());
		channelEntity.setCityId(entity.getCityId());
		channelEntity.setChannelName(entity.getChannelName());
		channelEntity.setChannelType(MkChannelTypeEunm.LINE.getIndex());
		channelEntity.setIsDel((byte) 0);

		List<PhonePreserveDto> phonePreserveDtos = this.getPhoneDtoByMkNums(numberEntitys,
				channelEntity.getChannelName());
		
		List<PhonePreserveEntity> phonePersonEntitys = null;
		if(phonePreserveDtos!=null) {
			phonePersonEntitys = this.phoneDtoToEntity(phonePreserveDtos);
		}
		
		if(phonePersonEntitys!=null&&phonePersonEntitys.size()>0) {
			ccresult = this.phonePreserveLogic.savePhonePreserve(phonePersonEntitys);
		}
		
		if (ccresult) {
			rows = this.channelService.saveLineChannel(channelEntity, numberEntitys);
		}

		Map resultMap = new HashMap();

		if (!ccresult) {
			resultMap.put("status", "fail");
			resultMap.put("data", "操作CC库失败！");
		} else if (rows == 0) {
			resultMap.put("status", "fail");
			resultMap.put("data", "操作zra库失败！");
		} else {
			resultMap.put("status", "success");
			resultMap.put("data", "操作成功！");
		}

		return resultMap;

	}

	/**
	 * 获取需要删除的分机号码
	 * 
	 * @author tianxf9
	 * @param oldEntitys
	 * @param newEntitys
	 */
	public List<MkNumberEntity> getDelList(List<MkNumberEntity> oldEntitys, List<MkNumberEntity> newEntitys) {
		
		List<MkNumberEntity> delEntitys = new ArrayList<MkNumberEntity>();
		for (MkNumberEntity oldEntity : oldEntitys) {
			boolean isExist = false;
			for (MkNumberEntity newEntity : newEntitys) {
				if (oldEntity.getNumber().equals(newEntity.getNumber())) {
					isExist = true;
					break;
				}
			}

			if (!isExist) {
				delEntitys.add(oldEntity);
			}
		}
		
		LOGGER.info("需要调用400删除的numbers:"+JSON.toJSONString(delEntitys));
		return delEntitys;
	}

	/**
	 * 获取需要更新的实体
	 * 需要更新的实体：分机号没有变更，项目变更了！
	 * @author tianxf9
	 * @param oldEntitys
	 * @param newEntitys
	 * @return
	 */
	public List<MkNumberEntity> getUpdateList(List<MkNumberEntity> oldEntitys, List<MkNumberEntity> newEntitys) {

		List<MkNumberEntity> updateEntitys = new ArrayList<MkNumberEntity>();
		for (MkNumberEntity oldEntity : oldEntitys) {
			boolean isUpdate = false;
			for (MkNumberEntity newEntity : newEntitys) {
				if (oldEntity.getNumberBid().equals(newEntity.getNumberBid())
						&& !oldEntity.getProjectId().equals(newEntity.getProjectId())&&oldEntity.getNumber().equals(newEntity.getNumber())) {
					oldEntity.setProjectId(newEntity.getProjectId());
					isUpdate = true;
					break;
				}
			}

			if (isUpdate) {
				updateEntitys.add(oldEntity);
			}
		}
		LOGGER.info("需要调用400更新的numbers:"+JSON.toJSONString(updateEntitys));
		return updateEntitys;
	}

	/**
	 * 获取需要新增的实体
	 * 
	 * @author tianxf9
	 * @param oldEntitys
	 * @param newEntitys
	 * @return
	 */
	public List<MkNumberEntity> getInsertEntitys(List<MkNumberEntity> oldEntitys, List<MkNumberEntity> newEntitys) {

		List<MkNumberEntity> insertEntitys = new ArrayList<MkNumberEntity>();
		for (MkNumberEntity newEntity : newEntitys) {
			boolean isInsert = true;
			for (MkNumberEntity oldEntity : oldEntitys) {
				if (newEntity.getNumber().equals(oldEntity.getNumber())) {
					isInsert = false;
					break;
				}
			}
			if (isInsert) {
				insertEntitys.add(newEntity);
			}
		}
		LOGGER.info("需要调用400新增的numbers:"+JSON.toJSONString(insertEntitys));
		return insertEntitys;
	}

	/**
	 * 判断当前渠道下的分机号是否和其他渠道的分机号重复
	 * 
	 * @author tianxf9
	 * @param numberList
	 * @param channelBid
	 * @return
	 */
	public Map isExistNumber(List<String> numberList, String channelBid) {

		Map result = new HashMap();
		List<String> existNum = this.numLogic.isExistNumber(numberList, channelBid);
		String existNumStr = "";
		for (String temp : existNum) {

			if (existNumStr.equals("")) {
				existNumStr = temp;
			} else {
				existNumStr = existNumStr + "," + temp;
			}
		}
		if (existNum != null && existNum.size() > 0) {
			result.put("status", "fail");
			result.put("data", "分机号：" + existNumStr + ";已经存在！");
		} else {
			result.put("status", "success");
		}
		return result;
	}

	/**
	 * 根据分机号和排班构造传递给400的参数
	 * 
	 * @author tianxf9
	 * @return
	 */
	public List<PhonePreserveDto> getPhoneDtoByMkNums(List<MkNumberEntity> numberEntitys, String channelName) {

		List<PhonePreserveDto> phoneDtoList = new ArrayList<PhonePreserveDto>();

		int nowTimeWeek = DateUtilFormate.getWeekDay();
		int nowHour = DateUtilFormate.getHourOfDay();
		String fweek = null;

		if (nowHour >= 0 && nowHour < 19) {
			fweek = String.valueOf(nowTimeWeek);
		} else {
			fweek = DateUtilFormate.getAddWeekDay(nowTimeWeek);
		}

		// 没有排班项目对应的所有管家的电话发送过去
		Set<String> projectIds = new HashSet<String>();
		for (MkNumberEntity entity : numberEntitys) {
			projectIds.add(entity.getProjectId());
		}
		List<SchedulePersonDto> schedulePersonDtos = this.channelService.getSchedulePresonInfoService(fweek,
				projectIds);
		// key = projectId
		Map<String, List<SchedulePersonDto>> schedulePersonMap = new HashMap<String, List<SchedulePersonDto>>();
		for (SchedulePersonDto dto : schedulePersonDtos) {
			if (schedulePersonMap.get(dto.getProId()) == null) {
				List<SchedulePersonDto> persons = new ArrayList<SchedulePersonDto>();
				persons.add(dto);
				schedulePersonMap.put(dto.getProId(), persons);
			} else {
				schedulePersonMap.get(dto.getProId()).add(dto);
			}

		}

		
		for (MkNumberEntity numEntity : numberEntitys) {
			
			if (schedulePersonMap.get(numEntity.getProjectId()) != null && schedulePersonMap.get(numEntity.getProjectId()).size() > 0) {
				PhonePreserveDto phone = new PhonePreserveDto();
				phone.setName(channelName);
				phone.setExtNum(numEntity.getNumber());
				phone.setEmployees(schedulePersonMap.get(numEntity.getProjectId()));
				phoneDtoList.add(phone);
			} else {
				// 没有排班的项目查询所有管家
				List<ProjectZODto> projectZoDto = this.projectZOLogic.getProjectZOsByProId(numEntity.getProjectId());
				List<SchedulePersonDto> zoList = new ArrayList<SchedulePersonDto>();
				for (ProjectZODto tempDto : projectZoDto) {
					SchedulePersonDto spDto = new SchedulePersonDto();
					spDto.setJobNum(tempDto.getProjectZOCode());
					spDto.setMobile(tempDto.getZrojectZoPhone());
					spDto.setProId(tempDto.getProjectId());
					zoList.add(spDto);
				}

				if (zoList != null && zoList.size() > 0) {
					PhonePreserveDto phone = new PhonePreserveDto();
					phone.setName(channelName);
					phone.setExtNum(numEntity.getNumber());
					phone.setEmployees(zoList);
					phoneDtoList.add(phone);
				}
			}
		}
        
		LOGGER.info("分机号参数转换===="+JSON.toJSONString(numberEntitys)+"===TO==="+JSON.toJSONString(phoneDtoList));
		return phoneDtoList;

	}

	/**
	 * 删除渠道统计
	 * 
	 * @author tianxf9
	 * @param channelDto
	 * @return
	 */
	public boolean deleteChannelByBid(MkChannelDto channelDto) {

		// 需要删除的分机号
		List<String> deleteNumBid = this.numLogic.getNumberByChannelBid(channelDto.getChannelBid());
		// 调用400删除
		boolean result = true;
		if (MkChannelTypeEunm.LINE.getIndex().byteValue() == channelDto.getChannelType().byteValue()) {
			result = this.phonePreserveLogic.deletePhonePreserveByExtNum(deleteNumBid);
		} 

		if (result) {
			// 删除渠道
			result = this.channelService.deleteChannel(channelDto);
		}

		return result;
	}

	/**
	 * 查询线上渠道list
	 * 
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	public Map getOnLineList(String cityId, int pageNum, int rows) {
		PageHelper.startPage(pageNum, rows);
		List<MkChannelDto> channelDtos = this.channelService.getOnLineChannelByCityId(cityId);
		PageInfo<MkChannelDto> pageInfo = new PageInfo<>(channelDtos);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		LOGGER.info("total:" + channelDtos.size() + "," + channelDtos);
		dataMap.put("total", pageInfo.getTotal());
		dataMap.put("rows", channelDtos);
		return dataMap;
	}

	/**
	 * 获取线下渠道列表
	 * 
	 * @author tianxf9
	 * @param cityId
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<MkLineChannelShowDto> getLineList(String cityId) {
		
		List<MkLineChannelShowDto> channelEntitys = this.channelService.getLineChannelByCityId(cityId);

		Map<String, List<MkLineChannelShowDto>> channelMap = new HashMap<String, List<MkLineChannelShowDto>>();
		for (MkLineChannelShowDto channelShowDto : channelEntitys) {
			if (channelMap.get(channelShowDto.getChannelBid()) == null) {
				List<MkLineChannelShowDto> showDtos = new ArrayList<MkLineChannelShowDto>();
				showDtos.add(channelShowDto);
				channelMap.put(channelShowDto.getChannelBid(), showDtos);
			} else {
				channelMap.get(channelShowDto.getChannelBid()).add(channelShowDto);
			}
		}

		int index = 1;
		for (String key : channelMap.keySet()) {
			List<MkLineChannelShowDto> tempList = channelMap.get(key);
			for (int i = 0; i < tempList.size(); i++) {
				MkLineChannelShowDto temp = channelMap.get(key).get(i);
				temp.setSequenceNum(index);
				if (i == 0) {
					temp.setShowNum(true);
				} else {
					temp.setShowNum(false);
				}
			}

			index++;
		}
		
		return channelEntitys;
	}

	/**
	 * 根据渠道bid获取渠道400电话
	 * 
	 * @author tianxf9
	 * @param channelBid
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public Map getNumsByChannelBid(String channelBid) {

		List<MkNumberDto> numberDtos = this.numLogic.getNumberMsgByChannelBid(channelBid);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		LOGGER.info("total:" + numberDtos.size() + "," + numberDtos);
		dataMap.put("total", numberDtos.size());
		dataMap.put("rows", numberDtos);
		return dataMap;
	}

	/**
	 * 根据渠道bid获取渠道name
	 * 
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public String getChannelNameByBid(String channelBid) {
		return this.channelService.getChannelNameByBid(channelBid);
	}

	/**
	 * 根据渠道内容获取渠道bid
	 * 
	 * @author tianxf9
	 * @param channelContent
	 * @return
	 */
	public String getChannelBidByContent(String channelContent, String cityId) {

		List<MkChannelDto> mkDtos = this.channelService.getOnLineChannelByCityId(cityId);

		for (MkChannelDto mkDto : mkDtos) {
			String mkContent = mkDto.getChannelContent();
			mkContent = mkContent.replace(" ", "");
			if (channelContent.indexOf(mkContent) >= 0) {
				return mkDto.getChannelBid();
			}
		}
		return null;

	}
	
	/**
	 * 判断渠道名称是否存在
	 * @author tianxf9
	 * @param channelName
	 * @param channelType
	 * @return
	 */
	public boolean isExistChannelName(String channelName,int channelType,String channelBid) {
		return this.channelService.isExistChannelName(channelName, channelType,channelBid);
	}
	
	
	/**
	 * dto转实体
	 * 
	 * @author tianxf9
	 * @param dtos
	 * @return
	 */
	public List<PhonePreserveEntity> phoneDtoToEntity(List<PhonePreserveDto> dtos) {

		List<PhonePreserveEntity> phoneEntitys = new ArrayList<PhonePreserveEntity>();
		try {
			for (PhonePreserveDto dto : dtos) {
				List<SchedulePersonDto> personList = dto.getEmployees();
				if (personList == null || personList.size() == 0) {
					continue;
				}
				PhonePreserveEntity entity = new PhonePreserveEntity();
				entity.setExtensionNumber(dto.getExtNum());
				entity.setName(dto.getName());
				int length = personList.size();
				if (length > 10) {
					length = 10;
				}
				for (int i = 0; i < length; i++) {
					String method1 = "";
					String method2 = "setMobile" + (i + 1);
					;
					if (i == 0) {
						method1 = "setJobNumber";
						entity.getClass().getMethod(method1, String.class).invoke(entity,
								personList.get(i).getJobNum());
						entity.getClass().getMethod(method2, String.class).invoke(entity,
								personList.get(i).getMobile());
					} else {
						method1 = "setJobNumber" + (i + 1);
						entity.getClass().getMethod(method1, String.class).invoke(entity,
								personList.get(i).getJobNum());
						entity.getClass().getMethod(method2, String.class).invoke(entity,
								personList.get(i).getMobile());
					}
				}

				entity.setModifyTime(new Date());
				entity.setSource("77");
				phoneEntitys.add(entity);
			}
		} catch (Exception e) {
			LOGGER.error("phoneDto转phoneEntit出错!", e);
		}

		return phoneEntitys;
	}
	
	
	/**
	 * 判断新添的分机号是否和项目已有的分机号重复
	 * @author tianxf9
	 * @param entity
	 * @return
	 */
	public boolean isExistNumbers(MkChannelLineDto entity) {
		
		List<MkNumberDto> numbers = entity.getChannelNumber();
		List<ProjectTelDto> projectTelDtos = projectLogic.getAllProjectTelMsg();
		if(numbers!=null&&numbers.size()>0) {
			for(ProjectTelDto projectTelDto:projectTelDtos) {
				String tel = projectTelDto.getMarketTel();
				if(!tel.contains(",")) {
					continue;
				}else {
					String[] tels = tel.split(",");
					if(tels.length==2) {
						for(MkNumberDto number:numbers) {
							if(number.getPhoneNum().equals(tels[1])) {
								return true;
							}
						}
					}else {
						continue;
					}
				}
			}
		}
		
		return false;
	}

}
