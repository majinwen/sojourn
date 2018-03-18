package com.zra.item.dao;

import java.util.List;

import com.zra.item.entity.MItemIconEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.common.dto.bedInfo.ItemDto;
import com.zra.common.dto.bedInfo.QueryItemDto;

/**
 * 物品Dto
 * @author tianxf9
 *
 */
@Repository
public interface ItemMapper {

	/**
	 * 查询物品列表
	 * @author tianxf9
	 * @return
	 */
	public List<ItemDto> getAllItemList(QueryItemDto queryDto);

	/**
	 * 根据id查询物品
	 * @author tianxf9
	 * @param itemIds
	 * @return
	 */
	public List<ItemDto> getItemListById(List<String> itemIds);

	Integer getYQWYJId();

	String getYQWYJItemName();

	List<MItemIconEntity> getItemImgUrl(@Param("itemId") String itemId);
}
