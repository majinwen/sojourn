package com.zra.item.dao;

/**
 * Created by PC on 2016/9/23.
 */

import com.zra.item.entity.ItemdeliveryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 物品交割和合同的配置关系--xiaona--2016年9月23日
 */
@Repository
public interface ItemdeliveryMapper {

	/**
	 * 根据物品id和合同以及房间或者床位id查询出已经分配了的物品
	 * @author xiaona
	 * @return
	 */
	public List<ItemdeliveryEntity> getExistItemById(@Param("contractId") String contractId, @Param("roomId") String roomId, @Param("rentType") Integer rentType,@Param("itemIds") List<String> itemIds);


	/**
	 * 保存物品配置关系
	 * @author xiaona
	 * @return
	 */
	public int insert(ItemdeliveryEntity entity);



	/**
	 * 更新物品数量
	 * @author tianxf9
	 * @param itemIds
	 * @return
	 */
	public int updateItemCount(@Param("contractId") String contractId, @Param("roomId") String roomId, @Param("itemIds") List<String> itemIds);
}
