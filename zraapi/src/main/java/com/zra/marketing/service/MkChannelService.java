package com.zra.marketing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zra.common.dto.marketing.MkChannelDto;
import com.zra.common.dto.marketing.MkLineChannelShowDto;
import com.zra.common.dto.marketing.SchedulePersonDto;
import com.zra.common.enums.MkChannelTypeEunm;
import com.zra.marketing.dao.MkChannelMapper;
import com.zra.marketing.dao.MkNumberMapper;
import com.zra.marketing.entity.MkChannelEntity;
import com.zra.marketing.entity.MkNumberEntity;
import com.zra.marketing.entity.dto.MkChannelCountDto;
import com.zra.marketing.logic.MkChannelLogic;

@Service
public class MkChannelService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MkChannelLogic.class);

	@Autowired
	private MkChannelMapper mkChannelMapper;
	
	@Autowired
	private MkNumberMapper mkNumberMapper;
	/**
	 * 获取约看量
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param channelType
	 * @return
	 */
	public List<MkChannelCountDto> getYueKanCountService(String startDate, String endDate, int channelType,
			String cityId) {
		if (channelType != MkChannelTypeEunm.ONLINE.getIndex().intValue()
				&& channelType != MkChannelTypeEunm.LINE.getIndex().intValue()) {
			LOGGER.info("getYueKanCountService方法参数channelType非法！");
			List<MkChannelCountDto> result = new ArrayList<MkChannelCountDto>();
			return result;
		}
		return mkChannelMapper.getYueKanCount(startDate, endDate, channelType, cityId);
	}

	/**
	 * 获取客源量
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param channelType
	 * @return
	 */
	public List<MkChannelCountDto> getTouristsCountService(String startDate, String endDate, int channelType,
			String cityId) {
		if (channelType != MkChannelTypeEunm.ONLINE.getIndex().intValue()
				&& channelType != MkChannelTypeEunm.LINE.getIndex().intValue()) {
			LOGGER.info("getTouristsCountService方法参数channelType非法！");
			List<MkChannelCountDto> result = new ArrayList<MkChannelCountDto>();
			return result;
		}
		return mkChannelMapper.getTouristsCount(startDate, endDate, channelType, cityId);
	}

	/**
	 * 获取成交量
	 * 
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param channelType
	 * @return
	 */
	public List<MkChannelCountDto> getDealCountService(String startDate, String endDate, int channelType,
			String cityId) {
		if (channelType != MkChannelTypeEunm.ONLINE.getIndex().intValue()
				&& channelType != MkChannelTypeEunm.LINE.getIndex().intValue()) {
			LOGGER.info("getDealCountService方法参数channelType非法！");
			List<MkChannelCountDto> result = new ArrayList<MkChannelCountDto>();
			return result;
		}
		return mkChannelMapper.getDealCount(startDate, endDate, channelType, cityId);
	}

	/**
	 * 新增或更新线上渠道
	 * 
	 * @author tianxf9
	 * @param insertEntitys
	 * @param updateEntitys
	 * @return
	 */
	public int saveOnLineChannelEntitys(List<MkChannelEntity> insertEntitys, List<MkChannelEntity> updateEntitys) {
		int insertrows = 0;
		int updaterows = 0;
		if (insertEntitys != null && insertEntitys.size() > 0) {
			insertrows = mkChannelMapper.insertEntitys(insertEntitys);
			LOGGER.info("新增线上渠道" + insertrows + "条");
		}

		if (updateEntitys != null && updateEntitys.size() > 0) {
			for (MkChannelEntity entity : updateEntitys) {
				mkChannelMapper.updateByBid(entity);
			}
			updaterows = updateEntitys.size();
			LOGGER.info("更新线上渠道" + updaterows + "条");
		}

		return insertrows + updaterows;

	}

	/**
	 * 更新线下渠道数据
	 * 
	 * @author tianxf9
	 * @param channelEntity
	 * @param numberEntitys
	 * @return
	 */
	public int updateLineChannel(MkChannelEntity channelEntity, List<MkNumberEntity> numberEntitys) {
		int rows = 0;
		rows = rows + this.mkChannelMapper.updateByBid(channelEntity);
		LOGGER.info("删除channelBid="+channelEntity.getChannelBid()+"的Mk_Number表记录！");
		rows = rows + this.mkNumberMapper.delByChannelBid(channelEntity.getChannelBid(), channelEntity.getUpdateId());

		LOGGER.info("新增Mk_Number分机号："+JSON.toJSONString(numberEntitys));
		rows = rows + this.mkNumberMapper.insert(numberEntitys);

		return rows;
	}

	/**
	 * 新增线下下渠道
	 * 
	 * @author tianxf9
	 * @param channelEntity
	 * @param numberEntitys
	 * @return
	 */
	public int saveLineChannel(MkChannelEntity channelEntity, List<MkNumberEntity> numberEntitys) {
		int rows = 0;
		LOGGER.info("插入mk_channel表："+JSON.toJSONString(channelEntity));
		
		rows = rows + this.mkChannelMapper.insertEntity(channelEntity);
		
		LOGGER.info("插入mk_number表："+JSON.toJSONString(numberEntitys));
		rows = rows + this.mkNumberMapper.insert(numberEntitys);
		return rows;
	}

	/**
	 * 根据日期获取所有项目的所有排班人员信息
	 * 
	 * @author tianxf9
	 * @param fweek
	 * @return
	 */
	public List<SchedulePersonDto> getSchedulePresonInfoService(String fweek,Set<String> projectIds) {
		return this.mkChannelMapper.getSchedulePresonInfo(fweek,projectIds);
	}

	/**
	 * 根据bid删除渠道统计
	 * 
	 * @author tianxf9
	 * @param channelBid
	 * @param userId
	 * @return
	 */
	public int deleteChannelByBid(String channelBid, String userId) {
		return this.mkChannelMapper.deleteMkChannelByBid(channelBid, userId);
	}

	/**
	 * 删除渠道统计
	 * 
	 * @author tianxf9
	 * @param channelDto
	 * @return
	 */
	public boolean deleteChannel(MkChannelDto channelDto) {
		if (channelDto.getChannelBid() != null && !channelDto.getChannelBid().equals("")) {
			if (MkChannelTypeEunm.LINE.getIndex().byteValue() == channelDto.getChannelType().byteValue()) {
				// 删除分机号
				this.mkNumberMapper.delByChannelBid(channelDto.getChannelBid(), channelDto.getUserId());
			}
			// 删除渠道
			deleteChannelByBid(channelDto.getChannelBid(), channelDto.getUserId());
			return true;
		}
		return false;
	}

	/**
	 * 根据城市id查询渠道列表
	 * 
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	public List<MkLineChannelShowDto> getLineChannelByCityId(String cityId) {
		return this.mkChannelMapper.selectLineChannelByCityId(cityId);
	}
	
	
	/**
	 * 根据城市获取线上渠道
	 * @author tianxf9
	 * @param cityId
	 * @return
	 */
	public List<MkChannelDto> getOnLineChannelByCityId(String cityId) {
		return this.mkChannelMapper.getOnLineChannelByCityId(cityId);
	}

	
	/**
	 * 根据bid获取name
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public String getChannelNameByBid(String channelBid) {
		return this.mkChannelMapper.getChannelNameByBid(channelBid);
	}
	
	/**
	 * 判断渠道名称是否存在
	 * @author tianxf9
	 * @param channelName
	 * @param channelType
	 * @return
	 */
	public boolean isExistChannelName(String channelName,int channelType,String channelBid) {
		return this.mkChannelMapper.isExistChannelName(channelName, channelType,channelBid)>0;
	}

}
