package com.zra.item.dao;

import com.zra.item.entity.RoomitemsconfigEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PC on 2016/9/23.
 */
@Repository
public interface RoomitemsconfigMapper {
	/**
	 * 查询合同中是否存在相应的物品--xiaona--2016年9月28日
	 * @param roomId
	 * @param typeInfo
	 * @param itemIds
	 * @return
	 */
	List<RoomitemsconfigEntity> getExistItemById(@Param("roomId") String roomId,@Param("typeInfo") Integer typeInfo,@Param("itemIds") List<String> itemIds);

	/**
	 * 保存物品配置关系
	 * @author xiaona
	 * @return
	 */
	public int insert(RoomitemsconfigEntity entity);

	/**
	 * 更新房间中物品的数量--xiaona--2016年9月28日
	 * @param roomId
	 * @param itemIds
	 * @return
	 */
	public int updateRoomItemCount(@Param("roomId") String roomId,@Param("itemIds") List<String> itemIds);
}

