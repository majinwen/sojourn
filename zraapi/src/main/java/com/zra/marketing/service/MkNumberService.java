package com.zra.marketing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.dto.marketing.MkNumberDto;
import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.marketing.dao.MkNumberMapper;
import com.zra.marketing.entity.MkNumberEntity;

/**
 *
 * @author tianxf9
 *
 */
@Service
public class MkNumberService {
	
	@Autowired
	private MkNumberMapper mkNumberMapper;
	
	/**
	 * 保存实体
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveNumberEntitys(List<MkNumberEntity> entitys) {
		return this.mkNumberMapper.insert(entitys);
	}
	
	/**
	 * 线下渠道维护在保存前将之前的该渠道下的所有分机号删除
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public int deleteBeforeSave(String channelBid,String userId) {
		return this.mkNumberMapper.delByChannelBid(channelBid, userId);
	}
	
	/**
	 * 根据渠道统计bid统计分机号
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public List<String> getNumberByChannelBid(String channelBid) {
		return this.mkNumberMapper.getNumberByChannelBid(channelBid);
	}
	
	/**
	 * 根据渠道统计bid获取分机号实体
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public List<MkNumberDto> getNumberEntitysByChannelBid(String channelBid) {
		return this.mkNumberMapper.selectByChannelBid(channelBid);
	}
	
	/**
	 * 获取所有分机号
	 * @author tianxf9
	 * @return
	 */
	public List<ProjectTelDto> getAllNumber() {
		return this.mkNumberMapper.getAllNumber();
	}
	
	/**
	 * 根据项目id获取该项目下的所有分机号
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<String> getAllNumberByProId(String projectId) {
		return this.mkNumberMapper.getNumbersByProId(projectId);
	}
	
	
	/**
	 * 当前渠道的分机号和其他渠道重复
	 * @author tianxf9
	 * @param numbers
	 * @param channelBdi
	 * @return
	 */
	public List<String> isExistNumber(List<String> numbers,String channelBid) {
		return this.mkNumberMapper.isExistNumber(numbers, channelBid);
	}

}
