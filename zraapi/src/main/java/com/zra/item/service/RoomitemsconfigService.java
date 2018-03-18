package com.zra.item.service;

import com.zra.common.dto.meteritem.MeterItemParamDto;
import com.zra.item.dao.RoomitemsconfigMapper;
import com.zra.item.entity.RoomitemsconfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PC on 2016/9/28.
 */
@Service
public class RoomitemsconfigService {
	@Autowired
	private RoomitemsconfigMapper mapper;

	/**
	 * 查询已经存在的物品列表--房间或者床位的私有物品配置
	 * @author xiaona
	 * @param itemIds
	 * @return
	 */
	public List<RoomitemsconfigEntity> getExistItems(MeterItemParamDto paramDto, List<String> itemIds) {
		String roomId=paramDto.getRoomId();
		Integer typeInfo=paramDto.getTypeInfo();
		return this.mapper.getExistItemById(roomId,typeInfo,itemIds);
	}
}
