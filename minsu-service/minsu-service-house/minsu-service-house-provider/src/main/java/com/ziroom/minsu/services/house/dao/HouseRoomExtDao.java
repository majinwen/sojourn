package com.ziroom.minsu.services.house.dao;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseRoomExtEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>房间信息扩展Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseRoomExtDao")
public class HouseRoomExtDao {
	
	private String SQLID="house.houseRoomExtDao.";
	
	@Autowired
	@Qualifier("house.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * 新增房间扩展实体
	 *
	 * @author loushuai
	 * @created 2017年6月16日 下午6:31:20
	 *
	 * @param houseRoomExtEntity
	 * @return
	 */
	public int insertHouseRoomExtSelective(HouseRoomExtEntity houseRoomExtEntity){
		return mybatisDaoContext.save(SQLID+"insertSelective", houseRoomExtEntity);
	}
	
	/**
	 * 
	 * 根据id修改房间扩展实体
	 *
	 * @author loushuai
	 * @created 2017年6月16日 下午6:35:46
	 *
	 * @param houseRoomExtEntity
	 * @return
	 */
	public int updateByRoomfid(HouseRoomExtEntity houseRoomExtEntity){
		return mybatisDaoContext.update(SQLID+"updateByRoomfid", houseRoomExtEntity);
	}

	/**
	 * 根据房源fid更新房间信息
	 * @author jixd
	 * @created 2017年06月28日 18:42:26
	 * @param
	 * @return
	 */
	public int updateCheckInMsgByHouseFid(HouseBaseExtEntity houseBaseExtEntity){
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("houseFid",houseBaseExtEntity.getHouseBaseFid());
		paraMap.put("minDay",houseBaseExtEntity.getMinDay());
		paraMap.put("checkInTime",houseBaseExtEntity.getCheckInTime());
		paraMap.put("checkOutTime",houseBaseExtEntity.getCheckOutTime());
		return mybatisDaoContext.update(SQLID + "updateCheckInMsgByHouseFid",paraMap);
	}

	
	/**
	 * 
	 * 根据房间fid获取房间扩展对象
	 *
	 * @author loushuai
	 * @created 2017年6月16日 下午6:41:12
	 *
	 * @param roomFid
	 * @return
	 */
	public HouseRoomExtEntity getByRoomfid(String roomFid){
		if(Check.NuNStr(roomFid)){
			return null;
		}
		return mybatisDaoContext.findOne(SQLID+"getByRoomfid", HouseRoomExtEntity.class, roomFid);
	}
	

}