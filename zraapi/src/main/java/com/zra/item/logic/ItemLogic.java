package com.zra.item.logic;

import java.util.List;

import com.zra.item.entity.MItemIconEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.dto.bedInfo.ItemDto;
import com.zra.common.dto.bedInfo.QueryItemDto;
import com.zra.item.service.ItemService;

/**
 * 物品logic
 * @author tianxf9
 *
 */
@Component
public class ItemLogic {
	
	@Autowired
	private ItemService itemService;
	
	public List<ItemDto> getAllItems(QueryItemDto queryDto) {
		return this.itemService.getAllItems(queryDto);
	}
	
	public List<ItemDto> getItemsById(List<String> itemIds) {
		return this.itemService.getItemsById(itemIds);
	}

	public List<MItemIconEntity> getItemImgUrl(String s) {
		return itemService.getItemImgUrl(s);
	}

}
