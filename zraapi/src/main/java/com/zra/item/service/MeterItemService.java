package com.zra.item.service;

import com.zra.common.dto.bedInfo.ItemDto;
import com.zra.common.dto.meteritem.MeterItemParamDto;
import com.zra.common.enums.RoomTypeInfoEnum;
import com.zra.item.dao.ItemdeliveryMapper;
import com.zra.item.dao.RoomitemsconfigMapper;
import com.zra.item.entity.ItemdeliveryEntity;
import com.zra.item.entity.RoomitemsconfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 2016/9/23.
 */
@Service
public class MeterItemService {

	@Autowired
	private ItemdeliveryMapper mapper;
	@Autowired
	private ItemService itemService;
	@Autowired
	private RoomitemsconfigMapper roomitemsconfigMapper;
	/**
	 * 查询已经存在的物品列表
	 * @author xiaona
	 * @param itemIds
	 * @return
	 */
	public List<ItemdeliveryEntity> getExistItems(String contractId,String roomId,Integer rentType, List<String> itemIds) {
		return this.mapper.getExistItemById(contractId,roomId,rentType, itemIds);
	}


	/**
	 * 保存物品配置
	 * @author xiaona
	 * @return
	 */
	public int saveItemConfig(ItemdeliveryEntity entity) {
		return this.mapper.insert(entity);
	}


	public  int saveRoomItemConfig(RoomitemsconfigEntity entity){
		return roomitemsconfigMapper.insert(entity);
	}
	/**
	 * 更新物品数量
	 * @author tianxf9
	 * @param itemIds
	 * @return
	 */
	public boolean updateStandardItemCount(String contactId,String roomId,List<String> itemIds) {
		int count = 0;
		count = this.mapper.updateItemCount(contactId,roomId,itemIds);
		return count>0;
	}


	/**
	 * 更新物品数量
	 * @author tianxf9
	 * @param itemIds
	 * @return
	 */
	public boolean updateRoomConfig(String roomId,List<String> itemIds) {
		int count = 0;
		count = this.roomitemsconfigMapper.updateRoomItemCount(roomId,itemIds);
		return count>0;
	}

	/**
	 * 根据物品id和合同以及房间床位更新合同以及房间物品配置表
	 * @param paramDto
	 * @return
	 */
	public boolean saveItemConfigByIds(MeterItemParamDto paramDto) {
		// 处理itemIds
		String[] idArray = paramDto.getItemIds().split(",");
		List<String> idList = Arrays.asList(idArray);
		/**
		 * 首先判断合同中有没有，有的话则更新加一，没有的话则新增一条记录即可
		 */
		// 已经存在的物品
		List<ItemdeliveryEntity> existMeterItemDtos = this.mapper
				.getExistItemById(paramDto.getContractId(),paramDto.getRoomId(),paramDto.getTypeInfo(), idList);

		//再次判断是否在房间私有物品中存在相同的物品
		List<RoomitemsconfigEntity> roomitemsconfigs=roomitemsconfigMapper
				 .getExistItemById(paramDto.getRoomId(),paramDto.getTypeInfo(),idList);
		List<String> updateItemIds = new ArrayList<String>();  //需要更新的  在合同中与物品的对应关系
		List<String> insertItemIds = new ArrayList<String>();  //需要添加的  在合同中与物品的对应关系

		List<String> updateRoomItemIds = new ArrayList<>();  // 分为需要添加和更新的  房间与物品的对应关系
		List<String> insertRoomItemIds = new ArrayList<>();  // 分为需要添加和更新的  房间与物品的对应关系

		/**
		 * 这个是查找合同与物品的配置关系--xiaona--2016年9月24日
		 */
		if (existMeterItemDtos != null && existMeterItemDtos.size() > 0) {
			for (String itemId : idList) {
				boolean exist = false;
				for (ItemdeliveryEntity itemDto : existMeterItemDtos) {
					if (itemId.equals(itemDto.getItemid())) {
						exist = true;
						break;
					}
				}

				if (exist) {
					updateItemIds.add(itemId);
				} else {
					insertItemIds.add(itemId);
				}
			}
		} else {
			insertItemIds = idList;
		}

		/*查找房间与物品的对应关系*/
		if (roomitemsconfigs != null && roomitemsconfigs.size() > 0) {
			for (String itemId : idList) {
				boolean exist = false;
				for (RoomitemsconfigEntity itemDto : roomitemsconfigs) {
					if (itemId.equals(itemDto.getItemid())) {
						exist = true;
						break;
					}
				}

				if (exist) {
					updateRoomItemIds.add(itemId);
				} else {
					insertRoomItemIds.add(itemId);
				}
			}
		} else {
			insertRoomItemIds = idList;
		}

		/**
		 * 合同和物品的更新关系
		 */
		boolean insertResult = false;
		// 根据id查询物品
		if (insertItemIds != null && insertItemIds.size() > 0) {
			List<ItemDto> items = this.itemService.getItemsById(insertItemIds);

			// 将物品转为物品，配置关系Dto
			List<ItemdeliveryEntity> insertStandardItemDtos = this.converItemTOStandardItem(items,
					paramDto, paramDto.getUserId());

			// 保存关系
			if (insertStandardItemDtos != null && insertStandardItemDtos.size() > 0) {
				insertResult = this.saveItemConfig(insertStandardItemDtos);
			}
		}
		// 更新
		boolean updateResult = false;
		if (updateItemIds.size() > 0) {
			updateResult = this.updateStandardItemCount(paramDto.getContractId(),paramDto.getRoomId(),
					updateItemIds);
		}
		return insertResult || updateResult;

	}

