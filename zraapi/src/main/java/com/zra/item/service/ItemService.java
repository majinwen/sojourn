package com.zra.item.service;

import java.util.List;

import com.zra.item.entity.MItemIconEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.common.dto.bedInfo.ItemDto;
import com.zra.common.dto.bedInfo.QueryItemDto;
import com.zra.item.dao.ItemMapper;

/**
 * 物品Service
 * @author tianxf9
 *
 */
@Service
public class ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	public List<ItemDto> getAllItems(QueryItemDto queryDto) {
		return this.itemMapper.getAllItemList(queryDto);
	}
	
	public List<ItemDto> getItemsById(List<String> itemIds) {
		return this.itemMapper.getItemListById(itemIds);
	}

	public Integer getYQWYJId() {
		return itemMapper.getYQWYJId();
	}

	public String getYQWYJItemName() {
		return itemMapper.getYQWYJItemName();
	}

	public List<MItemIconEntity> getItemImgUrl(String s) {
		return itemMapper.getItemImgUrl(s);
	}

}
