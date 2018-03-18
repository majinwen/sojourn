/**
 * @FileName: RoomInfoExtServiceImpl.java
 * @Package com.ziroom.zrp.service.houses.service
 * 
 * @author bushujie
 * @created 2018年1月18日 下午3:24:32
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.houses.service;

import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomInfoExtEntity;
import com.ziroom.zrp.service.houses.dao.RoomInfoExtDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service("houses.roomInfoExtServiceImpl")
public class RoomInfoExtServiceImpl {
	
	@Resource(name="houses.roomInfoExtDao")
	private RoomInfoExtDao roomInfoExtDao;
	
	
	/**
	 * 
	 * 房间fid查询房间扩展信息
	 *
	 * @author bushujie
	 * @created 2018年1月18日 下午3:26:38
	 *
	 * @param roomFid
	 * @return
	 */
	public RoomInfoExtEntity getRoomInfoExtEntityByRoomFid(String roomFid){
		return roomInfoExtDao.getRoomInfoExtByRoomFid(roomFid);
	}

	/**
	 * 查询绑定水表的所有房间
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月28日
	 */
	public List<RoomInfoEntity> getAllRoomOfBindingWaterMeter() {
		return roomInfoExtDao.getAllRoomOfBindingWaterMeter();
	}
}
