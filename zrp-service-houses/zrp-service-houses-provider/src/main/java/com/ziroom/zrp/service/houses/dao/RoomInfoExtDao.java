/**
 * @FileName: RoomInfoExtDao.java
 * @Package com.ziroom.zrp.service.houses.dao
 * 
 * @author bushujie
 * @created 2018年1月18日 下午3:18:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("houses.roomInfoExtDao")
public class RoomInfoExtDao {
	
	
	private String SQLID = "houses.roomInfoExtDao.";
	
	@Autowired
	@Qualifier("houses.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 
	 * roomFid查询房间扩展信息
	 *
	 * @author bushujie
	 * @created 2018年1月18日 下午3:19:29
	 *
	 * @param roomFid
	 * @return
	 */
	public RoomInfoExtEntity getRoomInfoExtByRoomFid(String roomFid){
		return mybatisDaoContext.findOneSlave(SQLID+"getRoomInfoExtByRoomFid", RoomInfoExtEntity.class, roomFid);
	}
	
	/**
	 * 
	 * 插入房间扩展信息
	 *
	 * @author bushujie
	 * @created 2018年1月18日 下午5:13:54
	 *
	 * @param roomInfoExtEntity
	 */
	public void insertRoomInfoExt(RoomInfoExtEntity roomInfoExtEntity){
		mybatisDaoContext.save(SQLID+"insertRoomInfoExt", roomInfoExtEntity);
	}

	/**
	 * 查询绑定水表的所有房间
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月28日
	 */
	public List<RoomInfoEntity> getAllRoomOfBindingWaterMeter() {
		return mybatisDaoContext.findAll(SQLID + "getAllRoomOfBindingWaterMeter");
	}

}
