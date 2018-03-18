package com.zra.marketing.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.marketing.MkNumberDto;
import com.zra.house.entity.dto.ProjectTelDto;
import com.zra.marketing.entity.MkNumberEntity;
import com.zra.marketing.service.MkNumberService;

/**
 * 
 * @author tianxf9
 *
 */
@Component
public class MkNumberLogic {
	
	@Autowired
	private MkNumberService numberService;
	
	/**
	 * @author tianxf9
	 * @return
	 */
	public List<String> getNumberByChannelBid(String channelBid) {
		return this.numberService.getNumberByChannelBid(channelBid);		
	}
	
	/**
	 * 
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public int deleteNumberByChannelBid(String channelBid,String userId) {
		return this.numberService.deleteBeforeSave(channelBid, userId);
	}
	
	
	/**
	 * 
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public List<MkNumberDto> getNumberMsgByChannelBid(String channelBid) {
		return this.numberService.getNumberEntitysByChannelBid(channelBid);
	}
	
	/**
	 * 获取所有电话号码
	 * @author tianxf9
	 * @return
	 */
	public List<ProjectTelDto> getAllNumber() {
		return this.numberService.getAllNumber();
	}
	
	/**
	 * 根据项目获取该项目的分机号
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<String> getNumberByProId(String projectId) {
		return this.numberService.getAllNumberByProId(projectId);
	}
	
	
	/**
	 * 当前渠道的分机号和其他渠道重复
	 * @author tianxf9
	 * @param numbers
	 * @param channelBdi
	 * @return
	 */
	public List<String> isExistNumber(List<String> numbers,String channelBid) {
		return this.numberService.isExistNumber(numbers, channelBid);
	}
	
	
	/**
	 * 根据channelBid获取实体
	 * @author tianxf9
	 * @param channelBid
	 * @return
	 */
	public List<MkNumberEntity> getNumEntitysByChannelBid(String channelBid) {
		List<MkNumberDto> numDtos = this.numberService.getNumberEntitysByChannelBid(channelBid);
		List<MkNumberEntity> entitys = new ArrayList<MkNumberEntity>();
		for(MkNumberDto numDto:numDtos) {
			MkNumberEntity entity = new MkNumberEntity();
			entity.setNumber(numDto.getPhoneNum());
			entity.setNumberBid(numDto.getNumberBid());
			entity.setProjectId(numDto.getProjectId());
			entitys.add(entity);
		}
		
		return entitys;
	}

}