	/**
	 * 存储合同与物品的配置物品配置信息
	 *
	 * @author xiaona
	 * @param entitys
	 * @return
	 */
	public boolean saveItemConfig(List<ItemdeliveryEntity> entitys) {
		int i = 0;
		for (ItemdeliveryEntity entity : entitys) {
			i = i + this.saveItemConfig(entity);
		}
		return i > 0;
	}

	public  boolean saveRoomItems(List<RoomitemsconfigEntity> roomitemsconfigEntities){
		int  num=0;
		for (RoomitemsconfigEntity roomitemsconfigEntity : roomitemsconfigEntities) {
			num=num+this.saveRoomItemConfig(roomitemsconfigEntity);
		}
		return num>0;
	}

	/**
	 * 将物品转换物品，配置关系Dto
	 *
	 * @author xiaona
	 * @param items
	 * @return
	 */
	public List<ItemdeliveryEntity> converItemTOStandardItem(List<ItemDto> items,MeterItemParamDto paramDto, String userId) {
          Byte typeInfo=0;
		List<ItemdeliveryEntity> meterItemDtos = new ArrayList<ItemdeliveryEntity>();
		for (ItemDto itemDto : items) {
			ItemdeliveryEntity meterItemDto = new ItemdeliveryEntity();
			meterItemDto.setContractid(paramDto.getContractId());
			meterItemDto.setCreaterid(userId);
			meterItemDto.setCreatetime(new Date());
			meterItemDto.setRoomId(paramDto.getRoomId());
			if(paramDto.getTypeInfo()==null){
				if(paramDto.getRentType()==2){
					paramDto.setTypeInfo(1);
				}else {
					paramDto.setTypeInfo(0);
				}
			}
			if(paramDto.getTypeInfo()== RoomTypeInfoEnum.ROOM_TYPE){ //房间
				typeInfo=0;
			}else {
				typeInfo=1;
			}
			meterItemDto.setIsbeditem(typeInfo); //表明是床位还是房间的
			meterItemDto.setItemid(itemDto.getId());
			meterItemDto.setFisdel(0);
			meterItemDto.setItemname(itemDto.getName());
			meterItemDto.setForiginalnum(1);
			meterItemDto.setFpayfee(new BigDecimal(itemDto.getPrice()==null?0.0:itemDto.getPrice()));
			meterItemDto.setPrice(itemDto.getPrice()==null?0.0:itemDto.getPrice());
			meterItemDto.setItemType(itemDto.getType());
			meterItemDto.setCityid(paramDto.getCityId());
			meterItemDto.setFunitmeter(0.0);
			meterItemDto.setFactualnum(1);
			meterItemDto.setIsDefined(2); //表示已添加的
			meterItemDto.setFstate("0");  //表示物品是正常的
			meterItemDto.setFvalid(1); //表示是有效的
			meterItemDtos.add(meterItemDto);
		}
		return meterItemDtos;

	}


	/**
	 * 将物品转换物品，配置关系Dto
	 *
	 * @author xiaona
	 * @param items
	 * @return
	 */
	public List<RoomitemsconfigEntity> converItemTORoomConfig(List<ItemDto> items,MeterItemParamDto paramDto, String userId) {

		List<RoomitemsconfigEntity> roomConfigs = new ArrayList<RoomitemsconfigEntity>();

		for (ItemDto itemDto : items) {
			RoomitemsconfigEntity roomItem = new RoomitemsconfigEntity();
			roomItem.setCreaterid(userId);
			roomItem.setFcreatetime(new Date());
			roomItem.setRoomid(paramDto.getRoomId());
			roomItem.setFtype((byte)paramDto.getTypeInfo().intValue()); //表明是床位还是房间的
			roomItem.setItemid(itemDto.getId());
			roomItem.setFisdel(0);
			roomItem.setFnum(1);
			roomItem.setFprice(itemDto.getPrice());
			roomItem.setCityid(paramDto.getCityId());
			roomConfigs.add(roomItem);
		}

		return roomConfigs;

	}
}
